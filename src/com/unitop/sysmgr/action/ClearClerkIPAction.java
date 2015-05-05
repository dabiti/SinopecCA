package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.impl.ClerkManageServiceImpl;
@Controller("/clearClerkIP")
public class ClearClerkIPAction extends ExDispatchAction {

	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	private OrgService OrgService;
	
	public ActionForward get(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String clerkCode = request.getParameter("clerkCode");
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out =null;
		try {
			out = response.getWriter();
			
			Clerk clerk_=clerkManageService.getClerkByCode(clerkCode);
			if(clerk_.getName()==null||clerk_.getName().trim().equals("")){
				out.println(" , , ,柜员不存在");
				out.close();
				return null;
			}
			boolean bool = clerkManageService.CanOperDesClerkCode(clerk, clerkCode);
			if (bool)
			{
				String[] string = clerkManageService.getClerkIP(clerkCode);
				if (string[2] == null)
					string[2] = "未锁定";
				String clerkinfo = string[0] + " ," + string[1] + " ," + string[2] + ", ";
				out.println(clerkinfo);
			}else{
				out.println(" , , ,没有权限查看该柜员");
			}
			out.close();
			return null;
		} catch (Exception e) {
			out.println(" , , ,柜员不存在或者没有权限查看");
			return this.errrForLogAndException(e, actionMapping, request, "");
		}
	}

	public ActionForward clear(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String clerkCode = request.getParameter("clerkCode");
		try {
			boolean bool = clerkManageService.CanOperDesClerkCode(clerk, clerkCode);
			if (!bool)
			{
				return this.showMessageJSP(actionMapping,request,"login","无权限清除柜员：" + clerkCode);
			}
			String[] string = clerkManageService.getClerkIP(clerkCode);
			if (string[2] == null)
			{
				return this.showMessageJSP(actionMapping,request,"login","柜员：[ " + clerkCode + " ] 未锁定，不需要清除");
			}
			clerkManageService.ClearClerkIP(clerkCode);
			this.createManageLog(clerk.getCode(), "清除柜员IP限制[清除柜员IP|柜员号:"+clerkCode+"]");
			return this.showMessageJSP(actionMapping,request,"login","清除成功!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "login");
		}
	}

	public ActionForward login(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping.findForward("login");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"null");
		}
	}

	public ActionForward clerkOrgclerkIP(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setAttribute("list", new ArrayList());
			return actionMapping.findForward("clerkOrgclerkIP");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"clerkOrgclerkIP");
		}
	}

	public ActionForward getOrgclerkIP(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String orgCode = request.getParameter("netpointflag");
		try {
			Org org = OrgService.getOrgByCode(orgCode);
			if(org==null){
				request.setAttribute("orgCode", orgCode);
				request.setAttribute("list", new ArrayList());
				return this.showMessageJSP(actionMapping,request,"clerkOrgclerkIP","机构[" + orgCode+"]不存在!");
			}
			boolean bool = this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(), orgCode);
			if (!bool)
			{
				request.setAttribute("orgCode", orgCode);
				request.setAttribute("list", new ArrayList());
				return this.showMessageJSP(actionMapping,request,"clerkOrgclerkIP","无权限清除机构：" + orgCode);
			} else {
				List list = clerkManageService.getOrgclerkIP(orgCode);
				request.setAttribute("list", list);
			}
			request.setAttribute("orgCode", orgCode);
			return actionMapping.findForward("clerkOrgclerkIP");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("list", new ArrayList());
			return this.errrForLogAndException(e, actionMapping, request, "clerkOrgclerkIP");
		}
	}

	public ActionForward clearOrgclerkIP(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String clerkCode = request.getParameter("clerkCode");
		try {
			boolean bool = clerkManageService.CanOperDesClerkCode(clerk, clerkCode);
			if (!bool)
			{
				request.setAttribute("orgCode", request.getParameter("netpointflag"));
				request.setAttribute("list", new ArrayList());
				return this.showMessageJSP(actionMapping,request,"clerkOrgclerkIP","无权限清除柜员：" + clerkCode);
			}
			request.setAttribute("orgCode", request.getParameter("netpointflag"));
			clerkManageService.ClearClerkIP(clerkCode);
			request.setAttribute("list", new ArrayList());
			this.createManageLog(clerk.getCode(), "清除柜员IP限制[清除机构下柜员IP限制|柜员号："+clerkCode+"]");
			return this.showMessageJSP(actionMapping,request,"clerkOrgclerkIP","清除成功！");
		} catch (Exception e) {
			request.setAttribute("list", new ArrayList());
			return this.errrForLogAndException(e, actionMapping, request,"clerkOrgclerkIP");
		}
	}

	public ActionForward clearAllOrgclerkIP(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String orgCode = request.getParameter("clerkCode");
		try {
			boolean bool = this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(), orgCode);
			if (!bool)
			{
				request.setAttribute("list", new ArrayList());
				return this.showMessageJSP(actionMapping,request,"clerkOrgclerkIP","无权限清除机构：" + orgCode);
			}
			clerkManageService.clearAllOrgclerkIP(orgCode);
			request.setAttribute("list", new ArrayList());
			this.createManageLog(clerk.getCode(), "清除柜员IP限制[清除机构下柜员IP限制|机构号"+orgCode+"]");
			return this.showMessageJSP(actionMapping,request,"clerkOrgclerkIP","机构" + "[ " + orgCode + " ]下柜员IP清除成功！");
		} catch (Exception e) {
			request.setAttribute("list", new ArrayList());
			return this.errrForLogAndException(e, actionMapping, request,"clerkOrgclerkIP");
		}
	}
	
	public void setClerkManageService(ClerkManageServiceImpl clerkManageService) {
		this.clerkManageService = clerkManageService;
	}
}