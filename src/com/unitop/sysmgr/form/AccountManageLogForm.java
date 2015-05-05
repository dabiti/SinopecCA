package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AccountManageLogForm extends ActionForm {
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
	private String str10;
	private String str2;
	private String str3;
	private String str4;
	private String str5;
	private String str6;
	private String str7;
	private String str8;
	private String str9;
	private String upflag;
	private String orgcode;
	private String orgname;
	
	
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.account = null;
		this.clerkname = null;
		this.clerknum = null;
		this.ip = null;
		this.managedate = null;
		this.managetime = null;
		this.managetype = null;
		this.str1 = null;
		this.str10 = null;
		this.str2 = null;
		this.str3 = null;
		this.str4 = null;
		this.str5 = null;
		this.str6 = null;
		this.str7 = null;
		this.str8 = null;
		this.str9 = null;
		this.upflag = null;
		this.orgcode = null;
		this.orgname = null;
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

	public String getStr10() {
		return this.str10;
	}
	public void setStr10(String str10) {
		this.str10 = str10;
	}

	public String getStr2() {
		return this.str2;
	}
	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return this.str3;
	}
	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return this.str4;
	}
	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public String getStr5() {
		return this.str5;
	}
	public void setStr5(String str5) {
		this.str5 = str5;
	}

	public String getStr6() {
		return this.str6;
	}
	public void setStr6(String str6) {
		this.str6 = str6;
	}

	public String getStr7() {
		return this.str7;
	}
	public void setStr7(String str7) {
		this.str7 = str7;
	}

	public String getStr8() {
		return this.str8;
	}
	public void setStr8(String str8) {
		this.str8 = str8;
	}

	public String getStr9() {
		return this.str9;
	}
	public void setStr9(String str9) {
		this.str9 = str9;
	}

	public String getUpflag() {
		return this.upflag;
	}
	public void setUpflag(String upflag) {
		this.upflag = upflag;
	}
	
	public void setOrgcode(String orgcode){
		this.orgcode = orgcode ;
	}
	
	public String getOrgcode(){
		return this.orgcode ;
	}
	
	public void setOrgname(String orgname){
		this.orgname = orgname ;
	}
	
	public String getOrgname(){
		return this.orgname ;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.toString();
	}

	public void setManagecontent(String managecontent) {
		this.managecontent = managecontent;
	}

	public String getManagecontent() {
		return managecontent;
	}
	
}
