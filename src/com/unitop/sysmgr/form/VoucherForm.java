package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VoucherForm extends ActionForm {

	private String pingzbs;
	private String pingzmc;
	private String yanyjb;
	private String shifqy;
	private String pingzhqz;
	private Integer meibzs;
	private String shifzk;
	private String jigh;

	public void setShifzk(String shifzk) {
		this.shifzk = shifzk;
	}

	public String getJigh() {
		return jigh;
	}

	public void setJigh(String jigh) {
		this.jigh = jigh;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1){
		super.reset(arg0, arg1);
		this.pingzbs = "";
		this.pingzmc = "";
		this.yanyjb = "";
		this.shifqy = "";
		this.pingzhqz = "";
		this.meibzs = 0;
		this.shifzk = "";
		this.jigh = "";
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1){
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/voucherManage.do?method=createVoucher") || arg1.getServletPath().equals("/voucherManage.do?method=updateVoucher")) 
		{
			
		}
		return errors;
	}

	public String getPingzbs() {
		return pingzbs;
	}

	public void setPingzbs(String pingzbs) {
		this.pingzbs = pingzbs;
	}

	public String getPingzmc() {
		return pingzmc;
	}

	public void setPingzmc(String pingzmc) {
		this.pingzmc = pingzmc;
	}

	public String getYanyjb() {
		return yanyjb;
	}

	public void setYanyjb(String yanyjb) {
		this.yanyjb = yanyjb;
	}

	public String getShifqy() {
		return shifqy;
	}

	public void setShifqy(String shifqy){
		this.shifqy = shifqy;
	}

	public String getPingzhqz() {
		return pingzhqz;
	}

	public void setPingzhqz(String pingzhqz) {
		this.pingzhqz = pingzhqz;
	}

	public Integer getMeibzs() {
		return meibzs;
	}

	public void setMeibzs(Integer meibzs) {
		this.meibzs = meibzs;
	}

	public String getShifzk() {
		return shifzk;
	}
}
