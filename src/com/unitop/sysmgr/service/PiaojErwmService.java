package com.unitop.sysmgr.service;

import java.util.List;

public interface PiaojErwmService {
	
	//����Ʊ�ݶ�ά��
	public void save(PiaojErwmService piaojErwmBo);
	
	//����Ʊ�ݶ�ά��
	public void update(PiaojErwmService piaojErwmBo);
	
	//���沢�Ҹ���Ʊ�ݶ�ά��
	public void saveOrUpdate(PiaojErwmService piaojErwmBo);
	
	//ɾ����ά��
	public void delete(PiaojErwmService piaojErwmBo);
	
	//��ѯ���м�¼
	public List selectAll();
	
	//��ѯ�ض���¼
	public PiaojErwmService selectAll(String piaojErwmId);
	
}
