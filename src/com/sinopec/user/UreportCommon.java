package com.sinopec.user;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sinopec.common.SpringContent;
import com.sinopec.report.model.ReportForm;
import com.sinopec.report.model.ReportResult;
import com.sinopec.report.model.ReportReturn;
import com.sinopec.table.model.Table;
import com.unitop.config.DBinfoConig;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.SystemMgrService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.ClerkManageServiceImpl;

public  class UreportCommon{
	//初始化 FORM
	public ReportReturn doInitForForm(Clerk clerk,ReportForm reportForm,SpringContent springContent)  throws BusinessException{
		return new ReportReturn(null, null, null);
	}
	//初始化 Result
	public ReportReturn doInitForResult(Clerk clerk,ReportResult reportResult,SpringContent springContent)  throws BusinessException{
		return new ReportReturn(null, null, null);
	}
	//业务特色
	public ReportReturn doCommon(Clerk clerk, Table table,SpringContent springContent) throws BusinessException{
		ZhanghbService ZhanghbService = (ZhanghbService) springContent.getBean("ZhanghbServiceImpl");
		SystemMgrService systemMgrService = (SystemMgrService) springContent.getBean("systemMgrService");
		ClerkManageServiceImpl clerkManageService =(ClerkManageServiceImpl)springContent.getBean("ClerkManageServiceImpl");
		OrgService orgService =(OrgService)springContent.getBean("OrgServiceImpl");
		//业务权限验证
		{	
			if(clerk==null) clerk = new Clerk();
			String orgCode = clerk.getOrgcode();
			//20140812增加处理中心的校验
			Org clerk_org=orgService.getOrgByCode(orgCode);
			if(clerk_org!=null&&clerk_org.getLeixbs().equals("4")){
				orgCode=clerk_org.getShOrgCode();
			}
			Map map= table.getWhere().getWhereValue();
			Iterator it = map.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry entry  = (Entry) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				
				if("account".equals(key.toLowerCase()))
				{
					Zhanghb account = ZhanghbService.getZhanghb(value);
					if(account==null)
					{
						return new ReportReturn("ureport_account_isNull",null,map);
					}
					boolean  bool = systemMgrService.CanOperDesOrg(orgCode,account.getJigh());
					if(!bool)
					{
						return new ReportReturn("ureport_account_isPost",null,map);
					}
				}
				
				if("zhangh".equals(key.toLowerCase()))
				{
					Zhanghb account = ZhanghbService.getZhanghb(value);
					if(account==null)
					{
						//针对 account>zhangh转义，实现提示信息共享 
						map.put("account", map.get("ZHANGH"));
						return new ReportReturn("ureport_account_isNull",null,map);
					}
					boolean  bool = systemMgrService.CanOperDesOrg(orgCode,account.getJigh());
					if(!bool)
					{
						//针对 account>zhangh转义，实现提示信息共享 
						map.put("account", map.get("ZHANGH"));
						return new ReportReturn("ureport_account_isPost",null,map);
					}
				}
				
				if("kehh".equals(key.toLowerCase()))
				{
					List kehhList = (List) ZhanghbService.getKehh(value,clerk.getOrgcode());
					if(kehhList.size() == 0 )
					{
						return new ReportReturn(null,"客户号不存在或没有权限查看此客户号!",map);
					}
					//特殊处理  针对客户简单查询  此处二期产品需要考虑进去
					String db_type = DBinfoConig.getDBType();
					if("oracle".equals(db_type))
					{
						table.setSqlString(table.getSqlString()+" and internalorganizationnumber in (select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber  start with internalorganizationnumber ="+clerk.getOrgcode()+")");
					}else{
						table.setSqlString(table.getSqlString()+" and internalorganizationnumber in (select * from TABLE(ORGFUNCTION('"+clerk.getOrgcode()+"')))");
					}
				}
				//柜员号 权限验证
				if("clerknum".equals(key.toLowerCase()))
				{
					Clerk tClerk = clerkManageService.getClerkByCode(value);
					if(tClerk==null)
					{
						return new ReportReturn("ureport_clerknum_isNull",null,map);
					}
					boolean  bool = clerkManageService.CanOperDesClerkCode(clerk, value);
					if(!bool)
					{
						return new ReportReturn("ureport_clerknum_isPost",null,map);
					}
				}
				//增加柜员号验证
				if("guiyh".equals(key.toLowerCase()))
				{
					Clerk tClerk = clerkManageService.getClerkByCode(value);
					if(tClerk==null)
					{
						//针对 guiyh>clerknum转义，实现提示信息共享 
						map.put("clerknum", map.get("GUIYH"));
						return new ReportReturn("ureport_clerknum_isNull",null,map);
					}
					boolean  bool = clerkManageService.CanOperDesClerkCode(clerk, value);
					if(!bool)
					{
						//针对 guiyh>clerknum转义，实现提示信息共享 
						map.put("clerknum", map.get("GUIYH"));
						return new ReportReturn("ureport_clerknum_isPost",null,map);
					}
				}

				if("orgCode".equals(key.toLowerCase())||"netpointflag".equals(key.toLowerCase()))
				{	
					//验证机构是否存在
					Org org = orgService.getOrgByCode(value);
					if(org==null)
					{
						return new ReportReturn(null,"机构号不存在!",map);
					}
					//柜员机构是否有权限操作此柜员
					boolean  shifyouqx = systemMgrService.CanOperDesOrg(clerk.getOrgcode(),value);
					if(!shifyouqx)
					{
						return new ReportReturn(null,"没有权限查看此机构信息!",map);
					}
				}
				if("internalorganizationnumber".equals(key))
				{	
					//验证机构是否存在
					Org org = orgService.getOrgByCode(value);
					if(org==null)
					{
						return new ReportReturn(null,"机构号不存在!",map);
					}
					//柜员机构是否有权限操作此柜员
					boolean  shifyouqx = systemMgrService.CanOperDesOrg(orgCode,value);
					if(!shifyouqx)
					{
						return new ReportReturn(null,"没有权限查看此机构信息!",map);
					}
				}


				if("INTERNALORGANIZATIONNUMBER".equals(key))
				{	
					//验证机构是否存在
					Org org = orgService.getOrgByCode(value);
					if(org==null)
					{
						return new ReportReturn(null,"机构号不存在!",map);
					}
					//柜员机构是否有权限操作此柜员
					boolean  shifyouqx = systemMgrService.CanOperDesOrg(clerk.getOrgcode(),value);
					if(!shifyouqx)
					{
						return new ReportReturn(null,"没有权限查看此机构信息!",map);
					}
				}
			}
		}
		return null;
	}
}