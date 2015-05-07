package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class CCBDailyLog implements Serializable{

	private static final long serialVersionUID = 1L;
	private String terminal_id;
	private float amountsum;
	private float poundagesum;
	private String seal_type;
	private String seal_date;
	private String card_type;
	private String seal_time;
	
	public String getSeal_time() {
		return seal_time;
	}

	public void setSeal_time(String seal_time) {
		this.seal_time = seal_time;
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
	public float getAmountsum() {
		return amountsum;
	}
	public void setAmountsum(float amountsum) {
		this.amountsum = amountsum;
	}
	public float getPoundagesum() {
		return poundagesum;
	}
	public void setPoundagesum(float poundagesum) {
		this.poundagesum = poundagesum;
	}
	public String getSeal_type() {
		return seal_type;
	}
	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}
	public String getSeal_date() {
		return seal_date;
	}
	public void setSeal_date(String seal_date) {
		this.seal_date = seal_date;
	}

	
}
