package com.unitop.sysmgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.Tisxx;
import com.unitop.sysmgr.dao.TisxxDAO;
import com.unitop.sysmgr.service.TisxxService;

@Service("TisxxServiceImpl")
public class TisxxServiceImpl implements TisxxService {
	@Resource
	private TisxxDAO tisxxDAO;
	
	public void deleteTisxx(String msgId) {
		tisxxDAO.deleteTisxx(msgId);
	}

	public List<Tisxx> findAllTisxx() {
		return tisxxDAO.findAllTisxx();
	}

	public Tisxx findTisxx(String msgId) {
		return tisxxDAO.findTisxx(msgId);
	}

	public void insertTisxx(Tisxx tisxx) {
		tisxxDAO.insertTisxx(tisxx);
		
	}

	public void updateTisxx(Tisxx tisxx) {
		tisxxDAO.updateTisxx(tisxx);
	}


}
