package com.unitop.config;

import java.util.Map;

/*
 * BY ldd
 * ϵͳ���Ʋ�����
 */
public class SystemConfig {
	
	public static Map<String,String> systemConfigMap = null;
	private static SystemConfig config;
	
	public  void initArticleCategory(Map<String,String> systemConfigMap) throws Exception{
		if(systemConfigMap==null)
		{
			throw new Exception("systemConfigMap is null!");
		}
		this.systemConfigMap = systemConfigMap;
		System.out.println("init SystemConfig success!"+SystemConfig.systemConfigMap);
	}
	
	public static SystemConfig getInstance(){
		if (config == null)
			config = new SystemConfig();
		return config;
	}
	
	//��ȡϵͳ���Ʋ������в���ϵͳ ���룺����ID
	public String getValue(String id) {
		String systemControlParameterValue = (String) systemConfigMap.get(id);
		return systemControlParameterValue;
	}

	public String getAdminCode() {
		return this.getValue("admincode");
	}

	public String getRootCode() {
		return this.getValue("rootcode");
	}
	
	public String getSuperManager() {
		return this.getValue("supermanager");
	}
	
	public String getAdminPassword() {
		return this.getValue("adminPassword");
	}
	public String getSystemModle() {
		return this.getValue("systemmodle");
	}
	
	public String getStartPz() {
		return this.getValue("startPz");
	}
	
	public String getMaxpings() {
		return this.getValue("maxpings");
	}

	public String getRootName() {
		return this.getValue("rootname");
	}
}