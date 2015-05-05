package com.unitop.sysmgr.service;

import java.util.List;

public interface PiaojErwmService {
	
	//保存票据二维码
	public void save(PiaojErwmService piaojErwmBo);
	
	//更新票据二维码
	public void update(PiaojErwmService piaojErwmBo);
	
	//保存并且更新票据二维码
	public void saveOrUpdate(PiaojErwmService piaojErwmBo);
	
	//删除二维码
	public void delete(PiaojErwmService piaojErwmBo);
	
	//查询所有记录
	public List selectAll();
	
	//查询特定记录
	public PiaojErwmService selectAll(String piaojErwmId);
	
}
