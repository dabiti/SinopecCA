package com.sinopec.user;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinopec.common.SpringContent;
import com.sinopec.report.model.ReportForm;
import com.sinopec.report.model.ReportResult;
import com.sinopec.report.model.ReportReturn;
import com.sinopec.report.util.UreportUserInterface;
import com.sinopec.table.model.Table;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;

/*
 * 机构通过率
 */
public class TongglForjigImpl extends UreportCommon implements
		UreportUserInterface {

	//用户查询定制
	public ReportReturn UserRules(Clerk clerk, Table table,SpringContent springContent) throws BusinessException {
		return this.doCommon(clerk, table, springContent);
	}
	
	public ReportReturn doInitForForm(Clerk clerk, ReportForm reportForm,
			SpringContent springContent) throws BusinessException {
		String yaosbs = reportForm.getYaosbs();
		String[] morz = reportForm.getMoyz();
		if("RIQFW".equals(yaosbs))
		{
			Date date = new Date();
			SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM");
			morz[0]=sformat.format(date);
			reportForm.setMoyz(morz);
		}
		return super.doInitForForm(clerk, reportForm, springContent);
	}
	
	public ReportReturn doInitForResult(Clerk clerk, ReportResult reportResult,
			SpringContent springContent) throws BusinessException {
		return super.doInitForResult(clerk, reportResult, springContent);
	}
}