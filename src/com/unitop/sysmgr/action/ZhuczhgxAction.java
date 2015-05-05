package com.unitop.sysmgr.action;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.ZhuczhgxForm;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.ZhuczhgxService;
import com.unitop.sysmgr.service.impl.ZhuczhgxServiceImpl;

@Controller("/zhuczh")
public class ZhuczhgxAction extends ExDispatchAction{
	
	@Resource 
	private ZhuczhgxService zhuczhgxService;
	@Resource 
	private ZhanghbService zhanghbService;

	/*
	 * 维护主从账户关系页面跳转
	 * by wp
	 */
	public ActionForward zhuczh(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			return actionMapping.findForward("zhuczhgx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "zhuczhgx");
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 维护主从账户关系
	 * by wp
	 */
	public ActionForward weihzczhgx(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		ZhuczhgxForm zhucform = (ZhuczhgxForm) actionForm;
		String zhangh_old = zhucform.getZhangh();
		try {
			//账号不为空校验
			if (zhangh_old==null||"".equals(zhangh_old))
			{
				return this.showMessageJSP(actionMapping, request, "zhuczhgx", "请输入账号！");
				}else{
					Zhanghb zhanghb = zhanghbService.getZhanghb(zhangh_old);
					//验证账户是否存在
					if(zhanghb.getZhangh()==null||"".equals(zhanghb.getZhangh()))
					{
						return super.showMessageJSP(actionMapping, request, "zhuczhgx", "账号：["+zhangh_old+"]不存在!");
					}
				}
			
			//子账户列表
			List list = zhuczhgxService.getzizh(zhangh_old);
			request.setAttribute("list", list);//jsp页面{list}可以显示
			System.out.println(list.size());
			if(list.size()==0)
			{
				//list为空表示该账户不是主账户
				return this.showMessageJSP(actionMapping, request, "zhuczhgx", "该输入账户不是主账户！");
			}
			return actionMapping.findForward("zhuczhgx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "zhuczhgx");
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 新建主从关系页面跳转
	 * by wp
	 */
	public ActionForward add(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			//获取当前日期
			String date = this.getSystemMgrService().getSystetemNowDate().substring(0,10);
			request.getSession().setAttribute("date", date);
			return actionMapping.findForward("xinjgx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "xinjgx");
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 获取从账户信息（新建主从关系）（AJAX）
	 * by wp
	 */ 
	public ActionForward getcongzh(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			//获取界面从账户
			String congzh = request.getParameter("congzh");
			String congzhname = ""; //从账户户名
			String congzhState ="";//从账户账户状态
			String congzhVerifyState="";//印鉴审核状态
			String youwyj="";//有无印鉴
			ZhuczhgxServiceImpl zhuczhgxServiceImpl = (ZhuczhgxServiceImpl) this.zhuczhgxService;
			Zhanghb zhanghb = zhuczhgxServiceImpl.zhanghbDao.getZhanghb(congzh);
			if(zhanghb!=null)
			{
				congzhname = zhanghb.getHum();
				congzhState = zhanghb.getZhanghzt();
				congzhVerifyState = zhanghb.getYinjshzt();
				youwyj = zhanghb.getYouwyj();
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			String accountin = congzhname + "," + congzhState + "," + congzhVerifyState + "," + youwyj;
			out.println(accountin);
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "null");
		} 
	}
	
	/*
	 * 获取主账户信息（新建主从关系）（AJAX）
	 * js中调
	 * by wp
	 */ 
	public ActionForward getzhuzh(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			//获取界面主账户
			String mainAccount = request.getParameter("mainAccount");
			String mainAccountName="";//户名
			String mainAccountState = ""; //账户状态
			String mainAccountVerifyState ="";//账户审核状态
			String mainYouwyj="";//有无印鉴
			
			ZhuczhgxServiceImpl zhuczhgxServiceImpl = (ZhuczhgxServiceImpl) this.zhuczhgxService;
			Zhanghb zhanghb = zhuczhgxServiceImpl.zhanghbDao.getZhanghb(mainAccount);
			if(zhanghb!=null)
			{
				mainAccountName = zhanghb.getHum();
				mainAccountState  = zhanghb.getZhanghzt();
				mainAccountVerifyState = zhanghb.getYinjshzt();
				mainYouwyj = zhanghb.getYouwyj();
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			String accountin = mainAccountName + "," + mainAccountState + "," + mainAccountVerifyState + "," + mainYouwyj;
			out.println(accountin);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "null");
		} 
	}
	
	/*
	 * 新建主从关系
	 * by wp
	 */
	public ActionForward xinjgx(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");//获取当前登录柜员信息
		try {
			//获取界面主从账户信息
			ZhuczhgxForm zhucform = (ZhuczhgxForm) actionForm;
			String congzh = zhucform.getCongzh();// 从账户
			String mainaccount = zhucform.getMainAccount();//主账户
			String fuyrq = zhucform.getFuyrq();//复用日期
			
			//输入不为空校验
			if(congzh==null||"".equals(congzh)){
				return super.showMessageJSP(actionMapping, request, "xinjgx", "从账户不能为空！");
			}
			if(fuyrq==null||"".equals(fuyrq)){
				return super.showMessageJSP(actionMapping, request, "xinjgx", "复用日期不能为空！");
			}
			if(mainaccount==null||"".equals(mainaccount)){
				return super.showMessageJSP(actionMapping, request, "xinjgx", "主账户不能为空！");
			}
			
			//获取主从账户印鉴信息
			Zhanghb zhanghb_c = zhanghbService.getZhanghb(congzh);
			Zhanghb zhanghb_z = zhanghbService.getZhanghb(mainaccount);
			
			//保存建立的主从关系
			zhuczhgxService.saveyinj(fuyrq,congzh,mainaccount,zhanghb_c,zhanghb_z);
			
			//记录日志
			String content = "新建主从账户关系(主账户为" + mainaccount + ";柜员代码:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(mainaccount ,"新建主从账户关系", "新建主从账户关系", clerk);
			String content_ = "新建主从账户关系(从账户为" + congzh + ";柜员代码:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content_);
			this.createAccountManageLog(congzh,"新建主从账户关系", "新建主从账户关系", clerk);
			return super.showMessageJSP(actionMapping, request, "xinjgx","新建主从关系成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "xinjgx");
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 取消主从关系
	 * by wp
	 */
	public ActionForward quxgx(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");//获取当前登录柜员信息
		try {
			String czhangh = request.getParameter("czhangh");//通过URL传递的从账户
			String zzhangh = request.getParameter("zzhangh");//通过URL传递的从账户的主账户
			// 获取当前时间  //取消复用日期
			String date = this.getSystemMgrService().getSystetemNowDate().substring(0,10);
			zhuczhgxService.doquxgx(czhangh,zzhangh,date);
			//记录日志
			String content = "取消主从账户关系(主账户为" + zzhangh + ";柜员代码:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zzhangh ,"取消主从账户关系", "取消主从账户关系", clerk);
			String content_ = "取消主从账户关系(从账户为" + czhangh + ";柜员代码:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content_);
			this.createAccountManageLog(czhangh,"新建主从账户关系", "新建主从账户关系", clerk);
			return super.showMessageJSP(actionMapping, request, "zhuczhgx","取消主从账户关系成功");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "zhuczhgx");
		} finally {
			// 必执行
		}
	}
}