package com.unitop.sysmgr.bo;

/**
 * JicgncdId entity. @author MyEclipse Persistence Tools
 */

public class JicgncdId implements java.io.Serializable {

	// Fields

	private String gongnid;
	private String zignid;

	// Constructors

	/** default constructor */
	public JicgncdId() {
	}

	/** full constructor */
	public JicgncdId(String gongnid, String zignid) {
		this.gongnid = gongnid;
		this.zignid = zignid;
	}

	// Property accessors

	public String getGongnid() {
		return this.gongnid;
	}

	public void setGongnid(String gongnid) {
		this.gongnid = gongnid;
	}

	public String getZignid() {
		return this.zignid;
	}

	public void setZignid(String zignid) {
		this.zignid = zignid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof JicgncdId))
			return false;
		JicgncdId castOther = (JicgncdId) other;

		return ((this.getGongnid() == castOther.getGongnid()) || (this
				.getGongnid() != null
				&& castOther.getGongnid() != null && this.getGongnid().equals(
				castOther.getGongnid())))
				&& ((this.getZignid() == castOther.getZignid()) || (this
						.getZignid() != null
						&& castOther.getZignid() != null && this.getZignid()
						.equals(castOther.getZignid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGongnid() == null ? 0 : this.getGongnid().hashCode());
		result = 37 * result
				+ (getZignid() == null ? 0 : this.getZignid().hashCode());
		return result;
	}

}