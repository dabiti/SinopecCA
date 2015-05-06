package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.framework.util.DateTool;
import com.sinopec.common.ClassFactory;
import com.sinopec.common.SpringContent;
import com.sinopec.report.model.Report;
import com.sinopec.report.model.ReportForm;
import com.sinopec.report.model.ReportResult;
import com.sinopec.report.model.ReportReturn;
import com.sinopec.report.util.HtmlUtil;
import com.sinopec.report.util.ReportTool;
import com.sinopec.report.util.UeportUserDefined;
import com.sinopec.report.util.UreportUserInterface;
import com.sinopec.table.model.SqlParameter;
import com.sinopec.table.model.Table;
import com.sinopec.table.model.Where;
import com.sinopec.table.util.GroupTool;
import com.sinopec.table.util.TableTool;
import com.sinopec.user.UreportCommon;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.service.DanbwhService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.DanbwhServiceImpl;
import com.unitop.util.CoreBillUtils;
import com.unitop.util.PhoneUtil;
//ureport����
@Controller("/reportService")
public class ReportServiceAction extends ExDispatchAction{

	@Resource
	private DanbwhService DanbwhService;
	@Resource
	private ZhanghbService zhanghbService;
	
	/*
	 * ���ɱ����б�
	 */
	public ActionForward doReport(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			//EC�����ʼ������ ����ҳ���ʼ��ʧ�� ��ʾ�հ�ҳ��
			request.setAttribute("totalRows", 0);
			String baobbs = request.getParameter("baobbs");
		try {
			Table table= null;
			//��ȡurport��Ϣ
			{
				List list  = getTableInfo(baobbs);
				//��֤������������ڷ�����ʾ��Ϣ
				if(list.size() == 0)
				{
					response.setCharacterEncoding("GBK");
					PrintWriter print = response.getWriter();
					print.write("�˱�����񲻴��ڻ�δ������!");
					print.close();
					return null;
				}
				Report report = new Report((Map)list.get(0));
				//���ureportû�����÷�ҳ��������Ĭ��ͳһ��ҳ����չʾ
				ServletContext servletContext = request.getSession().getServletContext();
				String ec_yemjlts = (String) servletContext.getAttribute("ec_yemjlts");
				if(report.getFenytj()==null||"".equals(report.getFenytj())||"0".equals(report.getFenytj()))
					report.setFenytj(ec_yemjlts);
				request.setAttribute("report", report);
			}
			//��ȡ����Ϣ
			{
				List list  = getTableForm(baobbs);
				StringBuffer formStringForHTML  = new StringBuffer();
				ReportForm reportForm = new ReportForm();
				for(int i = 0 ;i<list.size();i++)
				{
					reportForm.setReportFormList((Map)list.get(i));
					ReportForm report = (ReportForm) reportForm.getReportFormList().get(i);
					//��ȡ��̬����
					if("��̬��ѡ��".equals(report.getYaoslx()))
					{
						Table groupTable = new Table((report.getBeiz())); 
						List tlist = DanbwhService.doSelect(groupTable);
						String beiz = GroupTool.doGoupForDongtdxk(tlist);
						report.setBeiz(beiz);
					}
					if("��������".equals(report.getYaoslx())||"���ڷ�Χ".equals(report.getYaoslx()))
					{
						HtmlUtil.initReportForm(report);
					}else{
						//�״μ������Ĭ��ֵ
				//		String[] value = new String[2];
				//		value[0]="";
				//		value[1]="";
				//		report.setMoyz(value);
					}
					//��ʼ�� Result
					Clerk clerk  =(Clerk) request.getSession().getAttribute("clerk");
					String calsspath = UeportUserDefined.getClassPath(baobbs);
					ReportReturn reportReturn = null;
					if(calsspath!=null&&!"".equals(calsspath))
					{
						UreportUserInterface ureportUserInterface = (UreportUserInterface) ClassFactory.getClass(calsspath);
						reportReturn = ureportUserInterface.doInitForForm(clerk,report, new SpringContent(request.getSession().getServletContext()));
					}else{
						//Ĭ��ģʽ
						UreportCommon UreportCommon = new UreportCommon();
						reportReturn =UreportCommon.doInitForForm(clerk,report, new SpringContent(request.getSession().getServletContext()));
					}
					if(reportReturn!=null)
					{
						if(reportReturn.getTisxxid()!=null)
						{
							return super.showMessageJSP(actionMapping, request, "report", getPromptService().getPromptMsg(reportReturn.getTisxxid(),reportReturn.getMap()));
						}
						if(reportReturn.getTisxxMessage()!=null)
						{
							return super.showMessageJSP(actionMapping, request, "report", reportReturn.getTisxxMessage());
						}
					}
					
					HtmlUtil.doHtmlForForm(i,Integer.valueOf(getSystemContolParameter("report_line")), formStringForHTML, report);
				}
				//System.out.println("HTML   :    "+formStringForHTML.toString());
				request.setAttribute("formStringForHTML",formStringForHTML.toString());
			}
			//��ȡ�����Ϣ
			{
				List list  = getTableResult(baobbs);
				ReportResult reportResult = new ReportResult();
				for(int i = 0 ;i<list.size();i++)
				{
					reportResult.setReportResultList((Map)list.get(i));
				}
				request.setAttribute("reportResult", reportResult);
				
				//��ʼ�� Result
				Clerk clerk  =(Clerk) request.getSession().getAttribute("clerk");
				String calsspath = UeportUserDefined.getClassPath(baobbs);
				ReportReturn reportReturn = null;
				if(calsspath!=null&&!"".equals(calsspath))
				{
					UreportUserInterface ureportUserInterface = (UreportUserInterface) ClassFactory.getClass(calsspath);
					reportReturn = ureportUserInterface.doInitForResult(clerk,reportResult, new SpringContent(request.getSession().getServletContext()));
				}else{
					//Ĭ��ģʽ
					UreportCommon UreportCommon = new UreportCommon();
					reportReturn =UreportCommon.doInitForResult(clerk,reportResult, new SpringContent(request.getSession().getServletContext()));
				}
				if(reportReturn!=null)
				{
					if(reportReturn.getTisxxid()!=null)
					{
						return super.showMessageJSP(actionMapping, request, "report", getPromptService().getPromptMsg(reportReturn.getTisxxid(),reportReturn.getMap()));
					}
					if(reportReturn.getTisxxMessage()!=null)
					{
						return super.showMessageJSP(actionMapping, request, "report", reportReturn.getTisxxMessage());
					}
				}
			}
			return actionMapping.findForward("report");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,"report");
		}
	}

	static boolean bool =true;
	/*
	 * �����ѯ
	 */
	@SuppressWarnings("unchecked")
	public ActionForward exeReport(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			//EC�����ʼ������ ����ҳ���ʼ��ʧ�� ��ʾ�հ�ҳ��
			request.setAttribute("totalRows", 0);
		try {
			String baobbs = request.getParameter("baobbs");
			Table tableForSelect = new Table(null);
			tableForSelect.setField(new HashMap());
			
			Table table= null;
			//��ȡurport��Ϣ
			{
				List list  = getTableInfo(baobbs);
				Report report = new Report((Map)list.get(0));
				
				//���ureportû�����÷�ҳ��������Ĭ��ͳһ��ҳ����չʾ
				ServletContext servletContext = request.getSession().getServletContext();
				String ec_yemjlts = (String) servletContext.getAttribute("ec_yemjlts");
				if(report.getFenytj()==null||"".equals(report.getFenytj())||"0".equals(report.getFenytj()))
					report.setFenytj(ec_yemjlts);
				tableForSelect.setTableName(report.getShujhqfs());
				request.setAttribute("report", report);
			}
			
			//JSP��ʾ��Ϣ
			ActionMessages errors = new ActionMessages();
			//��ȡ����Ϣ
			{
				List list  = getTableForm(baobbs);
				StringBuffer formStringForHTML  = new StringBuffer();
				ReportForm reportForm = new ReportForm();
				Map fieldMap = new HashMap();
				Where where = new Where();
				Map fieldTableType = new HashMap();
				for(int i = 0 ;i<list.size();i++)
				{
					Map fieldMapTemp = (Map)list.get(i);
					reportForm.setReportFormList(fieldMapTemp);
					ReportForm report = (ReportForm) reportForm.getReportFormList().get(i);
					
					String[] value = (String[]) request.getParameterValues((String) fieldMapTemp.get("yaosbs"));
					fieldMapTemp.put("moyz", value==null?"":value[0]);
					Object yaosmc=fieldMapTemp.get("yaosmc");
					fieldMap.put(fieldMapTemp.get("yaosmc"), null);
				
					//���ѡ��ҳ������ѡ���ѯ
					if(value==null)
					{
						value = new String[1];
						value[0]="";
					}
					if(yaosmc!=null&&yaosmc.toString().equals("ZHANGH")){
						// �¾��˺�ת��
						if (value[0] != null && value[0].length() != 17) {
							value[0] = zhanghbService.parseTypeN(value[0], 17);
						}
					}
					report.setMoyz(value);
					if(!"".equals(value[0])&&null!=value[0])
					{
						String yaoslx = (String) fieldMapTemp.get("yaoslx");
						if("�ı�����|��������|��̬��ѡ��|��̬��ѡ��|��������".indexOf(yaoslx)==-1)
						{
							if("���ڷ�Χ".equals(yaoslx))
							{
								where.getWhereBigEqualsValue().put(fieldMapTemp.get("yaosmc"),value[0]);
								where.getWhereSmaillEqualsValue().put(fieldMapTemp.get("yaosmc"),value[1]);
								if(DateTool.bijRQ(value[0], value[1]))
								{
									errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.detail","���ڷ�Χ����ʼ���ڲ��ܴ�����ֹ����!"));
								}
							}
							if("��������".equals(yaoslx))
							{
								where.getWhereValue().put(fieldMapTemp.get("yaosmc"),value[0]);
							}
							String morz = report.getMoyz()[0];
							if("�Ӽ�����".equals(yaoslx)&&"��".equals(report.getMoyz()[0]))
							{
								where.getWhereInForSQL().put(fieldMapTemp.get("yaosmc"), report.getBeiz());
							}
							if("ģ������".equals(yaoslx))
							{
								where.getWhereLikeValue().put(fieldMapTemp.get("yaosmc"),"%"+value[0]+"%");
							}
						}else{
							if("ȫ��V2".equals(value[0]))
								;
							else{
								if((baobbs.equals("002")||baobbs.equals("004")||baobbs.equals("003"))&&yaosmc.equals("internalorganizationnumber")){
									where.getWhereInForSQL().put(fieldMapTemp.get("yaosmc"), "select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber =? ");
								}
									where.getWhereValue().put(fieldMapTemp.get("yaosmc"),value[0]);
								
							}
						}
						fieldTableType.put(fieldMapTemp.get("yaosmc").toString(), fieldMapTemp.get("yaosgs"));
					}
					
					//��ȡ��̬����
					if("��̬��ѡ��".equals(report.getYaoslx()))
					{
						Table groupTable = new Table((report.getBeiz())); 
						List tlist = DanbwhService.doSelect(groupTable);
						String beiz = GroupTool.doGoupForDongtdxk(tlist);
						report.setBeiz(beiz);
					}
					
					HtmlUtil.doHtmlForForm(i, Integer.valueOf(getSystemContolParameter("report_line")), formStringForHTML, report);
				}
				
				tableForSelect.setWhere(where);
				tableForSelect.setSqlParameter(new HashMap());
				tableForSelect.setFieldTableType(fieldTableType);
				request.setAttribute("formStringForHTML",formStringForHTML);
			}
			
			//��ȡ�����Ϣ
			{
				ReportResult reportResult = new ReportResult();
				List list  = getTableResult(baobbs);
				for(int i = 0 ;i<list.size();i++)
				{
					Map fieldMapTemp = (Map)list.get(i);
					reportResult.setReportResultList(fieldMapTemp);
					tableForSelect.getField().put(fieldMapTemp.get("yaosmc"),null);
				}
				request.setAttribute("reportResult", reportResult);
			}
			
			//�ж��Ƿ������ʾ��Ϣ �������ֱ�ӵ�����JSP
			this.saveErrors(request, errors);
			if(errors.size()>0)
			{
				return actionMapping.findForward("report");
			}
			
			//ҵ������
			Clerk clerk  =(Clerk) request.getSession().getAttribute("clerk");
			String calsspath = UeportUserDefined.getClassPath(baobbs);
			ReportReturn reportReturn = null;
			if(calsspath!=null&&!"".equals(calsspath))
			{
				UreportUserInterface ureportUserInterface = (UreportUserInterface) ClassFactory.getClass(calsspath);
				reportReturn = ureportUserInterface.UserRules(clerk, tableForSelect, new SpringContent(request.getSession().getServletContext()));
			}else{
				//Ĭ��ģʽ
				UreportCommon UreportCommon = new UreportCommon();
				reportReturn =UreportCommon.doCommon(clerk, tableForSelect, new SpringContent(request.getSession().getServletContext()));
			}
			if(reportReturn!=null)
			{
				if(reportReturn.getTisxxid()!=null)
				{
					return super.showMessageJSP(actionMapping, request, "report", getPromptService().getPromptMsg(reportReturn.getTisxxid(),reportReturn.getMap()));
				}
				if(reportReturn.getTisxxMessage()!=null)
				{
					return super.showMessageJSP(actionMapping, request, "report", reportReturn.getTisxxMessage());
				}
			}
			
			TabsBo TabsBo = this.createTabsBo(request);
			DanbwhServiceImpl DanbwhServiceImpl = (DanbwhServiceImpl) DanbwhService;
			DanbwhServiceImpl.setTabsService(TabsBo);
			TableTool.getSelectSQL(tableForSelect);
			ReportTool.doTableWhere(tableForSelect);//��������
			TabsBo tabsBo = DanbwhService.doSelectForPage(tableForSelect);
		
			if(baobbs.equals("002")){
				List<Map> list = updateAccountinfo(tabsBo);
				tabsBo.setList(list);
			}
			//List list=tabsBo.getList();
			//JSONArray jlist=JSONArray.fromObject(list);
			//request.setAttribute("jlist", tabsBo.getParamterMapStr());
			//request.setAttribute("jsql", tabsBo.getSql());
			ReportTool.doTableResult(tabsBo,getTableDictionary(baobbs));//1.�ֵ�ת��2.���������
			this.showTabsModel(request, tabsBo);
			return super.showMessageJSPForFeny(actionMapping,request,tabsBo,"report");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,"report");
		}
	}

	private List<Map> updateAccountinfo(TabsBo tabsBo) {
		String zhanghxz="";
		String shifdh="";
		String dianh="";
		String dianh2="";
		String dianh3="";
		String dianh4="";
		String ischecksmall="";
		List<Map> list=tabsBo.getList();
		if(list!=null&&list.size()>0){
			for (Map map : list) {
				zhanghxz=(String)map.get("zhanghxz");
				if(zhanghxz!=null&&!zhanghxz.trim().equals("")){
					if(zhanghxz.equals("�ڲ���")||zhanghxz.equals("�������ʻ�")){
						shifdh="";
					}else{
						shifdh=(String)map.get("shifdh");
						shifdh=shifdh!=null&&shifdh.equals("0")?"��":"��";
					}
				}
				map.put("shifdh",shifdh);
				
				ischecksmall=(String)map.get("ischecksmall");
				map.put("ischecksmall", ischecksmall==null||"".equals(ischecksmall.trim())||"0".equals(ischecksmall.trim())?"��":"��");
				 dianh=PhoneUtil.makePhoneToHole((String)map.get("areacode"),(String)map.get("phonenumber"),(String)map.get("extensionnumber"));
				 dianh2=PhoneUtil.makePhoneToHole((String)map.get("areacode2"),(String)map.get("phonenumber2"),(String)map.get("extensionnumber2"));
				 dianh3=PhoneUtil.makePhoneToHole((String)map.get("areacode3"),(String)map.get("phonenumber3"),(String)map.get("extensionnumber3"));
				 dianh4=PhoneUtil.makePhoneToHole((String)map.get("areacode4"),(String)map.get("phonenumber4"),(String)map.get("extensionnumber4"));
				 map.put("phonenumber", dianh);
				 map.put("phonenumber2", dianh2);
				 map.put("phonenumber3", dianh3);
				 map.put("phonenumber4", dianh4);
			}
		}
		return list;
	}
	
	static Map CasheMapForTableInfo = new HashMap();
	
	/*
	 * ��ȡUREPORT������Ϣ
	 */
	private List getTableInfo(String baobbs) throws Exception{
		String systemModel = SystemConfig.getInstance().getSystemModle();
		//Cashe
		{
				List list  = (List) CasheMapForTableInfo.get(baobbs);
				if(null!=list&&!"test".equals(systemModel))
				{
					return list;
				}
		}
		
		Table table = new Table("select * from r_baobpz where baobbs=?"); 
		table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
		List list  = DanbwhService.doSelect(table);
		
		if(CasheMapForTableInfo.size()<=15)
		{
			CasheMapForTableInfo.put(baobbs, list);
		}else{
			CasheMapForTableInfo.clear();
		}
		return list;
	}
	
	
	static Map CasheMapForTableForm = new HashMap();
	/*
	 * ��ȡUREPORT��������Ϣ
	 */
	private List getTableForm(String baobbs) throws Exception{
		String systemModel = SystemConfig.getInstance().getSystemModle();
		//Cashe
		{
				List list  = (List) CasheMapForTableForm.get(baobbs);
				if(null!=list&&!"test".equals(systemModel))
				{
					return list;
				}
		}
		Table table = new Table("select b.*,z.zhuanhz from  r_biaodyspz b left join r_zhidpz z on b.baobbs = z.suoyz where b.baobbs=?  order by to_number(b.xianssx)"); 
		table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
		List list  =  DanbwhService.doSelect(table);
		if(CasheMapForTableForm.size()<=15)
		{
			CasheMapForTableForm.put(baobbs, list);
		}else{
			CasheMapForTableForm.clear();
		}
		return list;
	}
	
	static Map CasheMapForTableResult = new HashMap();
	/*
	 * ��ȡUREPORT���������Ϣ
	 */
	private List getTableResult(String baobbs) throws Exception{
		String systemModel = SystemConfig.getInstance().getSystemModle();
		//Cashe
		{
				List list  = (List) CasheMapForTableResult.get(baobbs);
				if(null!=list&&!"test".equals(systemModel))
				{
					return list;
				}
		}
		Table table = new Table("select * from  r_jiegyspz where baobbs=? order by to_number(xianssx)"); 
		table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
		List list = DanbwhService.doSelect(table);
		if(CasheMapForTableResult.size()<=15)
		{
			CasheMapForTableResult.put(baobbs, list);
		}else{
			CasheMapForTableResult.clear();
		}
		return list;
	}
	static Map CasheMapForTableDictionary = new HashMap();
	/*
	 * ��ȡUREPORT�ֵ���Ϣ
	 */
	private Map getTableDictionary(String baobbs) throws Exception{
		Map rmap = new HashMap();
		String systemModel = SystemConfig.getInstance().getSystemModle();
		//Cashe
		{
				Map map  = (Map) CasheMapForTableDictionary.get(baobbs);
				if(null!=map&&!"test".equals(systemModel))
				{
					return map;
				}
		}
		Table table = new Table("select * from R_JIEGYSPZ where baobbs =? and zhidbs  is not null and zhidbs<>''"); 
		table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
		List list = DanbwhService.doSelect(table);
		if(list!=null&&list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				Map map = (Map) list.get(i);
				rmap.put(map.get("yaosmc"),map.get("zhidbs"));
			}
		}
		if(CasheMapForTableDictionary.size()<=15)
		{
			CasheMapForTableDictionary.put(baobbs, rmap);
		}else{
			CasheMapForTableDictionary.clear();
		}
		return rmap;
	}
	
	/*
	 * ��ѯ�����б�
	 */
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Table table = new Table("select BAOBBS, BAOBMC, BAOBBT, SHIFKY from r_baobpz t");
			List rlist1 =  DanbwhService.doSelect(table);
			if (rlist1 == null)
				rlist1 = new ArrayList();
			request.setAttribute("list", rlist1);
			return actionMapping.findForward("list");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "report");
		}
	}
}