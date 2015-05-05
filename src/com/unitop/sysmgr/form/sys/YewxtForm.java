package com.unitop.sysmgr.form.sys;

import org.apache.struts.action.ActionForm;

public class YewxtForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4271249539517085503L;
	
	private String xitbs;
	private String xitmc;
	private String yanzxx;
	
	public void clear(){
		this.xitbs = null;
		this.xitmc = null;
		this.yanzxx = null;
	}
	
	public YewxtForm() {
		this.xitbs = null;
		this.xitmc = null;
		this.yanzxx = null;
	}
	public YewxtForm(String xitbs, String xitmc, String yanzxx) {
		super();
		this.xitbs = xitbs;
		this.xitmc = xitmc;
		this.yanzxx = yanzxx;
	}
	public String getXitbs() {
		return xitbs;
	}
	public void setXitbs(String xitbs) {
		this.xitbs = xitbs;
	}
	public String getXitmc() {
		return xitmc;
	}
	public void setXitmc(String xitmc) {
		this.xitmc = xitmc;
	}
	public String getYanzxx() {
		return yanzxx;
	}
	public void setYanzxx(String yanzxx) {
		this.yanzxx = yanzxx;
	}
}
