package com.unitop.sysmgr.bo;

/*
 * by wp
 */

public class Pingzgcb implements java.io.Serializable {
	
	private PingzgcbId pingzgcbid = new PingzgcbId();
	private String rukrq;
	private String rukjg;
	private String guiyh;
	private String lingyjg;
	private String jiglyfzr;
	private String pingzzt;
	
	public Pingzgcb(){
		
	}
	public Pingzgcb(String pingzh,String pingzlx,String rukrq,String rukjg,String guiyh,String lingyjg,String jiglyfzr,String pingzzt){
		
		this.pingzgcbid=pingzgcbid;
		this.rukrq=rukrq;
		this.rukjg=rukjg;
		this.guiyh=guiyh;
		this.lingyjg=lingyjg;
		this.jiglyfzr=jiglyfzr;
		this.pingzzt=pingzzt;
		
	}
	
	public void setRukrq(String rukrq) {
		this.rukrq = rukrq;
	}
	public String getRukrq() {
		return rukrq;
	}
	public void setRukjg(String rukjg) {
		this.rukjg = rukjg;
	}
	public String getRukjg() {
		return rukjg;
	}
	public void setGuiyh(String guiyh) {
		this.guiyh = guiyh;
	}
	public String getGuiyh() {
		return guiyh;
	}
	public void setLingyjg(String lingyjg) {
		this.lingyjg = lingyjg;
	}
	public String getLingyjg() {
		return lingyjg;
	}
	public void setJiglyfzr(String jiglyfzr) {
		this.jiglyfzr = jiglyfzr;
	}
	public String getJiglyfzr() {
		return jiglyfzr;
	}
	public void setPingzzt(String pingzzt) {
		this.pingzzt = pingzzt;
	}
	public String getPingzzt() {
		return pingzzt;
	}
	public void setPingzgcbid(PingzgcbId pingzgcbid) {
		this.pingzgcbid = pingzgcbid;
	}
	public PingzgcbId getPingzgcbid() {
		return pingzgcbid;
	}

}
