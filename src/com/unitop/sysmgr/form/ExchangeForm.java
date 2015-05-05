package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class ExchangeForm extends ActionForm {
	
	private String id;
	
	private String orgCode1;
	
	private String orgCode2;
	
	private String funcOption;

	private String quyid;
	
	private String quyname;
		
	
	public String getFuncOption() {
		return funcOption;
	}

	public void setFuncOption(String funcOption) {
		this.funcOption = funcOption;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgCode1() {
		return orgCode1;
	}

	public void setOrgCode1(String orgCode1) {
		this.orgCode1 = orgCode1;
	}

	public String getOrgCode2() {
		return orgCode2;
	}

	public void setOrgCode2(String orgCode2) {
		this.orgCode2 = orgCode2;
	}

	public String getQuyid() {
		return quyid;
	}

	public void setQuyid(String quyid) {
		this.quyid = quyid;
	}

	public String getQuyname() {
		return quyname;
	}

	public void setQuyname(String quyname) {
		this.quyname = quyname;
	}
}
