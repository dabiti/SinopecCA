package com.unitop.sysmgr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.sinodata.table.model.RelationTable;
import com.sinodata.table.model.SqlParameter;
import com.sinodata.table.model.Table;
import com.sinodata.table.model.Where;
import com.sinodata.table.util.DanbwhTool;
import com.sinodata.table.util.TableTool;
import com.unitop.config.SystemConfig;
import com.unitop.framework.util.JsonTool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.service.DanbwhService;

/*
 * 单表维护
 * date:2011-07-14
 * by ldd
 */
@Controller("/doDanbwh")
public class DoDanbwhAction extends ExDispatchAction {
	
	@Resource
	private DanbwhService DanbwhService;
	
	/*
	 * 单表遍历
	 */
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			try {
				String gongnID =request.getParameter("gongnid");
				Table table = getTableByID(gongnID);
				if(table.getField().size()==0) return this.showMessageJSP(actionMapping, request, "list", "没有发现配置字典信息!");
				TableTool.getSelectSQL(table);
				List list = DanbwhService.doSelect(table);
				request.setAttribute("table",table);
				request.setAttribute("list", list);
				//字典信息
				request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(table.getDictionaryMap()));
				return actionMapping.findForward("list");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, actionMapping, request, "list");
			}
	}
	
	/*
	 * 单表查询(主从表)
	 */
	public ActionForward listForZhucb(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			String zhub_gongnid = request.getParameter("zhub_gongnid");//主表功能ID
			String zib_gongnid = request.getParameter("zib_gongnid");//子表功能ID
			try {
				Table zhubtable = getTableByID(zhub_gongnid);
				Table zibtable = getTableByID(zib_gongnid);
				if(zibtable.getField().size()==0) return this.showMessageJSP(actionMapping, request, "list", "没有发现配置字典信息!");
				DanbwhTool.doHrefLinked(zhubtable, zibtable, request);
				TableTool.getSelectSQL(zibtable);
				List list = DanbwhService.doSelect(zibtable);
				
				request.setAttribute("list", list);
				request.setAttribute("table",zibtable);
				request.setAttribute("zhubtable", zhubtable);
				//字典信息
				request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(zhubtable.getDictionaryMap()));
				return actionMapping.findForward("list");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, actionMapping, request, "list");
			}finally{
			}
	}
	
	/*
	 * 单表查询_检索
	 */
	public ActionForward listForSelect(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			String zhub_gongnid = request.getParameter("gongnid");//主表功能ID
			String zib_gongnid = request.getParameter("zib_gongnid");//子表功能ID
			try {
				Table zhubtable = getTableByID(zhub_gongnid);
				Table zibtable = getTableByID(zib_gongnid);
				DanbwhTool.doSelectForjians(zhubtable, zibtable, request);
				TableTool.getSelectSQL(zhubtable);
				List list = DanbwhService.doSelect(zhubtable);
				request.setAttribute("list", list);
				request.setAttribute("table",zhubtable);
				//字典信息
				request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(zhubtable.getDictionaryMap()));
				return actionMapping.findForward("list");
			} catch (Exception e) {
				e.printStackTrace();
				return this.errrForLogAndException(e, actionMapping, request, "list");
			}finally{
			}
	}
	
	/*
	 * 添加跳转
	 */
	public ActionForward forwardAdd(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			try {
				String gongnID =request.getParameter("gongn_id");
				Table table = getTableByID(gongnID);
				{
					String zhub_gongnid = request.getParameter("zhub_gongnid");//主表功能ID
					if(zhub_gongnid!=null)
					{
						Table zhubtable = getTableByID(zhub_gongnid);
						DanbwhTool.doHrefLinked(zhubtable, table, request);
						request.setAttribute("zhubtable", zhubtable);
					}
				}
				request.setAttribute("table",table);
				//字典信息
				request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(table.getDictionaryMap()));
				return actionMapping.findForward("add");
			} catch (Exception e) {
				return this.errrForLogAndException(e, actionMapping, request, "list");
			}
	}
	
	/*
	 * 修改跳转
	 */
	public ActionForward forwardUpdate(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String gongnID =request.getParameter("gongn_id");
			Table table = getTableByID(gongnID);
			Map whereVale = new HashMap();
			Iterator it = table.getTableKeys().entrySet().iterator();
			while(it.hasNext())
			{
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
				 whereVale.put(key, request.getParameter(key));
			}
			table.setWhere(new Where());
			table.getWhere().setWhereValue(whereVale);
			{
				String zhub_gongnid = request.getParameter("zhub_gongnid");//主表功能ID
				if(zhub_gongnid!=null)
				{
					Table zhubtable = getTableByID(zhub_gongnid);
					DanbwhTool.doHrefLinked(zhubtable, table, request);
					request.setAttribute("zhubtable", zhubtable);
				}
			}
			TableTool.getSelectSQL(table);
			List list = DanbwhService.doSelect(table);
			request.setAttribute("table",table);
			request.setAttribute("map", list.get(0));
			//字典信息
			request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(table.getDictionaryMap()));
			return actionMapping.findForward("update");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "list");
		}
	}
	
	/*
	 * 保存
	 */
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk  clerk= (Clerk) request.getSession().getAttribute("clerk");
			if(clerk==null) clerk = new Clerk();
			Table table = null;
			List list =null;
			String content ="[保存成功](";
		try {
			String gongnID =request.getParameter("gongn_id");
			table = getTableByID(gongnID);
			Map fieldValue = new HashMap();
			Iterator it = table.getField().entrySet().iterator();
			while(it.hasNext())
			{
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
				 fieldValue.put(key, request.getParameter(key)==null?"": request.getParameter(key));
			}
			table.setFieldValue(fieldValue);
			TableTool.getIneertSQL(table);
			DanbwhService.doDML(table);
			{
				table = getTableByID(gongnID);
				//V3.0 主从表维护
				String zhub_gongnid = request.getParameter("zhub_gongnid");//主表功能ID
				if(zhub_gongnid!=null)
				{
					Table zhubtable = getTableByID(zhub_gongnid);
					request.setAttribute("zhubtable",zhubtable);
					DanbwhTool.doHrefLinked(zhubtable, table, request);
				}
				TableTool.getSelectSQL(table);
				list = DanbwhService.doSelect(table);
			}
			
			it = table.getTableKeys().entrySet().iterator();
			while(it.hasNext())
			{
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
			 content+=" "+table.getField().get(key)+"="+fieldValue.get(key);
			}
			 content+=" )";
			if(!"".equals(clerk.getCode()))
			this.createManageLog(clerk.getCode(), table.getName()+content);
			//字典信息
			request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(table.getDictionaryMap()));
			return this.showMessageJSP(actionMapping,request,"list",content);
		} catch (Exception e) {
			e.printStackTrace();
			return this.processException(actionMapping, request, e, "add");
		}finally{
			request.setAttribute("table",table);
			request.setAttribute("list", list);
		}
	}
	
	/*
	 * 修改
	 */
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk  clerk= (Clerk) request.getSession().getAttribute("clerk");
		if(clerk==null) clerk = new Clerk();
		Table table = null;
		List list =null;
		String content ="[修改成功](";
		try {
			String gongnID =request.getParameter("gongn_id");
			
			table = getTableByID(gongnID);
			
			Map whereVale = new HashMap();
			Iterator it = table.getTableKeys().entrySet().iterator();
			while(it.hasNext()){
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
				 whereVale.put(key, request.getParameter(key+"ID"));
			}
			table.setWhere(new Where());
			table.getWhere().setWhereValue(whereVale);
			
			Map fieldValue = new HashMap();
			it = table.getField().entrySet().iterator();
			while(it.hasNext()){
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
				 fieldValue.put(key, request.getParameter(key+"Value")==null?"": request.getParameter(key+"Value"));
			}
			table.setFieldValue(fieldValue);
			TableTool.getUpdateSQL(table);
			DanbwhService.doDML(table);
			
			{
				table = getTableByID(gongnID);
				//V3.0 主从表维护
				String zhub_gongnid = request.getParameter("zhub_gongnid");//主表功能ID
				if(zhub_gongnid!=null)
				{
					Table zhubtable = getTableByID(zhub_gongnid);
					request.setAttribute("zhubtable",zhubtable);
					DanbwhTool.doHrefLinked(zhubtable, table, request);
				}
				TableTool.getSelectSQL(table);
				list = DanbwhService.doSelect(table);
			}
			
			it = table.getTableKeys().entrySet().iterator();
			while(it.hasNext())
			{
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
				 content+=" "+table.getField().get(key)+"="+fieldValue.get(key);
			}
			 content+=" )";
			if(!"".equals(clerk.getCode()))
			this.createManageLog(clerk.getCode(), table.getName()+content);
			//字典信息
			request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(table.getDictionaryMap()));
			return this.showMessageJSP(actionMapping,request,"list",content);
		} catch (Exception e) {
			return this.processException(actionMapping, request, e, "update");
		}finally{
			request.setAttribute("table",table);
			request.setAttribute("list", list);
		}
	}
	
	/*
	 *删除 
	 */
	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk  clerk= (Clerk) request.getSession().getAttribute("clerk");
		if(clerk==null) clerk = new Clerk();
		List list =null;
		Table table = null;
		String content ="[删除成功](";
		try {
			String gongnID =request.getParameter("gongn_id");
			
			table = getTableByID(gongnID);
			Map whereVale = new HashMap();
			Iterator it = table.getTableKeys().entrySet().iterator();
			while(it.hasNext()){
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
				 whereVale.put(key, request.getParameter(key));
			}
			table.setWhere(new Where());
			table.getWhere().setWhereValue(whereVale);
			TableTool.getDeleteSQL(table);
			DanbwhService.doDML(table);
			
			{
				table = getTableByID(gongnID);
				//V3.0 主从表维护
				String zhub_gongnid = request.getParameter("zhub_gongnid");//主表功能ID
				if(zhub_gongnid!=null)
				{
					Table zhubtable = getTableByID(zhub_gongnid);
					request.setAttribute("zhubtable",zhubtable);
					DanbwhTool.doHrefLinked(zhubtable, table, request);
				}
				TableTool.getSelectSQL(table);
				list = DanbwhService.doSelect(table);
			}
			
			it = table.getTableKeys().entrySet().iterator();
			while(it.hasNext())
			{
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = (String) entry.getKey();
			 content+=" "+table.getField().get(key)+"="+whereVale.get(key);
			}
			 content+=" )";
			if(!"".equals(clerk.getCode()))
			this.createManageLog(clerk.getCode(), table.getName()+content);
			//字典信息
			request.setAttribute("dictionaryDatad", JsonTool.toJsonForArry(table.getDictionaryMap()));
			return this.showMessageJSP(actionMapping,request,"list",content);
		} catch (Exception e) {
			e.printStackTrace();
			return this.processException(actionMapping, request, e, "list");
		}finally{
			request.setAttribute("table",table);
			request.setAttribute("list", list);
		}
	}
	
	static Map CasheMap = new HashMap();
	
	Table getTableByID(String gongnID) throws Exception{
		String systemModel = SystemConfig.getInstance().getSystemModle();
		//Cashe
		{
				Table t  = (Table) CasheMap.get(gongnID);
				if(null!=t&&!"test".equals(systemModel))
				{
					t.setSqlString("");
					t.getSqlParameter().clear();
					t.setWhere(null);
					return t;
				}
		}
		
		Table table = new Table("select * from danbwhzb where gongnid=?"); 
		table.getSqlParameter().put(1,new SqlParameter("gongnid","string",gongnID));
		List list= DanbwhService.doSelect(table);
		if(list.size()>0)
		{
			Map danbwhMap = (Map) list.get(0);
			table = new Table("select * from danbwhcb where gongnid=? order by to_number(xianssx)");
			table.getSqlParameter().put(1,new SqlParameter("gongnid","string",gongnID));
			table.setId(danbwhMap.get("gongnid").toString());
			table.setName(danbwhMap.get("gongnmc").toString());
			table.setTableName(danbwhMap.get("weihbm").toString());
			table.setUpdate(danbwhMap.get("shifbj").toString().equals("1"));
			table.setSave(danbwhMap.get("shifbc").toString().equals("1"));
			table.setDelete(danbwhMap.get("shifsc").toString().equals("1"));
			table.setMasterTable(danbwhMap.get("shifzb").toString().equals("1"));
			table.setClassForUserDefined(danbwhMap.get("zhidyl").toString());
			
			list = DanbwhService.doSelect(table);
			table.getSqlParameter().clear();
			//加载字典信息
			Map dictionaryMap  = DanbwhService.getTable(danbwhMap.get("weihbm").toString());
			table.setDictionaryMap(dictionaryMap);
			
			Map fieldMap = new LinkedHashMap();
			Map keysMap = new LinkedHashMap();
			Map fieldType = new LinkedHashMap();
			Map fieldTableType = new LinkedHashMap();
			Map fieldDefaultValue = new LinkedHashMap();
			Map fieldForShow = new LinkedHashMap();
			Map fieldForSaveOrUpdate = new LinkedHashMap();
			Map validateMap  = new LinkedHashMap();
			
			for(int i = 0 ; i<list.size() ; i++)
			{
				Map tempMap = (Map) list.get(i);
				fieldMap.put(tempMap.get("zidmc").toString().toLowerCase(),tempMap.get("zhansmc"));
				if("1".equals(tempMap.get("shifzx")))
				{
					fieldForShow.put(tempMap.get("zidmc").toString().toLowerCase(), tempMap.get("zhansmc"));
				}
				if("1".equals(tempMap.get("shifbc")))
				{
					Map m = new LinkedHashMap();
					if(tempMap.get("shurlx").equals("静态多选框"))
					{
						String beiz = (String) tempMap.get("beiz");
						String[] b = getStrings(beiz,"|");
						for(int t=0;t<b.length;t++)
						{
							String temp =b[t];
							String[] c = getStrings(temp,":");
							for(int t1=0;t1<c.length;t1++)
							{
								 m.put(c[0],c[1]);
							}
						}
						fieldForSaveOrUpdate.put(tempMap.get("zidmc").toString().toLowerCase(),new LinkedHashMap().put(tempMap.get("shurlx"), m));
					}else{
						fieldForSaveOrUpdate.put(tempMap.get("zidmc").toString().toLowerCase(),tempMap.get("shurlx"));
					}
				}
				
				//获取主键
				if("1".equals(tempMap.get("shifzj")))
				{
					keysMap.put(tempMap.get("zidmc").toString().toLowerCase(),tempMap.get("zhansmc"));
				}
				fieldTableType.put(tempMap.get("zidmc").toString().toLowerCase(), tempMap.get("zidlx"));
				fieldDefaultValue.put(tempMap.get("zidmc").toString().toLowerCase(), tempMap.get("morz"));
				validateMap.put(tempMap.get("zidmc").toString().toLowerCase(),tempMap.get("jiaoygz"));
			}
			table.setField(fieldMap);
			table.setFieldForShow(fieldForShow);
			table.setFieldForSaveOrUpdate(fieldForSaveOrUpdate);
			table.setTableKeys(keysMap);
			table.setFieldType(fieldType);
			table.setFieldTableType(fieldTableType);
			table.setFieldDefaultValue(fieldDefaultValue);
			table.setValidateMap(validateMap);
		}
		
		//主表加载子表关系表配置信息
		if(table.isMasterTable())
		{
			Table relationForZibbhTable = new Table("select zibbh from DANBWHGXB where zhubbh=? group by zibbh"); 
			relationForZibbhTable.getSqlParameter().put(1,new SqlParameter("gongnid","string",gongnID));
			List relationForZibbhList= DanbwhService.doSelect(relationForZibbhTable);
			
			Map relationTableMap = new LinkedHashMap();
			Map isHrefLinked = new LinkedHashMap();
			Map chiledTableForfield = new LinkedHashMap();
			if(relationForZibbhList==null)relationForZibbhList = new ArrayList(); 
			for(int i = 0 ; i<relationForZibbhList.size(); i++)
			{
				Map relationForZibbhMap = (Map) relationForZibbhList.get(i);
				Table relationTable = new Table("select * from DANBWHGXB where zhubbh=? and zibbh=?"); 
				relationTable.getSqlParameter().put(1,new SqlParameter("gongnid","string",gongnID));
				relationTable.getSqlParameter().put(2,new SqlParameter("zibbh","string",relationForZibbhMap.get("zibbh").toString()));
				List relationList= DanbwhService.doSelect(relationTable);
				if(relationList==null)relationList = new ArrayList();
				RelationTable relation = new RelationTable();
				relation.setDanbwhId(relationForZibbhMap.get("zibbh").toString());
				relationTableMap.put(relationForZibbhMap.get("zibbh").toString(), relation);
				Map childrRlationField = new LinkedHashMap();
				Map childrRlationLinkedName = new LinkedHashMap();
				for(int t = 0 ; t<relationList.size(); t++)
				{
					Map relationMap = (Map) relationList.get(0);
					childrRlationField.put(relationMap.get("zhubzd").toString().toLowerCase(), relationMap.get("zibzd").toString().toLowerCase());
					isHrefLinked.put(relationMap.get("zhubzd").toString().toLowerCase(), relationMap.get("shifljzd"));
					chiledTableForfield.put(relationMap.get("zhubzd").toString().toLowerCase(), relationForZibbhMap.get("zibbh").toString());
					childrRlationLinkedName.put(relationForZibbhMap.get("zibbh").toString(),relationMap.get("zhibwhmc").toString());
				}
				relation.setChildrRlationField(childrRlationField);
				relation.setChildrRlationLinkedName(childrRlationLinkedName);
			}
			table.setRelationTableMap(relationTableMap);
			table.setHrefLinked(isHrefLinked);
			table.setChiledTableForfield(chiledTableForfield);
		}
		
		if(CasheMap.size()<=15)
		{
			CasheMap.put(gongnID, table);
		}else{
			CasheMap.clear();
		}
		return table;
	}
	
	private String[] getStrings(String str,String spilt){
			return str.split("\\"+spilt);
	}
}