package com.unitop.sysmgr.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

public class AccountnumForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private String account;
	private String accountcharacter;
	private String accountname;
	private String accountstate;
	private String address;
	private String allexchange;
	private String billperiod;
	private String checkperiod;
	private Long creditgrade;
	private String englishname;
	private String enterprisecharacter;
	private String incomerange;
	private String industrycharacter;
	private String linkman;
	private String mainoperation;
	private String netpointflag;
	private String opendate;
	private String phone;
	private String postalcode;
	private Double registercapital;
	private String remark;
	private String startdate;
	private Long timestampnum;
	private String yearcheckdate;
	
	private String sealstate; 
	private String yinqbz; 
	public String getAccount() {
		return this.account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountcharacter() {
		return this.accountcharacter;
	}
	public void setAccountcharacter(String accountcharacter) {
		this.accountcharacter = accountcharacter;
	}

	public String getAccountname() {
		return this.accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccountstate() {
		return this.accountstate;
	}
	public void setAccountstate(String accountstate) {
		this.accountstate = accountstate;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getAllexchange() {
		return this.allexchange;
	}
	public void setAllexchange(String allexchange) {
		this.allexchange = allexchange;
	}

	public String getBillperiod() {
		return this.billperiod;
	}
	public void setBillperiod(String billperiod) {
		this.billperiod = billperiod;
	}

	public String getCheckperiod() {
		return this.checkperiod;
	}
	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}

	public Long getCreditgrade() {
		return this.creditgrade;
	}
	public void setCreditgrade(Long creditgrade) {
		this.creditgrade = creditgrade;
	}

	public String getEnglishname() {
		return this.englishname;
	}
	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getEnterprisecharacter() {
		return this.enterprisecharacter;
	}
	public void setEnterprisecharacter(String enterprisecharacter) {
		this.enterprisecharacter = enterprisecharacter;
	}

	public String getIncomerange() {
		return this.incomerange;
	}
	public void setIncomerange(String incomerange) {
		this.incomerange = incomerange;
	}

	public String getIndustrycharacter() {
		return this.industrycharacter;
	}
	public void setIndustrycharacter(String industrycharacter) {
		this.industrycharacter = industrycharacter;
	}

	public String getLinkman() {
		return this.linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMainoperation() {
		return this.mainoperation;
	}
	public void setMainoperation(String mainoperation) {
		this.mainoperation = mainoperation;
	}

	public String getNetpointflag() {
		return this.netpointflag;
	}
	public void setNetpointflag(String netpointflag) {
		this.netpointflag = netpointflag;
	}

	public String getOpendate() {
		return this.opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalcode() {
		return this.postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public Double getRegistercapital() {
		return this.registercapital;
	}
	public void setRegistercapital(Double registercapital) {
		this.registercapital = registercapital;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartdate() {
		return this.startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public Long getTimestampnum() {
		return this.timestampnum;
	}
	public void setTimestampnum(Long timestampnum) {
		this.timestampnum = timestampnum;
	}

	public String getYearcheckdate() {
		return this.yearcheckdate;
	}
	public void setYearcheckdate(String yearcheckdate) {
		this.yearcheckdate = yearcheckdate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("account", getAccount())
			.toString();
	}
	public String getSealstate() {
		return sealstate;
	}
	public void setSealstate(String sealstate) {
		this.sealstate = sealstate;
	}
	public String getYinqbz() {
		return yinqbz;
	}
	public void setYinqbz(String yinqbz) {
		this.yinqbz = yinqbz;
	}
}
