package com.unitop.sysmgr.dao;

import java.util.Map;

public interface AutoCompleteDao {
	
	//ִ��SQL��䷵�� XXX,YYY�ַ�����Ϣ
	public String  autoCompleteForAccount(String sql,Map paramMap); 
	
	//���ر��ֶ���Ϣ
	public Map  getTableLineMap(String sql); 
}
