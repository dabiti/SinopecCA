package com.unitop.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Privilege implements Serializable{

	private static final long serialVersionUID = 6696762555039015714L;

	private String name;

	private String value;
	
	private String type;
	
	private List zhignList = new ArrayList();

	public List getZhignList() {
		return zhignList;
	}

	public void addZhignList(Privilege privilege) {
		zhignList.add(privilege);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if(value==null)value="0";
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
