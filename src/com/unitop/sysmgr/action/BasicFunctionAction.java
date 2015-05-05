package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Jicgncd;
import com.unitop.sysmgr.bo.JicgncdId;
import com.unitop.sysmgr.service.BasicFunctionServcie;

@Controller("/basicFunction")
public class BasicFunctionAction extends ExDispatchAction {
	
	@Resource
	BasicFunctionServcie BasicFunctionServcie;
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			response.setCharacterEncoding("GBK");
		try {
				Jicgncd jicg =new Jicgncd();
				jicg.setId(new JicgncdId(request.getParameter("gongnid"),"0"));
				jicg.setCaidlx((request.getParameter("gongnfl")));
				jicg.setGongnmc((request.getParameter("gongnmc")));
				jicg.setGongnurl(request.getParameter("gongnurl"));
				jicg.setQuanxwz(request.getParameter("quanxwz"));
				jicg.setGongnlx("0");
				jicg.setBeiz("");
				BasicFunctionServcie.insert(jicg);
				PrintWriter print;
				print = response.getWriter();
				print.write("添加成功!");
				return mapping.findForward(null);
			} catch (Exception e) {
				e.printStackTrace();
				PrintWriter print;
				try {
					print = response.getWriter();
					print.write("异常："+e.getCause());
					print.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return this.errrForLogAndException(e, mapping, request, null);
			}
	}
	
	public ActionForward getPost(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("GBK");
		try {
			Jicgncd jicg =new Jicgncd();
			jicg.setQuanxwz(request.getParameter("quanxwz"));
			jicg = BasicFunctionServcie.getPost(jicg);
			PrintWriter print;
			try {
				print = response.getWriter();
				if(jicg!=null)
				{
					print.write("该权限位已被["+jicg.getCaidlx()+"|"+jicg.getGongnmc()+"]使用");
				}
				print.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request, null);
		}
	}
	
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			response.setCharacterEncoding("GBK");
		try {
			Jicgncd jicg =new Jicgncd();
			jicg.setId(new JicgncdId(request.getParameter("gongnid"),"0"));
			jicg = BasicFunctionServcie.select(jicg);
			PrintWriter print;
			try {
				print = response.getWriter();
				if(jicg!=null)
				{
					print.write("该编号已被["+jicg.getCaidlx()+"|"+jicg.getGongnmc()+"]使用");
				}
				print.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, null);
		}
	}
}
