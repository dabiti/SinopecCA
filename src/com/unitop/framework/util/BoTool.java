package com.unitop.framework.util;

import java.util.List;
import java.util.Map;

public class BoTool {

	/*
	 * Ȩ�޿���
	 * ˵����list ת�� Ȩ��JSON�ַ���
	 */
	public static String ListToJsonString(List list) {
		Map[] maparr = new Map[list.size()];
		return JsonTool.toJsonForArry(list);
	}
	
	/*
	 * Ȩ�޿���
	 * ˵���� Ȩ��JSON�ַ��� ת�� List
	 */
	public static List JsonStringToList(String jsonString){
		return JsonTool.toSringForList(jsonString,Map.class);
	}
}
