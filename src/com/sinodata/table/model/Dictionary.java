package com.sinodata.table.model;

/*
 * ���-�ֵ�ʵ��
 */
public class Dictionary {
	
	private String field;//�ֶ�����
	private String type;//�ֶ�����
	private int columnSize;//�ֶγ���
	private int isNullable;//�Ƿ��Ϊ��
	private boolean isReadOnly ;//�Ƿ�ֻ��
	
	public int getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(int isNullable) {
		this.isNullable = isNullable;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isReadOnly() {
		return isReadOnly;
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	public int getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
}
