package com.unitop.framework.util;

import org.apache.commons.dbcp.BasicDataSource;
public class DataSourceSetTool extends BasicDataSource {

	 public synchronized void setPassword(String password) {
		super.setPassword(PasswordUtil.decodePwd(password));
	}
	  public synchronized void setUsername(String username) {
		super.setUsername(PasswordUtil.decodePwd(username));
	}
	public synchronized void setUrl(String url) {
		super.setUrl(PasswordUtil.decodePwd(url));
	}
	
}
