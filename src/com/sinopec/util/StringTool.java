package com.sinopec.util;

import java.util.HashMap;
import java.util.Map;

public class StringTool {
	
	//���ַ�����Ѱ������ �㷨
	public static Map fingNumberInString(Map<String,String> map ,String str) {
		if(str==null)str="";
		if(map==null) map = new HashMap();
		for (String sss : str.replaceAll("[^0-9]", ",").split(",")) 
		{
			if (sss.length() > 0)map.put(sss, "");
		}
		return map;
	}
	
}
