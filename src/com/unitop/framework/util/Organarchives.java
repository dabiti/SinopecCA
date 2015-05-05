package com.unitop.framework.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.unitop.sysmgr.bo.Org;

/**
 * 此类实现了机构树
 * 
 * @author liushan
 * 
 */
public class Organarchives {
	/**
	 * SQL语句
	 */
	private String SQL = "select INTERNALORGANIZATIONNUMBER,LEGALNAME,P_INTERNALORGANIZATIONNUMBER from ORGANARCHIVES where P_INTERNALORGANIZATIONNUMBER=?";

	/**
	 * 用来存放机构树的List
	 */
	private List<Org> list = new ArrayList<Org>();

	/**
	 * 用来获得连接
	 */
	private Connection con = null;

	public Organarchives(Connection con) {

		this.con = con;

	}

	/**
	 * 此方法返回一个机构树List
	 * 
	 * @param orgNum
	 * @return
	 */
	private List getOrg(String orgNum) {

		PreparedStatement prep = null;
		ResultSet rst = null;
		try {
			prep = con.prepareStatement(SQL);
			prep.setString(1, orgNum);
			rst = prep.executeQuery();
			Org organarchives = null;

			// 遍历结果集
			while (rst.next()) {
				// 将结果包装成Org对象，存入List中
				organarchives = new Org();
				String num = rst.getString("ORGANNUM");
				// System.out.println(++i+":"+num);
				organarchives.setCode(num);
				organarchives.setName(rst.getString("ORGANNAME"));
				organarchives.setParentCode(rst.getString("N_PARENTNUM"));
				list.add(organarchives);

				// 递归调用getOrg方法
				this.getOrg(num);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (prep != null) {
					prep.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				if (rst != null) {
					rst.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 关闭数据连接
	 */
	public void closeCon() {
		try {

			if (con != null && !con.isClosed()) {
				con.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为外界提供一个调用getOrg的公共方法
	 * 
	 * @param orgNum
	 * @return
	 */
	public List dowork(String orgNum) {
		List list = this.getOrg(orgNum);
		// 关闭连接
		this.closeCon();
		return list;
	}

	public String getSQL() {
		return SQL;
	}

	public void setSQL(String sql) {
		SQL = sql;
	}


}
