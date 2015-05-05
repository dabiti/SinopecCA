package com.unitop.sysmgr.service.impl;

import java.sql.Connection;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.dao.SystemControlParametersDao;
import com.unitop.sysmgr.dao.impl.BaseDataResources;

public class BaseServiceImpl extends BaseDataResources {
	
	@Resource
	public SystemControlParametersDao SystemControlParametersDao;
	
	@Resource
	SessionFactory sessionFactory;
	
	//分页查询实体
	protected TabsBo tabsBo = null;

	// getHibernateSession();
	public Session getHibernateSession() {
		return super.getBaseHibernateDao().getHibernateSession();
	}

	// colseSessin()
	public void closeSession(Session session) {

		 super.getBaseHibernateDao().closeSession(session);
	}

	// getJDBC();
	public Connection getConnection() {
		return super.getBaseJDBCDao().getConnection();
	}

	// colseJDBC();
	public void closeConnection(Connection con) throws Exception {
		super.getBaseJDBCDao().closeConnection(con);
	}
	
	//分页参数设置
	public void setTabsService(TabsBo tabsBo) throws Exception {
		this.tabsBo = tabsBo;
	}
}