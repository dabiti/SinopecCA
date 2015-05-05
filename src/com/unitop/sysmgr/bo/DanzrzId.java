package com.unitop.sysmgr.bo;

public class DanzrzId implements java.io.Serializable {
	private String account;
	private String checknum;
	private String sealinktype;
	private String sealinknum;
	private String checkdate;
	private String checktime;
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
	public String getSealinktype() {
		return sealinktype;
	}
	public void setSealinktype(String sealinktype) {
		this.sealinktype = sealinktype;
	}
	public String getSealinknum() {
		return sealinknum;
	}
	public void setSealinknum(String sealinknum) {
		this.sealinknum = sealinknum;
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
