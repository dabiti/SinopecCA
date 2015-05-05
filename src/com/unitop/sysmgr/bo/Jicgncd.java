package com.unitop.sysmgr.bo;

/**
 * Jicgncd entity. @author MyEclipse Persistence Tools
 */

public class Jicgncd implements java.io.Serializable {

	// Fields

	private JicgncdId id ;
	private String gongnmc;
	private String gongnurl;
	private String gongnlx;
	private String beiz;
	private String quanxwz;
	private String caidlx;
	private String classid;
	
	public void setClassid(String classid) {
		this.classid = classid;
	}
	
	public String getClassid() {
		return classid;
	}

	// Constructors

	/** default constructor */
	public Jicgncd() {
	}

	/** minimal constructor */
	public Jicgncd(JicgncdId id) {
		this.id = id;
	}

	/** full constructor */
	public Jicgncd(JicgncdId id, String gongnmc, String gongnurl,
			String gongnlx, String beiz, String quanxwz, String caidlx) {
		this.id = id;
		this.gongnmc = gongnmc;
		this.gongnurl = gongnurl;
		this.gongnlx = gongnlx;
		this.beiz = beiz;
		this.quanxwz = quanxwz;
		this.caidlx = caidlx;
	}

	// Property accessors

	public JicgncdId getId() {
		return this.id;
	}

	public void setId(JicgncdId id) {
		this.id = id;
	}

	public String getGongnmc() {
		return this.gongnmc;
	}

	public void setGongnmc(String gongnmc) {
		this.gongnmc = gongnmc;
	}

	public String getGongnurl() {
		return this.gongnurl;
	}

	public void setGongnurl(String gongnurl) {
		this.gongnurl = gongnurl;
	}

	public String getGongnlx() {
		return this.gongnlx;
	}

	public void setGongnlx(String gongnlx) {
		this.gongnlx = gongnlx;
	}

	public String getBeiz() {
		return this.beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}

	public String getQuanxwz() {
		return this.quanxwz;
	}

	public void setQuanxwz(String quanxwz) {
		this.quanxwz = quanxwz;
	}

	public String getCaidlx() {
		return this.caidlx;
	}

	public void setCaidlx(String caidlx) {
		this.caidlx = caidlx;
	}

}