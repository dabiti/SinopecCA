package com.sinodata.table.util;

import java.util.Map;
public class TableUserDefined {
	
	private static Map tableClassPath = null;

	// ¹¹Ôìº¯Êý
	public  TableUserDefined(Map ureportClassPath) throws Exception {
		if (ureportClassPath == null) {
			throw new Exception("tableClassPath is null!");
		}
		TableUserDefined.tableClassPath = ureportClassPath;
	}

	public void putClassPath(String id, String value) {
		TableUserDefined.tableClassPath.put(id, value);
	}

	public static  String getClassPath(String id) {
		return (String)TableUserDefined.tableClassPath.get(id);
	}
}
