package com.unitop.sysmgr.action;

import java.io.IOException;
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

import com.unitop.sysmgr.bo.Ci_renwxx;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhengprz;
import com.unitop.sysmgr.form.BatchSealCheckForm;
import com.unitop.sysmgr.service.BatchSealCheckService;
import com.unitop.sysmgr.service.YanyinLogService;
import com.unitop.sysmgr.service.ZhanghbService;

@Controller("/batchsealcheck")
public class BatchSealCheckAction extends ExDispatchAction {

	
	@Resource
	private BatchSealCheckService batchService;
	@Resource
	private YanyinLogService YanyinLogService;
	@Resource
	private ZhanghbService zhanghbService;
	
	public ActionForward queryFinishResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		
		
		BatchSealCheckForm bc = (BatchSealCheckForm)form;
		String batchid = bc.getBatchid();
		String systemid = bc.getSystemid();

		
		String returnJson = "error!";
		
		if(batchid!=null&&!"".equals(batchid)&&systemid!=null&&!"".equals(systemid)){
			
			//该批次是否已全部验完
			String result = batchService.getCountFromList(batchid );
			
			returnJson = batchService.createFinishResult(batchid, systemid, result);
		
		}else{
			returnJson = "lost param [batchid] or [systemid]!";
		}
		
		
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(returnJson);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		};
		
		return null;
	}
	
	
	
	
	public ActionForward queryCheckResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		
		BatchSealCheckForm bc = (BatchSealCheckForm)form;
		String batchid = bc.getBatchid();
		String systemid = bc.getSystemid();
		
		String returnXml = "error!";
		
		if(batchid!=null&&!"".equals(batchid)&&systemid!=null&&!"".equals(systemid)){
			List<Ci_renwxx> bscTotal = batchService.getRenwxxList(batchid,null);
			if(bscTotal!=null&&bscTotal.size()!=0){
				bscTotal = batchService.addYyrzToRenwxx(bscTotal);
			}
			
			returnXml = batchService.convertCheckResultToXml(systemid, batchid, bscTotal);
		}else{
			returnXml = "lost param [batchid] or [systemid]!";
		}
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(returnXml);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		};
		
		return null;
	}
	
	
	//更新手工结果
	public ActionForward updateCheckResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		
		BatchSealCheckForm bc = (BatchSealCheckForm)form;
		String taskid1 = bc.getTaskid1();
		String taskid2 = bc.getTaskid2();
		String clerkid1 = bc.getClerkid1();
		String clerkid2 = bc.getClerkid2();
		
		String returnXml = "error!";
		
		if(taskid1!=null&&taskid2!=null&&clerkid1!=null&&clerkid2!=null&&!"".equals(taskid1)&&!"".equals(taskid2)&&!"".equals(clerkid1)&&!"".equals(clerkid2)){
			Ci_renwxx task1 = batchService.getRenwxx(taskid1);
			Zhengprz task2 = YanyinLogService.getZhengp(taskid2);
			if(task1!=null&&task2!=null){
				boolean isOk = batchService.updateCheckResult(task1,task2, clerkid1, clerkid2);
				if(isOk){
					returnXml = "0000";
				}
			}else{
				returnXml = "taskid1 or taskid2 is not exist !";
			}
		}else{
			returnXml = "lost param [taskid1] or [taskid2] or [clerkid1] or [clerkid2]!";
		}
		
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(returnXml);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		};
		
		return null;
	}
	
	
	//在验印结果中添加复核柜员
	public ActionForward addClerk2OfCheckResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		
		BatchSealCheckForm bc = (BatchSealCheckForm)form;
		String systemid = bc.getSystemid();
		String tasktype = bc.getTasktype();
		String taskid1 = bc.getTaskid1();
		String taskid2 = bc.getTaskid2();
		String clerkid2 = bc.getClerkid2();
		
		String returnXml = "unknow error!";
		
		
		if(tasktype!=null&&!"".equals(tasktype)){
			if("1".equals(tasktype)){
				if(taskid1!=null&&!"".equals(taskid1)){
					Zhengprz task1 = YanyinLogService.getZhengp(taskid1);					
					if(task1!=null){
						boolean isOk = YanyinLogService.addClerk2OfZhengprz(taskid1, clerkid2);
						if(isOk){
							returnXml = "0000";
						}else{
							returnXml = "add clerk2 error !";
						}
					}else{
						returnXml = "task is not exist !";
					}
				
				}else{
					returnXml = "[taskid1] is not found !";
				}
			}else if("2".equals(tasktype)){
				if(taskid2!=null&&!"".equals(taskid2)&&systemid!=null&&!"".equals(systemid)&&clerkid2!=null&&!"".equals(clerkid2)){
					Ci_renwxx task2 = batchService.getRenwxxById(systemid,taskid2);
					if(task2!=null){
						boolean isOk = batchService.addClerk2OfRenwxx(systemid, taskid2, clerkid2);
						if(isOk){
							returnXml = "0000";
						}else{
							returnXml = "add clerk2 error !";
						}
					}else{
						returnXml = "task is not exist !";
					}
				}else{
					returnXml = "[taskid2] or [clerkid2] or [systemid] is not found !";
				}
				
			}else{
				returnXml = "error [tasktype] !";
			}
		}else{
			returnXml = "[tasktype] is not found !";
		}
		
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(returnXml);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
		
		return null;
	}
	
	
	
	//查询是否可通兑(接口)
	public ActionForward queryCandoForYanyin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		
		BatchSealCheckForm bc = (BatchSealCheckForm)form;
		String clerkid1 = bc.getClerkid1();
		String account = bc.getAccount();
		
		String returnXml = "unknow error!";
		
		if(clerkid1!=null&&!"".equals(clerkid1)&&account!=null&&!"".equals(account)){
			try{
				// 新旧账号转换
				if (account != null && account.length() != 17) {
					account = zhanghbService.parseTypeN(account, 17);
				}
				Zhanghb zhanghb =  zhanghbService.getZhanghb(account);
				if(zhanghb==null){
					returnXml = "account is not exist in YANYIN system !";
				}
				Clerk clerk  = clerkService.getClerkByCode(clerkid1);
				if("".equals(clerk.getCode())){
					clerk = null;
				}
				if(clerk==null){
					returnXml = "clerkid1 is not exist in YANYIN system !";
				}
				if(zhanghb!=null&&clerk!=null){
					boolean bool = this.getSystemMgrService().CanTongd(
							clerkid1, account);
					if(bool){
						returnXml = "1";
					}else{
						returnXml = "0";
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				returnXml = "exception has accured !" ;
			}
		}else{
			returnXml = "[clerkid1] or [account] is not found !";
		}
		
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(returnXml);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		};
		
		return null;
	}
}