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
	 * ��Ա��¼ �߼�
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

			// ϵͳ�����¼
			// ��ʼ��
			String clerkCode = null;
			boolean manageFlag = false;
			SystemConfig systemConfig = SystemConfig.getInstance();
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			try {
				// ͬ������
				InitSystem.synchronousSystemParameters();
				// �汾����
				String state = controlRights.control();
				if ("��������".equals(state)) {
					return this.showMessageJSP(mapping, request, "login.error",
							"��ϵͳΪ���ð棬���������ѹ���");
				} else if ("�ն�����".equals(state)) {
					return this.showMessageJSP(mapping, request, "login.error",
							"ͬʱ�������������ޣ�");
				} else if ("�汾����".equals(state)) {
					return this.showMessageJSP(mapping, request, "login.error",
							"û��Ȩ��ʹ�ô�ϵͳ");
				}
				if ("shiyong".equals(systemConfig.getValue(
						"version"))) {
					String product_period = "�����ڣ�"
							+ systemConfig.getValue("productperiod") + "  �汾�ţ�"
							+systemConfig.getValue("versionnum");
					request.getSession().setAttribute("product_period",
							product_period);
				} else {
					request.setAttribute("orgCode", loginForm.getOrgCode());
					request.getSession().setAttribute(
							"product_period",
							"��ʽ��   �汾�ţ�"
									+ controlRights.getControlMap().get(
											"versionnum"));
				}

				clerkCode = loginForm.getCode();
				Clerk clerk = clerkManageService.getClerkByCode(clerkCode);

				// ���жϹ�Ա�Ƿ����
				if (clerk == null || "".equals(clerk.getCode())) {
					return this.showMessageJSP(mapping, request, "login.error",
							"��Ա������!");
				} else {
					// ��ù�Ա��Ϣ
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

					// ���ù�ԱȨ�ޱ�ʶ
					if ("0".equals(org.getWdflag())
							|| "1".equals(org.getWdflag())) {
						manageFlag = true;
					}

					if (clerkType != null
							&& !clerkType.equals(clerk.getLeixbs())) {
						return this.showMessageJSP(mapping, request,
								"login.error", "��Ա�����Ա��¼���Ͳ���!");
					}

					// ��ӡ��Ա
						/*
						 * �жϵ�¼�����Ƿ���ȷ
						 */
						if (clerk.getPassword().equals(loginForm.getPassword())) {

							// ������ȷ���жϵ�¼��������Ƿ񳬹�6��
							if (errorTime.intValue() > (Integer.valueOf(systemConfig.getValue("errorpasswordtimes")) - 1)) {
								return this.showMessageJSP(mapping, request,
										"login.error", "��������������࣬�û���������");
							}
							clerk.setErrortime(0);
							clerkManageService.setErrorNum(clerk.getCode(), "0");// ���������ȷ���������������

							if (clerk.getIp() != null
									&& !clerk.getIp().trim().equals("")) {
								return this.showMessageJSP(mapping, request,"login.error", "��Ա��" + clerk.getCode()+ "������IPΪ:" + clerk.getIp()+ "�Ļ����ϵ�¼!");
							}
							//Clerk ip_clerk = clerkManageService.getClerkCountByIp(ipaddress);

							// �жϹ�Աip�Ƿ��Ǳ�����ip��������ǣ�����ʾ��
							if (clerk.getIp() != null
									&& !ipaddress.equals(clerk.getIp())) {
								Map<String, String> map = new HashMap<String, String>();
								map.put("clerknum", clerk.getCode());
								map.put("clerkip", clerk.getIp());
								return this.showMessageJSP(mapping, request,"login.error", getPromptService().getPromptMsg("YYA-ip_err1",map));
							}


							// �жϵ�¼��ԱȨ��
							if (manageFlag
									&& systemConfig.getAdminCode().equals(
											loginForm.getCode())) {
								// ��ʶ�˹�ԱΪ��ϵͳ����Ա��
								clerk.setSysManager("administrator");
							}
							// �жϵ�¼��ԱȨ��
							if (manageFlag
									&& (clerk.getOrgcode() + systemConfig
											.getSuperManager())
											.equals(loginForm.getCode())) {
								// ��ʶ�˹�ԱΪ��ϵͳ����Ա��
								clerk.setSysManager("administrator");
							}

							if (clerk.getIp() == null) {
								clerk.setIp(ipaddress);
								clerkManageService.updateClerk(clerk);
								clerk.setPassword(PasswordUtil.decodePwd(clerk
										.getPassword()));
							}

							// �ж��Ƿ�ǿ���޸�����
							if ("0".equals(clerk.getClerkstatus()))
								if ("1".equals(systemConfig
										.getValue("clerk_firstlogincpw"))) {
									clerk.clerkMotion = "ǿ���޸�����";
									request.getSession().setAttribute("clerk",
											clerk);
									this.showMessageJSP(mapping, request,
											"login.error", "��һ��¼���޸������ٽ��е�¼!");
									return mapping
											.findForward("changepwd.success");
								}

							// �ж�����ʹ�������Ƿ�>90��
							if (update != null && !"".equals(update)) {
								Date updatedate = format.parse(update);
								long mm = rightNow.getTime()
										- updatedate.getTime();
								long day = mm / (24 * 60 * 60 * 1000);
								if (day > (Integer.valueOf(systemConfig
										.getValue("changpassworddays")))) {
									clerk.clerkMotion = "�������";
									request.getSession().setAttribute("clerk",
											clerk);
									this.showMessageJSP(mapping,request,"login.error","����ʹ������>"+ systemConfig.getValue("changpassworddays")+ "�죡���޸���������µ�¼");
									return mapping.findForward("changepwd.success");
								}
							}

							/*
							 * ��ȡ��Ա��ɫȨ�޼���
							 */
							Map juesMap = privilegeService
									.getPrivilegeForMenue(clerkCode);
							if (!(manageFlag && (clerk.getOrgcode() + systemConfig
									.getSuperManager()).equals(loginForm
									.getCode()))) {
								if (juesMap.size() <= 0) {
									return this.showMessageJSP(mapping,
											request, "login.error",
											"�ù�Ա��δ����Ȩ�ޣ�����ϵ����Ա!");
								}
							}
							clerk.setJuesMap(juesMap);
							clerk.setRoleStr(StringUtil.mapToString(juesMap));
							String roleName = clerkManageService
									.getClerkByOrgClerkName(clerkCode);
							clerk.setPostName(roleName);
						} else {
							// ��Сè�����û�����
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
											"��������������࣬�û�������!");
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

				// ��¼��Ա��¼����
				clerk.setLoginDate(date);
				session.setAttribute("clerk", clerk);
				Cookie orgcode = new Cookie("orgcodecookie", loginForm
						.getOrgCode());
				orgcode.setMaxAge(60 * 60 * 24 * 90);
				response.addCookie(orgcode);
				this.createManageLog(clerkCode, "��¼");
				clerkManageService.setErrorNum(clerk.getCode(), "0");
				Rights.getInstance().setNowonline(
						Rights.getInstance().getNowonline() + 1);// ������������

				// ������ת
				ActionForward forward = new ActionForward();
				forward.setPath("/index.jsp");
				forward.setRedirect(true);
				return forward;
			} catch (Exception e) {
				logString.append("(" + getClass() + ") ���룺��Ա�� " + clerkCode);
				return this.errrForLogAndException(e, mapping, request,
						"login.error");
			} finally {
				loginForm.setPassword(null);
			}
	}

	/*
	 * ǿ��ǩ��
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
						"forcedtosign.error", "��Ա������!");
			} else {
				if (clerkType != null && !clerkType.equals(clerk.getLeixbs())) {
					return this.showMessageJSP(mapping, request,
							"forcedtosign.error", "��Ա�����Ա���Ͳ���!");
				}
				if (clerk.getIp() == null) {
					return this.showMessageJSP(mapping, request,
							"forcedtosign.error", "��Աû������,����Ҫǩ��!");
				} else {
					if (clerkType != null) {

						if ("0".equals(clerkType)) {
							if (!"0".equals(clerk.getLeixbs())) {
								return this.showMessageJSP(mapping, request,
										"forcedtosign.error", "��Ա�����Ա���Ͳ���!");
							}
							if (!clerk.getPassword().equals(
									loginForm.getPassword())) {
								return this.showMessageJSP(mapping, request,
										"forcedtosign.error", "�������!");
							}
						}
					} else {
						throw new Exception();
					}
					clerk.setIp(null);
					clerkManageService.updateClerk(clerk);
					this.showMessageJSP(mapping, request, "login.error",
							"��Ա��:[" + clerk.getCode() + "],ǩ�˳ɹ�!");
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
	 *�����֤\��Ȩ
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
			String quanxbs = request.getParameter("quanxbs");// ��ȡ��Ҫ��֤��Ȩ��
			String checkType = URLDecoder.decode(request
					.getParameter("checkType"), "utf-8");
			Clerk clerk = null;
			if (!checkType.equals("��¼")) {
				clerk = (Clerk) request.getSession().getAttribute("clerk");

			}
			Clerk authorizationClerk = clerkManageService
					.getClerkByCode(clerkCode);

			if (authorizationClerk == null) {
				out.print("0");// ��Ա������
				return null;
			}
			if (authorizationClerk.getName() == null
					|| authorizationClerk.getName().equals("")) {
				out.print("0");// ��Ա������
				return null;
			}

			if (!checkType.equals("��¼")) {
				if (clerkCode.equals(clerk.getCode())) {
					out.println("4");// ��Ա�����ǵ�¼��Ա
					return null;
				}

				if (!authorizationClerk.getOrgcode().trim().equals(
						clerk.getOrgcode().trim())) {
					out.println("6");// ��ͬ���� û��Ȩ��
					return null;
				}
			}
			String result = "";
			// �ж������֤ģʽ �Ƿ�ͨ�����ʷ�����֤
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
					// ͨ��ָ�Ʒ�����������֤
					result = clerkManageService.checkFeature(request);
				}
			}
			// System.out.println("result : "+result);
			// result="000000";
			if (result.indexOf("000001") != -1) {
				out.println("2" + result.substring(6));// ʧ�ܣ������֤ʧ��
				return null;
			}

			if ("000002".equals(result)) {
				out.println("3");// ʧ�ܣ��������
				return null;
			}
			if ("000003".equals(result)) {
				out.println("5");// ʧ�ܣ������֤����ʧ��
				return null;
			}

			if ("000000".equals(result)) {

				if (checkType.equals("��Ȩ") || checkType.equals("����")) {
					// �������֤ͨ��ʱ���Ҳ�������Ϊ��Ȩ�� ����Ա�Ƿ�����Ҫ��Ȩҵ���Ȩ��
					if (result.equals("000000")) {
						Map juesMap = privilegeService
								.getPrivilegeForMenue(clerkCode);
						String quanxStr = StringUtil.mapToString(juesMap);
						int i = quanxStr.indexOf(quanxbs);
						if (i == -1) {
							out.println("6");// û��Ȩ��
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
				out.println("1");// �ɹ��������֤����ʧ��
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

			// ϵͳ�����¼
			// ��ʼ��
			boolean manageFlag = false;
			SystemConfig systemConfig = SystemConfig.getInstance();
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				out = response.getWriter();
				
				// ͬ������
				InitSystem.synchronousSystemParameters();
				
				String isupdatecache =  SystemConfig.getInstance().getValue("isupdatecache");
				if("1".equals(isupdatecache)){
					out.print("0");
					return null;
				}
				// �汾����
				String state = controlRights.control();
				if ("��������".equals(state)) {
					out.print("0");
					return null;
				} else if ("�ն�����".equals(state)) {
					out.print("0");
					return null;
				} else if ("�汾����".equals(state)) {
					out.print("0");
					return null;
				}

				Clerk clerk = clerkManageService.getClerkByCode(clerkCode);

				// ���жϹ�Ա�Ƿ����
				if (clerk == null || "".equals(clerk.getCode())) {
					out.print("0");
					return null;
				} else {
					// ��ù�Ա��Ϣ
					String update = clerk.getUpdateDate();
					Integer errorTime = clerk.getErrortime() == null ? new Integer(
							0)
							: clerk.getErrortime();
//					Org parent_org = OrgService.getOrgByCode(clerk
//							.getShOrgCode());
//					clerk.setShOrgName(parent_org.getName());
					Org org = OrgService.getOrgByCode(clerk.getOrgcode());

					// ���ù�ԱȨ�ޱ�ʶ
					if ("0".equals(org.getWdflag())
							|| "1".equals(org.getWdflag())) {
						manageFlag = true;
					}

					if (clerkType != null
							&& !clerkType.equals(clerk.getLeixbs())) {
						out.print("0");
						return null;
					}

					// ��ӡ��Ա
					if (clerkType != null && "0".equals(clerkType)) {
						/*
						 * �жϵ�¼�����Ƿ���ȷ
						 */
						if (clerk.getPassword().equals(clerkPassword)) {

							// ������ȷ���жϵ�¼��������Ƿ񳬹�6��
							if (errorTime.intValue() > (Integer.valueOf(systemConfig.getValue("errorpasswordtimes")) - 1)) {
								out.print("0");
								return null;
							}

							if (clerk.getIp() != null
									&& !clerk.getIp().trim().equals("")) {
								out.print("0");
								return null;
							}

							// �жϹ�Աip�Ƿ��Ǳ�����ip��������ǣ�����ʾ��
							if (clerk.getIp() != null
									&& !ipaddress.equals(clerk.getIp())) {
								out.print("0");
								return null;
							}

							// �жϵ�¼��ԱȨ��
							if (manageFlag
									&& systemConfig.getAdminCode().equals(
											clerkCode)) {
								// ��ʶ�˹�ԱΪ��ϵͳ����Ա��
								clerk.setSysManager("administrator");
							}
							// �жϵ�¼��ԱȨ��
							if (manageFlag
									&& (clerk.getOrgcode() + systemConfig
											.getSuperManager())
											.equals(clerkCode)) {
								// ��ʶ�˹�ԱΪ��ϵͳ����Ա��
								clerk.setSysManager("administrator");
							}

							// �ж��Ƿ�ǿ���޸�����
							if ("0".equals(clerk.getClerkstatus()))
								if ("1".equals(systemConfig
										.getValue("clerk_firstlogincpw"))) {
									out.print("0");
									return null;
								}

							// �ж�����ʹ�������Ƿ�>90��
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
						// ���Ĺ�Ա
					} else if (clerkType != null && "1".equals(clerkType)) {

					
						if (clerk.getIp() != null
								&& !clerk.getIp().trim().equals("")) {
							out.print("0");
							return null;
						}

						// �жϹ�Աip�Ƿ��Ǳ�����ip��������ǣ�����ʾ��
						if (clerk.getIp() != null
								&& !ipaddress.equals(clerk.getIp())) {
							out.print("0");
							return null;
						}

						// �жϵ�¼��ԱȨ��
						if (manageFlag
								&& systemConfig.getAdminCode().equals(
										clerkCode)) {
							// ��ʶ�˹�ԱΪ��ϵͳ����Ա��
							clerk.setSysManager("administrator");
						}
						// �жϵ�¼��ԱȨ��
						if (manageFlag
								&& (clerk.getOrgcode() + systemConfig
										.getSuperManager()).equals(clerkCode)) {
							// ��ʶ�˹�ԱΪ��ϵͳ����Ա��
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
