package com.sinopec.user;

import com.sinopec.common.SpringContent;
import com.sinopec.report.model.ReportReturn;
import com.sinopec.report.util.UreportUserInterface;
import com.sinopec.table.model.Table;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;

public class ZhanghugjcxImpl extends UreportCommon implements
		UreportUserInterface {

	public ReportReturn UserRules(Clerk clerk, Table table,SpringContent springContent) throws BusinessException {
		return this.doCommon(clerk, table, springContent);
	}
	public ReportReturn doInit(Clerk clerk, Table table,SpringContent springContent) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}
