package com.unitop.sysmgr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.Rights;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.action.ExDispatchAction;

@Controller("/logout")
public class LogoutAction extends ExDispatchAction {
	@Resource
	private ClerkManageService clerkManageService;
	
	//��Աע��
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			if(clerk!=null)
			{
				//ע��
				Clerk clerkBo = clerkManageService.getClerkByCode(clerk.getCode());
				clerkBo.setIp(null);
				this.createManageLog(clerkBo.getCode(), "ע��");
				clerkManageService.updateClerk(clerkBo);
				request.getSession().removeAttribute("clerk");
				
				//�������߹�Ա����
				int i= Rights.getInstance().getNowonline();
				Rights.getInstance().setNowonline(i-1);
			}
			request.setAttribute("logoutok", "logoutok");
			return mapping.findForward("logout.ok");
		}catch(Exception e){
			e.printStackTrace();
			return this.errrForLogAndException(e,mapping,request,"logout.ok");
		}
	}
}