package com.unitop.sysmgr.bo;

import java.util.List;
import java.util.Map;

/*
 * 分页实体
 */
public class TabsBo {
	private int fenysl = 0;//一页展示结果数量
	private int dangqym = 0;//当前分页数量
	//返回结果集合
	private List list =null;//基于数据库结果集合
	private int counts;//结果数量
	
	private String sql ;//执行的sql语句
	
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
