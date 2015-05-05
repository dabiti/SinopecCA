package com.unitop.sysmgr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Autopasscount;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.SealchecklogForm;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.PingzService;
import com.unitop.sysmgr.service.YanyinLogService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;
/**
 * ��Ʊ��ӡ��־��ѯ
 * @author Owner
 */
@Controller("/voucherchecklog")
public class VoucherCheckAction extends ExDispatchAction {

	private final static String SUCCESS = "success";
	@Resource
	private YanyinLogService YanyinLogService;
	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	private ZhanghbService ZhanghbService;

	@Resource
	public PingzService pingzService;

	
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
 
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			
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
			String canal=logForm.getCanal();
			String checktype=logForm.getChecktype();
			request.setAttribute("checktype", checktype);
			//����
	//		String sequence=logForm.getSequence();//��ˮ��
			/*if(orgCode!= null&&!"".equals(orgCode)){
			if(!this.getSystemMgrService().CanOperDesOrg(orgCode, clerk.getOrgcode())){
				super.processBusinessException(mapping, request,"������Ч��û��Ȩ�޲鿴�û�������־!");
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
			//�����ǰ��Ա���ڻ���Ϊ�����˺ŵĿ����У�����Բ�ѯ���˻���������ӡ��־������ѯ������ȥ�������ŵ�ɸѡ
			if(account!=null&&account.trim().length()!=0){
				Zhanghb zhanghb=ZhanghbService.getZhanghb(account);
				if(zhanghb==null||zhanghb.getZhangh()==null||"".equals(zhanghb.getZhangh().trim())){
					super.processBusinessException(mapping, request,"�˻�["+account+"]������!");
					return mapping.findForward("success");
				}else if(zhanghb.getJigh().trim().equals(clerk.getOrgcode().trim())){
					orgCode="";
				}
			}
			if(clerkNum!= null && !"".equals(clerkNum))
			{			
				boolean canOperDesClerkCode = true;//clerkManageService.CanOperDesClerkCode(clerk,clerkNum);
				
				if(!canOperDesClerkCode)
				{
					super.processBusinessException(mapping, request,"������Ч��û��Ȩ��!");
					return mapping.findForward(SUCCESS);
				}else{
					
					TabsBo TabsBo = this.createTabsBo(request);
					QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
					queryServiceImpl.setTabsService(TabsBo);
					TabsBo tabsBo = this.getQueryService().pingzhengyanyinlog(null,account,clerkNum, checkMode, checkNum, checkResult, beginDate,endDate,orgCode,checktype,canal);
					request.setAttribute("jlist", tabsBo.getParamterMapStr());
					request.setAttribute("jsql", tabsBo.getSql());
					Autopasscount count=this.getQueryService().countAutopassrate(tabsBo.getSql(),tabsBo.getCounts(),tabsBo.getParamterMapStr());
					request.setAttribute("autopasscount", count);
					this.showTabsModel(request, tabsBo);
					
					logForm.setCheckmode(checkMode);
					logForm.setCheckresult(checkResult);
					if(!"".equals(checkNum)&&checkNum!=null)
					{
						if (YanyinLogService.validatePjhForZhengp(checkNum))
						{
							return this.showMessageJSP(mapping, request, "success", "������Ч,ƾ֤�Ų�����!");
						}
					}
					return super.showMessageJSPForFeny(mapping,request,tabsBo,SUCCESS);
				}
			}
			
			if(!"".equals(checkNum)&&checkNum!=null){
				if (YanyinLogService.validatePjhForZhengp(checkNum)){
					return this.showMessageJSP(mapping, request, "success", "������Ч,ƾ֤�Ų�����!");
				}
			}

			/*boolean canOperAcc = false;
			String canOperAccRetMsg = "";
			
			if (account!=null&&!account.equals(""))
			{
				CanOperAccReturn coar = this.getSystemMgrService().ProCanOperAcc(clerk.getOrgcode().toString(),account.toString());
				canOperAcc = coar.getReturnValue();
				canOperAccRetMsg = coar.getReturnMessage();
			}
			
			if (!canOperAcc && account!=null && account.trim().length()>0) 
			{
				return this.showMessageJSP(mapping,request,"success",canOperAccRetMsg+" !");
			}
			*/
			TabsBo TabsBo = this.createTabsBo(request);
			QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
			queryServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo = this.getQueryService().pingzhengyanyinlog(null,account,clerkNum, checkMode, checkNum, checkResult, beginDate,endDate,orgCode,checktype,canal);
			//List list=tabsBo.getList();
			//JSONArray jlist=JSONArray.fromObject(list);

			request.setAttribute("jlist", tabsBo.getParamterMapStr());
			request.setAttribute("jsql", tabsBo.getSql());
			logForm.setCheckmode(checkMode);
			logForm.setCheckresult(checkResult);
			Autopasscount count=this.getQueryService().countAutopassrate(tabsBo.getSql(),tabsBo.getCounts(),tabsBo.getParamterMapStr());
			request.setAttribute("autopasscount", count);
			this.showTabsModel(request, tabsBo);
			return super.showMessageJSPForFeny(mapping,request,tabsBo,SUCCESS);
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, SUCCESS);
		}
	}	
}