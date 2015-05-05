package com.unitop.sysmgr.dao.impl;

import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.ChoutId;
import com.unitop.sysmgr.bo.Kag;
import com.unitop.sysmgr.bo.KagRenw;
import com.unitop.sysmgr.dao.KagRenwDao;

@Repository("KagRenwDaoImpl")
public class KagRenwDaoImpl extends BaseDataResources implements KagRenwDao {

	public List<KagRenw> getTaskByZhangh(String zhangh, String jigh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "";
		if (zhangh == null || "".equals(zhangh)) {
			hql = "from KagRenw where renwzt<>'1'and internalorganizationnumber = '" + jigh + "'";
		} else {
			hql = "from KagRenw where renwzt<>'1' and zhangh = '" + zhangh + "'";
		}
		List<KagRenw> renwlist=new ArrayList<KagRenw>();
		try {
			Query query = session.createQuery(hql);
			renwlist = query.list();
			session.flush();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return renwlist;
	}
	public void updateZuob(ChoutId choutid, String renwbs) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = " update KagRenw set zuob=:zuob where renwbs=:renwbs";
		try {

			Query query = session.createQuery(hql);
			String zuob = choutid.getKagid() + "," + choutid.getCeng() + ","
					+ choutid.getChoutwz();
			query.setString("zuob", zuob);
			query.setString("renwbs", renwbs);
			query.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void createKagRenw(KagRenw kagrenw) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(kagrenw);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void updateTaskState(String renwbs, String renwzt) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "update kagrw set renwzt='"+renwzt+"',caozsj=to_char(sysdate,'hh24:mi:ss'),caozrq=to_char(sysdate,'yyyy-mm-dd') where renwbs = '"+renwbs+"'";
		try {
			SQLQuery sqlQuery = session.createSQLQuery(hql);
			sqlQuery.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public void deleteKagRenw(String renwbs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {

			KagRenw kagRenw = (KagRenw) session.get(KagRenw.class, renwbs);
			session.delete(kagRenw);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public KagRenw getTaskById(String renwbs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		KagRenw kagRenw = null;
		try {

			kagRenw = (KagRenw) session.get(KagRenw.class, renwbs);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return kagRenw;
	}

}