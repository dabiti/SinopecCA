package com.sinodata.table.model;

import java.util.Map;

/*
 *  ���ӱ��ϵ����
 *  V3.0
 */
public class RelationTable {
	
	private String danbwhId;//�ӱ�ID
	private Map childrRlationField;//�ӱ��Ӧ�ֶμ���
	private Map childrRlationLinkedName;//�ӱ����ӵ�ַ����
	
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
