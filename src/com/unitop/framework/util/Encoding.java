package com.unitop.framework.util;

import java.io.IOException;

public class Encoding {
	
	private String str;
	
	public Encoding(String str) {
		super();
		this.str = str;
	}

	public static String getGBKCoding(String str) throws IOException{
		str = new String(str.getBytes("ISO-8859-1"), "GBK");
		return str;
	}
	
	public static String getGBKCodingByUtf(String str) throws IOException{
		str = new String(str.getBytes("UTF-8"), "GBK");
		return str;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
