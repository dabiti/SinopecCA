package com.unitop.sysmgr.service;

public interface AutoCompleteService {
	
	//模糊查询帐号 生成对应字符串格式
	public String  autoCompleteForZhangh(String account); 
	//获取表字段信息
	public String getTableLineMap(String tableName);
	//获取表字段信息
	public String getZhidMC(String zhidId);
}
