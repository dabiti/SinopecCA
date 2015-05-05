package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class ChoutId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4510965355664171215L;
	private String kagid;
	private String ceng;
	private String choutwz;
	
	public String getKagid() {
		return kagid;
	}
	public void setKagid(String kagid) {
		this.kagid = kagid;
	}
	public String getCeng() {
		return ceng;
	}
	public void setCeng(String ceng) {
		this.ceng = ceng;
	}
	public String getChoutwz() {
		return choutwz;
	}
	public void setChoutwz(String choutwz) {
		this.choutwz = choutwz;
	}
	public ChoutId(String kagid, String ceng, String choutwz) {
		super();
		this.kagid = kagid;
		this.ceng = ceng;
		this.choutwz = choutwz;
	}
	public ChoutId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
