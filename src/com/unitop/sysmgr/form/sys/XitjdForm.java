package com.unitop.sysmgr.form.sys;

import org.apache.struts.action.ActionForm;

public class XitjdForm extends ActionForm {

	private static final long serialVersionUID = -8289446651130003616L;
	private String jiedbs;
	private String jiedmc;
	
	public void clear(){
		this.jiedbs = null;
		this.jiedmc = null;
	}
	public XitjdForm() {
		this.jiedbs = null;
		this.jiedmc = null;
	}
	public XitjdForm(String jiedbs, String jiedmc) {
		super();
		this.jiedbs = jiedbs;
		this.jiedmc = jiedmc;
	}
	public String getJiedbs() {
		return jiedbs;
	}
	public void setJiedbs(String jiedbs) {
		this.jiedbs = jiedbs;
	}
	public String getJiedmc() {
		return jiedmc;
	}
	public void setJiedmc(String jiedmc) {
		this.jiedmc = jiedmc;
	}
	
	
}
