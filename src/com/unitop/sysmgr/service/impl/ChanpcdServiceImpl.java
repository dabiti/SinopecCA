package com.unitop.sysmgr.service.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.config.Privilege;
import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.dao.ChanpcdDao;
import com.unitop.sysmgr.service.ChanpcdService;

@Service("ChanpcdServiceImpl")
public class ChanpcdServiceImpl implements ChanpcdService {

	@Resource
	private ChanpcdDao ChanpcdDao;
	
	static Collection colls = null;
	
	public Collection getPostCollection() {
		
		//系统模式 ：测试模式 生成模式
		String systemModel = SystemConfig.getInstance().getSystemModle();
		if(colls==null||"test".equals(systemModel))
		{
			Map Postmap = null;
			try {
				Postmap = ChanpcdDao.getAllFromChanpgncd();
				colls = Postmap.values();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return colls;
	}
	
	
	static Map Postmap =null;
	public Privilege getPostCollectionByName(String name) throws SQLException {
		if(Postmap!=null)return (Privilege) Postmap.get(name);
		Postmap = ChanpcdDao.getAllFromChanpgncd();
		return (Privilege) Postmap.get(name);
	}
}
