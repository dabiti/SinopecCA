package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class ChoutForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6594871028726223952L;
	private String kagid;
	private String ceng;
	private String choutwz;
	private int shengykj;
	private int zongkj;
	private int shiykj;
	private String jigh;
	private String choutzt;
	
	
	public ChoutForm(String kagid, String ceng, String choutwz, int shengykj,
			int zongkj, int shiykj, String jigh, String choutzt) {
		super();
		this.kagid = kagid;
		this.ceng = ceng;
		this.choutwz = choutwz;
		this.shengykj = shengykj;
		this.zongkj = zongkj;
		this.shiykj = shiykj;
		this.jigh = jigh;
		this.choutzt = choutzt;
	}
	public void setCeng(String ceng) {
		this.ceng = ceng;
	}
	public void setChoutwz(String choutwz) {
		this.choutwz = choutwz;
	}
	public String getChoutzt() {
		return choutzt;
	}
	public void setChoutzt(String choutzt) {
		this.choutzt = choutzt;
	}
	public ChoutForm() {
		super();
	}
	public String getKagid() {
		return kagid;
	}
	public void setKagid(String kagid) {
		this.kagid = kagid;
	}

	public int getShengykj() {
		return shengykj;
	}
	public void setShengykj(int shengykj) {
		this.shengykj = shengykj;
	}
	public int getZongkj() {
		return zongkj;
	}
	public void setZongkj(int zongkj) {
		this.zongkj = zongkj;
	}
	public int getShiykj() {
		return shiykj;
	}
	public void setShiykj(int shiykj) {
		this.shiykj = shiykj;
	}
	public String getJigh() {
		return jigh;
	}
	public void setJigh(String jigh) {
		this.jigh = jigh;
	}
	
	
}
