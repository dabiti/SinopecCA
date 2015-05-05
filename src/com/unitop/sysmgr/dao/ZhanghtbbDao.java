package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Zhanghtbb;

/*
 * 帐号表操作
 */
public interface ZhanghtbbDao   extends BaseDataResourcesInterface{
	public Zhanghtbb getZhanghtbb(String zhangh);
	public void updateZhanghtbb(Zhanghtbb zhangh);
	//删除同步成功的账号
	public int delZhanghtbbforZh(String zhangh);
	//清理已同步成功的同步记录
	public int delZhanghtbb();
	//清理已同步成功的同步记录
	public int delZhanghtbbforStr(String ZhanghtbbStr);
	public int updateException(String zhangh,String exception);
	
	//查询复合条件的同步账户记录
	public List<Zhanghtbb> queryZhanghtbbList(String zhangh);
	
	
	public List<Map<String, String>> queryTongbbListForNight()throws DAOException;
	public int updateZhanghtbbForJighForNight(Zhanghtbb zhanghtbb)throws DAOException;
	public int updateZhanghtbbForNight(Zhanghtbb zhanghtbb)throws DAOException;
}
