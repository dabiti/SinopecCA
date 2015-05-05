package com.unitop.sysmgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.Dayfwcs;
import com.unitop.sysmgr.dao.DayfwDao;
import com.unitop.sysmgr.service.DayfwService;

@Service("DayfwServiceImpl")
public class DayfwServiceImpl extends BaseServiceImpl implements DayfwService {
	
	@Resource
	private DayfwDao dayfwDao;

	public void delete(String dayfwid) {
		dayfwDao.delete(dayfwid);
	}

	public Dayfwcs getDayfwcs(String dayfwid) {
		return dayfwDao.getDayfwcs(dayfwid);
	}

	public void save(Dayfwcs dayfwcs) {
		dayfwDao.save(dayfwcs);
	}

	public List selectAll() {
		return dayfwDao.selectAll();
	}

	public void update(Dayfwcs dayfwcs,String dayfwid) throws Exception {
	        dayfwDao.update(dayfwcs);
	}

}
