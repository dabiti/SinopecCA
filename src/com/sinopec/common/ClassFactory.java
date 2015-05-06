package com.sinopec.common;

import java.util.HashMap;
import java.util.Map;
/*
 * ���������������
 * (����ģʽ+����ģʽ)
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