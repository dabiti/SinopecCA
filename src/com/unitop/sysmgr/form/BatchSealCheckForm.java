package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class BatchSealCheckForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String batchid;
	
	private String systemid;
	
	//批量任务号
	private String taskid1;
	
	//手工任务号
	private String taskid2;

	//经办员
	private String clerkid1;
	
	//复核员
	private String clerkid2;
	
	//验印日志类型：1、前台，2、后台
	private String tasktype;
	
	//账号
	private String account;
	
	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getTaskid1() {
		return taskid1;
	}

	public void setTaskid1(String taskid1) {
		this.taskid1 = taskid1;
	}

	public String getTaskid2() {
		return taskid2;
	}

	public void setTaskid2(String taskid2) {
		this.taskid2 = taskid2;
	}

	public String getClerkid1() {
		return clerkid1;
	}

	public void setClerkid1(String clerkid1) {
		this.clerkid1 = clerkid1;
	}

	public String getClerkid2() {
		return clerkid2;
	}

	public void setClerkid2(String clerkid2) {
		this.clerkid2 = clerkid2;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	
	
}
