package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Zhanghtbb;

/*
 * �ʺű����
 */
public interface ZhanghtbbDao   extends BaseDataResourcesInterface{
	public Zhanghtbb getZhanghtbb(String zhangh);
	public void updateZhanghtbb(Zhanghtbb zhangh);
	//ɾ��ͬ���ɹ����˺�
	public int delZhanghtbbforZh(String zhangh);
	//������ͬ���ɹ���ͬ����¼
	public int delZhanghtbb();
	//������ͬ���ɹ���ͬ����¼
	public int delZhanghtbbforStr(String ZhanghtbbStr);
	public int updateException(String zhangh,String exception);
	
	//��ѯ����������ͬ���˻���¼
	public List<Zhanghtbb> queryZhanghtbbList(String zhangh);
	
	
	public List<Map<String, String>> queryTongbbListForNight()throws DAOException;
	public int updateZhanghtbbForJighForNight(Zhanghtbb zhanghtbb)throws DAOException;
	public int updateZhanghtbbForNight(Zhanghtbb zhanghtbb)throws DAOException;
}
