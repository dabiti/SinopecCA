package com.unitop.sysmgr.form;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.service.QueryService;
import com.unitop.sysmgr.service.SystemMgrService;

public class CheckAcountForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String legalname;
	private String terminal_id;
	private String card_id;
	private String amount;
	private String beginamount;
	private String endamount;
	private String poundage;
	private String beginseal_date;
	private String endseal_date;
	private String seal_date;
	private String seal_type;
	private String card_type;
	

	
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.legalname = null;
		this.terminal_id = null;
		this.card_id = null;
		this.amount = "0";
		this.beginamount = "0";
		this.endamount = "9999999999.99";
		 this.poundage = "0";
		this.seal_date = null;
		this.seal_type = null;
		this.card_type = null;
		if (arg1.getServletPath().equals("/checkaccount.do"))
		{
			beginseal_date = DateTool.getNowDayForYYYMMDD();
			endseal_date = DateTool.getThreeMonthAgoYYYMMDD();
			
		}
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/checkaccount.do") && !arg1.getParameter("method").equals("enter")) 
		{
			try {
				if ((beginseal_date != null && beginseal_date.trim().length() > 0) && (endseal_date != null && endseal_date.trim().length() > 0)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date begin;
					try {
						begin = format.parse(beginseal_date);
						Date end = format.parse(endseal_date);
						if (end.compareTo(begin) < 1 && !begin.equals(end))
						{
							errors.add("error date", new ActionMessage("errors.detail", "结束日期不能小于开始日期！"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return errors;
	}

	private SystemMgrService systemMgrService;

	private QueryService queryService;

	protected QueryService getQueryService() {
		ServletContext servletContext = getServlet().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.queryService=(QueryService)wac.getBean("queryService");
		return this.queryService;
	}

	protected SystemMgrService getSystemMgrService() {
		ServletContext servletContext = getServlet().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.systemMgrService = (SystemMgrService)wac.getBean("systemMgrService");
		return this.systemMgrService;
	}

	
	public void setSystemMgrService(SystemMgrService systemMgrService) {
		this.systemMgrService = systemMgrService;
	}

	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

	public String getLegalname() {
		return legalname;
	}

	public void setLegalname(String legalname) {
		this.legalname = legalname;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}


	public String getSeal_type() {
		return seal_type;
	}

	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	

	public String getBeginseal_date() {
		return beginseal_date;
	}

	public void setBeginseal_date(String beginseal_date) {
		this.beginseal_date = beginseal_date;
	}

	public String getEndseal_date() {
		return endseal_date;
	}

	public void setEndseal_date(String endseal_date) {
		this.endseal_date = endseal_date;
	}



	public String getSeal_date() {
		return seal_date;
	}

	public void setSeal_date(String seal_date) {
		this.seal_date = seal_date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBeginamount() {
		return beginamount;
	}

	public void setBeginamount(String beginamount) {
		this.beginamount = beginamount;
	}

	public String getEndamount() {
		return endamount;
	}

	public void setEndamount(String endamount) {
		this.endamount = endamount;
	}

	public String getPoundage() {
		return poundage;
	}

	public void setPoundage(String poundage) {
		this.poundage = poundage;
	}

	
	

	
	
}