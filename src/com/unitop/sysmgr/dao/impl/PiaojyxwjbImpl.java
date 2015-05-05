package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.unitop.sysmgr.bo.Piaojyxwjb;
import com.unitop.sysmgr.bo.PiaojyxwjbId;
import com.unitop.sysmgr.dao.PiaojyxwjbDao;

@Repository("PiaojyxwjbImpl")
public class PiaojyxwjbImpl extends BaseDataResources implements PiaojyxwjbDao {

	//获取帐号影像文件服务器
	public Piaojyxwjb getPiaojyxwjb(PiaojyxwjbId id) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			Piaojyxwjb piaojyxwjb =(Piaojyxwjb) session.get(Piaojyxwjb.class,id);
			return piaojyxwjb;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return null;
	}

	public List getPiaojyxwjb(String zhangh,String wenjbh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "from Piaojyxwjb where id.zhangh=:zhangh and id.wenjbh=:wenjbh";
			Query query = session.createQuery(sql);
			query.setString("zhangh", zhangh);
			query.setString("wenjbh", wenjbh);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return new ArrayList();
	}
	
}
