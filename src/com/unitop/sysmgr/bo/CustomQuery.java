package com.unitop.sysmgr.bo;

import java.io.Serializable;
import java.sql.Blob;

public class CustomQuery implements Serializable {

	private static final long serialVersionUID = 4736544437884774134L;

	private String renwbs;

	private String renwlx;

	private String jianlsj;

	private String chaxtj;

	private String chaxyj;

	private String chulzt;

	private String chulsj;

	private String xiazsj;

	private String jiegwjm;

	private Blob jiegwj;

	private String guiy;

	public String getChaxtj() {
		return chaxtj;
	}

	public void setChaxtj(String chaxtj) {
		this.chaxtj = chaxtj;
	}

	public String getChaxyj() {
		return chaxyj;
	}

	public void setChaxyj(String chaxyj) {
		this.chaxyj = chaxyj;
	}

	public String getChulsj() {
		return chulsj;
	}

	public void setChulsj(String chulsj) {
		this.chulsj = chulsj;
	}

	public String getChulzt() {
		return chulzt;
	}

	public void setChulzt(String chulzt) {
		this.chulzt = chulzt;
	}

	public String getGuiy() {
		return guiy;
	}

	public void setGuiy(String guiy) {
		this.guiy = guiy;
	}

	public String getJianlsj() {
		return jianlsj;
	}

	public void setJianlsj(String jianlsj) {
		this.jianlsj = jianlsj;
	}

	public Blob getJiegwj() {
		return jiegwj;
	}

	public void setJiegwj(Blob jiegwj) {
		this.jiegwj = jiegwj;
	}

	public String getJiegwjm() {
		return jiegwjm;
	}

	public void setJiegwjm(String jiegwjm) {
		this.jiegwjm = jiegwjm;
	}

	public String getRenwbs() {
		return renwbs;
	}

	public void setRenwbs(String renwbs) {
		this.renwbs = renwbs;
	}

	public String getRenwlx() {
		return renwlx;
	}

	public void setRenwlx(String renwlx) {
		this.renwlx = renwlx;
	}

	public String getXiaozsj() {
		return xiazsj;
	}

	public void setXiazsj(String xiaozsj) {
		this.xiazsj = xiaozsj;
	}
}
