package com.unitop.sysmgr.bo;

/**
 * Juesqxgxb entity. @author MyEclipse Persistence Tools
 */

public class Juesqxgxb implements java.io.Serializable {

	// Fields

	private JuesqxgxbId id;
	private String beiz;

	// Constructors

	/** default constructor */
	public Juesqxgxb() {
	}

	/** minimal constructor */
	public Juesqxgxb(JuesqxgxbId id) {
		this.id = id;
	}

	/** full constructor */
	public Juesqxgxb(String  juesid,String quanxid, String beiz) {
		this.id = new JuesqxgxbId(juesid,quanxid);
		this.beiz = beiz;
	}

	// Property accessors

	public JuesqxgxbId getId() {
		return this.id;
	}

	public void setId(JuesqxgxbId id) {
		this.id = id;
	}

	public String getBeiz() {
		return this.beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}


}