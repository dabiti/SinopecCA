package com.unitop.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xblink.XBlink;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.unitop.framework.util.JsonTool;

public class JsonTest {
	public static void main(String[] args) {
		//string to List<bean>
//		String jsonString ="[{'id':'111','name':'333','pId':'222'},{'id':'111','name':'333','pId':'222'}]";
//		List  list  = JsonTool.toSringForList(jsonString,quanxModle.class);
//		
//		//List<bean> to String
//		List list1 = new ArrayList();
//		list1.add(new quanxModle());
//		list1.add(new quanxModle());
//		System.out.println(JsonTool.toJsonForArry(list1));
		
		Map map = new HashMap();
        map.put("name", "json");
        map.put("arr", new String[] { "a", "b" });
        map.put("int", new Integer(1));
        map.put("bool", Boolean.TRUE);
        System.out.println(map);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json.toString());
        Map m = (Map) JsonTool.toBean(json.toString(),Map.class);
        System.out.println(m);
        System.out.println(XBlink.toXml(m));
        System.out.println(XBlink.fromXml(XBlink.toXml(m)));
	}
}
