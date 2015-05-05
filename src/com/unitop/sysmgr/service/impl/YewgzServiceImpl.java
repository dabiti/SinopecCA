package com.unitop.sysmgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.Yewgz;
import com.unitop.sysmgr.dao.YewgzDAO;
import com.unitop.sysmgr.service.YewgzService;
@Service("YewgzServiceImpl")
public class YewgzServiceImpl implements YewgzService{ 
	@Resource
	private YewgzDAO yewgzDAO;
	public List<Yewgz> findAllYewgz(){
		return yewgzDAO.findAllYewgz();
	};
	public void updateYewgz(Yewgz yewgz){
		yewgzDAO.updateYewgz(yewgz);
	};
	public void insertYewgz(Yewgz yewgz){
		yewgzDAO.insertYewgz(yewgz);
	};
	public void deleteYewgz(String yuansid){
		yewgzDAO.deleteYewgz(yuansid);
	}
	public Yewgz findYewgz(String yuansid) {
		return yewgzDAO.findYewgz(yuansid);
	}
	
}
