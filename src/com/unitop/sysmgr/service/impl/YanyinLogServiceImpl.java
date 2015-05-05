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
	
	
	//�����Ʊ 
	public Zhengprz getZhengp(String id) {
		return YanyinLogDao.getZhengp(id);
	}
	
	
	//��Ʊ Ʊ�ݺ���֤
	public boolean validatePjhForZhengp(String piaojh) {
		int totalRows = YanyinLogDao.validateZhengppjh(piaojh);
		//�ж�Ʊ�ݺ��Ƿ����
		if(totalRows>0)
		{
			return false;
		}else{
			return true;
		}
	}
	
	//���� Ʊ�ݺ���֤
	public boolean validatePjhForDanz(String piaojh) {
		int totalRows =  YanyinLogDao.validateDanzppjh(piaojh);
		//�ж�Ʊ�ݺ��Ƿ����
		if(totalRows>0)
		{
			return false;
		}else{
			return true;
		}
	}
	
	//��ȡ ���쵥����ӡ��־
	public TabsBo getTodayDanzrzList(String account, String checknum) {
		String hql = "from Danzrz where id.account =:account and id.checknum=:checknum and id.checkdate=:checkdate order by id.checktime desc,id.checkdate desc";
		Map parameterMap = new HashMap();
		parameterMap.put("account",account);
		parameterMap.put("checknum",checknum);
		parameterMap.put("checkdate",SystemDao.getSystetemNowDate().substring(0,10));
		TabsBo tabs = TabsDao.pagingForHql(hql,this.tabsBo.getDangqym(),this.tabsBo.getFenysl(),parameterMap);
		return tabs;
	}
	
	//��ȡ ����ĳ����Ʊ��ӡ��־
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
	
	
	// ������Ʊ��־��Ӹ��˹�Ա
	public boolean addClerk2OfZhengprz(String taskid,String clerkid){
		return YanyinLogDao.addClerk2OfZhengprz(taskid, clerkid);
	}
}
