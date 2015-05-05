package com.unitop.sysmgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface BaseJDBCDao {

	// getJDBC();
	public Connection getConnection();

	// colseJDBC();
	public void closeConnection(Connection con,PreparedStatement pstmt,ResultSet rs);
	
	//获取 数据源
	public void set_Connection(Connection conn);
	//释放 数据源
	public void shifConnection();
	//关闭连接
	public void closeConnection(Connection conn);
	
	
}
