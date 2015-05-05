package com.sinodata.report.model;

import java.util.Map;

public class ReportReturn {
	
	private String  tisxxid;
	private String  tisxxMessage;
	private Map map = null;
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public ReportReturn(String tisxxid,String tisxxMessage,Map map){
		this.tisxxid = tisxxid;
		this.tisxxMessage = tisxxMessage;
		this.map = map;
	}
	
	public String getTisxxid() {
		return tisxxid;
	}
	public void setTisxxid(String tisxxid) {
		this.tisxxid = tisxxid;
	}
	public String getTisxxMessage() {
		return tisxxMessage;
	}
	public void setTisxxMessage(String tisxxMessage) {
		this.tisxxMessage = tisxxMessage;
	}
}
