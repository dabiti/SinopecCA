package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;
import com.unitop.sysmgr.dao.PingzpzbDao;

@Repository("PingzpzbDaoImpl")
public class PingzpzbDaoImpl extends BaseDataResources implements PingzpzbDao {


	
	
	// 查询凭证配置列表信息
	public ArrayList<Pingzpzb> getPingzpzList() {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		ArrayList<Pingzpzb> pingzpzList = new ArrayList<Pingzpzb>();
		try{
			Query query = session.createQuery("from Pingzpzb order by xianssx");
			pingzpzList = (ArrayList<Pingzpzb>) query.list();
//			for (Pingzpzb pingzpzb : pingzpzList) {
//				pingzpzb.setYanygz(getYanygzByGuizbh(pingzpzb.getGuizbh()));
//			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzpzList;
	}

	





	private Yanygz getYanygzByGuizbh(String guizbh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Yanygz yanygz=new Yanygz();
		try{
			Query query = session.createQuery("from Yanygz where guizbh=:guizbh");
			query.setString("guizbh", guizbh);
			yanygz=(Yanygz)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return yanygz;
	}







	// 根据凭证号查询凭证信息
	public Pingzpzb getPingzpzByPingzbs(String pingzbs) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Pingzpzb pingz=new Pingzpzb();
		try{
			Query query = session.createQuery("from Pingzpzb where pingzbs=:pingzbs");
			query.setString("pingzbs", pingzbs);
			pingz=(Pingzpzb)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingz;
	}
	
	public List<Yanygz> getYanygzList() {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List<Yanygz> yanygzList = new ArrayList<Yanygz>();
		try{
			Query query = session.createQuery("from Yanygz");
			yanygzList = (List<Yanygz>) query.list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return yanygzList;
	}

	public void updatePingzpzb(Pingzpzb pingzpzb) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			SQLQuery query = session.createSQLQuery("update pingzpzb set guizbh=:guizbh,pingzmc=:pingzmc,shifxs=:shifxs,xianssx=:xianssx where pingzbs=:pingzbs ");
			query.setString("guizbh", pingzpzb.getGuizbh());
			query.setString("pingzbs",pingzpzb.getPingzbs());
			query.setString("pingzmc",pingzpzb.getPingzmc());
			query.setString("shifxs",pingzpzb.getShifxs());
			query.setString("xianssx",pingzpzb.getXianssx());
			query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public Pingzpzb queryPingzpzbByPzmc(String pingzmc, String pingzbs) {
			Session session = this.getBaseHibernateDao().getHibernateSession();
			Pingzpzb pingz=new Pingzpzb();
			String hql="from Pingzpzb where pingzmc=:pingzmc";
			if(pingzbs!=null&&!pingzbs.trim().equals("")){
				hql="from Pingzpzb where pingzbs<>:pingzbs and pingzmc=:pingzmc";
			}
			try{
				Query query = session.createQuery(hql);
				if(pingzbs!=null&&!pingzbs.trim().equals("")){
					query.setString("pingzbs", pingzbs);
				}
				query.setString("pingzmc", pingzmc);
				pingz=(Pingzpzb)query.uniqueResult();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				this.getBaseHibernateDao().closeSession(session);
			}
			return pingz;
	}
	public void savePingz(Pingzpzb pz) {
		if(pz==null){
			return;
		}
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String sql="insert into pingzpzb(pingzbs,pingzmc,guizbh,shifqy,shifxs,xianssx) values(:pingzbs,:pingzmc,:guizbh,'1',:shifxs,:xianssx)";
		try{
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("guizbh", pz.getGuizbh());
			query.setString("pingzbs",pz.getPingzbs());
			query.setString("pingzmc",pz.getPingzmc());
			query.setString("shifxs",pz.getShifxs());
			query.setString("xianssx",pz.getXianssx());
			query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
	}
	public void deletePingz(String pingzbs) {
		if(pingzbs==null){
			return;
		}
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String sql="delete pingzpzb where pingzbs=:pingzbs";
		try{
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("pingzbs",pingzbs);
			query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
		
	}
	
}
