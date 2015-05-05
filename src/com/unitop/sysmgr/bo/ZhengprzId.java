package com.unitop.sysmgr.bo;

import com.unitop.framework.util.Format;

/*
 * 整票验印日志
 * by ldd
 * 解决hibernate支持查询数据库出现重复记录问题
 */
public class ZhengprzId implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String account;//帐号
	private String checknum;//凭证号
	private String money;//金额
	private String checkdate;//验印日期
	private String checktime;//验印时间

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
		//金额格式化
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