package com.unitop.sysmgr.action;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.form.PasswordForm;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.action.ExDispatchAction;

/*
 * 柜员密码修改服务
 * 
 */
@Controller("/changepwd")
public class ChangPasswordAction extends ExDispatchAction {
	@Resource
	private ClerkManageService clerkManageService;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String flag = request.getParameter("flag");
		PasswordForm passwordform = (PasswordForm) form;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			if (!clerk.getPassword().equals(passwordform.getPassword()))
			{
				return this.showMessageJSP(mapping,request,"changepwd.error","输入旧密码不正确!");
			}
			//柜员第一次登录已经强制修改过密码
			clerk.setClerkstatus("1");
			clerkManageService.changePassword(clerk,passwordform.getNewpassword());
			
			clerk.setPassword(passwordform.getNewpassword());
			
			if ("密码过期".equals(clerk.clerkMotion)||"强制修改密码".equals(clerk.clerkMotion))
			{
				//柜员强制修改密码
				request.getSession().removeValue("clerk");
				return super.showMessageJSP(mapping, request, "changepwd.sucess", "修改密码成功!");
			}else{
				//柜员正常启用 正常修改密码
				return super.showMessageJSP(mapping, request, "changepwd.error", "修改密码成功!");
			}
		} catch (Exception e) {
			logString.append("(" + getClass() + ") 输入：旧密码 " + passwordform.getPassword()).append(" 新密码 " + passwordform.getNewpassword());
			return this.errrForLogAndException(e, mapping, request,"changepwd.error");
		}finally{
			passwordform.setNewpassword(null);
			passwordform.setNewpassword1(null);
			passwordform.setPassword(null);
		}
	}
}