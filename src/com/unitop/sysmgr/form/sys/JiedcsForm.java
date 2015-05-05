package com.unitop.sysmgr.form.sys;

import org.apache.struts.action.ActionForm;

public class JiedcsForm extends ActionForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7288834356730172841L;
	
	private String Jiedbs;
	private String cansbs;
	private String cansmc;
	private String canslx;
	private String cansfl;
	private String cansz;
	private String canssm;
	
	
	public void clear(){
		this.Jiedbs = null;
		this.cansbs = null;
		this.cansmc = null;
		this.canslx = null;
		this.cansfl = null;
		this.cansz = null;
		this.canssm = null;
	}
	
	public JiedcsForm() {
		this.Jiedbs = null;
		this.cansbs = null;
		this.cansmc = null;
		this.canslx = null;
		this.cansfl = null;
		this.cansz = null;
		this.canssm = null;
	}
	public JiedcsForm(String jiedbs, String cansbs, String cansmc,
			String canslx, String cansfl, String cansz, String canssm) {
		super();
		this.Jiedbs = jiedbs;
		this.cansbs = cansbs;
		this.cansmc = cansmc;
		this.canslx = canslx;
		this.cansfl = cansfl;
		this.cansz = cansz;
		this.canssm = canssm;
	}
	public String getCansmc() {
		return cansmc;
	}
	public void setCansmc(String cansmc) {
		this.cansmc = cansmc;
	}
	public String getCansfl() {
		return cansfl;
	}
	public void setCansfl(String cansfl) {
		this.cansfl = cansfl;
	}
	
	public String getJiedbs() {
		return Jiedbs;
	}
	public void setJiedbs(String jiedbs) {
		Jiedbs = jiedbs;
	}
	public String getCansbs() {
		return cansbs;
	}
	public void setCansbs(String cansbs) {
		this.cansbs = cansbs;
	}
	public String getCanslx() {
		return canslx;
	}
	public void setCanslx(String canslx) {
		this.canslx = canslx;
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
