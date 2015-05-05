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
 * 角色管理
 */

@Controller("/roleManager")
public class RoleManagerAction extends ExDispatchAction {

	@Resource
	private RoleService roleService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	public ClerkDao clerkDao = null;
	
	//角色浏览
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
			//加载角色数据
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
	}
	
	//添加角色+权限 跳转
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
			//加载角色数据
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
			return this.errrForLogAndException(e, actionMapping, request, "add");
		}

	}
	//保存角色+权限
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
				return this.showMessageJSP(actionMapping,request,"add","["+role.getJuesid()+"]角色已经存在！");
			}
			String juesName = role.getJuesmc();
			//判断角色名称是否已经被占用
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
				return this.showMessageJSP(actionMapping,request,"add","角色名称["+role.getJuesmc()+"]已经存在！");
			}
			String jsonString = request.getParameter("jsonString");
			List privileList = JsonTool.toSringForList(jsonString,Privilege.class);
			privilegeService.saveJuesqxgxbList(role, privileList);
			this.createManageLog(clerk.getCode(), "角色管理[添加角色:"+role.getJuesid()+"]");
			return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]角色保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]角色保存失败！");
		}finally{
			//加载角色数据
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
	}

	//删除角色+权限
	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			RoleForm roleForm = (RoleForm) actionForm;
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			List list = clerkDao.selectGuiyjsgxb(roleForm.getJuesid());
			if(list.size()!=0)
			{
				return this.showMessageJSP(actionMapping,request,"list","["+roleForm.getJuesid()+"]角色已经被柜员使用，不能删除!");
			}
			roleService.deleteRole(roleForm.getJuesid());
			this.createManageLog(clerk.getCode(), "角色管理[删除角色:"+roleForm.getJuesid()+"]");
			return this.showMessageJSP(actionMapping,request,"list","["+roleForm.getJuesid()+"]角色删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			//加载角色数据
			return this.showMessageJSP(actionMapping,request,"list","["+roleForm.getJuesid()+"]角色删除失败！");
		}finally{
			//加载角色数据
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
	}
	
	//修改角色+权限
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
			//加载角色数据
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
			return this.errrForLogAndException(e, actionMapping, request,"modify");
		}
		
	}
	
	//更新角色权限
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			RoleForm roleForm = (RoleForm) actionForm;
			Role role = new Role();
		try {
			String juesName = roleForm.getJuesmc();
			//判断角色名称是否已经被占用
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
				this.createManageLog(clerk.getCode(), "角色管理[修改角色:"+roleForm.getJuesid()+"]");
				return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]角色修改成功！");
			}else {
				List<Privilege> juesList = privilegeService.getPrivilegeList(roleForm.getJuesid());
				Role role_ = roleService.getRole(roleForm.getJuesid());
				roleForm.setJuesmc(role_.getJuesmc());
				roleForm.setJuesms(role_.getJuesms());
				roleForm.setBeiz(role_.getBeiz());
				roleForm.setJuesjb(role_.getJuesjb());
				String jsonString = BoTool.ListToJsonString(juesList);
				request.setAttribute("juesListJsonString", jsonString);
				return this.showMessageJSP(actionMapping,request,"modify","角色名称["+juesName+"]已经存在！");
			}
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping,request,"list","["+role.getJuesmc()+"]角色修改失败！");
		}finally{
			//加载角色数据
			List list = roleService.getAllRole();
			request.setAttribute("rolelist", list);
		}
		
	}

}