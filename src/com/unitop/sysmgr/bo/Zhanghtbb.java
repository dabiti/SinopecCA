package com.unitop.sysmgr.bo;

/**
 * Tongbb entity. @author MyEclipse Persistence Tools
 */

public class Zhanghtbb implements java.io.Serializable {

	// Fields

	private String zhangh;//帐号
	private String caozlx;//操作类型
	private String exception;
	private String chuangjsj;//创建时间
	private String tongbsj;//同步结束时间
	private String result;
	private String str1;
	private String str2;
	private String str3;
	private String shenghjgh ;
	private String hum;

	
	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	/** default constructor */
	public Zhanghtbb() {
	}

	/** minimal constructor */
	public Zhanghtbb(String zhangh, String caozlx, String chuangjsj) {
		this.zhangh = zhangh;
		this.caozlx = caozlx;
		this.chuangjsj = chuangjsj;
		
	}

	/** full constructor */
	public Zhanghtbb(String zhangh, String caozlx, String exception, String tongbsj,
			String chuangjsj, String str1, String str2, String str3) {
		this.zhangh = zhangh;
		this.caozlx = caozlx;
		this.exception = exception;
		this.chuangjsj = chuangjsj;
		this.tongbsj = tongbsj;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
	}

	
	// Property accessors
	public String getZhangh() {
		return zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	public String getCaozlx() {
		return caozlx;
	}

	public void setCaozlx(String caozlx) {
		this.caozlx = caozlx;
	}

	
	public String getTongbsj() {
		return tongbsj;
	}

	public void setTongbsj(String tongbsj) {
		this.tongbsj = tongbsj;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getChuangjsj() {
		return chuangjsj;
	}

	public void setChuangjsj(String chuangjsj) {
		this.chuangjsj = chuangjsj;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getShenghjgh() {
		return shenghjgh;
	}

	public void setShenghjgh(String shenghjgh) {
		this.shenghjgh = shenghjgh;
	}


		

}