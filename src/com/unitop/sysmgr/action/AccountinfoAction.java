package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.stereotype.Controller;

import sun.util.logging.resources.logging;

import com.unitop.config.SystemConfig;
import com.unitop.config.ZhanghxzConfig;
import com.unitop.exception.BusinessException;
import com.unitop.framework.util.DateTool;
import com.unitop.framework.util.JsonSystemTool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.PiaojyxwjbId;
import com.unitop.sysmgr.bo.StringVO;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhanghxzb;
import com.unitop.sysmgr.bo.qianzhi.Head;
import com.unitop.sysmgr.bo.qianzhi.Msg00400;
import com.unitop.sysmgr.bo.qianzhi.Msg29178;
import com.unitop.sysmgr.bo.qianzhi.Msg29179;
import com.unitop.sysmgr.dao.impl.YinjbDaoImpl;
import com.unitop.sysmgr.form.AccountinfoForm;
import com.unitop.sysmgr.service.AccountImageServcie;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.ZhanghbServiceImpl;
import com.unitop.util.DesUtil;

@Controller("/accountinfo")
public class AccountinfoAction extends ExDispatchAction {
	private static Logger log = Logger.getLogger(AccountinfoAction.class);
	@Resource
	private ZhanghbService ZhanghbService;
	@Resource
	private AccountImageServcie AccountImageServcie;
	
//	private byte [] buf;
	public boolean CanOperDesOrg(String DesOrg, String OperOrg) {
		return this.getSystemMgrService().CanOperDesOrg( DesOrg,OperOrg);
	}

	/*
	 * �°���ʺ���ת
	 */
	public ActionForward net_view(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		// ����Ա��Ϣ��ʽ��>json
		String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
		request.setAttribute("jsonClerkrStr", jsonClerkrStr);
		return actionMapping.findForward("accountinfo.net.view.success");
	}

	
	public ActionForward ForimportOldAccountinfo_excel(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("uploadOldaccountinfo_excel");
	}
	
	public ActionForward ForimportAccountinfo_excel(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("uploadaccountinfo_excel");
	}

	/**
	 * �����¾ɼ��˻�������Ϣ (������ʣ�excle)
	 */
	public ActionForward importOldAccountinfo_excel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("��ʼ�����¾ɼ��˺Ŷ�����Ϣ��");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		FormFile file = (FormFile) accountinfoform.getFile();
		InputStream input = null;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String shenghh=clerk.getShOrgCode();
	
		try{
			input = file.getInputStream();
			int size = input.available();
			if (size <= 0) {
				return this.showMessageJSP(mapping, request, "uploadOldaccountinfo_excel",
						"�ϴ��ļ�Ϊ����Ϊ��!");
			}
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
		//	boolean isImport = false;
			List<String> errorlist = ZhanghbService.importZhanghzhb(sheet);
			ZhanghbService.deleteZhanghb_Error(shenghh, "1");
			String content="��Ա["+clerk.getCode()+"]����һ������["+clerk.getShOrgCode()+"]���¾ɼ��˻�������Ϣ(Excel)";
			this.createManageLog(clerk.getCode(), content);
			log.info("�����¾ɼ��˺Ŷ�����Ϣ�����");
				if(errorlist!=null&&errorlist.size()>0){
					ZhanghbService.inserZhanghb_Error(shenghh, errorlist, "1");
					request.setAttribute("file", "0");
					return this.showMessageJSP(mapping, request,
							"uploadOldaccountinfo_excel", "����ɹ�,"+errorlist.size()+"���˻����ڸ�ʽ������Ϣ!");
				}else{
					return  this.showMessageJSP(mapping, request,
						"uploadOldaccountinfo_excel", "����ɹ�,�޸�ʽ������Ϣ!");
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request,
			"uploadOldaccountinfo_excel");
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * ��ѯ�����б�
	 */
	public ActionForward getZhanghb_Error(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String shenghh=clerk.getShOrgCode();
		String type=request.getParameter("type");
		PrintWriter out=null;
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-disposition",
		"attachment;filename=errorInfo.txt" );
		response.setContentType("application/text");
		try{
			out=response.getWriter();
			if(shenghh==null||"".equals(shenghh.trim())){
				out.print("��Ա������һ�����кţ������Ա��Ϣ");
				out.flush();
				return  null;
			}
				List<String> errorlist=ZhanghbService.getZhanghb_Error(shenghh, type);//��Excel�е���Ϣת��У���ʽ����zhanghb_temp
				Collections.sort(errorlist);
				for (String string : errorlist) {
					out.print(string+"\r\n");
				}
				out.flush();
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			out.print("��ѯ����!");
			out.print(e.getMessage());
			out.flush();
			return null;
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	/**
	 * �����˻���Ϣ (������ʣ�excle)
	 */
	public ActionForward importAccountinfo_excel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("��ʼ��������˺���Ϣ��");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		FormFile file = (FormFile) accountinfoform.getFile();
		InputStream input = null;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String shenghh=clerk.getShOrgCode();
		PrintWriter out=null;
		if(shenghh==null||"".equals(shenghh.trim())){
			return  this.showMessageJSP(mapping, request,
					"uploadaccountinfo_excel", "��Աһ�����кŲ����ڣ������Ա��Ϣ!");
		}
		try{
			input = file.getInputStream();
			int size = input.available();
			if (size <= 0) {
				return this.showMessageJSP(mapping, request, "uploadaccountinfo_excel",
						"�ϴ��ļ�Ϊ����Ϊ��!");
			}
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			boolean isImport = false;
			isImport = ZhanghbService.importZhanghbTemp(shenghh,sheet);
			if(isImport){
				String content="��Ա["+clerk.getCode()+"]����һ������["+clerk.getShOrgCode()+"]���˻���Ϣ(Excel)";
				this.createManageLog(clerk.getCode(), content);
				List<Zhanghb> zhanghb_excel_list=ZhanghbService.getZhanghbExcelInfo(shenghh);
				List<String> errorlist=ZhanghbService.checkZhanghbExcelInfo(zhanghb_excel_list);//��Excel�е���Ϣת��У���ʽ����zhanghb_temp
				Collections.sort(errorlist);
				ZhanghbService.deleteZhanghb_Excel(shenghh);//ɾ��Excel��ʱ��
				ZhanghbService.deleteZhanghb_Error(shenghh,"0");//ɾ��Excel��ʱ��
				log.info("��ʼ��������˺���Ϣ�����");
				if(errorlist!=null&&errorlist.size()>0){
					ZhanghbService.inserZhanghb_Error(shenghh, errorlist, "0");
					request.setAttribute("file", "0");
					 return  this.showMessageJSP(mapping, request,
							"uploadaccountinfo_excel", "����ɹ�,"+errorlist.size()+"���˻����ڸ�ʽ������Ϣ!");
				}else{
					return  this.showMessageJSP(mapping, request,
						"uploadaccountinfo_excel", "����ɹ�,�޸�ʽ������Ϣ!");
				}
			}else{
				return  this.showMessageJSP(mapping, request,
						"uploadaccountinfo_excel", "����ʧ��,�����³���!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request,
			"uploadaccountinfo_excel");
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
		}
	}
	
	// ���н����˻���Ϣ���Կ��ٿ���ҳ��
	public ActionForward getAccountForAjax_Excel(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String account = request.getParameter("account");
		String accountname = "";
		String zhanghxz = "";
		String kaihrq = "";
		String allexchange = "";
		String linkman = "";
		String phone = "";
		String fuzr = "";
		String fuzrdh = "";
		String beiz = "";
		String yinjkbh = "";
		String yinjkbh2 = "";
		String yinjkbh3 = "";
		String yinjkbh4 = "";
		String gongy = "";
		String zhanghzt = "";
		String yinjshzt = "";
		String youwzh = "";
		String hasYS = "";
		String accorgno = "";
		String youwYJ="";
		String tongd="0";
		String jiankgy="";
		String qiyrq="";

		String linkman2 = "";
		String phone2 = "";
		String fuzr2 = "";
		String fuzrdh2 = "";
		String shifdh="";//0����  1����

		String zhuzh="";
		
		String qh1="";
		String qh2="";
		String qh3="";
		String qh4="";
		String fjh1="";
		String fjh2="";
		String fjh3="";
		String fjh4="";
		String fuyrq="";
		
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		StringBuffer accountin = new StringBuffer();
		// �¾��˺�ת��
		if (account != null && account.length() != 17) {
			account = ZhanghbService.parseTypeN(account, 17);
		}
		try {
			out = response.getWriter();
			
			Zhanghb accountinfo = ZhanghbService.getZhanghbTemp(account);
			
			if (accountinfo == null) {
				out.print("fail002");
				out.close();
				return null;
			}
			if (accountinfo != null) {
				boolean bool = this.getSystemMgrService().CanOperDesOrg(
						clerk.getOrgcode(), accountinfo.getJigh());
				if (!bool) {
					out.print("fail001");
					out.close();
					return null;
				}
				accountname = accountinfo.getHum() == null ? "" : accountinfo
						.getHum();
				allexchange = accountinfo.getTongctd() == null ? ""
						: accountinfo.getTongctd();
				zhanghxz = accountinfo.getZhanghxz() == null ? "" : accountinfo
						.getZhanghxz();
				kaihrq = accountinfo.getKaihrq() == null ? "" : accountinfo
						.getKaihrq();
				linkman = accountinfo.getLianxr() == null ? "" : accountinfo
						.getLianxr();

				phone = accountinfo.getDianh() == null ? "" : accountinfo
						.getDianh();
				fuzr = accountinfo.getFuzr() == null ? "" : accountinfo
						.getFuzr();
				fuzrdh = accountinfo.getFuzrdh() == null ? "" : accountinfo
						.getFuzrdh();
				

				linkman2 = accountinfo.getLianxr2() == null ? "" : accountinfo
						.getLianxr2();

				phone2 = accountinfo.getDianh2() == null ? "" : accountinfo
						.getDianh2();
				fuzr2 = accountinfo.getFuzr2() == null ? "" : accountinfo
						.getFuzr2();
				fuzrdh2 = accountinfo.getFuzrdh2() == null ? "" : accountinfo
						.getFuzrdh2();
				shifdh=accountinfo.getShifdh()==null?"0":accountinfo.getShifdh();//Ϊ��Ĭ��Ϊ��
				
				beiz = accountinfo.getBeiz() == null ? "" : accountinfo
						.getBeiz();
				// new
				accorgno = accountinfo.getJigh() == null ? "" : accountinfo
						.getJigh();
				zhanghzt = accountinfo.getZhanghzt() == null ? "" : accountinfo
						.getZhanghzt();
				yinjshzt = accountinfo.getYinjshzt() == null ? "" : accountinfo
						.getYinjshzt();
				youwzh = accountinfo.getYouwzh() == null ? "" : accountinfo
						.getYouwzh();
				youwYJ=accountinfo.getYouwyj() == null ? "��" : accountinfo
						.getYouwyj();
				yinjshzt=accountinfo.getYinjshzt() == null ? "δ��" : accountinfo
						.getYouwyj();
				
				yinjkbh = accountinfo.getYinjkbh() == null ? "" : accountinfo
						.getYinjkbh();
				zhuzh=accountinfo.getZhuzh() == null ? "" : accountinfo
						.getZhuzh();
				gongy = accountinfo.getZhuzh() == null
						|| accountinfo.getZhuzh() == "" ? "0" : "1";
				
				 qh1=bulidAreaCode(accountinfo.getLianxrqh());
				 qh2=bulidAreaCode(accountinfo.getLianxrqh2());
				 qh3=bulidAreaCode(accountinfo.getFuzrqh());
				 qh4=bulidAreaCode(accountinfo.getFuzrqh2());
				 fjh1=accountinfo.getLianxrfjh()== null ? "" : accountinfo.getLianxrfjh();
				 fjh2=accountinfo.getLianxrfjh2()== null ? "" : accountinfo.getLianxrfjh2();
				 fjh3=accountinfo.getFuzrfjh()== null ? "" : accountinfo.getFuzrfjh();
				 fjh4=accountinfo.getFuzrfjh2()== null ? "" : accountinfo.getFuzrfjh2();
				 fuyrq=accountinfo.getFuyrq()==null?"":accountinfo.getFuyrq();
				if (yinjkbh != null && yinjkbh.length() != 0
						&& yinjkbh.indexOf("|") != -1) {
					yinjkbh = yinjkbh.replace('|', ',');
					List<String> yinjkhList = createYinjkhList(yinjkbh);
					yinjkbh = yinjkhList.get(0);
					yinjkbh2 = yinjkhList.get(1);
					yinjkbh3 = yinjkhList.get(2);
					yinjkbh4 = yinjkhList.get(3);
				}
			}
			accountin.append(accountname).append(",").append(zhanghxz).append(
					",").append(kaihrq).append(",").append(allexchange).append(
					",").append(linkman).append(",").append(phone).append(",")
					.append(fuzr).append(",").append(fuzrdh).append(",")
					.append(beiz).append(",").append(yinjkbh).append(",")
					.append(gongy).append(",").append(yinjkbh2).append(",")
					.append(yinjkbh3).append(",")
					.append(yinjkbh4)
					// new
					.append(",").append(zhanghzt).append(",").append(yinjshzt)
					.append(",").append(youwzh).append(",").append(hasYS)
					.append(",").append(accorgno).append(",").append(youwYJ)
					.append(",").append(yinjshzt).append(",").append(tongd).append(",")
					.append(jiankgy).append(",").append(qiyrq)
					//��new
					.append(",").append(linkman2).append(",").append(phone2)
					.append(",").append(fuzr2).append(",").append(fuzrdh2).append(",").append(shifdh).append(",").append(zhuzh).append(",").append(account)
					//20140724 �������� �ֻ���
					.append(",").append(qh1).append(",").append(fjh1)
					.append(",").append(qh2).append(",").append(fjh2).append(",").append(qh3).append(",").append(fjh3).append(",").append(qh4).append(",").append(fjh4).append(",").append(fuyrq).append(",").append(zhuzh);
			out.print(accountin.toString());
			out.close();
			return null;
		} catch (Exception e) {
			out.print("fail003");
			e.printStackTrace();

			out.close();
			return null;
		}
	}
	
	/**
	 * �˻����ٿ�����תҳ��
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward FortongbAccountinfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//��ϵͳʱ�䷵��ǰ̨ҳ��
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		AccountinfoForm accountinfoForm=(AccountinfoForm)actionForm;
		accountinfoForm.reset(actionMapping, request);
		//��ѯ�˻�����
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		return actionMapping.findForward("fastcreateaccount");
	}
	
	
	
	/**
	 * �˻����ٿ���
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward TongbAccountinfoFromTemp(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//��ϵͳʱ�䷵��ǰ̨ҳ��
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		try{
			ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
			request.setAttribute("zhanghxzlist", zhanghxzList);
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
			Zhanghb zhanghb = null;
			zhanghb = getNewZhanghb(accountinfoform);//ҳ����д�˻���Ϣ
			zhanghb.setJigh(clerk.getOrgcode());
			String zhuzh=zhanghb.getZhuzh();
			if(zhuzh!=null&&!"".equals(zhuzh)){
				Zhanghb zhanghb_=ZhanghbService.getZhanghb(zhanghb.getZhangh());//ϵͳ���˻���Ϣ
				if(zhanghb_!=null&&zhanghb_.getHum()!=null&&!"".equals(zhanghb_.getHum())){
					List yinjList=ZhanghbService.getYinjb(zhanghb_.getZhangh());
					if(yinjList!=null&&yinjList.size()!=0){
						return super.showMessageJSP(actionMapping, request,
								"fastcreateaccount", "�ѽ���ӡ���˻��������������˻�");
					}
				}
				Zhanghb zhuzhinfo=ZhanghbService.getZhanghb(zhuzh);
				if(zhuzhinfo==null||zhuzhinfo.getZhangh()==null||"".equals(zhuzhinfo.getZhangh())){
					return super.showMessageJSP(actionMapping, request,
							"fastcreateaccount", "�����˻���δ���������Ȳ��������˻�");
				}else if(zhuzhinfo.getYinjshzt()==null||"δ��".equals(zhuzhinfo.getYinjshzt())){
					return super.showMessageJSP(actionMapping, request,
							"fastcreateaccount", "�����˻�����������ӡ�������Ȳ��������˻�");
				}else if(zhuzhinfo.getZhuzh()!=null&&!"".equals(zhuzhinfo.getZhuzh().trim())){
					return super.showMessageJSP(actionMapping, request,
							"fastcreateaccount", "�ѹ��������˻�ӡ�����˻�����������");
				}
				//�������˻�ӡ����Ϣ
				zhanghb.setYouwyj(zhuzhinfo.getYouwyj());
				zhanghb.setYouwzh(zhuzhinfo.getYouwzh());
				zhanghb.setYinjshzt(zhuzhinfo.getYinjshzt());
				zhanghb.setZuhshzt(zhuzhinfo.getZuhshzt());
				zhanghb.setYinjkbh("");
				request.setAttribute("zhanghb", zhanghb);
				request.setAttribute("kaih", "gongy");
				
			}else{
				Zhanghb zhanghb_=ZhanghbService.getZhanghb(zhanghb.getZhangh());//ϵͳ���˻���Ϣ
				if(zhanghb_==null||zhanghb_.getHum()==null||"".equals(zhanghb_.getHum())){
					//ԭϵͳδ����
					List<String> yinjkbhList = getYinjkhList(accountinfoform);
					String yinjkbhStr = createYinjkbhStr(yinjkbhList);
					request.setAttribute("zhanghb", zhanghb);
					// ��鿪���˻��Ƿ���ӡ����
					boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
					if (exist) {
						return super.showMessageJSP(actionMapping, request,
								"fastcreateaccount", "ӡ�����ѱ�ʹ��");
					}
					zhanghb.setYinjkbh(yinjkbhStr);
				}else{
					//ԭϵͳ�ѿ���
					String zhuzhangh=zhanghb_.getZhuzh();
					
					if(zhuzhangh==null||"".equals(zhuzhangh)){
						//ԭϵͳ���˻��ǹ����˻�
						List<String> yinjkbhList = getYinjkhList(accountinfoform);
						String yinjkbhStr = createYinjkbhStr(yinjkbhList);
						request.setAttribute("zhanghb", zhanghb);
						// ��鿪���˻��Ƿ���ӡ����
						String yyinjk=zhanghb_.getYinjkbh()==null?"":zhanghb_.getYinjkbh();//ԭӡ������
						if(!yyinjk.equals(yinjkbhStr)){
							boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
							if (exist) {
								return super.showMessageJSP(actionMapping, request,
										"fastcreateaccount", "ӡ�����ѱ�ʹ��");
							}
						}
						zhanghb.setYinjkbh(yinjkbhStr);
						//����֮ǰ��ӡ����Ϣ
						zhanghb.setYouwyj(zhanghb_.getYouwyj());
						zhanghb.setYouwzh(zhanghb_.getYouwzh());
						zhanghb.setYinjshzt(zhanghb_.getYinjshzt());
						zhanghb.setZuhshzt(zhanghb_.getZuhshzt());
						
					}else{
						//ԭϵͳ���˻�Ϊ�����˻�
						List<String> yinjkbhList = getYinjkhList(accountinfoform);
						String yinjkbhStr = createYinjkbhStr(yinjkbhList);
						request.setAttribute("zhanghb", zhanghb);
						// ��鿪���˻��Ƿ���ӡ����
						boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
						if (exist) {
							return super.showMessageJSP(actionMapping, request,
									"fastcreateaccount", "ӡ�����ѱ�ʹ��");
						}
						zhanghb.setYinjkbh(yinjkbhStr);
					}
				}
				request.setAttribute("kaih", "kaih");
			}
			ZhanghbService.tongbAccountinfoFromTemp(zhanghb);
			String content="��Ա["+clerk.getCode()+"]ͬ��,�˻�["+zhanghb.getZhangh()+"]��Ϣ :("+createAccountInfo(zhanghb)+")";
			// ��¼���ٿ�����־
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "0", content,
							clerk);
			return super.showMessageJSP(actionMapping, request,
					"fastcreateaccount", "ͬ���˺�["+zhanghb.getZhangh()+"]����ӡϵͳ�ɹ�!");
		}catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
			"fastcreateaccount");
		}
	}
	
	
	private String createAccountInfo(Zhanghb zhanghb) {
		StringBuffer sb =new StringBuffer();
		sb.append("�˺ţ�").append(zhanghb.getZhangh()==null?"":zhanghb.getZhangh());
		sb.append(";������").append(zhanghb.getHum()==null?"":zhanghb.getHum());
		sb.append(";�˻����ʣ�").append(zhanghb.getZhanghxz()==null?"":zhanghb.getZhanghxz());
		sb.append(";�˻�״̬��").append(zhanghb.getZhanghzt()==null?"":zhanghb.getZhanghzt());
		sb.append(";ͨ�ұ�־��").append(zhanghb.getTongctd()==null?"":zhanghb.getTongctd());
		sb.append(";�������ڣ�").append(zhanghb.getKaihrq()==null?"":zhanghb.getKaihrq());
		sb.append(";�����˺ţ�").append(zhanghb.getZhuzh()==null?"":zhanghb.getZhuzh());
		sb.append(";�������ڣ�").append(zhanghb.getFuyrq()==null?"":zhanghb.getFuyrq());
		sb.append(";ӡ�����ţ�").append(zhanghb.getYinjkbh()==null?"":zhanghb.getYinjkbh());
		sb.append(";�Ƿ��ˣ�").append("0".equals(zhanghb.getShifdh())?"��":"��");
		sb.append(";��ע��").append(zhanghb.getBeiz()==null?"":zhanghb.getBeiz());
		sb.append(";��ϵ�ˣ�").append(zhanghb.getLianxr()==null?"":zhanghb.getLianxr());
		sb.append(";��ϵ�����ţ�").append(zhanghb.getLianxrqh()==null?"":zhanghb.getLianxrqh());
		sb.append(";��ϵ�˵绰��").append(zhanghb.getDianh()==null?"":zhanghb.getDianh());
		sb.append(";��ϵ�˷ֻ��ţ�").append(zhanghb.getLianxrfjh()==null?"":zhanghb.getLianxrfjh());
		sb.append(";��ϵ��2��").append(zhanghb.getLianxr2()==null?"":zhanghb.getLianxr2());
		sb.append(";��ϵ��2���ţ�").append(zhanghb.getLianxrqh2()==null?"":zhanghb.getLianxrqh2());
		sb.append(";��ϵ��2�绰��").append(zhanghb.getDianh2()==null?"":zhanghb.getDianh2());
		sb.append(";��ϵ��2�ֻ��ţ�").append(zhanghb.getLianxrfjh2()==null?"":zhanghb.getLianxrfjh2());
		sb.append(";�����ˣ�").append(zhanghb.getFuzr()==null?"":zhanghb.getFuzr());
		sb.append(";���������ţ�").append(zhanghb.getFuzrqh()==null?"":zhanghb.getFuzrqh());
		sb.append(";�����˵绰��").append(zhanghb.getFuzrdh()==null?"":zhanghb.getFuzrdh());
		sb.append(";�����˷ֻ��ţ�").append(zhanghb.getFuzrfjh()==null?"":zhanghb.getFuzrfjh());
		sb.append(";������2��").append(zhanghb.getFuzr2()==null?"":zhanghb.getFuzr2());
		sb.append(";������2���ţ�").append(zhanghb.getFuzrqh2()==null?"":zhanghb.getFuzrqh2());
		sb.append(";������2�绰��").append(zhanghb.getFuzrdh2()==null?"":zhanghb.getFuzrdh2());
		sb.append(";������2�ֻ��ţ�").append(zhanghb.getFuzrfjh2()==null?"":zhanghb.getFuzrfjh2());
		sb.append(";�Ƿ�ȶ�С�룺").append(zhanghb.getIschecksmall()==null||"0".equals(zhanghb.getIschecksmall())?"��":"��");
		return sb.toString();
	}

	/*
	 * �°���ʺŲ�ѯ
	 */
	public ActionForward net_select(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String zhangh = request.getParameter("zhangh");
		// �¾��˺�ת��
		if (zhangh != null && zhangh.length() != 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.view.success", "û��Ȩ�޲鿴�˺�:["
								+ zhanghb.getZhangh() + "]");
			}
			if (zhanghb == null) {
				return this.showMessageJSP(actionMapping, request,
						"accountinfo.net.view.success", "û���ҵ��˺�!");
			}
			request.setAttribute("Zhanghb", zhanghb);
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.view.success");
		} finally {
			// ����Ա��Ϣ��ʽ��>json
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
		// �¾��˺�ת��
		if (zhangh != null && zhangh.length() !=17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
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
	 * �˺���ϸ��ѯ .net
	 */
	public ActionForward scanAccountinfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			String zhangh = request.getParameter("zhangh");
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length() !=17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb Zhanghb = ZhanghbService.getZhanghb(zhangh);
			if((Zhanghb.getYinjkbh()==null||Zhanghb.getYinjkbh().trim().equals(""))&&(Zhanghb.getZhuzh()!=null&&!Zhanghb.getZhuzh().trim().equals(""))){
				Zhanghb zhuzh=ZhanghbService.getZhanghb(Zhanghb.getZhuzh());
				if(zhuzh==null){
					Zhanghb.setYinjkbh("");
				}else{
					Zhanghb.setYinjkbh(zhuzh.getYinjkbh());
				}
			}
			String dianh=makePhone(bulidAreaCode(Zhanghb.getLianxrqh()),Zhanghb.getDianh(),Zhanghb.getLianxrfjh());
			Zhanghb.setDianh(dianh);
			String dianh2=makePhone(bulidAreaCode(Zhanghb.getLianxrqh2()),Zhanghb.getDianh2(),Zhanghb.getLianxrfjh2());
			Zhanghb.setDianh2(dianh2);
			String dianh3=makePhone(bulidAreaCode(Zhanghb.getFuzrqh()),Zhanghb.getFuzrdh(),Zhanghb.getFuzrfjh());
			Zhanghb.setFuzrdh(dianh3);
			String dianh4=makePhone(bulidAreaCode(Zhanghb.getFuzrqh2()),Zhanghb.getFuzrdh2(),Zhanghb.getFuzrfjh2());
			Zhanghb.setFuzrdh2(dianh4);
			request.setAttribute("Zhanghb", Zhanghb);
			return actionMapping.findForward("accountinfo.net.scan.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.scan.success");
		} finally {
			// ����Ա��Ϣ��ʽ��>json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
		}
	}

	// �����ָ� ��ת
	public ActionForward recoverAccountForNetView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			return actionMapping.findForward("accountinfo.net.resume.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.resume.success");
		}

	}

	// �����ָ���Ϣ����
	public ActionForward getAccountInfoForResume(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String account = request.getParameter("account");
		Clerk clerk=(Clerk)(request.getSession().getAttribute("clerk"));
		// �¾��˺�ת��
		if (account != null && account.length() != 17) {
			account = ZhanghbService.parseTypeN(account, 17);
		}
		String accountname = "";
		String allexchange = "";
		String accountstate = "";
		String isZhuzh = "0";// 0:�������˻���1�����˻�
		String pass="1";
		String yzhanghxz="";
		String xiaohsj = "";
		String nowDate = getSystemMgrService().getSystetemNowDate();
		String today = nowDate.substring(0, 10);
		try {
			Zhanghb accountinfo = ZhanghbService.getZhanghb(account);
			if (accountinfo != null) {
				boolean bool = this.getSystemMgrService().CanOperDesOrg(
						clerk.getOrgcode(), accountinfo.getJigh());
				if(!bool){
					pass="0";
				}
				accountname = accountinfo.getHum();

				allexchange = accountinfo.getTongctd();
				accountstate = accountinfo.getZhanghzt();
				List zlist = ZhanghbService.getzzhlist(account);// ���˻��б�
				// �ж��˻��Ƿ�������˻�
				if (zlist != null && zlist.size() >= 1)
					isZhuzh = "1";
			
				yzhanghxz=ZhanghbService.queryXiaohqzt(account);
			
				
				if (accountinfo.getZhanghzt() != null
						&& accountinfo.getZhanghzt().equals("����")) {
					xiaohsj = ZhanghbService.getXiaohsj(account);
				}
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			StringBuffer msg = new StringBuffer();

			msg.append(accountname).append(",").append(allexchange).append(",")
					.append(accountstate).append(",").append(isZhuzh).append(
							",");
			msg.append(xiaohsj).append(",").append(today).append(",").append(pass).append(",").append(yzhanghxz).append(",").append(account);
			out.print(msg.toString());
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}

	}

	/**
	 * �����ָ�
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward recoverAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			String zhangh = accountinfoform.getAccount();
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "û��Ȩ�������ָ����˺�:["
								+ accountinfoform.getAccount() + "]");
			}
			String zhuzh=zhanghb.getZhuzh();
			if(zhuzh!=null&&zhuzh.trim().length()!=0){
				Zhanghb zzh=ZhanghbService.getZhanghb(zhuzh);
				if(zzh!=null&&zzh.getZhanghzt().equals("����")){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.resume.success", "���˻�["+zhuzh+"]�����������Ȼָ����˻�");
				}
			}
			// ��֤�˻�״̬
			if ("����".equals(zhanghb.getZhanghzt())) {
				String yzhanghzt=ZhanghbService.queryXiaohqzt(zhanghb.getZhangh());
				if(yzhanghzt.trim().equals("��������")){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.resume.success", "�˻�["+zhanghb.getZhangh()+"]���������棬�޷����������ָ�!");
				}
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("��Ч");
				zhanghbxzList.add("����");
				zhanghbxzList.add("����");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk,zhanghbxzList,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.resume.success", "�������������޷����˺�:["
									+ accountinfoform.getAccount() + "]���������ָ�!");
				}
				if(ZhanghbService.canResumeYinjk(zhangh)){
					List<String> yinjkhList=new ArrayList<String>();
					String yinjkbh =zhanghb.getYinjkbh();
					if(yinjkbh==null||yinjkbh.trim().equals("")){
						 zhuzh=zhanghb.getZhuzh();
						if(zhuzh!=null&&!zhuzh.trim().equals("")){
							yinjkbh=ZhanghbService.getZhanghb(zhuzh).getYinjkbh();
						}
					}
					yinjkhList=createYinjkhList(yinjkbh);
					//boolean pass = iCheckYinjkhXiaoK(zhangh, yinjkhList,"02", clerk);  //���ú��Ľ��ײ�ѯ
					//boolean	pass=true;
				//	if(!pass){
					//	return super.showMessageJSP(actionMapping, request,
						//		"accountinfo.net.resume.success", "�޷����������ָ�������ӡ����״̬���Ϸ�!");
					//}
					ZhanghbService.recoverAccount(zhanghb.getZhangh(),true,yzhanghzt);
				}else{
					ZhanghbService.recoverAccount(zhanghb.getZhangh(),false,yzhanghzt);
				}

				String content = "�˺�[" + zhanghb.getZhangh() + "]������תΪ"+yzhanghzt;
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "5", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "�����ָ��ɹ�!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "�˺ţ�["
								+ accountinfoform.getAccount()
								+ "]���ɽ��������ָ�,��ǰ״̬Ϊ��[" + zhanghb.getZhanghzt()
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.resume.success");
		}
	}

	// ���� ��ת
	public ActionForward coverAccountForNetView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Zhanghb zhanghb = (Zhanghb) request.getSession().getAttribute("zhanghb_zt");
		request.getSession().removeAttribute("zhanghb_zt");
		if (zhanghb == null) {
			return actionMapping.findForward("accountinfo.xiaohu");
		}
		if (zhanghb.getZhangh() == null || zhanghb.getZhangh().equals("")) {
			return actionMapping.findForward("accountinfo.xiaohu");
		}
		request.setAttribute("zhanghb", zhanghb);
		return actionMapping.findForward("accountinfo.xiaohu");
	}

	/**
	 * ����
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward coverAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String gszh=request.getParameter("gszh");
		try {
			String zhangh = accountinfoform.getAccount();
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "û��Ȩ���������˺�:["
								+ accountinfoform.getAccount() + "]!");
			}
			/*List<Zhanghb> zizhlist=ZhanghbService.getZhangbListByZzh(zhangh);
			if(zizhlist.size()>0){
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "�˺ţ�[" + zhanghb.getZhangh()
								+ "]����δ���������˻������ɽ�������");
			}*/
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// ��֤�˻�״̬
			if ("��Ч".equals(zhanghzt)||"����".equals(zhanghzt)||"��������".equals(zhanghzt)) {
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("����");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk, zhanghbxzList,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.xiaohu", "�����״̬�������˺�["
									+ accountinfoform.getAccount() + "]�޷�����!");
				}
				String yinjkbh = zhanghb.getYinjkbh();
				String zhuzh = zhanghb.getZhuzh();
				if (zhuzh != null && zhuzh.trim().length() != 0) {
					yinjkbh = ZhanghbService.getZhanghb(zhuzh).getYinjkbh();
				}
				if (yinjkbh != null && yinjkbh.length() != 0
						&& yinjkbh.indexOf("|") != -1) {
					yinjkbh = yinjkbh.replace('|', ',');
				}
				List<String> yinjkhList = createYinjkhList(yinjkbh);
				String content = "�˺�[" + zhanghb.getZhangh() + "]��"+zhanghzt+"תΪ����";
				//�ж��Ƿ���Ҫ������������
				if(ZhanghbService.canCancleYinjk(zhangh)){
				/*boolean pass = iCheckYinjkhXiaoK(zhangh, yinjkhList,"04", clerk);  //���ú��Ľ��ײ�ѯ
					if(pass){
						ZhanghbService.closeAccount(zhanghb.getZhangh(), yinjkhList);
						}else{
						//20140324 --qjk  �޸� ���Ŀ�δ�� ��ӡϵͳ������
						return super.showMessageJSP(actionMapping, request,
									"accountinfo.xiaohu", "�˺ţ�[" + zhanghb.getZhangh()
											+ "]���ɽ�������,����δ������������!");
						}*/
					content+="�����ϸ�ӡ����"+yinjkbh;
					//�ǹ��� ��δ�������˻���������
					ZhanghbService.closeAccount(zhanghb.getZhangh(), yinjkhList);
				}else{
					//�������˻����� ����ӡ��ת�Ƹ������˻�
					List<Zhanghb> zizhlist=ZhanghbService.getZhangbListByZzh(zhangh);
					if(zizhlist!=null&&zizhlist.size()>0){
						boolean pass=ZhanghbService.cancleZhuzh(zhanghb.getZhangh(),gszh);
						content+="������ӡ���������˺�["+gszh+"]";
						if(!pass){
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.xiaohu", "ӡ���ı�������˺�["+gszh+"]ʧ�ܣ������²���!");
						}
					}else{
					//�����˻�����
					zhanghb.setZhanghzt("����");
					zhanghb.setTingyrq(getSystemMgrService().getSystetemNowDate().substring(0, 10));
					ZhanghbService.update(zhanghb);
					}
				}
				
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "4", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "�����ɹ�!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "�˺ţ�[" + zhanghb.getZhangh()
								+ "]���ɽ�������,��ǰ״̬Ϊ��[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.xiaohu");
		}
	}


	// ת���� ��ת
	public ActionForward AccountToJiuxuanView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.jiuxuan");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.jiuxuan");
		}
	}

	/**
	 * �˻�ת����
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward AccountToJiuxuan(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			String zhangh = accountinfoform.getAccount();
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "û��Ȩ�޲����˺�:["
								+ accountinfoform.getAccount() + "]!");
			}
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// ��֤�˻�״̬
			if ("��Ч".equals(zhanghzt)) {
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("����");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk, zhanghbxzList,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.jiuxuan", "�����״̬�������޷��޸��˺�:["
									+ accountinfoform.getAccount() + "]״̬Ϊ����!");
				}
				zhanghb.setZhanghzt("����");
				ZhanghbService.update(zhanghb);
				String content = "�˺�[" + zhanghb.getZhangh() + "]��"+zhanghzt+"תΪ����";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "9", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "�޸��˻�["+zhanghb.getZhangh()+"]״̬Ϊ�����ɹ�!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "�˺ţ�[" + zhanghb.getZhangh()
								+ "]���ɽ����޸�,��ǰ״̬Ϊ��[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.jiuxuan");
		}
	}
	// �������� ��ת
	public ActionForward ZhangxacView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.zhangxac");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.zhangxac");
		}
	}

	/**
	 * ��������
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward Zhangxac(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			String zhangh = accountinfoform.getAccount();
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "û��Ȩ�޲����˺�:["
								+ accountinfoform.getAccount() + "]!");
			}
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// ��֤�˻�״̬
			if ("����".equals(zhanghzt)) {
				zhanghb.setZhanghzt("��������");
				ZhanghbService.update(zhanghb);
				String content = "�˺�[" + zhanghb.getZhangh() + "]�ɾ���תΪ��������";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "9", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "�޸��˻�["+zhanghb.getZhangh()+"]״̬Ϊ��������ɹ�!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "�˺ţ�[" + zhanghb.getZhangh()
								+ "]���ɽ����޸�,��ǰ״̬Ϊ��[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.zhangxac");
		}
	}


	// �����˻�ת���� ��ת
	public ActionForward AccountToNormalView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.normal");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.normal");
		}
	}

	/**
	 * �˻�ת����
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward AccountToNormal(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			String zhangh = accountinfoform.getAccount();
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "û��Ȩ�޲����˺�:["
								+ accountinfoform.getAccount() + "]!");
			}
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// ��֤�˻�״̬
			if ("����".equals(zhanghzt)) {
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("��Ч");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk,zhanghbxzList ,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.normal", "�����״̬�������޷��޸��˺�:["
									+ accountinfoform.getAccount() + "]״̬Ϊ��Ч!");
				}
				zhanghb.setZhanghzt("��Ч");
				ZhanghbService.update(zhanghb);
				String content = "�˺�[" + zhanghb.getZhangh() + "]��"+zhanghzt+"תΪ��Ч";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "9", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "�޸��˻�["+zhanghb.getZhangh()+"]״̬Ϊ��Ч�ɹ�!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "�˺ţ�[" + zhanghb.getZhangh()
								+ "]���ɽ����޸�,��ǰ״̬Ϊ��[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.normal");
		}
	}

	
	
	// �˺Ž�� ��ת
	public ActionForward jiegAccountForNetView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.net.zhanghjg.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghjg.success");
		}
	}

	// �˺Ž��
	public ActionForward jiegAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjg.success", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjg.success", "û��Ȩ�޽���˺�:["
								+ accountinfoform.getAccount() + "]");
			}
			
			// ��֤�˻�״̬
			if ("��ʧ".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.recoverAccount(zhanghb.getZhangh(),true);
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjg.success", "�˺ţ�["
								+ zhanghb.getZhangh() + "]���ɽ��н��,��ǰ״̬Ϊ��["
								+ zhanghb.getZhanghzt() + "]");
			}
			String content = "��ҳɹ�(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա����:"
					+ clerk.getCode() + ")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "�˻����", "�˻����",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghjg.success", "�˺Ž�ҳɹ�!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghjg.success");
		}
	}

	// �˺Žⶳ ��ת
	public ActionForward jiedAccountForNetView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.net.zhanghjd.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghjd.success");
		}
	}

	// �˺Žⶳ
	public ActionForward jiedAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjd.success", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjd.success", "û��Ȩ�������˺�:["
								+ accountinfoform.getAccount() + "]");
			}
			// ��֤�˻�״̬
			if ("����".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.recoverAccount(zhanghb.getZhangh(),true);
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjd.success", "�˺ţ�["
								+ zhanghb.getZhangh() + "]���ɽ��нⶳ,��ǰ״̬Ϊ��["
								+ zhanghb.getZhanghzt() + "]");
			}
			String content = "�ⶳ�ɹ�(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա����:"
					+ clerk.getCode() + ")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "�˻��ⶳ", "�˻��ⶳ",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghjd.success", "�˺Žⶳ�ɹ�!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghjd.success");
		}
	}

	// ����ɾ�� ��ת
	public ActionForward physicsDeleteForNetForView(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.net.physicsdelete.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.physicsdelete.success");
		}
	}

	// �°�����ɾ��
	public ActionForward physicsDeleteForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.physicsdelete.success", "�˻�������!");
			}
			// ��֤�˻��Ƿ�Ϊ���˻������˻����ܽ�������ɾ�� by wp
			List list = ZhanghbService.getzzhlist(accountinfoform.getAccount());
			if (list.size() > 0) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.physicsdelete.success",
						"��ǰ�˻�Ϊ���˻�������ִ������ɾ������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.physicsdelete.success", "û��Ȩ��ɾ���˺�:["
								+ accountinfoform.getAccount() + "]");
			}
			ZhanghbService.physicsdelete(zhanghb.getZhangh());
			String content = "�˻�����ɾ��(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա��:"
					+ clerk.getCode() + ")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "�˻�ɾ��", "�˻�ɾ��",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.physicsdelete.success", "�˻�����ɾ�������ɹ�!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.physicsdelete.success");
		}
	}

	/**
	 * 
	 * <dl>
	 * <dt><b>getAccountForNet:��.net���˻����л�ȡ�˻�����</b></dt>
	 * <dd></dd>
	 * </dl>
	 */
	public ActionForward getAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String account = request.getParameter("account");
		Clerk clerk =(Clerk)(request.getSession().getAttribute("clerk"));
		// �¾��˺�ת��
		if (account != null && account.length() != 17) {
			account = ZhanghbService.parseTypeN(account, 17);
		}
		String accountname = "";
		String allexchange = "";
		String tingyrq = "";
		String accountstate = "";
		String isZhuzh = "0";// 0:�������˻���1�����˻�
		String yinjshzt = "";
		String youwzh = "";
		String accorgno = "";
		String pass="1";
		String zzhstr="";//���˻��ַ���
		
		try {
			Zhanghb accountinfo = ZhanghbService.getZhanghb(account);
			
			if (accountinfo != null) {
				boolean bool = this.getSystemMgrService().CanOperDesOrg(
						clerk.getOrgcode(), accountinfo.getJigh());
				if(!bool){
					pass="0";
				}
				accountname = accountinfo.getHum();
				allexchange = accountinfo.getTongctd();
				tingyrq = accountinfo.getTingyrq() == null ? "" : accountinfo
						.getTingyrq();
				accountstate = accountinfo.getZhanghzt();
				List<Zhanghb> zlist = ZhanghbService.getZhangbListByZzh(account);// ���˻��б�
				
				// �ж��˻��Ƿ�������˻�
				if (zlist != null && zlist.size() >= 1){
					isZhuzh = "1";
					for (Zhanghb zhanghb : zlist) {
						zzhstr+=zhanghb.getZhangh()+"&"+zhanghb.getKaihrq();
						zzhstr+="|";
					}
					zzhstr=zzhstr.substring(0, zzhstr.length()-1);
				}
				yinjshzt = accountinfo.getYinjshzt();
				youwzh = accountinfo.getYouwzh();
				accorgno = accountinfo.getJigh();

			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			String accountin = accountname + "," + allexchange + "," + tingyrq
					+ "," + accountstate + "," + isZhuzh + "," + yinjshzt + ","
					+ youwzh + "," + accorgno+","+pass+","+zzhstr+","+account;
			out.println(accountin);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}

	}

	/*
	 * ����Ʊ��Ӱ��
	 */
	public ActionForward getBillImage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		// �¾��˺�ת��
		if (zhangh != null && zhangh.length()!= 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		String wenjbh = request.getParameter("wenjbh");
		try {
			ServletOutputStream out = response.getOutputStream();
			PiaojyxwjbId id = new PiaojyxwjbId();
			id.setZhangh(zhangh);
			id.setWenjbh(wenjbh);
			AccountImageServcie.downloadBillImage(id, out);
			out.close();
			out = null;
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}


	/*
	 * �����˻�ӡ����Ӱ��
	 */
	/*public ActionForward getYinjImage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		String qiyrq=request.getParameter("qiyrq");
		String yinjbh=request.getParameter("yinjbh");
		response.setContentType("image/JPEG");
		// �¾��˺�ת��
		if (zhangh != null && zhangh.length() > 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		ServletOutputStream out =null;
		InputStream is=null;
		BufferedImage bi =null;
		try {
			//request.setCharacterEncoding("gbk");
			out= response.getOutputStream();
			Yinjb yinjb=AccountImageServcie.downloadYinjImage(zhangh, yinjbh, qiyrq);
			if(yinjb!=null){
				Blob yinjtp=yinjb.getYinjtp();
				if(yinjtp!=null){
					byte[] stream = yinjtp.getBytes(1, (int) yinjtp.length());
					yinjtp.setBytes(1, Base64.encodeBytes(stream).getBytes());
					is= yinjtp.getBinaryStream();
					bi=ImageIO.read(new BufferedInputStream(is));
					JPEGImageEncoder encoder =JPEGCodec.createJPEGEncoder(out);
					encoder.encode(bi);
				}
			}
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/
	
	/**
	 * ����ӡ����Ϣ
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getYinjImage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		String qiyrq=request.getParameter("qiyrq");
		String yinjbh=request.getParameter("yinjbh");
		response.setContentType("image/jpeg");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		
		// �¾��˺�ת��
		if (zhangh != null && zhangh.length() != 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		OutputStream out =null;
		InputStream is=null;
		try {
			//request.setCharacterEncoding("gbk");
		//	File image=new File("D://"+UUID.randomUUID()+".dib");
			out= response.getOutputStream();
		//	out= new FileOutputStream(image);
			Yinjb yinjb=AccountImageServcie.downloadYinjImage(zhangh, yinjbh, qiyrq);
			if(yinjb!=null){
				Blob yinjtp=yinjb.getYinjtp();
				if(yinjtp!=null){
					//byte[] stream = yinjtp.getBytes(1, (int) yinjtp.length());
					//String tup=Base64.encodeBytes(stream);
					//out.write(tup.getBytes(),0,tup.getBytes().length);
					is=yinjtp.getBinaryStream();
					byte [] buf =new byte [1024*1024];
					int count =0;
					while((count =is.read(buf))!=-1){
						out.write(buf,0,count);
					}
					out.flush();
				}
			}
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/*
	 * �����˻�ӡ����Ӱ��
	 */
	public ActionForward downloadYinjkImage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		// �¾��˺�ת��
		if (zhangh != null && zhangh.length() != 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		String yinjkh = request.getParameter("yinjkh");
		String billcm = request.getParameter("billcm");
		try {
			ServletOutputStream out = response.getOutputStream();
			AccountImageServcie.downloadYinjkImage(zhangh, yinjkh, billcm, out);
			out.close();
			out = null;
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}
	
	/**
	 * ��ȡ�˺�ӡ����Ϣ
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAcccountYjImageList(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String account = request.getParameter("account");
		try {
			List<Yinjb> list = AccountImageServcie.getZhanghYjList(account);
			if (list == null || list.size() == 0) {
				return this.showMessageJSP(actionMapping, request,
						"accountinfo.yinj.image", "û���ҵ����˺ŵ�Ԥ��ӡ��!");
			}
			request.setAttribute("yinjList", list);
			return actionMapping.findForward("accountinfo.yinj.image");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.yinj.image");
		}
	}
	

	/*
	 * ��ȡ�˺�ӡ������Ϣ
	 */
	public ActionForward getAcccountYjkImageList(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String account = request.getParameter("account");
		try {
			List list = AccountImageServcie.getZhanghYjkList(account);
			if (list == null || list.size() == 0) {
				return this.showMessageJSP(actionMapping, request,
						"accountinfo.image", "û���ҵ����˺ŵ�Ԥ��ӡ����!");
			}
			request.setAttribute("accountList", list);
			return actionMapping.findForward("accountinfo.image");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.image");
		}
	}

	/*
	 * ��ȡ�˺�ӡ������Ϣ
	 */
	public ActionForward getYinjkListByQiyrq(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		String qiyrq = request.getParameter("qiyrq");
		try {
			List list = AccountImageServcie.getYinjkByQiyrq(zhangh, qiyrq);
			if (list == null || list.size() == 0) {
				return this.showMessageJSP(actionMapping, request,
						"accountinfo.image", "û���ҵ����˺ŵ�Ԥ��ӡ����!");
			}
			request.setAttribute("accountList", list);
			return actionMapping.findForward("accountinfo.image");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.image");
		}
	}

	/*
	 * ��ȡƱ����Ϣ
	 */
	public ActionForward getPiaojImageList(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		String pingzbsm = request.getParameter("pingzbsm");
		try {
			List list = AccountImageServcie.getBillImgList(zhangh, pingzbsm);
			if (list == null || list.size() == 0) {
				return this.showMessageJSP(actionMapping, request,
						"accountinfo.image", "û���ҵ���Ʊ��!");
			}
			request.setAttribute("accountList", list);
			return actionMapping.findForward("accountinfo.bill");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.bill");
		}
	}

	// �˻�������תҳ��
	public ActionForward zhanghdjView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.net.zhanghdj.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghdj.success");
		}
	}

	// ����
	public ActionForward zhanghdj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "û��Ȩ�޶��ᣬ�˺�:["
								+ accountinfoform.getAccount() + "]!");
			}
			// ��֤�˻�״̬
			if ("��Ч".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.dongjAccount(zhanghb.getZhangh());
				String content = "����(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա����:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "����", "����",
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "�˻�����ɹ�!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "�˺ţ�["
								+ zhanghb.getZhangh() + "]���ɽ����˻�����,��ǰ״̬Ϊ��["
								+ zhanghb.getZhanghzt() + "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghdj.success");
		}
	}

	// �˻���ʧ��תҳ��
	public ActionForward zhanghgsView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.net.zhanghgs.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghgs.success");
		}
	}

	// ��ʧ
	public ActionForward zhanghgs(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "�˺ţ�["
								+ accountinfoform.getAccount() + "]������!");
			}
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "û��Ȩ�޹�ʧ���˺�:["
								+ accountinfoform.getAccount() + "]!");
			}
			// ��֤�˻�״̬
			if ("��Ч".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.guasAccount(zhanghb.getZhangh());
				String content = "��ʧ�˻�(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա����:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "��ʧ�˻�",
						"��ʧ�˻�", clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "��ʧ�ɹ�!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "�˺ţ�["
								+ zhanghb.getZhangh() + "]���ɽ����˻���ʧ,��ǰ״̬Ϊ��["
								+ zhanghb.getZhanghzt() + "]!");
			}

		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghgs.success");
		}
	}

	/**
	 * �˻�������תҳ��
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward zhanghkhView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//��ϵͳʱ�䷵��ǰ̨ҳ��
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		HttpSession session = request.getSession();
		AccountinfoForm accountinfoForm=(AccountinfoForm)actionForm;
		accountinfoForm.reset(actionMapping, request);
		//��ѯ�˻�����
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		
		request.setAttribute("zhanghxzlist", zhanghxzList);
		Zhanghb zhanghb = (Zhanghb) session.getAttribute("zhanghb_zt");
		String logintype = (String) session.getAttribute("logintype");
		Clerk clerk=(Clerk)session.getAttribute("clerk");
		String org_for_tongd=clerk.getOrgcode();;
		session.removeAttribute("zhanghb_zt");
		session.removeAttribute("logintype");
		//���Կ����ӿ�
		/*zhanghb=new Zhanghb();
		zhanghb.setZhangh("20140521111111111");
		zhanghb.setHum("�쳬2");
		zhanghb.setZhanghxz("������");
	//	zhanghb.setYinjkbh("12345678912345678");
		zhanghb.setKaihrq("2013-04-05");
		logintype="ztkaih";*/
		request.setAttribute("logintype", "kaih");
		try{
			if (logintype == null || logintype.equals("")||logintype.equals("ztlogin")||logintype.equals("ztqiantyy")) {
				return actionMapping
						.findForward("accountinfo.net.zhanghkh.success");
			} else {
				if (zhanghb == null) {
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.zhanghkh.success", "���ú��Ŀ�����Ϣʧ��!");
				}
				if (zhanghb.getZhangh() == null || zhanghb.getZhangh().equals("")) {
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.zhanghkh.success", "���ú��Ŀ�����Ϣʧ��!");
				}
				accountinfoForm.setAccount(zhanghb.getZhangh());
				//���Ӻ����õ����˻���������ӡϵͳ�Ƿ���� �������� ��������Ϊ"������"
				String zhanghxz = zhanghb.getZhanghxz()==null||zhanghb.getZhanghxz().trim().equals("")?"������":zhanghb.getZhanghxz();
				int count=0;
				for (Zhanghxzb zhanghxzb : zhanghxzList) {
					if(zhanghxzb!=null&&zhanghxzb.getZhanghxz().equals(zhanghxz)){
						count=1;
						break;
					}
				}
				if(count==0){
					zhanghb.setZhanghxz("������");
				}
				
				String kaihrq=zhanghb.getKaihrq();
				if(kaihrq!=null&&kaihrq.indexOf("-")==-1){
					zhanghb.setKaihrq(DateTool.toSimpleFormat(kaihrq));
				}
				
				if (logintype.equals("ztzilxg")) {
					try {
						String zhangh = zhanghb.getZhangh();
						// �¾��˺�ת��
						if (zhangh != null && zhangh.length() != 17) {
							zhangh = ZhanghbService.parseTypeN(zhangh, 17);
						}
						Zhanghb zhanghb_info = ZhanghbService.getZhanghb(zhangh);
						if(zhanghb_info==null){
							updateAccountinfoForm(zhanghb, (AccountinfoForm) actionForm);
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "���˻���δ����ӡϵͳ����!");
						}
						org_for_tongd=zhanghb_info.getJigh();
						zhanghb_info.setZhanghxz(zhanghb.getZhanghxz());
						zhanghb_info.setHum(zhanghb.getHum());
						request.setAttribute("zhanghb", zhanghb_info);
						request.setAttribute("logintype", logintype);
						//�����ݻ��Ե�ҳ��
						updateAccountinfoForm(zhanghb_info, (AccountinfoForm) actionForm);
					
						return actionMapping
								.findForward("accountinfo.net.zhanghkh.success");
					} catch (BusinessException e) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.zhanghkh.success", "���ú��Ŀ�����Ϣʧ��!");
					}
				} else {
					Zhanghb zhanghb_info = null;
					try {
						zhanghb_info = ZhanghbService.getZhanghb(zhanghb
								.getZhangh());
					} catch (BusinessException e) {
					}
					if (zhanghb_info != null) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.zhanghkh.success", "�˺ţ�["
										+ zhanghb_info.getZhangh()
										+ "]�Ѵ���!");
					}
					//updateAccountinfoForm( zhanghb, (AccountinfoForm)actionForm) ;
					//String zhangh, String yinjkhStr,String yinjkzt, Clerk clerk
					//�����˺�ȥ���Ĳ�ѯ���˺Ŵ��ڵ�����ӡ����
					/*{
						List<Yinjk> yinjkList=null;
						try {
							//ӡ������֤ģʽ
							String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
							if(ip.equals("0")){
								yinjkList=new ArrayList<Yinjk>();
							}else{
								yinjkList=iQueryYinjkh(zhanghb.getZhangh(), "02", "", clerk);
							}
						} catch (BusinessException e) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", e.getMessage());
						}
						if(yinjkList==null||yinjkList.size()==0||"".equals(yinjkList.get(0).getYinjkh().trim())){
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "δ��ѯ�����˻���ӡ����");
						}else{
							zhanghb.setYinjkbh(yinjkList.get(0).getYinjkh().trim());
							zhanghb.setYinjkbh2(yinjkList.get(1).getYinjkh().trim());
							zhanghb.setYinjkbh3(yinjkList.get(2).getYinjkh().trim());
							zhanghb.setYinjkbh4(yinjkList.get(3).getYinjkh().trim());
						}	
					}*/
					updateAccountinfoForm( zhanghb, (AccountinfoForm)actionForm) ;
					request.setAttribute("logintype", logintype);
					return actionMapping
							.findForward("accountinfo.net.zhanghkh.success");
				}
			}
		}finally{
			Org org_forTongd=OrgService.getOrgByCode(org_for_tongd);
			String tongdFlag="0";
			//���û������ϼ�������ʡ�л�����ͬʱ �����ڶ�������
			if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
				tongdFlag="1";
			}
			request.setAttribute("tongdFlag", tongdFlag);
		
		}
	}

	// ��װ���ݵ�form�� ���Ե�jspҳ��
	private void updateAccountinfoForm(Zhanghb zhanghb,
			AccountinfoForm actionForm) {
		if (zhanghb == null) {
			return;
		}
		if (actionForm == null) {
			return;
		}
		actionForm.setAccount(zhanghb.getZhangh());
		actionForm.setAllexchange(zhanghb.getTongctd());
		actionForm.setHum(zhanghb.getHum());
		actionForm.setAccountname(zhanghb.getHum());
		actionForm.setRemark(zhanghb.getBeiz());
		actionForm.setLinkman(zhanghb.getLianxr());
		actionForm.setPhone(zhanghb.getDianh());
		actionForm.setFuzr(zhanghb.getFuzr());
		actionForm.setFuzrdh(zhanghb.getFuzrdh());
		actionForm.setLinkman2(zhanghb.getLianxr2());
		actionForm.setPhone2(zhanghb.getDianh2());
		actionForm.setFuzr2(zhanghb.getFuzr2());
		actionForm.setFuzrdh2(zhanghb.getFuzrdh2());
		actionForm.setShifdh(zhanghb.getShifdh());
		actionForm.setZhanghxz(zhanghb.getZhanghxz());
		actionForm.setOpendate(zhanghb.getKaihrq());	
		String yinjkbh = zhanghb.getYinjkbh();
		actionForm.setYinjkbh(yinjkbh);
		actionForm.setZhuzh(zhanghb.getZhuzh());
/*		if (yinjkbh != null && yinjkbh.length() != 0
				&& yinjkbh.indexOf("|") != -1) {
			yinjkbh = yinjkbh.replace('|', ',');
			List<String> yinjkhList = createYinjkhList(yinjkbh);
			actionForm.setYinjkbh(yinjkhList.get(0));
			actionForm.setYinjkbh2(yinjkhList.get(1));
			actionForm.setYinjkbh3(yinjkhList.get(2));
			actionForm.setYinjkbh4(yinjkhList.get(3));
		}else{
			actionForm.setYinjkbh(zhanghb.getYinjkbh());
			actionForm.setYinjkbh2(zhanghb.getYinjkbh2());
			actionForm.setYinjkbh3(zhanghb.getYinjkbh3());
			actionForm.setYinjkbh4(zhanghb.getYinjkbh4());
		}*/
		actionForm.setLianxrfjh(zhanghb.getLianxrfjh());
		actionForm.setLianxrfjh2(zhanghb.getLianxrfjh2());
		actionForm.setFuzrfjh(zhanghb.getFuzrfjh());
		actionForm.setFuzrfjh2(zhanghb.getFuzrfjh2());
		
		//���Ų�Ϊ�� �ҳ��Ȳ�Ϊ0  ������λ��Ϊ0 �� Ҫ��0��ʾ
		String qh =zhanghb.getLianxrqh();
		actionForm.setLianxrqh(bulidAreaCode(qh));
		 qh =zhanghb.getLianxrqh2();
		actionForm.setLianxrqh2(bulidAreaCode(qh));
		 qh =zhanghb.getFuzrdh();
		actionForm.setFuzrqh(bulidAreaCode(qh));
		 qh =zhanghb.getFuzrdh2();
		actionForm.setFuzrqh2(bulidAreaCode(qh));
		
		String zhuzh = zhanghb.getZhuzh();
		if (zhuzh != null && zhuzh.trim().length() != 0) {
			actionForm.setGongy("1");
			if(yinjkbh==null||yinjkbh.trim().length()==0){
				try {
					actionForm.setYinjkbh(ZhanghbService.getZhanghb(zhuzh).getYinjkbh());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}

	}

	/**
	 * �����˻�����
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createAccount(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		//���˻������б���ǰ̨ҳ��
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		Org org_forTongd=OrgService.getOrgByCode(clerk.getOrgcode());
		String tongdFlag="0";
		//���û������ϼ�������ʡ�л�����ͬʱ �����ڶ�������
		if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
			tongdFlag="1";
		}
		request.setAttribute("tongdFlag", tongdFlag);
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		Zhanghb zhanghb = null;
		
		try {
			zhanghb = ZhanghbService.getZhanghb(accountinfoform.getAccount());
		} catch (Exception e) {
		}
		// ��֤�˻��Ƿ��Ѵ���
		if (zhanghb != null) {
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghkh.success", "�˺ţ�["
							+ accountinfoform.getAccount() + "]�Ѵ���!");
		}
		zhanghb = getNewZhanghb(accountinfoform);
		zhanghb.setJigh(clerk.getOrgcode());
		List<String> yinjkbhList = getYinjkhList(accountinfoform);
		String yinjkbhStr = createYinjkbhStr(yinjkbhList);
		String gongy = accountinfoform.getGongy();
		request.setAttribute("zhanghb", zhanghb);
		try{
		// ��鿪���˻��Ƿ���ӡ����
		if (gongy.equals("1")) {
			Zhanghb gongyzh = ZhanghbService.getZhanghbByYinjkbh(yinjkbhStr);
			if (gongyzh == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "�����˻�������");
			}
			if (!gongyzh.getJigh().equals(zhanghb.getJigh())) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "ֻ����ͬһ�����ڹ���ӡ��");
			}
			if (!gongyzh.getHum().equals(zhanghb.getHum())) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "ֻ�л���һ��ʱ�ſɹ���");
			}
			if (gongyzh.getZhanghxz().equals("�ڲ���")||gongyzh.getZhanghxz().equals("�������ʻ�")) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "�����˻�["+gongyzh.getZhangh()+"]Ϊ"+gongyzh.getZhanghxz()+"���޷�����");
			}
			if (gongyzh.getYinjshzt().equals("δ��")) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "�����˻�["+gongyzh.getZhangh()+"]����δ��ӡ�����޷�����");
			}
			zhanghb.setFuyrq(zhanghb.getKaihrq());//������������
			zhanghb.setZhuzh(gongyzh.getZhangh());
			zhanghb.setYouwyj(gongyzh.getYouwyj());
			zhanghb.setYouwzh(gongyzh.getYouwzh());
			zhanghb.setZuhshzt(gongyzh.getZuhshzt());
			zhanghb.setYinjshzt(gongyzh.getYinjshzt());
			ZhanghbService.createZhanghb(zhanghb, new ArrayList<String>());
			String yinjkbh = gongyzh.getYinjkbh();
			zhanghb.setYinjkbh(yinjkbh);
			request.setAttribute("logintype", "ztzilxg");
			this.createAccountManageLog(zhanghb.getZhangh(), "0", "��������ӡ������",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghkh.success", "�����˺�["
							+ gongyzh.getZhangh() + "]�ɹ�!");
		} else {
			// ��ӡ���������ж�ӡ��������Ƿ�ʹ��
			boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
			if (exist) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "ӡ�����ѱ�ʹ��");
			}
			zhanghb.setYinjkbh(yinjkbhStr);
			// ���� �ֱ��zhangh���yinjk����в���
			ZhanghbService.createZhanghb(zhanghb, yinjkbhList);
			request.setAttribute("logintype", "ztzilxg");
			request.setAttribute("kaih", "kaih");
			// ��¼������־
			this
					.createAccountManageLog(zhanghb.getZhangh(), "0", "�˻�����",
							clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghkh.success", "�����ɹ�!");
		}
		}catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghkh.success");
		}
	}
	/**
	 * �Ǻ����˻��������������ʻ����ڲ�����
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createInnerAccount(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		Org org_forTongd=OrgService.getOrgByCode(clerk.getOrgcode());
		String tongdFlag="0";
		//���û������ϼ�������ʡ�л�����ͬʱ �����ڶ�������
		if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
			tongdFlag="1";
		}
		request.setAttribute("tongdFlag", tongdFlag);
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		Zhanghb zhanghb = null;
		try {
			zhanghb = ZhanghbService.getZhanghb(accountinfoform.getAccount());
		} catch (Exception e) {
		}
		// ��֤�˻��Ƿ��Ѵ���
		if (zhanghb != null) {
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.inner.zhanghkh.success", "�˺ţ�["
							+ accountinfoform.getAccount() + "]�Ѵ���!");
		}
		zhanghb = getNewZhanghb(accountinfoform);
		zhanghb.setJigh(clerk.getOrgcode());
		List<String> yinjkbhList = getYinjkhList(accountinfoform);
		String yinjkbhStr = createYinjkbhStr(yinjkbhList);
		request.setAttribute("zhanghb", zhanghb);
		// ��ӡ���������ж�ӡ��������Ƿ�ʹ��
		boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
		if (exist) {
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.inner.zhanghkh.success", "ӡ�����ѱ�ʹ��");
		}
		zhanghb.setYinjkbh(yinjkbhStr);
		// ���� �ֱ��zhangh���yinjk����в���
		try{
		ZhanghbService.createZhanghb(zhanghb, yinjkbhList);
		request.setAttribute("logintype", "zilxg");
		request.setAttribute("kaih", "kaih");
		// ��¼������־
		this
				.createAccountManageLog(zhanghb.getZhangh(), "0", "�˻�����",
						clerk);
		return super.showMessageJSP(actionMapping, request,
				"accountinfo.net.inner.zhanghkh.success", "�����ɹ�!");
		}catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.inner.zhanghkh.success");
		}
	}
	/**
	 * ������ù�ϵ
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteShare(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			String zhangh = request.getParameter("zhangh");
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = null;
			zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ��Ѵ���
			if (zhanghb == null) {
				out.print("fail001");// �˻�������
				return null;
			}
			Zhanghb oldZhanghb = copyZhanghb(zhanghb);
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				out.print("fail002");// ��Ȩ��
				return null;
			}
			Zhanghb zhanghb_gy = ZhanghbService.getZhanghb(zhanghb.getZhuzh());
			String yinjkbh = zhanghb_gy.getYinjkbh();
			
			//�ж�ӡ������֤ģʽ �Ƿ�ͨ�����Ľ�����֤
			/*String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
			if(ip.equals("1")){
				//���ú��Ľ��ײ�ѯ
				boolean pass = iCheckYinjkh(zhangh, yinjkbh,"02", clerk);	
				if(pass){
					//�˻�ӡ����δ������޷����н��ӡ��������
					out.print("fail004");
					return null;
				}
			}
			if (yinjkbh != null && yinjkbh.length() != 0
					&& yinjkbh.indexOf("|") != -1) {
				yinjkbh = yinjkbh.replace('|', ',');
			}
			List<String> yinjkhList = createYinjkhList(yinjkbh);
			*/
			zhanghb.setZhuzh("");
			zhanghb.setFuyrq("");
			zhanghb.setYouwyj("��");
			zhanghb.setYinjshzt("δ��");
			zhanghb.setYouwzh("��");
			zhanghb.setZuhshzt("δ��");
			
			ZhanghbService.update(zhanghb);
			//ZhanghbService.cancleYinjk(zhanghb_gy.getZhangh(), yinjkhList);
			String content = "��Ա[" + clerk.getCode() + "]����˻�[" + zhangh
					+ "]�Ĺ��ù�ϵ";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhangh, "3", content, clerk);
			out.print("0");
			return null;
		} catch (Exception e) {
			out.print("fail003");// �������ʧ��
			return null;
		} finally {
			out.close();
		}

	}
/**
 * ӡ����״̬����
 * @param actionMapping
 * @param actionForm
 * @param request
 * @param response
 * @return
 */
	public ActionForward queryYinjk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String zhangh = request.getParameter("zhangh");
		String yinjkhStr = request.getParameter("yinjkStr");
		String zhanghxz=request.getParameter("zhanghxz");
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		try {
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length()!= 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
		List<String> yinjkbhList = createYinjkhList(yinjkhStr);
		Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
		if (zhanghb != null) {
			List<String> oldYinjkbhList = new ArrayList<String>();// �޸�֮��ʹ�õ�ӡ����
			List<String> commen = new ArrayList<String>();// �޸�ǰ�󲻱��ӡ����
			// ���޸�֮ǰ��֮��Ķ�����ӡ����������ֳ���
			splitYinjkList(zhanghb.getYinjkbh(), yinjkbhList,
					oldYinjkbhList, commen);

		}
			out = response.getWriter();
			String type =SystemConfig.getInstance().getValue("yinjk_check_model");
			String yinjkFlag=yinjkhStr!=null&&yinjkhStr.trim().length()==20?"1":"0"; //����ӡ�������� �ж��Ƿ�Ϊ�����˻�  �����˻�ӡ��������Ϊ17����22λ
			if(type.equals("0")||zhanghxz.equals("0")||yinjkFlag.equals("0")){
				boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
				if (exist) {
					if(zhanghxz!=null&&!zhanghxz.equals("0")){
						out.print("1");//����
					}else{
						//if(yinjkFlag.equals("0")){
						//	out.print("1");//�����˻�����
						//}else{
							out.print("fail002");
						//}//ӡ�����ѱ�ʹ�ã��������ʻ����ڲ�����������
					}
					return null;
				}else{
					out.print("0");//ͨ��
					return null;
				}
			}else{
				//�ж�ӡ������֤ģʽ �Ƿ�ͨ�����Ľ�����֤
				List<String> yinjks= createYinjkhList(yinjkhStr);
				int pass = iCheckKaih(zhangh, yinjks,"02", clerk);	//���ú��Ľ��ײ�ѯ
		
				if (pass==0) {
					out.print("fail001");
					return null;
				}
				
				boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
				if (exist) {
					if(pass==1){
						out.print("fail002");
						return null;
					}
					// ����ӡ�����ѱ�ʹ�� �����Ƿ��Ѵ���ʹ�ô�ӡ�������˻�
					Zhanghb gongyzh = ZhanghbService
							.getZhanghbByYinjkbh(createYinjkbhStr(yinjkbhList));
					if (gongyzh == null) {
						out.print("fail001");
						return null;
					}
					out.print("1");
				} else {
					if(pass==2){
						out.print("fail004");
						return null;
					}
					out.print("0");
				}
			}
		} catch (BusinessException e){
			out.print(e.getMessage());
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			out.print("fail003");
			return null;
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * ���ú��Ľӿ� ��ѯ�����˻���Ϣ
	 * @param zhangh
	 * @param orgNum
	 * @param clerk
	 * @return
	 * @throws BusinessException
	 */
	private Zhanghb getBlZhangh(String zhangh,String orgNum,Clerk clerk) throws BusinessException{
		String msg00400=create00400Msg(zhangh, clerk.getOrgcode(), clerk);
		byte[] result=iQuery(msg00400.getBytes());
		Zhanghb zhanghb=analysisMsg00400(result);
		return zhanghb;
	}
	/**
	 * У������˻�״̬
	 * @param zhangh
	 * @param orgNum
	 * @param clerk
	 * @param statue
	 * @return
	 * @throws BusinessException
	 */
	private boolean validataBLZhanghzt(String zhangh,String orgNum,Clerk clerk,List<String>zhanghxzList,String zhanghxz) throws BusinessException{
		String type =SystemConfig.getInstance().getValue("yinjk_check_model");
		//String yinjkbh=ZhanghbService.getZhanghb(zhangh).getYinjkbh();
		if(type.equals("0")||zhanghxz.equals("�ڲ���")||zhanghxz.equals("�������ʻ�")){
			return true;
		}
		Zhanghb zhanghb=getBlZhangh(zhangh, orgNum, clerk);
		if(zhanghb==null){
			return false;
		}
		return zhanghxzList.contains(zhanghb.getZhanghzt());
	}
	/**
	 * ��ѯ�����˻���Ϣ
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
		public ActionForward queryBLZhangh(ActionMapping actionMapping,
				ActionForm actionForm, HttpServletRequest request,
				HttpServletResponse response) {
			
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String zhangh = request.getParameter("zhangh");
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("GBK");

			String type =SystemConfig.getInstance().getValue("yinjk_check_model");
			PrintWriter out = null;
			try {
				// �¾��˺�ת��
				if (zhangh != null && zhangh.length() != 17) {
					zhangh = ZhanghbService.parseTypeN(zhangh, 17);
				}
				
				out = response.getWriter();
				if(type.equals("0")){
					out.print("fail002");
					return null;
				}
				Zhanghb zhanghb=getBlZhangh(zhangh, clerk.getOrgcode(), clerk);
				if(zhanghb==null){
					out.print("fail001");
					return null;
				}else{
				out.print(JSONObject.fromObject(zhanghb).toString());
				//System.out.println(JSONObject.fromObject(zhanghb).toString());
				return null;
				}
			} catch (BusinessException e){
				out.print(e.getMessage());
				return null;
			}catch (Exception e) {
				e.printStackTrace();
				out.print("fail003");
				return null;
			} finally {
				out.close();
			}
		}
	
	/**
	 * У��ӡ�����Ƿ�������
	 * @param zhangh
	 * @param xkList
	 * @param yinjkzt
	 * @param clerk
	 * @return
	 
	private boolean  iCheckYinjkhXiaoK (String zhangh, List<String> xkList,String yinjkzt, Clerk clerk){
		//�ж�ӡ������֤ģʽ �Ƿ�ͨ�����Ľ�����֤
		String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
		if(ip.equals("0")){
			return true;
		}
		if(xkList.size()==0){
			return false;
		}
		System.out.println("zhangh :"+zhangh +"  yinjkzt : "+yinjkzt +" clerk : "+ clerk.getCode() +" orgCode :" +clerk.getOrgcode());
		//������Ҫ�Ķ���ӡ��������
		for (String string : xkList) {
			try {
				if(string==null||string.trim().equals("")){
					continue;
				}
				//�����˺� ӡ������ ӡ����״̬ ��Ա�� ȥ��ѯӡ������Ϣ
				List<Yinjk> yinjkList = iQueryYinjkh(zhangh, yinjkzt,string, clerk);
				if(yinjkList==null||yinjkList.size()==0){
					return false;
				}
				Yinjk yinjk =yinjkList.get(0);
				if(!zhangh.equals(yinjk.getZhangh())){
					return false;
				}
			} catch (BusinessException e) {
				return false;
			}
			
			
		}
		return true;
		
	}*/
	
	/**
	 * 
	 * �°� ӡ������ѯ
	 * 
	 * @param zhangh
	 * @param yinjkhList
	 * @param yinjkzt
	 * @param clerk
	 * @return
	 * @throws BusinessException
	 */
	
	//0 �� δͨ�� 1��ͨ��  2:����
	private int iCheckKaih(String zhangh,List<String> yinjkhList,String yinjkzt,Clerk clerk) throws BusinessException{
			StringVO msg=new StringVO();
			//�ж�ӡ������֤ģʽ �Ƿ�ͨ�����Ľ�����֤
			String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
			if(ip.equals("0")){
				return 1;
			}
		
			for (String yinjkh : yinjkhList) {
				if(yinjkh==null||yinjkh.trim().equals("")){
					continue;
				}
				List<Yinjk> yinjkList = iQueryYinjkh("", yinjkzt,yinjkh, clerk,msg);
				//����ӡ����δ��ѯ����¼ ˵���˿�δ������ ӡ����״̬���Ϸ�
				if(yinjkList==null||yinjkList.size()==0){
					return 0;
				}
				//�����ڼ�¼ �����˺ź�ǰ�������˺�һ��  ��֤ͨ��
				if(zhangh.equals(yinjkList.get(0).getZhangh().trim())){
					return 1;
				}
				//���ڼ�¼ �����˺ŷ�ǰ�������˺� �����˺Ź��ò�ѯ ��ѯ������ӡ�����Ĺ����˺�
				List<String> zhanghList=iQueryZhanghList(msg.getValue().getBytes());
				//�����˺ż���Ϊ�� ����δͨ��
				if(zhanghList==null||zhanghList.size()==0){
					return 0;
				}
				//���������˺ż��� ����� ǰ��������˺� �Ƿ����ڸ���ӡ�����Ĺ����˺� ���� ����2 ������
				for (String zhanghao : zhanghList) {
					if(zhanghao!=null&&zhanghao.trim().equals(zhangh.substring(0, zhangh.length()-1))){
						return 2;
					}
				}
			}
			return 0;
	}
	
	/**
	 * ��ѯӡ�����Ĺ����˻��б�
	 * @param buf
	 * @return
	 * @throws BusinessException
	 */
	private List<String> iQueryZhanghList(byte[] buf) throws BusinessException {
		Msg29178 msg =new Msg29178(buf);
		String lsh = this.getSystemMgrService().getSystemSequence();
		msg.doChange(lsh);
		buf=iQuery(msg.getMsg());
		Msg29179 result =null;
		List<String> zhanghList=new ArrayList<String>();
		f1 :while(buf.length>501){
			result =new Msg29179(buf);
			
			List<String> zhList=new ArrayList<String>();
			
			try {
				zhList=analysisMsg29179(result.getMsg());
			} catch (BusinessException e) {
				if(e.getMessage().equals("�����������ݿ���ʾ")||result.getHead().getReplyCd().equals("HX1957")){
					break f1;
				}else{
					throw new BusinessException(e);
				}
			}
			for (String string : zhList) {
				zhanghList.add(string);
			}
			if(zhList.size()<10){
				break;
			}
			lsh = this.getSystemMgrService().getSystemSequence();
			result.doChange(lsh);
			buf=iQuery(result.getMsg());
			
		}
		return zhanghList;
	}
	/**
	 * �ɰ�ӡ������ѯ
	 * @param zhangh
	 * @param yinjkhStr
	 * @param yinjkzt
	 * @param clerk
	 * @return
	 * @throws BusinessException
	 
	private boolean iCheckYinjkh(String zhangh, String yinjkhStr,String yinjkzt, Clerk clerk)
			throws BusinessException {
		//�ж�ӡ������֤ģʽ �Ƿ�ͨ�����Ľ�����֤
		String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
		if(ip.equals("0")){
			return true;
		}
		byte [] buf=new byte [1];
		List<Yinjk> yinjkList = iQueryYinjkh(zhangh, yinjkzt,"", clerk);
		List<String> yinjkhList=new ArrayList<String>();
		for (Yinjk yinjk : yinjkList) {
			yinjkhList.add(yinjk.getYinjkh());
		}
		String yinjkh = createYinjkbhStr(yinjkhList);
		return yinjkh.equals(yinjkhStr);
	}*/
	/**
	 * ���Ľӿ� socket
	 * @param sendMsg
	 * @return
	 * @throws BusinessException
	 */
	private byte[] iQuery(byte [] sendMsg) throws BusinessException{
		Socket socket = null;
		try {
			String ip =SystemConfig.getInstance().getValue("yinjk_check_ip");
			String portStr =SystemConfig.getInstance().getValue("yinjk_check_port");
			
			int port=8894;
			try {
				port = Integer.parseInt(portStr);
			} catch (NumberFormatException e) {
				port=8894;
			}
			socket = new Socket(ip, port);
			if (socket != null) {
				String length = sendMsg.length+ "";
				while (length.length() < 6) {
					length = "0" + length;
				}
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				log.info("��ǰ�÷��ͱ��ģ�"+new String (sendMsg));
				os.write(length.getBytes());
				os.write(sendMsg);
				byte[] b = new byte[6];
				is.read(b, 0, 6);
				int len = Integer.parseInt((new String(b).trim()));
				byte[] buf = new byte[len];
				is.read(buf, 0, len);
				log.info("ǰ�÷��ر��ģ�"+new String (buf));
			return buf;
			}
		} catch (IOException e) {
			throw new BusinessException("��ǰ��ͨѶ�쳣�����Ӻ��ķ���ʧ��");
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	private List<Yinjk> iQueryYinjkh(String zhangh,String yinjkzt, String yinjkh,Clerk clerk,StringVO msg)
			throws BusinessException {
		byte[]buf=null;
		List<Yinjk>yinjkList=new ArrayList<Yinjk>();
		String sendMsg = create29177Msg(zhangh,yinjkh, yinjkzt, clerk.getOrgcode(),clerk);
		buf=iQuery(sendMsg.getBytes());
		yinjkList=analysisMsg29178(buf);
		msg.setValue(new String(buf));
		return yinjkList;
	}
	/**
	 * ����29178����
	 * @param buf
	 * @return
	 * @throws BusinessException
	 */
	private List<Yinjk> analysisMsg29178(byte[] buf) throws BusinessException {
		
		Msg29178 result =new  Msg29178(buf);
		Head head=result.getHead();
		if (head.getReplyCd().trim().equals("000000")) {
			return result.getYinjkList();
		}else{
			if (!head.getReplyText().trim().equals("")) {
					throw new BusinessException(head.getReplyText().trim());
			}
			throw new BusinessException("������Ĳ�ѯ����ʧ��");
		}
	}
	/**
	 * ����29179����
	 * @param buf
	 * @return
	 * @throws BusinessException
	 */
	private List<String> analysisMsg29179(byte[] buf) throws BusinessException {
		Msg29179 result =new  Msg29179(buf);
		Head head=result.getHead();
		if (head.getReplyCd().trim().equals("000000")) {
			return result.getZhanghList();
		}else{
			if (!head.getReplyText().trim().equals("")) {
					throw new BusinessException(head.getReplyText().trim());
			}
			throw new BusinessException("������Ĳ�ѯ����ʧ��");
		}
	}
	
	/**
	 * ����00400���ر���
	 * @param buf
	 * @return
	 * @throws BusinessException
	 */
	private Zhanghb analysisMsg00400(byte[] buf) throws BusinessException {
		Msg00400 result =new  Msg00400(buf);
		Head head=result.getHead();
		if (head.getReplyCd().trim().equals("000000")) {
			return result.getZhanghb();
		}else{
			if (!head.getReplyText().trim().equals("")) {
					throw new BusinessException(head.getReplyText().trim());
			}
			throw new BusinessException("������Ĳ�ѯ����ʧ��");
		}
	}
	/**
	 * ����ǰ�ñ���ͷ
	 * @param zhangh
	 * @param yinjkh
	 * @param yinjkzt
	 * @param orgNum
	 * @param clerk
	 * @return
	 */
	private String createHeadMsg(String orgNum,
			Clerk clerk,String jiaoyNo) {
		String flwNo =SystemConfig.getInstance().getValue("yinjk_check_flwno");//2λ������3λǰ��ϵͳ���
		if(flwNo==null||"".equals(flwNo)||flwNo.length()!=5){
			flwNo="00000";
		}
		StringBuffer msg = new StringBuffer();
		String nowDate = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		String lsh = this.getSystemMgrService().getSystemSequence();
		lsh = lsh == null ? "" : lsh;
		while (lsh.length() < 7) {
			lsh = "0" + lsh;
		}
		msg.append(lsh +flwNo+ "        ");// ǰ��ϵͳ��ˮ��
		msg.append("                    ");// ԭ����ǰ����ˮ��
		msg.append(nowDate.substring(0, 8));
		msg.append(nowDate.substring(8));
		msg.append(jiaoyNo);
		msg.append("    ");
		String orgCode = clerk.getOrgcode();
		orgCode = orgCode == null ? "" : orgCode;
		while (orgCode.length() < 8) {
			orgCode += " ";
		}
		msg.append(orgCode);
		msg.append(orgCode);
		int i = 1;
		while (i <= 252) {
			msg.append(" ");
			i++;
		}
		msg.append("        ");// �ն˱��
		String clerkCode = clerk.getCode();
		clerkCode = clerkCode == null ? "" : clerkCode;
		while (clerkCode.length() < 6) {
			clerkCode += " ";
		}
		if(clerkCode.length()>6){
			clerkCode=clerkCode.substring(0,6);
		}
		msg.append(clerkCode);// ��Ա���
		msg.append("      ");// ��Ȩ����Ա���
		msg.append(nowDate.substring(0, 8));// ǰ��ҵ������
		msg.append(nowDate.substring(8, 14));// ʱ��
		msg.append("            ");// ��̨ҵ����ˮ��
		msg.append("        ");
		msg.append("    ");// ҳ��
		msg.append(" ");// �������
		msg.append("000");// ����վ��
		msg.append("   ");// ������Ϣ��
		msg.append("0");// ������
		msg.append("E");// ����Դϵͳ��־
		int j = 1;
		while (j <= 92) {
			msg.append(" ");
			j++;
		}
		return msg.toString();
	}
	/**
	 * ����29177����
	 * @param zhangh
	 * @param yinjkh
	 * @param yinjkzt
	 * @param orgNum
	 * @param clerk
	 * @return
	 */
	private String create29177Msg(String zhangh, String yinjkh, String yinjkzt,String orgNum,
			Clerk clerk) {
		StringBuffer msg = new StringBuffer();
		msg.append(createHeadMsg(orgNum, clerk,"S001203000801"));
		String zhangh_ = "";
		if(yinjkh==null||yinjkh.trim().equals("")){
			yinjkh="00000000000000000000";
		}
		while(yinjkh.length()<20){
			yinjkh="0"+yinjkh;
		}
		String yinjkqzh_ =yinjkh.substring(0, 4);
		while (yinjkqzh_.length() < 4) {
			yinjkqzh_ = "0" + yinjkqzh_;
		}
		String yinjkxh_ =yinjkh.substring(4);
		
		while (yinjkxh_.length() < 16) {
			yinjkxh_ = "0" + yinjkxh_;
		}
		zhangh_ = zhangh == null || zhangh.trim().length() == 0 ? "0000000000000000"
				: zhangh;
		while (zhangh_.length() < 17) {
			zhangh_ = "0" + zhangh_;
		}
		if (zhangh_.length() > 17) {
			zhangh_ = zhangh_.substring(0, 17);
		}
		msg.append(yinjkqzh_);// ǰ׺��
		msg.append(yinjkxh_);// ӡ�������
		msg.append(yinjkzt);// ӡ����״̬
		msg.append(zhangh_);// �˺�
		while(orgNum.length()<4){
			orgNum="0"+orgNum;
		}
		msg.append("0000");// ������
		msg.append("E ");
		return msg.toString();
	}
	/**
	 * ����29177����
	 * @param zhangh
	 * @param yinjkh
	 * @param yinjkzt
	 * @param orgNum
	 * @param clerk
	 * @return
	 */
	private String create00400Msg(String zhangh, String orgNum,
			Clerk clerk) {
		StringBuffer msg = new StringBuffer();
		msg.append(createHeadMsg(orgNum,clerk,"S001203000804"));
		msg.append(zhangh);// �˺�
		msg.append("1");// 
		msg.append("0");// 
		msg.append("0");//
		
		msg.append("00000000");
		msg.append("00000000");
		return msg.toString();
	}
	
	
	/**
	 * �Ǻ����˻�������תҳ��
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward innerZhanghkhView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//��ϵͳʱ�䷵��ǰ̨ҳ��
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		HttpSession session = request.getSession();
		AccountinfoForm accountinfoForm=(AccountinfoForm)actionForm;
		accountinfoForm.reset(actionMapping, request);
		Clerk clerk=(Clerk)session.getAttribute("clerk");
		String org_for_tongd=clerk.getOrgcode();;
		Org org_forTongd=OrgService.getOrgByCode(org_for_tongd);
		String tongdFlag="0";
		//���û������ϼ�������ʡ�л�����ͬʱ �����ڶ�������
		if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
			tongdFlag="1";
		}
		request.setAttribute("tongdFlag", tongdFlag);
		request.setAttribute("logintype", "kaih");
		return actionMapping.findForward("accountinfo.net.inner.zhanghkh.success");
		
	}

	// ��form�л�ȡӡ������ ��װ�ɼ���
	private List<String> getYinjkhList(AccountinfoForm accountinfoform) {
		List<String> yinjkbhList = new ArrayList<String>();
		if (accountinfoform.getYinjkbh() != null
				&& accountinfoform.getYinjkbh().trim().length() != 0)
			yinjkbhList.add(accountinfoform.getYinjkbh().trim());
		if (accountinfoform.getYinjkbh2() != null
				&& accountinfoform.getYinjkbh2().trim().length() != 0)
			yinjkbhList.add(accountinfoform.getYinjkbh2().trim());
		if (accountinfoform.getYinjkbh3() != null
				&& accountinfoform.getYinjkbh3().trim().length() != 0)
			yinjkbhList.add(accountinfoform.getYinjkbh3().trim());
		if (accountinfoform.getYinjkbh4() != null
				&& accountinfoform.getYinjkbh4().trim().length() != 0)
			yinjkbhList.add(accountinfoform.getYinjkbh4().trim());
		return yinjkbhList;
	}

	// ���������˻�
	private Zhanghb getNewZhanghb(AccountinfoForm accountinfoform) {
		Zhanghb zhanghb;
		zhanghb = new Zhanghb();
		zhanghb.setZhangh(accountinfoform.getAccount());
		zhanghb.setHum(accountinfoform.getAccountname());
		zhanghb.setKaihrq(accountinfoform.getOpendate());
		zhanghb.setZhanghxz(accountinfoform.getZhanghxz());
		zhanghb.setLianxr(accountinfoform.getLinkman());
		zhanghb.setDianh(accountinfoform.getPhone());
		zhanghb.setTongctd(accountinfoform.getAllexchange());
		zhanghb.setFuzr(accountinfoform.getFuzr());
		zhanghb.setFuzrdh(accountinfoform.getFuzrdh());
		zhanghb.setBeiz(accountinfoform.getRemark().trim());
		zhanghb.setLianxr2(accountinfoform.getLinkman2());
		zhanghb.setDianh2(accountinfoform.getPhone2());
		zhanghb.setFuzr2(accountinfoform.getFuzr2());
		zhanghb.setFuzrdh2(accountinfoform.getFuzrdh2());
		zhanghb.setShifdh(accountinfoform.getShifdh());
		zhanghb.setYouwyj("��");
		zhanghb.setYouwzh("��");
		zhanghb.setZuhshzt("δ��");
		zhanghb.setZhanghshzt("����");
		zhanghb.setYinjshzt("δ��");
		//zhanghb.setZhanghzt("��Ч");
		zhanghb.setZhanghzt(accountinfoform.getZhanghzt()==null||"".equals(accountinfoform.getZhanghzt())?"��Ч":accountinfoform.getZhanghzt());
		zhanghb.setZhuzh(accountinfoform.getZhuzh());
		zhanghb.setFuyrq(accountinfoform.getFuyrq());
		zhanghb.setLianxrfjh(accountinfoform.getLianxrfjh());
		zhanghb.setLianxrfjh2(accountinfoform.getLianxrfjh2());
		zhanghb.setFuzrfjh(accountinfoform.getFuzrfjh());
		zhanghb.setFuzrfjh2(accountinfoform.getFuzrfjh2());
		zhanghb.setIschecksmall(accountinfoform.getIschecksmall());
		//���Ų�Ϊ�� �ҳ��Ȳ�Ϊ0 Ҫȥ����λ0�������ݿ�
		String qh=accountinfoform.getLianxrqh();
		zhanghb.setLianxrqh(subAreaCode(qh));
		 qh=accountinfoform.getLianxrqh2();
		zhanghb.setLianxrqh2(subAreaCode(qh));
		qh=accountinfoform.getFuzrqh();
		zhanghb.setFuzrqh(subAreaCode(qh));
		qh=accountinfoform.getFuzrqh2();
		zhanghb.setFuzrqh2(subAreaCode(qh));
		return zhanghb;
	}

	/**
	 * �����˻������޸�
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateAccount(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		//���˻������б���ǰ̨ҳ��
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		String tongdFlag="0";
		request.setAttribute("tongdFlag", tongdFlag);
		
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String zhangh = accountinfoform.getAccount();
		// �¾��˺�ת��
		if (zhangh != null && zhangh.length()!= 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "�˺ţ�[" + zhangh
								+ "]������!");
			}
			zhanghb.setYinjkbh(ZhanghbService.getYinjkhByZhangh(zhanghb.getZhangh()));
			Zhanghb oldZhanghb = copyZhanghb(zhanghb);
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "��û��Ȩ���޸ģ��˺�:["
								+ zhangh + "]!");
			}
			
			String oldyinjkbh = zhanghb.getYinjkbh();
			List<String> yinjkbhList = getYinjkhList(accountinfoform);

			String yinjkbhStr = createYinjkbhStr(yinjkbhList);
			zhanghb.setHum(accountinfoform.getAccountname());
			zhanghb.setKaihrq(accountinfoform.getOpendate());
			zhanghb.setZhanghxz(accountinfoform.getZhanghxz());
			zhanghb.setLianxr(accountinfoform.getLinkman());
			zhanghb.setDianh(accountinfoform.getPhone());
			zhanghb.setTongctd(accountinfoform.getAllexchange());
			zhanghb.setFuzr(accountinfoform.getFuzr());
			zhanghb.setFuzrdh(accountinfoform.getFuzrdh());
			zhanghb.setLianxr2(accountinfoform.getLinkman2());
			zhanghb.setDianh2(accountinfoform.getPhone2());
			zhanghb.setShifdh(accountinfoform.getShifdh());
			zhanghb.setFuzr2(accountinfoform.getFuzr2());
			zhanghb.setFuzrdh2(accountinfoform.getFuzrdh2());
			
			zhanghb.setBeiz(accountinfoform.getRemark());
			
			zhanghb.setLianxrfjh(accountinfoform.getLianxrfjh());
			zhanghb.setLianxrfjh2(accountinfoform.getLianxrfjh2());
			zhanghb.setFuzrfjh(accountinfoform.getFuzrfjh());
			zhanghb.setFuzrfjh2(accountinfoform.getFuzrfjh2());
			zhanghb.setIschecksmall(accountinfoform.getIschecksmall()==null?"0":accountinfoform.getIschecksmall());
			//���Ų�Ϊ�� �ҳ��Ȳ�Ϊ0 Ҫȥ����λ0�������ݿ�
			String qh=accountinfoform.getLianxrqh();
			zhanghb.setLianxrqh(subAreaCode(qh));
			 qh=accountinfoform.getLianxrqh2();
			zhanghb.setLianxrqh2(subAreaCode(qh));
			qh=accountinfoform.getFuzrqh();
			zhanghb.setFuzrqh(subAreaCode(qh));
			qh=accountinfoform.getFuzrqh2();
			zhanghb.setFuzrqh2(subAreaCode(qh));
			
			
			request.setAttribute("logintype", "ztzilxg");
			String gongy = accountinfoform.getGongy();

				List<String> oldYinjkbhList = new ArrayList<String>();// �޸�֮��ʹ�õ�ӡ����
				List<String> commen = new ArrayList<String>();// �޸�ǰ�󲻱��ӡ����
				// ���޸�֮ǰ��֮��Ķ�����ӡ����������ֳ���
				splitYinjkList(oldyinjkbh, yinjkbhList, oldYinjkbhList, commen);
				
				//����ͨ�ұ�־����ѡ�����Ƿ������������
				Org org_forTongd=OrgService.getOrgByCode(zhanghb.getJigh());
				//���û������ϼ�������ʡ�л�����ͬʱ �����ڶ�������
				if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
					tongdFlag="1";
				}
				request.setAttribute("tongdFlag", tongdFlag);
				
				// �ǹ����˻�
				if ("0".equals(gongy)) {
					// ���������ӡ�����Ƿ��Ѿ���ʹ��
					boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
					if (exist) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.zhanghkh.success", "ӡ�����ѱ�ʹ��");
					}
					zhanghb.setYinjkbh(yinjkbhStr);
					ZhanghbService.updateZhanghb(zhanghb, yinjkbhList,
							oldYinjkbhList);
	
					// ��¼��־
					String content = "�޸��˻�(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա����:"
							+ clerk.getCode() + ")";
					this.createManageLog(clerk.getCode(), content);
					this.createAccountManageLog(zhanghb.getZhangh(), "1", clerk,
							oldZhanghb, zhanghb);
					request.setAttribute("logintype", "ztzilxg");

					request.setAttribute("kaih", "kaih");//����ҳ���Ƿ���ת����ҳ��
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.zhanghkh.success", "�޸ĳɹ�!");
				} else {
					accountinfoform.setGongy("0");
					accountinfoform.setYinjkbh("");
					Zhanghb zhanghb_=ZhanghbService.getZhanghb(zhanghb.getZhangh());//ϵͳ���˻���Ϣ
					if(zhanghb_!=null&&zhanghb_.getHum()!=null&&!"".equals(zhanghb_.getHum())){
						List yinjList=ZhanghbService.getYinjb(zhanghb_.getZhangh());
						if(yinjList!=null&&yinjList.size()!=0){
							accountinfoform.setZhuzh("");
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "�ѽ���ӡ���˻��������������˻�");
						}
					}
					Zhanghb gongyzh = ZhanghbService.getZhanghbByYinjkbh(yinjkbhStr);
					if (gongyzh == null) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.zhanghkh.success", "�����˻�������");
					}
					String zhuzh = zhanghb.getZhuzh();
					//���˻�ӡ���������䶯ʱ ִ�����²��� ���¹���
					if (zhuzh == null || zhuzh.equals("")||!zhuzh.equals(gongyzh.getZhangh())) {
						if (!gongyzh.getJigh().equals(zhanghb.getJigh())) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "ֻ����ͬһ�����ڹ���ӡ��");
						}
						if (!gongyzh.getHum().equals(zhanghb.getHum())) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "ֻ�л���һ��ʱ�ſɹ���");
						}
						if (gongyzh.getYinjshzt().equals("δ��")) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "�����˻�["+gongyzh.getZhangh()+"]����δ��ӡ�����޷�����");
						}
						
						zhanghb.setFuyrq(this.getSystemMgrService().getSystetemNowDate().substring(0,10));//������������
						zhanghb.setZhuzh(gongyzh.getZhangh());
						zhanghb.setYinjkbh("");
						zhanghb.setYinjshzt(gongyzh.getYinjshzt());
						zhanghb.setYouwyj(gongyzh.getYouwyj());
						zhanghb.setYouwzh(gongyzh.getYouwzh());
						zhanghb.setZuhshzt(gongyzh.getZuhshzt());
					}
					accountinfoform.setYinjkbh(yinjkbhStr);
					accountinfoform.setGongy("1");
					zhanghb.setYinjkbh("");
				ZhanghbService.updateZhanghb(zhanghb,new ArrayList<String>(),oldYinjkbhList);
				// ��¼��־
				String content = "�޸��˻�(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա����:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				zhanghb.setYinjkbh(yinjkbhStr);
				this.createAccountManageLog(zhanghb.getZhangh(), "1", clerk,
						oldZhanghb, zhanghb);
				request.setAttribute("logintype", "ztzilxg");
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "�޸ĳɹ�!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghkh.success");
		}
	}

	/**
	 * �Ǻ����˻������޸ģ��������ʻ����ڲ�����
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateInnerAccount(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		//���˻������б���ǰ̨ҳ��
		String tongdFlag="0";
		request.setAttribute("tongdFlag", tongdFlag);
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String zhangh = accountinfoform.getAccount();
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// ��֤�˻��Ƿ����
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.inner.zhanghkh.success", "�˺ţ�[" + zhangh
								+ "]������!");
			}
			zhanghb.setYinjkbh(ZhanghbService.getYinjkhByZhangh(zhanghb.getZhangh()));
			Zhanghb oldZhanghb = copyZhanghb(zhanghb);
			// ��֤��ǰ��Ա�����˺�Ȩ��
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.inner.zhanghkh.success", "��û��Ȩ���޸ģ��˺�:["
								+ zhangh + "]!");
			}
			
			String oldyinjkbh = zhanghb.getYinjkbh();
			List<String> yinjkbhList = getYinjkhList(accountinfoform);

			String yinjkbhStr = createYinjkbhStr(yinjkbhList);

			zhanghb.setHum(accountinfoform.getAccountname());
			zhanghb.setKaihrq(accountinfoform.getOpendate());
			zhanghb.setZhanghxz(accountinfoform.getZhanghxz());
			zhanghb.setLianxr(accountinfoform.getLinkman());
			zhanghb.setDianh(accountinfoform.getPhone());
			zhanghb.setTongctd(accountinfoform.getAllexchange());
			zhanghb.setFuzr(accountinfoform.getFuzr());
			zhanghb.setFuzrdh(accountinfoform.getFuzrdh());
			zhanghb.setLianxr2(accountinfoform.getLinkman2());
			zhanghb.setDianh2(accountinfoform.getPhone2());
			zhanghb.setShifdh(accountinfoform.getShifdh());
			zhanghb.setFuzr2(accountinfoform.getFuzr2());
			zhanghb.setFuzrdh2(accountinfoform.getFuzrdh2());
			zhanghb.setBeiz(accountinfoform.getRemark());
			zhanghb.setLianxrfjh(accountinfoform.getLianxrfjh());
			zhanghb.setLianxrfjh2(accountinfoform.getLianxrfjh2());
			zhanghb.setFuzrfjh(accountinfoform.getFuzrfjh());
			zhanghb.setFuzrfjh2(accountinfoform.getFuzrfjh2());
			zhanghb.setIschecksmall(accountinfoform.getIschecksmall()==null?"0":accountinfoform.getIschecksmall());
			//���Ų�Ϊ�� �ҳ��Ȳ�Ϊ0 Ҫȥ����λ0�������ݿ�
			String qh=accountinfoform.getLianxrqh();
			zhanghb.setLianxrqh(subAreaCode(qh));
			 qh=accountinfoform.getLianxrqh2();
			zhanghb.setLianxrqh2(subAreaCode(qh));
			qh=accountinfoform.getFuzrqh();
			zhanghb.setFuzrqh(subAreaCode(qh));
			qh=accountinfoform.getFuzrqh2();
			zhanghb.setFuzrqh2(subAreaCode(qh));
			
			
			request.setAttribute("logintype", "ztzilxg");
			List<String> oldYinjkbhList = new ArrayList<String>();// �޸�֮��ʹ�õ�ӡ����
			List<String> commen = new ArrayList<String>();// �޸�ǰ�󲻱��ӡ����
			// ���޸�֮ǰ��֮��Ķ�����ӡ����������ֳ���
			splitYinjkList(oldyinjkbh, yinjkbhList, oldYinjkbhList, commen);
			
			//����ͨ�ұ�־����ѡ�����Ƿ������������
			Org org_forTongd=OrgService.getOrgByCode(zhanghb.getJigh());
			//���û������ϼ�������ʡ�л�����ͬʱ �����ڶ�������
			if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
				tongdFlag="1";
			}
			request.setAttribute("tongdFlag", tongdFlag);
			
				// ���������ӡ�����Ƿ��Ѿ���ʹ��
				boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
				if (exist) {
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.inner.zhanghkh.success", "ӡ�����ѱ�ʹ��");
				}
				zhanghb.setYinjkbh(yinjkbhStr);
				ZhanghbService.updateZhanghb(zhanghb, yinjkbhList,
						oldYinjkbhList);

				// ��¼��־
				String content = "�޸��˻�(�˺�Ϊ" + zhanghb.getZhangh() + ";��Ա����:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "1", clerk,
						oldZhanghb, zhanghb);
				request.setAttribute("logintype", "zilxg");

				request.setAttribute("kaih", "kaih");//����ҳ���Ƿ���ת����ҳ��
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.inner.zhanghkh.success", "�޸ĳɹ�!");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.inner.zhanghkh.success");
		}
	}

	
	private void splitYinjkList(String oldyinjkbh, List<String> yinjkbhList,
			List<String> oldYinjkbhList, List<String> commen) {
		if (oldyinjkbh != null && oldyinjkbh.length() != 0
				&& oldyinjkbh.indexOf("|") != -1) {
			oldyinjkbh = oldyinjkbh.replace('|', ',');
			String[] str = oldyinjkbh.split(",", 0);
			for (String string : str) {
				if (string != null && string.trim().length() != 0) {
					oldYinjkbhList.add(string.trim());
				}
			}
		} else {
			oldYinjkbhList.add(oldyinjkbh);
		}
		for (String string : oldYinjkbhList) {
			if (yinjkbhList.contains(string)) {
				yinjkbhList.remove(string);
				commen.add(string);
			}
		}
		for (String string : commen) {
			oldYinjkbhList.remove(string);
		}
	}

	// �����˺�
	private Zhanghb copyZhanghb(Zhanghb zhanghb) {
		Zhanghb oldZhanghb = new Zhanghb();
		oldZhanghb.setHum(zhanghb.getHum());
		oldZhanghb.setTongctd(zhanghb.getTongctd());
		oldZhanghb.setZhanghxz(zhanghb.getZhanghxz());
		oldZhanghb.setBeiz(zhanghb.getBeiz());
		oldZhanghb.setLianxr(zhanghb.getLianxr());
		oldZhanghb.setDianh(zhanghb.getDianh());
		oldZhanghb.setFuzr(zhanghb.getFuzr());
		oldZhanghb.setFuzrdh(zhanghb.getFuzrdh());
		oldZhanghb.setLianxr2(zhanghb.getLianxr2());
		oldZhanghb.setDianh2(zhanghb.getDianh2());
		oldZhanghb.setFuzr2(zhanghb.getFuzr2());
		oldZhanghb.setFuzrdh2(zhanghb.getFuzrdh2());
		oldZhanghb.setShifdh(zhanghb.getShifdh());
		oldZhanghb.setYinjkbh(zhanghb.getYinjkbh());
		oldZhanghb.setZhuzh(zhanghb.getZhuzh());
		oldZhanghb.setLianxrfjh(zhanghb.getLianxrfjh());
		oldZhanghb.setLianxrfjh2(zhanghb.getLianxrfjh2());
		oldZhanghb.setLianxrqh(zhanghb.getLianxrqh());
		oldZhanghb.setLianxrqh2(zhanghb.getLianxrqh2());
		oldZhanghb.setFuzrfjh(zhanghb.getFuzrfjh());
		oldZhanghb.setFuzrfjh2(zhanghb.getFuzrfjh2());
		oldZhanghb.setFuzrqh(zhanghb.getFuzrqh());
		oldZhanghb.setFuzrqh2(zhanghb.getFuzrqh2());	
		oldZhanghb.setIschecksmall(zhanghb.getIschecksmall());
		return oldZhanghb;
	}

	// �˻���Ϣ���Կ���ҳ��
	public ActionForward getAccountForAjax(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String account = request.getParameter("account");
		String accountname = "";
		String zhanghxz = "";
		String kaihrq = "";
		String allexchange = "";
		String linkman = "";
		String phone = "";
		String fuzr = "";
		String fuzrdh = "";
		String beiz = "";
		String yinjkbh = "";
		String yinjkbh2 = "";
		String yinjkbh3 = "";
		String yinjkbh4 = "";
		String gongy = "";
		String zhanghzt = "";
		String yinjshzt = "";
		String youwzh = "";
		String hasYS = "";
		String accorgno = "";
		String youwYJ="";
		String tongd="0";
		String jiankgy="";
		String qiyrq="";

		String linkman2 = "";
		String phone2 = "";
		String fuzr2 = "";
		String fuzrdh2 = "";
		String shifdh="";//0����  1����

		String zhuzh="";
		
		String qh1="";
		String qh2="";
		String qh3="";
		String qh4="";
		String fjh1="";
		String fjh2="";
		String fjh3="";
		String fjh4="";
		String yinjFlag="";
		
		String ischecksmall="0";
		
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		StringBuffer accountin = new StringBuffer();
		// �¾��˺�ת��
		if (account != null && account.length() != 17) {
			account = ZhanghbService.parseTypeN(account, 17);
		}
		try {
			out = response.getWriter();
			Zhanghb accountinfo = ZhanghbService.getZhanghb(account);
			if (accountinfo == null) {
				out.print("fail002");
				out.close();
				return null;
			}
			if (accountinfo != null) {
				// ��֤��ǰ��Ա�����˺� Ȩ��
				boolean bool = this.getSystemMgrService().CanOperDesOrg(
						clerk.getOrgcode(), accountinfo.getJigh());
				if (!bool) {
					out.print("fail001");
					out.close();
				}
				accountname = accountinfo.getHum() == null ? "" : accountinfo
						.getHum();
				allexchange = accountinfo.getTongctd() == null ? ""
						: accountinfo.getTongctd();
				zhanghxz = accountinfo.getZhanghxz() == null ? "" : accountinfo
						.getZhanghxz();
				kaihrq = accountinfo.getKaihrq() == null ? "" : accountinfo
						.getKaihrq();
				linkman = accountinfo.getLianxr() == null ? "" : accountinfo
						.getLianxr();

				phone = accountinfo.getDianh() == null ? "" : accountinfo
						.getDianh();
				fuzr = accountinfo.getFuzr() == null ? "" : accountinfo
						.getFuzr();
				fuzrdh = accountinfo.getFuzrdh() == null ? "" : accountinfo
						.getFuzrdh();
				

				linkman2 = accountinfo.getLianxr2() == null ? "" : accountinfo
						.getLianxr2();

				phone2 = accountinfo.getDianh2() == null ? "" : accountinfo
						.getDianh2();
				fuzr2 = accountinfo.getFuzr2() == null ? "" : accountinfo
						.getFuzr2();
				fuzrdh2 = accountinfo.getFuzrdh2() == null ? "" : accountinfo
						.getFuzrdh2();
				shifdh=accountinfo.getShifdh()==null?"0":accountinfo.getShifdh();//Ϊ��Ĭ��Ϊ��
				
				beiz = accountinfo.getBeiz() == null ? "" : accountinfo
						.getBeiz();
				// new
				accorgno = accountinfo.getJigh() == null ? "" : accountinfo
						.getJigh();
				zhanghzt = accountinfo.getZhanghzt() == null ? "" : accountinfo
						.getZhanghzt();
				yinjshzt = accountinfo.getYinjshzt() == null ? "" : accountinfo
						.getYinjshzt();
				youwzh = accountinfo.getYouwzh() == null ? "" : accountinfo
						.getYouwzh();
				youwYJ=accountinfo.getYouwyj() == null ? "��" : accountinfo
						.getYouwyj();
				yinjshzt=accountinfo.getYinjshzt() == null ? "δ��" : accountinfo
						.getYouwyj();
				
				yinjkbh = accountinfo.getYinjkbh() == null ? "" : accountinfo
						.getYinjkbh();
				zhuzh=accountinfo.getZhuzh() == null ? "" : accountinfo
						.getZhuzh();
				gongy = accountinfo.getZhuzh() == null
						|| accountinfo.getZhuzh() == "" ? "0" : "1";
				
				 qh1=bulidAreaCode(accountinfo.getLianxrqh());
				 qh2=bulidAreaCode(accountinfo.getLianxrqh2());
				 qh3=bulidAreaCode(accountinfo.getFuzrqh());
				 qh4=bulidAreaCode(accountinfo.getFuzrqh2());
				 fjh1=accountinfo.getLianxrfjh()== null ? "" : accountinfo.getLianxrfjh();
				 fjh2=accountinfo.getLianxrfjh2()== null ? "" : accountinfo.getLianxrfjh2();
				 fjh3=accountinfo.getFuzrfjh()== null ? "" : accountinfo.getFuzrfjh();
				 fjh4=accountinfo.getFuzrfjh2()== null ? "" : accountinfo.getFuzrfjh2();
				
				 ischecksmall=accountinfo.getIschecksmall()== null ? "0" : accountinfo.getIschecksmall();
				if (gongy.equals("1")) {
					Zhanghb gongyzh = ZhanghbService.getZhanghb(accountinfo
							.getZhuzh());
					// to do
					yinjkbh = gongyzh == null || gongyzh.getYinjkbh() == null ? ""
							: gongyzh.getYinjkbh();

				}
				List Yinj=ZhanghbService.getYinjb(account);
				if (Yinj != null && Yinj.size() != 0) {
					yinjFlag = "1";
				} else {
					yinjFlag = "0";
				}
				//����ӡ��
				List YSyinj = ZhanghbService.getLastYSyj(account);
				if(gongy.equals("1")){
					YSyinj = ZhanghbService.getLastYSyj(accountinfo.getZhuzh());
				}
				if (YSyinj != null && YSyinj.size() != 0) {
					hasYS = "1";
				} else {
					hasYS = "0";
				}
				
				if (yinjkbh != null && yinjkbh.length() != 0
						&& yinjkbh.indexOf("|") != -1) {
					yinjkbh = yinjkbh.replace('|', ',');
					List<String> yinjkhList = createYinjkhList(yinjkbh);
					yinjkbh = yinjkhList.get(0);
					yinjkbh2 = yinjkhList.get(1);
					yinjkbh3 = yinjkhList.get(2);
					yinjkbh4 = yinjkhList.get(3);
				}
				
				//�жϸ��˺����ڻ���֮���Ƿ��ж�������
				Org org= OrgService.getOrgByCode(accountinfo.getJigh());
				if(org!=null&&org.getWdflag().equals("3")&&org.getParentCode().equals(org.getShOrgCode())){
					tongd="1";
				}
				//������������ Ĭ��Ϊ�������ڵĴ���
				if(kaihrq!=null&&kaihrq.length()==10){
					qiyrq=this.getSystemMgrService().getSystetemNextDate().substring(0,10);
				}
				
				
				//��ȡ��ǰ�˺ŵĽ����Ա ��δ����������Ϊ""
				if(!accountinfo.getYouwyj().equals("��")){
					jiankgy=ZhanghbService.getJiankgy(account);
				}
				
			}
			accountin.append(accountname).append(",").append(zhanghxz).append(
					",").append(kaihrq).append(",").append(allexchange).append(
					",").append(linkman).append(",").append(phone).append(",")
					.append(fuzr).append(",").append(fuzrdh).append(",")
					.append(beiz).append(",").append(yinjkbh).append(",")
					.append(gongy).append(",").append(yinjkbh2).append(",")
					.append(yinjkbh3).append(",")
					.append(yinjkbh4)
					// new
					.append(",").append(zhanghzt).append(",").append(yinjshzt)
					.append(",").append(youwzh).append(",").append(hasYS)
					.append(",").append(accorgno).append(",").append(youwYJ)
					.append(",").append(yinjshzt).append(",").append(tongd).append(",")
					.append(jiankgy).append(",").append(qiyrq)
					//��new
					.append(",").append(linkman2).append(",").append(phone2)
					.append(",").append(fuzr2).append(",").append(fuzrdh2).append(",").append(shifdh).append(",").append(zhuzh).append(",").append(account)
					//20140724 �������� �ֻ���
					.append(",").append(qh1).append(",").append(fjh1)
					.append(",").append(qh2).append(",").append(fjh2).append(",").append(qh3).append(",").append(fjh3).append(",").append(qh4).append(",").append(fjh4)
					//С��
					.append(",").append(ischecksmall).append(",").append(yinjFlag);
			out.print(accountin.toString());
			out.close();
			return null;
		} catch (Exception e) {
			out.print("fail003");
			e.printStackTrace();

			out.close();
			return null;
		}
	}

	// ���ӡ�������ַ���
	private List<String> createYinjkhList(String yinjkbh) {

		List<String> yinjkhList = new ArrayList<String>();
		if (yinjkbh != null && yinjkbh.trim().length() != 0) {
			String[] str = yinjkbh.split(",", 0);
			for (int i = 0; i < str.length; i++) {
				if (i == 0) {
					yinjkhList.add(str[0]);
				} else if (i == 1) {
					yinjkhList
							.add(str[1] == null || str[1] == "" ? "" : str[1]);
				} else if (i == 2) {
					yinjkhList
							.add(str[2] == null || str[2] == "" ? "" : str[2]);
				} else if (i == 3) {
					yinjkhList
							.add(str[3] == null || str[3] == "" ? "" : str[3]);
				}
			}
		}
		while (yinjkhList.size() < 4) {
			yinjkhList.add("");
		}
		return yinjkhList;
	}

	// �˻���Ϣ����ǰ̨��ӡҳ��
	public ActionForward getAccountForQiantyy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String account = request.getParameter("account");
		String accountname = "";
	
		String gongy = "";
		String zhanghzt = "";
		String yinjshzt = "";
		String youwzh = "";
		String hasYSyj = "";
		String accorgno = "";
		String hasYSzh = "";
		String beiz="";
		

		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		StringBuffer accountin = new StringBuffer();
		// �¾��˺�ת��
		if (account != null && account.length() != 17) {
			account = ZhanghbService.parseTypeN(account, 17);
		}
		try {
			out = response.getWriter();
			Zhanghb accountinfo = ZhanghbService.getZhanghb(account);
			if (accountinfo == null) {
				out.print("fail002");
				out.close();
				return null;
			}
			if (accountinfo != null) {
				// ��֤��ǰ��Ա�����˺� Ȩ��
				boolean bool = this.getSystemMgrService().CanTongd(
						clerk.getCode(), account);
				//bool=true;
				if (!bool) {
					out.print("fail001");
					out.close();
				}
				accountname = accountinfo.getHum() == null ? "" : accountinfo
						.getHum();
			
				// new
				accorgno = accountinfo.getJigh() == null ? "" : accountinfo
						.getJigh();
				zhanghzt = accountinfo.getZhanghzt() == null ? "" : accountinfo
						.getZhanghzt();
				yinjshzt = accountinfo.getYinjshzt() == null ? "" : accountinfo
						.getYinjshzt();
				youwzh = accountinfo.getYouwzh() == null ? "" : accountinfo
						.getYouwzh();
				
				beiz=accountinfo.getBeiz()==null?"":accountinfo.getBeiz();
				
				gongy = accountinfo.getZhuzh() == null
						|| accountinfo.getZhuzh() == "" ? "0" : "1";
				
				
				//����ӡ��
				List YSyinj = ZhanghbService.getLastYSyj(account);
				
				
				if(gongy.equals("1")){
					YSyinj = ZhanghbService.getLastYSyj(accountinfo.getZhuzh());
					Zhanghb zhuzh = ZhanghbService.getZhanghb(accountinfo.getZhuzh());
					youwzh = zhuzh.getYouwzh() == null ? "" : zhuzh
							.getYouwzh();
				}
				if (YSyinj != null && YSyinj.size() != 0) {
					hasYSyj = "1";
				} else {
					hasYSyj = "0";
				}
				
				//����ӡ��
				List YSzuh = ZhanghbService.getLastYSzh(account);
				
				
				if(gongy.equals("1")){
					YSzuh = ZhanghbService.getLastYSzh(accountinfo.getZhuzh());
				}
				if (YSzuh != null && YSzuh.size() != 0) {
					hasYSzh = "1";
				} else {
					hasYSzh = "0";
				}
				
				
			}
			accountin.append(accountname).append(",").append(zhanghzt).append(",").append(yinjshzt)
					.append(",").append(youwzh).append(",").append(hasYSyj)
					.append(",").append(accorgno).append(",").append(account).append(",").append(hasYSzh).append(",").append(beiz);
			out.print(accountin.toString());
			out.close();
			return null;
		} catch (Exception e) {
			out.print("fail003");
			e.printStackTrace();

			out.close();
			return null;
		}
	}


	/**
	 * ��ϵ�˲�ѯ�ӿ�
	 */
	public ActionForward getAccountLinkManInfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		AccountinfoForm accountinfoForm = (AccountinfoForm) actionForm;
		Zhanghb zhanghb = new Zhanghb();
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		String retcode = "0000";
		String retinfo = "";
		String jsonStr = "";
		String zhangh = accountinfoForm.getAccno();

		try {
			out = response.getWriter();
//			DesUtils des = new DesUtils("zkztinterface");
//			String jiaoywxx = des.decrypt(accountinfoForm.getJiaoyw());
			String jiaoywxx  = DesUtil.decrypt(accountinfoForm.getJiaoyw());
			// �¾��˺�ת��
			if (zhangh == null || zhangh.trim().equals("")) {
				retcode = "0001";
				retinfo = "�Ƿ����ģ���ѯ��ϵ��ʧ��";
				jsonStr = JsonSystemTool.toJspnForAccountLinkManInfo(
						new Zhanghb(), retcode, retinfo);
				return null;
			}
			if (zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			if ("agree2seal".equals(jiaoywxx)) {
				try {
					zhanghb = ZhanghbService.getZhanghb(zhangh);
					jsonStr = JsonSystemTool.toJspnForAccountLinkManInfo(
							zhanghb, retcode, "");
				} catch (Exception e) {
					retcode = "0001";
					retinfo = "�˻�������";
					jsonStr = JsonSystemTool.toJspnForAccountLinkManInfo(
							new Zhanghb(), retcode, retinfo);
				}
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			retcode = "0001";
			retinfo = "�Ƿ����ģ���ѯ��ϵ��ʧ��";
			jsonStr = JsonSystemTool.toJspnForAccountLinkManInfo(new Zhanghb(),
					retcode, retinfo);
		} finally {
			if(null!=out){
				out.print(jsonStr);
				out.flush();
				out.close();
			}
		}
		return null;
	}
	/**
	 * ���ݼ��˺Ų�ѯ�����˺�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAccountFromShort(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		String zhangh=request.getParameter("account");
		try{
			out = response.getWriter();
			zhangh=ZhanghbService.getZhanghFromShort(zhangh);
			out.print(zhangh);
			return null;
		}catch (Exception e) {
			out.print(zhangh);
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
		
	}

	/**
	 * ��ת�˻�ͨ������
	 * */
	public ActionForward accountAllexchangeView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return actionMapping
					.findForward("accountinfo.net.accountAllexchange.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.accountAllexchange.success");
		}
	}

	/**
	 * �����˻�����˺�ͨ����Ϣ
	 */
	public ActionForward getAccountForAllexchange(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String zhangh = (String) request.getParameter("zhangh");
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		request.setAttribute("pass", "1");
		if (zhangh == null || zhangh.equals("")) {
			return actionMapping
					.findForward("accountinfo.net.accountAllexchange.success");
		}
		try {
			// �¾��˺�ת��

			if (zhangh != null && zhangh.length() !=17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			accountinfoform.setAccount(zhangh);
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			if (zhanghb == null) {
				zhanghb =new Zhanghb();
				zhanghb.setZhangh(zhangh);
				request.setAttribute("zhanghb", zhanghb);
				request.setAttribute("pass", "0");
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.accountAllexchange.success",
						"�˺�:[" + zhangh + "]������!");
			
			}
			// �жϵ�ǰ��Ա�Ƿ���Ȩ�޲鿴���˺���Ϣ
			if (!this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh())) {
				request.setAttribute("pass", "0");
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.accountAllexchange.success",
						"��û��Ȩ���޸ģ��˺�:[" + zhangh + "]!");
			}
			// ����ͨ�һ�����Ϣ�����ɻ����б�
			String tongdsz = zhanghb.getTongdsz();
			List<Org> orgList = new ArrayList<Org>();
			if (tongdsz != null && !tongdsz.equals("")) {
				String[] tongd = tongdsz.replace("|", ",").split(",", 0);
				for (String string : tongd) {
					if (string != null && !string.trim().equals("")) {
						orgList.add(OrgService.getOrgByCode(string));
					}
				}
			}
			// ���˻���Ϣ�ͻ�����Ϣ����ҳ��
			accountinfoform.setAccount(zhangh);
			accountinfoform.setAllexchange(zhanghb.getTongctd());
			request.setAttribute("orgList", orgList);
			request.setAttribute("orgNum", orgList.size());
			request.setAttribute("zhanghb", zhanghb);
		} catch (BusinessException e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.accountAllexchange.success");
		}
		return actionMapping
				.findForward("accountinfo.net.accountAllexchange.success");
	}

	/**
	 * ɾ��ͨ�һ���
	 */
	public ActionForward deleteTongdsz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String orgCode = (String) request.getParameter("orgCode");
		String zhangh = (String) request.getParameter("zhangh");
		// �¾��˺�ת��

		if (zhangh != null && zhangh.length() !=17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		if (zhangh == null || zhangh.equals("")) {
			return actionMapping
					.findForward("accountinfo.net.accountAllexchange.success");
		}
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			if (zhanghb == null) {
				throw new BusinessException();
			}
			// �жϵ�ǰ��Ա�Ƿ���Ȩ�޲������˺�
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.accountAllexchange.success",
						"��û��Ȩ���޸ģ��˺�:[" + zhangh + "]!");
			}
			// ����ͨ�һ�����Ϣ�����ɻ����б�
			String tongdsz = zhanghb.getTongdsz();
			List<String> orgList = new ArrayList<String>();
			if (tongdsz != null && !tongdsz.equals("")) {
				String[] tongd = tongdsz.replace("|", ",").split(",", 0);
				for (String string : tongd) {
					if (string != null && !string.trim().equals("")
							&& !string.equals(orgCode)) {
						orgList.add(string);
					}
				}
			}
			// ���˻����µ�ͨ�һ�����Ϣ����zhanghb�У�����¼��־
			tongdsz = createStr(orgList);
			zhanghb.setTongdsz(tongdsz);
			ZhanghbService.update(zhanghb);
			String content="�˺�["+zhanghb.getZhangh()+"]ɾ��ͨ�һ���:"+orgCode;
			createAccountManageLog(zhanghb.getZhangh(), "1",content, clerk);
			createManageLog(clerk.getCode(), content);
			// �ض����˻�ͨ�һ����б�ҳ��
			response
					.sendRedirect("accountinfo.do?method=getAccountForAllexchange&zhangh="
							+ zhangh);
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.accountAllexchange.success");
		}
		return null;
	}

	private String createStr(List<String> orgList) {
		if (orgList == null) {
			return "";
		}
		if (orgList.size() == 0) {
			return "";
		}
		StringBuffer msg = new StringBuffer();
		for (String string : orgList) {
			if (string != null && !string.trim().equals("")) {
				msg.append("|").append(string);
			}
		}
		if (msg.length() != 0) {
			return msg.substring(1).trim();
		}
		return "";
	}

	/**
	 * ��ת�����˻�ͨ�һ���
	 * */
	public ActionForward addAllexchangeView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhangh = request.getParameter("zhangh");
			// �¾��˺�ת��
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			if (zhangh != null) {
				Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
				if (zhanghb == null) {
					zhanghb =new Zhanghb();
					zhanghb.setZhangh(zhangh);
				}
				request.setAttribute("zhanghb", zhanghb);
			}
			return actionMapping
					.findForward("accountinfo.net.addAllexchange.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.addAllexchange.success");
		}
	}

	/**
	 * �����˻�ͨ�һ���
	 * */
	public ActionForward addAllexchange(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
			String zhangh = accountinfoform.getAccount();
			// //�¾��˺�ת��
			//			
			// if (zhangh != null &&zhangh.length() > 17) {
			// zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			// }
			String tongd = accountinfoform.getOrgcode();
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			if (zhangh != null) {
					Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
					if (zhanghb == null) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.addAllexchange.success",
						"�˻���Ϣ��ѯʧ��!");
					}
					Org tongdOrg = OrgService.getOrgByCode(tongd);
					request.setAttribute("zhanghb", zhanghb);
					if (tongdOrg == null) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.addAllexchange.success",
								"��ӻ���������!");
					}
					// �жϵ�ǰ��Ա�Ƿ���Ȩ�޲������˺�
					boolean bool = this.getSystemMgrService().CanOperDesOrg(
							clerk.getOrgcode(), zhanghb.getJigh());
					if (!bool) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.addAllexchange.success",
								"��û��Ȩ���޸ģ��˺�:[" + zhangh + "]!");
					}
					if(zhanghb.getJigh().equals(tongd)){
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.addAllexchange.success",
								"���������˻�����������!");
					}
					/*String tongctd = zhanghb.getTongctd();
					if (tongctd.equals("һ������")) {
						if (!this.getSystemMgrService().CanOperDesOrg(
								clerk.getShOrgCode(), tongd)) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.addAllexchange.success",
									"���õ�ͨ�����㲻�����˻�������ͬһ������!");
						}
					}
					if (tongctd.equals("��������")) {
						Org erjOrg = OrgService.getOrgByCode(zhanghb.getJigh());
						String erjOrgCode = erjOrg.getWdflag().equals("3") ? erjOrg
								.getParentCode() : erjOrg.getCode();
						if (this.getSystemMgrService().CanOperDesOrg(erjOrgCode,
								tongd)) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.addAllexchange.success",
									"���õ�ͨ�����㲻�����˻�������ͬ��������!");
						}
					}*/
					String tongdsz = zhanghb.getTongdsz();
					if (tongdsz == null || tongdsz.trim().length() == 0) {
						zhanghb.setTongdsz(tongd);
					} else {
						zhanghb.setTongdsz(tongdsz + "|" + tongd);
					}
					ZhanghbService.update(zhanghb);
					String content="�˺�["+zhanghb.getZhangh()+"]���ͨ�һ���:"+tongd;
					createAccountManageLog(zhanghb.getZhangh(), "1", content, clerk);
					createManageLog(clerk.getCode(),content);
					request.setAttribute("zhangh", zhanghb.getZhangh());
					request.setAttribute("tongd", zhanghb.getTongdsz());
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.addAllexchange.success", "��ӳɹ�!");
			}
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.addAllexchange.success", "���ʧ��!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.addAllexchange.success");
		}
	}
	
	/**
	 * ��תӡ�������ò�ѯ�˻�ҳ��
	 * */
	public ActionForward forqueryyinjkshareView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if(actionForm!=null){
				actionForm.reset(actionMapping, request);
			}
			return actionMapping
					.findForward("yinjkshare.view");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkshare.view");
		}
	}


	/**
	 * ���ݲ�����ѯӡ�������õ��˻�
	 */
	public ActionForward queryyinjkshare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm aform = (AccountinfoForm) form;

		try {
			// ��Ա��Ϣ��ʽת��Ϊjson
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);

			String orgcode = aform.getOrgcode();
			String account = aform.getAccount();
			String yinjkbh = aform.getYinjkbh();
			// //�¾��˺�ת��
			if (account != null && account.length() != 17) {
				account = ZhanghbService.parseTypeN(account, 17);
			}
			aform.setAccount(account);
			
			if (orgcode != null && !"".equals(orgcode)) {
				Org org = OrgService.getOrgByCode(orgcode);
				if (org == null) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", "������Ч,����������!");
				}

				if (!this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(), orgcode)) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", " �]�в�û�����Ȩ��!");
				}
			}
			if (account != null && !"".equals(account)) {
				Zhanghb zhanghb = ZhanghbService.getZhanghb(account);
				if (zhanghb == null) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", "�˻�������!");
				}
				if (!this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(), zhanghb.getJigh())) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", " �]�в���˺ŵ�Ȩ��!");
				}
				if (zhanghb.getZhanghzt().equals("����")) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", " ���˻�������!");
				}
			}
			if (yinjkbh != null && !"".equals(yinjkbh)) {
				Yinjk yinjk = yinjkService.getYinjkByYinjkbh(yinjkbh);
				if (yinjk == null) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", "������Ч,ӡ����������!");
				}
			}
			// ����request�еĲ���������ҳ����
			TabsBo TabsBo = this.createTabsBo(request);

			// ����ҳ������service
			ZhanghbServiceImpl zhanghbServiceImpl = (ZhanghbServiceImpl) ZhanghbService;
			zhanghbServiceImpl.setTabsService(TabsBo);

			// ִ��SQL��䣬���ز�ѯ���
			if (orgcode == null || "".equals(orgcode)) {

				orgcode = clerk.getOrgcode();
			}
			TabsBo tabsBo = ZhanghbService.getYinjkShareList(orgcode, account,
					yinjkbh);
			//request.setAttribute("jlist", tabsBo.getParamterMapStr());
			//request.setAttribute("jsql", tabsBo.getSql());
			aform.setYinjkbh(yinjkbh);
			// ������ʾ
			this.showTabsModel(request, tabsBo);

			return super.showMessageJSPForFeny(mapping, request, tabsBo,
					"yinjkshare.success");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request,
					"yinjkshare.success");
		}
	}
	
	/**
	 * ��ѯ��������
	 */
	public ActionForward queryToDoZhangh(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm aform = (AccountinfoForm) form;
		String jigh =(String)request.getAttribute("jigh");
		if(jigh==null||jigh.trim().equals("")){
			jigh=clerk.getOrgcode();
		}
		try {
			List<Zhanghb> zhanghbList=new ArrayList<Zhanghb>();
			zhanghbList=ZhanghbService.getToDoZhanghbList(jigh);
			request.setAttribute("zhanghbList", zhanghbList);
			return	mapping.findForward("zhangh_list_todo");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request,
					"zhangh_list_todo");
		}
	}
	public ActionForward getTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		response.setCharacterEncoding("GBK");
		PrintWriter out = null;
		String jigh=request.getParameter("jigh");
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try{
			out = response.getWriter();
			
			if(jigh==null||jigh.trim().length()==0){
				if(clerk==null||clerk.getOrgcode()==null||clerk.getOrgcode().equals("")){
					out.print("0");
					return null;
				}else{
					jigh=clerk.getOrgcode();
				}
			}
			int num=ZhanghbService.countTodoZhanghbList(jigh);
			out.print(num);
			return null;
		}catch (Exception e) {
			out.print("0");
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
		
	}
	
}