package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.bo.Autopasscount;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.JiejrBo;
import com.unitop.sysmgr.service.AjaxService;
import com.unitop.sysmgr.service.JiejrService;
import com.unitop.sysmgr.service.QueryService;
import com.unitop.util.ExportPdfUtil;
import com.unitop.util.PdfHeaderFooter;

//ajax 异步处理
@Controller("/ajax")
public class AjaxAction extends ExDispatchAction {
	
	@Resource
	private AjaxService ajaxServicel;
	@Resource
	private JiejrService jiejrService;
	@Resource
	private QueryService queryService;
	
	public ActionForward getMessage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) {
		String sql = request.getParameter("sql");
		String message = request.getParameter("message");
		try {
			message = ajaxServicel.ajax(sql,message);
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			out.println(message);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}
	
	
	//获取节假日管理
	public ActionForward getJiejrgl(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) {
		String year = request.getParameter("year");
		try {
			JiejrBo jiejrBo = jiejrService.getJiejr(year);
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(jiejrBo.getMonthString());
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}
	
	//保存 节假日管理
	public ActionForward saveJiejrgl(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) {
		String year = request.getParameter("year");
		String monthString = request.getParameter("monthString");
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		PrintWriter out = null;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			JiejrBo JiejrBo = new JiejrBo();
			JiejrBo.setYear(year);
			String[] monthStrings = monthString.split(",");
			JiejrBo.setMonth_01(monthStrings[0]);
			JiejrBo.setMonth_02(monthStrings[1]);
			JiejrBo.setMonth_03(monthStrings[2]);
			JiejrBo.setMonth_04(monthStrings[3]);
			JiejrBo.setMonth_05(monthStrings[4]);
			JiejrBo.setMonth_06(monthStrings[5]);
			JiejrBo.setMonth_07(monthStrings[6]);
			JiejrBo.setMonth_08(monthStrings[7]);
			JiejrBo.setMonth_09(monthStrings[8]);
			JiejrBo.setMonth_10(monthStrings[9]);
			JiejrBo.setMonth_11(monthStrings[10]);
			JiejrBo.setMonth_12(monthStrings[11]);
			jiejrService.updateOrSaveJiejr(JiejrBo);
			String content = "保存["+year+"]节假日数据";
			this.createManageLog(clerk.getCode(), content);
			out = response.getWriter();
			out.println("1");
			return null;
		} catch (Exception e) {
			if(out!=null)
			out.println("0");
			return this.errrForLogAndException(e, actionMapping, request, null);
		}finally{
			if(out!=null)
			out.close();
		}
	}
	
	//删除 节假日管理
	public ActionForward deletJiejrgl(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) {
		String year = request.getParameter("year");
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		PrintWriter out = null;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			jiejrService.deletJiejr(year);
			String content = "删除["+year+"]节假日数据";
			this.createManageLog(clerk.getCode(), content);
			out = response.getWriter();
			out.println("1");
			return null;
		} catch (Exception e) {
			if(out!=null)
			out.println("0");
			return this.errrForLogAndException(e, actionMapping, request, null);
		}finally{
			if(out!=null)
			out.close();
		}
	}
	//数据导出pdf
	public ActionForward exportPdf(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) {
		ServletOutputStream	out=null;
		try {
			request.setCharacterEncoding("GBK");
			String className = request.getParameter("className");
			String fileName =className+".pdf";
			String jlist =  URLDecoder.decode(request
					.getParameter("jlist"), "utf-8");
			String jsql =  URLDecoder.decode(request
					.getParameter("jsql"), "utf-8");
			Class c=null;
			if(className.substring(0, 3).equals("Map")){
				c=Class.forName("java.util.Map");
			}else if(className.indexOf("SealcheckLog")!=-1){
				
				c =Class.forName("com.unitop.sysmgr.bo.SealcheckLog");
			}else{
				c =Class.forName("com.unitop.sysmgr.bo."+className);
			}
			Map<String,String> parameterMap=praseJsonToMap(jlist);
			String titlename=ExportPdfUtil.parseTitlename(className);//表名
			List<String>colnames=ExportPdfUtil.parseTitles(className);//列名
			List<String>cols=ExportPdfUtil.parseCols(className);//每列对应的字段
			List<Integer>colsize=ExportPdfUtil.parseColsize(className);//每个字段分别占用的列数
			int colnum=ExportPdfUtil.parseConum(className);//总列数
			
			response.setHeader("Content-disposition",
				"attachment;filename=" + fileName);
			response.setContentType("application/pdf");
			Document document =new Document(PageSize.A4,-48,-48,36,36);
			out=response.getOutputStream();
			PdfHeaderFooter phf=new PdfHeaderFooter(ExportPdfUtil.getBasefont(), className) ;
			PdfWriter pw =PdfWriter.getInstance(document, out);
			Rectangle rect= new Rectangle(36,54,559,888);
			rect.setBorderColor(BaseColor.BLACK);
			pw.setBoxSize("art", rect);
			pw.setPageEvent(phf);
			Font font=new Font(ExportPdfUtil.getBasefont(),9);
			document.open();
			ExportPdfUtil.setTitle(document,titlename+"\n\n",new Font(ExportPdfUtil.getBasefont(),22));
			if(c.getName().equals("com.unitop.sysmgr.bo.SealcheckLog")){
				String jigh=parameterMap.get("orgCode");
				String begindate=DateTool.toSimpleFormat2(parameterMap.get("begindate"));
				String enddate=DateTool.toSimpleFormat2(parameterMap.get("enddate"));
				String content="机构号："+(jigh==null?"":jigh)+"                               验印日期："+begindate+"-"+enddate+"\n\r";
				jsql=jsql.replaceAll("<br/>", "\r");
				ExportPdfUtil.setTableHead(document, content, new Font(ExportPdfUtil.getBasefont(),12));
			}
			PdfPTable pdfPTable=new PdfPTable(colnum);
			ExportPdfUtil.setFirstLine(pdfPTable, colnames, font,colsize);
			document.add(pdfPTable);
			List list=null;
			int first=1;
			int end=0;
			do{
				pdfPTable=new PdfPTable(colnum);
				end=first+1000;
				if(c.getName().equals("java.util.Map")){
					list=null;
					list=queryService.queryDataToMap(jsql,parameterMap, first, end,className);
					ExportPdfUtil.setDataFromMap(pdfPTable, list, font, cols,colsize);
					
				}else{
					list=null;
					list=queryService.queryData(jsql,parameterMap, first, end,className);
					ExportPdfUtil.setData(pdfPTable, list, font, c, cols,colsize,first);
				}
				first+=1000;
				document.add(pdfPTable);
			}while(list!=null&&list.size()!=0);
			Autopasscount ap=queryService.countAutopassrate(jsql, 0, jlist);
			String tailmsg="                                                                                                共验印"+ap.getTotal()+"笔，自动通过"+ap.getAutopassnum()+"笔\r                                                                                                           自动通过率"+ap.getPassrate();
			ExportPdfUtil.setTailmsg(document, tailmsg, new Font(ExportPdfUtil.getBasefont(),9));
			//document.add(pdfPTable);
			document.close();
			pw.flush();
			out.flush();
			return null;
		} catch (Exception e) {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		}
	}




/*	//数据导出pdf
	public ActionForward exportPdf(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) {
		ServletOutputStream	out=null;
		try {
			request.setCharacterEncoding("GBK");
			String className = request.getParameter("className");
			String fileName =className+".pdf";
			String jlist =  URLDecoder.decode(request
					.getParameter("jlist"), "utf-8");
			String jsql =  URLDecoder.decode(request
					.getParameter("jsql"), "utf-8");
			Class c=null;
			if(className.substring(0, 3).equals("Map")){
				c=Class.forName("java.util.Map");
			}else if(className.indexOf("SealcheckLog")!=-1){
				
				c =Class.forName("com.unitop.sysmgr.bo.SealcheckLog");
			}else{
				c =Class.forName("com.unitop.sysmgr.bo."+className);
			}
			//List list =JSONArray.toList(JSONArray.fromObject(jlist),c);
			Map<String,String> parameterMap=praseJsonToMap(jlist);
			List<String>colnames=ExportPdfUtil.parseTitles(className);
			List<String>cols=ExportPdfUtil.parseCols(className);
			response.setHeader("Content-disposition",
				"attachment;filename=" + fileName);
			response.setContentType("application/pdf");
			Document document =new Document(PageSize.A3);
			out=response.getOutputStream();
			PdfWriter pw =PdfWriter.getInstance(document, out);
			document.open();
			Font font=new Font(ExportPdfUtil.getBasefont(),9);
			PdfPTable pdfPTable=new PdfPTable(colnames.size());
			ExportPdfUtil.setFirstLine(pdfPTable, colnames, font);
//			if(c.getName().equals("java.util.Map")){
//				ExportPdfUtil.setDataFromMap(pdfPTable, list, font, cols);
//			}else{
//				ExportPdfUtil.setData(pdfPTable, list, font, c, cols);
//			}
			document.add(pdfPTable);
			document.close();
			pw.flush();
			out.flush();
			return null;
		} catch (Exception e) {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		}
	}
*/

	private Map<String,String> praseJsonToMap(String jlist) {
		Map<String,String> map =new HashMap<String,String>();
		JSONObject json =JSONObject.fromObject(jlist);
		Iterator<String> keys=json.keys();
		while(keys.hasNext()){
			String key =keys.next();
			String value=json.get(key).toString();
			map.put(key, value);
		}
		return map;
	}
	
}
