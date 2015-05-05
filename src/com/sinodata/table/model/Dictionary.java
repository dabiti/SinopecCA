package com.sinodata.table.model;

/*
 * 库表-字典实体
 */
public class Dictionary {
	
	private String field;//字段名称
	private String type;//字段类型
	private int columnSize;//字段长度
	private int isNullable;//是否可为空
	private boolean isReadOnly ;//是否只读
	
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
