package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.Jicgncd;

public interface BasicFunctionServcie {

	//�������ܱ���
	public void insert(Jicgncd jicg);
	//��ѯ
	public Jicgncd select(Jicgncd jicg);
	//��֤Ȩ��
	public Jicgncd getPost(Jicgncd jicg);
	
}
