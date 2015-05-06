package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class CCBBills implements Serializable{

	private static final long serialVersionUID = 1L;
	private String bill_id;
	private String terminal_id;
	private String card_id;
	private float amount;
	private float poundage;
	private String seal_time;
	private String seal_type;
	private String card_type;
	
	public String getBill_id() {
		return bill_id;
	}
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getPoundage() {
		return poundage;
	}
	public void setPoundage(float poundage) {
		this.poundage = poundage;
	}
	public String getSeal_time() {
		return seal_time;
	}
	public void setSeal_time(String seal_time) {
		this.seal_time = seal_time;
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

}
