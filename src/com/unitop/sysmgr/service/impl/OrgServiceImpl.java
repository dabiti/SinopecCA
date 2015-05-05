package com.unitop.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.framework.util.ExpOrImp;
import com.unitop.framework.util.PasswordUtil;
import com.unitop.sysmgr.bo.CacheConfig;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.SystemControlParameter;
import com.unitop.sysmgr.bo.Zhipyxxx;
import com.unitop.sysmgr.bo.AsynSealCheckConfig;
import com.unitop.sysmgr.dao.ClerkDao;
import com.unitop.sysmgr.dao.OrgDao;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.service.OrgService;
//机构管理
@Service("OrgServiceImpl")
public class OrgServiceImpl extends BaseServiceImpl implements OrgService {
	
	@Resource  
	private OrgDao orgDao;  
	@Resource
	private ClerkDao clerkDao;
	@Resource
	private ZhanghbDao ZhanghbDao;
	 
	//获取所有机构
	public List getAllOrg() {
		return orgDao.getAllOrg();
	}
	
	//修改机构
	public void updateOrg(Org bo) throws BusinessException {
		orgDao.updateOrg(bo);
	}
	
	//创建机构
	public Clerk createOrg(Org bo,Clerk clerk) throws BusinessException {
		Clerk c =null;
		SystemConfig systemConfig = SystemConfig.getInstance();
		Org org = orgDao.getOrgByCode(bo.getCode());
		if (org != null)
		{
			throw new BusinessException("机构["+bo.getCode()+"]已存在!");
		}
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		orgDao.set_Session(session);
		clerkDao.set_Session(session);
		try{
			//创建机构
			orgDao.updateOrg(bo);
			//总行 创建一级分行 自动创建超级管理员
		//	if("2".equals(systemConfig.getValue("clerk_guanlms")))
			if(bo.getWdflag().equals("1"))
			{
				c=new Clerk();
				c.setCode(bo.getCode() + systemConfig.getSuperManager());
				c.setPassword(PasswordUtil.encodePwd(systemConfig.getAdminPassword()));
				c.setOrgcode(bo.getCode());
				c.setWdFlag(bo.getWdflag());
				c.setName("管理员");
				c.setShOrgCode(bo.getCode());
				c.setLeixbs("0");
				c.setStatus("1");
				clerkDao.saveOrUpdate(c);
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
		}finally{
			clerkDao.shifSession();
		}
		return c;
	}
	
	public void deleteOrg(Org org) throws BusinessException{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		orgDao.set_Session(session);
		try{
			orgDao.deleteOrg(org);
			session.flush();
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}finally{
			orgDao.shifSession();
		}
	}
	
	
	public static String lineNumber;
	public static String lastWdflag;
	
	public  int getLastWdflag(){
		try {
			SystemControlParameter p = SystemControlParametersDao.findSystemControlParameterById("org_ranking");
			lineNumber = p.getValue();
			lastWdflag = ""+(Integer.parseInt(lineNumber)-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(lastWdflag);
	}
	
	public  int getLineNumber(){
		try {
			SystemControlParameter p = SystemControlParametersDao.findSystemControlParameterById("org_ranking");
			lineNumber = p.getValue();
			lastWdflag = ""+(Integer.parseInt(lineNumber)-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(lineNumber);
	}

	/**
	 * @see com.unitop.sysmgr.service.OrgService#exportOrg(java.lang.String)
	 */
	public List exportOrg(String orgcode,String include) {
		if("0".equals(include)){
			List list = orgDao.getAllOrg(orgcode);
			List message = setMap(list);
			return message;
		}else{
			List list = orgDao.exportOrg(orgcode);
			List message = setMap(list);
			return message;
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private List setMap(List orgGroup){
		List list = new ArrayList();
		Map title = new HashMap();
		//设置表头
		title.put(0, "机构号");
		title.put(1, "机构名称");
		title.put(2, "上级机构");
		
		title.put(3, "一级分行号");
		title.put(4, "通存通兑");
		title.put(5, "网点标识");
//		title.put(3, "人行行号");
//		title.put(4, "省行号");
//		title.put(5, "通存通兑");
//		title.put(6, "网点标识");
		list.add(title);
		for(int i=0;i<orgGroup.size();i++){
			Map map = new HashMap();
			Org org = (Org) orgGroup.get(i);
			map.put(0, org.getCode());
			map.put(1, org.getName());
			map.put(2, org.getParentCode());
			map.put(3, org.getPaymentCode());
			map.put(4, org.getShOrgCode());
			map.put(5, org.getTctd());
			map.put(6, org.getWdflag());
			list.add(map);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.service.OrgService#getOrgCount(java.lang.String)
	 */
	public List getOrgCount(String code) {
		List count = new ArrayList();
		int org = orgDao.getOrgCount(code);
		int all = orgDao.getOrgCountAll(code);
		count.add(org);
		count.add(all);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.service.OrgService#importOrg(org.apache.poi.hssf.usermodel.HSSFSheet)
	 */
	public boolean importOrg(HSSFSheet sheet) {
		List list = ExpOrImp.importExcel(sheet, 7);
		boolean isImport = false;
		for(int i=0;i<list.size();i++)
		{
			Map map = (Map)list.get(i);
			Org org = new Org();
			org.setCode((String)map.get(0));
			org.setName((String)map.get(1));
			org.setParentCode((String)map.get(2));
			
			
			org.setShOrgCode((String)map.get(3));
			org.setTctd((String)map.get(4));
			org.setWdflag((String)map.get(5));
//			org.setPaymentCode((String)map.get(3));
//			org.setShOrgCode((String)map.get(4));
//			org.setTctd((String)map.get(5));
//			org.setWdflag((String)map.get(6));
			isImport = orgDao.importOrg(org);
		}
		return isImport;
	}

	public List getOrgChildrenByCode(String code) {
		return orgDao.getOrgChildrenByCode(code);
	}
	
	public List getOrgListForTCTD(String wdflag,String shenghOrgCode) {
		return orgDao.getOrgListForTCTD(wdflag, shenghOrgCode);
	}
	
	public boolean updateOrgAndAccount(String orgCode, String tctd) {
		try{
			orgDao.updateForOrg(orgCode, tctd);
			ZhanghbDao.updateForAccount(orgCode, tctd);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//撤并机构
	public void mergeOrg(String oldCode, String newCode) throws BusinessException {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction ts =session.beginTransaction();
	//	orgDao.set_Session(session);
		ts.begin();
		try{
			String cbjb = "";
			try {
				String value = super.SystemControlParametersDao .findSystemControlParameterById("org_ranking").getValue();
				int n_= Integer.valueOf(value);
				cbjb = "" +(n_- 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Org newOrg = orgDao.getOrgByCode(newCode);
			Org oldOrg = orgDao.getOrgByCode(oldCode);
			if (newOrg == null)
				throw new BusinessException("机构代码'" + newCode + "'不存在");
			else if (!newOrg.getWdflag().equals(cbjb))
				//throw new BusinessException("机构'" + newCode + "'不是" + cbjb + "级机构");
				throw new BusinessException("撤并业务只能在支行之间进行");
			else if (!newOrg.getShOrgCode().equals(oldOrg.getShOrgCode())) {
				throw new BusinessException("两机构不在同一个一级分行内");
			} else if (oldCode.equals(newCode))
				throw new BusinessException("机构代码无效");
			
			//super.getBaseHibernateDao().getDaoHibernateTemplate().delete(bo);
			//旧机构的上级为二级分行 而新机构的上级为一级分行 则修改旧机构中账户通兑标志为“二级分行”的账户 的通兑标志为“一级分行”
			if((!oldOrg.getParentCode().equals(oldOrg.getShOrgCode()))&&(newOrg.getParentCode().equals(newOrg.getShOrgCode()))){
				String updateSql4="update zhanghb a set a.tongctd='一级分行全辖' where a.tongctd='二级分行' and a.internalorganizationnumber=:jigh";
				SQLQuery sq4= session.createSQLQuery(updateSql4);
				sq4.setString("jigh", oldCode);
				sq4.executeUpdate();
			}
			//20140321 --qjk  修改撤并将撤销机构的柜员归属到新机构中
			String updateSql1="update clerktable c set c.internalorganizationnumber=:jigh ,c.f_internalorganizationnumber=:shenghjgh where c.internalorganizationnumber=:oldjigh";
			SQLQuery sq1= session.createSQLQuery(updateSql1);
			sq1.setString("jigh", newCode);
			sq1.setString("shenghjgh", newOrg.getShOrgCode());
			sq1.setString("oldjigh", oldCode);
			sq1.executeUpdate();
			//删除机构
			String updateSql5="delete organarchives where internalorganizationnumber=:orgCode";
			SQLQuery sq5= session.createSQLQuery(updateSql5);
			sq5.setString("orgCode", oldCode);
			sq5.executeUpdate();
			//将账号归属到新机构中
			String updateSql2 = "update zhanghb a set a.internalorganizationnumber=:jigh where a.internalorganizationnumber=:oldjigh";
			SQLQuery sq2= session.createSQLQuery(updateSql2);
			sq2.setString("jigh", newCode);
			sq2.setString("oldjigh", oldCode);
			sq2.executeUpdate();
			//将印鉴卡归属到新机构中
			String updateSql3="update yinjk a set a.internalorganizationnumber=:jigh where a.internalorganizationnumber=:oldjigh";
			SQLQuery sq3= session.createSQLQuery(updateSql3);
			sq3.setString("jigh", newCode);
			sq3.setString("oldjigh", oldCode);
			sq3.executeUpdate();
			ts.commit();
		}catch (BusinessException e) {
			ts.rollback();
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}catch (Exception e) {
			ts.rollback();
			e.printStackTrace();
			throw new BusinessException("撤并失败，请重新操作或联系管理员");
		}finally{
			getBaseHibernateDao().closeSession(session);
		}
	}
	
	//获取机构信息
	public Org getOrgByCode(String code){
		return orgDao.getOrgByCode(code);
	}
	
	//获取分行缓存地址
	public String getCacheAddByCode(String code){
		return orgDao.getCacheAddByCode(code);
	}
	
	public boolean validateOrg(String str1, String str2){
		return orgDao.validateOrg(str1, str2);
	}
	
	/**
	 * 根据分行号获得支票影像同步地址
	 */
	public String getSocketaddByJigh(String jigh) {
		return orgDao.getSocketaddByJigh(jigh);
	}
	
	public List<Org> getAllOrgByWdflag(String string) {
		
		return orgDao.getAllOrgByWdflag(string);
	}
	
	public Zhipyxxx getZhipyxx(String code) {
		return orgDao.getZhipyxx(code);
	}
	public AsynSealCheckConfig getZuoyzxxx(String code) {
		return  orgDao.getAsynSealCheckConfig(code);
	}
	public void updateOrSaveZhipyxxx(Zhipyxxx zhipyxxx) {
		orgDao.updateOrSaveZhipyxxx(zhipyxxx);
	}
	public void updateOrSaveAsynSealCheckConfig(AsynSealCheckConfig zuoyzx) {
		orgDao.updateOrSaveAsynSealCheckConfig(zuoyzx);
		
	}
	
	public CacheConfig getCacheConfigByOrgCode(String code) {
		return  orgDao.getCacheConfigByOrgCode(code);
	}
	
	@Override
	public void updateOrSaveCacheConfig(CacheConfig yingxhc) {
		orgDao.updateOrSaveCacheConfig(yingxhc);
		
	}

}