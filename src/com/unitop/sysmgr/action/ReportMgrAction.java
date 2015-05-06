package com.unitop.sysmgr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.sinopec.table.model.SqlParameter;
import com.sinopec.table.model.Table;
import com.unitop.framework.util.MapUtil;
import com.unitop.sysmgr.form.CustomForm;
import com.unitop.sysmgr.service.impl.QueryServiceImpl;

@Controller("/ureportMgr")
public class ReportMgrAction extends ExDispatchAction {

	/*
	 * 查询报表列表
	 */
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List list = this.getUreportList();
			request.setAttribute("list", list);
			return actionMapping.findForward("listUreport");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"listUreport");
		}
	}

	/*
	 * 获取修改报表记录
	 */
	public ActionForward get(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CustomForm b = (CustomForm) actionForm;
			String baobbs = request.getParameter("baobbs");
			Map map = this.getUreportList(baobbs);
			String fenytj=map.get("FENYTJ").toString();
			int fenytjIndex = fenytj.indexOf(".");
			if(fenytjIndex!=-1){
				fenytj=fenytj.substring(0, fenytj.indexOf("."));
			}
			map.put("FENYTJ", Integer.parseInt(fenytj));
			b.setMap(map);
			return actionMapping.findForward("updateUreport");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"listUreport");
		}
	}

	/*
	 * 跳转 到 报表新增记录界面
	 */
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CustomForm f = (CustomForm) actionForm;
			f.setMap(new HashMap());
			return actionMapping.findForward("addUreport");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"listUreport");
		}
	}

	/*
	 * 新增 报表记录
	 */
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CustomForm f = (CustomForm) actionForm;
			Map pmap = MapUtil.MaptoLowerCase(f.getMap());
			this.addReport(pmap);
			List list = this.getUreportList();
			request.setAttribute("list", list);
			return actionMapping.findForward("listUreport");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"listUreport");
		}
	}

	/*
	 * 删除报表记录 报表记录
	 */
	public ActionForward del(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String baobbs = request.getParameter("baobbs");
			Map map = new HashMap();
			map.put("baobbs", baobbs);
			this.deleteReport(map);
			List list = this.getUreportList();
			request.setAttribute("list", list);
			return actionMapping.findForward("deleteUreport");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"listUreport");
		}
	}

	/*
	 * 修改报表记录
	 */
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CustomForm f = (CustomForm) actionForm;
			//转换小写
			this.updateReport(MapUtil.MaptoLowerCase(f.getMap()));
			List list = this.getUreportList();
			request.setAttribute("list", list);
			return actionMapping.findForward("listUreport");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,"listUreport");
		}
	}

	public Map getUreportList(String baobbs) {
		String sql="select baobbs, baobmc, baobbt, shifky, shiffy, fenytj, shifdy, shifsc, shujhqfs,zhidyl from r_baobpz  where baobbs=? and shifky ='是'";
		Map pmap = new HashMap();
		pmap.put(1, new SqlParameter("baobbs","string",baobbs));
		return (Map) this.getQueryService().execDBForDQL(sql,pmap).get(0);
	}
	
	public List getUreportList() {
		String sql = "select baobbs, baobmc, baobbt, shifky ,zhidyl from r_baobpz t";
		return this.getQueryService().execDBForDQL(sql,new HashMap<String, String>());
	}

	public void addReport(Map map) {
		try {
			String sql = "INSERT INTO r_baobpz (baobbs, baobmc, baobbt, shifky, shiffy, fenytj, shifdy, shifsc, shujhqfs, zhidyl) values (?,?,?,?,?,?,?,?,?,?)";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("baobbs","string",map.get("baobbs").toString()));
			pmap.put(2, new SqlParameter("baobmc","string",map.get("baobmc").toString()));
			pmap.put(3, new SqlParameter("baobbt","string",map.get("baobbt").toString()));
			pmap.put(4, new SqlParameter("shifky","string",map.get("shifky").toString()));
			pmap.put(5, new SqlParameter("shiffy","string",""));
			pmap.put(6, new SqlParameter("fenytj","string",map.get("fenytj").toString()));
			pmap.put(7, new SqlParameter("shifdy","string",""));
			pmap.put(8, new SqlParameter("shifsc","string",""));
			pmap.put(9, new SqlParameter("shujhqfs","string",map.get("shujhqfs").toString()));
			pmap.put(10, new SqlParameter("zhidyl","string",map.get("zhidyl").toString())); 
			this.getQueryService().execDBForDML(sql, pmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateReport(Map map) {
		try {
			String sql = "update r_baobpz set baobmc=?,baobbt=?,shifky=?,shiffy=?,fenytj=?,shifdy=?,shifsc=?,shujhqfs=?,zhidyl=? where baobbs=?";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("baobmc","string",map.get("baobmc").toString()));
			pmap.put(2, new SqlParameter("baobbt","string",map.get("baobbt").toString()));
			pmap.put(3, new SqlParameter("shifky","string",map.get("shifky").toString()));
			pmap.put(4, new SqlParameter("shiffy","string",""));
			pmap.put(5, new SqlParameter("fenytj","string",map.get("fenytj").toString()));
			pmap.put(6, new SqlParameter("shifdy","string",""));
			pmap.put(7, new SqlParameter("shifsc","string",""));
			pmap.put(8, new SqlParameter("shujhqfs","string",map.get("shujhqfs").toString()));
			pmap.put(9, new SqlParameter("zhidyl","string",map.get("zhidyl").toString()));
			pmap.put(10, new SqlParameter("baobbs","string",map.get("baobbs").toString()));
			
			this.getQueryService().execDBForDML(sql, pmap);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void deleteReport(Map map) {
			String zhangh = map.get("baobbs").toString();
		try {
			QueryServiceImpl queryServiceImpl = (QueryServiceImpl) this.getQueryService();
			List tableList = new ArrayList();
			//删除报表配置信息
			String r_baobpzSQL = "delete from r_baobpz where baobbs =?"; 
			Table r_baobpzTable = new Table(r_baobpzSQL);
			r_baobpzTable.getSqlParameter().put(1,new SqlParameter("zhangh","string",zhangh));
			tableList.add(r_baobpzTable);
			//删除表单要素配置信息
			String R_BIAODYSPZSQL = "delete  R_BIAODYSPZ where baobbs=?"; 
			Table R_BIAODYSPZSQLTable = new Table(R_BIAODYSPZSQL);
			R_BIAODYSPZSQLTable.getSqlParameter().put(1,new SqlParameter("zhangh","string",zhangh));
			tableList.add(R_BIAODYSPZSQLTable);
			//删除结果要素配置信息
			String R_JIEGYSPZSQL = "delete  R_JIEGYSPZ where baobbs=?"; 
			Table R_JIEGYSPZSQLTable = new Table(R_JIEGYSPZSQL);
			R_JIEGYSPZSQLTable.getSqlParameter().put(1,new SqlParameter("zhangh","string",zhangh));
			tableList.add(R_JIEGYSPZSQLTable);
			//执行
			queryServiceImpl.DanbwhDao.doSWDML(tableList);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}