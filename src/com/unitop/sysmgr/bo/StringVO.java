package com.unitop.sysmgr.bo;

public class StringVO {
	private String value;

	public StringVO() {
		// TODO Auto-generated constructor stub
	}
	public StringVO(byte[]buf) {
		if(buf!=null)
		this.value=new String(buf);
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
