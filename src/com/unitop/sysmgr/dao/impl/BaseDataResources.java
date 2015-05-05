package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.dao.BaseDataResourcesInterface;
import com.unitop.sysmgr.dao.BaseHibernateDao;
import com.unitop.sysmgr.dao.BaseJDBCDao;
@Repository("BaseDataResources")
public class BaseDataResources implements BaseDataResourcesInterface {
	
	@Resource
	private BaseHibernateDao BaseHibernateDao;
	@Resource
	private BaseJDBCDao BaseJDBCDao;
	
	public BaseHibernateDao getBaseHibernateDao() {
		return BaseHibernateDao;
	}
	
	public BaseJDBCDao getBaseJDBCDao() {
		return BaseJDBCDao;
	}

	public void set_Session(Session session) {
		BaseHibernateDao.set_Session(session);
	}

	public void shifSession() {
		BaseHibernateDao.shifSession();
	}

	public void set_Connection(Connection conn) {
		BaseJDBCDao.set_Connection(conn);
	}

	public void shifConnection() {
		BaseJDBCDao.shifConnection();
	}
}