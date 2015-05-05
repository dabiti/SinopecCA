package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Piaojyxwjb;
import com.unitop.sysmgr.bo.PiaojyxwjbId;

public interface PiaojyxwjbDao   extends BaseDataResourcesInterface{
	
	//获取帐号下特定影像文件信息
	public Piaojyxwjb getPiaojyxwjb(PiaojyxwjbId id);
	//获取帐号下文件信息
	public List getPiaojyxwjb(String zhangh,String wenjbh);
}
