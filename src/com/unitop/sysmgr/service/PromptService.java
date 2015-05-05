package com.unitop.sysmgr.service;

import java.util.Map;

public interface PromptService {
	public String getPromptMsg(String msgId,Map<String,String> map);
	public Map<String,String> getPromptMap();
	public Map<String,String> getTisxxMap();
}
