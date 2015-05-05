package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class Yewgz implements Serializable{

	private static final long serialVersionUID = -3928301675132555425L;
	private String yuansid;
	private String yuansname;
	private String maxlength;
	private String yuansstyle;
	private String styleClass;
	private String isreadonly;
	private String validaterule;
	private String msgid;
	private String remark;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getYuansid() {
		return yuansid;
	}
	public void setYuansid(String yuansid) {
		this.yuansid = yuansid;
	}
	public String getYuansname() {
		return yuansname;
	}
	public void setYuansname(String yuansname) {
		this.yuansname = yuansname;
	}
	public String getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	public String getYuansstyle() {
		return yuansstyle;
	}
	public void setYuansstyle(String yuansstyle) {
		this.yuansstyle = yuansstyle;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getIsreadonly() {
		return isreadonly;
	}
	public void setIsreadonly(String isreadonly) {
		this.isreadonly = isreadonly;
	}
	public String getValidaterule() {
		return validaterule;
	}
	public void setValidaterule(String validaterule) {
		this.validaterule = validaterule;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
}
