package com.unitop.sysmgr.service;

import java.util.Map;

import com.unitop.sysmgr.bo.BusinessRule;

public interface BusinessService {
	public Map<String,BusinessRule>getBusinessRules();
	public Map<String, BusinessRule> getYewgzRules();
}
