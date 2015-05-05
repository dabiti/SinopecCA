package com.unitop.sysmgr.service;

import java.util.List;

import com.unitop.sysmgr.bo.Privilege;
import com.unitop.sysmgr.bo.Role;

public interface RoleService {
	
    public List getAllRole();
	
	public Role getRole(String role);
	
	public void deleteRole(String juesid) throws Exception;
	
	public void update(Role role) throws Exception; 
	
	public Role getRoleByName(String juesmc);
}
