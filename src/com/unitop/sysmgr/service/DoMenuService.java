package com.unitop.sysmgr.service;

import java.sql.SQLException;
import java.util.List;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Menu;
import com.unitop.sysmgr.bo.Tree;

public interface DoMenuService {
//	
//	//����˵�ά����
//	public void insert(List l) throws SQLException;
	
//	//��ȡ�˵�ά����
//	public List select() throws SQLException;
	
	//��ȡ�˵�ά����
	public List selectForMenu(Clerk clerk) throws SQLException;
	
//	//��ȡ�˵�ά����
//	public Tree getTree(String treeId) throws SQLException;
//	
	//��ȡ�˵���
	public Menu getMenuForId(String gongnid,String zignid) throws SQLException;
	
//	//��ȡ�˵���
//	public List selectForPost() throws SQLException;
//	
	//�޸ı�
	public void updateMenu(Menu menu)throws SQLException;
	
	//�ӻ����˵����в�ѯ
	public List getMenuForName(String name)throws SQLException;
	
	//��ȡ��Ʒ�˵��б�
	public List getMenuForShangjgn(String gongnid)throws SQLException;

	public void save(Menu menu)throws SQLException;

	public void delete(String gongnid, String zidmc)throws SQLException;
	
	public void saveMenu(String gongnid,String zignid,String shangjgn) throws SQLException;		
	
	public List getCaidlx() throws SQLException;
	
	public Menu getMenubyJcgnid(String gongnid,String zignid) throws SQLException;

	public List getJicFunctions();

	public void saveOrUpdate(List privileList);
	
}
