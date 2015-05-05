package com.unitop.sysmgr.action;

/***********************************************************************
 * Module:  KagAction.java
 * Author:  Administrator
 * Purpose: Defines the Class KagAction
 ***********************************************************************/

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Kag;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.form.KagForm;
import com.unitop.sysmgr.service.KagService;

@Controller("/kag")
public class KagAction extends ExDispatchAction {
	@Resource
	private KagService kagService;

	public ActionForward kaglist(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String orgcode = request.getParameter("orgcode");// 查询子机构的卡柜信息
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String orgcode1 = clerk.getOrgcode();
		if (orgcode == null || "".equals(orgcode)) {
			orgcode = orgcode1;
		}
		if(clerk.getOrgcode().equals(orgcode)){//判断所查询的机构和柜员所在机构是否同一个机构
			request.setAttribute("ifSameOrg", "0");
		}else{
			request.setAttribute("ifSameOrg", "1");
		}
		List<Kag> kaglist = kagService.kaglist(orgcode);
		request.setAttribute("kaglist", kaglist);
		return actionMapping.findForward("kag_list");
	}

	public ActionForward getKagOrgs(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String orgcode = clerk.getOrgcode();
		List<Org> orglist = kagService.getKagOrgs(orgcode);
		request.setAttribute("orglist", orglist);
		return actionMapping.findForward("kagorglist");
	}

	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		KagForm kagForm = (KagForm) actionForm;
		kagForm.clear();
		return actionMapping.findForward("kag_add");
	}

	public ActionForward modify(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO: implement
		return null;
	}

	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		KagForm kagForm = (KagForm) actionForm;
		Kag kag = new Kag();
		kag.setCaozgy(clerk.getCode());
		kag.setCengs(kagForm.getCengs());
		kag.setChoutkj(kagForm.getChoutkj());
		kag.setChouts(kagForm.getChouts());
		kag.setJigh(clerk.getOrgcode());
		kag.setKagid(kagForm.getKagid());
		kag.setKagip(kagForm.getKagip());
		kag.setKagmc(kagForm.getKagmc());
		kag.setZongkj(kagForm.getCengs() * kagForm.getChouts()
				* kag.getChoutkj());
		kag.setKagzt("0");// 0：未使用
		try {
			Kag kag1 = kagService.getKagById(kag.getKagid());
			if(kag1!=null){
				return this.showMessageJSP(actionMapping, request, "kag_add", "该卡柜已存在！");
			}
			kagService.save(kag);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "load");
		}
		return actionMapping.findForward("load");
	}

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String kagid = request.getParameter("kagid");
		try {
			int usedcount = kagService.getUsedChoutCount(kagid);
//			if(usedcount>0){
//				this.showMessageJSP(actionMapping, request, "load", "卡柜已使用不允许删除！");
//			}
			kagService.delete(kagid);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "load");
		}
		return actionMapping.findForward("load");
	}

	public ActionForward countKagSpace(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String kagid = request.getParameter("kagid");
			Kag kag = kagService.getKagById(kagid);
			Map<String, String> choutSpace = kagService
					.getChoutSpaceByKagId(kagid);
			List<String> tableheadlist = new ArrayList();
			tableheadlist.add("层\\抽屉");
			for (int i = 0; i < kag.getChouts(); i++) {
				tableheadlist.add("第" + i + "抽屉");
			}
			List<List<String>> cenglist = new ArrayList<List<String>>();
			for (int i = 0; i < kag.getCengs(); i++) {
				List<String> choutlist = new ArrayList<String>();
				choutlist.add("第" + i + "层");
				for (int j = 0; j < kag.getChouts(); j++) {
					String spaceRate = choutSpace.get(kagid + i + j);
					choutlist.add(spaceRate);
				}
				cenglist.add(choutlist);
			}
			request.setAttribute("tableheadlist", tableheadlist);
			request.setAttribute("cenglist", cenglist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionMapping.findForward("kagspace");
	}
}