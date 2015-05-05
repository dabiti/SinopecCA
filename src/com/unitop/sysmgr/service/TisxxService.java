package com.unitop.sysmgr.service;

import java.util.List;

import com.unitop.sysmgr.bo.Tisxx;


public interface TisxxService {
	public List<Tisxx> findAllTisxx();
	public void updateTisxx(Tisxx tisxx);
	public void insertTisxx(Tisxx tisxx);
	public void deleteTisxx(String msgId);
	public Tisxx findTisxx(String msdId);
}
