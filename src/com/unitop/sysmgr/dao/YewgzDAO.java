package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.sysmgr.bo.Yewgz;

public interface YewgzDAO   extends BaseDataResourcesInterface{
	public List<Yewgz> findAllYewgz();
	public void updateYewgz(Yewgz yewgz);
	public void insertYewgz(Yewgz yewgz);
	public void deleteYewgz(String yuansid);
	public Yewgz findYewgz(String yuansid);
}
