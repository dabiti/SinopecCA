package com.unitop.sysmgr.dao;
import java.sql.SQLException;
import java.util.List;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjzhb;
import com.unitop.sysmgr.bo.Zhanghb;

/*
 * �����˻���ϵά��
 * by wp
 */
public interface ZhuczhgxDao extends BaseDataResourcesInterface
{
	//��ȡ���˻���Ϣ�б�
	public List getzizh(String zhangh)throws SQLException;
	//������´��˻�ӡ����Ϣ
	public void saveyinj(Yinjb yinjb)throws Exception;
	//�������ӡ�������Ϣ
	public void savezuh(Yinjzhb yinjzhb)throws Exception;
	//��������˻���Ϣ
	public void savezhanghb(Zhanghb zhanghb)throws Exception;
	//��ȡ���˻���ӡ������������
	public List getqiyrq(String mainaccount)throws Exception;
	//����ȡ�����ӹ�ϵ�洢����
	public void doquxgx(String zzhangh, String czhangh,String quxfyrq_)throws Exception;
	//��ȡ�˻�����ϵ���������
	public List getQiyrq(String congzh)throws Exception;

}
