package com.unitop.sysmgr.bo;

/***********************************************************************
 * Module:  Yinjk.java
 * Author:  Administrator
 * Purpose: Defines the Class Yinjk
 ***********************************************************************/


public class Yinjk {
	
	private YinjkId yinjkid;
	private String yinjkh;
	private String zhangh;
	private String qiyrq;
	private String jigh;
	private String kagid;
	private String ceng;
	private String choutwz;
	private String zhengmwjm;//影像正面文件名
	private String fanmwjm;//反面文件名
	
	private String tingyrq;
	private String shifzk;
	private String yewlx;
	
	private String yinjkzt;
	private String clerkcode;
	
	public Yinjk() {
		// TODO Auto-generated constructor stub
	}
	public Yinjk(String zhangh,String yinjkh,String yinjkzt) {
		this.yinjkh=yinjkh;
		this.zhangh=zhangh;
		this.yinjkzt=yinjkzt.equals("01")?"领用":yinjkzt.equals("02")?"已用":yinjkzt.equals("03")?"变更":yinjkzt.equals("04")?"销户":yinjkzt.equals("05")?"作废":"";
	}
	
	public String getClerkcode() {
		return clerkcode;
	}
	public void setClerkcode(String clerkcode) {
		this.clerkcode = clerkcode;
	}
	public String getYinjkzt() {
		return yinjkzt;
	}
	public void setYinjkzt(String yinjkzt) {
		this.yinjkzt = yinjkzt;
	}
	public String getYinjkh() {
		return yinjkh;
	}
	public void setYinjkh(String yinjkh) {
		this.yinjkh = yinjkh;
	}
	public String getZhangh() {
		return zhangh;
	}
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}
	public String getQiyrq() {
		return qiyrq;
	}
	public void setQiyrq(String qiyrq) {
		this.qiyrq = qiyrq;
	}
	public String getCeng() {
		return ceng;
	}
	public void setCeng(String ceng) {
		this.ceng = ceng;
	}
	public String getChoutwz() {
		return choutwz;
	}
	public void setChoutwz(String choutwz) {
		this.choutwz = choutwz;
	}
	public YinjkId getYinjkid() {
		return yinjkid;
	}
	public void setYinjkid(YinjkId yinjkid) {
		this.yinjkid = yinjkid;
	}
	public String getJigh() {
		return jigh;
	}
	public void setJigh(String jigh) {
		this.jigh = jigh;
	}
	public String getKagid() {
		return kagid;
	}
	public void setKagid(String kagid) {
		this.kagid = kagid;
	}

	public String getZhengmwjm() {
		return zhengmwjm;
	}
	public void setZhengmwjm(String zhengmwjm) {
		this.zhengmwjm = zhengmwjm;
	}
	public String getFanmwjm() {
		return fanmwjm;
	}
	public void setFanmwjm(String fanmwjm) {
		this.fanmwjm = fanmwjm;
	}
	public String getTingyrq() {
		return tingyrq;
	}
	public void setTingyrq(String tingyrq) {
		this.tingyrq = tingyrq;
	}
	public String getShifzk() {
		return shifzk;
	}
	public void setShifzk(String shifzk) {
		this.shifzk = shifzk;
	}
	public String getYewlx() {
		return yewlx;
	}
	public void setYewlx(String yewlx) {
		this.yewlx = yewlx;
	}

	
}