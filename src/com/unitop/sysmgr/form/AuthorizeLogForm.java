package com.unitop.sysmgr.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.unitop.framework.util.DateTool;

public class AuthorizeLogForm extends ActionForm {
	private String begindate;
	private String enddate;
	private String account;
	private String managedate;
	private String managetype;
	private String managetime;
	private String operationclerk;
	private String authorizationclerk;
	private String managecontent;
	
	
	
	public String getBegindate() {
		return begindate;
	}



	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}



	public String getEnddate() {
		return enddate;
	}



	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}



	public String getAccount() {
		return account;
	}



	public void setAccount(String account) {
		this.account = account;
	}



	public String getManagedate() {
		return managedate;
	}



	public void setManagedate(String managedate) {
		this.managedate = managedate;
	}



	public String getManagetype() {
		return managetype;
	}



	public void setManagetype(String managetype) {
		this.managetype = managetype;
	}



	public String getManagetime() {
		return managetime;
	}



	public void setManagetime(String managetime) {
		this.managetime = managetime;
	}



	public String getOperationclerk() {
		return operationclerk;
	}



	public void setOperationclerk(String operationclerk) {
		this.operationclerk = operationclerk;
	}



	public String getAuthorizationclerk() {
		return authorizationclerk;
	}



	public void setAuthorizationclerk(String authorizationclerk) {
		this.authorizationclerk = authorizationclerk;
	}



	public String getManagecontent() {
		return managecontent;
	}



	public void setManagecontent(String managecontent) {
		this.managecontent = managecontent;
	}


@Override
public void reset(ActionMapping mapping, HttpServletRequest request) {
	// TODO Auto-generated method stub
	super.reset(mapping, request);
	this.account=null;
	this.authorizationclerk=null;
	this.operationclerk=null;
	this.managecontent=null;
	this.managedate=null;
	this.managetime=null;
	this.managetype=null;
	if (!request.getServletPath().equals("/authorizelog.do"))
	{
		begindate = DateTool.getNowDayForYYYMMDD().substring(0,7)+"-01";
		enddate = DateTool.getNowDayForYYYMMDD();
	}
}
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/authorizelog.do")) {
			if ((begindate != null && begindate.trim().length() > 0)
					&& (enddate != null && enddate.trim().length() > 0)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date begin;
				try {
					begin = format.parse(begindate);
					Date end = format.parse(enddate);
					if (end.compareTo(begin) < 1 && !begin.equals(end))
						errors.add("error date", new ActionMessage(
								"errors.detail", "结束日期不能小于开始日期！"));
				}catch (Exception e) {
					e.printStackTrace();
				}		
			}
			if (this.account == null || this.account.trim().length() == 0) {
				errors.add("error code", new ActionMessage("errors.required",
						"账号"));
			} else {
				int index = account.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"账号输入错误"));
			}
		}
		return errors;
	}

}
