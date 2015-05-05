package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class YewgzForm  extends ActionForm{
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
	public YewgzForm(ActionMapping arg0, HttpServletRequest arg1) {
		this.yuansid = null;
		this.yuansname = null;
		this.maxlength = null;
		this.yuansstyle = null;
		this.styleClass = null;
		this.isreadonly = null;
		this.validaterule = null;
		this.msgid = null;
		this.remark = null;
	}
	
	public void clear() {
		this.yuansid = "";
		this.yuansname = "";
		this.maxlength = "";
		this.yuansstyle = "";
		this.styleClass = "";
		this.isreadonly = "";
		this.validaterule = "";
		this.msgid = "";
		this.remark = "";
	}
	
	public YewgzForm(String yuansid, String yuansname, String maxlength,
			String yuansstyle, String styleClass, String isreadonly,
			String validaterule, String msgid, String remark) {
		super();
		this.yuansid = yuansid;
		this.yuansname = yuansname;
		this.maxlength = maxlength;
		this.yuansstyle = yuansstyle;
		this.styleClass = styleClass;
		this.isreadonly = isreadonly;
		this.validaterule = validaterule;
		this.msgid = msgid;
		this.remark = remark;
	}
	public YewgzForm() {
	}
	
}
