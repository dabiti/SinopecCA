package com.unitop.bean;

import java.util.Map;

public class ReturnMessage {
	
	private String errorMsg = "";
	
	private Map<String, Object> returnMap;

	public String getErrorMsg() {
		return errorMsg;
	}

	public Map<String, Object> getReturnMap() {
		return returnMap;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}

	public ReturnMessage() {
		super();
	}

}
