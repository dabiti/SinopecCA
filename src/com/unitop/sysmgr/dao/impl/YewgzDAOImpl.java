package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Yewgz; 
import com.unitop.sysmgr.dao.YewgzDAO;
@Repository("YewgzDAOImpl")
public class YewgzDAOImpl extends BaseDataResources implements YewgzDAO{

	public List<Yewgz> findAllYewgz() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Yewgz> list=null;
		try {
			Query query = session.createQuery("from Yewgz order by yuansname");
			list = query.list();
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	public void deleteYewgz(String yuansid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			Yewgz yewgz = (Yewgz) session.get(Yewgz.class, yuansid);
			session.delete(yewgz);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}

	public void insertYewgz(Yewgz yewgz) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(yewgz);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		
	}

	public void updateYewgz(Yewgz yewgz) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(yewgz);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}

	public Yewgz findYewgz(String yuansid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Yewgz yewgz=null;
		try {
			yewgz = (Yewgz) session.get(Yewgz.class, yuansid);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return yewgz;
	}

}
