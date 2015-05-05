package com.unitop.sysmgr.service;

import java.util.ArrayList;
import java.util.List;

import com.unitop.sysmgr.bo.Ci_renwxx;
import com.unitop.sysmgr.bo.Ci_yyrz;
import com.unitop.sysmgr.bo.Zhengprz;

/*
 * ��ѯ��Ϣ��ѯ�ӿ�
 */
public interface BatchSealCheckService {
	
	// ����������Ϣ
	public Ci_renwxx getRenwxx(String renwbs);
	
	
	// ��������ѯ��������
	public ArrayList<Ci_renwxx> getRenwxxList(String batchid,String chulzt);
	
	
	//���½��
	public boolean updateCheckResult(Ci_renwxx taskid1,Zhengprz taskid2,String clerkid1,String clerkid2);
	
	
	public List<Ci_yyrz> getCi_yyrzList(String xitbs, String renwbs);
	
	
	public String createFinishResult(String batchid,String systemid,String result);
	
	
	//���������ӵ���
	public List<Ci_renwxx> addYyrzToRenwxx(List<Ci_renwxx> renwxxList);
	
	
	//���ݲ�ѯ���ת����xml
	public String convertCheckResultToXml(String systemid,String batchid,List<Ci_renwxx> renwxxList);


	public String getCountFromList(String batchid);
	
	
	//�������������ѯ����
	public Ci_renwxx getRenwxxById(String systemid,String renwbs);
	
	// ����������Ϣ��־��Ӹ��˹�Ա
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2);
}
