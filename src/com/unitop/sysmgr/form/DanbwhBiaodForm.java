package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class DanbwhBiaodForm extends ActionForm {
	
	public String gongnid;
	
	public String zidmc;
	
	public String zhansmc;
	
	public String zidlx;
	
	public String shurlx;
	
	public String quzfw;
	
	public String morz;
	
	public String jiaoygz;
	
	public String shifbc;
	
	public String shifbj;
	
	public String shifsc;
	
	public String shifzj;
	
	public String xianssx;
	

	public String getXianssx() {
		return xianssx;
	}
	public void setXianssx(String xianssx) {
		this.xianssx = xianssx;
	}
	public DanbwhBiaodForm() {
		this.gongnid = null;
		this.zidmc = null;
		this.zhansmc = null;
		this.zidlx = null;
		this.shurlx = null;
		this.quzfw = null;
		this.morz = null;
		this.jiaoygz = null;
		this.shifbc = null;
		this.shifbj = null;
		this.shifsc = null;
		this.shifzj = null;
		this.xianssx =null;
	}
	public void clear(){
		this.gongnid = null;
		this.zidmc = null;
		this.zhansmc = null;
		this.zidlx = null;
		this.shurlx = null;
		this.quzfw = null;
		this.morz = null;
		this.jiaoygz = null;
		this.shifbc = null;
		this.shifbj = null;
		this.shifsc = null;
		this.shifzj = null;
		this.xianssx =null;
	}

	public DanbwhBiaodForm(String gongnid, String zidmc, String zhansmc,
			String zidlx, String shurlx, String quzfw, String morz,
			String jiaoygz, String shifbc, String shifbj, String shifsc,
			String shifzj) {
		super();
		this.gongnid = gongnid;
		this.zidmc = zidmc;
		this.zhansmc = zhansmc;
		this.zidlx = zidlx;
		this.shurlx = shurlx;
		this.quzfw = quzfw;
		this.morz = morz;
		this.jiaoygz = jiaoygz;
		this.shifbc = shifbc;
		this.shifbj = shifbj;
		this.shifsc = shifsc;
		this.shifzj = shifzj;
		this.xianssx =null;
	}
	public String getGongnid() {
		return gongnid;
	}

	public void setGongnid(String gongnid) {
		this.gongnid = gongnid;
	}

	public String getZidmc() {
		return zidmc;
	}

	public void setZidmc(String zidmc) {
		this.zidmc = zidmc;
	}

	public String getZidlx() {
		return zidlx;
	}

	public void setZidlx(String zidlx) {
		this.zidlx = zidlx;
	}

	public String getShurlx() {
		return shurlx;
	}

	public void setShurlx(String shurlx) {
		this.shurlx = shurlx;
	}

	public String getQuzfw() {
		return quzfw;
	}

	public void setQuzfw(String quzfw) {
		this.quzfw = quzfw;
	}

	public String getMorz() {
		return morz;
	}

	public void setMorz(String morz) {
		this.morz = morz;
	}

	public String getJiaoygz() {
		return jiaoygz;
	}

	public void setJiaoygz(String jiaoygz) {
		this.jiaoygz = jiaoygz;
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
	public String getZhansmc() {
		return zhansmc;
	}
	public void setZhansmc(String zhansmc) {
		this.zhansmc = zhansmc;
	}
	public String getShifzj() {
		return shifzj;
	}
	public void setShifzj(String shifzj) {
		this.shifzj = shifzj;
	}

}
