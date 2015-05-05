package com.unitop.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.unitop.sysmgr.bo.Nighttaskconfig;

public interface BatchService {

	public String tongb() throws Exception;
	public String executeSqlForCall(String callName,Map map) throws Exception;
	public boolean zftongb();
	public List<Nighttaskconfig> getTaskList();
	public Nighttaskconfig getNightTaskById(String id);
	public void saveOrUpdate(Nighttaskconfig task);
	public void updateTaskStatu(String id,String result);
	public boolean exportFile(String file1,String file2,String workdate);
	
}
