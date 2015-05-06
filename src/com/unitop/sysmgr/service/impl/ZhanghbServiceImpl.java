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
	
	// 获取帐号信息
	@SuppressWarnings("unchecked")
	public Zhanghb getZhanghb(String zhangh) throws BusinessException {
		Zhanghb zhanghTemp = ZhanghbDao.getZhanghb(zhangh);
		/*
		 * if(zhanghTemp ==null) { throw new BusinessException("账号不存在!"); } List
		 * list = YinjbDao.getYinj(zhangh); if(null != list) { int size
		 * =list.size(); String yinjkbh =""; Map tMap = new HashMap(); for(int
		 * i=0;i<size;i++) { Yinjb yinjb = (Yinjb)list.get(i); if(i==0) {
		 * yinjkbh+=yinjb.getYinjkbh()+"<br>"; tMap.put(yinjb.getYinjkbh(), "");
		 * } else{ if(tMap.get(yinjb.getYinjkbh())==null) {
		 * yinjkbh+=yinjb.getYinjkbh()+"<br>"; tMap.put(yinjb.getYinjkbh(), "");
		 * } } } if(yinjkbh.length()!=0)
		 * zhanghTemp.setYinjkbh(yinjkbh.substring(0, yinjkbh.length()-4)); }
		 * //设置获取印鉴帐号
		 * if("".equals(zhanghTemp.getZhuzh())||null==(zhanghTemp.getZhuzh())) {
		 * zhanghTemp.setZhangh_(zhanghTemp.getZhangh()); }else{
		 * zhanghTemp.setZhangh_(zhanghTemp.getZhuzh()); }
		 */
		// zhanghTemp.setHuobh("["+zhanghTemp.getHuobh()+"]"+SystemDao.getHuobh(zhanghTemp.getHuobh()).getHuobmc());
		return zhanghTemp;
	}

	// 帐号物理删除
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
			// 删除帐号信息
			ZhanghbDao.deleteZhanghb(zhanghb);
			// 删除印鉴
			YinjbDao.delYinj(zhangh);
			// 删除印鉴组合
			YinjzhbDao.delYinjzh(zhangh);
			// 删除印鉴卡
			YinjkDao.deleteYinjk(zhangh);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			ZhanghbDao.shifSession();
		}
	}

	// 帐号销户恢复
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
			zhanghb.setZhanghzt("有效");
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

	// 账户数量统计
	public AccountNum zhanghNumber(String orgCode, String huobh, String shifbhxj) {
		String zhanghxzSQL = " and zhanghxz='" + huobh + "' ";
		if (huobh.equals("全部")) {
			zhanghxzSQL = " ";
		}
		String shifbhxjSQL = " internalorganizationnumber =? ";
		if ("是".equals(shifbhxj)) {
			String db_type = DanbwhDao.getDBtype();
			if ("oracle".equals(db_type)) {
				shifbhxjSQL = " internalorganizationnumber in (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber = ?) ";
			} else {
				shifbhxjSQL = " internalorganizationnumber in (select internalorganizationnumber from TABLE(ORGFUNCTION(?))) ";
			}
		}
		shifbhxjSQL += zhanghxzSQL;
		// -- 总数
		String zhanghzs = "select count(*) as zhanghzs  from zhanghb where "
				+ shifbhxjSQL;

		// --有印鉴
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
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","有"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs(String.valueOf(map
					.get("zhanghzs")));


			table.setSqlString(yinj_zhanghzs);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","无"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setWuyj_zhanghzs(String
					.valueOf(map.get("zhanghzs")));

			
			table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","有"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","有效"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_youx(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","有"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","销户"));
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
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","无"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","有效"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_youx(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","无"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","销户"));
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
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","有"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","久悬"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_jiux(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","有"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","账销案存"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_you_zxac(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","无"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","久悬"));
			list = DanbwhDao.doSelect(table);
			map = (Map) list.get(0);
			accountNum.setYouyj_zhanghzs_wu_jiux(String.valueOf(map
					.get("yinj_zhanghzt_zs")));

			//table.setSqlString(yinj_zhanghzt_shul);
			table.getSqlParameter().put(2,new SqlParameter("youwyj", "string","无"));
			table.getSqlParameter().put(3,new SqlParameter("zhanghzt", "string","账销案存"));
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
			zhanghb.setZhanghzt("销户");
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
				if (zhuzhb.getZhanghzt().equals("销户")) {
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
		// 主账号为空且印鉴卡号不为空且共用账户列表为空 可销卡
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
			if (yinjk.getYinjkzt().equals("作废")) {
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
			if (!zhanghb.getZhanghzt().equals("销户")) {
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
	 * 获取子账户列表信息 by wp
	 */
	public List getzzhlist(String account) throws SQLException {
		return zhuczhgxDao.getzizh(account);
	}

	// 冻结
	public void dongjAccount(String zhangh) {
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
		zhanghb.setZhanghzt("冻结");
		ZhanghbDao.updateZhanghb(zhanghb);

	}

	// 挂失
	public void guasAccount(String zhangh) {
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
		zhanghb.setZhanghzt("挂失");
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
						yinjk.setYinjkzt("已用");
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
		String yzhanghxz = "有效";
		try {
			SQLQuery query = session.createSQLQuery(sql);
			// query.addEntity(AccountManageLog.class);
			query.setString("managetype", "4");
			query.setString("zhangh", zhangh);

			List list = query.list();
			yzhanghxz = (String) list.get(0);
			yzhanghxz = yzhanghxz == null ? "有效"
					: yzhanghxz.indexOf("久悬") != -1 ? "久悬" : yzhanghxz
							.indexOf("账销案存") != -1 ? "账销案存" : "有效";
			// yzhanghxz = yzhanghxz == null ? "有效" : yzhanghxz;
		} catch (Exception e) {
			return yzhanghxz;
		} finally {
			super.getBaseHibernateDao().closeSession(session);
		}

		return yzhanghxz;
	}

	// 获取 印鉴卡共用的账号
	public TabsBo getYinjkShareList(String orgcode, String account,
			String yinjkbh) {
		Map parameterMap = new HashMap();
		String hql = "";
		TabsBo tabs = null;
		List<Zhanghb> zhanghbList = null;
		// 账号
		if ((account == null || "".equals(account))
				&& (yinjkbh == null || "".equals(yinjkbh.trim()))) {
			// hql = "from Zhanghb where jigh = :jigh and zhanghzt <> '销户' ";
			hql = "select distinct a.* from zhanghb a  start with a.zhuzh is not null and a.internalorganizationnumber=:jigh and zhanghzt<>'销户' connect by prior a.zhuzh=a.zhangh order by a.legalname,a.zhuzh desc,a.zhangh ";
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
			// 机构号+账号
			if ((account != null && !"".equals(account))
					&& (yinjkbh == null || "".equals(yinjkbh.trim()))) {

				hql = "from Zhanghb where jigh = :jigh and zhanghzt<>'销户' and (zhangh = :account  or zhuzh= :account1 )";
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
			// 机构号+印鉴卡号
			if ((account == null || "".equals(account))
					&& (yinjkbh != null && !"".equals(yinjkbh.trim()))) {
				hql = "from Zhanghb where jigh = :jigh and zhanghzt<>'销户' and (zhangh = :account  or zhuzh= :account1 )";
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
			// 机构号+印鉴卡号+账号
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
						zhanghb.setZhuzh("主账号");
					} else {
						zhanghb.setZhuzh("共用" + zhuzh);
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
			zhanghb.setZhuzh(objects[21] == null ? "主账号" : "共用"
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

	// 生成内部账号
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
			// 新增的印鉴卡需要存入印鉴卡表中
			for (String string : yinjkbhList) {
				if (string != null && string.trim().length() != 0) {
					Yinjk yinjk = new Yinjk();
					yinjk.setYinjkh(string);
					yinjk.setZhangh(zhanghb.getZhangh());
					yinjk.setYinjkzt("已用");
					yinjk.setJigh(zhanghb.getJigh());
					yinjk.setQiyrq(zhanghb.getKaihrq());
					// yinjk.setShifzk("0");

					YinjkDao.saveyinjk(yinjk);
				}
			}
			// 不用的印鉴卡需要销卡
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
		String hql = "select count(*) from Zhanghb where zhanghzt='有效' and zhuzh=:zhuzh";
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

	// 查询账户当日是否有变动
	public Zhanghtbb ischange(String zhangh) {
		Zhanghtbb zhanghtbb = zhanghtbbDao.getZhanghtbb(zhangh);
		return zhanghtbb;
	}

	// 检查该账号是否全部已审
	public boolean isHaveTrial(Zhanghb zhanghb) {
		if ("已审".equals(zhanghb.getZhanghshzt())
				&& "已审".equals(zhanghb.getYinjshzt())
				&& "已审".equals(zhanghb.getZuhshzt())) {
			return true;
		} else {
			return false;
		}
	}

	// 查询需要同步账户所有信息
	public String queryallfortongbu(String zhangh) throws BusinessException {
		String sendMapStr = "";
		try {
		// 传输的map(账号所有信息)
		// 存放该账户3张表信息的map
		Map<String, String> singleZhanghxx = new HashMap<String, String>();

		Zhanghtbb zhanghtbb = zhanghtbbDao.getZhanghtbb(zhangh);
		Zhanghb zhanghb = ZhanghbDao.getZhanghb(zhangh);
//		List<Map<String, String>> zhanghbMapList = ZhanghbDao
//				.getZhanghbforMap(zhangh);
//		zhanghbMapList = transitionZhanghbMap(zhanghbMapList);
//		Map<String, String> zhanghbMap = (Map<String, String>) ZhanghbDao
//				.attrConvert(zhanghbMapList).get(0);
		Map<String, String> zhanghbMap = ZhanghbDao.queryZhbByZhangh(zhangh);
		log.info("手工同步账户："+zhanghbMap);
//		zhanghbMap = transitionZhanghbMap(zhanghbMap);
		zhanghbMap = ZhanghbDao.attrConvert(zhanghbMap);
		//TODO
		String caozlx = zhanghtbb.getCaozlx();
		if ("0".equals(caozlx) || "2".equals(caozlx)) {

			// 查询其省行机构号
			String shenghjgh = ZhanghbDao.getShenghjgh(zhanghb.getJigh());
			zhanghbMap.put("SHENGHJGH", shenghjgh);
			zhanghbMap.put("CAOZLX", caozlx);
			// 将合格账户的账户表信息放入该同步账户中
			singleZhanghxx.put("ZHANGHB", JsonTool.toJsonForObject(zhanghbMap));
			// 该合格账户的印鉴表完整信息
			List yinjlist = YinjbDao.getYinjMapforTongb(zhangh);
			if (yinjlist != null && yinjlist.size() != 0) {
				// 将合格账户的印鉴表信息放入该同步账户中
				singleZhanghxx.put("YINJB", JsonTool.toJsonForArry(yinjlist));
			}

			// 该合格账户的印鉴组合表完整信息
			List yinjzhlist = YinjzhbDao.getYinjzhforMap(zhangh);
			if (yinjzhlist != null && yinjzhlist.size() != 0) {
				// 将合格账户的印鉴组合表信息放入该同步账户中
				singleZhanghxx.put("YINJZHB", JsonTool
						.toJsonForArry(yinjzhlist));
			}
			sendMapStr = JsonTool.toJsonForObject(singleZhanghxx);

			// 若该账户操作类型为删除，则只记录其账号和操作类型信息
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

	// 同步Socket
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
			// 缓存大小
			int bytesize = 1024;
			// 缓存
			byte[] shuz = new byte[bytesize];
			// 拼接报文
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

	// 对返回信息进行操作
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

	// 获得所要报文数量
	private int readMessageNum(InputStream ist) throws IOException {
		byte[] b = new byte[8];
		ist.read(b);
		return Integer.parseInt(new String(b, "GBK"));
	}

	// 获得最新已审印鉴
	public List getLastYSyj(String zhangh) throws BusinessException {
		return YinjbDao.getLastYSyj(zhangh);
	}

	// 获取最新已审组合
	public List getLastYSzh(String zhangh) throws BusinessException {
		return YinjbDao.getLastYSzh(zhangh);
	}

	// 查询帐号性质信息
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
	 * 主账户销户，并将印鉴归属给其子账户
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
		String sql1 = "update yinjk set zhangh =:gszh where zhangh=:zhangh and yinjkzt='已用'";// 修改印鉴卡的归属账号
		String sql2 = "update zhanghb set zhuzh=:gszh,youwyj=:ywyj,youwzh=:ywzh,yinjshzt=:yjshzt,zuhshzt=:zhshzt where zhuzh =:zhangh and zhangh<>:guiszh ";// 修改其他共用账户的主账户为新的归属账户
		String sql3 = "update zhanghb set yinjkh=:yinjkbh,zhuzh='',youwyj=:ywyj,youwzh=:ywzh,yinjshzt=:yjshzt,zuhshzt=:zhshzt where zhangh=:gszh ";// 修改归属账户的主账户为空
																																					// 和印鉴卡编号
		String sql4 = "update zhanghb set yinjkh='',zhanghzt='销户',zhuzh=:gszh where zhangh=:zhangh ";// 将原账户的印鉴卡设置为空
		String sql5 = "update yinjb set zhangh=:gszh where zhangh=:zhangh ";// 修改印鉴的所属账户
		String sql6 = "update yinjzhb set zhangh=:gszh where zhangh=:zhangh ";// 修改印鉴组合表的所属账户
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
			  throw new Exception("旧账号格式不正确，无法使用算法转换");
		  }
		  
		  
		if(!validateCheckDigit(n)){
			 throw new Exception("旧账号不合法，无法使用算法转换");
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
	 * 校验老账号
	 * 
	 * @param sAccntNumber
	 *            22位老账号
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
	 * 导入excel新旧简账户对照数据
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
						errorlist.add(newaccount+";新账号格式不正确");
						continue f1;
					}
					String oldaccount=String.valueOf(map.get(1)==null?"":map.get(1)).trim();
					if(oldaccount==null||!valiedata(oldaccount, "^\\d{22}$",true)){
						errorlist.add(newaccount+";旧账号格式不正确");
						continue f1;
					}
					String shortaccount=String.valueOf(map.get(2)==null?"":map.get(2)).trim();
					if(shortaccount==null||!valiedata(shortaccount, "^\\d{5,17}$",true)){
						errorlist.add(newaccount+";简账号格式不正确");
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
	 * 导入excel账户数据
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
								value=value.replaceAll("　", "");
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
				zhanghb.setYinjkbh3(rs.getString("YINJKQZH"));//使用废弃字段装载印鉴卡前缀号20150106qjk
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
	
	
	
	private static final String zhanghxzinfo=",基本户,一般户,临时户,专用户,定期户,通知户,外币户,内部户,个人高级结算户,代发工资户,其它户,账销案存户,久悬户,";
	private  Zhanghb  checkZhanghInfo( Zhanghb zhanghmsg) throws Exception{
		if(zhanghmsg==null){
			return null;
		}
		String account_=zhanghmsg.getZhangh();
		String head="["+zhanghmsg.getJigh()+"]";
		String account=head+zhanghmsg.getZhangh();
		zhanghmsg.setZhangh_(account_);
		//转换账号
		{
			// String reg="^\\d{9}$||^\\d{17}$||^\\d{22}$";
			String zhangh=zhanghmsg.getZhangh();

			if(zhangh==null||!valiedata(zhangh, "^\\d{9}$||^\\d{17}$||^\\d{22}$",false)){
				throw new Exception(account+";账号格式不正确");
			}
			if(zhangh.length()==22){
				//zhangh=OldAccountConfig.getInstance().get(zhangh);
				zhangh=ZhanghbDao.getZhanghFromOldAccount(zhangh);
				if(zhangh==null||zhangh.length()==22){
					throw new Exception(account+";转换新账号失败(账号),"+getNewAccountByOld(account_));
				}
			}
			zhanghmsg.setZhangh(zhangh);
		
		}
		//校验户名长度
		{
			String hum=zhanghmsg.getHum();
			if(hum==null||hum.equals("")){
				throw new Exception(account+";户名不能为空");
			}
			if(hum.getBytes().length>100){
				throw new Exception(account+";户名长度超过100字节");
			}
		}
		//转换主账号
		{
			String zhuzh=zhanghmsg.getZhuzh()==null?"":zhanghmsg.getZhuzh();
			String yinjkh=zhanghmsg.getYinjkbh()==null?"":zhanghmsg.getYinjkbh();
			if(zhuzh.equals("")){
				if(yinjkh.equals("")){
					throw new Exception(account+";印鉴卡和主账号不能同时为空");
				}
				zhanghmsg.setFuyrq("");
			}else{
				if(zhuzh.equals(account_)){
					throw new Exception(account+";账号和主账号不能相同");
				}
				if(!valiedata(zhuzh, "^\\d{9}$||^\\d{17}$||^\\d{22}$",true)){
					throw new Exception(account+";主账号格式不正确");
				}
				if(zhuzh.length()==22){
				//zhuzh=OldAccountConfig.getInstance().get(zhuzh);
					zhuzh=ZhanghbDao.getZhanghFromOldAccount(zhuzh);
				if(zhuzh==null||zhuzh.length()==22){
					throw new Exception(account+";转换新账号失败(主账号),"+getNewAccountByOld(zhuzh));
				}
				}
				zhanghmsg.setZhuzh(zhuzh);
				zhanghmsg.setYinjkbh("");
				//转换复用日期
				{
					String fuyrq=zhanghmsg.getFuyrq();
					if(!valiedata(fuyrq, "^\\d{8}$||^\\d{4}-\\d{2}-\\d{2}$",false)){
						throw new Exception(account+";复用日期格式不正确"+fuyrq);
					}
					fuyrq=DateUtil.turnToSimple(fuyrq);
					zhanghmsg.setFuyrq(fuyrq);
				}
			}
		}
		//校验一级分行号
		{
			if(!valiedata(zhanghmsg.getYinjkbh2(), "^\\d{4}$", false)){
				throw new Exception(account+";归属一级分行机构号格式不正确");
			}
		}
		//校验印鉴卡前缀号
		{
			if(!valiedata(zhanghmsg.getYinjkbh3(), "^\\d{4}$", false)){
				throw new Exception(account+";分行机构号格式不正确");
			}
		}
		//转换印鉴卡号
		{
			String yinjkh=zhanghmsg.getYinjkbh()==null?"":zhanghmsg.getYinjkbh();
			if(!yinjkh.equals("")){
				if(!valiedata(yinjkh, "^\\d{4,17}$||^\\d{22}$",true)){
					throw new Exception(account+";印鉴卡号格式不正确");
				}
				if(yinjkh.length()==22){
					//yinjkh=OldAccountConfig.getInstance().get(yinjkh);
					yinjkh=ZhanghbDao.getZhanghFromOldAccount(yinjkh);
					if(yinjkh==null||yinjkh.length()==22){
						throw new Exception(account+";转换新账号失败(印鉴卡号),"+getNewAccountByOld(yinjkh));
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
		//转换是否电核
		{
			String shifdh=zhanghmsg.getShifdh();
			if(shifdh==null||shifdh.equals("否")){
				zhanghmsg.setShifdh("1");
			}else{
				zhanghmsg.setShifdh("0");
			}
		}

		//转换开户日期
		{
			String kaihrq=zhanghmsg.getKaihrq()==null?"":zhanghmsg.getKaihrq();
			if(!valiedata(kaihrq, "^\\d{8}$||^\\d{4}-\\d{2}-\\d{2}$",false)){
				throw new Exception(account+";开户日期格式不正确"+kaihrq);
			}
			kaihrq=DateUtil.turnToSimple(kaihrq);
			zhanghmsg.setKaihrq(kaihrq);
		}
		//校验联系人信息
		{

			if(zhanghmsg.getLianxr()!=null&&zhanghmsg.getLianxr().getBytes().length>30){
				throw new Exception(account+";联系人长度大于30字节");
			}
			if(zhanghmsg.getLianxr()!=null&&(zhanghmsg.getLianxr().indexOf(",")!=-1||zhanghmsg.getLianxr().indexOf("，")!=-1)){
				throw new Exception(account+";联系人含有逗号,请替换为分号或者顿号");
			}
			if(zhanghmsg.getLianxr2()!=null&&zhanghmsg.getLianxr2().getBytes().length>30){
				throw new Exception(account+";联系人2长度大于30字节");
			}

			if(zhanghmsg.getLianxr2()!=null&&(zhanghmsg.getLianxr2().indexOf(",")!=-1||zhanghmsg.getLianxr2().indexOf("，")!=-1)){
				throw new Exception(account+";联系人2含有逗号,请替换为分号或者顿号");
			}
			if(zhanghmsg.getFuzr()!=null&&zhanghmsg.getFuzr().getBytes().length>30){
				throw new Exception(account+";负责人长度大于30字节");
			}

			if(zhanghmsg.getFuzr()!=null&&(zhanghmsg.getFuzr().indexOf(",")!=-1||zhanghmsg.getFuzr().indexOf("，")!=-1)){
				throw new Exception(account+";负责人含有逗号,请替换为分号或者顿号");
			}
			if(zhanghmsg.getFuzr2()!=null&&zhanghmsg.getFuzr2().getBytes().length>30){
				throw new Exception(account+";负责人2长度大于30字节");
			}

			if(zhanghmsg.getFuzr2()!=null&&(zhanghmsg.getFuzr2().indexOf(",")!=-1||zhanghmsg.getFuzr2().indexOf("，")!=-1)){
				throw new Exception(account+";负责人2含有逗号,请替换为分号或者顿号");
			}
		}
		//校验备注信息
		{
			if(zhanghmsg.getBeiz()!=null&&zhanghmsg.getBeiz().getBytes().length>200){
				throw new Exception(account+";备注长度大于200字节");
			}

			if(zhanghmsg.getBeiz()!=null&&(zhanghmsg.getBeiz().indexOf(",")!=-1||zhanghmsg.getBeiz().indexOf("，")!=-1)){
				throw new Exception(account+";备注含有逗号,请替换为分号或者顿号");
			}
			//zhanghmsg.setBeiz("");
		}
		

		//转换电话号码
		{
				if(!valiedata(zhanghmsg.getLianxrqh(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";联系人1电话区号格式不正确");
				}
				if(!valiedata(zhanghmsg.getLianxrqh2(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";联系人2电话区号格式不正确");
				}
				if(!valiedata(zhanghmsg.getFuzrqh(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";负责人1电话区号格式不正确");
				}
				if(!valiedata(zhanghmsg.getFuzrqh2(), "0\\d{2,3}||\\d{3}", true)){
					throw new Exception(account+";负责人2电话区号格式不正确");
				}
				zhanghmsg.setLianxrqh(zhanghmsg.getLianxrqh()==null||zhanghmsg.getLianxrqh().equals("")?"":zhanghmsg.getLianxrqh().substring(0,1).equals("0")?zhanghmsg.getLianxrqh().substring(1):zhanghmsg.getLianxrqh());
				zhanghmsg.setLianxrqh2(zhanghmsg.getLianxrqh2()==null||zhanghmsg.getLianxrqh2().equals("")?"":zhanghmsg.getLianxrqh2().substring(0,1).equals("0")?zhanghmsg.getLianxrqh2().substring(1):zhanghmsg.getLianxrqh2());
				zhanghmsg.setFuzrqh(zhanghmsg.getFuzrqh()==null||zhanghmsg.getFuzrqh().equals("")?"":zhanghmsg.getFuzrqh().substring(0,1).equals("0")?zhanghmsg.getFuzrqh().substring(1):zhanghmsg.getFuzrqh());
				zhanghmsg.setFuzrqh2(zhanghmsg.getFuzrqh2()==null||zhanghmsg.getFuzrqh2().equals("")?"":zhanghmsg.getFuzrqh2().substring(0,1).equals("0")?zhanghmsg.getFuzrqh2().substring(1):zhanghmsg.getFuzrqh2());
				
				
				if(!valiedata(zhanghmsg.getLianxrfjh(), "\\d{1,6}", true)){
					throw new Exception(account+";联系人1电话分机号格式不正确");
				}
				if(!valiedata(zhanghmsg.getLianxrfjh2(), "\\d{1,6}", true)){
					throw new Exception(account+";联系人2电话分机号格式不正确");
				}
				if(!valiedata(zhanghmsg.getFuzrfjh(), "\\d{1,6}", true)){
					throw new Exception(account+";负责人1电话分机号格式不正确");
				}
				if(!valiedata(zhanghmsg.getFuzrfjh2(), "\\d{1,6}", true)){
					throw new Exception(account+";负责人2电话分机号格式不正确");
				}

				if(!valiedata(zhanghmsg.getDianh(), "\\d{5,11}", true)){
					throw new Exception(account+";联系人1电话号格式不正确:"+zhanghmsg.getDianh());
				}
				if(!valiedata(zhanghmsg.getDianh2(), "\\d{5,11}", true)){
					throw new Exception(account+";联系人2电话号格式不正确:"+zhanghmsg.getDianh2());
				}
				if(!valiedata(zhanghmsg.getFuzrdh(), "\\d{5,11}", true)){
					throw new Exception(account+";负责人1电话号格式不正确:"+zhanghmsg.getFuzrdh());
				}
				if(!valiedata(zhanghmsg.getFuzrdh2(), "\\d{5,11}", true)){
					throw new Exception(account+";负责人2电话号格式不正确:"+zhanghmsg.getFuzrdh2());
				}
			
		}
		
		//转换账户性质和账户状态
		{
			String zhanghxz=zhanghmsg.getZhanghxz()==null?"":zhanghmsg.getZhanghxz();
			if(zhanghxz.length()==0){
				throw new Exception(account+";账户性质不能为空");
			}else{
				if(zhanghxzinfo.indexOf(","+zhanghxz+",")==-1){
					throw new Exception(account+";账户性质不合法");
				}
				zhanghmsg.setZhanghzt("有效");
				if(zhanghxz.equals("久悬户")){
					zhanghmsg.setZhanghxz("内部户");
					zhanghmsg.setZhanghzt("久悬");
				}else if(zhanghxz.equals("账销案存户")||zhanghxz.equals("案存户")){
					zhanghmsg.setZhanghxz("内部户");
					zhanghmsg.setZhanghzt("账销案存");
				}else if(zhanghxz.equals("外币经常项目账户")||zhanghxz.equals("外币资本金账户")){
					zhanghmsg.setZhanghxz("外币户");
				}
			}
			
		}

		//转换通兑标志
		{
			String tongctd=zhanghmsg.getTongctd()==null?"":zhanghmsg.getTongctd();
			if(tongctd.length()==0){
				throw new Exception(account+";账户通兑标志不能为空");
			}else{
				if(",全国,一级分行全辖,一级分行本级,二级分行,不通兑,".indexOf(","+tongctd+",")==-1){
					throw new Exception(account+";账户通兑标志不合法");
				}
			}
			
		}
		zhanghmsg.setZhanghshzt("未审");
		zhanghmsg.setYinjshzt("未审");
		zhanghmsg.setYouwyj("无");
		zhanghmsg.setYouwzh("无");
		zhanghmsg.setZuhshzt("未审");
		
		return zhanghmsg;
		}
	private String getNewAccountByOld(String account) {
		String newaccount="";
		try {
			 newaccount=parseTypeN_(account, 17);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "可由算法转换为："+newaccount;
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
					yinjk.setYinjkzt("已用");
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
