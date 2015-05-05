package com.unitop.sysmgr.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.config.Transfer;
import com.unitop.framework.util.StringUtil;
import com.unitop.sysmgr.bo.Chanpgncd;
import com.unitop.sysmgr.bo.ChanpgncdId;
import com.unitop.sysmgr.bo.Jicgncd;
import com.unitop.sysmgr.bo.Menu;
import com.unitop.sysmgr.bo.Tree;
import com.unitop.sysmgr.dao.DoMenuDao;
@Repository("DoMenuDaoImpl")
public class DoMenuDaoImpl  extends BaseDataResources  implements DoMenuDao {

	public List selectForMenu() {
		List returnList = new ArrayList();
		List list = new ArrayList();
		List list1 = new ArrayList();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
		//	String sql ="select  c1.GONGNID, c1.SHANGJGN ,c1.GONGNMC,c1.GONGNURL,c1.ZHUANGT,c1.BEIZ,c1.CLASSID from chanpgncd c1 ,(select * from chanpgncd where shangjgn ='null') c2 where c1.shangjgn=c2.gongnid order by c1.gongnsx";
			String sql="select  c1.GONGNID, c1.SHANGJGN ,c1.GONGNMC,c1.GONGNURL,c1.ZHUANGT,c1.BEIZ,c1.CLASSID from chanpgncd c1 where shangjgn='1' order by c1.gongnsx ";
			SQLQuery query = session.createSQLQuery(sql);
			list = query.list();
			for(int i=0;i<list.size();i++){
				Object[] map = (Object[]) list.get(i);
				Tree t = new Tree();
				t.setId((String) map[0]);
				t.setName((String) map[2]);
				t.setUrl(StringUtil.nvl((String) map[3]));
				t.setIsShow((String) map[4]);
				t.setBeiz((String) map[5]);
				t.setClassid(StringUtil.nvl((String) map[6]));
				{
					if(StringUtil.isEmpty(t.getClassid())){
						Query query1 = session.createQuery("from Chanpgncd where shangjgn=:id order by gongnsx");
						query1.setString("id",t.getid());
						list1 = query1.list();
						for(int j=0;j<list1.size();j++){
							Chanpgncd chanpgncd = (Chanpgncd) list1.get(j);
							Tree t1 = new Tree();
							t1.setId(chanpgncd.getId().getGongnid());
							t1.setName(chanpgncd.getGongnmc());
							t1.setUrl(chanpgncd.getGongnurl());
							t1.setIsShow(chanpgncd.getZhuangt());
							t1.setBeiz(chanpgncd.getBeiz());
							t1.setClassid(chanpgncd.getClassid());
							t.getList().add(t1);
							t.privateNameStringAppend(chanpgncd.getId().getGongnid());
						}
					}
					t.privateNameStringAppend((String) map[0]);
				}
				returnList.add(t);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		return returnList;
	}

	/**
	 * 集成功能菜单
	 * 表Jicgncd
	 */
	public Menu getMenuById(String  gongnid,String zignid,String shangjgn) throws SQLException {
		Menu menu = new Menu();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Jicgncd where gongnid=:gongnid and zignid = :zignid");
			query.setString("gongnid", gongnid);
			query.setString("zignid", zignid);
			List list = query.list();
			for(int i=0;i<list.size();i++){
				Jicgncd jicgncd = (Jicgncd) list.get(i);
				menu.setGongnid(jicgncd.getId().getGongnid());
				menu.setGongnmc(jicgncd.getGongnmc());
				menu.setGongnlx(jicgncd.getGongnlx());
				menu.setGongnsx(0);//待定
				menu.setGongnurl(jicgncd.getGongnurl());
				menu.setCaidlx(jicgncd.getCaidlx());
				menu.setQuanxwz(jicgncd.getQuanxwz());
				menu.setShangjgn(shangjgn);//待定
				menu.setBeiz(jicgncd.getBeiz());
				menu.setZhuangt("1");//默认取 1
				menu.setZignid(jicgncd.getId().getZignid());
				menu.setClassid(StringUtil.nvl(jicgncd.getClassid()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		return menu;
	}

	public void saveProMenu(Menu menu) throws SQLException {
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Chanpgncd chanpgncd = new Chanpgncd();
			ChanpgncdId id = new ChanpgncdId();
			id.setGongnid(menu.getGongnid());
			id.setZignid(menu.getZignid());
			chanpgncd.setId(id);
			chanpgncd.setGongnmc(menu.getGongnmc());
			chanpgncd.setGongnsx(new BigDecimal(menu.getGongnsx()));
			chanpgncd.setShangjgn(menu.getShangjgn());
			chanpgncd.setBeiz(menu.getBeiz());
			chanpgncd.setZhuangt(menu.getZhuangt());
			chanpgncd.setQuanxwz(menu.getQuanxwz());
			chanpgncd.setGongnlx(menu.getGongnlx());
			chanpgncd.setClassid(menu.getClassid());
			chanpgncd.setGongnurl(menu.getGongnurl());
			session.save(chanpgncd);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
	}

	public void deleteProMenu(String gongnid, String zignid)
			throws SQLException {
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Chanpgncd chanpgncd = new Chanpgncd();
			ChanpgncdId id = new ChanpgncdId();
			id.setGongnid(gongnid);
			id.setZignid(zignid);
			chanpgncd = (Chanpgncd) session.get(Chanpgncd.class, id);
			session.delete(chanpgncd);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
	}

	/**
	 * 引用的方法来源已被注释，不起作用
	 */
	public List getCaidlx() throws SQLException {
		List rlist = new ArrayList();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from jicgncd group by caidlx");
			List list = query.list();
			for(int i=0;i<list.size();i++){
				rlist.add(((Jicgncd)list.get(i)).getCaidlx());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		return rlist;		
	}
	
	//根据ID查找菜单
	public Menu getMenuForId(String gongnid,String zignid) throws SQLException {
		Menu menu = null; 
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			ChanpgncdId id = new ChanpgncdId();
			id.setGongnid(gongnid);
			id.setZignid(zignid);
			Chanpgncd chanpgncd = (Chanpgncd) session.get(Chanpgncd.class, id);
			if(chanpgncd != null)
			{
				//当菜单不为空时候 创建菜单对象
				menu = new Menu();
				menu.setGongnid(chanpgncd.getId().getGongnid());
				menu.setZignid(chanpgncd.getId().getZignid());
				menu.setShangjgn(StringUtil.nvl(chanpgncd.getShangjgn()));
				menu.setGongnmc(StringUtil.nvl(chanpgncd.getGongnmc()));
				menu.setGongnurl(StringUtil.nvl(chanpgncd.getGongnurl()));
				menu.setGongnsx(chanpgncd.getGongnsx().intValue());
				menu.setQuanxwz(StringUtil.nvl(chanpgncd.getQuanxwz()));
				menu.setZhuangt(StringUtil.nvl(chanpgncd.getZhuangt()));
				menu.setBeiz(StringUtil.nvl(chanpgncd.getBeiz()));
				menu.setClassid(StringUtil.nvl(chanpgncd.getClassid()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		return menu;
	}

	public void updateMenu(Menu menu) throws SQLException {
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			ChanpgncdId id = new ChanpgncdId();
			id.setGongnid(menu.getGongnid());
			id.setZignid(menu.getZignid());
			Chanpgncd chanpgncd = (Chanpgncd) session.get(Chanpgncd.class, id);
			chanpgncd.setId(id);
			chanpgncd.setGongnmc(menu.getGongnmc());
			chanpgncd.setGongnurl( menu.getGongnurl());
			chanpgncd.setShangjgn(menu.getShangjgn());
			chanpgncd.setGongnsx(new BigDecimal(menu.getGongnsx()));
			chanpgncd.setQuanxwz(menu.getQuanxwz());
			chanpgncd.setZhuangt(menu.getZhuangt());
			chanpgncd.setBeiz(menu.getBeiz());
			chanpgncd.setClassid(menu.getClassid());
			session.save(chanpgncd);
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
	}
	
	/**
	 * 引用来源页已弃用，暂时搁置
	 */
	public List  getMenuForName(String name) {
		List list = new ArrayList();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			String hql = "";
			if (StringUtil.isEmpty(name)){
				hql = "from jicgncd";
				
			}else{
				hql = "from jicgncd where caidlx=:name";
			}
			Query query = session.createQuery(hql);
			if (StringUtil.isEmpty(name)){
				query.setString("name", name);
			}
			List rlist = query.list();
			for(int i=0;i<rlist.size();i++){
				Menu menu =new Menu(); 
				Chanpgncd chanpgncd = (Chanpgncd) rlist.get(i);
				menu.setGongnid(chanpgncd.getId().getGongnid());
				menu.setZignid(chanpgncd.getId().getZignid());
				menu.setGongnmc(StringUtil.nvl(chanpgncd.getGongnmc()));
				menu.setGongnurl(StringUtil.nvl(chanpgncd.getGongnurl()));
				menu.setQuanxwz(StringUtil.nvl(chanpgncd.getQuanxwz()));
				menu.setClassid(StringUtil.nvl(chanpgncd.getClassid()));
				list.add(menu);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		
		return list;
	}
	public List getMenuForShangjgn(String gongnid) throws SQLException {
		List list = new ArrayList();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Chanpgncd where shangjgn=:shangjgn order by gongnsx");
			query.setString("shangjgn", gongnid);
			List rlist = query.list();
			
			for(int i=0;i<rlist.size();i++){
				Menu menu =new Menu(); 
				Chanpgncd chanpgncd = (Chanpgncd) rlist.get(i);
				menu.setGongnid(chanpgncd.getId().getGongnid());
				menu.setZignid(chanpgncd.getId().getZignid());
				menu.setGongnmc(StringUtil.nvl(chanpgncd.getGongnmc()));
				menu.setGongnurl(StringUtil.nvl(chanpgncd.getGongnurl()));
				menu.setShangjgn(chanpgncd.getShangjgn());
				menu.setGongnsx(chanpgncd.getGongnsx().intValue());
				menu.setQuanxwz(StringUtil.nvl(chanpgncd.getQuanxwz()));
				menu.setZhuangt(Transfer.caidChange(StringUtil.nvl(chanpgncd.getZhuangt())));
				menu.setBeiz(StringUtil.nvl(chanpgncd.getBeiz()));
				menu.setClassid(StringUtil.nvl(chanpgncd.getClassid()));
				list.add(menu);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		
		return list;
	}
	public void save(Menu menu) {
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			ChanpgncdId id = new ChanpgncdId();
			id.setGongnid(menu.getGongnid());
			id.setZignid(menu.getZignid());
			Chanpgncd chanpgncd = new Chanpgncd();
			chanpgncd.setId(id);
			chanpgncd.setGongnmc(menu.getGongnmc());
			chanpgncd.setGongnurl( menu.getGongnurl());
			chanpgncd.setShangjgn(menu.getShangjgn());
			chanpgncd.setGongnsx(new BigDecimal(menu.getGongnsx()));
			chanpgncd.setQuanxwz(menu.getQuanxwz());
			chanpgncd.setZhuangt(menu.getZhuangt());
			chanpgncd.setBeiz(menu.getBeiz());
			chanpgncd.setClassid(menu.getClassid());
			session.save(chanpgncd);
			session.flush();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
	}
	
	public List getAllFunctions(){
		List list = new ArrayList();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Jicgncd order by gongnlx");
			List rlist = query.list();
			for(int i=0;i<rlist.size();i++){
				Jicgncd jicgncd = (Jicgncd) rlist.get(i);
				Menu menu =new Menu(); 
				menu.setGongnid(jicgncd.getId().getGongnid());
				menu.setZignid(jicgncd.getId().getZignid());
				menu.setGongnmc(jicgncd.getGongnmc());
				menu.setGongnurl(jicgncd.getGongnurl());
				menu.setQuanxwz(jicgncd.getQuanxwz());
				list.add(menu);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		return list;
	}
	
   public Map getAllChanp(){
	   Map map = new HashMap();
	   Session session = getBaseHibernateDao().getHibernateSession();
	   try{
		   Query query = session.createQuery("from Chanpgncd order by gongnsx");
		   List rlist = query.list();
		   for(int i=0;i<rlist.size();i++){
			   Chanpgncd chanpgncd = (Chanpgncd) rlist.get(i);
			   Menu menu = new Menu();
			   menu.setGongnid(chanpgncd.getId().getGongnid());
			   menu.setZignid(chanpgncd.getId().getZignid());
			   menu.setShangjgn(StringUtil.nvl(chanpgncd.getShangjgn()));
			   menu.setGongnmc(StringUtil.nvl(chanpgncd.getGongnmc()));
			   menu.setGongnurl(StringUtil.nvl(chanpgncd.getGongnurl()));
			   menu.setGongnsx(chanpgncd.getGongnsx().intValue());
			   menu.setGongnlx(StringUtil.nvl(chanpgncd.getGongnlx()));
			   menu.setQuanxwz(StringUtil.nvl(chanpgncd.getQuanxwz()));
			   menu.setZhuangt(StringUtil.nvl(chanpgncd.getZhuangt()));
			   menu.setBeiz(StringUtil.nvl(chanpgncd.getBeiz()));
			   menu.setClassid(StringUtil.nvl(chanpgncd.getClassid()));
			   map.put(chanpgncd.getId().getGongnid()+"|"+chanpgncd.getId().getZignid(), menu);
		   	}
	   	}catch (Exception e) {
	   		e.printStackTrace();
	   	}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
		return map;
   }
	
	/**
	 * 删除所有 产品功能菜单
	 */
	public void deleteAllChanp() {
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("delete from Chanpgncd");
			query.executeUpdate();
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	   		super.getBaseHibernateDao().closeSession(session);
	   	}
	}
}
