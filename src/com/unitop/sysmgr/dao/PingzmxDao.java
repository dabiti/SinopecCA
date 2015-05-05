/**
 *<dl>
 *<dt><b>类功能概要:</b></dt>
 *<dd></dd>
 *</dl>
 *@Title: PingzmxDao.java
 *@Package com.unitop.sysmgr.dao
 *@date 2012-4-23 下午06:56:42
 *
 *@copyright :Copyright @2012, IBM ETP. All right reserved.
 *【Update History】
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
 * @date 2012-4-23  下午06:56:42
 * 
 */
public interface PingzmxDao extends BaseDataResourcesInterface {

	// 查询所有凭证的详细信息
	public TabsBo selectAllPingz(PingzForm pingz,int page,int max) throws HibernateQueryException;
	
	// 保存凭证信息
	public void savePingz(Pingzmx pingz) throws HibernateException ;
	
	//保存凭证汇总信息
	public void savePinzhz(Pingzmx pingz) throws HibernateException ;
	
	// 修改凭证信息
	public void updatePingz(Pingzmx pingz) throws SQLException ;
	
	// 删除凭证信息
	public void deletePingz(String pingzh) throws SQLException ;
	
	// 查询一个凭证的详细信息
	public Pingzmx findPingzByPingzh(String pingzh) throws HibernateQueryException ;
	
	// 查询一个批次的凭证明细
	public List findPingzByPich(String pich);
	
	//查询凭证出售明细日志
	public List findPingzRizByPingzh(String pingzh);

	/**
	 * <dl>
	 * <dt><b>findPingzByPingzh机能概要:根据起始凭证号和终止凭证号查询明细信息</b></dt>
	 * <dd></dd>
	 * </dl>
	 * @throws SQLException 
	*/
	public List<Pingzmx> findPingzByPingzh(String qispzh, String zhongzpzh) throws HibernateQueryException;
	
	//凭证追踪日志保存
	public void savePingzzzrz(Pingzcszzrz Pingzcszzrz);
	
	public void updateZhuangt(String qispzh,String zhongzpzh) throws SQLException;
}
