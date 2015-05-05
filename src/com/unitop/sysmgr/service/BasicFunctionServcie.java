package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.Jicgncd;

public interface BasicFunctionServcie {

	//基础功能保存
	public void insert(Jicgncd jicg);
	//查询
	public Jicgncd select(Jicgncd jicg);
	//验证权限
	public Jicgncd getPost(Jicgncd jicg);
	
}
