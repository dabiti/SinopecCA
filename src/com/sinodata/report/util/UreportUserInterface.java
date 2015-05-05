package com.sinodata.report.util;

import com.sinodata.common.SpringContent;
import com.sinodata.report.model.ReportForm;
import com.sinodata.report.model.ReportResult;
import com.sinodata.report.model.ReportReturn;
import com.sinodata.table.model.Table;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;
/*
 * Ureport�Զ���ӿ���
 */
public interface UreportUserInterface {
	/*
	 * Ureport��ʼ�� Form�ӿ�
	 */
	public ReportReturn doInitForForm(Clerk clerk,ReportForm reportForm,SpringContent springContent)throws BusinessException;
	/*
	 * Ureport��ʼ�� Result�ӿ�
	 */
	public ReportReturn doInitForResult(Clerk clerk,ReportResult reportResult,SpringContent springContent)throws BusinessException;
	/*
	 * Ureport��ѯ���� �ӿ�
	 */
	public ReportReturn UserRules(Clerk clerk,Table table,SpringContent springContent)  throws BusinessException;
}
