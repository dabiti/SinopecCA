package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.JiejrBo;

/*
 * �ڼ��չ��� ʵ�ֽӿ�
 */
public interface JiejrService {
	//���¡�����ڼ��չ���
	public void updateOrSaveJiejr(JiejrBo JiejrBo);
	//��ȡ�ڼ��չ��� 
	public JiejrBo getJiejr(String year);
	//ɾ���ڼ��չ��� 
	public void deletJiejr(String year);
}
