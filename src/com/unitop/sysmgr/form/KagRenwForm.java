package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class KagRenwForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6993465740842640788L;
	private String renwbs;
	private String zhangh;
	private String qiyrq;
	private String jigh;
	private String hum;
	private String renwlx;
	private String renwrq;
	private String renwsj;
	private String yewlx;
	private String renwzt;
	private String guiy;
	private String caozrq;
	private String caozsj;
	private int yinjks;
	private String zuob;
	
	
	public String getQiyrq() {
		return qiyrq;
	}
	public void setQiyrq(String qiyrq) {
		this.qiyrq = qiyrq;
	}
	public int getYinjks() {
		return yinjks;
	}
	public void setYinjks(int yinjks) {
		this.yinjks = yinjks;
	}
	public String getRenwbs() {
		return renwbs;
	}
	public void setRenwbs(String renwbs) {
		this.renwbs = renwbs;
	}
	public String getZhangh() {
		return zhangh;
	}
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}
	public String getJigh() {
		return jigh;
	}
	public void setJigh(String jigh) {
		this.jigh = jigh;
	}
	public String getHum() {
		return hum;
	}
	public void setHum(String hum) {
		this.hum = hum;
	}
	public String getRenwlx() {
		return renwlx;
	}
	public void setRenwlx(String renwlx) {
		this.renwlx = renwlx;
	}
	public String getRenwrq() {
		return renwrq;
	}
	public void setRenwrq(String renwrq) {
		this.renwrq = renwrq;
	}
	public String getRenwsj() {
		return renwsj;
	}
	public void setRenwsj(String renwsj) {
		this.renwsj = renwsj;
	}
	public String getYewlx() {
		return yewlx;
	}
	public void setYewlx(String yewlx) {
		this.yewlx = yewlx;
	}
	public String getRenwzt() {
		return renwzt;
	}
	public void setRenwzt(String renwzt) {
		this.renwzt = renwzt;
	}
	
	public String getGuiy() {
		return guiy;
	}
	public void setGuiy(String guiy) {
		this.guiy = guiy;
	}
	public String getCaozrq() {
		return caozrq;
	}
	public void setCaozrq(String caozrq) {
		this.caozrq = caozrq;
	}
	public String getCaozsj() {
		return caozsj;
	}
	public void setCaozsj(String caozsj) {
		this.caozsj = caozsj;
	}
	
	public String getZuob() {
		return zuob;
	}
	public void setZuob(String zuob) {
		this.zuob = zuob;
	}
	
	
	public KagRenwForm(String renwbs, String zhangh, String qiyrq, String jigh,
			String hum, String renwlx, String renwrq, String renwsj,
			String yewlx, String renwzt, String guiy, String caozrq,
			String caozsj, int yinjks, String zuob) {
		super();
		this.renwbs = renwbs;
		this.zhangh = zhangh;
		this.qiyrq = qiyrq;
		this.jigh = jigh;
		this.hum = hum;
		this.renwlx = renwlx;
		this.renwrq = renwrq;
		this.renwsj = renwsj;
		this.yewlx = yewlx;
		this.renwzt = renwzt;
		this.guiy = guiy;
		this.caozrq = caozrq;
		this.caozsj = caozsj;
		this.yinjks = yinjks;
		this.zuob = zuob;
	}
	public KagRenwForm() {
	}
	public void clear() {
		this.renwbs = "";
		this.zhangh = "";
		this.jigh = "";
		this.hum = "";
		this.renwlx = "";
		this.renwrq = "";
		this.renwsj = "";
		this.yewlx = "";
		this.renwzt = "";
		this.guiy = "";
		this.caozrq = "";
		this.caozsj = "";
		this.yinjks = 0;
		this.zuob = "";
		this.qiyrq = "";
	}
}
