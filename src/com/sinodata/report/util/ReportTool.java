package com.sinodata.report.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.sinodata.report.model.ReportDictionary;
import com.sinodata.table.model.Table;
import com.unitop.sysmgr.bo.TabsBo;

/*
 * 工具类
 */
public class ReportTool {
	
	//字典转换
	public static void doTableResult(TabsBo tabsBo,Map rmap){
		List list = tabsBo.getList();
		if(list==null)list = new ArrayList();
		//分页过程中计算起始序号
		int qixh =(tabsBo.getFenysl()*(tabsBo.getDangqym()-1))+1;
		//序号依据分页实体计算得出
		for(int i = 0;i<list.size();i++)
		{
			Map map = (Map) list.get(i);
			map.put("rowid \"rowid\"",i+qixh);
			Iterator it = map.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry en =(Entry) it.next();
				String key = (String) en.getKey();
				Object value = en.getValue();
				if(rmap.get(key)==null)continue;
				ReportDictionary reportDictionary = InitDictionary.getInitDictionary(String.valueOf(rmap.get(key)));
				if(reportDictionary!=null)
				{
					map.put(key, reportDictionary.getDictionary(String.valueOf(value)));
				}
			}
		}
	}
	
	//特征分析接口
	public static void doTableWhere(Table table){
		if(table.getWhere().getWhereBigEqualsValue().size()==0)
		if(table.getWhere().getWhereBigValue().size()==0)
		if(table.getWhere().getWhereInForSQL().size()==0)
		if(table.getWhere().getWhereLikeValue().size()==0)
		if(table.getWhere().getWhereSmaillEqualsValue().size()==0)
		if(table.getWhere().getWhereSmaillValue().size()==0)
		if(table.getWhere().getWhereValue().size()==0)
		table.setSqlString(table.getSqlString()+" and 1<>1");
	}
	
}