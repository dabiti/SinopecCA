package com.unitop.sysmgr.bo;

public class PiaojyxwjbId implements java.io.Serializable {

	// Fields

	private String zhangh;
	private String wenjbh;

	// Constructors

	/** default constructor */
	public PiaojyxwjbId() {
	}

	/** full constructor */
	public PiaojyxwjbId(String zhangh, String wenjbh) {
		this.zhangh = zhangh;
		this.wenjbh = wenjbh;
	}

	// Property accessors

	public String getZhangh() {
		return this.zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	public String getWenjbh() {
		return this.wenjbh;
	}

	public void setWenjbh(String wenjbh) {
		this.wenjbh = wenjbh;
	}

}