package com.unitop.sysmgr.dao;

import com.unitop.sysmgr.bo.JiejrBo;

/*
 * �ڼ��չ��� ���ݲ�����
 */
public interface JiejrglDao extends BaseDataResourcesInterface{
	
	/*
	 * �������(ID) ��ȡ����
	 */
	public JiejrBo getJiejrBo(String year);
	/*
	 * ��������
	 */
	public void saveJiejrBo(JiejrBo jiejrBo);
	/*
	 * ��������
	 */
	public void updateJiejrBo(JiejrBo JiejrBo);
	
	/*
	 * ɾ������
	 */
	public void deleteJiejrBo(JiejrBo JiejrBo);
}
