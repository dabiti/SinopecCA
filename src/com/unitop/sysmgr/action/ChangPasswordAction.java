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
 * ��Ա�����޸ķ���
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
				return this.showMessageJSP(mapping,request,"changepwd.error","��������벻��ȷ!");
			}
			//��Ա��һ�ε�¼�Ѿ�ǿ���޸Ĺ�����
			clerk.setClerkstatus("1");
			clerkManageService.changePassword(clerk,passwordform.getNewpassword());
			
			clerk.setPassword(passwordform.getNewpassword());
			
			if ("�������".equals(clerk.clerkMotion)||"ǿ���޸�����".equals(clerk.clerkMotion))
			{
				//��Աǿ���޸�����
				request.getSession().removeValue("clerk");
				return super.showMessageJSP(mapping, request, "changepwd.sucess", "�޸�����ɹ�!");
			}else{
				//��Ա�������� �����޸�����
				return super.showMessageJSP(mapping, request, "changepwd.error", "�޸�����ɹ�!");
			}
		} catch (Exception e) {
			logString.append("(" + getClass() + ") ���룺������ " + passwordform.getPassword()).append(" ������ " + passwordform.getNewpassword());
			return this.errrForLogAndException(e, mapping, request,"changepwd.error");
		}finally{
			passwordform.setNewpassword(null);
			passwordform.setNewpassword1(null);
			passwordform.setPassword(null);
		}
	}
}