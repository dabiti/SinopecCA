package com.unitop.sysmgr.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.sinodata.table.model.SqlParameter;
import com.sinodata.table.model.Table;
import com.unitop.sysmgr.bo.AccountNum;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.YinjkManageLog;
import com.unitop.sysmgr.bo.YinjkNum;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.dao.DanbwhDao;
import com.unitop.sysmgr.dao.YinjkDao;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.form.YinjkForm;
import com.unitop.sysmgr.service.YinjkService;

@Service("YinjkServiceImpl")
public class YinjkServiceImpl extends  BaseServiceImpl implements YinjkService  {

	@Resource 
	private YinjkDao yinjkDao;
	@Resource 
	private ZhanghbDao zhanghbDao;
	
	
	@Resource
	private DanbwhDao DanbwhDao;
	
	
	public void cancleYinjk(String zhangh) {
		
	}

	public List<Yinjk> getYinjkByZhangh(String zhangh) {

		return yinjkDao.getYinjkByZhangh(zhangh);
	}
	
	
	//通过印鉴卡号获得印鉴卡
	public Yinjk getYinjkByYinjkbh(String yinjkbh) {

		return yinjkDao.getYinjkByYinjkbh(yinjkbh);
	}

	
	
	//印鉴卡数量统计
	public long yinjkNumber(String orgCode,String shifbhxj){
		String shifbhxjSQL=" internalorganizationnumber =? ";
		if("是".equals(shifbhxj))
		{
			String db_type = DanbwhDao.getDBtype();
			if("oracle".equals(db_type))
			{
				shifbhxjSQL=" internalorganizationnumber in (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber = ?) ";
			}else{
				shifbhxjSQL=" internalorganizationnumber in (select internalorganizationnumber from TABLE(ORGFUNCTION(?))) ";
			}
		}
		//-- 总数
		String zhanghzs = "select count(*) as yinjkzs  from yinjk where "+shifbhxjSQL;
		
		Long i = null ;
		try {
			Table table = new Table(zhanghzs);
			table.getSqlParameter().put(1,new SqlParameter("jigh","string",orgCode));
			List list = DanbwhDao.doSelect(table);
			Map map = (Map)list.get(0);
			i = Long.valueOf( String.valueOf( map.get("yinjkzs"))).longValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	public boolean CheckYinjkbhList(List<String> yinjkbhList) {
		
		return yinjkDao.CheckYinjkbhList(yinjkbhList);
	}
	
	public void saveYinjkList(List<String> yinjkbhList,Zhanghb zhanghb) {
		for (String string : yinjkbhList) {
			if(string!=null&&string.trim().length()!=0){
				Yinjk yinjk=new Yinjk();
				yinjk.setYinjkh(string);
				yinjk.setZhangh(zhanghb.getZhangh());
				
				yinjk.setJigh(zhanghb.getJigh());
				yinjk.setQiyrq(zhanghb.getKaihrq());
				yinjkDao.saveyinjk(yinjk);
			}
		}
		
	}
	
	public void cancleYinjk(List<String> deleteYinjkList) {
		for (String string : deleteYinjkList) {
			if(string!=null&&string.trim().length()!=0){
				yinjkDao.cancleYinjk(string);
			}
		}
		
	}
	

	public int countYinjkNum(Yinjk yinjk, String endYinjkh) {
		return yinjkDao.countYinjkNum(yinjk,endYinjkh);
	}
	public void save(Yinjk yinjk, int num) {
		yinjkDao.save(yinjk,num);
	}
	public void saveLog(YinjkManageLog log) {
		yinjkDao.saveLog(log);
		
	}
	public List<Yinjk> getMinCodeYinjk(String clerkCode,int i) {
		// TODO Auto-generated method stub
		return yinjkDao.getMinCodeYinjk(clerkCode,i);
	}
	public int countYinjkNumForDestroy(Yinjk yinjk, String endYinjkh) {
		
		return yinjkDao.countYinjkNumForDestroy(yinjk,endYinjkh);
	}
	
	public void saveYinjkList(List<Yinjk> yinjkList) {
		if(yinjkList==null||yinjkList.size()==0){
			
			return;
		}
		
		for (Yinjk yinjk : yinjkList) {
			yinjkDao.save(yinjk, 1);
		}
		
	}
	public YinjkNum selectYinjkNum(YinjkForm yinjkform) {
		YinjkNum yinjkNum=null;
		Session session=super.getBaseHibernateDao().getHibernateSession();
		try {
			String hql="select count(*) from Yinjk where internalorganizationnumber=:jigh ";
			Map<String,String> params=new HashMap<String, String>();
			String jigh = yinjkform.getJigh();
			String clerkCode = yinjkform.getGuiyh();
			String begindate=yinjkform.getBegindate();
			String enddate=yinjkform.getEnddate();
			String yinjkzt =yinjkform.getYinjkzt();
			if(clerkCode!=null&&!clerkCode.trim().equals("")){
				hql+=" and  clerkcode=:clerkCode ";
				params.put("clerkCode",clerkCode);
			}
			if(!yinjkzt.equals("全部")){
				hql+=" and  yinjkzt=:yinjkzt ";
				params.put("yinjkzt",yinjkzt);
			}
			if(begindate!=null&&!begindate.trim().equals("")){
				hql+=" and  qiyrq=:begindate ";
				params.put("begindate",begindate);
			}
			if(enddate!=null&&!enddate.trim().equals("")){
				hql+=" and  tingyrq=:enddate ";
				params.put("enddate",enddate);
			}
			Query query=session.createQuery(hql);
			query.setString("jigh", jigh);
			Set<String>keys=params.keySet();
			Iterator ite=keys.iterator();
			while (ite.hasNext()) {
				String key=(String)ite.next();
				query.setString(key, params.get(key));
			}
			Object result =query.uniqueResult();
			yinjkNum=new YinjkNum();
			yinjkNum.setJigh(jigh);
			yinjkNum.setGuiyh(clerkCode);
			yinjkNum.setYinjkzt(yinjkzt);
			yinjkNum.setNum(result.toString());
			return yinjkNum;
		} catch (HibernateException e) {
			return yinjkNum;
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
}
