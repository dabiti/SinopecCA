package com.unitop.sysmgr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.Jicgncd;
import com.unitop.sysmgr.dao.BasicFunctionDao;
import com.unitop.sysmgr.service.BasicFunctionServcie;

@Service("BasicFunctionServcieImpl")
public class BasicFunctionServcieImpl implements BasicFunctionServcie {
	
	@Resource
	public BasicFunctionDao BasicFunctionDao;
	
	public Jicgncd getPost(Jicgncd jicg) {
		
		return BasicFunctionDao.getPost(jicg);
	}

	public void insert(Jicgncd jicg) {
		BasicFunctionDao.insert(jicg);
	}

	public Jicgncd select(Jicgncd jicg) {
		
		return BasicFunctionDao.select(jicg);
	}

}
