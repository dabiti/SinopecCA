package com.unitop.sysmgr.service.impl;

/***********************************************************************
 * Module:  KagServiceImpl.java
 * Author:  Administrator
 * Purpose: Defines the Class KagServiceImpl
 ***********************************************************************/

import java.util.*;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.ChoutId;
import com.unitop.sysmgr.bo.Kag;
import com.unitop.sysmgr.bo.KagRenw;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.YinjkId;
import com.unitop.sysmgr.dao.ChoutDao;
import com.unitop.sysmgr.dao.KagDao;
import com.unitop.sysmgr.dao.KagRenwDao;
import com.unitop.sysmgr.dao.OrgDao;
import com.unitop.sysmgr.dao.TabsDao;
import com.unitop.sysmgr.dao.YinjkDao;
import com.unitop.sysmgr.form.YinjkForm;
import com.unitop.sysmgr.service.KagService;
import com.unitop.sysmgr.bo.Chout;

@Service("KagServiceImpl")
public class KagServiceImpl extends BaseServiceImpl implements KagService {

	@Resource
	private KagDao kagDao;
	@Resource
	private KagRenwDao kagRenwDao;
	@Resource
	private YinjkDao yinjkDao;
	@Resource
	private ChoutDao choutDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	private TabsDao tabsDao;

	/**
	 * @param kagid
	 * @throws Exception
	 */
	public void delete(String kagid) throws Exception {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		kagDao.set_Session(session);
		choutDao.set_Session(session);
		try {
			kagDao.delete(kagid);
			choutDao.deleteChout(kagid);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("ɾ�������쳣!");
		} finally {
			kagDao.shifSession();
		}
	}

	/**
	 * @param kag
	 * @throws Exception
	 */
	public void save(Kag kag) throws Exception {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		kagDao.set_Session(session);
		choutDao.set_Session(session);
		try {
			kagDao.save(kag);
			for (int i = 0; i < kag.getCengs(); i++) {
				for (int j = 0; j < kag.getChouts(); j++) {
					Chout chout = new Chout();
					ChoutId choutid = new ChoutId();

					choutid.setCeng("" + i);
					choutid.setChoutwz("" + j);
					choutid.setKagid(kag.getKagid());
					chout.setChoutid(choutid);
					chout.setJigh(kag.getJigh());
					chout.setShengykj(kag.getChoutkj());
					chout.setShiykj(0);
					chout.setZongkj(kag.getChoutkj());
					chout.setChoutzt("0");
					choutDao.saveChout(chout);
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new Exception("��ʼ�������쳣!");
		} finally {
			kagDao.shifSession();
		}

	}

	/** @param kag */
	public void update(Kag kag) {
		// TODO: implement
	}

	/** @param orgcode */
	public List<Kag> kaglist(String orgcode) {
		return kagDao.kaglist(orgcode);
	}

	/** @param zhangh */
	public TabsBo getTask(String zhangh, String jigh) {
		String hql = "";
		Map map = new HashMap();
		if (zhangh == null || "".equals(zhangh)) {
			hql = "from KagRenw where renwzt<>'1'and jigh =:jigh ";
			map.put("jigh", jigh);
		} else {
			hql = "from KagRenw where renwzt<>'1' and zhangh =:zhangh ";
			map.put("zhangh", zhangh);
		}
		TabsBo tabsBo = tabsDao.pagingForHql(hql, this.tabsBo.getDangqym(),
				this.tabsBo.getFenysl(), map);
		return tabsBo;
	}

	public Chout divideSpace(String renwbs, String jigh, int count, String zuob)
			throws Exception {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		kagRenwDao.set_Session(session);
		choutDao.set_Session(session);
		Chout chout = new Chout();
		try {
			if (zuob != null && !"".equals(zuob)) {
				String[] zuobArr = zuob.split(",");
				ChoutId choutid = new ChoutId();
				choutid.setKagid(zuobArr[0]);
				choutid.setCeng(zuobArr[1]);
				choutid.setChoutwz(zuobArr[2]);
				chout.setChoutid(choutid);
				chout = choutDao.getChoutById(choutid);
			} else {
				chout = choutDao.getElseChout(jigh, count);// ��ȡһ�����ó���
				choutDao.updateChoutCount(chout.getChoutid(), count);
				kagRenwDao.updateZuob(chout.getChoutid(), renwbs);// �����������Ϸ���ĳ������꣬��ֹҳ��ˢ�£��޷�����
			}
			if ("0".equals(chout.getChoutzt())) {// ��������ǿ��еģ��������������״̬����������
				kagRenwDao.updateTaskState(renwbs, "2");
			}
			choutDao.updateChoutState(chout.getChoutid(), "1");// ���³���״̬Ϊ����ʹ��
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new Exception("�򿪿��񣬷���ռ��쳣!");
		} finally {
			kagRenwDao.shifSession();
		}
		return chout;
	}

	public List<Org> getKagOrgs(String orgcode) {
		return orgDao.getKagOrgs(orgcode);
	}

	public TabsBo getYinjk(YinjkForm yinjkForm) {
		//String hql = "select zhangh,jigh,kagid,ceng,choutwz,case yewlx when '0' then '����' else '��ʷ' end as yewlx,shifzk,qiyrq from yinjk where kagid<>'' and";
		String hql = "select zhangh,internalorganizationnumber,kagid,ceng,choutwz,case yewlx when '0' then '����' else '��ʷ' end as yewlx,shifzk,qiyrq,yinjkh from yinjk where (kagid is not null or kagid<>'') and";
		String zhangh = yinjkForm.getZhangh();
		String yewlx = yinjkForm.getYewlx();
		String jigh = yinjkForm.getJigh();
		String hqlwhere = "";
		if (zhangh != null && !"".equals(zhangh)) {
			hqlwhere += " zhangh='" + zhangh + "' and";
		}
		if (yewlx != null && !"".equals(yewlx)) {
			hqlwhere += " yewlx='" + yewlx + "' and";
		}
		if (jigh != null && !"".equals(jigh)) {
			hqlwhere += " internalorganizationnumber='" + jigh + "' and";
		}
		hql += hqlwhere;
		hql = hql.substring(0, hql.length() - 3);
		hql += " group by (zhangh,internalorganizationnumber,kagid,ceng,choutwz,yewlx,shifzk,qiyrq,yinjkh)";
		TabsBo tabsBo = tabsDao.pagingObjectForSql(hql, this.tabsBo
				.getDangqym(), this.tabsBo.getFenysl(), null);
		List<Yinjk> yinjklist = new ArrayList<Yinjk>();
		try {
			for (Iterator iter = tabsBo.getList().iterator(); iter.hasNext();) {
				Yinjk yinjk = new Yinjk();
				Object[] element = (Object[]) iter.next();
				YinjkId yinjkid = new YinjkId();
				yinjkid.setZhangh((String) element[0]);
				yinjk.setYinjkid(yinjkid);
				yinjk.setJigh((String) element[1]);
				yinjk.setKagid((String) element[2]);
				yinjk.setCeng(element[3].toString());
				yinjk.setChoutwz(element[4].toString());
				yinjk.setYewlx(element[5].toString());
				yinjk.setShifzk(element[6].toString());
				yinjk.getYinjkid().setQiyrq(element[7].toString());
				yinjk.getYinjkid().setYinjkh(element[8].toString());
				yinjklist.add(yinjk);
			}
			tabsBo.setList(yinjklist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tabsBo;
	}

	public void createKagRenw(KagRenw kagrenw) {
		kagRenwDao.createKagRenw(kagrenw);
	}

	public void updateShifzk(String yinjkUpdateSql) {
		yinjkDao.updateShifzk(yinjkUpdateSql);
	}

	public void closeKag(String renwbs, String renwzt, String zuob,
			String choutzt) throws Exception {
		ChoutId choutid = new ChoutId();
		String[] zuobArr = zuob.split(",");
		choutid.setKagid(zuobArr[0]);
		choutid.setCeng(zuobArr[1]);
		choutid.setChoutwz(zuobArr[2]);

		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		kagRenwDao.set_Session(session);
		choutDao.set_Session(session);
		try {
			choutDao.updateChoutState(choutid, choutzt);
			kagRenwDao.updateTaskState(renwbs, renwzt);
			KagRenw kagRenw = kagRenwDao.getTaskById(renwbs);
			// if("4".equals(kagRenw.getYewlx())){
			// List list = yinjkDao.getYinjkByZhangh(kagRenw.getZhangh());
			// choutDao.releaseChoutSpace(choutid, list.size());
			// }�ͷſռ䣬����Ŀʵ���������
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new Exception(e.getMessage());
		} finally {
			kagRenwDao.shifSession();
		}
	}

	public Kag getKagById(String kagid) {
		Kag kag = kagDao.getKagById(kagid);
		return kag;
	}

	public Map<String, String> getChoutSpaceByKagId(String kagid) {
		List<Chout> choutlist = choutDao.getChoutSpaceByKagId(kagid);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < choutlist.size(); i++) {
			Chout chout = choutlist.get(i);
			String spaceRate = chout.getShiykj() + "/" + chout.getZongkj();
			map.put(kagid + chout.getChoutid().getCeng()
					+ chout.getChoutid().getChoutwz(), spaceRate);
		}
		return map;
	}

	public int getUsedChoutCount(String kagid) {
		List<Chout> choutlist = choutDao.getUsedChout(kagid);
		return choutlist.size();
	}

	public void deleteTask(String renwbs) throws Exception {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		kagRenwDao.set_Session(session);
		yinjkDao.set_Session(session);
		try {
			KagRenw kagRenw = kagRenwDao.getTaskById(renwbs);
			kagRenwDao.deleteKagRenw(renwbs);
			String yewlx = kagRenw.getYewlx();
			yewlx = yewlx.trim();
			String shifzk = "1";
			if ("2".equals(yewlx)) {// ���Ϊ2����������ӡ������ǰ״̬Ϊ0���ڿ⣬�ع���1�ڿ�
				shifzk = "1";
			} else {
				shifzk = "0";
			}
			String yinjkUpdateSql = "update yinjk set shifzk = '" + shifzk
					+ "' where zhangh='" + kagRenw.getZhangh()
					+ "' and qiyrq='" + kagRenw.getQiyrq() + "'";
			yinjkDao.updateShifzk(yinjkUpdateSql);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new Exception("ɾ������ʱ�����쳣");
		} finally {
			kagRenwDao.shifSession();
		}
	}

	//����ӡ������Ϣ
	public void saveyinjk(Yinjk yinjk) {
		yinjkDao.saveyinjk(yinjk);
	}
	//ͨ�������ʶ��ȡӡ������
	public KagRenw getYinjkh(String renwbs) {
		return yinjkDao.getYinjkh(renwbs);
	}

	//��ȡӡ������Ϣ
	public Yinjk getYinjk(String zhangh, String yinjkh, String qiyrq) {
		return yinjkDao.getYinjk(zhangh, yinjkh, qiyrq);
	}
}