package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import com.unitop.sysmgr.dao.BaseJDBCDao;

@Repository("BaseJDBCDaoImpl")
public class BaseJDBCDaoImpl implements BaseJDBCDao {
	
	//private Connection connection = null;//事务session
	
	//private Connection _connection = null;//新开启sesion
	
	private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();
	
	private DataSource dataSource;

	public Connection getConnection() {
		if(tl.get() != null)
			return tl.get();
		else{
		Connection conn=null;
			 try {
				conn=this.dataSource.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return conn;
		}
	}
	
	public void closeConnection(Connection con,PreparedStatement pstmt,ResultSet rs){
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(pstmt!=null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(con!=null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void set_Connection(Connection conn) {
		try {
			conn.setAutoCommit(false);
			tl.set(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void shifConnection() {
		try {
			if(tl.get()!=null)
			{
				Connection conn=tl.get();
				conn.setAutoCommit(true);
				conn.close();
				tl.remove();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// connection
	public void closeConnection(Connection conn) {
		if(tl.get() == null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Resource(name="dataSource")
	public void setJDBCDataSource(DataSource dataSource) throws SQLException{
		 this.dataSource = dataSource;
	}
	
}
