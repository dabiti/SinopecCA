package com.unitop.sysmgr.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.unitop.sysmgr.bo.Zhengprz;

/*
 * ��ӡ��־����
 */
public interface YanyinLogDao {
	//��Ʊ��ӡ��� ��֤Ʊ�ݺ��Ƿ����
	public int validateZhengppjh(String piaojh);
	//������ӡ��� ��֤Ʊ�ݺ��Ƿ����
	public int validateDanzppjh(String piaojh);

	//������Ʊ ���
	public Zhengprz getZhengp(String id) ;
	
	// ����������Ϣ��־��Ӹ��˹�Ա
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2);
	
	// ������Ʊ��־��Ӹ��˹�Ա
	public boolean addClerk2OfZhengprz(String taskid,String clerkid);
}
