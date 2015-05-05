package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class SealinkTypeForm extends ActionForm{

	private static final long serialVersionUID = -6088730938700352950L;

	String sealinktype;
	
	String remark;
	
	public SealinkTypeForm() {
		this.sealinktype = null;
		this.remark = null;
	}

	public void clear(){
		this.sealinktype = null;
		this.remark = null;
	}

	public SealinkTypeForm(String sealinktype, String remark) {
		super();
		this.sealinktype = sealinktype;
		this.remark = remark;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSealinktype() {
		return sealinktype;
	}

	public void setSealinktype(String sealinktype) {
		this.sealinktype = sealinktype;
	}

}
