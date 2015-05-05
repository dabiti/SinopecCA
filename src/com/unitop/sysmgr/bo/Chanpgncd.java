package com.unitop.sysmgr.bo;

import java.math.BigDecimal;

/**
 * Chanpgncd entity. @author MyEclipse Persistence Tools
 */

public class Chanpgncd implements java.io.Serializable {

	// Fields

	private ChanpgncdId id;
	private String gongnmc;
	private String gongnurl;
	private BigDecimal gongnsx;
	private String shangjgn;
	private String quanxwz;
	private String zhuangt;
	private String beiz;
	private String gongnlx;
	private String classid;
	
	public void setClassid(String classid) {
		this.classid = classid;
	}
	
	public String getClassid() {
		return classid;
	}

	// Constructors

	/** default constructor */
	public Chanpgncd() {
	}

	/** minimal constructor */
	public Chanpgncd(ChanpgncdId id) {
		this.id = id;
	}

	/** full constructor */
	public Chanpgncd(ChanpgncdId id, String gongnmc, String gongnurl,
			BigDecimal gongnsx, String shangjgn, String quanxwz,
			String zhuangt, String beiz, String gongnlx) {
		this.id = id;
		this.gongnmc = gongnmc;
		this.gongnurl = gongnurl;
		this.gongnsx = gongnsx;
		this.shangjgn = shangjgn;
		this.quanxwz = quanxwz;
		this.zhuangt = zhuangt;
		this.beiz = beiz;
		this.gongnlx = gongnlx;
	}

	// Property accessors

	public ChanpgncdId getId() {
		return this.id;
	}

	public void setId(ChanpgncdId id) {
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

	public BigDecimal getGongnsx() {
		return this.gongnsx;
	}

	public void setGongnsx(BigDecimal gongnsx) {
		this.gongnsx = gongnsx;
	}

	public String getShangjgn() {
		return this.shangjgn;
	}

	public void setShangjgn(String shangjgn) {
		this.shangjgn = shangjgn;
	}

	public String getQuanxwz() {
		return this.quanxwz;
	}

	public void setQuanxwz(String quanxwz) {
		this.quanxwz = quanxwz;
	}

	public String getZhuangt() {
		return this.zhuangt;
	}

	public void setZhuangt(String zhuangt) {
		this.zhuangt = zhuangt;
	}

	public String getBeiz() {
		return this.beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}

	public String getGongnlx() {
		return this.gongnlx;
	}

	public void setGongnlx(String gongnlx) {
		this.gongnlx = gongnlx;
	}

}