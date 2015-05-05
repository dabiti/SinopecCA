package com.unitop.sysmgr.dao;
import java.sql.SQLException;
import java.util.List;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjzhb;
import com.unitop.sysmgr.bo.Zhanghb;

/*
 * 主从账户关系维护
 * by wp
 */
public interface ZhuczhgxDao extends BaseDataResourcesInterface
{
	//获取子账户信息列表
	public List getzizh(String zhangh)throws SQLException;
	//保存更新从账户印鉴信息
	public void saveyinj(Yinjb yinjb)throws Exception;
	//保存更新印鉴组合信息
	public void savezuh(Yinjzhb yinjzhb)throws Exception;
	//保存更新账户信息
	public void savezhanghb(Zhanghb zhanghb)throws Exception;
	//获取主账户各印鉴的启用日期
	public List getqiyrq(String mainaccount)throws Exception;
	//调用取消主从关系存储过程
	public void doquxgx(String zzhangh, String czhangh,String quxfyrq_)throws Exception;
	//获取账户各组合的启用日期
	public List getQiyrq(String congzh)throws Exception;

}
