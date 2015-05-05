package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class AccountLog implements Serializable{
	
	private static final long serialVersionUID = 2704719025965976834L;

	private String clerknum;
	
	private String sqclerknum; 
	
	private String account;
	
	private String operdate;
	
	private String content;
	
	private String startdate;
	
	private String accountname;
	
	private String NETPOINTFLAG;
	
	private String INDUSTRYCHARACTER;
	
	private String caozlx;//²Ù×÷ÀàÐÍ

	public String getCaozlx() {
		return caozlx;
	}

	public void setCaozlx(String caozlx) {
		this.caozlx = caozlx;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getClerknum() {
		return clerknum;
	}

	public void setClerknum(String clerknum) {
		this.clerknum = clerknum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperdate() {
		return operdate;
	}

	public void setOperdate(String operdate) {
		this.operdate = operdate;
	}

	public String getSqclerknum() {
		return sqclerknum;
	}

	public void setSqclerknum(String sqclerknum) {
		this.sqclerknum = sqclerknum;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getNETPOINTFLAG() {
		return NETPOINTFLAG;
	}

	public void setNETPOINTFLAG(String netpointflag) {
		NETPOINTFLAG = netpointflag;
	}

	public String getINDUSTRYCHARACTER() {
		return INDUSTRYCHARACTER;
	}

	public void setINDUSTRYCHARACTER(String industrycharacter) {
		INDUSTRYCHARACTER = industrycharacter;
	}
	
	
	
}
