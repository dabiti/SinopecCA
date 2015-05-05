package com.unitop.sysmgr.dao;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface BaseHibernateDao {
	
	// getHibernateSession();
	public Session getHibernateSession();
	//关闭连接
	public void closeSession(Session session);
	public HibernateTemplate getDaoHibernateTemplate();
	
	//获取
	public void set_Session(Session session);
	//释放
	public void shifSession();
	
}
