package com.unitop.util;

public class DateUtil {
	
	/**
	 * 将yyyymmdd格式的日志转换成yyyy-mm-dd
	 * @param date
	 * @return
	 */
	public static String turnToSimple(String date){
		if(date.trim().length()==8){
			date=date.substring(0, 4)+"-"+date.substring(4,6)+"-"+date.substring(6);
		}
		return date;
	}

}
