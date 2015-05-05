package com.unitop.sysmgr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.framework.util.PasswordUtil;
import com.unitop.sysmgr.dao.AjaxDao;
import com.unitop.sysmgr.service.AjaxService;

@Service("AjaxServiceImpl")
public class AjaxServiceImpl  implements AjaxService {
	
	@Resource
	private AjaxDao AjaxDao =null;
	
	public String ajax(String id,String message) throws Exception {
		return AjaxDao.ajax(PasswordUtil.decodePwd(id), message);
	}
}