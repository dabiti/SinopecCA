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
 * ������ӡ��־��ѯ
 * ��ѯҪ��
 * 		1.���ڡ����ʺš� ��ѯ
 * 		2.���ڡ����ʺ�+������ ��ѯ
 * 		3.���ڡ���Ա����ѯ
 * 
 * �������󣺡���¼��Ա��
 * 
 * Ȩ�ޣ�
 * 		��Ա �� ��Ա
 * 		��Ա �� �ʺ�[1.���ʺ� 2.���ʺ�+����]
 * 
 * Ȩ�޶��壺
 * 		1.��Ա- �ʺţ���Ա�ܶԱ����������������ʺŽ��в�����
 * 		2.��Ա - �ʺţ���Աֻ�ܶԿɲ�����Χ��ͨ��ͨ�ҵ��ʺŽ��в�����
 * 		3.��Ա - ��Ա����Ա���������Ƿ�Ͳ�����Աͬ���������Ա�ϼ���
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
			//��Ա��Ϣ��ʽת��Ϊjson
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
	//		String sequence = logForm.getSequence();//������ˮ��
			
			/*if(orgCode!= null&&!"".equals(orgCode)){
				if(!this.getSystemMgrService().CanOperDesOrg(orgCode, clerk.getOrgcode())){
					super.processBusinessException(mapping, request,"������Ч��û��Ȩ�޲鿴�û�������־��");
					return mapping.findForward("success");
				}
			}*/
			if(orgCode==null||orgCode.equals("")){
				super.processBusinessException(mapping, request,"�����Ų���Ϊ��!");
				return mapping.findForward("success");
			}
			if(!this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(), orgCode)){
				super.processBusinessException(mapping, request,"û��Ȩ�޲鿴�û�����ӡ��־!");
				return mapping.findForward("success");
			}
			
			if(clerkNum!= null&&!"".equals(clerkNum))
			{				
				boolean canOperDesClerkCode = true;//clerkManageService.CanOperDesClerkCode(clerk, clerkNum);;
				if(!canOperDesClerkCode)
				{
					super.processBusinessException(mapping, request,"������Ч��û��Ȩ��!");
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
							return this.showMessageJSP(mapping, request, "success", "������Ч,ƾ֤�Ų�����!");
						}
					}
					return super.showMessageJSPForFeny(mapping,request,tabsBo,"success");
				}
			}

			if(checkNum!=null&&!"".equals(checkNum))
			{
				if (YanyinLogService.validatePjhForDanz(checkNum))
				{
					return this.showMessageJSP(mapping, request, "success", "������Ч,ƾ֤�Ų�����!");
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
			//����			
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