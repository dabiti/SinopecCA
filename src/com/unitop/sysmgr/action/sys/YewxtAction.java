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
import com.unitop.sysmgr.bo.sys.Ci_yewxt;
import com.unitop.sysmgr.dao.InterFaceParameter;
import com.unitop.sysmgr.form.sys.YewxtForm;

@Controller("/yewxt")
public class YewxtAction extends ExDispatchAction {
	
	@Resource
	private InterFaceParameter interParameter;
	
	public ActionForward select(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = interParameter.findAllYewxt();
			request.setAttribute("listYewxt", list);
			return mapping.findForward("showyewxt");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"showyewxt");
		}
	}
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String xitbs = request.getParameter("xitbs");
		try {
			interParameter.deleteYewxt(xitbs);
			return mapping.findForward("deleteyewxt");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deleteyewxt");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		YewxtForm yewxtForm = (YewxtForm)actionForm;
		String xitbs = request.getParameter("xitbs");
		try {
			Ci_yewxt yewxt = interParameter.findYewxt(xitbs);
			yewxtForm.setXitbs(yewxt.getXitbs());
			yewxtForm.setXitmc(yewxt.getXitmc());
			yewxtForm.setYanzxx(yewxt.getYanzxx());
			return mapping.findForward("findyewxt");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"findyewxt");
		}
	}
	//hhh
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		YewxtForm yewxtForm = (YewxtForm)actionForm;
		Ci_yewxt yewxt = new Ci_yewxt();
		try {
			yewxt.setXitbs(yewxtForm.getXitbs());
			yewxt.setXitmc(yewxtForm.getXitmc());
			yewxt.setYanzxx(yewxtForm.getYanzxx());
			interParameter.updateYewxt(yewxt);
			return mapping.findForward("updateyewxt");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updateyewxt");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		YewxtForm yewxtForm = (YewxtForm)actionForm;
		yewxtForm.clear();
		return mapping.findForward("doyewxt");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		YewxtForm yewxtForm = (YewxtForm)actionForm;
		Ci_yewxt yewxt = new Ci_yewxt();
		try {
			yewxt.setXitbs(yewxtForm.getXitbs());
			yewxt.setXitmc(yewxtForm.getXitmc());
			yewxt.setYanzxx(yewxtForm.getYanzxx());
			interParameter.addYewxt(yewxt);
			return mapping.findForward("addyewxt");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"addyewxt");
		}
	}
}
