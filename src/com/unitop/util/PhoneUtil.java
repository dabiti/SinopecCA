package com.unitop.util;

public class PhoneUtil {

	//区号不为空 且长度不为0 要去掉首位0存入数据库
	public static String subAreaCode(String qh){
		if(qh==null){
			qh="";
		}
		return qh!=null&&qh.trim().length()!=0?qh.substring(1):qh;
	}
	
	//区号不为空 且长度不为0  并且首位不为0 的 要补0显示
	public  static String bulidAreaCode(String qh){
		if(qh==null){
			qh="";
		}
		return qh!=null&&qh.trim().length()!=0&&!qh.substring(0, 1).equals("0")?"0"+qh:qh;
	}
	//将区号和电话和分机号拼接
	public static String makePhone(String qh, String dianh, String fjh) {
		StringBuffer sb = new StringBuffer();
		if(qh!=null&&qh.trim().length()!=0){
			sb.append(qh).append("-");
		}
		if(dianh!=null&&dianh.trim().length()!=0){
			sb.append(dianh);
		}
		if(fjh!=null&&fjh.trim().length()!=0){
			sb.append("-").append(fjh);
		}
		return sb.toString();
	}
	
	//将区号和电话和分机号拼接
	public static String makePhoneToHole(String qh, String dianh, String fjh) {
		qh=bulidAreaCode(qh);
		StringBuffer sb = new StringBuffer();
		if(qh!=null&&qh.trim().length()!=0){
			sb.append(qh).append("-");
		}
		if(dianh!=null&&dianh.trim().length()!=0){
			sb.append(dianh);
		}
		if(fjh!=null&&fjh.trim().length()!=0){
			sb.append("-").append(fjh);
		}
		return sb.toString();
	}
}
