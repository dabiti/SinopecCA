package com.unitop.sysmgr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhanghxzb;

/*
 * �ʺű����
 */
public interface ZhanghbDao   extends BaseDataResourcesInterface{
	public Zhanghb getZhanghb(String zhangh);
	public void updateZhanghb(Zhanghb zhangh);
	public void deleteZhanghb(Zhanghb zhangh);
	public List getKehh(String keh,String jigh);
	public void updateForAccount(String orgCode, String tctd);
	public Zhanghb getZhanghbByYinjkbh( String yinjkbh);

	public String getInternalReleaseZhangh(String rule);

	
	//����˻�����ϢMap
	public List<Map<String,String>> getZhanghbforMap(String zhangh) throws BusinessException ;
	
	//�����˺Ż����Ż�ȡ��ʡ�л�����
	public String getShenghjgh(String jigh);
	
	
	//ͨ��ͨ�ұ�־ת��BS��CS
	public Map<String,String> attrConvert(Map<String, String> zhanghbMap);
	
	public List<Zhanghb> getZhanghbByZzh(String zhangh);
	public String getYinjkh(String account);

	
	// ��ѯ�ʺ�������Ϣ
	public ArrayList<Zhanghxzb> getZhanghxzList();
	public int countTodoZhanghbList(String jigh);
	public List<Zhanghb> getToDoZhanghbList(String jigh);
	public String getJiankgy(String account);
	
	
	
	public Map<String, String> queryZhbByZhangh(String zhangh)throws DAOException;
//	public Map<String, String> tongctdConvert(Map<String, String> zhanghsj)throws DAOException;
	public Map<String, String> queryOrganarchivesByJigh(String string)throws DAOException;
	public String getSocketaddByJigh(String jigh)throws DAOException;
	public String getZhanghFromShort(String zhangh);
	public String getZhanghFromOldAccount(String n);
	public void saveOrUpdateForZhanghTemp(Zhanghb zhanghb);
	
	public String getOldAccount(String zhangh);
}
