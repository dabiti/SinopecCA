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
	 * 新版简单帐号跳转
	 */
	public ActionForward net_view(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		// 将柜员信息格式化>json
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
	 * 导入新旧简账户对照信息 (导入介质：excle)
	 */
	public ActionForward importOldAccountinfo_excel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("开始导入新旧简账号对照信息表");
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
						"上传文件为不能为空!");
			}
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
		//	boolean isImport = false;
			List<String> errorlist = ZhanghbService.importZhanghzhb(sheet);
			ZhanghbService.deleteZhanghb_Error(shenghh, "1");
			String content="柜员["+clerk.getCode()+"]导入一级分行["+clerk.getShOrgCode()+"]下新旧简账户对照信息(Excel)";
			this.createManageLog(clerk.getCode(), content);
			log.info("导入新旧简账号对照信息表完成");
				if(errorlist!=null&&errorlist.size()>0){
					ZhanghbService.inserZhanghb_Error(shenghh, errorlist, "1");
					request.setAttribute("file", "0");
					return this.showMessageJSP(mapping, request,
							"uploadOldaccountinfo_excel", "导入成功,"+errorlist.size()+"个账户存在格式错误信息!");
				}else{
					return  this.showMessageJSP(mapping, request,
						"uploadOldaccountinfo_excel", "导入成功,无格式错误信息!");
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
	 * 查询错误列表
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
				out.print("柜员不存在一级分行号，请检查柜员信息");
				out.flush();
				return  null;
			}
				List<String> errorlist=ZhanghbService.getZhanghb_Error(shenghh, type);//将Excel中的信息转换校验格式导入zhanghb_temp
				Collections.sort(errorlist);
				for (String string : errorlist) {
					out.print(string+"\r\n");
				}
				out.flush();
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			out.print("查询错误!");
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
	 * 导入账户信息 (导入介质：excle)
	 */
	public ActionForward importAccountinfo_excel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("开始导入存量账号信息表");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		FormFile file = (FormFile) accountinfoform.getFile();
		InputStream input = null;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String shenghh=clerk.getShOrgCode();
		PrintWriter out=null;
		if(shenghh==null||"".equals(shenghh.trim())){
			return  this.showMessageJSP(mapping, request,
					"uploadaccountinfo_excel", "柜员一级分行号不存在，请检查柜员信息!");
		}
		try{
			input = file.getInputStream();
			int size = input.available();
			if (size <= 0) {
				return this.showMessageJSP(mapping, request, "uploadaccountinfo_excel",
						"上传文件为不能为空!");
			}
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			boolean isImport = false;
			isImport = ZhanghbService.importZhanghbTemp(shenghh,sheet);
			if(isImport){
				String content="柜员["+clerk.getCode()+"]导入一级分行["+clerk.getShOrgCode()+"]下账户信息(Excel)";
				this.createManageLog(clerk.getCode(), content);
				List<Zhanghb> zhanghb_excel_list=ZhanghbService.getZhanghbExcelInfo(shenghh);
				List<String> errorlist=ZhanghbService.checkZhanghbExcelInfo(zhanghb_excel_list);//将Excel中的信息转换校验格式导入zhanghb_temp
				Collections.sort(errorlist);
				ZhanghbService.deleteZhanghb_Excel(shenghh);//删除Excel临时表
				ZhanghbService.deleteZhanghb_Error(shenghh,"0");//删除Excel临时表
				log.info("开始导入存量账号信息表完成");
				if(errorlist!=null&&errorlist.size()>0){
					ZhanghbService.inserZhanghb_Error(shenghh, errorlist, "0");
					request.setAttribute("file", "0");
					 return  this.showMessageJSP(mapping, request,
							"uploadaccountinfo_excel", "导入成功,"+errorlist.size()+"个账户存在格式错误信息!");
				}else{
					return  this.showMessageJSP(mapping, request,
						"uploadaccountinfo_excel", "导入成功,无格式错误信息!");
				}
			}else{
				return  this.showMessageJSP(mapping, request,
						"uploadaccountinfo_excel", "导入失败,请重新尝试!");
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
	
	// 集中建库账户信息回显快速开户页面
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
		String shifdh="";//0：是  1：否

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
		// 新旧账号转换
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
				shifdh=accountinfo.getShifdh()==null?"0":accountinfo.getShifdh();//为空默认为是
				
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
				youwYJ=accountinfo.getYouwyj() == null ? "无" : accountinfo
						.getYouwyj();
				yinjshzt=accountinfo.getYinjshzt() == null ? "未审" : accountinfo
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
					//更new
					.append(",").append(linkman2).append(",").append(phone2)
					.append(",").append(fuzr2).append(",").append(fuzrdh2).append(",").append(shifdh).append(",").append(zhuzh).append(",").append(account)
					//20140724 增加区号 分机号
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
	 * 账户快速开户跳转页面
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward FortongbAccountinfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//将系统时间返回前台页面
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		AccountinfoForm accountinfoForm=(AccountinfoForm)actionForm;
		accountinfoForm.reset(actionMapping, request);
		//查询账户性质
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		return actionMapping.findForward("fastcreateaccount");
	}
	
	
	
	/**
	 * 账户快速开户
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward TongbAccountinfoFromTemp(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//将系统时间返回前台页面
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		try{
			ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
			request.setAttribute("zhanghxzlist", zhanghxzList);
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
			Zhanghb zhanghb = null;
			zhanghb = getNewZhanghb(accountinfoform);//页面填写账户信息
			zhanghb.setJigh(clerk.getOrgcode());
			String zhuzh=zhanghb.getZhuzh();
			if(zhuzh!=null&&!"".equals(zhuzh)){
				Zhanghb zhanghb_=ZhanghbService.getZhanghb(zhanghb.getZhangh());//系统中账户信息
				if(zhanghb_!=null&&zhanghb_.getHum()!=null&&!"".equals(zhanghb_.getHum())){
					List yinjList=ZhanghbService.getYinjb(zhanghb_.getZhangh());
					if(yinjList!=null&&yinjList.size()!=0){
						return super.showMessageJSP(actionMapping, request,
								"fastcreateaccount", "已建立印鉴账户不允许共用其他账户");
					}
				}
				Zhanghb zhuzhinfo=ZhanghbService.getZhanghb(zhuzh);
				if(zhuzhinfo==null||zhuzhinfo.getZhangh()==null||"".equals(zhuzhinfo.getZhangh())){
					return super.showMessageJSP(actionMapping, request,
							"fastcreateaccount", "共用账户尚未开户，请先操作共用账户");
				}else if(zhuzhinfo.getYinjshzt()==null||"未审".equals(zhuzhinfo.getYinjshzt())){
					return super.showMessageJSP(actionMapping, request,
							"fastcreateaccount", "共用账户不存在已审印鉴，请先操作共用账户");
				}else if(zhuzhinfo.getZhuzh()!=null&&!"".equals(zhuzhinfo.getZhuzh().trim())){
					return super.showMessageJSP(actionMapping, request,
							"fastcreateaccount", "已共用其他账户印鉴的账户不允许被共用");
				}
				//复用主账户印鉴信息
				zhanghb.setYouwyj(zhuzhinfo.getYouwyj());
				zhanghb.setYouwzh(zhuzhinfo.getYouwzh());
				zhanghb.setYinjshzt(zhuzhinfo.getYinjshzt());
				zhanghb.setZuhshzt(zhuzhinfo.getZuhshzt());
				zhanghb.setYinjkbh("");
				request.setAttribute("zhanghb", zhanghb);
				request.setAttribute("kaih", "gongy");
				
			}else{
				Zhanghb zhanghb_=ZhanghbService.getZhanghb(zhanghb.getZhangh());//系统中账户信息
				if(zhanghb_==null||zhanghb_.getHum()==null||"".equals(zhanghb_.getHum())){
					//原系统未开户
					List<String> yinjkbhList = getYinjkhList(accountinfoform);
					String yinjkbhStr = createYinjkbhStr(yinjkbhList);
					request.setAttribute("zhanghb", zhanghb);
					// 检查开户账户是否共用印鉴卡
					boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
					if (exist) {
						return super.showMessageJSP(actionMapping, request,
								"fastcreateaccount", "印鉴卡已被使用");
					}
					zhanghb.setYinjkbh(yinjkbhStr);
				}else{
					//原系统已开户
					String zhuzhangh=zhanghb_.getZhuzh();
					
					if(zhuzhangh==null||"".equals(zhuzhangh)){
						//原系统中账户非共用账户
						List<String> yinjkbhList = getYinjkhList(accountinfoform);
						String yinjkbhStr = createYinjkbhStr(yinjkbhList);
						request.setAttribute("zhanghb", zhanghb);
						// 检查开户账户是否共用印鉴卡
						String yyinjk=zhanghb_.getYinjkbh()==null?"":zhanghb_.getYinjkbh();//原印鉴卡号
						if(!yyinjk.equals(yinjkbhStr)){
							boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
							if (exist) {
								return super.showMessageJSP(actionMapping, request,
										"fastcreateaccount", "印鉴卡已被使用");
							}
						}
						zhanghb.setYinjkbh(yinjkbhStr);
						//复用之前的印鉴信息
						zhanghb.setYouwyj(zhanghb_.getYouwyj());
						zhanghb.setYouwzh(zhanghb_.getYouwzh());
						zhanghb.setYinjshzt(zhanghb_.getYinjshzt());
						zhanghb.setZuhshzt(zhanghb_.getZuhshzt());
						
					}else{
						//原系统中账户为共用账户
						List<String> yinjkbhList = getYinjkhList(accountinfoform);
						String yinjkbhStr = createYinjkbhStr(yinjkbhList);
						request.setAttribute("zhanghb", zhanghb);
						// 检查开户账户是否共用印鉴卡
						boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
						if (exist) {
							return super.showMessageJSP(actionMapping, request,
									"fastcreateaccount", "印鉴卡已被使用");
						}
						zhanghb.setYinjkbh(yinjkbhStr);
					}
				}
				request.setAttribute("kaih", "kaih");
			}
			ZhanghbService.tongbAccountinfoFromTemp(zhanghb);
			String content="柜员["+clerk.getCode()+"]同步,账户["+zhanghb.getZhangh()+"]信息 :("+createAccountInfo(zhanghb)+")";
			// 记录快速开户日志
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "0", content,
							clerk);
			return super.showMessageJSP(actionMapping, request,
					"fastcreateaccount", "同步账号["+zhanghb.getZhangh()+"]到验印系统成功!");
		}catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
			"fastcreateaccount");
		}
	}
	
	
	private String createAccountInfo(Zhanghb zhanghb) {
		StringBuffer sb =new StringBuffer();
		sb.append("账号：").append(zhanghb.getZhangh()==null?"":zhanghb.getZhangh());
		sb.append(";户名：").append(zhanghb.getHum()==null?"":zhanghb.getHum());
		sb.append(";账户性质：").append(zhanghb.getZhanghxz()==null?"":zhanghb.getZhanghxz());
		sb.append(";账户状态：").append(zhanghb.getZhanghzt()==null?"":zhanghb.getZhanghzt());
		sb.append(";通兑标志：").append(zhanghb.getTongctd()==null?"":zhanghb.getTongctd());
		sb.append(";开户日期：").append(zhanghb.getKaihrq()==null?"":zhanghb.getKaihrq());
		sb.append(";共用账号：").append(zhanghb.getZhuzh()==null?"":zhanghb.getZhuzh());
		sb.append(";共用日期：").append(zhanghb.getFuyrq()==null?"":zhanghb.getFuyrq());
		sb.append(";印鉴卡号：").append(zhanghb.getYinjkbh()==null?"":zhanghb.getYinjkbh());
		sb.append(";是否电核：").append("0".equals(zhanghb.getShifdh())?"是":"否");
		sb.append(";备注：").append(zhanghb.getBeiz()==null?"":zhanghb.getBeiz());
		sb.append(";联系人：").append(zhanghb.getLianxr()==null?"":zhanghb.getLianxr());
		sb.append(";联系人区号：").append(zhanghb.getLianxrqh()==null?"":zhanghb.getLianxrqh());
		sb.append(";联系人电话：").append(zhanghb.getDianh()==null?"":zhanghb.getDianh());
		sb.append(";联系人分机号：").append(zhanghb.getLianxrfjh()==null?"":zhanghb.getLianxrfjh());
		sb.append(";联系人2：").append(zhanghb.getLianxr2()==null?"":zhanghb.getLianxr2());
		sb.append(";联系人2区号：").append(zhanghb.getLianxrqh2()==null?"":zhanghb.getLianxrqh2());
		sb.append(";联系人2电话：").append(zhanghb.getDianh2()==null?"":zhanghb.getDianh2());
		sb.append(";联系人2分机号：").append(zhanghb.getLianxrfjh2()==null?"":zhanghb.getLianxrfjh2());
		sb.append(";负责人：").append(zhanghb.getFuzr()==null?"":zhanghb.getFuzr());
		sb.append(";负责人区号：").append(zhanghb.getFuzrqh()==null?"":zhanghb.getFuzrqh());
		sb.append(";负责人电话：").append(zhanghb.getFuzrdh()==null?"":zhanghb.getFuzrdh());
		sb.append(";负责人分机号：").append(zhanghb.getFuzrfjh()==null?"":zhanghb.getFuzrfjh());
		sb.append(";负责人2：").append(zhanghb.getFuzr2()==null?"":zhanghb.getFuzr2());
		sb.append(";负责人2区号：").append(zhanghb.getFuzrqh2()==null?"":zhanghb.getFuzrqh2());
		sb.append(";负责人2电话：").append(zhanghb.getFuzrdh2()==null?"":zhanghb.getFuzrdh2());
		sb.append(";负责人2分机号：").append(zhanghb.getFuzrfjh2()==null?"":zhanghb.getFuzrfjh2());
		sb.append(";是否比对小码：").append(zhanghb.getIschecksmall()==null||"0".equals(zhanghb.getIschecksmall())?"否":"是");
		return sb.toString();
	}

	/*
	 * 新版简单帐号查询
	 */
	public ActionForward net_select(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String zhangh = request.getParameter("zhangh");
		// 新旧账号转换
		if (zhangh != null && zhangh.length() != 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.view.success", "没有权限查看账号:["
								+ zhanghb.getZhangh() + "]");
			}
			if (zhanghb == null) {
				return this.showMessageJSP(actionMapping, request,
						"accountinfo.net.view.success", "没有找到账号!");
			}
			request.setAttribute("Zhanghb", zhanghb);
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.view.success");
		} finally {
			// 将柜员信息格式化>json
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
		// 新旧账号转换
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
	 * 账号明细查询 .net
	 */
	public ActionForward scanAccountinfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			String zhangh = request.getParameter("zhangh");
			// 新旧账号转换
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
			// 将柜员信息格式化>json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);
		}
	}

	// 销户恢复 跳转
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

	// 销户恢复信息回显
	public ActionForward getAccountInfoForResume(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String account = request.getParameter("account");
		Clerk clerk=(Clerk)(request.getSession().getAttribute("clerk"));
		// 新旧账号转换
		if (account != null && account.length() != 17) {
			account = ZhanghbService.parseTypeN(account, 17);
		}
		String accountname = "";
		String allexchange = "";
		String accountstate = "";
		String isZhuzh = "0";// 0:不是主账户；1：主账户
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
				List zlist = ZhanghbService.getzzhlist(account);// 子账户列表
				// 判断账户是否存在主账户
				if (zlist != null && zlist.size() >= 1)
					isZhuzh = "1";
			
				yzhanghxz=ZhanghbService.queryXiaohqzt(account);
			
				
				if (accountinfo.getZhanghzt() != null
						&& accountinfo.getZhanghzt().equals("销户")) {
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
	 * 销户恢复
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
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "没有权限销户恢复，账号:["
								+ accountinfoform.getAccount() + "]");
			}
			String zhuzh=zhanghb.getZhuzh();
			if(zhuzh!=null&&zhuzh.trim().length()!=0){
				Zhanghb zzh=ZhanghbService.getZhanghb(zhuzh);
				if(zzh!=null&&zzh.getZhanghzt().equals("销户")){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.resume.success", "主账户["+zhuzh+"]已销户，请先恢复主账户");
				}
			}
			// 验证账户状态
			if ("销户".equals(zhanghb.getZhanghzt())) {
				String yzhanghzt=ZhanghbService.queryXiaohqzt(zhanghb.getZhangh());
				if(yzhanghzt.trim().equals("账销案存")){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.resume.success", "账户["+zhanghb.getZhangh()+"]已账销案存，无法进行销户恢复!");
				}
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("有效");
				zhanghbxzList.add("久悬");
				zhanghbxzList.add("待销");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk,zhanghbxzList,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.resume.success", "核心已销户，无法对账号:["
									+ accountinfoform.getAccount() + "]进行销户恢复!");
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
					//boolean pass = iCheckYinjkhXiaoK(zhangh, yinjkhList,"02", clerk);  //调用核心交易查询
					//boolean	pass=true;
				//	if(!pass){
					//	return super.showMessageJSP(actionMapping, request,
						//		"accountinfo.net.resume.success", "无法进行销户恢复，核心印鉴卡状态不合法!");
					//}
					ZhanghbService.recoverAccount(zhanghb.getZhangh(),true,yzhanghzt);
				}else{
					ZhanghbService.recoverAccount(zhanghb.getZhangh(),false,yzhanghzt);
				}

				String content = "账号[" + zhanghb.getZhangh() + "]由销户转为"+yzhanghzt;
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "5", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "销户恢复成功!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.resume.success", "账号：["
								+ accountinfoform.getAccount()
								+ "]不可进行销户恢复,当前状态为：[" + zhanghb.getZhanghzt()
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.resume.success");
		}
	}

	// 销户 跳转
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
	 * 销户
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
			// 新旧账号转换
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "没有权限销户，账号:["
								+ accountinfoform.getAccount() + "]!");
			}
			/*List<Zhanghb> zizhlist=ZhanghbService.getZhangbListByZzh(zhangh);
			if(zizhlist.size()>0){
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "账号：[" + zhanghb.getZhangh()
								+ "]存在未销户的子账户，不可进行销户");
			}*/
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// 验证账户状态
			if ("有效".equals(zhanghzt)||"久悬".equals(zhanghzt)||"账销案存".equals(zhanghzt)) {
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("销户");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk, zhanghbxzList,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.xiaohu", "与核心状态不符，账号["
									+ accountinfoform.getAccount() + "]无法销户!");
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
				String content = "账号[" + zhanghb.getZhangh() + "]由"+zhanghzt+"转为销户";
				//判断是否需要进行销卡操作
				if(ZhanghbService.canCancleYinjk(zhangh)){
				/*boolean pass = iCheckYinjkhXiaoK(zhangh, yinjkhList,"04", clerk);  //调用核心交易查询
					if(pass){
						ZhanghbService.closeAccount(zhanghb.getZhangh(), yinjkhList);
						}else{
						//20140324 --qjk  修改 核心卡未消 验印系统不销户
						return super.showMessageJSP(actionMapping, request,
									"accountinfo.xiaohu", "账号：[" + zhanghb.getZhangh()
											+ "]不可进行销户,核心未进行销卡操作!");
						}*/
					content+="并作废该印鉴卡"+yinjkbh;
					//非共用 且未被共用账户销户销卡
					ZhanghbService.closeAccount(zhanghb.getZhangh(), yinjkhList);
				}else{
					//被共用账户销户 并将印鉴转移给其子账户
					List<Zhanghb> zizhlist=ZhanghbService.getZhangbListByZzh(zhangh);
					if(zizhlist!=null&&zizhlist.size()>0){
						boolean pass=ZhanghbService.cancleZhuzh(zhanghb.getZhangh(),gszh);
						content+="并将其印鉴归属给账号["+gszh+"]";
						if(!pass){
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.xiaohu", "印鉴改变归属给账号["+gszh+"]失败，请重新操作!");
						}
					}else{
					//共用账户销户
					zhanghb.setZhanghzt("销户");
					zhanghb.setTingyrq(getSystemMgrService().getSystetemNowDate().substring(0, 10));
					ZhanghbService.update(zhanghb);
					}
				}
				
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "4", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "销户成功!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.xiaohu", "账号：[" + zhanghb.getZhangh()
								+ "]不可进行销户,当前状态为：[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.xiaohu");
		}
	}


	// 转久悬 跳转
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
	 * 账户转久悬
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
			// 新旧账号转换
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "没有权限操作账号:["
								+ accountinfoform.getAccount() + "]!");
			}
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// 验证账户状态
			if ("有效".equals(zhanghzt)) {
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("久悬");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk, zhanghbxzList,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.jiuxuan", "与核心状态不符，无法修改账号:["
									+ accountinfoform.getAccount() + "]状态为久悬!");
				}
				zhanghb.setZhanghzt("久悬");
				ZhanghbService.update(zhanghb);
				String content = "账号[" + zhanghb.getZhangh() + "]由"+zhanghzt+"转为久悬";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "9", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "修改账户["+zhanghb.getZhangh()+"]状态为久悬成功!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.jiuxuan", "账号：[" + zhanghb.getZhangh()
								+ "]不可进行修改,当前状态为：[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.jiuxuan");
		}
	}
	// 账销案存 跳转
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
	 * 账销案存
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
			// 新旧账号转换
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "没有权限操作账号:["
								+ accountinfoform.getAccount() + "]!");
			}
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// 验证账户状态
			if ("久悬".equals(zhanghzt)) {
				zhanghb.setZhanghzt("账销案存");
				ZhanghbService.update(zhanghb);
				String content = "账号[" + zhanghb.getZhangh() + "]由久悬转为账销案存";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "9", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "修改账户["+zhanghb.getZhangh()+"]状态为账销案存成功!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.zhangxac", "账号：[" + zhanghb.getZhangh()
								+ "]不可进行修改,当前状态为：[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.zhangxac");
		}
	}


	// 久悬账户转正常 跳转
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
	 * 账户转正常
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
			// 新旧账号转换
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "没有权限操作账号:["
								+ accountinfoform.getAccount() + "]!");
			}
			String zhanghzt=zhanghb.getZhanghzt().trim();
			// 验证账户状态
			if ("久悬".equals(zhanghzt)) {
				List<String> zhanghbxzList=new ArrayList<String>();
				zhanghbxzList.add("有效");
				if(!validataBLZhanghzt(zhangh, clerk.getOrgcode(), clerk,zhanghbxzList ,zhanghb.getZhanghxz())){
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.normal", "与核心状态不符，无法修改账号:["
									+ accountinfoform.getAccount() + "]状态为有效!");
				}
				zhanghb.setZhanghzt("有效");
				ZhanghbService.update(zhanghb);
				String content = "账号[" + zhanghb.getZhangh() + "]由"+zhanghzt+"转为有效";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "9", content,
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "修改账户["+zhanghb.getZhangh()+"]状态为有效成功!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.normal", "账号：[" + zhanghb.getZhangh()
								+ "]不可进行修改,当前状态为：[" + zhanghzt
								+ "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.normal");
		}
	}

	
	
	// 账号解挂 跳转
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

	// 账号解挂
	public ActionForward jiegAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjg.success", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjg.success", "没有权限解挂账号:["
								+ accountinfoform.getAccount() + "]");
			}
			
			// 验证账户状态
			if ("挂失".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.recoverAccount(zhanghb.getZhangh(),true);
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjg.success", "账号：["
								+ zhanghb.getZhangh() + "]不可进行解挂,当前状态为：["
								+ zhanghb.getZhanghzt() + "]");
			}
			String content = "解挂成功(账号为" + zhanghb.getZhangh() + ";柜员代码:"
					+ clerk.getCode() + ")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "账户解挂", "账户解挂",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghjg.success", "账号解挂成功!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghjg.success");
		}
	}

	// 账号解冻 跳转
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

	// 账号解冻
	public ActionForward jiedAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjd.success", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjd.success", "没有权限销户账号:["
								+ accountinfoform.getAccount() + "]");
			}
			// 验证账户状态
			if ("冻结".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.recoverAccount(zhanghb.getZhangh(),true);
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghjd.success", "账号：["
								+ zhanghb.getZhangh() + "]不可进行解冻,当前状态为：["
								+ zhanghb.getZhanghzt() + "]");
			}
			String content = "解冻成功(账号为" + zhanghb.getZhangh() + ";柜员代码:"
					+ clerk.getCode() + ")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "账户解冻", "账户解冻",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghjd.success", "账号解冻成功!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghjd.success");
		}
	}

	// 物理删除 跳转
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

	// 新版物理删除
	public ActionForward physicsDeleteForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.physicsdelete.success", "账户不存在!");
			}
			// 验证账户是否为主账户，主账户不能进行物理删除 by wp
			List list = ZhanghbService.getzzhlist(accountinfoform.getAccount());
			if (list.size() > 0) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.physicsdelete.success",
						"当前账户为主账户，不能执行物理删除操作!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.physicsdelete.success", "没有权限删除账号:["
								+ accountinfoform.getAccount() + "]");
			}
			ZhanghbService.physicsdelete(zhanghb.getZhangh());
			String content = "账户物理删除(账号为" + zhanghb.getZhangh() + ";柜员号:"
					+ clerk.getCode() + ")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhanghb.getZhangh(), "账户删除", "账户删除",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.physicsdelete.success", "账户物理删除操作成功!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.physicsdelete.success");
		}
	}

	/**
	 * 
	 * <dl>
	 * <dt><b>getAccountForNet:从.net版账户表中获取账户数据</b></dt>
	 * <dd></dd>
	 * </dl>
	 */
	public ActionForward getAccountForNet(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String account = request.getParameter("account");
		Clerk clerk =(Clerk)(request.getSession().getAttribute("clerk"));
		// 新旧账号转换
		if (account != null && account.length() != 17) {
			account = ZhanghbService.parseTypeN(account, 17);
		}
		String accountname = "";
		String allexchange = "";
		String tingyrq = "";
		String accountstate = "";
		String isZhuzh = "0";// 0:不是猪账户；1：主账户
		String yinjshzt = "";
		String youwzh = "";
		String accorgno = "";
		String pass="1";
		String zzhstr="";//子账户字符串
		
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
				List<Zhanghb> zlist = ZhanghbService.getZhangbListByZzh(account);// 子账户列表
				
				// 判断账户是否存在主账户
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
	 * 下载票据影像
	 */
	public ActionForward getBillImage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		// 新旧账号转换
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
	 * 下载账户印鉴卡影像
	 */
	/*public ActionForward getYinjImage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		String qiyrq=request.getParameter("qiyrq");
		String yinjbh=request.getParameter("yinjbh");
		response.setContentType("image/JPEG");
		// 新旧账号转换
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
	 * 下载印鉴信息
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
		
		// 新旧账号转换
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
	 * 下载账户印鉴卡影像
	 */
	public ActionForward downloadYinjkImage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		// 新旧账号转换
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
	 * 获取账号印鉴信息
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
						"accountinfo.yinj.image", "没有找到此账号的预留印鉴!");
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
	 * 获取账号印鉴卡信息
	 */
	public ActionForward getAcccountYjkImageList(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String account = request.getParameter("account");
		try {
			List list = AccountImageServcie.getZhanghYjkList(account);
			if (list == null || list.size() == 0) {
				return this.showMessageJSP(actionMapping, request,
						"accountinfo.image", "没有找到此账号的预留印鉴卡!");
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
	 * 获取账号印鉴卡信息
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
						"accountinfo.image", "没有找到此账号的预留印鉴卡!");
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
	 * 获取票据信息
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
						"accountinfo.image", "没有找到此票据!");
			}
			request.setAttribute("accountList", list);
			return actionMapping.findForward("accountinfo.bill");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.bill");
		}
	}

	// 账户冻结跳转页面
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

	// 冻结
	public ActionForward zhanghdj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "没有权限冻结，账号:["
								+ accountinfoform.getAccount() + "]!");
			}
			// 验证账户状态
			if ("有效".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.dongjAccount(zhanghb.getZhangh());
				String content = "冻结(账号为" + zhanghb.getZhangh() + ";柜员代码:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "冻结", "冻结",
						clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "账户冻结成功!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghdj.success", "账号：["
								+ zhanghb.getZhangh() + "]不可进行账户冻结,当前状态为：["
								+ zhanghb.getZhanghzt() + "]!");
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghdj.success");
		}
	}

	// 账户挂失跳转页面
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

	// 挂失
	public ActionForward zhanghgs(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(accountinfoform
					.getAccount());
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "账号：["
								+ accountinfoform.getAccount() + "]不存在!");
			}
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "没有权限挂失，账号:["
								+ accountinfoform.getAccount() + "]!");
			}
			// 验证账户状态
			if ("有效".equals(zhanghb.getZhanghzt())) {
				ZhanghbService.guasAccount(zhanghb.getZhangh());
				String content = "挂失账户(账号为" + zhanghb.getZhangh() + ";柜员代码:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "挂失账户",
						"挂失账户", clerk);
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "挂失成功!");
			} else {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghgs.success", "账号：["
								+ zhanghb.getZhangh() + "]不可进行账户挂失,当前状态为：["
								+ zhanghb.getZhanghzt() + "]!");
			}

		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghgs.success");
		}
	}

	/**
	 * 账户开户跳转页面
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward zhanghkhView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//将系统时间返回前台页面
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		HttpSession session = request.getSession();
		AccountinfoForm accountinfoForm=(AccountinfoForm)actionForm;
		accountinfoForm.reset(actionMapping, request);
		//查询账户性质
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		
		request.setAttribute("zhanghxzlist", zhanghxzList);
		Zhanghb zhanghb = (Zhanghb) session.getAttribute("zhanghb_zt");
		String logintype = (String) session.getAttribute("logintype");
		Clerk clerk=(Clerk)session.getAttribute("clerk");
		String org_for_tongd=clerk.getOrgcode();;
		session.removeAttribute("zhanghb_zt");
		session.removeAttribute("logintype");
		//测试开户接口
		/*zhanghb=new Zhanghb();
		zhanghb.setZhangh("20140521111111111");
		zhanghb.setHum("徐超2");
		zhanghb.setZhanghxz("基本户");
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
							"accountinfo.net.zhanghkh.success", "复用核心开户信息失败!");
				}
				if (zhanghb.getZhangh() == null || zhanghb.getZhangh().equals("")) {
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.zhanghkh.success", "复用核心开户信息失败!");
				}
				accountinfoForm.setAccount(zhanghb.getZhangh());
				//检查从核心拿到的账户性质在验印系统是否存在 若不存在 将其设置为"基本户"
				String zhanghxz = zhanghb.getZhanghxz()==null||zhanghb.getZhanghxz().trim().equals("")?"基本户":zhanghb.getZhanghxz();
				int count=0;
				for (Zhanghxzb zhanghxzb : zhanghxzList) {
					if(zhanghxzb!=null&&zhanghxzb.getZhanghxz().equals(zhanghxz)){
						count=1;
						break;
					}
				}
				if(count==0){
					zhanghb.setZhanghxz("基本户");
				}
				
				String kaihrq=zhanghb.getKaihrq();
				if(kaihrq!=null&&kaihrq.indexOf("-")==-1){
					zhanghb.setKaihrq(DateTool.toSimpleFormat(kaihrq));
				}
				
				if (logintype.equals("ztzilxg")) {
					try {
						String zhangh = zhanghb.getZhangh();
						// 新旧账号转换
						if (zhangh != null && zhangh.length() != 17) {
							zhangh = ZhanghbService.parseTypeN(zhangh, 17);
						}
						Zhanghb zhanghb_info = ZhanghbService.getZhanghb(zhangh);
						if(zhanghb_info==null){
							updateAccountinfoForm(zhanghb, (AccountinfoForm) actionForm);
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "该账户尚未在验印系统开户!");
						}
						org_for_tongd=zhanghb_info.getJigh();
						zhanghb_info.setZhanghxz(zhanghb.getZhanghxz());
						zhanghb_info.setHum(zhanghb.getHum());
						request.setAttribute("zhanghb", zhanghb_info);
						request.setAttribute("logintype", logintype);
						//将数据回显到页面
						updateAccountinfoForm(zhanghb_info, (AccountinfoForm) actionForm);
					
						return actionMapping
								.findForward("accountinfo.net.zhanghkh.success");
					} catch (BusinessException e) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.zhanghkh.success", "复用核心开户信息失败!");
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
								"accountinfo.net.zhanghkh.success", "账号：["
										+ zhanghb_info.getZhangh()
										+ "]已存在!");
					}
					//updateAccountinfoForm( zhanghb, (AccountinfoForm)actionForm) ;
					//String zhangh, String yinjkhStr,String yinjkzt, Clerk clerk
					//根据账号去核心查询该账号存在的已用印鉴卡
					/*{
						List<Yinjk> yinjkList=null;
						try {
							//印鉴卡验证模式
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
									"accountinfo.net.zhanghkh.success", "未查询到该账户的印鉴卡");
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
			//当该机构的上级机构和省行机构相同时 不存在二级分行
			if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
				tongdFlag="1";
			}
			request.setAttribute("tongdFlag", tongdFlag);
		
		}
	}

	// 封装数据到form中 回显到jsp页面
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
		
		//区号不为空 且长度不为0  并且首位不为0 的 要补0显示
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
	 * 核心账户开户
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
		//将账户性质列表返回前台页面
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		Org org_forTongd=OrgService.getOrgByCode(clerk.getOrgcode());
		String tongdFlag="0";
		//当该机构的上级机构和省行机构相同时 不存在二级分行
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
		// 验证账户是否已存在
		if (zhanghb != null) {
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghkh.success", "账号：["
							+ accountinfoform.getAccount() + "]已存在!");
		}
		zhanghb = getNewZhanghb(accountinfoform);
		zhanghb.setJigh(clerk.getOrgcode());
		List<String> yinjkbhList = getYinjkhList(accountinfoform);
		String yinjkbhStr = createYinjkbhStr(yinjkbhList);
		String gongy = accountinfoform.getGongy();
		request.setAttribute("zhanghb", zhanghb);
		try{
		// 检查开户账户是否共用印鉴卡
		if (gongy.equals("1")) {
			Zhanghb gongyzh = ZhanghbService.getZhanghbByYinjkbh(yinjkbhStr);
			if (gongyzh == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "共用账户不存在");
			}
			if (!gongyzh.getJigh().equals(zhanghb.getJigh())) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "只能在同一网点内共用印鉴");
			}
			if (!gongyzh.getHum().equals(zhanghb.getHum())) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "只有户名一致时才可共用");
			}
			if (gongyzh.getZhanghxz().equals("内部户")||gongyzh.getZhanghxz().equals("代发工资户")) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "共用账户["+gongyzh.getZhangh()+"]为"+gongyzh.getZhanghxz()+"，无法共用");
			}
			if (gongyzh.getYinjshzt().equals("未审")) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "共用账户["+gongyzh.getZhangh()+"]存在未审印鉴，无法共用");
			}
			zhanghb.setFuyrq(zhanghb.getKaihrq());//新增复用日期
			zhanghb.setZhuzh(gongyzh.getZhangh());
			zhanghb.setYouwyj(gongyzh.getYouwyj());
			zhanghb.setYouwzh(gongyzh.getYouwzh());
			zhanghb.setZuhshzt(gongyzh.getZuhshzt());
			zhanghb.setYinjshzt(gongyzh.getYinjshzt());
			ZhanghbService.createZhanghb(zhanghb, new ArrayList<String>());
			String yinjkbh = gongyzh.getYinjkbh();
			zhanghb.setYinjkbh(yinjkbh);
			request.setAttribute("logintype", "ztzilxg");
			this.createAccountManageLog(zhanghb.getZhangh(), "0", "开户并且印鉴共用",
					clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghkh.success", "共用账号["
							+ gongyzh.getZhangh() + "]成功!");
		} else {
			// 从印鉴卡表中判断印鉴卡编号是否被使用
			boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
			if (exist) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "印鉴卡已被使用");
			}
			zhanghb.setYinjkbh(yinjkbhStr);
			// 开户 分别对zhangh表和yinjk表进行操作
			ZhanghbService.createZhanghb(zhanghb, yinjkbhList);
			request.setAttribute("logintype", "ztzilxg");
			request.setAttribute("kaih", "kaih");
			// 记录开户日志
			this
					.createAccountManageLog(zhanghb.getZhangh(), "0", "账户开户",
							clerk);
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.zhanghkh.success", "开户成功!");
		}
		}catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghkh.success");
		}
	}
	/**
	 * 非核心账户开户（代发工资户、内部户）
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
		//当该机构的上级机构和省行机构相同时 不存在二级分行
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
		// 验证账户是否已存在
		if (zhanghb != null) {
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.inner.zhanghkh.success", "账号：["
							+ accountinfoform.getAccount() + "]已存在!");
		}
		zhanghb = getNewZhanghb(accountinfoform);
		zhanghb.setJigh(clerk.getOrgcode());
		List<String> yinjkbhList = getYinjkhList(accountinfoform);
		String yinjkbhStr = createYinjkbhStr(yinjkbhList);
		request.setAttribute("zhanghb", zhanghb);
		// 从印鉴卡表中判断印鉴卡编号是否被使用
		boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
		if (exist) {
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.inner.zhanghkh.success", "印鉴卡已被使用");
		}
		zhanghb.setYinjkbh(yinjkbhStr);
		// 开户 分别对zhangh表和yinjk表进行操作
		try{
		ZhanghbService.createZhanghb(zhanghb, yinjkbhList);
		request.setAttribute("logintype", "zilxg");
		request.setAttribute("kaih", "kaih");
		// 记录开户日志
		this
				.createAccountManageLog(zhanghb.getZhangh(), "0", "账户开户",
						clerk);
		return super.showMessageJSP(actionMapping, request,
				"accountinfo.net.inner.zhanghkh.success", "开户成功!");
		}catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.inner.zhanghkh.success");
		}
	}
	/**
	 * 解除共用关系
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
			// 新旧账号转换
			if (zhangh != null && zhangh.length() != 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
			Zhanghb zhanghb = null;
			zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证账户是否已存在
			if (zhanghb == null) {
				out.print("fail001");// 账户不存在
				return null;
			}
			Zhanghb oldZhanghb = copyZhanghb(zhanghb);
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				out.print("fail002");// 无权限
				return null;
			}
			Zhanghb zhanghb_gy = ZhanghbService.getZhanghb(zhanghb.getZhuzh());
			String yinjkbh = zhanghb_gy.getYinjkbh();
			
			//判断印鉴卡验证模式 是否通过核心交易验证
			/*String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
			if(ip.equals("1")){
				//调用核心交易查询
				boolean pass = iCheckYinjkh(zhangh, yinjkbh,"02", clerk);	
				if(pass){
					//账户印鉴卡未变更，无法进行解除印鉴卡操作
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
			zhanghb.setYouwyj("无");
			zhanghb.setYinjshzt("未审");
			zhanghb.setYouwzh("无");
			zhanghb.setZuhshzt("未审");
			
			ZhanghbService.update(zhanghb);
			//ZhanghbService.cancleYinjk(zhanghb_gy.getZhangh(), yinjkhList);
			String content = "柜员[" + clerk.getCode() + "]解除账户[" + zhangh
					+ "]的共用关系";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zhangh, "3", content, clerk);
			out.print("0");
			return null;
		} catch (Exception e) {
			out.print("fail003");// 解除共用失败
			return null;
		} finally {
			out.close();
		}

	}
/**
 * 印鉴卡状态检验
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
			// 新旧账号转换
			if (zhangh != null && zhangh.length()!= 17) {
				zhangh = ZhanghbService.parseTypeN(zhangh, 17);
			}
		List<String> yinjkbhList = createYinjkhList(yinjkhStr);
		Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
		if (zhanghb != null) {
			List<String> oldYinjkbhList = new ArrayList<String>();// 修改之后不使用的印鉴卡
			List<String> commen = new ArrayList<String>();// 修改前后不变的印鉴卡
			// 将修改之前与之后改动过的印鉴卡编号区分出来
			splitYinjkList(zhanghb.getYinjkbh(), yinjkbhList,
					oldYinjkbhList, commen);

		}
			out = response.getWriter();
			String type =SystemConfig.getInstance().getValue("yinjk_check_model");
			String yinjkFlag=yinjkhStr!=null&&yinjkhStr.trim().length()==20?"1":"0"; //根据印鉴卡长度 判断是否为存量账户  存量账户印鉴卡长度为17或者22位
			if(type.equals("0")||zhanghxz.equals("0")||yinjkFlag.equals("0")){
				boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
				if (exist) {
					if(zhanghxz!=null&&!zhanghxz.equals("0")){
						out.print("1");//共用
					}else{
						//if(yinjkFlag.equals("0")){
						//	out.print("1");//存量账户共用
						//}else{
							out.print("fail002");
						//}//印鉴卡已被使用，代发工资户何内部户不允许共用
					}
					return null;
				}else{
					out.print("0");//通过
					return null;
				}
			}else{
				//判断印鉴卡验证模式 是否通过核心交易验证
				List<String> yinjks= createYinjkhList(yinjkhStr);
				int pass = iCheckKaih(zhangh, yinjks,"02", clerk);	//调用核心交易查询
		
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
					// 若果印鉴卡已被使用 则检查是否已存在使用此印鉴卡的账户
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
	 * 调用核心接口 查询核心账户信息
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
	 * 校验核心账户状态
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
		if(type.equals("0")||zhanghxz.equals("内部户")||zhanghxz.equals("代发工资户")){
			return true;
		}
		Zhanghb zhanghb=getBlZhangh(zhangh, orgNum, clerk);
		if(zhanghb==null){
			return false;
		}
		return zhanghxzList.contains(zhanghb.getZhanghzt());
	}
	/**
	 * 查询核心账户信息
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
				// 新旧账号转换
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
	 * 校验印鉴卡是否已销卡
	 * @param zhangh
	 * @param xkList
	 * @param yinjkzt
	 * @param clerk
	 * @return
	 
	private boolean  iCheckYinjkhXiaoK (String zhangh, List<String> xkList,String yinjkzt, Clerk clerk){
		//判断印鉴卡验证模式 是否通过核心交易验证
		String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
		if(ip.equals("0")){
			return true;
		}
		if(xkList.size()==0){
			return false;
		}
		System.out.println("zhangh :"+zhangh +"  yinjkzt : "+yinjkzt +" clerk : "+ clerk.getCode() +" orgCode :" +clerk.getOrgcode());
		//迭代需要改动的印鉴卡集合
		for (String string : xkList) {
			try {
				if(string==null||string.trim().equals("")){
					continue;
				}
				//输入账号 印鉴卡号 印鉴卡状态 柜员号 去查询印鉴卡信息
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
	 * 新版 印鉴卡查询
	 * 
	 * @param zhangh
	 * @param yinjkhList
	 * @param yinjkzt
	 * @param clerk
	 * @return
	 * @throws BusinessException
	 */
	
	//0 ： 未通过 1：通过  2:共用
	private int iCheckKaih(String zhangh,List<String> yinjkhList,String yinjkzt,Clerk clerk) throws BusinessException{
			StringVO msg=new StringVO();
			//判断印鉴卡验证模式 是否通过核心交易验证
			String ip =SystemConfig.getInstance().getValue("yinjk_check_model");
			if(ip.equals("0")){
				return 1;
			}
		
			for (String yinjkh : yinjkhList) {
				if(yinjkh==null||yinjkh.trim().equals("")){
					continue;
				}
				List<Yinjk> yinjkList = iQueryYinjkh("", yinjkzt,yinjkh, clerk,msg);
				//根据印鉴卡未查询到记录 说明此卡未开卡， 印鉴卡状态不合法
				if(yinjkList==null||yinjkList.size()==0){
					return 0;
				}
				//若存在记录 并且账号和前段输入账号一致  验证通过
				if(zhangh.equals(yinjkList.get(0).getZhangh().trim())){
					return 1;
				}
				//存在记录 但是账号非前端输入账号 进行账号共用查询 查询出该张印鉴卡的共用账号
				List<String> zhanghList=iQueryZhanghList(msg.getValue().getBytes());
				//共用账号集合为空 返回未通过
				if(zhanghList==null||zhanghList.size()==0){
					return 0;
				}
				//遍历共用账号集合 ，检查 前端输入的账号 是否属于该张印鉴卡的共用账号 若是 返回2 代表共用
				for (String zhanghao : zhanghList) {
					if(zhanghao!=null&&zhanghao.trim().equals(zhangh.substring(0, zhangh.length()-1))){
						return 2;
					}
				}
			}
			return 0;
	}
	
	/**
	 * 查询印鉴卡的共用账户列表
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
				if(e.getMessage().equals("往下已无数据可显示")||result.getHead().getReplyCd().equals("HX1957")){
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
	 * 旧版印鉴卡查询
	 * @param zhangh
	 * @param yinjkhStr
	 * @param yinjkzt
	 * @param clerk
	 * @return
	 * @throws BusinessException
	 
	private boolean iCheckYinjkh(String zhangh, String yinjkhStr,String yinjkzt, Clerk clerk)
			throws BusinessException {
		//判断印鉴卡验证模式 是否通过核心交易验证
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
	 * 核心接口 socket
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
				log.info("向前置发送报文："+new String (sendMsg));
				os.write(length.getBytes());
				os.write(sendMsg);
				byte[] b = new byte[6];
				is.read(b, 0, 6);
				int len = Integer.parseInt((new String(b).trim()));
				byte[] buf = new byte[len];
				is.read(buf, 0, len);
				log.info("前置返回报文："+new String (buf));
			return buf;
			}
		} catch (IOException e) {
			throw new BusinessException("与前置通讯异常，连接核心服务失败");
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
	 * 解析29178报文
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
			throw new BusinessException("请求核心查询交易失败");
		}
	}
	/**
	 * 解析29179报文
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
			throw new BusinessException("请求核心查询交易失败");
		}
	}
	
	/**
	 * 解析00400返回报文
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
			throw new BusinessException("请求核心查询交易失败");
		}
	}
	/**
	 * 创建前置报文头
	 * @param zhangh
	 * @param yinjkh
	 * @param yinjkzt
	 * @param orgNum
	 * @param clerk
	 * @return
	 */
	private String createHeadMsg(String orgNum,
			Clerk clerk,String jiaoyNo) {
		String flwNo =SystemConfig.getInstance().getValue("yinjk_check_flwno");//2位渠道号3位前端系统编号
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
		msg.append(lsh +flwNo+ "        ");// 前端系统流水号
		msg.append("                    ");// 原服务前端流水号
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
		msg.append("        ");// 终端编号
		String clerkCode = clerk.getCode();
		clerkCode = clerkCode == null ? "" : clerkCode;
		while (clerkCode.length() < 6) {
			clerkCode += " ";
		}
		if(clerkCode.length()>6){
			clerkCode=clerkCode.substring(0,6);
		}
		msg.append(clerkCode);// 柜员编号
		msg.append("      ");// 授权操作员编号
		msg.append(nowDate.substring(0, 8));// 前段业务日期
		msg.append(nowDate.substring(8, 14));// 时间
		msg.append("            ");// 后台业务流水号
		msg.append("        ");
		msg.append("    ");// 页码
		msg.append(" ");// 结束标记
		msg.append("000");// 工作站号
		msg.append("   ");// 返回信息码
		msg.append("0");// 渠道号
		msg.append("E");// 交易源系统标志
		int j = 1;
		while (j <= 92) {
			msg.append(" ");
			j++;
		}
		return msg.toString();
	}
	/**
	 * 创建29177报文
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
		msg.append(yinjkqzh_);// 前缀号
		msg.append(yinjkxh_);// 印鉴卡序号
		msg.append(yinjkzt);// 印鉴卡状态
		msg.append(zhangh_);// 账号
		while(orgNum.length()<4){
			orgNum="0"+orgNum;
		}
		msg.append("0000");// 机构号
		msg.append("E ");
		return msg.toString();
	}
	/**
	 * 创建29177报文
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
		msg.append(zhangh);// 账号
		msg.append("1");// 
		msg.append("0");// 
		msg.append("0");//
		
		msg.append("00000000");
		msg.append("00000000");
		return msg.toString();
	}
	
	
	/**
	 * 非核心账户开户跳转页面
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward innerZhanghkhView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		//将系统时间返回前台页面
		request.setAttribute("date", this.getSystemMgrService().getSystetemNowDate().substring(0, 10));
		HttpSession session = request.getSession();
		AccountinfoForm accountinfoForm=(AccountinfoForm)actionForm;
		accountinfoForm.reset(actionMapping, request);
		Clerk clerk=(Clerk)session.getAttribute("clerk");
		String org_for_tongd=clerk.getOrgcode();;
		Org org_forTongd=OrgService.getOrgByCode(org_for_tongd);
		String tongdFlag="0";
		//当该机构的上级机构和省行机构相同时 不存在二级分行
		if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
			tongdFlag="1";
		}
		request.setAttribute("tongdFlag", tongdFlag);
		request.setAttribute("logintype", "kaih");
		return actionMapping.findForward("accountinfo.net.inner.zhanghkh.success");
		
	}

	// 从form中获取印鉴卡号 封装成集合
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

	// 创建开户账户
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
		zhanghb.setYouwyj("无");
		zhanghb.setYouwzh("无");
		zhanghb.setZuhshzt("未审");
		zhanghb.setZhanghshzt("已审");
		zhanghb.setYinjshzt("未审");
		//zhanghb.setZhanghzt("有效");
		zhanghb.setZhanghzt(accountinfoform.getZhanghzt()==null||"".equals(accountinfoform.getZhanghzt())?"有效":accountinfoform.getZhanghzt());
		zhanghb.setZhuzh(accountinfoform.getZhuzh());
		zhanghb.setFuyrq(accountinfoform.getFuyrq());
		zhanghb.setLianxrfjh(accountinfoform.getLianxrfjh());
		zhanghb.setLianxrfjh2(accountinfoform.getLianxrfjh2());
		zhanghb.setFuzrfjh(accountinfoform.getFuzrfjh());
		zhanghb.setFuzrfjh2(accountinfoform.getFuzrfjh2());
		zhanghb.setIschecksmall(accountinfoform.getIschecksmall());
		//区号不为空 且长度不为0 要去掉首位0存入数据库
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
	 * 核心账户资料修改
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
		//将账户性质列表返回前台页面
		ArrayList<Zhanghxzb> zhanghxzList = ZhanghxzConfig.getList();
		request.setAttribute("zhanghxzlist", zhanghxzList);
		String tongdFlag="0";
		request.setAttribute("tongdFlag", tongdFlag);
		
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String zhangh = accountinfoform.getAccount();
		// 新旧账号转换
		if (zhangh != null && zhangh.length()!= 17) {
			zhangh = ZhanghbService.parseTypeN(zhangh, 17);
		}
		
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "账号：[" + zhangh
								+ "]不存在!");
			}
			zhanghb.setYinjkbh(ZhanghbService.getYinjkhByZhangh(zhanghb.getZhangh()));
			Zhanghb oldZhanghb = copyZhanghb(zhanghb);
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "您没有权限修改，账号:["
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
			//区号不为空 且长度不为0 要去掉首位0存入数据库
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

				List<String> oldYinjkbhList = new ArrayList<String>();// 修改之后不使用的印鉴卡
				List<String> commen = new ArrayList<String>();// 修改前后不变的印鉴卡
				// 将修改之前与之后改动过的印鉴卡编号区分出来
				splitYinjkList(oldyinjkbh, yinjkbhList, oldYinjkbhList, commen);
				
				//控制通兑标志下拉选项中是否包含二级分行
				Org org_forTongd=OrgService.getOrgByCode(zhanghb.getJigh());
				//当该机构的上级机构和省行机构相同时 不存在二级分行
				if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
					tongdFlag="1";
				}
				request.setAttribute("tongdFlag", tongdFlag);
				
				// 非共用账户
				if ("0".equals(gongy)) {
					// 检查新增的印鉴卡是否已经被使用
					boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
					if (exist) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.zhanghkh.success", "印鉴卡已被使用");
					}
					zhanghb.setYinjkbh(yinjkbhStr);
					ZhanghbService.updateZhanghb(zhanghb, yinjkbhList,
							oldYinjkbhList);
	
					// 记录日志
					String content = "修改账户(账号为" + zhanghb.getZhangh() + ";柜员代码:"
							+ clerk.getCode() + ")";
					this.createManageLog(clerk.getCode(), content);
					this.createAccountManageLog(zhanghb.getZhangh(), "1", clerk,
							oldZhanghb, zhanghb);
					request.setAttribute("logintype", "ztzilxg");

					request.setAttribute("kaih", "kaih");//控制页面是否跳转建库页面
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.zhanghkh.success", "修改成功!");
				} else {
					accountinfoform.setGongy("0");
					accountinfoform.setYinjkbh("");
					Zhanghb zhanghb_=ZhanghbService.getZhanghb(zhanghb.getZhangh());//系统中账户信息
					if(zhanghb_!=null&&zhanghb_.getHum()!=null&&!"".equals(zhanghb_.getHum())){
						List yinjList=ZhanghbService.getYinjb(zhanghb_.getZhangh());
						if(yinjList!=null&&yinjList.size()!=0){
							accountinfoform.setZhuzh("");
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "已建立印鉴账户不允许共用其他账户");
						}
					}
					Zhanghb gongyzh = ZhanghbService.getZhanghbByYinjkbh(yinjkbhStr);
					if (gongyzh == null) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.zhanghkh.success", "共用账户不存在");
					}
					String zhuzh = zhanghb.getZhuzh();
					//当账户印鉴卡发生变动时 执行以下操作 重新共用
					if (zhuzh == null || zhuzh.equals("")||!zhuzh.equals(gongyzh.getZhangh())) {
						if (!gongyzh.getJigh().equals(zhanghb.getJigh())) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "只能在同一网点内共用印鉴");
						}
						if (!gongyzh.getHum().equals(zhanghb.getHum())) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "只有户名一致时才可共用");
						}
						if (gongyzh.getYinjshzt().equals("未审")) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.zhanghkh.success", "共用账户["+gongyzh.getZhangh()+"]存在未审印鉴，无法共用");
						}
						
						zhanghb.setFuyrq(this.getSystemMgrService().getSystetemNowDate().substring(0,10));//新增复用日期
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
				// 记录日志
				String content = "修改账户(账号为" + zhanghb.getZhangh() + ";柜员代码:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				zhanghb.setYinjkbh(yinjkbhStr);
				this.createAccountManageLog(zhanghb.getZhangh(), "1", clerk,
						oldZhanghb, zhanghb);
				request.setAttribute("logintype", "ztzilxg");
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.zhanghkh.success", "修改成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.zhanghkh.success");
		}
	}

	/**
	 * 非核心账户资料修改（代发工资户、内部户）
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
		//将账户性质列表返回前台页面
		String tongdFlag="0";
		request.setAttribute("tongdFlag", tongdFlag);
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String zhangh = accountinfoform.getAccount();
		try {
			Zhanghb zhanghb = ZhanghbService.getZhanghb(zhangh);
			// 验证账户是否存在
			if (zhanghb == null) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.inner.zhanghkh.success", "账号：[" + zhangh
								+ "]不存在!");
			}
			zhanghb.setYinjkbh(ZhanghbService.getYinjkhByZhangh(zhanghb.getZhangh()));
			Zhanghb oldZhanghb = copyZhanghb(zhanghb);
			// 验证当前柜员操作账号权限
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.inner.zhanghkh.success", "您没有权限修改，账号:["
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
			//区号不为空 且长度不为0 要去掉首位0存入数据库
			String qh=accountinfoform.getLianxrqh();
			zhanghb.setLianxrqh(subAreaCode(qh));
			 qh=accountinfoform.getLianxrqh2();
			zhanghb.setLianxrqh2(subAreaCode(qh));
			qh=accountinfoform.getFuzrqh();
			zhanghb.setFuzrqh(subAreaCode(qh));
			qh=accountinfoform.getFuzrqh2();
			zhanghb.setFuzrqh2(subAreaCode(qh));
			
			
			request.setAttribute("logintype", "ztzilxg");
			List<String> oldYinjkbhList = new ArrayList<String>();// 修改之后不使用的印鉴卡
			List<String> commen = new ArrayList<String>();// 修改前后不变的印鉴卡
			// 将修改之前与之后改动过的印鉴卡编号区分出来
			splitYinjkList(oldyinjkbh, yinjkbhList, oldYinjkbhList, commen);
			
			//控制通兑标志下拉选项中是否包含二级分行
			Org org_forTongd=OrgService.getOrgByCode(zhanghb.getJigh());
			//当该机构的上级机构和省行机构相同时 不存在二级分行
			if(org_forTongd!=null&&org_forTongd.getWdflag().equals("3")&&org_forTongd.getParentCode().equals(org_forTongd.getShOrgCode())){
				tongdFlag="1";
			}
			request.setAttribute("tongdFlag", tongdFlag);
			
				// 检查新增的印鉴卡是否已经被使用
				boolean exist = yinjkService.CheckYinjkbhList(yinjkbhList);
				if (exist) {
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.inner.zhanghkh.success", "印鉴卡已被使用");
				}
				zhanghb.setYinjkbh(yinjkbhStr);
				ZhanghbService.updateZhanghb(zhanghb, yinjkbhList,
						oldYinjkbhList);

				// 记录日志
				String content = "修改账户(账号为" + zhanghb.getZhangh() + ";柜员代码:"
						+ clerk.getCode() + ")";
				this.createManageLog(clerk.getCode(), content);
				this.createAccountManageLog(zhanghb.getZhangh(), "1", clerk,
						oldZhanghb, zhanghb);
				request.setAttribute("logintype", "zilxg");

				request.setAttribute("kaih", "kaih");//控制页面是否跳转建库页面
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.inner.zhanghkh.success", "修改成功!");
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

	// 复制账号
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

	// 账户信息回显开户页面
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
		String shifdh="";//0：是  1：否

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
		// 新旧账号转换
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
				// 验证当前柜员操作账号 权限
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
				shifdh=accountinfo.getShifdh()==null?"0":accountinfo.getShifdh();//为空默认为是
				
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
				youwYJ=accountinfo.getYouwyj() == null ? "无" : accountinfo
						.getYouwyj();
				yinjshzt=accountinfo.getYinjshzt() == null ? "未审" : accountinfo
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
				//已审印鉴
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
				
				//判断该账号所在机构之上是否有二级分行
				Org org= OrgService.getOrgByCode(accountinfo.getJigh());
				if(org!=null&&org.getWdflag().equals("3")&&org.getParentCode().equals(org.getShOrgCode())){
					tongd="1";
				}
				//计算启用日期 默认为开户日期的次日
				if(kaihrq!=null&&kaihrq.length()==10){
					qiyrq=this.getSystemMgrService().getSystetemNextDate().substring(0,10);
				}
				
				
				//获取当前账号的建库柜员 尚未建库则设置为""
				if(!accountinfo.getYouwyj().equals("无")){
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
					//更new
					.append(",").append(linkman2).append(",").append(phone2)
					.append(",").append(fuzr2).append(",").append(fuzrdh2).append(",").append(shifdh).append(",").append(zhuzh).append(",").append(account)
					//20140724 增加区号 分机号
					.append(",").append(qh1).append(",").append(fjh1)
					.append(",").append(qh2).append(",").append(fjh2).append(",").append(qh3).append(",").append(fjh3).append(",").append(qh4).append(",").append(fjh4)
					//小码
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

	// 拆分印鉴卡号字符串
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

	// 账户信息回显前台验印页面
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
		// 新旧账号转换
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
				// 验证当前柜员操作账号 权限
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
				
				
				//已审印鉴
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
				
				//已审印鉴
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
	 * 联系人查询接口
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
			// 新旧账号转换
			if (zhangh == null || zhangh.trim().equals("")) {
				retcode = "0001";
				retinfo = "非法报文，查询联系人失败";
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
					retinfo = "账户不存在";
					jsonStr = JsonSystemTool.toJspnForAccountLinkManInfo(
							new Zhanghb(), retcode, retinfo);
				}
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			retcode = "0001";
			retinfo = "非法报文，查询联系人失败";
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
	 * 根据简账号查询核心账号
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
	 * 跳转账户通兑设置
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
	 * 根据账户获得账号通兑信息
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
			// 新旧账号转换

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
						"账号:[" + zhangh + "]不存在!");
			
			}
			// 判断当前柜员是否有权限查看该账号信息
			if (!this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh())) {
				request.setAttribute("pass", "0");
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.accountAllexchange.success",
						"您没有权限修改，账号:[" + zhangh + "]!");
			}
			// 解析通兑机构信息，生成机构列表
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
			// 将账户信息和机构信息回显页面
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
	 * 删除通兑机构
	 */
	public ActionForward deleteTongdsz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		String orgCode = (String) request.getParameter("orgCode");
		String zhangh = (String) request.getParameter("zhangh");
		// 新旧账号转换

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
			// 判断当前柜员是否有权限操作该账号
			boolean bool = this.getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), zhanghb.getJigh());
			if (!bool) {
				return super.showMessageJSP(actionMapping, request,
						"accountinfo.net.accountAllexchange.success",
						"您没有权限修改，账号:[" + zhangh + "]!");
			}
			// 解析通兑机构信息，生成机构列表
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
			// 将账户最新的通兑机构信息放入zhanghb中，并记录日志
			tongdsz = createStr(orgList);
			zhanghb.setTongdsz(tongdsz);
			ZhanghbService.update(zhanghb);
			String content="账号["+zhanghb.getZhangh()+"]删除通兑机构:"+orgCode;
			createAccountManageLog(zhanghb.getZhangh(), "1",content, clerk);
			createManageLog(clerk.getCode(), content);
			// 重定向到账户通兑机构列表页面
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
	 * 跳转新增账户通兑机构
	 * */
	public ActionForward addAllexchangeView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhangh = request.getParameter("zhangh");
			// 新旧账号转换
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
	 * 新增账户通兑机构
	 * */
	public ActionForward addAllexchange(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
			String zhangh = accountinfoform.getAccount();
			// //新旧账号转换
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
						"账户信息查询失败!");
					}
					Org tongdOrg = OrgService.getOrgByCode(tongd);
					request.setAttribute("zhanghb", zhanghb);
					if (tongdOrg == null) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.addAllexchange.success",
								"添加机构不存在!");
					}
					// 判断当前柜员是否有权限操作该账号
					boolean bool = this.getSystemMgrService().CanOperDesOrg(
							clerk.getOrgcode(), zhanghb.getJigh());
					if (!bool) {
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.addAllexchange.success",
								"您没有权限修改，账号:[" + zhangh + "]!");
					}
					if(zhanghb.getJigh().equals(tongd)){
						return super.showMessageJSP(actionMapping, request,
								"accountinfo.net.addAllexchange.success",
								"不能设置账户的所属机构!");
					}
					/*String tongctd = zhanghb.getTongctd();
					if (tongctd.equals("一级分行")) {
						if (!this.getSystemMgrService().CanOperDesOrg(
								clerk.getShOrgCode(), tongd)) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.addAllexchange.success",
									"设置的通兑网点不能与账户属于相同一级分行!");
						}
					}
					if (tongctd.equals("二级分行")) {
						Org erjOrg = OrgService.getOrgByCode(zhanghb.getJigh());
						String erjOrgCode = erjOrg.getWdflag().equals("3") ? erjOrg
								.getParentCode() : erjOrg.getCode();
						if (this.getSystemMgrService().CanOperDesOrg(erjOrgCode,
								tongd)) {
							return super.showMessageJSP(actionMapping, request,
									"accountinfo.net.addAllexchange.success",
									"设置的通兑网点不能与账户属于相同二级分行!");
						}
					}*/
					String tongdsz = zhanghb.getTongdsz();
					if (tongdsz == null || tongdsz.trim().length() == 0) {
						zhanghb.setTongdsz(tongd);
					} else {
						zhanghb.setTongdsz(tongdsz + "|" + tongd);
					}
					ZhanghbService.update(zhanghb);
					String content="账号["+zhanghb.getZhangh()+"]添加通兑机构:"+tongd;
					createAccountManageLog(zhanghb.getZhangh(), "1", content, clerk);
					createManageLog(clerk.getCode(),content);
					request.setAttribute("zhangh", zhanghb.getZhangh());
					request.setAttribute("tongd", zhanghb.getTongdsz());
					return super.showMessageJSP(actionMapping, request,
							"accountinfo.net.addAllexchange.success", "添加成功!");
			}
			return super.showMessageJSP(actionMapping, request,
					"accountinfo.net.addAllexchange.success", "添加失败!");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"accountinfo.net.addAllexchange.success");
		}
	}
	
	/**
	 * 跳转印鉴卡共用查询账户页面
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
	 * 根据参数查询印鉴卡共用的账户
	 */
	public ActionForward queryyinjkshare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm aform = (AccountinfoForm) form;

		try {
			// 柜员信息格式转化为json
			String jsonClerkrStr = JsonSystemTool.toJsonForClerkForZhang(clerk);
			request.setAttribute("jsonClerkrStr", jsonClerkrStr);

			String orgcode = aform.getOrgcode();
			String account = aform.getAccount();
			String yinjkbh = aform.getYinjkbh();
			// //新旧账号转换
			if (account != null && account.length() != 17) {
				account = ZhanghbService.parseTypeN(account, 17);
			}
			aform.setAccount(account);
			
			if (orgcode != null && !"".equals(orgcode)) {
				Org org = OrgService.getOrgByCode(orgcode);
				if (org == null) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", "输入无效,机构不存在!");
				}

				if (!this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(), orgcode)) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", " 沒有查该机构的权限!");
				}
			}
			if (account != null && !"".equals(account)) {
				Zhanghb zhanghb = ZhanghbService.getZhanghb(account);
				if (zhanghb == null) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", "账户不存在!");
				}
				if (!this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(), zhanghb.getJigh())) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", " 沒有查该账号的权限!");
				}
				if (zhanghb.getZhanghzt().equals("销户")) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", " 此账户已销户!");
				}
			}
			if (yinjkbh != null && !"".equals(yinjkbh)) {
				Yinjk yinjk = yinjkService.getYinjkByYinjkbh(yinjkbh);
				if (yinjk == null) {
					return this.showMessageJSP(mapping, request,
							"yinjkshare.success", "输入无效,印鉴卡不存在!");
				}
			}
			// 根据request中的参数创建分页对象
			TabsBo TabsBo = this.createTabsBo(request);

			// 将分页对象传入service
			ZhanghbServiceImpl zhanghbServiceImpl = (ZhanghbServiceImpl) ZhanghbService;
			zhanghbServiceImpl.setTabsService(TabsBo);

			// 执行SQL语句，返回查询结果
			if (orgcode == null || "".equals(orgcode)) {

				orgcode = clerk.getOrgcode();
			}
			TabsBo tabsBo = ZhanghbService.getYinjkShareList(orgcode, account,
					yinjkbh);
			//request.setAttribute("jlist", tabsBo.getParamterMapStr());
			//request.setAttribute("jsql", tabsBo.getSql());
			aform.setYinjkbh(yinjkbh);
			// 设置显示
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
	 * 查询待办事务
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