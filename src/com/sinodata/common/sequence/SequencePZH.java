package com.sinodata.common.sequence;

import com.unitop.sysmgr.dao.SystemDao;

/*
 * ����ƾ֤������
 * 
 */
public class SequencePZH {
	
	private SystemDao systemDao;

	public SequencePZH(SystemDao systemDao){
		this.systemDao = systemDao;
	}
	
	//��ȡ����ƾ֤��
	public String getNewPZH(){
		return systemDao.getPZHSequence();
	}
	
}