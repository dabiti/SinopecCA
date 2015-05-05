package com.unitop.sysmgr.action;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.framework.util.BoTool;
import com.unitop.framework.util.JsonTool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Privilege;
import com.unitop.sysmgr.bo.Role;
import com.unitop.sysmgr.dao.ClerkDao;
import com.unitop.sysmgr.form.RoleForm;
import com.unitop.sysmgr.service.PrivilegeService;
import com.unitop.sysmgr.service.RoleService;

/*
 * ��ɫ����
 */

@Controller("/roleManager")
public class RoleManagerAction extends ExDispatchAction {

	@Resource
	private RoleService roleService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	public ClerkDao clerkDao = null;
	
	//��ɫ���
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			return actionMapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}finally{
			//���ؽ�ɫ����
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
	}
	
	//��ӽ�ɫ+Ȩ�� ��ת
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			RoleForm roleForm = (RoleForm) actionForm;
			roleForm.setJuesid(null);
			roleForm.setJuesmc(null);
			roleForm.setJuesms(null);
			roleForm.setBeiz(null);
			roleForm.setJuesjb(null);
			List list = privilegeService.getAllPrivilege();
			String jsonString = JsonTool.toJsonForArry(list);
			request.setAttribute("privilegelist", jsonString);
			return actionMapping.findForward("add");
		} catch (Exception e) {
			//���ؽ�ɫ����
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
			return this.errrForLogAndException(e, actionMapping, request, "add");
		}

	}
	//�����ɫ+Ȩ��
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			RoleForm roleForm = (RoleForm) actionForm;
			Role role = new Role();
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			role.setJuesid(roleForm.getJuesid());
			role.setJuesmc(roleForm.getJuesmc());
			role.setJuesms(roleForm.getJuesms());
			role.setBeiz(roleForm.getBeiz());
			role.setJuesjb(roleForm.getJuesjb());
			Role roleForCZ = roleService.getRole(role.getJuesid());
			if(roleForCZ!=null)
			{
				List list = privilegeService.getAllPrivilege();
				String jsonString = JsonTool.toJsonForArry(list);
				request.setAttribute("privilegelist", jsonString);
				return this.showMessageJSP(actionMapping,request,"add","["+role.getJuesid()+"]��ɫ�Ѿ����ڣ�");
			}
			String juesName = role.getJuesmc();
			//�жϽ�ɫ�����Ƿ��Ѿ���ռ��
			if(juesName == null)
			{
				juesName = "";
			}
			juesName = juesName.trim();
			Role roleMc = roleService.getRoleByName(juesName);
			if(roleMc!=null)
			{
				List list = privilegeService.getAllPrivilege();
				String jsonString = JsonTool.toJsonForArry(list);
				request.setAttribute("privilegelist", jsonString);
				return this.showMessageJSP(actionMapping,request,"add","��ɫ����["+role.getJuesmc()+"]�Ѿ����ڣ�");
			}
			String jsonString = request.getParameter("jsonString");
			List privileList = JsonTool.toSringForList(jsonString,Privilege.class);
			privilegeService.saveJuesqxgxbList(role, privileList);
			this.createManageLog(clerk.getCode(), "��ɫ����[��ӽ�ɫ:"+role.getJuesid()+"]");
			return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]��ɫ����ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]��ɫ����ʧ�ܣ�");
		}finally{
			//���ؽ�ɫ����
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
	}

	//ɾ����ɫ+Ȩ��
	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			RoleForm roleForm = (RoleForm) actionForm;
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			List list = clerkDao.selectGuiyjsgxb(roleForm.getJuesid());
			if(list.size()!=0)
			{
				return this.showMessageJSP(actionMapping,request,"list","["+roleForm.getJuesid()+"]��ɫ�Ѿ�����Աʹ�ã�����ɾ��!");
			}
			roleService.deleteRole(roleForm.getJuesid());
			this.createManageLog(clerk.getCode(), "��ɫ����[ɾ����ɫ:"+roleForm.getJuesid()+"]");
			return this.showMessageJSP(actionMapping,request,"list","["+roleForm.getJuesid()+"]��ɫɾ���ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			//���ؽ�ɫ����
			return this.showMessageJSP(actionMapping,request,"list","["+roleForm.getJuesid()+"]��ɫɾ��ʧ�ܣ�");
		}finally{
			//���ؽ�ɫ����
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
	}
	
	//�޸Ľ�ɫ+Ȩ��
	public ActionForward modify(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			RoleForm roleForm = (RoleForm) actionForm;
			List<Privilege> juesList = privilegeService.getPrivilegeList(roleForm.getJuesid());
			Role role = roleService.getRole(roleForm.getJuesid());
			roleForm.setJuesmc(role.getJuesmc());
			roleForm.setJuesms(role.getJuesms());
			roleForm.setBeiz(role.getBeiz());
			roleForm.setJuesjb(role.getJuesjb());
			String jsonString = BoTool.ListToJsonString(juesList);
			request.setAttribute("juesListJsonString", jsonString);
			return actionMapping.findForward("modify");
		} catch (Exception e) {
			//���ؽ�ɫ����
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
			return this.errrForLogAndException(e, actionMapping, request,"modify");
		}
		
	}
	
	//���½�ɫȨ��
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			RoleForm roleForm = (RoleForm) actionForm;
			Role role = new Role();
		try {
			String juesName = roleForm.getJuesmc();
			//�жϽ�ɫ�����Ƿ��Ѿ���ռ��
			if(juesName == null)
			{
				juesName = "";
			}
			juesName = juesName.trim();
			Role roleMc = roleService.getRoleByName(juesName);
			Role roleId = roleService.getRole(roleForm.getJuesid());
			if(roleMc == null || juesName.equals(roleId.getJuesmc()))
			{
				role.setJuesid(roleForm.getJuesid());
				role.setJuesmc(roleForm.getJuesmc());
				role.setJuesms(roleForm.getJuesms());
				role.setBeiz(roleForm.getBeiz());
				role.setJuesjb(roleForm.getJuesjb());
				
				String jsonString = request.getParameter("jsonString");
				List privileList = JsonTool.toSringForList(jsonString,Privilege.class);
				privilegeService.updateJuesqxgxbList(role, privileList);
				this.createManageLog(clerk.getCode(), "��ɫ����[�޸Ľ�ɫ:"+roleForm.getJuesid()+"]");
				return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]��ɫ�޸ĳɹ���");
			}else {
				List<Privilege> juesList = privilegeService.getPrivilegeList(roleForm.getJuesid());
				Role role_ = roleService.getRole(roleForm.getJuesid());
				roleForm.setJuesmc(role_.getJuesmc());
				roleForm.setJuesms(role_.getJuesms());
				roleForm.setBeiz(role_.getBeiz());
				roleForm.setJuesjb(role_.getJuesjb());
				String jsonString = BoTool.ListToJsonString(juesList);
				request.setAttribute("juesListJsonString", jsonString);
				return this.showMessageJSP(actionMapping,request,"modify","��ɫ����["+juesName+"]�Ѿ����ڣ�");
			}
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]��ɫ�޸�ʧ�ܣ�");
		}finally{
			//���ؽ�ɫ����
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
		
	}

}