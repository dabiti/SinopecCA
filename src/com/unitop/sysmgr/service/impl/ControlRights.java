package com.unitop.sysmgr.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.config.Rights;
import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.service.ClerkManageService;

@Service("ControlRights")
public class ControlRights {

	@Resource
	private ClerkManageService ClerkManageService;
	
	// 版本控制
	private static Map<String, String> map = null;
	
	public Map<String, String> getControlMap() {
		return SystemConfig.getInstance().systemConfigMap;
	}
	
	public String timeControl() throws Exception {
		String state = "";
		String version = SystemConfig.getInstance().getValue("version");
		if ("shiyong".equals(version)) 
		{
			String product_period = getControlMap().get("productperiod");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			long rightNow = Calendar.getInstance().getTime().getTime();
			long productperiod = format.parse(product_period).getTime() + 24 * 3600 * 1000;
			long i = rightNow - productperiod;
			if (i >= 0)
			{
				state = "-1";
			}
		} else if ("zhengshi".equals(version)) 
		{
			state = "1";
		} else {
			return "0";
		}
		return state;
	}

	// 授权控制
	public boolean emPower() {
		String empower =SystemConfig.getInstance().getValue("empower");
		if ("y".equals(empower))
		{
			return true;
		}
		return false;
	}

	// 终端控制
	public boolean zdControl() {
		int nowonline = Rights.getInstance().getNowonline();
		int onlinenum = Integer.parseInt(SystemConfig.getInstance().getValue("onlinenum"));
		if (nowonline >= onlinenum) 
		{
			return false;
		}
		return true;
	}

	// 创建柜员数控制
	public boolean clerkNumControl() {
		if ("shiyong".equals(SystemConfig.getInstance().getValue("version")))
		{
			return true;
		}
		if (!emPower()) 
		{
			return true;
		}
		long clerkquantity = Long.parseLong(SystemConfig.getInstance().getValue("clerkquantity"));
		long nowclerkquantity = ClerkManageService.getClerkCouns();
		if (nowclerkquantity >= clerkquantity)
		{
			return false;
		}
		return true;
	}

	public String control() throws Exception {
		String i = timeControl();
		if ("-1".equals(i))
		{
			return "试用期满";
		} else if ("1".equals(i))
		{
			if (emPower())
			{
				if (!zdControl()) 
				{
					return "终端数满";
				}
			}
		} else if ("0".equals(i)) 
		{
			return "版本错误";
		}
		return "继续执行";
	}

}
