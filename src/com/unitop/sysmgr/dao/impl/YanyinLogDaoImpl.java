package com.unitop.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Zhengprz;
import com.unitop.sysmgr.bo.ZhengprzId;
import com.unitop.sysmgr.dao.YanyinLogDao;
@Repository("YanyinLogDaoImpl")
public class YanyinLogDaoImpl extends BaseDataResources  implements YanyinLogDao {
	
	//返回整票 票据号笔数
	public int validateZhengppjh(String piaojh) {
		int totalRows = 0;
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String hql = "select count(id.account) from Zhengprz where checknum=:piaojh";
			Query query = session.createQuery(hql);
			query.setString("piaojh", piaojh);
			List rList = query.list();
			totalRows = Integer.valueOf(rList.get(0).toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return totalRows;
	}

	//返回单章  票据号笔数
	public int validateDanzppjh(String piaojh) {
		int totalRows = 0;
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String hql = "select count(account) from Danzrz where checknum=:piaojh";
			Query query = session.createQuery(hql);
			query.setString("piaojh", piaojh);
			List rList = query.list();
			totalRows = Integer.valueOf(rList.get(0).toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return totalRows;
	}
	
	//返回整票 结果
	public Zhengprz getZhengp(String id) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String sql = "select * from credencechecklog where id =:id";
			Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("id", id);
			Map resultMap  = (Map) query.uniqueResult();
			
			Zhengprz zprz = new Zhengprz();
			ZhengprzId zhengpId = new ZhengprzId();
			
			zhengpId.setAccount(String.valueOf(resultMap.get("ZHANGH")==null?"":resultMap.get("ZHANGH")));
			zhengpId.setChecknum(String.valueOf(resultMap.get("CHECKNUM")==null?"":resultMap.get("CHECKNUM")));
			zhengpId.setMoney(String.valueOf(resultMap.get("MONEY")==null?"":resultMap.get("MONEY")));
			zhengpId.setCheckdate(String.valueOf(resultMap.get("CHECKDATE")==null?"":resultMap.get("CHECKDATE")));
			zhengpId.setChecktime(String.valueOf(resultMap.get("CHECKTIME")==null?"":resultMap.get("CHECKTIME")));
			zprz.setId(zhengpId);
			
			zprz.setIdd(String.valueOf(resultMap.get("ID")==null?"":resultMap.get("ID")));
			zprz.setIp(String.valueOf(resultMap.get("IP")==null?"":resultMap.get("IP")));
			zprz.setCredencetype(String.valueOf(resultMap.get("CREDENCETYPE")==null?"":resultMap.get("CREDENCETYPE")));
			zprz.setClerknum(String.valueOf(resultMap.get("CLERKNUM")==null?"":resultMap.get("CLERKNUM")));
			zprz.setClerkname(String.valueOf(resultMap.get("CLERKNAME")==null?"":resultMap.get("CLERKNAME")));
			zprz.setDoublesignatureclerknum(String.valueOf(resultMap.get("DOUBLESIGNATURECLERKNUM")==null?"":resultMap.get("DOUBLESIGNATURECLERKNUM")));
			zprz.setDoublesignatureclerkname(String.valueOf(resultMap.get("DOUBLESIGNATURECLERKNAME")==null?"":resultMap.get("DOUBLESIGNATURECLERKNAME")));
			zprz.setCheckresult(String.valueOf(resultMap.get("CHECKRESULT")==null?"":resultMap.get("CHECKRESULT")));
			zprz.setCheckmode(String.valueOf(resultMap.get("CHECKMODE")==null?"":resultMap.get("CHECKMODE")));
			zprz.setRemark(String.valueOf(resultMap.get("REMARK")==null?"":resultMap.get("REMARK")));
			zprz.setZuhgz(String.valueOf(resultMap.get("ZUHGZ")==null?"":resultMap.get("ZUHGZ")));
			zprz.setClerkorgcode(String.valueOf(resultMap.get("INTERNALORGANIZATIONNUMBER")==null?"":resultMap.get("INTERNALORGANIZATIONNUMBER")));
			zprz.setChuprq(String.valueOf(resultMap.get("CHUPRQ")==null?"":resultMap.get("CHUPRQ")));
			zprz.setXitlx(String.valueOf(resultMap.get("XITLX")==null?"":resultMap.get("XITLX")));
			zprz.setYanybs(String.valueOf(resultMap.get("YANYBS")==null?"":resultMap.get("YANYBS")));
			zprz.setPingzbsm(String.valueOf(resultMap.get("PINGZBSM")==null?"":resultMap.get("PINGZBSM")));
			
			return zprz;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
	}
	
	
	
	// 更新任务信息日志添加复核柜员
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "update CI_RENWXX set  clerkid2 = :clerkid2 where ID = :renwbs and xitbs=:systemid ";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("clerkid2", clerkid2);
			query.setString("renwbs", taskid2);
			query.setString("systemid", systemid);
			int i= query.executeUpdate();
			if(i>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	
	// 更新整票日志添加复核柜员
	public boolean addClerk2OfZhengprz(String taskid,String clerkid) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "update CREDENCECHECKLOG set  DOUBLESIGNATURECLERKNUM = :clerkid2 where ID = :taskid";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("clerkid2", clerkid);
			query.setString("taskid", taskid);
			int i= query.executeUpdate();
			if(i>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	
}