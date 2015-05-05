package com.unitop.sysmgr.bo;
import java.io.Serializable;
/*
 * by wp
 * 20130409
 */

public class Ocxkongzcsb implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	private String target;
	private String defaultvalue;
	private String remark;
	

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

}
