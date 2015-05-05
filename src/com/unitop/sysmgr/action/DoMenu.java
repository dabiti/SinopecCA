package com.unitop.sysmgr.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;

import com.unitop.framework.util.Encoding;
import com.unitop.framework.util.JsonTool;
import com.unitop.framework.util.PasswordUtil;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Menu;
import com.unitop.sysmgr.bo.Privilege;
import com.unitop.sysmgr.form.MenuForm;
import com.unitop.sysmgr.service.DoMenuService;
import com.unitop.sysmgr.service.PrivilegeService;
@Controller("/doMenu")
public class DoMenu extends ExDispatchAction {

	@Resource
	private DoMenuService doMenuService;
	@Resource
	private PrivilegeService privilegeService;
	
	@SuppressWarnings("unchecked")
	public ActionForward loadtree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
				String code = request.getParameter("shangjgn");
				String zignid = request.getParameter("zignid");
		try {
				List list = doMenuService.getMenuForShangjgn(code);
				Element rootElement = new Element("tree");
				Document myDocument = new Document(rootElement);
				for (int i = 0; i < list.size(); i++) {
					Menu bo = (Menu) list.get(i);
					if (!code.equals(bo.getGongnid())){
						Element tree = new Element("tree");
						tree.setAttribute("text", bo.getGongnmc());
						tree.setAttribute("action", "../doMenu.do?method=load&shangjgn="+ bo.getGongnid());
						tree.setAttribute("target", "mainF");
						tree.setAttribute("src", "../doMenu.do?method=loadtree&shangjgn=" + bo.getGongnid()+ "&randomid=" + new Random().nextInt(1000000));
						rootElement.addContent(tree);
					}
				}
				XMLOutputter outputter = new XMLOutputter("  ", true, "GBK");
				response.setContentType("text/xml");
				response.setLocale(Locale.SIMPLIFIED_CHINESE);
				response.setCharacterEncoding("GBK");
				outputter.output(myDocument, response.getWriter());
			return null;
		} catch (IOException e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}

	public ActionForward load(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String gongnid = null;
		String zignid = null;
	
		if(request.getParameter("shangjgn")!=null){
			gongnid= request.getParameter("shangjgn");
			zignid = request.getParameter("sjzignid");
		}else if(request.getAttribute("shangjgn")!=null){
			gongnid= request.getAttribute("shangjgn").toString();
		    zignid = request.getAttribute("sjzignid").toString();
		}
		try {
			gongnid = gongnid==null?"1":gongnid;
			zignid = zignid ==null?"0":zignid;
			Menu menu = doMenuService.getMenuForId(gongnid,zignid);
			List list = doMenuService.getMenuForShangjgn(gongnid);
			request.setAttribute("list", list);
			request.getSession().setAttribute("shangjmc",menu.getGongnmc());
			request.setAttribute("shangjgn", gongnid);
			request.setAttribute("rootcode", gongnid);
			return actionMapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"list");
		}
	}

	//修改功能信息
	public ActionForward doSave(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		MenuForm menuForm = (MenuForm) actionForm;
		try {
			Menu menu = new Menu();
			menu.setGongnid(menuForm.getGongnid());
			menu.setZignid(menuForm.getZignid());
			menu.setGongnmc(menuForm.getGongnmc());
			menu.setGongnsx(menuForm.getGongnsx());
			menu.setGongnurl(menuForm.getGongnurl());
			menu.setQuanxwz(menuForm.getQuanxwz());
			Menu shangjmenu = doMenuService.getMenuForId(menuForm.getShangjgn(),"0");
			if(shangjmenu==null){
				return this.showMessageJSP(mapping,request,"find","上级功能不存在，请重新添加上级功能！");
			}
			menu.setShangjgn(menuForm.getShangjgn());//如果上级功能不存在的话，将会出现添加的菜单不在菜单树上，需要限制
			menu.setGongnlx(menuForm.getGongnlx());
			menu.setZhuangt(menuForm.getZhuangt());
			menu.setBeiz(menuForm.getBeiz());		
			doMenuService.updateMenu(menu);
			return this.showMessageJSP(mapping,request,"load","修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request, "load");
		}
	}
	
	//跳转到修改页面
	public ActionForward find(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			String gongnid = request.getParameter("gongnid");
			String zignid = request.getParameter("zignid");
		try {
			Menu menu = doMenuService.getMenuForId(gongnid,zignid);
			MenuForm menuForm = (MenuForm) actionForm;
			menuForm.setGongnid(menu.getGongnid());
			menuForm.setZignid(menu.getZignid()); 
			menuForm.setGongnmc(menu.getGongnmc());
			menuForm.setGongnsx(menu.getGongnsx());
			menuForm.setGongnurl(menu.getGongnurl());
			menuForm.setQuanxwz(menu.getQuanxwz());
			menuForm.setShangjgn(menu.getShangjgn());
			menuForm.setZhuangt(menu.getZhuangt());
			menuForm.setBeiz(menu.getBeiz());
			return actionMapping.findForward("find");
		} catch (Exception e) {
			return this.errrForLogAndException(e,actionMapping,request,"find");
		}
	}
	
	//添加按钮，在菜单功能列表看到。
	public ActionForward add(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MenuForm menuForm = (MenuForm) actionForm;
		String shangjgn = request.getParameter("shangjgn");
		List list = doMenuService.getCaidlx();
		request.getSession().setAttribute("group", list);
		request.getSession().setAttribute("shangjgn", shangjgn);
		return mapping.findForward("do");
	}
	
	//添加，从基础功能菜单读数据。
	public ActionForward doAdd(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		MenuForm menuForm = (MenuForm) actionForm;
		String gongnid = request.getParameter("gongnid");
		String zignid = request.getParameter("zignid");
		String shangjgn = request.getParameter("shangjgn");
		try {
			Menu menu = doMenuService.getMenuForId(gongnid, zignid);

			if(menu!=null){
				return this.showMessageJSP(mapping,request,"load","添加失败:功能菜单已存在！");
			}
			doMenuService.saveMenu(gongnid,zignid,shangjgn);
			return this.showMessageJSP(mapping,request,"load","添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request, "load");
		}
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		MenuForm menuForm = (MenuForm) actionForm;
		String shangjgn = request.getParameter("shangjgn");
		request.getSession().setAttribute("shangjgn", shangjgn);
		return mapping.findForward("create");
	}
	
	//新建页面中的保存功能，保存新建的菜单。
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		MenuForm menuForm = (MenuForm) actionForm;
		Menu menu1 = null;
		try {
			menu1 = doMenuService.getMenuForId(menuForm.getGongnid(), menuForm.getZignid());
			if(menu1!=null){
				return this.showMessageJSP(mapping,request,"create","新建失败：功能菜单已存在！");
			}
			Menu menu = new Menu();
			menu.setGongnid(menuForm.getGongnid());
			menu.setZignid(menuForm.getZignid());
			menu.setGongnmc(menuForm.getGongnmc());
			menu.setGongnsx(menuForm.getGongnsx());
			menu.setGongnurl(menuForm.getGongnurl());
			menu.setQuanxwz("0");
			menu.setShangjgn(menuForm.getShangjgn());
			menu.setGongnlx(menuForm.getGongnlx());
			menu.setZhuangt(menuForm.getZhuangt()!=null?menuForm.getZhuangt():"1");
			menu.setBeiz(menuForm.getBeiz()!=null?menuForm.getBeiz():"");		
			doMenuService.save(menu);
			return this.showMessageJSP(mapping,request,"load","新建成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request, "load");
		}
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String gongnid = request.getParameter("gongnid");
		String zignid2 = request.getParameter("zignid");
		String shangjgn = request.getParameter("shangjgn");
		try {	
			List menu = doMenuService.getMenuForShangjgn(gongnid);
			if(0!=menu.size()&&zignid2.equals("0")){
				return this.showMessageJSP(mapping,request,"load","删除失败:请先删除子菜单！");
			}
			doMenuService.delete(gongnid,zignid2);
			request.setAttribute("shangjgn", shangjgn);
			return this.showMessageJSP(mapping,request,"load","删除成功！");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "load");
		}
	}
	
	//查询
	public ActionForward select(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			String gongnlx = "";
			//Tomcat服务器需要转码
			if(request.getCharacterEncoding().equals("GB18030")){
				gongnlx = Encoding.getGBKCoding(request.getParameter("gongnlx"));
			}else{
				//Websphere服务器不需要转码
				gongnlx = request.getParameter("gongnlx");
			}		
		try {
			List list = doMenuService.getMenuForName(gongnlx);
			request.setAttribute("list", list);
			return actionMapping.findForward("do");
		} catch (Exception e) {
			return this.errrForLogAndException(e,actionMapping,request,"do");
		}
	} 
	
	// 获取菜单树
	public ActionForward doShow(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk =(Clerk) request.getSession().getAttribute("clerk");
		try {
			List list = doMenuService.selectForMenu(clerk);
			request.setAttribute("list", list);
			return actionMapping.findForward("doshow");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "doshow");
		}
	}
	
	/**
	 * 添加和修改的跳转
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOrModify(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List jiclist = doMenuService.getJicFunctions();
		String jicStr = JsonTool.toJsonForArry(jiclist);
		
		List chanplist = privilegeService.getChanpcd();
		String chanpStr = JsonTool.toJsonForArry(chanplist);
//		System.out.println(chanpStr);
		request.setAttribute("jicStr", jicStr);
		request.setAttribute("chanpStr", chanpStr);
		return mapping.findForward("add");
	}
	
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonString = request.getParameter("jsonString");
		List privileList = JsonTool.toSringForList(jsonString,Privilege.class);
		doMenuService.saveOrUpdate(privileList);
		return mapping.findForward("load");
	}
}
