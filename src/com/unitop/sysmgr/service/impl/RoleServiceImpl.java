package com.unitop.sysmgr.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.Role;
import com.unitop.sysmgr.dao.PrivilegeDao;
import com.unitop.sysmgr.dao.RoleDao;
import com.unitop.sysmgr.service.RoleService;

@Service("RoleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	@Resource
	private PrivilegeDao PrivilegeDao;

	// 删除角色+权限明细
	public void deleteRole(String juesid) throws Exception {
		Role role = new Role();
		role.setJuesid(juesid);

		// 开启事务
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		roleDao.set_Session(session);
		PrivilegeDao.set_Session(session);
		try {
			// 删除角色信息
			roleDao.deleteRole(role);
			// 删除角色下的权限
			PrivilegeDao.deleteJuesqxgxb(juesid);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("角色保存异常!");
		} finally {
			PrivilegeDao.shifSession();
		}
	}

	// 获取所有权限
	public List getAllRole() {
		return roleDao.getAllRole();
	}

	// 获取角色
	public Role getRole(String role) {
		return roleDao.getRole(role);
	}

	// 更新角色
	public void update(Role role) throws Exception {
		roleDao.save(role);
	}
	//以角色名称获取角色
	public Role getRoleByName(String juesmc) {
		// TODO Auto-generated method stub
		return roleDao.getRoleByName(juesmc);
	}
}
