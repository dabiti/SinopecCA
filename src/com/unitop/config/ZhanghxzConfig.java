package com.unitop.config;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Zhanghxzb;
import com.unitop.sysmgr.dao.ZhanghbDao;

/*
 * BY ldd
 * 系统控制参数类
 */
@Repository
public class ZhanghxzConfig implements ApplicationListener{
	
	public static ArrayList<Zhanghxzb> zhanghxzList = null;
	
	@Resource
	public ZhanghbDao zhanghbDao;
	
	
	
	public static ArrayList<Zhanghxzb> getList(){
		if (zhanghxzList == null){
			ZhanghxzConfig zcf = new ZhanghxzConfig();
			zcf.instrance();
		}
		return zhanghxzList;
	}
	
	
	private void instrance() {
		ArrayList<Zhanghxzb> zhanghxzList = zhanghbDao.getZhanghxzList();
		ZhanghxzConfig.zhanghxzList = zhanghxzList;
	}


	public void onApplicationEvent(ApplicationEvent arg0) {
		instrance();
	}
	
	
	
	



}