package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

/*
 * �����˻���ϵά��
 * by wp
 */
public class ZhuczhgxForm extends ActionForm {

	//�����˻���ϵά��
	private static final long serialVersionUID = 1L;
	private String zhangh;  //�˺�
	//�½������˻���ϵ
	private String congzh;//���˻�
	private String fuyrq;//��������
	private String congzhname;//���˻�����
	private String congzhState;//���˻��˻�״̬
	private String congzhVerifyState;//���˻�ӡ�����״̬
	private String youwyj;//���˻�����ӡ��
	private String mainAccount;//���˻�
	private String mainAccountName;//���˻�����
	private String mainAccountState;//���˻�״̬
	private String mainAccountVerifyState;//���˻����״̬
	private String mainYouwyj;//���˻�����ӡ��
	
	
	
	
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
