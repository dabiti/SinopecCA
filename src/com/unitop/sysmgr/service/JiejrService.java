package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.JiejrBo;

/*
 * 节假日管理 实现接口
 */
public interface JiejrService {
	//更新、保存节假日管理
	public void updateOrSaveJiejr(JiejrBo JiejrBo);
	//获取节假日管理 
	public JiejrBo getJiejr(String year);
	//删除节假日管理 
	public void deletJiejr(String year);
}
