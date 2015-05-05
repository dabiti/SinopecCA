package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Ci_yyrz;
import com.unitop.sysmgr.bo.Ci_yyrzid;
import com.unitop.sysmgr.dao.Ci_yyrzDao;

@Repository("ci_yyrzDaoImpl")
public class Ci_yyrzDaoImpl extends BaseDataResources implements Ci_yyrzDao {

	
	public List<Ci_yyrz> getCi_yyrzListByRenwbs(String xitbs ,String renwbs) {
	//	String hql = "from Ci_yyrz ";
		String sql = "select renwbs,yinjbh,xitbs,zhangh,renwlx,yinqbgh,yinjbz,yanyms,yanyjg,chulrq,chulsj,renwrq,jiedbs from ci_yyrz ";
		boolean xitbsOk = xitbs!=null&&!"".equals(xitbs);
		boolean renwbsOk = renwbs!=null&&!"".equals(renwbs);
		Map<String,String>paramMap=new HashMap<String, String>();
		if(xitbsOk&&renwbsOk){
			sql+= " where xitbs =:xitbs and renwbs=:renwbs ";
			paramMap.put("xitbs", xitbs);
			paramMap.put("renwbs", renwbs);
		}else if(xitbsOk){
			sql+= " where xitbs =:xitbs ";
			paramMap.put("xitbs", xitbs);
		}else if(renwbsOk){
			sql+= " where renwbs=:renwbs ";
			paramMap.put("renwbs", renwbs);
		}
		//System.out.println("sql:"+sql);
		Session session = this.getBaseHibernateDao().getHibernateSession();
		ArrayList<Ci_yyrz> yyrzList = new ArrayList<Ci_yyrz>();
		try{
			Query query = session.createSQLQuery(sql);
			if(paramMap.size()>0){
				Set<String> keys=paramMap.keySet();
				for (String key : keys) {
					query.setString(key, paramMap.get(key));
				}
			}
			for(Iterator iter = query.list().iterator();iter.hasNext();){
				Ci_yyrz yyrz = new Ci_yyrz();
				Ci_yyrzid yyrzid = new Ci_yyrzid();
				Object[] yyrzObj = (Object[]) iter.next();
				yyrzid.setRenwbs((String) yyrzObj[0]);
				yyrzid.setYinjbh((String) yyrzObj[1]);
				yyrzid.setXitbs((String) yyrzObj[2]);
				yyrzid.setZhangh((String) yyrzObj[3]);
				yyrz.setCi_yyrzid(yyrzid);
				yyrz.setRenwlx((String) yyrzObj[4]);
				yyrz.setYinqbgh((String) yyrzObj[5]);
				yyrz.setYinjbz((String) yyrzObj[6]);
				yyrz.setYanyms((String) yyrzObj[7]);
				yyrz.setYanyjg((String) yyrzObj[8]);
				yyrz.setChulrq((String) yyrzObj[9]);
				yyrz.setChulsj((String) yyrzObj[10]);
				yyrz.setRenwrq((String) yyrzObj[11]);
				yyrz.setJiedbs((String) yyrzObj[12]);
				yyrzList.add(yyrz);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return yyrzList;
	}
	
	

	
}
