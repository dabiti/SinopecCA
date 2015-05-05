package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.framework.util.JsonTool;
import com.unitop.framework.util.MD5Tool;
import com.unitop.sysmgr.bo.Dayfwcs;
import com.unitop.sysmgr.form.DayfwcsForm;
import com.unitop.sysmgr.service.DayfwService;

@Controller("/dayfw")
public class DayfwAction extends ExDispatchAction{
	@Resource
	private DayfwService dayfwService;
	
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		try {
			    List<Dayfwcs> dayfwlist = dayfwService.selectAll();
			    request.setAttribute("dayfwlist", dayfwlist);
	    		return actionMapping.findForward("list");
			} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}
	}
	
	public ActionForward forwardModify(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
			String dayfwid = request.getParameter("dayfwid");
			Dayfwcs dayfwcs = dayfwService.getDayfwcs(dayfwid);
			request.setAttribute("dayfwcs", dayfwcs);
			return actionMapping.findForward("modify");
	}
	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		   String dayfwid = request.getParameter("dayfwid");
		   dayfwService.delete(dayfwid);
		   return this.showMessageJSP(actionMapping,request,"load","成功删除此项打印服务");
	}
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		   String dayfwid = request.getParameter("dayfwid");
		   try {
			   dayfwid =URLDecoder.decode(dayfwid,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		   dayfwid = dayfwid.replace('\\', '/');
		    Dayfwcs d = (Dayfwcs)JsonTool.toBean(dayfwid, Dayfwcs.class);		   
		    int f = d.getDayfwmc().indexOf("//");
		    String daymc = d.getDayfwmc();
		    if(f==-1){
			   daymc = "//"+d.getBendname().toUpperCase()+"/"+d.getDayfwmc();
			}
		   dayfwid = transDayfwid(dayfwid);
		   d.setDayfwid(dayfwid);
           d.setDangqsl(0);
           d.setZuidsl(4000);
           d.setDayfwmc(daymc);
           dayfwService.save(d);
		   return this.showMessageJSP(actionMapping,request,"load","打印服务注册成功！");
	}
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		   DayfwcsForm dayfwcsForm = (DayfwcsForm) actionForm;
		   
		   Dayfwcs dayfwcs = new Dayfwcs();
		   dayfwcs.setDayfwip(dayfwcsForm.getDayfwip());
		   dayfwcs.setDayfwport(dayfwcsForm.getDayfwport());
		   dayfwcs.setDayfwmc(dayfwcsForm.getDayfwmc());
		   dayfwcs.setDayfwmac(dayfwcsForm.getDayfwmac());
		   dayfwcs.setDangqsl(dayfwcsForm.getDangqsl());
		   dayfwcs.setZuidsl(dayfwcsForm.getZuidsl());
		   dayfwcs.setDayfwid(dayfwcsForm.getDayfwid());
		   try {
			dayfwService.update(dayfwcs, dayfwcsForm.getDayfwid());
		} catch (Exception e) {
			e.printStackTrace();
			return this.showMessageJSP(actionMapping,request,"load","更新打印服务失败！");
		}
		 return this.showMessageJSP(actionMapping,request,"load","打印服务修改成功！");
	}

	public ActionForward printchk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		String result = "";
		try {
		String dayfwmsg = request.getParameter("dayfwmsg");
		 try {
		  dayfwmsg = URLDecoder.decode(dayfwmsg,"utf-8");
		} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		}
		
		  dayfwmsg = dayfwmsg.replace('\\', '/');
		  Dayfwcs d = (Dayfwcs)JsonTool.toBean(dayfwmsg, Dayfwcs.class);
		  String dayfwid = transDayfwid(dayfwmsg);
		d = dayfwService.getDayfwcs(dayfwid);
		
		if(d==null){
		   result="f";	
		}else{
		   result =""+(d.getZuidsl()-d.getDangqsl());
		}
           
		response.setContentType("text/xml");
		response.setLocale(Locale.SIMPLIFIED_CHINESE);
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	//转换打印服务唯一标识
	public String transDayfwid(String dayfwmsg){
		dayfwmsg = dayfwmsg.replace('\\', '/');
		Dayfwcs d = (Dayfwcs)JsonTool.toBean(dayfwmsg, Dayfwcs.class);
		String[] port = d.getDayfwport().split(".");
		String str ="";
		if(port.length==4){
			 str = d.getDayfwport(); 
		}else{
			int f = d.getDayfwmc().indexOf("//");
			if(f!=-1){
				str = d.getDayfwmc()+"|"+d.getDayfwport()+"|"+d.getDayfwmac();
			}else{
				str = "//"+d.getBendname().toUpperCase()+"/"+d.getDayfwmc()+"|"+d.getDayfwport()+"|"+d.getDayfwmac();
			}
		}
		String dayfwid = MD5Tool.getMD5ByStr(str);
		return dayfwid;
	}
}
