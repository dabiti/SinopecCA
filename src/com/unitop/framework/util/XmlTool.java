package com.unitop.framework.util;

import org.xblink.XBlink;
public class XmlTool {
	
//	static{   
//		   // ע��һ�μ���   
//		    Class<?>[] clzs = {Clerk.class};   
//		    XBlink.registerClassesToBeUsed(clzs);   
//	}  

	//BoToXMl���л�
	public  static String boToXml(Object object,Class _class){
		Class<?>[] clzs = {_class};
		XBlink.registerClassesToBeUsed(clzs);
		return XBlink.toXml(object);  
	}
	
}
