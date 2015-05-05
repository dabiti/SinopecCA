package com.unitop.bean;

import java.util.List;
import java.util.Map;

import com.unitop.bean.DataSets;

public class UniDBI {
	// 交易ID
	private String funcID;
	// 控制参数(引擎对Json支持时无此控制参数)
	private List<String> controlParams ;
	// 普通参数
	private Map<String,String> Params;
	// 子交易及子交易的参数
	private DataSets dataSets = null;

	public List<String> getControlParams() {
		return controlParams;
	}
	public void setControlParams(List<String> controlParams) {
		this.controlParams = controlParams;
	}
	public DataSets getDataSets() {
		return dataSets;
	}
	public void setDataSets(DataSets dataSets) {
		this.dataSets = dataSets;
	}
	public String getFuncID() {
		return funcID;
	}
	public void setFuncID(String funcID) {
		this.funcID = funcID;
	}
	public Map<String,String> getParams() {
		return Params;
	}
	public void setParams(Map<String,String> params) {
		Params = params;
	}
	
}
