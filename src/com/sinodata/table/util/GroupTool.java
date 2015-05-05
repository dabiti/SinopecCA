package com.sinodata.table.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.sinodata.table.model.Group;
//分组工具类
public class GroupTool {

	public static void appendGroup(StringBuffer SQL, Group  group){
	  Map gMap = group.getGroupValue();
	  //如果参数为空 终止
	  if(gMap.size()==0)
	  {
		  return;
	  }
	  SQL.append(" group by ");
	  Iterator it = gMap.keySet().iterator();
	  int i=0;
	  while(it.hasNext())
	  {
		 Map.Entry entry = (Map.Entry) it.next();
         String groupColumns = (String) entry.getKey();
         if(i++==0)
         {
        	 SQL.append(groupColumns);
         }
         else{
        	 SQL.append(","+groupColumns);
         }
	  }
	}
	
	//解析动态多选框
	public static String doGoupForDongtdxk(List tlist){
		String beiz="";
		for(int t = 0 ; t < tlist.size() ; t++)
		{
			Map mTemp = (Map) tlist.get(t);
			beiz += mTemp.get("key")+"="+mTemp.get("name")+"|";
		}
		return beiz;
	}
}
