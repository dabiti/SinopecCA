package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class NewOrg implements Serializable {

	private static final long serialVersionUID = 8784742255132483255L;

	private String orgid;

	private String orgname;

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

}
