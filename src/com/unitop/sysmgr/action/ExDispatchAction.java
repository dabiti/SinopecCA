package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.AccountManageLog;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.SystemManageLog;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.KagService;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.PrivilegeService;
import com.unitop.sysmgr.service.PromptService;
import com.unitop.sysmgr.service.QueryService;
import com.unitop.sysmgr.service.SystemMgrService;
import com.unitop.sysmgr.service.YinjkService;
import com.unitop.util.PhoneUtil;

public class ExDispatchAction extends DispatchAction {

	@Resource
	private SystemMgrService systemMgrService;

	@Resource
	protected ClerkManageService clerkService;

	@Resource
	private QueryService queryService;

	@Resource
	private PromptService prompteService;

	@Resource
	protected OrgService OrgService;
	@Resource
	protected PrivilegeService privilegeservice;
	@Resource
	protected YinjkService yinjkService;
	@Resource
	protected KagService kagService;

	public ClerkManageService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkManageService clerkService) {
		this.clerkService = clerkService;
	}

	public PromptService getPrompteService() {
		return prompteService;
	}

	public void setPrompteService(PromptService prompteService) {
		this.prompteService = prompteService;
	}

	public YinjkService getYinjkService() {
		return yinjkService;
	}

	public void setYinjkService(YinjkService yinjkService) {
		this.yinjkService = yinjkService;
	}

	public KagService getKagService() {
		return kagService;
	}

	public void setKagService(KagService kagService) {
		this.kagService = kagService;
	}

	public StringBuffer getLogString() {
		return logString;
	}

	public void setLogString(StringBuffer logString) {
		this.logString = logString;
	}

	public OrgService getOrgService() {
		return OrgService;
	}

	public PrivilegeService getPrivilegeservice() {
		return privilegeservice;
	}

	public void setSystemMgrService(SystemMgrService systemMgrService) {
		this.systemMgrService = systemMgrService;
	}

	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

	protected StringBuffer logString = new StringBuffer();

	protected static Log log = LogFactory.getLog(ExDispatchAction.class);

	public void setOrgService(OrgService orgService) {
		this.OrgService = orgService;
	}

	
	public void setPrivilegeservice(PrivilegeService privilegeservice) {
		this.privilegeservice = privilegeservice;
	}

	protected SystemMgrService getSystemMgrService() {
		return this.systemMgrService;
	}

	protected QueryService getQueryService() {
		return this.queryService;
	}

	protected PromptService getPromptService() {
		return this.prompteService;
	}
	
	//区号不为空 且长度不为0 要去掉首位0存入数据库
	public String subAreaCode(String qh){
		return PhoneUtil.subAreaCode(qh);
	}
	
	//区号不为空 且长度不为0  并且首位不为0 的 要补0显示
	public String bulidAreaCode(String qh){
		return PhoneUtil.bulidAreaCode(qh);
	}

	protected void processBusinessException(ActionMapping actionMapping,
			HttpServletRequest request, BusinessException e) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.detail", e.getMessage()));
		this.saveErrors(request, errors);
	}

	protected void processBusinessException(ActionMapping actionMapping,
			HttpServletRequest request, String message) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.detail", message));
		this.saveErrors(request, errors);
	}

	// 拼接印鉴卡编号字符串
	protected static String createYinjkbhStr(List<String> yinjkbhList) {
		if (yinjkbhList == null) {
			return "";
		}
		if (yinjkbhList.size() == 0) {
			return "";
		}
		Collections.sort(yinjkbhList);
		StringBuffer msg = new StringBuffer();
		for (String string : yinjkbhList) {
			if (string != null && !string.trim().equals("")) {
				msg.append("|").append(string);
			}
		}
		if (msg.length() != 0) {
			return msg.substring(1).trim();
		}
		return "";
	}

	protected ActionForward showMessageJSP(ActionMapping actionMapping,
			HttpServletRequest request, String Forward, String message) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.detail", message));
		this.saveErrors(request, errors);
		return actionMapping.findForward(Forward);
	}

	protected ActionForward errrForLogAndException(Exception e,
			ActionMapping mapping, HttpServletRequest request, String forward) {

		String admincode = "";
		String warn = null;
		String error = null;

		if (request.getSession().getAttribute("clerk") != null) {
			admincode = "柜员："
					+ ((Clerk) request.getSession().getAttribute("clerk"))
							.getCode();
		}

		warn = admincode + " IP:" + request.getRemoteAddr() + " "
				+ e.getMessage() + logString.toString();
		error = admincode + " IP:" + request.getRemoteAddr()
				+ logString.toString();

		logString.delete(0, logString.length());

		if (e != null
				&& e.getClass().getName().equals(
						"com.unitop.exception.BusinessException")) {
			log.warn(warn);
			processBusinessException(mapping, request, new BusinessException(e
					.getMessage()));
			return mapping.findForward(forward);
		} else {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.error(sw.toString() + "[" + error + "]");
			try {
				sw.close();
				pw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return processException(mapping, request, e, forward);
		}
	}

	protected ActionForward processBusinessException(
			ActionMapping actionMapping, HttpServletRequest request,
			BusinessException e, String Forward) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.detail", e.getMessage()));
		this.saveErrors(request, errors);
		return actionMapping.findForward(Forward);
	}

	protected ActionForward processException(ActionMapping actionMapping,
			HttpServletRequest request, Exception e, String Forward) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.detail", "操作失败!(" + e.getMessage() + ")"));
		this.saveErrors(request, errors);
		return actionMapping.findForward(Forward);
	}

	protected void createManageLog(String admincode, String content)
			throws BusinessException {
		try {
			String strdate = this.getSystemMgrService().getSystetemNowDate();
			SystemManageLog mlog = new SystemManageLog();
			mlog.setAdmincode(admincode);
			mlog.setOperdate(strdate);
			mlog.setContent(content);
			// 设置柜员IP
			Clerk clerk = clerkService.getClerkByCode(admincode);
			mlog.setIp(clerk.getIp());
			this.getSystemMgrService().createSystemManageLog(mlog);
			log.info("柜员：" + admincode + " 操作：" + content);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	public String getSystemContolParameter(String number) throws Exception {
		return getSystemMgrService().getSystemContolParameter(number);
	}

	public void createAccountManageLog(String account, String managetype,
			String content, Clerk clerk) {
		AccountManageLog bo = new AccountManageLog();
		String strdate = this.getSystemMgrService().getSystetemNowDate();
		bo.setAccount(account);
		bo.setManagedate(strdate.substring(0, 10));
		bo.setManagetime(strdate.substring(11, 19));
		bo.setManagetype(managetype);
		bo.setManagecontent(content);
		bo.setClerkname(clerk.getName());
		bo.setClerknum(clerk.getCode());
		bo.setIp(clerk.getIp());
		bo.setUpflag(clerk.getOrgcode());
		bo.setStr1("未上传");
		getSystemMgrService().createAccountmanagelog(bo);
	}

	public void createAccountManageLog(String account, String managetype,
			Clerk clerk, Zhanghb oldZhanghb, Zhanghb newZhanghb) {
		AccountManageLog bo = new AccountManageLog();
		String strdate = this.getSystemMgrService().getSystetemNowDate();
		bo.setAccount(account);
		bo.setManagedate(strdate.substring(0, 10));
		bo.setManagetime(strdate.substring(11, 19));

		bo.setClerkname(clerk.getName());
		bo.setClerknum(clerk.getCode());
		bo.setIp(clerk.getIp());
		bo.setUpflag(clerk.getOrgcode());
		bo.setStr1("未上传");

		// 联系人变动日志
		String oldcontent = getContent(oldZhanghb);
		String newcontent = getContent(newZhanghb);
		if (!oldcontent.equals(newcontent)) {
			bo.setOldcontent(oldcontent);
			bo.setNewcontent(newcontent);
			bo.setManagetype("2");
			getSystemMgrService().createAccountmanagelogNew(bo);
		}
		// 印鉴卡变动日志
		String oldyinjkh = oldZhanghb.getYinjkbh() == null ? "" : oldZhanghb
				.getYinjkbh();
		if (!oldyinjkh.trim().equals(newZhanghb.getYinjkbh())) {
			bo.setManagecontent("印鉴卡号由" + oldZhanghb.getYinjkbh() + "变更为："
					+ newZhanghb.getYinjkbh());
			bo.setManagetype("1");
			getSystemMgrService().createAccountmanagelogNew(bo);
		}

		String content = "";
		
		// 账户资料修改日志
		if (oldZhanghb.getHum() != null
				&& !oldZhanghb.getHum().equals(newZhanghb.getHum())) {
			content += "修改户名为:" + newZhanghb.getHum() + ";";
		}
		if (oldZhanghb.getTongctd() != null
				&& !oldZhanghb.getTongctd().equals(newZhanghb.getTongctd())) {
			content += "修改通兑标志为:" + newZhanghb.getTongctd() + ";";
		}
		if (oldZhanghb.getZhanghxz() != null
				&& !oldZhanghb.getZhanghxz().equals(newZhanghb.getZhanghxz())) {
			content += "修改账户性质为:" + newZhanghb.getZhanghxz() + ";";
		}
		String oldbeiz=oldZhanghb.getBeiz() == null?"":oldZhanghb.getBeiz();
		if (!oldbeiz.equals(newZhanghb.getBeiz())) {
			content += "修改备注资料;";
		}
		String smallcode=oldZhanghb.getIschecksmall()==null?"0":oldZhanghb.getIschecksmall();
		if(!smallcode.equals(newZhanghb.getIschecksmall())){
			content+="修改是否比对小码为:";
			content+=newZhanghb.getIschecksmall()==null||"0".equals(newZhanghb.getIschecksmall())?"否;":"是;";
		}

		if (!content.equals("")) {
			bo.setManagecontent(content);
			bo.setManagetype("1");
			getSystemMgrService().createAccountmanagelogNew(bo);
		}

	}

	private String getContent(Zhanghb zhanghb) {
		if (zhanghb == null) {
			return "";
		}
		StringBuffer msg = new StringBuffer();
		String dianh=makePhone(bulidAreaCode(zhanghb.getLianxrqh()),zhanghb.getDianh(),zhanghb.getLianxrfjh());
		
		msg.append(
				zhanghb.getLianxr() == null ? "" : zhanghb.getLianxr().trim())
				.append("|").append(dianh == null ? "" : dianh.trim()).append("|");

		String dianh2=makePhone(bulidAreaCode(zhanghb.getLianxrqh2()),zhanghb.getDianh2(),zhanghb.getLianxrfjh2());
		msg.append(
				zhanghb.getLianxr2() == null ? "" : zhanghb.getLianxr2().trim())
				.append("|").append(dianh2 == null ? "" : dianh2.trim()).append("|");

		String dianh3=makePhone(bulidAreaCode(zhanghb.getFuzrqh()),zhanghb.getFuzrdh(),zhanghb.getFuzrfjh());
		msg.append(
				zhanghb.getFuzr() == null ? "" : zhanghb.getFuzr().trim())
		.append("|").append(dianh3 == null ? "" : dianh3.trim()).append("|");

		String dianh4=makePhone(bulidAreaCode(zhanghb.getFuzrqh2()),zhanghb.getFuzrdh2(),zhanghb.getFuzrfjh2());
		msg.append(
				zhanghb.getFuzr2() == null ? "" : zhanghb.getFuzr2().trim())
		.append("|").append(dianh4 == null ? "" : dianh4.trim());
		msg.append("|").append(zhanghb.getShifdh());
		return msg.toString();
	}

	public String makePhone(String qh, String dianh, String fjh) {
	
		return PhoneUtil.makePhone(qh, dianh, fjh);
	}

	// 分页查询 (提交请求)
	public TabsBo createTabsBo(HttpServletRequest request) {
		TabsBo TabsBo = new TabsBo();
		String ec_p = request.getParameter("ec_p");
		String ec_rd = request.getParameter("ec_rd");
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String ec_yemjlts = (String) servletContext.getAttribute("ec_yemjlts");
		if (ec_rd == null)
			ec_rd = ec_yemjlts;
		if (ec_p == null)
			ec_p = "1";
		TabsBo.setDangqym(Integer.valueOf(ec_p));
		TabsBo.setFenysl(Integer.valueOf(ec_rd));
		return TabsBo;
	}

	// 分页查询 (查询展示)
	public void showTabsModel(HttpServletRequest request, TabsBo tabsBo) {
		String maxnum=SystemConfig.getInstance().getValue("maxexportnum");
		int num=0;
		if(maxnum!=null&&maxnum.trim().length()!=0){
			try {
				num=Integer.parseInt(maxnum);
			} catch (NumberFormatException e) {
				num=30000;
			}
		}else{
			num=30000;
		}
		if(tabsBo.getList().size() < num){
		request.setAttribute("list", tabsBo.getList());
		request.setAttribute("totalRows", tabsBo.getCounts());
		}
	}

	// 分页查询 跳转展示
	public ActionForward showMessageJSPForFeny(ActionMapping actionMapping,
			HttpServletRequest request, TabsBo tabsBo, String Forward) {
		String maxnum=SystemConfig.getInstance().getValue("maxexportnum");
		int num=0;
		if(maxnum!=null&&maxnum.trim().length()!=0){
			try {
				num=Integer.parseInt(maxnum);
			} catch (NumberFormatException e) {
				num=30000;
			}
		}else{
			num=30000;
		}
		if(tabsBo.getList().size() >= num){
			return this.showMessageJSP(actionMapping, request, Forward,
					"已超过最大查询数量:"+num+"条,请修改条件进行筛选!");
		}else if (tabsBo.getList().size() == 0) {
			return this.showMessageJSP(actionMapping, request, Forward,
						"无查询结果！");
		} else {
				return actionMapping.findForward(Forward);
		}
		
	}
}