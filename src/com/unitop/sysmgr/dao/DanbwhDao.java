package com.unitop.sysmgr.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sinopec.table.model.Table;
import com.unitop.sysmgr.bo.Danbwh;
import com.unitop.sysmgr.bo.TabsBo;

public interface DanbwhDao   extends BaseDataResourcesInterface{

	public List getAll() throws SQLException;

	public void delete(String gongnid)throws SQLException;
	
	public Danbwh find(String gongnid)throws SQLException;

	public void update(Danbwh danbwh)throws SQLException;

	public void add(Danbwh danbwh)throws SQLException;
	
	public void doDML(Table table) throws Exception;
	public int doDMLForUpdate(Table table) throws Exception;
	public List<Map> doSelect(Table table) throws Exception ;
	public List<Map> doSelectForUpperCase(Table table) throws Exception;
	public TabsBo doSelectForPage(Table table,int index,int pageCounts) throws Exception;
	public long getPageCounts(Table table) throws Exception;
	public List<Map> page(String sql,int index,int pageCounts) throws Exception;
	public Map getTable(String tableName) throws Exception;
	public void doSWDML(List list) throws SQLException;
	//获取数据库类型
	public String getDBtype();
}
