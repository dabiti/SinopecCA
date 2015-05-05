package com.unitop.sysmgr.action;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Yewgz;
import com.unitop.sysmgr.form.YewgzForm;
import com.unitop.sysmgr.service.BusinessService;
import com.unitop.sysmgr.service.YewgzService;
@Controller("/yewgzConfig")
public class YewgzConfigAction extends ExDispatchAction{
	@Resource 
	private YewgzService yewgzService;
	@Resource
	private BusinessService businessService;
	
	/**
	 * ��ѯ
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			List<Yewgz> list = yewgzService.findAllYewgz();
			request.setAttribute("list", list);
			return actionMapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "list");
		}
	}
	/**
	 * ��ת�����ҳ��
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String yuansid = request.getParameter("yuansid");
			Yewgz yewgz = yewgzService.findYewgz(yuansid);
			request.setAttribute("yewgz", yewgz);
			return actionMapping.findForward("add");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "add");
		}
	}
	
	/**
	 * ����
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		YewgzForm yewgzForm = (YewgzForm)actionForm;
		Yewgz yewgzExist =yewgzService.findYewgz(yewgzForm.getYuansid());
		if(yewgzExist!=null){
			return this.showMessageJSP(actionMapping,request,"add","��ҵ�������Ϣ�Ѿ����ڣ�");
		}
		Yewgz yewgz = new Yewgz();
		yewgz.setYuansid(yewgzForm.getYuansid());
		yewgz.setYuansname(yewgzForm.getYuansname());
		yewgz.setYuansstyle(yewgzForm.getYuansstyle());
		yewgz.setMaxlength(yewgzForm.getMaxlength());
		yewgz.setValidaterule(yewgzForm.getValidaterule());
		yewgz.setStyleClass(yewgzForm.getStyleClass());
		yewgz.setMsgid(yewgzForm.getMsgid());
		yewgz.setIsreadonly(yewgzForm.getIsreadonly());
		yewgz.setRemark(yewgzForm.getRemark());
		yewgzService.insertYewgz(yewgz);
		return actionMapping.findForward("load");
	}
	/**
	 * ɾ��
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		YewgzForm yewgzForm = (YewgzForm)actionForm;
		yewgzService.deleteYewgz(yewgzForm.getYuansid());
		return actionMapping.findForward("load");
	}
	/**
	 * ��ת���޸Ľ���
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward modify(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String yuansid = request.getParameter("yuansid");
		String type = request.getParameter("type");
		Yewgz yewgz = yewgzService.findYewgz(yuansid);
		if(yewgz==null){
			yewgz= new Yewgz();
			yewgz.setYuansid(yuansid);
		}
		request.setAttribute("type", type);
		request.setAttribute("yewgz", yewgz);
		return actionMapping.findForward("modify");
	}
	/**
	 * ����
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		YewgzForm yewgzForm = (YewgzForm)actionForm;
		String type = request.getParameter("type");
		Yewgz yewgz = new Yewgz();
		yewgz.setYuansid(yewgzForm.getYuansid());
		yewgz.setYuansname(yewgzForm.getYuansname());
		yewgz.setYuansstyle(yewgzForm.getYuansstyle());
		yewgz.setMaxlength(yewgzForm.getMaxlength());
		yewgz.setValidaterule(yewgzForm.getValidaterule());
		yewgz.setStyleClass(yewgzForm.getStyleClass());
		yewgz.setMsgid(yewgzForm.getMsgid());
		yewgz.setIsreadonly(yewgzForm.getIsreadonly());
		yewgz.setRemark(yewgzForm.getRemark());
		yewgzService.updateYewgz(yewgz);
		if("ureport".equals(type)){
			List<Yewgz> list = new ArrayList<Yewgz>();
			list.add(yewgz);
			request.setAttribute("list", list);
			return actionMapping.findForward("yewgzlist");
		}
		return actionMapping.findForward("load");
	}
	/**
	 * ͬ��ҵ�������Ϣ
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sync(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		businessService.getYewgzRules();
		return actionMapping.findForward("load");
	}
}
