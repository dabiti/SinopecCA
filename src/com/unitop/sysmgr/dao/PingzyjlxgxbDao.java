package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Pingzxtlxgxb;
import com.unitop.sysmgr.bo.Pingzyjlxgxb;

/*
 * ӡ�����ͷ��ʽӿ�
 */

public interface PingzyjlxgxbDao extends BaseDataResourcesInterface{
	public List getPingzyjlxgxbList();
	//��������ϵͳ���͹�ϵ
	public void saveOrUpdate(Pingzyjlxgxb pingzyjlxgxb);
	//��ȡ�ض�ӡ������ϵͳ��ϵ����
	public List getyinjList(String jigh, String pingzbh);
	//ɾ��ӡ�����͹�ϵ����
	public void deleteyjList(String jigh, String pingzh);
	
}
