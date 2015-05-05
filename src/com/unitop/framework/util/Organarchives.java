package com.unitop.framework.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.unitop.sysmgr.bo.Org;

/**
 * ����ʵ���˻�����
 * 
 * @author liushan
 * 
 */
public class Organarchives {
	/**
	 * SQL���
	 */
	private String SQL = "select INTERNALORGANIZATIONNUMBER,LEGALNAME,P_INTERNALORGANIZATIONNUMBER from ORGANARCHIVES where P_INTERNALORGANIZATIONNUMBER=?";

	/**
	 * ������Ż�������List
	 */
	private List<Org> list = new ArrayList<Org>();

	/**
	 * �����������
	 */
	private Connection con = null;

	public Organarchives(Connection con) {

		this.con = con;

	}

	/**
	 * �˷�������һ��������List
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

			// ���������
			while (rst.next()) {
				// �������װ��Org���󣬴���List��
				organarchives = new Org();
				String num = rst.getString("ORGANNUM");
				// System.out.println(++i+":"+num);
				organarchives.setCode(num);
				organarchives.setName(rst.getString("ORGANNAME"));
				organarchives.setParentCode(rst.getString("N_PARENTNUM"));
				list.add(organarchives);

				// �ݹ����getOrg����
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
	 * �ر���������
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
	 * Ϊ����ṩһ������getOrg�Ĺ�������
	 * 
	 * @param orgNum
	 * @return
	 */
	public List dowork(String orgNum) {
		List list = this.getOrg(orgNum);
		// �ر�����
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
