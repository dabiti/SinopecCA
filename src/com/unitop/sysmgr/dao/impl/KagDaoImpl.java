package com.unitop.sysmgr.dao.impl;

/***********************************************************************
 * Module:  KagDaoImpl.java
 * Author:  Administrator
 * Purpose: Defines the Class KagDaoImpl
 ***********************************************************************/

import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Kag;
import com.unitop.sysmgr.dao.KagDao;

@Repository("KagDaoImpl")
public class KagDaoImpl extends BaseDataResources implements KagDao {
	public void save(Kag kag) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(kag);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/** @param kag */
	public void update(Kag kag) {
		// TODO: implement
	}

	/** @param jigh */
	public List<Kag> kaglist(String orgcode) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List<Kag> list=new ArrayList<Kag>();
		try {
			String hql = "from Kag where internalorganizationnumber = :orgcode ";
			Query query = session.createQuery(hql);
			query.setString("orgcode", orgcode);
			list = query.list();
			session.flush();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	/** @param kagid */
	public void delete(String kagid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {

			Kag kag = (Kag) session.get(Kag.class, kagid);
			session.delete(kag);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/** @param kagid */
	public Kag getKagById(String kagid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Kag kag = null;
		try {
			kag = (Kag) session.get(Kag.class, kagid);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return kag;
	}
}