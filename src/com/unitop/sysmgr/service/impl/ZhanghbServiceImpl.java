package com.unitop.sysmgr.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.sinopec.table.model.SqlParameter;
import com.sinopec.table.model.Table;
import com.unitop.exception.BusinessException;
import com.unitop.exception.DAOException;
import com.unitop.framework.util.ExpOrImp;
import com.unitop.framework.util.JsonTool;
import com.unitop.sysmgr.bo.AccountNum;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhanghtbb;
import com.unitop.sysmgr.bo.Zhanghxzb;
import com.unitop.sysmgr.dao.DanbwhDao;
import com.unitop.sysmgr.dao.SystemDao;
import com.unitop.sysmgr.dao.TabsDao;
import com.unitop.sysmgr.dao.YinjbDao;
import com.unitop.sysmgr.dao.YinjkDao;
import com.unitop.sysmgr.dao.YinjzhbDao;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.dao.ZhanghtbbDao;
import com.unitop.sysmgr.dao.ZhuczhgxDao;
import com.unitop.sysmgr.service.SystemMgrService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.util.CommonUtil;
import com.unitop.util.DateUtil;
import com.unitop.util.Paritybitcal;

@Service("ZhanghbServiceImpl")
public class ZhanghbServiceImpl extends BaseServiceImpl implements
		ZhanghbService {

	private static final Category log = Logger.getLogger(ZhanghbServiceImpl.class);
	@Resource
	private ZhanghbDao ZhanghbDao;
	@Resource
	private YinjbDao YinjbDao;
	@Resource
	private YinjzhbDao YinjzhbDao;
	@Resource
	private SystemDao SystemDao;
	@Resource
	private DanbwhDao DanbwhDao;
	@Resource
	private ZhuczhgxDao zhuczhgxDao;
	@Resource
	private YinjkDao YinjkDao;
	@Resource
	private SystemMgrService systemMgrService;
	@Resource
	private TabsDao TabsDao;
	@Resource
	private ZhanghtbbDao zhanghtbbDao;
	private final String insertintozhanghb_excel="insert into zhanghb_excel (ZHANGH,F_INTERNALORGANIZATIONNUMBER, INTERNALORGANIZATIONNUMBER, LEGALNAME, ZHANGHXZ, KAIHRQ, TONGCTD, SHIFDH, LIANXR, AREACODE, PHONENUMBER, EXTENSIONNUMBER, LIANXR2, AREACODE2, PHONENUMBER2, EXTENSIONNUMBER2, FUZR, AREACODE3, PHONENUMBER3, EXTENSIONNUMBER3, FUZR2, AREACODE4, PHONENUMBER4, EXTENSIONNUMBER4, ZHUZH, FUYRQ, YINJKH, BEIZ,YINJKQZH)"
							+"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
	/*
		create table zhanghb_excel(
		zhangh varchar2(40),
		LEGALNAME   VARCHAR2(100),
		f_INTERNALORGANIZATIONNUMBER varchar2(20),
		INTERNALORGANIZATIONNUMBER varchar2(20),
		zhanghxz varchar2(40),
		kaihrq varchar2(20),
		tongctd varchar2(20),
		shifdh varchar2(10),
		lianxr varchar2(50),
		areacode varchar2(40),
		PHONENUMBER varchar2(40),
		EXTENSIONNUMBER varchar2(40),
		lianxr2 varchar2(50),
		areacode2 varchar2(40),
		PHONENUMBER2 varchar2(40),
		EXTENSIONNUMBER2 varchar2(40),
		FUZR  VARCHAR2(50),
		areacode3 varchar2(40),                                    
		PHONENUMBER3  VARCHAR2(40),
		EXTENSIONNUMBER3 varchar2(40),
		FUZR2      VARCHAR2(50),
		areacode4 varchar2(40),                                    
		PHONENUMBER4   VARCHAR2(40),
		EXTENSIONNUMBER4 varchar2(40),
		zhuzh varchar2(40),
		fuyrq varchar2(20),
		yinjkh varchar2(40),
		beiz varchar2(500));
)*/
	
	// ��ȡ�ʺ���Ϣ
	@SuppressWarnings("unchecked")
	public Zhanghb getZhanghb(String zhangh) throws BusinessException {
		Zhanghb zhanghTemp = ZhanghbDao.getZhanghb(zhangh);
		/*
		 * if(zhanghTemp ==null) { throw new BusinessException("�˺Ų�����!"); } List
		 * list = YinjbDao.getYinj(zhangh); if(null != list) { int size
		 * =list.size(); String yinjkbh =""; Map tMap = new HashMap(); for(int
		 * i=0;i<size;i++) { Yinjb yinjb = (Yinjb)list.get(i); if(i==0) {
		 * yinjkbh+=yinjb.getYinjkbh()+"<br>"; tMap.put(yinjb.getYinjkbh(), "");
		 * } else{ if(tMap.get(yinjb.getYinjkbh())==null) {
		 * yinjkbh+=yinjb.getYinjkbh()+"<br>"; tMap.put(yinjb.getYinjkbh(), "");
		 * } } } if(yinjkbh.length()!=0)
		 * zhanghTemp.setYinjkbh(yinjkbh.substring(0, yinjkbh.length()-4)); }
		 * //���û�ȡӡ���ʺ�
		 * if("".equals(zhanghTemp.getZhuzh())||null==(zhanghTemp.getZhuzh())) {
		 * zhanghTemp.setZhangh_(zhanghTemp.getZhangh()); }else{
		 * zhanghTemp.setZhangh_(zhanghTemp.getZhuzh()); }
		 */
		// zhanghTemp.setHuobh("["+zhanghTemp.getHuobh()+"]"+SystemDao.getHuobh(zhanghTemp.getHuobh()).getHuobmc());
		return zhanghTemp;
	}

	// �ʺ�����ɾ��
	public void physicsdelete(String zhangh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		ZhanghbDao.set_Session(session);
		YinjbDao.set_Session(session);
		YinjzhbDao.set_Session(session);
		YinjkDao.set_Session(session);
		try {
			Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
			// ɾ���ʺ���Ϣ
			ZhanghbDao.deleteZhanghb(zhanghb);
			// ɾ��ӡ��
			YinjbDao.delYinj(zhangh);
			// ɾ��ӡ�����
			YinjzhbDao.delYinjzh(zhangh);
			// ɾ��ӡ����
			YinjkDao.deleteYinjk(zhangh);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			ZhanghbDao.shifSession();
		}
	}

	// �ʺ������ָ�
	public void recoverAccount(String zhangh, boolean yinjkFlag,
			String yzhanghxz) {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		ZhanghbDao.set_Session(session);
		YinjbDao.set_Session(session);
		YinjzhbDao.set_Session(session);
		YinjkDao.set_Session(session);
		try {
			Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
			zhanghb.setZhanghzt(yzhanghxz);
			zhanghb.setTingyrq("");
			ZhanghbDao.updateZhanghb(zhanghb);
			String zhuzh = zhanghb.getZhuzh();
			if (yinjkFlag) {
				if (zhuzh != null && !zhuzh.trim().equals("")) {
					YinjkDao.resumeYinjk(zhuzh);
				} else {
					YinjkDao.resumeYinjk(zhanghb.getZhangh());
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			ZhanghbDao.shifSession();
		}

	}

	public void recoverAccount(String zhangh, boolean yinjkFlag) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		ZhanghbDao.set_Session(session);
		YinjbDao.set_Session(session);
		YinjzhbDao.set_Session(session);
		YinjkDao.set_Session(session);
		try {
			Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
			zhanghb.setZhanghzt("��Ч");
			ZhanghbDao.updateZhanghb(zhanghb);
			String zhuzh = zhanghb.getZhuzh();
			if (yinjkFlag) {
				if (zhuzh != null && !zhuzh.trim().equals("")) {
					YinjkDao.resumeYinjk(zhuzh);
				} else {
					YinjkDao.resumeYinjk(zhanghb.getZhangh());
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			ZhanghbDao.shifSession();
		}

	}

	// �˻�����ͳ��
	public AccountNum zhanghNumber(String orgCode, String huobh, String shifbhxj) {
		String zhanghxzSQL = " and zhanghxz='" + huobh + "' ";
		if (huobh.equals("ȫ��")) {
			zhanghxzSQL = " ";
		}
		String shifbhxjSQL = " internalorganizationnumber =? ";
		if ("��".equals(shifbhxj)) {
			String db_type = DanbwhDao.getDBtype();
			if ("oracle".equals(db_type)) {
				shifbhxjSQL = " internalorganizationnumber in (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber = ?) ";
			} else {
				shifbhxjSQL = " internalorganizationnumber in (select internalorganizationnumber from TABLE(ORGFUNCTION(?))) ";
			}
		}
		shifbhxjSQL += zhanghxzSQL;
		// -- ����
		String zhanghzs = "select count(*) as zhanghzs  from zhanghb where "
				+ shifbhxjSQL;

		// --��ӡ��
		String yinj_zhanghzs = "select count(*) as zhanghzs  from zhanghb where "
				+ shifbhxjSQL + " and youwyj=? ";
		String yinj_zhanghzt_shul = "select count(*) as yinj_zhanghzt_zs  from zhanghb where "
				+ shifbhxjSQL + "  and youwyj=?  and zhanghzt =? ";
		AccountNum accountNum = new AccountNum();
		try {
			Table table = new Table(zhanghzs);
			table.getSqlParameter().put(1,new SqlParameter("internalorganizationnumber", "string",orgCode));
			List list = DanbwhDao.doSelect(table);
			Map map = (Map) list.get(0);
			accountNum.setZhanghzs(String.valueOf(map.get("zhanghzs")));

			table.setSqlString(yinj_zhanghzs);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs(String.valueOf(map
					.get("zhanghzs")));


			table.setSqlString(yinj_zhanghzs);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setWuyj_zhanghzs(String
					.valueOf(map.get("zhanghzs")));

			
			table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","��Ч"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_youx(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","����"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_xiaoh(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

		/*	table.setSqlString(youyj_zhanghzs_you_dongj);
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_dongj(String.valueOf(map
					.get("youyj_zhanghzs_you_dongj")));

			table.setSqlString(youyj_zhanghzs_you_guas);
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_guas(String.valueOf(map
					.get("youyj_zhanghzs_you_guas")));*/

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","��Ч"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_youx(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","����"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_xiaoh(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			/*table.setSqlString(youyj_zhanghzs_wu_dongj);
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_dongj(String.valueOf(map
					.get("youyj_zhanghzs_wu_dongj")));

			table.setSqlString(youyj_zhanghzs_wu_guas);
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_guas(String.valueOf(map
					.get("youyj_zhanghzs_wu_guas")));
*/
			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","����"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_jiux(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","��������"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_zxac(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","����"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_jiux(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","��"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","��������"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_zxac(String.valueOf(map
					.get("yinj_zhanghzt_zs")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountNum;
	}

	public List getKehh(String kehh, String jigh) {
		return ZhanghbDao.getKehh(kehh, jigh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unitop.sysmgr.service.ZhanghbService#closeAccount(java.lang.String)
	 */
	public void closeAccount(String zhangh, List<String> yinjkhList) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		ZhanghbDao.set_Session(session);
		// YinjbDao.set_Session(session);
		// YinjzhbDao.set_Session(session);
		YinjkDao.set_Session(session);
		try {
			Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
			zhanghb.setZhanghzt("����");
			zhanghb.setTingyrq(SystemDao.getSystetemNowDate().substring(0, 10));
			ZhanghbDao.updateZhanghb(zhanghb);
			String zhuzh = zhanghb.getZhuzh();
			if (zhuzh == null || zhuzh.trim().equals("")) {
				int num = this.queryShareAccountNum(zhangh);
				if (num <= 0) {
					for (String string : yinjkhList) {
						YinjkDao.cancleYinjk(string);
					}
				}
			} else {
				Zhanghb zhuzhb = ZhanghbDao.getZhanghb(zhuzh);
				if (zhuzhb.getZhanghzt().equals("����")) {
					int num = queryShareAccountNum(zhuzh);
					if (num <= 0) {
						for (String string : yinjkhList) {
							YinjkDao.cancleYinjk(string);
						}
					}
				}
			}

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			ZhanghbDao.shifSession();
		}

	}

	public boolean canCancleYinjk(String zhangh) {
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
		if (zhanghb == null) {
			return false;
		}
		List<Zhanghb> zhanghbList = ZhanghbDao.getZhanghbByZzh(zhangh);
		// ���˺�Ϊ����ӡ�����Ų�Ϊ���ҹ����˻��б�Ϊ�� ������
		if (((zhanghb.getZhuzh() == null || zhanghb.getZhuzh().trim()
				.equals(""))
				&& zhanghb.getYinjkbh() != null && !zhanghb.getYinjkbh().trim()
				.equals(""))
				&& (zhanghbList == null || zhanghbList.size() == 0)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean canResumeYinjk(String zhangh) {
		// boolean pass =false;
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
		if (zhanghb == null) {
			return false;
		}
		List<Zhanghb> zhanghbList = ZhanghbDao.getZhanghbByZzh(zhangh);
		if (((zhanghb.getZhuzh() == null || zhanghb.getZhuzh().trim()
				.equals(""))
				&& zhanghb.getYinjkbh() != null && !zhanghb.getYinjkbh().trim()
				.equals(""))
				|| (zhanghbList != null && zhanghbList.size() != 0)) {
			return true;
		} else {
			return false;
		}
		/*
		 * String yinjkh=zhanghb.getYinjkbh();
		 * if(yinjkh==null||yinjkh.trim().equals("")){ String
		 * zhuzh=zhanghb.getZhuzh(); if(zhuzh==null||zhuzh.trim().equals("")){
		 * return false; }else{
		 * yinjkh=ZhanghbDao.getZhanghb(zhuzh).getYinjkbh();
		 * if(yinjkh==null||yinjkh.trim().equals("")){ return false; }
		 * pass=checkYinjkzt(yinjkh); } }else{ pass=checkYinjkzt(yinjkh); }
		 * return pass;
		 */
	}

	private boolean checkYinjkzt(String yinjkh) {
		boolean pass = false;
		List<String> yinjkhList = new ArrayList<String>();

		if (yinjkh != null && yinjkh.trim().length() != 0) {
			yinjkh = yinjkh.replace("|", ",");
			String[] str = yinjkh.split(",", 0);
			for (int i = 0; i < str.length; i++) {
				if (i == 0) {
					yinjkhList.add(str[0]);
				} else if (i == 1) {
					yinjkhList
							.add(str[1] == null || str[1] == "" ? "" : str[1]);
				} else if (i == 2) {
					yinjkhList
							.add(str[2] == null || str[2] == "" ? "" : str[2]);
				} else if (i == 3) {
					yinjkhList
							.add(str[3] == null || str[3] == "" ? "" : str[3]);
				}
			}
		}
		for (String string : yinjkhList) {
			Yinjk yinjk = YinjkDao.getYinjkByYinjkbh(string);
			if (yinjk.getYinjkzt().equals("����")) {
				pass = true;
			}
		}
		return pass;
	}

	public void cancleYinjk(String zhangh, List<String> yinjkhList) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		ZhanghbDao.set_Session(session);
		// YinjbDao.set_Session(session);
		// YinjzhbDao.set_Session(session);
		YinjkDao.set_Session(session);
		transaction.begin();
		try {
			Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
			if (zhanghb == null) {
				return;
			}
			if (!zhanghb.getZhanghzt().equals("����")) {
				return;
			}
			int num = this.queryShareAccountNum(zhangh);
			if (num <= 0) {
				for (String string : yinjkhList) {
					YinjkDao.cancleYinjk(string);
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			ZhanghbDao.shifSession();
		}
	}

	/*
	 * ��ȡ���˻��б���Ϣ by wp
	 */
	public List getzzhlist(String account) throws SQLException {
		return zhuczhgxDao.getzizh(account);
	}

	// ����
	public void dongjAccount(String zhangh) {
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
		zhanghb.setZhanghzt("����");
		ZhanghbDao.updateZhanghb(zhanghb);

	}

	// ��ʧ
	public void guasAccount(String zhangh) {
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
		zhanghb.setZhanghzt("��ʧ");
		ZhanghbDao.updateZhanghb(zhanghb);

	}

	public void createZhanghb(Zhanghb zhanghb, List<String> yinjkbhList) throws Exception {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		ZhanghbDao.set_Session(session);
		YinjkDao.set_Session(session);
		try {
			ZhanghbDao.updateZhanghb(zhanghb);
			if (yinjkbhList != null) {
				for (String string : yinjkbhList) {
					if (string != null && string.trim().length() != 0) {
						Yinjk yinjk = new Yinjk();
						yinjk.setYinjkh(string);
						yinjk.setZhangh(zhanghb.getZhangh());
						yinjk.setYinjkzt("����");
						yinjk.setJigh(zhanghb.getJigh());
						yinjk.setQiyrq(zhanghb.getKaihrq());
						YinjkDao.saveyinjk(yinjk);
					}
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new Exception(e);
		} finally {
			ZhanghbDao.shifSession();
		}
	}

	public List getYinjb(String account) {
		List list = YinjbDao.getYinj(account);
		return list;
	}

	public void update(Zhanghb zhanghb) throws Exception{
		ZhanghbDao.updateZhanghb(zhanghb);

	}

	public Zhanghb getZhanghbByYinjkbh(String yinjkbh) {

		return ZhanghbDao.getZhanghbByYinjkbh(yinjkbh);
	}

	public ZhanghbDao getZhanghbDao() {
		return ZhanghbDao;
	}

	public void setZhanghbDao(ZhanghbDao zhanghbDao) {
		ZhanghbDao = zhanghbDao;
	}

	public YinjbDao getYinjbDao() {
		return YinjbDao;
	}

	public void setYinjbDao(YinjbDao yinjbDao) {
		YinjbDao = yinjbDao;
	}

	public YinjzhbDao getYinjzhbDao() {
		return YinjzhbDao;
	}

	public void setYinjzhbDao(YinjzhbDao yinjzhbDao) {
		YinjzhbDao = yinjzhbDao;
	}

	public SystemDao getSystemDao() {
		return SystemDao;
	}

	public void setSystemDao(SystemDao systemDao) {
		SystemDao = systemDao;
	}

	public DanbwhDao getDanbwhDao() {
		return DanbwhDao;
	}

	public void setDanbwhDao(DanbwhDao danbwhDao) {
		DanbwhDao = danbwhDao;
	}

	public ZhuczhgxDao getZhuczhgxDao() {
		return zhuczhgxDao;
	}

	public void setZhuczhgxDao(ZhuczhgxDao zhuczhgxDao) {
		this.zhuczhgxDao = zhuczhgxDao;
	}

	public YinjkDao getYinjkDao() {
		return YinjkDao;
	}

	public void setYinjkDao(YinjkDao yinjkDao) {
		YinjkDao = yinjkDao;
	}

	public SystemMgrService getSystemMgrService() {
		return systemMgrService;
	}

	public void setSystemMgrService(SystemMgrService systemMgrService) {
		this.systemMgrService = systemMgrService;
	}

	public TabsDao getTabsDao() {
		return TabsDao;
	}

	public void setTabsDao(TabsDao tabsDao) {
		TabsDao = tabsDao;
	}

	public ZhanghtbbDao getZhanghtbbDao() {
		return zhanghtbbDao;
	}

	public void setZhanghtbbDao(ZhanghtbbDao zhanghtbbDao) {
		this.zhanghtbbDao = zhanghtbbDao;
	}

	public String getXiaohsj(String zhangh) {

		Session session = super.getBaseHibernateDao().getHibernateSession();
		String sql = "select managedate from accountmanagelog where managetype=:managetype and zhangh =:zhangh order by managedate desc,managetime desc";
		String xiaohsj = "";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			// query.addEntity(AccountManageLog.class);
			query.setString("managetype", "4");
			query.setString("zhangh", zhangh);

			List list = query.list();

			xiaohsj = (String) list.get(0);

			xiaohsj = xiaohsj == null ? "" : xiaohsj;
		} catch (Exception e) {
			xiaohsj = "";
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}

		return xiaohsj;
	}

	public String queryXiaohqzt(String zhangh) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		String sql = "select managecontent from accountmanagelog where managetype=:managetype and zhangh =:zhangh order by managedate desc,managetime desc";
		String yzhanghxz = "��Ч";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			// query.addEntity(AccountManageLog.class);
			query.setString("managetype", "4");
			query.setString("zhangh", zhangh);

			List list = query.list();
			yzhanghxz = (String) list.get(0);
			yzhanghxz = yzhanghxz == null ? "��Ч"
					: yzhanghxz.indexOf("����") != -1 ? "����" : yzhanghxz
							.indexOf("��������") != -1 ? "��������" : "��Ч";
			// yzhanghxz = yzhanghxz == null ? "��Ч" : yzhanghxz;
		} catch (Exception e) {
			return yzhanghxz;
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}

		return yzhanghxz;
	}

	// ��ȡ ӡ�������õ��˺�
	public TabsBo getYinjkShareList(String orgcode, String account,
			String yinjkbh) {
		Map parameterMap = new HashMap();
		String hql = "";
		TabsBo tabs = null;
		List<Zhanghb> zhanghbList = null;
		// �˺�
		if ((account == null || "".equals(account))
				&& (yinjkbh == null || "".equals(yinjkbh.trim()))) {
			// hql = "from Zhanghb where jigh = :jigh and zhanghzt <> '����' ";
			hql = "select distinct a.* from zhanghb a  start with a.zhuzh is not null and a.internalorganizationnumber=:jigh and zhanghzt<>'����' connect by prior a.zhuzh=a.zhangh order by a.legalname,a.zhuzh desc,a.zhangh ";
			parameterMap.put("jigh", orgcode);
			tabs = TabsDao.pagingObjectForSql(hql, this.tabsBo.getDangqym(),
					this.tabsBo.getFenysl(), parameterMap);
			List<Object[]> list = tabs.getList();
			zhanghbList = new ArrayList<Zhanghb>();
			if (list != null) {
				for (Object[] objects : list) {
					zhanghbList.add(createZhanghb(objects));
				}
				if (zhanghbList != null && zhanghbList.size() != 0) {
					for (Zhanghb zhanghb : zhanghbList) {
						zhanghb.setYinjkbh(ZhanghbDao.getYinjkh(zhanghb
								.getZhangh()));
					}
				}
			}
			tabs.setList(zhanghbList);
			return tabs;
		} else {
			// ������+�˺�
			if ((account != null && !"".equals(account))
					&& (yinjkbh == null || "".equals(yinjkbh.trim()))) {

				hql = "from Zhanghb where jigh = :jigh and zhanghzt<>'����' and (zhangh = :account  or zhuzh= :account1 )";
				Zhanghb zhanghb = ZhanghbDao.getZhanghb(account);
				if (zhanghb != null) {
					String zhuzh = zhanghb.getZhuzh();
					if (zhuzh != null && !zhuzh.equals("")) {
						account = zhuzh;
					}
				}
				parameterMap.put("jigh", orgcode);
				parameterMap.put("account", account);
				parameterMap.put("account1", account);
			}
			// ������+ӡ������
			if ((account == null || "".equals(account))
					&& (yinjkbh != null && !"".equals(yinjkbh.trim()))) {
				hql = "from Zhanghb where jigh = :jigh and zhanghzt<>'����' and (zhangh = :account  or zhuzh= :account1 )";
				// getZhanghbByYinjkbh(yinjkbh)
				Zhanghb zhanghb = ZhanghbDao.getZhanghbByYinjkbh(yinjkbh);
				String zhangh = "";
				if (zhanghb != null) {
					zhangh = zhanghb.getZhangh();
				}
				parameterMap.put("jigh", orgcode);
				parameterMap.put("account", zhangh);
				parameterMap.put("account1", zhangh);
			}
			// ������+ӡ������+�˺�
			if ((account != null && !"".equals(account))
					&& (yinjkbh != null && !"".equals(yinjkbh.trim()))) {
				hql = "from Zhanghb where jigh = :jigh  and (zhangh = :account  or zhuzh= :account1) and (zhangh = :zhangh  or zhuzh= :zhangh1) ";
				Zhanghb zhanghb = ZhanghbDao.getZhanghb(account);
				if (zhanghb != null) {
					String zhuzh = zhanghb.getZhuzh();
					if (zhuzh != null && !zhuzh.equals("")) {
						account = zhuzh;
					}
				}
				Zhanghb zhanghb_ = ZhanghbDao.getZhanghbByYinjkbh(yinjkbh);
				String zhangh = "";
				if (zhanghb_ != null) {
					zhangh = zhanghb_.getZhangh();
				}
				parameterMap.put("jigh", orgcode);
				parameterMap.put("account", account);
				parameterMap.put("account1", account);
				parameterMap.put("zhangh", zhangh);
				parameterMap.put("zhangh1", zhangh);
			}
			hql += " order by hum,zhuzh desc,zhangh ";

			tabs = TabsDao.pagingForHql(hql, this.tabsBo.getDangqym(),
					this.tabsBo.getFenysl(), parameterMap);
			zhanghbList = (List<Zhanghb>) tabs.getList();
			String zhuzh = "";
			if (zhanghbList != null && zhanghbList.size() > 1) {
				for (Zhanghb zhanghb : zhanghbList) {
					zhanghb.setYinjkbh(ZhanghbDao
							.getYinjkh(zhanghb.getZhangh()));
					zhuzh = zhanghb.getZhuzh();
					if (zhuzh == null || zhuzh.trim().length() == 0) {
						zhanghb.setZhuzh("���˺�");
					} else {
						zhanghb.setZhuzh("����" + zhuzh);
					}

				}
				tabs.setList(zhanghbList);
			} else {
				tabs.setList(new ArrayList<Zhanghb>());
				tabs.setCounts(0);
			}
			return tabs;
		}
	}

	private Zhanghb createZhanghb(Object[] objects) {
		Zhanghb zhanghb = new Zhanghb();
		if (objects != null) {
			zhanghb.setZhangh((String) objects[0]);
			zhanghb.setHum((String) objects[2]);
			zhanghb.setJigh((String) objects[3]);
			zhanghb.setLianxr(objects[6] == null ? "" : (String) objects[6]);
			zhanghb.setDianh(objects[7] == null ? "" : (String) objects[7]);
			zhanghb.setKaihrq(objects[8] == null ? "" : (String) objects[8]);
			zhanghb.setTongctd(objects[9] == null ? "" : (String) objects[9]);
			zhanghb.setYouwyj(objects[11] == null ? "" : (String) objects[11]);
			zhanghb.setYouwzh(objects[12] == null ? "" : (String) objects[12]);
			zhanghb
					.setYinjshzt(objects[13] == null ? ""
							: (String) objects[13]);
			zhanghb.setZhanghshzt(objects[14] == null ? ""
					: (String) objects[14]);
			zhanghb.setZuhshzt(objects[15] == null ? "" : (String) objects[15]);
			zhanghb
					.setZhanghzt(objects[16] == null ? ""
							: (String) objects[16]);
			zhanghb
					.setZhanghxz(objects[17] == null ? ""
							: (String) objects[17]);
			zhanghb.setBeiz(objects[20] == null ? "" : (String) objects[20]);
			zhanghb.setZhuzh(objects[21] == null ? "���˺�" : "����"
					+ (String) objects[21]);
			zhanghb.setFuyrq(objects[22] == null ? "" : (String) objects[22]);
			zhanghb.setFuzr(objects[26] == null ? "" : (String) objects[26]);
			zhanghb.setFuzrdh(objects[27] == null ? "" : (String) objects[27]);
			zhanghb.setYinjkbh(objects[28] == null ? "" : (String) objects[28]);
			zhanghb.setTongdsz(objects[29] == null ? "" : (String) objects[29]);
		}
		return zhanghb;
	}

	private Zhanghb getZhanghbByYinjkh(String yinjkh) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Zhanghb zhanghb=null;
		try {
			String hql = "from Zhanghb where yinjkbh like :yinjkh";
			zhanghb = null;
			zhanghb = (Zhanghb) (session.createQuery(hql).setString("yinjkh",
					yinjkh).uniqueResult());
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return zhanghb;

	}

	public String getYinjkhByZhangh(String zhangh) {

		return ZhanghbDao.getYinjkh(zhangh);
	}

	// �����ڲ��˺�
	public String getInternalReleaseZhangh(String rule) {
		String zhangh = ZhanghbDao.getInternalReleaseZhangh(rule);
		if (zhangh != null && !zhangh.equals("")) {
			BigDecimal bd = new BigDecimal(zhangh);

			return bd.add(new BigDecimal(1)).toString();
		}
		return null;
	}

	public void updateZhanghb(Zhanghb zhanghb, List<String> yinjkbhList,
			List<String> oldYinjkbhList) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		ZhanghbDao.set_Session(session);
		YinjkDao.set_Session(session);
		try {
			ZhanghbDao.updateZhanghb(zhanghb);
			// ������ӡ������Ҫ����ӡ��������
			for (String string : yinjkbhList) {
				if (string != null && string.trim().length() != 0) {
					Yinjk yinjk = new Yinjk();
					yinjk.setYinjkh(string);
					yinjk.setZhangh(zhanghb.getZhangh());
					yinjk.setYinjkzt("����");
					yinjk.setJigh(zhanghb.getJigh());
					yinjk.setQiyrq(zhanghb.getKaihrq());
					// yinjk.setShifzk("0");

					YinjkDao.saveyinjk(yinjk);
				}
			}
			// ���õ�ӡ������Ҫ����
			for (String string : oldYinjkbhList) {
				if (string != null && string.trim().length() != 0) {
					YinjkDao.cancleYinjk(string);
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			ZhanghbDao.shifSession();
		}

	}

	public int queryShareAccountNum(String zhangh) {
		int num = 0;
		Session session = getBaseHibernateDao().getHibernateSession();
		String hql = "select count(*) from Zhanghb where zhanghzt='��Ч' and zhuzh=:zhuzh";
		Query query = session.createQuery(hql);
		query.setString("zhuzh", zhangh);
		Object result = query.uniqueResult();
		try {
			num = Integer.parseInt(result.toString());
			return num;
		} catch (NumberFormatException e) {
			num = 0;
			return num;
		}

	}

	// ��ѯ�˻������Ƿ��б䶯
	public Zhanghtbb ischange(String zhangh) {
		Zhanghtbb zhanghtbb = zhanghtbbDao.getZhanghtbb(zhangh);
		return zhanghtbb;
	}

	// �����˺��Ƿ�ȫ������
	public boolean isHaveTrial(Zhanghb zhanghb) {
		if ("����".equals(zhanghb.getZhanghshzt())
				&& "����".equals(zhanghb.getYinjshzt())
				&& "����".equals(zhanghb.getZuhshzt())) {
			return true;
		} else {
			return false;
		}
	}

	// ��ѯ��Ҫͬ���˻�������Ϣ
	public String queryallfortongbu(String zhangh) throws BusinessException {
		String sendMapStr = "";
		try {
		// �����map(�˺�������Ϣ)
		// ��Ÿ��˻�3�ű���Ϣ��map
		Map<String, String> singleZhanghxx = new HashMap<String, String>();

		Zhanghtbb zhanghtbb = zhanghtbbDao.getZhanghtbb(zhangh);
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
//		List<Map<String, String>> zhanghbMapList = ZhanghbDao
//				.getZhanghbforMap(zhangh);
//		zhanghbMapList = transitionZhanghbMap(zhanghbMapList);
//		Map<String, String> zhanghbMap = (Map<String, String>) ZhanghbDao
//				.attrConvert(zhanghbMapList).get(0);
		Map<String, String> zhanghbMap = ZhanghbDao.queryZhbByZhangh(zhangh);
		log.info("�ֹ�ͬ���˻���"+zhanghbMap);
//		zhanghbMap = transitionZhanghbMap(zhanghbMap);
		zhanghbMap = ZhanghbDao.attrConvert(zhanghbMap);
		//TODO
		String caozlx = zhanghtbb.getCaozlx();
		if ("0".equals(caozlx) || "2".equals(caozlx)) {

			// ��ѯ��ʡ�л�����
			String shenghjgh = ZhanghbDao.getShenghjgh(zhanghb.getJigh());
			zhanghbMap.put("SHENGHJGH", shenghjgh);
			zhanghbMap.put("CAOZLX", caozlx);
			// ���ϸ��˻����˻�����Ϣ�����ͬ���˻���
			singleZhanghxx.put("ZHANGHB", JsonTool.toJsonForObject(zhanghbMap));
			// �úϸ��˻���ӡ����������Ϣ
			List yinjlist = YinjbDao.getYinjMapforTongb(zhangh);
			if (yinjlist != null && yinjlist.size() != 0) {
				// ���ϸ��˻���ӡ������Ϣ�����ͬ���˻���
				singleZhanghxx.put("YINJB", JsonTool.toJsonForArry(yinjlist));
			}

			// �úϸ��˻���ӡ����ϱ�������Ϣ
			List yinjzhlist = YinjzhbDao.getYinjzhforMap(zhangh);
			if (yinjzhlist != null && yinjzhlist.size() != 0) {
				// ���ϸ��˻���ӡ����ϱ���Ϣ�����ͬ���˻���
				singleZhanghxx.put("YINJZHB", JsonTool
						.toJsonForArry(yinjzhlist));
			}
			sendMapStr = JsonTool.toJsonForObject(singleZhanghxx);

			// �����˻���������Ϊɾ������ֻ��¼���˺źͲ���������Ϣ
		} else if ("1".equals(caozlx)) {
			Map<String, String> zhanghsj = new HashMap<String, String>();
			zhanghsj.put("ZHANGH", zhangh);
			zhanghsj.put("CAOZLX", caozlx);
			//TODO
			String oldZhangh = ZhanghbDao.getOldAccount(zhangh) ;
			if(null!=oldZhangh){
				zhanghsj.put("OLDZHANGH", oldZhangh);
			}
			singleZhanghxx.put("ZHANGHB", JsonTool.toJsonForObject(zhanghsj));
			sendMapStr = JsonTool.toJsonForObject(singleZhanghxx);
		}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendMapStr;

	}

	private Map<String, String> transitionZhanghbMap(
			Map<String, String> zhanghbMap) {
		if (zhanghbMap == null || zhanghbMap.size() == 0) {
			return zhanghbMap;
		}
		Map<String, String> newzhanghbMap = new HashMap<String, String>();
		Set<String> keys = zhanghbMap.keySet();
		for (String string : keys) {
			if (string.toLowerCase().equals("yinjkh")) {
				newzhanghbMap.put("YINJKBH", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("legalname")) {
				newzhanghbMap.put("HUM", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("customernumber")) {
				newzhanghbMap.put("KEHH", zhanghbMap.get(string));
			} else if (string.toLowerCase()
					.equals("internalorganizationnumber")) {
				newzhanghbMap.put("JIGH", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("physicaladdress")) {
				newzhanghbMap.put("DIZ", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("postalcode")) {
				newzhanghbMap.put("YOUZBM", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("phonenumber")) {
				newzhanghbMap.put("DIANH", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("currency")) {
				newzhanghbMap.put("HUOBH", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("postalcode")) {
				newzhanghbMap.put("YOUZBM", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("phonenumber2")) {
				newzhanghbMap.put("DIANH2", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("phonenumber3")) {
				newzhanghbMap.put("FUZRDH", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("phonenumber4")) {
				newzhanghbMap.put("FUZRDH2", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("extensionnumber")) {
				newzhanghbMap.put("EXTNUM", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("extensionnumber2")) {
				newzhanghbMap.put("EXTNUM2", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("extensionnumber3")) {
				newzhanghbMap.put("EXTNUM3", zhanghbMap.get(string));
			} else if (string.toLowerCase().equals("extensionnumber4")) {
				newzhanghbMap.put("EXTNUM4", zhanghbMap.get(string));
			} else {
				newzhanghbMap.put(string, zhanghbMap.get(string));
			}
		}

		return newzhanghbMap;
	}

	// ͬ��Socket
	public String toSocket(String sendAdd, String sendMapStr)
			throws UnsupportedEncodingException {
		Socket socket = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		String receiveStr = "";
		String[] socketAdd = sendAdd.split(":");
		try {

			socket = new Socket(socketAdd[0], Integer.valueOf(socketAdd[1]));
			in = new BufferedInputStream(socket.getInputStream());
			out = new BufferedOutputStream(socket.getOutputStream());
			out.write("00000001".getBytes());
			out.write(getMsglength(sendMapStr.getBytes().length).getBytes());
			out.write(sendMapStr.getBytes());
			out.flush();
			int messageSize = readMessageNum(in);
			int totalSiz = 0;
			// �����С
			int bytesize = 1024;
			// ����
			byte[] shuz = new byte[bytesize];
			// ƴ�ӱ���
			StringBuffer sb = new StringBuffer();
			int z = 0;
			while ((z = in.read(shuz)) != -1 && totalSiz < messageSize) {
				String aa = new String(shuz, 0, z, "GBK");
				sb.append(aa);
				totalSiz += z;
			}
			receiveStr = sb.toString();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return receiveStr;
	}

	// �Է�����Ϣ���в���
	public String returnTongbu(String returnStr, String tongbzh) {
		if (returnStr != null && !"".equals(returnStr)) {
			JSONObject resultMap = JSONObject.fromObject(returnStr);
			if (resultMap != null && resultMap.containsKey("result")) {
				Zhanghtbb zhanghtbb = new Zhanghtbb();
				Date rightNow = Calendar.getInstance().getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(rightNow);
				zhanghtbb.setTongbsj(date);
				zhanghtbb.setZhangh(tongbzh);
				zhanghtbb.setResult("1");
				if ("fail".equals(resultMap.get("result"))) {
					if (resultMap.containsKey("exception")) {
						String exception = String.valueOf(resultMap
								.get("exception"));
						zhanghtbb.setException(exception);
						zhanghtbb.setResult("2");
					}
				}
				zhanghtbbDao.updateZhanghtbb(zhanghtbb);
				return String.valueOf(resultMap.get("result"));
			}
		}
		return "fail";
	}

	public static String getMsglength(int length) {
		String len = "" + length;
		String str = "";
		for (int i = 0; i < 8 - len.length(); i++) {
			str += "0";
		}
		str += len;
		return str;
	}

	// �����Ҫ��������
	private int readMessageNum(InputStream ist) throws IOException {
		byte[] b = new byte[8];
		ist.read(b);
		return Integer.parseInt(new String(b, "GBK"));
	}

	// �����������ӡ��
	public List getLastYSyj(String zhangh) throws BusinessException {
		return YinjbDao.getLastYSyj(zhangh);
	}

	// ��ȡ�����������
	public List getLastYSzh(String zhangh) throws BusinessException {
		return YinjbDao.getLastYSzh(zhangh);
	}

	// ��ѯ�ʺ�������Ϣ
	public ArrayList<Zhanghxzb> getZhanghxzList() {
		return ZhanghbDao.getZhanghxzList();
	}

	public int countTodoZhanghbList(String jigh) {
		return ZhanghbDao.countTodoZhanghbList(jigh);
	}

	public List<Zhanghb> getToDoZhanghbList(String jigh) {
		return ZhanghbDao.getToDoZhanghbList(jigh);
	}

	public List<Zhanghb> getZhangbListByZzh(String zhangh) {
		return ZhanghbDao.getZhanghbByZzh(zhangh);
	}

	public String getJiankgy(String account) {

		return ZhanghbDao.getJiankgy(account);
	}

	/**
	 * ���˻�����������ӡ�������������˻�
	 */
	public boolean cancleZhuzh(String zhangh, String gszh) {
		List<Zhanghb> zzhlist = ZhanghbDao.getZhanghbByZzh(zhangh);
		Zhanghb yzhangh = ZhanghbDao.getZhanghb(zhangh);
		String yinjkbh = yzhangh.getYinjkbh();
		boolean contains = false;
		for (Zhanghb zhanghb : zzhlist) {
			if (gszh.equals(zhanghb.getZhangh())) {
				contains = true;
			}
		}
		if (gszh == null || gszh.trim().length() == 0 || !contains) {
			gszh = zzhlist.get(0).getZhangh();
		}
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		String sql1 = "update yinjk set zhangh =:gszh where zhangh=:zhangh and yinjkzt='����'";// �޸�ӡ�����Ĺ����˺�
		String sql2 = "update zhanghb set zhuzh=:gszh,youwyj=:ywyj,youwzh=:ywzh,yinjshzt=:yjshzt,zuhshzt=:zhshzt where zhuzh =:zhangh and zhangh<>:guiszh ";// �޸����������˻������˻�Ϊ�µĹ����˻�
		String sql3 = "update zhanghb set yinjkh=:yinjkbh,zhuzh='',youwyj=:ywyj,youwzh=:ywzh,yinjshzt=:yjshzt,zuhshzt=:zhshzt where zhangh=:gszh ";// �޸Ĺ����˻������˻�Ϊ��
																																					// ��ӡ�������
		String sql4 = "update zhanghb set yinjkh='',zhanghzt='����',zhuzh=:gszh where zhangh=:zhangh ";// ��ԭ�˻���ӡ��������Ϊ��
		String sql5 = "update yinjb set zhangh=:gszh where zhangh=:zhangh ";// �޸�ӡ���������˻�
		String sql6 = "update yinjzhb set zhangh=:gszh where zhangh=:zhangh ";// �޸�ӡ����ϱ�������˻�
		SQLQuery query = null;
		try {
			query = session.createSQLQuery(sql1);
			query.setString("gszh", gszh);
			query.setString("zhangh", zhangh);
			query.executeUpdate();
			query = session.createSQLQuery(sql2);
			query.setString("gszh", gszh);
			query.setString("zhangh", zhangh);
			query.setString("guiszh", gszh);
			query.setString("ywyj", yzhangh.getYouwyj());
			query.setString("ywzh", yzhangh.getYouwzh());
			query.setString("yjshzt", yzhangh.getYinjshzt());
			query.setString("zhshzt", yzhangh.getZuhshzt());
			query.executeUpdate();
			query = session.createSQLQuery(sql3);
			query.setString("gszh", gszh);
			query.setString("yinjkbh", yinjkbh);
			query.setString("ywyj", yzhangh.getYouwyj());
			query.setString("ywzh", yzhangh.getYouwzh());
			query.setString("yjshzt", yzhangh.getYinjshzt());
			query.setString("zhshzt", yzhangh.getZuhshzt());
			query.executeUpdate();

			query = session.createSQLQuery(sql4);
			query.setString("gszh", gszh);
			query.setString("zhangh", zhangh);
			query.executeUpdate();

			query = session.createSQLQuery(sql5);
			query.setString("gszh", gszh);
			query.setString("zhangh", zhangh);
			query.executeUpdate();
			query = session.createSQLQuery(sql6);
			query.setString("gszh", gszh);
			query.setString("zhangh", zhangh);
			query.executeUpdate();
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}

	}

	public String getZhanghFromShort(String zhangh) {
		if (zhangh.trim().length() == 22) {
			return this.parseTypeN(zhangh, 17);
		}
		return ZhanghbDao.getZhanghFromShort(zhangh);
	}

	public String parseTypeN(String n, int len) {
		String str = n;
		if (n == null || "".equals(n.trim())) {
			return str;
		} else if (n.trim().length() > 17) {
			str = ZhanghbDao.getZhanghFromOldAccount(n);
		} else if (n.trim().length() < 17 && n.trim().length() != 9) {
			str = ZhanghbDao.getZhanghFromShort(n);
		}
		return str;
	}

	
	  public String parseTypeN_(String n, int len) throws Exception { 
		  if(n==null||n.length()!=22){
			  throw new Exception("���˺Ÿ�ʽ����ȷ���޷�ʹ���㷨ת��");
		  }
		  
		  
		if(!validateCheckDigit(n)){
			 throw new Exception("���˺Ų��Ϸ����޷�ʹ���㷨ת��");
			} 
		String brchCode = CommonUtil.getCoreBranchCode(n.substring(0,8));
				
		if(!"".equals(brchCode)) {
			n = brchCode + n.substring(8, 20);
			n = Paritybitcal.GenBancsCd(n);
					
		}
		if (n.length()>len) {
			n = n.substring(0,len);
		}
		String str = String.valueOf(n);
		while (str.length() < len) {
			str = "0" + str;
		}
			 
	  return str; 
	 }
	 
	/**
	 * У�����˺�
	 * 
	 * @param sAccntNumber
	 *            22λ���˺�
	 * @return
	 */
	private static boolean validateCheckDigit(String sAccntNumber) {
		int p1 = 0;
		int p2 = 0;
		String sAcct = sAccntNumber.substring(0, 20);
		int iChkDigits = Integer.parseInt(sAccntNumber.substring(20));
		int iDigit = 0;
		for (int i = 0; i < 20; i++) {
			if (i % 2 == 0) {
				p1 += Integer.parseInt(sAcct.substring(i, i + 1)) * 3;
			} else {
				p2 += Integer.parseInt(sAcct.substring(i, i + 1));
			}
		}

		iDigit = (Math.abs(p1 - p2)) % 100;
		iDigit = (100 - iDigit) % 100;
		if (iDigit == iChkDigits) {
			return true;
		}
		return false;

	}
	private final String insertintozhanghzhb_excel="insert into zhanghzhb(newaccount,oldaccount,shortaccount) values(?,?,?)";
	private final String selectfromzhanghzhb_excel="select count(*) as num from zhanghzhb where newaccount=? ";
	private final String updatezhanghzhb_excel="update zhanghzhb set oldaccount=?,shortaccount=? where newaccount=? ";
	/**
	 * ����excel�¾ɼ��˻���������
	 * @throws Exception 
	 */
	public List<String> importZhanghzhb(HSSFSheet sheet) throws Exception {
			List list = ExpOrImp.importExcel(sheet, 3);
			List<String> errorlist=new ArrayList<String>();
			try{
				//ps=conn.prepareStatement(insertintozhanghzhb_excel);
				f1:for(int i=0;i<list.size();i++)
				{
					Map map = (Map)list.get(i);
					String newaccount=String.valueOf(map.get(0)==null?"":map.get(0)).trim();
					if(newaccount==null||!valiedata(newaccount, "^\\d{17}$",false)){
						errorlist.add(newaccount+";���˺Ÿ�ʽ����ȷ");
						continue f1;
					}
					String oldaccount=String.valueOf(map.get(1)==null?"":map.get(1)).trim();
					if(oldaccount==null||!valiedata(oldaccount, "^\\d{22}$",true)){
						errorlist.add(newaccount+";���˺Ÿ�ʽ����ȷ");
						continue f1;
					}
					String shortaccount=String.valueOf(map.get(2)==null?"":map.get(2)).trim();
					if(shortaccount==null||!valiedata(shortaccount, "^\\d{5,17}$",true)){
						errorlist.add(newaccount+";���˺Ÿ�ʽ����ȷ");
						continue f1;
					}
					int count=countZhanghzhb(newaccount);
					if(count==0){
						insertZhanghzhb(newaccount, oldaccount, shortaccount);
					}else{
						updateZhanghzhb(newaccount, oldaccount, shortaccount);
					}
				}
					
				return errorlist;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return errorlist;
	}
				private int countZhanghzhb(String newaccount){
					Connection conn=null;
					PreparedStatement ps =null;
					ResultSet rs =null;
					int count=0;
					try{
						conn=this.getBaseJDBCDao().getConnection();
					ps=conn.prepareStatement(selectfromzhanghzhb_excel);
					ps.setString(1, newaccount);
					rs=ps.executeQuery();
					while (rs.next()) {
						try {
							count=Integer.parseInt(rs.getString("num"));
						} catch (Exception e) {
							count=0;
						}
						
					}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(rs!=null){
							try {
							rs.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(ps!=null){
							try {
								ps.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(conn!=null){
							try {
								conn.setAutoCommit(true);
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
					return count;
				}

				private void insertZhanghzhb(String newaccount,String oldaccount,String shortaccount){
					Connection conn=null;
					PreparedStatement ps =null;
					try{
						conn=this.getBaseJDBCDao().getConnection();
						ps=conn.prepareStatement(insertintozhanghzhb_excel);
						ps.setString(1, newaccount);
						ps.setString(2, oldaccount);
						ps.setString(3	, shortaccount);
						ps.execute();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(ps!=null){
							try {
								ps.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(conn!=null){
							try {
								conn.setAutoCommit(true);
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
				private void updateZhanghzhb(String newaccount,String oldaccount,String shortaccount){
					Connection conn=null;
					PreparedStatement ps =null;
					try{
						conn=this.getBaseJDBCDao().getConnection();
						ps=conn.prepareStatement(updatezhanghzhb_excel);
						ps.setString(1, oldaccount);
						ps.setString(2, shortaccount);
						ps.setString(3, newaccount);
						ps.execute();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(ps!=null){
							try {
								ps.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(conn!=null){
							try {
								conn.setAutoCommit(true);
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}

	/**
	 * ����excel�˻�����
	 */
				public boolean importZhanghbTemp(String shenghh,HSSFSheet sheet) {
					List list = ExpOrImp.importExcel(sheet, 29);
					Connection conn=null;
					PreparedStatement ps =null;
					try{
						conn=getBaseJDBCDao().getConnection();
						conn.setAutoCommit(false);
						ps=conn.prepareStatement(insertintozhanghb_excel);
						for(int i=0;i<list.size();i++)
						{
							Map map = (Map)list.get(i);
							String value="";
							 for(int j =0;j<map.size();j++){
								value=String.valueOf(map.get(j)==null?"":map.get(j));
								value=value.replaceAll("\r", "");
								value=value.replaceAll("\n", "");
								value=value.replaceAll(" ", "");
								value=value.replaceAll("��", "");
								ps.setString(j+1, value.trim());
								
							}
							ps.addBatch();
						}
						ps.executeBatch();
						conn.commit();
						return true;
					}catch (Exception e) {
						e.printStackTrace();
						try {
							conn.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						return false;
					}finally{
						if(ps!=null){
							try {
								ps.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(conn!=null){
							try {
								conn.setAutoCommit(true);
								getBaseJDBCDao().closeConnection(conn);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
	
	@Override
	public List<Zhanghb> getZhanghbExcelInfo(String shenghh) {
		String sql ="select ZHANGH,F_INTERNALORGANIZATIONNUMBER, INTERNALORGANIZATIONNUMBER, LEGALNAME, ZHANGHXZ, KAIHRQ, TONGCTD, SHIFDH, LIANXR, AREACODE, PHONENUMBER, EXTENSIONNUMBER, LIANXR2, AREACODE2, PHONENUMBER2, EXTENSIONNUMBER2, FUZR, AREACODE3, PHONENUMBER3, EXTENSIONNUMBER3, FUZR2, AREACODE4, PHONENUMBER4, EXTENSIONNUMBER4, ZHUZH, FUYRQ, YINJKH, BEIZ,YINJKQZH from zhanghb_excel where F_INTERNALORGANIZATIONNUMBER=? ";
		//ZHANGH,F_INTERNALORGANIZATIONNUMBER, INTERNALORGANIZATIONNUMBER, LEGALNAME, ZHANGHXZ, KAIHRQ, TONGCTD, SHIFDH, LIANXR,
		//AREACODE, PHONENUMBER, EXTENSIONNUMBER, LIANXR2, AREACODE2, PHONENUMBER2, EXTENSIONNUMBER2, FUZR, AREACODE3, PHONENUMBER3, EXTENSIONNUMBER3, FUZR2,
		//AREACODE4, PHONENUMBER4, EXTENSIONNUMBER4, ZHUZH, FUYRQ, YINJKH, BEIZ
		Connection conn =null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		List<Zhanghb> zhanghbList=new ArrayList<Zhanghb>();
		
		try {
			conn=this.getBaseJDBCDao().getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, shenghh);
			rs=ps.executeQuery();
			Zhanghb zhanghb=null;
			while(rs.next()){
				zhanghb =new Zhanghb();
				zhanghb.setZhangh(rs.getString("ZHANGH"));
				zhanghb.setHum(rs.getString("LEGALNAME"));
				zhanghb.setJigh(rs.getString("INTERNALORGANIZATIONNUMBER"));
				zhanghb.setZhuzh(rs.getString("ZHUZH"));
				zhanghb.setBeiz(rs.getString("BEIZ"));
				zhanghb.setDianh(rs.getString("PHONENUMBER"));
				zhanghb.setShifdh(rs.getString("SHIFDH"));
				zhanghb.setDianh2(rs.getString("PHONENUMBER2"));
				zhanghb.setFuzrdh(rs.getString("PHONENUMBER3"));
				zhanghb.setFuzrdh2(rs.getString("PHONENUMBER4"));
				zhanghb.setLianxrqh(rs.getString("AREACODE"));
				zhanghb.setLianxrqh2(rs.getString("AREACODE2"));
				zhanghb.setFuzrqh(rs.getString("AREACODE3"));
				zhanghb.setFuzrqh2(rs.getString("AREACODE4"));
				zhanghb.setLianxrfjh(rs.getString("EXTENSIONNUMBER"));
				zhanghb.setLianxrfjh2(rs.getString("EXTENSIONNUMBER2"));
				zhanghb.setFuzrfjh(rs.getString("EXTENSIONNUMBER3"));
				zhanghb.setFuzrfjh2(rs.getString("EXTENSIONNUMBER4"));
				zhanghb.setLianxr(rs.getString("LIANXR"));
				zhanghb.setLianxr2(rs.getString("LIANXR2"));
				zhanghb.setFuzr(rs.getString("FUZR"));
				zhanghb.setFuzr2(rs.getString("FUZR2"));
				zhanghb.setZhanghxz(rs.getString("ZHANGHXZ"));
				zhanghb.setFuyrq(rs.getString("FUYRQ"));
				zhanghb.setYinjkbh(rs.getString("YINJKH"));
				zhanghb.setYinjkbh2(rs.getString("F_INTERNALORGANIZATIONNUMBER"));
				zhanghb.setKaihrq(rs.getString("KAIHRQ"));
				zhanghb.setTongctd(rs.getString("TONGCTD"));
				zhanghb.setYinjkbh3(rs.getString("YINJKQZH"));//ʹ�÷����ֶ�װ��ӡ����ǰ׺��20150106qjk
				zhanghbList.add(zhanghb);
			}
			return zhanghbList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return zhanghbList;
	}
	@Override
	public List<String> checkZhanghbExcelInfo(List<Zhanghb> zhanghbExcelList) {
		//int errornum=0;
		List<String>errorlist=new ArrayList<String>();
		if(zhanghbExcelList==null||zhanghbExcelList.size()==0){
			return errorlist;
		}
		f1:for (Zhanghb zhanghb : zhanghbExcelList) {
			try {
				zhanghb=checkZhanghInfo(zhanghb);
			} catch (Exception e) {
				errorlist.add(e.getMessage());
				continue f1;
			}
			ZhanghbDao.saveOrUpdateForZhanghTemp(zhanghb);
		}
		return errorlist;
	}
	
	public void deleteZhanghb_Excel(String shenghh) {
		String deleteExcel="delete from zhanghb_excel where F_INTERNALORGANIZATIONNUMBER=:shenghh";
		Session session=this.getHibernateSession();
		try{
			SQLQuery delete=session.createSQLQuery(deleteExcel);
			delete.setString("shenghh", shenghh);
			delete.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
		
	}
	public void deleteZhanghb_Error(String shenghh,String type) {
		String deleteError="delete from zhanghb_error where F_INTERNALORGANIZATIONNUMBER=:shenghh and msgtype=:type";
		Session session=this.getHibernateSession();
		try{
			SQLQuery delete=session.createSQLQuery(deleteError);
			delete.setString("shenghh", shenghh);
			delete.setString("type", type);
			delete.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public List<String> getZhanghb_Error(String shenghh,String type){
		String getError="select errormsg from zhanghb_error where  F_INTERNALORGANIZATIONNUMBER=:shenghh  and msgtype=:type ";
		Session session=this.getHibernateSession();
		List<String> errorlist=new ArrayList<String>();
		try{
			SQLQuery select=session.createSQLQuery(getError);
			select.setString("shenghh", shenghh);
			select.setString("type", type);
			errorlist=select.list();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
		return errorlist;
	}
	public void inserZhanghb_Error(String shenghh,List<String> errorlist,String type){
		//List list = ExpOrImp.importExcel(sheet, 28);
		Connection conn=null;
		PreparedStatement ps =null;
		try{
			conn=getBaseJDBCDao().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("insert into zhanghb_error (F_INTERNALORGANIZATIONNUMBER,ERRORMSG,MSGTYPE) values(?,?,?)");
			for(int i=0;i<errorlist.size();i++)
			{

				ps.setString(1, shenghh);
				ps.setString(2, errorlist.get(i));
				ps.setString(3, type);
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
					this.getBaseJDBCDao().closeConnection(conn);
			}
		}
	}
	
	
	
	private static final String zhanghxzinfo=",������,һ�㻧,��ʱ��,ר�û�,���ڻ�,֪ͨ��,��һ�,�ڲ���,���˸߼����㻧,�������ʻ�,������,�������滧,������,";
	private  Zhanghb  checkZhanghInfo( Zhanghb zhanghmsg) throws Exception{
		if(zhanghmsg==null){
			return null;
		}
		String account_=zhanghmsg.getZhangh();
		String head="["+zhanghmsg.getJigh()+"]";
		String account=head+zhanghmsg.getZhangh();
		zhanghmsg.setZhangh_(account_);
		//ת���˺�
		{
			// String reg="^\\d{9}$||^\\d{17}$||^\\d{22}$";
			String zhangh=zhanghmsg.getZhangh();

			if(zhangh==null||!valiedata(zhangh, "^\\d{9}$||^\\d{17}$||^\\d{22}$",false)){
				throw new Exception(account+";�˺Ÿ�ʽ����ȷ");
			}
			if(zhangh.length()==22){
				//zhangh=OldAccountConfig.getInstance().get(zhangh);
				zhangh=ZhanghbDao.getZhanghFromOldAccount(zhangh);
				if(zhangh==null||zhangh.length()==22){
					throw new Exception(account+";ת�����˺�ʧ��(�˺�),"+getNewAccountByOld(account_));
				}
			}
			zhanghmsg.setZhangh(zhangh);
		
		}
		//У�黧������
		{
			String hum=zhanghmsg.getHum();
			if(hum==null||hum.equals("")){
				throw new Exception(account+";��������Ϊ��");
			}
			if(hum.getBytes().length>100){
				throw new Exception(account+";�������ȳ���100�ֽ�");
			}
		}
		//ת�����˺�
		{
			String zhuzh=zhanghmsg.getZhuzh()==null?"":zhanghmsg.getZhuzh();
			String yinjkh=zhanghmsg.getYinjkbh()==null?"":zhanghmsg.getYinjkbh();
			if(zhuzh.equals("")){
				if(yinjkh.equals("")){
					throw new Exception(account+";ӡ���������˺Ų���ͬʱΪ��");
				}
				zhanghmsg.setFuyrq("");
			}else{
				if(zhuzh.equals(account_)){
					throw new Exception(account+";�˺ź����˺Ų�����ͬ");
				}
				if(!valiedata(zhuzh, "^\\d{9}$||^\\d{17}$||^\\d{22}$",true)){
					throw new Exception(account+";���˺Ÿ�ʽ����ȷ");
				}
				if(zhuzh.length()==22){
				//zhuzh=OldAccountConfig.getInstance().get(zhuzh);
					zhuzh=ZhanghbDao.getZhanghFromOldAccount(zhuzh);
				if(zhuzh==null||zhuzh.length()==22){
					throw new Exception(account+";ת�����˺�ʧ��(���˺�),"+getNewAccountByOld(zhuzh));
				}
				}
				zhanghmsg.setZhuzh(zhuzh);
				zhanghmsg.setYinjkbh("");
				//ת����������
				{
					String fuyrq=zhanghmsg.getFuyrq();
					if(!valiedata(fuyrq, "^\\d{8}$||^\\d{4}-\\d{2}-\\d{2}$",false)){
						throw new Exception(account+";�������ڸ�ʽ����ȷ"+fuyrq);
					}
					fuyrq=DateUtil.turnToSimple(fuyrq);
					zhanghmsg.setFuyrq(fuyrq);
				}
			}
		}
		//У��һ�����к�
		{
			if(!valiedata(zhanghmsg.getYinjkbh2(), "^\\d{4}$", false)){
				throw new Exception(account+";����һ�����л����Ÿ�ʽ����ȷ");
			}
		}
		//У��ӡ����ǰ׺��
		{
			if(!valiedata(zhanghmsg.getYinjkbh3(), "^\\d{4}$", false)){
				throw new Exception(account+";���л����Ÿ�ʽ����ȷ");
			}
		}
		//ת��ӡ������
		{
			String yinjkh=zhanghmsg.getYinjkbh()==null?"":zhanghmsg.getYinjkbh();
			if(!yinjkh.equals("")){
				if(!valiedata(yinjkh, "^\\d{4,17}$||^\\d{22}$",true)){
					throw new Exception(account+";ӡ�����Ÿ�ʽ����ȷ");
				}
				if(yinjkh.length()==22){
					//yinjkh=OldAccountConfig.getInstance().get(yinjkh);
					yinjkh=ZhanghbDao.getZhanghFromOldAccount(yinjkh);
					if(yinjkh==null||yinjkh.length()==22){
						throw new Exception(account+";ת�����˺�ʧ��(ӡ������),"+getNewAccountByOld(yinjkh));
					}
				}else if(yinjkh.length()==17){
					;
				}else{
					String shenghh=zhanghmsg.getYinjkbh3()==null?"":zhanghmsg.getYinjkbh3();//shenghangh
					int count=20-yinjkh.length();
					if(shenghh==null||shenghh.trim().equals("")){
						shenghh="0000";
					}
					while(shenghh.length()<count){
						shenghh+="0";
					}
					yinjkh=shenghh+yinjkh;
				}
			}
			zhanghmsg.setYinjkbh(yinjkh);
		}
		//ת���Ƿ���
		{
			String shifdh=zhanghmsg.getShifdh();
			if(shifdh==null||shifdh.equals("��")){
				zhanghmsg.setShifdh("1");
			}else{
				zhanghmsg.setShifdh("0");
			}
		}

		//ת����������
		{
			String kaihrq=zhanghmsg.getKaihrq()==null?"":zhanghmsg.getKaihrq();
			if(!valiedata(kaihrq, "^\\d{8}$||^\\d{4}-\\d{2}-\\d{2}$",false)){
				throw new Exception(account+";�������ڸ�ʽ����ȷ"+kaihrq);
			}
			kaihrq=DateUtil.turnToSimple(kaihrq);
			zhanghmsg.setKaihrq(kaihrq);
		}
		//У����ϵ����Ϣ
		{

			if(zhanghmsg.getLianxr()!=null&&zhanghmsg.getLianxr().getBytes().length>30){
				throw new Exception(account+";��ϵ�˳��ȴ���30�ֽ�");
			}
			if(zhanghmsg.getLianxr()!=null&&(zhanghmsg.getLianxr().indexOf(",")!=-1||zhanghmsg.getLianxr().indexOf("��")!=-1)){
				throw new Exception(account+";��ϵ�˺��ж���,���滻Ϊ�ֺŻ��߶ٺ�");
			}
			if(zhanghmsg.getLianxr2()!=null&&zhanghmsg.getLianxr2().getBytes().length>30){
				throw new Exception(account+";��ϵ��2���ȴ���30�ֽ�");
			}

			if(zhanghmsg.getLianxr2()!=null&&(zhanghmsg.getLianxr2().indexOf(",")!=-1||zhanghmsg.getLianxr2().indexOf("��")!=-1)){
				throw new Exception(account+";��ϵ��2���ж���,���滻Ϊ�ֺŻ��߶ٺ�");
			}
			if(zhanghmsg.getFuzr()!=null&&zhanghmsg.getFuzr().getBytes().length>30){
				throw new Exception(account+";�����˳��ȴ���30�ֽ�");
			}

			if(zhanghmsg.getFuzr()!=null&&(zhanghmsg.getFuzr().indexOf(",")!=-1||zhanghmsg.getFuzr().indexOf("��")!=-1)){
				throw new Exception(account+";�����˺��ж���,���滻Ϊ�ֺŻ��߶ٺ�");
			}
			if(zhanghmsg.getFuzr2()!=null&&zhanghmsg.getFuzr2().getBytes().length>30){
				throw new Exception(account+";������2���ȴ���30�ֽ�");
			}

			if(zhanghmsg.getFuzr2()!=null&&(zhanghmsg.getFuzr2().indexOf(",")!=-1||zhanghmsg.getFuzr2().indexOf("��")!=-1)){
				throw new Exception(account+";������2���ж���,���滻Ϊ�ֺŻ��߶ٺ�");
			}
		}
		//У�鱸ע��Ϣ
		{
			if(zhanghmsg.getBeiz()!=null&&zhanghmsg.getBeiz().getBytes().length>200){
				throw new Exception(account+";��ע���ȴ���200�ֽ�");
			}

			if(zhanghmsg.getBeiz()!=null&&(zhanghmsg.getBeiz().indexOf(",")!=-1||zhanghmsg.getBeiz().indexOf("��")!=-1)){
				throw new Exception(account+";��ע���ж���,���滻Ϊ�ֺŻ��߶ٺ�");
			}
			//zhanghmsg.setBeiz("");
		}
		

		//ת���绰����
		{
				if(!valiedata(zhanghmsg.getLianxrqh(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";��ϵ��1�绰���Ÿ�ʽ����ȷ");
				}
				if(!valiedata(zhanghmsg.getLianxrqh2(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";��ϵ��2�绰���Ÿ�ʽ����ȷ");
				}
				if(!valiedata(zhanghmsg.getFuzrqh(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";������1�绰���Ÿ�ʽ����ȷ");
				}
				if(!valiedata(zhanghmsg.getFuzrqh2(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";������2�绰���Ÿ�ʽ����ȷ");
				}
				zhanghmsg.setLianxrqh(zhanghmsg.getLianxrqh()==null||zhanghmsg.getLianxrqh().equals("")?"":zhanghmsg.getLianxrqh().substring(0,1).equals("0")?zhanghmsg.getLianxrqh().substring(1):zhanghmsg.getLianxrqh());
				zhanghmsg.setLianxrqh2(zhanghmsg.getLianxrqh2()==null||zhanghmsg.getLianxrqh2().equals("")?"":zhanghmsg.getLianxrqh2().substring(0,1).equals("0")?zhanghmsg.getLianxrqh2().substring(1):zhanghmsg.getLianxrqh2());
				zhanghmsg.setFuzrqh(zhanghmsg.getFuzrqh()==null||zhanghmsg.getFuzrqh().equals("")?"":zhanghmsg.getFuzrqh().substring(0,1).equals("0")?zhanghmsg.getFuzrqh().substring(1):zhanghmsg.getFuzrqh());
				zhanghmsg.setFuzrqh2(zhanghmsg.getFuzrqh2()==null||zhanghmsg.getFuzrqh2().equals("")?"":zhanghmsg.getFuzrqh2().substring(0,1).equals("0")?zhanghmsg.getFuzrqh2().substring(1):zhanghmsg.getFuzrqh2());
				
				
				if(!valiedata(zhanghmsg.getLianxrfjh(), "\\d{1,6}", true)){
					throw new Exception(account+";��ϵ��1�绰�ֻ��Ÿ�ʽ����ȷ");
				}
				if(!valiedata(zhanghmsg.getLianxrfjh2(), "\\d{1,6}", true)){
					throw new Exception(account+";��ϵ��2�绰�ֻ��Ÿ�ʽ����ȷ");
				}
				if(!valiedata(zhanghmsg.getFuzrfjh(), "\\d{1,6}", true)){
					throw new Exception(account+";������1�绰�ֻ��Ÿ�ʽ����ȷ");
				}
				if(!valiedata(zhanghmsg.getFuzrfjh2(), "\\d{1,6}", true)){
					throw new Exception(account+";������2�绰�ֻ��Ÿ�ʽ����ȷ");
				}

				if(!valiedata(zhanghmsg.getDianh(), "\\d{5,11}", true)){
					throw new Exception(account+";��ϵ��1�绰�Ÿ�ʽ����ȷ:"+zhanghmsg.getDianh());
				}
				if(!valiedata(zhanghmsg.getDianh2(), "\\d{5,11}", true)){
					throw new Exception(account+";��ϵ��2�绰�Ÿ�ʽ����ȷ:"+zhanghmsg.getDianh2());
				}
				if(!valiedata(zhanghmsg.getFuzrdh(), "\\d{5,11}", true)){
					throw new Exception(account+";������1�绰�Ÿ�ʽ����ȷ:"+zhanghmsg.getFuzrdh());
				}
				if(!valiedata(zhanghmsg.getFuzrdh2(), "\\d{5,11}", true)){
					throw new Exception(account+";������2�绰�Ÿ�ʽ����ȷ:"+zhanghmsg.getFuzrdh2());
				}
			
		}
		
		//ת���˻����ʺ��˻�״̬
		{
			String zhanghxz=zhanghmsg.getZhanghxz()==null?"":zhanghmsg.getZhanghxz();
			if(zhanghxz.length()==0){
				throw new Exception(account+";�˻����ʲ���Ϊ��");
			}else{
				if(zhanghxzinfo.indexOf(","+zhanghxz+",")==-1){
					throw new Exception(account+";�˻����ʲ��Ϸ�");
				}
				zhanghmsg.setZhanghzt("��Ч");
				if(zhanghxz.equals("������")){
					zhanghmsg.setZhanghxz("�ڲ���");
					zhanghmsg.setZhanghzt("����");
				}else if(zhanghxz.equals("�������滧")||zhanghxz.equals("���滧")){
					zhanghmsg.setZhanghxz("�ڲ���");
					zhanghmsg.setZhanghzt("��������");
				}else if(zhanghxz.equals("��Ҿ�����Ŀ�˻�")||zhanghxz.equals("����ʱ����˻�")){
					zhanghmsg.setZhanghxz("��һ�");
				}
			}
			
		}

		//ת��ͨ�ұ�־
		{
			String tongctd=zhanghmsg.getTongctd()==null?"":zhanghmsg.getTongctd();
			if(tongctd.length()==0){
				throw new Exception(account+";�˻�ͨ�ұ�־����Ϊ��");
			}else{
				if(",ȫ��,һ������ȫϽ,һ�����б���,��������,��ͨ��,".indexOf(","+tongctd+",")==-1){
					throw new Exception(account+";�˻�ͨ�ұ�־���Ϸ�");
				}
			}
			
		}
		zhanghmsg.setZhanghshzt("δ��");
		zhanghmsg.setYinjshzt("δ��");
		zhanghmsg.setYouwyj("��");
		zhanghmsg.setYouwzh("��");
		zhanghmsg.setZuhshzt("δ��");
		
		return zhanghmsg;
		}
	private String getNewAccountByOld(String account) {
		String newaccount="";
		try {
			 newaccount=parseTypeN_(account, 17);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "�����㷨ת��Ϊ��"+newaccount;
	}

	private boolean valiedata(String info,String reg,boolean ifisnull){
		if(info==null||info.length()==0){
			return ifisnull;
		}
		return info.matches(reg);
	}
	@Override
	public Zhanghb getZhanghbTemp(String account) {
		String sql ="select ZHANGH, INTERNALORGANIZATIONNUMBER, LEGALNAME, ZHANGHXZ, KAIHRQ, TONGCTD, SHIFDH, LIANXR, AREACODE, PHONENUMBER, EXTENSIONNUMBER, ZHANGHZT,LIANXR2, AREACODE2, PHONENUMBER2, EXTENSIONNUMBER2, FUZR, AREACODE3, PHONENUMBER3, EXTENSIONNUMBER3, FUZR2, AREACODE4, PHONENUMBER4, EXTENSIONNUMBER4, ZHUZH, FUYRQ, YINJKH, BEIZ from zhanghb_temp where ZHANGH=? ";
		//ZHANGH,F_INTERNALORGANIZATIONNUMBER, INTERNALORGANIZATIONNUMBER, LEGALNAME, ZHANGHXZ, KAIHRQ, TONGCTD, SHIFDH, LIANXR,
		//AREACODE, PHONENUMBER, EXTENSIONNUMBER, LIANXR2, AREACODE2, PHONENUMBER2, EXTENSIONNUMBER2, FUZR, AREACODE3, PHONENUMBER3, EXTENSIONNUMBER3, FUZR2,
		//AREACODE4, PHONENUMBER4, EXTENSIONNUMBER4, ZHUZH, FUYRQ, YINJKH, BEIZ
		Connection conn =null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			conn=this.getBaseJDBCDao().getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, account);
			rs=ps.executeQuery();
			Zhanghb zhanghb=null;
			while(rs.next()){
				zhanghb =new Zhanghb();
				zhanghb.setZhangh(rs.getString("ZHANGH"));
				zhanghb.setHum(rs.getString("LEGALNAME"));
				zhanghb.setJigh(rs.getString("INTERNALORGANIZATIONNUMBER"));
				zhanghb.setZhuzh(rs.getString("ZHUZH"));
				zhanghb.setBeiz(rs.getString("BEIZ"));
				zhanghb.setDianh(rs.getString("PHONENUMBER"));
				zhanghb.setShifdh(rs.getString("SHIFDH"));
				zhanghb.setDianh2(rs.getString("PHONENUMBER2"));
				zhanghb.setFuzrdh(rs.getString("PHONENUMBER3"));
				zhanghb.setFuzrdh2(rs.getString("PHONENUMBER4"));
				zhanghb.setLianxrqh(rs.getString("AREACODE"));
				zhanghb.setLianxrqh2(rs.getString("AREACODE2"));
				zhanghb.setFuzrqh(rs.getString("AREACODE3"));
				zhanghb.setFuzrqh2(rs.getString("AREACODE4"));
				zhanghb.setLianxrfjh(rs.getString("EXTENSIONNUMBER"));
				zhanghb.setLianxrfjh2(rs.getString("EXTENSIONNUMBER2"));
				zhanghb.setFuzrfjh(rs.getString("EXTENSIONNUMBER3"));
				zhanghb.setFuzrfjh2(rs.getString("EXTENSIONNUMBER4"));
				zhanghb.setLianxr(rs.getString("LIANXR"));
				zhanghb.setLianxr2(rs.getString("LIANXR2"));
				zhanghb.setFuzr(rs.getString("FUZR"));
				zhanghb.setFuzr2(rs.getString("FUZR2"));
				zhanghb.setZhanghxz(rs.getString("ZHANGHXZ"));
				zhanghb.setFuyrq(rs.getString("FUYRQ"));
				zhanghb.setYinjkbh(rs.getString("YINJKH"));
				zhanghb.setKaihrq(rs.getString("KAIHRQ"));
				zhanghb.setTongctd(rs.getString("TONGCTD"));
				zhanghb.setZhanghzt(rs.getString("ZHANGHZT"));
				
			}
			return zhanghb;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
			this.getBaseJDBCDao().closeConnection(conn);
			}
		}
		return null;
	}

	@Override
	public void tongbAccountinfoFromTemp(Zhanghb zhanghb) {
		if(zhanghb==null||zhanghb.getZhangh()==null){
			return;
		}
			Session session = this.getBaseHibernateDao().getHibernateSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			ZhanghbDao.set_Session(session);
			YinjkDao.set_Session(session);
			try {
				ZhanghbDao.updateZhanghb(zhanghb);
				String yinjkh=zhanghb.getYinjkbh();
				YinjkDao.deleteYinjk(zhanghb.getZhangh());
				if(yinjkh!=null&&
						!yinjkh.trim().equals("")){
					Yinjk yinjk = new Yinjk();
					yinjk.setYinjkh(zhanghb.getYinjkbh());
					yinjk.setZhangh(zhanghb.getZhangh());
					yinjk.setYinjkzt("����");
					yinjk.setJigh(zhanghb.getJigh());
					yinjk.setQiyrq(zhanghb.getKaihrq());
					YinjkDao.saveyinjk(yinjk);
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				ZhanghbDao.shifSession();
			}
		}
	
}
