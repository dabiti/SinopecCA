package com.unitop.sysmgr.service;


import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhengprz;

public interface YanyinLogService {
	//��֤��ƱƱ�ݺ��Ƿ����
	public boolean validatePjhForZhengp(String piaojh);
	//��֤��ƱƱ�ݺ��Ƿ����
	public boolean validatePjhForDanz(String piaojh);
	//��ȡ��Ȼ��ӡ��־
	public TabsBo getTodayDanzrzList(String account, String checknum);
	//��ȡ������ĳ����Ʊ��ӡ��־
	public TabsBo getOrgZhengprzList(String orgCode,String dateString);
	//�����Ʊ 
	public Zhengprz getZhengp(String id);
	// ������Ʊ��־��Ӹ��˹�Ա
	public boolean addClerk2OfZhengprz(String taskid,String clerkid);
}
