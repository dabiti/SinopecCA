package com.unitop.sysmgr.service;

import java.util.List;

import com.unitop.sysmgr.bo.AccountNum;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.YinjkManageLog;
import com.unitop.sysmgr.bo.YinjkNum;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.YinjkForm;

public interface YinjkService {
	void cancleYinjk(String zhangh);
	void cancleYinjk(List<String> deleteYinjkList);
	List<Yinjk> getYinjkByZhangh(String zhangh);
	
	//印鉴卡数量统计
	public long yinjkNumber(String orgCode,String shifbhxj);
	
	//通过印鉴卡号获得印鉴卡
	public Yinjk getYinjkByYinjkbh(String yinjkbh);
	
	boolean CheckYinjkbhList(List<String> yinjkbhList);
	void saveYinjkList(List<String> yinjkbhList,Zhanghb zhangh);
	int countYinjkNum(Yinjk yinjk, String endYinjkh);
	void save(Yinjk yinjk, int num);
	void saveLog(YinjkManageLog log);
	List<Yinjk> getMinCodeYinjk(String clerkCode, int i) ;
	int countYinjkNumForDestroy(Yinjk yinjk, String endYinjkh);
	void saveYinjkList(List<Yinjk> yinjkList);
	YinjkNum selectYinjkNum(YinjkForm yinjkform);

}
