package com.unitop.sysmgr.dao;

import java.util.*;

import com.unitop.sysmgr.bo.Kag;

/***********************************************************************
 * Module:  KagDao.java
 * Author:  Administrator
 * Purpose: Defines the Interface KagDao
 ***********************************************************************/


public interface KagDao extends BaseDataResourcesInterface {
   public List<Kag> kaglist(String orgcode);
   
   public void save(Kag kag);
   
   public void delete(String kagid);
   
   public void update(Kag kag);
   
   public Kag getKagById(String kagid); 

}