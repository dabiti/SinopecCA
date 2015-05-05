package com.unitop.sysmgr.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.unitop.sysmgr.bo.Ci_renwxx;
import com.unitop.sysmgr.bo.Zhengprz;

/*
 */
public interface Ci_renwxxDao   extends BaseDataResourcesInterface{
	// 加载任务信息
	public Ci_renwxx getRenwxx(String renwbs);

	// 按条件查询批次任务
	public ArrayList<Ci_renwxx> getRenwxxList(String batchid ,String chulzt) ;
	
	//按条件查询数量
	public int getCountFromList(String batchid ,String chulzt) ;
	
	// 更新结果
	public boolean updateForAccount(Ci_renwxx taskid1,Zhengprz taskid2,String clerkid1,String clerkid2);
	
	//根据组合主键查询任务
	public Ci_renwxx getRenwxxById(String systemid,String renwbs);
	
	// 更新任务信息日志添加复核柜员
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2);
}
