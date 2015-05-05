package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class ClerkPasswordHistory implements Serializable {

	private static final long serialVersionUID = 2488142186056679748L;

	private String id;

	private String clerkCode;

	private String password;

	private String updateDate;
	

	public String getClerkCode() {
		return clerkCode;
	}

	public void setClerkCode(String clerkCode) {
		this.clerkCode = clerkCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}


}
