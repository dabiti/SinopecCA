package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import com.unitop.config.DBinfoConig;
import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhanghxzb;
import com.unitop.sysmgr.dao.ZhanghbDao;

@Repository("ZhanghbDaoImpl")
public class ZhanghbDaoImpl extends BaseDataResources implements ZhanghbDao {
	private static Logger  log = Logger.getLogger(ZhanghbDaoImpl.class);
	// �����ʺ���Ϣ
	public Zhanghb getZhanghb(String zhangh) {
		Zhanghb zhanghb = null;
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Zhanghb  where zhangh=:zhangh");
			query.setString("zhangh", zhangh);
			zhanghb = (Zhanghb) query.uniqueResult();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zhanghb;
	}
	
	
	// ��ѯ�ʺ�������Ϣ
	public ArrayList<Zhanghxzb> getZhanghxzList() {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		ArrayList<Zhanghxzb> zhanghxzList = new ArrayList<Zhanghxzb>();
		try{
			Query query = session.createQuery("from Zhanghxzb");
			zhanghxzList = (ArrayList<Zhanghxzb>) query.list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zhanghxzList;
	}
	
	

	// �����ʺ���Ϣ
	public void updateZhanghb(Zhanghb zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(zhangh);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	// ɾ���ʺ�
	public void deleteZhanghb(Zhanghb zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.delete(zhangh);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public List getKehh(String keh, String jigh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Zhanghb> list1 = new ArrayList<Zhanghb>();
		String sql = "";
		String db_type = DBinfoConig.getDBType();
		if ("oracle".equals(db_type)) {
			sql = "select * from zhanghb where customernumber= :keh and internalorganizationnumber in (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber = :jigh)";
		} else {
			sql = "select * from zhanghb where customernumber= :keh and internalorganizationnumber in (select * from TABLE(ORGFUNCTION(:jigh)))";
		}
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("keh", keh);
			query.setString("jigh", jigh);
			List list = query.list();
			if (list == null)
				list = new ArrayList();
			Zhanghb zhanghb = new Zhanghb();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				zhanghb.setZhangh((String) element[0]);
				zhanghb.setKehh((String) element[1]);
				zhanghb.setJigh((String) element[2]);
				list1.add(zhanghb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list1;
	}

	// �����ʺ�ͨ��ͨ�� ��ϵ
	public void updateForAccount(String orgCode, String tctd) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			String sql = "update zhanghb set tongctd=:tctd where internalorganizationnumber in (select internalorganizationnumber from organarchives where f_internalorganizationnumber=:shenghjgh)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("tctd", tctd);
			query.setString("shenghjgh", orgCode);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	//ƴ��ӡ��������ַ���
	private static String createYinjkbhStr(List<String> yinjkbhList){
		if(yinjkbhList==null){
			return "";
		}
		if(yinjkbhList.size()==0){
			return "";
		}
		Collections.sort(yinjkbhList);
		StringBuffer msg=new StringBuffer();
		for (String string : yinjkbhList) {
			string=string.trim();
			if(string!=null&&!string.equals("")){
				msg.append("|").append(string);
			}
		}
		if(msg.length()!=0){
			return msg.substring(1);
		}
		return "";
	}
	public Zhanghb getZhanghbByYinjkbh(String yinjkbh) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		Zhanghb zhanghb=null;
		try {
			conn=super.getBaseJDBCDao().getConnection();
			
				String sql =" select * from zhanghb where zhuzh is null  and yinjkh like ? ";
				ps= conn.prepareStatement(sql);
				ps.setString(1, yinjkbh);
				rs=ps.executeQuery();
			while(rs.next()){
				zhanghb=new Zhanghb();
				zhanghb.setZhangh(rs.getString("zhangh"));
				zhanghb.setHum(rs.getString("legalname"));
				zhanghb.setJigh(rs.getString("internalorganizationnumber"));
				zhanghb.setLianxr(rs.getString("lianxr"));
				zhanghb.setDianh(rs.getString("phonenumber"));
				zhanghb.setFuzr(rs.getString("fuzr"));
				zhanghb.setFuzrdh(rs.getString("phonenumber3"));
				zhanghb.setKaihrq(rs.getString("kaihrq"));
				zhanghb.setTongctd(rs.getString("tongctd"));
				zhanghb.setYouwyj(rs.getString("youwyj"));
				zhanghb.setYouwzh(rs.getString("youwzh"));
				zhanghb.setZhanghshzt(rs.getString("zhanghshzt"));
				zhanghb.setYinjshzt(rs.getString("yinjshzt"));
				zhanghb.setZuhshzt(rs.getString("zuhshzt"));
				zhanghb.setZhanghzt(rs.getString("zhanghzt"));
				zhanghb.setZhanghxz(rs.getString("zhanghxz"));
				zhanghb.setBeiz(rs.getString("beiz"));
				zhanghb.setYinjkbh(rs.getString("yinjkh"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			
			super.getBaseJDBCDao().closeConnection(conn);
		}

		return zhanghb;
	}
	
	public String getInternalReleaseZhangh(String rule) {
		Session session=getBaseHibernateDao().getHibernateSession();
		String zhangh="";
		try {
			String hql ="select max(zhangh) from Zhanghb where zhangh like :rule and zhangh >='70000000000000000' and zhangh<='79999999999999999'";
			Query query=session.createQuery(hql);
			query.setString("rule", rule);
			zhangh = (String)query.uniqueResult();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return zhangh;
	}

	
	
	
	//����˻�����ϢMap
	public List<Map<String,String>> getZhanghbforMap(String zhangh) throws BusinessException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Map<String,String>> list = null;
		try{
			Query query = session.createSQLQuery("select * from Zhanghb where zhangh=:zhangh").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("zhangh", zhangh);
			list = query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		if(list==null||list.size()==0){
			throw new BusinessException("׼��ͬ��ʱ��ȡ���˻����˻����е���Ϣʧ��");
		}
		
		return list;
	}
	
	//ͨ��ͨ�ұ�־ת��BS��CS
	public Map<String,String> attrConvert(Map<String, String> zhanghbMap){
		if(zhanghbMap==null||zhanghbMap.size()==0){
			return zhanghbMap;
		} 
		if(zhanghbMap.containsKey("TONGCTD")){
					if("ȫ��".equals(zhanghbMap.get("TONGCTD"))||"��".equals(zhanghbMap.get("TONGCTD"))||"һ������ȫϽ".equals(zhanghbMap.get("TONGCTD"))||"һ�����б���".equals(zhanghbMap.get("TONGCTD"))||"��������".equals(zhanghbMap.get("TONGCTD"))){
						zhanghbMap.put("TONGCTD", "ͨ");
					}else if("��ͨ��".equals(zhanghbMap.get("TONGCTD"))){
						zhanghbMap.put("TONGCTD", "��");
					}else{
						zhanghbMap.put("TONGCTD", "");
					}
		}
		if(zhanghbMap.containsKey("ZHANGHZT")){
			if("����".equals(zhanghbMap.get("ZHANGHZT"))||"��������".equals(zhanghbMap.get("ZHANGHZT"))){
				zhanghbMap.put("ZHANGHZT", "����");
			}
		}
		return zhanghbMap;
	}
	
	
	
	//�����˺Ż����Ż�ȡ��ʡ�л�����
	public String getShenghjgh(String jigh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String shenghjgh = "";
		try{
			Query query = session.createSQLQuery("select * from Organarchives where internalorganizationnumber=:organnum").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("organnum", jigh);
			List<Map> list = query.list();
			shenghjgh = (String) list.get(0).get("F_INTERNALORGANIZATIONNUMBER");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return shenghjgh;
	}
	
public List<Zhanghb> getZhanghbByZzh(String zhangh) {
	Session session = super.getBaseHibernateDao().getHibernateSession();
	List<Zhanghb>zhanghbList=new ArrayList<Zhanghb>();
	try{
		Query query = session.createQuery("from Zhanghb where zhuzh=:zhuzh and zhanghzt <> '����' order by fuyrq");
		query.setString("zhuzh", zhangh);
		zhanghbList=(List<Zhanghb>)query.list();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		this.getBaseHibernateDao().closeSession(session);
	}
	return zhanghbList;
}
public String getYinjkh(String account) {
	if(account ==null||account.trim().equals("")){
		return null;
	}
	Zhanghb zhanghb=getZhanghb(account);
	if(zhanghb==null){
		return null;
	}
	String yinjkh= zhanghb.getYinjkbh();
	if(yinjkh!=null&&!yinjkh.trim().equals("")){
		return yinjkh;
	}else{
		String zhuzh=zhanghb.getZhuzh();
		if(zhuzh==null||zhuzh.trim().equals("")){
			return null;
		}
		Zhanghb zhuzhb=getZhanghb(zhuzh);
		if(zhuzhb==null){
			return null;
		}
		yinjkh =zhuzhb.getYinjkbh();
		return yinjkh;
	}
}
	public int countTodoZhanghbList(String jigh) {
		if(jigh==null||jigh.trim().length()==0){
			return 0;
		}
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(" select count (*) from Zhanghb where internalorganizationnumber=:jigh  and (youwyj ='��' or yinjshzt='δ��') and zhanghzt <>'����'");
			query.setString("jigh", jigh);
			Object result=query.uniqueResult();
			return Integer.valueOf(result.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	public List<Zhanghb> getToDoZhanghbList(String jigh) {
		List<Zhanghb> zhanghbList=new ArrayList<Zhanghb>();
		if(jigh==null||jigh.trim().length()==0){
			return zhanghbList;
		}
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(" from Zhanghb where jigh=:jigh  and (youwyj =:youwyj or yinjshzt=:yinjshzt) and zhanghzt <>:xiaohu order by kaihrq");
			query.setString("jigh", jigh);
			query.setString("youwyj", "��");
			query.setString("yinjshzt", "δ��");
			query.setString("xiaohu", "����");
			zhanghbList=(List<Zhanghb>)query.list();
			for (Zhanghb zhanghb : zhanghbList) {
				if(zhanghb!=null){
					zhanghb.setJiankgy(getJiankgy(zhanghb.getZhangh()));
					zhanghb.setKaihgy(getKaihgy(zhanghb.getZhangh()));
				}
			}
			return zhanghbList;
			
		} catch (Exception e) {
			//e.printStackTrace();
			return zhanghbList;
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public String getJiankgy(String account) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String sql = "select CLERKNUM from accountmanagelog where (managetype=:managetype or managetype=:caozlx) and zhangh =:zhangh order by managedate desc,managetime desc";
		String xiaohsj = "";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			// query.addEntity(AccountManageLog.class);
			query.setString("managetype", "6");
			query.setString("caozlx", "8");
			query.setString("zhangh", account);

			List list = query.list();
			if(list.size()==0){
				return "";
			}
			xiaohsj = (String) list.get(0);

			xiaohsj = xiaohsj == null ? "" : xiaohsj;
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}

		return xiaohsj;
	}
	public String getKaihgy(String account) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String sql = "select CLERKNUM from accountmanagelog where managetype=:managetype and zhangh =:zhangh order by managedate desc,managetime desc";
		String xiaohsj = "";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			// query.addEntity(AccountManageLog.class);
			query.setString("managetype", "0");
			query.setString("zhangh", account);

			List list = query.list();
			if(list.size()==0){
				return "";
			}
			xiaohsj = (String) list.get(0);

			xiaohsj = xiaohsj == null ? "" : xiaohsj;
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}

		return xiaohsj;
	}
	
	
	
	//--------------------------------------�ܷ�ͬ��------------------------------------------------------//
	/**
	 * ���ݷ��кŲ�ѯ��ͬ����ַ
	 * @param Jigh
	 * @return
	 * @throws DAOException 
	 * @throws DAOException 
	 */
	public String getSocketaddByJigh(String Jigh) throws DAOException {
		//��ѯ�˻���
		String sql = "select t.ip,t.port from ZHIPYXXX t where internalorganizationnumber=? ";
		
		String socketadd = null;
		String ip = null;
		String port = null;
		Connection operation=this.getBaseJDBCDao().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = operation.prepareStatement(sql);
			stmt.setString(1, Jigh);
			rs = stmt.executeQuery();
			if(rs.next()){
				ip =  rs.getString("ip");
				port =  rs.getString("port");
				socketadd = ip+":"+port;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("��ѯ������Ϊ"+Jigh+"��ͬ����ַʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯ������Ϊ"+Jigh+"��ͬ����ַʱ�����쳣",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("��ѯ������Ϊ"+Jigh+"��ͬ����ַʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯ������Ϊ"+Jigh+"��ͬ����ַʱ�����쳣",e);
		}finally{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(operation!=null){
					this.getBaseJDBCDao().closeConnection(operation);
			}
		}
		
		return socketadd;
	}
	/**
	 * �����˺Ų�ѯ�˻�����Ϣ
	 * @return
	 * @throws DAOException 
	 * @throws Exception
	 */
	public Map<String, String> queryZhbByZhangh(String zhangh) throws DAOException {
		//��ѯ�˻���
		String sql = "select t.*, t.rowid from ZHANGHB t where zhangh=? ";
		
		Map<String, String> singleMap = new HashMap<String, String>();
		Connection operation=this.getBaseJDBCDao().getConnection();
		PreparedStatement stmt =null;
		ResultSet rs=null;
		try {
			 stmt = operation.prepareStatement(sql);
			 stmt.setString(1, zhangh);
			 rs = stmt.executeQuery();
			while(rs.next()){
				singleMap.put("ZHANGH", rs.getString("ZHANGH")==null?"":rs.getString("ZHANGH"));
				//��ѯ���˺�
				if(null!=rs.getString("ZHANGH")){
					String oldZhangh = getOldAccount(rs.getString("ZHANGH")) ;
					if(null!=oldZhangh){
						singleMap.put("OLDZHANGH", oldZhangh);
					}
				}
				singleMap.put("KEHH", rs.getString("CUSTOMERNUMBER")==null?"":rs.getString("CUSTOMERNUMBER"));
				singleMap.put("HUM", rs.getString("LEGALNAME")==null?"":rs.getString("LEGALNAME"));
				singleMap.put("JIGH", rs.getString("INTERNALORGANIZATIONNUMBER")==null?"":rs.getString("INTERNALORGANIZATIONNUMBER"));
				singleMap.put("DIZ", rs.getString("PHYSICALADDRESS")==null?"":rs.getString("PHYSICALADDRESS"));
				singleMap.put("YOUZBM", rs.getString("POSTALCODE")==null?"":rs.getString("POSTALCODE"));
				singleMap.put("LIANXR", rs.getString("LIANXR")==null?"":rs.getString("LIANXR"));
				singleMap.put("DIANH", rs.getString("PHONENUMBER")==null?"":rs.getString("PHONENUMBER"));
				singleMap.put("KAIHRQ", rs.getString("KAIHRQ")==null?"":rs.getString("KAIHRQ"));
				singleMap.put("TONGCTD", rs.getString("TONGCTD")==null?"":rs.getString("TONGCTD"));
				singleMap.put("HUOBH", rs.getString("CURRENCY")==null?"":rs.getString("CURRENCY"));
				singleMap.put("YOUWYJ", rs.getString("YOUWYJ")==null?"":rs.getString("YOUWYJ"));
				singleMap.put("YOUWZH", rs.getString("YOUWZH")==null?"":rs.getString("YOUWZH"));
				singleMap.put("YINJSHZT", rs.getString("YINJSHZT")==null?"":rs.getString("YINJSHZT"));
				singleMap.put("ZHANGHSHZT", rs.getString("ZHANGHSHZT")==null?"":rs.getString("ZHANGHSHZT"));
				singleMap.put("ZUHSHZT", rs.getString("ZUHSHZT")==null?"":rs.getString("ZUHSHZT"));
				String zhanghzt = rs.getString("ZHANGHZT")==null?"":rs.getString("ZHANGHZT");
				if("����".equals(zhanghzt)||"��������".equals(zhanghzt)){
					zhanghzt = "����";
				}
				singleMap.put("ZHANGHZT", zhanghzt);
				singleMap.put("ZHANGHXZ", rs.getString("ZHANGHXZ")==null?"":rs.getString("ZHANGHXZ"));
				singleMap.put("YANYJB", rs.getString("YANYJB")==null?"":rs.getString("YANYJB"));
				singleMap.put("YANYJG", rs.getString("YANYJG")==null?"":rs.getString("YANYJG"));
				singleMap.put("BEIZ", rs.getString("BEIZ")==null?"":rs.getString("BEIZ"));
				singleMap.put("ZHUZH", rs.getString("ZHUZH")==null?"":rs.getString("ZHUZH"));
				singleMap.put("FUYRQ", rs.getString("FUYRQ")==null?"":rs.getString("FUYRQ"));
				singleMap.put("QUXFYRQ", rs.getString("QUXFYRQ")==null?"":rs.getString("QUXFYRQ"));
				singleMap.put("JIANKBS", rs.getString("JIANKBS")==null?"":rs.getString("JIANKBS"));
				singleMap.put("TINGYRQ", rs.getString("TINGYRQ")==null?"":rs.getString("TINGYRQ"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.info("��ѯ�˺�Ϊ"+zhangh+"���˻�����ϸ��Ϣʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯ�˺�Ϊ"+zhangh+"���˻�����ϸ��Ϣʱ�����쳣",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("��ѯ�˺�Ϊ"+zhangh+"���˻�����ϸ��Ϣʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯ�˺�Ϊ"+zhangh+"���˻�����ϸ��Ϣʱ�����쳣",e);
		}finally{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(operation!=null)
				try {
					operation.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return singleMap;
	}
	/**
	 * ���ݻ����Ų�ѯ�������Ϣ
	 * @param Jigh
	 * @return
	 * @throws DAOException 
	 */
	public Map<String, String> queryOrganarchivesByJigh(String Jigh)throws DAOException{
		//��ѯ�˻���
		String sql = "select t.*, t.rowid from ORGANARCHIVES t where INTERNALORGANIZATIONNUMBER=? ";
		
		
		Map<String, String> singleMap = new HashMap<String, String>();
		Connection operation=this.getBaseJDBCDao().getConnection();
		PreparedStatement stmt =null;
		ResultSet rs = null;
		
		try {
			stmt = operation.prepareStatement(sql);
			stmt.setString(1, Jigh);
			rs = stmt.executeQuery();
			while(rs.next()){
				singleMap.put("ORGANNUM", rs.getString("INTERNALORGANIZATIONNUMBER")==null?"":rs.getString("INTERNALORGANIZATIONNUMBER"));
				singleMap.put("SHENGHJGH", rs.getString("F_INTERNALORGANIZATIONNUMBER")==null?"":rs.getString("F_INTERNALORGANIZATIONNUMBER"));
				singleMap.put("SOCKETADD", rs.getString("SOCKETADD")==null?"":rs.getString("SOCKETADD"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("��ѯ������Ϊ"+Jigh+"�Ļ�����Ϣʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯ������Ϊ"+Jigh+"�Ļ�����Ϣʱ�����쳣",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("��ѯ������Ϊ"+Jigh+"�Ļ�����Ϣʱ�����쳣��"+e.getMessage());
			throw new DAOException("��ѯ������Ϊ"+Jigh+"�Ļ�����Ϣʱ�����쳣",e);
		}finally{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(operation!=null){
					this.getBaseJDBCDao().closeConnection(operation);
			}
		}
		
		return singleMap;
	}
	//ͬ��ͨ�ұ�־ת��BS��CS
//	public Map<String, String> tongctdConvert(Map<String, String> zhanghb) {
//		if(zhanghb!=null&&zhanghb.containsKey("TONGCTD")){
//			if("ȫ��".equals(zhanghb.get("TONGCTD"))||"��".equals(zhanghb.get("TONGCTD"))||"һ������ȫϽ".equals(zhanghb.get("TONGCTD"))||"һ�����б���".equals(zhanghb.get("TONGCTD"))||"��������".equals(zhanghb.get("TONGCTD"))){
//				zhanghb.put("TONGCTD", "ͨ");
//			}else if("��ͨ��".equals(zhanghb.get("TONGCTD"))){
//				zhanghb.put("TONGCTD", "��");
//			}else{
//				zhanghb.put("TONGCTD", "");
//			}
//		}
//		return zhanghb;
//	}
	//--------------------------------------�ܷ�ͬ��------------------------------------------------------//
	
	
	public String getZhanghFromShort(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String sql = "select newaccount from zhanghzhb where shortaccount=:short";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("short", zhangh);
			Object obj=query.uniqueResult();
			if(obj!=null&&String.valueOf(obj).length()==17){
				zhangh=String.valueOf(obj);
			}
			
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}

		return zhangh;
	}
	public String getZhanghFromOldAccount(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String sql = "select newaccount from zhanghzhb where oldaccount=:old";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("old", zhangh);
			Object obj=query.uniqueResult();
			if(obj!=null&&String.valueOf(obj).length()==17){
				zhangh=String.valueOf(obj);
			}
			
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}
		return zhangh;
	}
	private final String insert ="insert into zhanghb_temp (ZHANGH, LEGALNAME, INTERNALORGANIZATIONNUMBER, LIANXR, PHONENUMBER, KAIHRQ, TONGCTD, YOUWYJ, YOUWZH, YINJSHZT, ZHANGHSHZT, ZUHSHZT, ZHANGHZT, ZHANGHXZ, BEIZ, ZHUZH, FUYRQ, FUZR, PHONENUMBER3, YINJKH, LIANXR2, PHONENUMBER2, FUZR2, PHONENUMBER4, SHIFDH, AREACODE, AREACODE2, AREACODE3, AREACODE4, EXTENSIONNUMBER, EXTENSIONNUMBER2, EXTENSIONNUMBER3, EXTENSIONNUMBER4) values ( :ZHANGH, :LEGALNAME, :INTERNALORGANIZATIONNUMBER, :LIANXR, :PHONENUMBER, :KAIHRQ, :TONGCTD, :YOUWYJ, :YOUWZH, :YINJSHZT, :ZHANGHSHZT, :ZUHSHZT, :ZHANGHZT, :ZHANGHXZ, :BEIZ, :ZHUZH, :FUYRQ, :FUZR, :PHONENUMBER3, :YINJKH, :LIANXR2, :PHONENUMBER2, :FUZR2, :PHONENUMBER4, :SHIFDH, :AREACODE, :AREACODE2, :AREACODE3, :AREACODE4, :EXTENSIONNUMBER, :EXTENSIONNUMBER2, :EXTENSIONNUMBER3, :EXTENSIONNUMBER4)";
	private final String select ="select count(*)  from zhanghb_temp where zhangh=:zhangh ";
	private final String update ="update zhanghb_temp set LEGALNAME=:LEGALNAME, INTERNALORGANIZATIONNUMBER=:INTERNALORGANIZATIONNUMBER, LIANXR=:LIANXR, PHONENUMBER=:PHONENUMBER, KAIHRQ=:KAIHRQ, TONGCTD=:TONGCTD, ZHANGHZT=:ZHANGHZT, ZHANGHXZ=:ZHANGHXZ, BEIZ=:BEIZ, ZHUZH=:ZHUZH, FUYRQ=:FUYRQ, FUZR=:FUZR, PHONENUMBER3=:PHONENUMBER3, YINJKH=:YINJKH, LIANXR2=:LIANXR2, PHONENUMBER2=:PHONENUMBER2, FUZR2=:FUZR2, PHONENUMBER4=:PHONENUMBER4, SHIFDH=:SHIFDH, AREACODE=:AREACODE, AREACODE2=:AREACODE2, AREACODE3=:AREACODE3, AREACODE4=:AREACODE4, EXTENSIONNUMBER=:EXTENSIONNUMBER, EXTENSIONNUMBER2=:EXTENSIONNUMBER2, EXTENSIONNUMBER3=:EXTENSIONNUMBER3, EXTENSIONNUMBER4=:EXTENSIONNUMBER4 WHERE ZHANGH=:ZHANGH";
	@Override
	public void saveOrUpdateForZhanghTemp(Zhanghb zhanghb) {
		
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Transaction ts =session.beginTransaction();
		ts.begin();
		
		try {
			SQLQuery query = session.createSQLQuery(select);
			query.setString("zhangh", zhanghb.getZhangh());
			Object obj=query.uniqueResult();
			int count=0;
			try{
			count=obj==null?0:Integer.parseInt(String.valueOf(obj));
			}catch (Exception e) {
				count=1;
			}
			
			if(count==0){
				SQLQuery insertquery=session.createSQLQuery(insert);
				insertquery.setString("ZHANGH", zhanghb.getZhangh());
				insertquery.setString("LEGALNAME", zhanghb.getHum());
				insertquery.setString("INTERNALORGANIZATIONNUMBER", zhanghb.getJigh());
				insertquery.setString("LIANXR", zhanghb.getLianxr());
				insertquery.setString("PHONENUMBER", zhanghb.getDianh());
				insertquery.setString("KAIHRQ", zhanghb.getKaihrq());
				insertquery.setString("TONGCTD", zhanghb.getTongctd());
				insertquery.setString("YOUWYJ", "��");
				insertquery.setString("YOUWZH", "��");
				insertquery.setString("YINJSHZT", "δ��");
				insertquery.setString("ZHANGHSHZT", "����");
				insertquery.setString("ZUHSHZT", "δ��");
				insertquery.setString("ZHANGHZT", zhanghb.getZhanghzt());
				insertquery.setString("ZHANGHXZ", zhanghb.getZhanghxz());
				insertquery.setString("BEIZ", zhanghb.getBeiz());
				insertquery.setString("ZHUZH", zhanghb.getZhuzh());
				insertquery.setString("FUYRQ", zhanghb.getFuyrq());
				insertquery.setString("FUZR", zhanghb.getFuzr());
				insertquery.setString("PHONENUMBER3", zhanghb.getFuzrdh());
				insertquery.setString("YINJKH", zhanghb.getYinjkbh());
				insertquery.setString("LIANXR2", zhanghb.getLianxr2());
				insertquery.setString("PHONENUMBER2", zhanghb.getDianh2());
				insertquery.setString("FUZR2", zhanghb.getFuzr2());
				insertquery.setString("PHONENUMBER4", zhanghb.getFuzrdh2());
				insertquery.setString("SHIFDH", zhanghb.getShifdh());
				insertquery.setString("AREACODE", zhanghb.getLianxrqh());
				insertquery.setString("AREACODE2", zhanghb.getLianxrqh2());
				insertquery.setString("AREACODE3", zhanghb.getFuzrqh());
				insertquery.setString("AREACODE4", zhanghb.getFuzrqh2());
				insertquery.setString("EXTENSIONNUMBER", zhanghb.getLianxrfjh());
				insertquery.setString("EXTENSIONNUMBER2", zhanghb.getLianxrfjh2());
				insertquery.setString("EXTENSIONNUMBER3", zhanghb.getFuzrfjh());
				insertquery.setString("EXTENSIONNUMBER4", zhanghb.getFuzrfjh2());
				insertquery.executeUpdate();
			}else{
				SQLQuery updateQuery=session.createSQLQuery(update);
				//LEGALNAME=?, INTERNALORGANIZATIONNUMBER=?, LIANXR=?, PHONENUMBER=?, KAIHRQ=?, TONGCTD=?, ZHANGHSHZT=?,  ZHANGHZT=?, ZHANGHXZ=?, BEIZ=?, ZHUZH=?, FUYRQ=?, FUZR=?, PHONENUMBER3=?, YINJKH=?, LIANXR2=?, PHONENUMBER2=?, FUZR2=?, PHONENUMBER4=?, SHIFDH=?, AREACODE=?, AREACODE2=?, AREACODE3=?, AREACODE4=?, EXTENSIONNUMBER=?, EXTENSIONNUMBER2=?, EXTENSIONNUMBER3=?, EXTENSIONNUMBER4=? WHERE ZHANGH=?

				updateQuery.setString("ZHANGH", zhanghb.getZhangh());
				updateQuery.setString("LEGALNAME", zhanghb.getHum());
				updateQuery.setString("INTERNALORGANIZATIONNUMBER", zhanghb.getJigh());
				updateQuery.setString("LIANXR", zhanghb.getLianxr());
				updateQuery.setString("PHONENUMBER", zhanghb.getDianh());
				updateQuery.setString("KAIHRQ", zhanghb.getKaihrq());
				updateQuery.setString("TONGCTD", zhanghb.getTongctd());
				updateQuery.setString("ZHANGHZT", zhanghb.getZhanghzt());
				updateQuery.setString("ZHANGHXZ", zhanghb.getZhanghxz());
				updateQuery.setString("BEIZ", zhanghb.getBeiz());
				updateQuery.setString("ZHUZH", zhanghb.getZhuzh());
				updateQuery.setString("FUYRQ", zhanghb.getFuyrq());
				updateQuery.setString("FUZR", zhanghb.getFuzr());
				updateQuery.setString("PHONENUMBER3", zhanghb.getFuzrdh());
				updateQuery.setString("YINJKH", zhanghb.getYinjkbh());
				updateQuery.setString("LIANXR2", zhanghb.getLianxr2());
				updateQuery.setString("PHONENUMBER2", zhanghb.getDianh2());
				updateQuery.setString("FUZR2", zhanghb.getFuzr2());
				updateQuery.setString("PHONENUMBER4", zhanghb.getFuzrdh2());
				updateQuery.setString("SHIFDH", zhanghb.getShifdh());
				updateQuery.setString("AREACODE", zhanghb.getLianxrqh());
				updateQuery.setString("AREACODE2", zhanghb.getLianxrqh2());
				updateQuery.setString("AREACODE3", zhanghb.getFuzrqh());
				updateQuery.setString("AREACODE4", zhanghb.getFuzrqh2());
				updateQuery.setString("EXTENSIONNUMBER", zhanghb.getLianxrfjh());
				updateQuery.setString("EXTENSIONNUMBER2", zhanghb.getLianxrfjh2());
				updateQuery.setString("EXTENSIONNUMBER3", zhanghb.getFuzrfjh());
				updateQuery.setString("EXTENSIONNUMBER4", zhanghb.getFuzrfjh2());
				updateQuery.executeUpdate();
			}
			ts.commit();
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//��ȡ���˺�
	public String getOldAccount(String zhangh) {
		String oldzhangh = null;
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String sql = "select oldaccount from zhanghzhb where newaccount=:new";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("new", zhangh);
			Object obj=query.uniqueResult();
			if(obj!=null){
				oldzhangh=String.valueOf(obj);
			}
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}
		return oldzhangh;
	}
	
}
