package com.unitop.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgPraseUtil {
	
	public static Map<String,String> praseMsg(byte[] msg,int[]nums,String nameStr) {
		Map<String,String> msgMap=new HashMap<String, String>();
		List<String>names=splitStr(nameStr);
		int count=0;
		for (int i = 0; i < nums.length; i++) {
			String value;
			try {
				value = new String(ByteArrayUtil.copyOfRange(msg, count, count+nums[i]),"GBK");
			} catch (UnsupportedEncodingException e) {
				value="";
			}
			msgMap.put(names.get(i), value);
			count+=nums[i];
		}
		return msgMap;
	}
	// ²ð·ÖÓ¡¼ø¿¨ºÅ×Ö·û´®
	private static  List<String> splitStr(String string) {

		List<String> strList = new ArrayList<String>();
		if (string != null && string.trim().length() != 0) {
			String[] str = string.split(",", 0);
			for (int i = 0; i < str.length; i++) {
				strList.add(str[i]);
			}
		}
		return strList;
	}
}
