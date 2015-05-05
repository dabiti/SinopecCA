package com.unitop.sysmgr.bo;

/**
 * GuiyjsgxbId entity. @author MyEclipse Persistence Tools
 */

public class GuiyjsgxbId implements java.io.Serializable {

	// Fields

	private String guiyid;
	private String juesid;
	private String juesmc;

	// Constructors

	public String getJuesmc() {
		return juesmc;
	}

	public void setJuesmc(String juesmc) {
		this.juesmc = juesmc;
	}

	/** default constructor */
	public GuiyjsgxbId() {
	}

	/** full constructor */
	public GuiyjsgxbId(String guiyid, String juesid) {
		this.guiyid = guiyid;
		this.juesid = juesid;
	}

	// Property accessors

	public String getGuiyid() {
		return this.guiyid;
	}

	public void setGuiyid(String guiyid) {
		this.guiyid = guiyid;
	}

	public String getJuesid() {
		return this.juesid;
	}

	public void setJuesid(String juesid) {
		this.juesid = juesid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GuiyjsgxbId))
			return false;
		GuiyjsgxbId castOther = (GuiyjsgxbId) other;

		return ((this.getGuiyid() == castOther.getGuiyid()) || (this
				.getGuiyid() != null
				&& castOther.getGuiyid() != null && this.getGuiyid().equals(
				castOther.getGuiyid())))
				&& ((this.getJuesid() == castOther.getJuesid()) || (this
						.getJuesid() != null
						&& castOther.getJuesid() != null && this.getJuesid()
						.equals(castOther.getJuesid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGuiyid() == null ? 0 : this.getGuiyid().hashCode());
		result = 37 * result
				+ (getJuesid() == null ? 0 : this.getJuesid().hashCode());
		return result;
	}

}