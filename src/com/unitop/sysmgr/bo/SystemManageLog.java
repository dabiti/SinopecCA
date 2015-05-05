package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class SystemManageLog implements Serializable {

	private static final long serialVersionUID = -6442462651699544264L;

	private String id;

	private String admincode;

	private String content;

	private String operdate;
	
	private String ip;


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAdmincode() {
		return admincode;
	}

	public void setAdmincode(String admincode) {
		this.admincode = admincode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperdate() {
		return operdate;
	}

	public void setOperdate(String operdate) {
		this.operdate = operdate;
	}
}
