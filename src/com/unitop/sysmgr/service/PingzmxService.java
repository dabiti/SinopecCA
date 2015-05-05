package com.unitop.sysmgr.service;

import java.sql.SQLException;
import java.util.List;

import com.unitop.sysmgr.bo.Pingzmx;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.PingzForm;

public interface PingzmxService {
	// 查询所有凭证的详细信息
	public TabsBo selectAllPingz(PingzForm pingzmx) throws SQLException ;
	
	// 保存凭证信息
	public void savePingz(Pingzmx pingz) throws Exception ;
	
	// 修改凭证信息
	public void updatePingz(Pingzmx pingz) throws SQLException ;
	
	// 删除凭证信息
	public void deletePingz(String pingzh) throws SQLException ;
	
	// 查询一个凭证的详细信息
	public Pingzmx findPingzByPingzh(String pingzh) throws SQLException ;
	
	// 查询一个批次的凭证明细
	public List<Pingzmx> findPingzByPich(String pich);
	
	//根据起始凭证号和终止凭证号查询明细信息
	public List<Pingzmx> findPingzByPingzh(String qispzh,String zhongzpzh) throws SQLException;
	
	public void updateZhuangt(String qispzh,String zhongzpzh,String dayfwmsg,String count) throws SQLException;
	
	//批量 删除凭证信息
	public void batchDeletePingz(String pingzhString) throws Exception;
	/*
	 * 根据凭证号查询凭证明细日志
	 */
	public List findPingzRizByPingzh(String pingzh);
}
