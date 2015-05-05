package com.unitop.sysmgr.action.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.unitop.config.SystemConfig;
import com.unitop.framework.util.IPTool;
import com.unitop.sysmgr.action.ExDispatchAction;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.InitSystem;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.PrivilegeService;
import com.unitop.sysmgr.service.impl.ControlRights;

/* 
 * 《单点登录DEMO 实现-参考浙商项目》
 * 模块名称：单点登录SSO
 * 功能描述：通过集成行方提供CommInterface_test.jar包 进行访问统一认证平台进行校验用户
 * by ldd
 */
public class SSOLoginAction extends ExDispatchAction {
	@Resource
	private ControlRights controlRights;	
	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private OrgService OrgService;
	@Resource
	private InitSystem InitSystem;
	
	/*
	 *  单点登录 入口
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		//初始化
		SystemConfig systemConfig = SystemConfig.getInstance();
		Date rightNow = Calendar.getInstance().getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(rightNow);
		try {
			//同步参数
			InitSystem.synchronousSystemParameters();
			//版本控制
			String state = controlRights.control();
			if("试用期满".equals(state))
			{
				return this.showMessageJSP(mapping,request,"login.error","该系统为试用版，试用期限已过！");
			}else if("终端数满".equals(state))
			{
				return this.showMessageJSP(mapping, request, "login.error", "同时在线人数达上限！");
			}else if("版本错误".equals(state))
			{
				return this.showMessageJSP(mapping, request, "login.error", "没有权限使用此系统");
			}
			if("shiyong".equals(controlRights.getControlMap().get("version")))
			{
				String product_period = "试用期："+ controlRights.getControlMap().get("productperiod")+"  版本号："+controlRights.getControlMap().get("versionnum");
				request.getSession().setAttribute("product_period",product_period);
			}else{
				request.getSession().setAttribute("product_period","正式版   版本号："+controlRights.getControlMap().get("versionnum"));
			}
			//获取员工号、密码
			String ssoAuth = request.getParameter("ssoAuth");
			String ssoSign = request.getParameter("ssoSign");
			
			//如果没有获取正常单点传入参数进行警告提示
			if(ssoAuth==null||ssoSign==null)
			{
				return this.showMessageJSP(mapping, request, "login.error", "警告：拒绝非法访问!");
			}
				
            /**
             * GetKeys的参数含义：
             * 1.是否允许使用统一认证
             * 2.本系统名称
             * 3.本系统所在的服务器根地址
             * 4.统一认证服务器所在的根地址
             * 5.固定字符串，保持不变
             */
	      //GetKeys keys = new GetKeys(true,systemConfig.getValue("SSOlocalServerName"),systemConfig.getValue("localServerAddress"),systemConfig.getValue("SSOServerAddress"),systemConfig.getValue("SSOServerString"));
	        
	        /**
	         * 在用户登陆时，得到用户的柜员号。
	         * 返回值示例如下：A0210
	         * 在此处的这两个字符串由于已经过期，通不过系统验证，将不会有返回值
	         * temp2.getSSOEmployeeCode()方法参数含义：
	         * 1.前面生成的keys对象内的serviceconfig数据
	         * 2.由统一认证系统返回来的第一个加密串
	         * 3.由统一认证系统返回来的第二个加密串
	         */
	        //ServiceTool serviceTool = new ServiceTool();
	        String ssoYuangh = ""; //serviceTool.getSSOEmployeeCodeFromAtDecoded(keys.getServiceConfig(),ssoAuth,ssoSign);
	        
	        HttpSession session = request.getSession();
	        Clerk sessionClerk = (Clerk) session.getAttribute("clerk");
	        if(sessionClerk!=null)
	        {
	        	return this.showMessageJSP(mapping, request, "login.error","柜员或员工[<a title='点击注销' href='logout.do?logoutMark="+sessionClerk.getCode()+"'>"+sessionClerk.getCode()+"</a>]在本机已经登陆系统，可能此柜员或员工非正常注销！");
	        }
	        
			String clerkCode = ssoYuangh;
			Clerk clerk = clerkManageService.getClerkByCode(clerkCode);
			 // 先判断柜员是否存在
			if (clerk == null||"".equals(clerk.getCode()))
			{
				return this.showMessageJSP(mapping, request, "login.error", "员工号不存在!");
			}else{
				//获得柜员信息
				Org parent_org = OrgService.getOrgByCode(clerk.getShOrgCode());
				clerk.setShOrgName(parent_org.getName());
				Org org = OrgService.getOrgByCode(clerk.getOrgcode());
				if (org==null)
				{
					return this.showMessageJSP(mapping,request,"login.error","登陆失败，此柜员隶属机构："+clerk.getOrgcode()+"不存在！");
				}
				clerk.setOrgname(org.getName());
				/*
				 	* 获取柜员角色权限集合
				 */
				Map juesMap =  privilegeService.getPrivilegeForMenue(clerk.getCode());
				clerk.setJuesMap(juesMap);
				
				String roleName = clerkManageService.getClerkByOrgClerkName(clerk.getCode());
				clerk.setPostName(roleName);
			}
			String ipaddress = IPTool.getIpAddr(request);
			clerk.setIp(ipaddress);
			session.setMaxInactiveInterval(Integer.valueOf(systemConfig.getValue("outtime")));
			//记录柜员登录日期
			clerk.setLoginDate(date);
			session.setAttribute("clerk", clerk);
			this.createManageLog(clerkCode, "登陆");		
			ActionForward forward = new ActionForward() ;
			forward.setPath("/index.jsp");
			forward.setRedirect(true);
			return forward;
		} catch (Exception e) {
			return this.showMessageJSP(mapping, request, "login.error", "单点登陆异常："+e.getMessage());
		}
	}
}