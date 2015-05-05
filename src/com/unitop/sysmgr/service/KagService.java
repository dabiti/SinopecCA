package com.unitop.sysmgr.service;

/***********************************************************************
 * Module:  KagService.java
 * Author:  Administrator
 * Purpose: Defines the Interface KagService
 ***********************************************************************/

import java.util.*;

import com.unitop.sysmgr.bo.Chout;
import com.unitop.sysmgr.bo.Kag;
import com.unitop.sysmgr.bo.KagRenw;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.form.YinjkForm;

public interface KagService {

	/** @param orgcode */
	public List<Kag> kaglist(String orgcode);

	/** @param kagid 
	 * @throws Exception */
	public void delete(String kagid) throws Exception;

	/** @param kag 
	 * @throws Exception */
	public void save(Kag kag) throws Exception;

	/** @param kag */
	public void update(Kag kag);
	
	public Kag getKagById(String kagid);

	/**
	 * @param zhangh
	 * @param yinjkbh
	 * @param count
	 * @param zuob 
	 * @throws Exception 
	 */
	public Chout divideSpace(String renwbs, String zhangh, int count, String zuob) throws Exception;

	/** @param zhangh */
	public TabsBo getTask(String zhangh, String jigh);

	public List<Org> getKagOrgs(String orgcode);

	public TabsBo getYinjk(YinjkForm yinjkForm);

	public void createKagRenw(KagRenw kagrenw);

	public void updateShifzk(String yinjkUpdateSql);

	public void closeKag(String renwbs, String renwzt, String zuob,
			String choutzt) throws Exception;

	public Map<String, String> getChoutSpaceByKagId(String kagid);

	public int getUsedChoutCount(String kagid);

	public void deleteTask(String renwbs) throws Exception;
	
    //保存印鉴卡信息
	public void saveyinjk(Yinjk yinjk);
	//通过任务标识获取印鉴卡号
	public KagRenw getYinjkh(String renwbs);
	//获取印鉴卡信息
	public Yinjk getYinjk(String zhangh, String yinjkh, String qiyrq);
}