package com.unitop.sysmgr.action;


import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.ZhanghxzConfig;
import com.unitop.sysmgr.bo.AccountNum;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Zhanghxzb;
import com.unitop.sysmgr.form.AccountnumForm;
import com.unitop.sysmgr.service.ZhanghbService;

@Controller("/accountnum")
public class AccountNumAction extends ExDispatchAction {
	
	@Resource
	ZhanghbService ZhanghbService;
	
	public ActionForward show(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			AccountnumForm aform = (AccountnumForm) actionForm;
			aform.setNetpointflag("");
			request.setAttribute("orgNum", "");
			request.setAttribute("AccountNum", null);
			return actionMapping.findForward("accountnum.show");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "accountnum.show");
		}
	}
	
	/*
	 * �°�NETocx �˻�����ͳ�� �鿴
	 */
	public ActionForward showForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		//��ѯ�˻�����
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		
		try {
			AccountnumForm aform = (AccountnumForm) actionForm;
			//������ػ�����
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//��Ա����������
			aform.setNetpointflag( clerk.getOrgcode());
			return actionMapping.findForward("accountnum.show.net");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "accountnum.show.net");
		}
	}
	
	/*
	 * �°�NETocx �˻�����ͳ�� ��ѯ
	 */
	public ActionForward accountnumForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//��ѯ�˻�����
			ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
			request.setAttribute("zhanghxzlist", zhanghxzList);
			
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			AccountnumForm accountnumform = (AccountnumForm) actionForm;
			String orgnum = accountnumform.getNetpointflag();
			String huobh = accountnumform.getIndustrycharacter();
			String shifbhxj = accountnumform.getRemark();
			huobh=accountnumform.getPostalcode();//�����˻�����
			if (this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(),orgnum))
			{
				AccountNum accountnum = ZhanghbService.zhanghNumber(orgnum,huobh,shifbhxj);
				request.setAttribute("AccountNum", accountnum);
			} else {
				return this.showMessageJSP(actionMapping,request,"accountnum.show.net","���������Ч����Ȩ�޲鿴�øĻ�����Ϣ!");
			}
			accountnumform.setIndustrycharacter(accountnumform.getIndustrycharacter());
			accountnumform.setNetpointflag(accountnumform.getNetpointflag());
			return actionMapping.findForward("accountnum.show.net");
		} catch (Exception e) {
			request.setAttribute("AccountNum", new AccountNum());
			return this.errrForLogAndException(e, actionMapping, request, "accountnum.show.net");
		}
		
	}
}
