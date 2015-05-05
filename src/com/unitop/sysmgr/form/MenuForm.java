package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MenuForm extends ActionForm{
	
	private String gongnid;
	
	private String zignid;

	private String gongnmc;
	
	private String gongnurl;
	
	private int gongnsx;

	private String shangjgn;
	
	private String quanxwz;
	
	private String gongnlx;
	
	private String zhuangt;
	
	private String beiz;

	public MenuForm() {

	}

	public String getGongnlx() {
		return gongnlx;
	}

	public void setGongnlx(String gongnlx) {
		this.gongnlx = gongnlx;
	}

	public String getZignid() {
		return zignid;
	}

	public void setZignid(String zignid) {
		this.zignid = zignid;
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

	public String getGongnurl() {
		return gongnurl;
	}

	public void setGongnurl(String gongnurl) {
		this.gongnurl = gongnurl;
	}

	public int getGongnsx() {
		return gongnsx;
	}

	public void setGongnsx(int gongnsx) {
		this.gongnsx = gongnsx;
	}

	public String getShangjgn() {
		return shangjgn;
	}

	public void setShangjgn(String shangjgn) {
		this.shangjgn = shangjgn;
	}

	public String getQuanxwz() {
		return quanxwz;
	}

	public void setQuanxwz(String quanxwz) {
		this.quanxwz = quanxwz;
	}



	public String getZhuangt() {
		return zhuangt;
	}

	public void setZhuangt(String zhuangt) {
		this.zhuangt = zhuangt;
	}

	public String getBeiz() {
		return beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}
	
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		this.gongnid = null;
		this.zignid = null;
		this.gongnmc = null;
		this.gongnurl = null;
		this.gongnsx = 0;
		this.shangjgn = null;
		this.quanxwz = null;
		this.zhuangt = null;
		this.beiz = null;
	}
	
	public void clear(){
		this.gongnid = "";
		this.zignid = "";
		this.gongnmc = "";
		this.gongnurl = "";
		this.gongnsx = 0;
		this.shangjgn = "";
		this.quanxwz = "";
		this.zhuangt = "";
		this.beiz = "";
	}
	
}
