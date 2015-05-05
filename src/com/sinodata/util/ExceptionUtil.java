package com.sinodata.util;

/**
 * @author Daniel
 * @2013-3-1
 * @TODO
 */
public class ExceptionUtil {
	
	public static  Throwable getNestedException(Throwable cause) {
		return cause.getCause();
	};

	public static String retrunErrorMsg(Throwable cause) {
		String msg = cause.getMessage();
		Throwable parent = cause;
		Throwable child;
		while ((child = getNestedException(parent)) != null) {
			String msg2 = child.getMessage();
			if (msg2 != null) {
				if (msg != null) {
					msg = msg2;
				} else {
					msg = msg2;
				}
			}
			if (child instanceof NullPointerException) {
				break;
			}
			parent = child;
		}
		if (msg.length() > 200) {
			msg = msg.substring(1, 200);
		}
		msg = packagingDBError(msg);
		return msg;
	}
	
	public static String packagingDBError(String str) {
		if (str.contains("DB2 SQL error: SQLCODE: -302")) {
			str = "上送数据中存在无效列或数据长度超限的列!";
		}
		if(str.contains("DB2 SQL error: SQLCODE: -104")) {
			str = "交易语句中遇到非法符号!";
		}
		return str;
	}

}
