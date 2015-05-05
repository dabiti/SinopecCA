package com.sinodata.common.sequence;

import com.unitop.sysmgr.dao.SystemDao;

/*
 * 生成凭证号序列
 * 
 */
public class SequencePZH {
	
	private SystemDao systemDao;

	public SequencePZH(SystemDao systemDao){
		this.systemDao = systemDao;
	}
	
	//获取最新凭证号
	public String getNewPZH(){
		return systemDao.getPZHSequence();
	}
	
}