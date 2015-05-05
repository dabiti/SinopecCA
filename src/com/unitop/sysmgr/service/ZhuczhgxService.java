package com.unitop.sysmgr.service;
import java.sql.SQLException;
import java.util.List;
import com.unitop.sysmgr.bo.Zhanghb;

/*
 * 主从账户关系维护
 * by wp
 */
public interface ZhuczhgxService {
	
	//获取子账户信息列表
	public List getzizh(String zhangh)throws SQLException;
	//保存印鉴（新建主从关系）
	public void saveyinj(String fuyrq,String congzh,String mainaccount, Zhanghb zhanghb_c,Zhanghb zhanghb_z)throws Exception;
	//取消主从关系
	public void doquxgx(String czhangh, String zzhangh,String quxfyrq_)throws Exception;

}
