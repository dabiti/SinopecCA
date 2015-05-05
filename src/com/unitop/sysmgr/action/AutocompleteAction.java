package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.action.ExDispatchAction;
import com.unitop.sysmgr.service.AutoCompleteService;

@Controller("/autocomplete")
public class AutocompleteAction extends ExDispatchAction {
	
	@Resource
	private AutoCompleteService AutoCompleteService =null;
	
	//帐号模糊查询
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		try {
			String account = request.getParameter("account");
			String messageAccountinfo = AutoCompleteService.autoCompleteForZhangh(account);
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(messageAccountinfo);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,null);
		}
	}
	
	//抓取表字段信息
	public ActionForward getTableLine(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		try {
			String tableName = request.getParameter("tableName");
			String messageTableLine = AutoCompleteService.getTableLineMap(tableName);
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(messageTableLine);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,null);
		}
	}
	
	//抓取字段信息
	public ActionForward getZhidMC(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		try {
			String zhidId = request.getParameter("zhidId");
			String messageTableLine = AutoCompleteService.getZhidMC(zhidId);
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(messageTableLine);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,null);
		}
	}
	
}
