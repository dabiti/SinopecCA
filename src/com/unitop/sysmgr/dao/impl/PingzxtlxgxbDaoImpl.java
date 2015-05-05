package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Pingzxtlxgxb;
import com.unitop.sysmgr.dao.PingzxtlxgxbDao;

@Repository("PingzxtlxgxbDaoImpl")
public class PingzxtlxgxbDaoImpl extends BaseDataResources implements PingzxtlxgxbDao{

	/*
	 * 配置标识获取实现
	 */
	public List getPingzxtlxgxbList() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Pingzxtlxgxb");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	/*
	 * 保存或更新 凭证系统类型表
	 */
	public void saveOrUpdate(Pingzxtlxgxb pingzxtlxgxb) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			//前提是 表必须设置主键 并在HIBERNATE中也配置了主键
			//有一个特色就是 
			//1会自动检测 如果数据库已经有这条记录 更新
			//2如果检查没有这条记录 就保存
			session.saveOrUpdate(pingzxtlxgxb);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	//查询凭证系统关系数据
	public List getxitList(String jigh, String pingzbh){
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			 Query query = session.createQuery("from Pingzxtlxgxb where jigh=:jigh and pingzbs=:pingzh");
			 query.setString("jigh", jigh);
			 query.setString("pingzh", pingzbh);
			 list = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
			return list;
		}
	}
	  //删除系统类型关系数据
	public void deletexitList(String jigh, String pingzh){
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			 Query query = session.createQuery("delete Pingzxtlxgxb where internalorganizationnumber=:jigh and pingzbs=:pingzh");
			 query.setString("jigh", jigh);
			 query.setString("pingzh", pingzh);
			 query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
     }
	
}
