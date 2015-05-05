package com.unitop.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.unitop.sysmgr.bo.Tisxx;
import com.unitop.sysmgr.dao.PromptDao;

@Repository("PromptDaoImpl")
public class PromptDaoImpl extends BaseDataResources implements PromptDao {

	public Map<String, String> getPromptMsg() {
		Map map = new HashMap();
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery("from Tisxx");
			List rs = query.list();
			for(int i=0;i<rs.size();i++){
				Tisxx tisxx = (Tisxx) rs.get(i);
				map.put(tisxx.getMsgId(), tisxx.getMsgContent());
			}
			
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			getBaseHibernateDao().closeSession(session);
		}
		/*Connection con = getBaseJDBCDao().getConnection();
		String sql = "select * from tisxx";
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String,String> map = new HashMap<String, String>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				map.put(rs.getString("msgid"), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pst!=null){
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		
		return map;
	}

}
