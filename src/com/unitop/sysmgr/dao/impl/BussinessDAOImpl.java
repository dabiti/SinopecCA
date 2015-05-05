package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.BusinessRule;
import com.unitop.sysmgr.bo.Yewgz;
import com.unitop.sysmgr.dao.BusinessDAO;
@Repository("BussinessDAOImpl")
public class BussinessDAOImpl extends BaseDataResources implements BusinessDAO {

	public Map<String,BusinessRule> getBusinessRules() {
		List list = null;
		Session session = this.getBaseHibernateDao().getHibernateSession();
		Map<String,BusinessRule> map = new HashMap<String, BusinessRule>();
		try{
			Query query = session.createQuery("from Yewgz");
			list = query.list();
			if(list == null)
			{
				list  = new ArrayList();
			}
			for(int i = 0;i<list.size();i++)
			{
				Yewgz yewgz = (Yewgz) list.get(i);
				BusinessRule rule = new BusinessRule();
				rule.setYuansId(yewgz.getYuansid());
				rule.setYuansName(yewgz.getYuansname());
				rule.setIsReadonly(yewgz.getIsreadonly());
				rule.setMaxLength(yewgz.getMaxlength());
				rule.setYuansStyle(yewgz.getYuansstyle());
				rule.setStyleClass(yewgz.getStyleClass());
				rule.setValidateRule(yewgz.getValidaterule());
				rule.setMsgId(yewgz.getMsgid());
				map.put(rule.getYuansId(),rule);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return map;
	}
}
