package com.sinodata.report.model;

import java.util.HashMap;
import java.util.Map;

public class ReportDictionary {
	
	private Map map =new  HashMap();
	
	public void putDictionary(String suoyz ,String zhuanhz){
		this.map.put(suoyz, zhuanhz);
	}
	
	public String getDictionary(String suoyz){
		return (String) this.map.get(suoyz);
	}
	
}
