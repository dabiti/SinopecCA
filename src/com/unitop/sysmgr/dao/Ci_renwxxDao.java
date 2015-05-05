package com.unitop.sysmgr.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.unitop.sysmgr.bo.Ci_renwxx;
import com.unitop.sysmgr.bo.Zhengprz;

/*
 */
public interface Ci_renwxxDao   extends BaseDataResourcesInterface{
	// ����������Ϣ
	public Ci_renwxx getRenwxx(String renwbs);

	// ��������ѯ��������
	public ArrayList<Ci_renwxx> getRenwxxList(String batchid ,String chulzt) ;
	
	//��������ѯ����
	public int getCountFromList(String batchid ,String chulzt) ;
	
	// ���½��
	public boolean updateForAccount(Ci_renwxx taskid1,Zhengprz taskid2,String clerkid1,String clerkid2);
	
	//�������������ѯ����
	public Ci_renwxx getRenwxxById(String systemid,String renwbs);
	
	// ����������Ϣ��־��Ӹ��˹�Ա
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2);
}
