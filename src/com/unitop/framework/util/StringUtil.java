package com.unitop.framework.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

//����String by ldd 2011��4��23��10:39:53
public class StringUtil {
	public static final String mixings = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/*
	 * ʵ��HTMLչʾ��λ��Ϣ���� �Զ�����
	 * 
	 * by ldd
	 */
	public static String  EnterLineForHTML(String str ,int index){
		StringBuffer buffer  = new StringBuffer(str);
		if (buffer.length() > index)
		{
			buffer.insert(index, "</br>&nbsp;");
		}
		return buffer.toString();
	}
	
	public static String  intForIndex(String str ){
		if(str ==null)str="";
		int l =str.getBytes().length;
		boolean bool=true;
		for(int i=0;i<=l;i++)
		{
			String tt = str.substring(i);
			if(bool)
			{
				 try{
					 Long.valueOf(tt);
					 bool=false;
					 return tt;
				 }catch(Exception e){
				 }
			}else{
				break;
			}
			
		}
		return str.substring(l);
		
	}
	// ��������ַ���
	public static String getRandomStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(mixings.charAt(random.nextInt(mixings.length())));
		}
		return sb.toString();
	}
	
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static int isInteger(Object str) {
		if (str == null || "".equals(str)) {
			return 0;
		} else {
			return Integer.valueOf(str.toString());
		}
	}
	
	/**
	 * @param str
	 * �����null���ؿմ������򷵻����ַ���
	 */
	public static String nvl(String str){
		
		return ((str==null)?"":str);
	}
	/**
	 * @param str
	 * ��MAP ת��Ϊ String ȡkey ,�ָ�
	 */
	public static String mapToString(Map map){
		String str="";
		Iterator it = map.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			str+=key+",";
		}
		if(!"".equals(str))
		{
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
	
	
	public static void main(String[] args) {
		String str = "12a3";
		System.out.print(StringUtil.intForIndex(str));
	}
	

}
