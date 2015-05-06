package com.sinopec.common;

import java.util.HashMap;
import java.util.Map;
/*
 * 类加载容器管理类
 * (工厂模式+单例模式)
 * by ldd
 */
public class ClassFactory {
	
	private static Map classMap = new HashMap();
	
	public static Object getClass(String calsspath){
		if(classMap.get(calsspath)==null)
		{
			try {
				classMap.put(calsspath, Class.forName(calsspath).newInstance());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return (Object) classMap.get(calsspath);
	}
}