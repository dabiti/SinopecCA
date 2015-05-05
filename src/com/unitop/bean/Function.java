package com.unitop.bean;

import com.unitop.bean.MultiTrans;

public class Function {
	private String id;

	private String sql;

	private String type;
	
	private String remark;

	private MultiTrans multi=null;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MultiTrans getMutil() {
		return multi;
	}

	public void setMutil(MultiTrans multi) {
		this.multi = multi;
	}






}
