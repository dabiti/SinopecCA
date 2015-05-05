package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.unitop.config.Privilege;
import com.unitop.framework.util.GetSQLService;
import com.unitop.sysmgr.dao.ChanpcdDao;
@Repository("ChanpcdDaoImpl")
public class ChanpcdDaoImpl extends BaseDataResources  implements ChanpcdDao {
	
	public Map getAllFromChanpgncd() throws SQLException {
		Map temp = new HashMap();
		Connection con = super.getBaseJDBCDao().getConnection();
		String sql = GetSQLService.getBcodeMesage("PostDaoImpl_1");
		PreparedStatement p=null;
		ResultSet rs=null;
		try {
			p = con.prepareStatement(sql);
			rs = p.executeQuery();
			while (rs.next()) {
				{
					sql = GetSQLService.getBcodeMesage("PostDaoImpl_2");
					PreparedStatement p_ = con.prepareStatement(sql);
					p_.setString(1,rs.getString("GONGNID"));
					ResultSet rs_ = p_.executeQuery();
					int i=0;
					while (rs_.next()){
						Privilege privilege =new Privilege();
						privilege.setName(rs_.getString("GONGNMC"));
						privilege.setType(rs.getString("GONGNMC"));
						privilege.setValue(rs_.getString("QUANXWZ"));
						{
							sql = GetSQLService.getBcodeMesage("PostDaoImpl_3");
							PreparedStatement p__ = con.prepareStatement(sql);
							p__.setString(1,rs_.getString("GONGNID"));
							ResultSet rs__ = p__.executeQuery();
							while (rs__.next()){
								Privilege privilege_ =new Privilege();
								privilege_.setName(rs__.getString("GONGNMC"));
								privilege_.setType(rs__.getString("GONGNMC"));
								privilege_.setValue(rs__.getString("QUANXWZ"));
								privilege.addZhignList(privilege_);
							}
						}
						temp.put(rs_.getString("GONGNMC"), privilege);
						i++;
					}
					if(i==0){
						Privilege privilege =new Privilege();
						privilege.setName(rs.getString("GONGNMC"));
						privilege.setType("业务功能");
						privilege.setValue(rs.getString("QUANXWZ"));
						{
							sql = GetSQLService.getBcodeMesage("PostDaoImpl_3");
							PreparedStatement p__ = con.prepareStatement(sql);
							p__.setString(1,rs.getString("GONGNID"));
							ResultSet rs__ = p__.executeQuery();
							while (rs__.next()){
								Privilege privilege_ =new Privilege();
								privilege_.setName(rs__.getString("GONGNMC"));
								privilege_.setType(rs__.getString("GONGNMC"));
								privilege_.setValue(rs__.getString("QUANXWZ"));
								privilege.addZhignList(privilege_);
							}
						}
						temp.put(rs.getString("GONGNMC"), privilege);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)rs.close();
			if(p!=null)p.close();
			if(con!=null){
				this.getBaseJDBCDao().closeConnection(con);
			}
		}
		return temp;
	}
	
}
