package com.unitop.sysmgr.bo;

import java.util.List;
import java.util.Map;

/*
 * ��ҳʵ��
 */
public class TabsBo {
	private int fenysl = 0;//һҳչʾ�������
	private int dangqym = 0;//��ǰ��ҳ����
	//���ؽ������
	private List list =null;//�������ݿ�������
	private int counts;//�������
	
	private String sql ;//ִ�е�sql���
	
	private String paramterMapStr;
	
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List getList() {
		return list;
	}
	public String getParamterMapStr() {
		return paramterMapStr;
	}
	public void setParamterMapStr(String paramterMapStr) {
		this.paramterMapStr = paramterMapStr;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public int getFenysl() {
		return fenysl;
	}
	public void setFenysl(int fenysl) {
		this.fenysl = fenysl;
	}
	public int getDangqym() {
		return dangqym;
	}
	public void setDangqym(int dangqym) {
		this.dangqym = dangqym;
	}
}
