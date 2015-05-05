package com.unitop.sysmgr.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.config.DBinfoConig;
import com.unitop.sysmgr.dao.AutoCompleteDao;
import com.unitop.sysmgr.service.AutoCompleteService;

//获取自动匹配信息类
@Service("AutoCompleteServiceImpl")
public class AutoCompleteServiceImpl implements AutoCompleteService {

	@Resource
	private AutoCompleteDao AutoCompleteDao = null;

	// 帐号模糊查询
	public String autoCompleteForZhangh(String zhangh) {
		String db_type = DBinfoConig.getDBType();
		String sql = "";
		if("oracle".equals(db_type))
		{
			 sql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select zhangh from zhanghb where zhangh like :zhangh) A WHERE ROWNUM <= 10) WHERE RN > 0";
		}else{
			 sql = "SELECT * FROM (SELECT B.*, ROWNUMBER() OVER() AS RN FROM (select zhangh from zhanghb where zhangh like :zhangh ) AS B ) AS A WHERE A.RN BETWEEN 0 AND 10";
		}
		Map map = new HashMap();
		map.put("zhangh", zhangh);
		return AutoCompleteDao.autoCompleteForAccount(sql,map);
	}

	// 表字段信息
	public String getTableLineMap(String tableName) {
		Map lineMap = AutoCompleteDao.getTableLineMap("select * from " + tableName);
		Iterator it = lineMap.entrySet().iterator();
		String backmesage = "";
		while (it.hasNext())
		{
			Entry e = (Entry) it.next();
			backmesage += e.getKey() + ",";
		}
		return backmesage;
	}

	// 获取字典信息
	public String getZhidMC(String zhidId) {
		String sql = "select ZHIDMC from zhidb where zhidid=:zhidId";
		Map map = new HashMap();
		map.put("zhidId", zhidId);
		return AutoCompleteDao.autoCompleteForAccount(sql,map);
	}
}
