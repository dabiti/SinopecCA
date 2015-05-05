package com.unitop.sysmgr.bo;

import java.io.Serializable;
public class YinjkNum implements Serializable {
	private String jigh;
	private String guiyh;
	private String yinjkzt;
	private String num;
	public String getJigh() {
		return jigh;
	}
	public void setJigh(String jigh) {
		this.jigh = jigh;
	}
	public String getGuiyh() {
		return guiyh;
	}
	public void setGuiyh(String guiyh) {
		this.guiyh = guiyh;
	}
	public String getYinjkzt() {
		return yinjkzt;
	}
	public void setYinjkzt(String yinjkzt) {
		this.yinjkzt = yinjkzt;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public YinjkNum() {
		// TODO Auto-generated constructor stub
	}
	public YinjkNum(String jigh, String guiyh, String yinjkzt, String num) {
		super();
		this.jigh = jigh;
		this.guiyh = guiyh;
		this.yinjkzt = yinjkzt;
		this.num = num;
	}
	
	
	
	
	
	
}
