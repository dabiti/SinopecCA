package com.unitop.sysmgr.dao;

/***********************************************************************
 * Module:  KagRenwDao.java
 * Author:  Administrator
 * Purpose: Defines the Interface KagRenwDao
 ***********************************************************************/

import java.util.*;

import com.unitop.sysmgr.bo.ChoutId;
import com.unitop.sysmgr.bo.KagRenw;

public interface KagRenwDao  extends BaseDataResourcesInterface{

	public List<KagRenw> getTaskByZhangh(String zhangh, String jigh);

	public void updateTaskState(String renwbs, String renwzt);

	public void updateZuob(ChoutId choutid,String renwbs);//¸üÐÂ×ø±ê

	public void createKagRenw(KagRenw kagrenw);

	public void deleteKagRenw(String renwbs);

	public KagRenw getTaskById(String renwbs);

}