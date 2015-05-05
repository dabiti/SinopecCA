<%@page import="java.util.*,com.unitop.framework.util.*,com.unitop.sysmgr.bo.BusinessRule"%>
<%@ page import="com.unitop.sysmgr.service.BusinessService,javax.servlet.ServletContext,org.springframework.web.context.WebApplicationContext,org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.unitop.sysmgr.service.PromptService"%>
<link rel="stylesheet" type="text/css" media="screen" href="style/validate_css/screen.css" />
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/messages_cn.js" type="text/javascript"></script>
	<%
	ServletContext servletContext = this.getServletConfig().getServletContext(); ;
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	BusinessService busiService = (BusinessService) wac.getBean("BusinessServiceImpl");
	PromptService promptService = (PromptService)wac.getBean("PromptServiceImpl");
	Map rulesmap = busiService.getBusinessRules();

	out.println("<script type='text/javascript'>");
	Iterator it=rulesmap.keySet().iterator();
/**
	Iterator<String> itVali=rulesmap.keySet().iterator();
	while(itVali.hasNext()){
		BusinessRule rule = rulesmap.get(itVali.next());
	}
*/
	out.println("$(document).ready(function(){");
	while(it.hasNext()){
		BusinessRule rule =(BusinessRule) rulesmap.get(it.next());
		/**
		校验规则
		*/
		if("".equals(rule.getValidateRule())){
						
		}else{
			if("".equals(rule.getValidateRule())){
				out.println("var "+rule.getYuansId()+"='"+rule.getValidateRule()+"';");
			}else{
				out.println("var "+rule.getYuansId()+"="+rule.getValidateRule()+";");
			}
			out.println("$.validator.addMethod('"+rule.getYuansId()+"', function(value,element) {");
			out.println("return this.optional(element) || "+rule.getYuansId()+".test(value);");
			out.println("},'"+promptService.getPromptMsg(rule.getMsgId(),new HashMap())+"');");
		}
			
		/**
		栏位样式
		*/
		String maxlength = rule.getYuansId()+"_maxlength";
		String style = rule.getYuansId()+"_style";
		String readonly = rule.getYuansId()+"_readonly";
		out.println("var "+maxlength+"='"+rule.getMaxLength()+"';var "+style+"='"+rule.getYuansStyle()+"';");
		out.println("var "+readonly+"='"+rule.getIsReadonly()+"';");
		out.println("setYewgzdz('"+rule.getYuansId()+"',"+maxlength+","+style+","+readonly+");\n\n");
	}
		out.println("})");
		out.println("function setYewgzdz(name,maxlength,style,isreadonly){"); 
		out.println("if(document.getElementById(name)!=null){");
		out.println("if(maxlength!=''){");	
		out.println("$('#'+name).attr('maxlength',maxlength);");
		out.println("}");
		out.println("if(isreadonly!=''){");	
		out.println("$('#'+name).attr('readonly',isreadonly);");
		out.println("}");
		
		out.println("if(style!=''){");		
		out.println("$('#'+name).attr('style',style);");
		out.println("}");
		out.println("if(name!=''){");
		out.println("$('#'+name).addClass(name)");
		out.println("}");
		out.println("}}\n\n");
	out.println("</script>");
%>