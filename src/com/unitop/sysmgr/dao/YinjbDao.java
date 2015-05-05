package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Yinjb;

public interface YinjbDao   extends BaseDataResourcesInterface {

	//获取印鉴
	public List getYinj (String zhangh);
	//物理删除帐号印鉴
	public void delYinj(String zhangh);
	

	//获得印鉴MapList
	public List getYinjforMap(String zhangh) throws BusinessException;
	
	//获得印鉴MapList（总分同步）
	public List getYinjMapforTongb(String zhangh) throws BusinessException ;
	
	//获得最新已审印鉴
	public List getLastYSyj(String zhangh) throws BusinessException;
	
	//获得最新已审组合
	public List getLastYSzh(String zhangh) throws BusinessException ;
	
	public Yinjb getYinj(String zhangh, String yinjbh, String qiyrq);
	public List<Map<String, String>> queryYjbListByZhangh(String zhangh)throws DAOException;
}
