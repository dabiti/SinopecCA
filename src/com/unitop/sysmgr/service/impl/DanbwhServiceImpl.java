package com.unitop.sysmgr.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinopec.table.model.SqlParameter;
import com.sinopec.table.model.Table;
import com.unitop.sysmgr.bo.Danbwh;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.dao.DanbwhBiaodDao;
import com.unitop.sysmgr.dao.DanbwhDao;
import com.unitop.sysmgr.dao.DanbwhgxbDAO;
import com.unitop.sysmgr.service.DanbwhService;

@Service("DanbwhServiceImpl")
public class DanbwhServiceImpl extends BaseServiceImpl implements DanbwhService {
	
	@Resource
	public DanbwhDao DanbwhDao;
	@Resource
	public DanbwhBiaodDao doDanbwh;
	@Resource
	public DanbwhgxbDAO danbwhgxbDAO;
	
	
	//DML���ִ��
	public void doDML(Table table) throws Exception {
		DanbwhDao.doDML(table);
	}
	
	//DML���ִ��-����rows
	public int doDMLForUpdate(Table table) throws Exception {
		return DanbwhDao.doDMLForUpdate(table);
	}
	
	//��ѯ���ִ��
	public List<Map> doSelect(Table table) throws Exception {
		List list  = DanbwhDao.doSelect(table); 
		return list;
	}
	
	//��ѯ���ִ��
	public TabsBo doSelectForPage(Table table) throws Exception {
		return DanbwhDao.doSelectForPage(table, this.tabsBo.getDangqym(), this.tabsBo.getFenysl()); 
	}
	
	//��ѯ���ִ�� ����
	public long getPageCounts(Table table) throws Exception {
		return DanbwhDao.getPageCounts(table);
	}
	
	public List<Map> page(String sql,int index,int pageCounts) throws Exception{
		return DanbwhDao.page(sql, index, pageCounts);
	}
	
	public void add(Danbwh danbwh) throws SQLException {
		DanbwhDao.add(danbwh);
	}

	public void delete(String gongnid) throws SQLException {
		List tableList = new ArrayList();
		//ɾ������ά������
		String DANBWHZBSQL = "delete  DANBWHZB where gongnid=?"; 
		Table DANBWHZBTable = new Table(DANBWHZBSQL);
		DANBWHZBTable.getSqlParameter().put(1,new SqlParameter("zhangh","string",gongnid));
		tableList.add(DANBWHZBTable);
		//ɾ������ά���ӱ�
		String DANBWHCBSQL = "delete DANBWHCB where gongnid=?"; 
		Table DANBWHCBTable = new Table(DANBWHCBSQL);
		DANBWHCBTable.getSqlParameter().put(1,new SqlParameter("zhangh","string",gongnid));
		tableList.add(DANBWHCBTable);
		//ɾ������ά����ϵ��
		String DANBWHGXBSQL = "delete DANBWHGXB where zhubbh=?"; 
		Table DANBWHGXBTable = new Table(DANBWHGXBSQL);
		DANBWHGXBTable.getSqlParameter().put(1,new SqlParameter("zhangh","string",gongnid));
		tableList.add(DANBWHGXBTable);
		//ִ��
		DanbwhDao.doSWDML(tableList);
	}

	public Danbwh find(String gongnid) throws SQLException {
		return DanbwhDao.find(gongnid);
	}

	public List getAll() throws SQLException {
		return DanbwhDao.getAll();
	}

	public void update(Danbwh danbwh) throws SQLException {
		DanbwhDao.update(danbwh);
	}
	public int getR_Result(String baobbs,String yaosbs) throws Exception{
		Table table = new Table("select * from r_jiegyspz where baobbs=? and yaosbs = ?");
		table.getSqlParameter().put(1, new SqlParameter("baobbs", "string", baobbs));
		table.getSqlParameter().put(2, new SqlParameter("yaosbs", "string", yaosbs));
		List<Map> list = doSelect(table);
		return list.size();
	}
	
	public Map getTable(String tableName) throws Exception{
		return DanbwhDao.getTable(tableName); 
	}	
}