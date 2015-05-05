package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Tisxx;
import com.unitop.sysmgr.dao.TisxxDAO;
@Repository("TisxxDAOImpl")
public class TisxxDAOImpl extends BaseDataResources implements TisxxDAO {

	public void deleteTisxx(String msgId) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			Tisxx tisxx = (Tisxx) session.get(Tisxx.class, msgId);
			session.delete(tisxx);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}

	public List<Tisxx> findAllTisxx() {
		Session session  = getBaseHibernateDao().getHibernateSession();
		String hql = "from Tisxx t order by t.msgId";
		List<Tisxx> list=null;
		try {
			Query query = session.createQuery(hql);
			list = query.list();
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public Tisxx findTisxx(String msgId) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Tisxx tisxx=null;
		try {
			tisxx = (Tisxx) session.get(Tisxx.class, msgId);
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return tisxx;
	}

	public void insertTisxx(Tisxx tisxx) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(tisxx);
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}

	public void updateTisxx(Tisxx tisxx) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(tisxx);
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
}
