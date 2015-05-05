package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.Clerk;

/*
 * 单点登录接口
 */
public interface SSOservice {
	/*
	 * 单点登录验证接口
	 * 
	 * 描述：单点登录验证接口，此接口对应项目中对应行方单点登录实现
	 */
	public boolean ValidateSSO(Clerk clerk);
	/*
	 * 获取单个柜员信息接口
	 * 
	 * 描述：调用指定第三方系统进行获取柜员信息。
	 */
	public Clerk getClerkFromSSO(String clerknum) throws Exception;
	/*
	 * 同步单个柜员信息接口
	 * 
	 * 描述：保存从第三方系统获取的柜员信息同步到柜员表中。
	 * 
	 */
	public void createOrUpdateClerkForSSO(Clerk clerk);	
}
