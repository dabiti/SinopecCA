package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;
import com.unitop.sysmgr.bo.TabsBo;

/**
 * �����ҳ�����ļ��нӿ�
 * @author lhz
 *
 */
public interface TabsDao {
	public TabsBo pagingForHql(String hql,int page,int max,Map parameterMap);
	public List pagingEntityForSql(String sql,int page,int max,Map parameterMap,Class entity,String[] columns);
	public TabsBo pagingObjectForSql(String sql,int page,int max,Map parameterMap);
	public TabsBo pagingMapForSql(String sql,int page,int max,Map parameterMap);
}
