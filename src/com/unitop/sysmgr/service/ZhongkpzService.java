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

	//保存入库信息（过程表、库存剩余表）
	public void savePingz(String qispzh,String zhongzpzh,String pingzlx,String rukrq,String rukjg,Pingzkcsyb pingz) throws Exception ;
	//保存机构领用信息(过程表、库存剩余表、机构剩余表)
	public String saveLybyJg(String jiglyfzr,String bens,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg)throws Exception;
	//保存个人领用信息（过程表、机构剩余表）
	public String saveLybyGr(String Guiyh,String bens, Pingzgcb pingz,Pingzjgsyb pzjg,String jigh)throws Exception;
	//保存个人退回信息(过程表、机构剩余表)
	public void saveTogc(String guiyh,String qispzh, String zhongzpzh, Pingzgcb pingz,Pingzjgsyb pzjg)throws Exception;
	//保存机构退回信息(过程表、库存剩余表、机构剩余表)
	public void savetogc(String guiyh,String qispzh, String zhongzpzh,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg)throws Exception;
	//保存作废信息到过程表、库存剩余表、机构剩余表
	public void savezf(String qispzh,String zhongzpzh,String guiyh,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg)throws Exception ;
	// 根据起始凭证号和终止凭证号查询凭证信息
	public List findPingzByPingzh(String qispzh, String zhongzpzh,String pingzlx) throws SQLException;
    //保存强制入库信息
	public void saveQzrk(String qispzh, String zhongzpzh, String pingzlx,String rukrq, String rukjg) throws SQLException;
	
}



