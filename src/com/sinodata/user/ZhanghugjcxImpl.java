package com.sinodata.user;

import com.sinodata.common.SpringContent;
import com.sinodata.report.model.ReportReturn;
import com.sinodata.report.util.UreportUserInterface;
import com.sinodata.table.model.Table;
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
