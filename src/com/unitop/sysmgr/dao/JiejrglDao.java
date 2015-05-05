package com.unitop.sysmgr.dao;

import com.unitop.sysmgr.bo.JiejrBo;

/*
 * 节假日管理 数据操作类
 */
public interface JiejrglDao extends BaseDataResourcesInterface{
	
	/*
	 * 依据年份(ID) 获取数据
	 */
	public JiejrBo getJiejrBo(String year);
	/*
	 * 保存数据
	 */
	public void saveJiejrBo(JiejrBo jiejrBo);
	/*
	 * 更新数据
	 */
	public void updateJiejrBo(JiejrBo JiejrBo);
	
	/*
	 * 删除数据
	 */
	public void deleteJiejrBo(JiejrBo JiejrBo);
}
