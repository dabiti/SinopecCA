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

	// ����ȫ��Ȩ�� -��Դ ��Ʒ���Ʊ�
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

	// ����ȫ��Ȩ�� -��Դ �����ɫ����
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

	// ���ݽ�ɫID����Ȩ��
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

	// �������½�ɫȨ��
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

	// ɾ��ָ����ɫ�µ�Ȩ��
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
