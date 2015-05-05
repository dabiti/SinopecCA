package com.unitop.sysmgr.dao;

/***********************************************************************
 * Module:  YinjkDao.java
 * Author:  Administrator
 * Purpose: Defines the Interface YinjkDao
 ***********************************************************************/

import java.util.*;

import com.unitop.sysmgr.bo.KagRenw;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.YinjkManageLog;
import com.unitop.sysmgr.form.YinjkForm;

public interface YinjkDao extends BaseDataResourcesInterface{
   /** @param yinjkwzForm */
   public List<Yinjk> getYinjk(YinjkForm yinjkForm);
   public List<Yinjk> getYinjkByZhangh(String string);
   public void updateShifzk(String yinjkUpdateSql);
   public Yinjk getYinjkByZhangh(String zhangh,String yinjkh);
   public List<Yinjk> getYinjkByQiyrq(String zhangh,String qiyrq);
   //保存印鉴卡信息
   public void saveyinjk(Yinjk yinjk);
   //获取印鉴卡号
   public KagRenw getYinjkh(String renwbs);
   //获取印鉴卡信息
   public Yinjk getYinjk(String zhangh, String yinjkh, String qiyrq);
   //删除印鉴卡信息
   public void deleteYinjk(String zhangh, String yinjkh, String qiyrq);
   //删除账号下的印鉴卡信息
   public void deleteYinjk(String zhangh);
   
   public void deleteYinjk(List<String> yinjkList);
   
   //销户 同时销卡
   public void cancleYinjk(String yinjkh);
   //销户恢复同时恢复印鉴卡
   public void resumeYinjk(String zhangh);
   
   
	// 通过印鉴卡编号获取印鉴卡
	public Yinjk getYinjkByYinjkbh(String yinjkbh) ;
	public boolean CheckYinjkbhList(List<String> yinjkbhList);
	public int countYinjkNum(Yinjk yinjk, String endYinjkh);
	public void save(Yinjk yinjk, int num);
	public void saveLog(YinjkManageLog log);
	public List<Yinjk> getMinCodeYinjk(String clerkCode, int i);
	public int countYinjkNumForDestroy(Yinjk yinjk, String endYinjkh);
	//public void newResumeYinjk(String)

}