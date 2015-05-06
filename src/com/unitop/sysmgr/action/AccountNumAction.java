package com.unitop.sysmgr.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.form.AccountnumForm;
import com.unitop.sysmgr.service.ZhanghbService;

@Controller("/accountnum")
public class AccountNumAction extends ExDispatchAction {
	
	@Resource
	ZhanghbService ZhanghbService;
	
	public ActionForward show(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			AccountnumForm aform = (AccountnumForm) actionForm;
			aform.setNetpointflag("");
			request.setAttribute("orgNum", "");
			request.setAttribute("AccountNum", null);
			return actionMapping.findForward("accountnum.show");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "accountnum.show");
		}
	}
	
	
	
	 
}
