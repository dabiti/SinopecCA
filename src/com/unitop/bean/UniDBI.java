package com.unitop.bean;

import java.util.List;
import java.util.Map;

import com.unitop.bean.DataSets;

public class UniDBI {
	// ����ID
	private String funcID;
	// ���Ʋ���(�����Json֧��ʱ�޴˿��Ʋ���)
	private List<String> controlParams ;
	// ��ͨ����
	private Map<String,String> Params;
	// �ӽ��׼��ӽ��׵Ĳ���
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
