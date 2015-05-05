package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.unitop.sysmgr.bo.Jicgncd;
import com.unitop.sysmgr.dao.BasicFunctionDao;

@Repository("BasicFunctionDaoImpl")
public class BasicFunctionDaoImpl extends BaseDataResources implements
		BasicFunctionDao {

	public Jicgncd select(Jicgncd jicg) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Jicgncd jicgn = (Jicgncd)session.get(Jicgncd.class, jicg.getId());
			session.flush();
			return jicgn;
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void insert(Jicgncd jicg) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(jicg);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public Jicgncd getPost(Jicgncd jicg) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		Jicgncd jicgn=null;
		try {
			List list = session.createQuery("from Jicgncd where quanxwz='"+jicg.getQuanxwz()+"'").list();
			session.flush();
			if(list.size() != 0){ jicgn = (Jicgncd) list.get(0);}
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return jicgn;
	}

}
