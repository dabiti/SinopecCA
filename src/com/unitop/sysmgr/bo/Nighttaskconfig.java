package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class Nighttaskconfig implements Serializable{

	private String id;
	private String taskname;
	private String tasktype;
	private String lastruntime;
	private String lastrunresult;
	private String timetype;
	private String timevalue;
	private String parametertype;
	private String parametervalue;
	private String taskurl;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLastruntime() {
		return lastruntime;
	}
	public void setLastruntime(String lastruntime) {
		this.lastruntime = lastruntime;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getTasktype() {
		return tasktype;
	}
	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getLastrunresult() {
		return lastrunresult;
	}
	public void setLastrunresult(String lastrunresult) {
		this.lastrunresult = lastrunresult;
	}
	public String getTimetype() {
		return timetype;
	}
	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}
	public String getTimevalue() {
		return timevalue;
	}
	public void setTimevalue(String timevalue) {
		this.timevalue = timevalue;
	}
	public String getParametertype() {
		return parametertype;
	}
	public void setParametertype(String parametertype) {
		this.parametertype = parametertype;
	}
	public String getParametervalue() {
		return parametervalue;
	}
	public void setParametervalue(String parametervalue) {
		this.parametervalue = parametervalue;
	}
	public String getTaskurl() {
		return taskurl;
	}
	public void setTaskurl(String taskurl) {
		this.taskurl = taskurl;
	}
	
	
	
	
	
	
}
