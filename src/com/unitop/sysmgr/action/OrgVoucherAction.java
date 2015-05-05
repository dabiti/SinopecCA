package com.unitop.sysmgr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.LogTglForm;
import com.unitop.sysmgr.service.YanyinLogService;
import com.unitop.sysmgr.service.impl.YanyinLogServiceImpl;
import com.unitop.sysmgr.action.ExDispatchAction;

//机构整票日志
@Controller("/orgVoucher")
public class OrgVoucherAction extends ExDispatchAction {
	
	@Resource
	private YanyinLogService YanyinLogService;
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("totalRows", new Integer(0));
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "list");
		}
		return mapping.findForward("list");
	}

	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Clerk clerk =(Clerk) request.getSession().getAttribute("clerk");
			LogTglForm logTglForm = (LogTglForm) form;
			request.setAttribute("totalRows", new Integer(0));
			boolean bool =this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),logTglForm.getOrgCode());
			if(!bool)
			{
				this.processBusinessException(mapping, request,"没有权限查询机构["+logTglForm.getOrgCode()+"],或机构不存在！");
				return mapping.getInputForward();
			}
			
			//分页查询
			TabsBo TabsBo = this.createTabsBo(request);
			YanyinLogServiceImpl yanyinLogServiceImpl = (YanyinLogServiceImpl) YanyinLogService;
			yanyinLogServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo = YanyinLogService.getOrgZhengprzList(logTglForm.getOrgCode(), logTglForm.getDate());
			this.showTabsModel(request, tabsBo);
			
			return super.showMessageJSPForFeny(mapping,request,tabsBo,"success");
		} catch (Exception e){
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request, "list");
		}
	}
}
