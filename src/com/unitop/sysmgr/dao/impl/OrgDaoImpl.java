package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.unitop.config.DBinfoConig;
import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.AsynSealCheckConfig;
import com.unitop.sysmgr.bo.CacheConfig;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Zhipyxxx;
import com.unitop.sysmgr.dao.OrgDao;
import com.unitop.sysmgr.dao.SystemControlParametersDao;

@Repository("OrgDaoImpl")
public class OrgDaoImpl extends BaseDataResources  implements OrgDao{
	private static Connection conn;
	
	
	public Connection getConn() {
		if(conn==null){
			conn=this.getBaseJDBCDao().getConnection();
		}
		return conn;
	}

	@Resource
	public SystemControlParametersDao SystemControlParametersDao;
	
	//获取所有机构  
	@SuppressWarnings("unchecked")
	public List getAllOrg() {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			list = session.createSQLQuery("from Org order by org.code").list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	//修改机构
	public void updateOrg (Org bo) throws BusinessException {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try{
			session.saveOrUpdate(bo);
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//获取机构
	public Org getOrgByCode(String code) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		Org org = null;
		try{
			org = (Org)session.get(Org.class, code);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return org;
	}

	private static Log log = LogFactory.getLog(OrgDaoImpl.class);

	public boolean CanOperDesOrg(String OperOrg, String DesOrg) {
		log.info("CanOperDesOrg[" + OperOrg + ", " + DesOrg + "]");
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		boolean ReturnValue = false;
		try {
			if (OperOrg.equals(DesOrg)) {
				ReturnValue = true;
			} else {
				String sql = "";
				String db_type = DBinfoConig.getDBType();
				if("oracle".equals(db_type))
				{
					sql="SELECT internalorganizationnumber as C FROM organarchives"
						+ "WHERE internalorganizationnumber IN ("
						+ "select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber start with internalorganizationnumber=:OperOrg"
						+ ") AND internalorganizationnumber=:DesOrg";
				}else{
					sql="SELECT internalorganizationnumber as C FROM organarchives"
						+ "WHERE internalorganizationnumber IN (select * from TABLE(ORGFUNCTION(:OperOrg))) AND internalorganizationnumber=:DesOrg";
				}
					SQLQuery qu = session.createSQLQuery(sql);
					qu.setString("OperOrg", OperOrg);
					qu.setString("DesOrg", DesOrg);
					List list = qu.list();
					ReturnValue = list.size() ==1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return ReturnValue;
	}

	public void deleteOrg(Org org) throws BusinessException {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		String code = org.getCode();
		//Transaction ts =session.beginTransaction();
		//ts.begin();
		try{
			Query q1=session.createSQLQuery("delete from guiyjsgxb where guiyid in (select clerknum from clerktable c where c.internalorganizationnumber=:code)");
			q1.setString("code", code);
			q1.executeUpdate();
			
			q1=session.createSQLQuery("delete clerktable c where c.internalorganizationnumber =:code ");
			q1.setString("code", code);
			q1.executeUpdate();
			//super.getBaseHibernateDao().getDaoHibernateTemplate().bulkUpdate("delete Clerk c where c.orgcode = ?",new Object[] {code});

			//q1=session.createSQLQuery("delete Zhanghb a where a.jigh =:code ");
			//q1.setString("code", code);
			//q1.executeUpdate();
			//super.getBaseHibernateDao().getDaoHibernateTemplate().bulkUpdate("delete Zhanghb a where a.jigh = ?",new Object[] { code });
			q1=session.createSQLQuery("delete organarchives a where a.internalorganizationnumber =:code ");
			q1.setString("code", code);
			q1.executeUpdate();
			//session.delete(org);

		//	q1=session.createSQLQuery("delete from Yinjzhb a where a.zhangh in (select zhangh from zhanghb b where b.internalorganizationnumber=:code )");
			//q1.setString("code", code);
			//q1.executeUpdate();
			//session.createSQLQuery("delete from Yinjzhb a where a.zhangh in (select zhangh from zhanghb b where b.internalorganizationnumber='"+code+"' )").executeUpdate();
			//super.getBaseHibernateDao().getDaoHibernateTemplate().bulkUpdate("delete Yinjb s where s.jigh = ?", new Object[] { code });	
			//session.createSQLQuery("delete from pingzpzb  where internalorganizationnumber='"+code+"'").executeUpdate();
			//session.createQuery("delete Pingzxtlxgxb  where internalorganizationnumber ='"+code+"' ").executeUpdate();	
			//session.createQuery("delete Pingzyjlxgxb  where internalorganizationnumber ='"+code+"' ").executeUpdate();	
			//ts.commit();
		}catch (Exception e) {
			//ts.rollback();
			e.printStackTrace();
			throw new BusinessException(e);
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public Org findOrgByOrgannum(String organnum) throws Exception {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Org org = null;
		try{
			org = (Org)session.get(Org.class, organnum);
			session.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return org;
	}

	public List getNetpointflag(String code) throws Exception {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String orgcode = code.substring(0, 6);
		List<String> list = new ArrayList<String>();
		String query = "select a.account,a.netpointflag from accountinfo a,accountexpand t "
			+ "where a.account = t.account(+) and t.str10 <> ' ' and "
			+ "substr(a.netpointflag,0,6) like :orgcode";
		String sql = "";
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			sql="select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber "
				+ "start with internalorganizationnumber= :code";
		}else{
			sql="select * from TABLE(ORGFUNCTION(:code))";
		}
		try{
			SQLQuery query1 = session.createSQLQuery(sql);
			query1.setString("code", code);
			SQLQuery query2 = session.createSQLQuery(query);
			query2.setString("orgcode", orgcode+"%");
			List list1 = query1.list();
			if(list1==null)  list1=new ArrayList();
			List list2 = query2.list();
			if(list2==null)  list2=new ArrayList();
		for (Iterator iter2 = list2.iterator(); iter2.hasNext();) 
		  {
			 Object[] element = (Object[])iter2.next();
			 list.add((String)element[1]);
		  }
		for (Iterator iter1 = list1.iterator(); iter1.hasNext();) 
		{
			 Object[] element = (Object[])iter1.next();
			 list.add((String)element[0]);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
		
	}

	public Org getOrgByOrgCode(String orgCode) {
		Org org = null;
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try{
			if(orgCode==null||orgCode.equals("")){
				return org;
			}
			org = (Org)session.get(Org.class, orgCode);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return org;
	}

	public List getOrgChildrenByCode(String code) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			if (code == null || code.length() == 0)
			{
				 list = session.createQuery("from Org  where parentCode is null order by code").list();
				
			}else{
				Query query = session.createQuery("from Org  where parentCode =:code order by code");
				query.setString("code", code);
				list=query.list();
				if(list == null) list = new ArrayList();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
		return list ;
	}

	public List getOrgList(String parentCode) {
		List list = null;
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try{
			String sql = "from Org org where parentCode=:parentCode or code=:parentCode2 order by org.code";
			Query query=session.createQuery(sql);
			query.setString("parentCode", parentCode);
			query.setString("parentCode2", parentCode);
			list = query.list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
		
	}

	public Org getParentCode(String code) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		Org org = null;
		try{
			org = (Org)session.get(Org.class, code);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return org;
	}

	public void mergeOrg(String oldCode, String newCode)throws BusinessException {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		String cbjb=null;
		Transaction transaction = null;
		try {
			cbjb= ""+(Integer.valueOf(SystemControlParametersDao.findSystemControlParameterById("org_ranking").toString())+1);
			transaction = session.beginTransaction();
			Org newOrg = this.getOrgByCode(newCode);
			Org oldOrg = this.getOrgByCode(oldCode);
			if (newOrg == null)
				throw new BusinessException("机构代码'" + newCode + "'不存在");
			else if (!newOrg.getWdflag().equals(cbjb))
				throw new BusinessException("机构'" + newCode + "'不是"+cbjb+"级机构");
			else if (!newOrg.getShOrgCode().equals(oldOrg.getShOrgCode())) {
				throw new BusinessException("两机构不在同一个省行内");
			} else if (oldCode.equals(newCode))
				throw new BusinessException("机构代码无效");
			Org bo = this.getOrgByCode(oldCode);
			session.delete(bo);
			String updateSql1 = "delete Clerk c where c.orgcode = "+oldCode;
			String updateSql2 = "update Accountinfo a set a.netpointflag = "+newCode+" where a.netpointflag = "+oldCode;
			session.createSQLQuery(updateSql1);
			session.createSQLQuery(updateSql2);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void updateOrg(String account, String orgcode) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.unitop.sysmgr.dao.OrgDao#exportOrg(java.lang.String)
	 */
	public List<Org> exportOrg(String orgcode) {
		Session session = getBaseHibernateDao().getHibernateSession();
		List<Org> list1 = new ArrayList<Org>();
		try{
			String sql = "select internalorganizationnumber,legalname,p_internalorganizationnumber,n_paymentnum,wdflag,f_internalorganizationnumber,tongdgz " +
				"from organarchives where p_internalorganizationnumber= :orgcode  or internalorganizationnumber= :orgcode order by p_internalorganizationnumber";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("orgcode", orgcode);
			List list = query.list();
			if(list==null) list=new ArrayList();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				Org org = new Org();
				org.setCode((String) element[0]);
				org.setName((String) element[1]);
				org.setParentCode((String) element[2]);
				org.setPaymentCode((String)element[3]);
				org.setWdflag((String)element[4]);
				org.setShOrgCode((String) element[5]);
				org.setTctd((String) element[6]);
				list1.add(org);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list1;
	}

	/**
	 * @see com.unitop.sysmgr.dao.OrgDao#importOrg(com.unitop.sysmgr.bo.Org)
	 */
	public boolean importOrg(Org org) {
		Session session = getBaseHibernateDao().getHibernateSession();
		boolean rs = false;
		try{
			String sql = "insert into organarchives (INTERNALORGANIZATIONNUMBER, LEGALNAME, P_INTERNALORGANIZATIONNUMBER, N_PAYMENTNUM, WDFLAG, F_INTERNALORGANIZATIONNUMBER, TONGDGZ) " +
				" values (:ORGANNUM, :ORGANNAME, :N_PARENTNUM, :N_PAYMENTNUM, :WDFLAG, :SHENGHJGH, :TONGDGZ)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("ORGANNUM", org.getCode());
			query.setString("ORGANNAME", org.getName());
			query.setString("N_PARENTNUM", org.getParentCode());
			query.setString("N_PAYMENTNUM", org.getPaymentCode());
			query.setString("WDFLAG", org.getWdflag());
			query.setString("SHENGHJGH", org.getShOrgCode());
			query.setString("TONGDGZ", org.getTctd());
			query.executeUpdate();
			rs=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return rs;
	}

	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.dao.OrgDao#getAllOrg(java.lang.String)
	 */
	public List<Org> getAllOrg(String orgcode) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Org> list1 = new ArrayList<Org>();
		String sql = "";
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			sql="select internalorganizationnumber,legalname,p_internalorganizationnumber,n_paymentnum,wdflag,f_internalorganizationnumber,tongdgz from organarchives " +
				" where internalorganizationnumber in (select internalorganizationnumber from organarchives connect by " +
				" prior internalorganizationnumber=p_internalorganizationnumber start with internalorganizationnumber= :organnum) order by p_internalorganizationnumber";
		}else{
			sql="select internalorganizationnumber,legalname,p_internalorganizationnumber,n_paymentnum,wdflag,f_internalorganizationnumber,tongdgz from organarchives " +
				" where internalorganizationnumber in (select * from TABLE(ORGFUNCTION(:organnum)) AS test)";
		}
		try{
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("organnum", orgcode);
			List list = query.list();
			if(list==null)  list=new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) 
		{
			 Org org=new Org();
			 Object[] element = (Object[])iter.next();
			 org.setCode((String)element[0]);
			 org.setName((String)element[1]);
			 org.setParentCode((String)element[2]);
			 org.setPaymentCode(String.valueOf(element[3]));
			 org.setWdflag(element[4].toString());
			 org.setShOrgCode((String)element[5]);
			 org.setTctd((String)element[6]);
			 list1.add(org);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list1;
	}
	//获取具有卡柜的机构集合
	public List<Org> getKagOrgs(String orgcode) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Org> list1 = new ArrayList<Org>();
		String sql = "";
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			sql="select internalorganizationnumber,legalname,p_internalorganizationnumber,n_paymentnum,wdflag,f_internalorganizationnumber,tongdgz from organarchives " +
				" where internalorganizationnumber in (select internalorganizationnumber from organarchives connect by " +
				" prior internalorganizationnumber=n_parentnum start with internalorganizationnumber= :organnum) and internalorganizationnumber in(select internalorganizationnumber from kagb) order by p_internalorganizationnumber";
		}else{
			sql="select internalorganizationnumber,legalname,p_internalorganizationnumber,n_paymentnum,wdflag,f_internalorganizationnumber,tongdgz from organarchives " +
				" where internalorganizationnumber in (select * from TABLE(ORGFUNCTION(:organnum)) AS test)and internalorganizationnumber in(select internalorganizationnumber from kagb) ";
		}
		try{
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("organnum", orgcode);
			List list = query.list();
			if(list==null)  list=new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) 
		{
			 Org org=new Org();
			 Object[] element = (Object[])iter.next();
			 org.setCode((String)element[0]);
			 org.setName((String)element[1]);
			 org.setParentCode((String)element[2]);
			 org.setPaymentCode(String.valueOf(element[3]));
			 org.setWdflag(String.valueOf(element[4]));
			 org.setShOrgCode((String)element[5]);
			 org.setTctd((String)element[6]);
			 list1.add(org);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list1;
	}

	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.dao.OrgDao#getOrgCount(java.lang.String)
	 */
	public int getOrgCount(String code) {
		Connection con = super.getBaseJDBCDao().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "select count(*) from organarchives where p_internalorganizationnumber=? ";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, code);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}
	
	public int getOrgCountAll(String code) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		int element=0;
		String sql = "";
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			sql="select count(*) from organarchives "+
			" where internalorganizationnumber in (select internalorganizationnumber from organarchives connect by " +
			" prior internalorganizationnumber=p_internalorganizationnumber start with internalorganizationnumber=:organnum)";
		}else{
			sql="select count(internalorganizationnumber) from TABLE(ORGFUNCTION(:organnum))";
		}
		try{
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("organnum", code);
		    element = new Integer(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return element;
	}
	
	//获取机构通存通兑机构列表
	public List getOrgListForTCTD(String wdflag,String shenghOrgCode) {
		List list = null;
		if (wdflag.equals("0"))
		{
			list = super.getBaseHibernateDao().getDaoHibernateTemplate().find("from Org o where o.wdflag = '1' order by o.code");
		}else
			list = super.getBaseHibernateDao().getDaoHibernateTemplate().find("from Org o where o.wdflag = '1' and o.code=?  order by o.code",new Object[] {shenghOrgCode});
		return list;
	}
	
	//更新机构通存通兑 关系
	public void updateForOrg(String orgCode, String tctd) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "update organarchives set tongdgz=:tctd where internalorganizationnumber=:organnum or f_internalorganizationnumber=:shenghjgh";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("tctd", tctd);
			query.setString("organnum", orgCode);
			query.setString("shenghjgh", orgCode);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//更新机构通存通兑 关系
	public boolean validateOrg(String str1, String str2) {
		boolean flag = false;
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "";
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type))
			{
				sql="select * from (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber =:organnum) where internalorganizationnumber=:organnum_";
			}else{
				sql="select * from (select * from TABLE(ORGFUNCTION(:organnum))) where internalorganizationnumber=:organnum_";
			}
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("organnum", str1);
			query.setString("organnum_", str2);
			List list = query.list();
			if(list!=null)
			{
				flag = list.size()>0?true:false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return flag;
	}
	/**
	 * @param srcorg 操作机构
	 * @param relorg 目标机构
	 */
	public void updateOrgRelation(String srcorg, String relorg) throws Exception {
	Session session =  super.getBaseHibernateDao().getHibernateSession();
	Org thisOrg=getOrgByCode(srcorg);
	Org relationOrg=getOrgByCode(relorg);
	String sh_code=relationOrg.getShOrgCode();
	
		
	if(thisOrg==null||relationOrg==null){
		throw new Exception();
	}
	//当目标机构为总行时 将当前操作机构的省行号设置为它本身
	if(relationOrg.getWdflag().equals("0"))
		sh_code=srcorg;
	Integer thisOrgWd=Integer.parseInt(thisOrg.getWdflag());
	Integer relationOrgWd=Integer.parseInt(relationOrg.getWdflag());
	boolean tongdxg=thisOrgWd==2||(thisOrgWd==3&&relationOrgWd==1)?true:false;//是否修改 该机构下属机构的账户的通兑标志
	
	int cha=thisOrgWd -(relationOrgWd+1);
	Transaction ts=session.beginTransaction();
	ts.begin();//开启事务
	try {
			
			if(tongdxg){
				SQLQuery query6 = session.createSQLQuery("update zhanghb z set tongctd ='一级分行全辖' where z.tongctd='二级分行' and z.internalorganizationnumber in (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber = :souorg)");
				query6.setString("souorg", srcorg);
				query6.executeUpdate();
			}
			//修改操作机构的上级机构为目标机构
			SQLQuery query7 = session.createSQLQuery("update organarchives set p_internalorganizationnumber=:relorg  where internalorganizationnumber=:srcorg");
			query7.setString("relorg", relorg);
			query7.setString("srcorg", srcorg);
			query7.executeUpdate();
			
			//修改机构及其子机构的一级分行行号
			String sql ="update organarchives set f_internalorganizationnumber=:shenghjgh where internalorganizationnumber in (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber = :souorg)";
			SQLQuery query8=session.createSQLQuery(sql);
			query8.setString("shenghjgh", sh_code);
			query8.setString("souorg", srcorg);
			query8.executeUpdate();
			 sql ="update organarchives set wdflag=wdflag -"+cha+" ,f_internalorganizationnumber=:shenghjgh where internalorganizationnumber=:srcorg and wdflag<>'3'";
			if(cha<0){
				cha=0-cha;
				sql ="update organarchives set wdflag=wdflag +"+cha+" ,f_internalorganizationnumber=:shenghjgh where internalorganizationnumber=:srcorg and  wdflag<>'3'";
				}
			SQLQuery squery=session.createSQLQuery(sql);
			squery.setString("shenghjgh", sh_code);
			squery.setString("srcorg", srcorg);
			squery.executeUpdate();
			ts.commit();
			ts.begin();
			//同步机构及其子机构的柜员的网点标示和一级分行行号
			List<Org> allSrcOrg=getAllOrg(srcorg);
			sql ="update clerktable set f_internalorganizationnumber=:shenghjgh , wdflag=:wdflag where internalorganizationnumber=:orgcode";
			
				for (Org org : allSrcOrg) {
					SQLQuery squery2=session.createSQLQuery(sql);
					squery2.setString("shenghjgh", sh_code);
					squery2.setString("wdflag", org.getWdflag());
					squery2.setString("orgcode", org.getCode());
					squery2.executeUpdate();
				}
			ts.commit();
		} catch (Exception e) {
			e.printStackTrace();
			ts.rollback();
			
		}finally{
			 super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	
/*	private void tongbGuiyInfo(List<Org> allSrcOrg,String sh_code){
		Session session =this.getBaseHibernateDao().getHibernateSession();
		session.beginTransaction().begin();
		String sql ="update clerktable set shenghjgh=:shenghjgh , wdflag=:wdflag where n_organnum=:orgcode";
		try {
			for (Org org : allSrcOrg) {
				SQLQuery squery2=session.createSQLQuery(sql);
				squery2.setString("shenghjgh", sh_code);
				squery2.setString("wdflag", org.getWdflag());
				//System.out.println("orgname : "+org.getName());
				//System.out.println("wdflag:  "+org.getWdflag());
				squery2.setString("orgcode", org.getCode());
			//	System.out.println("orgcode : "+org.getCode());
	
				squery2.executeUpdate();
			}
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 根据分行号获得支票影像同步地址
	 */
	public String getSocketaddByJigh(String jigh) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		String socketadd = null;
		try{
			Query query = session.createQuery("from Zhipyxxx where  jigh=:jigh");
			query.setString("jigh", jigh);
			Zhipyxxx zhip =  (Zhipyxxx) query.uniqueResult();
			socketadd = zhip.getIp()+":"+zhip.getPort();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return socketadd;
	}
	public List<Org> getAllOrgByWdflag(String string) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		List<Org> orgList=new ArrayList<Org>();
		try{
			Query query = session.createQuery("from Org where  wdflag=:wdflag");
			query.setString("wdflag", string);
			orgList=(List<Org>)query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return orgList;
	}
	
	public Zhipyxxx getZhipyxx(String code) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		Zhipyxxx zhipyxxx=new Zhipyxxx();
		try{
			Query query = session.createQuery("from Zhipyxxx where  jigh=:jigh");
			query.setString("jigh", code);
			zhipyxxx=(Zhipyxxx)query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zhipyxxx;
	}
	
	public AsynSealCheckConfig getAsynSealCheckConfig(String code) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		AsynSealCheckConfig zuoyzxxx=new AsynSealCheckConfig();
		try{
			Query query = session.createQuery("from AsynSealCheckConfig where  jigh=:jigh");
			query.setString("jigh", code);
			zuoyzxxx=(AsynSealCheckConfig)query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zuoyzxxx;
	}
	
	public void updateOrSaveZhipyxxx(Zhipyxxx zhipyxxx) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		
		Transaction ts=session.beginTransaction();
		try{
			ts.begin();
			session.saveOrUpdate(zhipyxxx);
			ts.commit();
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public void updateOrSaveAsynSealCheckConfig(AsynSealCheckConfig zuoyzx) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		
		Transaction ts=session.beginTransaction();
		try{
			ts.begin();
			session.saveOrUpdate(zuoyzx);
			ts.commit();
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	@Override
	public CacheConfig getCacheConfigByOrgCode(String code) {
		Session session =  super.getBaseHibernateDao().getHibernateSession();
		CacheConfig yingxhc=new CacheConfig();
		try{
			Query query = session.createQuery("from CacheConfig where  jigh=:jigh");
			query.setString("jigh", code);
			yingxhc=(CacheConfig)query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return yingxhc;
	}
	
	public void updateOrSaveCacheConfig(CacheConfig yingxhc) {
	Session session =  super.getBaseHibernateDao().getHibernateSession();
		
		Transaction ts=session.beginTransaction();
		try{
			ts.begin();
			session.saveOrUpdate(yingxhc);
			ts.commit();
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
	}
	
	//-----------------------------------------------------------同步dao----------------------------------------------------------//
	
	public void deleteUndefindOrg_TB() throws DAOException {
		String sql ="delete organarchives where status='1'";
		PreparedStatement ps=null;
		Connection conn=getConn();
		try {
			ps=conn.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			throw new DAOException("删除机构失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Org getOrgByCode_TB(String orgCode) throws DAOException{
		Org org=null;
		Connection conn=getConn();
		PreparedStatement ps =null;
		ResultSet rs =null;
		String sql ="select internalorganizationnumber,legalname,wdflag,f_internalorganizationnumber,p_internalorganizationnumber,leixbs,status from organarchives where internalorganizationnumber=?";
		try{
		ps =conn.prepareStatement(sql);
		ps.setString(1, orgCode);
		rs= ps.executeQuery();
		while(rs.next()){
			org =createOrg_TB(rs);
			return org;
		}
		}catch (Exception e) {
			throw new DAOException("查询机构"+orgCode+"失败:"+e.getMessage(), e);
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
//	private Org createOrg(ResultSet rs) throws SQLException {
//		Org org =new Org();
//		org.setCode(rs.getString("organnum"));
//		org.setName(rs.getString("organname"));
//		org.setWdflag(rs.getString("wdflag"));
//		org.setShOrgCode(rs.getString("shenghjgh"));
//		org.setParentCode(rs.getString("n_parentnum"));
//		org.setLeixbs(rs.getString("leixbs"));
//		org.setStatus(rs.getString("status"));
//		return org;
//	}

	private Org createOrg_TB(ResultSet rs) throws SQLException {
		Org org =new Org();
		org.setCode(rs.getString("internalorganizationnumber"));
		org.setName(rs.getString("legalname"));
		org.setWdflag(rs.getString("wdflag"));
		org.setShOrgCode(rs.getString("f_internalorganizationnumber"));
		org.setParentCode(rs.getString("p_internalorganizationnumber"));
		org.setLeixbs(rs.getString("leixbs"));
		org.setStatus(rs.getString("status"));
		return org;
	}


	public void saveOrgList_TB(List<Org> orgList) throws DAOException  {
		String sql ="insert into organarchives(internalorganizationnumber,legalname,wdflag,f_internalorganizationnumber,p_internalorganizationnumber,leixbs,status) values(?,?,?,?,?,'1','0') ";
		PreparedStatement ps =null;
		Connection conn=getConn();
		try {
			for (Org org : orgList) {
				ps =conn.prepareStatement(sql);
				ps.setString(1, org.getCode());
				ps.setString(2, org.getName());
				ps.setString(3, org.getWdflag());
				ps.setString(4, org.getShOrgCode());
				ps.setString(5, org.getParentCode());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (Exception e) {
			throw new DAOException("批量新增机构失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public void updateAllOrgForEnd_TB() throws DAOException{
		String sql ="update organarchives set status='1'";
		PreparedStatement ps=null;
		Connection conn=getConn();
		try {
			ps=conn.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			throw new DAOException("修改机构类型标示失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void updateOrgList_TB(List<Org> orgList) throws DAOException{
		String sql ="update  organarchives set legalname=?,status='0' where internalorganizationnumber=? ";
		PreparedStatement ps =null;
		Connection conn=getConn();
		try {
			for (Org org : orgList) {
				ps =conn.prepareStatement(sql);
				ps.setString(1, org.getName());
				ps.setString(2, org.getCode());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (Exception e) {
			throw new DAOException("修改机构失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	public void saveOrg_TB(Org org) throws DAOException {
		if(org==null){
			throw new DAOException("新增机构失败,机构为空");
		}
		String sql ="insert into organarchives(internalorganizationnumber,legalname,wdflag,f_internalorganizationnumber,p_internalorganizationnumber,leixbs,status) values(?,?,?,?,?,'1','0') ";
		PreparedStatement ps =null;
		Connection conn=getConn();
		try {
				ps =conn.prepareStatement(sql);
				ps.setString(1, org.getCode());
				ps.setString(2, org.getName());
				ps.setString(3, org.getWdflag());
				ps.setString(4, org.getShOrgCode());
				ps.setString(5, org.getParentCode());
				ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("新增机构"+org.getCode()+"失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	public void updateOrg_TB(Org org) throws DAOException {
		if(org==null){
			throw new DAOException("修改机构失败,机构为空");
		}
		Connection conn=getConn();
		String sql ="update  organarchives set legalname=? ,status='0' where internalorganizationnumber=? ";
		PreparedStatement ps =null;
		try {
				ps =conn.prepareStatement(sql);
				ps.setString(1, org.getName());
				ps.setString(2, org.getCode());
				ps.execute();
		} catch (Exception e) {
			throw new DAOException("修改机构"+org.getCode()+"失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void addTemp_TB(Org org) throws DAOException {
		if(org==null){
			throw new DAOException("新增机构失败,机构为空");
		}
		Connection conn=getConn();
		String sql ="insert into org_temp(internalorganizationnumber,legalname,wdflag,f_internalorganizationnumber,p_internalorganizationnumber)  values(?,?,?,?,?)";
		PreparedStatement ps =null;
		try {
				ps =conn.prepareStatement(sql);
				ps.setString(1, org.getCode());
				ps.setString(2, org.getName());
				ps.setString(3, org.getWdflag());
				ps.setString(4,	org.getShOrgCode());
				ps.setString(5,org.getParentCode());
				ps.execute();
		} catch (Exception e) {
			throw new DAOException("添加核心机构["+org.getCode()+"]到临时表失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void clearTemp_TB() throws DAOException {
		String sql ="delete org_temp ";
		PreparedStatement ps =null;
		Connection conn=getConn();
		try {
				ps =conn.prepareStatement(sql);
				ps.execute();
		} catch (Exception e) {
			throw new DAOException("清空机构临时表失败:"+e.getMessage(), e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<Org> getAllTempOrg_TB() throws DAOException {
		List<Org> orgList=null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		Connection conn=getConn();
		String sql ="select internalorganizationnumber,legalname,wdflag,f_internalorganizationnumber,p_internalorganizationnumber from org_temp order by wdflag";
		try{
		ps =conn.prepareStatement(sql);
		rs= ps.executeQuery();
		orgList =new ArrayList<Org>();
		while(rs.next()){
			Org org =new Org();
			org.setCode(rs.getString("internalorganizationnumber"));
			org.setName(rs.getString("legalname"));
			org.setWdflag(rs.getString("wdflag"));
			org.setShOrgCode(rs.getString("f_internalorganizationnumber"));
			org.setParentCode(rs.getString("p_internalorganizationnumber"));
			orgList.add(org);
		}
		}catch (Exception e) {
			throw new DAOException("查询核心全部机构失败:"+e.getMessage(), e);
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return orgList;
	}
	
	public Org getOrgFromTemp_TB(String orgCode) throws DAOException {
		Org org=null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		Connection conn=getConn();
		String sql ="select internalorganizationnumber,legalname,wdflag,f_internalorganizationnumber,p_internalorganizationnumber from org_temp where internalorganizationnumber=?";
		try{
		ps =conn.prepareStatement(sql);
		ps.setString(1, orgCode);
		rs= ps.executeQuery();
		while(rs.next()){
		 org =new Org();
			org.setCode(rs.getString("internalorganizationnumber"));
			org.setName(rs.getString("legalname"));
			org.setWdflag(rs.getString("wdflag"));
			org.setShOrgCode(rs.getString("f_internalorganizationnumber"));
			org.setParentCode(rs.getString("p_internalorganizationnumber"));
			return org;
		}
		}catch (Exception e) {
			throw new DAOException("查询机构"+orgCode+"失败:"+e.getMessage(), e);
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	public Org getCenterBankFromTemp_TB() throws DAOException {
		Org org=null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		Connection conn=getConn();
		String sql ="select internalorganizationnumber,legalname,wdflag,f_internalorganizationnumber,p_internalorganizationnumber from org_temp where wdflag='0'";
		try{
		ps =conn.prepareStatement(sql);
		rs= ps.executeQuery();
		while(rs.next()){
		 org =new Org();
		 org.setCode(rs.getString("internalorganizationnumber"));
			org.setName(rs.getString("legalname"));
			org.setWdflag(rs.getString("wdflag"));
			org.setShOrgCode(rs.getString("f_internalorganizationnumber"));
			org.setParentCode(rs.getString("p_internalorganizationnumber"));
			return org;
		}
		}catch (Exception e) {
			throw new DAOException("查询总行机构失败:"+e.getMessage(), e);
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	public void closeConn_TB() throws DAOException {
		if(conn!=null){
			try {
				conn.close();
				conn=null;
			} catch (SQLException e) {
				throw new DAOException("关闭连接失败:"+e.getMessage(), e);
			}
		}
		
	}
	public void commit_TB() throws DAOException {
		try {
			if(conn==null){
				conn=getConn();
			}
			conn.commit();
		} catch (SQLException e) {
			throw new DAOException("事物提交失败:"+e.getMessage(), e);
		}
		
	}
	public void rollback_TB() throws DAOException {
		try {
			if(conn==null){
				throw new Exception("数据库连接为空");
			}
			conn.rollback();
		} catch (Exception e) {
			throw new DAOException("事物回滚失败:"+e.getMessage(), e);
		}	
	}
	public void startTransaction_TB() throws DAOException {
		if(conn==null){
			conn=getConn();
		}
		try {
			conn.setAutoCommit(false);
			conn.rollback();
		} catch (SQLException e) {
			throw new DAOException("开启事物失败:"+e.getMessage(), e);
		}
	}
	
	
	//查询缓存地址
	public String getCacheAddByCode(String orgCode){
		Connection conn=super.getBaseJDBCDao().getConnection();
		PreparedStatement ps =null;
		ResultSet rs =null;
		String sql ="select ip,port,timeout from cacheconfig where internalorganizationnumber=?";
		String add = null;
		try{
			ps =conn.prepareStatement(sql);
			ps.setString(1, orgCode);
			rs= ps.executeQuery();
			if(rs.next()){
				add = rs.getString("ip")+","+rs.getString("port")+","+rs.getString("timeout");
			}
				return add;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}finally{
				if(rs!=null){
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(ps!=null){
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(conn!=null){
						super.getBaseJDBCDao().closeConnection(conn);
				}
				
			}
	}
	//-----------------------------------------------------------End----------------------------------------------------------//
	

}
