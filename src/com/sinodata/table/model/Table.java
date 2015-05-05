package com.sinodata.table.model;

import java.util.HashMap;
import java.util.Map;
//表-实体
public class Table {
	
	private String id;
	
	private String name;
	
	private String tableName;
	
	private Map tableKeys;
	
	private Map field;
	
	private Map fieldForShow;
	
	private Map fieldForSaveOrUpdate;

	private Map fieldDefaultValue;
	
	private Map fieldValue;
	
	private Map fieldType;
	
	private Map fieldTableType;

	private Group group;
	
	private Order Order;
	
	private Where Where = new Where();
	
	private String SqlString;
	
	private Map SqlParameter = new HashMap();;
	
	private boolean save = true;
	private boolean update = true;
	private boolean delete = true;
	
	private Map validateMap;
	
	//V3.0 主从表设计
	private boolean masterTable = false;//是否主表
	private String classForUserDefined;//自定义类
	private Map relationTableMap;
	private Map hrefLinked;//是否连接字段
	private Map chiledTableForfield;//子表字段对应子表ID
	private Map dictionaryMap;//字典信息
	
	public Map getDictionaryMap() {
		return dictionaryMap;
	}

	public void setDictionaryMap(Map dictionaryMap) {
		this.dictionaryMap = dictionaryMap;
	}

	public Map getChiledTableForfield() {
		return chiledTableForfield;
	}

	public void setChiledTableForfield(Map chiledTableForfield) {
		this.chiledTableForfield = chiledTableForfield;
	}

	public Map getHrefLinked() {
		return hrefLinked;
	}

	public void setHrefLinked(Map hrefLinked) {
		this.hrefLinked = hrefLinked;
	}

	public boolean isMasterTable() {
		return masterTable;
	}

	public void setMasterTable(boolean masterTable) {
		this.masterTable = masterTable;
	}
	
	public String getClassForUserDefined() {
		return classForUserDefined;
	}

	public Map getRelationTableMap() {
		return relationTableMap;
	}

	public void setRelationTableMap(Map relationTableMap) {
		this.relationTableMap = relationTableMap;
	}


	public String isClassForUserDefined() {
		return classForUserDefined;
	}

	public void setClassForUserDefined(String classForUserDefined) {
		this.classForUserDefined = classForUserDefined;
	}

	public Map getValidateMap() {
		return validateMap;
	}

	public void setValidateMap(Map validateMap) {
		this.validateMap = validateMap;
	}

	public Map getFieldDefaultValue() {
		return fieldDefaultValue;
	}

	public void setFieldDefaultValue(Map fieldDefaultValue) {
		this.fieldDefaultValue = fieldDefaultValue;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public Map getSqlParameter() {
		return SqlParameter;
	}
	
	public Table(String SqlString ){
		this.SqlString = SqlString;
	}
	
	public void setSqlParameter(Map sqlParameter) {
		SqlParameter = sqlParameter;
	}

	public String getSqlString() {
		return SqlString;
	}

	public void setSqlString(String sqlString) {
		SqlString = sqlString;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Order getOrder() {
		return Order;
	}

	public void setOrder(Order order) {
		Order = order;
	}

	public Where getWhere() {
		return Where;
	}

	public void setWhere(Where where) {
		Where = where;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Map fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Map getFieldType() {
		return fieldType;
	}
	
	
	public Map getFieldTableType() {
		return fieldTableType;
	}

	public void setFieldTableType(Map fieldTableType) {
		this.fieldTableType = fieldTableType;
	}

	public void setFieldType(Map fieldType) {
		this.fieldType = fieldType;
	}
	public Map getTableKeys() {
		return tableKeys;
	}

	public void setTableKeys(Map tableKeys) {
		this.tableKeys = tableKeys;
	}

	public Map getField() {
		return field;
	}

	public void setField(Map field) {
		this.field = field;
	}
	
	public Map getFieldForShow() {
		return fieldForShow;
	}

	public void setFieldForShow(Map fieldForShow) {
		this.fieldForShow = fieldForShow;
	}
	
	public Map getFieldForSaveOrUpdate() {
		return fieldForSaveOrUpdate;
	}

	public void setFieldForSaveOrUpdate(Map fieldForSaveOrUpdate) {
		this.fieldForSaveOrUpdate = fieldForSaveOrUpdate;
	}

}
