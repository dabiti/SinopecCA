package com.sinodata.common.sequence;

import java.util.Date;

import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.dao.SystemDao;

/*
 * 生成日期序列
 * 
 */
public class SequenceYYYYMMDD {
	
	private SystemDao systemDao;
	private static String db_date = "";
	private static long xt_init_nowtime = 0;
	private static long db_init_nowtime = 0;
	
	public SequenceYYYYMMDD(SystemDao systemDao){
		this.systemDao = systemDao;
	}
	
	//考虑 每天与数据库同步一次
	public String getYYYYMMDD(){
		
		long nowtime = System.currentTimeMillis();
		
		if(xt_init_nowtime == 0)
		{
			xt_init_nowtime = System.currentTimeMillis();
		}
		if(db_init_nowtime == 0)
		{
			String tempString = systemDao.getSystetemNowDate();
			db_init_nowtime = DateTool.strToDate(tempString).getTime();
			db_date = tempString.substring(0, 10).replace("-", "");
		}
		
		long yx_time = (xt_init_nowtime-nowtime)+db_init_nowtime;
		Date db_yx_time = new Date(yx_time);
		Date db_init_time = new Date(db_init_nowtime);
		
		if(db_yx_time.getDate()!=db_init_time.getDate())
		{
			db_date = systemDao.getSystetemNowDate().substring(0, 10).replace("-", "");
			xt_init_nowtime=0;
			db_init_nowtime=0;
		}else{
			return db_date;
		}
		return db_date;
	}
	
}