package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery; 
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.unitop.config.DBinfoConig;
import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.framework.util.CloseRsPsCon;
import com.unitop.framework.util.PasswordUtil;
import com.unitop.framework.util.StringUtil;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.ClerkPasswordHistory;
import com.unitop.sysmgr.bo.Guiyjsgxb;
import com.unitop.sysmgr.bo.GuiyjsgxbId;
import com.unitop.sysmgr.bo.Shouqrzb;
import com.unitop.sysmgr.dao.ClerkDao;
@Repository("ClerkDaoImpl")
public class ClerkDaoImpl  extends BaseDataResources  implements ClerkDao {
	
	private static Connection conn;
	
	

	
	public Connection getConn(){
		if(conn==null)
			conn=super.getBaseJDBCDao().getConnection();
		return conn;
	}

	public void saveOrUpdate(Clerk clerk) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.saveOrUpdate(clerk);
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public boolean CanOperDesClerkCode(Clerk OperClerk, String DesClerkCode)
			throws BusinessException {
		return false;
	}

	public void deleteClerk(Clerk clerk) throws BusinessException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			session.delete(clerk);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public Clerk getClerkByCode(String code) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Clerk clerk = null;
		try{
			if(code==null||code.trim().equals("")){
				return null;
			}
			clerk = (Clerk)session.get(Clerk.class,code);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return clerk;		
	}

	public List<Clerk> getClerkByOrgcode(String orgcode) {
		Session session = getBaseHibernateDao().getHibernateSession();
		List<Clerk> result = new ArrayList<Clerk>();
		try{
		//	String sql = "select * from getclerklist where internalorganizationnumber = :orgcode";
			//20140404 修改柜员列表回显  by qiejinkai
			String sql = " select clerkname,clerknum,c_internalorganizationnumber,n_error,wdflag,leixbs from clerktable where internalorganizationnumber=:orgcode order by leixbs desc";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("orgcode", orgcode);
			SystemConfig sc =new SystemConfig();
			//20140404 增加系统管理员判断
			String adminCode=sc.getAdminCode();
			String fenhadminCode=orgcode+sc.getSuperManager();
			List<Clerk> list = query.list();
			String errStr = "0";
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				//20140404 增加系统管理员判断
				if(element[4].equals("0")||element[4].equals("1")||element[4].equals("2")){
					if(adminCode.equals((String) element[1])||fenhadminCode.equals((String) element[1])){
						continue;
					}
				}
				Clerk clerk = new Clerk();
				clerk.setName((String) element[0]);
				clerk.setCode((String) element[1]);
				clerk.setCreator((String) element[2]);
				clerk.setErrortime(StringUtil.isInteger(element[3]));
				clerk.setLeixbs((String) element[5]);
				result.add(clerk);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return result;
	}

	/**
	 * @see com.unitop.sysmgr.dao.ClerkDao#exportClerk(java.lang.String)
	 */
	public List exportClerk(String orgcode) {
		Session session = getBaseHibernateDao().getHibernateSession();
		List<Clerk> list1 = new ArrayList<Clerk>();
		try{
			String sql = "select clerknum,clerkname,clerkpwd,postnum,n_updatedate,internalorganizationnumber,n_logdate,c_internalorganizationnumber,f_internalorganizationnumber,wdflag " +
				"from clerktable where internalorganizationnumber= :orgcode";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("orgcode", orgcode);
			List list = query.list();
			if(list==null) list=new ArrayList();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				Clerk clerk = new Clerk();
				clerk.setCode((String) element[0]);
				clerk.setName((String) element[1]);
				clerk.setPassword((String) element[2]);
				clerk.setPostCode((String) element[3]);
				clerk.setUpdateDate(String.valueOf(element[4]));
				clerk.setOrgcode((String) element[5]);
				clerk.setLogDate((String) element[6]);
				clerk.setCreator((String) element[7]);
				clerk.setShOrgCode((String) element[8]);
				clerk.setWdFlag((String) element[9]);
				list1.add(clerk);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list1;
	}

	/**
	 * @see com.unitop.sysmgr.dao.ClerkDao#importClerk(java.lang.String)
	 */
	public boolean importClerk(Clerk clerk) throws Exception {
		Session session = getBaseHibernateDao().getHibernateSession();
		boolean rs = false;
		try{
			String sql = "insert into clerktable(clerknum,clerkname,clerkpwd,postnum,internalorganizationnumber,c_internalorganizationnumber,f_internalorganizationnumber,wdflag) "+ 
						 "values(:clerknum,:clerkname,:clerkpwd,:postnum,:organnum,:creator,:shenghjgh,:wdflag)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("clerknum", clerk.getCode());
			query.setString("clerkname", clerk.getName());
			query.setString("clerkpwd", clerk.getPassword());
			query.setString("postnum", "");
			query.setString("organnum", clerk.getOrgcode());
			query.setString("creator", clerk.getCreator());
			query.setString("shenghjgh", clerk.getShOrgCode());
			query.setString("wdflag", clerk.getWdFlag());
			query.executeUpdate();
			rs=true;
			//维护柜员角色关系数据
			String juesStirng = clerk.getPostCode();
			String[] juesStirngs = juesStirng.split(",");
			String[] _juesStirngs = new String[juesStirngs.length];
			for(int i =0 ;i<juesStirngs.length;i++)
			{
				_juesStirngs[i]= StringUtil.substringBetween(juesStirngs[i], "[", ":");
			}
			this.updateClerkRoles(clerk.getCode(),_juesStirngs,"");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return rs;
	}

	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.dao.ClerkDao#getAllClerkByOrgcode(java.lang.String)
	 */
	public List<Clerk> getAllClerkByOrgcode(String orgcode) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Clerk> list1 = new ArrayList<Clerk>();
		String sql = "";
		String db_type = DBinfoConig.getDBType();
		if("oracle".equals(db_type))
		{
			sql="select clerknum,clerkname,clerkpwd,postnum,n_updatedate,internalorganizationnumber,n_logdate,c_internalorganizationnumber,f_internalorganizationnumber,wdflag from clerktable " +
				" where internalorganizationnumber in (select internalorganizationnumber from organarchives connect by " +
				" prior internalorganizationnumber=p_internalorganizationnumber start with internalorganizationnumber= :organnum)";
		}else{
			sql="select clerknum,clerkname,clerkpwd,postnum,n_updatedate,internalorganizationnumber,n_logdate,c_internalorganizationnumber,f_internalorganizationnumber,wdflag from clerktable " +
		" where internalorganizationnumber in (select * from TABLE(ORGFUNCTION(:organnum)))";
		}
		try{
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("organnum", orgcode);
			List list = query.list();
			if(list==null)  list=new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) 
		{
			 Clerk clerk=new Clerk();
			 Object[] element = (Object[])iter.next();
			 clerk.setCode((String)element[0]);
			 clerk.setName((String)element[1]);
			 clerk.setPassword((String)element[2]);
			 clerk.setPostCode((String)element[3]);
			 clerk.setUpdateDate(String.valueOf(element[4]));
			 clerk.setOrgcode((String)element[5]);
			 clerk.setLoginDate((String)element[6]);
			 clerk.setCreator((String)element[7]);
			 clerk.setShOrgCode((String)element[8]);
			 clerk.setWdFlag((String)element[9]);
			 list1.add(clerk);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list1;	
	}
	
	public Clerk getClerkCountByIp(String ip) {
		List list = null;
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("select c.code,c.name from Clerk c where c.ip=?");
			query.setString(0, ip);
			list = query.list();
			if(list!=null&&list.size()!=0)
			{
				Object[] o  =  (Object[]) list.get(list.size()-1);
				Clerk clerk = new Clerk();
				clerk.setCode((String) o[0]);
				clerk.setName((String) o[1]);
				return clerk;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return null;
		
	}
	public void setErrorNum(String clerkNum,String errorNum) throws Exception{
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("update Clerk c set c.errortime =:errortime where code = :code");
			query.setInteger("errortime",Integer.parseInt(errorNum));
			query.setString("code", clerkNum);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	public void updateClerkRoles(String guiyid,String[] jues,String beiz) throws Exception{
		Session session= super.getBaseHibernateDao().getHibernateSession();
		//Connection con =session.connection();
		//PreparedStatement pst = null;
		Query query=null;
		try {
			String deletesql = "delete from guiyjsgxb where guiyid =:guiyid ";
			query=session.createSQLQuery(deletesql);
			query.setString("guiyid", guiyid);
			query.executeUpdate();
			session.flush();
			String insertsql = "insert into guiyjsgxb values(:guiyid,:juesid,:beiz)";	
			query=session.createSQLQuery(insertsql);
			for(int i=0;i<jues.length;i++)
			{
				query.setString("guiyid", guiyid);
				query.setString("juesid", jues[i]);
				query.setString("beiz", beiz);
				query.executeUpdate();
			}
			session.flush();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		
	}
	
	//删除柜员角色关系表对应角色数据
	public void deleteGuiyjsgxb(String guiyid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			String queryString = "delete Guiyjsgxb where id.guiyid=:guiyid";//注意：id.guiyid=:guiyid的等号后面一定不能是“id.guiyid”
		    Query query = session.createQuery(queryString);
			query.setString("guiyid", guiyid);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//查询柜员角色关系表对应角色数据
	public List selectGuiyjsgxb(String juesid) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from  Guiyjsgxb where id.juesid=:juesid");
			query.setString("juesid", juesid);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	//保存柜员角色关系表对应角色数据
	public void saveClerkRoles(String guiyid, String[] jues) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			for(int i=0;i<jues.length;i++)
			{
				Guiyjsgxb guiyjsgxb = new Guiyjsgxb();
				guiyjsgxb.setId(new GuiyjsgxbId());
				guiyjsgxb.getId().setGuiyid(guiyid);
				guiyjsgxb.getId().setJuesid(jues[i]);
				session.save(guiyjsgxb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}

	public long getClerkCounts() {
		long clerk_counts = 0;
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String hql="select count(*) as count from Clerk"; 
			Query query=session.createQuery(hql); 
			clerk_counts=((Number)query.iterate().next()).intValue(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return clerk_counts;
	}
	
	//修改柜员密码
	public void changePassword(Clerk clerk, String password) throws BusinessException {
		SystemConfig systemConfig = SystemConfig.getInstance();
		List list = super .getBaseHibernateDao() .getDaoHibernateTemplate() .find( "from ClerkPasswordHistory c where c.clerkCode = ? order by c.updateDate",new Object[] { clerk.getCode() });
		for (int i = 0; i < list.size(); i++)
		{
			ClerkPasswordHistory element = (ClerkPasswordHistory) list.get(i);
			if (password.equals(PasswordUtil.decodePwd(element.getPassword())))
			{
				throw new BusinessException("密码最近" + systemConfig.getValue("updatepasswordtimes") + "次已被使用");
			}
		}
		try {
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			String date1 = format1.format(rightNow);
			String date2 = format2.format(rightNow);
			if (list.size() < Integer.valueOf(systemConfig.getValue("updatepasswordtimes"))) 
			{
				ClerkPasswordHistory history = new ClerkPasswordHistory();
				history.setClerkCode(clerk.getCode());
				history.setPassword(PasswordUtil.encodePwd(clerk.getPassword()));
				history.setUpdateDate(date1);
				super.getBaseHibernateDao().getDaoHibernateTemplate().save(history);
			} else {
				ClerkPasswordHistory history = (ClerkPasswordHistory) list.get(0);
				history.setClerkCode(clerk.getCode());
				history.setPassword(PasswordUtil.encodePwd(clerk.getPassword()));
				history.setUpdateDate(date1);
				super.getBaseHibernateDao().getDaoHibernateTemplate().update(history);
			}
			clerk.setUpdateDate(date2);
			clerk.setPassword(PasswordUtil.encodePwd(password));
			clerk.setIp("");
			super.getBaseHibernateDao().getDaoHibernateTemplate().saveOrUpdate(clerk);
			clerk.setPassword(password);
		} catch (Exception e) {
			throw new BusinessException("密码修改失败！");
		}finally{
			super.getBaseHibernateDao().shifSession();
		}
//		throw new BusinessException("密码修改成功！");
	}
	//查询柜员对应角色信息
	public List getRoleByClerk(String clerknum) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Role where juesid in (select id.juesid from Guiyjsgxb where id.guiyid=:guiyid)");
			query.setString("guiyid", clerknum);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	/**
	 * 根据柜员查询不属于该柜员其他角色
	 */
	public List getElseRoleByClerk(String clerknum,String juesjb) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try {
			Query query = session.createQuery("from Role where juesjb<=:juesjb and juesid not in (select id.juesid from Guiyjsgxb where id.guiyid=:guiyid)");
			query.setString("guiyid", clerknum);
			query.setString("juesjb", juesjb);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	public void saveAuthorizedLog(Shouqrzb bo) {
		Session session =super.getBaseHibernateDao().getHibernateSession();
		try{
			session.beginTransaction().begin();
			session.saveOrUpdate(bo);
			session.beginTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//-----------------------------------------------同步-------------------------------------------------------------------------//
	public void deleteUndefindClerk_TB() throws DAOException {
		String sql ="delete clerktable where status='1' and leixbs='1'";
		PreparedStatement ps=null;
		Connection conn=getConn();
		try {
			ps=conn.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			throw new DAOException("删除柜员失败:"+e.getMessage(),e);
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
	public Clerk getClerkByCode_TB(String clerkCode) throws DAOException {
		Clerk clerk =null;
		Connection conn=getConn();
		PreparedStatement ps =null;
		ResultSet rs =null;
		String sql ="select clerknum,clerkname,wdflag,f_internalorganizationnumber,internalorganizationnumber,leixbs,status from clerktable where clerknum=?";
		try{
		ps =conn.prepareStatement(sql);
		ps.setString(1, clerkCode);
		rs= ps.executeQuery();
		while(rs.next()){
			clerk =createClerk(rs);
			return clerk;
		}
		}catch (Exception e) {
			throw new DAOException("查询柜员失败:"+e.getMessage(),e);
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
	
	private Clerk createClerk(ResultSet rs) throws Exception{
		if(rs==null)
			return null;
		Clerk clerk =new Clerk();
		clerk.setCode(rs.getString("clerknum"));
		clerk.setName(rs.getString("clerkname"));
		clerk.setOrgcode(rs.getString("internalorganizationnumber"));
		clerk.setLeixbs(rs.getString("leixbs"));
		clerk.setWdFlag(rs.getString("wdflag"));
		clerk.setShOrgCode(rs.getString("f_internalorganizationnumber"));
		clerk.setStatus(rs.getString("status"));
		return clerk;
	}
	
	public void saveClerk_TB(Clerk clerk) throws DAOException {
		String sql ="insert into clerktable(clerknum,clerkpwd,clerkname,f_internalorganizationnumber,internalorganizationnumber,N_UPDATEDATE,WDFLAG,leixbs,status) values(?,?,?,?,?,?,?,'1','0') ";
		PreparedStatement ps =null;
		Connection conn=getConn();
		try {
				ps =conn.prepareStatement(sql);
				ps.setString(1, clerk.getCode());
				ps.setString(3,clerk.getName());
				ps.setString(2, PasswordUtil.encodePwd("AAAAAAAA"));
				ps.setString(4, clerk.getShOrgCode());
				ps.setString(5, clerk.getOrgcode());
				ps.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				ps.setString(7, clerk.getWdFlag());
				ps.execute();
		} catch (Exception e) {
			throw new DAOException("新增柜员失败:"+e.getMessage(),e);
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
	public void updateAllClerkForEnd_TB() throws DAOException {
		String sql ="update clerktable set status='1' where leixbs='1' ";
		PreparedStatement ps=null;
		Connection conn=getConn();
		try {
			ps=conn.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			throw new DAOException("修改柜员类型标示失败:"+e.getMessage(),e);
		}
		finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	public void updateClerk_TB(Clerk clerk) throws DAOException {
		if(clerk==null){
			throw new DAOException("柜员为空");
		}
		Connection conn=getConn();
		String sql ="update  clerktable set CLERKNAME=? ,leixbs='1',status='0' where CLERKNUM=? ";
		PreparedStatement ps =null;
		try {
				ps =conn.prepareStatement(sql);
				ps.setString(1, clerk.getName());
				ps.setString(2, clerk.getCode());
				ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("修改柜员"+clerk.getCode()+"失败:"+e.getMessage(), e);
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
				throw new Exception("数据库连接为空");
			}
			conn.commit();
		} catch (Exception e) {
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
		} catch (SQLException e) {
			throw new DAOException("开启事物失败:"+e.getMessage(), e);
		}
	}
//--------------------------------------------------------end------------------------------------------------------------//
	
	
	
public String getMaxClerkCodeByHead(String head) {
	String sql ="select max(clerknum) from clerktable c where c.clerknum like '"+head+"____'  order by clerknum";
	Session session = super.getBaseHibernateDao().getHibernateSession();
	try {
	SQLQuery query=session.createSQLQuery(sql);
	Object o=query.uniqueResult();
		if(o==null){
			return null;
		}else{
			return String.valueOf(o);
		}
	} catch (Exception e) {
	}finally{
		super.getBaseHibernateDao().closeSession(session);
	}
	return null;
	}
	
	
}
