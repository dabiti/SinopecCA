package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Dayfwcs;
import com.unitop.sysmgr.dao.DayfwDao;

@Repository("DayfwDaoImpl")
public class DayfwDaoImpl extends BaseDataResources implements DayfwDao {

	public void delete(String dayfwid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String hql = "delete from Dayfwcs d where d.dayfwid = '"+dayfwid+"'";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void save(Dayfwcs dayfwcs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.save(dayfwcs);
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public List selectAll() {
		Session session = getBaseHibernateDao().getHibernateSession();
		List<Dayfwcs> list=null;
		try {
			Query query = session.createQuery("from Dayfwcs d order by d.dayfwmc");
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public void update(Dayfwcs dayfwcs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.update(dayfwcs);
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public Dayfwcs getDayfwcs(String dayfwid) {
		Session session = getBaseHibernateDao().getHibernateSession();
		Dayfwcs dayfwcs=null;
		try {
			dayfwcs = (Dayfwcs) session.get(Dayfwcs.class, dayfwid);
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return dayfwcs;
	}

	public void updateDaysl(String dayfwid, int currentCount) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("update Dayfwcs d set d.dangqsl = d.dangqsl+"+currentCount+" where d.dayfwid = '"+dayfwid+"'");
		    query.executeUpdate();
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
}
