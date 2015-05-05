package com.unitop.sysmgr.form;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CustomForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> map = new HashMap<String, String>();
	
	public void setMap(Map<String, String> value) {
		this.map = value;
	}

	public Map<String, String> getMap() {
		return this.map;
	}

	public void setFormvalue(String key, String value) {
		map.put(key, value);
	}

	public String getFormvalue(String key) {
		return map.get(key);
	}
	
	public void reset(ActionMapping arg0, HttpServletRequest arg1){
		super.reset(arg0, arg1);
		this.map.clear();
	}
}
