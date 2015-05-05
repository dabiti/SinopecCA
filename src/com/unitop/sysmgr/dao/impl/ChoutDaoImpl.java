package com.unitop.sysmgr.dao.impl;

/***********************************************************************
 * Module:  ChoutDaoImpl.java
 * Author:  Administrator
 * Purpose: Defines the Class ChoutDaoImpl
 ***********************************************************************/

import java.util.*;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Chout;
import com.unitop.sysmgr.bo.ChoutId;
import com.unitop.sysmgr.dao.ChoutDao;

@Repository("ChoutDaoImpl")
public class ChoutDaoImpl extends BaseDataResources implements ChoutDao {

	public void saveChout(Chout chout) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(chout);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void deleteChout(String kagid) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			String hql = "delete from Chout where choutid.kagid=:kagid";
			Query query = session.createQuery(hql);
			query.setString("kagid", kagid);
			query.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
//获取剩余空间大于count的抽屉
	public Chout getElseChout(String jigh, int count) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "from Chout where choutzt='0' and shengykj>=" + count + " and jigh='"
				+ jigh + "' order by shiykj desc,kagid,ceng,choutwz";
		Chout chout = null;
		try {
			Query query = session.createQuery(hql);
			List<Chout> list = query.list();
			if(list!=null&&list.size()!=0){
				chout = list.get(0);
			}
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return chout;
	}
//更新抽屉状态
	public void updateChoutState(ChoutId choutid, String state) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "update chout set choutzt=:choutzt where kagid=:kagid and ceng=:ceng and choutwz=:choutwz";
		try {
			SQLQuery query = session.createSQLQuery(hql);
			query.setString("choutzt", state);
			query.setString("kagid", choutid.getKagid());
			query.setString("ceng", choutid.getCeng());
			query.setString("choutwz", choutid.getChoutwz());
			query.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
//更新抽屉空间的值
	public void updateChoutCount(ChoutId choutid, int count) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "update chout set shengykj=shengykj-"+count+",shiykj=shiykj+"+count+" where " +
				"kagid='"+choutid.getKagid()+"' and ceng='"+choutid.getCeng()+"' and choutwz='"+choutid.getChoutwz()+"'";
		try {
			SQLQuery sqlQuery = session.createSQLQuery(hql);
			sqlQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
//获取该卡柜所有的抽屉
	public List<Chout> getChoutSpaceByKagId(String kagid) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String sql = "from Chout where kagid='"+kagid+"'";
		List<Chout> list=new ArrayList<Chout>();
		try {
			Query query = session.createQuery(sql);
			list = query.list();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public Chout getChoutById(ChoutId choutid) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Chout chout = new Chout();
		try {
			chout = (Chout) session.get(Chout.class, choutid);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return chout;
	}
  //获取这个卡柜已使用抽屉的数量
	public List<Chout> getUsedChout(String kagid) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "from Chout where shiykj<>'0' and kagid='" + kagid + "'";
		List<Chout> list =new ArrayList<Chout>();
		try {
			Query query = session.createQuery(hql);
			list = query.list();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	//释放抽屉空间
	public void releaseChoutSpace(ChoutId choutid, int count) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "update chout set shengykj=shengykj+"+count+",shiykj=shiykj-"+count+" where " +
				"kagid='"+choutid.getKagid()+"' and ceng='"+choutid.getCeng()+"' and choutwz='"+choutid.getChoutwz()+"'";
		try {
			SQLQuery sqlQuery = session.createSQLQuery(hql);
			sqlQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
}