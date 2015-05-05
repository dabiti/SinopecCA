package com.unitop.sysmgr.service;

import java.util.List;
import java.util.Map;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Autopasscount;
import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.SystemControlParameter;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.form.AccountinfoForm;
import com.unitop.sysmgr.form.SealchecklogForm;
public interface QueryService {
	public CanOperAccReturn updateOrgParent(String tellerorg,String operaccount,String opertellorg) throws Exception;
	public TabsBo findSealCheckLog(String sequence,String account, String clerknum, String checkmode, String checknum, String checkresult, String begindate, String enddate, String orgCode);//江西加流水号
	public TabsBo pingzhengyanyinlog(String sequence,String account, String clerknum, String checkmode, String checknum, String checkresult, String begindate, String enddate,String orgCode,String checktype,String canal);
	public TabsBo findSystemManageLog(String admincode, String begindate, String enddate);
	public TabsBo findAccountLog(String account, String begindate, String enddate, String[] managetype,String Industrycharacter, String orgcode);
	public List getCustomQueryList(String guiy, String QueryType);
	public List getCustomSealcheckList(String guiy);
	public List getCustomAccountinfoList(String guiy);
	public void CreateCustomaQuery(String TaskFlag, String clerkcode, String sqlstr, int QueryType,String remark) throws BusinessException, Exception;
	public void CreateCustomaQuery_AccountInfoForNet(String TaskFlag,AccountinfoForm accountinfoform,String clerkCode,String opendate1,String opendate2,String startdate1,String startdate2) throws BusinessException, Exception;
	public void CreateCustomaQuery_SealCheckLog(String TaskFlag,String clerkCode,SealchecklogForm sealchecklogForm) throws BusinessException, Exception;
	public void DeleteCustomaQuery(String TaskFlag, String clerkcode) throws BusinessException;
	public SystemControlParameter findSystemControlParameterById(String id);
	public List findAllSystemControlParameters();
	public void deleteSystemControlParameter(String id);
	public void updateSystemControlParameter(SystemControlParameter controlParameter);
	public void addSystemControlParameter(SystemControlParameter controlParameter);
	public List execDBForDQL(String sql,Map<String, String> pmap);
	public void execDBForDML(String sql,Map<String, String> pmap);
	//获取货币号列表
	public List getHuobh();
	
	public TabsBo findAuthorizeLog(String account,String begindate,String enddate);
	public TabsBo findAccountTongbrzLog(String account, String jigh,
			String begindate, String enddate);
	public List queryData(String jsql, Map<String, String> parameterMap,
			int first, int end,String className);
	public List queryDataToMap(String jsql, Map<String, String> parameterMap,
			int first, int end, String className);
	public Autopasscount countAutopassrate(String sql, int counts,String paramstr);
}

