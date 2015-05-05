package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Pingzxtlxgxb;
import com.unitop.sysmgr.bo.Pingzyjlxgxb;

/*
 * 印鉴类型访问接口
 */

public interface PingzyjlxgxbDao extends BaseDataResourcesInterface{
	public List getPingzyjlxgxbList();
	//保存或更新系统类型关系
	public void saveOrUpdate(Pingzyjlxgxb pingzyjlxgxb);
	//获取特定印鉴类型系统关系数据
	public List getyinjList(String jigh, String pingzbh);
	//删除印鉴类型关系数据
	public void deleteyjList(String jigh, String pingzh);
	
}
