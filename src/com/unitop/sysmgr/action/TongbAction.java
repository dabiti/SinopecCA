package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.framework.util.DateTool;
import com.unitop.framework.util.JsonSystemTool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Nighttaskconfig;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhanghtbb;
import com.unitop.sysmgr.service.AccountImageServcie;
import com.unitop.sysmgr.service.BatchService;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.util.Base64;
import com.unitop.util.CoreBillUtils;
@Controller("/tongb")
public class TongbAction extends ExDispatchAction {
	
	@Resource
	private ZhanghbService ZhanghbService;
	@Resource
	private AccountImageServcie AccountImageServcie;
	@Resource
	private OrgService OrgService;
	@Resource
	private BatchService batchService;


	public boolean CanOperDesOrg(String DesOrg, String OperOrg)
	{
		return this.getSystemMgrService().CanOperDesOrg(OperOrg, DesOrg);
	}
	
	
	
	/*
	 * 新版简单帐号跳转
	 */
	public ActionForward fortongbbyhand(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//将柜员信息格式化>json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
			return actionMapping.findForward("accountinfo.net.view.success");
	}
	
	
	/*
	 * 新版简单帐号查询
	 */
	public ActionForward net_select(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String zhangh = request.getParameter("zhangh");
			//新旧账号转换
			if (zhangh != null&&zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
		try{
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			//验证当前柜员操作账号权限
			if(zhanghb==null)
			{
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "没有找到账号!");
			}
			boolean bool = this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),zhanghb.getJigh());
			if(!bool)
			{
				return super.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "没有权限查看账号:["+zhanghb.getZhangh()+"]");
			}
			request.setAttribute("Zhanghb", zhanghb);
			} catch (Exception e) {
				return this.errrForLogAndException(e, actionMapping, request, "accountinfo.net.view.success");
			}finally{
				//将柜员信息格式化>json
				String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
				request.setAttribute("jsonClerkrStr", jsonClerkrStr);
			}
			return actionMapping.findForward("accountinfo.net.view.success");
	}
	
	
	
	/*
	 * 新版简单账号查询 excel下载
	 */
	public ActionForward net_excel(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		//新旧账号转换
		if (zhangh != null&&zhangh.length() != 17) {
			zhangh =ZhanghbService.parseTypeN(zhangh, 17);
		}
		try {
			Zhanghb Zhanghb = ZhanghbService.getZhanghb(zhangh);
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;   filename=\"log.csv\"");
			WritableWorkbook book;
			book = Workbook.createWorkbook(response.getOutputStream());

			WritableSheet sheet = book.createSheet("Accountinfo", 0);

			Label name = new Label(0, 0, "账号");
			sheet.addCell(name);
			name = new Label(0, 1, Zhanghb.getZhangh());
			sheet.addCell(name);

			name = new Label(1, 0, "机构号");
			sheet.addCell(name);
			name = new Label(1, 1, Zhanghb.getJigh());
			sheet.addCell(name);

			name = new Label(2, 0, "户名");
			sheet.addCell(name);
			name = new Label(2, 1, Zhanghb.getHum());
			sheet.addCell(name);

			name = new Label(3, 0, "地址");
			sheet.addCell(name);
			name = new Label(3, 1, Zhanghb.getDiz());
			sheet.addCell(name);

			name = new Label(4, 0, "邮政编码");
			sheet.addCell(name);
			name = new Label(4, 1, Zhanghb.getYouzbm());
			sheet.addCell(name);

			name = new Label(5, 0, "开户日期");
			sheet.addCell(name);
			name = new Label(5, 1, Zhanghb.getKaihrq());
			sheet.addCell(name);

			// name = new Label(6, 0, "启用日期");
			// sheet.addCell(name);
			// name = new Label(6, 1, Zhanghb.getQiyrq());
			// sheet.addCell(name);

			name = new Label(6, 0, "客户号");
			sheet.addCell(name);
			name = new Label(6, 1, Zhanghb.getKehh());
			sheet.addCell(name);

			name = new Label(7, 0, "货币号");
			sheet.addCell(name);
			name = new Label(7, 1, Zhanghb.getHuobh());
			sheet.addCell(name);

			name = new Label(8, 0, "是否通存通兑户");
			sheet.addCell(name);
			name = new Label(8, 1, Zhanghb.getTongctd());
			sheet.addCell(name);

			name = new Label(9, 0, "是否有印鉴");
			sheet.addCell(name);
			name = new Label(9, 1, Zhanghb.getYouwyj());
			sheet.addCell(name);

			name = new Label(10, 0, "是否有印鉴组合");
			sheet.addCell(name);
			name = new Label(10, 1, Zhanghb.getYouwzh());
			sheet.addCell(name);

			name = new Label(11, 0, "账户状态");
			sheet.addCell(name);
			name = new Label(11, 1, Zhanghb.getZhanghshzt());
			sheet.addCell(name);

			name = new Label(12, 0, "印鉴状态");
			sheet.addCell(name);
			name = new Label(12, 1, Zhanghb.getYinjshzt());
			sheet.addCell(name);

			name = new Label(13, 0, "组合状态");
			sheet.addCell(name);
			name = new Label(13, 1, Zhanghb.getZuhshzt());
			sheet.addCell(name);

			name = new Label(14, 0, "备注");
			sheet.addCell(name);
			name = new Label(14, 1, Zhanghb.getBeiz());
			sheet.addCell(name);

			book.write();
			book.close();
			request.getRequestDispatcher("").forward(request, response);
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
		return null;
	}
	
	
	/*
	 * 同步
	 */
	public ActionForward tongb(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String tongbzh = request.getParameter("tongbzh");
		//新旧账号转换
		if (tongbzh != null&&tongbzh.length() != 17) {
			tongbzh = ZhanghbService.parseTypeN(tongbzh, 17);
		}
		

		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(tongbzh);
			
			if(zhanghb==null)
			{
				zhanghb = new Zhanghb();
				zhanghb.setZhangh(tongbzh);
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "账号不存在!");
			}
			String zhanghzx = zhanghb.getZhanghxz();
			if(!"基本户".equals(zhanghzx)&&!"一般户".equals(zhanghzx)&&!"临时户".equals(zhanghzx)&&!"专用户".equals(zhanghzx)&&!"其它户".equals(zhanghzx))
			{
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "该账户性质不允许同步!");
			}
			request.setAttribute("Zhanghb", zhanghb);
			
			//验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),zhanghb.getJigh());
			if(!bool)
			{
				return super.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "没有权限查看账号:["+zhanghb.getZhangh()+"]");
			}
			if(!ZhanghbService.isHaveTrial(zhanghb)){
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "账号存在未审审核状态!");
			}
			Zhanghtbb zhanghtbb = ZhanghbService.ischange(tongbzh);
			if(zhanghtbb==null){
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "该账号未发生变动，不需要同步！");
			}
//			//开始同步
			String sendMapStr = ZhanghbService.queryallfortongbu(tongbzh);
			log.info("要发送同步的账号: "+zhanghb.getZhangh());
			String sendAddress = OrgService.getSocketaddByJigh(zhanghtbb.getShenghjgh()) ;
			log.info("发送SOCKET地址为："+sendAddress);
			if(sendAddress==null||"".equals(sendAddress)){
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "该账号对应支票影像系统同步地址未设置，无法进行同步！");
			}
			sendMapStr = Base64.encodeBytes(sendMapStr.getBytes());
			String receiveStr = ZhanghbService.toSocket(sendAddress,sendMapStr);
			log.info("收到返回字符串为:"+receiveStr);
			
			String result = ZhanghbService.returnTongbu(receiveStr,tongbzh);
			String returnResult = "账号："+tongbzh+"同步失败";
			if("success".equals(result)){
				returnResult = "账号："+tongbzh+"同步成功";
			}
			if(returnResult!=null&&!"".equals(returnResult)){
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", returnResult);
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "accountinfo.net.view.success");

		}catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "accountinfo.net.view.success");

		}finally{
			//将柜员信息格式化>json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
		}
		return actionMapping.findForward("accountinfo.net.view.success");
	}
	
	/*
	 * 批处理页面跳转
	 */
	public ActionForward forBizView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			List<Nighttaskconfig> taskList=batchService.getTaskList();
			String yestoday=this.getSystemMgrService().getSystetemYestoDay().substring(0,10);
			request.setAttribute("today", yestoday);
			request.setAttribute("tasklist", taskList);
			return actionMapping.findForward("bizview");
	}
	
	/**
	 * 同步机构和柜员
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward tongbOrgAndClerk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream	out=null;
		try {
			request.setCharacterEncoding("GBK");
			out=response.getOutputStream();
			Long now= System.currentTimeMillis();
			String reslut=batchService.tongb();
			Long end=System.currentTimeMillis();
			String time=(end-now)/60000+"";
			System.out.println("执行时间：                "+time);
			batchService.updateTaskStatu("3",reslut);
			out.write(reslut.getBytes());
			out.flush();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 机构通过率统计
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward jigtgltj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream	out=null;
		try {
			request.setCharacterEncoding("GBK");
			out=response.getOutputStream();
			String reslut=batchService.executeSqlForCall("{call jigtgltj_pro()}", new HashMap());
			batchService.updateTaskStatu("2",reslut);
			out.write(reslut.getBytes());
			out.flush();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	/**
	 * 账户通过率统计
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward zhanghtgltj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream	out=null;
		try {
			request.setCharacterEncoding("GBK");
			out=response.getOutputStream();
			String reslut=batchService.executeSqlForCall("{call zhanghtgltj_pro()}", new HashMap());
			batchService.updateTaskStatu("1",reslut);
			out.write(reslut.getBytes());
			out.flush();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	/**
	 * 总分同步印鉴
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward zftongb(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream	out=null;
		
			
		try {
			request.setCharacterEncoding("GBK");
			out=response.getOutputStream();
			boolean result=batchService.zftongb();
			
			String i= result?"0":"1";
			batchService.updateTaskStatu("4",i);
			out.write(i.getBytes());
			out.flush();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	
	/**
	 * 跳转修改日终跑批配置信息
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toModiTaskConfig(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			try {
				request.setCharacterEncoding("GBK");
				String id =request.getParameter("id");
				List<Nighttaskconfig> taskList=batchService.getTaskList();
				request.setAttribute("tasklist", taskList);
				Nighttaskconfig task=batchService.getNightTaskById(id);
				if(task==null){
					return super.showMessageJSP(actionMapping, request,
							"bizview", "程序不存在");
				}
				request.setAttribute("task", task);
				return actionMapping.findForward("modiTaskConfig");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, actionMapping, request, "bizview");
			}
			
	}
	
	/**
	 * 修改日终跑批配置信息
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateTaskConfig(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			try {
				request.setCharacterEncoding("GBK");
				String yestoday=this.getSystemMgrService().getSystetemYestoDay().substring(0,10);
				request.setAttribute("today", yestoday);
				String id =request.getParameter("id");
				String timetype=request.getParameter("timetype");
				String timevalue=request.getParameter("timevalue");

				List<Nighttaskconfig> taskList=batchService.getTaskList();
				request.setAttribute("tasklist", taskList);
				Nighttaskconfig task=batchService.getNightTaskById(id);
				if(task==null){
					return super.showMessageJSP(actionMapping, request,
							"bizview", "程序不存在");
				}
				task.setTimetype(timetype);
				task.setTimevalue(timevalue);
				batchService.saveOrUpdate(task);
				request.setAttribute("task", task);
				taskList=batchService.getTaskList();
				request.setAttribute("tasklist", taskList);
				return super.showMessageJSP(actionMapping, request,
						"bizview", "修改程序["+task.getTaskname()+"]配置参数成功");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return actionMapping.findForward("bizview");
			}
			
	}
	
	/**
	 * 导出账户表和账户日志表文件
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTxt(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream	out=null;
		String today=this.getSystemMgrService().getSystetemNowDate();
		String workdate=request.getParameter("workdate");
		today=DateTool.toSimpleFormatToddmmyyyy(workdate);
		String zhanghFile=SystemConfig.getInstance().getValue("zhanghFilePath")+"YANYIN-ZHANGHB_"+today+".TXT";
		String accountmanagelogFile=SystemConfig.getInstance().getValue("zhanghrzFilePath")+"YANYIN-ACCOUNTMANAGELOG_"+today+".TXT";
		try {
			request.setCharacterEncoding("GBK");
			out=response.getOutputStream();
			boolean result=batchService.exportFile(zhanghFile,accountmanagelogFile,workdate);
			String i= result?"0":"1";
			batchService.updateTaskStatu("5",i);
			out.write(i.getBytes());
			out.flush();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
			
	}
	
	
	
	
	
	
	
	
}