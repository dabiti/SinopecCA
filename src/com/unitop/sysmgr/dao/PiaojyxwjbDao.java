package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Piaojyxwjb;
import com.unitop.sysmgr.bo.PiaojyxwjbId;

public interface PiaojyxwjbDao   extends BaseDataResourcesInterface{
	
	//��ȡ�ʺ����ض�Ӱ���ļ���Ϣ
	public Piaojyxwjb getPiaojyxwjb(PiaojyxwjbId id);
	//��ȡ�ʺ����ļ���Ϣ
	public List getPiaojyxwjb(String zhangh,String wenjbh);
}
