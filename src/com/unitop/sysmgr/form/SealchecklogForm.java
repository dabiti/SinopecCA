package com.unitop.sysmgr.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.service.PromptService;
import com.unitop.util.CoreBillUtils;

public class SealchecklogForm extends ActionForm {

	private static final long serialVersionUID = 5504274923101966132L;

	private String account;

	private String oldaccount;

	private String netpointflag;

	private String checknum;

	private String clerknum;

	private String begindate;

	private String enddate;

	private String begindate1;

	private String enddate1;

	private String checkresult;

	private String checkmode;
	
	private String sequence;//流水号	
	
	private String orgCode;
	
	private String checktype;
	private String canal;
	
	
	
	

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getChecktype() {
		return checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getBegindate1() {
		return begindate1;
	}

	public void setBegindate1(String begindate1) {
		this.begindate1 = begindate1;
	}

	public String getEnddate1() {
		return enddate1;
	}

	public void setEnddate1(String enddate1) {
		this.enddate1 = enddate1;
	}

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
//		//新旧账号转换
//		if (oldaccount != null &&oldaccount.length() > 17) {
//			oldaccount = CoreBillUtils.parseTypeN(oldaccount, 17);
//		}
		this.oldaccount = oldaccount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String string) {
//		//新旧账号转换
//		if (string != null &&string.length() > 17) {
//			string = CoreBillUtils.parseTypeN(string, 17);
//		}
		this.account = string;
	}

	public String getChecknum() {
		return checknum;
	}

	public void setChecknum(String string) {
		checknum = string;
	}

	public SealchecklogForm() {
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.sequence=null;
		this.account = null;
		this.checknum = null;
		this.clerknum = null;
		this.checkmode = "全部";
		this.checkresult = "全部";
		this.oldaccount = null;
		this.netpointflag = null;
		this.orgCode=null;
		this.checktype=null;
		this.canal=null;
		if (!arg1.getServletPath().equals("/voucherchecklog.do"))
		{
			begindate = DateTool.getNowDayForYYYMMDD();
			enddate = DateTool.getThreeMonthAgoYYYMMDD();
			begindate1 = DateTool.getNowDayForYYYMMDD();
			enddate1 = DateTool.getThreeMonthAgoYYYMMDD();
			
		}
		
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		ServletContext servletContext= arg1.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		PromptService promptService = (PromptService)wac.getBean("PromptServiceImpl");
		
		if (arg1.getServletPath().equals("/voucherchecklog.do")) {
			//江西
			if(sequence == null || sequence.trim().length() == 0){				
				/*if (account == null || account.trim().length() == 0) {
					if (clerknum == null || clerknum.trim().length() == 0) {
						errors.add("error num", new ActionMessage("errors.detail",
								promptService.getPromptMsg("YYF-voucherchecklog", new HashMap())));
					}

				}*/
			}
			
			if (account != null && account.trim().length() > 0) {
				int index = account.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"账号输入错误！"));
			}
			//end
			if (checknum != null && checknum.trim().length() > 0) {
				int index = checknum.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"凭证号输入错误！"));
			}
			if (begindate == null || begindate.trim().length() == 0) {
				errors.add("error begindate", new ActionMessage(
						"errors.required", "开始日期"));
			}
			if (enddate == null || enddate.trim().length() == 0) {
				errors.add("error enddate", new ActionMessage(
						"errors.required", "截至日期"));
			}
			if ((begindate != null && begindate.trim().length() > 0) && (enddate != null && enddate.trim().length() > 0)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date begin;
				try {
					begin = format.parse(begindate);
					Date end = format.parse(enddate);
					if (end.compareTo(begin) < 1 && !begin.equals(end))
						errors.add("error date", new ActionMessage( "errors.detail", "结束日期不能小于开始日期！"));
					long day = (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000);
					if (day > 93) errors.add("error date", new ActionMessage( "errors.detail", "日期范围不能超过3个月！"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if ((arg1.getServletPath().equals("/batchsealchecklog.do"))
				|| (arg1.getServletPath().equals("/sealcheckCustom.do") || (arg1
						.getServletPath().equals("/sealcheck.do"))
						&& arg1.getParameter("method").equals("save"))) {
			/*
			if (account == null || account.trim().length() == 0) {

				if (clerknum == null || clerknum.trim().length() == 0) {
					errors.add("error num", new ActionMessage("errors.detail",
							promptService.getPromptMsg("YYF-voucherchecklog", new HashMap())));
				}

			}*/
			
			//江西begin			
			if(sequence == null || sequence.trim().length() == 0){
				/*if (account == null || account.trim().length() == 0) {
					if (clerknum == null || clerknum.trim().length() == 0) {
						errors.add("error num", new ActionMessage("errors.detail",
								promptService.getPromptMsg("YYF-voucherchecklog", new HashMap())));
					}

				}*/
			}
			//end
			if (account != null && account.trim().length() > 0) {				
				int index = account.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"账号输入错误！"));
			}
			if (checknum != null && checknum.trim().length() > 0) {				
				int index = checknum.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"凭证号输入错误！"));
			}
			if (clerknum != null && clerknum.trim().length() > 0) {				
				int index = clerknum.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"柜员代码输入错误！"));
			}
			if (begindate == null || begindate.trim().length() == 0) {				
				errors.add("error begindate", new ActionMessage(
						"errors.required", "开始日期"));
			}
			if (enddate == null || enddate.trim().length() == 0) {				
				errors.add("error enddate", new ActionMessage(
						"errors.required", "截至日期"));
			}
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
					long day = (end.getTime() - begin.getTime())
							/ (24 * 60 * 60 * 1000);
					if (day > 93)
						errors.add("error date", new ActionMessage(
								"errors.detail", "日期范围不能超过3个月！"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (arg1.getServletPath().equals("/sealchecklog.do")) {
			if(account == null ||account.trim().length()==0||checknum==null||checknum.trim().length()==0){
				errors.add("error num", new ActionMessage(
						"errors.detail", promptService.getPromptMsg("YYF-sealchecklog", new HashMap())));
			}
			if (account != null && account.trim().length() > 0) {
				int index = account.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"账号输入错误！"));
			}
			if (checknum != null && checknum.trim().length() > 0) {
				int index = checknum.indexOf("'");
				if (index != -1)
					errors.add("error code", new ActionMessage("errors.detail",
							"凭证号输入错误！"));
			}
		} else if (arg1.getServletPath().equals("/sealcheckcustom.do")) {
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return errors;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getCheckmode() {
		return checkmode;
	}

	public void setCheckmode(String checkmode) {
		this.checkmode = checkmode;
	}

	public String getCheckresult() {
		return checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	public String getClerknum() {
		return clerknum;
	}

	public void setClerknum(String clerknum) {
		this.clerknum = clerknum;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}
