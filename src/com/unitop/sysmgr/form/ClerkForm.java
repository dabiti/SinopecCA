package com.unitop.sysmgr.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

public class ClerkForm extends ActionForm {

	private static final long serialVersionUID = -6715018294587759909L;

	public ClerkForm() {
	}

	private String code;

	private String name;

	private String password;

	private String password1;

	private String postCode;

	private String postName;

	private String updateDate;

	private String logDate;
	
	private Integer errortime;
	
	private List postlist;
	
	private String oldcode;
	
	private String orgcode;
	
	private String orgname;
	
	private FormFile file;
	
	
	/**
	 * @return the file
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public List getPostlist() {
		return postlist;
	}

	public void setPostlist(List postlist) {
		this.postlist = postlist;
	}

	public String getOldcode() {
		return oldcode;
	}

	public void setOldcode(String oldcode) {
		this.oldcode = oldcode;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		this.code = null;
		this.name = null;
		this.password = null;
		this.password1 = null;
		this.oldcode = null;
		this.orgcode = null;
		this.postCode = null;
		this.updateDate = null;
		this.orgname = null;
		this.file = null;
	}
	public void clear(){
		this.code = "";
		this.name = "";
		this.password = "";
		this.password1 = "";
		this.oldcode = "";
		this.updateDate = "";
	}
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if(arg1.getServletPath().equals("/clerkManage.do?method=createClerk") || arg1.getServletPath().equals("/clerkManage.do?method=updateClerk"))
		{
			if (code == null || code.trim().length() ==0) {
				errors.add("error code",
						new ActionMessage("errors.required","柜员代码"));
			}
			if (name == null || name.equals("")) {
				errors.add("error name",
						new ActionMessage("errors.required","柜员名称"));
			}
			if(password == null || password.trim().length() == 0)
			{
				errors.add("error password",
						new ActionMessage("errors.required","密码"));
			}
			
			else if(!password.equals(password1))
			{
				errors.add("error password",
						new ActionMessage("errors.detail","两次输入的密码不一致"));
			}
			
				
		}
		return errors;
	}

	private boolean isNumber(String password) {
		try {
			Integer.valueOf(password);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public Integer getErrortime() {
		return errortime;
	}

	public void setErrortime(Integer errortime) {
		this.errortime = errortime;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

}
