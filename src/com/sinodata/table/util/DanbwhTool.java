package com.sinodata.table.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.sinodata.table.model.RelationTable;
import com.sinodata.table.model.Table;
import com.sinodata.table.model.Where;
import com.unitop.framework.util.JsonTool;

/*
 * 服务主从表服务
 */
public class DanbwhTool {
	
	
	//生成主从表
	public static void doHrefLinked(Table zhubtable,Table zibtable,HttpServletRequest request){
		Map relationTableMap = zhubtable.getRelationTableMap();
		RelationTable RelationTable = (RelationTable) relationTableMap.get(zibtable.getId());
		
		Map ChildrRlationField = RelationTable.getChildrRlationField();
		
		Where where = new Where();
		zibtable.setWhere(where);
		
		Map zhucb_map = new HashMap();
		Iterator it = ChildrRlationField.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			String whereValue = (String) request.getParameter("key_"+key);
			where.getWhereValue().put(value, whereValue);
			zhucb_map.put("key_"+key, whereValue);
			//传值使用
			request.setAttribute("zhucb_map", zhucb_map);
		}
	}
	
	//检索服务
	public static void doSelectForjians(Table zhubtable,Table zibtable,HttpServletRequest request){
		Where where = new Where();
		zhubtable.setWhere(where);
		String jiansxx = request.getParameter("jiansxx");
		String jianslx = (String) request.getParameter("jianslx");
		if(jianslx!=null)
		{
			where.getWhereValue().put(jianslx, jiansxx);
		}
		request.setAttribute("jiansxx", jiansxx);
		request.setAttribute("jianslx", jianslx);
		if(zibtable.getId()!=null)
		DanbwhTool.doHrefLinked(zhubtable, zibtable, request);
	}
}
