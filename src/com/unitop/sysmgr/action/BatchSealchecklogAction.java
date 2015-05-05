package com.unitop.sysmgr.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.framework.util.JsonSystemTool;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.SealchecklogForm;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.YanyinLogService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;
/*
 * 单章验印日志查询
 * 查询要求
 * 		1.基于【新帐号】 查询
 * 		2.基于【旧帐号+机构】 查询
 * 		3.基于【柜员】查询
 * 
 * 操作对象：【登录柜员】
 * 
 * 权限：
 * 		柜员 → 柜员
 * 		柜员 → 帐号[1.新帐号 2.旧帐号+机构]
 * 
 * 权限定义：
 * 		1.柜员- 帐号：柜员能对本机构及下属机构帐号进行操作。
 * 		2.柜员 - 帐号：柜员只能对可操作范围的通存通兑的帐号进行操作。
 * 		3.柜员 - 柜员：柜员所属机构是否和操作柜员同级或操作柜员上级。
 * 
 */
@Controller("/batchsealchecklog")
public class BatchSealchecklogAction extends ExDispatchAction {

	@Resource
	private YanyinLogService YanyinLogService;

	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	private ZhanghbService ZhanghbService;
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {			
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//柜员信息格式转化为json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
			
			SealchecklogForm logForm = (SealchecklogForm) form;
			String account = logForm.getAccount();
			
			if (account != null&&account.length() != 17) {
				account = ZhanghbService.parseTypeN(account, 17);
			}
			logForm.setAccount(account);
			String beginDate = logForm.getBegindate();
			String endDate = logForm.getEnddate();
			String clerkNum = logForm.getClerknum();
			String checkMode = logForm.getCheckmode();
			String checkNum = logForm.getChecknum();
			String checkResult = logForm.getCheckresult();
			String orgCode=logForm.getOrgCode();
	//		String sequence = logForm.getSequence();//江西流水号
			
			/*if(orgCode!= null&&!"".equals(orgCode)){
				if(!this.getSystemMgrService().CanOperDesOrg(orgCode, clerk.getOrgcode())){
					super.processBusinessException(mapping, request,"输入无效，没有权限查看该机构的日志！");
					return mapping.findForward("success");
				}
			}*/
			if(orgCode==null||orgCode.equals("")){
				super.processBusinessException(mapping, request,"机构号不能为空!");
				return mapping.findForward("success");
			}
			if(!this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(), orgCode)){
				super.processBusinessException(mapping, request,"没有权限查看该机构验印日志!");
				return mapping.findForward("success");
			}
			
			if(clerkNum!= null&&!"".equals(clerkNum))
			{				
				boolean canOperDesClerkCode = true;//clerkManageService.CanOperDesClerkCode(clerk, clerkNum);;
				if(!canOperDesClerkCode)
				{
					super.processBusinessException(mapping, request,"输入无效，没有权限!");
					return mapping.findForward("success");
				}else {
					
					TabsBo TabsBo = this.createTabsBo(request);
					QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
					queryServiceImpl.setTabsService(TabsBo);
					TabsBo tabsBo = this.getQueryService().findSealCheckLog(null,account,clerkNum, checkMode, checkNum, checkResult, beginDate,endDate,orgCode);
					request.setAttribute("jlist", tabsBo.getParamterMapStr());
					request.setAttribute("jsql", tabsBo.getSql());
					this.showTabsModel(request, tabsBo);
					logForm.setCheckmode(checkMode);
					logForm.setCheckresult(checkResult);
					if(!"".equals(checkNum)&&checkNum!=null){
						if (YanyinLogService.validatePjhForDanz(checkNum))
						{
							return this.showMessageJSP(mapping, request, "success", "输入无效,凭证号不存在!");
						}
					}
					return super.showMessageJSPForFeny(mapping,request,tabsBo,"success");
				}
			}

			if(checkNum!=null&&!"".equals(checkNum))
			{
				if (YanyinLogService.validatePjhForDanz(checkNum))
				{
					return this.showMessageJSP(mapping, request, "success", "输入无效,凭证号不存在!");
				}
			}			
			boolean canOperAcc = false;
			String canOperAccRetMsg = "";
			if (account!=null&&!account.equals(""))
			{
				CanOperAccReturn coar = this.getSystemMgrService().ProCanOperAcc(clerk.getOrgcode(), account);
				canOperAcc = coar.getReturnValue();
				canOperAccRetMsg = coar.getReturnMessage();
			}
			
			if (!canOperAcc && account!=null && account.trim().length()>0) 
			{
				return this.showMessageJSP(mapping,request,"success",canOperAccRetMsg+" !");

			}
			TabsBo TabsBo = this.createTabsBo(request);
			QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
			queryServiceImpl.setTabsService(TabsBo);
			//江西			
			TabsBo tabsBo = this.getQueryService().findSealCheckLog(null,account,clerkNum, checkMode, checkNum, checkResult, beginDate,endDate,orgCode);
			logForm.setCheckmode(checkMode);
			logForm.setCheckresult(checkResult);
			
			//List list=tabsBo.getList();
			//JSONArray jlist=JSONArray.fromObject(list);
			request.setAttribute("jlist", tabsBo.getParamterMapStr());
			request.setAttribute("jsql", tabsBo.getSql());
			
			this.showTabsModel(request, tabsBo);
			return super.showMessageJSPForFeny(mapping,request,tabsBo,"success");
		} catch (Exception e) {
			e.getStackTrace();
			return this.errrForLogAndException(e, mapping, request, "success");
		}
	}
}