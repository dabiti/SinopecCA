package com.unitop.sysmgr.dao;

import java.sql.Connection;

import org.hibernate.Session;

public interface BaseDataResourcesInterface {
	public BaseHibernateDao getBaseHibernateDao();
	public BaseJDBCDao getBaseJDBCDao() ;
	public void set_Session(Session session);
	public void shifSession();
	//获取 数据源
	public void set_Connection(Connection conn);
	//释放 数据源
	public void shifConnection();
}
