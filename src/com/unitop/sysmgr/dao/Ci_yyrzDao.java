package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Ci_yyrz;

/*
 */
public interface Ci_yyrzDao   extends BaseDataResourcesInterface{
	
	// ��������ѯ��������
	public List<Ci_yyrz> getCi_yyrzListByRenwbs(String xitbs ,String renwbs) ;
}
