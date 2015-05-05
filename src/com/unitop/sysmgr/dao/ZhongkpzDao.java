package com.unitop.sysmgr.dao;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import com.unitop.sysmgr.bo.Pingzgcb;
import com.unitop.sysmgr.bo.Pingzjgsyb;
import com.unitop.sysmgr.bo.Pingzkcsyb;
import com.unitop.sysmgr.bo.Pingzmx;

/*
 * by wp 
 */
public interface ZhongkpzDao extends BaseDataResourcesInterface{

	// ����ƾ֤��Ϣ�����̱�
	public void savePingz(Pingzgcb pingz) throws HibernateException ;
    //����ƾ֤��Ϣ�����ʣ���
	public void savePingz(Pingzkcsyb pingz)throws HibernateException;
	//ͨ�������š�ƾ֤���Ͳ�ѯ���ʣ����е�ƾ֤��Ϣ
	public List findPingz(String jigh,String pingzlx) throws SQLException;
	//ͨ����ʼ����ֹƾ֤�š�ƾ֤���Ͳ�ѯƾ֤��Ϣ
	public List findPingzByPingzh(String qispzh, String zhongzpzh,String pingzlx) throws SQLException;
	//ͨ��ƾ֤���͡�ƾ֤״̬��ѯƾ֤��Ϣ
	public List findPz(String pingzlx,String pingzzt) throws SQLException;
	//����������Ϣ�����̱�
	public void saveGc(Pingzgcb pzgcObj)throws HibernateException;
	//��ѯ��ȡÿ������
	public List findMbzs(String pingzlx) throws SQLException;
	//ͨ�������š�ƾ֤���Ͳ�ѯ����ʣ����е�ƾ֤��Ϣ
	public List findPingzjg(String jigh, String pingzlx);
	//����ƾ֤��Ϣ������ʣ���
	public void savePingz(Pingzjgsyb pzobj);
	//ͨ����ʼ����ֹƾ֤�š�ƾ֤���͡�״̬��ѯ���̱��еļ�¼��Ϣ�������˻أ�
	public List findPz(String qispzh, String zhongzpzh, String pingzlx,String pingzzt)throws SQLException;
	//ͨ����ʼ����ֹƾ֤�š�ƾ֤���͡�״̬��ѯ���̱��еļ�¼��Ϣ�������˻أ�
	public List findpz(String qispzh, String zhongzpzh, String pingzlx,String pingzzt)throws SQLException;
	//ͨ����ʼ����ֹƾ֤�š�ƾ֤���Ͳ�ѯ���̱��еļ�¼��Ϣ�����ϣ�
	public List findpz(String qispzh,String zhongzpzh,String pingzlx) throws SQLException;
	//ͨ��ƾ֤���͡�ƾ֤״̬�������Ų�ѯƾ֤��Ϣ
	public List findPz(String pingzlx, String pingzzt, String jigh)throws SQLException;
	//��ȡƾ֤��Ϣ
	public List findly_jig(String pingzlx, String pingzzt)throws SQLException;
	//ɾ������ʣ��������ü�¼��Ϣ
	public void deletePingzjg(Pingzjgsyb pingzJg);
	//ͨ��ƾ֤���͡�ƾ֤״̬�������Ų�ѯƾ֤��Ϣ(��������)������ظ����õ�����
	public List findPZ(String pingzlx, String pingzzt, String jigh)throws SQLException;
	//��ȡƾ֤��Ϣ(��������)������ظ����õ�����
	public List findly_jg(String pingzlx, String pingzzt)throws SQLException;
	//ɾ��ƾ֤���̱��е�ƾ֤��Ϣ
	public void deletepz(String qispzh, String zhongzpzh, String pingzlx);
	
	
	
	
	
	
	
	
	
}
