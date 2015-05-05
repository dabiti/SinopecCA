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
	
	//获取所有机构
	public List getAllOrg();
	//依据机构号 获取机构信息
	public Org getOrgByCode(String code);
	//修改机构
	public void updateOrg (Org bo) throws BusinessException;
	//创建机构
	public Clerk createOrg(Org bo,Clerk clerk) throws BusinessException; 
	//删除机构
	public void deleteOrg(Org org) throws BusinessException; 
	//撤并机构
	public void mergeOrg(String oldCode, String newCode) throws BusinessException;
	public int getLastWdflag();
	public int getLineNumber();
	//获取子机构
	public List getOrgChildrenByCode(String code);
	/**
	 * <dd>导入机构信息到数据库中</dd>
	*/
	public boolean importOrg(HSSFSheet sheet);
	
	/**
	 * <dd>查询机构信息列表</dd>
	*/
	public List exportOrg(String orgcode,String include);
	/**
	 * <dt><b>getOrgCount:统计机构数量</b></dt>
	*/
	public List getOrgCount(String code); 
	//获取通存通兑机构信息
	public List getOrgListForTCTD(String wdflag,String shenghOrgCode);
	public boolean updateOrgAndAccount(String orgCode, String tctd);
	//判断机构 权限
	public boolean validateOrg(String str1, String str2);
	
	
	/**
	 * 根据分行号获得支票影像同步地址
	 */
	public String getSocketaddByJigh(String jigh) ;
	public List<Org> getAllOrgByWdflag(String string);
	public Zhipyxxx getZhipyxx(String code);
	public void updateOrSaveZhipyxxx(Zhipyxxx zhipyxxx);
	public AsynSealCheckConfig getZuoyzxxx(String code);
	public void updateOrSaveAsynSealCheckConfig(AsynSealCheckConfig zuoyzx);
	
	
	//获取分行缓存地址
	public String getCacheAddByCode(String code);
	public CacheConfig getCacheConfigByOrgCode(String code);
	public void updateOrSaveCacheConfig(CacheConfig yingxhc);
}