package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Huobb;
import com.unitop.sysmgr.bo.SystemControlParameter;

public interface SystemDao {
	//获取系统时间
	public String getSystetemNowDate();
	//添加系统控制参数
	public void addSystemControlParameter(SystemControlParameter controlParameter);
	//修改系统控制参数
	public void updateSystemControlParameter( SystemControlParameter controlParameter);
	//删除系统控制参数
	public void deleteSystemControlParameter(String id);
	//查找系统控制参数
	public List findAllSystemControlParameters();
	//依据ID查找系统控制参数
	public SystemControlParameter findSystemControlParameterById(String id);
	//获取货币号
	public List getHuobhList();
	public Huobb getHuobh(String huobbh);
	//获取系统验印类型
	public List getYanyinSystemType();
	public String getSystetemNextDate();
	
	//获取下一个凭证号
	public String getPZHSequence() ;
	public String getSystetemYestoDay();
}
