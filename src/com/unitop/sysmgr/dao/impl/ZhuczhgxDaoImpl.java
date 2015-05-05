package com.unitop.sysmgr.dao.impl;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjzhb;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.dao.ZhuczhgxDao;
/*
 * �����˻���ϵά��
 * by wp 
 */
@Repository("ZhuczhgxDaoImpl")
public class ZhuczhgxDaoImpl extends BaseDataResources implements ZhuczhgxDao
{
	/*
	 * ��ȡ���˻���Ϣ�б�
	 * by wp
	 */
	public List getzizh(String zhangh) throws SQLException
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "from Zhanghb where zhuzh =:zhangh ";
		List list=new ArrayList();
		try {
			Query query = session.createQuery(hql);
			query.setString("zhangh", zhangh);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new SQLException("��ȡ���˻���Ϣʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	/*
	 * �������ӡ�������Ϣ
	 * by wp
	 */
	public void savezuh(Yinjzhb yinjzhb) throws Exception
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try 
		{
			session.saveOrUpdate(yinjzhb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("����ӡ�������Ϣʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/*
	 * ��������˻���Ϣ
	 * by wp
	 */
	public void savezhanghb(Zhanghb zhanghb) throws Exception
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try 
		{
			session.saveOrUpdate(zhanghb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("�����˻���Ϣʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/*
	 * ��ȡ���˻���ӡ������������
	 * by wp
	 */
	public List getqiyrq(String mainaccount) throws SQLException
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "from Yinjb where zhangh =:zhangh ";
		List list = new ArrayList();
		try {
			Query query = session.createQuery(hql);
			query.setString("zhangh", mainaccount);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new SQLException("��ȡ���˻���ӡ����������ʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	/*
	 * ���ô洢����(ȡ�����ӹ�ϵ�洢����)
	 * 
	 * by wp
	 */
	public void doquxgx(String zzhangh, String czhangh,String quxfyrq_) throws Exception 
	{
		Connection con = null;
		CallableStatement proc = null;
		try {
			con = super.getBaseJDBCDao().getConnection();
			//�洢��������quxzczhgx(zzhangh,czhangh,quxfyrq_)
			proc = con.prepareCall("{call quxzczhgx(?,?,?)}");
			proc.setString(1, zzhangh);
			proc.setString(2, czhangh);
			proc.setString(3, quxfyrq_);
			proc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("ִ��ȡ�����ӹ�ϵ�洢����ʧ�ܣ�");
		} finally {
			try {
				if (proc != null)
					proc.close();
				if (con != null){
					this.getBaseJDBCDao().closeConnection(con);
				}
			} catch (SQLException e){
				throw e;
			}
		 }
	 }
	
	/*
	 * ��������˻�ӡ����Ϣ
	 * by wp
	 */
	public void saveyinj(Yinjb yinjb) throws Exception 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(yinjb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��������˻�ӡ����Ϣʧ��");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	//��ȡ�˻�����ϵ���������
	public List getQiyrq(String congzh) throws Exception 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "from Yinjzhb where zhangh =:zhangh ";
		List list = new ArrayList();
		try {
			Query query = session.createQuery(hql);
			query.setString("zhangh", congzh);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new SQLException("��ȡ���˻��������������ʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
}