package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.Clerk;

/*
 * �����¼�ӿ�
 */
public interface SSOservice {
	/*
	 * �����¼��֤�ӿ�
	 * 
	 * �����������¼��֤�ӿڣ��˽ӿڶ�Ӧ��Ŀ�ж�Ӧ�з������¼ʵ��
	 */
	public boolean ValidateSSO(Clerk clerk);
	/*
	 * ��ȡ������Ա��Ϣ�ӿ�
	 * 
	 * ����������ָ��������ϵͳ���л�ȡ��Ա��Ϣ��
	 */
	public Clerk getClerkFromSSO(String clerknum) throws Exception;
	/*
	 * ͬ��������Ա��Ϣ�ӿ�
	 * 
	 * ����������ӵ�����ϵͳ��ȡ�Ĺ�Ա��Ϣͬ������Ա���С�
	 * 
	 */
	public void createOrUpdateClerkForSSO(Clerk clerk);	
}
