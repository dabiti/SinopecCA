package com.unitop.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import com.unitop.sysmgr.bo.Chanpgncd;
import com.unitop.sysmgr.bo.Guiyjsgxb;
import com.unitop.sysmgr.bo.Juesqxgxb;
import com.unitop.sysmgr.bo.Privilege;
import com.unitop.sysmgr.bo.Role;
import com.unitop.sysmgr.dao.PrivilegeDao;
import com.unitop.sysmgr.dao.RoleDao;
import com.unitop.sysmgr.service.PrivilegeService;

@Service("PrivilegeServiceImpl")
public class PrivilegeServiceImpl extends BaseServiceImpl implements
		PrivilegeService {

	@Resource
	private PrivilegeDao PrivilegeDao = null;
	@Resource
	private RoleDao RoleDao = null;

	// ��ɫID���� �ý�ɫ��Ȩ����ϸ һ����
	public List getPrivilegeList(String juesid) {
		Map juesMap = new HashMap();
		this.getPrivilegeMap(juesid, juesMap);
		List list = PrivilegeDao.getAllPrivilegeForJuesgl();
		List allPrivilegeList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Chanpgncd chanpgncd = (Chanpgncd) list.get(i);
			Privilege privilege = new Privilege();
			if ("0".equals(chanpgncd.getId().getZignid())) {
				privilege.setId(chanpgncd.getId().getGongnid());
			} else {
				privilege.setId(chanpgncd.getId().getGongnid() + "|" + chanpgncd.getId().getZignid());
			}
			privilege.setpId(chanpgncd.getShangjgn());
			privilege.setName(chanpgncd.getGongnmc());

			allPrivilegeList.add(privilege);
		}
		for (int i = 0; i < allPrivilegeList.size(); i++) {
			Privilege privilege = (Privilege) allPrivilegeList.get(i);
			if (juesMap.get(privilege.getId()) != null) {
				privilege.setChecked(true);
			}
		}
		return allPrivilegeList;
	}

	private static List allPrivilegeList = null;

	// ��������Ȩ���б�Ȩ��ʵ��ת�� Chanpgncd>Privilege
	public List getAllPrivilege() {

		// �����Ʒ�˵���Ϣ
		if (allPrivilegeList == null) {
			allPrivilegeList = new ArrayList();
		} else {
			return allPrivilegeList;
		}

		List list = PrivilegeDao.getAllPrivilegeForJuesgl();
		for (int i = 0; i < list.size(); i++) {
			Chanpgncd chanpgncd = (Chanpgncd) list.get(i);
			Privilege privilege = new Privilege();
			if ("0".equals(chanpgncd.getId().getZignid())) {
				privilege.setId(chanpgncd.getId().getGongnid());
			} else {
				privilege.setId(chanpgncd.getId().getGongnid() + "|" + chanpgncd.getId().getZignid());
			}
			privilege.setpId(chanpgncd.getShangjgn());
			privilege.setName(chanpgncd.getGongnmc());

			allPrivilegeList.add(privilege);
		}

		return allPrivilegeList;
	}
	
	public Map getPrivilegeMap(){
		Map pivilegeMap = new HashMap();
		//�����Ʒ�˵���Ϣ
		if (allPrivilegeList == null) 
		{
			allPrivilegeList = this.getAllPrivilege();
		}
		for (int i = 0 ; i < allPrivilegeList.size() ; i++)
		{
			Privilege pri = (Privilege) allPrivilegeList.get(i);
			pivilegeMap.put(pri.getId(),"");
		}
		return pivilegeMap;
	}

	/**
	 * ���˵�����ʹ��
	 * 
	 * @return
	 */
	public List getChanpcd() {
		allPrivilegeList = new ArrayList();
		List list = PrivilegeDao.getAllPrivilege();
		for (int i = 0; i < list.size(); i++) {
			Chanpgncd chanpgncd = (Chanpgncd) list.get(i);
			Privilege privilege = new Privilege();
			if ("0".equals(chanpgncd.getId().getZignid())) {
				privilege.setId(chanpgncd.getId().getGongnid());
			} else {
				privilege.setId(chanpgncd.getId().getGongnid() + "|" + chanpgncd.getId().getZignid());
			}
			privilege.setpId(chanpgncd.getShangjgn());
			privilege.setName(chanpgncd.getGongnmc());
			allPrivilegeList.add(privilege);
		}

		return allPrivilegeList;
	}

	// ����Ȩ�޹�ϵ
	public void saveJuesqxgxbList(Role role, List privileList) throws Exception {
		List list = new ArrayList();
		int size = privileList.size();
		for (int i = 0; i < size; i++) {
			Privilege privilege = (Privilege) privileList.get(i);
			if (privilege.getChecked()) {
				Juesqxgxb Juesqxgx = new Juesqxgxb(role.getJuesid(), privilege .getId(), privilege.getBeiz());
				list.add(Juesqxgx);
			}
		}

		// �������� 
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		RoleDao.set_Session(session);
		PrivilegeDao.set_Session(session);
		// �����ɫ
		try {
			RoleDao.save(role);
			PrivilegeDao.deleteJuesqxgxb(role.getJuesid());
			PrivilegeDao.saveJuesqxgxbList(list);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("��ɫ�����쳣!");
		} finally {
			PrivilegeDao.shifSession();
		}
	}

	// ����Ȩ�޹�ϵ
	public void updateJuesqxgxbList(Role role, List privileList)
			throws Exception {
		List list = new ArrayList();
		int size = privileList.size();
		for (int i = 0; i < size; i++) {
			Privilege privilege = (Privilege) privileList.get(i);
			if (privilege.getChecked()) {
				Juesqxgxb Juesqxgx = new Juesqxgxb(role.getJuesid(), privilege.getId(), privilege.getBeiz());
				list.add(Juesqxgx);
			}
		}

		// ��������
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();

		RoleDao.set_Session(session);
		PrivilegeDao.set_Session(session);
		try {
			// �����ɫ
			RoleDao.save(role);
			// �������Ȩ�޹�ϵ
			PrivilegeDao.deleteJuesqxgxb(role.getJuesid());
			PrivilegeDao.saveJuesqxgxbList(list);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("��ɫ�����쳣!");
		} finally {
			PrivilegeDao.shifSession();
		}
	}

	// ���ݹ�ԱID���� �ý�ɫ��Ȩ����ϸ �˵���֤��ȡ
	public Map getPrivilegeForMenue(String clerkCode) {
		Map juesMap = new HashMap();
		List roleList = RoleDao.getRoleByClerkCode(clerkCode);

		if (roleList == null) {
			roleList = new ArrayList();
		}

		int roleSize = roleList.size();

		for (int n = 0; n < roleSize; n++) {
			Guiyjsgxb guiyjsgxb = (Guiyjsgxb) roleList.get(n);
			String juesID = guiyjsgxb.getId().getJuesid();
			this.getPrivilegeMap(juesID, juesMap);
		}
		return juesMap;
	}

	// ��ɫID���� �ý�ɫ��Ȩ����ϸ Map
	private Map getPrivilegeMap(String juesID, Map juesMap) {
		List list = PrivilegeDao.getPrivilegeByJuesid(juesID);
		if (list == null) {
			list = new ArrayList();
		}
		
		List privateList = this.getAllPrivilege();
		Map privateListMap = new HashMap();
		for (int i = 0; i < privateList.size(); i++) {
			Privilege privilege = (Privilege) privateList.get(i);
			privateListMap.put(privilege.getId(), privilege);
		}
		for (int i = 0; i < list.size(); i++) {
			Juesqxgxb juesqxgxb = (Juesqxgxb) list.get(i);
			Privilege privilege = new Privilege();
			privilege.setId(juesqxgxb.getId().getQuanxid());
			Privilege privilegeTemp = (Privilege) privateListMap.get(juesqxgxb.getId().getQuanxid());
			if (privilegeTemp == null)
				privilegeTemp = new Privilege();
			privilege.setpId(privilegeTemp.getpId());
			privilege.setName(privilegeTemp.getName());
			privilege.setBeiz(juesqxgxb.getBeiz());
			juesMap.put(privilege.getId(), "");
		}
		return juesMap;
	}

}
