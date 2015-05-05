package com.unitop.bean;

public class ParsedSql { 
	private String sql;

	private String newSql;

	private String[] parameterNames;
	public String getNewSql() {
		return newSql;
	}

	public void setNewSql(String newSql) {
		this.newSql = newSql;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(String[] parameterNames) {
		this.parameterNames = parameterNames;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public ParsedSql(String sql) {
		this.sql = sql;
	}
}
