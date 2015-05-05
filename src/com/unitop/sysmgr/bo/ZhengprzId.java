package com.unitop.sysmgr.bo;

import com.unitop.framework.util.Format;

/*
 * ��Ʊ��ӡ��־
 * by ldd
 * ���hibernate֧�ֲ�ѯ���ݿ�����ظ���¼����
 */
public class ZhengprzId implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String account;//�ʺ�
	private String checknum;//ƾ֤��
	private String money;//���
	private String checkdate;//��ӡ����
	private String checktime;//��ӡʱ��

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChecknum() {
		return checknum;
	}

	public void setChecknum(String checknum) {
		this.checknum = checknum;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		//����ʽ��
		double jine = Double.valueOf(money);
		double i = 100.00d;
		this.money = Format.formatJine((double)jine/i);
	}

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
}