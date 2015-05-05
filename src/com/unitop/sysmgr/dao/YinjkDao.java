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
   //����ӡ������Ϣ
   public void saveyinjk(Yinjk yinjk);
   //��ȡӡ������
   public KagRenw getYinjkh(String renwbs);
   //��ȡӡ������Ϣ
   public Yinjk getYinjk(String zhangh, String yinjkh, String qiyrq);
   //ɾ��ӡ������Ϣ
   public void deleteYinjk(String zhangh, String yinjkh, String qiyrq);
   //ɾ���˺��µ�ӡ������Ϣ
   public void deleteYinjk(String zhangh);
   
   public void deleteYinjk(List<String> yinjkList);
   
   //���� ͬʱ����
   public void cancleYinjk(String yinjkh);
   //�����ָ�ͬʱ�ָ�ӡ����
   public void resumeYinjk(String zhangh);
   
   
	// ͨ��ӡ������Ż�ȡӡ����
	public Yinjk getYinjkByYinjkbh(String yinjkbh) ;
	public boolean CheckYinjkbhList(List<String> yinjkbhList);
	public int countYinjkNum(Yinjk yinjk, String endYinjkh);
	public void save(Yinjk yinjk, int num);
	public void saveLog(YinjkManageLog log);
	public List<Yinjk> getMinCodeYinjk(String clerkCode, int i);
	public int countYinjkNumForDestroy(Yinjk yinjk, String endYinjkh);
	//public void newResumeYinjk(String)

}