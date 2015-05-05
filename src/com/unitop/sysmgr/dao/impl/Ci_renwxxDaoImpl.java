package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Ci_renwxx;
import com.unitop.sysmgr.bo.Zhengprz;
import com.unitop.sysmgr.dao.Ci_renwxxDao;

@Repository("ci_renwxxDaoImpl")
public class Ci_renwxxDaoImpl extends BaseDataResources implements Ci_renwxxDao {

	// 加载任务信息
	public Ci_renwxx getRenwxx(String renwbs) {
		Ci_renwxx ci_renwxx = null;
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Ci_renwxx  where renwbs=:renwbs");
			query.setString("renwbs", renwbs);
			ci_renwxx = (Ci_renwxx) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return ci_renwxx;
	}
	
	
	// 按条件查询批次任务
	public ArrayList<Ci_renwxx> getRenwxxList(String batchid ,String chulzt) {
		String hql = "from Ci_renwxx ";
		boolean batchidOk = batchid!=null&&!"".equals(batchid);
		boolean chulztOk = chulzt!=null&&!"".equals(chulzt);
		Map<String, String>paramMap=new HashMap<String, String>();
		if(batchidOk&&chulztOk){
			hql+= " where batchid =:batchid and chulzt =:chulzt ";
			paramMap.put("batchid", batchid);
			paramMap.put("chulzt", chulzt);
		}else if(batchidOk){
			hql+= " where batchid =:batchid ";
			paramMap.put("batchid", batchid);
		}else if(chulztOk){
			hql+= " where chulzt =:chulzt ";
			paramMap.put("chulzt", chulzt);
		}
		//System.out.println("hql:"+hql);
		Session session = this.getBaseHibernateDao().getHibernateSession();
		ArrayList<Ci_renwxx> renwxxList = new ArrayList<Ci_renwxx>();
		try{
			Query query = session.createQuery(hql);
			if(paramMap.size()>0){
				Set<String> keys=paramMap.keySet();
				for (String key : keys) {
					query.setString(key, paramMap.get(key));
				}
			}
			renwxxList = (ArrayList<Ci_renwxx>) query.list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return renwxxList;
	}
	
	
	
	//按条件查询数量
	public int getCountFromList(String batchid ,String chulzt) {
		int count = 0;
		String sql = "select count(*) from ci_renwxx ";
		boolean batchidOk = batchid!=null&&!"".equals(batchid);
		boolean chulztOk = chulzt!=null&&!"".equals(chulzt);
		Map<String, String>paramMap=new HashMap<String, String>();
		if(batchidOk&&chulztOk){
			sql+= " where batchid =:batchid and chulzt =:chulzt ";
			paramMap.put("batchid", batchid);
			paramMap.put("chulzt", chulzt);
		}else if(batchidOk){
			sql+= " where batchid =:batchid ";
			paramMap.put("batchid", batchid);
		}else if(chulztOk){
			sql+= " where chulzt =:chulzt ";
			paramMap.put("chulzt", chulzt);
		}
		//System.out.println("sql:"+sql);
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createSQLQuery(sql);
			if(paramMap.size()>0){
				Set<String> keys=paramMap.keySet();
				for (String key : keys) {
					query.setString(key, paramMap.get(key));
				}
			}
			count = Integer.valueOf(String.valueOf(query.uniqueResult()).trim());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return count;
	}

	// 更新结果
	public boolean updateForAccount(Ci_renwxx taskid1,Zhengprz taskid2,String clerkid1,String clerkid2) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "update ci_renwxx set yanyms =:yanyms , yanyjg = :yanyjg , clerkid1 = :clerkid1 , clerkid2 = :clerkid2 where renwbs = :renwbs";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("yanyms", taskid2.getCheckmode());
			query.setString("yanyjg", taskid2.getCheckresult());
			query.setString("clerkid1", clerkid1);
			query.setString("clerkid2", clerkid2);
			query.setString("renwbs", taskid1.getCi_renwxxid().getRenwbs());
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
	
	
	//根据组合主键查询任务
	public Ci_renwxx getRenwxxById(String systemid,String renwbs) {
		Ci_renwxx ci_renwxx = null;
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Ci_renwxx  where renwbs=:renwbs and xitbs=:systemid ");
			query.setString("renwbs", renwbs);
			query.setString("systemid", systemid);
			ci_renwxx = (Ci_renwxx) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return ci_renwxx;
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
}
