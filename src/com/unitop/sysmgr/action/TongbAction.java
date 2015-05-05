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
	 * �°���ʺ���ת
	 */
	public ActionForward fortongbbyhand(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//����Ա��Ϣ��ʽ��>json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
			return actionMapping.findForward("accountinfo.net.view.success");
	}
	
	
	/*
	 * �°���ʺŲ�ѯ
	 */
	public ActionForward net_select(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String zhangh = request.getParameter("zhangh");
			//�¾��˺�ת��
			if (zhangh != null&&zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
		try{
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			//��֤��ǰ��Ա�����˺�Ȩ��
			if(zhanghb==null)
			{
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "û���ҵ��˺�!");
			}
			boolean bool = this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),zhanghb.getJigh());
			if(!bool)
			{
				return super.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "û��Ȩ�޲鿴�˺�:["+zhanghb.getZhangh()+"]");
			}
			request.setAttribute("Zhanghb", zhanghb);
			} catch (Exception e) {
				return this.errrForLogAndException(e, actionMapping, request, "accountinfo.net.view.success");
			}finally{
				//����Ա��Ϣ��ʽ��>json
				String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
				request.setAttribute("jsonClerkrStr", jsonClerkrStr);
			}
			return actionMapping.findForward("accountinfo.net.view.success");
	}
	
	
	
	/*
	 * �°���˺Ų�ѯ excel����
	 */
	public ActionForward net_excel(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		//�¾��˺�ת��
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

			Label name = new Label(0, 0, "�˺�");
			sheet.addCell(name);
			name = new Label(0, 1, Zhanghb.getZhangh());
			sheet.addCell(name);

			name = new Label(1, 0, "������");
			sheet.addCell(name);
			name = new Label(1, 1, Zhanghb.getJigh());
			sheet.addCell(name);

			name = new Label(2, 0, "����");
			sheet.addCell(name);
			name = new Label(2, 1, Zhanghb.getHum());
			sheet.addCell(name);

			name = new Label(3, 0, "��ַ");
			sheet.addCell(name);
			name = new Label(3, 1, Zhanghb.getDiz());
			sheet.addCell(name);

			name = new Label(4, 0, "��������");
			sheet.addCell(name);
			name = new Label(4, 1, Zhanghb.getYouzbm());
			sheet.addCell(name);

			name = new Label(5, 0, "��������");
			sheet.addCell(name);
			name = new Label(5, 1, Zhanghb.getKaihrq());
			sheet.addCell(name);

			// name = new Label(6, 0, "��������");
			// sheet.addCell(name);
			// name = new Label(6, 1, Zhanghb.getQiyrq());
			// sheet.addCell(name);

			name = new Label(6, 0, "�ͻ���");
			sheet.addCell(name);
			name = new Label(6, 1, Zhanghb.getKehh());
			sheet.addCell(name);

			name = new Label(7, 0, "���Һ�");
			sheet.addCell(name);
			name = new Label(7, 1, Zhanghb.getHuobh());
			sheet.addCell(name);

			name = new Label(8, 0, "�Ƿ�ͨ��ͨ�һ�");
			sheet.addCell(name);
			name = new Label(8, 1, Zhanghb.getTongctd());
			sheet.addCell(name);

			name = new Label(9, 0, "�Ƿ���ӡ��");
			sheet.addCell(name);
			name = new Label(9, 1, Zhanghb.getYouwyj());
			sheet.addCell(name);

			name = new Label(10, 0, "�Ƿ���ӡ�����");
			sheet.addCell(name);
			name = new Label(10, 1, Zhanghb.getYouwzh());
			sheet.addCell(name);

			name = new Label(11, 0, "�˻�״̬");
			sheet.addCell(name);
			name = new Label(11, 1, Zhanghb.getZhanghshzt());
			sheet.addCell(name);

			name = new Label(12, 0, "ӡ��״̬");
			sheet.addCell(name);
			name = new Label(12, 1, Zhanghb.getYinjshzt());
			sheet.addCell(name);

			name = new Label(13, 0, "���״̬");
			sheet.addCell(name);
			name = new Label(13, 1, Zhanghb.getZuhshzt());
			sheet.addCell(name);

			name = new Label(14, 0, "��ע");
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
	 * ͬ��
	 */
	public ActionForward tongb(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String tongbzh = request.getParameter("tongbzh");
		//�¾��˺�ת��
		if (tongbzh != null&&tongbzh.length() != 17) {
			tongbzh = ZhanghbService.parseTypeN(tongbzh, 17);
		}
		

		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(tongbzh);
			
			if(zhanghb==null)
			{
				zhanghb = new Zhanghb();
				zhanghb.setZhangh(tongbzh);
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "�˺Ų�����!");
			}
			String zhanghzx = zhanghb.getZhanghxz();
			if(!"������".equals(zhanghzx)&&!"һ�㻧".equals(zhanghzx)&&!"��ʱ��".equals(zhanghzx)&&!"ר�û�".equals(zhanghzx)&&!"������".equals(zhanghzx))
			{
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "���˻����ʲ�����ͬ��!");
			}
			request.setAttribute("Zhanghb", zhanghb);
			
			//��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),zhanghb.getJigh());
			if(!bool)
			{
				return super.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "û��Ȩ�޲鿴�˺�:["+zhanghb.getZhangh()+"]");
			}
			if(!ZhanghbService.isHaveTrial(zhanghb)){
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "�˺Ŵ���δ�����״̬!");
			}
			Zhanghtbb zhanghtbb = ZhanghbService.ischange(tongbzh);
			if(zhanghtbb==null){
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "���˺�δ�����䶯������Ҫͬ����");
			}
//			//��ʼͬ��
			String sendMapStr = ZhanghbService.queryallfortongbu(tongbzh);
			log.info("Ҫ����ͬ�����˺�: "+zhanghb.getZhangh());
			String sendAddress = OrgService.getSocketaddByJigh(zhanghtbb.getShenghjgh()) ;
			log.info("����SOCKET��ַΪ��"+sendAddress);
			if(sendAddress==null||"".equals(sendAddress)){
				return this.showMessageJSP(actionMapping, request, "accountinfo.net.view.success", "���˺Ŷ�Ӧ֧ƱӰ��ϵͳͬ����ַδ���ã��޷�����ͬ����");
			}
			sendMapStr = Base64.encodeBytes(sendMapStr.getBytes());
			String receiveStr = ZhanghbService.toSocket(sendAddress,sendMapStr);
			log.info("�յ������ַ���Ϊ:"+receiveStr);
			
			String result = ZhanghbService.returnTongbu(receiveStr,tongbzh);
			String returnResult = "�˺ţ�"+tongbzh+"ͬ��ʧ��";
			if("success".equals(result)){
				returnResult = "�˺ţ�"+tongbzh+"ͬ���ɹ�";
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
			//����Ա��Ϣ��ʽ��>json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
		}
		return actionMapping.findForward("accountinfo.net.view.success");
	}
	
	/*
	 * ������ҳ����ת
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
	 * ͬ�������͹�Ա
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
			System.out.println("ִ��ʱ�䣺                "+time);
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
	 * ����ͨ����ͳ��
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
	 * �˻�ͨ����ͳ��
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
	 * �ܷ�ͬ��ӡ��
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
	 * ��ת�޸���������������Ϣ
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
							"bizview", "���򲻴���");
				}
				request.setAttribute("task", task);
				return actionMapping.findForward("modiTaskConfig");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, actionMapping, request, "bizview");
			}
			
	}
	
	/**
	 * �޸���������������Ϣ
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
							"bizview", "���򲻴���");
				}
				task.setTimetype(timetype);
				task.setTimevalue(timevalue);
				batchService.saveOrUpdate(task);
				request.setAttribute("task", task);
				taskList=batchService.getTaskList();
				request.setAttribute("tasklist", taskList);
				return super.showMessageJSP(actionMapping, request,
						"bizview", "�޸ĳ���["+task.getTaskname()+"]���ò����ɹ�");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return actionMapping.findForward("bizview");
			}
			
	}
	
	/**
	 * �����˻�����˻���־���ļ�
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