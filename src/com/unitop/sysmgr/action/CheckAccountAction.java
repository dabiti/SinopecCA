package com.unitop.sysmgr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.AccountLogForm;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;

@Controller("/checkaccount")
public class CheckAccountAction extends ExDispatchAction {
	
	
	@Resource
	private ZhanghbService zhanghbService;
	
	
	/*
	 * ��ת����ѯҳ��
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
	 * ��ѯ
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AccountLogForm aform = (AccountLogForm) form;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String jigh =aform.getOrgcode();
		String account =aform.getAccount();
		String begindate =aform.getBegindate();
		String enddate =aform.getEnddate();
		try {
		//�¾��˺�ת��
		if (account != null &&account.length() != 17) {
			account = zhanghbService.parseTypeN(account, 17);
		}
		aform.setAccount(account);
		
		if(jigh==null||jigh.trim().equals("")){
			jigh=clerk.getOrgcode();
		}else{
			// �жϵ�ǰ��Ա�Ƿ���Ȩ�޲鿴
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), jigh);
			if (!bool) {
				return super.showMessageJSP(mapping, request,
						"checkaccount.list",
						"��û��Ȩ�޲鿴����["+jigh+"]!");
			}
		}
		TabsBo TabsBo = this.createTabsBo(request);
		QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
		queryServiceImpl.setTabsService(TabsBo);
		TabsBo tabsBo = this.getQueryService().findAccountTongbrzLog(account,jigh,begindate,enddate);
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
	
}