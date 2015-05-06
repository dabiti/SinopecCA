package com.sinopec.table.model;

import java.util.HashMap;
import java.util.Map;

//过滤条件-实体
public class Where {

	private Map whereValue = new HashMap();
	
	private Map whereLikeValue = new HashMap();
	
	private Map whereBigValue = new HashMap();
	
	private Map whereBigEqualsValue = new HashMap();
	
	private Map whereSmaillValue = new HashMap();
	
	private Map whereSmaillEqualsValue = new HashMap();
	
	private Map whereInForSQL = new HashMap();
	
	public Map getWhereInForSQL() {
		return whereInForSQL;
	}

	public void setWhereInForSQL(Map whereInForSQL) {
		this.whereInForSQL = whereInForSQL;
	}

	public Map getWhereValue() {
		return whereValue;
	}

	public void setWhereValue(Map whereValue) {
		this.whereValue = whereValue;
	}

	public Map getWhereLikeValue() {
		return whereLikeValue;
	}

	public void setWhereLikeValue(Map whereLikeValue) {
		this.whereLikeValue = whereLikeValue;
	}

	public Map getWhereBigValue() {
		return whereBigValue;
	}

	public void setWhereBigValue(Map whereBigValue) {
		this.whereBigValue = whereBigValue;
	}

	public Map getWhereBigEqualsValue() {
		return whereBigEqualsValue;
	}

	public void setWhereBigEqualsValue(Map whereBigEqualsValue) {
		this.whereBigEqualsValue = whereBigEqualsValue;
	}

	public Map getWhereSmaillValue() {
		return whereSmaillValue;
	}

	public void setWhereSmaillValue(Map whereSmaillValue) {
		this.whereSmaillValue = whereSmaillValue;
	}

	public Map getWhereSmaillEqualsValue() {
		return whereSmaillEqualsValue;
	}

	public void setWhereSmaillEqualsValue(Map whereSmaillEqualsValue) {
		this.whereSmaillEqualsValue = whereSmaillEqualsValue;
	}


}
