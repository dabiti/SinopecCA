package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class SealcheckLog implements Serializable {

	private String account;//账号
	private String zhuzh;//账号
	private String zhangh_;//获取印鉴账号
	private String accountname;//户名
	private String checknum;//凭证号
	private String checkresult;//验印结果
	private String checkmode;//验印模式
	private String clerknum;//柜员号
	private String checkdate;//验印日期
	private String checktime;//验印时间
	private String sealinktype;//
	private String sealinknum;//
	private String money;
	private String remark;
	private String zuhgz;//验印组合规则
	private String pingzbsm;//凭证标识码
	private String qiyrq;//启用日期
	private String chuprq;//出票日期--江西添加
	private String pingzlx;//凭证类型
	private String batchinfo;//单章信息
	private String canal;
	
	
	
	//复合柜员
	private String doublesignatureclerknum;
	
	
	
	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getPingzlx() {
		return pingzlx;
	}

	public void setPingzlx(String pingzlx) {
		this.pingzlx = pingzlx;
	}

	public String getBatchinfo() {
		return batchinfo;
	}

	public void setBatchinfo(String batchinfo) {
		this.batchinfo = batchinfo;
	}

	public String getDoublesignatureclerknum() {
		return doublesignatureclerknum;
	}

	public void setDoublesignatureclerknum(String doublesignatureclerknum) {
		this.doublesignatureclerknum = doublesignatureclerknum;
	}

	public String getChuprq() {
		return chuprq;
	}

	public void setChuprq(String chuprq) {
		this.chuprq = chuprq;
	}

	public String getPingzbsm() {
		return pingzbsm;
	}

	public void setPingzbsm(String pingzbsm) {
		this.pingzbsm = pingzbsm;
	}

	public String getZhangh_() {
		return zhangh_;
	}

	public void setZhangh_(String zhangh) {
		zhangh_ = zhangh;
	}
	
	public String getZhuzh() {
		return zhuzh;
	}

	public void setZhuzh(String zhuzh) {
		this.zhuzh = zhuzh;
	}
	
	public String getZuhgz() {
		return zuhgz;
	}

	public void setZuhgz(String zuhgz) {
		this.zuhgz = zuhgz;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public String getCheckmode() {
		return checkmode;
	}

	public void setCheckmode(String checkmode) {
		this.checkmode = checkmode;
	}

	public String getChecknum() {
		return checknum;
	}

	public void setChecknum(String checknum) {
		this.checknum = checknum;
	}

	public String getCheckresult() {
		return checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public String getClerknum() {
		return clerknum;
	}

	public void setClerknum(String clerknum) {
		this.clerknum = clerknum;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getSealinktype() {
		return sealinktype;
	}

	public void setSealinktype(String sealinktype) {
		this.sealinktype = sealinktype;
	}

	public String getSealinknum() {
		return sealinknum;
	}

	public void setSealinknum(String sealinknum) {
		this.sealinknum = sealinknum;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setQiyrq(String qiyrq) {
		this.qiyrq = qiyrq;
	}

	public String getQiyrq() {
		return qiyrq;
	}
}