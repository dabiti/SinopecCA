package com.unitop.sysmgr.action;

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
import com.unitop.sysmgr.form.CustomForm;

/*
 * UREPORT 表单维护
 */
@Controller("/r_formMgr")
public class Report_FormMgrAction extends ExDispatchAction {

	public List getFormList(String baobbs) {
		List rlist = null;
		try {
			String sql = "select baobbs, yaosbs, yaosmc, yaosbt, yaoslx, shifbt, yaoscd, moyz, xianssx,zhidbs,yaoshw,beiz from r_biaodyspz where baobbs=? order by yaoshw , xianssx";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("baobbs","string",baobbs));
			rlist =this.getQueryService().execDBForDQL(sql, pmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rlist;
	}

	public Map findResult(String baobbs, String yaosbs) {
		List rlist = null;
		try {
			String sql ="select baobbs, yaosbs, yaosmc, yaosbt, yaoslx, shifbt, yaoscd, moyz, xianssx,zhidbs,beiz from r_biaodyspz where baobbs=? and yaosbs=?";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("baobbs","string",baobbs));
			pmap.put(2, new SqlParameter("yaosbs","string",yaosbs));
			rlist =this.getQueryService().execDBForDQL(sql, pmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Map)rlist.get(0);
	}

	public void addResult(Map<String, String> pmap) {
		try {
			System.out.println(""+pmap);
			String sql ="INSERT INTO r_biaodyspz (baobbs, yaosbs, yaosmc, yaosbt, yaoslx, shifbt, yaoscd, moyz, xianssx,zhidbs,beiz,yaosgs) values" +
					" (?,?,?,?,?,?,?,?,?,?,?,?)";
			Map pmaps = new HashMap();
			pmaps.put(1, new SqlParameter("baobbs","string",pmap.get("baobbs".toUpperCase()).toString()));
			pmaps.put(2, new SqlParameter("yaosbs","string",pmap.get("yaosbs".toUpperCase()).toString()));
			pmaps.put(3, new SqlParameter("yaosmc","string",pmap.get("yaosmc".toUpperCase()).toString()));
			pmaps.put(4, new SqlParameter("yaosbt","string",pmap.get("yaosbt".toUpperCase()).toString()));
			pmaps.put(5, new SqlParameter("yaoslx","string",pmap.get("yaoslx".toUpperCase()).toString()));
			pmaps.put(6, new SqlParameter("shifbt","string",pmap.get("shifbt".toUpperCase()).toString()));
			pmaps.put(7, new SqlParameter("yaoscd","string",""));
			pmaps.put(8, new SqlParameter("moyz","string",pmap.get("moyz".toUpperCase()).toString()));
			pmaps.put(9, new SqlParameter("xianssx","string",pmap.get("xianssx".toUpperCase()).toString()));
			pmaps.put(10, new SqlParameter("zhidbs","string",""));
			pmaps.put(11, new SqlParameter("beiz","string",pmap.get("beiz".toUpperCase()).toString()));
			pmaps.put(12, new SqlParameter("yaosgs","string",pmap.get("yaosgs".toUpperCase()).toString()));
			this.getQueryService().execDBForDML(sql, pmaps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteResult(Map<String, String> pmap) {
		try {
			String sql ="delete from r_biaodyspz where baobbs=? and yaosbs=?";
			Map pmaps = new HashMap();
			pmaps.put(1, new SqlParameter("baobbs","string",pmap.get("BAOBBS").toString()));
			pmaps.put(2, new SqlParameter("yaosbs","string",pmap.get("YAOSBS").toString()));
			this.getQueryService().execDBForDML(sql, pmaps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateResult(Map<String, String> pmap) {
		try {

			String sql ="update r_biaodyspz set yaosmc=?, yaosbt=?, yaoslx=?, shifbt=?, yaoscd=?, moyz=?, xianssx=?,zhidbs=?,beiz=?,yaosgs=? where baobbs=? and yaosbs=?";
			Map pmaps = new HashMap();
			pmaps.put(1, new SqlParameter("yaosmc","string",pmap.get("yaosmc").toString()));
			pmaps.put(2, new SqlParameter("yaosbt","string",pmap.get("yaosbt").toString()));
			pmaps.put(3, new SqlParameter("yaoslx","string",pmap.get("yaoslx").toString()));
			pmaps.put(4, new SqlParameter("shifbt","string",pmap.get("shifbt").toString()));
			pmaps.put(5, new SqlParameter("yaoscd","string",""));
			pmaps.put(6, new SqlParameter("moyz","string",pmap.get("moyz").toString()));
			pmaps.put(7, new SqlParameter("xianssx","string",pmap.get("xianssx").toString()));
			pmaps.put(8, new SqlParameter("zhidbs","string",""));
			pmaps.put(9, new SqlParameter("beiz","string",""));
			pmaps.put(10, new SqlParameter("yaosgs","string",pmap.get("yaosgs").toString()));
			pmaps.put(11, new SqlParameter("baobbs","string",pmap.get("baobbs").toString()));
			pmaps.put(12, new SqlParameter("yaosbs","string",pmap.get("yaosbs").toString()));
			this.getQueryService().execDBForDML(sql, pmaps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ActionForward listForm(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String baobbs = (String) request.getParameter("baobbs");			
			List list = this.getFormList(baobbs);
			request.setAttribute("list", list);
			request.setAttribute("baobbs", baobbs);
			return actionMapping.findForward("listForm");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"listForm");
		}
	}

	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CustomForm result = (CustomForm)actionForm;
			String baobbs = request.getParameter("baobbs");
			result.setFormvalue("BAOBBS", baobbs);
			//获取数据源
			Map dataMaop = getUreportList(baobbs);
			request.setAttribute("SHUJHQFS", dataMaop.get("SHUJHQFS"));
			return actionMapping.findForward("addForm");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "listForm");
		}
	}
	
	public ActionForward addForm(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			//静态参数
			String[] shurlxName = request.getParameterValues("shurlxName");
			String[] shurlxValue = request.getParameterValues("shurlxValue");
			//动态参数
			String shujy = request.getParameter("shujy");
			String beiz="";
			if(shurlxName!=null)
			{
				for(int i =0 ; i < shurlxName.length ; i++)
				{
					beiz+=shurlxName[i] +"="+ shurlxValue[i]+"|";
				}
			}
			if(shujy!=null){
				beiz = shujy;
			}
		try {
			String baobbs = request.getParameter("baobbs");
			CustomForm f = new CustomForm();
			f = (CustomForm)actionForm;
			f.getMap().put("BEIZ", beiz);
			this.addResult(f.getMap());
			List list = this.getFormList(baobbs);
			request.setAttribute("list", list);
			return actionMapping.findForward("listForm");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "listForm");
		}
	}

	public ActionForward deleteForm(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String baobbs = request.getParameter("baobbs");
			String yaosbs = request.getParameter("yaosbs");
			Map<String, String> pmap = new HashMap<String, String>();
			pmap = findResult(baobbs,yaosbs);
			deleteResult(pmap);
			List list = this.getFormList(baobbs);
			request.setAttribute("list", list);
			return actionMapping.findForward("deleteForm");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "listForm");
		}
	}
	public ActionForward getForm(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String baobbs = request.getParameter("baobbs");
			String yaosbs = request.getParameter("yaosbs");
			CustomForm f = (CustomForm)actionForm;
			f.setMap(this.findResult(baobbs, yaosbs));
			//获取数据源
			Map dataMaop = getUreportList(baobbs);
			request.setAttribute("SHUJHQFS", dataMaop.get("SHUJHQFS"));
			return actionMapping.findForward("updateForm");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"listForm");
		}
	}

	public ActionForward updateForm(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			//静态参数
			String[] shurlxName = request.getParameterValues("shurlxName");
			String[] shurlxValue = request.getParameterValues("shurlxValue");
			//动态参数
			String shujy = request.getParameter("beiz");
			String beiz="";
			if(shurlxName!=null)
			{
				for(int i =0 ; i < shurlxName.length ; i++)
				{
					beiz+=shurlxName[i] +"="+ shurlxValue[i]+"|";
				}
			}
			if(shujy!=null){
				beiz = shujy;
			}
		try {
			CustomForm f = (CustomForm)actionForm;
			f.getMap().put("baobbs",request.getParameter("map(BAOBBS)"));
			f.getMap().put("yaosbs",request.getParameter("map(YAOSBS)"));
			f.getMap().put("yaosmc",request.getParameter("map(YAOSMC)"));
			f.getMap().put("yaosbt",request.getParameter("map(YAOSBT)"));
			f.getMap().put("yaoslx",request.getParameter("map(YAOSLX)"));
			f.getMap().put("shifbt",request.getParameter("map(SHIFBT)"));
			f.getMap().put("shifbt",request.getParameter("map(SHIFBT)"));
			f.getMap().put("moyz",request.getParameter("map(MOYZ)"));
			f.getMap().put("xianssx",request.getParameter("map(XIANSSX)"));
			f.getMap().put("yaosgs",request.getParameter("map(YAOSGS)"));
			String baobbs =request.getParameter("baobbs");
			this.updateResult(f.getMap());
			List list = this.getFormList(baobbs);
			request.setAttribute("list", list);
			return actionMapping.findForward("listForm");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "listForm");
		}
	}
	
	//获取报表信息
	public Map getUreportList(String baobbs) {
		List rlist = null;
		try {
			String sql = "select baobbs, baobmc, baobbt, shifky, shiffy, fenytj, shifdy, shifsc, shujhqfs from r_baobpz  where baobbs=? and shifky ='是'";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("baobbs","string",baobbs));
			rlist =this.getQueryService().execDBForDQL(sql, pmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Map)rlist.get(0);
	}
}
