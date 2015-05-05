package com.sinodata.report.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportResult {
	
	private String baobbs="";
	private String yaosbs="";
	private String yaosmc="";
	private String yaosbt="";
	private String yaoscd="";
	private String xianslx="";
	private String shifxs="";
	private String xianssx="";
	private String zhidbs="";
	private String yaosgs="";
	private String beiz="";
	
	private List reportResultList = null;
	public List getReportResultList() {
		return reportResultList;
	}
	public void setReportResultList(Map map) {
		if(reportResultList==null)
			reportResultList = new ArrayList();
		ReportResult reportResult = new ReportResult();
		reportResult.baobbs=(String) map.get("baobbs"); 
		reportResult.yaosbs=(String) map.get("yaosbs"); 
		reportResult.yaosmc=(String) map.get("yaosmc"); 
		reportResult.yaoscd=(String) map.get("yaosmc"); 
		reportResult.yaosbt=(String) map.get("yaosbt"); 
		reportResult.xianslx=(String) map.get("xianslx"); 
		reportResult.shifxs=(String) map.get("shifxs"); 
		reportResult.xianssx=(String) map.get("yaoscd"); 
		reportResult.zhidbs=(String) map.get("moyz"); 
		reportResult.yaosgs=(String) map.get("yaosgs"); 
		reportResult.beiz=(String) map.get("beiz"); 
		reportResultList.add(reportResult);
	}
	public String getXianslx() {
		return xianslx;
	}
	public void setXianslx(String xianslx) {
		this.xianslx = xianslx;
	}
	public String getYaosgs() {
		return yaosgs;
	}
	public void setYaosgs(String yaosgs) {
		this.yaosgs = yaosgs;
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
	public String getBeiz() {
		return beiz;
	}
	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}
	public void setYaosbt(String yaosbt) {
		this.yaosbt = yaosbt;
	}
	public String getYaoscd() {
		return yaoscd;
	}
	public void setYaoscd(String yaoscd) {
		this.yaoscd = yaoscd;
	}
	public String getShifxs() {
		return shifxs;
	}
	public void setShifxs(String shifxs) {
		this.shifxs = shifxs;
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
}
