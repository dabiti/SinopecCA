package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.dao.AjaxDao;
/*
 * ajaxÇëÇóÏìÓ¦
 */
@Repository("AjaxDaoImpl")
public class AjaxDaoImpl  extends BaseDataResources  implements AjaxDao {
	/*
	 * id:SQLÓï¾ä
	 * message Ë÷Òý
	 * (non-Javadoc)
	 * @see com.unitop.sysmgr.dao.AjaxDao#ajax(java.lang.String, java.lang.String)
	 */
	public String ajax(String id,String message) throws Exception {
		String rMessage="";
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		try {
			conn = super.getBaseJDBCDao().getConnection();
			pre = conn.prepareStatement(id);
			pre.setString(1, message);
			rs = pre.executeQuery();
			while(rs.next())
			{
				rMessage=(String)rs.getObject(1);
			}
			return rMessage;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pre != null) {
				pre.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
}
