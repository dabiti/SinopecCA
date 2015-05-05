package com.unitop.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSets {

	private Map<Object, Object> map = new HashMap<Object, Object>();
	
	//���Ʋ���
	private Map<String,String> controlParamMap = new HashMap<String, String>();
	// �ӽ��ײ���
	private Map<String,List<Map<String, String>>> paramMap = new HashMap<String,List<Map<String, String>>>();

	public String putRowSet(DataSet dataSet) {
		return (String) this.map.put(dataSet.getName(), dataSet);
	}

	// ����ӽ��׺ͽ��׵Ĳ�������
	public void putParamMap(String id, List<Map<String,String>> list) {
		paramMap.put(id, list);
	}
	
	// ��ſ��Ʋ������Ͳ���ֵ
	public void putControlParamMap(String name,String value){
		controlParamMap.put(name, value);
	}
	
	public List<Map<String, String>> getParamMapValue(String id) {
		return paramMap.get(id);
	}

	public int getSize() {
		return this.map.size();
	}

	public DataSet getRowSet(String name) {
		return (DataSet) this.map.get(name);
	}

	public Map<String, List<Map<String, String>>> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, List<Map<String, String>>> paramMap) {
		this.paramMap = paramMap;
	}

	public Map<String, String> getControlParamMap() {
		return controlParamMap;
	}

	public void setControlParamMap(Map<String, String> controlParamMap) {
		this.controlParamMap = controlParamMap;
	}

	@SuppressWarnings("unchecked")
	public Map getMap() {
		return map;
	}

	@SuppressWarnings("unchecked")
	public void setMap(Map map) {
		this.map = map;
	}

}
