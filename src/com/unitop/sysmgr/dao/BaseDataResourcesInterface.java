package com.unitop.sysmgr.dao;

import java.sql.Connection;

import org.hibernate.Session;

public interface BaseDataResourcesInterface {
	public BaseHibernateDao getBaseHibernateDao();
	public BaseJDBCDao getBaseJDBCDao() ;
	public void set_Session(Session session);
	public void shifSession();
	//��ȡ ����Դ
	public void set_Connection(Connection conn);
	//�ͷ� ����Դ
	public void shifConnection();
}
