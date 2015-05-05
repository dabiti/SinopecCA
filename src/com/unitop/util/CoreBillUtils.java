package com.unitop.util;

import java.text.DecimalFormat;


public class CoreBillUtils {
	
	/**
	 * ��װ���������ַ���
	 * @param n ԭʼֵ
	 * @param len ��Ҫ��װ�ĳ���
	 * @return ��װ����ַ���
	 */
	public static String parseTypeN(String n,int len) {
		if(n==null||"".equals(n.trim())){
			n = "0";
		} else if(n.trim().length()>17) {
			String brchCode = CommonUtil.getCoreBranchCode(n.substring(0,8));
			
			if(!"".equals(brchCode)) {
				n = brchCode + n.substring(8, 20);
				n = Paritybitcal.GenBancsCd(n);
				
			}
		}
		if (n.length()>len) {
			n = n.substring(0,len);
		}
		String str = String.valueOf(n);
		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}
	
	/**
	 * �������ص������ַ���
	 * @param str ���ص��ַ���
	 * @return �������͵��ַ���
	 */
	public static String reverseTypeN(String str) {
		if(str == null||"".equals(str.trim())) {
			return "";
		}
		return String.valueOf(Long.parseLong(str));
	}
	
	/**
	 * ��װ�з�����ֵ������������
	 * @param ԭʼ����
	 * @param Ҫ��װ�ĳ���
	 * @return ��װ�õ��з�����ֵ�ַ���
	 */
	public static String parseTypeS(Double n,int len) {
		if(n==null){
			n = Double.valueOf(0.00);
		}
		n = n * 100;
		DecimalFormat df = null;
		String str = null;
		if(n < 0.0) {
			df = new DecimalFormat("0-");
			str = df.format(Math.round(Math.abs(n))*10);
		} else {
			df = new DecimalFormat("0+");
			str = df.format(Math.round(n)*10);
		}
		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}
	/**
	 * ��װ�з�����ֵ������������
	 * @param ԭʼ����
	 * @param Ҫ��װ�ĳ���
	 * @return ��װ�õ��з�����ֵ�ַ���
	 */
	public static String parseTypeD(Double n,int len) {
		if(n==null){  
			n = Double.valueOf(0.00);
		}
		n = n * 10000;
		DecimalFormat df = null;
		String str = null;
		if(n < 0.0) {
			df = new DecimalFormat("0-");
			str = df.format(Math.round(Math.abs(n)));
		} else {
			df = new DecimalFormat("0+");
			str = df.format(Math.round(n));
		}
		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}
	
	/**
	 * ����Ӧ������ݵõ��з�����ֵ
	 * @param str ԭʼ����
	 * @return �з�����ֵ
	 */
	public static Double reverseTypeS(String str) {
		if(str==null||"".equals(str.trim())) {
			return Double.valueOf(0.00);
		}
		int len = str.length();
		Double d = null;
		String str1 = str.substring(0, len - 1);
		String str2 = str.substring(len - 1);
		if("-".equals(str2)) {
			d = Double.valueOf(str1);
			d = d/1000.00;
			return -d;
		} else {
			d = Double.valueOf(str1);
			d = d/1000.00;
			return d;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(parseTypeN("61332020"+"123456789012"+"34",17));
//		System.out.println(reverseTypeN(parseTypeN("1",20)));
//		System.out.println(parseTypeS(99.0/100.00*333.33,18));
//		System.out.println(reverseTypeS(parseTypeS(-1.0,20)));
//		System.out.println(parseTypeS(1.0,20));
//		System.out.println(reverseTypeS(parseTypeS(1.0,20)));
	}
	
}
