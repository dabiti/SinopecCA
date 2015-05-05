package com.unitop.framework.util;

import java.util.List;
import java.util.Map;

public class BoTool {

	/*
	 * 权限控制
	 * 说明：list 转换 权限JSON字符串
	 */
	public static String ListToJsonString(List list) {
		Map[] maparr = new Map[list.size()];
		return JsonTool.toJsonForArry(list);
	}
	
	/*
	 * 权限控制
	 * 说明： 权限JSON字符串 转换 List
	 */
	public static List JsonStringToList(String jsonString){
		return JsonTool.toSringForList(jsonString,Map.class);
	}
}
