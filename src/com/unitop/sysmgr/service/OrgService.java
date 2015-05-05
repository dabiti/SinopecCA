package com.unitop.sysmgr.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.CacheConfig;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Zhipyxxx;
import com.unitop.sysmgr.bo.AsynSealCheckConfig;

public interface OrgService {
	
	//��ȡ���л���
	public List getAllOrg();
	//���ݻ����� ��ȡ������Ϣ
	public Org getOrgByCode(String code);
	//�޸Ļ���
	public void updateOrg (Org bo) throws BusinessException;
	//��������
	public Clerk createOrg(Org bo,Clerk clerk) throws BusinessException; 
	//ɾ������
	public void deleteOrg(Org org) throws BusinessException; 
	//��������
	public void mergeOrg(String oldCode, String newCode) throws BusinessException;
	public int getLastWdflag();
	public int getLineNumber();
	//��ȡ�ӻ���
	public List getOrgChildrenByCode(String code);
	/**
	 * <dd>���������Ϣ�����ݿ���</dd>
	*/
	public boolean importOrg(HSSFSheet sheet);
	
	/**
	 * <dd>��ѯ������Ϣ�б�</dd>
	*/
	public List exportOrg(String orgcode,String include);
	/**
	 * <dt><b>getOrgCount:ͳ�ƻ�������</b></dt>
	*/
	public List getOrgCount(String code); 
	//��ȡͨ��ͨ�һ�����Ϣ
	public List getOrgListForTCTD(String wdflag,String shenghOrgCode);
	public boolean updateOrgAndAccount(String orgCode, String tctd);
	//�жϻ��� Ȩ��
	public boolean validateOrg(String str1, String str2);
	
	
	/**
	 * ���ݷ��кŻ��֧ƱӰ��ͬ����ַ
	 */
	public String getSocketaddByJigh(String jigh) ;
	public List<Org> getAllOrgByWdflag(String string);
	public Zhipyxxx getZhipyxx(String code);
	public void updateOrSaveZhipyxxx(Zhipyxxx zhipyxxx);
	public AsynSealCheckConfig getZuoyzxxx(String code);
	public void updateOrSaveAsynSealCheckConfig(AsynSealCheckConfig zuoyzx);
	
	
	//��ȡ���л����ַ
	public String getCacheAddByCode(String code);
	public CacheConfig getCacheConfigByOrgCode(String code);
	public void updateOrSaveCacheConfig(CacheConfig yingxhc);
}