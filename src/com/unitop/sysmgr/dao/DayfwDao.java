package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Dayfwcs;

public interface DayfwDao extends BaseDataResourcesInterface{
	
	public void save(Dayfwcs dayfwcs); 
	
	public void delete(String dayfwid);
	
	public void update(Dayfwcs dayfwcs);
	
	public List selectAll();
	
	public Dayfwcs getDayfwcs(String dayfwid);
	
	public void updateDaysl(String dayfwid,int currentCount);
}
