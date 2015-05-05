package com.unitop.sysmgr.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.BusinessRule;
import com.unitop.sysmgr.dao.BusinessDAO;
import com.unitop.sysmgr.service.BusinessService;
/**
 * 调用BusinessDAO获得业务规则信息，并将style解析成Map集合
 * 
 * @author Owner
 *
 */
@Service("BusinessServiceImpl")
public class BusinessServiceImpl implements BusinessService {
	@Resource
	private BusinessDAO businessDao;
	private static Map<String, BusinessRule> rulesmap= null;
	
	public Map<String, BusinessRule> getBusinessRules() {
		if(rulesmap ==null){
			rulesmap = businessDao.getBusinessRules();
		}
		return rulesmap;
	}
	
	public Map<String, BusinessRule> getYewgzRules() {
		rulesmap = businessDao.getBusinessRules();
		return rulesmap;
	}
}
