package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class DayfwcsForm extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2600183977199297737L;
	//��ӡ����ID
	 private String dayfwid = "";
	//��ӡ��������
	 private String dayfwmc = "";
	//��ӡ����IP 
	 private String dayfwip = "";
	//��ӡ�˿ں�
	 private String dayfwport="";
	 //��ӡ��mac��ַ
	 private String dayfwmac = "";
	 //��ǰ��ӡ����
	 private int dangqsl ;
	 //����ӡ����
	 private int zuidsl ;
	 
	 
	public String getDayfwid() {
		return dayfwid;
	}
	public void setDayfwid(String dayfwid) {
		this.dayfwid = dayfwid;
	}
	public String getDayfwmc() {
		return dayfwmc;
	}
	public void setDayfwmc(String dayfwmc) {
		this.dayfwmc = dayfwmc;
	}
	public String getDayfwip() {
		return dayfwip;
	}
	public void setDayfwip(String dayfwip) {
		this.dayfwip = dayfwip;
	}
	public String getDayfwport() {
		return dayfwport;
	}
	public void setDayfwport(String dayfwport) {
		this.dayfwport = dayfwport;
	}
	public String getDayfwmac() {
		return dayfwmac;
	}
	public void setDayfwmac(String dayfwmac) {
		this.dayfwmac = dayfwmac;
	}
	public int getDangqsl() {
		return dangqsl;
	}
	public void setDangqsl(int dangqsl) {
		this.dangqsl = dangqsl;
	}
	public int getZuidsl() {
		return zuidsl;
	}
	public void setZuidsl(int zuidsl) {
		this.zuidsl = zuidsl;
	}

}
