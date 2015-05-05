package com.unitop.sysmgr.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.unitop.config.DBinfoConig;
import com.unitop.framework.util.LogPrinter;
import com.unitop.framework.util.StringUtil;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.dao.TabsDao;
@Repository("TabsDaoImpl")
public class TabsDaoImpl extends BaseDataResources implements TabsDao {

	/**
	 * @author lhz
	 * @param hql �����hql���
	 * @param page Ҫ��ѯ��ҳ��
	 * @param max һҳ��ʾ������
	 * ͨ�������hql��估ҳ�����������ط�ҳ��ѯ���
	 */
	public TabsBo pagingForHql(String hql,int page,int max,Map parameterMap) {
		TabsBo tabsBo = new TabsBo();
		tabsBo.setDangqym(page);
		tabsBo.setFenysl(max);
		Integer firstRow = (page-1)*max;
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			String counsSqlStr = doHQLorSQL("hql",hql);
			Query queryCounts = session.createQuery(counsSqlStr);
			this.doQuery(queryCounts, parameterMap);
			Integer counts = Integer.valueOf(queryCounts.uniqueResult().toString());
			tabsBo.setCounts(counts.intValue());
			Query query = session.createQuery(hql);
			this.doQuery(query, parameterMap);
			//���ÿ�ʼ�к�
			//query.setFirstResult(firstRow);
			//����һҳ��ʾ����
			//query.setMaxResults(max);
			List list = query.list();
			if(list == null)list = new ArrayList();
			tabsBo.setList(list);
			tabsBo.setSql("select * "+hql);
			tabsBo.setParamterMapStr(JSONObject.fromObject(parameterMap==null?new HashMap():parameterMap).toString());
		}catch (HibernateException e) {
			e.printStackTrace();
			LogPrinter.error(e);
		}finally{
			getBaseHibernateDao().closeSession(session);
		}
		return tabsBo;
	}
	
	/**
	 * @author lhz
	 * @param sql �����sql���
	 * @param page Ҫ��ѯ��ҳ��
	 * @param max һҳ��ʾ������
	 * @param parameterMap ����SQL��Ҫ��ֵ���ֶ�key=ռλ����value=Ҫ����ֵ
	 * @param entity ӳ��ʵ�������
	 * @param columns Ҫ��ѯ��column��ɵ��ַ�������
	 * (ע�⣺����ʹ�õ���ӳ�䵽ʵ���ﶨ��ı��������������ݿ����column����Ҫ���ִ�Сд����
	 * ���ʵ����û��Ҫ��ѯ�ֶε�ӳ�䣬�ô˷������������ʹ��pagingObjectForSql)
	 * @return list ͨ�������Sql��估ҳ�����������ط�ҳ��ѯ�����������ʵ��
	 */
	public List pagingEntityForSql(String sql,int page,int max,Map parameterMap,Class entity,String[] columns) {
		List list = new ArrayList();
		Integer firstRow = (page-1)*max;
		Integer lastRow = page*max;
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			StringBuffer pagingSelect = new StringBuffer();
			String db_type = DBinfoConig.getDBType();
			
			//�������ݿ�ƴװ����ҳ���ܵ�SQL���
			if("oracle".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*,  rownum rownum_ from (");
			}else if("db2".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*, ROW_NUMBER() over() as rownum_ from (");
			}
			pagingSelect.append(sql);
			pagingSelect.append(") row_ ) where rownum_ <= :lastRow and rownum_ > :firstRow");
			
			//SQLQuery query = session.createSQLQuery(pagingSelect.toString());
			SQLQuery query = session.createSQLQuery(sql);
			//������ѯ���ؽ����ʵ������
			query.setResultTransformer(new AliasToBeanResultTransformer(entity));
			//���ز�ѯ��Ӧʵ���е��ֶΣ�����select����е��ֶ�һһ��Ӧ
			for(int i=0;i<columns.length;i++){
				if(!StringUtil.isEmpty(columns[i])){
					query.addScalar(columns[i]);
				}
			}
			this.doQuery(query, parameterMap);
			//���ÿ�ʼ�к�
			//query.setInteger("firstRow", firstRow);
			//���ý����к�
			//query.setInteger("lastRow", lastRow);
			list = query.list();
		}catch (HibernateException e) {
			e.printStackTrace();
			LogPrinter.error(e);
		}finally{
			getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	/**
	 * @author lhz
	 * @param sql �����sql���
	 * @param page Ҫ��ѯ��ҳ��
	 * @param max һҳ��ʾ������
	 * @param parameterMap ����SQL��Ҫ��ֵ���ֶ�key=ռλ����value=Ҫ����ֵ
	 * ���ݴ����Sql��估ҳ�����������ط�ҳ��ѯ�����������Object���飬������û�й̶�ӳ��Ĳ�ѯ�����\
	 * ע�⣺KEY Ϊ��д
	 */
	public TabsBo pagingObjectForSql(String sql,int page,int max,Map parameterMap) {
		TabsBo tabsBo = new TabsBo();
		tabsBo.setDangqym(page);
		tabsBo.setFenysl(max);
		Integer firstRow = (page-1)*max;
		Integer lastRow = page*max;
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			String counsSqlStr = doHQLorSQL("sql",sql);
			Query queryCounts = session.createSQLQuery(counsSqlStr);
			this.doQuery(queryCounts, parameterMap);
			Integer counts = Integer.valueOf(queryCounts.uniqueResult().toString());
			tabsBo.setCounts(counts.intValue());
			
			StringBuffer pagingSelect = new StringBuffer();
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*,  rownum rownum_ from (");
			}else if("db2".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*, ROW_NUMBER() over() as rownum_ from (");
			}
			pagingSelect.append(sql);
			pagingSelect.append(") row_ ) where rownum_ <= :lastRow and rownum_ > :firstRow");
			//Query query = session.createSQLQuery(pagingSelect.toString());
			Query query = session.createSQLQuery(sql);
			this.doQuery(query, parameterMap);
			//query.setInteger("firstRow", firstRow);
			//query.setInteger("lastRow", lastRow);
			List list = query.list();
			if(list == null)list = new ArrayList();
			tabsBo.setList(list);
			tabsBo.setSql(sql);
			tabsBo.setParamterMapStr(JSONObject.fromObject(parameterMap==null?new HashMap():parameterMap).toString());
		}catch (HibernateException e) {
			e.printStackTrace();
			LogPrinter.error(e);
		}finally{
			getBaseHibernateDao().closeSession(session);
		}
		return tabsBo;
	}
	
	/**
	 * @author lhz
	 * @param sql �����sql���
	 * @param page Ҫ��ѯ��ҳ��
	 * @param max һҳ��ʾ������
	 * @param parameterMap ����SQL��Ҫ��ֵ���ֶ�key=ռλ����value=Ҫ����ֵ
	 * ���ݴ����Sql��估ҳ�����������ط�ҳ��ѯ�����������MAP���飬������û�й̶�ӳ��Ĳ�ѯ�����
	 */
	public TabsBo pagingMapForSql(String sql,int page,int max,Map parameterMap) {
		TabsBo tabsBo = new TabsBo();
		tabsBo.setDangqym(page);
		tabsBo.setFenysl(max);
		Integer firstRow = (page-1)*max;
		Integer lastRow = page*max;
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			String counsSqlStr = doHQLorSQL("sql",sql);
			Query queryCounts = session.createSQLQuery(counsSqlStr);
			this.doQuery(queryCounts, parameterMap);
			Integer counts = Integer.valueOf(queryCounts.uniqueResult().toString());
			tabsBo.setCounts(counts.intValue());
			
			StringBuffer pagingSelect = new StringBuffer();
			String db_type = DBinfoConig.getDBType();
			if("oracle".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*,  rownum rownum_ from (");
			}else if("db2".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*, ROW_NUMBER() over() as rownum_ from (");
			}
			pagingSelect.append(sql);
			pagingSelect.append(") row_ ) where rownum_ <= :lastRow and rownum_ > :firstRow");
			//Query query = session.createSQLQuery(pagingSelect.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;
			Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;
			this.doQuery(query, parameterMap);
			//query.setInteger("firstRow", firstRow);
			//query.setInteger("lastRow", lastRow);
			List list = query.list();
			if(list == null)list = new ArrayList();
			tabsBo.setList(list);
		}catch (HibernateException e) {
			e.printStackTrace();
			LogPrinter.error(e);
		}finally{
			getBaseHibernateDao().closeSession(session);
		}
		return tabsBo;
	}
	
	//����HQL\SQL�д��ڷ������(type=sql\HQL)
	private String doHQLorSQL(String type,String str){
		if("hql".equals(type.toLowerCase()))
		{
			//System.out.println(" log 1  : "+"select count(*) "+str);
			int orderInt = str.indexOf(" order ");
			int groupInt = str.indexOf(" group ");
			if(orderInt!=-1) str =str.substring(0,orderInt);
			if(groupInt!=-1) str = str.substring(0,groupInt);
		//	System.out.println(" log 2  : "+"select count(*) "+str);
			return  "select count(*) "+str;
		}
		if("sql".equals(type.toLowerCase()))
		{
		//	System.out.println(" log 1  : "+"select count(*) from ("+str+")");
			int orderInt = str.indexOf(" order ");
			int groupInt = str.indexOf(" group ");
			if(orderInt!=-1) str =str.substring(0,orderInt);
			//if(groupInt!=-1) str = str.substring(0,groupInt);
		//	System.out.println(" log 2  : "+"select count(*) from ("+str+")");
			return "select count(*) from ("+str+")";
		}
		return "";
	}
	//SQL/HQL ������ֵ
	private void doQuery(Query query,Map parameterMap){
		//����Ҫ��ֵ��Map����ԭselect����е�ռλ�����ɾ�������
		if(parameterMap!=null){
			Iterator it=parameterMap.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
				if(parameterMap.get(key).getClass() == String.class)
					query.setString(key, (String) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Integer.class)
					query.setInteger(key, (Integer) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == BigDecimal.class)
					query.setBigDecimal(key, (BigDecimal) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setBoolean(key, (Boolean) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setDate(key, (Date) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setFloat(key, (Float) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setDouble(key, (Double) parameterMap.get(key));
			}
		}
	}
}