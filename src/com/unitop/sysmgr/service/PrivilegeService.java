package com.unitop.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.unitop.sysmgr.bo.Role;

public interface PrivilegeService {
	
	
	
	public List getPrivilegeList(String juesid);
	
	public Map getPrivilegeForMenue(String clerkCode) ;
	
	public List getAllPrivilege();
	
	//х╗оч: ID=Privilege 
	public Map getPrivilegeMap();
	
	public void saveJuesqxgxbList(Role role,List  privileList) throws Exception;
	
	public void updateJuesqxgxbList(Role role,List  privileList) throws Exception;

	public List getChanpcd();
	
}
