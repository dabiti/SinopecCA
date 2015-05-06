package com.unitop.sysmgr.form;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unitop.sysmgr.service.QueryService;
import com.unitop.sysmgr.service.SystemMgrService;

public class CheckAcountForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String legalname;
	private String terminal_id;
	private String card_id;
	private float amount;
	private float poundage;
	private String seal_time;
	private String seal_type;
	private String card_type;
	

	
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/checkaccount.do") && !arg1.getParameter("method").equals("enter")) 
		{
//			try {
//				if ((begindate != null && begindate.trim().length() > 0) && (enddate != null && enddate.trim().length() > 0)) {
//					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//					Date begin;
//					try {
//						begin = format.parse(begindate);
//						Date end = format.parse(enddate);
//						if (end.compareTo(begin) < 1 && !begin.equals(end))
//						{
//							errors.add("error date", new ActionMessage("errors.detail", "结束日期不能小于开始日期！"));
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getPoundage() {
		return poundage;
	}

	public void setPoundage(float poundage) {
		this.poundage = poundage;
	}

	public String getSeal_time() {
		return seal_time;
	}

	public void setSeal_time(String seal_time) {
		this.seal_time = seal_time;
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

	
	
}