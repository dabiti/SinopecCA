package com.unitop.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.unitop.sysmgr.bo.Ci_renwxx;
import com.unitop.sysmgr.bo.Ci_yyrz;
import com.unitop.sysmgr.bo.Zhengprz;
import com.unitop.sysmgr.dao.Ci_renwxxDao;
import com.unitop.sysmgr.dao.Ci_yyrzDao;
import com.unitop.sysmgr.service.BatchSealCheckService;

@Service("BatchSealCheckServiceImpl")
public class BatchSealCheckServiceImpl extends BaseServiceImpl implements
BatchSealCheckService {

	@Resource
	private Ci_renwxxDao RenwxxDao;
	@Resource
	private Ci_yyrzDao YyrzDao;
	

	// 加载任务信息
	public Ci_renwxx getRenwxx(String renwbs){
		return RenwxxDao.getRenwxx(renwbs);
	}
	
	
	// 按条件查询批次任务
	public ArrayList<Ci_renwxx> getRenwxxList(String batchid,String chulzt){
		return RenwxxDao.getRenwxxList(batchid,chulzt);
	}
	
	
	// 更新结果
	public boolean updateCheckResult(Ci_renwxx taskid1,Zhengprz taskid2,String clerkid1,String clerkid2){
	
		return RenwxxDao.updateForAccount(taskid1, taskid2, clerkid1, clerkid2);
	}
	
	
	public List<Ci_yyrz> getCi_yyrzList(String xitbs, String renwbs){
		return YyrzDao.getCi_yyrzListByRenwbs(xitbs,renwbs);
	}
	
	
	public String createFinishResult(String batchid,String systemid,String result){
		JSONObject jo = new JSONObject();
		jo.put("batchid", batchid);
		jo.put("systemid", systemid);
		jo.put("result", result);
		return jo.toString();
	}
	
	
	//给任务表添加单章
	public List<Ci_renwxx> addYyrzToRenwxx(List<Ci_renwxx> renwxxList){
		for(Ci_renwxx renwxx : renwxxList){
			String renwbs = renwxx.getCi_renwxxid().getRenwbs();
			String xitbs = renwxx.getCi_renwxxid().getXitbs();
			List<Ci_yyrz> yyrzList =  YyrzDao.getCi_yyrzListByRenwbs(xitbs,renwbs);
			renwxx.setCi_yyrzs(yyrzList);
		}
		return renwxxList;
	}
	
	
	//根据查询结果转换成xml
	public String convertCheckResultToXml(String systemid,String batchid,List<Ci_renwxx> renwxxList){
		StringBuffer sb  = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><msgtype>querycheckresult</msgtype><systemid>"+systemid+"</systemid><batchid>"+batchid+"</batchid></head><body><results>");
		
		for(Ci_renwxx renwxx : renwxxList){
			
			String renwbs = renwxx.getCi_renwxxid().getRenwbs();
			String xitbs = renwxx.getCi_renwxxid().getXitbs();
			
			//result
			String account = renwxx.getZhangh();
			String pingzh  = renwxx.getPingzh();
			String checktype = renwxx.getChecktype();
			String checkresult = renwxx.getYanyjg();
		//	String passcombine = renwxx.getTonggzh();
			String failreason = renwxx.getWeigyy();
			String imagemode = renwxx.getPictype();
			String imageindex = renwxx.getTuxsy();
			String checkmode = renwxx.getYanyms();
			String checkdate = renwxx.getChulrq();
			String clerkid1 = renwxx.getClerkid1();
			String clerkid2 = renwxx.getClerkid2();
			
			String vouchertype = renwxx.getPingzbs();
			String issuedate = renwxx.getChuprq();
			
			//singleresult
		    List<Ci_yyrz> yyrzList = renwxx.getCi_yyrzs();
		    JSONArray ja = new JSONArray();
			for(Ci_yyrz yyrz:yyrzList){
				JSONObject jo = new JSONObject();
				jo.put("yinjbh", yyrz.getCi_yyrzid().getYinjbh());
				jo.put("dzyyjg", yyrz.getYanyjg());
				jo.put("yinjlx", yyrz.getYinjbz());
				ja.add(jo);
			}
			
			//sb.append("<result><account>"+account+"</account><vouchertype>"+vouchertype+"</vouchertype><voucherid>"+pingzh+"</voucherid><issuedate>"+issuedate+"</issuedate><imagemode>"+imagemode+"</imagemode><imageindex>"+imageindex+"</imageindex><checktype>"+checktype+"</checktype><checkresult>"+checkresult+"</checkresult><passcombine>"+passcombine+"</passcombine><failreason>"+failreason+"</failreason><singleresult>"+ja.toString()+"</singleresult></result>");
			sb.append("<result><taskid>"+renwbs+"</taskid><account>"+account+"</account><clerkid1>"+clerkid1+"</clerkid1><clerkid2>"+clerkid2+"</clerkid2><vouchertype>"+vouchertype+"</vouchertype><voucherid>"+pingzh+"</voucherid><issuedate>"+issuedate+"</issuedate><imagemode>"+imagemode+"</imagemode><imageindex>"+imageindex+"</imageindex><checktype>"+checktype+"</checktype><checkmode>"+checkmode+"</checkmode><checkdate>"+checkdate+"</checkdate><checkresult>"+checkresult+"</checkresult><failreason>"+failreason+"</failreason><singleresult>"+ja.toString()+"</singleresult></result>");
			
			
		}
		
		sb.append("</results></body></root>");
		
		return sb.toString();
	}
	
	
	
	
	//该批次是否已全部验完
	public String getCountFromList(String batchid ){
		//按条件查询数量
		int totalNum  = RenwxxDao.getCountFromList(batchid ,null);
		int finishNum = RenwxxDao.getCountFromList(batchid ,"1");
		
		if(totalNum!=0){
			if(totalNum==finishNum){
				return "0";
			}else{
				return "1";
			}
		}else{
			return "2";
		}
	}
	
	
	
	//根据组合主键查询任务
	public Ci_renwxx getRenwxxById(String systemid,String renwbs) {
		return RenwxxDao.getRenwxxById(systemid, renwbs);
	}
	
	// 更新任务信息日志添加复核柜员
	public boolean addClerk2OfRenwxx(String systemid,String taskid2,String clerkid2){
		return RenwxxDao.addClerk2OfRenwxx(systemid,taskid2,clerkid2);
	}
	
}
