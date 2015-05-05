package com.unitop.sysmgr.bo;

public class Privilege {

	private String id = "";

	private String name = "";

	private String pId = "";

	private String quanxms = "";

	private String beiz = "";
	
	private boolean checked = false;

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Privilege() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param pId
	 * @param quanxms
	 * @param beiz
	 */
	public Privilege(String id, String name, String pId, String quanxms,
			String beiz) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.quanxms = quanxms;
		this.beiz = beiz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	/**
	 * @return the quanxms
	 */
	public String getQuanxms() {
		return quanxms;
	}

	/**
	 * @param quanxms
	 *            the quanxms to set
	 */
	public void setQuanxms(String quanxms) {
		this.quanxms = quanxms;
	}

	/**
	 * @return the beiz
	 */
	public String getBeiz() {
		return beiz;
	}

	/**
	 * @param beiz
	 *            the beiz to set
	 */
	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}

}
