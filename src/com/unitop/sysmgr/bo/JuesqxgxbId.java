package com.unitop.sysmgr.bo;

/**
 * JuesqxgxbId entity. @author MyEclipse Persistence Tools
 */

public class JuesqxgxbId implements java.io.Serializable {

	// Fields

	private String juesid;
	private String quanxid;

	// Constructors

	/** default constructor */
	public JuesqxgxbId() {
	}

	/** full constructor */
	public JuesqxgxbId(String juesid, String quanxid) {
		this.juesid = juesid;
		this.quanxid = quanxid;
	}

	// Property accessors

	public String getJuesid() {
		return this.juesid;
	}

	public void setJuesid(String juesid) {
		this.juesid = juesid;
	}

	public String getQuanxid() {
		return this.quanxid;
	}

	public void setQuanxid(String quanxid) {
		this.quanxid = quanxid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof JuesqxgxbId))
			return false;
		JuesqxgxbId castOther = (JuesqxgxbId) other;

		return ((this.getJuesid() == castOther.getJuesid()) || (this
				.getJuesid() != null
				&& castOther.getJuesid() != null && this.getJuesid().equals(
				castOther.getJuesid())))
				&& ((this.getQuanxid() == castOther.getQuanxid()) || (this
						.getQuanxid() != null
						&& castOther.getQuanxid() != null && this.getQuanxid()
						.equals(castOther.getQuanxid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getJuesid() == null ? 0 : this.getJuesid().hashCode());
		result = 37 * result
				+ (getQuanxid() == null ? 0 : this.getQuanxid().hashCode());
		return result;
	}

}