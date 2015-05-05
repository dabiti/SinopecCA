package com.unitop.sysmgr.service;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;

public interface PingzService {
	/*
	 * 根据批次ID 查询此批次凭证明细列表
	 */
	public List findPingzByPich(String pich);
	
	/*
	 * 根据凭证ID 下载凭证模版
	 */
	public void loadPingzmbByName(String pich, ServletOutputStream out)  throws FileNotFoundException;
	
	public Pingzpzb getPingzpzbByPzbs(String pingzbs);
	
	public List<Pingzpzb> getPingzList();
	
	public List<Yanygz> getYangzList();

	public void updatePingzpzb(Pingzpzb pingzpzb);

	public Pingzpzb queryPingzpzbByPzmc(String pingzmc, String pingzbs);

	public void savePingz(Pingzpzb pz);

	public void deletePingzByPzbs(String pingzbs);
}
