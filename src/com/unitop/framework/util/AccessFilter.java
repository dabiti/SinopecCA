package com.unitop.framework.util;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.impl.ClerkManageServiceImpl;

public class AccessFilter implements Filter {
	@Resource
	private ClerkManageService clerkManageService;
	{
		
		
	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		String path = request.getServletPath();
	//	String ipaddress = IPTool.getIpAddr(request);
		Clerk clerk = (Clerk) session.getAttribute("clerk");
		String adminCode = (String) session.getAttribute("admin");
		if (path.equals("/yinjManage.do")) 
		{
			String method = request.getParameter("method");

			if ("getUpdateParam".equals(method))
			{
			chain.doFilter(request, response);
			return;
			}
		}
		if (path.equals("/batchsealcheck.do")) 
		{
			chain.doFilter(request, response);
			return;
		}
		if (path.equals("/login.do")) 
		{
//			String method = request.getParameter("method");
//			if ("getAccountLinkManInfo".equals(method)||"zhanghkhView".equals(method))
//			{
			chain.doFilter(request, response);
			return;
//			}
		}
		if (path.equals("/batchsealcheck.do")) 
		{
			chain.doFilter(request, response);
			return;
		}
		if (path.equals("/accountinfo.do")) 
		{
			String method = request.getParameter("method");

			if ("getAccountLinkManInfo".equals(method)||"hexzhkhView".equals(method)||"hexzhxgView".equals(method))
			{
			chain.doFilter(request, response);
			return;
			}
		}
		// 打印模版下载放开限制 由于是控件主动获取模版信息地址 服务器认为此请求为新session
		if (path.equals("/pingz.do")) 
		{
			String method = request.getParameter("method");
			if ("getPingzmb".equals(method))
			{
				chain.doFilter(request, response);
				return;
			}
		}
		if (path.equals("/doMenu.do")) 
		{
			String method = request.getParameter("method");
			if ("doShow".equals(method))
			{
				chain.doFilter(request, response);
				return;
			}
		}
	
		if (path.equals("/syslogin.do")) 
		{
			chain.doFilter(request, response);
			return;
		}
		if (adminCode != null) 
		{
			request.setAttribute("totalRows", 0);
			chain.doFilter(request, response);
			return;
		}
		if (clerk == null) 
		{
			if (path.equals("/logout.do")) 
			{
				request.getRequestDispatcher("/login.jsp").forward(request,response);
				return;
			}
			if (!path.equals("/login.do"))
			{
				request.getRequestDispatcher("/timeOutlogin.jsp").forward(request, response);
				return;
			}
		} else {
			String ipaddress = clerkManageService.getClerkByCode(clerk.getCode()).getIp();
			if (ipaddress==null||ipaddress.trim().length()==0||!ipaddress.equals(clerk.getIp())) 
			{	
				String logintype=clerk.getLoginType();
				//if (logintype == null ||!logintype.equals("zt")){
					request.getRequestDispatcher("/areadyLogout.jsp").forward(request, response);
					return;
				//}
			}
		}
		request.setAttribute("totalRows", 0);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();
		SystemConfig systemConfig = SystemConfig.getInstance();
		//EC组件页面分页数量参数设置
		if(servletContext.getAttribute("ec_yemjlts")==null)
		{
			servletContext.setAttribute("ec_yemjlts", systemConfig.getValue("baobys"));
		}
		//加载柜员service
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		clerkManageService=(ClerkManageServiceImpl)wac.getBean("ClerkManageServiceImpl");
		
	}

	public void destroy() {
	}
}