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
import com.unitop.sysmgr.bo.sys.Ci_xitcs;
import com.unitop.sysmgr.bo.sys.UnionXitcs;
import com.unitop.sysmgr.dao.InterFaceParameter;
import com.unitop.sysmgr.form.sys.XitcsForm;

@Controller("/xitcs")
public class XitcsAction extends ExDispatchAction{
	
	@Resource
	private InterFaceParameter interParameter;
	
	public ActionForward select(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = interParameter.findAllXitcs();
			request.setAttribute("listXitcs", list);
			return mapping.findForward("showxitcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"showxitcs");
		}
	}
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String xitbs = request.getParameter("xitbs");
		String cansbs = request.getParameter("cansbs");
		try {
			interParameter.deleteXitcs(xitbs, cansbs);
			return mapping.findForward("deletexitcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deletexitcs");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitcsForm xitcsForm = (XitcsForm)actionForm;
		String xitbs = request.getParameter("xitbs");
		String cansbs = request.getParameter("cansbs");
		
		try {
			Ci_xitcs xitcs = interParameter.findXitcs(xitbs, cansbs);
			xitcsForm.setXitbs(xitcs.getUnionXitcs().getXitbs());
			xitcsForm.setCansbs(xitcs.getUnionXitcs().getCansbs());
			xitcsForm.setCansfl(xitcs.getCansfl());
			xitcsForm.setCanslx(xitcs.getCanslx());
			xitcsForm.setCansz(xitcs.getCansz());
			xitcsForm.setCansmc(xitcs.getCansmc());
			xitcsForm.setCanssm(xitcs.getCanssm());
			return mapping.findForward("findxitcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"findxitcs");
		}
	}
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitcsForm xitcsForm = (XitcsForm)actionForm;
		Ci_xitcs xitcs = new Ci_xitcs();
		String xitbs = xitcsForm.getXitbs();
		String cansbs = xitcsForm.getCansbs();
		UnionXitcs unionXitcs = new UnionXitcs();
		unionXitcs.setCansbs(cansbs);
		unionXitcs.setXitbs(xitbs);
		try {
			xitcs.setUnionXitcs(unionXitcs);
			xitcs.setCansfl(xitcsForm.getCansfl());
			xitcs.setCanslx(xitcsForm.getCanslx());
			xitcs.setCansmc(xitcsForm.getCansmc());
			xitcs.setCansz(xitcsForm.getCansz());
			xitcs.setCanssm(xitcsForm.getCanssm());
			interParameter.updateXitcs(xitcs);
			return mapping.findForward("updatexitcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updatexitcs");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitcsForm xitcsForm = (XitcsForm)actionForm;
		xitcsForm.clear();
		return mapping.findForward("doxitcs");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		XitcsForm xitcsForm = (XitcsForm)actionForm;
		Ci_xitcs xitcs = new Ci_xitcs();
		String xitbs = xitcsForm.getXitbs();
		String cansbs = xitcsForm.getCansbs();
		UnionXitcs unionXitcs = new UnionXitcs();
		unionXitcs.setCansbs(cansbs);
		unionXitcs.setXitbs(xitbs);
		try {
			xitcs.setUnionXitcs(unionXitcs);
			xitcs.setCansfl(xitcsForm.getCansfl());
			xitcs.setCanslx(xitcsForm.getCanslx());
			xitcs.setCansmc(xitcsForm.getCansmc());
			xitcs.setCansz(xitcsForm.getCansz());
			xitcs.setCanssm(xitcsForm.getCanssm());
			interParameter.addXitcs(xitcs);
			return mapping.findForward("addxitcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"addxitcs");
		}
	}
}
