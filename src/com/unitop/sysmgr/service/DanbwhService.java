package com.unitop.sysmgr.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.sinodata.table.model.Table;
import com.unitop.sysmgr.bo.Danbwh;
import com.unitop.sysmgr.bo.TabsBo;


public interface DanbwhService {
	/*
	 * ��ѯ���
	 */
	public List<Map> doSelect(Table table) throws Exception;
	/*
	 * ��ѯ���(��ҳ)
	 */
	public TabsBo doSelectForPage(Table table) throws Exception;
	/*
	 * DML���ִ��
	 */
	public void  doDML(Table table) throws Exception;
	
	/*
	 * DML���ִ�У����£�
	 */
	public int doDMLForUpdate(Table table) throws Exception;
	/*
	 * ��ѯ��ҳ���ִ��
	 */
	public List<Map> page(String sql,int index,int pageCounts) throws Exception;
	/*
	 * ��ѯ��ҳ���ִ������
	 */
	public long getPageCounts(Table table) throws Exception;
	public List getAll() throws SQLException;
	public void delete(String gongnid)throws SQLException;
	public Danbwh find(String gongnid)throws SQLException;
	public void update(Danbwh danbwh)throws SQLException;
	public void add(Danbwh danbwh)throws SQLException;
	public int getR_Result(String baobbs,String yaosbs) throws Exception;
	public Map getTable(String tableName) throws Exception;
}
