package com.unitop.sysmgr.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Danbwh;
import com.unitop.sysmgr.form.DanbwhForm;
import com.unitop.sysmgr.service.DanbwhService;

@Controller("/danbwh")
public class DanbwhAction extends ExDispatchAction{
	
	@Resource
	private DanbwhService DanbwhService;
	
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List list = DanbwhService.getAll();
			request.setAttribute("list", list);
			return actionMapping.findForward("showdanbwh");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"showdanbwh");
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String gongnid = request.getParameter("gongnid");
		try {
			DanbwhService.delete(gongnid);
			return mapping.findForward("deletedanbwh");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deletedanbwh");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		DanbwhForm danbwhForm = (DanbwhForm)actionForm;
		String gongnid = request.getParameter("gongnid");
		
		try {
			Danbwh danbwh = DanbwhService.find(gongnid);
			danbwhForm.setGongnid(danbwh.getGongnid());
			danbwhForm.setGongnmc(danbwh.getGongnmc());
			danbwhForm.setGongnurl(danbwh.getGongnurl());
			danbwhForm.setWeihbm(danbwh.getWeihbm());
			danbwhForm.setShifbc(danbwh.getShifbc());
			danbwhForm.setShifbj(danbwh.getShifbj());
			danbwhForm.setShifsc(danbwh.getShifsc());
			danbwhForm.setShifzb(danbwh.getShifzb());
			danbwhForm.setZhidyl(danbwh.getZhidyl());
			return mapping.findForward("finddanbwh");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"finddanbwh");
		}
	}
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		DanbwhForm danbwhForm = (DanbwhForm)actionForm;
		Danbwh danbwh = new Danbwh();
		String gongnid = danbwhForm.getGongnid();
		try {
			danbwh.setGongnid(gongnid);
			danbwh.setGongnmc(danbwhForm.getGongnmc());
			danbwh.setGongnurl(danbwhForm.getGongnurl());
			danbwh.setWeihbm(danbwhForm.getWeihbm());
			danbwh.setShifbc(danbwhForm.getShifbc());
			danbwh.setShifsc(danbwhForm.getShifsc());
			danbwh.setShifbj(danbwhForm.getShifbj());
			danbwh.setShifzb(danbwhForm.getShifzb().trim());
			danbwh.setZhidyl(danbwhForm.getZhidyl().trim());
			DanbwhService.update(danbwh);
			return mapping.findForward("updatedanbwh");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updatedanbwh");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		DanbwhForm danbwhForm = (DanbwhForm)actionForm;
		danbwhForm.clear();
		return mapping.findForward("dodanbwh");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		DanbwhForm danbwhForm = (DanbwhForm)actionForm;
		Danbwh danbwh = new Danbwh();
		String gongnid = danbwhForm.getGongnid();
		try {
			danbwh.setGongnid(gongnid);
			danbwh.setGongnmc(danbwhForm.getGongnmc());
			danbwh.setGongnurl(danbwhForm.getGongnurl());
			danbwh.setWeihbm(danbwhForm.getWeihbm());
			danbwh.setShifbc(danbwhForm.getShifbc().trim());
			danbwh.setShifsc(danbwhForm.getShifsc().trim());
			danbwh.setShifbj(danbwhForm.getShifbj().trim());
			danbwh.setShifzb(danbwhForm.getShifzb().trim());
			danbwh.setZhidyl(danbwhForm.getZhidyl().trim());
			DanbwhService.add(danbwh);
			return mapping.findForward("adddanbwh");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,"adddanbwh");
		}
	}
}