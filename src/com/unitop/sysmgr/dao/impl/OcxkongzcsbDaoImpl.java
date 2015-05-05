package com.unitop.sysmgr.dao.impl;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.unitop.sysmgr.bo.Ocxkongzcsb;
import com.unitop.sysmgr.dao.OcxkongzcsbDao;

/*
 * by wp
 * 20130409
 */

@Repository("OcxkongzcsbDaoImpl")
public class OcxkongzcsbDaoImpl extends BaseDataResources implements OcxkongzcsbDao{

	//����������ȡocxkongzcsb������Ϣ
	public Ocxkongzcsb getocxkongzcs(String key)throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "from Ocxkongzcsb where key =:key ";
		Ocxkongzcsb ocxkongzcsbt = null;
		try {
			Query query = session.createQuery(hql);
			query.setString("key", key);
			ocxkongzcsbt  = (Ocxkongzcsb) query.uniqueResult();
		} catch (HibernateException e) {
			throw new SQLException("��ȡOCX���Ʋ�����Ϣʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return ocxkongzcsbt;
	}
}