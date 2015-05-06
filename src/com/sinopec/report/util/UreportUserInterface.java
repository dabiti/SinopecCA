package com.sinopec.report.util;

import com.sinopec.common.SpringContent;
import com.sinopec.report.model.ReportForm;
import com.sinopec.report.model.ReportResult;
import com.sinopec.report.model.ReportReturn;
import com.sinopec.table.model.Table;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;
/*
 * Ureport自定义接口类
 */
public interface UreportUserInterface {
	/*
	 * Ureport初始化 Form接口
	 */
	public ReportReturn doInitForForm(Clerk clerk,ReportForm reportForm,SpringContent springContent)throws BusinessException;
	/*
	 * Ureport初始化 Result接口
	 */
	public ReportReturn doInitForResult(Clerk clerk,ReportResult reportResult,SpringContent springContent)throws BusinessException;
	/*
	 * Ureport查询定制 接口
	 */
	public ReportReturn UserRules(Clerk clerk,Table table,SpringContent springContent)  throws BusinessException;
}
