package com.unitop.sysmgr.service;

public interface AutoCompleteService {
	
	//ģ����ѯ�ʺ� ���ɶ�Ӧ�ַ�����ʽ
	public String  autoCompleteForZhangh(String account); 
	//��ȡ���ֶ���Ϣ
	public String getTableLineMap(String tableName);
	//��ȡ���ֶ���Ϣ
	public String getZhidMC(String zhidId);
}
