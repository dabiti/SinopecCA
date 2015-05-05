package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class DanbwhForm extends ActionForm {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -90398685006583L;
	
	private String gongnid;
	private String gongnmc;
	private String weihbm;
	private String gongnurl;
	private String shifbc;
	private String shifbj;
	private String shifsc;
	private String shifzb;
	private String zhidyl;
	public String getShifzb() {
		return shifzb;
	}
	public void setShifzb(String shifzb) {
		this.shifzb = shifzb;
	}
	public String getZhidyl() {
		return zhidyl;
	}
	public void setZhidyl(String zhidyl) {
		this.zhidyl = zhidyl;
	}
	public DanbwhForm() {
		this.gongnid = null;
		this.gongnurl = null;
		this.gongnmc = null;
		this.weihbm = null;
		this.shifbc = null;
		this.shifbc = null;
		this.shifsc = null;
	}
	public void clear(){
		this.gongnid = null;
		this.gongnurl = null;
		this.gongnmc = null;
		this.weihbm = null;
		this.shifbc = null;
		this.shifbc = null;
		this.shifsc = null;
	}
	
	
	public DanbwhForm(String gongnid, String gongnmc, String weihbm,
			String gongnurl, String shifbc, String shifbj, String shifsc) {
		super();
		this.gongnid = gongnid;
		this.gongnmc = gongnmc;
		this.weihbm = weihbm;
		this.gongnurl = gongnurl;
		this.shifbc = shifbc;
		this.shifbj = shifbj;
		this.shifsc = shifsc;
	}
	public String getGongnid() {
		return gongnid;
	}
	public void setGongnid(String gongnid) {
		this.gongnid = gongnid;
	}
	public String getGongnmc() {
		return gongnmc;
	}
	public void setGongnmc(String gongnmc) {
		this.gongnmc = gongnmc;
	}
	public String getWeihbm() {
		return weihbm;
	}
	public void setWeihbm(String weihbm) {
		this.weihbm = weihbm;
	}
	public String getGongnurl() {
		return gongnurl;
	}
	public void setGongnurl(String gongnurl) {
		this.gongnurl = gongnurl;
	}
	public String getShifbc() {
		return shifbc;
	}
	public void setShifbc(String shifbc) {
		this.shifbc = shifbc;
	}
	public String getShifbj() {
		return shifbj;
	}
	public void setShifbj(String shifbj) {
		this.shifbj = shifbj;
	}
	public String getShifsc() {
		return shifsc;
	}
	public void setShifsc(String shifsc) {
		this.shifsc = shifsc;
	}
	
}
