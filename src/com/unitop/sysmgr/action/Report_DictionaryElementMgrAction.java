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

import com.sinodata.table.model.SqlParameter;
import com.unitop.sysmgr.form.CustomForm;
/*
 *URPORT ×ÖµäÅäÖÃ
 */
@Controller("/dictionaryMgr")
public class Report_DictionaryElementMgrAction extends ExDispatchAction {

	public ActionForward listDictionary(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhidbs = request.getParameter("zhidbs");
			((CustomForm) actionForm).setFormvalue("ZHIDBS", zhidbs);
			List list = this.getDictionaryList(zhidbs);
			request.setAttribute("list", list);
			request.setAttribute("zhidbs", zhidbs);
			return actionMapping.findForward("listDictionary");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"listDictionary");
		}
	}

	public ActionForward addDictionary(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhidbs = request.getParameter("zhidbs");
			((CustomForm) actionForm).setFormvalue("ZHIDBS", zhidbs);
			request.setAttribute("zhidbs", zhidbs);
			return actionMapping.findForward("addDictionary");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"listDictionary");
		}
	}

	public ActionForward saveDictionary(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhidbs = request.getParameter("zhidbs");
			CustomForm dictionary  = (CustomForm) actionForm;
			String sql="INSERT INTO r_zhidpz(zhidbs,suoyz,zhuanhz) values(?,?,?)";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("zhidbs","string",dictionary.getMap().get("zhidbs").toString()));
			pmap.put(2, new SqlParameter("suoyz","string",dictionary.getMap().get("suoyz").toString()));
			pmap.put(3, new SqlParameter("zhuanhz","string",dictionary.getMap().get("zhuanhz").toString()));
			this.getQueryService().execDBForDML(sql, pmap);
			List list = this.getDictionaryList(zhidbs);
			request.setAttribute("list", list);
			request.setAttribute("zhidbs", zhidbs);
			return actionMapping.findForward("listDictionary");
		} catch (Exception e) {
			return this.errrForLogAndException(e,actionMapping,request,"listDictionary");
		}
	}

	public ActionForward deleteDictionary(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhidbs = request.getParameter("zhidbs");
			String suoyz = request.getParameter("suoyz");
			String sql = "delete from r_zhidpz where zhidbs=? and suoyz=?";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("zhidbs","string",zhidbs));
			pmap.put(2, new SqlParameter("suoyz","string",suoyz));
			this.getQueryService().execDBForDML(sql, pmap);
			List list = this.getDictionaryList(zhidbs);
			request.setAttribute("list", list);
			request.setAttribute("zhidbs", zhidbs);
			return actionMapping.findForward("deleteDictionary");
		} catch (Exception e) {
			return this.errrForLogAndException(e,actionMapping,request,"listDictionary");
		}
	}

	public ActionForward getDictionary(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhidbs = request.getParameter("zhidbs");
			String suoyz = request.getParameter("suoyz");
			String sql ="select zhidbs,suoyz,zhuanhz from r_zhidpz where zhidbs=? and suoyz=?";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("zhidbs","string",zhidbs));
			pmap.put(2, new SqlParameter("suoyz","string",suoyz));
			List list =this.getQueryService().execDBForDQL(sql, pmap);
			if (list != null && list.size() >= 1){
				((CustomForm) actionForm).setMap((Map<String, String>) list.get(0));
			}
			request.setAttribute("zhidbs", zhidbs);
			return actionMapping.findForward("updateDictionary");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"listForm");
		}
	}

	public ActionForward updateDictionary(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CustomForm dictionary = new CustomForm();
			dictionary = (CustomForm) actionForm;
			String zhidbs = dictionary.getFormvalue("ZHIDBS");
			String sql = "update r_zhidpz set zhuanhz=? where zhidbs=? and suoyz=?";
			Map pmap = new HashMap();
			pmap.put(1, new SqlParameter("zhuanhz","string",dictionary.getMap().get("zhuanhz").toString()));
			pmap.put(2, new SqlParameter("zhidbs","string",dictionary.getMap().get("zhidbs").toString()));
			pmap.put(3, new SqlParameter("suoyz","string",dictionary.getMap().get("suoyz").toString()));
			this.getQueryService().execDBForDML(sql, pmap);
			List list = this.getDictionaryList(zhidbs);
			request.setAttribute("list", list);
			return actionMapping.findForward("listDictionary");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "listDictionary");
		}
	}

	public List getDictionaryList(String zhidbs) {
		String sql = "select zhidbs,suoyz,zhuanhz from r_zhidpz where zhidbs=?";
		Map pmap = new HashMap();
		pmap.put(1, new SqlParameter("zhidbs","string",zhidbs));
		List list =this.getQueryService().execDBForDQL(sql, pmap);
		return list;
	}
}