package com.unitop.sysmgr.dao;

import java.util.ArrayList;
import java.util.List;

import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;

/*
 * 凭证配置表操作
 */
public interface PingzpzbDao   extends BaseDataResourcesInterface{
	
	// 查询凭证配置列表信息
	public List<Pingzpzb> getPingzpzList();
	

	// 根据凭证号查询凭证信息
	public Pingzpzb getPingzpzByPingzbs(String pingzbs);
	
	public List<Yanygz> getYanygzList();


	public void updatePingzpzb(Pingzpzb pingzpzb);


	public Pingzpzb queryPingzpzbByPzmc(String pingzmc, String pingzbs);


	public void savePingz(Pingzpzb pz);


	public void deletePingz(String pingzbs);
}
