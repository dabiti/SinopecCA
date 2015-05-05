package com.unitop.sysmgr.dao.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.action.ExDispatchAction;
import com.unitop.sysmgr.dao.BaseHibernateDao;
@Repository("BaseHibernateDaoImpl")
public class BaseHibernateDaoImpl  extends HibernateDaoSupport  implements BaseHibernateDao {

	
	private static ThreadLocal<Session> tl=new ThreadLocal<Session>();
	
	
	@Resource(name="sessionFactory")    
    public void setSuperSessionFactory(SessionFactory sessionFactory){ 
        super.setSessionFactory(sessionFactory); 
    }
	
	public HibernateTemplate getDaoHibernateTemplate() {
		return super.getHibernateTemplate();
	}

	// getHibernateSession();
	public Session getHibernateSession(){
		if(tl.get() != null)
		{
			return  tl.get();
		}else{
			Session sesion=this.getSession();
			return  sesion;
		}
	}
	
	//关闭session
	public void closeSession(Session session){
		if(tl.get()==null)
		{
			session.flush();
			session.close();
			session=null;
		}
	}		
	

	public void set_Session(Session session) {
		tl.set(session);
	}
	
	
	//释放事务
	public void shifSession() {
		try{
		if(this.tl.get()!=null){
			Session session_=tl.get();

			session_.flush();
			session_.clear();
			session_.close();
			this.tl.remove();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
