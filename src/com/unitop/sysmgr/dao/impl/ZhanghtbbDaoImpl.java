package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andromda.core.server.ClientException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Zhanghtbb;
import com.unitop.sysmgr.dao.ZhanghtbbDao;

@Repository("ZhanghtbbDaoImpl")
public class ZhanghtbbDaoImpl  extends BaseDataResources implements ZhanghtbbDao {

	private static Logger  log = Logger.getLogger(ZhanghtbbDaoImpl.class);
	
	//����ͬ����Ϣ
	public Zhanghtbb getZhanghtbb(String zhangh) {
		Zhanghtbb zhanghtbb = null;
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Zhanghtbb  where zhangh=:zhangh and result in ('0','2')");
			query.setString("zhangh", zhangh);
			zhanghtbb = (Zhanghtbb) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zhanghtbb;
	}
	
	//����ͬ����Ϣ
	public void updateZhanghtbb(Zhanghtbb zhanghtbb) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String sql = "update Zhanghtbb set exception=? , tongbsj=? ,result=? where zhangh=? ";
			Query query = session.createSQLQuery(sql);
			query.setString(0, zhanghtbb.getException()!=null?zhanghtbb.getException():"");
			query.setString(1, zhanghtbb.getTongbsj()!=null?zhanghtbb.getTongbsj():"");
			query.setString(2, zhanghtbb.getResult());
			query.setString(3, zhanghtbb.getZhangh());
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//ɾ��ͬ��
	public int delZhanghtbb() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		int i = 0;
		try{
			Query query = session.createSQLQuery("delete from Zhanghtbb ");
			i = query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return i;
	}
	
	
	//ɾ��ͬ���ɹ����˺�
	public int delZhanghtbbforZh(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		int i = 0;
		try{
			Query query = session.createSQLQuery("delete from Zhanghtbb where zhangh=?");
			query.setString(0, zhangh);
			i = query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return i;
	}
	
	//������ͬ���ɹ���ͬ����¼
	public int delZhanghtbbforStr(String ZhanghtbbStr) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		int i = 0;
		try{
			Query query = session.createSQLQuery("delete from Zhanghtbb where zhangh not in ("+ZhanghtbbStr+")");
			i = query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return i;
	}
	
	//������ͬ���ɹ���ͬ����¼
	public int updateException(String zhangh,String exception) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		int i = 0;
		try{
			Query query = session.createSQLQuery("update Zhanghtbb set exception=? where zhangh=?");
			query.setString(0, exception);
			query.setString(1, zhangh);
			i = query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return i;
	}

	
	//��ѯ����������ͬ���˻���¼
	public List<Zhanghtbb> queryZhanghtbbList(String zhangh){
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "from Zhanghtb ";
		if(zhangh!=null&&!"".equals(zhangh)){
			hql += " where zhangh =:zhangh ";
		}
		List<Zhanghtbb> zhanghtbList = null;
		try {
			Query query = session.createQuery(hql);
			if(zhangh!=null&&!"".equals(zhangh)){
				query.setString("zhangh", zhangh);
			}
			zhanghtbList  = (List<Zhanghtbb>) query.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("��ȡͬ���˻���Ϣʧ�ܣ�");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zhanghtbList;
	}

	
	//----------------------------------�ܷ�ͬ��---------------------------------------------------------//
	//����ͬ����Ϣ���˺ţ�
	public int updateZhanghtbbForNight(Zhanghtbb zhanghtbb) throws DAOException {
		String sql = "update Zhanghtbb set exception=? , tongbsj=? ,result=? where zhangh=? ";
		int i = 0;
		Connection operation=this.getBaseJDBCDao().getConnection();
		PreparedStatement ps = null;
		try {
			ps = operation.prepareStatement(sql);
			ps.setString(1, zhanghtbb.getException());
			ps.setString(2, zhanghtbb.getTongbsj());
			ps.setString(3, zhanghtbb.getResult());
			ps.setString(4, zhanghtbb.getZhangh());
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("�����˺�Ϊ"+zhanghtbb.getZhangh()+"��ͬ�����ʱ�����쳣��"+e.getMessage());
			throw new DAOException("�����˺�Ϊ"+zhanghtbb.getZhangh()+"��ͬ�����ʱ�����쳣",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("�����˺�Ϊ"+zhanghtbb.getZhangh()+"��ͬ�����ʱ�����쳣��"+e.getMessage());
			throw new DAOException("�����˺�Ϊ"+zhanghtbb.getZhangh()+"��ͬ�����ʱ�����쳣",e);
		}finally{
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(operation!=null){
					this.getBaseJDBCDao().closeConnection(operation);
				}
		}
		return i;

	}
	
	//����ͬ����Ϣ���˺ţ�
	public int updateZhanghtbbForJighForNight(Zhanghtbb zhanghtbb) throws DAOException {
		String sql = "update Zhanghtbb set exception=? , tongbsj=? ,result=? where f_internalorganizationnumber=? and result in ('0','2')";
		int i = 0;
		Connection operation=this.getBaseJDBCDao().getConnection();
		PreparedStatement ps =null;
		try {
			 ps = operation.prepareStatement(sql);
			ps.setString(1, zhanghtbb.getException());
			ps.setString(2, zhanghtbb.getTongbsj());
			ps.setString(3, zhanghtbb.getResult());
			ps.setString(4, zhanghtbb.getShenghjgh());
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("���»���Ϊ"+zhanghtbb.getShenghjgh()+"��ͬ��ʧ�ܽ��ʱ�����쳣��"+e.getMessage());
			throw new DAOException("���»���Ϊ"+zhanghtbb.getShenghjgh()+"��ͬ��ʧ�ܽ��ʱ�����쳣",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("���»���Ϊ"+zhanghtbb.getShenghjgh()+"��ͬ��ʧ�ܽ��ʱ�����쳣��"+e.getMessage());
			throw new DAOException("���»���Ϊ"+zhanghtbb.getShenghjgh()+"��ͬ��ʧ�ܽ��ʱ�����쳣",e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(operation!=null){{
					this.getBaseJDBCDao().closeConnection(operation);
			}
			}
		}
		
		return i;

	}
	
	
	/**
	 * ��ѯͬ������Ϣ
	 * @return
	 * @throws DAOException 
	 * @throws ClientException 
	 * @throws Exception
	 */
	public List<Map<String, String>> queryTongbbListForNight() throws DAOException{
		//��ѯ�˻���
		String sql = "select t.*, t.rowid from zhanghtbb t where t.result in ('0') and rownum<1000 order by t.chuangjsj";
		log.info("��ִ��sql=="+sql);
		Connection operation=this.getBaseJDBCDao().getConnection();
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = operation.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Map<String, String> singleMap = new HashMap<String, String>();
				singleMap.put("ZHANGH", rs.getString("ZHANGH")==null?"":rs.getString("ZHANGH"));
				singleMap.put("CAOZLX", rs.getString("CAOZLX")==null?"":rs.getString("CAOZLX"));
				singleMap.put("SHENGHJGH", rs.getString("F_INTERNALORGANIZATIONNUMBER")==null?"":rs.getString("F_INTERNALORGANIZATIONNUMBER"));
				mapList.add(singleMap);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("��ѯͬ����������Ϣʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯͬ����������Ϣʱ�����쳣",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("��ѯͬ����������Ϣʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯͬ����������Ϣʱ�����쳣",e);
		}finally{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(operation!=null){
					this.getBaseJDBCDao().closeConnection(operation);
			}
		}
		
		return mapList;
	}
	

	

}
