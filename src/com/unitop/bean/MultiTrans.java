package com.unitop.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiTrans { 
	
//	private Map<String,String> multiMap = new HashMap<String, String>();
//
//	public Map<String, String> getMultiMap() {
//		return multiMap;
//	}
//
//	public void setMultiMap(Map<String, String> multiMap) {
//		this.multiMap = multiMap;
//	}
	
	private ArrayList<String> list = new ArrayList<String>();
	
	public void addToList(String id)
	{
		list.add(id);
	}
	public ArrayList<String> getList()
	{
		return list;
	}

}
