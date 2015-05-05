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
	 *保存凭证信息(入库、退回)到过程表
	 */
	public void savePingz(Pingzgcb pingz) throws HibernateException 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pingz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("保存凭证信息到过程表失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
    /*
     * 保存凭证信息到库存剩余表
     */
	public void savePingz(Pingzkcsyb pingz) throws HibernateException 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pingz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("保存凭证信息到库存剩余表失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	/*
	 * 通过机构号、凭证类型查询库存剩余表中的凭证信息
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
	 * 通过起始、终止凭证号查询凭证信息
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
	 * 通过凭证类型、凭证状态查询凭证信息
	 * 凭证过程表
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
	 * 保存领用信息到过程表
	 */
	public void saveGc(Pingzgcb pzgcObj) throws HibernateException 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pzgcObj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException ("保存领用信息到过程表失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}	
	}

	//查询获取每本张数
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

	//通过机构号、凭证类型查询机构剩余表中的凭证信息 (。。。退回)
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

	//保存凭证信息到机构剩余表
	public void savePingz(Pingzjgsyb pzobj) 
	{
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(pzobj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("保存凭证信息到机构剩余表失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	//通过起始、终止凭证号、凭证类型、状态查询过程表中的记录信息（个人退回）
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
			this.getBaseHibernateDao().closeSession(session);//为session.close();时出错
		}
		return pingzList;
	}

	//通过起始、终止凭证号、凭证类型查询过程表中的记录信息（作废）
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
			this.getBaseHibernateDao().closeSession(session);//为session.close();时出错
		}
		return pingzList;
	}

	//通过起始、终止凭证号、凭证类型、状态查询过程表中的记录信息（机构退回）
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
			this.getBaseHibernateDao().closeSession(session);//为session.close();时出错
		}
		return pingzList;
	}

	
	//通过凭证类型、凭证状态、机构号查询凭证信息
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

	//获取凭证信息
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

	//删除机构剩余表中无用记录信息
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

	//通过凭证类型、凭证状态、机构号查询凭证信息(个人领用)解决可重复领用的问题
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

	//获取凭证信息(机构领用)解决可重复领用的问题
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

	//删除凭证过程表中的凭证信息
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
			throw new HibernateException("删除凭证过程表中凭证信息失败！");
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

}
