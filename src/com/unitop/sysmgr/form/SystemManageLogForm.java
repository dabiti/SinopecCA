package com.unitop.sysmgr.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.unitop.framework.util.DateTool;

public class SystemManageLogForm extends ActionForm {

	private static final long serialVersionUID = 7340350415655057109L;

	private String admincode;

	private String begindate;

	private String enddate;

	public String getAdmincode() {
		return admincode;
	}

	public void setAdmincode(String string) {
		admincode = string;
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

	public SystemManageLogForm() {
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.admincode = null;
		if (!arg1.getServletPath().equals("/managelog.do"))
		{
			begindate = DateTool.getNowDayForYYYMMDD().substring(0,7)+"-01";
			enddate = DateTool.getNowDayForYYYMMDD();
		}
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/managelog.do")) {
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
			if (this.admincode == null || this.admincode.trim().length() == 0) {
				errors.add("error code", new ActionMessage("errors.required",
						"管理员代码"));
			} else {
				int index = admincode.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"管理员代码输入错误"));
			}
		}
		return errors;
	}

}
