package com.unitop.framework.tag;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.service.PrivilegeService;

public class HasPrivilegeForZignTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String name = "";

	private static Map PrivilegeMap = null;
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int doStartTag() throws JspException {
		Clerk clerk = (Clerk) pageContext.getAttribute("clerk", PageContext.SESSION_SCOPE);
		
		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		PrivilegeService PrivilegeService = (PrivilegeService) wac.getBean("PrivilegeServiceImpl");
		if(PrivilegeMap==null)
		{
			PrivilegeMap = PrivilegeService.getPrivilegeMap();
		}
		Map jueMap = clerk.getJuesMap();
		//系统管理员验证逻辑
		if("administrator".equals(clerk.getSysManager()))
		{
				if(PrivilegeMap.get(name)==null)
				{
					return (SKIP_BODY);
				}else{
					return EVAL_BODY_INCLUDE;
				}
		}
		//基本柜员验证逻辑
		try {
			if (jueMap.get(name)==null)
			{
				return (SKIP_BODY);
			}else {
				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception e) {
				return (SKIP_BODY);
		}
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}
}
