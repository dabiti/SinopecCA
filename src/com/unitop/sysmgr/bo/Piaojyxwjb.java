package com.unitop.sysmgr.bo;

public class Piaojyxwjb implements java.io.Serializable {


	private PiaojyxwjbId id;
	private String yxlx;
	private String wenjfwdz;
	private String piaojyxdz;
	
	public String getYxlx() {
		return this.yxlx;
	}

	public void setYxlx(String yxlx) {
		this.yxlx = yxlx;
	}

	/** default constructor */
	public Piaojyxwjb() {
	}

	/** minimal constructor */
	public Piaojyxwjb(PiaojyxwjbId id) {
		this.id = id;
	}

	/** full constructor */
	public Piaojyxwjb(PiaojyxwjbId id, String wenjfwdz, String piaojyxdz) {
		this.id = id;
		this.wenjfwdz = wenjfwdz;
		this.piaojyxdz = piaojyxdz;
	}

	// Property accessors

	public PiaojyxwjbId getId() {
		return this.id;
	}

	public void setId(PiaojyxwjbId id) {
		this.id = id;
	}

	public String getWenjfwdz() {
		return this.wenjfwdz;
	}

	public void setWenjfwdz(String wenjfwdz) {
		this.wenjfwdz = wenjfwdz;
	}

	public String getPiaojyxdz() {
		return this.piaojyxdz;
	}

	public void setPiaojyxdz(String piaojyxdz) {
		this.piaojyxdz = piaojyxdz;
	}

}