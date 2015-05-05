package com.unitop.sysmgr.bo;

/**
 * PingzxtlxgxbId entity. @author MyEclipse Persistence Tools
 */

public class PingzxtlxgxbId implements java.io.Serializable {

	// Fields

	private String pingzbs;
	private String xitlx;
	private String jigh;

	// Constructors

	/** default constructor */
	public PingzxtlxgxbId() {
	}

	/** full constructor */
	public PingzxtlxgxbId(String pingzbs, String xitlx, String jigh) {
		this.pingzbs = pingzbs;
		this.xitlx = xitlx;
		this.jigh = jigh;
	}

	// Property accessors

	public String getPingzbs() {
		return this.pingzbs;
	}

	public void setPingzbs(String pingzbs) {
		this.pingzbs = pingzbs;
	}

	public String getXitlx() {
		return this.xitlx;
	}

	public void setXitlx(String xitlx) {
		this.xitlx = xitlx;
	}

	public String getJigh() {
		return this.jigh;
	}

	public void setJigh(String jigh) {
		this.jigh = jigh;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PingzxtlxgxbId))
			return false;
		PingzxtlxgxbId castOther = (PingzxtlxgxbId) other;

		return ((this.getPingzbs() == castOther.getPingzbs()) || (this
				.getPingzbs() != null
				&& castOther.getPingzbs() != null && this.getPingzbs().equals(
				castOther.getPingzbs())))
				&& ((this.getXitlx() == castOther.getXitlx()) || (this
						.getXitlx() != null
						&& castOther.getXitlx() != null && this.getXitlx()
						.equals(castOther.getXitlx())))
				&& ((this.getJigh() == castOther.getJigh()) || (this.getJigh() != null
						&& castOther.getJigh() != null && this.getJigh()
						.equals(castOther.getJigh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPingzbs() == null ? 0 : this.getPingzbs().hashCode());
		result = 37 * result
				+ (getXitlx() == null ? 0 : this.getXitlx().hashCode());
		result = 37 * result
				+ (getJigh() == null ? 0 : this.getJigh().hashCode());
		return result;
	}

}