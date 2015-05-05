package com.unitop.sysmgr.bo;

/***********************************************************************
 * Module:  KagRenw.java
 * Author:  Administrator
 * Purpose: Defines the Class KagRenw
 ***********************************************************************/
public class KagRenw {
	private String renwbs;
	private String zhangh;
	private String qiyrq;
	private String jigh;
	private String hum;
	private String renwlx;
	private String renwrq;
	private String renwsj;
	private int yinjks;
	private String yewlx;
	private String renwzt;
	private String guiy;//执行任务的柜员
	private String caozrq;
	private String caozsj;
	private String zuob;//分配抽屉的坐标
	
	private String yinjkh;//SIT测试时添加字段，解决打开柜子时印鉴卡表中KAGID、CENG、CHOUTWZ字段值未传入问题
	
	
	public String getQiyrq() {
		return qiyrq;
	}
	public void setQiyrq(String qiyrq) {
		this.qiyrq = qiyrq;
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
	public int getYinjks() {
		return yinjks;
	}
	public void setYinjks(int yinjks) {
		this.yinjks = yinjks;
	}
	public String getZuob() {
		return zuob;
	}
	public void setZuob(String zuob) {
		this.zuob = zuob;
	}
	public void setYinjkh(String yinjkh) {
		this.yinjkh = yinjkh;
	}
	public String getYinjkh() {
		return yinjkh;
	}
	
	
}