package com.unitop.sysmgr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sinodata.table.model.SqlParameter;
import com.sinodata.table.model.Table;
import com.unitop.sysmgr.dao.AutoCompleteDao;
import com.unitop.sysmgr.form.CustomForm;
import com.unitop.sysmgr.service.DanbwhService;


@Controller("/r_resultMgr")
public class Report_ResultElementMgrAction extends ExDispatchAction {
	
	@Resource
	private DanbwhService DanbwhService;
	
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			Map<String, String> pmap = ((CustomForm) actionForm).getMap();
			String baobbs = request.getParameter("baobbs");
			Table table = new Table("select baobbs,yaosbs,yaosmc,yaosbt,yaoscd,xianslx,shifxs,xianssx,zhidbs,yaosgs,beiz from r_jiegyspz where baobbs=? order by (xianssx)"); 
			table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
			List list = DanbwhService.doSelect(table);
			
			request.setAttribute("list", list);
			request.setAttribute("baobbs", baobbs);
			return actionMapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}
	}
	
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setAttribute("action", "add");
			String baobbs = request.getParameter("baobbs");
			request.setAttribute("baobbs", baobbs);
			((CustomForm) actionForm).getMap().clear();
			((CustomForm) actionForm).getMap().put("baobbs", baobbs);
			((CustomForm) actionForm).setFormvalue("BAOBBS", baobbs);
			//获取数据源
			Map dataMaop = getUreportList(baobbs);
			request.setAttribute("SHUJHQFS", dataMaop.get("shujhqfs"));
			return actionMapping.findForward("add");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"list");
		}
	}

	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setAttribute("action", "upd");
			String baobbs = request.getParameter("baobbs");
			String yaosbs = request.getParameter("yaosbs");
			CustomForm f = (CustomForm)actionForm;
			
			Table table = new Table("select baobbs,yaosbs,yaosmc,yaosbt,yaoscd,xianslx,shifxs,xianssx,zhidbs ,beiz  from r_jiegyspz where baobbs=? and yaosbs =?"); 
			table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
			table.getSqlParameter().put(2,new SqlParameter("yaosbs","string",yaosbs));
			List list = DanbwhService.doSelect(table);
			if (list != null && list.size() >= 1) 
			{
				f .setMap((Map<String, String>) list.get(0));
			}
			request.setAttribute("baobbs", baobbs);
			request.setAttribute("yaosbs", yaosbs);
			//获取数据源
			Map dataMaop = getUreportList(baobbs);
			request.setAttribute("SHUJHQFS", dataMaop.get("shujhqfs"));
			return actionMapping.findForward("update");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}
	}

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String baobbs = request.getParameter("baobbs");
			String yaosbs = request.getParameter("yaosbs");
			Table table = new Table("delete from r_jiegyspz where baobbs =? and yaosbs=?");
			table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
			table.getSqlParameter().put(2,new SqlParameter("yaosbs","string",yaosbs));
			DanbwhService.doDML(table);
			return actionMapping.findForward("list");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}
	}

	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CustomForm f = (CustomForm)actionForm;
			f.getMap().put("yaosmc",request.getParameter("map(yaosmc)"));
			f.getMap().put("yaosbt",request.getParameter("map(yaosbt)"));
			f.getMap().put("yaoscd",request.getParameter("map(yaoscd)"));
			f.getMap().put("xianslx",request.getParameter("map(xianslx)"));
			f.getMap().put("shifxs",request.getParameter("map(shifxs)"));
			f.getMap().put("xianssx",request.getParameter("map(xianssx)"));
			f.getMap().put("zhidbs",request.getParameter("map(zhidbs)"));
			f.getMap().put("yaosgs",request.getParameter("map(yaosgs)"));
			f.getMap().put("beiz",request.getParameter("map(beiz)"));
			f.getMap().put("baobbs",request.getParameter("map(baobbs)"));
			f.getMap().put("yaosbs",request.getParameter("map(yaosbs)"));
			Map<String, String> pmap = f.getMap();
			String action = request.getParameter("action");
			if (action == null)
				action = "";
			if (action.toLowerCase().equals("upd")) {
				Table table = new Table("update r_jiegyspz set yaosmc=?,yaosbt=?,yaoscd=?,xianslx=?,shifxs=?,xianssx=?,zhidbs=?,yaosgs=?,beiz=? where baobbs=? and yaosbs=?"); 
				table.getSqlParameter().put(1,new SqlParameter("yaosmc","string",pmap.get("yaosmc")));
				table.getSqlParameter().put(2,new SqlParameter("yaosbt","string",pmap.get("yaosbt")));
				table.getSqlParameter().put(3,new SqlParameter("yaoscd","string",pmap.get("yaoscd")));
				table.getSqlParameter().put(4,new SqlParameter("xianslx","string",pmap.get("xianslx")));
				table.getSqlParameter().put(5,new SqlParameter("shifxs","string",pmap.get("shifxs")));
				table.getSqlParameter().put(6,new SqlParameter("xianssx","string",pmap.get("xianssx")));
				table.getSqlParameter().put(7,new SqlParameter("zhidbs","string",pmap.get("zhidbs")));
				table.getSqlParameter().put(8,new SqlParameter("yaosgs","string",pmap.get("yaosgs")));
				table.getSqlParameter().put(9,new SqlParameter("beiz","string",pmap.get("beiz")));
				table.getSqlParameter().put(10,new SqlParameter("baobbs","string",pmap.get("baobbs")));
				table.getSqlParameter().put(11,new SqlParameter("yaosbs","string",pmap.get("yaosbs")));
				DanbwhService.doDML(table);
			} else {
				int n = DanbwhService.getR_Result(pmap.get("baobbs"), pmap.get("yaosbs"));
				if(n>0){
					return this.showMessageJSP(actionMapping,request,"list","该记录已经存在!");
				}
				Table table = new Table("insert into r_jiegyspz(baobbs,yaosbs, yaosmc,yaosbt,yaoscd,xianslx,shifxs,xianssx,zhidbs,yaosgs,beiz) values(?,?,?,?,?,?,?,?,?,?,?)"); 
				table.getSqlParameter().put(1,new SqlParameter("baobbs","string",pmap.get("baobbs")));
				table.getSqlParameter().put(2,new SqlParameter("yaosbs","string",pmap.get("yaosbs")));
				table.getSqlParameter().put(3,new SqlParameter("yaosmc","string",pmap.get("yaosmc")));
				table.getSqlParameter().put(4,new SqlParameter("yaosbt","string",pmap.get("yaosbt")));
				table.getSqlParameter().put(5,new SqlParameter("yaoscd","string",pmap.get("yaoscd")));
				table.getSqlParameter().put(6,new SqlParameter("xianslx","string",pmap.get("xianslx")));
				table.getSqlParameter().put(7,new SqlParameter("shifxs","string",pmap.get("shifxs")));
				table.getSqlParameter().put(8,new SqlParameter("xianssx","string",pmap.get("xianssx")));
				table.getSqlParameter().put(9,new SqlParameter("zhidbs","string",pmap.get("zhidbs")));
				table.getSqlParameter().put(10,new SqlParameter("yaosgs","string",pmap.get("yaosgs")));
				table.getSqlParameter().put(11,new SqlParameter("beiz","string",pmap.get("beiz")));
				DanbwhService.doDML(table);
			}
			request.setAttribute("action", "close");
			return actionMapping.findForward("update");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "list");
		}finally{
			((CustomForm) actionForm).setMap(new HashMap<String, String>());
		}
	}
	
	//获取报表信息
	public Map getUreportList(String baobbs) throws Exception {
		Table table = new Table("select baobbs, baobmc, baobbt, shifky, shiffy, fenytj, shifdy, shifsc, shujhqfs from r_baobpz  where baobbs=? and shifky ='是'");
		table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
		List rlist1 = DanbwhService.doSelect(table);
		if (rlist1 == null)
		{
			rlist1 = new ArrayList();
		}
		return (Map) rlist1.get(0);
	}
	//设置缺省结果要素配置
	public ActionForward setDeFaultResult(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,
	HttpServletResponse response){

		String baobbs = request.getParameter("baobbs");
		try {
		Map dataMaop = getUreportList(baobbs);
		String tableName = (String) dataMaop.get("shujhqfs");
		ServletContext servletContext = request.getSession().getServletContext(); ;
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		AutoCompleteDao autoCompleteDao = (AutoCompleteDao) wac.getBean("AutoCompleteDaoImpl");
		Map lineMap = autoCompleteDao.getTableLineMap("select * from "+tableName);
		Iterator it = lineMap.keySet().iterator();
		int i = 0;
		while(it.hasNext()){
			i++;
			String str = (String)it.next();
			Table table = new Table("insert into r_jiegyspz(baobbs,yaosbs,yaosmc,yaosbt,yaoscd,xianslx,shifxs,xianssx,zhidbs,yaosgs,beiz) values(?,?,?,?,?,?,?,?,?,?,?)"); 
			table.getSqlParameter().put(1,new SqlParameter("baobbs","string",baobbs));
			table.getSqlParameter().put(2,new SqlParameter("yaosbs","string",str));
			table.getSqlParameter().put(3,new SqlParameter("yaosmc","string",str));
			table.getSqlParameter().put(4,new SqlParameter("yaosbt","string",str));
			table.getSqlParameter().put(5,new SqlParameter("yaoscd","string",""));
			table.getSqlParameter().put(6,new SqlParameter("xianslx","string","普通显示"));
			table.getSqlParameter().put(7,new SqlParameter("shifxs","string","是"));
			table.getSqlParameter().put(8,new SqlParameter("xianssx","string",""+i));
			table.getSqlParameter().put(9,new SqlParameter("zhidbs","string",""));
			table.getSqlParameter().put(10,new SqlParameter("yaosgs","string","string"));
			table.getSqlParameter().put(11,new SqlParameter("beiz","string",""));
			try{
				DanbwhService.doDML(table);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("action", "close");
		return actionMapping.findForward("update");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "list");
		}
		} 
}
