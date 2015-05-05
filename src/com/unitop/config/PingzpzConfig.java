package com.unitop.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;
import com.unitop.sysmgr.dao.PingzpzbDao;


@Repository
public class PingzpzConfig implements ApplicationListener{
	
	public static List<Pingzpzb> pingzpzList = null;
	public static List<Yanygz>yanygzList=null;
	
	@Resource
	public PingzpzbDao pingzpzbDao;
	
	
	
	public static List<Pingzpzb> getList(){
		if (pingzpzList == null){
			PingzpzConfig zcf = new PingzpzConfig();
			zcf.instrance();
		}
		return pingzpzList;
	}
	public static List<Yanygz> getYangzList(){
		if(yanygzList==null){
			PingzpzConfig zcf = new PingzpzConfig();
			zcf.instrance();
		}
		return yanygzList;
	}
	
	
	private void instrance() {
		List<Pingzpzb> pingzpzList = pingzpzbDao.getPingzpzList();
		PingzpzConfig.pingzpzList = pingzpzList;

		List<Yanygz> yanygzList = pingzpzbDao.getYanygzList();
		PingzpzConfig.yanygzList = yanygzList;
	}


	public void onApplicationEvent(ApplicationEvent arg0) {
		instrance();
	}
	public static List<Pingzpzb> getPartList() {
		
			PingzpzConfig zcf = new PingzpzConfig();
			zcf.instrance();
		List<Pingzpzb> pingzPartList =screenList();
	/*	Collections.sort(pingzPartList,new Comparator() {
			Pingzpzb p1;
			Pingzpzb p2;
			public int compare(Object o1, Object o2) {
				p1=(Pingzpzb)o1;
				p2=(Pingzpzb)o2;
				return Integer.parseInt(p2.getPingzmc().substring(0, 1))-Integer.parseInt(p1.getPingzmc().substring(0, 1));
			}
			
		});*/
		return pingzPartList;
	}
	private static List<Pingzpzb> screenList() {
		List<Pingzpzb> pingzPartList=new ArrayList<Pingzpzb>();
		int i=1;
		f1 : for (Pingzpzb pingzpzb : pingzpzList) {
			try {
				Integer.parseInt(pingzpzb.getPingzmc().substring(0, 1));
				pingzPartList.add(pingzpzb);
			} catch (NumberFormatException e) {
				continue f1;
			}
		}
		return pingzPartList;
	}
	
	
	



}