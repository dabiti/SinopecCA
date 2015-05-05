package com.unitop.sysmgr.dao;

import java.util.List;
import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.CacheConfig;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Zhipyxxx;
import com.unitop.sysmgr.bo.AsynSealCheckConfig;
public interface OrgDao  extends BaseDataResourcesInterface {
	
	public List getOrgChildrenByCode(String code);
	//获取全部机构
	public List getAllOrg();
	//修改机构
	public void updateOrg (Org bo) throws BusinessException;
	//根据机构号获取机构
	public Org getOrgByCode(String code);
	public List getNetpointflag(String code) throws Exception;
	public void updateOrg(String account,String orgcode) throws Exception;
	public boolean validateOrg(String str1, String str2);
	public Org findOrgByOrgannum(String organnum) throws Exception;
	public List getOrgList(String parentCode);
	public Org getParentCode(String code);
	public void mergeOrg(String oldCode, String newCode)
	throws BusinessException;
	public boolean CanOperDesOrg(String OperOrg, String DesOrg);
	public void deleteOrg(Org org) throws BusinessException;
	public Org getOrgByOrgCode(String orgCode);
	/**
	 * <dt><b>exportOrg:导出机构信息</b></dt>
	*/
	public List<Org> exportOrg(String orgcode);
	/**
	 * <dt><b>importOrg:导入机构信息</b></dt>
	*/
	public boolean importOrg(Org org);
	/**
	 * <dt><b>getAllOrg:获取机构信息，包含下级机构</b></dt>
	*/
	public List<Org> getAllOrg(String orgcode);
	/**
	 *获取某个机构下属有卡柜的机构集合
	 */
	public List<Org> getKagOrgs(String orgcode);
	/**
	 * <dt><b>getOrgCount:获取机构数量</b></dt>
	*/
	public int getOrgCount(String code);
	/**
	 * <dt><b>getOrgCountAll:获取所有下级机构数量</b></dt>
	*/
	public int getOrgCountAll(String code);
	//获取通存通兑机构列表
	public List getOrgListForTCTD(String wdflag,String shenghOrgCode);
	public void updateForOrg(String orgCode, String tctd);
	
	public void updateOrgRelation(String srcorg,String relorg )throws Exception; 
	
	
	/**
	 * 根据分行号获得支票影像同步地址
	 */
	public String getSocketaddByJigh(String jigh) ;
	public List<Org> getAllOrgByWdflag(String string);
	public Zhipyxxx getZhipyxx(String code);
	public void updateOrSaveZhipyxxx(Zhipyxxx zhipyxxx) ;
	public AsynSealCheckConfig getAsynSealCheckConfig(String code);
	public void updateOrSaveAsynSealCheckConfig(AsynSealCheckConfig zuoyzx);
	
	
	public Org getOrgByCode_TB(String orgCode) throws DAOException;
	
	public void saveOrgList_TB(List<Org> orgList) throws DAOException;
	
	public void updateOrgList_TB(List<Org> orgList) throws DAOException;
	
	public void deleteUndefindOrg_TB() throws DAOException;
	
	public void updateAllOrgForEnd_TB()throws DAOException;
	
	public void updateOrg_TB(Org org) throws DAOException;
	
	public void saveOrg_TB(Org org) throws DAOException;
	
	public void startTransaction_TB() throws DAOException;

	public void rollback_TB() throws DAOException;

	public void closeConn_TB() throws DAOException;
	
	public void commit_TB()throws DAOException;
	
	public void clearTemp_TB() throws DAOException;
	
	public void addTemp_TB(Org org) throws DAOException;
	public List<Org> getAllTempOrg_TB() throws DAOException;
	
	public Org getOrgFromTemp_TB(String orgCode) throws DAOException;
	
	public Org getCenterBankFromTemp_TB()throws DAOException;
	public CacheConfig getCacheConfigByOrgCode(String code);
	public void updateOrSaveCacheConfig(CacheConfig yingxhc);
	
	//查询缓存地址
	public String getCacheAddByCode(String orgCode);
	
	
}
