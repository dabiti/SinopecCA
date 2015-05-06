package com.sinopec.table.util;

import com.sinopec.common.SpringContent;
import com.sinopec.report.model.ReportReturn;
import com.sinopec.table.model.Table;
import com.unitop.sysmgr.bo.Clerk;
/*
 * Table自定义接口类
 */
public interface TableUserInterface{
	public ReportReturn UserRules(Clerk clerk,Table table,SpringContent springContent);
}
