package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class CurrencyMaintenanceForm extends ActionForm{
	
	String currencytype;
	String remark;
	

	public CurrencyMaintenanceForm() {
		this.currencytype = null;
		this.remark = null;
	}

	public void clear(){
		this.currencytype = null;
		this.remark = null;
	}

	public CurrencyMaintenanceForm(String currencytype, String remark) {
		super();
		this.currencytype = currencytype;
		this.remark = remark;
	}

	public String getCurrencytype() {
		return currencytype;
	}

	public void setCurrencytype(String currencytype) {
		this.currencytype = currencytype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
