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
 * 主从账户关系维护
 * by wp 
 */
@Repository("ZhuczhgxDaoImpl")
public class ZhuczhgxDaoImpl extends BaseDataResources implements ZhuczhgxDao
{
	/*
	 * 获取子账户信息列表
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
			throw new SQLException("获取子账户信息失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	/*
	 * 保存更新印鉴组合信息
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
			throw new Exception("保存印鉴组合信息失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/*
	 * 保存更新账户信息
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
			throw new Exception("保存账户信息失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/*
	 * 获取主账户各印鉴的启用日期
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
			throw new SQLException("获取子账户各印鉴启用日期失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	/*
	 * 调用存储过程(取消主从关系存储过程)
	 * 
	 * by wp
	 */
	public void doquxgx(String zzhangh, String czhangh,String quxfyrq_) throws Exception 
	{
		Connection con = null;
		CallableStatement proc = null;
		try {
			con = super.getBaseJDBCDao().getConnection();
			//存储过程名称quxzczhgx(zzhangh,czhangh,quxfyrq_)
			proc = con.prepareCall("{call quxzczhgx(?,?,?)}");
			proc.setString(1, zzhangh);
			proc.setString(2, czhangh);
			proc.setString(3, quxfyrq_);
			proc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("执行取消主从关系存储过程失败！");
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
	 * 保存更新账户印鉴信息
	 * by wp
	 */
	public void saveyinj(Yinjb yinjb) throws Exception 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(yinjb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存更新账户印鉴信息失败");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	//获取账户各组合的启用日期
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
			throw new SQLException("获取子账户各组合启用日期失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
}