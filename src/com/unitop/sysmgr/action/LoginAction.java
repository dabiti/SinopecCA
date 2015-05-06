package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.Rights;
import com.unitop.config.SystemConfig;
import com.unitop.framework.util.IPTool;
import com.unitop.framework.util.PasswordUtil;
import com.unitop.framework.util.StringUtil;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.LoginForm;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.InitSystem;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.PrivilegeService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.ControlRights;
import com.unitop.util.CoreBillUtils;
import com.unitop.util.DesUtil;

@Controller("/login")
public class LoginAction extends ExDispatchAction {
	@Resource
	private ControlRights controlRights;
	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private OrgService OrgService;
	@Resource
	private InitSystem InitSystem;
	@Resource
	private ZhanghbService zhanghbService;

	/*
	 * 柜员登录 逻辑
	 */
	public ActionForward loginsys(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;
		// String code = loginForm.getCode();
		// System.out.println("code=" + code);
		String typeofrequest = loginForm.getTypeofrequest();
		// //System.out.println("typeofrequest=" + typeofrequest);
		String clerkType = loginForm.getClerkType();
		// System.out.println("clerkType=" + clerkType);
		String ipaddress = IPTool.getIpAddr(request);
		// System.out.println("ip=" + ipaddress);

			// 系统自身登录
			// 初始化
			String clerkCode = null;
			boolean manageFlag = false;
			SystemConfig systemConfig = SystemConfig.getInstance();
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			try {
				// 同步参数
				InitSystem.synchronousSystemParameters();
				// 版本控制
				String state = controlRights.control();
				if ("试用期满".equals(state)) {
					return this.showMessageJSP(mapping, request, "login.error",
							"该系统为试用版，试用期限已过！");
				} else if ("终端数满".equals(state)) {
					return this.showMessageJSP(mapping, request, "login.error",
							"同时在线人数达上限！");
				} else if ("版本错误".equals(state)) {
					return this.showMessageJSP(mapping, request, "login.error",
							"没有权限使用此系统");
				}
				if ("shiyong".equals(systemConfig.getValue(
						"version"))) {
					String product_period = "试用期："
							+ systemConfig.getValue("productperiod") + "  版本号："
							+systemConfig.getValue("versionnum");
					request.getSession().setAttribute("product_period",
							product_period);
				} else {
					request.setAttribute("orgCode", loginForm.getOrgCode());
					request.getSession().setAttribute(
							"product_period",
							"正式版   版本号："
									+ controlRights.getControlMap().get(
											"versionnum"));
				}

				clerkCode = loginForm.getCode();
				Clerk clerk = clerkManageService.getClerkByCode(clerkCode);

				// 先判断柜员是否存在
				if (clerk == null || "".equals(clerk.getCode())) {
					return this.showMessageJSP(mapping, request, "login.error",
							"柜员不存在!");
				} else {
					// 获得柜员信息
					String update = clerk.getUpdateDate();
					Integer errorTime = clerk.getErrortime() == null ? new Integer(
							0)
							: clerk.getErrortime();
					Org parent_org = OrgService.getOrgByCode(clerk
							.getShOrgCode());
					clerk.setShOrgName(parent_org.getName());
					Org org = OrgService.getOrgByCode(clerk.getOrgcode());	
					clerk.setParentorg(org.getParentCode());
					clerk.setOrgname(org.getName());

					// 设置柜员权限标识
					if ("0".equals(org.getWdflag())
							|| "1".equals(org.getWdflag())) {
						manageFlag = true;
					}

					if (clerkType != null
							&& !clerkType.equals(clerk.getLeixbs())) {
						return this.showMessageJSP(mapping, request,
								"login.error", "柜员号与柜员登录类型不符!");
					}

					// 验印柜员
						/*
						 * 判断登录密码是否正确
						 */
						if (clerk.getPassword().equals(loginForm.getPassword())) {

							// 密码正确，判断登录错误次数是否超过6次
							if (errorTime.intValue() > (Integer.valueOf(systemConfig.getValue("errorpasswordtimes")) - 1)) {
								return this.showMessageJSP(mapping, request,
										"login.error", "密码输入次数过多，用户被锁定！");
							}
							clerk.setErrortime(0);
							clerkManageService.setErrorNum(clerk.getCode(), "0");// 如果密码正确，清空密码错误次数

							if (clerk.getIp() != null
									&& !clerk.getIp().trim().equals("")) {
								return this.showMessageJSP(mapping, request,"login.error", "柜员【" + clerk.getCode()+ "】已在IP为:" + clerk.getIp()+ "的机器上登录!");
							}
							//Clerk ip_clerk = clerkManageService.getClerkCountByIp(ipaddress);

							// 判断柜员ip是否是本机器ip，如果不是，则提示！
							if (clerk.getIp() != null
									&& !ipaddress.equals(clerk.getIp())) {
								Map<String, String> map = new HashMap<String, String>();
								map.put("clerknum", clerk.getCode());
								map.put("clerkip", clerk.getIp());
								return this.showMessageJSP(mapping, request,"login.error", getPromptService().getPromptMsg("YYA-ip_err1",map));
							}


							// 判断登录柜员权限
							if (manageFlag
									&& systemConfig.getAdminCode().equals(
											loginForm.getCode())) {
								// 标识此柜员为“系统管理员”
								clerk.setSysManager("administrator");
							}
							// 判断登录柜员权限
							if (manageFlag
									&& (clerk.getOrgcode() + systemConfig
											.getSuperManager())
											.equals(loginForm.getCode())) {
								// 标识此柜员为“系统管理员”
								clerk.setSysManager("administrator");
							}

							if (clerk.getIp() == null) {
								clerk.setIp(ipaddress);
								clerkManageService.updateClerk(clerk);
								clerk.setPassword(PasswordUtil.decodePwd(clerk
										.getPassword()));
							}

							// 判断是否强制修改密码
							if ("0".equals(clerk.getClerkstatus()))
								if ("1".equals(systemConfig
										.getValue("clerk_firstlogincpw"))) {
									clerk.clerkMotion = "强制修改密码";
									request.getSession().setAttribute("clerk",
											clerk);
									this.showMessageJSP(mapping, request,
											"login.error", "第一登录请修改密码再进行登录!");
									return mapping
											.findForward("changepwd.success");
								}

							// 判断密码使用天数是否>90天
							if (update != null && !"".equals(update)) {
								Date updatedate = format.parse(update);
								long mm = rightNow.getTime()
										- updatedate.getTime();
								long day = mm / (24 * 60 * 60 * 1000);
								if (day > (Integer.valueOf(systemConfig
										.getValue("changpassworddays")))) {
									clerk.clerkMotion = "密码过期";
									request.getSession().setAttribute("clerk",
											clerk);
									this.showMessageJSP(mapping,request,"login.error","密码使用天数>"+ systemConfig.getValue("changpassworddays")+ "天！请修改密码后重新登录");
									return mapping.findForward("changepwd.success");
								}
							}

							/*
							 * 获取柜员角色权限集合
							 */
							Map juesMap = privilegeService
									.getPrivilegeForMenue(clerkCode);
							if (!(manageFlag && (clerk.getOrgcode() + systemConfig
									.getSuperManager()).equals(loginForm
									.getCode()))) {
								if (juesMap.size() <= 0) {
									return this.showMessageJSP(mapping,
											request, "login.error",
											"该柜员尚未分配权限，请联系管理员!");
								}
							}
							clerk.setJuesMap(juesMap);
							clerk.setRoleStr(StringUtil.mapToString(juesMap));
							String roleName = clerkManageService
									.getClerkByOrgClerkName(clerkCode);
							clerk.setPostName(roleName);
						} else {
							// 大小猫跳过用户锁定
							if (!(manageFlag && (clerk.getOrgcode() + systemConfig
									.getSuperManager()).equals(loginForm
									.getCode()))) {
								if (errorTime.intValue() > (Integer
										.valueOf(systemConfig
												.getValue("errorpasswordtimes")) - 2)) {
									clerkManageService.setErrorNum(clerk
											.getCode(), "6");
									return this.showMessageJSP(mapping,
											request, "login.error",
											"密码输入次数过多，用户被锁定!");
								}
								if (clerk.getLogDate() == null
										|| "".equals(clerk.getLogDate())) {
									clerk.setLogDate(date);
								}
								if (date.equals(clerk.getLogDate())) {
									if (clerk.getErrortime() == null
											|| "".equals(clerk.getErrortime())) {
										clerk.setErrortime(0);
									}
									clerk.setErrortime(new Integer(clerk.getErrortime().intValue() + 1));
								} else if (errorTime.intValue() < (Integer.valueOf(systemConfig.getValue("errorpasswordtimes")) - 1)) {
									clerk.setErrortime(new Integer(1));
									clerk.setLogDate(date);
								}
								clerkManageService.updateClerk(clerk);
								Map<String, String> map = new HashMap<String, String>();
								map.put("clerknum", clerk.getCode());
								map.put("n_error", "" + clerk.getErrortime());
								map.put("leavetimes",""+ (Integer.valueOf(systemConfig.getValue("errorpasswordtimes")) - clerk.getErrortime()));
								return this.showMessageJSP(mapping, request,
										"login.error", getPromptService().getPromptMsg("YYA-pwdError",map));
							}
							request.setAttribute("orgCode", loginForm
									.getOrgCode());
							Map<String, String> errormap = new HashMap<String, String>();
							return this.showMessageJSP(mapping, request,
									"login.error", getPromptService()
											.getPromptMsg("YYA-pwdErrorCat",
													errormap));
						}
				}

				HttpSession session = request.getSession();
//				session.setMaxInactiveInterval(Integer.valueOf(systemConfig
//						.getValue("outtime")));

				// 记录柜员登录日期
				clerk.setLoginDate(date);
				session.setAttribute("clerk", clerk);
				Cookie orgcode = new Cookie("orgcodecookie", loginForm
						.getOrgCode());
				orgcode.setMaxAge(60 * 60 * 24 * 90);
				response.addCookie(orgcode);
				this.createManageLog(clerkCode, "登录");
				clerkManageService.setErrorNum(clerk.getCode(), "0");
				Rights.getInstance().setNowonline(
						Rights.getInstance().getNowonline() + 1);// 增加在线人数

				// 定向跳转
				ActionForward forward = new ActionForward();
				forward.setPath("/index.jsp");
				forward.setRedirect(true);
				return forward;
			} catch (Exception e) {
				logString.append("(" + getClass() + ") 输入：柜员号 " + clerkCode);
				return this.errrForLogAndException(e, mapping, request,
						"login.error");
			} finally {
				loginForm.setPassword(null);
			}
	}

	/*
	 * 强制签退
	 */
	public ActionForward forcedtosign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;
		try {
			String clerkCode = loginForm.getCode();
			String clerkType = loginForm.getClerkType();
			Clerk clerk = clerkManageService.getClerkByCode(clerkCode);
			if (clerk == null) {
				return this.showMessageJSP(mapping, request,
						"forcedtosign.error", "柜员不存在!");
			} else {
				if (clerkType != null && !clerkType.equals(clerk.getLeixbs())) {
					return this.showMessageJSP(mapping, request,
							"forcedtosign.error", "柜员号与柜员类型不符!");
				}
				if (clerk.getIp() == null) {
					return this.showMessageJSP(mapping, request,
							"forcedtosign.error", "柜员没有锁定,不需要签退!");
				} else {
					if (clerkType != null) {

						if ("0".equals(clerkType)) {
							if (!"0".equals(clerk.getLeixbs())) {
								return this.showMessageJSP(mapping, request,
										"forcedtosign.error", "柜员号与柜员类型不符!");
							}
							if (!clerk.getPassword().equals(
									loginForm.getPassword())) {
								return this.showMessageJSP(mapping, request,
										"forcedtosign.error", "密码错误!");
							}
						}
					} else {
						throw new Exception();
					}
					clerk.setIp(null);
					clerkManageService.updateClerk(clerk);
					this.showMessageJSP(mapping, request, "login.error",
							"柜员号:[" + clerk.getCode() + "],签退成功!");
					return mapping.findForward("login.error");
				}
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
					"forcedtosign.error");
		} finally {
			loginForm.setPassword(null);
		}
	}

	/**
	 *身份认证\授权
	 */
	public ActionForward authorized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		try {

			out = response.getWriter();

			String clerkCode = request.getParameter("clerkCode");
			String quanxbs = request.getParameter("quanxbs");// 获取需要验证的权限
			String checkType = URLDecoder.decode(request
					.getParameter("checkType"), "utf-8");
			Clerk clerk = null;
			if (!checkType.equals("登录")) {
				clerk = (Clerk) request.getSession().getAttribute("clerk");

			}
			Clerk authorizationClerk = clerkManageService
					.getClerkByCode(clerkCode);

			if (authorizationClerk == null) {
				out.print("0");// 柜员不存在
				return null;
			}
			if (authorizationClerk.getName() == null
					|| authorizationClerk.getName().equals("")) {
				out.print("0");// 柜员不存在
				return null;
			}

			if (!checkType.equals("登录")) {
				if (clerkCode.equals(clerk.getCode())) {
					out.println("4");// 柜员不能是登录柜员
					return null;
				}

				if (!authorizationClerk.getOrgcode().trim().equals(
						clerk.getOrgcode().trim())) {
					out.println("6");// 不同机构 没有权限
					return null;
				}
			}
			String result = "";
			// 判断身份验证模式 是否通过质问服务验证
			String model = SystemConfig.getInstance().getValue("feature_model");
			if (model.equals("0")) {
				result = "000000";
			} else {
				if (authorizationClerk.getLeixbs().equals("0")) {
					// System.out.println(authorizationClerk.getPassword()+"      ,       "+request.getParameter("passwd"));
					result = authorizationClerk.getPassword().equals(
							request.getParameter("passwd")) ? "000000"
							: "000002";
				} else {
					// 通过指纹服务进行身份验证
					result = clerkManageService.checkFeature(request);
				}
			}
			// System.out.println("result : "+result);
			// result="000000";
			if (result.indexOf("000001") != -1) {
				out.println("2" + result.substring(6));// 失败，身份验证失败
				return null;
			}

			if ("000002".equals(result)) {
				out.println("3");// 失败，密码错误
				return null;
			}
			if ("000003".equals(result)) {
				out.println("5");// 失败，身份验证连接失败
				return null;
			}

			if ("000000".equals(result)) {

				if (checkType.equals("授权") || checkType.equals("复核")) {
					// 当身份验证通过时并且操作类型为授权是 检查柜员是否有需要授权业务的权限
					if (result.equals("000000")) {
						Map juesMap = privilegeService
								.getPrivilegeForMenue(clerkCode);
						String quanxStr = StringUtil.mapToString(juesMap);
						int i = quanxStr.indexOf(quanxbs);
						if (i == -1) {
							out.println("6");// 没有权限
							return null;
						}
					}
					String manageType = URLDecoder.decode(request
							.getParameter("titleName"), "utf-8");
					String account = request.getParameter("account");
					if (account != null && account.length() > 17) {
						account = CoreBillUtils.parseTypeN(account, 17);
					}
					String manageDate = getSystemMgrService()
							.getSystetemNowDate().substring(0, 10);
					String manageTime = getSystemMgrService()
							.getSystetemNowDate().substring(11, 19);
					clerkManageService.createAuthorizedLog(account, manageType,
							manageTime, manageDate, clerk.getCode(),
							authorizationClerk.getCode());
				}
				out.println("1");// 成功，身份验证连接失败
				return null;
			}

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			out.println("5");
			return null;
		} finally {
			out.close();
		}
	}

	public ActionForward getTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		String jigh = request.getParameter("jigh");
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			out = response.getWriter();

			if (jigh == null || jigh.trim().length() == 0) {
				if (clerk == null || clerk.getOrgcode() == null
						|| clerk.getOrgcode().equals("")) {
					out.print("0");
					return null;
				} else {
					jigh = clerk.getOrgcode();
				}
			}
			int num = zhanghbService.countTodoZhanghbList(jigh);
			out.print(num);
			return null;
		} catch (Exception e) {
			out.print("0");
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;

	}

	
	
	
	public ActionForward checkLoginForUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String clerkType = request.getParameter("clerkType");
		String clerkCode = request.getParameter("clerkCode");
		String clerkPassword  = request.getParameter("clerkPassword");
		
		String ipaddress = IPTool.getIpAddr(request);
		PrintWriter out = null;

			// 系统自身登录
			// 初始化
			boolean manageFlag = false;
			SystemConfig systemConfig = SystemConfig.getInstance();
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				out = response.getWriter();
				
				// 同步参数
				InitSystem.synchronousSystemParameters();
				
				String isupdatecache =  SystemConfig.getInstance().getValue("isupdatecache");
				if("1".equals(isupdatecache)){
					out.print("0");
					return null;
				}
				// 版本控制
				String state = controlRights.control();
				if ("试用期满".equals(state)) {
					out.print("0");
					return null;
				} else if ("终端数满".equals(state)) {
					out.print("0");
					return null;
				} else if ("版本错误".equals(state)) {
					out.print("0");
					return null;
				}

				Clerk clerk = clerkManageService.getClerkByCode(clerkCode);

				// 先判断柜员是否存在
				if (clerk == null || "".equals(clerk.getCode())) {
					out.print("0");
					return null;
				} else {
					// 获得柜员信息
					String update = clerk.getUpdateDate();
					Integer errorTime = clerk.getErrortime() == null ? new Integer(
							0)
							: clerk.getErrortime();
//					Org parent_org = OrgService.getOrgByCode(clerk
//							.getShOrgCode());
//					clerk.setShOrgName(parent_org.getName());
					Org org = OrgService.getOrgByCode(clerk.getOrgcode());

					// 设置柜员权限标识
					if ("0".equals(org.getWdflag())
							|| "1".equals(org.getWdflag())) {
						manageFlag = true;
					}

					if (clerkType != null
							&& !clerkType.equals(clerk.getLeixbs())) {
						out.print("0");
						return null;
					}

					// 验印柜员
					if (clerkType != null && "0".equals(clerkType)) {
						/*
						 * 判断登录密码是否正确
						 */
						if (clerk.getPassword().equals(clerkPassword)) {

							// 密码正确，判断登录错误次数是否超过6次
							if (errorTime.intValue() > (Integer.valueOf(systemConfig.getValue("errorpasswordtimes")) - 1)) {
								out.print("0");
								return null;
							}

							if (clerk.getIp() != null
									&& !clerk.getIp().trim().equals("")) {
								out.print("0");
								return null;
							}

							// 判断柜员ip是否是本机器ip，如果不是，则提示！
							if (clerk.getIp() != null
									&& !ipaddress.equals(clerk.getIp())) {
								out.print("0");
								return null;
							}

							// 判断登录柜员权限
							if (manageFlag
									&& systemConfig.getAdminCode().equals(
											clerkCode)) {
								// 标识此柜员为“系统管理员”
								clerk.setSysManager("administrator");
							}
							// 判断登录柜员权限
							if (manageFlag
									&& (clerk.getOrgcode() + systemConfig
											.getSuperManager())
											.equals(clerkCode)) {
								// 标识此柜员为“系统管理员”
								clerk.setSysManager("administrator");
							}

							// 判断是否强制修改密码
							if ("0".equals(clerk.getClerkstatus()))
								if ("1".equals(systemConfig
										.getValue("clerk_firstlogincpw"))) {
									out.print("0");
									return null;
								}

							// 判断密码使用天数是否>90天
							if (update != null && !"".equals(update)) {
								Date updatedate = format.parse(update);
								long mm = rightNow.getTime()
										- updatedate.getTime();
								long day = mm / (24 * 60 * 60 * 1000);
								if (day > (Integer.valueOf(systemConfig
										.getValue("changpassworddays")))) {
									out.print("0");
									return null;
								}
							}
							String cacheAdd = OrgService.getCacheAddByCode(clerk
									.getShOrgCode());
							out.print("1,"+cacheAdd);
							return null;

						} else {
							out.print("0");
							return null;
						}
						// 核心柜员
					} else if (clerkType != null && "1".equals(clerkType)) {

					
						if (clerk.getIp() != null
								&& !clerk.getIp().trim().equals("")) {
							out.print("0");
							return null;
						}

						// 判断柜员ip是否是本机器ip，如果不是，则提示！
						if (clerk.getIp() != null
								&& !ipaddress.equals(clerk.getIp())) {
							out.print("0");
							return null;
						}

						// 判断登录柜员权限
						if (manageFlag
								&& systemConfig.getAdminCode().equals(
										clerkCode)) {
							// 标识此柜员为“系统管理员”
							clerk.setSysManager("administrator");
						}
						// 判断登录柜员权限
						if (manageFlag
								&& (clerk.getOrgcode() + systemConfig
										.getSuperManager()).equals(clerkCode)) {
							// 标识此柜员为“系统管理员”
							clerk.setSysManager("administrator");
						}
						String cacheAdd = OrgService.getCacheAddByCode(clerk
								.getShOrgCode());
						out.print("1,"+cacheAdd);
						return null;
					}
				}
				out.print("0");
				return null;
			} catch (Exception e) {
				out.print("0");
				return null;
			} 

	}
}
