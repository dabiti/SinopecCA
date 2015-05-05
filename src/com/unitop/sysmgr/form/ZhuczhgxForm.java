package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

/*
 * 主从账户关系维护
 * by wp
 */
public class ZhuczhgxForm extends ActionForm {

	//主从账户关系维护
	private static final long serialVersionUID = 1L;
	private String zhangh;  //账号
	//新建主从账户关系
	private String congzh;//从账户
	private String fuyrq;//复用日期
	private String congzhname;//从账户户名
	private String congzhState;//从账户账户状态
	private String congzhVerifyState;//从账户印鉴审核状态
	private String youwyj;//从账户有无印鉴
	private String mainAccount;//主账户
	private String mainAccountName;//主账户户名
	private String mainAccountState;//主账户状态
	private String mainAccountVerifyState;//主账户审核状态
	private String mainYouwyj;//主账户有无印鉴
	
	
	
	
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}
	public String getZhangh() {
		return zhangh;
	}
	public void setCongzh(String congzh) {
		this.congzh = congzh;
	}
	public String getCongzh() {
		return congzh;
	}
	public void setCongzhname(String congzhname) {
		this.congzhname = congzhname;
	}
	public String getCongzhname() {
		return congzhname;
	}
	public void setCongzhState(String congzhState) {
		this.congzhState = congzhState;
	}
	public String getCongzhState() {
		return congzhState;
	}
	public void setCongzhVerifyState(String congzhVerifyState) {
		this.congzhVerifyState = congzhVerifyState;
	}
	public String getCongzhVerifyState() {
		return congzhVerifyState;
	}
	public void setYouwyj(String youwyj) {
		this.youwyj = youwyj;
	}
	public String getYouwyj() {
		return youwyj;
	}
	public void setMainAccount(String mainAccount) {
		this.mainAccount = mainAccount;
	}
	public String getMainAccount() {
		return mainAccount;
	}
	public void setMainAccountName(String mainAccountName) {
		this.mainAccountName = mainAccountName;
	}
	public String getMainAccountName() {
		return mainAccountName;
	}
	public void setMainAccountState(String mainAccountState) {
		this.mainAccountState = mainAccountState;
	}
	public String getMainAccountState() {
		return mainAccountState;
	}
	public void setMainAccountVerifyState(String mainAccountVerifyState) {
		this.mainAccountVerifyState = mainAccountVerifyState;
	}
	public String getMainAccountVerifyState() {
		return mainAccountVerifyState;
	}
	public void setMainYouwyj(String mainYouwyj) {
		this.mainYouwyj = mainYouwyj;
	}
	public String getMainYouwyj() {
		return mainYouwyj;
	}
	public void setFuyrq(String fuyrq) {
		this.fuyrq = fuyrq;
	}
	public String getFuyrq() {
		return fuyrq;
	}

	
}
