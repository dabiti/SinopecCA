
package com.unitop.sysmgr.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;

/**
 * @author LiuShan
 * 用来处理柜员管理中遇到的复杂业务逻辑
 *	
 */
public interface ClerkManageService {
	
	public List exportClerk(String orgcode,String include)throws Exception;
	public boolean importClerk(HSSFSheet sheet)throws Exception;
	/**
	 * <dl>
	 * <dt><b>getClerkByOrgcode:通过机构号查找柜员</b></dt>
	 * <dd></dd>
	 * </dl>
	*/
	public List<Clerk> getClerkByOrgcode(String orgcode)throws Exception;
	/**
	 * 获取IP为'ip'的柜员数量
	 * @param ip
	 * @return
	 */
	public Clerk getClerkCountByIp(String ip);
	public void setErrorNum(String clerkNum,String errorNum) throws Exception;
	/**
	 * v2.5新增
	 */
	public List getRoleByClerk(String clerknum);
	public List getElseRoleByClerk(String delclerknum,String clerknum);
	public void deleteRoleByClerk(String clerknum);
	//保存柜员+角色
	public void save(Clerk clerk,String[] juesid)  throws Exception;
	public boolean updateClerkRoles(Clerk clerk,String[] jues);
	public void updateClerk(Clerk clerk)throws BusinessException;
	public String getClerkByOrgClerkName(String clerkCode) throws Exception;
	//获取柜员数量
	public long getClerkCouns();
	//更改柜员密码
	public void changePassword(Clerk clerk, String password) throws BusinessException;
	//通过柜员编号获取柜员实体
	public Clerk getClerkByCode(String code);
	//删除柜员
	public void deleteClerk(String code) throws BusinessException;
	//创建柜员
	public void createClerk(Clerk bo) throws BusinessException ;
	//判断能否操作柜员
	public boolean CanOperDesClerkCode(Clerk OperClerk, String DesClerkCode)throws BusinessException;
	public String[] getClerkIP(String clerkCode);
	public void ClearClerkIP(String clerkCode);
	public List getOrgclerkIP(String orgCode);
	public void clearAllOrgclerkIP(String orgCode);
	//获取柜员角色级别
	public String getClerkJusjb(String clerkNum);
	//依据柜员角色级别查询柜员角色列表
	public List getAllRoleByClerk(Clerk clerk);
	
	//身份认证
	public String checkFeature(HttpServletRequest request) throws Exception;
	//记录授权日志
	public void createAuthorizedLog(String account, String manageType,String manageTime,String manageDate,
			String code, String code2);
	//new  根据角色级别和机构级别 返回角色列表
	public List getAllRoleByClerk(Clerk clerk, String wdflag);
	public String getNewClerkCode(String orgcode) throws BusinessException;
}
