package com.sinodata.table.util;

import java.util.Iterator;
import java.util.Map;

import com.sinodata.table.model.SqlParameter;
import com.sinodata.table.model.Table;
import com.sinodata.table.model.Where;

//�����������
public class TableTool {
	
	/*
	 * ��ȡ����SQL���
	 */
	public static void getIneertSQL(Table  table){
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into ");
		SQL.append(table.getTableName());
		SQL.append("(");
		Iterator it = table.getField().entrySet().iterator();
		while (it.hasNext())
		{
	            Map.Entry entry = (Map.Entry) it.next();
	            String columns = (String) entry.getKey();
	            SQL.append(columns+",");
		}
		String sql = SQL.substring(0, SQL.length()-1);
		SQL = new StringBuffer(sql);
		SQL.append(")");
		SQL.append(" values ");
		SQL.append("(");
		
		Map sqlParameter = table.getSqlParameter();
		int index  = sqlParameter.size();
		it = table.getField().entrySet().iterator();
		while (it.hasNext())
		{
            Map.Entry entry = (Map.Entry) it.next();
            String columns = (String) entry.getKey();
            SQL.append("?,");
            sqlParameter.put(++index,new SqlParameter(columns,table.getFieldTableType().get(columns).toString(),table.getFieldValue().get(columns).toString()));
		}
		sql = SQL.substring(0, SQL.length()-1);
		SQL = new StringBuffer(sql);
		SQL.append(")");
		table.setSqlString(SQL.toString());
	}
	
	/*
	 * ��ȡɾ��SQL���
	 */
	public static void getDeleteSQL(Table  table){
		StringBuffer SQL = new StringBuffer();
		SQL.append("delete ");
		SQL.append(table.getTableName());
		Where where = table.getWhere();
		WhereTool.appendWhere(SQL, where,table.getSqlParameter(),table.getFieldTableType());
		table.setSqlString(SQL.toString());
	}
	
	/*
	 * ��ȡ����SQL���
	 */
	public static void getUpdateSQL(Table  table){
		StringBuffer SQL = new StringBuffer();
		SQL.append("update  ");
		SQL.append(table.getTableName());
		SQL.append(" set ");
		
		Map sqlParameter = table.getSqlParameter();
		int index  = sqlParameter.size();
		Iterator it = table.getField().entrySet().iterator();
		while (it.hasNext())
		{
	            Map.Entry entry = (Map.Entry) it.next();
	            String columns = (String) entry.getKey();
	            SQL.append(columns+"=?,");
	            sqlParameter.put(++index,new SqlParameter(columns,table.getFieldTableType().get(columns).toString(),table.getFieldValue().get(columns).toString()));
		}
		String sql = SQL.substring(0, SQL.length()-1);
		SQL = new StringBuffer(sql);
		Where where = table.getWhere();
		WhereTool.appendWhere(SQL, where,table.getSqlParameter(),table.getFieldTableType());
		table.setSqlString(SQL.toString());
	}
	
	/*
	 * ��ȡ��ѯSQL���
	 */
	public static void getSelectSQL(Table  table){
		StringBuffer SQL = new StringBuffer();
		SQL.append("select ");
		Iterator it = table.getField().entrySet().iterator();
		while (it.hasNext()) 
		{
	            Map.Entry entry = (Map.Entry) it.next();
	            String columns = (String) entry.getKey();
	            String columnsType = (String) entry.getValue();
	            SQL.append(columns+",");
	    }
		if(SQL.lastIndexOf(",") ==-1) return;
		SQL.replace(SQL.lastIndexOf(","),SQL.lastIndexOf(",")+1,"");
	    SQL.append(" from "+table.getTableName());
	   // System.out.println("SQL : "+SQL.toString());
	    Where where = table.getWhere();
	    WhereTool.appendWhere(SQL, where,table.getSqlParameter(),table.getFieldTableType());
	   // System.out.println("SQL : "+SQL.toString());
	    table.setSqlString(SQL.toString());
	}
	
}
