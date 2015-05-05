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
	 * @param hql 传入的hql语句
	 * @param page 要查询的页数
	 * @param max 一页显示的条数
	 * 通过传入的hql语句及页数、条数返回分页查询结果
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
			//设置开始行号
			//query.setFirstResult(firstRow);
			//设置一页显示行数
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
	 * @param sql 传入的sql语句
	 * @param page 要查询的页数
	 * @param max 一页显示的条数
	 * @param parameterMap 传入SQL中要赋值的字段key=占位符、value=要赋的值
	 * @param entity 映射实体的类型
	 * @param columns 要查询的column组成的字符串数组
	 * (注意：这里使用的是映射到实体里定义的变量名，不是数据库里的column名，要区分大小写！！
	 * 如果实体中没有要查询字段的映射，用此方法会出错，建议使用pagingObjectForSql)
	 * @return list 通过传入的Sql语句及页数、条数返回分页查询结果，类型是实体
	 */
	public List pagingEntityForSql(String sql,int page,int max,Map parameterMap,Class entity,String[] columns) {
		List list = new ArrayList();
		Integer firstRow = (page-1)*max;
		Integer lastRow = page*max;
		Session session = getBaseHibernateDao().getHibernateSession();
		try{
			StringBuffer pagingSelect = new StringBuffer();
			String db_type = DBinfoConig.getDBType();
			
			//区分数据库拼装带分页功能的SQL语句
			if("oracle".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*,  rownum rownum_ from (");
			}else if("db2".equals(db_type)){
				pagingSelect.append("select * from ( select row_.*, ROW_NUMBER() over() as rownum_ from (");
			}
			pagingSelect.append(sql);
			pagingSelect.append(") row_ ) where rownum_ <= :lastRow and rownum_ > :firstRow");
			
			//SQLQuery query = session.createSQLQuery(pagingSelect.toString());
			SQLQuery query = session.createSQLQuery(sql);
			//声明查询返回结果的实体类型
			query.setResultTransformer(new AliasToBeanResultTransformer(entity));
			//加载查询对应实体中的字段，必须select语句中的字段一一对应
			for(int i=0;i<columns.length;i++){
				if(!StringUtil.isEmpty(columns[i])){
					query.addScalar(columns[i]);
				}
			}
			this.doQuery(query, parameterMap);
			//设置开始行号
			//query.setInteger("firstRow", firstRow);
			//设置结束行号
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
	 * @param sql 传入的sql语句
	 * @param page 要查询的页数
	 * @param max 一页显示的条数
	 * @param parameterMap 传入SQL中要赋值的字段key=占位符、value=要赋的值
	 * 根据传入的Sql语句及页数、条数返回分页查询结果，类型是Object数组，适用于没有固定映射的查询结果集\
	 * 注意：KEY 为大写
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
	 * @param sql 传入的sql语句
	 * @param page 要查询的页数
	 * @param max 一页显示的条数
	 * @param parameterMap 传入SQL中要赋值的字段key=占位符、value=要赋的值
	 * 根据传入的Sql语句及页数、条数返回分页查询结果，类型是MAP数组，适用于没有固定映射的查询结果集
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
	
	//处理HQL\SQL中存在分组参数(type=sql\HQL)
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
	//SQL/HQL 参数赋值
	private void doQuery(Query query,Map parameterMap){
		//遍历要赋值的Map，将原select语句中的占位符换成具体数据
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