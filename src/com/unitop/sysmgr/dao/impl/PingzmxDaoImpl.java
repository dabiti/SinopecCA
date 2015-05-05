/**
 *<dl>
 *<dt><b>类功能概要:</b></dt>
 *<dd></dd>
 *</dl>
 *@Title: PingzmxDaoImpl.java
 *@Package com.unitop.sysmgr.dao.impl
 *@date 2012-4-23 下午06:58:47
 *
 *@copyright :Copyright @2012, IBM ETP. All right reserved.
 *【Update History】
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2012-4-23       IBM ETP      luoxiaoyi		    create
 */

package com.unitop.sysmgr.dao.impl; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateQueryException;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Pingzcshz;
import com.unitop.sysmgr.bo.Pingzcszzrz;
import com.unitop.sysmgr.bo.Pingzmx;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.dao.PingzmxDao;
import com.unitop.sysmgr.form.PingzForm;

/**
 * @ClassName:PingzmxDaoImpl
 * @Description:TODO
 * @author luoxiaoyi
 * @date 2012-4-23 下午06:58:47
 * 
 */
@Repository("PingzmxDaoImpl")
public class PingzmxDaoImpl extends BaseDataResources implements PingzmxDao {

	/*
	 * (non-Javadoc) 根据凭证号查询凭证信息
	 * 
	 * @see com.unitop.sysmgr.dao.PingzmxDao#findPingzByPingzh(java.lang.String)
	 */
	public Pingzmx findPingzByPingzh(String pingzh) throws HibernateQueryException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Pingzmx pingzmx=null;
		try {
			pingzmx = (Pingzmx) session.get(Pingzmx.class, pingzh);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}		
		return pingzmx;
	}
	
	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.dao.PingzmxDao#findPingzByPingzh(java.lang.String, java.lang.String)
	 * 根据起始凭证号和终止凭证号查询明细信息
	 */
	public List<Pingzmx> findPingzByPingzh(String qispzh, String zhongzpzh)throws HibernateQueryException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Pingzmx> pingzList=null;
		try {
			String hql = "from Pingzmx where pingzh between :qispzh and :zhongzpzh ";
			Query query = session.createQuery(hql);
			query.setString("qispzh", qispzh);
			query.setString("zhongzpzh", zhongzpzh);
			
			pingzList = new ArrayList<Pingzmx>();
			pingzList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return pingzList;
	}

	/*
	 * (non-Javadoc) 保存凭证信息
	 * 
	 * @see
	 * com.unitop.sysmgr.dao.PingzmxDao#savePingz(com.unitop.sysmgr.bo.Pingzmx)
	 */
	public void savePingz(Pingzmx pingz) throws HibernateException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.save(pingz);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/*
	 * (non-Javadoc) 查询所有的凭证信息
	 * 
	 * @see com.unitop.sysmgr.dao.PingzmxDao#selectAllPingz()
	 */
	public TabsBo selectAllPingz(PingzForm pingz,int page,int max) throws HibernateQueryException {
		TabsBo tabsBo = new TabsBo();
		Integer firstRow = (page-1)*max;
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Criteria crit = session.createCriteria(Pingzmx.class);
			if (!"".equals(pingz.getPingzlx()) && pingz.getPingzlx() != null) {
				crit.add(Expression.eq("pingzlx", pingz.getPingzlx()));
			}
			if (!"".equals(pingz.getZhangh()) && pingz.getZhangh() != null) {
				crit.add(Expression.eq("zhangh", pingz.getZhangh()));
			}
			if (!"".equals(pingz.getGuiyh()) && pingz.getGuiyh() != null) {
				crit.add(Expression.eq("guiyh", pingz.getGuiyh()));
			}
			if (!"".equals(pingz.getPich()) && pingz.getPich() != null) {
				crit.add(Expression.eq("pich", pingz.getPich()));
			}
			if (!"".equals(pingz.getJigh()) && pingz.getJigh() != null) {
				crit.add(Expression.eq("jigh", pingz.getJigh()));
			}
			if (!"".equals(pingz.getZhuangt()) && pingz.getZhuangt() != null) {
				crit.add(Expression.eq("zhuangt", pingz.getZhuangt()));
			}
			String qispzh = pingz.getQispzh();
			String zhongzpzh = pingz.getZhongzpzh();
			// 凭证区间,按照凭证号中每个字符的asc码比较的。需要保证长度一致，要不会有问题
			if (!"".equals(qispzh) && qispzh != null && !"".equals(zhongzpzh) && zhongzpzh != null) {
				crit.add(Expression.ge("pingzh", qispzh));
				crit.add(Expression.le("pingzh", zhongzpzh));
			}
			String beginDate = pingz.getBegindate();
			String endDate = pingz.getEnddate();
			if (!"".equals(beginDate) && beginDate != null && !"".equals(endDate) && endDate != null) 
			{
				crit.add(Expression.ge("riq", beginDate));
				crit.add(Expression.le("riq", endDate));
			}
			
			crit.setProjection(Projections.rowCount()); 
			Object tt = crit.uniqueResult();
			tabsBo.setCounts(Integer.valueOf(tt.toString()));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		 session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Criteria crit = session.createCriteria(Pingzmx.class);
			if (!"".equals(pingz.getPingzlx()) && pingz.getPingzlx() != null) {
				crit.add(Expression.eq("pingzlx", pingz.getPingzlx()));
			}
			if (!"".equals(pingz.getZhangh()) && pingz.getZhangh() != null) {
				crit.add(Expression.eq("zhangh", pingz.getZhangh()));
			}
			if (!"".equals(pingz.getGuiyh()) && pingz.getGuiyh() != null) {
				crit.add(Expression.eq("guiyh", pingz.getGuiyh()));
			}
			if (!"".equals(pingz.getPich()) && pingz.getPich() != null) {
				crit.add(Expression.eq("pich", pingz.getPich()));
			}
			if (!"".equals(pingz.getJigh()) && pingz.getJigh() != null) {
				crit.add(Expression.eq("jigh", pingz.getJigh()));
			}
			if (!"".equals(pingz.getZhuangt()) && pingz.getZhuangt() != null) {
				crit.add(Expression.eq("zhuangt", pingz.getZhuangt()));
			}
			String qispzh = pingz.getQispzh();
			String zhongzpzh = pingz.getZhongzpzh();
			// 凭证区间,按照凭证号中每个字符的asc码比较的。需要保证长度一致，要不会有问题
			if (!"".equals(qispzh) && qispzh != null && !"".equals(zhongzpzh) && zhongzpzh != null) {
				crit.add(Expression.ge("pingzh", qispzh));
				crit.add(Expression.le("pingzh", zhongzpzh));
			}
			String beginDate = pingz.getBegindate();
			String endDate = pingz.getEnddate();
			if (!"".equals(beginDate) && beginDate != null && !"".equals(endDate) && endDate != null) 
			{
				crit.add(Expression.ge("riq", beginDate));
				crit.add(Expression.le("riq", endDate));
			}
			
			if (!"".equals(pingz) && pingz.getZhuangt() != null) 
			{
				if("降序".equals(pingz.getPaix()))
				{
					crit.addOrder(Order.desc("zhangh"));
					crit.addOrder(Order.desc("pingzh"));
				}else{
					//升序
					crit.addOrder(Order.asc("zhangh"));
					crit.addOrder(Order.asc("pingzh"));
				}
			}
			
			//设置开始行号
			crit.setFirstResult(firstRow);
			//设置一页显示行数
			crit.setMaxResults(max);
			tabsBo.setList(crit.list());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return tabsBo;
	}

	/*
	 * (non-Javadoc) 根据凭证号更新凭证信息
	 * 
	 * @see
	 * com.unitop.sysmgr.dao.PingzmxDao#updatePingz(com.unitop.sysmgr.bo.Pingzmx
	 * )
	 */
	public void updatePingz(Pingzmx pingz) throws SQLException {
		
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(pingz);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
		/*Connection conn = getBaseJDBCDao().getConnection();
		String sql = "update pingzcsmx set pich = ?,zhangh = ?, jigh = ?,"
				+ "hum = ?, pingzlx = ?,guiyh = ?,riq = ?,shij = ?,zhuangt = ? "
				+ " where pingzh = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, pingz.getPich());
		ps.setString(2, pingz.getZhangh());
		ps.setString(3, pingz.getJigh());
		ps.setString(4, pingz.getHum());
		ps.setString(5, pingz.getPingzlx());
		ps.setString(6, pingz.getGuiyh());
		ps.setString(7, pingz.getRiq());
		ps.setString(8, pingz.getShij());
		ps.setString(9, pingz.getZhuangt());
		ps.setString(10, pingz.getPingzh());
		ps.execute();
		CloseRsPsCon.closePsCon(ps, conn);*/
	}
	
	
	/*
	 * 更新打印状态
	 */
	public void updateZhuangt(String qispzh,String zhongzpzh) throws SQLException{
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			SQLQuery query;
			if(qispzh.equals(zhongzpzh)){
				query = session.createSQLQuery("update pingzcsmx set zhuangt = '已打印' where pingzh = :qispzh");
				query.setString("qispzh", qispzh);
			}else if(qispzh.compareTo(zhongzpzh)<0){
				query = session.createSQLQuery("update pingzcsmx set zhuangt = '已打印' where pingzh >= :qispzh and pingzh <= :zhongzpzh");
				query.setString("qispzh", qispzh);
				query.setString("zhongzpzh", zhongzpzh);
			}else{
				query = session.createSQLQuery("update pingzcsmx set zhuangt = '已打印' where pingzh >= :zhongzpzh and pingzh <= :qispzh");
				query.setString("zhongzpzh", zhongzpzh);
				query.setString("qispzh", qispzh);
			}
			query.executeUpdate();
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		
	} 
	
	
	
	/*
	 * (non-Javadoc) 根据凭证号删除凭证信息
	 * @see com.unitop.sysmgr.dao.PingzmxDao#deletePingz(java.lang.String)
	 */
	public void deletePingz(String pingzh) throws SQLException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			Query query = session.createQuery("delete Pingzmx where pingzh=:pingzh");
			query.setString("pingzh", pingzh);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/**
	 * 查询一个批次的凭证明细
	 */
	public List findPingzByPich(String pich) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Pingzmx where pich =:pich");
			query.setString("pich", pich);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}

		if (list == null) {
			list = new ArrayList();
		}
		return list;
	}

	/*
	 * (non-Javadoc) 保存凭证汇总信息
	 * 
	 * @see
	 * com.unitop.sysmgr.dao.PingzmxDao#savePinzhz(com.unitop.sysmgr.bo.Pingzmx)
	 */
	public void savePinzhz(Pingzmx pingz) throws HibernateException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Pingzcshz pingzcshz =  new Pingzcshz();
			pingzcshz.setBens(Integer.valueOf(pingz.getBens()==""?"0":pingz.getBens()));
			pingzcshz.setGuiyh(pingz.getGuiyh());
			pingzcshz.setHum(pingz.getHum());
			pingzcshz.setJigh(pingz.getJigh());
			pingzcshz.setPich(pingz.getPich());
			pingzcshz.setPingzlx(pingz.getPingzlx());
			pingzcshz.setQispzh(pingz.getQispzh());
			pingzcshz.setRiq(pingz.getRiq());
			pingzcshz.setShij(pingz.getShij());
			pingzcshz.setZhangh(pingz.getZhangh());
			pingzcshz.setZhangs(Integer.valueOf(pingz.getZhangs()==""?"0":pingz.getZhangs()));
			pingzcshz.setZhongzpzh(pingz.getZhongzpzh());
			pingzcshz.setZhuangt(pingz.getZhuangt());
			session.save(pingzcshz);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	/**
	 * 查询凭证出售日志
	 */
	public List findPingzRizByPingzh(String pingzh) {
		Session session=super.getBaseHibernateDao().getHibernateSession();
		List list =null;
		try{
			Query query = session.createQuery("from Pingzcszzrz where pingzh =:pingzh");
			query.setString("pingzh", pingzh);
			list = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
		if(list==null)
		{
			list  = new ArrayList();
		}
		return list;
	}

	//凭证追踪日志保存
	public void savePingzzzrz(Pingzcszzrz Pingzcszzrz){
		Session session=super.getBaseHibernateDao().getHibernateSession();
		try{
			session.save(Pingzcszzrz);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
}
