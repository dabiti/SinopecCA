package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.bo.Clerk;

public class LogTglForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String time = DateTool.toYYYYMM();;
	private String date;
	private String sealStyle;
	private String TGL="50";
	private String orgCode;
	private String sort;

	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		this.date=null;
		if(date == null)
		{
			date = DateTool.getNowDayForYYYMMDD();
		}
		this.orgCode = null;
		this.sealStyle=null;
		this.sort = null;
	}
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		Clerk clerk =(Clerk) arg1.getSession().getAttribute("clerk");
		if(this.getOrgCode()==(null))
		{
			setOrgCode(clerk.getOrgcode());
		}
		
		if (arg1.getServletPath().equals("/acountTgl.do")){
		if(("").equals(time))
			errors.add("error code", new ActionMessage("errors.detail","输入时间不能为空！"));
		if(("").equals(TGL))
			errors.add("error code", new ActionMessage("errors.detail","输入通过率不能为空！"));
		if(("").equals(orgCode))
			errors.add("error code", new ActionMessage("errors.detail","输入机构号不能为空！"));
		if(!validate(TGL))
			errors.add("error code", new ActionMessage("errors.detail","通过率输入不合法，必须为数字！"));
		}
		return errors;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getSealStyle() {
		return sealStyle;
	}
	public void setSealStyle(String sealStyle) {
		this.sealStyle = sealStyle;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getTGL() {
		return TGL;
	}
	public void setTGL(String tgl) {
		TGL = tgl;
	}
	public String getTime() {
		 return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	/*
	 * @验证字符串是否为数字格式
	 * 是:true
	 * 否:false
	 */
	private boolean validate(String test){
		try{
		if(test!=null)Integer.valueOf(test);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
