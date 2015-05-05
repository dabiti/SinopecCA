package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Danbwhgxb;
import com.unitop.sysmgr.dao.DanbwhgxbDAO;

@Repository("DanbwhgxbDAOImpl")
public class DanbwhgxbDAOImpl  extends BaseDataResources implements DanbwhgxbDAO {

	public void delete(Danbwhgxb danbwhgxb) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.delete(danbwhgxb);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	public void save(Danbwhgxb danbwhgxb) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(danbwhgxb);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public Danbwhgxb findById(String id) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			return (Danbwhgxb) session.get(Danbwhgxb.class,id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return null;
	}

	public List findDanbwhgxbList(String zhubbh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "from Danbwhgxb where zhubbh=:zhubbh order by zhubbh,zibbh";
		try {
			 Query query = session.createQuery(hql);
			 query.setString("zhubbh",zhubbh);
			 return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return new ArrayList();
	}

	public void update(Danbwhgxb danbwhgxb) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(danbwhgxb);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
}