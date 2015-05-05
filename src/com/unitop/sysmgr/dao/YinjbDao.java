package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Yinjb;

public interface YinjbDao   extends BaseDataResourcesInterface {

	//��ȡӡ��
	public List getYinj (String zhangh);
	//����ɾ���ʺ�ӡ��
	public void delYinj(String zhangh);
	

	//���ӡ��MapList
	public List getYinjforMap(String zhangh) throws BusinessException;
	
	//���ӡ��MapList���ܷ�ͬ����
	public List getYinjMapforTongb(String zhangh) throws BusinessException ;
	
	//�����������ӡ��
	public List getLastYSyj(String zhangh) throws BusinessException;
	
	//��������������
	public List getLastYSzh(String zhangh) throws BusinessException ;
	
	public Yinjb getYinj(String zhangh, String yinjbh, String qiyrq);
	public List<Map<String, String>> queryYjbListByZhangh(String zhangh)throws DAOException;
}
