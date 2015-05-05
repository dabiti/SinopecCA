package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class AccountcharacterForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4637964851086654605L;
	String accountcharacter;
	String remark;
	
	public AccountcharacterForm() {
		this.accountcharacter = null;
		this.remark = null;
	}
	
	public void clear(){
		this.accountcharacter = null;
		this.remark = null;
	}
	
	public AccountcharacterForm(String accountcharacter, String remark) {
		super();
		this.accountcharacter = accountcharacter;
		this.remark = remark;
	}

	public void setAccountcharacter(String accountcharacter) {
		this.accountcharacter = accountcharacter;
	}
	public String getAccountcharacter() {
		return this.accountcharacter;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return this.remark;
	}

}
