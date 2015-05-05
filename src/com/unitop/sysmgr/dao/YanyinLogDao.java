package com.unitop.sysmgr.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.unitop.sysmgr.bo.Zhengprz;

/*
 * 验印日志服务
 */
public interface YanyinLogDao {
	//整票验印结果 验证票据号是否存在
	public int validateZhengppjh(String piaojh);
	//单章验印结果 验证票据号是否存在
	public int validateDanzppjh(String piaojh);

	//返回整票 结果
	public Zhengprz getZhengp(String id) ;
	
	// 更新任务信息日志添加复核柜员
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2);
	
	// 更新整票日志添加复核柜员
	public boolean addClerk2OfZhengprz(String taskid,String clerkid);
}
