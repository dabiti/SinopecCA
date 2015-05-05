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
	
	//加载同步信息
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
	
	//更新同步信息
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
	
	//删除同步
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
	
	
	//删除同步成功的账号
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
	
	//清理已同步成功的同步记录
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
	
	//清理已同步成功的同步记录
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

	
	//查询符合条件的同步账户记录
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
			throw new HibernateException("获取同步账户信息失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zhanghtbList;
	}

	
	//----------------------------------总分同步---------------------------------------------------------//
	//更新同步信息（账号）
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
			log.info("更新账号为"+zhanghtbb.getZhangh()+"的同步结果时发生异常："+e.getMessage());
			throw new DAOException("更新账号为"+zhanghtbb.getZhangh()+"的同步结果时发生异常",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("更新账号为"+zhanghtbb.getZhangh()+"的同步结果时发生异常："+e.getMessage());
			throw new DAOException("更新账号为"+zhanghtbb.getZhangh()+"的同步结果时发生异常",e);
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
	
	//更新同步信息（账号）
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
			log.info("更新机构为"+zhanghtbb.getShenghjgh()+"的同步失败结果时发生异常："+e.getMessage());
			throw new DAOException("更新机构为"+zhanghtbb.getShenghjgh()+"的同步失败结果时发生异常",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("更新机构为"+zhanghtbb.getShenghjgh()+"的同步失败结果时发生异常："+e.getMessage());
			throw new DAOException("更新机构为"+zhanghtbb.getShenghjgh()+"的同步失败结果时发生异常",e);
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
	 * 查询同步表信息
	 * @return
	 * @throws DAOException 
	 * @throws ClientException 
	 * @throws Exception
	 */
	public List<Map<String, String>> queryTongbbListForNight() throws DAOException{
		//查询账户表
		String sql = "select t.*, t.rowid from zhanghtbb t where t.result in ('0') and rownum<1000 order by t.chuangjsj";
		log.info("将执行sql=="+sql);
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
			log.info("查询同步表所有信息时发生异常："+e.getMessage());
			throw new DAOException("查询同步表所有信息时发生异常",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("查询同步表所有信息时发生异常："+e.getMessage());
			throw new DAOException("查询同步表所有信息时发生异常",e);
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
