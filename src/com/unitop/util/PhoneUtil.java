package com.unitop.util;

public class PhoneUtil {

	//���Ų�Ϊ�� �ҳ��Ȳ�Ϊ0 Ҫȥ����λ0�������ݿ�
	public static String subAreaCode(String qh){
		if(qh==null){
			qh="";
		}
		return qh!=null&&qh.trim().length()!=0?qh.substring(1):qh;
	}
	
	//���Ų�Ϊ�� �ҳ��Ȳ�Ϊ0  ������λ��Ϊ0 �� Ҫ��0��ʾ
	public  static String bulidAreaCode(String qh){
		if(qh==null){
			qh="";
		}
		return qh!=null&&qh.trim().length()!=0&&!qh.substring(0, 1).equals("0")?"0"+qh:qh;
	}
	//�����ź͵绰�ͷֻ���ƴ��
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
	
	//�����ź͵绰�ͷֻ���ƴ��
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
