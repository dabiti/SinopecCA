package com.unitop.util;

public class DateUtil {
	
	/**
	 * ��yyyymmdd��ʽ����־ת����yyyy-mm-dd
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
