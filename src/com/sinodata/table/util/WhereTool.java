package com.sinodata.table.util;

import java.util.Iterator;
import java.util.Map;

import com.sinodata.table.model.SqlParameter;
import com.sinodata.table.model.Where;

//过滤条件工具类
public class WhereTool {
		
	/*
	 * 增加where 过滤条件
	 */
	public static StringBuffer appendWhere(StringBuffer SQL,Where where,Map  sqlParameter,Map FieldType ){
		int index  = sqlParameter.size();
		if(where!=null)
	    {	
	    	SQL.append(" where ");
	    	
	    	//= 过滤(执行 in语法)
	    	Iterator its = where.getWhereValue().entrySet().iterator();
			while (its.hasNext()) {
		            Map.Entry entry = (Map.Entry) its.next();
		            String whereColumns = (String) entry.getKey();
		            String whereColumnsValue = (String) entry.getValue();
		            String beizSQL=(String) where.getWhereInForSQL().get(whereColumns);
		            if(beizSQL!=null)
		            {
		            	 SQL.append(whereColumns+" in (" + beizSQL + ")   and ");
		            }else{
		            	 SQL.append(whereColumns+" = ?  and ");
		            }
		            sqlParameter.put(++index,new SqlParameter(whereColumns,FieldType.get(whereColumns).toString(),whereColumnsValue));
		    }
			
			//like 过滤
			its = where.getWhereLikeValue().entrySet().iterator();
			while (its.hasNext()) {
		            Map.Entry entry = (Map.Entry) its.next();
		            String whereColumns = (String) entry.getKey();
		            String whereColumnsValue = (String) entry.getValue();
		            SQL.append(whereColumns+" like ? and ");
		            sqlParameter.put(++index,new SqlParameter(whereColumns,FieldType.get(whereColumns).toString(),whereColumnsValue));
		    }
			
			//< 过滤
			its = where.getWhereSmaillValue().entrySet().iterator();
			while (its.hasNext()) {
		            Map.Entry entry = (Map.Entry) its.next();
		            String whereColumns = (String) entry.getKey();
		            String whereColumnsValue = (String) entry.getValue();
		            SQL.append(whereColumns+" < ? and ");
		            sqlParameter.put(++index,new SqlParameter(whereColumns,FieldType.get(whereColumns).toString(),whereColumnsValue));
		    }
			
			//<= 过滤
			its = where.getWhereSmaillEqualsValue().entrySet().iterator();
			while (its.hasNext()) {
		            Map.Entry entry = (Map.Entry) its.next();
		            String whereColumns = (String) entry.getKey();
		            String whereColumnsValue = (String) entry.getValue();
		            SQL.append(whereColumns+" <= ? and ");
		            sqlParameter.put(++index,new SqlParameter(whereColumns,FieldType.get(whereColumns).toString(),whereColumnsValue));
		    }
			
			
			//> 过滤
			its = where.getWhereBigValue().entrySet().iterator();
			while (its.hasNext()) {
		            Map.Entry entry = (Map.Entry) its.next();
		            String whereColumns = (String) entry.getKey();
		            String whereColumnsValue = (String) entry.getValue();
		            SQL.append(whereColumns+" > ? and ");
		            sqlParameter.put(++index,new SqlParameter(whereColumns,FieldType.get(whereColumns).toString(),whereColumnsValue));
		    }
			
			//>= 过滤
			its = where.getWhereBigEqualsValue().entrySet().iterator();
			while (its.hasNext()) {
		            Map.Entry entry = (Map.Entry) its.next();
		            String whereColumns = (String) entry.getKey();
		            String whereColumnsValue = (String) entry.getValue();
		            SQL.append(whereColumns+" >= ? and ");
		            sqlParameter.put(++index,new SqlParameter(whereColumns,FieldType.get(whereColumns).toString(),whereColumnsValue));
		    }
			
			SQL.append("1=1");
	    }
		return SQL;
	}
}
