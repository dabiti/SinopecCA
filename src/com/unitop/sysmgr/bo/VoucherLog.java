package com.unitop.sysmgr.bo;

public class VoucherLog {
	
	private int index;
	private String account;
	private String checkNum;
	private String money;
	private String checkDate;
	private String clerkNum;
	private String clerkName;
	private String checkResult;
	private String checkMode;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckMode() {
		return checkMode;
	}
	public void setCheckMode(String checkMode) {
		this.checkMode = checkMode;
	}
	public String getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getClerkName() {
		return clerkName;
	}
	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}
	public String getClerkNum() {
		return clerkNum;
	}
	public void setClerkNum(String clerkNum) {
		this.clerkNum = clerkNum;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
}
