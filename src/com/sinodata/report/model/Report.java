package com.sinodata.report.model;

import java.util.Map;

public class Report {
	
	//wtf
	
	//fuck git test
	private String baobbs="";
	private String baobmc="";
	private String baobbt="";
	private String shifky="";
	private String shiffy="";
	private String fenytj=""; 
	private String shifdy="";
	private String dayfa="";
	private String shifsc="";
	private String shucfa="";
	private String shujhqfs="";
	private String zhidyl="";
	
	public  Report(Map map){
		this.baobbs = map.get("baobbs").toString();
		this.baobmc=map.get("baobmc").toString();
		this.baobbt=map.get("baobbt").toString();
		this.shifky=map.get("shifky").toString();
		this.shiffy=map.get("shiffy").toString();
		this.fenytj=map.get("fenytj").toString(); 
		this.shifdy=map.get("shifdy").toString();
		this.dayfa=map.get("dayfa").toString();
		this.shifsc=map.get("shifsc").toString();
		this.shucfa=map.get("shucfa").toString();
		this.shujhqfs=map.get("shujhqfs").toString();
		this.zhidyl=map.get("zhidyl").toString();
	}
	public String getZhidyl() {
		return zhidyl;
	}

	public void setZhidyl(String zhidyl) {
		this.zhidyl = zhidyl;
	}
	
	public String getBaobbs() {
		return baobbs;
	}
	public void setBaobbs(String baobbs) {
		this.baobbs = baobbs;
	}
	public String getBaobmc() {
		return baobmc;
	}
	public void setBaobmc(String baobmc) {
		this.baobmc = baobmc;
	}
	public String getBaobbt() {
		return baobbt;
	}
	public void setBaobbt(String baobbt) {
		this.baobbt = baobbt;
	}
	public String getShifky() {
		return shifky;
	}
	public void setShifky(String shifky) {
		this.shifky = shifky;
	}
	public String getShiffy() {
		return shiffy;
	}
	public void setShiffy(String shiffy) {
		this.shiffy = shiffy;
	}
	public String getFenytj() {
		return fenytj;
	}
	public void setFenytj(String fenytj) {
		this.fenytj = fenytj;
	}
	public String getShifdy() {
		return shifdy;
	}
	public void setShifdy(String shifdy) {
		this.shifdy = shifdy;
	}
	public String getDayfa() {
		return dayfa;
	}
	public void setDayfa(String dayfa) {
		this.dayfa = dayfa;
	}
	public String getShifsc() {
		return shifsc;
	}
	public void setShifsc(String shifsc) {
		this.shifsc = shifsc;
	}
	public String getShucfa() {
		return shucfa;
	}
	public void setShucfa(String shucfa) {
		this.shucfa = shucfa;
	}
	public String getShujhqfs() {
		return shujhqfs;
	}
	public void setShujhqfs(String shujhqfs) {
		this.shujhqfs = shujhqfs;
	}

}
