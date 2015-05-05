package com.unitop.sysmgr.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.struts.upload.FormFile;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;

import com.unitop.config.Privilege;
import com.unitop.config.PrivilegeConfig;
import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.framework.util.ExpOrImp;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.form.ClerkForm;
import com.unitop.sysmgr.service.ChanpcdService;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.impl.ControlRights;

@Controller("/clerkManage")
public class ClerkManageAction extends ExDispatchAction {
	@Resource
	private OrgService OrgService;
	@Resource
	private ClerkManageService clerkService;
	@Resource
	private ControlRights controlRights;
	@Resource
	private ChanpcdService ChanpcdService;

	public ActionForward loadtree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("parentcode");
		List list = OrgService.getOrgChildrenByCode(code);
		try {
			// ��ȡĬ�����л�����
			String rootCode = SystemConfig.getInstance().getRootCode();
			// ��ȡ��Ա����ģʽ:1:���й���;2:���й���
			String clerk_guanlms = SystemConfig.getInstance().getValue(
					"clerk_guanlms");
			Element rootElement = new Element("tree");
			Document myDocument = new Document(rootElement);
			if ((!rootCode.equalsIgnoreCase(code))
					|| "1".equalsIgnoreCase(clerk_guanlms)) {
				for (int i = 0; i < list.size(); i++) {
					Org bo = (Org) list.get(i);
					Element tree = new Element("tree");
					tree.setAttribute("text", bo.getName());
					tree.setAttribute("action",
							"../clerkManage.do?method=load&orgcode="
									+ bo.getCode());
					tree.setAttribute("target", "mainF");
					tree.setAttribute("src",
							"../clerkManage.do?method=loadtree&parentcode="
									+ bo.getCode() + "&randomid="
									+ new Random().nextInt(1000000));
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
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}

	/**
	 * ���ع�Ա��Ϣ�б�
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward load(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			boolean addflag = true;
			ClerkForm form = (ClerkForm) actionForm;
			String orgcode = request.getParameter("orgcode");
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			List list = this.clerkService.getClerkByOrgcode(orgcode);
			Privilege privilege = ChanpcdService
					.getPostCollectionByName("��Ա����");
			if (clerk.getOrgcode().equals(orgcode)) {
				addflag = true;
			} else {
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Clerk element = (Clerk) iter.next();
					String postpodeom = element.getPostpopedom();
					int value = new Integer(privilege.getValue()).intValue();
					if (postpodeom == null || postpodeom.equals(""))
						;
					else if (PrivilegeConfig.CheckPrivilege(postpodeom, value)) {
						addflag = false;
						break;
					}
				}
			}
			// ���ƹ�Ա����ӡ���ť����ʾ
			int nowwdflag = Integer.parseInt(OrgService.getOrgByCode(orgcode)
					.getWdflag());
			int lastWdflag = OrgService.getLastWdflag();
			boolean b = nowwdflag > lastWdflag;
			if (b) {
				addflag = false;
			}
			if (addflag)
				request.setAttribute("addflag", "1");
			else
				request.setAttribute("addflag", "0");
			form.setOrgcode(orgcode);
			String errorpasswordtimes = SystemConfig.getInstance().getValue(
					"errorpasswordtimes");
			request.setAttribute("errorpasswordtimes", errorpasswordtimes);
			request.setAttribute("list", list);
			request.setAttribute("orgcode", orgcode);
			return actionMapping.findForward("list.success");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"list.success");
		}
	}

	private ActionForward loadForMessage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response, String orgCode, String Message) {
		try {
			boolean addflag = true;
			ClerkForm form = (ClerkForm) actionForm;
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			List list = this.clerkService.getClerkByOrgcode(orgCode);
			Privilege privilege = ChanpcdService
					.getPostCollectionByName("��Ա����");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Clerk element = (Clerk) iter.next();
				String postpodeom = element.getPostpopedom();
				int value = new Integer(privilege.getValue()).intValue();
				if (postpodeom == null || postpodeom.equals(""))
					;
				else if (PrivilegeConfig.CheckPrivilege(postpodeom, value)) {
					addflag = false;
					break;
				}
			}
			// ���ƹ�Ա����ӡ���ť����ʾ
			int nowwdflag = Integer.parseInt(OrgService.getOrgByCode(orgCode)
					.getWdflag());
			int lastWdflag = OrgService.getLastWdflag();
			if ((nowwdflag > lastWdflag)) {
				addflag = false;
			}
			if (addflag)
				request.setAttribute("addflag", "1");
			else
				request.setAttribute("addflag", "0");
			form.setOrgcode(orgCode);
			String errorpasswordtimes = SystemConfig.getInstance().getValue(
					"errorpasswordtimes");
			request.setAttribute("errorpasswordtimes", errorpasswordtimes);
			request.setAttribute("list", list);
			return this.showMessageJSP(actionMapping, request, "list.success",
					Message);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"list.success");
		}
	}

	/**
	 * ɾ����Ա
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String code = "";
		String orgcode = "";
		Clerk delclerk = null;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			code = request.getParameter("code");
			delclerk = clerkService.getClerkByCode(code);
			orgcode = delclerk.getOrgcode();
			clerkService.deleteRoleByClerk(code);// ɾ����Ա+��Ա��ɫ��ϵ
			if (delclerk != null) {
				this.createManageLog(clerk.getCode(), "[ɾ���ɹ�]��Ա��"
						+ delclerk.getName() + "[" + code + "]");
			}
		} catch (BusinessException e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			request.setAttribute("orgcode", delclerk.getOrgcode());
		}
		return this.loadForMessage(actionMapping, actionForm, request,
				response, orgcode, "[ɾ���ɹ�]��Ա��" + delclerk.getName() + "["
						+ code + "]");
	}

	public ActionForward unlock(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			Clerk jsclerk = clerkService.getClerkByCode(code);
			if (jsclerk != null) {
				jsclerk.setErrortime(new Integer(0));
				try {
					clerkService.updateClerk(jsclerk);
					String content = "[�����ɹ�]��Ա��" + jsclerk.getName() + "["
							+ code + "]";
					this.createManageLog(clerk.getCode(), content);
				} catch (BusinessException e) {
					return this.showMessageJSP(actionMapping, request,
							"list.success", "ϵͳ�쳣������ʧ�ܣ�");
				}
			}
			return this.loadForMessage(actionMapping, actionForm, request,
					response, clerk.getOrgcode(), "[�����ɹ�]��Ա��"
							+ jsclerk.getName() + "[" + code + "]");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"list.success");
		}
	}

	/**
	 * ��ת����Ա���ģ��
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forwardCreate(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			String orgcode = request.getParameter("orgcode");
			if(orgcode==null||orgcode.trim().equals("")){
				return this.showMessageJSP(actionMapping, request, "list.success",
				"");
			}
			Org org = OrgService.getOrgByCode(orgcode);
			List roleList = this.clerkService.getAllRoleByClerk(clerk,org.getWdflag());
			ClerkForm clerkForm = (ClerkForm) actionForm;
			clerkForm.setOrgcode(orgcode);
			clerkForm.setPostlist(roleList);
			request.setAttribute("rolelist", roleList);
			if (org != null)
				clerkForm.setOrgname(org.getName());
			return actionMapping.findForward("forward.create");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"list.success");
		}
	}

	/**
	 * ������Ա
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createClerk(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			// ��ɫ�б�
			String[] selected = request.getParameterValues("s2");

			ClerkForm clerkForm = (ClerkForm) form;
			Org org = OrgService.getOrgByCode(clerkForm.getOrgcode());
			String leixbs=request.getParameter("leixbs");
			if (selected == null || selected.length == 0) {
				List roleList = this.clerkService.getAllRoleByClerk(clerk,org.getWdflag());
				request.setAttribute("rolelist", roleList);
				return this.showMessageJSP(mapping, request, "create.input",
						"����ӹ�Ա��ɫ��");
			}

			// ����ϵͳ��Ա����
			if (!controlRights.clerkNumControl()) {
				List roleList = this.clerkService.getAllRoleByClerk(clerk,org.getWdflag());
				request.setAttribute("rolelist", roleList);
				return this.showMessageJSP(mapping, request, "create.input",
						"�����Ĺ�Ա���Ѵ����ޣ�");
			}

			Clerk bo = new Clerk();
			bo.setCode(clerkForm.getCode());
			bo.setName(clerkForm.getName());
			bo.setOrgcode(clerkForm.getOrgcode());
			SystemConfig systemConfig = SystemConfig.getInstance();
			if (clerkForm.getPassword() == null
					|| clerkForm.getPassword().length() == 0) {
				bo.setPassword(systemConfig.getAdminPassword());
			} else {
				bo.setPassword(systemConfig.getAdminPassword());
			}

			bo.setPostCode(clerkForm.getPostCode());
			String date = this.getSystemMgrService().getSystetemNowDate();
			bo.setUpdateDate(date.substring(0, 10));
			bo.setCreator(clerk.getOrgcode());
			bo.setShOrgCode(org.getShOrgCode());
			bo.setLeixbs(leixbs);
			bo.setStatus("1");
			
		
			bo.setWdFlag(org.getWdflag());
			boolean flag = false;
			try {
				Clerk clerk_temp = clerkService.getClerkByCode(clerkForm
						.getCode());
				if (clerk_temp.getCode() != "") {
					List roleList = this.clerkService.getAllRoleByClerk(clerk,org.getWdflag());
					request.setAttribute("rolelist", roleList);
					return this.showMessageJSP(mapping, request,
							"create.input", "��Ա�����Ѵ���!");
				}

				// �����Ա+��Ա��ɫ
				clerkService.save(bo, selected);
				flag = true;
			} catch (BusinessException e) {
				processBusinessException(mapping, request, e);
				return mapping.findForward("create.input");
			}
			String admincode = ((Clerk) request.getSession().getAttribute(
					"clerk")).getCode();
			String content = "[����ɹ�]��Ա��" + bo.getName() + "[" + bo.getCode()
					+ "]";
			this.createManageLog(admincode, content);

			if (flag) {
				clerkForm.clear();
				return this.showMessageJSP(mapping, request, "load", content);
			}
			ActionForward forward = mapping.findForward("load");
			String path = forward.getPath();
			path = path + "&orgcode=" + bo.getOrgcode();
			return forward;
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request,
					"create.input");
		}
	}

	/**
	 * ��ת���޸Ĺ�Ա��Ϣģ��
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forwardupdate(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String admincode = ((Clerk) request.getSession().getAttribute(
					"clerk")).getCode();
			String code = request.getParameter("code");
			Clerk clerk = clerkService.getClerkByCode(code);
			List roleList = clerkService.getElseRoleByClerk(admincode, code);// ��ȡ�����ڸù�Ա�Ľ�ɫ
			List roleSelectList = clerkService.getRoleByClerk(code);// ��ȡ�ù�Ա�Ľ�ɫ
			ClerkForm clerkForm = (ClerkForm) actionForm;
			clerkForm.setCode(clerk.getCode());
			clerkForm.setName(clerk.getName());
			clerkForm.setPostCode(clerk.getPostCode());
			clerkForm.setPostlist(roleList);
			clerkForm.setOldcode(clerk.getCode());
			clerkForm.setOrgcode(clerk.getOrgcode());
			clerkForm.setPassword(clerk.getPassword());
			clerkForm.setPassword1(clerk.getPassword());
			request.setAttribute("leixbs", clerk.getLeixbs());
			request.setAttribute("rolelist", roleList);
			request.setAttribute("roleselectlist", roleSelectList);
			return actionMapping.findForward("forward.update");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"list.success");
		}
	}

	/**
	 * �޸Ĺ�Ա��Ϣ
	 * 
	 * @param mapping
	 * @param actionform
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateClerk(ActionMapping mapping,
			ActionForm actionform, HttpServletRequest request,
			HttpServletResponse response) {
		ClerkForm form = (ClerkForm) actionform;
		try {
			// ��ɫ�б�
			String[] selected = request.getParameterValues("s2");
			
			if (selected == null || selected.length == 0) {
				Clerk clerk = (Clerk) request.getSession()
						.getAttribute("clerk");
				List roleList = this.clerkService.getAllRoleByClerk(clerk);
				request.setAttribute("rolelist", roleList);
				return this.showMessageJSP(mapping, request, "update.error",
						"����ӹ�Ա��ɫ��");
			}

			if (!form.getPassword().equals(form.getPassword1())) {
				return this.showMessageJSP(mapping, request, "update.error",
						"�����������벻һ��!");
			}

			Clerk clerk = clerkService.getClerkByCode(form.getOldcode());
			String leixbs=clerk.getLeixbs();
			clerk.setCode(form.getCode());
			clerk.setName(form.getName());
			clerk.setLeixbs(leixbs);
			if(leixbs.equals("0")){
			clerk.setPassword(form.getPassword());
			clerk.setPostCode(form.getPostCode());
			}
			boolean flag = clerkService.updateClerkRoles(clerk, selected);
			String content = "";
			String admincode = ((Clerk) request.getSession().getAttribute(
					"clerk")).getCode();
			if (flag) {
				form.setOldcode(clerk.getCode());
				content = "[�޸ĳɹ�]��Ա��" + clerk.getName() + "[" + clerk.getCode()
						+ "]";
				this.createManageLog(admincode, content);
				List roleList = clerkService.getElseRoleByClerk(admincode, form
						.getCode());// ��ȡ�����ڸù�Ա�Ľ�ɫ
				List roleSelectList = clerkService.getRoleByClerk(form
						.getCode());// ��ȡ�ù�Ա�Ľ�ɫ
				request.setAttribute("rolelist", roleList);
				request.setAttribute("roleselectlist", roleSelectList);
				return this.showMessageJSP(mapping, request, "load", content);
			} else {
				content = "[�޸�ʧ��]��Ա��" + clerk.getName() + "[" + clerk.getCode()
						+ "]";
				this.createManageLog(admincode, content);
				return this.showMessageJSP(mapping, request, "update.error",
						content);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request,
					"update.error");
		}
	}

	/**
	 * ��д download������������Ա��Ϣ
	 */
	public ActionForward download(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, BusinessException {
		String file = "clerk.xls";
		String orgcode = request.getParameter("orgcode");
		String include = request.getParameter("include");
		Map map = new HashMap();
		List list = null;
		try {
			list = clerkService.exportClerk(orgcode, include);
			if (list.size() > 0) {
				HSSFWorkbook wb = ExpOrImp.exportExcel("clerk", list, 7);
				response.setHeader("Content-disposition",
						"attachment;filename=" + file);
				response.setContentType("application/rar");
				response.setContentLength(wb.getSheetIndex(file));
				ServletOutputStream out = response.getOutputStream();
				wb.write(out);
				out.flush();
			} else {
				throw new BusinessException("����ļ������ڣ�");
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return this.showMessageJSP(mapping, request, "load",
					getPromptService().getPromptMsg("YYA-export-error", map));
		}
	}

	/**
	 * �����Ա��Ϣ
	 */
	@SuppressWarnings("unchecked")
	public ActionForward importClerk(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ClerkForm form = (ClerkForm) actionForm;
		FormFile file = (FormFile) form.getFile();
		Map map = new HashMap();
		InputStream input = null;
		try {
			input = file.getInputStream();
			int size = input.available();
			if (size <= 0) {
				return this.showMessageJSP(mapping, request, "load",
						"�ϴ��ļ�Ϊ����Ϊ��!");
			}
			input = file.getInputStream();
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			boolean isImport = false;
			isImport = clerkService.importClerk(sheet);
			if (isImport) {
				return this.showMessageJSP(mapping, request, "load",
						getPromptService().getPromptMsg("YYA-import-ok", map));
			} else {
				return this.showMessageJSP(mapping, request, "load",
						getPromptService()
								.getPromptMsg("YYA-import-error", map));
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "load");
		} finally {
			request.setAttribute("orgcode", request.getParameter("orgcode"));
		}
	}

	// ˫�˻�ǩ
	public ActionForward shuangrhq(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String clerkCode = request.getParameter("clerknum");
		String clerkPwd = request.getParameter("password");
		;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String titleName = "";
		String account = request.getParameter("account");
		try {
			titleName = URLDecoder.decode(request.getParameter("titleName"),
					"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);

			Clerk clerk_ = (Clerk) clerkService.getClerkByCode(clerkCode);
			;
			if (clerk_ == null) {
				out.println("0");// ��Ա������
				return null;
			}
			// �ж��Ƿ�ǿ���޸�����
			if ("0".equals(clerk_.getClerkstatus()))
				if ("1".equals(SystemConfig.getInstance().getValue(
						"clerk_firstlogincpw"))) {
					out.println("5");// �жϹ�Ա�Ƿ���������
					return null;
				}
			if (clerk_.getCode().equals(clerk.getCode())) {
				out.println("4");// ��ǩ��Ա�����ǵ�¼��Ա
				return null;
			}
			if (clerk.getOrgcode().endsWith(clerk_.getOrgcode())) {
				if (clerkPwd.equals(clerk_.getPassword())) {
					out.println("1");// ˫ǩ�ɹ�
					if (account != null) {
						this.createAccountManageLog(account, "��ǩ", titleName
								+ "��ǩ", clerk_);
					}
				} else {
					out.println("2");// �������
				}
				return null;
			} else {
				out.println("3");// ������ͬһ����
			}
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, null);
		} finally {
			if (out != null) {
				out.close();
			}
			return null;
		}
	}

	/*
	 * �жϵ�¼��Ա�������Ա��ɫ�����С
	 */
	public ActionForward validateClerks(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String denglguiy = request.getParameter("denglguiy");
		String caozguiy = request.getParameter("caozguiy");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String denglguiyJuesJB = clerkService.getClerkJusjb(denglguiy);
			String caozguiyJuesJB = clerkService.getClerkJusjb(caozguiy);
			if (Integer.valueOf(denglguiyJuesJB) >= Integer
					.valueOf(caozguiyJuesJB)) {
				out.write("1");
			} else {
				out.write("0");
			}
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			if (out != null) {
				out.close();
			}
			return null;
		}
	}
	
	/**
	 * �Զ�������ӡ��Ա��
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getNewClerkCode(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String orgcode = request.getParameter("orgcode");
		response.setCharacterEncoding("gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			try{
			String newcode=clerkService.getNewClerkCode(orgcode);
			out.print(newcode);
			}catch (BusinessException e) {
				out.print(e.getMessage());
				return null;
			}
			return null;
		} catch (Exception e) {
			out.print(e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
			return null;
		}
	}
}