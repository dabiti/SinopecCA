/**
 *<dl>
 *<dt><b>�๦�ܸ�Ҫ:</b></dt>
 *<dd></dd>
 *</dl>
 *@Title: PingzmxDao.java
 *@Package com.unitop.sysmgr.dao
 *@date 2012-4-23 ����06:56:42
 *
 *@copyright :Copyright @2012, IBM ETP. All right reserved.
 *��Update History��
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2012-4-23       IBM ETP      luoxiaoyi		    create
 */

package com.unitop.sysmgr.dao;
 
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateQueryException;

import com.unitop.sysmgr.bo.Pingzcszzrz;
import com.unitop.sysmgr.bo.Pingzmx;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.PingzForm;

/**
 * @ClassName:PingzmxDao
 * @Description:TODO
 * @author luoxiaoyi
 * @date 2012-4-23  ����06:56:42
 * 
 */
public interface PingzmxDao extends BaseDataResourcesInterface {

	// ��ѯ����ƾ֤����ϸ��Ϣ
	public TabsBo selectAllPingz(PingzForm pingz,int page,int max) throws HibernateQueryException;
	
	// ����ƾ֤��Ϣ
	public void savePingz(Pingzmx pingz) throws HibernateException ;
	
	//����ƾ֤������Ϣ
	public void savePinzhz(Pingzmx pingz) throws HibernateException ;
	
	// �޸�ƾ֤��Ϣ
	public void updatePingz(Pingzmx pingz) throws SQLException ;
	
	// ɾ��ƾ֤��Ϣ
	public void deletePingz(String pingzh) throws SQLException ;
	
	// ��ѯһ��ƾ֤����ϸ��Ϣ
	public Pingzmx findPingzByPingzh(String pingzh) throws HibernateQueryException ;
	
	// ��ѯһ�����ε�ƾ֤��ϸ
	public List findPingzByPich(String pich);
	
	//��ѯƾ֤������ϸ��־
	public List findPingzRizByPingzh(String pingzh);

	/**
	 * <dl>
	 * <dt><b>findPingzByPingzh���ܸ�Ҫ:������ʼƾ֤�ź���ֹƾ֤�Ų�ѯ��ϸ��Ϣ</b></dt>
	 * <dd></dd>
	 * </dl>
	 * @throws SQLException 
	*/
	public List<Pingzmx> findPingzByPingzh(String qispzh, String zhongzpzh) throws HibernateQueryException;
	
	//ƾ֤׷����־����
	public void savePingzzzrz(Pingzcszzrz Pingzcszzrz);
	
	public void updateZhuangt(String qispzh,String zhongzpzh) throws SQLException;
}
