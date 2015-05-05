package com.sinodata.report.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportForm {
	
	public  String baobbs="";
	public  String yaosbs=""; 
	public  String yaosmc=""; 
	public  String yaosbt=""; 
	public  String yaoslx=""; 
	public  String shifbt=""; 
	public  String yaoscd=""; 
	public  String[] moyz=new String[2]; 
	public  String xianssx=""; 
	public  String zhidbs=""; 
	public  String yaoshw=""; 
	public  String beiz="";
	
	List reportFormList = null;
	
	public List getReportFormList() {
		return reportFormList;
	}
	public void setReportFormList(Map map) {
		if(reportFormList==null)
			reportFormList = new ArrayList();
		ReportForm reprot = new ReportForm();
		reprot.baobbs=(String) map.get("baobbs"); 
		reprot.yaosbs=(String) map.get("yaosbs"); 
		reprot.yaosmc=(String) map.get("yaosmc"); 
		reprot.yaosbt=(String) map.get("yaosbt"); 
		reprot.yaoslx=(String) map.get("yaoslx"); 
		reprot.shifbt=(String) map.get("shifbt"); 
		reprot.yaoscd=(String) map.get("yaoscd"); 
		reprot.moyz=(String[]) new String[]{(String) map.get("moyz"),(String) map.get("moyz")}; 
		reprot.xianssx=(String)map.get("xianssx");
		reprot.zhidbs=(String) map.get("zhidbs");
		reprot.yaoshw=(String) map.get("yaoshw");
		reprot.beiz=(String) map.get("beiz");
		reportFormList.add(reprot);
		map.put("moyz","");
	}
	
	public String getBeiz() {
		return beiz;
	}
	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}
	public String getBaobbs() {
		return baobbs;
	}
	public void setBaobbs(String baobbs) {
		this.baobbs = baobbs;
	}
	public String getYaosbs() {
		return yaosbs;
	}
	public void setYaosbs(String yaosbs) {
		this.yaosbs = yaosbs;
	}
	public String getYaosmc() {
		return yaosmc;
	}
	public void setYaosmc(String yaosmc) {
		this.yaosmc = yaosmc;
	}
	public String getYaosbt() {
		return yaosbt;
	}
	public void setYaosbt(String yaosbt) {
		this.yaosbt = yaosbt;
	}
	public String getYaoslx() {
		return yaoslx;
	}
	public void setYaoslx(String yaoslx) {
		this.yaoslx = yaoslx;
	}
	public String getShifbt() {
		return shifbt;
	}
	public void setShifbt(String shifbt) {
		this.shifbt = shifbt;
	}
	public String getYaoscd() {
		return yaoscd;
	}
	public void setYaoscd(String yaoscd) {
		this.yaoscd = yaoscd;
	}
	public String[] getMoyz() {
		return moyz;
	}
	public void setMoyz(String[] moyz) {
		this.moyz = moyz;
	}
	public String getXianssx() {
		return xianssx;
	}
	public void setXianssx(String xianssx) {
		this.xianssx = xianssx;
	}
	public String getZhidbs() {
		return zhidbs;
	}
	public void setZhidbs(String zhidbs) {
		this.zhidbs = zhidbs;
	}
	public String getYaoshw() {
		return yaoshw;
	}
	public void setYaoshw(String yaoshw) {
		this.yaoshw = yaoshw;
	}
}
