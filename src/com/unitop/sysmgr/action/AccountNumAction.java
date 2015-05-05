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
	 * 新版NETocx 账户数量统计 查看
	 */
	public ActionForward showForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		//查询账户性质
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		
		try {
			AccountnumForm aform = (AccountnumForm) actionForm;
			//界面加载机构号
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//柜员所属机构号
			aform.setNetpointflag( clerk.getOrgcode());
			return actionMapping.findForward("accountnum.show.net");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "accountnum.show.net");
		}
	}
	
	/*
	 * 新版NETocx 账户数量统计 查询
	 */
	public ActionForward accountnumForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//查询账户性质
			ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
			request.setAttribute("zhanghxzlist", zhanghxzList);
			
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			AccountnumForm accountnumform = (AccountnumForm) actionForm;
			String orgnum = accountnumform.getNetpointflag();
			String huobh = accountnumform.getIndustrycharacter();
			String shifbhxj = accountnumform.getRemark();
			huobh=accountnumform.getPostalcode();//设置账户性质
			if (this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(),orgnum))
			{
				AccountNum accountnum = ZhanghbService.zhanghNumber(orgnum,huobh,shifbhxj);
				request.setAttribute("AccountNum", accountnum);
			} else {
				return this.showMessageJSP(actionMapping,request,"accountnum.show.net","输入机构无效或无权限查看该改机构信息!");
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
