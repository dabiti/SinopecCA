package com.unitop.sysmgr.form.sys;

import org.apache.struts.action.ActionForm;

public class PingzcsForm extends ActionForm {
	
	private String xitbs;
	private String pingzbs;
	private String pingzmc;
	private String zhaozq;
	private String fazq;
	private Integer yanyjb;
	private Integer xfenbl;
	private Integer yfenbl;
	private Integer xsuof ;
	private Integer ysuof;
	
	
	public void clear(){

		this.xitbs = null;
		this.pingzbs = null;
		this.pingzmc = null;
		this.zhaozq = null;
		this.fazq = null;
		this.yanyjb = null;
		this.xfenbl = null;
		this.yfenbl = null;
		this.xsuof = null;
		this.ysuof = null;
	}
	public PingzcsForm(){
		this.xitbs = null;
		this.pingzbs = null;
		this.pingzmc = null;
		this.zhaozq = null;
		this.fazq = null;
		this.yanyjb = null;
		this.xfenbl = null;
		this.yfenbl = null;
		this.xsuof = null;
		this.ysuof = null;
	}

	
	public PingzcsForm(String xitbs, String pingzbs, String pingzmc,
			String zhaozq, String fazq, Integer yanyjb, Integer xfenbl,
			Integer yfenbl, Integer xsuof, Integer ysuof) {
		super();
		this.xitbs = xitbs;
		this.pingzbs = pingzbs;
		this.pingzmc = pingzmc;
		this.zhaozq = zhaozq;
		this.fazq = fazq;
		this.yanyjb = yanyjb;
		this.xfenbl = xfenbl;
		this.yfenbl = yfenbl;
		this.xsuof = xsuof;
		this.ysuof = ysuof;
	}



	public String getXitbs() {
		return xitbs;
	}


	public void setXitbs(String xitbs) {
		this.xitbs = xitbs;
	}


	public String getPingzbs() {
		return pingzbs;
	}


	public void setPingzbs(String pingzbs) {
		this.pingzbs = pingzbs;
	}


	public String getPingzmc() {
		return pingzmc;
	}


	public void setPingzmc(String pingzmc) {
		this.pingzmc = pingzmc;
	}


	public String getZhaozq() {
		return zhaozq;
	}


	public void setZhaozq(String zhaozq) {
		this.zhaozq = zhaozq;
	}


	public String getFazq() {
		return fazq;
	}


	public void setFazq(String fazq) {
		this.fazq = fazq;
	}


	public Integer getYanyjb() {
		return yanyjb;
	}


	public void setYanyjb(Integer yanyjb) {
		this.yanyjb = yanyjb;
	}


	public Integer getXfenbl() {
		return xfenbl;
	}


	public void setXfenbl(Integer xfenbl) {
		this.xfenbl = xfenbl;
	}


	public Integer getYfenbl() {
		return yfenbl;
	}


	public void setYfenbl(Integer yfenbl) {
		this.yfenbl = yfenbl;
	}


	public Integer getXsuof() {
		return xsuof;
	}


	public void setXsuof(Integer xsuof) {
		this.xsuof = xsuof;
	}


	public Integer getYsuof() {
		return ysuof;
	}


	public void setYsuof(Integer ysuof) {
		this.ysuof = ysuof;
	}


}
