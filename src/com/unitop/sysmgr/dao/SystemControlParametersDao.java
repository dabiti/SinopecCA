package com.unitop.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.unitop.sysmgr.bo.SystemControlParameter;
 
public interface SystemControlParametersDao  extends BaseDataResourcesInterface {
	
	public SystemControlParameter findSystemControlParameterById(String id);
	
	public List<SystemControlParameter> findAllSystemControlParameters();
	
	public void deleteSystemControlParameter(String id);
	
	public void updateSystemControlParameter(SystemControlParameter controlParameter);
	
	public void addSystemControlParameter(SystemControlParameter controlParameter);
	
	public Map<String, String> findSystemControlParameters();
	
	public String updateSequence(String sid);

}
