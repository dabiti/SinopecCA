package com.unitop.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.unitop.exception.DAOException;

public interface TongbuService {
	
	public Map<String, String> queryTongbu() throws DAOException;
	
	public String returnTongbu(String returnStr,String tongbzh) throws DAOException;

	public Map<String, List> queryAllJig(Map<String, String> sendMap) throws DAOException;
	
	
	/**
	 * 根据机构号获取机构信息
	 * @param jigh
	 * @return
	 * @throws DAOException 
	 */
	public Map<String,String> queryJigForJigh(String jigh) throws DAOException;
	
	
	
	
	
	// 记录Socket未发送成功错误信息(机构)
	public void saveSendExceptionForJigh(String exception, String jigh);
	
	
	
	/**
	 * 根据分行号获取同步地址
	 * 
	 * @param jigh
	 * @return
	 * @throws DAOException
	 */
	
	public String getSocketaddByJigh(String jigh) throws DAOException ;
}
