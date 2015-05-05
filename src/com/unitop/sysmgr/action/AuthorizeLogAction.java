package com.unitop.sysmgr.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.AuthorizeLogForm;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;
import com.unitop.util.CoreBillUtils;
/*
 * 授权日志查询
 */
@Controller("/authorizelog")
public class AuthorizeLogAction  extends ExDispatchAction{

	private final static String SUCCESS = "success";
	@Resource
	private OrgService OrgService;
	@Resource
	private ZhanghbService zhanghbService;
	
	public boolean CanOperDesOrg(String OperOrg, String DesOrg) {
		return this.getSystemMgrService().CanOperDesOrg(OperOrg, DesOrg);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			
			AuthorizeLogForm authorizeLogForm = (AuthorizeLogForm) actionform;
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String account =authorizeLogForm.getAccount();
			//新旧账号转换
			if (account != null &&account.length() != 17) {
				account = zhanghbService.parseTypeN(account, 17);
			}
			authorizeLogForm.setAccount(account);
			Zhanghb zhanghb=zhanghbService.getZhanghb(account);
			if(zhanghb==null){
				throw new RuntimeException("账户不存在");
			}
			boolean pass=CanOperDesOrg(clerk.getOrgcode(), zhanghb.getJigh());
			if(!pass){
				throw new RuntimeException("您没有权限查询该账户的授权日志");
			}
				TabsBo TabsBo = this.createTabsBo(request);
				QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
				queryServiceImpl.setTabsService(TabsBo);
				TabsBo tabsBo = this.getQueryService().findAuthorizeLog(account,authorizeLogForm.getBegindate(),authorizeLogForm.getEnddate());
				this.showTabsModel(request, tabsBo);
				//request.setAttribute("jlist",tabsBo.getParamterMapStr());
				//request.setAttribute("jsql",tabsBo.getSql());
				//System.out.println(tabsBo.getSql());
				return super.showMessageJSPForFeny(mapping,request,tabsBo,SUCCESS);
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "error");
		}
	}
}