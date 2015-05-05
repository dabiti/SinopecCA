package com.unitop.sysmgr.service;


import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhengprz;

public interface YanyinLogService {
	//验证整票票据号是否存在
	public boolean validatePjhForZhengp(String piaojh);
	//验证整票票据号是否存在
	public boolean validatePjhForDanz(String piaojh);
	//获取当然验印日志
	public TabsBo getTodayDanzrzList(String account, String checknum);
	//获取机构下某日整票验印日志
	public TabsBo getOrgZhengprzList(String orgCode,String dateString);
	//获得整票 
	public Zhengprz getZhengp(String id);
	// 更新整票日志添加复核柜员
	public boolean addClerk2OfZhengprz(String taskid,String clerkid);
}
