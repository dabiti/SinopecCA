package com.unitop.sysmgr.bo;

/**
 * Danbwhgxb entity. @author MyEclipse Persistence Tools
 */

public class Danbwhgxb implements java.io.Serializable {

	private String id;
	private String zhubbh;
	private String zibbh;
	private String zhubzd;
	private String zibzd;
	private String shifljzd;
	private String zhibwhmc;
	
	public String getZhibwhmc() {
		return zhibwhmc;
	}

	public void setZhibwhmc(String zhibwhmc) {
		this.zhibwhmc = zhibwhmc;
	}

	public String getShifljzd() {
		return shifljzd;
	}

	public void setShifljzd(String shifljzd) {
		this.shifljzd = shifljzd;
	}

	// Constructors

	/** default constructor */
	public Danbwhgxb() {
	}

	/** minimal constructor */
	public Danbwhgxb(String id) {
		this.id = id;
	}

	/** full constructor */
	public Danbwhgxb(String id, String zhubbh, String zibbh, String zhubzd,
			String zibzd) {
		this.id = id;
		this.zhubbh = zhubbh;
		this.zibbh = zibbh;
		this.zhubzd = zhubzd;
		this.zibzd = zibzd;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZhubbh() {
		return this.zhubbh;
	}

	public void setZhubbh(String zhubbh) {
		this.zhubbh = zhubbh;
	}

	public String getZibbh() {
		return this.zibbh;
	}

	public void setZibbh(String zibbh) {
		this.zibbh = zibbh;
	}

	public String getZhubzd() {
		return this.zhubzd;
	}

	public void setZhubzd(String zhubzd) {
		this.zhubzd = zhubzd;
	}

	public String getZibzd() {
		return this.zibzd;
	}

	public void setZibzd(String zibzd) {
		this.zibzd = zibzd;
	}

}