package com.unitop.sysmgr.service;

import java.sql.SQLException;
import java.util.List;

import com.unitop.sysmgr.bo.Pingzmx;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.PingzForm;

public interface PingzmxService {
	// ��ѯ����ƾ֤����ϸ��Ϣ
	public TabsBo selectAllPingz(PingzForm pingzmx) throws SQLException ;
	
	// ����ƾ֤��Ϣ
	public void savePingz(Pingzmx pingz) throws Exception ;
	
	// �޸�ƾ֤��Ϣ
	public void updatePingz(Pingzmx pingz) throws SQLException ;
	
	// ɾ��ƾ֤��Ϣ
	public void deletePingz(String pingzh) throws SQLException ;
	
	// ��ѯһ��ƾ֤����ϸ��Ϣ
	public Pingzmx findPingzByPingzh(String pingzh) throws SQLException ;
	
	// ��ѯһ�����ε�ƾ֤��ϸ
	public List<Pingzmx> findPingzByPich(String pich);
	
	//������ʼƾ֤�ź���ֹƾ֤�Ų�ѯ��ϸ��Ϣ
	public List<Pingzmx> findPingzByPingzh(String qispzh,String zhongzpzh) throws SQLException;
	
	public void updateZhuangt(String qispzh,String zhongzpzh,String dayfwmsg,String count) throws SQLException;
	
	//���� ɾ��ƾ֤��Ϣ
	public void batchDeletePingz(String pingzhString) throws Exception;
	/*
	 * ����ƾ֤�Ų�ѯƾ֤��ϸ��־
	 */
	public List findPingzRizByPingzh(String pingzh);
}
