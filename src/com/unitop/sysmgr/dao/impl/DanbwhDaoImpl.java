package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sinopec.table.model.Dictionary;
import com.sinopec.table.model.SqlParameter;
import com.sinopec.table.model.Table;
import com.unitop.config.DBinfoConig;
import com.unitop.sysmgr.bo.Danbwh;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.dao.DanbwhDao;

@Repository("DanbwhDaoImpl")
public class DanbwhDaoImpl extends BaseDataResources implements DanbwhDao {

	public void add(Danbwh danbwh) throws SQLException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(danbwh);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public void delete(String gongnid) throws SQLException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			Danbwh danbwh = (Danbwh) session.get(Danbwh.class, gongnid);
			session.delete(danbwh);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public Danbwh find(String gongnid) throws SQLException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		Danbwh danbwh=null;
		try {
			danbwh = (Danbwh) session.get(Danbwh.class, gongnid);
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return danbwh;
	}
	
	public List getAll() throws SQLException {
		List list = null;
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Danbwh");
			list = query.list();
			if(list == null) list = new ArrayList();	
			return list;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public void update(Danbwh danbwh) throws SQLException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.saveOrUpdate(danbwh);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	

	//DML语句执行
	public void doDML(Table table) throws Exception {
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			pre = conn.prepareStatement(table.getSqlString());
			this.doPreparedStatement(pre, table);
			pre.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (pre != null) {
				pre.close();
			}
			if (conn != null) {
				super.getBaseJDBCDao().closeConnection(conn);
			}
		}
	}
	
	//DML语句执行-返回rows
	public int doDMLForUpdate(Table table) throws Exception {
		int UPDATE_COUNTS = 0;
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			pre = conn.prepareStatement(table.getSqlString());
			this.doPreparedStatement(pre, table);
			UPDATE_COUNTS = pre.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pre != null) {
				pre.close();
			}
			if (conn != null) {
				super.getBaseJDBCDao().closeConnection(conn);
			}
		}
		return UPDATE_COUNTS;
	}
	
	//查询语句执行 返回集合统一小写
	public List<Map> doSelect(Table table) throws Exception {
		List<Map> list = new ArrayList<Map>();
		PreparedStatement pre = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Connection conn = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			pre = conn.prepareStatement(table.getSqlString());
			this.doPreparedStatement(pre, table);
			rs = pre.executeQuery();
			rsmd = pre.getMetaData();
			while (rs.next()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++)
				{
					Object getColumnValueObject = rs.getObject(i + 1);
					getColumnValueObject=(null)==getColumnValueObject?"":getColumnValueObject;
					map.put(rsmd.getColumnName(i + 1).toLowerCase(),getColumnValueObject);
				//	System.out.println(rsmd.getColumnName(i+1).toLowerCase()+"="+getColumnValueObject);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("异常SQL:" + table.getSqlString());
			throw new Exception(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pre != null) {
					pre.close();
				}
				if (conn != null) {
					super.getBaseJDBCDao().closeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	//查询语句执行 返回集合统一大写
	public List<Map> doSelectForUpperCase(Table table) throws Exception {
		List<Map> list = new ArrayList<Map>();
		PreparedStatement pre = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Connection conn = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			pre = conn.prepareStatement(table.getSqlString());
			this.doPreparedStatement(pre, table);
			rs = pre.executeQuery();
			rsmd = pre.getMetaData();
			while (rs.next()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++)
				{
					Object getColumnValueObject = rs.getObject(i + 1);
					getColumnValueObject=(null)==getColumnValueObject?"":getColumnValueObject;
					map.put(rsmd.getColumnName(i + 1).toUpperCase(),getColumnValueObject);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("异常SQL:" + table.getSqlString());
			throw new Exception(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pre != null) {
					pre.close();
				}
				if (conn != null) {
					super.getBaseJDBCDao().closeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//查询语句执行 分页接口
	public TabsBo doSelectForPage(Table table,int index,int pageCounts) throws Exception {
		TabsBo TabsBo = new TabsBo();
		TabsBo.setDangqym(index);
		TabsBo.setFenysl(pageCounts);
		
		StringBuffer SQL = null;
		List<Map> list = new ArrayList<Map>();
		PreparedStatement pre = null;
		PreparedStatement countspre = null;
		ResultSet rs = null;
		ResultSet countsrs = null;
		ResultSetMetaData rsmd = null;
		Connection conn = null;
		try{
			conn = super.getBaseJDBCDao().getConnection();
			String sql =table.getSqlString();
			if(sql.toLowerCase().indexOf("order")!=-1&&sql.toLowerCase().indexOf("by")!=-1){
			//	System.out.println("sql : "+sql);
				sql =sql.substring(0,sql.indexOf("order"))+sql.substring(sql.indexOf(")", sql.indexOf("order")));
			//	System.out.println("sql : "+sql);
			}
			String countsSqlStr = "select count(*) from ("+ sql+")";
			TabsBo.setSql(table.getSqlString());
		//	TabsBo.setParamterMapStr(JSONObject.fromObject(table.getSqlParameter()).toString());
		//	System.out.println(JSONObject.fromObject(table.getSqlParameter()).toString());
			//Map map =table.getSqlParameter();
			Map pamaeterMap =new HashMap();
			Map SqlParameter = table.getSqlParameter();
			if(SqlParameter!=null)
			{
				for(int i = 1 ;i<=SqlParameter.size();i++)
				{	
					SqlParameter sqlParameter = (SqlParameter) SqlParameter.get(i);
					if(sqlParameter!=null)
					{
						if("string".equals(sqlParameter.getType().toLowerCase()))
						{
							pamaeterMap.put(i+"", sqlParameter.getValue());
						}
						if("int".equals(sqlParameter.getType().toLowerCase()))
						{
							pamaeterMap.put(i+"", Integer.valueOf(sqlParameter.getValue()));
						}
						if("long".equals(sqlParameter.getType().toLowerCase()))
						{
							pamaeterMap.put(i+"", Long.valueOf(sqlParameter.getValue()));
						}
						if("float".equals(sqlParameter.getType().toLowerCase()))
						{
							pamaeterMap.put(i+"",Float.valueOf(sqlParameter.getValue()));
						}
						if("double".equals(sqlParameter.getType().toLowerCase()))
						{
							pamaeterMap.put(i+"",Double.valueOf(sqlParameter.getValue()));
						}
					}
				}
			}
			TabsBo.setParamterMapStr(JSONObject.fromObject(pamaeterMap).toString());
			countspre = conn.prepareStatement(countsSqlStr);
			this.doPreparedStatement(countspre, table);
			countsrs = countspre.executeQuery();
			countsrs.next();
			Integer counts = Integer.valueOf(countsrs.getObject(1).toString());
			TabsBo.setCounts(counts.intValue());
			
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type))
			{
				SQL=new StringBuffer("SELECT * FROM (SELECT  ROWNUM 序号,A.* FROM () A WHERE ROWNUM <= ?) WHERE 序号 > ?");
				SQL.insert(43, table.getSqlString());
			}else{
				SQL=new StringBuffer("SELECT * FROM (SELECT B.*, ROWNUMBER() OVER() AS RN FROM () AS B )AS A WHERE A.RN BETWEEN ? AND ?");
				SQL.insert(58, table.getSqlString());
			}
			pre = conn.prepareStatement(SQL.toString());
			if("oracle".equals(db_type)){
				pre.setLong(table.getSqlParameter().size()+1,index*pageCounts);//25  50
				pre.setLong(table.getSqlParameter().size()+2,(index-1)*pageCounts);//0  25 
			}else{
				pre.setLong(table.getSqlParameter().size()+1,(index-1)*pageCounts+1);
				pre.setLong(table.getSqlParameter().size()+2,(index)*pageCounts);
				System.out.println(SQL);
			}
			pre = conn.prepareStatement(table.getSqlString());
			
			this.doPreparedStatement(pre, table);
			rs = pre.executeQuery();
			rsmd = pre.getMetaData();
			while (rs.next()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++)
				{
					Object getColumnValueObject = rs.getObject(i + 1);
					getColumnValueObject=(null)==getColumnValueObject?"":getColumnValueObject;
					map.put(rsmd.getColumnName(i + 1).toLowerCase(),getColumnValueObject);
					
				}
				list.add(map);
			}
			TabsBo.setList(list);
		} catch (SQLException e) {
			System.out.println(SQL);
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pre != null) {
					pre.close();
				}if (countsrs != null) {
					countsrs.close();
				}
				if (countspre != null) {
					countspre.close();
				}
				if (conn != null) {
					super.getBaseJDBCDao().closeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return TabsBo;
	}
	
	//查询语句执行 数量
	public long getPageCounts(Table table) throws Exception {
		String sql =table.getSqlString();
		/*if(sql.toLowerCase().indexOf("order")!=-1&&sql.toLowerCase().indexOf("by")!=-1){
			sql=sql.toLowerCase();
			System.out.println(sql);
			sql =sql.substring(0,sql.indexOf("order"))+sql.substring(sql.indexOf(")", sql.indexOf("order")));
			System.out.println(sql);
		}*/
		StringBuffer CountsSQL= new StringBuffer("SELECT count(*) as counts FROM()");
		CountsSQL.insert(31, sql);
		long counts = 0;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			 pre = conn.prepareStatement(CountsSQL.toString());
			 rs = pre.executeQuery();
			 while(rs.next()){
				 counts = rs.getLong("counts");
			 }
			
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pre != null) {
					pre.close();
				}
				if (conn != null) {
					super.getBaseJDBCDao().closeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return counts;
	}
	
	//事物DML语句执行
	public void doSWDML(List list) throws SQLException{
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<list.size();i++)
			{
				Table table = (Table) list.get(i);
				System.out.println(table.getSqlString());
				pre = conn.prepareStatement(table.getSqlString());
				this.doPreparedStatement(pre,table);
				pre.execute();
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new SQLException("交易提交操作失败!请核查上送交易是否正确!异常："+e.getMessage());
		} finally {
			if (pre != null)
			{
				pre.close();
			}
			if (conn != null)
			{
				conn.setAutoCommit(true);
				super.getBaseJDBCDao().closeConnection(conn);
			}
			
		}
	}
	
	/*
	 *SQL赋值 【内部】
	 */
	private void doPreparedStatement(PreparedStatement pre ,Table table) throws Exception{
		Map SqlParameter = table.getSqlParameter();
		if(SqlParameter!=null)
		{
			for(int i = 1 ;i<=SqlParameter.size();i++)
			{	
				SqlParameter sqlParameter = (SqlParameter) SqlParameter.get(i);
				if(sqlParameter!=null)
				{
					if("string".equals(sqlParameter.getType().toLowerCase()))
					{
						pre.setString(i, sqlParameter.getValue());
					}
					if("int".equals(sqlParameter.getType().toLowerCase()))
					{
						pre.setInt(i, Integer.valueOf(sqlParameter.getValue()));
					}
					if("long".equals(sqlParameter.getType().toLowerCase()))
					{
						pre.setLong(i, Long.valueOf(sqlParameter.getValue()));
					}
					if("float".equals(sqlParameter.getType().toLowerCase()))
					{
						pre.setFloat(i,Float.valueOf(sqlParameter.getValue()));
					}
					if("double".equals(sqlParameter.getType().toLowerCase()))
					{
						pre.setDouble(i,Double.valueOf(sqlParameter.getValue()));
					}
				}
			}
		}
	}
	
	public List<Map> page(String sql,int index,int pageCounts) throws Exception{
			List<Map> list =new ArrayList<Map>();
			StringBuffer CountsSQL= new StringBuffer("SELECT count(*) as counts FROM()");
			StringBuffer SQL =null;
			Connection conn = null;
		 	PreparedStatement pre1 =null;
		 	PreparedStatement pre2 =null;
		 	ResultSet rs1 = null;
		 	ResultSet rs2 = null;
		 	ResultSetMetaData rsmd=null;
			try{
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type))
			{
				SQL = new StringBuffer("SELECT * FROM (SELECT  ROWNUM 序号,A.* FROM () A WHERE ROWNUM <= ?) WHERE 序号 > ?");
				SQL.insert(43, sql);
			}else{
				SQL = new StringBuffer("SELECT * FROM (SELECT B.*, ROWNUMBER() OVER() AS RN FROM () AS B )AS A WHERE A.RN BETWEEN ? AND ?");
				SQL.insert(58, sql);
			}
		 	
		 	CountsSQL.insert(31, sql);
		 		conn = super.getBaseJDBCDao().getConnection();
				 pre1 = conn.prepareStatement(CountsSQL.toString());
				 rs1 = pre1.executeQuery();
				 while(rs1.next()){
					 Map<String,Object> map =new LinkedHashMap<String,Object>();
					 map.put("counts", rs1.getObject("counts"));
					 list.add(map);
				 }
				 pre2 = conn.prepareStatement(SQL.toString());
				 System.out.println(SQL.toString());
				 if("oracle".equals(db_type)){
					 pre2.setInt(1,(index+1)*pageCounts);
					 pre2.setInt(2,index*pageCounts);
				 }else{
					 pre2.setInt(1,index*pageCounts);
					 pre2.setInt(2,(index+1)*pageCounts);
				 }
				
				 rs2 = pre2.executeQuery();
				 rsmd= pre2.getMetaData();
				 while (rs2.next()) {   
					 	Map<String,Object> map =new LinkedHashMap<String,Object>();
					    for (int i = 0; i < rsmd.getColumnCount(); i++) { 
					    	Object getColumnValueObject = rs2.getObject(i + 1);
							getColumnValueObject=(null)==getColumnValueObject?"":getColumnValueObject;
							map.put(rsmd.getColumnName(i + 1).toLowerCase(),getColumnValueObject);
					     }   
					    list.add(map);
					 }  
			} catch (SQLException e) {
				throw  new Exception(e);
			}finally{
					try {
						if(rs1!=null) 
						{
							rs1.close();
						}
						if(pre1!=null)
						{
							pre1.close();
						}
						if(rs2!=null)
						{
							rs2.close();
						}
						if(pre2!=null)
						{
							pre2.close();
						}
						if(conn!=null)
						{
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		 	return list;
	 }
	
	
	//获取表列属性
	public Map getTable(String tableName) throws Exception {
		Map lineMap = new HashMap();
		PreparedStatement pre = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Connection conn = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			pre = conn.prepareStatement("select * from " + tableName);
			rs = pre.executeQuery();
			rsmd = pre.getMetaData();
			if (rsmd != null)
			{
				int count = rsmd.getColumnCount();
				for (int i = 1; i <= count; i++)
				{
					Dictionary dictionary = new Dictionary();
                    dictionary.setField(rsmd.getColumnName(i).toLowerCase());
                    dictionary.setType(rsmd.getColumnTypeName(i).toLowerCase());
                    dictionary.setColumnSize(rsmd.getColumnDisplaySize(i));
                    dictionary.setReadOnly(rsmd.isReadOnly(i));
                    dictionary.setIsNullable(rsmd.isNullable(i));
                    lineMap.put(rsmd.getColumnName(i).toLowerCase(), dictionary);
                                    
				}
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pre != null) {
					pre.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lineMap;
	}
	
	//获取数据库类型
	public String getDBtype(){
		String db_type = DBinfoConig.getDBType();
		return db_type;
	}
}
