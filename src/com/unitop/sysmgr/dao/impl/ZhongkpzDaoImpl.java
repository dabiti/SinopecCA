package com.unitop.sysmgr.dao.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.config.DBinfoConig;
import com.unitop.sysmgr.bo.Pingzgcb;
import com.unitop.sysmgr.bo.Pingzjgsyb;
import com.unitop.sysmgr.bo.Pingzkcsyb;
import com.unitop.sysmgr.dao.ZhongkpzDao;

/*
 * by wp 
 */
@Repository("ZhongkpzDaoImpl")
public class ZhongkpzDaoImpl extends BaseDataResources implements ZhongkpzDao{

	/*
	 *����ƾ֤��Ϣ(��⡢�˻�)�����̱�
	 */
	public void savePingz(Pingzgcb pingz) throws HibernateException 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pingz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("����ƾ֤��Ϣ�����̱�ʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
    /*
     * ����ƾ֤��Ϣ�����ʣ���
     */
	public void savePingz(Pingzkcsyb pingz) throws HibernateException 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pingz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("����ƾ֤��Ϣ�����ʣ���ʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	/*
	 * ͨ�������š�ƾ֤���Ͳ�ѯ���ʣ����е�ƾ֤��Ϣ
	 */
	public List findPingz(String jigh, String pingzlx) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List pingzList = null;
		try {
			String hql = "from Pingzkcsyb where jigh =:jigh and pingzlx =:pingzlx";
			Query query = session.createQuery(hql);
			query.setString("jigh", jigh);
			query.setString("pingzlx", pingzlx);
			pingzList = query.list();
			if(pingzList == null)pingzList = new ArrayList();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}
	
	/*
	 * ͨ����ʼ����ֹƾ֤�Ų�ѯƾ֤��Ϣ
	 */
	public List findPingzByPingzh(String qispzh, String zhongzpzh,String pingzlx)throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Pingzgcb> pingzList = null;
		try {
			String hql = "from Pingzgcb where pingzh between :qispzh and :zhongzpzh and pingzlx =:pingzlx";
			Query query = session.createQuery(hql);
			query.setString("qispzh", qispzh);
			query.setString("zhongzpzh", zhongzpzh);
			query.setString("pingzlx",pingzlx);
		    pingzList = new ArrayList<Pingzgcb>();
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	/*
	 * ͨ��ƾ֤���͡�ƾ֤״̬��ѯƾ֤��Ϣ
	 * ƾ֤���̱�
	 */
	public List findPz(String pingzlx,String pingzzt) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List pingzList =null;
		try {
			String hql = "from Pingzgcb p where pingzlx =:pingzlx and pingzzt =:pingzzt ";
			Query query = session.createQuery(hql);
			query.setString("pingzlx",pingzlx);
			query.setString("pingzzt",pingzzt);
			pingzList = query.list();
			if(pingzList == null)pingzList = new ArrayList();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	/*
	 * ����������Ϣ�����̱�
	 */
	public void saveGc(Pingzgcb pzgcObj) throws HibernateException 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pzgcObj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException ("����������Ϣ�����̱�ʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}	
	}

	//��ѯ��ȡÿ������
	public List findMbzs(String pingzlx) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List pingzList = null;
		try {
			String hql = "from Voucher where pingzbs =:pingzlx";
			Query query = session.createQuery(hql);
			query.setString("pingzlx", pingzlx);
			pingzList = query.list();
			if(pingzList == null)pingzList = new ArrayList();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	//ͨ�������š�ƾ֤���Ͳ�ѯ����ʣ����е�ƾ֤��Ϣ (�������˻�)
	public List findPingzjg(String jigh, String pingzlx) 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List pingzList = null;
		try {
			String hql = "from Pingzjgsyb where jigh =:jigh and pingzlx =:pingzlx";
			Query query = session.createQuery(hql);
			query.setString("jigh", jigh);
			query.setString("pingzlx", pingzlx);
			pingzList = query.list();
			if(pingzList == null)pingzList = new ArrayList();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	//����ƾ֤��Ϣ������ʣ���
	public void savePingz(Pingzjgsyb pzobj) 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pzobj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("����ƾ֤��Ϣ������ʣ���ʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	//ͨ����ʼ����ֹƾ֤�š�ƾ֤���͡�״̬��ѯ���̱��еļ�¼��Ϣ�������˻أ�
	public List findPz(String qispzh, String zhongzpzh, String pingzlx,String pingzzt) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Pingzgcb> pingzList=null;
		try {
			String hql ="";
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type))
			{
				hql = "from Pingzgcb p where pingzh between :qispzh and :zhongzpzh and pingzlx=:pingzlx and pingzzt=:pingzzt and (p.guiyh!='' or p.guiyh is not null)";
			}else
			{
		        hql = "from Pingzgcb p where pingzh between :qispzh and :zhongzpzh and pingzlx=:pingzlx and pingzzt=:pingzzt and p.guiyh not in('','null')";
			}
			Query query = session.createQuery(hql);
			query.setString("qispzh", qispzh);
			query.setString("zhongzpzh", zhongzpzh);
			query.setString("pingzlx", pingzlx);
			query.setString("pingzzt", pingzzt);
		    pingzList = new ArrayList<Pingzgcb>();
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);//Ϊsession.close();ʱ����
		}
		return pingzList;
	}

	//ͨ����ʼ����ֹƾ֤�š�ƾ֤���Ͳ�ѯ���̱��еļ�¼��Ϣ�����ϣ�
	public List findpz(String qispzh, String zhongzpzh, String pingzlx)throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Pingzgcb> pingzList =null;
		try {
			String hql = "from Pingzgcb where pingzh between :qispzh and :zhongzpzh and pingzlx=:pingzlx ";
			Query query = session.createQuery(hql);
			query.setString("qispzh", qispzh);
			query.setString("zhongzpzh", zhongzpzh);
			query.setString("pingzlx", pingzlx);
			pingzList = new ArrayList<Pingzgcb>();
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);//Ϊsession.close();ʱ����
		}
		return pingzList;
	}

	//ͨ����ʼ����ֹƾ֤�š�ƾ֤���͡�״̬��ѯ���̱��еļ�¼��Ϣ�������˻أ�
	public List findpz(String qispzh, String zhongzpzh, String pingzlx,String pingzzt) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Pingzgcb> pingzList = null;
		try {
			String hql ="";
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type))
			{
				hql = "from Pingzgcb p where pingzh between :qispzh and :zhongzpzh and pingzlx=:pingzlx and pingzzt=:pingzzt and (p.guiyh='' or p.guiyh is null) and (p.jiglyfzr!='' or p.jiglyfzr is not null)";
			}else
			{
		        hql = "from Pingzgcb p where pingzh between :qispzh and :zhongzpzh and pingzlx=:pingzlx and pingzzt=:pingzzt and p.guiyh in ('','null') and p.jiglyfzr not in('','null')";	
			}
			
			Query query = session.createQuery(hql);
			query.setString("qispzh", qispzh);
			query.setString("zhongzpzh", zhongzpzh);
			query.setString("pingzlx", pingzlx);
			query.setString("pingzzt", pingzzt);
			pingzList = new ArrayList<Pingzgcb>();
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);//Ϊsession.close();ʱ����
		}
		return pingzList;
	}

	
	//ͨ��ƾ֤���͡�ƾ֤״̬�������Ų�ѯƾ֤��Ϣ
	public List findPz(String pingzlx, String pingzzt, String jigh) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List pingzList = null;
		try {
			String hql = "from Pingzgcb p where pingzlx =:pingzlx and pingzzt =:pingzzt and lingyjg=:jigh ";
			Query query = session.createQuery(hql);
			query.setString("pingzlx",pingzlx);
			query.setString("pingzzt",pingzzt);
			query.setString("jigh",jigh);
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	//��ȡƾ֤��Ϣ
	public List findly_jig(String pingzlx, String pingzzt) throws SQLException
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List pingzList = null;
		try {
			String hql = "from Pingzgcb p where pingzlx =:pingzlx and pingzzt =:pingzzt";
			Query query = session.createQuery(hql);
			query.setString("pingzlx",pingzlx);
			query.setString("pingzzt",pingzzt);
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	//ɾ������ʣ��������ü�¼��Ϣ
	public void deletePingzjg(Pingzjgsyb pz) 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.delete(pz);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
			
	}
	}

	//ͨ��ƾ֤���͡�ƾ֤״̬�������Ų�ѯƾ֤��Ϣ(��������)������ظ����õ�����
	public List findPZ(String pingzlx, String pingzzt, String jigh) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List pingzList = null;
		try {
			String hql ="";
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type))
			{
				hql = "from Pingzgcb p where pingzlx =:pingzlx and pingzzt =:pingzzt and lingyjg=:jigh and (p.guiyh='' or p.guiyh is null) and (p.jiglyfzr !='' or p.jiglyfzr is not null)";
			}else
			{
				hql = "from Pingzgcb p where pingzlx =:pingzlx and pingzzt =:pingzzt and lingyjg=:jigh and (p.guiyh='' or p.guiyh is null) and p.jiglyfzr not in('','null')";
			}
			
			Query query = session.createQuery(hql);
			query.setString("pingzlx",pingzlx);
			query.setString("pingzzt",pingzzt);
			query.setString("jigh",jigh);
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	//��ȡƾ֤��Ϣ(��������)������ظ����õ�����
	public List findly_jg(String pingzlx, String pingzzt) throws SQLException 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		
		List pingzList = null;
		try {
			String hql = "from Pingzgcb p where pingzlx =:pingzlx and pingzzt =:pingzzt and (p.jiglyfzr='' or p.jiglyfzr is null)";
			Query query = session.createQuery(hql);
			query.setString("pingzlx",pingzlx);
			query.setString("pingzzt",pingzzt);
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	//ɾ��ƾ֤���̱��е�ƾ֤��Ϣ
	public void deletepz(String qispzh, String zhongzpzh, String pingzlx) 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "delete from Pingzgcb where pingzgcbid.pingzh between :qispzh and :zhongzpzh and pingzgcbid.pingzlx=:pingzlx";
		try {
			Query query = session.createQuery(hql);
			query.setString("qispzh", qispzh);
			query.setString("zhongzpzh", zhongzpzh);
			query.setString("pingzlx", pingzlx);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("ɾ��ƾ֤���̱���ƾ֤��Ϣʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

}
