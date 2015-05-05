package com.unitop.sysmgr.dao;

/***********************************************************************
 * Module:  ChoutDao.java
 * Author:  Administrator
 * Purpose: Defines the Interface ChoutDao
 ***********************************************************************/

import com.unitop.sysmgr.bo.Chout;
import com.unitop.sysmgr.bo.ChoutId;

import java.util.*;


public interface ChoutDao extends BaseDataResourcesInterface{

	public void saveChout(Chout chout);
	
	public void deleteChout(String kagid);
	
	public Chout getElseChout(String jigh, int count);//��ȡһ�����ó���
	
	public void updateChoutState(ChoutId choutid,String state);//���ĳ����״̬

	public void updateChoutCount(ChoutId choutid, int count);

	public List<Chout> getChoutSpaceByKagId(String kagid);

	public Chout getChoutById(ChoutId choutid);

	public List<Chout> getUsedChout(String kagid); 
	
	public void releaseChoutSpace(ChoutId choutid, int count);
}