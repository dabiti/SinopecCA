package com.unitop.sysmgr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.framework.util.JsonSystemTool;
import com.unitop.framework.util.PasswordUtil;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.SealchecklogForm;
import com.unitop.sysmgr.service.YanyinLogService;
import com.unitop.sysmgr.service.impl.YanyinLogServiceImpl;

@Controller("/sealchecklog")
public class SealchecklogAction extends ExDispatchAction {

	@Resource
	private YanyinLogService YanyinLogService;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			SealchecklogForm logform = (SealchecklogForm) form;
		try {
			//柜员信息格式转化为json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
			String account = logform.getAccount().trim();
			CanOperAccReturn coar = this.getSystemMgrService().ProCanOperAcc(clerk.getOrgcode(),account);
			if(!"".equals(logform.getChecknum())&&logform.getChecknum()!=null)
			{
				if (YanyinLogService.validatePjhForZhengp(logform.getChecknum()))
				{
					return this.showMessageJSP(mapping, request, "success", "输入无效,凭证号不存在！");
				}
			}
			if (!coar.getReturnValue()&&account!=null) {
				return this.showMessageJSP(mapping,request,"success",coar.getReturnMessage()+" ！");
			}
			
			TabsBo TabsBo = this.createTabsBo(request);
			YanyinLogServiceImpl yanyinLogServiceImpl = (YanyinLogServiceImpl) YanyinLogService;
			yanyinLogServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo = YanyinLogService.getTodayDanzrzList(account.trim(), logform.getChecknum());
			this.showTabsModel(request, tabsBo);
			
			return super.showMessageJSPForFeny(mapping,request,tabsBo,"success");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e,mapping,request,"success");
		}
	}
}


