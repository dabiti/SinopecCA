package com.unitop.config;

public class Transfer {

	public static String caidChange(String str){
		String c = "";
		if("1".equals(str)){
			c="1-Õ¹ÏÖ";
		}else if("0".equals(str)){
			c="0-Òþ²Ø";
		}
		return c;
	}
}
