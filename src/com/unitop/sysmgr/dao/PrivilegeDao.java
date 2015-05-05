package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Privilege;


public interface PrivilegeDao extends BaseDataResourcesInterface  {
	//��ȡȫ��Ȩ��
	public List getAllPrivilege();
	//��ȡȫ��Ȩ�� ��ɫ����
	public List getAllPrivilegeForJuesgl();
	
	//��ȡ��ɫ�µ�Ȩ��
	public List<Privilege> getPrivilegeByJuesid(String juesid);
	//������������
	public void saveJuesqxgxbList(List JuesqxgxbList);
	//ɾ����ɫ�µ�Ȩ��
	public void deleteJuesqxgxb(String  juesid);
}
