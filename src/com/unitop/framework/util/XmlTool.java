package com.unitop.framework.util;

import org.xblink.XBlink;
public class XmlTool {
	
//	static{   
//		   // 注册一次即可   
//		    Class<?>[] clzs = {Clerk.class};   
//		    XBlink.registerClassesToBeUsed(clzs);   
//	}  

	//BoToXMl序列化
	public  static String boToXml(Object object,Class _class){
		Class<?>[] clzs = {_class};
		XBlink.registerClassesToBeUsed(clzs);
		return XBlink.toXml(object);  
	}
	
}
