package com.sinodata.report.util;

import java.util.Map;

/*
 * Ureoport 用户自定义容器类
 */
public class UeportUserDefined {

	private static Map ureportClassPath = null;

	// 构造函数
	public  UeportUserDefined(Map ureportClassPath) throws Exception {
		if (ureportClassPath == null) {
			throw new Exception("ureportClassPath is null!");
		}
		UeportUserDefined.ureportClassPath = ureportClassPath;
	}

	public void putClassPath(String id, String value) {
		UeportUserDefined.ureportClassPath.put(id, value);
	}

	public static  String getClassPath(String id) {
		return (String) UeportUserDefined.ureportClassPath.get(id);
	}
}