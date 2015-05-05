package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Role;

public interface RoleDao extends BaseDataResourcesInterface {
	public List getAllRole();
	public Role getRole(String juesid);
	public void deleteRole(Role role);
	public void save(Role role) throws Exception; 
	public List getRoleByClerkCode(String clerkCode);
	//依据柜员号查询柜员角色明细
	public List getRoleListByClerkCode(String clerkCode);
	public Role getRoleByName(String juesmc);
	public List getAllRoleByJuesjb(String juesjb);
}
