package com.unitop.sysmgr.service.impl;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.sysmgr.dao.PromptDao;
import com.unitop.sysmgr.service.PromptService;
/**
 * 提示信息解析器
 * @author LiZhennan
 *	
 */
@Service("PromptServiceImpl")
public class PromptServiceImpl implements PromptService {
	@Resource
	private PromptDao promptDao;
	private static Map<String,String> promptmap = null;
	/**
	 * msgId 为提示信息的编号
	 * map为动态信息集合
	 */
	public Map<String,String> getPromptMap(){
		if(promptmap==null){
			promptmap = promptDao.getPromptMsg();
		}
		return promptmap;
	}
	public String getPromptMsg(String msgId,Map<String,String> map) {
		
		Map<String,String> promptmap = getPromptMap();
		
		String msg = promptmap.get(msgId);
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String activeMsg = map.get(key);
			String location = "\\$\\{"+key.toLowerCase()+"}";
			String loc = "${"+key.toLowerCase()+"}";
			int index = msg.indexOf(loc);
			if(index!=-1){
				msg=msg.replaceAll(location, activeMsg);
			}
		}
		return msg;
	}
	public Map<String, String> getTisxxMap() {
		promptmap = promptDao.getPromptMsg();
		return promptmap;
	}
}
