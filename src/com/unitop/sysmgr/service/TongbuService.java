package com.unitop.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.unitop.exception.DAOException;

public interface TongbuService {
	
	public Map<String, String> queryTongbu() throws DAOException;
	
	public String returnTongbu(String returnStr,String tongbzh) throws DAOException;

	public Map<String, List> queryAllJig(Map<String, String> sendMap) throws DAOException;
	
	
	/**
	 * ���ݻ����Ż�ȡ������Ϣ
	 * @param jigh
	 * @return
	 * @throws DAOException 
	 */
	public Map<String,String> queryJigForJigh(String jigh) throws DAOException;
	
	
	
	
	
	// ��¼Socketδ���ͳɹ�������Ϣ(����)
	public void saveSendExceptionForJigh(String exception, String jigh);
	
	
	
	/**
	 * ���ݷ��кŻ�ȡͬ����ַ
	 * 
	 * @param jigh
	 * @return
	 * @throws DAOException
	 */
	
	public String getSocketaddByJigh(String jigh) throws DAOException ;
}
