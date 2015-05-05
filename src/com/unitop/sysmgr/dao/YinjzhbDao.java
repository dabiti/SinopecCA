package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;

public interface YinjzhbDao   extends BaseDataResourcesInterface {
	
	//��ȡӡ�������Ϣ
	public List getYinjzh(String zhangh);
	//����ɾ��ӡ�������Ϣ
	public void delYinjzh(String zhangh);
	
	public List getYinjzhforMap(String zhangh) throws BusinessException ;
	
	
	public List<Map<String, String>> queryYjzhbListByZhangh(String zhangh) throws DAOException;
}
