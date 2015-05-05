package com.unitop.sysmgr.service;
import java.sql.SQLException;
import java.util.List;

import com.unitop.sysmgr.bo.Pingzgcb;
import com.unitop.sysmgr.bo.Pingzjgsyb;
import com.unitop.sysmgr.bo.Pingzkcsyb;
import com.unitop.sysmgr.bo.Pingzmx;

/*
 * by wp 
 */

public interface ZhongkpzService {

	//���������Ϣ�����̱����ʣ���
	public void savePingz(String qispzh,String zhongzpzh,String pingzlx,String rukrq,String rukjg,Pingzkcsyb pingz) throws Exception ;
	//�������������Ϣ(���̱����ʣ�������ʣ���)
	public String saveLybyJg(String jiglyfzr,String bens,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg)throws Exception;
	//�������������Ϣ�����̱�����ʣ���
	public String saveLybyGr(String Guiyh,String bens, Pingzgcb pingz,Pingzjgsyb pzjg,String jigh)throws Exception;
	//��������˻���Ϣ(���̱�����ʣ���)
	public void saveTogc(String guiyh,String qispzh, String zhongzpzh, Pingzgcb pingz,Pingzjgsyb pzjg)throws Exception;
	//��������˻���Ϣ(���̱����ʣ�������ʣ���)
	public void savetogc(String guiyh,String qispzh, String zhongzpzh,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg)throws Exception;
	//����������Ϣ�����̱����ʣ�������ʣ���
	public void savezf(String qispzh,String zhongzpzh,String guiyh,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg)throws Exception ;
	// ������ʼƾ֤�ź���ֹƾ֤�Ų�ѯƾ֤��Ϣ
	public List findPingzByPingzh(String qispzh, String zhongzpzh,String pingzlx) throws SQLException;
    //����ǿ�������Ϣ
	public void saveQzrk(String qispzh, String zhongzpzh, String pingzlx,String rukrq, String rukjg) throws SQLException;
	
}



