package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.GuiyjsgxbId;
import com.unitop.sysmgr.bo.Role;
import com.unitop.sysmgr.dao.RoleDao;

@Repository("RoleDaoImpl")
public class RoleDaoImpl extends BaseDataResources implements RoleDao {

	public void deleteRole(Role role) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.delete(role);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
			
		}
	}

	public List getAllRole(){
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			list = (List) session.createQuery("from Role").list();
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	public List getAllRoleByJuesjb(String juesjb){
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			Query query = session.createQuery("from Role where juesjb<=:juesjb");
			query.setString("juesjb", juesjb);
			list = query.list();
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public Role getRole(String juesid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Role role = null;
		try{
			role = (Role) session.get(Role.class, juesid);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return role;
	}

	public void save(Role role) throws Exception{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.saveOrUpdate(role);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	public List getRoleByClerkCode(String clerkCode) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			String hql="from Guiyjsgxb where id.guiyid=:guiyid";
			Query query=session.createQuery(hql);
			query.setString("guiyid", clerkCode);
			list = (List) query.list();
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	
	public List getRoleListByClerkCode(String clerkCode) {
		List list = new ArrayList();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			SQLQuery query = session.createSQLQuery("select j.guiyid,ju.juesid,ju.juesmc from guiyjsgxb j join juesb ju on j.juesid = ju.juesid  and j.guiyid=:guiyid ");
			query.setString("guiyid", clerkCode);
			List reList = query.list();
			for(Iterator iter = reList.iterator(); iter.hasNext();){
				Object[] element = (Object[]) iter.next();
				GuiyjsgxbId guiyjsgxb = new GuiyjsgxbId();
				guiyjsgxb.setGuiyid((String) element[0]);
				guiyjsgxb.setJuesid((String) element[1]);
				guiyjsgxb.setJuesmc((String) element[2]);
				list.add(guiyjsgxb);
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		/*Connection conn = super.getBaseJDBCDao().getConnection();
		List list  = new ArrayList();
		PreparedStatement pre = null;
		ResultSet rs = null;
		try{
			pre = conn.prepareStatement("select j.guiyid,ju.juesid,ju.juesmc from guiyjsgxb j join juesb ju on j.juesid = ju.juesid  and j.guiyid=? ");
			pre.setString(1,clerkCode );
			rs = pre.executeQuery();
			while(rs.next())
			{
				GuiyjsgxbId guiyjsgxb = new GuiyjsgxbId();
				guiyjsgxb.setGuiyid(rs.getString("guiyid"));
				guiyjsgxb.setJuesid(rs.getString("juesid"));
				guiyjsgxb.setJuesmc(rs.getString("juesmc"));
				list.add(guiyjsgxb);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			CloseRsPsCon.closeStPsCon(conn, rs, pre);
		}*/
		return list;
	}
	public Role getRoleByName(String juesmc) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Role role = null;
		try{
			Query query = session.createQuery("FROM Role where juesmc=:juesmc");
			query.setString("juesmc", juesmc);
			List list = query.list();
			if(list == null||list.size()==0)
			{
				return null;
			}else{
				return (Role) list.get(0);
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}catch (Exception e1){
			e1.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return role;
	}
	
	
}
