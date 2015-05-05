package com.unitop.sysmgr.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.unitop.exception.BusinessException;

public class AccountPRStatDetailForm extends ActionForm {

	private static final long serialVersionUID = 7674459654182615350L;

	private String account;

	private String oldaccount;

	private String netpointflag;

	private String begindate;

	private String enddate;

	public String getNetpointflag() {
		return netpointflag;
	}

	public void setNetpointflag(String netpointflag) {
		this.netpointflag = netpointflag;
	}

	public String getOldaccount() {
		return oldaccount;
	}

	public void setOldaccount(String oldaccount) {
		this.oldaccount = oldaccount;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String string) {
		begindate = string;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String string) {
		enddate = string;
	}

	public AccountPRStatDetailForm() {
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.begindate = null;
		this.enddate = null;
		this.account = null;
		this.oldaccount = null;
		this.netpointflag = null;
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		try {
			if (arg1.getServletPath().equals("/accPRStatDetail.do")
					&& !arg1.getParameter("type").equals("view")) {
				if ("".equals(account)) {
					if ("".equals(oldaccount)
							&& "".equals(netpointflag)) {
						throw new BusinessException("请输入新账号或网点机构号和旧账号！");
					}
					if ("".equals(oldaccount)) {
						throw new BusinessException("输入网点机构号，就必须输入旧账号！");
					}
					if ("".equals(netpointflag)) {
						throw new BusinessException("输入旧账号，就必须输入网点机构号！");
					}
				}
				if ((begindate == null && begindate.trim().length() == 0)
						&& (enddate == null && enddate.trim().length() == 0)) {
					errors.add("error date", new ActionMessage("errors.detail",
							"请输入日期！"));
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date begin = format.parse(begindate);
					Date end = format.parse(enddate);
					if (end.compareTo(begin) < 1 && !begin.equals(end))
						errors.add("error date", new ActionMessage(
								"errors.detail", "结束日期不能小于开始日期!"));
					long day = (end.getTime() - begin.getTime())
							/ (24 * 60 * 60 * 1000);
					if (day > 93)
						errors.add("error date", new ActionMessage(
								"errors.detail", "日期范围不能超过3个月"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
