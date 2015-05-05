package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class SystemSealinkForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2345L;
	
	private String organnum;
	private String min;
	private String max;


	
	public SystemSealinkForm() {
		this.organnum = null;
		this.min = null;
		this.max = null;

	}
	
	public void clear(){
		this.organnum = null;
		this.min = null;
		this.max = null;

	}

	public SystemSealinkForm(String code, String name, String min, String max) {
		super();
		this.organnum = code;
		this.min = min;
		this.max = max;
	}

	public String getOrgannum() {
		return organnum;
	}

	public void setOrgannum(String organnum) {
		this.organnum = organnum;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

}
