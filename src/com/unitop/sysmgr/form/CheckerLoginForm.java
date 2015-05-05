package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class CheckerLoginForm extends ActionForm {

	private static final long serialVersionUID = -1164577900848657742L;

	private String orgCode;

	private String code;

	private String password;

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (request.getServletPath().equals("/checkerlogin.do")) {
			if (code == null || code.equals("")) {
				errors.add("error logname", new ActionMessage(
						"errors.required", "柜员代码"));
			} else if (code.length() != 4) {
				errors.add("error code", new ActionMessage("errors.detail",
						"柜员代码长度错误"));
			}
			if (password == null || password.equals("")) {
				errors.add("error passwords", new ActionMessage(
						"errors.required", "密码"));
			}
		}
		return errors;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
