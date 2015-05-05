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
import com.unitop.sysmgr.bo.Danbwhgxb;
import com.unitop.sysmgr.form.DanbwhgxbForm;
import com.unitop.sysmgr.service.DanbwhService;
import com.unitop.sysmgr.service.impl.DanbwhServiceImpl;

@Controller("/danbwhGuanxb")
public class DanbwhGuanxbdAction extends ExDispatchAction{
	
	@Resource
	private DanbwhService DanbwhService;
	
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			String gongnid = request.getParameter("gongnid");
			try {
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				List list = DanbwhServiceImpl.danbwhgxbDAO.findDanbwhgxbList(gongnid);
				request.setAttribute("list", list);
				request.setAttribute("gongnid", gongnid);
				return actionMapping.findForward("danbwh_guanxblist");
			} catch (Exception e) {
				return this.errrForLogAndException(e, actionMapping, request,"danbwh_guanxblist");
			}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			String id = request.getParameter("id");
			try {
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				Danbwhgxb Danbwhgxb = DanbwhServiceImpl.danbwhgxbDAO.findById(id);
				DanbwhServiceImpl.danbwhgxbDAO.delete(Danbwhgxb);
				
				List list = DanbwhServiceImpl.danbwhgxbDAO.findDanbwhgxbList(Danbwhgxb.getZhubbh());
				request.setAttribute("list", list);
				request.setAttribute("gongnid",Danbwhgxb.getZhubbh());
				
				return mapping.findForward("danbwh_guanxblist");
			} catch (Exception e) {
				return this.errrForLogAndException(e, mapping, request, "danbwh_guanxblist");
			}
	}
	
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			DanbwhgxbForm  DanbwhgxbForm = (DanbwhgxbForm) actionForm;
			String id = request.getParameter("id");
			try {
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				Danbwhgxb Danbwhgxb = DanbwhServiceImpl.danbwhgxbDAO.findById(id);
				DanbwhgxbForm.setId(Danbwhgxb.getId());
				DanbwhgxbForm.setZhubbh(Danbwhgxb.getZhubbh());
				DanbwhgxbForm.setZhubzd(Danbwhgxb.getZhubzd());
				DanbwhgxbForm.setZibbh(Danbwhgxb.getZibbh());
				DanbwhgxbForm.setZibzd(Danbwhgxb.getZibzd());
				DanbwhgxbForm.setZhibwhmc(Danbwhgxb.getZhibwhmc());
				return mapping.findForward("danbwh_guanxbupdate");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, mapping, request,"danbwh_guanxbupdate");
			}
	}
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			DanbwhgxbForm  DanbwhgxbForm = (DanbwhgxbForm) actionForm;
			try {
				Danbwhgxb Danbwhgxb = new Danbwhgxb();
				Danbwhgxb.setId(DanbwhgxbForm.getId());
				Danbwhgxb.setZhubbh(DanbwhgxbForm.getZhubbh());
				Danbwhgxb.setZhubzd(DanbwhgxbForm.getZhubzd());
				Danbwhgxb.setZibbh(DanbwhgxbForm.getZibbh());
				Danbwhgxb.setZibzd(DanbwhgxbForm.getZibzd());
				Danbwhgxb.setShifljzd(DanbwhgxbForm.getShifljzd());
				Danbwhgxb.setZhibwhmc(DanbwhgxbForm.getZhibwhmc());
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				DanbwhServiceImpl.danbwhgxbDAO.update(Danbwhgxb);
				List list = DanbwhServiceImpl.danbwhgxbDAO.findDanbwhgxbList(DanbwhgxbForm.getZhubbh());
				request.setAttribute("list", list);
				request.setAttribute("gongnid", DanbwhgxbForm.getZhubbh());
				return mapping.findForward("danbwh_guanxblist");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, mapping, request,"update");
			}
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			String gongnid = request.getParameter("gongnid");
			DanbwhgxbForm  DanbwhgxbForm = (DanbwhgxbForm) actionForm;
			try {
				Danbwh danbwh = DanbwhService.find(gongnid);
				request.setAttribute("danbwh", DanbwhgxbForm);
				DanbwhgxbForm.setZhubbh(gongnid);
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, mapping, request, "danbwh_guanxbadd");
			}
			return mapping.findForward("danbwh_guanxbadd");
	}

	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			try {
				DanbwhgxbForm  DanbwhgxbForm = (DanbwhgxbForm) actionForm;
				Danbwhgxb Danbwhgxb = new Danbwhgxb();
				Danbwhgxb.setZhubbh(DanbwhgxbForm.getZhubbh());
				Danbwhgxb.setZhubzd(DanbwhgxbForm.getZhubzd());
				Danbwhgxb.setZibbh(DanbwhgxbForm.getZibbh());
				Danbwhgxb.setZibzd(DanbwhgxbForm.getZibzd());
				Danbwhgxb.setShifljzd(DanbwhgxbForm.getShifljzd());
				Danbwhgxb.setZhibwhmc(DanbwhgxbForm.getZhibwhmc());
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				DanbwhServiceImpl.danbwhgxbDAO.save(Danbwhgxb);
				
				List list = DanbwhServiceImpl.danbwhgxbDAO.findDanbwhgxbList(DanbwhgxbForm.getZhubbh());
				request.setAttribute("list", list);
				request.setAttribute("gongnid", DanbwhgxbForm.getZhubbh());
				
				return mapping.findForward("danbwh_guanxblist");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, mapping, request,"danbwh_guanxblist");
			}
	}
}