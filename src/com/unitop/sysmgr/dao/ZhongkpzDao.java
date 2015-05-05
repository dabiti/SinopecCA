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

	// 保存凭证信息到过程表
	public void savePingz(Pingzgcb pingz) throws HibernateException ;
    //保存凭证信息到库存剩余表
	public void savePingz(Pingzkcsyb pingz)throws HibernateException;
	//通过机构号、凭证类型查询库存剩余表中的凭证信息
	public List findPingz(String jigh,String pingzlx) throws SQLException;
	//通过起始、终止凭证号、凭证类型查询凭证信息
	public List findPingzByPingzh(String qispzh, String zhongzpzh,String pingzlx) throws SQLException;
	//通过凭证类型、凭证状态查询凭证信息
	public List findPz(String pingzlx,String pingzzt) throws SQLException;
	//保存领用信息到过程表
	public void saveGc(Pingzgcb pzgcObj)throws HibernateException;
	//查询获取每本张数
	public List findMbzs(String pingzlx) throws SQLException;
	//通过机构号、凭证类型查询机构剩余表中的凭证信息
	public List findPingzjg(String jigh, String pingzlx);
	//保存凭证信息到机构剩余表
	public void savePingz(Pingzjgsyb pzobj);
	//通过起始、终止凭证号、凭证类型、状态查询过程表中的记录信息（个人退回）
	public List findPz(String qispzh, String zhongzpzh, String pingzlx,String pingzzt)throws SQLException;
	//通过起始、终止凭证号、凭证类型、状态查询过程表中的记录信息（机构退回）
	public List findpz(String qispzh, String zhongzpzh, String pingzlx,String pingzzt)throws SQLException;
	//通过起始、终止凭证号、凭证类型查询过程表中的记录信息（作废）
	public List findpz(String qispzh,String zhongzpzh,String pingzlx) throws SQLException;
	//通过凭证类型、凭证状态、机构号查询凭证信息
	public List findPz(String pingzlx, String pingzzt, String jigh)throws SQLException;
	//获取凭证信息
	public List findly_jig(String pingzlx, String pingzzt)throws SQLException;
	//删除机构剩余表中无用记录信息
	public void deletePingzjg(Pingzjgsyb pingzJg);
	//通过凭证类型、凭证状态、机构号查询凭证信息(个人领用)解决可重复领用的问题
	public List findPZ(String pingzlx, String pingzzt, String jigh)throws SQLException;
	//获取凭证信息(机构领用)解决可重复领用的问题
	public List findly_jg(String pingzlx, String pingzzt)throws SQLException;
	//删除凭证过程表中的凭证信息
	public void deletepz(String qispzh, String zhongzpzh, String pingzlx);
	
	
	
	
	
	
	
	
	
}
