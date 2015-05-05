package com.unitop.sysmgr.service;

import java.util.ArrayList;
import java.util.List;

import com.unitop.sysmgr.bo.Ci_renwxx;
import com.unitop.sysmgr.bo.Ci_yyrz;
import com.unitop.sysmgr.bo.Zhengprz;

/*
 * 查询信息查询接口
 */
public interface BatchSealCheckService {
	
	// 加载任务信息
	public Ci_renwxx getRenwxx(String renwbs);
	
	
	// 按条件查询批次任务
	public ArrayList<Ci_renwxx> getRenwxxList(String batchid,String chulzt);
	
	
	//更新结果
	public boolean updateCheckResult(Ci_renwxx taskid1,Zhengprz taskid2,String clerkid1,String clerkid2);
	
	
	public List<Ci_yyrz> getCi_yyrzList(String xitbs, String renwbs);
	
	
	public String createFinishResult(String batchid,String systemid,String result);
	
	
	//给任务表添加单章
	public List<Ci_renwxx> addYyrzToRenwxx(List<Ci_renwxx> renwxxList);
	
	
	//根据查询结果转换成xml
	public String convertCheckResultToXml(String systemid,String batchid,List<Ci_renwxx> renwxxList);


	public String getCountFromList(String batchid);
	
	
	//根据组合主键查询任务
	public Ci_renwxx getRenwxxById(String systemid,String renwbs);
	
	// 更新任务信息日志添加复核柜员
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2);
}
