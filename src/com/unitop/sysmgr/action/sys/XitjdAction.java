package com.unitop.sysmgr.action.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.action.ExDispatchAction;
import com.unitop.sysmgr.bo.sys.Ci_xitjd;
import com.unitop.sysmgr.dao.InterFaceParameter;
import com.unitop.sysmgr.form.sys.XitjdForm;

@Controller("/xitjd")
public class XitjdAction extends ExDispatchAction{
	
	@Resource
	private InterFaceParameter interParameter;

	
	public ActionForward select(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = interParameter.findAllXitjd();
			request.setAttribute("listXitjd", list);
			return mapping.findForward("showXitjd");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"showXitjd");
		}
	}
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String jiedbs = request.getParameter("jiedbs");
		try {
			interParameter.deleteXitjd(jiedbs);
			return mapping.findForward("deleteXitjd");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deleteXitjd");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitjdForm xitjdForm = (XitjdForm)actionForm;
		String jiedbs = request.getParameter("jiedbs");
		try {
			Ci_xitjd xitjd = interParameter.findXitjd(jiedbs);
			xitjdForm.setJiedbs(xitjd.getJiedbs());
			xitjdForm.setJiedmc(xitjd.getJiedmc());
			return mapping.findForward("findXitjd");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"findXitjd");
		}
	}
	//hhh
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitjdForm xitjdForm = (XitjdForm)actionForm;
		Ci_xitjd xitjd = new Ci_xitjd();
		try {
			xitjd.setJiedbs(xitjdForm.getJiedbs());
			xitjd.setJiedmc(xitjdForm.getJiedmc());
			interParameter.updateXitjd(xitjd);
			return mapping.findForward("updateXitjd");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updateXitjd");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitjdForm xitjdForm = (XitjdForm)actionForm;
		xitjdForm.clear();
		return mapping.findForward("doXitjd");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitjdForm xitjdForm = (XitjdForm)actionForm;
		Ci_xitjd xitjd = new Ci_xitjd();
		try {
			xitjd.setJiedbs(xitjdForm.getJiedbs());
			xitjd.setJiedmc(xitjdForm.getJiedmc());
			interParameter.addXitjd(xitjd);
			return mapping.findForward("addXitjd");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"addXitjd");
		}
	}
}
