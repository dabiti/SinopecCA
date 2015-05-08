package com.unitop.sysmgr.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CheckAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	private String site_name;
	private String terminal_id;
	private BigDecimal sino_amountsum;
	private BigDecimal sino_poundagesum;
	private BigDecimal ccb_amountsum;
	private BigDecimal ccb_poundagesum;
	private BigDecimal ccb_settlesum;
	private String seal_date;
	private String seal_type;
	private String card_type;
	private String seal_time;
	
	public String getSeal_time() {
		return seal_time;
	}

	public void setSeal_time(String seal_time) {
		this.seal_time = seal_time;
	}
	public String getSeal_date() {
		return seal_date;
	}

	public void setSeal_date(String seal_date) {
		this.seal_date = seal_date;
	}

	public String getSeal_type() {
		return seal_type;
	}

	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	
	
	public BigDecimal getSino_amountsum() {
		return sino_amountsum;
	}

	public void setSino_amountsum(BigDecimal sino_amountsum) {
		this.sino_amountsum = sino_amountsum;
	}

	public BigDecimal getSino_poundagesum() {
		return sino_poundagesum;
	}

	public void setSino_poundagesum(BigDecimal sino_poundagesum) {
		this.sino_poundagesum = sino_poundagesum;
	}

	public BigDecimal getCcb_amountsum() {
		return ccb_amountsum;
	}

	public void setCcb_amountsum(BigDecimal ccb_amountsum) {
		this.ccb_amountsum = ccb_amountsum;
	}

	public BigDecimal getCcb_poundagesum() {
		return ccb_poundagesum;
	}

	public void setCcb_poundagesum(BigDecimal ccb_poundagesum) {
		this.ccb_poundagesum = ccb_poundagesum;
	}

	public BigDecimal getCcb_settlesum() {
		return ccb_settlesum;
	}

	public void setCcb_settlesum(BigDecimal ccb_settlesum) {
		this.ccb_settlesum = ccb_settlesum;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	} 
}
