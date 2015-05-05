package com.unitop.framework.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Clerk;

/*
 * ϵͳ���Ʋ����������� ��ǩ
 */
public class HasPrivilegeForSystemConfig extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String name = "";

	public void setName(String name) {
		this.name = name;
	}

	// �жϲ��� �����1ͨ�� ������ʾ
	public int doStartTag() throws JspException {
		Clerk clerk = (Clerk) pageContext.getAttribute("clerk",PageContext.SESSION_SCOPE);
		if (clerk == null)
		{
			return (SKIP_BODY);
		}
		SystemConfig systemConfig = SystemConfig.getInstance();
		//System.out.println(name);
		String valueString = systemConfig.getValue(name);
		//System.out.println(valueString);
		try {
			if ("1".equals(valueString)) {
				return (EVAL_BODY_INCLUDE);
			} else {
				return SKIP_BODY;
			}
		} catch (Exception e) {
			return (SKIP_BODY);
		}
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

}
