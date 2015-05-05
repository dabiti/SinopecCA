package com.unitop.framework.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Format {

	/*
	 * 金额格式化
	 */
	public static String formatJine(double jine){
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern("###,###.##");
		String jineStr = df.format(jine);
		int bz = jineStr.indexOf(".");
		if(bz==-1)
		{
			jineStr = jineStr+".00";
		}
		else{
			String lengStr =jineStr.substring(bz, jineStr.length());
			if(lengStr.length()==2)
			{
				jineStr = jineStr+"0";
			}
		}
		return jineStr;
	}
}
