package com.unitop.sysmgr.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.dao.VoucherDao;
@Repository("VoucherDaoImpl")
public class VoucherDaoImpl extends BaseDataResources implements VoucherDao{
	
	/**
	 * @author lhz
	 * @param VoucherPk
	 * 使用联合主键获取凭证对象
	 */
	public Voucher getVoucher(String id) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Voucher voucher = null;
		try {
			voucher = (Voucher) session.get(Voucher.class,id);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return voucher;
	}

	public void saveVoucher(Voucher voucher) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(voucher);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}

	}
	
	public void updateVoucher(Voucher voucher) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(voucher);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}		
	}
	
	public void deleteVoucher(Voucher voucher) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.delete(voucher);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}		
	}
	
	/**
	 * @author ldd
	 * @param orgcode机构号
	 * 根据机构号取回所属的票据信息列表
	 */
	public List getVoucherList() {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			String hql = "from Voucher order by PINGZBS";
			Query query = session.createQuery(hql);
			list = query.list();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public List getVoucherList_zk() 
	{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			String hql = "from Voucher where shifzk='1' order by PINGZBS";
			Query query = session.createQuery(hql);
			list = query.list();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
		
	}
	
}
