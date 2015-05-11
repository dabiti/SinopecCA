package com.unitop.sysmgr.service;

import com.unitop.sysmgr.bo.AccountManageLog;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.SystemManageLog;
public interface SystemMgrService {

	public void createSystemManageLog(SystemManageLog log);
	public void createAccountmanagelog(AccountManageLog bo);
	public void createAccountmanagelogNew(AccountManageLog bo);
	public boolean CanOperDesOrg(String OperOrg, String DesOrg);
	public boolean CanOperDesSpecialOrg(String OperOrg, String DesOrg);
	
	//拆分方法分别判断柜员是否能操作机构、柜员、账户
	public CanOperAccReturn ProCanOperOrg(String tellerorg,String operorg);
	public CanOperAccReturn ProCanOperAcc(String tellerorg,String operaccount);
	public CanOperAccReturn ProCanOperTel(String tellerorg,String clerknum);
	public boolean CanTongd(String cleaknum,String zhangh);
	
	public String getSystetemNowDate();
	public String getSystetemNextDate();
	public String getSystemContolParameter(String id) throws Exception;
	public String getSystemSequence();
	
	/**
	 * @author wcl
	 * @param tellerorg 柜员ID
	 * @param operaccount 被操作印鉴卡
	 */
//	public CanOperAccReturn ProCanOperYinjk(String tellerorg,String operyinjk);
	
	//获取下一个凭证号
	public String getPZHSequence();
	public String getSystetemYestoDay();
	
	/*
	 * 中石化权限校验
	 */
	public boolean CanOperSite(String OperTer
			, String DesTer);
}
