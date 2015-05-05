package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class BasepostForm extends ActionForm {

	private String code;
	

	private String name;

	private String postpopedom;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostpopedom() {
		return postpopedom;
	}

	public void setPostpopedom(String postpopedom) {
		this.postpopedom = postpopedom;
	}
}
