package com.unitop.sysmgr.service;
import java.sql.SQLException;
import java.util.List;
import com.unitop.sysmgr.bo.Zhanghb;

/*
 * �����˻���ϵά��
 * by wp
 */
public interface ZhuczhgxService {
	
	//��ȡ���˻���Ϣ�б�
	public List getzizh(String zhangh)throws SQLException;
	//����ӡ�����½����ӹ�ϵ��
	public void saveyinj(String fuyrq,String congzh,String mainaccount, Zhanghb zhanghb_c,Zhanghb zhanghb_z)throws Exception;
	//ȡ�����ӹ�ϵ
	public void doquxgx(String czhangh, String zzhangh,String quxfyrq_)throws Exception;

}
