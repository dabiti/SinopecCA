package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.dao.YinjlxDao;

/*
 * 印鉴类型获取实现
 */
@Repository("YinjlxDaoImpl")
public class YinjlxDaoImpl extends BaseDataResources implements YinjlxDao {

	// 获取印鉴类型
	public List getYinjlxList() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Yinjlxb");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
}
