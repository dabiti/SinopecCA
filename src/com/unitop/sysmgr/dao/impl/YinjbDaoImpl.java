package com.unitop.sysmgr.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.dao.YinjbDao;
import com.unitop.util.Base64;
@Repository("YinjbDaoImpl")
public class YinjbDaoImpl extends BaseDataResources implements YinjbDao {

	private static Logger log = Logger.getLogger(YinjbDaoImpl.class);
	public void delYinj(String zhangh){
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String sql = "delete yinjb where zhangh=:zhangh";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("zhangh", zhangh);
			query.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public List getYinj(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			Query query = session.createQuery("from Yinjb where zhangh=:zhangh");
			query.setString("zhangh", zhangh);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	
	
	//获得印鉴MapList
	public List getYinjforMap(String zhangh) throws BusinessException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Map> list = null;
		try{
			Query query = session.createSQLQuery("select * from Yinjb where zhangh=:zhangh").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("zhangh", zhangh);
			list = query.list();
			for(Map yinj:list){
				Blob blob = (Blob) yinj.get("YINJTP");
				byte[] stream = blob.getBytes(1, (int) blob.length());
				yinj.put("YINJTP", Base64.encodeBytes(stream));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
		return list;
	}
	
	
	

	//获得印鉴MapList（总分同步）
	public List getYinjMapforTongb(String zhangh) throws BusinessException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Map> list = null;
		try{
			Query query = session.createSQLQuery("select * from Yinjb  where zhangh=:zhangh order by yinjbh asc,qiyrq desc").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("zhangh", zhangh);
			list = query.list();
			String lastYinjbh = "";
			int changeNum = 0;
			int count = 1;
			for(Map yinj:list){
				Blob blob = (Blob) yinj.get("YINJTP");
				byte[] stream = blob.getBytes(1, (int) blob.length());
				yinj.put("YINJTP", Base64.encodeBytes(stream));
				if( "未审".equals(yinj.get("YINJSHZT"))){
					yinj.put("SEALINKCHANGENUM", "-1");
				}else{
					if(count!=1){
						if(!lastYinjbh.equals(yinj.get("YINJBH"))){
							changeNum = 0;
						}
					}
					yinj.put("SEALINKCHANGENUM", String.valueOf(changeNum));
					changeNum += 1;
					lastYinjbh = (String) yinj.get("YINJBH");
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
		return list;
	}
	
	
	
	
	//获得最新已审印鉴
	public List getLastYSyj(String zhangh) throws BusinessException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Map> list = null;
		try{
			Query query = session.createSQLQuery("select * from Yinjb where zhangh=:zhangh and yinjshzt=:yinjshzt").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("zhangh", zhangh);
			query.setString("yinjshzt","已审");
			list = query.list();
			for(Map yinj:list){
				Blob blob = (Blob) yinj.get("YINJTP");
				byte[] stream = blob.getBytes(1, (int) blob.length());
				yinj.put("YINJTP", Base64.encodeBytes(stream));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(); 
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
		return list;
	}
	
	
	//获得最新已审组合
	public List getLastYSzh(String zhangh) throws BusinessException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Map> list = null;
		try{
			Query query = session.createSQLQuery("select * from yinjzhb where zhangh=:zhangh and zuhshzt=:zuhshzt").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("zhangh", zhangh);
			query.setString("zuhshzt","已审");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(); 
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
		return list;
	}
	
	
	
	public Yinjb getYinj(String zhangh, String yinjbh, String qiyrq) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Yinjb yinjb = null;
		try{
			Query query = session.createQuery("from Yinjb where zhangh=:zhangh and yinjbh=:yinjbh and qiyrq=:qiyrq");
			query.setString("zhangh", zhangh);
			query.setString("yinjbh", yinjbh);
			query.setString("qiyrq", qiyrq);
			yinjb = (Yinjb)query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return yinjb;
	}
	
	//-------------------总分同步----------------------------------------------------------------------------------//
	public List<Map<String, String>> queryYjbListByZhangh(String zhangh) throws DAOException {
		//查询印鉴表
		//20140918修改 同步印鉴不限制审核状态状态
		//String sql = "select * from YINJB t where t.zhangh=? and t.yinjshzt=? order by t.qiyrq desc,t.yinjbh asc";
		String sql = "select * from YINJB t where t.zhangh=? order by t.yinjbh asc,t.qiyrq desc";
		
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		Connection operation=this.getBaseJDBCDao().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			 stmt = operation.prepareStatement(sql);
			 stmt.setString(1, zhangh);
			 //stmt.setString(2,"已审");
			 rs=stmt.executeQuery();
		//	String lastQyrq = "";
			String lastYinjbh = "";
			int changeNum = 0;
			int count = 1;
			
			while(rs.next()){
				Map<String, String> singleMap = new HashMap<String, String>();
				singleMap.put("ZHANGH", rs.getString("ZHANGH")==null?"":rs.getString("ZHANGH"));
				singleMap.put("YINJBH", rs.getString("YINJBH")==null?"":rs.getString("YINJBH"));
				singleMap.put("QIYRQ", rs.getString("QIYRQ")==null?"":rs.getString("QIYRQ"));
				
				if( "未审".equals((String)rs.getString("YINJSHZT"))){
					singleMap.put("SEALINKCHANGENUM", "-1");
				}else{
					if(count!=1){
						if(!lastYinjbh.equals((String)rs.getString("YINJBH"))){
//						if(!lastQyrq.equals((String)rs.getString("QIYRQ"))){
					//		changeNum += -1;
							changeNum = 0;
						}
					}
					singleMap.put("SEALINKCHANGENUM", String.valueOf(changeNum));
					changeNum += 1;
//					lastQyrq = rs.getString("QIYRQ");
					lastYinjbh = rs.getString("YINJBH");
					count++;
				}
				//singleMap.put("SEALINKCHANGENUM", String.valueOf(changeNum));
				
				
				singleMap.put("SHANCRQ", rs.getString("SHANCRQ")==null?"":rs.getString("SHANCRQ"));
				singleMap.put("YINJLX", rs.getString("YINJLX")==null?"":rs.getString("YINJLX"));
				singleMap.put("YINJZL", rs.getString("YINJZL")==null?"":rs.getString("YINJZL"));
				singleMap.put("YINJYS", rs.getString("YINJYS")==null?"":rs.getString("YINJYS"));
				singleMap.put("YINJZT", rs.getString("YINJZT")==null?"":rs.getString("YINJZT"));
				singleMap.put("YINJSHZT", rs.getString("YINJSHZT")==null?"":rs.getString("YINJSHZT"));
				singleMap.put("YINJKBH", rs.getString("YINJKBH")==null?"":rs.getString("YINJKBH"));
				singleMap.put("YANYJB", rs.getString("YANYJB")==null?"":rs.getString("YANYJB"));
				singleMap.put("YANYJG", rs.getString("YANYJG")==null?"":rs.getString("YANYJG"));
				singleMap.put("TINGYRQ", rs.getString("TINGYRQ")==null?"":rs.getString("TINGYRQ"));
					
				Blob blob = rs.getBlob("YINJTP");
				byte[] stream = blob.getBytes(1, (int) blob.length());
				String yinjtpStr = Base64.encodeBytes(stream);
				singleMap.put("YINJTP", yinjtpStr==null?"":yinjtpStr);
					
				mapList.add(singleMap);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.info("查询账号为"+zhangh+"的所有印鉴信息时发生异常："+e.getMessage());
				throw new DAOException("查询账号为"+zhangh+"的所有印鉴信息时发生异常",e);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("查询账号为"+zhangh+"的所有印鉴信息时发生异常："+e.getMessage());
				throw new DAOException("查询账号为"+zhangh+"的所有印鉴信息时发生异常",e);
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
			return mapList;
	}

}
