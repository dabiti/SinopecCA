package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.unitop.config.SystemConfig;

public class OrgForm extends ActionForm {

	private static final long serialVersionUID = 8987485652022432594L;

	private String code;

	private String name;

	private String parentcode;

	private String parentname;
	
	private String parentwdflag;

	private String paymentCode;

	private String newcode;
	
	private String newname;

	private String wdflag;
	
	private FormFile file;
	
	private String jigjb;
	private String leixbs;



	
	public String getLeixbs() {
		return leixbs;
	}

	public void setLeixbs(String leixbs) {
		this.leixbs = leixbs;
	}

	public String getJigjb() {
		return jigjb;
	}

	public void setJigjb(String jigjb) {
		this.jigjb = jigjb;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getNewcode() {
		return newcode;
	}

	public void setNewcode(String newcode) {
		this.newcode = newcode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String string) {
		code = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		name = string;
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String string) {
		parentcode = string;
	}

	public OrgForm() {
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		code = null;
		name = null;
		parentcode = null;
		parentname = null;
		paymentCode = null;
		newcode = null;
		wdflag = "0";
		leixbs="1";
		this.parentwdflag = "0";
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		SystemConfig systemConfig = SystemConfig.getInstance();
		if (arg1.getServletPath().equals("/orgManage.do?method=createOrg")
				|| arg1.getServletPath().equals("/orgManage.do?method=updateOrg")) {
			if (code == null || code.equals("")) {
				errors.add("error code", new ActionMessage("errors.required",
						"��������"));
			}
			if (!isNumber(code)) {
				errors.add("error code", new ActionMessage("errors.detail",
						"�����������Ϊ����"));
			} else if (code.length() != 4) {
				errors.add("error code", new ActionMessage("errors.detail",
						"�����������Ϊ4λ"));
			} else if((wdflag.equals("1")) && (!code.substring(2).equals(systemConfig.getRootCode())))
			{
				errors.add("error code", new ActionMessage("errors.detail",
				"һ�����д���ĺ�6λӦ��Ϊ0"));
			}
			
			if((!parentcode.equals(SystemConfig.getInstance().getRootCode())) && (!code.substring(0,2).equals(parentcode.substring(0,2))))
			{
				errors.add("error code", new ActionMessage("errors.detail",
				"������������"));
			}
			
			if (name == null || name.equals("")) {
				errors.add("error name", new ActionMessage("errors.required",
						"��������"));
			} else if (name.length() > 120) {
				errors.add("error name", new ActionMessage("errors.detail",
						"�������Ʋ��ܳ���120"));
			}
			if (!parentcode.equals(SystemConfig.getInstance().getRootCode())) {
				if (paymentCode == null || paymentCode.equals("")) {
					errors.add("error paymentCode", new ActionMessage(
							"errors.required", "����֧��ϵͳ�к�"));
				} else if (paymentCode.length() != 12) {
					errors.add("error code", new ActionMessage("errors.detail",
							"����֧��ϵͳ�кű�����12λ"));
				}
			}
		
		}
		if (arg1.getServletPath().equals("/orgManage.do?method=changeOrg")) {
			if (parentcode == null || parentcode.equals("")) {
				errors.add("error code", new ActionMessage("errors.required",
						"�ϼ���������"));
			} else if (parentcode.length() != 8) {
				errors.add("error code", new ActionMessage("errors.detail",
						"�ϼ������������Ϊ8λ"));
			}
		}

		return errors;
	}

	private boolean isNumber(String code) {
		try {
			Integer.valueOf(code);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public String getWdflag() {
		return wdflag;
	}

	public void setWdflag(String wdflag) {
		this.wdflag = wdflag;
	}

	public String getParentwdflag() {
		return parentwdflag;
	}

	public void setParentwdflag(String parentwdflag) {
		this.parentwdflag = parentwdflag;
	}

	public String getNewname() {
		return newname;
	}

	public void setNewname(String newname) {
		this.newname = newname;
	}

}
