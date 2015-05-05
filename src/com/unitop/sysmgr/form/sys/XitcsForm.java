package com.unitop.sysmgr.form.sys;

import org.apache.struts.action.ActionForm;

public class XitcsForm extends ActionForm {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -903951358685006583L;
	
	private String xitbs;
	private String cansbs;
	private String cansmc;
	private String canslx;
	private String cansfl;
	private String cansz;
	private String canssm;
	
	
	public XitcsForm() {
		this.xitbs = null;
		this.cansbs = null;
		this.cansmc = null;
		this.canslx = null;
		this.cansfl = null;
		this.cansz = null;
		this.canssm = null;
	}
	public void clear(){
		this.xitbs = null;
		this.cansbs = null;
		this.cansmc = null;
		this.canslx = null;
		this.cansfl = null;
		this.cansz = null;
		this.canssm = null;
	}

	public XitcsForm(String xitbs, String cansbs, String cansmc, String canslx,
			String cansfl, String cansz, String canssm) {
		super();
		this.xitbs = xitbs;
		this.cansbs = cansbs;
		this.cansmc = cansmc;
		this.canslx = canslx;
		this.cansfl = cansfl;
		this.cansz = cansz;
		this.canssm = canssm;
	}
	
	
	public String getXitbs() {
		return xitbs;
	}
	public void setXitbs(String xitbs) {
		this.xitbs = xitbs;
	}
	public String getCansbs() {
		return cansbs;
	}
	public void setCansbs(String cansbs) {
		this.cansbs = cansbs;
	}
	public String getCansmc() {
		return cansmc;
	}
	public void setCansmc(String cansmc) {
		this.cansmc = cansmc;
	}
	public String getCanslx() {
		return canslx;
	}
	public void setCanslx(String canslx) {
		this.canslx = canslx;
	}
	public String getCansfl() {
		return cansfl;
	}
	public void setCansfl(String cansfl) {
		this.cansfl = cansfl;
	}
	public String getCansz() {
		return cansz;
	}
	public void setCansz(String cansz) {
		this.cansz = cansz;
	}
	public String getCanssm() {
		return canssm;
	}
	public void setCanssm(String canssm) {
		this.canssm = canssm;
	}
}
