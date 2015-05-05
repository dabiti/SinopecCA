package com.unitop.sysmgr.form;

public class TisxxForm extends AccountinfoForm {
	private String msgId;
	private String msgContent;
	private String functionArea;
	private String functionPoint;
	private String remark;
	
	public String getFunctionArea() {
		return functionArea;
	}
	
	public void setFunctionArea(String functionArea) {
		this.functionArea = functionArea;
	}
	
	public String getFunctionPoint() {
		return functionPoint;
	}
	
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}


	public TisxxForm(String msgId, String msgContent, String functionArea,
			String functionPoint, String remark) {
		this.msgId = msgId;
		this.msgContent = msgContent;
		this.functionArea = functionArea;
		this.functionPoint = functionPoint;
		this.remark = remark;
	}

	public TisxxForm() {
	}
	public void clear(){
		this.msgId = "";
		this.msgContent = "";
		this.functionArea = "";
		this.functionPoint ="";
		this.remark = "";
	}
}
