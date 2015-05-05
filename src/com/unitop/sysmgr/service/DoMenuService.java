package com.unitop.sysmgr.service;

import java.sql.SQLException;
import java.util.List;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Menu;
import com.unitop.sysmgr.bo.Tree;

public interface DoMenuService {
//	
//	//保存菜单维护树
//	public void insert(List l) throws SQLException;
	
//	//获取菜单维护树
//	public List select() throws SQLException;
	
	//获取菜单维护树
	public List selectForMenu(Clerk clerk) throws SQLException;
	
//	//获取菜单维护树
//	public Tree getTree(String treeId) throws SQLException;
//	
	//获取菜单表
	public Menu getMenuForId(String gongnid,String zignid) throws SQLException;
	
//	//获取菜单表
//	public List selectForPost() throws SQLException;
//	
	//修改表单
	public void updateMenu(Menu menu)throws SQLException;
	
	//从基础菜单表中查询
	public List getMenuForName(String name)throws SQLException;
	
	//获取产品菜单列表
	public List getMenuForShangjgn(String gongnid)throws SQLException;

	public void save(Menu menu)throws SQLException;

	public void delete(String gongnid, String zidmc)throws SQLException;
	
	public void saveMenu(String gongnid,String zignid,String shangjgn) throws SQLException;		
	
	public List getCaidlx() throws SQLException;
	
	public Menu getMenubyJcgnid(String gongnid,String zignid) throws SQLException;

	public List getJicFunctions();

	public void saveOrUpdate(List privileList);
	
}
