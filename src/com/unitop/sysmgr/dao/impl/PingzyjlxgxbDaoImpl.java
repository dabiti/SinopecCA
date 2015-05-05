package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Pingzyjlxgxb;
import com.unitop.sysmgr.dao.PingzyjlxgxbDao;
/*
 * 获取印鉴类型实现
 */
@Repository("PingzyjlxgxbDaoImpl")
public class PingzyjlxgxbDaoImpl extends BaseDataResources implements PingzyjlxgxbDao{

	public List  getPingzyjlxgxbList() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Pingzyjlxgxb");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	/*
	 * 保存更新印鉴类型表
	 */
	public void saveOrUpdate(Pingzyjlxgxb pingzyjlxgxb) {
		Session session = this.getBaseHibernateDao().getHibernateSession();//连接数据库所必须操作
		try{
			session.saveOrUpdate(pingzyjlxgxb);
		}catch(Exception e){
			e.printStackTrace();
			}finally{
				this.getBaseHibernateDao().closeSession(session);
			}
		}
	public List getyinjList(String jigh, String pingzh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			 Query query = session.createQuery("from Pingzyjlxgxb where jigh=:jigh and pingzbs=:pingzh");
			 query.setString("jigh", jigh);
			 query.setString("pingzh", pingzh);
			 list = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	//删除印鉴类型关系数据
	public void deleteyjList(String jigh, String pingzh){
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			 Query query = session.createQuery("delete pingzyjlxgxb where internalorganizationnumber=:jigh and pingzbs=:pingzh");
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











