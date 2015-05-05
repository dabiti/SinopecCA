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
import com.unitop.sysmgr.bo.DanbwhBiaod;
import com.unitop.sysmgr.bo.UnionBiaod;
import com.unitop.sysmgr.form.DanbwhBiaodForm;
import com.unitop.sysmgr.service.DanbwhService;
import com.unitop.sysmgr.service.impl.DanbwhServiceImpl;

@Controller("/danbwhbd")
public class DanbwhBiaodAction extends ExDispatchAction{
	
	@Resource
	private DanbwhService DanbwhService;
	
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			String gongnid = request.getParameter("gongnid");
			try {
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				List list = DanbwhServiceImpl.doDanbwh.getAll(gongnid);
				request.setAttribute("list", list);
				request.setAttribute("gongnid", gongnid);
				return actionMapping.findForward("show");
			} catch (Exception e) {
				return this.errrForLogAndException(e, actionMapping, request,"show");
			}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			String gongnid = request.getParameter("gongnid");
			String zidmc = request.getParameter("zidmc");
			try {
				DanbwhBiaod danbwh =new DanbwhBiaod();
				UnionBiaod biaod = new UnionBiaod();
				biaod.setGongnid(gongnid);
				biaod.setZidmc(zidmc);
				danbwh.setId(biaod);
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				DanbwhServiceImpl.doDanbwh.delete(danbwh.id);
				return mapping.findForward("delete");
			} catch (Exception e) {
				return this.errrForLogAndException(e, mapping, request, "delete");
			}
	}
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			DanbwhBiaodForm danbwhForm = (DanbwhBiaodForm)actionForm;
			String gongnid = request.getParameter("gongnid");
			String zidmc = request.getParameter("zidmc");
			try {
				DanbwhBiaod danb =new DanbwhBiaod();
				UnionBiaod biaod = new UnionBiaod();
				biaod.setGongnid(gongnid);
				biaod.setZidmc(zidmc);
				danb.setId(biaod);
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				DanbwhBiaod danbwh = DanbwhServiceImpl.doDanbwh.find(danb.id);
				danbwhForm.setGongnid(danbwh.id.getGongnid());
				danbwhForm.setZidmc(danbwh.id.getZidmc());
				danbwhForm.setZhansmc(danbwh.getZhansmc());
				danbwhForm.setZidlx(danbwh.getZidlx());
				danbwhForm.setShurlx(danbwh.getShurlx());
				danbwhForm.setMorz(danbwh.getMorz());
				danbwhForm.setQuzfw(danbwh.getQuzfw());
				danbwhForm.setJiaoygz(danbwh.getJiaoygz());
				danbwhForm.setShifbc(danbwh.getShifbc());
				danbwhForm.setShifbj(danbwh.getShifbj());
				danbwhForm.setShifzj(danbwh.getShifzj());
				danbwhForm.setXianssx(danbwh.getXianssx());
				Danbwh danbwhModel = DanbwhService.find(gongnid);
				request.setAttribute("danbwh", danbwhModel);
				return mapping.findForward("find");
			} catch (Exception e) {
				return this.errrForLogAndException(e, mapping, request,
				"find");
			}
	}
	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			DanbwhBiaodForm danbwhForm = (DanbwhBiaodForm)actionForm;
			DanbwhBiaod danbwh = new DanbwhBiaod();
			UnionBiaod biaod = new UnionBiaod();
			String gongnid = danbwhForm.getGongnid();
			String zidmc = danbwhForm.getZidmc();
			try {
				biaod.setGongnid(gongnid);
				biaod.setZidmc(zidmc);
				danbwh.setId(biaod);
				danbwh.setZhansmc(danbwhForm.getZhansmc());
				danbwh.setZidlx(danbwhForm.getZidlx());
				danbwh.setShurlx(danbwhForm.getShurlx());
				danbwh.setMorz(danbwhForm.getMorz());
				danbwh.setQuzfw(danbwhForm.getQuzfw());
				danbwh.setJiaoygz(danbwhForm.getJiaoygz());
				danbwh.setShifbc(danbwhForm.getShifbc());
				danbwh.setShifbj(danbwhForm.getShifbj());
				danbwh.setShifzj(danbwhForm.getShifzj());
				danbwh.setXianssx(danbwhForm.getXianssx());
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				DanbwhServiceImpl.doDanbwh.update(danbwh);
				return mapping.findForward("update");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, mapping, request,"update");
			}
	}
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			String gongnid = request.getParameter("gongnid");
			try {
				Danbwh danbwh = DanbwhService.find(gongnid);
				request.setAttribute("danbwh", danbwh);
			} catch (Exception e) {
				return this.errrForLogAndException(e, mapping, request, "do");
			}
			return mapping.findForward("do");
	}

	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
			DanbwhBiaodForm danbwhForm = (DanbwhBiaodForm)actionForm;
			//静态参数
			String[] shurlxName = request.getParameterValues("shurlxName");
			String[] shurlxValue = request.getParameterValues("shurlxValue");
			String beiz="|";
			if(shurlxName!=null)
			{
				for(int i =0 ; i < shurlxName.length ; i++)
				{
					beiz+=shurlxName[i] +":"+ shurlxValue[i]+"|";
				}
			}
			
			DanbwhBiaod danbwh = new DanbwhBiaod();
			UnionBiaod biaod = new UnionBiaod();
			String gongnid = danbwhForm.getGongnid();
			String zidmc = danbwhForm.getZidmc();
			try {
				biaod.setGongnid(gongnid);
				biaod.setZidmc(zidmc);
				danbwh.setId(biaod);
				danbwh.setZhansmc(danbwhForm.getZhansmc());
				danbwh.setZidlx(danbwhForm.getZidlx());
				danbwh.setShurlx(danbwhForm.getShurlx());
				danbwh.setMorz(danbwhForm.getMorz());
				danbwh.setQuzfw(danbwhForm.getQuzfw());
				danbwh.setJiaoygz(danbwhForm.getJiaoygz());
				danbwh.setShifbc(danbwhForm.getShifbc());
				danbwh.setShifbj(danbwhForm.getShifbj());
				danbwh.setShifzj(danbwhForm.getShifzj());
				danbwh.setXianssx(danbwhForm.getXianssx());
				danbwh.setBeiz(beiz);
				
				DanbwhServiceImpl DanbwhServiceImpl =(DanbwhServiceImpl) DanbwhService;
				DanbwhBiaod DanbwhBiaod = DanbwhServiceImpl.doDanbwh.find(biaod);
				if(DanbwhBiaod != null)
				{
					return this.showMessageJSP(mapping,request,"add","["+zidmc+"]字典要素已经存在!");
				}
				DanbwhServiceImpl.doDanbwh.add(danbwh);
				return mapping.findForward("add");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, mapping, request,"add");
			}
	}
}