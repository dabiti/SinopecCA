package com.unitop.sysmgr.bo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.unitop.framework.util.PasswordUtil;

public class Clerk implements Serializable {

	private static final long serialVersionUID = 4249145416226891270L;

	private String code="";
	private String name="";
	private String password="";
	private String postCode="";
	private String postName="";
	private String updateDate=null;
	private String orgcode="";
	private String wdFlag="";
	private String orgname="";
	private String logDate="";
	private Integer errortime;
	private String ip;
	private String creator;
	private String postpopedom;
	private String shOrgCode;
	private String clerkstatus = "0";
	private String shOrgName;
	private String loginDate;
	private String sysManager;
	private Map juesMap = new HashMap();
	private String roleStr="";
	private String leixbs;
	private String status="1";
	private String parentorg;
	
	
	
	public String getParentorg() {
		return parentorg;
	}

	public void setParentorg(String parentorg) {
		this.parentorg = parentorg;
	}

	public String clerkMotion="";
	public String loginType="";
	
	
	
	
	public String getClerkMotion() {
		return clerkMotion;
	}

	public void setClerkMotion(String clerkMotion) {
		this.clerkMotion = clerkMotion;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLeixbs() {
		return leixbs;
	}

	public void setLeixbs(String leixbs) {
		this.leixbs = leixbs;
	}

	public String getClerkstatus() {
		return clerkstatus;
	}
	
	public void setClerkstatus(String clerkstatus) {
		this.clerkstatus = clerkstatus;
	}
	
	public String getRoleStr() {
		return roleStr;
	}
	
	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}
	
	public Map getJuesMap() {
		return juesMap;
	}

	public void setJuesMap(Map juesMap) {
		this.juesMap = juesMap;
	}

	public String getSysManager() {
		return sysManager;
	}

	public void setSysManager(String sysManager) {
		this.sysManager = sysManager;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getPostpopedom() {
		return postpopedom;
	}

	public void setPostpopedom(String postpopedom) {
		this.postpopedom = postpopedom;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public Integer getErrortime() {
		return errortime;
	}

	public void setErrortime(Integer errortime) {
		this.errortime = errortime;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		if("".equals(ip))
		{
			ip = null;
		}
		this.ip = ip;
	}

	public String getShOrgCode() {
		return shOrgCode;
	}

	public void setShOrgCode(String shOrgCode) {
		this.shOrgCode = shOrgCode;
	}

	public String getShOrgName() {
		return shOrgName;
	}

	public void setShOrgName(String shOrgName) {
		this.shOrgName = shOrgName;
	}

	public String getWdFlag() {
		return wdFlag;
	}

	public void setWdFlag(String wdFlag) {
		this.wdFlag = wdFlag;
	}

	public String getPasswordstring() {
		return PasswordUtil.decodePwd(password);
	}

	public void setPasswordstring(String passwordstring) {
		this.password = PasswordUtil.encodePwd(passwordstring);
	}

}
