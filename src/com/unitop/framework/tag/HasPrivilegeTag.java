package com.unitop.framework.tag;

import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import com.unitop.sysmgr.bo.Clerk;
public class HasPrivilegeTag extends TagSupport {

	private static final long serialVersionUID = 7629737200527240567L;

	private String name = "";

	public void setName(String name) {
		this.name = name;
	}
	/*产品菜单控制-权限验证
	 */
	@Override
	public int doStartTag() throws JspException {
		Clerk clerk = (Clerk) pageContext.getAttribute("clerk", PageContext.SESSION_SCOPE);
		//系统管理员默认不走角色管理
		if("administrator".equals(clerk.getSysManager()))
		{
			return EVAL_BODY_INCLUDE;
		}
		Map jueMap = clerk.getJuesMap();
		try {
				String[] privilegeName = name.split(" ");
				int length =  privilegeName.length;
				for (int i = 0; i < length; i++) 
				{
					try {
						if (jueMap.get(privilegeName[i])!=null)
						{
							return EVAL_BODY_INCLUDE;
						}
					} catch (Exception e) {
						return (SKIP_BODY);
					}
				}
				return (SKIP_BODY);
		} catch (Exception e) {
			return (SKIP_BODY);
		}
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}
}
