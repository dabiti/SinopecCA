package com.sinodata.table.tag;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sinodata.table.model.Table;
import com.sinodata.report.util.HtmlUtil;

public class TableTagForzhuj extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	/*
	 * V3.0单表维护-主从表子表主键不能修改
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			//Map转成input 传值
			Map zhucb_map = (Map) pageContext.getRequest().getAttribute("zhucb_map");
			if(zhucb_map!=null)
			{
				Iterator it = zhucb_map.entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry entry = (Map.Entry) it.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					out.write(HtmlUtil.getHtmlForJavascript(key.substring(4), value));
				}
			}
			out.flush();
			return (SKIP_BODY);
		} catch (Exception e) {
			e.printStackTrace();
			return (SKIP_BODY);
		}
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}
}