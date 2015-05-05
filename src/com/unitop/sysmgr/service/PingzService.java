package com.unitop.sysmgr.service;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;

public interface PingzService {
	/*
	 * ��������ID ��ѯ������ƾ֤��ϸ�б�
	 */
	public List findPingzByPich(String pich);
	
	/*
	 * ����ƾ֤ID ����ƾ֤ģ��
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
