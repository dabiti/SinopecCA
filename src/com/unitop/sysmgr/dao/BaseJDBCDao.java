package com.unitop.sysmgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface BaseJDBCDao {

	// getJDBC();
	public Connection getConnection();

	// colseJDBC();
	public void closeConnection(Connection con,PreparedStatement pstmt,ResultSet rs);
	
	//��ȡ ����Դ
	public void set_Connection(Connection conn);
	//�ͷ� ����Դ
	public void shifConnection();
	//�ر�����
	public void closeConnection(Connection conn);
	
	
}
