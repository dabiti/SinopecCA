package com.unitop.sysmgr.form;


import org.apache.struts.action.ActionForm;

public class SystemControlForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String type;
	private String value;
	
	public SystemControlForm() {
		this.id = null;
		this.name = null;
		this.type = null;
		this.value = null;
	}
	public void clear(){
			this.id = null;
			this.name = null;
			this.type = null;
			this.value = null;
	}
	public SystemControlForm(String id, String name, String type, String value) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
