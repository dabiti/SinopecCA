package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.config.DBinfoConig;
import com.unitop.sysmgr.bo.Huobb;
import com.unitop.sysmgr.bo.SystemControlParameter;
import com.unitop.sysmgr.dao.SystemDao;
@Repository("SystemDaoImpl")
public class SystemDaoImpl extends BaseDataResources implements SystemDao {
	
	public String getSystetemNowDate() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String dbtime = null;
		String sql = "";
		
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			//oracle 获取数据服务器时间
			 sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') GETDATE from dual";
		}else{
			//DB2 获取数据服务器时间
			sql = "select to_char(current	timestamp,'YYYY-MM-DD HH24:mi:ss') getdate from sysibm.sysdummy1";
		}
		
		try{
			SQLQuery query = session.createSQLQuery(sql);
			List list = query.list();
		for (Iterator iter = list.iterator(); iter.hasNext();) 
		{
			String element = (String)iter.next();
			dbtime = (String) element;
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
			
		}
		return dbtime;
	}
	public String getSystetemYestoDay() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String dbtime = null;
		String sql = "";
		
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			//oracle 获取数据服务器时间
			 sql = "select to_char(sysdate-1,'yyyy-mm-dd hh24:mi:ss') GETDATE from dual";
		}else{
			//DB2 获取数据服务器时间
			sql = "select to_char(current-1	timestamp,'YYYY-MM-DD HH24:mi:ss') getdate from sysibm.sysdummy1";
		}
		
		try{
			SQLQuery query = session.createSQLQuery(sql);
			List list = query.list();
		for (Iterator iter = list.iterator(); iter.hasNext();) 
		{
			String element = (String)iter.next();
			dbtime = (String) element;
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
			
		}
		return dbtime;
	}
	public String getSystetemNextDate() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String dbtime = null;
		String sql = "";
		
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			//oracle 获取数据服务器时间
			 sql = "select to_char(sysdate+1,'yyyy-mm-dd hh24:mi:ss') GETDATE from dual";
		}else{
			//DB2 获取数据服务器时间
			sql = "select to_char(current+1	timestamp,'YYYY-MM-DD HH24:mi:ss') getdate from sysibm.sysdummy1";
		}
		
		try{
			SQLQuery query = session.createSQLQuery(sql);
			List list = query.list();
		for (Iterator iter = list.iterator(); iter.hasNext();) 
		{
			String element = (String)iter.next();
			dbtime = (String) element;
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
			
		}
		return dbtime;
	}
	
	public SystemControlParameter findSystemControlParameterById(String id){
		return (SystemControlParameter) super.getBaseHibernateDao().getDaoHibernateTemplate().get(SystemControlParameter.class, id);
	}

	public List findAllSystemControlParameters() {
		String hql = "from SystemControlParameter";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			list =session.createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public void deleteSystemControlParameter(String id) {
		String hql = "delete from SystemControlParameter where id =?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, id);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void updateSystemControlParameter( SystemControlParameter controlParameter) {
		 super.getBaseHibernateDao().getDaoHibernateTemplate().update(controlParameter);
	}

	public void addSystemControlParameter(SystemControlParameter controlParameter) {
		 super.getBaseHibernateDao().getDaoHibernateTemplate().save(controlParameter);
	}
	
	//获取货币号
	public List getHuobhList() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			String hql = "from Huobb";
			 Query query = session.createQuery(hql);
			list = query.list();
			if(list == null) list = new ArrayList();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	//依据货币号 查询所有货币实体
	public Huobb getHuobh(String huobbh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Huobb huobb = null;
		try {
			String hql = "from Huobb where huobbh=:huobbh";
			Query query = session.createQuery(hql);
			query.setString("huobbh", huobbh);
			huobb = (Huobb) query.uniqueResult();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		if(huobb==null)huobb = new Huobb();
		return huobb;
	}
	
	/*
	 * 获取系统类型
	 */
	public List getYanyinSystemType(){
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Xitlx");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	
	//获取下一个凭证号
	public String getPZHSequence() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			String query = "select PZH_SEQUENCE.nextval from dual";
			SQLQuery qu = session.createSQLQuery(query);
			Object result =qu.uniqueResult();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
}
