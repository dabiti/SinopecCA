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
import com.unitop.sysmgr.bo.sys.PeizDpi;
import com.unitop.sysmgr.dao.InterFaceParameter;
import com.unitop.sysmgr.form.sys.PeizDPIForm;

@Controller("/peizDPI")
public class PeizDPIParameterAction extends ExDispatchAction {
	
	@Resource
	private InterFaceParameter interParameter;
	
	public ActionForward select(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = interParameter.findAllDPI();
			request.setAttribute("listDPI", list);
			return mapping.findForward("showDPI");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"showDPI");
		}
	}
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String dpiid = request.getParameter("dpiid");
		try {
			interParameter.deleteDPI(dpiid);
			return mapping.findForward("deleteDPI");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deleteDPI");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PeizDPIForm dpiForm = (PeizDPIForm) actionForm;
		String dpiid = request.getParameter("dpiid");
		try {
			PeizDpi peizDpi = interParameter.findDPIById(dpiid);
			dpiForm.setDpiid(peizDpi.getDpiid());
			dpiForm.setDpi(peizDpi.getDpi());
			dpiForm.setDiqh(peizDpi.getDiqh());
			dpiForm.setSecbz(peizDpi.getSecbz());
			dpiForm.setTiaoycs(peizDpi.getTiaoycs());
			dpiForm.setYewlx(peizDpi.getYewlx());
			dpiForm.setWangdh(peizDpi.getWangdh());
			return mapping.findForward("findDPI");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"findDPI");
		}
	}
	//hhh
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PeizDPIForm dpiForm = (PeizDPIForm) actionForm;
		PeizDpi peizDpi = new PeizDpi();
		try {
			peizDpi.setSecbz(dpiForm.getSecbz());
			peizDpi.setDiqh(dpiForm.getDiqh());
			peizDpi.setDpi(dpiForm.getDpi());
			peizDpi.setDpiid(dpiForm.getDpiid());
			peizDpi.setWangdh(dpiForm.getWangdh());
			peizDpi.setYewlx(dpiForm.getYewlx());
			peizDpi.setTiaoycs(dpiForm.getTiaoycs());
			interParameter.updateDPI(peizDpi);
			return mapping.findForward("updateDPI");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updateDPI");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PeizDPIForm dpiForm = (PeizDPIForm) actionForm;
		dpiForm.clear();
		return mapping.findForward("doDPI");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PeizDPIForm dpiForm = (PeizDPIForm) actionForm;
		PeizDpi peizDpi = new PeizDpi();
		try {
			peizDpi.setSecbz(dpiForm.getSecbz());
			peizDpi.setDiqh(dpiForm.getDiqh());
			peizDpi.setDpi(dpiForm.getDpi());
			peizDpi.setDpiid(dpiForm.getDpiid());
			peizDpi.setTiaoycs(dpiForm.getTiaoycs());
			peizDpi.setYewlx(dpiForm.getYewlx());
			peizDpi.setWangdh(dpiForm.getWangdh());
			interParameter.addDPI(peizDpi);
			return mapping.findForward("addDPI");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"addDPI");
		}
	}
}
