package com.unitop.util;

import java.io.IOException;
import java.util.Properties;

public class CommonUtil {
	private static Properties params = new Properties();
	static {
		try {
			params.load(CommonUtil.class.getClassLoader().getResourceAsStream("orgCodeCompara.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCoreBranchCode(String code) {
		String result="";
		if(code!=null&&!code.trim().equals("")){
			result= params.getProperty(code);
			if(result==null){
				return "";
			}else{
				return result;
			}
		}
		
		return result;
	}
}
