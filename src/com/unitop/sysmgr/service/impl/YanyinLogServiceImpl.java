package com.unitop.sysmgr.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhengprz;
import com.unitop.sysmgr.dao.SystemDao;
import com.unitop.sysmgr.dao.TabsDao;
import com.unitop.sysmgr.dao.YanyinLogDao;
import com.unitop.sysmgr.service.YanyinLogService;

@Service("YanyinLogServiceImpl")
public class YanyinLogServiceImpl extends BaseServiceImpl implements YanyinLogService {
	@Resource
	private YanyinLogDao YanyinLogDao;
	@Resource
	private SystemDao SystemDao;
	@Resource
	private TabsDao TabsDao;
	
	
	//获得整票 
	public Zhengprz getZhengp(String id) {
		return YanyinLogDao.getZhengp(id);
	}
	
	
	//整票 票据号验证
	public boolean validatePjhForZhengp(String piaojh) {
		int totalRows = YanyinLogDao.validateZhengppjh(piaojh);
		//判断票据号是否存在
		if(totalRows>0)
		{
			return false;
		}else{
			return true;
		}
	}
	
	//单章 票据号验证
	public boolean validatePjhForDanz(String piaojh) {
		int totalRows =  YanyinLogDao.validateDanzppjh(piaojh);
		//判断票据号是否存在
		if(totalRows>0)
		{
			return false;
		}else{
			return true;
		}
	}
	
	//获取 当天单章验印日志
	public TabsBo getTodayDanzrzList(String account, String checknum) {
		String hql = "from Danzrz where id.account =:account and id.checknum=:checknum and id.checkdate=:checkdate order by id.checktime desc,id.checkdate desc";
		Map parameterMap = new HashMap();
		parameterMap.put("account",account);
		parameterMap.put("checknum",checknum);
		parameterMap.put("checkdate",SystemDao.getSystetemNowDate().substring(0,10));
		TabsBo tabs = TabsDao.pagingForHql(hql,this.tabsBo.getDangqym(),this.tabsBo.getFenysl(),parameterMap);
		return tabs;
	}
	
	//获取 机构某天整票验印日志
	public TabsBo getOrgZhengprzList(String orgCode,String dateString){
		TabsBo tabs = null;
		String hql = "from Zhengprz where clerkorgcode=:orgCode and id.checkdate =:dateString";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Map parameterMap = new HashMap();
			parameterMap.put("orgCode", orgCode);
			parameterMap.put("dateString", dateString);
			tabs = TabsDao.pagingForHql(hql, tabsBo.getDangqym(), tabsBo.getFenysl(), parameterMap);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return tabs;
	}
	
	
	// 更新整票日志添加复核柜员
	public boolean addClerk2OfZhengprz(String taskid,String clerkid){
		return YanyinLogDao.addClerk2OfZhengprz(taskid, clerkid);
	}
}
