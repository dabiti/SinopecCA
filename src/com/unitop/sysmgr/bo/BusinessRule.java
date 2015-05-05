package com.unitop.sysmgr.bo;

public class BusinessRule {
	private String yuansId;
	private String yuansName;
	private String maxLength;
	private String yuansStyle;
	private String styleClass;
	private String isReadonly;
	private String validateRule;
	private String msgId;
	public String getYuansId() {
		return yuansId;
	}
	public void setYuansId(String yuansId) {
		if(yuansId == null)
		{
			yuansId="";
		} else{
			yuansId = yuansId.trim();
		}
		this.yuansId = yuansId;
	}
	public String getYuansName() {
		return yuansName;
	}
	public void setYuansName(String yuansName) {
		if(yuansName == null)
		{
			yuansName="";
		} else{
			yuansName = yuansName.trim();
		}
		this.yuansName = yuansName;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		if(maxLength == null)
		{
			maxLength="";
		} else{
			maxLength = maxLength.trim();
		}
		this.maxLength = maxLength;
	}
	public String getYuansStyle() {
		return yuansStyle;
	}
	public void setYuansStyle(String yuansStyle) {
		if(yuansStyle == null)
		{
			yuansStyle="";
		} else{
			yuansStyle = yuansStyle.trim();
		}
		this.yuansStyle = yuansStyle;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		if(styleClass == null)
		{
			styleClass="";
		} else{
			styleClass = styleClass.trim();
		}
		this.styleClass = styleClass;
	}
	public String getIsReadonly() {
		return isReadonly;
	}
	public void setIsReadonly(String isReadonly) {
		if(isReadonly == null)
		{
			isReadonly="";
		} else{
			isReadonly = isReadonly.trim();
		}
		this.isReadonly = isReadonly;
	}
	public String getValidateRule() {
		return validateRule;
	}
	public void setValidateRule(String validateRule) {
		if(validateRule == null)
		{
			validateRule="";
		} else{
			validateRule = validateRule.trim();
		}
		this.validateRule = validateRule;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		if(msgId == null)
		{
			msgId="";
		} else{
			msgId = msgId.trim();
		}
		this.msgId = msgId;
	}

}
