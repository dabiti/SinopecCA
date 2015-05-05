package com.unitop.sysmgr.dao;
import java.sql.SQLException;

import com.unitop.sysmgr.bo.Ocxkongzcsb;

/*
 * by wp
 * 20130409
 */

public interface OcxkongzcsbDao extends BaseDataResourcesInterface
{
	//依据主键获取ocxkongzcsb表中信息
	public Ocxkongzcsb getocxkongzcs(String key)throws SQLException;

}

