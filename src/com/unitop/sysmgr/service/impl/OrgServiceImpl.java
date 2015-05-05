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
//��������
@Service("OrgServiceImpl")
public class OrgServiceImpl extends BaseServiceImpl implements OrgService {
	
	@Resource  
	private OrgDao orgDao;  
	@Resource
	private ClerkDao clerkDao;
	@Resource
	private ZhanghbDao ZhanghbDao;
	 
	//��ȡ���л���
	public List getAllOrg() {
		return orgDao.getAllOrg();
	}
	
	//�޸Ļ���
	public void updateOrg(Org bo) throws BusinessException {
		orgDao.updateOrg(bo);
	}
	
	//��������
	public Clerk createOrg(Org bo,Clerk clerk) throws BusinessException {
		Clerk c =null;
		SystemConfig systemConfig = SystemConfig.getInstance();
		Org org = orgDao.getOrgByCode(bo.getCode());
		if (org != null)
		{
			throw new BusinessException("����["+bo.getCode()+"]�Ѵ���!");
		}
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		orgDao.set_Session(session);
		clerkDao.set_Session(session);
		try{
			//��������
			orgDao.updateOrg(bo);
			//���� ����һ������ �Զ�������������Ա
		//	if("2".equals(systemConfig.getValue("clerk_guanlms")))
			if(bo.getWdflag().equals("1"))
			{
				c=new Clerk();
				c.setCode(bo.getCode() + systemConfig.getSuperManager());
				c.setPassword(PasswordUtil.encodePwd(systemConfig.getAdminPassword()));
				c.setOrgcode(bo.getCode());
				c.setWdFlag(bo.getWdflag());
				c.setName("����Ա");
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
		//���ñ�ͷ
		title.put(0, "������");
		title.put(1, "��������");
		title.put(2, "�ϼ�����");
		
		title.put(3, "һ�����к�");
		title.put(4, "ͨ��ͨ��");
		title.put(5, "�����ʶ");
//		title.put(3, "�����к�");
//		title.put(4, "ʡ�к�");
//		title.put(5, "ͨ��ͨ��");
//		title.put(6, "�����ʶ");
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
	
	//��������
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
				throw new BusinessException("��������'" + newCode + "'������");
			else if (!newOrg.getWdflag().equals(cbjb))
				//throw new BusinessException("����'" + newCode + "'����" + cbjb + "������");
				throw new BusinessException("����ҵ��ֻ����֧��֮�����");
			else if (!newOrg.getShOrgCode().equals(oldOrg.getShOrgCode())) {
				throw new BusinessException("����������ͬһ��һ��������");
			} else if (oldCode.equals(newCode))
				throw new BusinessException("����������Ч");
			
			//super.getBaseHibernateDao().getDaoHibernateTemplate().delete(bo);
			//�ɻ������ϼ�Ϊ�������� ���»������ϼ�Ϊһ������ ���޸ľɻ������˻�ͨ�ұ�־Ϊ���������С����˻� ��ͨ�ұ�־Ϊ��һ�����С�
			if((!oldOrg.getParentCode().equals(oldOrg.getShOrgCode()))&&(newOrg.getParentCode().equals(newOrg.getShOrgCode()))){
				String updateSql4="update zhanghb a set a.tongctd='һ������ȫϽ' where a.tongctd='��������' and a.internalorganizationnumber=:jigh";
				SQLQuery sq4= session.createSQLQuery(updateSql4);
				sq4.setString("jigh", oldCode);
				sq4.executeUpdate();
			}
			//20140321 --qjk  �޸ĳ��������������Ĺ�Ա�������»�����
			String updateSql1="update clerktable c set c.internalorganizationnumber=:jigh ,c.f_internalorganizationnumber=:shenghjgh where c.internalorganizationnumber=:oldjigh";
			SQLQuery sq1= session.createSQLQuery(updateSql1);
			sq1.setString("jigh", newCode);
			sq1.setString("shenghjgh", newOrg.getShOrgCode());
			sq1.setString("oldjigh", oldCode);
			sq1.executeUpdate();
			//ɾ������
			String updateSql5="delete organarchives where internalorganizationnumber=:orgCode";
			SQLQuery sq5= session.createSQLQuery(updateSql5);
			sq5.setString("orgCode", oldCode);
			sq5.executeUpdate();
			//���˺Ź������»�����
			String updateSql2 = "update zhanghb a set a.internalorganizationnumber=:jigh where a.internalorganizationnumber=:oldjigh";
			SQLQuery sq2= session.createSQLQuery(updateSql2);
			sq2.setString("jigh", newCode);
			sq2.setString("oldjigh", oldCode);
			sq2.executeUpdate();
			//��ӡ�����������»�����
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
			throw new BusinessException("����ʧ�ܣ������²�������ϵ����Ա");
		}finally{
			getBaseHibernateDao().closeSession(session);
		}
	}
	
	//��ȡ������Ϣ
	public Org getOrgByCode(String code){
		return orgDao.getOrgByCode(code);
	}
	
	//��ȡ���л����ַ
	public String getCacheAddByCode(String code){
		return orgDao.getCacheAddByCode(code);
	}
	
	public boolean validateOrg(String str1, String str2){
		return orgDao.validateOrg(str1, str2);
	}
	
	/**
	 * ���ݷ��кŻ��֧ƱӰ��ͬ����ַ
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