package com.unitop.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.sysmgr.dao.YinjzhbDao;
@Repository("YinjzhbDaoImpl")
public class YinjzhbDaoImpl extends BaseDataResources implements YinjzhbDao {

	private static Logger log = Logger.getLogger(YinjzhbDaoImpl.class);
	public void delYinjzh(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			String sql = "delete yinjzhb where zhangh=:zhangh";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("zhangh", zhangh);
			query.executeUpdate();
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}

	public List getYinjzh(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = null;
		try{
			Query query = session.createQuery("from Yinjzhb where id.zhangh=:zhangh");
			query.setString("zhangh", zhangh);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	
	public List getYinjzhforMap(String zhangh) throws BusinessException {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List<Map> list = null;
		try{
			Query querytime=session.createSQLQuery("select max(qiyrq) from yinjb where zhangh=:zhangh and yinjshzt='����'");
			querytime.setString("zhangh", zhangh);
			Object result=querytime.uniqueResult();
			if(result ==null||"null".equals(String.valueOf(result))||"".equals(String.valueOf(result))){
				return list;
			}
			String qiyrq=String.valueOf(result);
			Query query = session.createSQLQuery("select * from Yinjzhb where zhangh=:zhangh and qiyrq=:qiyrq").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setString("zhangh", zhangh);
			query.setString("qiyrq",qiyrq);
			list = query.list();
			for (Iterator<Map> iter = list.iterator(); iter.hasNext();) 
			{
				 Map element = iter.next();
				 String ZUHGZ = (String) element.get("ZUHGZ");
			/*	 ZUHGZ = ZUHGZ.replace("|", "����");
				 ZUHGZ = ZUHGZ.replace("&", "����");
				 StringBuffer newZUHGZ = new StringBuffer();
					//ѭ������
					while(ZUHGZ.indexOf("C")!=-1){
						//newZUHGZ���C֮ǰ����
						newZUHGZ.append(ZUHGZ.substring(0, ZUHGZ.indexOf("C")));
						//�ҵ�C�������ѡ����
						int c = ZUHGZ.indexOf("C");
						char j = ZUHGZ.charAt(c+1);
						//��һ����λ��
						int k = ZUHGZ.indexOf("[");
						//��һ����λ��
						int m = ZUHGZ.indexOf("]");
						//ȡ�á����в���
						String kuoh = ZUHGZ.substring(k+1,m);
						//newZUHGZ���C����
						newZUHGZ.append("["+kuoh+"����ѡ"+j+"]");
						//�ж��Ƿ�Ϊ���һ��
						if(ZUHGZ.indexOf("C")==ZUHGZ.lastIndexOf("C")){
							newZUHGZ.append(ZUHGZ.substring(m+1));
						}
						ZUHGZ = ZUHGZ.substring(m+1);
					}
					element.put("ZUHGZ", "".equals(newZUHGZ.toString())?ZUHGZ:newZUHGZ.toString());
				 
				 */
				 element.put("ZUHGZ", transformZuhgz(ZUHGZ));
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
		
		return list;
	}
	private String transformZuhgz(String ZUHGZ){
		if(ZUHGZ==null||"".equals(ZUHGZ)){
			return ZUHGZ;
		}
		ZUHGZ = ZUHGZ.replace("|", "����");
		 ZUHGZ = ZUHGZ.replace("&", "����");
		 StringBuffer newZUHGZ = new StringBuffer();
			//ѭ������
			while(ZUHGZ.indexOf("C")!=-1){
				//newZUHGZ���C֮ǰ����
				newZUHGZ.append(ZUHGZ.substring(0, ZUHGZ.indexOf("C")));
				//�ҵ�C�������ѡ����
				int c = ZUHGZ.indexOf("C");
				char j = ZUHGZ.charAt(c+1);
				//��һ����λ��
				int k = ZUHGZ.indexOf("[");
				//��һ����λ��
				int m = ZUHGZ.indexOf("]");
				//ȡ�á����в���
				String kuoh = ZUHGZ.substring(k+1,m);
				//newZUHGZ���C����
				newZUHGZ.append("["+kuoh+"����ѡ"+j+"]");
				//�ж��Ƿ�Ϊ���һ��
				if(ZUHGZ.indexOf("C")==ZUHGZ.lastIndexOf("C")){
					newZUHGZ.append(ZUHGZ.substring(m+1));
				}
				ZUHGZ = ZUHGZ.substring(m+1);
			}
			return "".equals(newZUHGZ.toString())?ZUHGZ:newZUHGZ.toString();
	}
	
	//-----------------------�ܷ�ͬ��------------------------------------------------------//
	public List<Map<String, String>> queryYjzhbListByZhangh(String zhangh) throws DAOException {
		// ��ѯӡ����ϱ�
		String datesql="select max(qiyrq) qiyrq from yinjb where zhangh=? and yinjshzt='����' ";
		String sql = "select * from YINJZHB t where zhangh=? and qiyrq=? ";

		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		Connection operation =this.getBaseJDBCDao().getConnection();
		PreparedStatement stmt =null;
		PreparedStatement dateps=null;
		ResultSet daters=null;
		ResultSet rs =null;
		String qiyrq="";
		try {
			dateps=operation.prepareStatement(datesql);
			dateps.setString(1, zhangh);
			daters= dateps.executeQuery();
			while (daters.next()) {
				qiyrq=daters.getString("qiyrq");
			}
			if(qiyrq==null||"".equals(qiyrq)){
				return mapList;
			}
			stmt=operation.prepareStatement(sql);
			stmt.setString(1, zhangh);
			stmt.setString(2, qiyrq);
			rs= stmt.executeQuery();

			while (rs.next()) {
				Map<String, String> singleMap = new HashMap<String, String>();
				singleMap.put("ZHANGH", rs.getString("ZHANGH") == null ? ""
						: rs.getString("ZHANGH"));
				singleMap.put("JINEXX", rs.getString("JINEXX") == null ? ""
						: rs.getString("JINEXX"));
				singleMap.put("JINESX", rs.getString("JINESX") == null ? ""
						: rs.getString("JINESX"));
				singleMap.put("XITLX", rs.getString("XITLX") == null ? "" : rs
						.getString("XITLX"));
				singleMap.put("ZUHGZ", transformZuhgz(rs.getString("ZUHGZ") == null ? "" : rs
						.getString("ZUHGZ")));
				singleMap.put("QIYRQ", rs.getString("QIYRQ") == null ? "" : rs
						.getString("QIYRQ"));
				singleMap.put("SHANCRQ", rs.getString("SHANCRQ") == null ? ""
						: rs.getString("SHANCRQ"));
				singleMap.put("ZUHZT", rs.getString("ZUHZT") == null ? "" : rs
						.getString("ZUHZT"));
				singleMap.put("ZUHSHZT", rs.getString("ZUHSHZT") == null ? ""
						: rs.getString("ZUHSHZT"));
				singleMap.put("TINGYRQ", rs.getString("TINGYRQ") == null ? ""
						: rs.getString("TINGYRQ"));

				mapList.add(singleMap);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("��ѯ�˺�Ϊ" + zhangh + "������ӡ�������Ϣʱ�����쳣��" + e.getMessage());
			throw new DAOException("��ѯ�˺�Ϊ" + zhangh + "������ӡ�������Ϣʱ�����쳣",e);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("��ѯ�˺�Ϊ" + zhangh + "������ӡ�������Ϣʱ�����쳣��" + e.getMessage());
			throw new DAOException("��ѯ�˺�Ϊ" + zhangh + "������ӡ�������Ϣʱ�����쳣",e);
		}finally{
			if(daters!=null)
				try {
					daters.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(dateps!=null)
				try {
					dateps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(operation!=null)
				try {
					operation.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return mapList;
	}
	
	
}
