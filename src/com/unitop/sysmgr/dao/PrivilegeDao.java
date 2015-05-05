package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Privilege;


public interface PrivilegeDao extends BaseDataResourcesInterface  {
	//获取全部权限
	public List getAllPrivilege();
	//获取全部权限 角色管理
	public List getAllPrivilegeForJuesgl();
	
	//获取角色下的权限
	public List<Privilege> getPrivilegeByJuesid(String juesid);
	//批量保存数据
	public void saveJuesqxgxbList(List JuesqxgxbList);
	//删除角色下的权限
	public void deleteJuesqxgxb(String  juesid);
}
