package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;

public interface YinjzhbDao   extends BaseDataResourcesInterface {
	
	//获取印鉴组合信息
	public List getYinjzh(String zhangh);
	//物理删除印鉴组合信息
	public void delYinjzh(String zhangh);
	
	public List getYinjzhforMap(String zhangh) throws BusinessException ;
	
	
	public List<Map<String, String>> queryYjzhbListByZhangh(String zhangh) throws DAOException;
}
