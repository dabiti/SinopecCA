package com.unitop.framework.util;

import java.util.HashMap;
import java.util.Map;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.util.PhoneUtil;

import net.sf.json.JSONObject;

/*
 * by ldd 2013Äê03ÔÂ26ÈÕ 
 */
public class JsonSystemTool {

	/*
	 * String to JSON For Clerk
	 */
	public static String toJsonForClerk(Clerk clerk){
		Map jsonMap = new HashMap();
		jsonMap.put("guiyh",clerk.getCode());
		jsonMap.put("guiyjgh",clerk.getOrgcode());
		jsonMap.put("guiyqx",clerk.getRoleStr());
		JSONObject json = JSONObject.fromObject(jsonMap);
		return json.toString().replace("\"", "\\\\\"");
	}
	
	public static String toJsonForClerkForZhang(Clerk clerk){
		Map jsonMap = new HashMap();
		jsonMap.put("guiyh",clerk.getCode());
		jsonMap.put("guiyjgh",clerk.getOrgcode());
		jsonMap.put("guiyqx",clerk.getRoleStr());
		JSONObject json = JSONObject.fromObject(jsonMap);
		return json.toString().replace("\"", "\\\"");
	}
	
	public static String toJspnForAccountLinkManInfo(Zhanghb zhanghb,String retcode,String retinfo){
		Map jsonMap=new HashMap();
		String dianh=PhoneUtil.makePhoneToHole(zhanghb.getLianxrqh(),zhanghb.getDianh(),zhanghb.getLianxrfjh());
		String dianh2=PhoneUtil.makePhoneToHole(zhanghb.getLianxrqh2(),zhanghb.getDianh2(),zhanghb.getLianxrfjh2());
		String dianh3=PhoneUtil.makePhoneToHole(zhanghb.getFuzrqh(),zhanghb.getFuzrdh(),zhanghb.getFuzrfjh());
		String dianh4=PhoneUtil.makePhoneToHole(zhanghb.getFuzrqh2(),zhanghb.getFuzrdh2(),zhanghb.getFuzrfjh2());
		jsonMap.put("accno", zhanghb.getZhangh()==null?"":zhanghb.getZhangh());
		jsonMap.put("caiwlxr", zhanghb.getLianxr()==null?"":zhanghb.getLianxr());
		jsonMap.put("caiwlxrdh", dianh==null?"":dianh);
		jsonMap.put("caiwfzr", zhanghb.getFuzr()==null?"":zhanghb.getFuzr());
		jsonMap.put("caiwfzrdh", dianh3==null?"":dianh3);
		jsonMap.put("caiwlxr2", zhanghb.getLianxr2()==null?"":zhanghb.getLianxr2());
		jsonMap.put("caiwlxrdh2", dianh2==null?"":dianh2);
		jsonMap.put("caiwfzr2", zhanghb.getFuzr2()==null?"":zhanghb.getFuzr2());
		jsonMap.put("caiwfzrdh2", dianh4==null?"":dianh4);
		jsonMap.put("shifdh", zhanghb.getShifdh()==null?"":zhanghb.getShifdh());
		jsonMap.put("retcode", retcode==null?"":retcode);
		jsonMap.put("retinfo", retinfo==null?"":retinfo);
		JSONObject json = JSONObject.fromObject(jsonMap);
		return json.toString();
	}
	

}
