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
import com.unitop.sysmgr.bo.sys.Ci_jiedcs;
import com.unitop.sysmgr.bo.sys.UnionJiedcs;
import com.unitop.sysmgr.dao.InterFaceParameter;
import com.unitop.sysmgr.form.sys.JiedcsForm;

@Controller("/jiedcs")
public class JiedcsAction extends ExDispatchAction {
	@Resource
	private InterFaceParameter interParameter;
	
	public ActionForward select(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = interParameter.findAllJiedcs();
			request.setAttribute("listJiedcs", list);
			return mapping.findForward("showjiedcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"showjiedcs");
		}
	}
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String jiedbs = request.getParameter("jiedbs");
		String cansbs = request.getParameter("cansbs");
		try {
			interParameter.deleteJiedcs(jiedbs, cansbs);
			return mapping.findForward("deletejiedcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deletejiedcs");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		JiedcsForm jiedcsForm = (JiedcsForm)actionForm;
		String jiedbs = request.getParameter("jiedbs");
		String cansbs = request.getParameter("cansbs");
		
		try {
			Ci_jiedcs jiedcs = interParameter.findJiedcs(jiedbs,cansbs);
			jiedcsForm.setJiedbs(jiedcs.getUnionJiedcs().getJiedbs());
			jiedcsForm.setCansbs(jiedcs.getUnionJiedcs().getCansbs());
			jiedcsForm.setCansz(jiedcs.getCansz());
			jiedcsForm.setCanslx(jiedcs.getCanslx());
			jiedcsForm.setCanssm(jiedcs.getCanssm());
			jiedcsForm.setCansmc(jiedcs.getCansmc());
			jiedcsForm.setCansfl(jiedcs.getCansfl());
			return mapping.findForward("findjiedcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"findjiedcs");
		}
	}
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		JiedcsForm jiedcsForm = (JiedcsForm) actionForm;
		Ci_jiedcs jiedcs = new Ci_jiedcs();
		String jiedbs = jiedcsForm.getJiedbs();
		String cansbs = jiedcsForm.getCansbs();
		UnionJiedcs unionJiedcs = new UnionJiedcs();
		unionJiedcs.setCansbs(cansbs);
		unionJiedcs.setJiedbs(jiedbs);
		try {
			jiedcs.setUnionJiedcs(unionJiedcs);
			jiedcs.setCansz(jiedcsForm.getCansz());
			jiedcs.setCanslx(jiedcsForm.getCanslx());
			jiedcs.setCanssm(jiedcsForm.getCanssm());
			jiedcs.setCansmc(jiedcsForm.getCansmc());
			jiedcs.setCansfl(jiedcsForm.getCansfl());
			interParameter.updateJiedcs(jiedcs);
			return mapping.findForward("updatejiedcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updatejiedcs");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		JiedcsForm jiedcsForm = (JiedcsForm) actionForm;
		jiedcsForm.clear();
		return mapping.findForward("dojiedcs");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		JiedcsForm jiedcsForm = (JiedcsForm) actionForm;
		Ci_jiedcs jiedcs = new Ci_jiedcs();
		String jiedbs = jiedcsForm.getJiedbs();
		String cansbs = jiedcsForm.getCansbs();
		UnionJiedcs unionJiedcs = new UnionJiedcs();
		unionJiedcs.setCansbs(cansbs);
		unionJiedcs.setJiedbs(jiedbs);
		try {
			jiedcs.setUnionJiedcs(unionJiedcs);
			jiedcs.setCansz(jiedcsForm.getCansz());
			jiedcs.setCanslx(jiedcsForm.getCanslx());
			jiedcs.setCanssm(jiedcsForm.getCanssm());
			jiedcs.setCansmc(jiedcsForm.getCansmc());
			jiedcs.setCansfl(jiedcsForm.getCansfl());
			interParameter.addJiedcs(jiedcs);
			return mapping.findForward("addjiedcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"addjiedcs");
		}
	}
}
