package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Pingzxtlxgxb;

public interface PingzxtlxgxbDao extends BaseDataResourcesInterface{
	
	//��ȡ��ǰ���ñ�ʶ
	public List getPingzxtlxgxbList();
	//��������ϵͳ���͹�ϵ
	public void saveOrUpdate(Pingzxtlxgxb pingzxtlxgxb);
	//��ȡ�ض�ƾ֤����ϵͳ��ϵ����
	public List getxitList(String jigh, String pingzh);
	//ɾ��ϵͳ���͹�ϵ����
	public void deletexitList(String jigh, String pingzh);
}
