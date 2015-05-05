package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class YinjForm extends ActionForm {

	private String zhangh;
	private String pingzh;
	private String chuprq;
	private String jine;
	private String yanylx;
	private String yanyms;
 
    
	
	public YinjForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YinjForm(String zhangh,String pingzh,String chuprq,String jine,String yanylx,String yanyms) {
		super();
		this.zhangh = zhangh;
		this.pingzh = pingzh;
		this.chuprq = chuprq;
		this.jine = jine;
		this.yanylx = yanylx;
		this.yanyms = yanyms;

	}


	public void clear() {
		this.zhangh = "";
		this.pingzh = "";
		this.chuprq = "";
		this.jine = "";
		this.yanylx ="";
		this.yanyms = "";

	}

	public String getZhangh() {
		return zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	public String getPingzh() {
		return pingzh;
	}

	public void setPingzh(String pingzh) {
		this.pingzh = pingzh;
	}

	public String getChuprq() {
		return chuprq;
	}

	public void setChuprq(String chuprq) {
		this.chuprq = chuprq;
	}

	public String getJine() {
		return jine;
	}

	public void setJine(String jine) {
		this.jine = jine;
	}

	public String getYanylx() {
		return yanylx;
	}

	public void setYanylx(String yanylx) {
		this.yanylx = yanylx;
	}

	public String getYanyms() {
		return yanyms;
	}

	public void setYanyms(String yanyms) {
		this.yanyms = yanyms;
	}



	

	

}
