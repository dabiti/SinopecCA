package com.unitop.sysmgr.bo;

/*
 * 整票验印日志实体
 * BY ldd
 */
public class Zhengprz {

	private ZhengprzId id;//主键
	private String idd;
	private String ip;
	private String credencetype;
	private String clerknum;
	private String clerkorgcode;
	private String clerkname;
	private String doublesignatureclerknum;
	private String doublesignatureclerkname;
	private String checkresult;
	private String checkmode;
	private String remark;
	private String sealinkflag;
	private String zuhgz;
	//V3.0 新增
	private String chuprq;
	private String xitlx;
	private String yanybs;
	private String pingzbsm;

	public String getChuprq() {
		return chuprq;
	}
	public void setChuprq(String chuprq) {
		this.chuprq = chuprq;
	}
	public String getXitlx() {
		return xitlx;
	}
	public void setXitlx(String xitlx) {
		this.xitlx = xitlx;
	}
	public String getYanybs() {
		return yanybs;
	}
	public void setYanybs(String yanybs) {
		this.yanybs = yanybs;
	}
	public String getPingzbsm() {
		return pingzbsm;
	}
	public void setPingzbsm(String pingzbsm) {
		this.pingzbsm = pingzbsm;
	}
	public String getClerkorgcode() {
		return clerkorgcode;
	}
	public void setClerkorgcode(String clerkorgcode) {
		this.clerkorgcode = clerkorgcode;
	}
	
	public ZhengprzId getId() {
		return id;
	}
	public void setId(ZhengprzId id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCredencetype() {
		return credencetype;
	}
	public void setCredencetype(String credencetype) {
		this.credencetype = credencetype;
	}
	public String getClerknum() {
		return clerknum;
	}
	public void setClerknum(String clerknum) {
		this.clerknum = clerknum;
	}
	public String getClerkname() {
		return clerkname;
	}
	public void setClerkname(String clerkname) {
		this.clerkname = clerkname;
	}
	public String getDoublesignatureclerknum() {
		return doublesignatureclerknum;
	}
	public void setDoublesignatureclerknum(String doublesignatureclerknum) {
		this.doublesignatureclerknum = doublesignatureclerknum;
	}
	public String getDoublesignatureclerkname() {
		return doublesignatureclerkname;
	}
	public void setDoublesignatureclerkname(String doublesignatureclerkname) {
		this.doublesignatureclerkname = doublesignatureclerkname;
	}
	public String getCheckresult() {
		return checkresult;
	}
	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}
	public String getCheckmode() {
		return checkmode;
	}
	public void setCheckmode(String checkmode) {
		this.checkmode = checkmode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSealinkflag() {
		return sealinkflag;
	}
	public void setSealinkflag(String sealinkflag) {
		this.sealinkflag = sealinkflag;
	}
	public String getZuhgz() {
		return zuhgz;
	}
	public void setZuhgz(String zuhgz) {
		this.zuhgz = zuhgz;
	}
	public String getIdd() {
		return idd;
	}
	public void setIdd(String idd) {
		this.idd = idd;
	}
	
}