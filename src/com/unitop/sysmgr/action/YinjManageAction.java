package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.PingzpzConfig;
import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.YinjForm;
import com.unitop.sysmgr.service.PingzService;

@Controller("/yinjManage")
public class YinjManageAction extends ExDispatchAction {
	@Resource
	public PingzService pingzService;

	/**
	 * 查询凭证列表返回页面json信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryPingzlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<Pingzpzb> pingzpzList = pingzService.getPingzList();
			String msg=JSONArray.fromObject(pingzpzList).toString();
			System.out.println(msg);
			out.print(msg);
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			out.print("fail001");
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
		
	} 
	/**
	 * 前台验印跳转页面
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forqiantyy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 页面回显数据库今日时间
			request.setAttribute("date", this.getSystemMgrService()
					.getSystetemNowDate().substring(0, 10));
			YinjForm yinjkform = (YinjForm) actionForm;
			List<Pingzpzb> pingzpzList = pingzService.getPingzList();

			String yyserviceurl = SystemConfig.getInstance().getValue(
					"yyserviceurl");

			request.setAttribute("yyserviceurl", yyserviceurl);
			request.setAttribute("pingzlxlist", pingzpzList);
			return actionMapping.findForward("qiantyy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"qiantyy");
		}
	}

	/**
	 * 印鉴审核跳转页面
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward foryinjsh(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;
			String yyserviceurl = SystemConfig.getInstance().getValue(
					"yyserviceurl");
			request.setAttribute("yyserviceurl", yyserviceurl);
			return actionMapping.findForward("yinjsh");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjsh");
		}
	}

	/**
	 * 印鉴建库（变更）跳转页面
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward foryinjgl(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 页面回显数据库次日时间
			request.setAttribute("date", this.getSystemMgrService()
					.getSystetemNowDate().substring(0, 10));
			request.setAttribute("nextdate", this.getSystemMgrService()
					.getSystetemNextDate().substring(0, 10));
			YinjForm yinjkform = (YinjForm) actionForm;
			/*
			 * String zhangh=request.getParameter("zhangh"); if(zhangh!=null){
			 * yinjkform.setZhangh(zhangh); }
			 */
			String yyserviceurl = SystemConfig.getInstance().getValue(
					"yyserviceurl");
			request.setAttribute("yyserviceurl", yyserviceurl);
			return actionMapping.findForward("yinjgl");
		} catch (Exception e) {
			// e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjgl");
		}
	}

	/**
	 * 印鉴审核跳转页面
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward foryinjshjz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;
			String yyserviceurl = SystemConfig.getInstance().getValue(
					"yyserviceurl");
			request.setAttribute("yyserviceurl", yyserviceurl);
			return actionMapping.findForward("jzyinjsh");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"jzyinjsh");
		}
	}
	

	/**
	 * 集中印鉴建库（变更）跳转页面
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward foryinjgljz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 页面回显数据库次日时间
			request.setAttribute("date", this.getSystemMgrService()
					.getSystetemNowDate().substring(0, 10));
			request.setAttribute("nextdate", this.getSystemMgrService()
					.getSystetemNextDate().substring(0, 10));
			YinjForm yinjkform = (YinjForm) actionForm;
			/*
			 * String zhangh=request.getParameter("zhangh"); if(zhangh!=null){
			 * yinjkform.setZhangh(zhangh); }
			 */
			String yyserviceurl = SystemConfig.getInstance().getValue(
					"yyserviceurl");
			request.setAttribute("yyserviceurl", yyserviceurl);
			return actionMapping.findForward("jzyinjgl");
		} catch (Exception e) {
			// e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"jzyinjgl");
		}
	}
	
	
	/**
	 * 凭证规则设置跳转页面
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forPingzgz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;
			List<Pingzpzb> pingzlxList = pingzService.getPingzList();
			request.setAttribute("pingzList", pingzlxList);
			return actionMapping.findForward("pingzgz");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"pingzgz");
		}
	}

	public ActionForward toModiPingzGz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;
			List<Pingzpzb> pingzlxList = pingzService.getPingzList();
			request.setAttribute("pingzList", pingzlxList);
			String pingzbs = URLDecoder.decode(request.getParameter("pingzbs"),
					"utf-8");
			pingzbs = pingzbs.replace('|', '#');
			if (pingzbs == null || pingzbs.trim().length() == 0) {
				return super.showMessageJSP(actionMapping, request, "pingzgz",
						"凭证标识错误");

			}
			Pingzpzb pingz = pingzService.getPingzpzbByPzbs(pingzbs);
			if (pingz == null) {
				return super.showMessageJSP(actionMapping, request, "pingzgz",
						"凭证不存在");
			}
			List<Yanygz> yanygzList = PingzpzConfig.getYangzList();
			request.setAttribute("gzlist", yanygzList);
			request.setAttribute("pingz", pingz);
			return actionMapping.findForward("modiPingzyygz");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"pingzgz");
		}
	}

	public ActionForward toDeletePingzGz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;
			List<Pingzpzb> pingzlxList = pingzService.getPingzList();
			request.setAttribute("pingzList", pingzlxList);
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String pingzbs = URLDecoder.decode(request.getParameter("pingzbs"),
					"utf-8");
			pingzbs = pingzbs.replace('|', '#');
			if (pingzbs == null || pingzbs.trim().length() == 0) {
				return super.showMessageJSP(actionMapping, request, "pingzgz",
						"凭证标识错误");

			}
			Pingzpzb pingz = pingzService.getPingzpzbByPzbs(pingzbs);
			if (pingz == null) {
				return super.showMessageJSP(actionMapping, request, "pingzgz",
						"凭证不存在");
			}
			pingzService.deletePingzByPzbs(pingzbs);

			String content = clerk.getCode() + "删除凭证[" + pingz.getPingzmc()
					+ "]";
			this.createManageLog(clerk.getCode(), content);
			pingzlxList = pingzService.getPingzList();
			request.setAttribute("pingzList", pingzlxList);
			return super.showMessageJSP(actionMapping, request, "pingzgz",
					"删除凭证[" + pingz.getPingzmc() + "]成功");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"pingzgz");
		}
	}

	public ActionForward toAddPingzxx(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;

			List<Yanygz> yanygzList = PingzpzConfig.getYangzList();
			request.setAttribute("gzlist", yanygzList);
			return actionMapping.findForward("toAddPingzxx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"toAddPingzxx");
		}
	}

	public ActionForward updatePingzyygz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String pingzbs = (String) request.getAttribute("pingzbs");
			String guizbh = (String) request.getAttribute("guizbh");
			String pingzmc = (String) request.getAttribute("pingzmc");

			pingzbs = request.getParameter("pingzbs");
			guizbh = request.getParameter("guizbh");
			pingzmc = request.getParameter("pingzmc");
			String shifxs = request.getParameter("shifxs");
			String xianssx = request.getParameter("xianssx");
			List<Yanygz> yanygzList = PingzpzConfig.getYangzList();
			request.setAttribute("gzlist", yanygzList);
			Pingzpzb pingzpzb = pingzService.getPingzpzbByPzbs(pingzbs);
			if (pingzpzb == null) {
				return super.showMessageJSP(actionMapping, request,
						"modiPingzyygz", "凭证不存在");
			}
			request.setAttribute("pingz", pingzpzb);
			Pingzpzb pingzpzb_info = pingzService.queryPingzpzbByPzmc(pingzmc,
					pingzbs);
			if (pingzpzb_info != null) {
				return super.showMessageJSP(actionMapping, request,
						"modiPingzyygz", "凭证名称[" + pingzmc + "]已被使用");
			}
			if (guizbh == null || guizbh.trim().equals("")) {
				return super.showMessageJSP(actionMapping, request,
						"modiPingzyygz", "规则编号为空");
			}
			pingzpzb.setGuizbh(guizbh);
			pingzpzb.setPingzmc(pingzmc);
			pingzpzb.setShifxs(shifxs);
			pingzpzb.setXianssx(xianssx == null ? "" : xianssx);
			pingzService.updatePingzpzb(pingzpzb);
			List<Pingzpzb> pingzlxList = pingzService.getPingzList();
			request.setAttribute("pingzList", pingzlxList);
			String content = clerk.getCode() + "修改凭证[" + pingzpzb.getPingzmc()
					+ "]";
			this.createManageLog(clerk.getCode(), content);
			return super.showMessageJSP(actionMapping, request, "pingzgz",
					"修改凭证[" + pingzpzb.getPingzmc() + "]成功!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"modiPingzyygz");
		}
	}

	public ActionForward addPingzxx(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjForm yinjkform = (YinjForm) actionForm;
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String pingzbs = (String) request.getAttribute("pzbs");
			String guizbh = (String) request.getAttribute("guizbh");
			String pingzmc = (String) request.getAttribute("pingzmc");

			pingzbs = request.getParameter("pzbs");
			guizbh = request.getParameter("guizbh");
			pingzmc = request.getParameter("pingzmc");
			String shifxs = request.getParameter("shifxs");
			String xianssx = request.getParameter("xianssx");
			Pingzpzb pz = new Pingzpzb();
			pz.setPingzbs(pingzbs);
			pz.setGuizbh(guizbh);
			pz.setPingzmc(pingzmc);
			pz.setShifxs(shifxs);
			pz.setXianssx(xianssx == null ? "" : xianssx);
			request.setAttribute("pingz", pz);
			List<Yanygz> yanygzList = PingzpzConfig.getYangzList();
			request.setAttribute("gzlist", yanygzList);
			Pingzpzb pingzpzb = pingzService.getPingzpzbByPzbs(pingzbs);
			if (pingzpzb != null) {
				return super.showMessageJSP(actionMapping, request,
						"toAddPingzxx", "凭证标示已存在");
			}
			Pingzpzb pingzpzb_info = pingzService.queryPingzpzbByPzmc(pingzmc,
					pingzbs);
			if (pingzpzb_info != null) {
				return super.showMessageJSP(actionMapping, request,
						"toAddPingzxx", "凭证名称[" + pingzmc + "]已被使用");
			}
			if (guizbh == null || guizbh.trim().equals("")) {
				return super.showMessageJSP(actionMapping, request,
						"toAddPingzxx", "规则编号为空");
			}

			pingzService.savePingz(pz);
			List<Pingzpzb> pingzlxList = pingzService.getPingzList();
			request.setAttribute("pingzList", pingzlxList);

			String content = clerk.getCode() + "新增凭证[" + pz.getPingzmc()
					+ "]成功";
			this.createManageLog(clerk.getCode(), content);
			return super.showMessageJSP(actionMapping, request, "pingzgz",
					"新增凭证[" + pz.getPingzmc() + "]成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"toAddPingzxx");
		}
	}

	// 获取控件升级相关参数
	public ActionForward getUpdateParam(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try{
			out = response.getWriter();
			String isupdatecache =  SystemConfig.getInstance().getValue("isupdatecache");
			String updatecacheip = SystemConfig.getInstance().getValue("updatecacheip");
			String updatecacheport =SystemConfig.getInstance().getValue("updatecacheport");
			String updatecachetimeout =SystemConfig.getInstance().getValue("updatecachetimeout");
			
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("GBK");
			StringBuffer updateStr = new StringBuffer();
			updateStr.append(isupdatecache).append(",").append(updatecacheip).append(",").append(updatecacheport).append(",").append(updatecachetimeout);
			
			out.print(updateStr.toString());
			out.close();
			return null;
		} catch (Exception e) {
			out.print("fail");
			e.printStackTrace();
			out.close();
			return null;
		}
	}
}
