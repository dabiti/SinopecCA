package com.sinopec.report.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinopec.report.model.ReportForm;
//html form 生成器
public class HtmlUtil {
	public static String doHtmlForForm(int i ,int lineNumber,StringBuffer formStringForHTML,ReportForm report){
		try{
			if(i%lineNumber==0)
			{
				//formStringForHTML.append("<tr>");
				printHtml(formStringForHTML,report);
			}else{
				printHtml(formStringForHTML,report);
				if(i==lineNumber||(i+1)%lineNumber==0)
				{
					//formStringForHTML.append("</tr>");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return formStringForHTML.toString();
	}
	
	private static void printHtml(StringBuffer formStringForHTML,ReportForm report){
		//是否必填
		boolean shifbt = report.shifbt.equals("是");
		//formStringForHTML.append("<td class='class1_td alignleft'>"); 
		formStringForHTML.append("<td class=' alignleft'>"); 
		if("日期范围".equals(report.yaoslx))
		{
			if(report.moyz.length!=2)initReportForm(report);
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：<input style = 'width:62px;'  id="+report.yaosbs+" name="+report.yaosbs+" type=text class='inputField "+(shifbt?"required":"")+"' value='"+report.moyz[0]+"' onclick='WdatePicker({isShowClear:false,readOnly:false})'/>-<input style = 'width:62px;' id="+report.yaosbs+" name="+report.yaosbs+" type=text class='inputField "+(shifbt?"required":"")+"' value='"+report.moyz[1]+"' onclick='WdatePicker({isShowClear:false,readOnly:false})'/>"+(shifbt?"<font color=red>*</font>":""));
		}
		if("日期输入".equals(report.yaoslx))
		{
			if(report.moyz.length!=2)initReportForm(report);
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：<input style = 'width:62px;'  id="+report.yaosbs+" name="+report.yaosbs+" type=text class='inputField "+(shifbt?"required":"")+"' value='"+report.moyz[1]+"' onclick='WdatePicker({isShowClear:false,readOnly:false})'/>"+(shifbt?"<font color=red>*</font>":""));
		}
		if("文本输入".equals(report.yaoslx))
		{
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：<input id="+report.yaosbs+" name="+report.yaosbs+" type=text class='inputField "+(shifbt?"required":"")+"' value='"+report.moyz[0]+"' />"+(shifbt?"<font color=red>*</font>":""));
		}
		if("模糊输入".equals(report.yaoslx))
		{
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：<input id="+report.yaosbs+" name="+report.yaosbs+" type=text class='inputField "+(shifbt?"required":"")+"' value='"+report.moyz[0]+"' />"+(shifbt?"<font color=red>*</font>":""));
		}
		if("数字输入".equals(report.yaoslx))
		{
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：<input id="+report.yaosbs+" name="+report.yaosbs+" type=text class='inputField "+(shifbt?"number":"")+"' value='"+report.moyz[0]+"' />"+(shifbt?"<font color=red>*</font>":""));
		}
		if("回显输入".equals(report.yaoslx))
		{
			String sql = PasswordUtil.encodePwd(report.getBeiz());
			String onclick = " onchange=startAjax('"+sql+"',this)" ;
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：<input id="+report.yaosbs+" name="+report.yaosbs+" type=text class='inputField "+(shifbt?"required":"")+"' value='"+report.moyz[0]+"' "+onclick+" />"+(shifbt?"<font color=red>*</font>":"")+"<span id="+sql+"Message></span>");	
		}
		if("子集输入".equals(report.yaoslx))
		{
			String beiz = report.beiz;
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：");
			formStringForHTML.append("<select id="+report.yaosbs+" name="+report.yaosbs+" class='"+(shifbt?"required":"")+"'/>");
			formStringForHTML.append("<option value='否' "+("否".equals(report.moyz[0])?"selected":"")+">否</option>");
			formStringForHTML.append("<option value='是' "+("是".equals(report.moyz[0])?"selected":"")+">是</option>");
			formStringForHTML.append("</select>");
			formStringForHTML.append(shifbt?"<font color=red>*</font>":"");
		}
		if("静态多选框".equals(report.yaoslx))
		{
			String beiz = report.beiz;
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：");
			formStringForHTML.append("<select id="+report.yaosbs+" name="+report.yaosbs+" class='"+(shifbt?"required":"")+"'/>");
			if(!report.baobbs.equals("004")&&!report.baobbs.equals("003")){
			formStringForHTML.append("<option value='全部V2'>全部</option>");
			}
			String[] options = beiz.split("\\|");
			if(options.length > 1)
			{
				for(int i = 0 ;i<options.length;i++)
				{	
					String[] option = options[i].split("\\=");
					formStringForHTML.append("<option value='"+option[1]+"' "+(option[1].equals(report.moyz[0])?"selected":"")+">"+option[0]+"</option>");
				}
			}
			formStringForHTML.append("</select>");
			formStringForHTML.append(shifbt?"<font color=red>*</font>":"");
		}
		
		if("动态多选框".equals(report.yaoslx))
		{
			String beiz = report.beiz;
			formStringForHTML.append(toStringFor4(report.yaosbt)+"：");
			formStringForHTML.append("<select id="+report.yaosbs+" name="+report.yaosbs+" class='"+(shifbt?"required":"")+"'/>");
			if(!report.baobbs.equals("004")&&!report.baobbs.equals("003")){
				formStringForHTML.append("<option value='全部V2'>全部</option>");
			}
			String[] options = beiz.split("\\|");
			if(options.length > 1)
			{
				for(int i = 0 ;i<options.length;i++)
				{	
					String[] option = options[i].split("\\=");
					formStringForHTML.append("<option value='"+option[1]+"' "+(option[1].equals(report.moyz[0])?"selected":"")+">"+option[0]+"</option>");
				}
			}
			formStringForHTML.append("</select>");
			formStringForHTML.append(shifbt?"<font color=red>*</font>":"");
		}
		formStringForHTML.append("</td>");
	}
	
	private static String toStringFor4(String string){
		//if(string.length()==3) string+="&nbsp;&nbsp;&nbsp;&nbsp;";
		//if(string.length()==2) string+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		return string;
	}
	
	//初始化日期组件 默认从01日期开始
	public static ReportForm initReportForm(ReportForm report){
		Date date = new Date();
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
		String[] dateMrz = new String[2];
		dateMrz[0]=(sformat.format(date)).substring(0, 8)+"01";
		dateMrz[1]=sformat.format(date);
		report.setMoyz(dateMrz);
		return report;
	}
	//生成INPUT标签
	public static String getHtmlForInput(String id,String name ,String type,String vlaue){
		return "<input id='"+id+"' name='"+name+"' type='"+type+"' value='"+vlaue+"'/>";
	}
	
	//主键不能修改
	public static String getHtmlForJavascript(String id,String value){
		return "<script>document.getElementById('"+id+"').value='"+value+"';document.getElementById('"+id+"').readOnly=true;</script>";
	}
	//主键不能修改
	public static String getHtmlForZhibLinked(int id,String tableName,String linkName){
		return "<button type='button' onclick=javascirpt:zhucongb('"+id+"','"+tableName+"');  onmouseover=\"this.className='buttom2'\" onmouseout=\"this.className='buttom1'\" class='buttom1'>"+linkName+"</button>";
	}
}
