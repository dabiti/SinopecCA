package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import org.hibernate.mapping.Array;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.framework.util.ExpOrImp;
import com.unitop.sysmgr.bo.CacheConfig;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Zhipyxxx;
import com.unitop.sysmgr.bo.AsynSealCheckConfig;
import com.unitop.sysmgr.form.OrgForm;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.PromptService;
import com.unitop.sysmgr.action.ExDispatchAction;

@Controller("/orgManage")
public class OrgManageAction extends ExDispatchAction {

	@Resource
	private OrgService OrgService;

	List<String> list = new ArrayList<String>();

	public ActionForward loadtree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("parentcode");
		try {
			List list = OrgService.getOrgChildrenByCode(code);
			Element rootElement = new Element("tree");
			Document myDocument = new Document(rootElement);
			for (int i = 0; i < list.size(); i++) {
				Org bo = (Org) list.get(i);
				Element tree = new Element("tree");
				tree.setAttribute("text", bo.getName());
//				tree.setAttribute("action",
//						"../accountlog.do?method=forqueryAccountTongbrz&parentcode="
//								+ bo.getCode());
				tree.setAttribute("action",
				"../checkaccount.do?method=forQueryAccounting&terminal_id="
						+ bo.getTerminal_id());
				tree.setAttribute("target", "mainF");
				tree.setAttribute("src",
						"../orgManage.do?method=loadtree&parentcode="
								+ bo.getCode() + "&randomid="
								+ new Random().nextInt(1000000));
				rootElement.addContent(tree);
			}
			XMLOutputter outputter = new XMLOutputter("  ", true, "GBK");
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("GBK");
			outputter.output(myDocument, response.getWriter());
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}

	public ActionForward load(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = null;
		if (request.getParameter("parentcode") != null)
			code = request.getParameter("parentcode");
		else if (request.getAttribute("parentcode") != null)
			code = request.getAttribute("parentcode").toString();
		try {
			Org org = OrgService.getOrgByCode(code);

			// 获取默认总行机构号
			String rootCode = SystemConfig.getInstance().getRootCode();
			// 获取机构管理模式:1:集中管理;2:分行管理
			String org_guanlms = SystemConfig.getInstance().getValue(
					"org_guanlms");
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String pcode = org.getCode();
			if (pcode == null)
				pcode = "0";
			String wdflag = org.getWdflag();
			if (wdflag == null)
				wdflag = "0";
			// 判断是否显示机构管理"添加"、"导入"、"导出"、"修改"、"添加"等按钮
			int lastWdflag = OrgService.getLastWdflag();

			if (clerk.getOrgcode().equals(rootCode)
					&& (!pcode.equals(rootCode))
					|| (Integer.parseInt(wdflag) >= lastWdflag)) {
				if ("1".equals(org_guanlms))
					request.setAttribute("guanlms", "1");
				else
					request.setAttribute("guanlms", "0");
			} else {
				request.setAttribute("guanlms", "1");
			}
			request.setAttribute("lastWdflag", OrgService.getLastWdflag());
			List list = OrgService.getOrgChildrenByCode(code);
			List count = OrgService.getOrgCount(code);
			request.setAttribute("org", count.get(0));// 下级机构数量
			request.setAttribute("all", count.get(1));// 所有下级机构数量
			request.setAttribute("list", list);
			request.setAttribute("parentcode", code);
			request.setAttribute("rootcode", rootCode);
			request.setAttribute("wdflag", org.getWdflag());
			return actionMapping.findForward("list.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"list.success");
		}
	}

	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("parentcode");
		try {
			Org parentorg = OrgService.getOrgByCode(code);
			OrgForm orgForm = (OrgForm) actionForm;
			orgForm.setParentcode(parentorg.getCode());
			orgForm.setParentname(parentorg.getName());
			orgForm.setParentwdflag(parentorg.getWdflag());
			int wdflag = Integer.parseInt(parentorg.getWdflag()) + 1;

			String wdflag1 = "" + wdflag;
			request.setAttribute("wdflag", wdflag1);
			request.setAttribute("rootCode", SystemConfig.getInstance()
					.getRootCode());
			return actionMapping.findForward("add.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"list.success");
		}
	}

	public ActionForward forwardupdate(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		try {
			Org org = OrgService.getOrgByCode(code);
			Org parentorg = OrgService.getOrgByCode(org.getParentCode());
			OrgForm orgForm = (OrgForm) actionForm;
			orgForm.setCode(org.getCode());
			orgForm.setName(org.getName());
			orgForm.setParentcode(parentorg.getCode());
			orgForm.setParentname(parentorg.getName());
			request.setAttribute("rootCode", SystemConfig.getInstance()
					.getRootCode());
			request.setAttribute("org", org);
			return actionMapping.findForward("updateforward.success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("rootCode", SystemConfig.getInstance()
					.getRootCode());
			return this.errrForLogAndException(e, actionMapping, request,
					"updateforward.success");
		}
	}

	public ActionForward changerelation(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		OrgForm orgForm = (OrgForm) actionForm;
		try {
			Org org = OrgService.getOrgByCode(code);
			Org parentorg = OrgService.getOrgByCode(org.getParentCode());
			orgForm.setCode(org.getCode());
			orgForm.setName(org.getName());
			orgForm.setParentcode(parentorg.getCode());
			orgForm.setParentname(parentorg.getName());
			orgForm.setWdflag(org.getWdflag());
			request.setAttribute("code", code);
			return actionMapping.findForward("changerelation.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"changerelation.success");
		}
	}

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");// 获取JSP页面的机构号

		try {
			Org bo = OrgService.getOrgByCode(code);
			request.setAttribute("parentcode", bo.getParentCode());
			if (bo == null) {
				return this.showMessageJSP(actionMapping, request, "load",
						"删除机构不存在!");
			}
			List list = this.OrgService.getOrgChildrenByCode(code);
			if (list != null && list.size() > 0) {
				throw new BusinessException("该机构下面存在机构，请先删除下级机构!");
			}
			this.OrgService.deleteOrg(bo);
			String admincode = ((Clerk) request.getSession().getAttribute(
					"clerk")).getCode();
			String content = bo.getName() + "[" + bo.getCode() + "]";
			this.createManageLog(admincode, content);
			return this.showMessageJSP(actionMapping, request, "load",
					"[删除成功] " + content);
		} catch (BusinessException e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"load");
		}
	}

	public ActionForward merge(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String oldcode = request.getParameter("code");
		String newcode = request.getParameter("newcode");
		Org org = null;
		try {
			org = OrgService.getOrgByCode(oldcode);
			OrgService.mergeOrg(oldcode, newcode);

			String admincode = ((Clerk) request.getSession().getAttribute(
					"clerk")).getCode();
			String content = "撤并机构(将机构" + oldcode + "撤并到" + newcode + ")";
			this.createManageLog(admincode, content);

			request.setAttribute("parentcode", org.getParentCode());
			return actionMapping.findForward("load");
		} catch (BusinessException e) {
			request.setAttribute("parentcode", org.getParentCode());
			return this.errrForLogAndException(e, actionMapping, request,
					"load");
		}
	}

	public ActionForward forwardmerge(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = "";
		OrgForm form = (OrgForm) actionForm;
		try {
			if (request.getParameter("code") != null)
				code = request.getParameter("code");
			else
				code = (String) request.getAttribute("code");
			Org org = OrgService.getOrgByCode(code);
			form.setCode(code);
			form.setName(org.getName());
			request.setAttribute("code", code);
			return actionMapping.findForward("merge.success");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"merge.success");
		}
	}

	/*
	 * 输入：code：机构号 Ajax方式获取机构名称 如果机构名称存在返回；如果机构不存在返回空
	 */
	public ActionForward getOrgname(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		String orgname = "";
		try {
			Org org = OrgService.getOrgByCode(code);
			if (org != null)
				orgname = org.getName()+","+org.getWdflag();
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			out.print(orgname);
			out.close();
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
		return null;
	}

	public ActionForward createOrg(ActionMapping mapping,
			ActionForm actionform, HttpServletRequest request,
			HttpServletResponse response) {
		OrgForm form = (OrgForm) actionform;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String admincode = clerk.getCode();
		try {
			Org bo = new Org();
			bo.setCode(form.getCode());
			bo.setName(form.getName());
			bo.setParentCode(form.getParentcode());
		//	bo.setPaymentCode(form.getPaymentCode());
			bo.setLeixbs("0");
			bo.setTctd("不通兑");
			bo.setWdflag(form.getJigjb());
			bo.setLeixbs("1");
			bo.setStatus("1");
			if (form.getWdflag().equals("1")){
				bo.setShOrgCode(form.getCode());	
			}else{
				bo.setShOrgCode(OrgService.getOrgByCode(bo.getParentCode()).getShOrgCode());
			}
			
			Clerk orgManagerClerk = OrgService.createOrg(bo, clerk);
			String orgManagerClerkStr ="";
			if(bo.getWdflag().equals("1")){
			 orgManagerClerkStr = ",自动分配机构管理员["
					+ orgManagerClerk.getCode() + "]";
			}
			SystemConfig systemConfig = SystemConfig.getInstance();
			String content = "";
			if ("2".equals(systemConfig.getValue("clerk_guanlms")))
				if (systemConfig.getRootCode().equals(clerk.getOrgcode())) {
					content = bo.getName() + "[" + bo.getCode() + "]"
							+ orgManagerClerkStr;
				} else {
					content = bo.getName() + "[" + bo.getCode() + "]";
				}
			this.createManageLog(admincode, content);
			request.setAttribute("parentcode", bo.getParentCode());
			return this.showMessageJSP(mapping, request, "load", "[保存成功] "
					+ content);
		} catch (Exception e) {
			request.setAttribute("wdflag", form.getWdflag());
			return this.errrForLogAndException(e, mapping, request,
					"add.success");
		}
	}

	public ActionForward updateOrg(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		OrgForm form = (OrgForm) actionForm;
		try {
			Org bo = OrgService.getOrgByCode(form.getCode());
			bo.setName(form.getName());
			bo.setLeixbs(form.getLeixbs());
			// bo.setPaymentCode(form.getPaymentCode());
			this.OrgService.updateOrg(bo);
			String admincode = ((Clerk) request.getSession().getAttribute(
					"clerk")).getCode();
			String content = bo.getName() + "[" + bo.getCode() + "]";
			this.createManageLog(admincode, content);
			request.setAttribute("parentcode", bo.getParentCode());
			return this.showMessageJSP(mapping, request, "load", "[修改成功] "
					+ content);
		} catch (Exception e) {
			request.setAttribute("rootCode", SystemConfig.getInstance()
					.getRootCode());
			return this.errrForLogAndException(e, mapping, request, "load");
		}
	}

	public ActionForward mergeOrg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OrgForm orgForm = (OrgForm) form;
		String code = request.getParameter("code");
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		ActionMessages errors = new ActionMessages();
		try {
			Org org = OrgService.getOrgByCode(orgForm.getCode());
			if (this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),
					orgForm.getNewcode())) {
				OrgService.mergeOrg(orgForm.getCode(), orgForm.getNewcode());
				String admincode = clerk.getCode();
				String content = "撤并机构(将机构" + orgForm.getCode() + "撤并到"
						+ orgForm.getNewcode() + ")";
				this.createManageLog(admincode, content);
				request.setAttribute("parentcode", org.getParentCode());
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"errors.detail", "[撤并成功] " + content));
				this.saveErrors(request, errors);
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"errors.detail", "[撤并失败]撤并权限不足:不能跨机构撤并!"));
				this.saveErrors(request, errors);
			}
			Org org_ = OrgService.getOrgByCode(code);
			if (org_ != null) {
				request.setAttribute("parentcode", org_.getParentCode());
			}
			return mapping.findForward("load");
		} catch (Exception e) {
			request.setAttribute("code", code);
			return this.errrForLogAndException(e, mapping, request,
					"merge.error");
		} finally {

		}
	}

	public ActionForward changeOrg(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		OrgForm form = (OrgForm) actionForm;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String admincode = clerk.getCode();
		ActionMessages errors = new ActionMessages();
		try {
			Org bo = OrgService.getOrgByCode(form.getCode());
			Org org = OrgService.getOrgByCode(code);
			String orgCode = bo.getParentCode();
			bo.setParentCode(form.getParentcode());
			OrgService.getOrgByCode(form.getParentcode());
			Org parentcode = OrgService.getOrgByCode(form.getParentcode());
			if (parentcode == null) {
				errors
						.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"errors.detail", "机构：" + form.getParentcode()
										+ "不存在!"));
				this.saveErrors(request, errors);
				if (parentcode == null)
					parentcode = new Org();
				form.setParentname(parentcode.getName());
				form.setName(org.getName());
				request.setAttribute("code", code);
				return mapping.findForward("change.error");
			}

			if (this.getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),
					form.getParentcode())) {
				CanOperAccReturn can = this.getQueryService().updateOrgParent(
						clerk.getOrgcode(), form.getCode(),
						form.getParentcode());
				if (can.getReturnValue()) {
					String content = "改变归属:(" + bo.getCode() + ")";
					content += "新上级" + bo.getParentCode() + "原上级" + orgCode+";"+ can
					.getReturnMessage();
					errors.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.detail", content));
					this.createManageLog(admincode, content);
				} else {
					errors.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.detail", can
									.getReturnMessage()));
					this.saveErrors(request, errors);
					form.setParentname(parentcode.getName());
					form.setName(org.getName());
					request.setAttribute("code", code);
					return mapping.findForward("change.error");
				}
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"errors.detail", "[归属关系修改失败]权限不足： " + "不能跨机构修改归属关系！"));
				this.saveErrors(request, errors);
				form.setParentname(parentcode.getName());
				form.setName(org.getName());
				request.setAttribute("code", code);
				return mapping.findForward("change.error");
			}
			return mapping.findForward("load");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "load");
		} finally {

		}
	}

	/**
	 * 重写 download方法,导出机构信息
	 */

	public ActionForward download(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, BusinessException {
		String file = "org.xls";
		String orgcode = request.getParameter("parentcode");
		String include = request.getParameter("include");
		Map map = new HashMap();
		List list;
		try {
			list = OrgService.exportOrg(orgcode, include);
			if (list.size() > 0) {
				HSSFWorkbook wb = ExpOrImp
						.exportExcel("organarchives", list, 6);
				response.setHeader("Content-disposition",
						"attachment;filename=" + file);
				response.setContentType("application/rar");
				response.setContentLength(wb.getSheetIndex(file));
				ServletOutputStream out = response.getOutputStream();
				wb.write(out);
				out.flush();
			} else {
				throw new BusinessException("结果文件不存在！");
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return this.showMessageJSP(mapping, request, "load",
					getPromptService().getPromptMsg("YYA-export-error", map));
		}
	}

	/**
	 * 导入机构信息 (导入介质：excle)
	 */
	public ActionForward importOrg(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("parentcode", request.getParameter("parentcode"));
		PromptService promptService = getPromptService();
		OrgForm orgForm = (OrgForm) actionForm;
		FormFile file = (FormFile) orgForm.getFile();
		InputStream input = null;
		try {
			input = file.getInputStream();
			int size = input.available();
			if (size <= 0) {
				return this.showMessageJSP(mapping, request, "load",
						"上传文件为不能为空!");
			}
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			boolean isImport = false;
			isImport = OrgService.importOrg(sheet);
			if (isImport) {
				return this.showMessageJSP(mapping, request, "load",
						promptService.getPromptMsg("YYA-import-ok",
								new HashMap()));
			} else {
				return this.showMessageJSP(mapping, request, "load",
						promptService.getPromptMsg("YYA-import-error",
								new HashMap()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.showMessageJSP(mapping, request, "load", promptService
					.getPromptMsg("YYA-import-error", new HashMap()));
		}
	}
	/**
	 * 跳转支票影像设置页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forZhipyx(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
		List<Zhipyxxx> zhipyxList=getZhipyxJig();
			request.setAttribute("zhipyxList", zhipyxList);
			return mapping.findForward("zhipyx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "zhipyx");
		} finally {

		}
	}
	/**
	 * 跳转支票影像修改页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toModiZhipyx(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jigh=request.getParameter("jigh");
			Org org =OrgService.getOrgByCode(jigh);
			if(org==null){
				List<Zhipyxxx> zhipyxList=getZhipyxJig();
					request.setAttribute("zhipyxList", zhipyxList);
				return this.showMessageJSP(mapping, request, "zhipyx",
						"机构"+jigh+"不存在");
			}
			Zhipyxxx zp=OrgService.getZhipyxx(jigh);
			if(zp==null){
				zp=new Zhipyxxx();
				zp.setJigh(jigh);
			}
			zp.setJigmc(org.getName());
			request.setAttribute("zhip", zp);
			return mapping.findForward("modiZhipyxxx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "zhipyx");
		} finally {

		}
	}

	/**
	 *修改支票影像设置
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateZhipyxxx(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Zhipyxxx> zhipyxList=getZhipyxJig();
			request.setAttribute("zhipyxList", zhipyxList);
			String jigh=request.getParameter("jigh");
			
			Org org_temp=OrgService.getOrgByCode(jigh);
			if(org_temp==null){
				return this.showMessageJSP(mapping, request, "zhipyx",
						"机构"+jigh+"不存在");
			}
			String socketadd=request.getParameter("socketadd");
			String port=request.getParameter("port");
			Zhipyxxx zhip=new Zhipyxxx();
			zhip.setJigh(jigh);
			zhip.setIp(socketadd);
			zhip.setPort(port);
			OrgService.updateOrSaveZhipyxxx(zhip);
			 zhipyxList=getZhipyxJig();
			request.setAttribute("zhipyxList", zhipyxList);
			return this.showMessageJSP(mapping, request, "zhipyx",
					"修改机构"+jigh+"支票影像设置成功");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "zhipyx");
		} finally {

		}
	}
	
	private List<Zhipyxxx>  getZhipyxJig(){
		List<Zhipyxxx> zhipyxList=new ArrayList<Zhipyxxx>();
		List<Org> orgList=OrgService.getAllOrgByWdflag("1");
		for (Org o : orgList) {
			Zhipyxxx zhipyx=OrgService.getZhipyxx(o.getCode());
			if(zhipyx==null){
				zhipyx=new Zhipyxxx();
				zhipyx.setJigh(o.getCode());
			}
			zhipyx.setJigmc(o.getName());
			zhipyxList.add(zhipyx);
		}
		return zhipyxList;
	}
	/**
	 * 跳转作业中心设置页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forAsynSealCheckConfig(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
		List<AsynSealCheckConfig> zuoyzxList=getZuoyzxJig();
			request.setAttribute("AsynSealCheckConfigList", zuoyzxList);
			return mapping.findForward("AsynSealCheckConfig");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "AsynSealCheckConfig");
		} finally {

		}
	}
	/**
	 * 跳转作业中心修改页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toModiAsynSealCheckConfig(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jigh=request.getParameter("jigh");
			Org org =OrgService.getOrgByCode(jigh);
			if(org==null){
				List<AsynSealCheckConfig> zuoyzxList=getZuoyzxJig();
					request.setAttribute("AsynSealCheckConfigList", zuoyzxList);
				return this.showMessageJSP(mapping, request, "AsynSealCheckConfig",
						"机构"+jigh+"不存在");
			}
			AsynSealCheckConfig zyzx=OrgService.getZuoyzxxx(jigh);
			if(zyzx==null){
				zyzx=new AsynSealCheckConfig();
				zyzx.setJigh(jigh);
			}
			zyzx.setJigmc(org.getName());
			request.setAttribute("asynSealCheckConfig", zyzx);
			return mapping.findForward("modiAsynSealCheckConfig");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "AsynSealCheckConfig");
		} finally {

		}
	}

	/**
	 *修改作业中心参数设置
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateAsynSealCheckConfig(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<AsynSealCheckConfig> zuoyzxList=getZuoyzxJig();
			request.setAttribute("AsynSealCheckConfigList", zuoyzxList);
			String jigh=request.getParameter("jigh");
			
			Org org_temp=OrgService.getOrgByCode(jigh);
			if(org_temp==null){
				return this.showMessageJSP(mapping, request, "AsynSealCheckConfig",
						"机构"+jigh+"不存在");
			}
			String ip=request.getParameter("zidkzip");
			String beiyip=request.getParameter("zidkzipb");
			String port=request.getParameter("zidkzport");
			String cs=request.getParameter("zidkzcs");
			String nodeid=request.getParameter("nodeid");
			AsynSealCheckConfig zuoyzx=new AsynSealCheckConfig();
			zuoyzx.setJigh(jigh);
			zuoyzx.setZidkzcs(cs);
			zuoyzx.setNodeid(nodeid);
			zuoyzx.setZidkzip(ip);
			zuoyzx.setZidkzport(port);
			zuoyzx.setZidkzipb(beiyip);
			OrgService.updateOrSaveAsynSealCheckConfig(zuoyzx);
			 zuoyzxList=getZuoyzxJig();
			request.setAttribute("AsynSealCheckConfigList", zuoyzxList);
			return this.showMessageJSP(mapping, request, "AsynSealCheckConfig",
					"修改机构"+jigh+"异步验印参数成功");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "AsynSealCheckConfig");
		} finally {

		}
	}
	
	private List<AsynSealCheckConfig>  getZuoyzxJig(){
		List<AsynSealCheckConfig> zuoyzxList=new ArrayList<AsynSealCheckConfig>();
		List<Org> orgList=OrgService.getAllOrgByWdflag("1");
		for (Org o : orgList) {
			AsynSealCheckConfig zuoyzx=OrgService.getZuoyzxxx(o.getCode());
			if(zuoyzx==null){
				zuoyzx=new AsynSealCheckConfig();
				zuoyzx.setJigh(o.getCode());
			}
			zuoyzx.setJigmc(o.getName());
			zuoyzxList.add(zuoyzx);
		}
		return zuoyzxList;
	}
	
	
	/**
	 * 跳转分行影像缓存设置页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forCacheConfig(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<CacheConfig> yingxhcList=getCacheJig();
			request.setAttribute("caCheConfigList", yingxhcList);
			return mapping.findForward("CacheConfig");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "CacheConfig");
		} finally {

		}
	}
	/**
	 * 跳转分行影像缓存修改页面
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toModiCacheConfig(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jigh=request.getParameter("jigh");
			Org org =OrgService.getOrgByCode(jigh);
			if(org==null){
				List<CacheConfig> yingxhcList=getCacheJig();
				request.setAttribute("cacheConfigList", yingxhcList);
				return this.showMessageJSP(mapping, request, "CacheConfig",
						"机构"+jigh+"不存在");
			}
			CacheConfig yingxhc=OrgService.getCacheConfigByOrgCode(jigh);
			if(yingxhc==null){
				yingxhc=new CacheConfig();
				yingxhc.setJigh(jigh);
			}
			yingxhc.setJigmc(org.getName());
			request.setAttribute("cacheConfig", yingxhc);
			return mapping.findForward("modiCacheConfig");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "modiCacheConfig");
		} finally {

		}
	}

	/**
	 *修改分行影像缓存参数设置
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateCacheConfig(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<CacheConfig> yingxhcList=getCacheJig();
			request.setAttribute("CacheConfigList", yingxhcList);
			String jigh=request.getParameter("jigh");
			
			Org org_temp=OrgService.getOrgByCode(jigh);
			if(org_temp==null){
				return this.showMessageJSP(mapping, request, "CacheConfig",
						"机构"+jigh+"不存在");
			}
			String ip=request.getParameter("ip");
			String port=request.getParameter("port");
			String timeout=request.getParameter("timeout");
			CacheConfig yingxhc=new CacheConfig();
			yingxhc.setJigh(jigh);
			yingxhc.setIp(ip);
			yingxhc.setPort(port);
			yingxhc.setTimeout(timeout);
			OrgService.updateOrSaveCacheConfig(yingxhc);
			yingxhcList=getCacheJig();
			request.setAttribute("caCheConfigList", yingxhcList);
			return this.showMessageJSP(mapping, request, "CacheConfig",
					"修改分行["+jigh+"]影像缓存参数成功");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "CacheConfig");
		} finally {

		}
	}
	
	private List<CacheConfig>  getCacheJig(){
		List<CacheConfig> yingxhcList=new ArrayList<CacheConfig>();
		List<Org> orgList=OrgService.getAllOrgByWdflag("1");
		for (Org o : orgList) {
			CacheConfig yingxhc=OrgService.getCacheConfigByOrgCode(o.getCode());
			if(yingxhc==null){
				yingxhc=new CacheConfig();
				yingxhc.setJigh(o.getCode());
			}
			yingxhc.setJigmc(o.getName());
			yingxhcList.add(yingxhc);
		}
		return yingxhcList;
	}

	
	

}
