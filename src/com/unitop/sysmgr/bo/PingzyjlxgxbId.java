package com.unitop.sysmgr.bo;

/**
 * PingzyjlxgxbId entity. @author MyEclipse Persistence Tools
 */

public class PingzyjlxgxbId implements java.io.Serializable {

	// Fields

	private String pingzbs;
	private String yinjlx;
	private String jigh;

	// Constructors

	/** default constructor */
	public PingzyjlxgxbId() {
	}

	/** full constructor */
	public PingzyjlxgxbId(String pingzbs, String yinjlx, String jigh) {
		this.pingzbs = pingzbs;
		this.yinjlx = yinjlx;
		this.jigh = jigh;
	}

	// Property accessors

	public String getPingzbs() {
		return this.pingzbs;
	}

	public void setPingzbs(String pingzbs) {
		this.pingzbs = pingzbs;
	}

	public String getYinjlx() {
		return this.yinjlx;
	}

	public void setYinjlx(String yinjlx) {
		this.yinjlx = yinjlx;
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
		if (!(other instanceof PingzyjlxgxbId))
			return false;
		PingzyjlxgxbId castOther = (PingzyjlxgxbId) other;

		return ((this.getPingzbs() == castOther.getPingzbs()) || (this
				.getPingzbs() != null
				&& castOther.getPingzbs() != null && this.getPingzbs().equals(
				castOther.getPingzbs())))
				&& ((this.getYinjlx() == castOther.getYinjlx()) || (this
						.getYinjlx() != null
						&& castOther.getYinjlx() != null && this.getYinjlx()
						.equals(castOther.getYinjlx())))
				&& ((this.getJigh() == castOther.getJigh()) || (this.getJigh() != null
						&& castOther.getJigh() != null && this.getJigh()
						.equals(castOther.getJigh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPingzbs() == null ? 0 : this.getPingzbs().hashCode());
		result = 37 * result
				+ (getYinjlx() == null ? 0 : this.getYinjlx().hashCode());
		result = 37 * result
				+ (getJigh() == null ? 0 : this.getJigh().hashCode());
		return result;
	}

}