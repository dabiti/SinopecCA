package com.unitop.sysmgr.bo;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AccountManageLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private String account;

	private String clerkname;

	private String clerknum;

	private String ip;

	private String managedate;

	private String managetime;

	private String managetype;
	
	private String managecontent;

	private String str1;

	private String upflag;

	private String orgcode;

	private String orgname;
	
	private String oldcontent;
	private String newcontent;
	

	public String getOldcontent() {
		return oldcontent;
	}

	public void setOldcontent(String oldcontent) {
		this.oldcontent = oldcontent;
	}

	public String getNewcontent() {
		return newcontent;
	}

	public void setNewcontent(String newcontent) {
		this.newcontent = newcontent;
	}

	public AccountManageLog() {
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getClerkname() {
		return this.clerkname;
	}

	public void setClerkname(String clerkname) {
		this.clerkname = clerkname;
	}

	public String getClerknum() {
		return this.clerknum;
	}

	public void setClerknum(String clerknum) {
		this.clerknum = clerknum;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getManagedate() {
		return this.managedate;
	}

	public void setManagedate(String managedate) {
		this.managedate = managedate;
	}

	public String getManagetime() {
		return this.managetime;
	}

	public void setManagetime(String managetime) {
		this.managetime = managetime;
	}

	public String getManagetype() {
		return this.managetype;
	}

	public void setManagetype(String managetype) {
		this.managetype = managetype;
	}

	public String getStr1() {
		return this.str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getUpflag() {
		return this.upflag;
	}

	public void setUpflag(String upflag) {
		this.upflag = upflag;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgname() {
		return this.orgname;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}

	public void setManagecontent(String managecontent) {
		this.managecontent = managecontent;
	}

	public String getManagecontent() {
		return managecontent;
	}

}