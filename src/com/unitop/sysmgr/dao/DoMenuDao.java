package com.unitop.sysmgr.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.unitop.sysmgr.bo.Menu;


public interface DoMenuDao  extends BaseDataResourcesInterface{
	public List  selectForMenu();
	
	public Menu getMenuById(String gongnid,String zignid,String shangjgn)throws SQLException;
	
	public void saveProMenu(Menu menu) throws SQLException;
	
	public void deleteProMenu(String gongnid,String zignid) throws SQLException;

	public List getCaidlx() throws SQLException;
	
	public Menu getMenuForId(String gongnid,String zignid) throws SQLException;
	
	public void updateMenu(Menu menu) throws SQLException;
	
	public List  getMenuForName(String name);
	
	public List getMenuForShangjgn(String gongnid) throws SQLException ;
	
	public void save(Menu menu);
//	public Menu getMenubyJcgnid(String gongnid,String zignid) throws SQLException ;

//	public List getMenusByGongnId(String gongnid);
	public List getAllFunctions();

	public void deleteAllChanp();
	
	public Map getAllChanp();
}
