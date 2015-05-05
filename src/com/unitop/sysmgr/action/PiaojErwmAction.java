package com.unitop.sysmgr.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.framework.util.DateTool;
import com.unitop.framework.util.JsonTool;
import com.unitop.framework.util.MD5Tool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Dayfwcs;
import com.unitop.sysmgr.bo.Pingzmx;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.form.PingzForm;
import com.unitop.sysmgr.service.PingzService;
import com.unitop.sysmgr.service.PingzmxService;
import com.unitop.sysmgr.service.VoucherMgrService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.PingzmxServiceImpl;
@Controller("/pingz")
public class PiaojErwmAction extends ExDispatchAction {
	@Resource
	private PingzmxService pingzmxService;
	@Resource
	private ZhanghbService zhanghService;
	@Resource
	private ZhanghbDao zhanghbDao ;
	@Resource
	private VoucherMgrService voucherMgrservice;
	@Resource
	public PingzService PingzService;

	// 凭证 出售页面跳转
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PingzForm pf = (PingzForm) actionForm;
			pf.clear();
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			return actionMapping.findForward("pingzcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}

	// 查找明细及 打印
	public ActionForward findPingzmx(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List pingzlxlist = findPingzlx(request);
			request.setAttribute("pingzlxlist", pingzlxlist);
			PingzForm pingzForm = (PingzForm) actionForm;

			// 异常处理，如果第一次登录进来 切换分页条件查询 直接返回
			if ("".equals(pingzForm.getEnddate()) && "".endsWith(pingzForm.getBegindate()))
			{
				return actionMapping.findForward("mingxcxdy");
			}
			//账号校验
			String accountNum=pingzForm.getZhangh();
			if((accountNum!=null)&&(!"".equals(accountNum.trim()))){
				Zhanghb zhanghb=zhanghbDao.getZhanghb(accountNum);
				if(zhanghb==null){
					return this.showMessageJSP(actionMapping, request, "mingxcxdy","账号不存在！");
				}
			}
			// 凭证号校验
			String qispzh = pingzForm.getQispzh();
			String zhongzpzh = pingzForm.getZhongzpzh();
			
			if (("".equals(qispzh) && !"".equals(zhongzpzh)) || ((!"".equals(qispzh) && "".equals(zhongzpzh)))) 
			{ 
				return this.showMessageJSP(actionMapping, request, "mingxcxdy","起始凭证号与终止凭证号必须同时输入！");
			} else if (!"".equals(qispzh) && !"".equals(zhongzpzh)) {
				String t1=qispzh.substring(2);
				String t2=zhongzpzh.substring(2);
				if (t1.compareTo(t2)>0) 
				{
					return this.showMessageJSP(actionMapping, request, "mingxcxdy", "起始凭证号不能大于终止凭证号！");
				}
				String qispzlx = qispzh.toUpperCase().substring(2);
				String zhongzpzlx = zhongzpzh.toUpperCase().substring(2);
				if (Long.valueOf(qispzlx)>Long.valueOf(zhongzpzlx))
				{
					return this.showMessageJSP(actionMapping, request, "mingxcxdy", "起始凭证号和终止凭证号的凭证类型不相同！");
				}
				pingzForm.setQispzh(qispzh);
				pingzForm.setZhongzpzh(zhongzpzh);
			}
			TabsBo TabsBo = this.createTabsBo(request);
			PingzmxServiceImpl pingzmxServiceImpl = (PingzmxServiceImpl) pingzmxService;
			pingzmxServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo =pingzmxService.selectAllPingz(pingzForm);
			this.showTabsModel(request, tabsBo);
			List erwmMsgList = new ArrayList();
			for (int i = 0; i < tabsBo.getList().size(); i++) 
			{
				Pingzmx pz =  (Pingzmx) tabsBo.getList().get(i);
				if ("未打印".equals(pz.getZhuangt()))
				{
					String erwmMsg = makeErWM(pz);
					pz.setErwmMsg(erwmMsg);
					erwmMsgList.add(erwmMsg);
				}
			}
			request.setAttribute("erwmMsgList", JsonTool.toJsonForArry(erwmMsgList));
			request.setAttribute("pingzlist", tabsBo.getList());
			return super.showMessageJSPForFeny(actionMapping,request,tabsBo,"mingxcxdy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "mingxcxdy");
		}
	}

	/**
	 * 组织打印数据报文
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private String makeErWM(Pingzmx pingz){
		List pingzlx = voucherMgrservice.getVoucherList();
		String pingzlxStr = "";
		if(pingzlx!=null)
		{
			Voucher v = (Voucher)pingzlx.get(0);
			pingzlxStr = v.getPingzbs();
		}
		pingz.setFilename(pingzlxStr);
		pingz.setErwmMsg(pingz.getZhangh()+"|"+pingz.getHum()+"|"+pingz.getPingzh());
		String json = JsonTool.toJsonForArry(pingz);
		return json;
	}

	/**
	 * 
	 * <dl>
	 * <dt><b>getAccountForNet:从.net版账户表中获取账户数据(新接口)</b></dt>
	 * <dd></dd>
	 * </dl>
	 */
	public ActionForward getZhanghbHum(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		String hum = "";
		try {
			Zhanghb zhanghinfo = zhanghService.getZhanghb(zhangh);
			if (zhanghinfo != null)
			{
				hum = zhanghinfo.getHum();
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			String zhanghin = hum;
			out.println(zhanghin);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}

	}

	// 明细查询及 打印页面跳转
	public ActionForward cxdy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			/*
			 * 获取凭证类型
			 */
			PingzForm pf = (PingzForm) actionForm;
			pf.clear();
			if(pf.getBegindate() == "")
			{
				pf.setBegindate(DateTool.getNowDayForYYYMMDD().substring(0,7)+"-01");
			}
			if(pf.getEnddate() == "")
			{
				pf.setEnddate(DateTool.getNowDayForYYYMMDD());
			}
			List<Voucher> pingzlxlist = findPingzlx(request);
			//此处不能使用session
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			return actionMapping.findForward("mingxcxdy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			request.setAttribute("erwmMsgList", "''");
			// 必执行
		}
	}

	// 获取凭证类型(内部专用)
	private List findPingzlx(HttpServletRequest request) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String orgcode = clerk.getOrgcode();
		List<Voucher> pingzlxlist = voucherMgrservice.getVoucherList();
		if (pingzlxlist == null || pingzlxlist.size() == 0) 
		{
			String rootcode = SystemConfig.getInstance().getRootCode();
			pingzlxlist = voucherMgrservice.getVoucherList();
		}
		return pingzlxlist;
	}

	// 保存汇总及明细信息
	public ActionForward saveHuiz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			// 获取当前时间
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			
			SimpleDateFormat formattime = new SimpleDateFormat("HH:mm:ss");
			String time = formattime.format(rightNow);

			// 获取Form中的值
			PingzForm pingzForm = (PingzForm) actionForm;
			Pingzmx pingzmx = new Pingzmx();
			Zhanghb zhangh = new Zhanghb();

			// 验证账号是否存在
			try {
				zhangh = zhanghService.getZhanghb(pingzForm.getZhangh());
			} catch (Exception e) {
				return this.showMessageJSP(actionMapping, request, "pingzcs", "此账户不存在！");
			}

			// 凭证号校验
			String qispzh = pingzForm.getQispzh().toUpperCase();
			String zhongzpzh = pingzForm.getZhongzpzh().toUpperCase();

			if (("".equals(qispzh) && !"".equals(zhongzpzh)) || ((!"".equals(qispzh) && "".equals(zhongzpzh))))
			{
				return this.showMessageJSP(actionMapping, request, "pingzcs", "起始凭证号与终止凭证号必须同时输入！");
			} else if (!"".equals(qispzh) && !"".equals(zhongzpzh)) {
				if (qispzh.compareTo(zhongzpzh)>0) 
				{
					return this.showMessageJSP(actionMapping, request, "pingzcs", "起始凭证号不能大于终止凭证号！");
				}
				pingzForm.setQispzh(qispzh);
				pingzForm.setZhongzpzh(zhongzpzh);
			}
			// 验证凭证号是否已存在
			List<Pingzmx> list = pingzmxService.findPingzByPingzh(qispzh,zhongzpzh);
			if (list == null || list.size() == 0) 
			{
				//验证凭证一次录入的张数是否过多
				Long qis = Long.parseLong(qispzh);
				Long zhongz = Long.parseLong(zhongzpzh);
				Long maxpz = Long.parseLong(SystemConfig.getInstance().getMaxpings());
				/*int qis = Integer.parseInt(qispzh);
				int zhongz = Integer.parseInt(zhongzpzh);
				int maxpz = Integer.parseInt(SystemConfig.getInstance().getMaxpings());*/
				if ((zhongz-qis)>maxpz)
				{
					return this.showMessageJSP(actionMapping, request,"pingzcs", "凭证录入过多！每次最多录入"+maxpz+"张！");
				}
				pingzmx.setZhangh(pingzForm.getZhangh() == null ? pingzmx.getZhangh() : pingzForm.getZhangh());
				pingzmx.setGuiyh(pingzForm.getGuiyh() == null ? pingzmx.getGuiyh() : pingzForm.getGuiyh());
				pingzmx.setHum(zhangh.getHum());
				pingzmx.setJigh(pingzForm.getJigh() == null ? pingzmx.getJigh(): pingzForm.getJigh());
				pingzmx.setRiq(date);
				pingzmx.setShij(time);
				pingzmx.setPingzlx(pingzForm.getPingzlx() == null ? pingzmx.getPingzlx() : pingzForm.getPingzlx());
				pingzmx.setQispzh(pingzForm.getQispzh() == null ? pingzmx.getQispzh() : pingzForm.getQispzh());
				pingzmx.setZhongzpzh(pingzForm.getZhongzpzh() == null ? pingzmx.getQispzh() : pingzForm.getZhongzpzh());
				pingzmx.setShij("".equals(pingzForm.getShij()) ? pingzmx.getShij() : pingzForm.getShij());
				pingzmx.setZhangs(request.getParameter("zhangs_"));
				pingzmxService.savePingz(pingzmx);
				Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
				String content = "凭证出售(账号为" + pingzmx.getZhangh() + ";起始凭证号:"+pingzmx.getQispzh()+ ";终止凭证号:"+pingzmx.getZhongzpzh()+")";
				this.createManageLog(clerk.getCode(), content);
				return this.showMessageJSP(actionMapping, request, "pingzcs", getPromptService().getPromptMsg("PJCS_success", map));
			} else {
				return this.showMessageJSP(actionMapping, request, "pingzcs", getPromptService().getPromptMsg("PJCS_alreadyhave", map));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzcs");
		} finally {
			// 必执行

		}
	}

	public ActionForward forwardForUpdate(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PingzForm pingzForm = (PingzForm) actionForm;
			Pingzmx pingzmx = pingzmxService.findPingzByPingzh(pingzForm.getPingzh());
			request.setAttribute("pingzmx", pingzmx);
			return actionMapping.findForward("toUpdate");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}

	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PingzForm pingzForm = (PingzForm) actionForm;
			Pingzmx pingzmx = pingzmxService.findPingzByPingzh(pingzForm
					.getPingzh());
			pingzmx.setZhangh(pingzForm.getZhangh() == null ? pingzmx
					.getZhangh() : pingzForm.getZhangh());
			pingzmx.setGuiyh(pingzForm.getGuiyh() == null ? pingzmx.getGuiyh()
					: pingzForm.getGuiyh());
			pingzmx.setHum(pingzForm.getHum() == null ? pingzmx.getHum()
					: pingzForm.getHum());
			pingzmx.setZhuangt(pingzForm.getZhuangt() == null ? pingzmx
					.getZhuangt() : pingzForm.getZhuangt());
			pingzmx.setRiq(pingzForm.getRiq() == null ? pingzmx.getRiq()
					: pingzForm.getRiq());
			pingzmx.setShij("".equals(pingzForm.getShij()) ? pingzmx.getShij()
					: pingzForm.getShij());
			pingzmxService.updatePingz(pingzmx);
			return actionMapping.findForward("mingxcxdy");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String deleteflag = "success";
			String pingzh = request.getParameter("pingzh");
			try {
				pingzmxService.deletePingz(pingzh);
				Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
				String content = "凭证单笔删除(凭证号" +pingzh+")";
				this.createManageLog(clerk.getCode(), content);
			} catch (Exception e) {
				deleteflag = "fail";
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(deleteflag);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 凭证批量删除   ajax
	 */
	public ActionForward bathDelete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String deleteflag = "success";
			String pingzhString = request.getParameter("pingzh");
			String selectString = request.getParameter("selectString");
			//转码
			selectString = URLDecoder.decode(selectString.toString(),"utf-8");
			try {
				pingzmxService.batchDeletePingz(pingzhString);
				Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
				String content = "凭证批量删除(查询条件~"+selectString+")";
				this.createManageLog(clerk.getCode(), content);
			} catch (Exception e) {
				deleteflag = "fail";
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(deleteflag);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}

	public ActionForward getPingzListByPich(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			String pich = request.getParameter("pich");
			List list = PingzService.findPingzByPich(pich);
			request.setAttribute("list", list);
			request.setAttribute("totalRows", list.size());
			return actionMapping.findForward("huizpzmx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "huizpzmx");
		} finally {
			// 必执行
		}
	}

	// 凭证出售明细日志
	public ActionForward getPingzMxRizListByPich(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String pingzh = request.getParameter("pingzh");
			List list = pingzmxService.findPingzRizByPingzh(pingzh);
			request.setAttribute("list", list);
			request.setAttribute("totalRows", list.size());
			return actionMapping.findForward("pingzmxrz");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"pingzmxrz");
		} finally {
			// 必执行
		}
	}

	// 下载凭证模版
	public ActionForward getPingzmb(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream out = null;
		String fileName = request.getParameter("filename");
		String type = request.getParameter("mode");
		try {
			out = response.getOutputStream();
			if ("0".equals(type)) {
				PingzService.loadPingzmbByName(request.getSession()
						.getServletContext().getRealPath("")
						+ File.separatorChar
						+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
						+ fileName + ".html", out);
				response.setHeader("Content-disposition",
						"attachment;filename=" + fileName);
			}
			if ("1".equals(type)) {
				PingzService.loadPingzmbByName(request.getSession()
						.getServletContext().getRealPath("")
						+ File.separatorChar
						+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
						+ fileName+".jpg", out);
				response.setHeader("Content-disposition",
						"attachment;filename=" + fileName);
			}
			System.gc();
			return null;
		} catch (Exception e) {
			try{
				if ("0".equals(type)) {
					PingzService.loadPingzmbByName(request.getSession()
							.getServletContext().getRealPath("")
							+ File.separatorChar
							+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
							+  "default.html", out);
					response.setHeader("Content-disposition", "attachment;filename=" + fileName);
				}
				if ("1".equals(type)) {
					PingzService.loadPingzmbByName(request.getSession()
							.getServletContext().getRealPath("")
							+ File.separatorChar
							+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
							+ "default.jpg", out);
					response.setHeader("Content-disposition",
							"attachment;filename=" + fileName);
				}} catch (Exception ee) {
			}
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			try {
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ActionForward updateZhuangt(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String updateflag = "success";
			String qispzh = request.getParameter("qispzh");
			String zhongzpzh = request.getParameter("zhongzpzh");
			String dayfwmsg = request.getParameter("dayfwmsg");
			String count = request.getParameter("count");
			log.info("action接收数量:"+count);
			dayfwmsg = URLDecoder.decode(dayfwmsg,"utf-8");//转码
			//将前端传入的打印服务信息转换
			dayfwmsg = transDayfwid(dayfwmsg);
		
			try {
				pingzmxService.updateZhuangt(qispzh,zhongzpzh,dayfwmsg,count);
			} catch (Exception e) {
				e.printStackTrace();
				updateflag = "fail";
			}

			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(updateflag);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}
	public String transDayfwid(String dayfwmsg){
		dayfwmsg = dayfwmsg.replace('\\', '/');
		Dayfwcs d = (Dayfwcs)JsonTool.toBean(dayfwmsg, Dayfwcs.class);
		String[] port = d.getDayfwport().split(".");
		String str ="";
		if(port.length==4){
			 str = d.getDayfwport(); 
		}else{
			int f = d.getDayfwmc().indexOf("//");
			if(f!=-1){
				str = d.getDayfwmc()+"|"+d.getDayfwport()+"|"+d.getDayfwmac();
			}else{
				str = "//"+d.getBendname().toUpperCase()+"/"+d.getDayfwmc()+"|"+d.getDayfwport()+"|"+d.getDayfwmac();
			}
		}
		String dayfwid = MD5Tool.getMD5ByStr(str);
		return dayfwid;
	}
	
	public void setVoucherMgrservice(VoucherMgrService voucherMgrservice) {
		this.voucherMgrservice = voucherMgrservice;
	}
}
