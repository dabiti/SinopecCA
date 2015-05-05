package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class RoleForm extends ActionForm {

	private String juesid = "";
	private String juesmc = "";
	private String juesms = "";
	private String beiz = "";
	private String juesjb = "";

	public String getJuesjb() {
		return juesjb;
	}

	public void setJuesjb(String juesjb) {
		this.juesjb = juesjb;
	}

	public String getJuesid() {
		return juesid;
	}

	public void setJuesid(String juesid) {
		this.juesid = juesid;
	}

	public String getJuesmc() {
		return juesmc;
	}

	public void setJuesmc(String juesmc) {
		this.juesmc = juesmc;
	}

	public String getJuesms() {
		return juesms;
	}

	public void setJuesms(String juesms) {
		this.juesms = juesms;
	}

	public String getBeiz() {
		return beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}

}
