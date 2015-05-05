package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Shouqrzb;

public interface ClerkDao extends BaseDataResourcesInterface {

	public void saveOrUpdate(Clerk clerk); 
	public void changePassword(Clerk clerk, String password) throws BusinessException;
	public void deleteClerk(Clerk clerk) throws BusinessException;
	public boolean CanOperDesClerkCode(Clerk OperClerk, String DesClerkCode)  throws BusinessException;
	/**
	 * <dt><b>getAllClerkByOrgcode:��ȡ��Ա��Ϣ��������</b></dt>
	*/
	public List<Clerk> getClerkByOrgcode(String orgcode);
	public Clerk getClerkByCode(String code);
	public List<Clerk> exportClerk(String orgcode) throws Exception;
	public boolean importClerk(Clerk clerk) throws Exception;
	/**
	 * <dt><b>getAllClerkByOrgcode:��ȡ��Ա��Ϣ�������¼�����</b></dt>
	*/
	public List<Clerk> getAllClerkByOrgcode(String orgcode);
	public Clerk getClerkCountByIp(String ip);
	public void setErrorNum(String clerkNum,String errorNum) throws Exception;
	/*�����Ա��ɫ��ϵ*/
	public void saveClerkRoles(String guiyid,String[] jues);
	/*���¹�Ա��ɫ��ϵ*/
	public void updateClerkRoles(String guiyid,String[] jues,String beiz)  throws Exception;
	//ɾ����ɫID-��Ա��ɫ��ϵ������
	public void deleteGuiyjsgxb(String guiyid);
	//��ѯ��ɫID-��Ա��ɫ��ϵ������
	public List selectGuiyjsgxb(String juesid);
	//��ѯ��Ա����
	public long getClerkCounts();
	//��ѯ��Ա��Ӧ��ɫ��Ϣ
	public List getRoleByClerk(String clerknum);
	//���ݹ�Ա��ѯ�����ڸù�Ա������ɫ
	public List getElseRoleByClerk(String clerknum,String juesjb);
	public void saveAuthorizedLog(Shouqrzb bo);
	
	
	
	public Clerk getClerkByCode_TB(String clerkCode) throws DAOException;
	
	public void saveClerk_TB(Clerk clerk) throws DAOException;
	
	public void updateClerk_TB(Clerk clerk) throws DAOException;
	
	public void deleteUndefindClerk_TB() throws DAOException;
	
	public void updateAllClerkForEnd_TB() throws DAOException;
	
	public void startTransaction_TB() throws DAOException;

	public void rollback_TB() throws DAOException;

	public void closeConn_TB() throws DAOException;
	
	public void commit_TB()throws DAOException;
	public String getMaxClerkCodeByHead(String head);
}
