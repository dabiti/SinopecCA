package com.sinodata.table.model;

import java.util.Map;

/*
 *  主从表关系描述
 *  V3.0
 */
public class RelationTable {
	
	private String danbwhId;//子表ID
	private Map childrRlationField;//子表对应字段集合
	private Map childrRlationLinkedName;//子表连接地址名称
	
	public Map getChildrRlationLinkedName() {
		return childrRlationLinkedName;
	}
	public void setChildrRlationLinkedName(Map childrRlationLinkedName) {
		this.childrRlationLinkedName = childrRlationLinkedName;
	}
	public Map getChildrRlationField() {
		return childrRlationField;
	}
	public void setChildrRlationField(Map childrRlationField) {
		this.childrRlationField = childrRlationField;
	}
	public String getDanbwhId() {
		return danbwhId;
	}
	public void setDanbwhId(String danbwhId) {
		this.danbwhId = danbwhId;
	}
	
}
