package com.unitop.sysmgr.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CCBDailyLog implements Serializable{

	private static final long serialVersionUID = 1L;
	private String terminal_id;
	private BigDecimal amountsum;
	private BigDecimal poundagesum;
	private String seal_date;
	private String legalname;
	
	
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	

	public BigDecimal getAmountsum() {
		return amountsum;
	}

	public void setAmountsum(BigDecimal amountsum) {
		this.amountsum = amountsum;
	}

	public BigDecimal getPoundagesum() {
		return poundagesum;
	}

	public void setPoundagesum(BigDecimal poundagesum) {
		this.poundagesum = poundagesum;
	}


	public String getSeal_date() {
		return seal_date;
	}
	public void setSeal_date(String seal_date) {
		this.seal_date = seal_date;
	}

	public String getLegalname() {
		return legalname;
	}

	public void setLegalname(String legalname) {
		this.legalname = legalname;
	}

	
	
	
}
