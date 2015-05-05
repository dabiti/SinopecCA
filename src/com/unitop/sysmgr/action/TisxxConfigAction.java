package com.unitop.sysmgr.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Tisxx;
import com.unitop.sysmgr.form.TisxxForm;
import com.unitop.sysmgr.service.PromptService;
import com.unitop.sysmgr.service.TisxxService;

@Controller("/tisxxConfig")
public class TisxxConfigAction extends ExDispatchAction{
	@Resource 
	private TisxxService tisxxService;
	@Resource
	private PromptService promptService;
	
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		List<Tisxx> list = tisxxService.findAllTisxx();
		request.setAttribute("list", list);
		return actionMapping.findForward("list");
	}
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String msgId = request.getParameter("msgId");
		Tisxx tisxx = tisxxService.findTisxx(msgId);
		request.setAttribute("tisxx", tisxx);
		return actionMapping.findForward("add");
	}
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TisxxForm tisxxForm = (TisxxForm)actionForm;
		Tisxx tisxxExist = tisxxService.findTisxx(tisxxForm.getMsgId());
		if(tisxxExist!=null){
			return this.showMessageJSP(actionMapping,request,"add","该提示信息已经存在！");
		}
		Tisxx tisxx = new Tisxx();
		tisxx.setMsgId(tisxxForm.getMsgId());
		tisxx.setMsgContent(tisxxForm.getMsgContent());
		tisxx.setFunctionArea(tisxxForm.getFunctionArea());
		tisxx.setFunctionPoint(tisxxForm.getFunctionPoint());
		tisxx.setRemark(tisxxForm.getRemark());
		tisxxService.insertTisxx(tisxx);
		return actionMapping.findForward("load");
	}
	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TisxxForm tisxxForm = (TisxxForm)actionForm;
		tisxxService.deleteTisxx(tisxxForm.getMsgId());
		return actionMapping.findForward("load");
	}
	public ActionForward modify(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String msgId = request.getParameter("msgId");
		Tisxx tisxx = tisxxService.findTisxx(msgId);
		if(tisxx==null){
			tisxx= new Tisxx();
			tisxx.setMsgId(msgId);
		}
		request.setAttribute("tisxx", tisxx);
		return actionMapping.findForward("modify");
	}
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TisxxForm tisxxForm = (TisxxForm)actionForm;
		Tisxx tisxx = new Tisxx();
		tisxx.setMsgId(tisxxForm.getMsgId());
		tisxx.setMsgContent(tisxxForm.getMsgContent());
		tisxx.setFunctionArea(tisxxForm.getFunctionArea());
		tisxx.setFunctionPoint(tisxxForm.getFunctionPoint());
		tisxx.setRemark(tisxxForm.getRemark());
		tisxxService.updateTisxx(tisxx);
		return actionMapping.findForward("load");
	}
	public ActionForward sync(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		promptService.getTisxxMap();
		return actionMapping.findForward("load");
	}
}
