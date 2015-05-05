package com.unitop.sysmgr.bo;

/**
 * Guiyjsgxb entity. @author MyEclipse Persistence Tools
 */

public class Guiyjsgxb implements java.io.Serializable {

	// Fields

	private GuiyjsgxbId id;
	private String beiz;

	// Constructors

	/** default constructor */
	public Guiyjsgxb() {
	}

	/** minimal constructor */
	public Guiyjsgxb(GuiyjsgxbId id) {
		this.id = id;
	}

	/** full constructor */
	public Guiyjsgxb(GuiyjsgxbId id, String beiz) {
		this.id = id;
		this.beiz = beiz;
	}

	// Property accessors

	public GuiyjsgxbId getId() {
		return this.id;
	}

	public void setId(GuiyjsgxbId id) {
		this.id = id;
	}

	public String getBeiz() {
		return this.beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}

}