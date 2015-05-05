package com.sinodata.table.util;

import com.sinodata.common.SpringContent;
import com.sinodata.report.model.ReportReturn;
import com.sinodata.table.model.Table;
import com.unitop.sysmgr.bo.Clerk;
/*
 * Table自定义接口类
 */
public interface TableUserInterface{
	public ReportReturn UserRules(Clerk clerk,Table table,SpringContent springContent);
}
