package com.unitop.framework.util;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/*
 * by ldd 2011Äê8ÔÂ19ÈÕ 12:36:08
 */
public class JsonTool {

	/*
	 * String to JSON For OBJECT
	 */
	public static String toJsonForObject(Object b){
		JSONObject ja = JSONObject.fromObject(b);
		return ja.toString();
	}
	
	/*
	 * JSON to  Bean 
	 */
	public static Object toBean(String jsonString,Class c){
		JSONObject object = JSONObject.fromObject(jsonString);
		Object b = object.toBean(object,c);
		return b;
	}
	
	/*
	 * JSON to  List
	 */
	public static List toSringForList(String jsonString,Class c){
		List list = JSONArray.fromObject(jsonString);
		JSONArray ja = JSONArray.fromObject(list);
		List l = ja.toList(ja, c);
		return l;
	}
	
	/*
	 * String to JSON
	 */
	public static String toJsonForArry(Object b){
		JSONArray ja = JSONArray.fromObject(b);
		return ja.toString();
	}
	
	
}
