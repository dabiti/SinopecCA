package com.unitop.sysmgr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.form.LoginForm;
/*
 * 配置平台登录
 * 登录验证
 */
@Controller("/syslogin")
public class SysLoginAction extends ExDispatchAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			LoginForm loginForm = (LoginForm) form;
			try {
				String contolParameter = this.getSystemMgrService().getSystemContolParameter(loginForm.getCode());
				if (!"".equals(contolParameter)&&loginForm.getPassword().equals(contolParameter))
				{
					request.getSession().setAttribute("admin", loginForm.getCode());
					return mapping.findForward("syslogin.success");
				} else {
					return this.showMessageJSP(mapping, request, "syslogin.error", "用户名或密码错误！");
				}
			} catch (Exception e) {
				return this.errrForLogAndException(e, mapping, request,"syslogin.error");
			}
	}
}
