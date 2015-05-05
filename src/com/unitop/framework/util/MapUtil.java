package com.unitop.framework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	//map key 转大写
	public static Map MaptoUpperCase(Map map){
		Map temp = new HashMap();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext())
		{
			 Map.Entry entry = (Map.Entry) it.next();
			 temp.put(entry.getKey().toString().toUpperCase(), entry.getValue());
			 
		}
		return temp;
	}
	//map key 转小写
	public static Map MaptoLowerCase(Map map){
		Map temp = new HashMap();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext())
		{
			 Map.Entry entry = (Map.Entry) it.next();
			 temp.put(entry.getKey().toString().toLowerCase(), entry.getValue());
		}
		return temp;
	}
}
