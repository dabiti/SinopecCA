package com.unitop.sysmgr.service;

import java.util.List;

import com.unitop.sysmgr.bo.Dayfwcs;

public interface DayfwService {
    
	public void save(Dayfwcs dayfwcs); 
	
	public void delete(String dayfwid);
	
	public void update(Dayfwcs dayfwcs,String dayfwid) throws Exception;
	
	public List selectAll();
	
	public Dayfwcs getDayfwcs(String dayfwid);
}
