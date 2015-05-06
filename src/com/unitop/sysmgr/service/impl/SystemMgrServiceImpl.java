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
	 * 针对作业处理中心的权限校验
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
	 * @param tellerorg 柜员ID
	 * @param operorg 被操作机构号
	 * 检查柜员能否操作该机构
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
				retmsg = "被操作的机构不存在";
			}else{
				boolean ret = CanOperDesOrg(tellerorg, operorg);
				if(ret){
					retflag = 1;
					retmsg = "直接上级，允许操作";
				}else{
					retflag = -1;
					retmsg = "被操作的机构不属于登录柜员管辖";
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
			if (retmsg.equals("直接上级，允许操作")) {
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
	 * @param tellerorg 柜员ID
	 * @param operaccount 被操作账户
	 * 代替原ProCanOperAcc存储过程中检查柜员能否操作账户的部分
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
				retmsg = "账户不存在:"+operaccount;
			}else{
				if(tellerorg.equals(zhanghb.getZhangh())){
					retflag = 1;
					retmsg = "本人账户，可以操作";
				}else{
					CanOperAccReturn orgResult = ProCanOperOrg(tellerorg, zhanghb.getJigh());
					if(orgResult.getReturnValue() == true){
						retflag = 1;
						retmsg = "直接上级，允许操作";
					}else{
						if("非".equals(zhanghb.getTongctd())){
							retflag = -1;
					    	retmsg = "账户为非通兑户";
						}else if("国".equals(zhanghb.getTongctd())){
							retflag = 1;
						}else {
							if("省".equals(zhanghb.getTongctd())){
								Org telOrg = (Org) orgDao.getOrgByCode(tellerorg);
								Org accOrg = (Org) orgDao.getOrgByCode(zhanghb.getJigh());
								if((telOrg.getShOrgCode()).equals(accOrg.getShOrgCode())){
									retflag = 1;
									retmsg = "同一省行，允许操作";
								}else{
									retflag = -1;
									retmsg = "非同一省行，不允许操作";
								}
							}else{
								retflag = -1;
								retmsg = "无权限查看该账户";
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
	 * @param tellerorg 登陆柜员机构
	 * @param clerknum 被操作的柜员ID
	 * 代替原ProCanOperAcc存储过程中检查柜员能否操作目标柜员的部分
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
				retmsg = "被操作柜员不存在";
			}else {		
				CanOperAccReturn orgResult = ProCanOperOrg(tellerorg, clerk.getOrgcode());
				if(orgResult.getReturnValue() == true){
			        retflag = 1;
			        retmsg = "直接上级，允许操作";
				} else{
			        retflag = -1;
			        retmsg = "被查询柜员的机构不属于登录柜员管辖";
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
			if (retmsg.equals("直接上级，允许操作")) {
				result.setReturnValue(true);
			} else {
				result.setReturnValue(false);
			}
		}
		result.setReturnMessage(retmsg);
		
		return result;
	}


	//获取系统时间
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
//	 * @param tellerorg 柜员ID
//	 * @param operaccount 被操作印鉴卡
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
//				retmsg = "印鉴卡不存在:"+operyinjk;
//			}else{
//				if(tellerorg.equals(yinjk.getJigh())){
//					retflag = 1;
//					retmsg = "本机构印鉴卡，可以操作";
//				}else{
//					CanOperAccReturn orgResult = ProCanOperOrg(tellerorg, yinjk.getJigh());
//					if(orgResult.getReturnValue() == true){
//						retflag = 1;
//						retmsg = "直接上级，允许操作";
//					}else{
//						retflag = -1;
//						retmsg = "非直接上级，不允许操作";
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
	
	//获取下一个凭证号
	public String getPZHSequence() {
		return SystemDao.getPZHSequence();
	}
	
	
	/**
	 * @param cleaknum 操作柜员机构号
	 * @param zhangh账户机构号
	 * @return 是否可以验印
	 */
	public boolean CanTongd(String cleaknum, String zhangh) {
		
		
		Clerk clerk=ClerkDao.getClerkByCode(cleaknum);
		Org org_clerk=orgDao.getOrgByCode(clerk.getOrgcode());
		String clerkOrgNum="";
		if(clerk!=null&&org_clerk!=null){
			clerkOrgNum=org_clerk.getCode();
			if(org_clerk.getWdflag().equals("3")&&org_clerk.getLeixbs().equals("4")){
				//作业中心执行上级机构验印职权  一级分行下作业中心对一级分行全辖  二级分行下作业中心对二级分行之下全部支行
				clerkOrgNum=org_clerk.getParentCode();
			}
		}
		Zhanghb zhanghb =ZhanghbDao.getZhanghb(zhangh);
		if(zhanghb==null||clerk==null||org_clerk==null){
			return false;
		}
		//判断是否为直接上级
		CanOperAccReturn orgResult = ProCanOperOrg(clerkOrgNum, zhanghb.getJigh());
		if(orgResult.getReturnValue() == true){
			return true;
		}
		
		String tongdsz=zhanghb.getTongdsz();
		//检查通兑机构列表中是否有当前柜员所属机构
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
		
		//根据通兑标志依次检查是否可以通兑
		if(tongdbz==null||tongdbz.equals("")){
			return false;
		}
		if(tongdbz.equals("全国")){
			return true;
		}
		if(tongdbz.equals("一级分行全辖")){
			//柜员所在机构和账户所在机构的省行机构号相同 符合一级分行全辖通兑规则 可以通兑
			if(clerk.getShOrgCode()!=null&&org_zhangh.getShOrgCode()!=null&&clerk.getShOrgCode().equals(org_zhangh.getShOrgCode())){
				return true;
			}
		}
		if(tongdbz.equals("一级分行本级")){
			//柜员机构归属一级分行，账号机构归属一级分行，柜员机构一级分行行号等于账号所在一级分行，符合一级分行本级通兑规则 可以通兑
			if(org_clerk.getShOrgCode().equals(org_clerk.getParentCode())&&org_zhangh.getShOrgCode().equals(org_zhangh.getParentCode())&&org_clerk.getParentCode().equals(org_zhangh.getParentCode())){
				return true;
			}
		}
		if(tongdbz.equals("二级分行")){
			//检查柜员所在机构是否归属于二级分行
			if(org_clerk==null||org_clerk.getShOrgCode().equals(org_clerk.getParentCode())){
				return false;
			}
			//检查账户所在机构是否归属于二级分行
			if(org_zhangh==null||org_zhangh.getShOrgCode().equals(org_zhangh.getParentCode())){
				return false;
			}
			//柜员所在机构和账户所在机构同为支行 并且其上级银行相同时则符合二级分行通的规则 可以通兑
			if(org_zhangh.getWdflag().equals("3")&&org_clerk.getWdflag().equals("3")&&org_clerk.getParentCode().equals(org_zhangh.getParentCode())){
				return true;
			}
		}
		if(tongdbz.equals("不通兑")){
			return false;
		}
		return false;
	}
	// 拆分印鉴卡号字符串
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
