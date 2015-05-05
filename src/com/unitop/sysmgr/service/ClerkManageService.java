
package com.unitop.sysmgr.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;

/**
 * @author LiuShan
 * ���������Ա�����������ĸ���ҵ���߼�
 *	
 */
public interface ClerkManageService {
	
	public List exportClerk(String orgcode,String include)throws Exception;
	public boolean importClerk(HSSFSheet sheet)throws Exception;
	/**
	 * <dl>
	 * <dt><b>getClerkByOrgcode:ͨ�������Ų��ҹ�Ա</b></dt>
	 * <dd></dd>
	 * </dl>
	*/
	public List<Clerk> getClerkByOrgcode(String orgcode)throws Exception;
	/**
	 * ��ȡIPΪ'ip'�Ĺ�Ա����
	 * @param ip
	 * @return
	 */
	public Clerk getClerkCountByIp(String ip);
	public void setErrorNum(String clerkNum,String errorNum) throws Exception;
	/**
	 * v2.5����
	 */
	public List getRoleByClerk(String clerknum);
	public List getElseRoleByClerk(String delclerknum,String clerknum);
	public void deleteRoleByClerk(String clerknum);
	//�����Ա+��ɫ
	public void save(Clerk clerk,String[] juesid)  throws Exception;
	public boolean updateClerkRoles(Clerk clerk,String[] jues);
	public void updateClerk(Clerk clerk)throws BusinessException;
	public String getClerkByOrgClerkName(String clerkCode) throws Exception;
	//��ȡ��Ա����
	public long getClerkCouns();
	//���Ĺ�Ա����
	public void changePassword(Clerk clerk, String password) throws BusinessException;
	//ͨ����Ա��Ż�ȡ��Աʵ��
	public Clerk getClerkByCode(String code);
	//ɾ����Ա
	public void deleteClerk(String code) throws BusinessException;
	//������Ա
	public void createClerk(Clerk bo) throws BusinessException ;
	//�ж��ܷ������Ա
	public boolean CanOperDesClerkCode(Clerk OperClerk, String DesClerkCode)throws BusinessException;
	public String[] getClerkIP(String clerkCode);
	public void ClearClerkIP(String clerkCode);
	public List getOrgclerkIP(String orgCode);
	public void clearAllOrgclerkIP(String orgCode);
	//��ȡ��Ա��ɫ����
	public String getClerkJusjb(String clerkNum);
	//���ݹ�Ա��ɫ�����ѯ��Ա��ɫ�б�
	public List getAllRoleByClerk(Clerk clerk);
	
	//�����֤
	public String checkFeature(HttpServletRequest request) throws Exception;
	//��¼��Ȩ��־
	public void createAuthorizedLog(String account, String manageType,String manageTime,String manageDate,
			String code, String code2);
	//new  ���ݽ�ɫ����ͻ������� ���ؽ�ɫ�б�
	public List getAllRoleByClerk(Clerk clerk, String wdflag);
	public String getNewClerkCode(String orgcode) throws BusinessException;
}
