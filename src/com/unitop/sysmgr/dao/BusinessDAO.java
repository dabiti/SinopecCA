package com.unitop.sysmgr.dao;

import java.util.Map;

import com.unitop.sysmgr.bo.BusinessRule;

public interface BusinessDAO {
	public Map<String,BusinessRule> getBusinessRules();
}
