package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Tisxx;
import com.unitop.sysmgr.bo.Yewgz;

public interface TisxxDAO   extends BaseDataResourcesInterface {
	public List<Tisxx> findAllTisxx();
	public void updateTisxx(Tisxx tisxx);
	public void insertTisxx(Tisxx tisxx);
	public void deleteTisxx(String msgId);
	public Tisxx findTisxx(String msgId);
}
