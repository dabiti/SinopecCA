package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.YinjkManageLog;
import com.unitop.sysmgr.bo.YinjkNum;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.YinjkForm;
import com.unitop.sysmgr.service.KagService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.KagServiceImpl;

@Controller("/yinjkOperate")
public class YinjkManageAction extends ExDispatchAction {
	@Resource
	private ZhanghbService zhanghbService;

	@Resource
	private KagService kagService;

	public ZhanghbService getZhanghbService() {
		return zhanghbService;
	}

	public void setZhanghbService(ZhanghbService zhanghbService) {
		this.zhanghbService = zhanghbService;
	}

	// 跳转到查询和归还申请界面
	public ActionForward toApply(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("toApply");
		return actionMapping.findForward("toApply");
	}

	public ActionForward getYinjk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		YinjkForm yinjkForm = (YinjkForm) actionForm;
		try {
			TabsBo TabsBo = this.createTabsBo(request);
			KagServiceImpl kagServiceImpl = (KagServiceImpl) kagService;
			kagServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo = kagService.getYinjk(yinjkForm);
			this.showTabsModel(request, tabsBo);
			return super.showMessageJSPForFeny(actionMapping, request, tabsBo,
					"toApply");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"toputandgetcard");
		}
	}

	/**
	 * 跳转到统计印鉴卡数量页面
	 */
	public ActionForward forqueryyinjknum(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yform = (YinjkForm) actionForm;
			// 界面加载机构号
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			// 柜员所属机构号
			yform.setNetpointflag(clerk.getOrgcode());
			return actionMapping.findForward("yinjknum.show.net");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjknum.show.net");
		}
	}

	/**
	 * 统计查询印鉴卡数量
	 */
	public ActionForward queryyinjknum(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			YinjkForm yform = (YinjkForm) actionForm;
			String orgnum = yform.getNetpointflag();
			String shifbhxj = yform.getRemark();
			Org org =OrgService.getOrgByCode(orgnum);
			if(org==null){
				return this.showMessageJSP(actionMapping, request,
						"yinjknum.show.net", "机构不存在!");
			}
			long i = 0;
			if (this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(),
					orgnum)) {
				i = yinjkService.yinjkNumber(orgnum, shifbhxj);
				request.setAttribute("YinjkNum", i);
			} else {
				return this.showMessageJSP(actionMapping, request,
						"yinjknum.show.net", "无权限查看该机构信息!");
			}
			yform.setNetpointflag(yform.getNetpointflag());
			return actionMapping.findForward("yinjknum.show.net");
		} catch (Exception e) {
			request.setAttribute("YinjkNum", "查询失败");
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjknum.show.net");
		}

	}

	/**
	 *分行入库页面跳转
	 */
	public ActionForward saveFenhrkForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkFenhrk");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkFenhrk");
		}
	}

	/**
	 * 分行入库
	 */
	public ActionForward saveFenhrk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			yinjk.setYinjkh(yinjkform.getYinjkh());
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkFenhrk", "总行柜员不可进行此操作");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != 0) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkFenhrk", "印鉴卡已经存在！");
			}
			yinjk.setYinjkzt("分行库存");
			yinjk.setJigh(clerk.getOrgcode());
			int i = Integer.parseInt(num);

			String content = "分行[" + clerk.getOrgcode() + "]入库" + num + "张印鉴卡";
			yinjkService.save(yinjk, i);
			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "分行入库",
					"分行库存", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkFenhrk",
					"凭证已入库!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkFenhrk",
					"凭证入库失败!");
		}
	}

	private YinjkManageLog createYinjkManageLog(YinjkForm yinjkform,
			Clerk clerk, String manageType, String yinjkzt, String managecontent) {
		YinjkManageLog log = new YinjkManageLog();
		String nowDate = getSystemMgrService().getSystetemNowDate();
		log.setManagedate(nowDate.substring(0, 10));
		log.setManagetime(nowDate.substring(11, 19));
		log.setYinjkh(yinjkform.getYinjkh());
		log.setClerknum(clerk.getCode());
		log.setUpflag(clerk.getOrgcode());
		log.setManagetype(manageType);
		log.setYinjkzt(yinjkzt);
		log.setManagecontent(managecontent);
		return log;
	}

	/**
	 *机构出库页面跳转
	 */
	public ActionForward saveJigckForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkJigck");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkJigck");
		}
	}

	/**
	 * 机构出库
	 */
	public ActionForward saveJigck(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			String endYinjkh = yinjkform.getEndYinjkh();

			Yinjk yinjk = new Yinjk();

			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigck", "总行柜员不可进行此操作");
			}
			// 判断当前机构级别
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "分行"
					: orgFlag.equals("3") ? "支行" : "总行";
			String yinjkzt = jiginfo + "库存";
			// 设置查询条件
			yinjk.setYinjkzt(yinjkzt);
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			int i = Integer.parseInt(num);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigck", "出库印鉴卡不存在或状态不符合操作条件!");
			}
			if (!getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),
					yinjkform.getReceiveOrgCode()))
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigck", "只有上下級机构之间可以进行印鉴卡的出库!");

			yinjk.setYinjkzt(jiginfo + "在途");
			yinjk.setJigh(yinjkform.getReceiveOrgCode());

			String content = jiginfo + "[" + clerk.getOrgcode() + "]出库" + num
					+ "张印鉴卡到机构[" + yinjk.getJigh() + "]";
			yinjkService.save(yinjk, i);
			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, jiginfo
					+ "出库", jiginfo + "在途", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkJigck",
					"凭证已出库!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkJigck",
					"凭证出库失败!");
		}
	}

	// 回显领用机构信息
	public ActionForward getOrgInfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("gbk");
			out = response.getWriter();
			HttpSession session = request.getSession();
			String receiveOrgCode = request.getParameter("orgCode");
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			if (receiveOrgCode.equals(clerk.getOrgcode())) {
				result = "1";
			}else{
				result ="2";
			} 
				Org org = OrgService.getOrgByCode(receiveOrgCode);
				result += ","+org.getName();
				result +=","+org.getWdflag();
			return null;
		} catch (Exception e) {
			result = "0";
			return null;
		} finally {
			out.print(result);
			out.close();
		}
	}

	/**
	 *机构入库页面跳转
	 */
	public ActionForward saveJigrkForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkJigrk");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkJigrk");
		}
	}

	/**
	 * 机构入库
	 */
	public ActionForward saveJigrk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigrk", "总行柜员不可进行此操作");
			}

			yinjk.setYinjkzt("分行在途");
			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigrk", "入库印鉴卡不存在或状态不符合操作条件!");
			}
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "分行"
					: orgFlag.equals("3") ? "支行" : "总行";
			yinjk.setYinjkzt(jiginfo + "库存");

			String content = "机构[" + clerk.getOrgcode() + "]入库" + num + "张印鉴卡";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "机构入库",
					jiginfo + "库存", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkJigrk",
					"凭证已入库!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkJigrk",
					"凭证入库失败!");
		}
	}

	/**
	 *柜员领用页面跳转
	 */
	public ActionForward saveGuiylyForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkGuiyly");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkGuiyly");
		}
	}

	/**
	 * 柜员领用
	 */
	public ActionForward saveGuiyly(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "分行"
					: orgFlag.equals("3") ? "支行" : "总行";
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "总行柜员不可进行此操作");
			}

			yinjk.setYinjkzt(jiginfo + "库存");
			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "印鉴卡不存在或状态不符合操作条件!");
			}

			Clerk lyClerk = clerkService.getClerkByCode(yinjkform
					.getLyClerkCode());
			if (lyClerk == null) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "领用柜员不存在");

			}
			if (!lyClerk.getOrgcode().equals(clerk.getOrgcode())) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "领用柜员应为本机构柜员");
			}

			yinjk.setYinjkzt("可用");
			yinjk.setClerkcode(lyClerk.getCode());
			String content = "柜员[" + yinjk.getClerkcode() + "]领用" + num
					+ "张印鉴卡";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "柜员领用",
					"可用", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkGuiyly",
					"领用成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkGuiyly",
					"领用失败!");
		}
	}

	/**
	 *柜员上缴页面跳转
	 */
	public ActionForward saveGuiysjForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkGuiysj");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkGuiysj");
		}
	}

	/**
	 * 柜员上缴
	 */
	public ActionForward saveGuiysj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setClerkcode(yinjkform.getLyClerkCode());
			yinjk.setYinjkzt("可用");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "分行"
					: orgFlag.equals("3") ? "支行" : "总行";
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiysj", "总行柜员不可进行此操作!");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiysj", "印鉴卡不存在或状态不符合操作条件!");
			}

			yinjk.setYinjkzt(jiginfo + "库存");
			yinjk.setClerkcode("");
			String content = "柜员[" + yinjkform.getLyClerkCode() + "]上缴" + num
					+ "张印鉴卡";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "柜员上缴",
					jiginfo + "库存", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkGuiysj",
					"上缴成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkGuiysj",
					"上缴失败!");
		}
	}

	/**
	 *柜员调剂页面跳转
	 */
	public ActionForward saveGuiytjForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkGuiytj");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkGuiytj");
		}
	}

	/**
	 * 柜员调剂
	 */
	public ActionForward saveGuiytj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setClerkcode(clerk.getCode());
			yinjk.setYinjkzt("可用");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "分行"
					: orgFlag.equals("3") ? "支行" : "总行";
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "总行柜员不可进行此操作");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "印鉴卡不存在或状态不符合操作条件!");
			}

			Clerk trClerk = clerkService.getClerkByCode(yinjkform
					.getLyClerkCode());
			if (trClerk == null) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "调入柜员不存在");

			}
			if (!trClerk.getOrgcode().equals(clerk.getOrgcode())) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "调入柜员应为本机构柜员");
			}

			yinjk.setClerkcode(trClerk.getCode());
			String content = "柜员[" + clerk.getCode() + "]调剂给柜员["
					+ yinjkform.getLyClerkCode() + "]" + num + "张印鉴卡";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "柜员调剂",
					"可用", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkGuiytj",
					"调剂成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkGuiytj",
					"调剂失败!");
		}
	}

	/**
	 * 印鉴卡作废页面跳转
	 */
	public ActionForward saveYinjkzfForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkZuof");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkZuof");
		}
	}

	/**
	 * 印鉴卡作废
	 */
	public ActionForward saveYinjkzf(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setClerkcode(clerk.getCode());
			yinjk.setYinjkzt("可用");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkZuof",
						"总行柜员不可进行此操作");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request, "yinjkZuof",
						"印鉴卡不存在或状态不符合操作条件!");
			}

			yinjk.setYinjkzt("作废");
			String content = "柜员[" + clerk.getCode() + "]作废" + num + "张印鉴卡";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"印鉴卡作废", "作废", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkZuof",
					"作废操作成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkZuof",
					"作废操作失败!");
		}
	}

	/**
	 * 印鉴卡预发放页面跳转
	 */
	public ActionForward saveYinjkyffForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkYff");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkYff");
		}
	}

	/**
	 * 印鉴卡预发放
	 */
	public ActionForward saveYinjkyff(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String currYinjkh = yinjkform.getYinjkh();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkYff",
						"总行柜员不可进行此操作");
			}
			List<Yinjk> yinjkList = yinjkService.getMinCodeYinjk(clerk
					.getCode(), 1);
			Yinjk minYinjk = yinjkList.get(0);
			if (minYinjk == null) {
				return this.showMessageJSP(actionMapping, request, "yinjkYff",
						"当前柜员无可用印鉴卡!");
			}
			if (!currYinjkh.equals(minYinjk.getYinjkh())) {
				return this.showMessageJSP(actionMapping, request, "yinjkYff",
						"预发放的印鉴卡必须为柜员持有的可用并且编号最小的印鉴卡!");
			}

			minYinjk.setYinjkzt("领用");
			String content = "柜员[" + clerk.getCode() + "]预发放印鉴卡" + currYinjkh
					+ "给客户：" + yinjkform.getHum();
			yinjkService.save(minYinjk, 1);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"印鉴卡预发放", "领用", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkYff",
					"预发放操作成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkYff",
					"预发放操作失败!");
		}
	}

	/**
	 * 印鉴卡发放页面跳转
	 */
	public ActionForward yinjkffForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkff");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkff");
		}
	}

	/**
	 * 印鉴卡发放
	 */
	public ActionForward yinjkff(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			List<String> currYinjkhList = new ArrayList<String>();
			currYinjkhList.add(yinjkform.getYinjkh());
			if (yinjkform.getYinjkh2() != null
					&& !yinjkform.getYinjkh2().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh2());
			}
			if (yinjkform.getYinjkh3() != null
					&& !yinjkform.getYinjkh3().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh3());
			}
			if (yinjkform.getYinjkh4() != null
					&& !yinjkform.getYinjkh4().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh4());
			}
			Collections.sort(currYinjkhList);

			List<String> oldYinjkhList = new ArrayList<String>();
			if (yinjkform.getOldyinjkh() != null
					&& !yinjkform.getOldyinjkh().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh());
			}
			if (yinjkform.getOldyinjkh2() != null
					&& !yinjkform.getOldyinjkh2().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh2());
			}
			if (yinjkform.getOldyinjkh3() != null
					&& !yinjkform.getOldyinjkh3().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh3());
			}
			if (yinjkform.getOldyinjkh4() != null
					&& !yinjkform.getOldyinjkh4().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh4());
			}
			Collections.sort(oldYinjkhList);
			
			// 新增和作废印鉴卡分离 currYinjkhList为新增印鉴卡编号列表 oldYinjkhList为作废印鉴卡编号列表
			List<String> commenYinjkhList = new ArrayList<String>();

			for (String string : oldYinjkhList) {
				if (currYinjkhList.contains(string)) {
					commenYinjkhList.add(string);
					currYinjkhList.remove(string);
				}
			}
			for (String string : commenYinjkhList) {
				oldYinjkhList.remove(string);
			}
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkff",
						"总行柜员不可进行此操作");
			}
			List<Yinjk> yinjkList = yinjkService.getMinCodeYinjk(clerk
					.getCode(), currYinjkhList.size());

			if (yinjkList == null || yinjkList.size() < currYinjkhList.size()) {
				return this.showMessageJSP(actionMapping, request, "yinjkff",
						"当前柜员无足够可用印鉴卡");
			}
			for (int i = 0; i < yinjkList.size(); i++) {
				if (!yinjkList.get(i).getYinjkh().equals(currYinjkhList.get(i))) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkff", "发放的印鉴卡必须为柜员持有的可用并且编号最小的印鉴卡!");
				}
			}
			String yinjkContent = "";
			String zhangh = yinjkform.getZhangh();
			Zhanghb zhanghb = zhanghbService.getZhanghb(zhangh);
			String ffType = yinjkform.getFfType();
			String zhuzh = zhanghb.getZhuzh();
			if (ffType.equals("0")) {
				if ((zhuzh != null && !zhuzh.equals(""))
						|| (zhanghb.getYinjkbh() != null && !zhanghb
								.getYinjkbh().equals(""))) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkff", "该账号存在关联印鉴卡，暂时不能发放");
				} else {
					for (Yinjk yinjk : yinjkList) {
						yinjk.setZhangh(zhanghb.getZhangh());
						yinjk.setYinjkzt("已用");
					}

					String yinjkbh = createYinjkbhStr(currYinjkhList);
					zhanghb.setYinjkbh(yinjkbh);
					zhanghbService.update(zhanghb);
					String content = "账号：" + zhanghb.getZhangh()
							+ ";印鉴卡发放(印鉴卡号：" + yinjkbh + ");类型：开户;柜员代码："
							+ clerk.getCode();
					this.createAccountManageLog(zhanghb.getZhangh(), "7",
							content, clerk);
					this.createManageLog(clerk.getCode(), content);

					yinjkService.saveYinjkList(yinjkList);
					yinjkContent = "柜员[" + clerk.getCode() + "]发放印鉴卡" + yinjkbh;

					YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
							"印鉴卡发放|开户", "已用", yinjkContent);
					yinjkService.saveLog(log);
				}
			} else {
				if ((zhuzh == null || !zhuzh.equals(""))
						&& (zhanghb.getYinjkbh() == null || zhanghb
								.getYinjkbh().equals(""))) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkff", "该账号不存在关联印鉴卡，暂时不能发放");
				}
				String yinjkStr = "";
				for (Yinjk yinjk : yinjkList) {
					yinjk.setZhangh(zhanghb.getZhangh());
					yinjk.setYinjkzt("已用");
					yinjkStr += yinjk.getYinjkh();
					yinjkStr += "|";
				}
				// 在印鉴卡表中更新 新增印鉴卡信息
				yinjkService.saveYinjkList(yinjkList);
				yinjkContent = "柜员[" + clerk.getCode() + "]发放印鉴卡"
						+ yinjkStr.substring(0, yinjkStr.length() - 1);
				String type = ffType.equals("1") ? "变更" : "替换";
				YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
						"印鉴卡发放|" + type, "已用", yinjkContent);
				yinjkService.saveLog(log);
				if (oldYinjkhList != null && oldYinjkhList.size() != 0) {

					List<Yinjk> oldYinjkList = new ArrayList<Yinjk>();
					for (String string : oldYinjkhList) {
				
						oldYinjkList
								.add(yinjkService.getYinjkByYinjkbh(string));
					}

					String oldYinjkStr = "";
					for (Yinjk yinjk : oldYinjkList) {
						yinjk.setYinjkzt("作废");
						oldYinjkStr += yinjk.getYinjkh();
						oldYinjkStr += "|";
					}
					// 在印鉴卡表中 作废被替换的印鉴卡
					yinjkService.saveYinjkList(oldYinjkList);
					String yinjkContent2 = "柜员["
							+ clerk.getCode()
							+ "]作废被替换的印鉴卡"
							+ oldYinjkStr
									.substring(0, oldYinjkStr.length() - 1);
					log = createYinjkManageLog(yinjkform, clerk, "印鉴卡发放|"
							+ type, "作废", yinjkContent2);
					yinjkService.saveLog(log);
				}
				for (String string : commenYinjkhList) {
					currYinjkhList.add(string);
				}
				Collections.sort(currYinjkhList);
				String yinjkbh = createYinjkbhStr(currYinjkhList);
				zhanghb.setYinjkbh(yinjkbh);
				zhanghbService.update(zhanghb);
				String content = "账号：" + zhanghb.getZhangh() + ";印鉴卡发放(印鉴卡号："
						+ yinjkbh + ");类型：" + type + ";柜员代码：" + clerk.getCode();
				this.createAccountManageLog(zhanghb.getZhangh(), "7", content,
						clerk);
				this.createManageLog(clerk.getCode(), content);

			}
			return this.showMessageJSP(actionMapping, request, "yinjkff",
					"发放操作成功!" + yinjkContent);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkff",
					"发放操作失败!");
		}
	}
	/**
	 * 印鉴卡内部发放页面跳转
	 */
	public ActionForward internalReleaseForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String rule = "7" + clerk.getOrgcode() + "%";
			String maxZhangh = zhanghbService.getInternalReleaseZhangh(rule);
			yinjkform.setZhangh(maxZhangh);

			return actionMapping.findForward("internalRelease");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"internalRelease");
		}
	}

	/**
	 * 印鉴卡内部发放
	 */
	public ActionForward internalRelease(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String zhangh = yinjkform.getZhangh();
			Zhanghb zhanghb = zhanghbService.getZhanghb(zhangh);
			if (zhanghb != null) {
				return this.showMessageJSP(actionMapping, request,
						"internalRelease", "内部账号已存在！");
			}
			List<String> currYinjkhList = new ArrayList<String>();
			currYinjkhList.add(yinjkform.getYinjkh());
			if (yinjkform.getYinjkh2() != null
					&& !yinjkform.getYinjkh2().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh2());
			}
			if (yinjkform.getYinjkh3() != null
					&& !yinjkform.getYinjkh3().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh3());
			}
			if (yinjkform.getYinjkh4() != null
					&& !yinjkform.getYinjkh4().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh4());
			}
			Collections.sort(currYinjkhList);
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkff",
						"总行柜员不可进行此操作");
			}
			List<Yinjk> yinjkList = yinjkService.getMinCodeYinjk(clerk
					.getCode(), currYinjkhList.size());

			if (yinjkList == null || yinjkList.size() < currYinjkhList.size()) {
				return this.showMessageJSP(actionMapping, request,
						"internalRelease", "当前柜员无足够可用印鉴卡");
			}
			for (int i = 0; i < yinjkList.size(); i++) {
				if (!yinjkList.get(i).getYinjkh().equals(currYinjkhList.get(i))) {
					return this.showMessageJSP(actionMapping, request,
							"internalRelease", "发放的印鉴卡必须为柜员持有的可用并且编号最小的印鉴卡!");
				}
			}
			zhanghb = new Zhanghb();
			zhanghb.setZhangh(zhangh);
			zhanghb.setZhanghxz("基本户");
			zhanghb.setHum(yinjkform.getHum());
			String nowDate = this.getSystemMgrService().getSystetemNowDate();
			zhanghb.setKaihrq(nowDate.substring(0, 10));

			zhanghb.setLianxr(yinjkform.getLinkman());
			zhanghb.setDianh(yinjkform.getPhone());
			zhanghb.setTongctd("不通兑");
			zhanghb.setYouwyj("无");
			zhanghb.setYouwzh("无");
			zhanghb.setZuhshzt("未审");
			zhanghb.setZhanghshzt("已审");
			zhanghb.setYinjshzt("未审");
			zhanghb.setZhanghzt("有效");
			zhanghb.setJigh(clerk.getOrgcode());
			String yinjkhStr = createYinjkbhStr(currYinjkhList);
			zhanghb.setYinjkbh(yinjkhStr);
			
			this
					.createAccountManageLog(zhanghb.getZhangh(), "2", "账户开户",
							clerk);
			String content = "柜员[" + clerk.getCode() + "]向内部账户[" + zhangh
					+ "]发放印鉴卡：" + yinjkhStr;
			for (Yinjk yinjk : yinjkList) {
				yinjk.setYinjkzt("已用");
				yinjk.setZhangh(zhangh);
			}
			zhanghbService.createZhanghb(zhanghb,null);
			yinjkService.saveYinjkList(yinjkList);
			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "内部发放",
					"已用", content);
			yinjkService.saveLog(log);
			this.createManageLog(clerk.getCode(), content);
			return this.showMessageJSP(actionMapping, request,
					"internalRelease", "内部发放操作成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request,
					"internalRelease", "内部发放操作失败!");
		}
	}

	/**
	 * 印鉴卡待销毁页面跳转
	 */
	public ActionForward readydestroyForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("readydestroy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"readydestroy");
		}
	}

	/**
	 * 印鉴卡待销毁
	 */
	public ActionForward readydestroy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setYinjkzt("已用");

			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"readydestroy", "总行柜员不可进行此操作");
			}

			int yinjkNum = yinjkService.countYinjkNumForDestroy(yinjk,
					endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"readydestroy", "印鉴卡不存在或状态不符合操作条件!");
			}

			yinjk.setYinjkzt("待销毁");
			String content = "柜员[" + clerk.getCode() + "]设置" + num + "张印鉴卡为待销毁";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"印鉴卡待销毁", "待销毁", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "readydestroy",
					"待销毁操作成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "readydestroy",
					"待销毁操作失败!");
		}
	}

	/**
	 * 印鉴卡销毁页面跳转
	 */
	public ActionForward destroyForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("destroy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"destroy");
		}
	}

	/**
	 * 印鉴卡销毁
	 */
	public ActionForward destroy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());

			yinjk.setYinjkzt("待销毁");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "destroy",
						"总行柜员不可进行此操作");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request, "destroy",
						"印鉴卡不存在或状态不符合操作条件!");
			}

			yinjk.setYinjkzt("销毁");
			String content = "柜员[" + clerk.getCode() + "]销毁" + num + "张印鉴卡";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"印鉴卡销毁", "销毁", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "destroy",
					"销毁操作成功!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "destroy",
					"销毁操作失败!");
		}
	}

	/**
	 * 印鉴卡余量查询页面跳转
	 */
	public ActionForward yinjkYulcxForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);
			HttpSession session = request.getSession();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjkform.setJigh(clerk.getOrgcode());
			yinjkform.setGuiyh(clerk.getCode());
			yinjkform.setYinjkzt("全部");
			String nowDate = getSystemMgrService().getSystetemNowDate();
			// yinjkform.setBegindate(nowDate.substring(0,10));
			// yinjkform.setEnddate(nowDate.substring(0,10));

			return actionMapping.findForward("yinjkYulcx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkYulcx");
		}
	}

	/**
	 * 印鉴卡余量查询
	 */
	public ActionForward yinjkYulcx(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String jigh = yinjkform.getJigh();
			String clerkCode = yinjkform.getGuiyh();
			if (clerkCode != null && !clerkCode.trim().equals("")) {
				Clerk clerk_ = clerkService.getClerkByCode(clerkCode);
				if (!clerk_.getOrgcode().equals(jigh)) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkYulcx", "柜员号与机构号不匹配");
				}
			}
			boolean pass = getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), jigh);
			if (!pass) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkYulcx", "当前柜员没有权限查看机构[" + jigh + "]下印鉴卡余量");
			}
			YinjkNum yinjkNum = yinjkService.selectYinjkNum(yinjkform);
			if (yinjkNum != null) {
				request.setAttribute("yinjkNum", yinjkNum);
			}
			return actionMapping.findForward("yinjkYulcx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkYulcx");
		}
	}

	/**
	 * 印鉴卡共用查询
	 */

}
