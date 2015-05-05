package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Pingzxtlxgxb;

public interface PingzxtlxgxbDao extends BaseDataResourcesInterface{
	
	//获取当前配置标识
	public List getPingzxtlxgxbList();
	//保存或更新系统类型关系
	public void saveOrUpdate(Pingzxtlxgxb pingzxtlxgxb);
	//获取特定凭证类型系统关系数据
	public List getxitList(String jigh, String pingzh);
	//删除系统类型关系数据
	public void deletexitList(String jigh, String pingzh);
}
