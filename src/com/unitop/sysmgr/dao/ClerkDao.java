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
	 * <dt><b>getAllClerkByOrgcode:获取柜员信息，本机构</b></dt>
	*/
	public List<Clerk> getClerkByOrgcode(String orgcode);
	public Clerk getClerkByCode(String code);
	public List<Clerk> exportClerk(String orgcode) throws Exception;
	public boolean importClerk(Clerk clerk) throws Exception;
	/**
	 * <dt><b>getAllClerkByOrgcode:获取柜员信息，包含下级机构</b></dt>
	*/
	public List<Clerk> getAllClerkByOrgcode(String orgcode);
	public Clerk getClerkCountByIp(String ip);
	public void setErrorNum(String clerkNum,String errorNum) throws Exception;
	/*保存柜员角色关系*/
	public void saveClerkRoles(String guiyid,String[] jues);
	/*更新柜员角色关系*/
	public void updateClerkRoles(String guiyid,String[] jues,String beiz)  throws Exception;
	//删除角色ID-柜员角色关系表数据
	public void deleteGuiyjsgxb(String guiyid);
	//查询角色ID-柜员角色关系表数据
	public List selectGuiyjsgxb(String juesid);
	//查询柜员数量
	public long getClerkCounts();
	//查询柜员对应角色信息
	public List getRoleByClerk(String clerknum);
	//根据柜员查询不属于该柜员其他角色
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
