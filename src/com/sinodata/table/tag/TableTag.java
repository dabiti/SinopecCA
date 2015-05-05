package com.sinodata.table.tag;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sinodata.report.util.HtmlUtil;
import com.sinodata.table.model.Table;

public class TableTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	/*
	 * V3.0单表维护-主从表参数控制输出
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			Table zhubtable = (Table) pageContext.getRequest().getAttribute("zhubtable");
			if(zhubtable!=null)
			{
				out.write(HtmlUtil.getHtmlForInput("zhub_gongnid", "zhub_gongnid", "hidden", zhubtable.getId()));
			}
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
					out.write(HtmlUtil.getHtmlForInput(key, key, "hidden", value));
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