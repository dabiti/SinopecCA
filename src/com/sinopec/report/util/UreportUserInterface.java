package com.sinopec.report.util;

import com.sinopec.common.SpringContent;
import com.sinopec.report.model.ReportForm;
import com.sinopec.report.model.ReportResult;
import com.sinopec.report.model.ReportReturn;
import com.sinopec.table.model.Table;
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
