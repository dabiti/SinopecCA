package com.unitop.bean;

import java.util.HashMap;
import java.util.Map;

public class DataSet {
 
	private String name;

	private Map FieldNamesMap = new HashMap();

	private Map RowsMap = new HashMap();

	private int RowsKey=0;

	public void putFieldName(FieldName fieldname) {
		FieldNamesMap.put(fieldname.getName(), fieldname);
	}

	public int getRowsMapSise() {
		return this.RowsMap.size();
	}

	public void putRowsMap(Map row) {
		RowsMap.put(RowsMap.size(), row);
	}

	public Map getRowsMap() {
		Map map = (Map) this.RowsMap.get(RowsKey);
		RowsKey++;
		return map;

	}

	public String getFieldKey(String name) {
		Map row = (Map) RowsMap.get("name");
		return (String) row.get(name);
	}

	public FieldName getRowName(String name) {
		FieldName fieldname = (FieldName) RowsMap.get(RowsKey);
		return fieldname;
	}

	public boolean hasNext() {
		return RowsKey == RowsMap.size() ? false : true;
	}

	public void next() {
		if (this.hasNext())
			RowsKey++;
	}

	public void prev() {
		if (RowsKey > 0)
			RowsKey--;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getFieldNamesMap() {
		return FieldNamesMap;
	}

	public void setFieldNamesMap(Map fieldNamesMap) {
		FieldNamesMap = fieldNamesMap;
	}
}
