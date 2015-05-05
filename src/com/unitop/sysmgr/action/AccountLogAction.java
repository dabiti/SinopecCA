package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.AccountLogForm;
import com.unitop.sysmgr.form.AccountinfoForm;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;
import com.unitop.sysmgr.action.ExDispatchAction;
import com.unitop.util.CoreBillUtils;

@Controller("/accountlog")
public class AccountLogAction extends ExDispatchAction {
	@Resource
	private ZhanghbService zhanghbService;

	public boolean CanOperDesOrg(String OperOrg, String DesOrg) {
		return getSystemMgrService().CanOperDesSpecialOrg(OperOrg, DesOrg);
	}

	public ActionForward enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AccountLogForm accountLogForm =(AccountLogForm)form;
		try {
			return mapping.findForward("enter");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "enter");
		}finally{
			//添加货币号
//			List HuobhList = getQueryService().getHuobh();
//			request.setAttribute("HuobhList",HuobhList);
			//添加验印日志类型
			accountLogForm.setManagetype(new String [] {"全部"});
			SystemConfig systemConfig = SystemConfig.getInstance();
			String yanyinLogType =  systemConfig.getValue("yanyinlogType");
			String[] yanyinlogs = yanyinLogType.split("\\|");
			request.setAttribute("yanyinlogs",yanyinlogs);
		}
	}

	public ActionForward find(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountLogForm accountform = (AccountLogForm) form;
		Zhanghb zhangh =null;
		String account =accountform.getAccount();
		//新旧账号转换
		if (account != null &&account.length() != 17) {
			account = zhanghbService.parseTypeN(account, 17);
		}
		accountform.setAccount(account);
		try {
			if(account!=null&&!account.trim().equals("")){
				zhangh = zhanghbService.getZhanghb(account);
				if(zhangh==null)
				{
					return this.showMessageJSP(mapping, request, "error", "此帐号不存在");
				}
				request.setAttribute("zhangh", accountform.getAccount());
				if (zhangh != null && !CanOperDesOrg(clerk.getOrgcode(), zhangh.getJigh())) {
					return this.showMessageJSP(mapping, request, "error", "输入无效,您不能操作此帐户！");
				}
			} 
			TabsBo TabsBo = this.createTabsBo(request);
			QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
			queryServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo = this.getQueryService().findAccountLog(account, accountform.getBegindate(),accountform.getEnddate(), accountform.getManagetype(),"", clerk.getOrgcode());

			//request.setAttribute("jlist", tabsBo.getParamterMapStr());
			//request.setAttribute("sql", tabsBo.getSql());
			this.showTabsModel(request, tabsBo);
			
			return super.showMessageJSPForFeny(mapping,request,tabsBo,"enter");
	
		} catch (Exception e) {
			e.printStackTrace();
//			
			request.setAttribute("industrycharacter", accountform.getIndustrycharacter());
			if(zhangh!=null)
			request.setAttribute("accountName", zhangh.getHum());
			return this.errrForLogAndException(e, mapping, request, "error");
		}finally{
			//添加验印日志类型
			SystemConfig systemConfig = SystemConfig.getInstance();
			String yanyinLogType =  systemConfig.getValue("yanyinlogType");
			String[] yanyinlogs = yanyinLogType.split("\\|");
			request.setAttribute("yanyinlogs",yanyinlogs);
		}
	}

	public ActionForward getaccountname(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		String accountname = "";
		//新旧账号转换
		if (code != null &&code.length() != 17) {
			code = zhanghbService.parseTypeN(code, 17);
		}
		try {
			Zhanghb account = zhanghbService.getZhanghb(code);
			if (account != null)
				accountname = account.getHum();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(accountname);
			out.close();
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
		return null;
	}
	public ActionForward forqueryAccountTongbrz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountlog.zhanghtb");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountlog.zhanghtb");
		}
	}
	
	public ActionForward queryAccountTongbrz(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AccountLogForm aform = (AccountLogForm) form;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String jigh =aform.getOrgcode();
		String account =aform.getAccount();
		String begindate =aform.getBegindate();
		String enddate =aform.getEnddate();
		try {
		//新旧账号转换
		if (account != null &&account.length() != 17) {
			account = zhanghbService.parseTypeN(account, 17);
		}
		aform.setAccount(account);
		
		if(jigh==null||jigh.trim().equals("")){
			jigh=clerk.getOrgcode();
		}else{
			// 判断当前柜员是否有权限查看
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), jigh);
			if (!bool) {
				return super.showMessageJSP(mapping, request,
						"accountlog.zhanghtb",
						"您没有权限查看机构["+jigh+"]!");
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
				"accountlog.zhanghtb");
	} catch (Exception e) {
		e.printStackTrace();
		return this.errrForLogAndException(e, mapping, request,
				"accountlog.zhanghtb");
	}
		
	}

}