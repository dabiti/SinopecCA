package com.unitop.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.SystemControlParameter;
import com.unitop.sysmgr.dao.SystemControlParametersDao;
@Repository("SystemControlParametersDaoImpl")
public class SystemControlParametersDaoImpl  extends BaseDataResources implements SystemControlParametersDao {
	
	public void addSystemControlParameter(SystemControlParameter controlParameter) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(controlParameter);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}

	public void deleteSystemControlParameter(String id) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			SystemControlParameter systemControlParameter = (SystemControlParameter) session.get(SystemControlParameter.class, id);
			session.delete(systemControlParameter);
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List<SystemControlParameter> findAllSystemControlParameters() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<SystemControlParameter> list=null;
		try {
			list = session.createSQLQuery("from SystemControlParameter").list();
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public SystemControlParameter findSystemControlParameterById(String id) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		SystemControlParameter systemControlParameter=null;
		try {
			systemControlParameter = (SystemControlParameter) session.get(SystemControlParameter.class, id);
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return systemControlParameter;
	}

	public void updateSystemControlParameter(SystemControlParameter controlParameter) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.delete(controlParameter);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public Map<String, String> findSystemControlParameters(){
		Map<String, String> map = new HashMap<String, String>();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from SystemControlParameter");
			List list = query.list();
			for(int i=0;i<list.size();i++){
				SystemControlParameter parameter = (SystemControlParameter) list.get(i);
				map.put(parameter.getId(), parameter.getValue());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return map;
	}
	//江西银行-序号头 数据库同步
	public String updateSequence(String sid){
		String value = "";
		Session session = getBaseHibernateDao().getHibernateSession();
		Transaction tr = session.beginTransaction();
		try{
			tr.begin();
			Query query=session.createQuery("from SystemControlParameter s where s.id=:id");
			query.setString("id",sid);
			SystemControlParameter scp=(SystemControlParameter)query.uniqueResult();
			value = scp.getValue();
			value =String.valueOf(Long.valueOf(value)+1);
			scp.setValue(value);
			session.update(scp);
			session.flush();
			tr.commit();
		}catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return value;
	}
}
