package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Juesqxgxb;
import com.unitop.sysmgr.bo.Privilege;
import com.unitop.sysmgr.dao.PrivilegeDao;

@Repository("PrivilegeDaoImpl")
public class PrivilegeDaoImpl extends BaseDataResources implements PrivilegeDao {

	// 加载全部权限 -来源 产品定制表
	public List getAllPrivilege() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			String queryString = "from Chanpgncd order by gongnsx ";
			list = session.createQuery(queryString).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
			
		}
		return list;
	}

	// 加载全部权限 -来源 服务角色管理
	public List getAllPrivilegeForJuesgl() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			String queryString = "from Chanpgncd where id.gongnid<>'1'  order by gongnsx ";
			list = session.createQuery(queryString).list();
			if(list==null)
			{
				list = new ArrayList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	// 依据角色ID加载权限
	public List<Privilege> getPrivilegeByJuesid(String juesid) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query=session.createQuery("from Juesqxgxb where id.juesid=:id");
			query.setString("id", juesid);
			list = (List)query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	// 批量更新角色权限
	public void saveJuesqxgxbList(List JuesqxgxbList) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			int size = JuesqxgxbList.size();
			for (int i = 0; i < size; i++)
			{
				Juesqxgxb juesqxgx = (Juesqxgxb) JuesqxgxbList.get(i);
				session.saveOrUpdate(juesqxgx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	// 删除指定角色下的权限
	public void deleteJuesqxgxb(String juesid) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "delete Juesqxgxb where juesid=:juesid";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("juesid", juesid);
			query.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
}
