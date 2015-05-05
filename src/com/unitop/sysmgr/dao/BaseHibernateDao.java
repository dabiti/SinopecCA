package com.unitop.sysmgr.dao;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface BaseHibernateDao {
	
	// getHibernateSession();
	public Session getHibernateSession();
	//�ر�����
	public void closeSession(Session session);
	public HibernateTemplate getDaoHibernateTemplate();
	
	//��ȡ
	public void set_Session(Session session);
	//�ͷ�
	public void shifSession();
	
}
