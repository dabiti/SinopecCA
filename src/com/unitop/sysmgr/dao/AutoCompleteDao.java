package com.unitop.sysmgr.dao;

import java.util.Map;

public interface AutoCompleteDao {
	
	//执行SQL语句返回 XXX,YYY字符串信息
	public String  autoCompleteForAccount(String sql,Map paramMap); 
	
	//返回表字段信息
	public Map  getTableLineMap(String sql); 
}
