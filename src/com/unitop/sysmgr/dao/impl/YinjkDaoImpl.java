package com.unitop.sysmgr.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.KagRenw;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.YinjkId;
import com.unitop.sysmgr.bo.YinjkManageLog;
import com.unitop.sysmgr.dao.YinjkDao;
import com.unitop.sysmgr.form.YinjkForm;

@Repository("YinjkDaoImpl")
public class YinjkDaoImpl extends BaseDataResources implements YinjkDao {

	public List<Yinjk> getYinjk(YinjkForm yinjkForm) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		String hql = "select zhangh,internalorganizationnumber,kagid,ceng,choutwz,case yewlx when '0' then '在用' else '历史' end as yewlx,shifzk,qiyrq from yinjk where kagid<>'' and";
		String zhangh = yinjkForm.getZhangh();
		String yewlx = yinjkForm.getYewlx();
		String jigh = yinjkForm.getJigh();
		String hqlwhere = "";
		if (zhangh != null && !"".equals(zhangh)) {
			hqlwhere += " zhangh='" + zhangh + "' and";
		}
		if (yewlx != null && !"".equals(yewlx)) {
			hqlwhere += " yewlx='" + yewlx + "' and";
		}
		if (jigh != null && !"".equals(jigh)) {
			hqlwhere += " internalorganizationnumber='" + jigh + "' and";
		}
		hql += hqlwhere;
		hql = hql.substring(0, hql.length() - 3);
		hql += " group by (zhangh,internalorganizationnumber,kagid,ceng,choutwz,yewlx,shifzk,qiyrq)";
		List<Yinjk> yinjklist = new ArrayList<Yinjk>();
		try {
			Query query = session.createSQLQuery(hql);
			List list = query.list();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Yinjk yinjk = new Yinjk();
				Object[] element = (Object[]) iter.next();
				YinjkId yinjkid = new YinjkId();
				yinjkid.setZhangh((String) element[0]);
				yinjk.setYinjkid(yinjkid);
				yinjk.setJigh((String) element[1]);
				yinjk.setKagid((String) element[2]);
				yinjk.setCeng(element[3].toString());
				yinjk.setChoutwz(element[4].toString());
				yinjk.setYewlx((String) element[5]);
				yinjk.setShifzk(element[6].toString());
				yinjk.getYinjkid().setQiyrq(element[7].toString());
				yinjklist.add(yinjk);
			}
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return yinjklist;
	}

	public Yinjk getYinjkByZhangh(String zhangh, String yinjkh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Yinjk yinjk = null;
		try {
			Query query = session
					.createQuery("from Yinjk where zhangh=:zhangh and yinjkh=:yinjkh ");
			query.setString("zhangh", zhangh);
			query.setString("yinjkh", yinjkh);
			yinjk = (Yinjk) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return yinjk;
	}

	// 印鉴卡查询
	public List<Yinjk> getYinjkByZhangh(String zhangh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List<Yinjk> list = new ArrayList<Yinjk>();
		try {
			// 在用印鉴卡查询
			// Query query =
			// session.createQuery("from Yinjk where zhangh=:zhangh and yewlx='0'");
			Query query = session
					.createQuery("from Yinjk where zhangh=:zhangh");
			query.setString("zhangh", zhangh);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	// 依据启用日期获取印鉴卡信息
	public List<Yinjk> getYinjkByQiyrq(String zhangh, String qiyrq) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List<Yinjk> list = new ArrayList<Yinjk>();
		try {
			Query query = session
					.createQuery("from Yinjk where zhangh=:zhangh and qiyrq=:qiyrq ");
			query.setString("zhangh", zhangh);
			query.setString("qiyrq", qiyrq);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public void updateShifzk(String yinjkUpdateSql) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(yinjkUpdateSql);
			sqlQuery.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	// 保存印鉴卡信息
	public void saveyinjk(Yinjk yinjk) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(yinjk);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("保存印鉴卡信息失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}

	}

	// 获取印鉴卡号
	public KagRenw getYinjkh(String renwbs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "from KagRenw where renwbs =:renwbs ";
		KagRenw kagRenw = null;
		try {
			Query query = session.createQuery(hql);
			query.setString("renwbs", renwbs);
			kagRenw = (KagRenw) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("获取印鉴卡号失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return kagRenw;
	}

	// 获取印鉴卡信息
	public Yinjk getYinjk(String zhangh, String yinjkh, String qiyrq) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "from Yinjk where zhangh =:zhangh and yinjkh =:yinjkh and qiyrq=:qiyrq";
		Yinjk yinjk = null;
		try {
			Query query = session.createQuery(hql);
			query.setString("zhangh", zhangh);
			query.setString("yinjkh", yinjkh);
			query.setString("qiyrq", qiyrq);
			yinjk = (Yinjk) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("获取印鉴卡信息失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return yinjk;
	}

	// 删除印鉴卡
	public void deleteYinjk(String zhangh, String yinjkh, String qiyrq) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "delete Yinjk where zhangh =:zhangh and yinjkh =:yinjkh and qiyrq=:qiyrq";
		try {
			Query query = session.createQuery(hql);
			query.setString("zhangh", zhangh);
			query.setString("yinjkh", yinjkh);
			query.setString("qiyrq", qiyrq);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("删除印鉴卡失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	// 删除账户下的印鉴卡
	public void deleteYinjk(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "delete Yinjk where zhangh =:zhangh";
		try {
			Query query = session.createQuery(hql);
			query.setString("zhangh", zhangh);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("删除账户下的印鉴卡失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	// 删除账户下的印鉴卡
	public void deleteYinjk(List<String> yinjkList) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "delete yinjk where yinjkh in ( :yinjks)";
		String param="";
		if (yinjkList.size() == 0) {
			return;
		}
		for (String string : yinjkList) {
			if (string != null && string.trim().length() != 0) {
				param +=string + ",";
			}
		}
		if(!"".equals(param)){
			param = param.substring(0, param.length() - 1);
		}
		try {
			SQLQuery query = session.createSQLQuery(hql);
			query.setString("yinjks",param);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("删除账户下的印鉴卡失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void cancleYinjk(String yinjkh) {
		if(yinjkh==null||yinjkh.trim().length()==0){
			return;
		}
		Session session = super.getBaseHibernateDao().getHibernateSession();
		// hql = "update Yinjk set yinjkzt=:yinjkzt where yinjkh =:yinjkh";
		 String hql="delete Yinjk where yinjkh=:yinjkh";
		try {
			Query query = session.createQuery(hql);
			query.setString("yinjkh", yinjkh);
		//	query.setString("yinjkzt","作废");
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("销卡失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}

	}
	
	public void resumeYinjk(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String hql = "update Yinjk set yinjkzt=:yinjkzt where zhangh =:zhangh";
		try {
			Query query = session.createQuery(hql);
			query.setString("zhangh", zhangh);
			query.setString("yinjkzt","已用");
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException("恢复账户下的印鉴卡失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	/**
	 * 通过印鉴卡编号获取印鉴卡
	 */
	public Yinjk getYinjkByYinjkbh(String yinjkbh) {
		if(yinjkbh==null||yinjkbh.trim().equals("")){
			return null;
		}
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Yinjk yinjk = null;
		try {
			Query query = session
					.createQuery("from Yinjk where  yinjkh=:yinjkbh ");
			query.setString("yinjkbh", yinjkbh);
			yinjk = (Yinjk) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return yinjk;
	}

/*	public boolean CheckYinjkbhList(List<String> yinjkbhList) {
		String sql = "select count(*) from yinjk where yinjkzt=:yinjkzt and yinjkh in ('";
		boolean flag=false;
		for (String string : yinjkbhList) {
			if (string != null && string.trim().length() != 0) {
				sql +=  string + "','";
				flag=true;
			}
		}
		if(flag)
		sql = sql.substring(0, sql.length() - 3);
		sql += "')";
		
		if (yinjkbhList.size() == 0) {
			
			sql = "select count(*) from yinjk where yinjkzt=:yinjkzt and yinjkh in ('')";
		}
		Session session = this.getBaseHibernateDao().getHibernateSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("yinjkzt", "已用");
		int num = 1;
		try {

			Object obj = query.uniqueResult();
			num = Integer.parseInt(obj.toString());
			System.out.println(num);
		} catch (Exception e) {
	
			return true;
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return num > 0;
	}*/
public boolean CheckYinjkbhList(List<String> yinjkbhList) {
		String sql = "select count(*) from yinjk where yinjkzt=:yinjkzt and yinjkh in(:yinjkh)";
		if (yinjkbhList.size() == 0) {
			return false;
		}
		String param="";
		for (String string : yinjkbhList) {
			if (string != null && string.trim().length() != 0) {
				param += ""+string.trim() + ",";
			}
		}
		if(!"".equals(param)){
			param = param.substring(0, param.length() - 1);
		}
		Session session = this.getBaseHibernateDao().getHibernateSession();
		//sql=sql.replace(":yinjks", param);
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("yinjkzt", "已用");
		query.setString("yinjkh", param);
		System.out.println(param);
		int num = 1;
		try {
			Object obj = query.uniqueResult();
			num = Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return true;
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return num > 0;
	}
	/**
	 * 空白印鉴卡管理
	 */
	public int countYinjkNum(Yinjk yinjk, String endYinjkh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		StringBuffer hql = new StringBuffer();
		String beginYinjkh = yinjk.getYinjkh();
		hql
				.append("select count(*) from Yinjk where yinjkh>=:yinjkh and yinjkh<=:endYinjkh ");
		Class c = yinjk.getClass();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals("yinjkh"))
					continue;
				Object temp = field.get(yinjk);
				if (temp == null || ((String) temp).equals(""))
					continue;

				hql.append(" and ").append(field.getName()).append("=:")
						.append(field.getName());
				params.put(field.getName(), temp);
			}
			Query query = session.createQuery(hql.toString());
			query.setString("yinjkh", yinjk.getYinjkh());
			query.setString("endYinjkh", endYinjkh);
			if (params.size() != 0) {
				Iterator<String> ite = params.keySet().iterator();
				while (ite.hasNext()) {
					String key = ite.next();
					query.setString(key, (String) params.get(key));
				}
			}

			Object obj = query.uniqueResult();
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return 0;
		} finally {
			getBaseHibernateDao().closeSession(session);
		}
	}

	public void save(Yinjk yinjk, int num) {
		
		BigInteger bd = new BigInteger(yinjk.getYinjkh());
		BigInteger temp = new BigInteger("1");
		Session session = getBaseHibernateDao().getHibernateSession();
		Transaction tran = session.beginTransaction();
		tran.begin();
		try {
			session.saveOrUpdate(yinjk);
			for (int i = 1; i < num; i++) {
				bd = bd.add(temp);
				Yinjk yinjkTemp=new Yinjk();
				copyMsg(yinjk,yinjkTemp);
				yinjkTemp.setYinjkh(bd.toString());
				session.saveOrUpdate(yinjkTemp);
				if(i%50==0){
					session.flush();
					session.clear();
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
			return;
		} finally {
			getBaseHibernateDao().closeSession(session);
		}
	}

	private void copyMsg(Yinjk yinjk, Yinjk yinjkTemp) {
		Class c =yinjk.getClass();
		try{
		Field[] fields=c.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object obj=field.get(yinjk);
			if(obj==null){
				continue;
			}
			String setMethodName="set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
			Method setMethod=c.getMethod(setMethodName, String.class);
			setMethod.invoke(yinjkTemp, field.get(yinjk));
			
		}
		
		}catch(Exception e){
			return;
		}
	}
	public void saveLog(YinjkManageLog log) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
	Transaction tran=	session.beginTransaction();
	tran.begin();
		try {
			session.save(log);
			tran.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tran.rollback();
			throw new HibernateException("保存印鉴卡操作日志失败！");
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
	}
	public List<Yinjk> getMinCodeYinjk(String clerkCode,int i) {
		Session session =super.getBaseHibernateDao().getHibernateSession();
		List<Yinjk> yinjkList=new ArrayList<Yinjk>();
	
		try {
			String hql="from Yinjk where clerkcode=:clerkCode and yinjkzt=:yinjkzt order by yinjkh";
			Query query=	session.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(i);
			query.setString("clerkCode", clerkCode);
			query.setString("yinjkzt", "可用");
			yinjkList=(List<Yinjk>)query.list();
		} finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return yinjkList;
	}
	public int countYinjkNumForDestroy(Yinjk yinjk, String endYinjkh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		StringBuffer hql = new StringBuffer();
		String beginYinjkh = yinjk.getYinjkh();
		hql
				.append("select count(*) from Yinjk where yinjkh>=:yinjkh and yinjkh<=:endYinjkh ");
		Class c = yinjk.getClass();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals("yinjkh"))
					continue;
				Object temp = field.get(yinjk);
				if (temp == null || ((String) temp).equals(""))
					continue;
				
				hql.append(" and ").append(field.getName());
				if(field.getName().equals("yinjkzt")){
					hql.append("<>:");
					}else{
						hql.append("=:");
				}
				hql.append(field.getName());
				params.put(field.getName(), temp);
			}
			Query query = session.createQuery(hql.toString());
			query.setString("yinjkh", yinjk.getYinjkh());
			query.setString("endYinjkh", endYinjkh);
			if (params.size() != 0) {
				Iterator<String> ite = params.keySet().iterator();
				while (ite.hasNext()) {
					String key = ite.next();
					query.setString(key, (String) params.get(key));
				}
			}

			Object obj = query.uniqueResult();
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return 0;
		} finally {
			getBaseHibernateDao().closeSession(session);
		}
	
	}
	
}