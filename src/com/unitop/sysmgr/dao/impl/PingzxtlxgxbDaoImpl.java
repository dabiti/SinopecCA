package com.unitop.sysmgr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Pingzxtlxgxb;
import com.unitop.sysmgr.dao.PingzxtlxgxbDao;

@Repository("PingzxtlxgxbDaoImpl")
public class PingzxtlxgxbDaoImpl extends BaseDataResources implements PingzxtlxgxbDao{

	/*
	 * ���ñ�ʶ��ȡʵ��
	 */
	public List getPingzxtlxgxbList() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Pingzxtlxgxb");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	/*
	 * �������� ƾ֤ϵͳ���ͱ�
	 */
	public void saveOrUpdate(Pingzxtlxgxb pingzxtlxgxb) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			//ǰ���� ������������� ����HIBERNATE��Ҳ����������
			//��һ����ɫ���� 
			//1���Զ���� ������ݿ��Ѿ���������¼ ����
			//2������û��������¼ �ͱ���
			session.saveOrUpdate(pingzxtlxgxb);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	//��ѯƾ֤ϵͳ��ϵ����
	public List getxitList(String jigh, String pingzbh){
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			 Query query = session.createQuery("from Pingzxtlxgxb where jigh=:jigh and pingzbs=:pingzh");
			 query.setString("jigh", jigh);
			 query.setString("pingzh", pingzbh);
			 list = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
			return list;
		}
	}
	  //ɾ��ϵͳ���͹�ϵ����
	public void deletexitList(String jigh, String pingzh){
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			 Query query = session.createQuery("delete Pingzxtlxgxb where internalorganizationnumber=:jigh and pingzbs=:pingzh");
			 query.setString("jigh", jigh);
			 query.setString("pingzh", pingzh);
			 query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
     }
	
}
