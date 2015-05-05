package com.unitop.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.unitop.sysmgr.bo.Yewgz;

public interface YewgzService {
	public List<Yewgz> findAllYewgz();
	public void updateYewgz(Yewgz yewgz);
	public void insertYewgz(Yewgz yewgz);
	public void deleteYewgz(String yuansid);
	public Yewgz findYewgz(String yuansid);

}
