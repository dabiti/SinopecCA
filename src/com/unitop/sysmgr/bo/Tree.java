package com.unitop.sysmgr.bo;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	private String id = "";
	private String name;
	private String url =null;
	private String url_ =null;
	private String postIndex ="";
	private String isShow ="1";
	private String beiz="";
	private String classid="";
	private List list = new ArrayList();
	
	private StringBuffer hasPrivateNameString = new StringBuffer();
	
	public String getHasPrivateNameString() {
		return hasPrivateNameString.toString();
	}
	
	private int i=0;
	public void privateNameStringAppend(String hasPrivateNameString) {
		if(i==0)
			this.hasPrivateNameString.append(hasPrivateNameString);
		else
			this.hasPrivateNameString.append(" "+hasPrivateNameString);
		i++;
	}
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
//	public static List  StringToObject(String jsonString){
//		String[] jsonArray = jsonString.split("\\|");
//		List list = new ArrayList();
//		for (int i = 0; i < jsonArray.length; i++) {
//			String string = jsonArray[i];
//			String[] jsonarray = string.split("\\;");
//			Tree t_ = new Tree();
//				t_.setId(jsonarray[0]);
//				t_.setpId(jsonarray[1]);
//				t_.setName(jsonarray[2]);
//				t_.setOpen((jsonarray[3]));
//				t_.setPostIndex((jsonarray[4]));
//				t_.setUrl((jsonarray[5]));
//				t_.setChecked((jsonarray[6]));
//			list.add(t_);
//		}
//		return list;
//	}
	
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	
	public String getPostIndex() {
		return postIndex;
	}

	public void setPostIndex(String postIndex) {
		this.postIndex = postIndex;
	}
//	public String getChecked() {
//		return checked;
//	}
//
//	public void setChecked(String checked) {
//		this.checked = checked;
//	}
//
//	public String getOpen() {
//		return open;
//	}
//
//	public void setOpen(String open) {
//		this.open = open;
//	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getid() {
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
	
	public String getBeiz() {
		return beiz;
	}
	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}
	public String getUrl_() {
		return url_;
	}
	public void setUrl_(String url) {
		url_ = url;
	}

//	public String getpId() {
//		return pId;
//	}
//
//	public void setpId(String pId) {
//		this.pId = pId;
//	}
}
