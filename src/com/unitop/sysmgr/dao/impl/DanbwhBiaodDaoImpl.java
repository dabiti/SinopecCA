package com.unitop.sysmgr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.DanbwhBiaod;
import com.unitop.sysmgr.bo.UnionBiaod;
import com.unitop.sysmgr.dao.DanbwhBiaodDao;

@Repository("DanbwhBiaodDaoImpl")
public class DanbwhBiaodDaoImpl  extends BaseDataResources  implements DanbwhBiaodDao {

	public void add(DanbwhBiaod danbwh) throws SQLException {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try{
			session.save(danbwh);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
	}

	public void delete(UnionBiaod biaod) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		DanbwhBiaod danbwh = new DanbwhBiaod();
		try {
			danbwh = (DanbwhBiaod) session.load(DanbwhBiaod.class, biaod);
			session.delete(danbwh);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public DanbwhBiaod find(UnionBiaod biaod) throws SQLException {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		DanbwhBiaod danbwh=null;
		try {
			danbwh = (DanbwhBiaod) session.get(DanbwhBiaod.class, biaod);
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return danbwh;
	}
	    
	public List getAll(String gongnid) throws SQLException {
		List list = null;
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from DanbwhBiaod  where gongnid=:gongnid order by xianssx");
			query.setString("gongnid", gongnid);
			list = query.list();
			if(list == null) list = new ArrayList();	
			return list;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public void update(DanbwhBiaod danbwh) throws SQLException {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try{
			session.update(danbwh);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
}