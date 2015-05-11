package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.AccountManageLog;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.SystemManageLog;
public interface SystemMgrService {

	public void createSystemManageLog(SystemManageLog log);
	public void createAccountmanagelog(AccountManageLog bo);
	public void createAccountmanagelogNew(AccountManageLog bo);
	public boolean CanOperDesOrg(String OperOrg, String DesOrg);
	public boolean CanOperDesSpecialOrg(String OperOrg, String DesOrg);
	
	//��ַ����ֱ��жϹ�Ա�Ƿ��ܲ�����������Ա���˻�
	public CanOperAccReturn ProCanOperOrg(String tellerorg,String operorg);
	public CanOperAccReturn ProCanOperAcc(String tellerorg,String operaccount);
	public CanOperAccReturn ProCanOperTel(String tellerorg,String clerknum);
	public boolean CanTongd(String cleaknum,String zhangh);
	
	public String getSystetemNowDate();
	public String getSystetemNextDate();
	public String getSystemContolParameter(String id) throws Exception;
	public String getSystemSequence();
	
	/**
	 * @author wcl
	 * @param tellerorg ��ԱID
	 * @param operaccount ������ӡ����
	 */
//	public CanOperAccReturn ProCanOperYinjk(String tellerorg,String operyinjk);
	
	//��ȡ��һ��ƾ֤��
	public String getPZHSequence();
	public String getSystetemYestoDay();
	
	/*
	 * ��ʯ��Ȩ��У��
	 */
	public boolean CanOperSite(String OperTer
			, String DesTer);
}
