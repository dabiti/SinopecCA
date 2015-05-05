package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class PasswordForm extends ActionForm {

	private static final long serialVersionUID = 7170323222065427799L;

	private String password;

	private String newpassword;

	private String newpassword1;
	
	private String flag ;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String string) {
		password = string;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String string) {
		newpassword = string;
	}

	public String getNewpassword1() {
		return newpassword1;
	}

	public void setNewpassword1(String string) {
		newpassword1 = string;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		this.password = null;
		this.newpassword = null;
		this.newpassword1 = null;
		this.flag = null;
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/changepwd.do")) {
			if (password == null || password.trim().length() == 0) {
				errors.add("error password", new ActionMessage("errors.required", "原密码"));
			}
			if (newpassword == null || newpassword.trim().length() == 0) {
				errors.add("error password", new ActionMessage("errors.required", "新密码"));
			} else if (!newpassword.equals(newpassword1)) {
				errors.add("error password length", new ActionMessage("errors.detail", "两次输入的密码不一致"));
			}
			if (password.equals(newpassword)) {
				errors.add("error password length", new ActionMessage("errors.detail", "新旧密码一致，不能修改"));
			}
		}
		return errors;
	}

	private boolean isNumber(String password) {
		try {
			Integer.valueOf(password);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public PasswordForm() {
	}

}
