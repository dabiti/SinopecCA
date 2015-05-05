package com.sinodata.report.util;

import java.util.Map;

/*
 * Ureoport �û��Զ���������
 */
public class UeportUserDefined {

	private static Map ureportClassPath = null;

	// ���캯��
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