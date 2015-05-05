package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class LoginForm extends ActionForm {

	private static final long serialVersionUID = -1164577900848657742L;

	private String orgCode;

	private String code;

	private String password;
	
	private String typeofrequest;

	private String  jiaoywxx;
	
	private String  kaihxx;
	
	private String  zilxgxx;
	
	private String xiaohxx;
	
	private String clerkType;
	


	public String getTypeofrequest() {
		return typeofrequest;
	}

	public void setTypeofrequest(String typeofrequest) {
		this.typeofrequest = typeofrequest;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String string) {
		code = string;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String string) {
		password = string;
	}

	public LoginForm() {
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.orgCode = null;
		code = null;
		password = null;
	}

//	@Override
//	public ActionErrors validate(ActionMapping mapping,
//			HttpServletRequest request) {
//		ActionErrors errors = new ActionErrors();
//		if (request.getServletPath().equals("/login.do"))
//		{
//			if (code == null || code.equals("")) 
//			{
//				errors.add("error logname", new ActionMessage("errors.required", "πÒ‘±¥˙¬Î"));
//			}
//			if (password == null || password.equals(""))
//			{
//				errors.add("error passwords", new ActionMessage("errors.required", "√‹¬Î"));
//			}
//		}
//		return errors;
//	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getJiaoywxx() {
		return jiaoywxx;
	}

	public void setJiaoywxx(String jiaoywxx) {
		this.jiaoywxx = jiaoywxx;
	}

	public String getKaihxx() {
		return kaihxx;
	}

	public void setKaihxx(String kaihxx) {
		this.kaihxx = kaihxx;
	}

	public String getZilxgxx() {
		return zilxgxx;
	}

	public void setZilxgxx(String zilxgxx) {
		this.zilxgxx = zilxgxx;
	}

	public String getXiaohxx() {
		return xiaohxx;
	}

	public void setXiaohxx(String xiaohxx) {
		this.xiaohxx = xiaohxx;
	}

	public String getClerkType() {
		return clerkType;
	}

	public void setClerkType(String clerkType) {
		this.clerkType = clerkType;
	}
	
	
	
}
