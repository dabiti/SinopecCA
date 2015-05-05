package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Huobb;
import com.unitop.sysmgr.bo.SystemControlParameter;

public interface SystemDao {
	//��ȡϵͳʱ��
	public String getSystetemNowDate();
	//���ϵͳ���Ʋ���
	public void addSystemControlParameter(SystemControlParameter controlParameter);
	//�޸�ϵͳ���Ʋ���
	public void updateSystemControlParameter( SystemControlParameter controlParameter);
	//ɾ��ϵͳ���Ʋ���
	public void deleteSystemControlParameter(String id);
	//����ϵͳ���Ʋ���
	public List findAllSystemControlParameters();
	//����ID����ϵͳ���Ʋ���
	public SystemControlParameter findSystemControlParameterById(String id);
	//��ȡ���Һ�
	public List getHuobhList();
	public Huobb getHuobh(String huobbh);
	//��ȡϵͳ��ӡ����
	public List getYanyinSystemType();
	public String getSystetemNextDate();
	
	//��ȡ��һ��ƾ֤��
	public String getPZHSequence() ;
	public String getSystetemYestoDay();
}
