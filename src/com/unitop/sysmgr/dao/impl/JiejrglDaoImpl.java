package com.unitop.sysmgr.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.JiejrBo;
import com.unitop.sysmgr.dao.JiejrglDao;

//节假日管理 实现类
@Repository("JiejrglDaoImpl")
public class JiejrglDaoImpl extends BaseDataResources implements JiejrglDao {
	
	public JiejrBo getJiejrBo(String year) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		JiejrBo jiejrBo = null;
		try {
			jiejrBo = (JiejrBo) session.get(JiejrBo.class, year);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return jiejrBo;
	}

	public void saveJiejrBo(JiejrBo jiejrBo) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(jiejrBo);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}

	}

	public void updateJiejrBo(JiejrBo JiejrBo) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(JiejrBo);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	
	public void deleteJiejrBo(JiejrBo JiejrBo){
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.delete(JiejrBo);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

}
