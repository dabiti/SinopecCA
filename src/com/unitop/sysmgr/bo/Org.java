package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class Org implements Serializable {

	private static final long serialVersionUID = -4942397894039134471L;

	private String code;

	private String name;
	
	private String parentCode;

	private String paymentCode;
	
	private String wdflag;
	
	private String shOrgCode;
	
	private String tctd;

	private String leixbs;
	
	private String status;
	
	private String terminal_id;
	

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String socketAdd;


	public String getLeixbs() {
		return leixbs;
	}

	public void setLeixbs(String leixbs) {
		this.leixbs = leixbs;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getWdflag() {
		return wdflag;
	}

	public void setWdflag(String wdflag) {
		this.wdflag = wdflag;
	}

	public String getShOrgCode() {
		return shOrgCode;
	}

	public void setShOrgCode(String shOrgCode) {
		this.shOrgCode = shOrgCode;
	}

	public String getTctd() {
		return tctd;
	}

	public void setTctd(String tctd) {
		this.tctd = tctd;
	}

	public String getSocketAdd() {
		return socketAdd;
	}

	public void setSocketAdd(String socketAdd) {
		this.socketAdd = socketAdd;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	
	
	
}
