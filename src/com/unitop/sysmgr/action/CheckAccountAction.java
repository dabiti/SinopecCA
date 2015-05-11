package com.unitop.sysmgr.action;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.CheckAcountForm;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;

@Controller("/checkaccount")
public class CheckAccountAction extends ExDispatchAction {
	
	
	@Resource
	private ZhanghbService zhanghbService;
	
	
	/*
	 * 跳转至查询流水页面
	 */
	public ActionForward forQueryAccounting(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("checkaccount.list");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"checkaccount.list");
		}
	}
	
	
	
	
	
	
	
	/*
	 * 查询建行流水明细
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CheckAcountForm aform = (CheckAcountForm) form;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String legalname =aform.getLegalname();
		String terminal_id =aform.getTerminal_id();
		String card_id =aform.getCard_id();
		BigDecimal beginamount = new BigDecimal(aform.getBeginamount());
		BigDecimal endamount = new BigDecimal(aform.getEndamount());
		BigDecimal poundage = new BigDecimal(aform.getPoundage());
		String beginseal_date =aform.getBeginseal_date();
		String endseal_date =aform.getEndseal_date();
		String seal_type =aform.getSeal_type();
		String card_type =aform.getCard_type();
		try {

		//权限
		boolean bool = this.getSystemMgrService().CanOperSite(
				clerk.getTerminal_id(), terminal_id);
		if (!bool) {
			return super.showMessageJSP(mapping, request,
					"checkaccount.list",
					"您没有权限查看符合输入条件的机构!");
		}
			
		TabsBo TabsBo = this.createTabsBo(request);
		QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
		queryServiceImpl.setTabsService(TabsBo);
		TabsBo tabsBo = this.getQueryService().findCCBBills(legalname,terminal_id,card_id,seal_type,card_type,beginamount,endamount,beginseal_date,endseal_date);
		this.showTabsModel(request, tabsBo);
		request.setAttribute("jlist", tabsBo.getParamterMapStr());
		request.setAttribute("jsql", tabsBo.getSql());

		return super.showMessageJSPForFeny(mapping, request, tabsBo,
				"checkaccount.list");
	} catch (Exception e) {
		e.printStackTrace();
		return this.errrForLogAndException(e, mapping, request,
				"checkaccount.list");
	}
		
	}
	
	
	
	
	/*
	 * 跳转至查询汇总页面
	 */
	public ActionForward forQuerySumAccount(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("checkaccount.sum");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"checkaccount.sum");
		}
	}
	
	
	
	
	/*
	 * 查询汇总
	 */
	public ActionForward querySumAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CheckAcountForm aform = (CheckAcountForm) form;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		
		String terminal_id =aform.getTerminal_id();
		String beginseal_date =aform.getBeginseal_date();
		String endseal_date =aform.getEndseal_date();
		try {


		//权限
		boolean bool = this.getSystemMgrService().CanOperSite(
				clerk.getTerminal_id(), terminal_id);
		if (!bool) {
			return super.showMessageJSP(mapping, request,
					"checkaccount.sum",
					"您没有权限查看符合输入条件的机构!");
		}
			
			
			
		TabsBo TabsBo = this.createTabsBo(request);
		QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
		queryServiceImpl.setTabsService(TabsBo);
		TabsBo tabsBo = this.getQueryService().findSumAccount(terminal_id, beginseal_date, endseal_date);
		this.showTabsModel(request, tabsBo);
		request.setAttribute("jlist", tabsBo.getParamterMapStr());
		request.setAttribute("jsql", tabsBo.getSql());

		return super.showMessageJSPForFeny(mapping, request, tabsBo,
				"checkaccount.sum");
	} catch (Exception e) {
		e.printStackTrace();
		return this.errrForLogAndException(e, mapping, request,
				"checkaccount.sum");
	}
	}
	
}