package com.unitop.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.unitop.config.DBinfoConig;
import com.unitop.sysmgr.bo.AccountManageLog;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.SystemControlParameter;
import com.unitop.sysmgr.bo.SystemManageLog;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.dao.ClerkDao;
import com.unitop.sysmgr.dao.OrgDao;
import com.unitop.sysmgr.dao.SystemDao;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.service.SystemMgrService;
@Service("systemMgrService")
public class SystemMgrServiceImpl extends BaseServiceImpl implements SystemMgrService {
	@Resource
	private SystemDao SystemDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	private ZhanghbDao ZhanghbDao;
	@Resource
	private ClerkDao ClerkDao;
//	@Resource
//	private YinjkDao yinjkDao;

	public void createSystemManageLog(SystemManageLog log) {
		Session session = this.getHibernateSession();
		try {
			session.saveOrUpdate(log);
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
	}

	public void createAccountmanagelog(AccountManageLog bo) {
		Session session = this.getHibernateSession();
		try {
			String query = "insert into accountmanagelog (zhangh,managedate,managetime,managetype,managecontent,clerknum,clerkname,ip,internalorganizationnumber,str1) "
					+ "values (:account,:managedate,:managetime,:managetype,:managecontent,:clerknum,:clerkname,:ip,:upflag,:str1)";
			SQLQuery qu = session.createSQLQuery(query);
			qu.setString("account", bo.getAccount());
			qu.setString("managedate", bo.getManagedate());
			qu.setString("managetime", bo.getManagetime());
			qu.setString("managetype", bo.getManagetype());
			qu.setString("managecontent", bo.getManagecontent());
			qu.setString("clerknum", bo.getClerknum());
			qu.setString("clerkname", bo.getClerkname());
			qu.setString("ip", bo.getIp());
			qu.setString("upflag", bo.getUpflag());
			qu.setString("str1", bo.getStr1());
			qu.executeUpdate();
		} finally {
			this.closeSession(session);
		}
	}
	
	public void createAccountmanagelogNew(AccountManageLog bo) {
		Session session = this.getHibernateSession();
		try {
			String query = "insert into accountmanagelog (zhangh,managedate,managetime,managetype,managecontent,clerknum,clerkname,ip,internalorganizationnumber,str1,oldcontent,newcontent) "
					+ "values (:account,:managedate,:managetime,:managetype,:managecontent,:clerknum,:clerkname,:ip,:upflag,:str1,:oldcontent,:newcontent)";
			SQLQuery qu = session.createSQLQuery(query);
			qu.setString("account", bo.getAccount());
			qu.setString("managedate", bo.getManagedate());
			qu.setString("managetime", bo.getManagetime());
			qu.setString("managetype", bo.getManagetype());
			qu.setString("managecontent", bo.getManagecontent());
			qu.setString("clerknum", bo.getClerknum());
			qu.setString("clerkname", bo.getClerkname());
			qu.setString("ip", bo.getIp());
			qu.setString("upflag", bo.getUpflag());
			qu.setString("str1", bo.getStr1());
			qu.setString("oldcontent", bo.getOldcontent());
			qu.setString("newcontent",bo.getNewcontent());
			qu.executeUpdate();
		} finally {
			this.closeSession(session);
		}
	}
	public boolean CanOperDesOrg(String OperOrg, String DesOrg) {
		Session session = this.getHibernateSession();
		boolean ReturnValue = false;
		try {
			if (OperOrg.equals(DesOrg)) {
				ReturnValue = true;
			} else {
				String sql = "";
				String db_type = DBinfoConig.getDBType();
				if("oracle".equals(db_type))
				{
					sql="SELECT internalorganizationnumber as C FROM organarchives "
						+ "WHERE internalorganizationnumber IN ("
						+ "select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber start with internalorganizationnumber=:OperOrg"
						+ ") AND internalorganizationnumber=:DesOrg";
				}else{
					sql="SELECT internalorganizationnumber as C FROM organarchives "
						+ "WHERE internalorganizationnumber IN (select * from TABLE(ORGFUNCTION(:OperOrg)) AS test) AND internalorganizationnumber=:DesOrg";
				}
				SQLQuery qu = session.createSQLQuery(sql);
				qu.setString("OperOrg", OperOrg);
				qu.setString("DesOrg", DesOrg);
				List list = qu.list();
				ReturnValue = list.size() == 1;
			}
		} finally {
			this.closeSession(session);
		}
		return ReturnValue;
	}
	
	/**
	 * �����ҵ�������ĵ�Ȩ��У��
	 */
	public boolean CanOperDesSpecialOrg(String OperOrg, String DesOrg) {
		Org clerk_org=orgDao.getOrgByCode(OperOrg);
		String orgCode=OperOrg;
		if(clerk_org!=null&&clerk_org.getLeixbs().equals("4")){
			orgCode=clerk_org.getShOrgCode();
		}
		return CanOperDesOrg(orgCode, DesOrg);
	}

	
	/**
	 * @author lhz
	 * @param tellerorg ��ԱID
	 * @param operorg ������������
	 * ����Ա�ܷ�����û���
	 */
	public CanOperAccReturn ProCanOperOrg(String tellerorg,String operorg){
		
		CanOperAccReturn result = new CanOperAccReturn();
		String retmsg = "";
		Integer retflag = 1;
		Session session = this.getHibernateSession();
		try{
			Org org = (Org) orgDao.getOrgByCode(operorg);
			if(org == null){
				retflag = -1;
				retmsg = "�������Ļ���������";
			}else{
				boolean ret = CanOperDesOrg(tellerorg, operorg);
				if(ret){
					retflag = 1;
					retmsg = "ֱ���ϼ����������";
				}else{
					retflag = -1;
					retmsg = "�������Ļ��������ڵ�¼��Ա��Ͻ";
				}
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.closeSession(session);
		}
		
		if (retflag == 1) {
			result.setReturnValue(true);
		} else {
			if (retmsg.equals("ֱ���ϼ����������")) {
				result.setReturnValue(true);
			} else {
				result.setReturnValue(false);
			}
		}
		result.setReturnMessage(retmsg);
		
		return result;
	}
	
	/**
	 * @author lhz
	 * @param tellerorg ��ԱID
	 * @param operaccount �������˻�
	 * ����ԭProCanOperAcc�洢�����м���Ա�ܷ�����˻��Ĳ���
	 */
	public CanOperAccReturn ProCanOperAcc(String tellerorg,String operaccount){
		
		Session session = this.getHibernateSession();
		Integer retflag = 1;
		String retmsg = "";
		CanOperAccReturn result = new CanOperAccReturn();
		try{
			Zhanghb zhanghb = ZhanghbDao.getZhanghb(operaccount);
			if(zhanghb == null){
				retflag = -1;
				retmsg = "�˻�������:"+operaccount;
			}else{
				if(tellerorg.equals(zhanghb.getZhangh())){
					retflag = 1;
					retmsg = "�����˻������Բ���";
				}else{
					CanOperAccReturn orgResult = ProCanOperOrg(tellerorg, zhanghb.getJigh());
					if(orgResult.getReturnValue() == true){
						retflag = 1;
						retmsg = "ֱ���ϼ����������";
					}else{
						if("��".equals(zhanghb.getTongctd())){
							retflag = -1;
					    	retmsg = "�˻�Ϊ��ͨ�һ�";
						}else if("��".equals(zhanghb.getTongctd())){
							retflag = 1;
						}else {
							if("ʡ".equals(zhanghb.getTongctd())){
								Org telOrg = (Org) orgDao.getOrgByCode(tellerorg);
								Org accOrg = (Org) orgDao.getOrgByCode(zhanghb.getJigh());
								if((telOrg.getShOrgCode()).equals(accOrg.getShOrgCode())){
									retflag = 1;
									retmsg = "ͬһʡ�У��������";
								}else{
									retflag = -1;
									retmsg = "��ͬһʡ�У����������";
								}
							}else{
								retflag = -1;
								retmsg = "��Ȩ�޲鿴���˻�";
							}
						}
					}
				}
			}
		}catch(HibernateException e){
			e.printStackTrace();
		}finally{
			this.closeSession(session);
		}
		
		if (retflag == 1) {
			result.setReturnValue(true);
		} else {
			result.setReturnValue(false);
		}
		result.setReturnMessage(retmsg);
		
		return result;
	}
	
	/**
	 * @author lhz
	 * @param tellerorg ��½��Ա����
	 * @param clerknum �������Ĺ�ԱID
	 * ����ԭProCanOperAcc�洢�����м���Ա�ܷ����Ŀ���Ա�Ĳ���
	 */
	public CanOperAccReturn ProCanOperTel(String tellerorg,String clerknum){
		
		Session session = this.getHibernateSession();
		Integer retflag = 1;
		String retmsg = "";
		CanOperAccReturn result = new CanOperAccReturn();
		try{
			Clerk clerk = ClerkDao.getClerkByCode(clerknum);
			if(clerk == null){
				retflag = -1;
				retmsg = "��������Ա������";
			}else {		
				CanOperAccReturn orgResult = ProCanOperOrg(tellerorg, clerk.getOrgcode());
				if(orgResult.getReturnValue() == true){
			        retflag = 1;
			        retmsg = "ֱ���ϼ����������";
				} else{
			        retflag = -1;
			        retmsg = "����ѯ��Ա�Ļ��������ڵ�¼��Ա��Ͻ";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			this.closeSession(session);
		}
				
		if (retflag == 1) {
			result.setReturnValue(true);
		} else {
			if (retmsg.equals("ֱ���ϼ����������")) {
				result.setReturnValue(true);
			} else {
				result.setReturnValue(false);
			}
		}
		result.setReturnMessage(retmsg);
		
		return result;
	}


	//��ȡϵͳʱ��
	public String getSystetemNowDate() {
		return SystemDao.getSystetemNowDate();
	}
	public String getSystetemNextDate() {
		return SystemDao.getSystetemNextDate();
	}
	public String getSystetemYestoDay() {
		// TODO Auto-generated method stub
		return SystemDao.getSystetemYestoDay();
	}
	public String getSystemContolParameter(String id) throws Exception {
		SystemControlParameter p = SystemControlParametersDao
				.findSystemControlParameterById(id);
		if (p != null)
			return p.getValue();
		else
			return "";
	}
	
	
	
	
//	/**
//	 * @author wcl
//	 * @param tellerorg ��ԱID
//	 * @param operaccount ������ӡ����
//	 */
//	public CanOperAccReturn ProCanOperYinjk(String tellerorg,String operyinjk){
//		
//		Session session = this.getHibernateSession();
//		Integer retflag = 1;
//		String retmsg = "";
//		CanOperAccReturn result = new CanOperAccReturn();
//		try{
//			Yinjk yinjk = yinjkDao.getYinjkByYinjkbh(operyinjk);
//			if(yinjk == null){
//				retflag = -1;
//				retmsg = "ӡ����������:"+operyinjk;
//			}else{
//				if(tellerorg.equals(yinjk.getJigh())){
//					retflag = 1;
//					retmsg = "������ӡ���������Բ���";
//				}else{
//					CanOperAccReturn orgResult = ProCanOperOrg(tellerorg, yinjk.getJigh());
//					if(orgResult.getReturnValue() == true){
//						retflag = 1;
//						retmsg = "ֱ���ϼ����������";
//					}else{
//						retflag = -1;
//						retmsg = "��ֱ���ϼ������������";
//					}
//				}
//			}
//		}catch(HibernateException e){
//			e.printStackTrace();
//		}finally{
//			this.closeSession(session);
//		}
//		
//		if (retflag == 1) {
//			result.setReturnValue(true);
//		} else {
//			result.setReturnValue(false);
//		}
//		result.setReturnMessage(retmsg);
//		
//		return result;
//	}
	
	public String getSystemSequence() {
		Session session = this.getHibernateSession();
		try {
			String query = "select LSH_SEQUENCE.nextval from dual";
			SQLQuery qu = session.createSQLQuery(query);
			Object result =qu.uniqueResult();
			System.out.println("LSH :"+result);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}finally {
			this.closeSession(session);
		}
	}
	
	//��ȡ��һ��ƾ֤��
	public String getPZHSequence() {
		return SystemDao.getPZHSequence();
	}
	
	
	/**
	 * @param cleaknum ������Ա������
	 * @param zhangh�˻�������
	 * @return �Ƿ������ӡ
	 */
	public boolean CanTongd(String cleaknum, String zhangh) {
		
		
		Clerk clerk=ClerkDao.getClerkByCode(cleaknum);
		Org org_clerk=orgDao.getOrgByCode(clerk.getOrgcode());
		String clerkOrgNum="";
		if(clerk!=null&&org_clerk!=null){
			clerkOrgNum=org_clerk.getCode();
			if(org_clerk.getWdflag().equals("3")&&org_clerk.getLeixbs().equals("4")){
				//��ҵ����ִ���ϼ�������ӡְȨ  һ����������ҵ���Ķ�һ������ȫϽ  ������������ҵ���ĶԶ�������֮��ȫ��֧��
				clerkOrgNum=org_clerk.getParentCode();
			}
		}
		Zhanghb zhanghb =ZhanghbDao.getZhanghb(zhangh);
		if(zhanghb==null||clerk==null||org_clerk==null){
			return false;
		}
		//�ж��Ƿ�Ϊֱ���ϼ�
		CanOperAccReturn orgResult = ProCanOperOrg(clerkOrgNum, zhanghb.getJigh());
		if(orgResult.getReturnValue() == true){
			return true;
		}
		
		String tongdsz=zhanghb.getTongdsz();
		//���ͨ�һ����б����Ƿ��е�ǰ��Ա��������
		if(tongdsz!=null&&!tongdsz.trim().equals("")){
			List<String> orgList=createList(tongdsz);
			if(orgList!=null&&orgList.contains(clerk.getOrgcode().trim())){
				return true;
			}
		}
		String tongdbz=zhanghb.getTongctd();
		Org org_zhangh=orgDao.getOrgByCode(zhanghb.getJigh());
		if(org_zhangh==null){
			return false;
		}
		
		//����ͨ�ұ�־���μ���Ƿ����ͨ��
		if(tongdbz==null||tongdbz.equals("")){
			return false;
		}
		if(tongdbz.equals("ȫ��")){
			return true;
		}
		if(tongdbz.equals("һ������ȫϽ")){
			//��Ա���ڻ������˻����ڻ�����ʡ�л�������ͬ ����һ������ȫϽͨ�ҹ��� ����ͨ��
			if(clerk.getShOrgCode()!=null&&org_zhangh.getShOrgCode()!=null&&clerk.getShOrgCode().equals(org_zhangh.getShOrgCode())){
				return true;
			}
		}
		if(tongdbz.equals("һ�����б���")){
			//��Ա��������һ�����У��˺Ż�������һ�����У���Ա����һ�������кŵ����˺�����һ�����У�����һ�����б���ͨ�ҹ��� ����ͨ��
			if(org_clerk.getShOrgCode().equals(org_clerk.getParentCode())&&org_zhangh.getShOrgCode().equals(org_zhangh.getParentCode())&&org_clerk.getParentCode().equals(org_zhangh.getParentCode())){
				return true;
			}
		}
		if(tongdbz.equals("��������")){
			//����Ա���ڻ����Ƿ�����ڶ�������
			if(org_clerk==null||org_clerk.getShOrgCode().equals(org_clerk.getParentCode())){
				return false;
			}
			//����˻����ڻ����Ƿ�����ڶ�������
			if(org_zhangh==null||org_zhangh.getShOrgCode().equals(org_zhangh.getParentCode())){
				return false;
			}
			//��Ա���ڻ������˻����ڻ���ͬΪ֧�� �������ϼ�������ͬʱ����϶�������ͨ�Ĺ��� ����ͨ��
			if(org_zhangh.getWdflag().equals("3")&&org_clerk.getWdflag().equals("3")&&org_clerk.getParentCode().equals(org_zhangh.getParentCode())){
				return true;
			}
		}
		if(tongdbz.equals("��ͨ��")){
			return false;
		}
		return false;
	}
	// ���ӡ�������ַ���
	private List<String> createList(String string) {

		List<String> list = new ArrayList<String>();
		if(string ==null||string.trim().equals("")){
			return list;
		}
		if (string != null && string.length() != 0
				&& string.indexOf("|") != -1) {
			string = string.replace('|', ',');
		}
		String[] str = string.split(",", 0);
		for (int i = 0; i < str.length; i++) {
			String ele=str[i];
			if(ele!=null&&!ele.trim().equals("")){
				list.add(ele);
			}
		}
		return list;
	}

}
