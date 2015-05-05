package com.unitop.sysmgr.bo;

/**
 * Yinjlxb entity. @author MyEclipse Persistence Tools
 */

public class Yinjlxb implements java.io.Serializable {

	// Fields

	private String yinjlx;
	private String beiz;
	private String shifqy;

	// Constructors

	/** default constructor */
	public Yinjlxb() {
	}

	/** minimal constructor */
	public Yinjlxb(String yinjlx) {
		this.yinjlx = yinjlx;
	}

	/** full constructor */
	public Yinjlxb(String yinjlx, String beiz, String shifqy) {
		this.yinjlx = yinjlx;
		this.beiz = beiz;
		this.shifqy = shifqy;
	}

	// Property accessors

	public String getYinjlx() {
		return this.yinjlx;
	}

	public void setYinjlx(String yinjlx) {
		this.yinjlx = yinjlx;
	}

	public String getBeiz() {
		return this.beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}

	public String getShifqy() {
		return this.shifqy;
	}

	public void setShifqy(String shifqy) {
		this.shifqy = shifqy;
	}

}