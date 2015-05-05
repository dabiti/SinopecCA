package com.unitop.sysmgr.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.SystemManageLogForm;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;
/*
 * 管理员日志查询
 */
@Controller("/managelog")
public class SystemManageLogAction extends ExDispatchAction {

	private final static String SUCCESS = "success";
	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	private OrgService OrgService;
	
	public boolean CanOperDesOrg(String OperOrg, String DesOrg) {
		return this.getSystemMgrService().CanOperDesOrg(OperOrg, DesOrg);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			SystemManageLogForm form = (SystemManageLogForm) actionform;
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			Clerk managerclerk = clerkManageService.getClerkByCode(form.getAdmincode());
			String netpointfiag = managerclerk.getOrgcode();
			if (!OrgService.validateOrg(clerk.getOrgcode(),netpointfiag))
			{
				return this.showMessageJSP(mapping,request,SUCCESS,"输入无效，管理员不存在或没有权限查看该柜员！");
			}else{
				TabsBo TabsBo = this.createTabsBo(request);
				QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
				queryServiceImpl.setTabsService(TabsBo);
				TabsBo tabsBo = this.getQueryService().findSystemManageLog(form.getAdmincode(),form.getBegindate(),form.getEnddate());
				this.showTabsModel(request, tabsBo);
				//request.setAttribute("jsql",tabsBo.getSql());
				//request.setAttribute("jlist",tabsBo.getParamterMapStr() );
				return super.showMessageJSPForFeny(mapping,request,tabsBo,SUCCESS);
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "error");
		}
	}
}