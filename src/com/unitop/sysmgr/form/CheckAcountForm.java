package com.unitop.sysmgr.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

	private static final long serialVersionUID = 8885944178141552462L;
	private String account;
	private String industrycharacter;
	private String begindate;
	private String enddate;
	private String managetype[];
	private String orgcode;
	
	

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String string) {
		account = string;
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

	public String[] getManagetype() {
		return managetype;
	}

	public void setManagetype(String[] string) {
		managetype = string;
	}

	public CheckAcountForm() {
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.account = null;
		this.managetype = null;
		System.out.println(arg1.getServletPath());
		if (arg1.getServletPath().equals("/accountlog.do"))
		{
			begindate = DateTool.getNowDayForYYYMMDD().substring(0,7)+"-01";
			enddate = DateTool.getNowDayForYYYMMDD();
		}
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/accountlog.do") && !arg1.getParameter("method").equals("enter")) 
		{
			try {
				if ((begindate != null && begindate.trim().length() > 0) && (enddate != null && enddate.trim().length() > 0)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date begin;
					try {
						begin = format.parse(begindate);
						Date end = format.parse(enddate);
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
		List HuobhList = getQueryService().getHuobh();
		arg1.setAttribute("HuobhList", HuobhList);
		arg1.setAttribute("industrycharacter", this.industrycharacter);
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

	public String getIndustrycharacter() {
		return industrycharacter;
	}

	public void setIndustrycharacter(String industrycharacter) {
		this.industrycharacter = industrycharacter;
	}

}