package com.unitop.sysmgr.form.sys;

import org.apache.struts.action.ActionForm;

public class PeizDPIForm extends ActionForm {
	
	private static final long serialVersionUID = -7052208778156774062L;
	private String dpiid;
	private String diqh;
	private String wangdh;
	private String yewlx;
	private String secbz;
	private String dpi;
	private String tiaoycs;
	
	
	
	public PeizDPIForm() {
		this.dpiid = null;
		this.diqh = null;
		this.wangdh = null;
		this.yewlx = null;
		this.secbz = null;
		this.dpi = null;
		this.tiaoycs = null;
	}
	public void clear(){
		this.dpiid = null;
		this.diqh = null;
		this.wangdh = null;
		this.yewlx = null;
		this.secbz = null;
		this.dpi = null;
		this.tiaoycs = null;
	}

	public PeizDPIForm(String dpiid, String diqh, String wangdh, String yewlx,
			String secbz, String dpi, String tiaoycs) {
		super();
		this.dpiid = dpiid;
		this.diqh = diqh;
		this.wangdh = wangdh;
		this.yewlx = yewlx;
		this.secbz = secbz;
		this.dpi = dpi;
		this.tiaoycs = tiaoycs;
	}
	
	
	public String getDpiid() {
		return dpiid;
	}
	public void setDpiid(String dpiid) {
		this.dpiid = dpiid;
	}
	public String getDiqh() {
		return diqh;
	}
	public void setDiqh(String diqh) {
		this.diqh = diqh;
	}
	public String getWangdh() {
		return wangdh;
	}
	public void setWangdh(String wangdh) {
		this.wangdh = wangdh;
	}
	public String getYewlx() {
		return yewlx;
	}
	public void setYewlx(String yewlx) {
		this.yewlx = yewlx;
	}

	public String getSecbz() {
		return secbz;
	}
	public void setSecbz(String secbz) {
		this.secbz = secbz;
	}
	public String getDpi() {
		return dpi;
	}
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	public String getTiaoycs() {
		return tiaoycs;
	}
	public void setTiaoycs(String tiaoycs) {
		this.tiaoycs = tiaoycs;
	}
	
	
}
