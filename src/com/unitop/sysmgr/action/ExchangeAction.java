package com.unitop.sysmgr.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.service.OrgService;
@Controller("/exchange")
public class ExchangeAction extends ExDispatchAction {
	@Resource
	private OrgService OrgService;
	
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			List list = OrgService.getOrgListForTCTD(clerk.getWdFlag(),clerk.getShOrgCode());
			request.setAttribute("tctdList", list);
			return actionMapping.findForward("exchange.list");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"exchange.list");
		}
	}

	public ActionForward change(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			String orgCode = request.getParameter("orgCode");
			//加载机构信息
			Org org = OrgService.getOrgByCode(orgCode);
			String tctd = request.getParameter("tctd");
			String date = this.getSystemMgrService().getSystemContolParameter("oper_tctd");
			Boolean bool =false;
			String systemDate=null;
			String date_hours=null;
			String date_minute="";
			String systemDate_minute="";
			String systemDate_hours=null;
			if(date!=null){
				systemDate = this.getSystemMgrService().getSystetemNowDate();
				date_hours=date.substring(0,date.indexOf(":"));
				date_minute=date.substring(date.indexOf(":")+1);
			    systemDate_hours=systemDate.substring(11, 13);
			    systemDate_minute = systemDate.substring(14, 16);
			    if(Integer.valueOf(systemDate_hours)>=Integer.valueOf(date_hours.toString()))
			    {
			    	bool = OrgService.updateOrgAndAccount(orgCode,tctd);
			    }else if(Integer.valueOf(systemDate_hours)==Integer.valueOf(date_hours.toString())){
			    	if(Integer.valueOf(systemDate_minute)>Integer.valueOf(date_minute.toString()))
			    	{
			    		bool = OrgService.updateOrgAndAccount(orgCode,tctd);
			    	}else{
			    		return this.showMessageJSP(actionMapping,request,"exchange.list","通存通兑逻辑修改必须在每天的["+date+"]之后进行！");
			    	}
			    }else
					return this.showMessageJSP(actionMapping,request,"exchange.list","通存通兑逻辑修改必须在每天的["+date+"]之后进行！");
			}else{
				bool = true;
				OrgService.updateOrgAndAccount(orgCode,tctd);
			}
			if (bool){
				return this.showMessageJSP(actionMapping,request,"exchange.list",org.getName() + "[" + orgCode + "]" + "通存通兑修改为:【" + tctd + "】");
			}
			return this.list(actionMapping, actionForm, request, response);
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"exchange.list");
		}finally{
			//加载通存通兑 列表信息
			List list = OrgService.getOrgListForTCTD(clerk.getWdFlag(),clerk.getShOrgCode());
			request.setAttribute("tctdList", list);
		}
	}

}