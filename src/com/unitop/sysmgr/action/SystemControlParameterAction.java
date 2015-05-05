package com.unitop.sysmgr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.SystemControlParameter;
import com.unitop.sysmgr.form.SystemControlForm;
import com.unitop.sysmgr.action.ExDispatchAction;
@Controller("/systemControlParameter")
public class SystemControlParameterAction extends ExDispatchAction {
	public ActionForward select(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = getQueryService().findAllSystemControlParameters();
			request.setAttribute("listParamenter", list);
			return mapping.findForward("showParameters");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"showParameters");
		}
	}
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			getQueryService().deleteSystemControlParameter(id);
			return mapping.findForward("deleteParameters");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deleteParameters");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		SystemControlForm controlForm = (SystemControlForm) actionForm;
		String id = request.getParameter("id");
		try {
			SystemControlParameter controlParameter = getQueryService().findSystemControlParameterById(id);
			controlForm.setId(controlParameter.getId());
			controlForm.setName(controlParameter.getName());
			controlForm.setType(controlParameter.getType());
			controlForm.setValue(controlParameter.getValue());
			return mapping.findForward("findParameters");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"findParameters");
		}
	}
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		SystemControlForm controlForm = (SystemControlForm) actionForm;
		SystemControlParameter controlParameter = new SystemControlParameter();
		try {
			controlParameter.setId(controlForm.getId());
			controlParameter.setName(controlForm.getName());
			controlParameter.setType(controlForm.getType());
			controlParameter.setValue(controlForm.getValue());
			getQueryService().updateSystemControlParameter(controlParameter);
			return mapping.findForward("updateParameters");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updateParameters");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		SystemControlForm controlForm = (SystemControlForm) actionForm;
		controlForm.clear();
		return mapping.findForward("doParameters");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		SystemControlForm controlForm = (SystemControlForm) actionForm;
		SystemControlParameter controlParameter = new SystemControlParameter();
		try {
			controlParameter.setId(controlForm.getId());
			controlParameter.setName(controlForm.getName());
			controlParameter.setType(controlForm.getType());
			controlParameter.setValue(controlForm.getValue());
			getQueryService().addSystemControlParameter(controlParameter);
			return mapping.findForward("addParameters");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"doParameters");
		}
	}
}
