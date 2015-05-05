package com.sinodata.table.tag;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sinodata.table.model.RelationTable;
import com.sinodata.table.model.Table;
import com.sinodata.report.util.HtmlUtil;

public class TableTagForzhiblianj extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private int name = 0;

	public void setName(int name) {
		this.name = name;
	}
	
	/*
	 * V3.0单表维护-主从表 子表连接展示 现在名称
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			Table table = (Table) pageContext.getRequest().getAttribute("table");
			Map relationTableMap =  table.getRelationTableMap();
			
			if(relationTableMap!=null)
			{
				Iterator it = relationTableMap.entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry entry = (Map.Entry) it.next();
					String key = (String) entry.getKey();
					RelationTable relationTable = (RelationTable) entry.getValue();
					Map ChildrRlationLinkedName = relationTable.getChildrRlationLinkedName();
					Iterator its = ChildrRlationLinkedName.entrySet().iterator();
					while(its.hasNext()){
						Map.Entry entrys = (Map.Entry) its.next();
						String keys = (String) entrys.getKey();
						String value = (String) entrys.getValue();
						out.write(HtmlUtil.getHtmlForZhibLinked(name,keys,value)+" ");
					}
					
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