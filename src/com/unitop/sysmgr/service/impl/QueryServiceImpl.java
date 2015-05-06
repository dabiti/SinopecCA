package com.unitop.sysmgr.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.sinopec.table.model.Table;
import com.unitop.config.DBinfoConig;
import com.unitop.exception.BusinessException;
import com.unitop.framework.util.DateTool;
import com.unitop.framework.util.Format;
import com.unitop.sysmgr.bo.AccountLog;
import com.unitop.sysmgr.bo.Autopasscount;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.Dzcxinfo;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.SealcheckLog;
import com.unitop.sysmgr.bo.Shouqrzb;
import com.unitop.sysmgr.bo.SystemControlParameter;
import com.unitop.sysmgr.bo.SystemManageLog;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhanghtbb;
import com.unitop.sysmgr.dao.DanbwhDao;
import com.unitop.sysmgr.dao.OrgDao;
import com.unitop.sysmgr.dao.SystemDao;
import com.unitop.sysmgr.dao.TabsDao;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.form.AccountinfoForm;
import com.unitop.sysmgr.form.SealchecklogForm;
import com.unitop.sysmgr.service.QueryService;
@Service("queryService")
public class QueryServiceImpl extends BaseServiceImpl implements QueryService {

	@Resource
	private SystemDao SystemDao;
	@Resource
	private TabsDao TabsDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	public DanbwhDao DanbwhDao;
	@Resource
	public ZhanghbDao zhangbDao;
	
	//数据查询DQL
	public List execDBForDQL(String sql,Map<String, String> pmap)
	{
		List<Map> rlist = null; 
		Table table = new Table(sql);
		table.setSqlParameter(pmap);
		try {
			rlist = DanbwhDao.doSelectForUpperCase(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rlist == null) rlist = new ArrayList();
		return rlist;
	}
	//数据操纵语言DML
	public void execDBForDML(String sql,Map<String, String> pmap)
	{
		Table table = new Table(sql);
		table.setSqlParameter(pmap);
		try {
			DanbwhDao.doDML(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TabsBo findSealCheckLog(String sequence,String account, String clerknum,
			String checkmode, String checknum, String checkresult,
			String begindate, String enddate,String orgCode) {
			StringBuffer sqlbuffer = new StringBuffer();
			//江西流水号begin
			if (sequence != null && sequence.trim().length() > 0)
				sqlbuffer.append(" and s.pingzbsm = :sequence");
			//end
			if (account != null && account.trim().length() > 0)
				sqlbuffer.append(" and s.zhangh = :account");
			if (clerknum != null && clerknum.trim().length() > 0)
				sqlbuffer.append(" and s.clerknum = :clerknum");
			if (checkmode != null && checkmode.trim().length() > 0)
				sqlbuffer.append(" and s.checkmode = :checkmode");
			if (checknum != null && checknum.trim().length() > 0)
				sqlbuffer.append(" and s.checknum = :checknum");
			if (checkresult != null && checkresult.trim().length() > 0)
				sqlbuffer.append(" and s.checkresult = :checkresult");
			if ( orgCode!= null && orgCode.trim().length() > 0)
				sqlbuffer.append(" and s.internalorganizationnumber = :clerkorgcode");
			sqlbuffer.append(" and s.checkdate >= :begindate");
			sqlbuffer.append(" and s.checkdate <= :enddate");
			String sql = ("select t.zhangh,t.checknum,t.qiyrq,t.checkresult ,t.checkmode,t.clerknum,t.checkdate,t.checktime,a.legalname, t.sealinktype,t.sealinknum,a.zhuzh,t.pingzbsm from (select * from sealchecklog s where 1=1 "+sqlbuffer.toString()+") t left join zhanghb a on t.zhangh = a.zhangh  order by t.checkdate desc,t.checktime desc");
			Map parameterMap = new HashMap();
			//江西begin
			if (sequence != null && sequence.trim().length() > 0)
				parameterMap.put("sequence", sequence);
			//end
			if (account != null && account.trim().length() > 0)
				parameterMap.put("account", account);
			if (clerknum != null && clerknum.trim().length() > 0)
				parameterMap.put("clerknum", clerknum);
			if (checknum != null && checknum.trim().length() > 0)
				parameterMap.put("checknum", checknum);
			parameterMap.put("begindate", begindate);
			parameterMap.put("enddate", enddate);
			if (checkmode != null && checkmode.trim().length() > 0)
				parameterMap.put("checkmode", checkmode);
			if (checkresult != null && checkresult.trim().length() > 0)
				parameterMap.put("checkresult", checkresult);
			if (orgCode != null && orgCode.trim().length() > 0)
				parameterMap.put("clerkorgcode", orgCode);
			
			TabsBo tabsBoModle = TabsDao.pagingObjectForSql(sql, this.tabsBo.getDangqym(), this.tabsBo.getFenysl(), parameterMap);
			tabsBoModle.setSql(sql);
			//System.out.println(JSONObject.fromObject(parameterMap).toString());
			tabsBoModle.setParamterMapStr(JSONObject.fromObject(parameterMap).toString());
			List<SealcheckLog> result = new ArrayList<SealcheckLog>();
			for (Iterator iter = tabsBoModle.getList().iterator(); iter.hasNext();)
			{
				Object[] element = (Object[]) iter.next();
				SealcheckLog slog = new SealcheckLog();
				slog.setAccount((String) element[0]);
				slog.setChecknum((String) element[1]);
				slog.setQiyrq((String) element[2]);
				slog.setCheckresult((String) element[3]);
				slog.setCheckmode((String) element[4]);
				slog.setClerknum((String) element[5]);
				slog.setCheckdate((String) element[6] + " " + String.valueOf(element[7].equals("null")||element[7]==null?"":element[7]));
				slog.setChecktime((String) element[7]);
				slog.setAccountname((String) element[8]);
				slog.setSealinktype((String) element[9]);
				slog.setSealinknum((String) element[10]);
				slog.setZhuzh((String) element[11]);
				//江西
				slog.setPingzbsm((String) element[12]);				
				//设置获取印鉴帐号
				if("".equals(slog.getZhuzh())||null==(slog.getZhuzh()))
				{
					slog.setZhangh_(slog.getAccount());
				}else{
					slog.setZhangh_(slog.getZhuzh());
				}
				result.add(slog);
			}
			tabsBoModle.setList(result);
		return tabsBoModle;
	}
	
	/*
	 * 查询系统管理日志
	 * by ldd
	 */
	public TabsBo findSystemManageLog(String admincode, String begindate,String enddate) {
		TabsBo tabs = null;
		String hql = "from SystemManageLog  where admincode =:admincode and operdate >=:begindate and operdate <=:enddate order by operdate desc";
		Map parameterMap = new HashMap();
		parameterMap.put("admincode", admincode);
		parameterMap.put("begindate", begindate);
		parameterMap.put("enddate", DateTool.xiaDay(enddate,"yyyy-MM-dd"));
		tabs = TabsDao.pagingForHql(hql, tabsBo.getDangqym(), tabsBo.getFenysl(), parameterMap);
		return tabs;
	}
	
	public TabsBo findAuthorizeLog(String account, String begindate,
			String enddate) {
		TabsBo tabs = null;
		String hql = "from Shouqrzb  where account =:account and managedate >=:begindate and managedate <=:enddate order by managedate desc";
		Map parameterMap = new HashMap();
		parameterMap.put("account", account);
		parameterMap.put("begindate", begindate);
		parameterMap.put("enddate", DateTool.xiaDay(enddate,"yyyy-MM-dd"));
		tabs = TabsDao.pagingForHql(hql, tabsBo.getDangqym(), tabsBo.getFenysl(), parameterMap);
		tabs.setSql("select * "+hql);
		tabs.setParamterMapStr(JSONObject.fromObject(parameterMap).toString());
		return tabs;
	}
	
	public TabsBo findAccountLog(String account, String begindate,
			String enddate, String[] managetype, String Industrycharacter,
			String orgcode) {
		Map paraMap=new HashMap();
		StringBuffer sql = new StringBuffer(" from accountmanagelog t,zhanghb a where t.zhangh = a.zhangh ");
		
		if (account != null && account.trim().length() > 0) 
		{
			sql.append(" and t.zhangh =:account ");
			paraMap.put("account", account);
		}else{
			sql.append(" and t.internalorganizationnumber =:orgcode  ");
			paraMap.put("orgcode", orgcode);
			
		}
		if (begindate != null && begindate.trim().length() > 0)
		{
			sql.append(" and managedate >=:begindate  ");
			paraMap.put("begindate", begindate);
		}
		if (enddate != null && enddate.trim().length() > 0)
		{
			sql.append(" and managedate <=:enddate  ");
			paraMap.put("enddate", enddate);
		}
		String sqltemp = "";
		boolean bool= true;
		if (managetype != null)
		{	
			for (int i = 0; i < managetype.length; i++)
			{	
				if("全部".equals(managetype[i]))
				{
					bool = false;
				}
				if(i==0)
				{
					sqltemp+=(" and managetype in (");
				}
				
				if (managetype[i] != null && managetype[i].trim().length() > 0)
				{
					sqltemp+=("'" + managetype[i]+ "'");
				}
				
				if(i==managetype.length-1)
				{
					sqltemp+=(")");
				}else{
					sqltemp+=(",");
				}
			}
		}
		//如果选择“全部” 默认放开 查询全部帐号日志
		if(bool)
		{
			sql.append(sqltemp);
		}else{
	//		sql.append(" and trim(managetype) in ('0','1','4','5','2','3','6','7','8','9') ");
		}
		
		/*if (!Industrycharacter.equals("")) 
		{
			sql.append(" and a.huobh = '" + Industrycharacter + "' ");
		}*/
		String SQL = "select t.zhangh,t.clerknum,t.managedate,t.managetime,t.managetype ,a.legalname,a.internalorganizationnumber,a.currency,t.managecontent,t.oldcontent,t.newcontent"
				+ sql.toString()
				+ " order by t.managedate desc,t.managetime desc,a.internalorganizationnumber desc,t.managetype desc,a.currency desc,t.zhangh desc ";
		
		
		
		TabsBo tabsBoModle = TabsDao.pagingObjectForSql(SQL, this.tabsBo.getDangqym(), this.tabsBo.getFenysl(), paraMap);
		
		List<AccountLog> result = new ArrayList<AccountLog>();
		for (Iterator it = tabsBoModle.getList().iterator(); it.hasNext();) 
		{
			Object[] element = (Object[]) it.next();
			AccountLog alog = new AccountLog();
			alog.setAccount(String.valueOf(element[0]));
			alog.setClerknum(String.valueOf(element[1]));
			alog.setOperdate(String.valueOf(element[2]) + " " + String.valueOf(element[3]));
			String caozlx="";
			try{
			switch (Integer.valueOf(String.valueOf(element[4]))) {
			case 0:
				caozlx="开户";
				break;
			case 6:
				caozlx="印鉴建库";
				break;
			case 7:
				caozlx="印鉴审核";
				break;
			case 1:
				caozlx="基本信息修改";
				break;
			case 8:
				caozlx="印鉴变更";
				break;
			case 2:
				caozlx="联系人变更";
				break;
			case 3:
				caozlx="解除共用关系";
				break;
			case 4:
				caozlx="销户";
				break;
			case 5:
				caozlx="销户恢复";
				break;
			case 9:
				caozlx="状态维护";
				break;
			}
			}catch(Exception e){ 
				caozlx=String.valueOf(element[4]);
			};
			alog.setCaozlx(caozlx);
			alog.setAccountname(String.valueOf(element[5]));
			alog.setNETPOINTFLAG(String.valueOf(element[6]));
			//alog.setINDUSTRYCHARACTER("["+String.valueOf(element[7])+"]"+SystemDao.getHuobh(String.valueOf(element[7])).getHuobmc());
			alog.setContent(String.valueOf(element[8]));
			if(alog.getCaozlx().equals("联系人变更")){
				alog.setContent("变更前："+String.valueOf(element[9])+";变更后:"+String.valueOf(element[10])+";"+String.valueOf(element[8]==null?"":element[8]));};
			result.add(alog);
		}
		tabsBoModle.setList(result);
		return tabsBoModle;
	}

	public TabsBo pingzhengyanyinlog(String sequence,String account,
			String clerknum, String checkmode, String checknum,
			String checkresult, String begindate, String enddate,String orgCode,String checktype,String canal) {
			StringBuffer sqlbuffer = new StringBuffer("select c.zhangh,c.checknum,c.checkdate,c.checktime,c.clerknum,c.checkresult,c.checkmode,c.remark,to_char(c.money),c.zuhgz,c.chuprq,c.doublesignatureclerknum,p.pingzmc,c.id,c.credencetype,c.logtype,c.internalorganizationnumber,c.xitlx,c.singleink from(select a.zhangh,a.checknum,a.checkdate,a.checktime,a.clerknum,a.checkresult,a.checkmode,a.remark,a.money,a.zuhgz,a.chuprq,a.doublesignatureclerknum,a.id,a.credencetype,a.internalorganizationnumber,'qt' as logtype,a.xitlx,replace(to_char(wmsys.wm_concat(b.sealinknum || '号' ||b.sealinktype || b.checkmode ||b.checkresult)),',','<br/>') as singleink  from  CREDENCECHECKLOG a,sealchecklog b where 1=1 and a.id=b.id ");
			StringBuffer one=new StringBuffer();
			StringBuffer two=new StringBuffer();
			Map parameterMap = new HashMap();
			//江西begin
			if (sequence != null && sequence.trim().length() > 0){
				one.append(" and a.pingzbsm = :sequence");
				two.append(" and d.pingzbsm = :sequence2");
				parameterMap.put("sequence", sequence);
				parameterMap.put("sequence2", sequence);
			}
			//end
			if (account != null && account.trim().length() > 0){
				one.append(" and a.zhangh = :account");
				two.append(" and d.zhangh = :account2");
				parameterMap.put("account", account);
				parameterMap.put("account2", account);
			}
			if (clerknum != null && clerknum.trim().length() > 0){
				one.append(" and a.clerknum = :clerknum");
				two.append(" and d.clerkid1 = :clerknum2");
				parameterMap.put("clerknum", clerknum);
				parameterMap.put("clerknum2", clerknum);
			}
			if (checkmode != null && checkmode.trim().length() > 0){
				one.append(" and a.checkmode = :checkmode");
				two.append(" and d.yanyms = :checkmode2");
				parameterMap.put("checkmode", checkmode);
				parameterMap.put("checkmode2", checkmode);
			}
			if (checknum != null && checknum.trim().length() > 0){
				one.append(" and a.checknum = :checknum");
				two.append(" and d.pingzh = :checknum2");
				parameterMap.put("checknum", checknum);
				parameterMap.put("checknum2", checknum);
			}
			if (checkresult != null && checkresult.trim().length() > 0 && !checkresult.equals("")){
				
				one.append(" and a.checkresult = :checkresult");
				two.append(" and d.yanyjg = :checkresult2");
				parameterMap.put("checkresult", checkresult);
				parameterMap.put("checkresult2", checkresult);
			}
			if (orgCode != null && orgCode.trim().length() > 0 && !orgCode.equals("")){
				
				one.append(" and a.internalorganizationnumber = :orgCode");
				two.append(" and d.internalorganizationnumber = :orgCode2");
				parameterMap.put("orgCode", orgCode);
				parameterMap.put("orgCode2", orgCode);
			}

			if (checktype != null && checktype.trim().length() > 0 && !checktype.equals("")){
				
				one.append(" and a.credencetype = :checktype ");
				two.append(" and d.pingzbs = :checktype2 ");
				parameterMap.put("checktype", checktype);
				parameterMap.put("checktype2", checktype);
			}

			if (canal != null && canal.trim().length() > 0 && !canal.equals("")){
				if("AB".equals(canal)){
					one.append(" and a.xitlx in ('AB','BPC') ");
					two.append(" and d.xitbs in ('AB','BPC') ");
					//parameterMap.put("canal", "AB,BPC");
					//parameterMap.put("canal2", "AB,BPC");
				}else if("PL".equals(canal)){
					one.append(" and a.xitlx = :canal");
					two.append(" and d.xitbs = :canal2");
					parameterMap.put("canal", "097");
					parameterMap.put("canal2", "097");
				}else{
					one.append(" and a.xitlx = :canal");
					two.append(" and d.xitbs = :canal2");
					parameterMap.put("canal", canal);
					parameterMap.put("canal2", canal);
				}
			}
			  one.append(" and a.checkdate >= :begindate ");
			  one.append(" and a.checkdate <= :enddate ");
			  two.append(" and d.renwrq >= :begindate2 ");
			  two.append(" and d.renwrq <= :enddate2 ");
			  parameterMap.put("begindate", begindate);
			  parameterMap.put("enddate", enddate);
			  parameterMap.put("begindate2", begindate);
			  parameterMap.put("enddate2", enddate);
			sqlbuffer.append(one.toString());
			sqlbuffer.append(" group by a.zhangh,a.checknum,a.checkdate,a.checktime,a.clerknum,a.checkresult,a.checkmode,a.remark,a.money,a.zuhgz,a.chuprq,a.doublesignatureclerknum,a.id,a.credencetype,a.internalorganizationnumber,a.xitlx ");
			sqlbuffer.append("union all select d.zhangh as zhangh,d.pingzh as checknum,d.renwrq as checkdate,'' as checktime,d.clerkid1 as clerknum,d.yanyjg as checkresult,d.yanyms as checkmode,d.weigyy as remark,d.jine as money,d.tonggzh as zuhgz,d.chuprq,clerkid2 as doublesignatureclerknumt,d.renwbs as id,d.pingzbs as credencetype,d.internalorganizationnumber,'ht' as logtype,d.xitbs,replace(to_char(wmsys.wm_concat(e.yinjbh || '号' || e.yinjbz ||e.yanyms || e.yanyjg)),',','<br/>') as singleink from ci_renwxx d, ci_yyrz e where 1=1 and d.renwbs = e.renwbs ");
			sqlbuffer.append(two.toString());
			sqlbuffer.append(" group by d.zhangh,d.pingzh,d.renwrq,d.clerkid1,d.yanyjg,d.yanyms,d.weigyy,d.jine,d.tonggzh,d.chuprq,d.clerkid2,d.renwbs,d.pingzbs,d.internalorganizationnumber,d.xitbs ");
			sqlbuffer.append(" ) c left join PINGZPZB p on c.credencetype=p.pingzbs ");
			sqlbuffer.append(" order by c.checkdate desc,c.checktime desc");
	
			TabsBo tabsBoModle = TabsDao.pagingObjectForSql(sqlbuffer.toString(), this.tabsBo.getDangqym(), this.tabsBo.getFenysl(), parameterMap);
			//tabsBoModle.setSql(sql)
			List<SealcheckLog> result = new ArrayList<SealcheckLog>();
//			Session session = this.getHibernateSession();
			Session session = this.getBaseHibernateDao().getHibernateSession();
			try{
				for (Iterator iter = tabsBoModle.getList().iterator(); iter.hasNext();)
				{
					try {
						Object[] element = (Object[]) iter.next();
						SealcheckLog slog = new SealcheckLog();
						slog.setAccount((String) element[0]);
						slog.setChecknum((String) element[1]);
						slog.setCheckdate((String) element[2] + " "+ String.valueOf(element[3]==null||element[3].equals("null")?"":element[3]));
						slog.setChecktime((String) element[3]);
						slog.setClerknum((String) element[4]);
						slog.setCheckresult((String) element[5]);
						slog.setCheckmode((String) element[6]);
						slog.setRemark((String) element[7]);
						//金额格式化
						double jine = Double.valueOf((element[8]).toString());
						double i = 100.00d;
						slog.setMoney(Format.formatJine((double)jine/i));
						slog.setZuhgz((String) (element[9]));
						//江西
						//slog.setPingzbsm((String) (element[10]));
						slog.setChuprq((String) (element[10]));
						slog.setDoublesignatureclerknum( element[11]==null?"":((String) element[11]).equals("null")?"":(String) element[11]);
						slog.setPingzlx((String)element[12]);
						/*if(String.valueOf(element[15]).indexOf("q")!=-1){
							slog.setBatchinfo(createQtBatchinfo((String)element[13],session));
						}else{
							slog.setBatchinfo(createHtBatchinfo((String)element[13],session));
						}*/
						slog.setRemark(element[16]==null?"":((String) element[16]).equals("null")?"":(String) element[16]);
						slog.setCanal(element[17]==null?"":((String) element[17]).equals("null")?"":((String) element[17]).equals("097")?"批量验印":((String) element[17]).equals("YY")?"单笔验印":((String) element[17]).equals("BPC")||((String) element[17]).equals("AB")?"流程验印":(String) element[17]);
						slog.setBatchinfo(element[18]==null?"":((String) element[18]));
						result.add(slog);
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}finally{
				this.getBaseHibernateDao().closeSession(session);
			}
			tabsBoModle.setList(result);
			return tabsBoModle;
	}


/*	private String createQtBatchinfo(String id,Session session) {
		String sql = "select s.sealinknum,s.sealinktype,s.checkmode,s.checkresult from SEALCHECKLOG s where s.id=:id ";
		if(session ==null){
			session=this.getHibernateSession();
		}
		List list = null;
		StringBuffer msg=new StringBuffer();
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("id", id);
			list = query.list();
			for (Iterator iter = list.iterator(); iter.hasNext();)
			{
				try {
					Object[] element = (Object[]) iter.next();
					msg.append((String) element[0]==null?"":(String) element[0]).append("号");
					msg.append((String) element[1]==null?"":(String) element[1]);
					msg.append((String) element[2]==null?"":(String) element[2]).append((String) element[3]==null?"":(String) element[3]).append("<br/>");
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			return msg.toString();
	}
	private String createHtBatchinfo(String id,Session session) {
		String sql = "select s.yinjbh,s.yinjbz,s.yanyms,s.yanyjg from CI_YYRZ s where s.renwbs=:id ";
		if(session ==null){
			session=this.getHibernateSession();
		}
		List list = null;
		StringBuffer msg=new StringBuffer();
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("id", id);
			list = query.list();
			for (Iterator iter = list.iterator(); iter.hasNext();)
			{
				try {
					Object[] element = (Object[]) iter.next();
					msg.append((String) element[0]==null?"":(String) element[0]).append("号");
					msg.append((String) element[1]==null?"":(String) element[1]);
					msg.append((String) element[2]==null?"":(String) element[2]).append((String) element[3]==null?"":(String) element[3]).append("<br/>");
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			return msg.toString();
	}*/
	public List getCustomSealcheckList(String guiy) {
		return getCustomQueryList(guiy, "02");
	}

	public List getCustomAccountinfoList(String guiy) {
		return getCustomQueryList(guiy, "01");
	}

	public List getCustomQueryList(String guiy, String QueryType) {
		String sql = "select id,sql,statue,isdownload,cj_time,error,clerknum,wc_time,xz_time,description from dzcxinfo where clerknum=:clerknum and taskflag=:taskflag";
		Session session = this.getHibernateSession();
		List list = null;
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(Dzcxinfo.class));
			query.setString("clerknum", guiy);
			query.setString("taskflag", QueryType);
			query.addScalar("id", Hibernate.STRING);
			query.addScalar("sql", Hibernate.STRING);
			query.addScalar("statue", Hibernate.STRING);
			query.addScalar("isdownload", Hibernate.STRING);
			query.addScalar("cj_time", Hibernate.STRING);
			query.addScalar("error", Hibernate.STRING);
			query.addScalar("clerknum", Hibernate.STRING);
			query.addScalar("wc_time", Hibernate.STRING);
			query.addScalar("xz_time", Hibernate.STRING);
			query.addScalar("description", Hibernate.STRING);
			list = query.list();
		} finally {
			this.closeSession(session);
		}
		return list;
	}

//	public void CreateCustomaQuery1(String TaskFlag, String clerkcode,
//			String sqlstr, int QueryType, String description) throws Exception {
//		String dz_field = "account=账户;money=金额;checknum=支票号;checkdate=验印日期;checktime=验印时间;clerknum=柜员代码;checkresult=验印结果;checkmode=验印方式;remark=备注";
//		String sql = "insert into dzcxinfo (ID, SQL, FILEINFO, STATUE, ISDOWNLOAD, CJ_TIME, ERROR, CLERKNUM,DZ_FIELD,TASKFLAG,DESCRIPTION) "
//				+ "values (?,?,empty_blob(),'1','0',to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),'无',?,?,?,?)";
//
//		Connection con = DataSourceUtils.getConnection(DataSourceFactory.getDataSourceByPool());
//		PreparedStatement pre;
//		try {
//			pre = con.prepareStatement(sql);
//			pre.setString(1, "02" + clerkcode + TaskFlag);
//			pre.setString(2, sqlstr);
//			pre.setString(3, clerkcode);
//			pre.setString(4, dz_field);
//			pre.setString(5, "02");
//			pre.setString(6, description);
//			pre.execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					closeConnection(con);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

//	public void CreateCustomaQuery(String TaskFlag, String clerkcode,
//			String sqlstr, int QueryType, String description) throws Exception {
//		String dz_field = "zhangh=账户;hum=户名;jigh=机构号;kehh=客户号;youzbm=邮编;kaihrq=开户日期;qiyongrq=启用日期;zhanghzt=账户状态;tongctd=通存通兑;huobh=货币号;beiz=备注;zhanghshzt=审核状态;youwyj=是否有印签";
//		String sql = "insert into dzcxinfo (ID, SQL, FILEINFO, STATUE, ISDOWNLOAD, CJ_TIME, ERROR, CLERKNUM,DZ_FIELD,TASKFLAG,description) "
//				+ "values (?,?,empty_blob(),'1','0',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'无',?,?,?,?)";
//
//		Connection con = DataSourceUtils.getConnection(DataSourceFactory
//				.getDataSourceByPool());
//		PreparedStatement pre;
//		try {
//			pre = con.prepareStatement(sql);
//			pre.setString(1, "01" + clerkcode + TaskFlag);
//			pre.setString(2, sqlstr);
//			pre.setString(3, clerkcode);
//			pre.setString(4, dz_field);
//			pre.setString(5, "01");
//			pre.setString(6, description);
//			pre.execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					closeConnection(con);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

//	public void CreateCustomaQuery_SealCheckLog(String TaskFlag,
//			String clerkCode, SealchecklogForm sealchecklogForm)
//			throws Exception {
//		String account = sealchecklogForm.getAccount();
//		String begindate = sealchecklogForm.getBegindate();
//		String enddate = sealchecklogForm.getEnddate();
//		String clerknum = sealchecklogForm.getClerknum();
//		String checkmode = sealchecklogForm.getCheckmode();
//		String checknum = sealchecklogForm.getChecknum();
//		String checkresult = sealchecklogForm.getCheckresult();
//		StringBuffer sql = new StringBuffer(
//				"select s.zhangh,s.money,s.checknum,s.checkdate,s.checktime,s.clerknum,s.checkresult,s.checkmode,"
//						+ "s.remark from CREDENCECHECKLOG s where 1=1 ");
//		if (account != null && account.trim().length() > 0)
//			sql.append(" and s.zhangh = " + "'" + account + "'");
//		if (clerknum != null && clerknum.trim().length() > 0)
//			sql.append(" and s.clerknum = " + "'" + clerknum + "'");
//		if (checkmode != null && checkmode.trim().length() > 0)
//			sql.append(" and s.checkmode = " + "'" + checkmode + "'");
//		if (checknum != null && checknum.trim().length() > 0)
//			sql.append(" and s.checknum = " + "'" + checknum + "'");
//		if (checkresult != null && checkresult.trim().length() > 0
//				&& !checkresult.equals(""))
//			sql.append(" and s.checkresult = " + "'" + checkresult + "'");
//		sql.append(" and s.checkdate >= " + "'" + begindate + "'");
//		sql.append(" and s.checkdate <= " + "'" + enddate + "'");
//		sql.append(" order by s.checkdate desc,s.checktime desc");
//
//		CreateCustomaQuery1(TaskFlag, clerkCode, sql.toString(), 1,
//				sealchecklogForm.getOldaccount());
//	}

	public void DeleteCustomaQuery(String TaskFlag, String clerkcode)
			throws BusinessException {
		Session session = this.getHibernateSession();
		try {
			String sql = "delete dzcxinfo where id=:id and clerknum=:clerknum";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("id", TaskFlag);
			query.setString("clerknum", clerkcode);
			query.executeUpdate();
		} finally {
			this.closeSession(session);
		}
	}

	private String getSql(StringBuffer query, AccountinfoForm accountinfoform,
			String opendate1, String opendate2, String startdate1,
			String startdate2) {
		if (accountinfoform.getAccount() != null
				&& !accountinfoform.getAccount().equals(""))
			query.append(" and a.account='" + accountinfoform.getAccount()
					+ "'");
		if (accountinfoform.getEnglishname() != null
				&& !accountinfoform.getEnglishname().equals(""))
			query.append(" and a.englishname = '"
					+ accountinfoform.getEnglishname() + "'");
		if (accountinfoform.getIndustrycharacter() != null
				&& !accountinfoform.getIndustrycharacter().equals(""))
			query.append(" and a.industrycharacter = '"
					+ accountinfoform.getIndustrycharacter() + "'");
		if (opendate1 != null && !opendate1.equals(""))
			query.append(" and a.opendate >= '" + opendate1 + "'");
		if (opendate2 != null && !opendate2.equals(""))
			query.append(" and a.opendate <= '" + opendate2 + "'");
		if (startdate1 != null && !startdate1.equals(""))
			query.append(" and a.startdate >= '" + startdate1 + "'");
		if (startdate2 != null && !startdate2.equals(""))
			query.append(" and a.startdate <= '" + startdate2 + "'");
		if (accountinfoform.getCreditgrade() != null
				&& !accountinfoform.getCreditgrade().equals("")
				&& !accountinfoform.getCreditgrade().equals(new Long(3)))
			query.append(" and a.creditgrade = '"
					+ accountinfoform.getCreditgrade() + "'");
		if (accountinfoform.getAccountstate() != null
				&& !accountinfoform.getAccountstate().equals("")
				&& !accountinfoform.getAccountstate().equals("全部"))
			query.append(" and a.accountstate = '"
					+ accountinfoform.getAccountstate() + "'");
		if (accountinfoform.getYinqbz() != null
				&& !accountinfoform.getYinqbz().equals("")
				&& !accountinfoform.getYinqbz().equals("全部")) {
			query.append(" and a.accountcharacter = '"
					+ accountinfoform.getYinqbz() + "'");
		}
		if (accountinfoform.getSealstate() != null
				&& !accountinfoform.getSealstate().equals("")
				&& accountinfoform.getSealstate().equals("全部")) {
			query
					.append(" order by a.netpointflag,a.industrycharacter,a.englishname,a.accountstate");
		} else if (accountinfoform.getSealstate() != null
				&& !accountinfoform.getSealstate().equals("")
				&& !accountinfoform.getSealstate().equals("全部"))
			query
					.append(" and a.mainoperation = '"
							+ accountinfoform.getSealstate()
							+ "' order by a.netpointflag,a.industrycharacter,a.englishname,a.accountstate");
		return query.toString();
	}

	public CanOperAccReturn updateOrgParent(String tellerorg,
			String operaccount, String opertellorg) throws Exception {
			CanOperAccReturn can = new CanOperAccReturn();
			Map resultMap = changeOrgParent(tellerorg,operaccount,opertellorg);
			can.setReturnMessage((String) resultMap.get("retmsg"));
			int retflag =  (Integer) resultMap.get("retflag");
			if (retflag == 1)
			{
				can.setReturnValue(true);
			} else {
				can.setReturnValue(false);
			}
			return can;
	}
	
	
	/**
	 * @author lhz
	 * @param telorg 登录柜员的机构
	 * @param souorg 被调整的机构
	 * @param tarorg 将要归属的机构
	 * @return
	 * 代替原有的ChangeOrgParent存储过程
	 */
	public Map changeOrgParent(String telorg,String souorg,String tarorg){
		
		Session session = this.getHibernateSession();
		Integer retflag = 1;  //返回标志  1：成功，其他失败
		String retmsg = ""; //返回消息或错误信息
		Map resultMap = new HashMap();
		
		telorg = (telorg==null?"":telorg);
		souorg = (souorg==null?"":souorg);
		tarorg = (tarorg==null?"":tarorg);
		try{
			//1、 目标机构是否存在存在
			Query query1 = session.createQuery("select count(*) from Org where code=:ORGANNUM");
			query1.setString("ORGANNUM", tarorg);
			Integer orgtype = ((Long) query1.uniqueResult()).intValue();
			if(orgtype <= 0){
				retflag = -1;
				retmsg = "上级机构"+tarorg+"不存在";
			}
			
			//2、 上级机构和被调整机构不能相同
			if(tarorg.equals(souorg)){
				retflag = -1;
				retmsg = "上级机构和被调整机构不能相同";
			}
			
			//3、目标机构代码不能比登录柜员的机构的级别高,目标机构和被调整机构必须是同一个省级分行
			
			Org telOrg = (Org) orgDao.getOrgByCode(telorg);
			String telorgtype = telOrg.getWdflag();
			
			Org tarOrg = (Org) orgDao.getOrgByCode(tarorg);
			String tarorgtype =  tarOrg.getWdflag();//将要归属的机构的网点
			String sh_tarorg = tarOrg.getShOrgCode();
			Org souOrg = (Org) orgDao.getOrgByCode(souorg);
			String souorgtype = souOrg.getWdflag();
			String sh_souorg = souOrg.getShOrgCode();
			
			if(Integer.valueOf(telorgtype) > Integer.valueOf(tarorgtype)){
				retflag = -1;
				retmsg = "上级机构代码不能比登录柜员的机构的级别低";//1高，4低
			}
			//4.支行不能够归属到总行下
			if(souOrg.getWdflag().equals("3")&&tarOrg.getWdflag().equals("0")){
				retflag = -1;
				retmsg = "支行不能直接归属于总行";
			}

			//5.二级分行不能够归属到二级分行下
			if(souOrg.getWdflag().equals("2")&&tarOrg.getWdflag().equals("2")){
				retflag = -1;
				retmsg = "二级分行不能够归属于二级分行";
			}
			
			//4、目标机构不能是总行，目标机构和被调整机构必须是同一个省级分行
			
		/*	sh_tarorg = (sh_tarorg==null?"":sh_tarorg);
			if(!sh_tarorg.equals(sh_souorg)){
				retflag = -1;
				retmsg = "上级机构和被调整机构必须属于同一个省行";
			}*/
			
			//5、目标机构代码不能是他本身或他的下级别
			String db_type = DBinfoConig.getDBType();
			SQLQuery query2;
			Integer maxsouorgtype;
			if("oracle".equals(db_type)){
				query2 = session.createSQLQuery("SELECT count(*) FROM organarchives WHERE :tarorg IN (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber = :souorg)");
				query2.setString("tarorg", tarorg);
				query2.setString("souorg", souorg);
				BigDecimal bigd=(BigDecimal)query2.uniqueResult();
				maxsouorgtype=bigd.intValue();
			}else{
				query2 = session.createSQLQuery("SELECT  count(*)  FROM organarchives WHERE :tarorg IN (select INTERNALORGANIZATIONNUMBER from TABLE(ORGFUNCTION(:souorg)))");
				query2.setString("tarorg", tarorg);
				query2.setString("souorg", souorg);
				maxsouorgtype = (Integer)(query2.uniqueResult());
			}
			//System.out.println("maxsouorgtype:"+maxsouorgtype);
			if(maxsouorgtype > 0){
				retflag = -1;
				retmsg = "目标机构代码不能是他本身或他的下级别";
			}
			
			//6、目标机构代码必须是登录柜员机构管理的机构中存在
			SQLQuery query3; 
			if("oracle".equals(db_type)){
				query3 = session.createSQLQuery("SELECT count(*) FROM organarchives WHERE :tarorg IN (select INTERNALORGANIZATIONNUMBER from organarchives connect by prior INTERNALORGANIZATIONNUMBER=p_internalorganizationnumber  start with INTERNALORGANIZATIONNUMBER = :telorg)");
				query3.setString("tarorg", tarorg);
				query3.setString("telorg", telorg);
				BigDecimal bigd=(BigDecimal)query3.uniqueResult();
				maxsouorgtype=bigd.intValue();
			}else{
				query3 = session.createSQLQuery("SELECT count(*) FROM organarchives WHERE :tarorg IN (select INTERNALORGANIZATIONNUMBER from TABLE(ORGFUNCTION(:telorg)))");
				query3.setString("tarorg", tarorg);
				query3.setString("telorg", telorg);
				maxsouorgtype = (Integer)(query3.uniqueResult());
			}
			if(maxsouorgtype <= 0){
				retflag = -1;
				retmsg = "目标机构必须是登录柜员的下属或本身";
			}
			//7、调整后的机构级别总数不能超过4个级别
			SQLQuery query4; 
			if("oracle".equals(db_type)){
				query4 = session.createSQLQuery("SELECT max(wdflag) FROM organarchives WHERE INTERNALORGANIZATIONNUMBER IN (select internalorganizationnumber from organarchives connect by prior INTERNALORGANIZATIONNUMBER=P_INTERNALORGANIZATIONNUMBER  start with INTERNALORGANIZATIONNUMBER = :souorg)");
				query4.setString("souorg", souorg);
				//System.out.println("query4.uniqueResult():"+query4.uniqueResult());
				Character c=(Character)(query4.uniqueResult());
				String str=c.toString();
				maxsouorgtype =Integer.parseInt(str);
			}else{
				query4 = session.createSQLQuery("SELECT max(wdflag) FROM organarchives WHERE INTERNALORGANIZATIONNUMBER IN (select INTERNALORGANIZATIONNUMBER from TABLE(ORGFUNCTION(:souorg)))");
				query4.setString("souorg", souorg);
				maxsouorgtype = Integer.valueOf((String)(query4.uniqueResult()));
			}
			//获取控制台设定的机构级别数
			int orgRank = Integer.parseInt((SystemDao.findSystemControlParameterById("org_ranking")).getValue());
			if((maxsouorgtype - Integer.valueOf(souorgtype) + Integer.valueOf(tarorgtype)) + 1 >(orgRank-1) )
			{
				retflag = -1;
				//System.out.println("maxsouorgtype:"+maxsouorgtype);
				//System.out.println("souorgtype:"+Integer.valueOf(souorgtype));
				//System.out.println("tarorgtype:"+Integer.valueOf(tarorgtype));
				retmsg = "当前归属关系调整将使机构级别总数超过最大层级，不允许执行";
			}
			
			
			//得到调整机构
			if(retflag == 1){
				if(souOrg.getWdflag().equals("2")&&tarOrg.getWdflag().equals("0")){
					retmsg = "机构["+souOrg.getCode()+"]自动升级为一级分行";
				}

				if((souOrg.getWdflag().equals("1")||souOrg.getWdflag().equals("2"))&&tarOrg.getWdflag().equals("1")){
					retmsg = "机构["+souOrg.getCode()+"]自动降级为二级分行";
				}
				
				/*SQLQuery query5 = session.createSQLQuery("select wdflag from organarchives where ORGANNUM in (select n_parentnum from organarchives where ORGANNUM=:souorg)");
				query5.setString("souorg", souorg);
				//被调整机构的原上级机构的网点
				String wd;
				if("oracle".equals(db_type)){
					wd = ((Character)query5.uniqueResult()).toString();
				}else{
					wd = (String)query5.uniqueResult();
				}
				Integer uporgtype = Integer.valueOf(wd);
				if(!(Integer.valueOf(tarorgtype)).equals(uporgtype)){
					Integer count = 0;
					String sql = "";
					if(Integer.valueOf(tarorgtype)>uporgtype){
						if("oracle".equals(db_type)){
							sql = "update organarchives set wdflag=wdflag + 1 where organnum in (select organnum from organarchives connect by prior organnum=n_parentnum  start with organnum = :souorg)";
						}else{
							sql = "update organarchives set wdflag=wdflag + 1 where organnum in (select ORGANNUM from TABLE(ORGFUNCTION(:souorg)))";
						}
						
						
					}
					if(Integer.valueOf(tarorgtype)<uporgtype){
						if("oracle".equals(db_type)){
							sql = "update organarchives set wdflag=wdflag - 1 where organnum in (select organnum from organarchives connect by prior organnum=n_parentnum  start with organnum = :souorg)";
						}else{
							sql = "update organarchives set wdflag=wdflag - 1 where organnum in (select ORGANNUM from TABLE(ORGFUNCTION(:souorg)))";
						}
					}
					SQLQuery query6 = session.createSQLQuery(sql);
					query6.setString("souorg", souorg);
					query6.executeUpdate();
				}
		
			
				SQLQuery query6 = session.createSQLQuery(sql);
				query6.setString("souorg", souorg);
				query6.executeUpdate();
				SQLQuery query7 = session.createSQLQuery("update organarchives set n_parentnum=:tarorg where organnum=:souorg");
				query7.setString("tarorg", tarorg);
				query7.setString("souorg", souorg);
				query7.executeUpdate();*/
				//20140320修改 --qiejinkai
				orgDao.updateOrgRelation(souorg, tarorg);
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}catch (Exception e) {
			resultMap.put("retflag", false);
			resultMap.put("retmsg", "机构不存在");
			return resultMap;
		}finally{
			session.flush();
			session.close();
		}
		resultMap.put("retflag", retflag);
		resultMap.put("retmsg", retmsg);
		return resultMap;
	}

	public void CreateCustomaQuery_AccountInfo(String TaskFlag,
			AccountinfoForm accountinfoform, String clerkCode,
			String opendate1, String opendate2, String startdate1,
			String startdate2) throws Exception {
		StringBuffer buffer = new StringBuffer(
				"select a.account,a.accountname,o.legalname,a.netpointflag,a.englishname,a.linkman,a.postalcode,a.opendate,a.incomerange,a.startdate,a.accountstate,a.allexchange,a.industrycharacter,a.remark,a.mainoperation,a.accountcharacter from accountinfo a ,organarchives o where a.netpointflag = o.internalorganizationnumber and");
		buffer.append(" a.opendate>= '" + opendate1 + "' and  a.opendate<='"
				+ opendate2 + "'");
		buffer.append(" and a.startdate>= '" + startdate1
				+ "' and  a.startdate<='" + opendate2 + "'");
		AccountinfoForm accountinfo = accountinfoform;
		if (accountinfo.getAccount() != null
				&& !accountinfo.getAccount().equals("")) {
			buffer.append(" and a.account ='" + accountinfo.getAccount() + "'");
		}
		if (accountinfo.getNetpointflag() != null
				&& !accountinfo.getNetpointflag().equals("")) {
			buffer.append(" and a.netpointflag ='"
					+ accountinfo.getNetpointflag() + "'");
		}
		if (accountinfo.getEnglishname() != null
				&& !accountinfo.getEnglishname().equals("")) {
			buffer.append(" and a.englishname ='"
					+ accountinfo.getEnglishname() + "'");
		}
		if (!"全部".equals(accountinfo.getIndustrycharacter())) {
			buffer.append(" and a.industrycharacter ='"
					+ accountinfo.getIndustrycharacter() + "'");
		}
		if (!"全部".equals(accountinfo.getAccountstate())) {
			buffer.append(" and a.accountstate ='"
					+ accountinfo.getAccountstate() + "'");
		}
		if (!"全部".equals(accountinfo.getSealstate())) {
			buffer.append(" and a.mainoperation ='"
					+ accountinfo.getSealstate() + "'");
		}
		if (!"全部".equals(accountinfo.getYinqbz())) {
			buffer.append(" and a.accountcharacter ='"
					+ accountinfo.getYinqbz() + "'");
		}
		CreateCustomaQuery(TaskFlag, clerkCode, buffer.toString(), 2,
				accountinfo.getRemark());
	}

	public void CreateCustomaQuery_AccountInfoForNet(String TaskFlag,
			AccountinfoForm accountinfoform, String clerkCode,
			String opendate1, String opendate2, String startdate1,
			String startdate2) throws Exception {
		StringBuffer buffer = new StringBuffer(
				"select * from zhanghb a,organarchives o where a.internalorganizationnumber = o.internalorganizationnumber and");
		buffer.append(" a.kaihrq >= '" + opendate1 + "' and  a.kaihrq <= '"
				+ opendate2 + " ' ");
		buffer.append(" and a.qiyrq >= '" + startdate1 + "' and  a.qiyrq <='"
				+ opendate2 + "'");
		AccountinfoForm accountinfo = accountinfoform;
		if (accountinfo.getAccount() != null
				&& !accountinfo.getAccount().equals("")) {
			buffer.append(" and a.zhangh ='" + accountinfo.getAccount() + "'");
		}
		if (accountinfo.getNetpointflag() != null
				&& !accountinfo.getNetpointflag().equals("")) {
			buffer.append(" and a.internalorganizationnumber ='" + accountinfo.getNetpointflag()
					+ "'");
		}
		if (accountinfo.getEnglishname() != null
				&& !accountinfo.getEnglishname().equals("")) {
			buffer
					.append(" and a.customernumber ='" + accountinfo.getEnglishname()
							+ "'");
		}
		if (!"全部".equals(accountinfo.getIndustrycharacter())) {
			buffer.append(" and a.currency ='"
					+ accountinfo.getIndustrycharacter() + "'");
		}
		if (!"全部".equals(accountinfo.getAccountstate())) {
			buffer.append(" and a.zhanghzt ='" + accountinfo.getAccountstate()
					+ "'");
		}
		if (!"全部".equals(accountinfo.getSealstate())) {
			buffer
					.append(" and a.yinjzt ='" + accountinfo.getSealstate()
							+ "'");
		}
		if (!"全部".equals(accountinfo.getYinqbz())) {
			buffer.append(" and a.youwyj ='" + accountinfo.getYinqbz() + "'");
		}
		CreateCustomaQuery(TaskFlag, clerkCode, buffer.toString(), 2, accountinfo.getRengwms());
	}

	public void addSystemControlParameter( SystemControlParameter controlParameter) {
		SystemDao.addSystemControlParameter(controlParameter);
	}

	public void deleteSystemControlParameter(String id) {
		SystemDao.deleteSystemControlParameter(id);
	}

	public List findAllSystemControlParameters() {
		return SystemDao.findAllSystemControlParameters();
	}
//获取系统控制参数值通过参数id
	public SystemControlParameter findSystemControlParameterById(String id) {
		return SystemDao.findSystemControlParameterById(id);
	}

	public void updateSystemControlParameter(
			SystemControlParameter controlParameter) {
		SystemDao.getSystetemNowDate();
	}

	public List getHuobh() {
		return SystemDao.getHuobhList();
	}
	
	public TabsBo findAccountTongbrzLog(String account, String jigh,
			String begindate, String enddate) {
		Map paraMap=new HashMap();
		String sql1 ="select t.zhangh,t.caozlx,t.exception,t.chuangjsj,t.tongbsj,t.str1,t.str2,t.str3,t.result,t.f_internalorganizationnumber,z.legalname from zhanghtbb t left join zhanghb z on t.zhangh=z.zhangh where t.chuangjsj >=:begindate and t.chuangjsj <=:enddate ";
		paraMap.put("begindate", begindate);
		paraMap.put("enddate", enddate);
		String sql2	="select t.zhangh,t.caozlx,t.exception,t.chuangjsj,t.tongbsj,t.str1,t.str2,t.str3,t.result,t.f_internalorganizationnumber,z.legalname from zhanghtbb t left join zhanghb z on t.zhangh=z.zhangh where z.internalorganizationnumber=:jigh and  t.chuangjsj >=:begindate and t.chuangjsj <=:enddate ";
		
		String SQL=sql1;
		if(jigh!=null&&!jigh.trim().equals("")){
			if(!orgDao.getOrgByCode(jigh).getWdflag().equals("0")){
				SQL=sql2;
				paraMap.put("jigh", jigh);
			}
		}
		if(account!=null&&!account.trim().equals("")){
			SQL=SQL+" and t.zhangh=:account ";
			paraMap.put("account", account);
		}
		SQL=SQL+" order by t.chuangjsj desc  ";
		//System.out.println(SQL);
		TabsBo tabsBoModle = TabsDao.pagingObjectForSql(SQL, this.tabsBo.getDangqym(), this.tabsBo.getFenysl(), paraMap);
		List<Zhanghtbb> result = new ArrayList<Zhanghtbb>();
		for (Iterator it = tabsBoModle.getList().iterator(); it.hasNext();) 
		{
			Object[] element = (Object[]) it.next();
			Zhanghtbb zhanghtbb = new Zhanghtbb();
			zhanghtbb.setZhangh(String.valueOf(element[0]==null?"":element[0]));
			zhanghtbb.setException(String.valueOf(element[2]==null?"":element[2]));
			zhanghtbb.setChuangjsj(String.valueOf(element[3]==null?"":element[3]));
			String caozlx="";
			try{
			switch (Integer.valueOf(String.valueOf(element[1]))) {
			case 0:
				caozlx="新增";
				break;
			case 1:
				caozlx="删除";
				break;
			case 2:
				caozlx="修改";
				break;
			
			}
			}catch(Exception e){ 
				caozlx=String.valueOf(element[1]);
			};
			zhanghtbb.setCaozlx(caozlx);
			zhanghtbb.setTongbsj(String.valueOf(element[4]==null?"":element[4]));
			zhanghtbb.setShenghjgh(String.valueOf(element[9]==null?"":element[9]));
			zhanghtbb.setHum(String.valueOf(element[10]==null?"":element[10]));
			String jieg="";
			try{
				switch (Integer.valueOf(String.valueOf(element[8]))) {
				case 0:
					jieg="未执行";
					break;
				case 1:
					jieg="同步成功";
					break;
				case 2:
					jieg="同步失败";
					break;
				
				}
				}catch(Exception e){ 
					jieg=String.valueOf(element[8]);
				};
				zhanghtbb.setResult(jieg);
				zhanghtbb.setStr1(String.valueOf(element[5]));
				zhanghtbb.setStr2(String.valueOf(element[6]));
				zhanghtbb.setStr3(String.valueOf(element[7]));
				result.add(zhanghtbb);
		}
		tabsBoModle.setList(result);
		return tabsBoModle;
	}
	
	public List queryData(String jsql, Map<String, String> parameterMap,
			int first, int end,String className) {
		Session session =this.getHibernateSession();
		String SQL="select * from(select dd.*,rownum as rn  from ("+jsql+" ) dd )where rn >=:first and rn<:end";
		List result=null;
		try{
		SQLQuery query=session.createSQLQuery(SQL);
		doQuery(query, parameterMap);
		query.setString("first", first+"");
		query.setString("end", end+"");
		//System.out.println("End "+end);
		List list=query.list();
		result=praseResultList(list,className);
		}catch (Exception e) {
			return null;
		}finally{
			this.closeSession(session);
		}
		return result;
	}
	private List praseResultList(List list,String className){
		List result=new ArrayList();
		if(className.equals("SealcheckLog1")){
			SealcheckLog slog = null;
			Object[] element=null;
			for (Iterator iter = list.iterator(); iter.hasNext();)
			{	
				slog=new SealcheckLog();
				element = (Object[]) iter.next();
				slog.setAccount((String) element[0]);
				slog.setChecknum((String) element[1]);
				slog.setQiyrq((String) element[2]);
				slog.setCheckresult((String) element[3]);
				slog.setCheckmode((String) element[4]);
				slog.setClerknum((String) element[5]);
				slog.setCheckdate((String) element[6] + " " + (String) element[7]);
				slog.setChecktime((String) element[7]);
				slog.setAccountname((String) element[8]);
				slog.setSealinktype((String) element[9]);
				slog.setSealinknum((String) element[10]);
				slog.setZhuzh((String) element[11]);
				//江西
				slog.setPingzbsm((String) element[12]);				
				//设置获取印鉴帐号
				if("".equals(slog.getZhuzh())||null==(slog.getZhuzh()))
				{
					slog.setZhangh_(slog.getAccount());
				}else{
					slog.setZhangh_(slog.getZhuzh());
				}
				result.add(slog);
			}
		}else if(className.equals("Shouqrzb")){	
			Shouqrzb slog = null;
			Object[] element=null;
			for (Iterator iter = list.iterator(); iter.hasNext();){	
				slog = new Shouqrzb();
			 element = (Object[]) iter.next();
			slog.setId((String) element[0]);
			slog.setAccount((String) element[1]);
			
			slog.setManagedate((String) element[2]);
			slog.setManagetime((String) element[3]);
			slog.setManagecontent((String) element[4]);
			slog.setManagetype((String) element[5]);
			slog.setOperationclerk((String) element[6]);
			slog.setAuthorizationclerk((String) element[7]);
			result.add(slog);
		}
			
		}else if(className.equals("SystemManageLog")){
			SystemManageLog slog =null;
			Object[] element=null;
			for (Iterator iter = list.iterator(); iter.hasNext();){	
				 slog = new SystemManageLog();
				element = (Object[]) iter.next();
				slog.setId((String) element[0]);
				slog.setAdmincode((String) element[1]);
				
				slog.setContent((String) element[2]);
				slog.setOperdate((String) element[3]);
				slog.setIp((String) element[4]);
				result.add(slog);
			}
			
		}else if(className.equals("AccountLog")){
			AccountLog alog =null;
			Object[] element=null;
			String caozlx="";
		for (Iterator it = list.iterator(); it.hasNext();){
			alog = new AccountLog();
			 element = (Object[]) it.next();
			alog.setAccount(String.valueOf(element[0]));
			alog.setClerknum(String.valueOf(element[1]));
			alog.setOperdate(String.valueOf(element[2]) + " " + String.valueOf(element[3]));
			try{
			switch (Integer.valueOf(String.valueOf(element[4]))) {
			case 0:
				caozlx="开户";
				break;
			case 6:
				caozlx="印鉴建库";
				break;
			case 7:
				caozlx="印鉴审核";
				break;
			case 1:
				caozlx="基本信息修改";
				break;
			case 8:
				caozlx="印鉴变更";
				break;
			case 2:
				caozlx="联系人变更";
				break;
			case 3:
				caozlx="解除共用关系";
				break;
			case 4:
				caozlx="销户";
				break;
			case 5:
				caozlx="销户恢复";
				break;
			case 9:
				caozlx="状态维护";
				break;
			}
			}catch(Exception e){ 
				caozlx=String.valueOf(element[4]);
			};
			alog.setCaozlx(caozlx);
			alog.setAccountname(String.valueOf(element[5]));
			alog.setNETPOINTFLAG(String.valueOf(element[6]));
		//	alog.setINDUSTRYCHARACTER("["+String.valueOf(element[7])+"]"+SystemDao.getHuobh(String.valueOf(element[7])).getHuobmc());
			alog.setContent(String.valueOf(element[8]));
			if(alog.getCaozlx().equals("联系人变更")){
				alog.setContent("变更前："+String.valueOf(element[9])+";变更后:"+String.valueOf(element[10])+";"+String.valueOf(element[8]==null?"":element[8]));};
			result.add(alog);
		}
			
		}else if(className.equals("Zhanghb")){
			Zhanghb zhanghb=null;
				Object[] element=null;
			for (Iterator iter = list.iterator(); iter.hasNext();)
			{
				try {
					 zhanghb= new Zhanghb();
					 element = (Object[]) iter.next();
					zhanghb.setZhangh( element[0]==null?"":(String) element[0]);
					zhanghb.setHum(element[2]==null?"":(String) element[2]);
					zhanghb.setJigh(element[3]==null?"":(String) element[3]);
					zhanghb.setLianxr(element[6]==null?"":(String) element[6]);
					zhanghb.setDianh(element[7]==null?"":(String) element[7]);
					zhanghb.setKaihrq(element[8]==null?"":(String) element[8]);
					zhanghb.setTongctd(element[9]==null?"":(String) element[9]);
					zhanghb.setYouwyj(element[11]==null?"":(String) element[11]);
					zhanghb.setYouwzh(element[12]==null?"":(String) element[12]);
					zhanghb.setYinjshzt(element[13]==null?"":(String) element[13]);
					zhanghb.setZhanghshzt(element[14]==null?"":(String) element[14]);
					zhanghb.setZuhshzt(element[15]==null?"":(String) element[15]);
					zhanghb.setZhanghzt(element[16]==null?"":(String) element[16]);
					zhanghb.setZhanghxz(element[17]==null?"":(String) element[17]);
					zhanghb.setZhuzh(element[21]==null?"主账号":"共用"+(String) element[21]);
			
					zhanghb.setFuyrq(element[22]==null?"":(String) element[22]);
					zhanghb.setFuzr(element[26]==null?"":(String) element[26]);
					zhanghb.setFuzrdh(element[27]==null?"":(String) element[27]);
					zhanghb.setYinjkbh(element[28]==null?"":(String) element[28]);
					if(zhanghb.getZhuzh()!=null||zhanghb.getZhuzh().trim().length()!=0){
						zhanghb.setYinjkbh(zhangbDao.getYinjkh(zhanghb.getZhangh()));
					}
					zhanghb.setTongdsz(element[29]==null?"":(String) element[29]);
					zhanghb.setLianxr2(element[30]==null?"":(String) element[30]);
					zhanghb.setDianh2(element[31]==null?"":(String) element[31]);
					zhanghb.setFuzr2(element[32]==null?"":(String) element[32]);
					zhanghb.setFuzrdh2(element[33]==null?"":(String) element[33]);
					String shifdh=element[34]==null?"":(String) element[34];
					zhanghb.setShifdh(shifdh!=null&&shifdh.equals("0")?"是":"否");
					result.add(zhanghb);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
		}else if(className.equals("SealcheckLog2")){
			SealcheckLog slog = null;
			Object[] element =null;
			double jine=1d;
			double i =1d;
			for (Iterator iter = list.iterator(); iter.hasNext();)
			{
				try {
					 slog = new SealcheckLog();
					element= (Object[]) iter.next();
					slog.setAccount((String) element[0]);
					slog.setChecknum((String) element[1]);
					slog.setCheckdate((String) element[2] + " "+ String.valueOf(element[3]==null||element[3].equals("null")?"":element[3]));
					slog.setChecktime((String) element[3]);
					slog.setClerknum((String) element[4]);
					slog.setCheckresult((String) element[5]);
					slog.setCheckmode((String) element[6]);
					slog.setRemark((String) element[7]);
					jine= Double.valueOf((element[8]).toString());
					i= 100.00d;
					slog.setMoney(Format.formatJine((double)jine/i));
					slog.setZuhgz((String) (element[9]));
					//slog.setPingzbsm((String) (element[10]));
					slog.setChuprq((String) (element[10]));
					slog.setDoublesignatureclerknum(element[11]==null?"":((String) element[11]).equals("null")?"":(String) element[11]);
					//System.out.println(element[12]==null);
					//System.out.println(element[12]);
					slog.setPingzlx((String)element[12]);
					/*if(String.valueOf(element[15]).indexOf("q")!=-1){
						slog.setBatchinfo(createQtBatchinfo_((String)element[13]));
					}else{
						slog.setBatchinfo(createHtBatchinfo_((String)element[13]));
					}*/
					slog.setCanal(element[17]==null?"":((String) element[17]).equals("null")?"":((String) element[17]).equals("097")?"批量验印":((String) element[17]).equals("YY")?"单笔验印":((String) element[17]).equals("BPC")||((String) element[17]).equals("AB")?"流程验印":(String) element[17]);
					slog.setBatchinfo(element[18]==null?"":((String) element[18]));
					result.add(slog);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}
/*	private String createQtBatchinfo_(String id) {
		String sql = "select s.sealinknum,s.sealinktype,s.checkmode,s.checkresult from SEALCHECKLOG s where s.id=:id ";
		Session session = this.getHibernateSession();
		List list = null;
		StringBuffer msg=new StringBuffer();
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("id", id);
			list = query.list();
			for (Iterator iter = list.iterator(); iter.hasNext();)
			{
				try {
					Object[] element = (Object[]) iter.next();
					msg.append((String) element[0]==null?"":(String) element[0]).append("号");
					msg.append((String) element[1]==null?"":(String) element[1]);
					msg.append((String) element[2]==null?"":(String) element[2]).append((String) element[3]==null?"":(String) element[3]).append("\r");
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			return msg.toString();
		} finally {
			this.closeSession(session);
		}
	}

	private String createHtBatchinfo_(String id) {
		String sql = "select s.yinjbh,s.yinjbz,s.yanyms,s.yanyjg from CI_YYRZ s where s.renwbs=:id ";
		Session session = this.getHibernateSession();
		List list = null;
		StringBuffer msg=new StringBuffer();
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("id", id);
			list = query.list();
			for (Iterator iter = list.iterator(); iter.hasNext();)
			{
				try {
					Object[] element = (Object[]) iter.next();
					msg.append((String) element[0]==null?"":(String) element[0]).append("号");
					msg.append((String) element[1]==null?"":(String) element[1]);
					msg.append((String) element[2]==null?"":(String) element[2]).append((String) element[3]==null?"":(String) element[3]).append("\r");
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			return msg.toString();
		} finally {
			this.closeSession(session);
		}
	}
	*/
	//SQL/HQL 参数赋值
	private void doQuery(Query query,Map parameterMap){
		//遍历要赋值的Map，将原select语句中的占位符换成具体数据
		
		String key="";
		if(parameterMap!=null){
			Iterator it=parameterMap.keySet().iterator();
			while(it.hasNext()){
				key = it.next().toString();
				if(parameterMap.get(key).getClass() == String.class)
					query.setString(key, (String) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Integer.class)
					query.setInteger(key, (Integer) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == BigDecimal.class)
					query.setBigDecimal(key, (BigDecimal) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setBoolean(key, (Boolean) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setDate(key, (Date) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setFloat(key, (Float) parameterMap.get(key));
				if(parameterMap.get(key).getClass() == Boolean.class)
					query.setDouble(key, (Double) parameterMap.get(key));
			}
		}
	}
	public List queryDataToMap(String jsql, Map<String, String> parameterMap,
			int first, int end, String className) {
		String SQL = null;
		List<Map> list = new ArrayList<Map>();
		PreparedStatement pre = null;
		PreparedStatement countspre = null;
		ResultSet rs = null;
		ResultSet countsrs = null;
		ResultSetMetaData rsmd = null;
		Connection conn = null;
		try{
			conn = super.getBaseJDBCDao().getConnection();
			SQL="select * from(select dd.* ,rownum as rn  from ( "+jsql+" )dd ) where rn >="+first+" and rn<"+end;
			
			//System.out.println(SQL);
			pre = conn.prepareStatement(SQL);
			for(int i = 1 ;i<=parameterMap.size();i++)
			{	
				pre.setString(i, parameterMap.get(i+""));
			}
			rs = pre.executeQuery();
			rsmd = pre.getMetaData();
			String colname="";
			Map<String, Object> map =null;
			Object getColumnValueObject =new Object();
			String yinjkbh="";
			while (rs.next()) {
				map = new LinkedHashMap<String, Object>();
				for (int i = 0; i < rsmd.getColumnCount(); i++){
					getColumnValueObject= rs.getObject(i + 1);
					getColumnValueObject=(null)==getColumnValueObject?"":getColumnValueObject;
					colname=rsmd.getColumnName(i + 1).toLowerCase();
					if(colname.equals("yinjkbh")){
						yinjkbh=zhangbDao.getYinjkh((String)map.get("zhangh"));
						map.put("yinjkbh", yinjkbh==null?"":yinjkbh);
					}else{
						map.put(colname,getColumnValueObject);
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pre != null) {
					pre.close();
				}if (countsrs != null) {
					countsrs.close();
				}
				if (countspre != null) {
					countspre.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (conn != null) {
				super.getBaseJDBCDao().closeConnection(conn);
			}
		}
		return list;
	}
	@Override
	public Autopasscount countAutopassrate(String sql, int counts,String paramstr) {
		Autopasscount count=new Autopasscount();
		count.setTotal(counts);
		String autopasssql ="select count(*) from ("+sql.substring(0,sql.indexOf("order"))+" where checkmode='自动' and checkresult='通过' )";
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Map<String,String> parameterMap=praseJsonToMap(paramstr);
		try {
			SQLQuery query = session.createSQLQuery(autopasssql);
			doQuery(query, parameterMap);
			int num = Integer.parseInt(String.valueOf(query.uniqueResult()));
			count.setAutopassnum(num);
			if(counts==0){
				query = session.createSQLQuery("select count(*) from ("+sql.substring(0,sql.indexOf("order"))+")");
				doQuery(query, parameterMap);
				counts=Integer.parseInt(String.valueOf(query.uniqueResult()));
				count.setTotal(counts);
			}
			DecimalFormat df=new DecimalFormat("#0.00");
			if(counts!=0){
			count.setPassrate((String.valueOf(df.format(100*(double)num/counts)))+"%");
			}else{
				count.setPassrate("0.00%");
			}
		} finally {
			this.getBaseHibernateDao().closeSession(session);
		}
		
		return count;
	}
	private Map<String,String> praseJsonToMap(String jlist) {
		Map<String,String> map =new HashMap<String,String>();
		JSONObject json =JSONObject.fromObject(jlist);
		Iterator<String> keys=json.keys();
		while(keys.hasNext()){
			String key =keys.next();
			String value=json.get(key).toString();
			map.put(key, value);
		}
		return map;
	}
	@Override
	public void CreateCustomaQuery(String TaskFlag, String clerkcode,
			String sqlstr, int QueryType, String remark)
			throws BusinessException, Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void CreateCustomaQuery_SealCheckLog(String TaskFlag,
			String clerkCode, SealchecklogForm sealchecklogForm)
			throws BusinessException, Exception {
		// TODO Auto-generated method stub
		
	}
	
}
