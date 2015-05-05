package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.dao.AutoCompleteDao;

//��ȡ�Զ�ƥ����Ϣ��
@Repository("AutoCompleteDaoImpl")
public class AutoCompleteDaoImpl extends BaseDataResources implements AutoCompleteDao {
	
	//ִ��SQL��䷵�� XXX,YYY�ַ�����Ϣ
	public String autoCompleteForAccount(String sql,Map paramMap) {
			StringBuffer buffer = new StringBuffer();
			Session session = this.getBaseHibernateDao().getHibernateSession();			
			try {
				SQLQuery query = session.createSQLQuery(sql);
				Set<String> key = paramMap.keySet();
				Iterator it=paramMap.keySet().iterator();    
				while(it.hasNext())
				{    
				     String key_ ="";    
				     String value_ = "";    
				     key_=it.next().toString();    
				     value_= (String) paramMap.get(key_);
				     query.setString(key_, value_+"%");
				}
				List list = query.list();
				if(list!=null)
				{
					for(int i=0;i<list.size();i++)
					{
						Object[] obj = (Object[]) list.get(i);
						buffer.append(obj[0]);
						if(i!=(list.size()-1))
						{
							buffer.append(",");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				 this.getBaseHibernateDao().closeSession(session);
			}
			return buffer.toString();
	}
	
	//���ر��ֶ���Ϣ
	public Map getTableLineMap(String sql){
		//���ⰲȫ����
		sql+=" where 1<>1";
		Map lineMap = new HashMap();
		// ��ȡһ����������
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd =null;
		try {
			con  = this.getBaseJDBCDao().getConnection();
			pstmt = con .prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			if (rsmd != null)
			{
				int count = rsmd.getColumnCount();
				for (int i = 1; i <= count; i++)
				{
					lineMap.put(rsmd.getColumnName(i).toLowerCase(), rsmd.getColumnTypeName(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.getBaseJDBCDao().closeConnection(con, pstmt, rs);
		}
		return lineMap;
	}	
}