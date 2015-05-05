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

	// ɾ����ɫ+Ȩ����ϸ
	public void deleteRole(String juesid) throws Exception {
		Role role = new Role();
		role.setJuesid(juesid);

		// ��������
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		roleDao.set_Session(session);
		PrivilegeDao.set_Session(session);
		try {
			// ɾ����ɫ��Ϣ
			roleDao.deleteRole(role);
			// ɾ����ɫ�µ�Ȩ��
			PrivilegeDao.deleteJuesqxgxb(juesid);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("��ɫ�����쳣!");
		} finally {
			PrivilegeDao.shifSession();
		}
	}

	// ��ȡ����Ȩ��
	public List getAllRole() {
		return roleDao.getAllRole();
	}

	// ��ȡ��ɫ
	public Role getRole(String role) {
		return roleDao.getRole(role);
	}

	// ���½�ɫ
	public void update(Role role) throws Exception {
		roleDao.save(role);
	}
	//�Խ�ɫ���ƻ�ȡ��ɫ
	public Role getRoleByName(String juesmc) {
		// TODO Auto-generated method stub
		return roleDao.getRoleByName(juesmc);
	}
}
