package com.unitop.sysmgr.dao;

import com.unitop.sysmgr.bo.Jicgncd;

public interface BasicFunctionDao {
	
	//�������ܱ���
	public void insert(Jicgncd jicg);
	//��ѯ
	public Jicgncd select(Jicgncd jicg);
	//��֤Ȩ��
	public Jicgncd getPost(Jicgncd jicg);
	
}
