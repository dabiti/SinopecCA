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
import com.unitop.sysmgr.bo.sys.Ci_pingzcs; 
import com.unitop.sysmgr.bo.sys.UnionPingzcs;
import com.unitop.sysmgr.dao.InterFaceParameter;
import com.unitop.sysmgr.form.sys.PingzcsForm;

@Controller("/pingzcs")
public class PingzcsAction extends ExDispatchAction {
	
	@Resource
	InterFaceParameter InterFaceParameter;
	
	public ActionForward select(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//System.out.println(InterFaceParameter);
			List list = InterFaceParameter.findAllPingzcs();
			request.setAttribute("listPingzcs", list);
			return mapping.findForward("showpingzcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"showpingzcs");
		}
	}
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String xitbs = request.getParameter("xitbs");
		String pingzbs = request.getParameter("pingzbs");
		try {
			InterFaceParameter.deletePingzcs(xitbs, pingzbs);
			return mapping.findForward("deletepingzcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"deletepingzcs");
		}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PingzcsForm pingzcsForm = (PingzcsForm)actionForm;
		String xitbs = request.getParameter("xitbs");
		String pingzbs = request.getParameter("pingzbs");
		
		try {
			Ci_pingzcs pingzcs = InterFaceParameter.findPingzcs(xitbs, pingzbs);
			pingzcsForm.setFazq(pingzcs.getFazq());
			pingzcsForm.setPingzbs(pingzcs.getUnionPingzcs().getPingzbs());
			pingzcsForm.setXitbs(pingzcs.getUnionPingzcs().getXitbs());
			pingzcsForm.setPingzmc(pingzcs.getPingzmc());
			pingzcsForm.setXfenbl(pingzcs.getXfenbl());
			pingzcsForm.setXsuof(pingzcs.getXsuof());
			pingzcsForm.setYanyjb(pingzcs.getYanyjb());
			pingzcsForm.setYfenbl(pingzcs.getYfenbl());
			pingzcsForm.setYsuof(pingzcs.getYsuof());
			pingzcsForm.setZhaozq(pingzcs.getZhaozq());
			return mapping.findForward("findpingzcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"findpingzcs");
		}
	}
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PingzcsForm pingzcsForm = (PingzcsForm)actionForm;
		Ci_pingzcs pingzcs = new Ci_pingzcs();
		String xitbs = pingzcsForm.getXitbs();
		String pingzbs = pingzcsForm.getPingzbs();
		UnionPingzcs unionPingzcs = new UnionPingzcs();
		unionPingzcs.setPingzbs(pingzbs);
		unionPingzcs.setXitbs(xitbs);
		try {
			pingzcs.setUnionPingzcs(unionPingzcs);
			pingzcs.setFazq(pingzcsForm.getFazq());
			pingzcs.setPingzmc(pingzcsForm.getPingzmc());
			pingzcs.setXfenbl(pingzcsForm.getXfenbl());
			pingzcs.setXsuof(pingzcsForm.getXsuof());
			pingzcs.setYanyjb(pingzcsForm.getYanyjb());
			pingzcs.setYfenbl(pingzcsForm.getYfenbl());
			pingzcs.setYsuof(pingzcsForm.getYsuof());
			pingzcs.setZhaozq(pingzcsForm.getZhaozq());
			
			InterFaceParameter.updatePingzcs(pingzcs);
			return mapping.findForward("updatepingzcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"updatepingzcs");
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PingzcsForm pingzcsForm = (PingzcsForm)actionForm;
		pingzcsForm.clear();
		return mapping.findForward("dopingzcs");
	}
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		PingzcsForm pingzcsForm = (PingzcsForm)actionForm;
		Ci_pingzcs pingzcs = new Ci_pingzcs();
		String xitbs = pingzcsForm.getXitbs();
		String pingzbs = pingzcsForm.getPingzbs();
		UnionPingzcs unionPingzcs = new UnionPingzcs();
		unionPingzcs.setPingzbs(pingzbs);
		unionPingzcs.setXitbs(xitbs);
		try {
			pingzcs.setUnionPingzcs(unionPingzcs);
			pingzcs.setFazq(pingzcsForm.getFazq());
			pingzcs.setPingzmc(pingzcsForm.getPingzmc());
			pingzcs.setXfenbl(pingzcsForm.getXfenbl());
			pingzcs.setXsuof(pingzcsForm.getXsuof());
			pingzcs.setYanyjb(pingzcsForm.getYanyjb());
			pingzcs.setYfenbl(pingzcsForm.getYfenbl());
			pingzcs.setYsuof(pingzcsForm.getYsuof());
			pingzcs.setZhaozq(pingzcsForm.getZhaozq());
			InterFaceParameter.addPingzcs(pingzcs);
			return mapping.findForward("addpingzcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
			"addpingzcs");
		}
	}
}
