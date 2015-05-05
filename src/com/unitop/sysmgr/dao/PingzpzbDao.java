package com.unitop.sysmgr.dao;

import java.util.ArrayList;
import java.util.List;

import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;

/*
 * ƾ֤���ñ����
 */
public interface PingzpzbDao   extends BaseDataResourcesInterface{
	
	// ��ѯƾ֤�����б���Ϣ
	public List<Pingzpzb> getPingzpzList();
	

	// ����ƾ֤�Ų�ѯƾ֤��Ϣ
	public Pingzpzb getPingzpzByPingzbs(String pingzbs);
	
	public List<Yanygz> getYanygzList();


	public void updatePingzpzb(Pingzpzb pingzpzb);


	public Pingzpzb queryPingzpzbByPzmc(String pingzmc, String pingzbs);


	public void savePingz(Pingzpzb pz);


	public void deletePingz(String pingzbs);
}
