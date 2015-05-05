package com.unitop.sysmgr.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.CanOperAccReturn;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.AccountinfoForm;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.ZhanghbService;
@Controller("/accountcustom")
public class AccountinfoCustomAction extends ExDispatchAction {

	@Resource
	private ZhanghbService ZhanghbService;
	@Resource
	private OrgService OrgService;

	public boolean CanOperDesOrg(String OperOrg, String DesOrg) {
		return getSystemMgrService().CanOperDesOrg(OperOrg, DesOrg);
	}

	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			List list = this.getQueryService().getCustomAccountinfoList(clerk.getCode());
			request.setAttribute("list", list);
			return actionMapping.findForward("list");
		} catch (Exception e) {
			request.setAttribute("list", new ArrayList());
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}

	}

	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List industrycharacterlist = getQueryService().getHuobh();
			request.setAttribute("industrycharacterlist",industrycharacterlist);
			return actionMapping.findForward("add");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}
	}

	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		AccountinfoForm accountinfoform = (AccountinfoForm) actionForm;
		try {
			Zhanghb zhanhb = ZhanghbService.getZhanghb(accountinfoform.getAccount());
			Org org = OrgService.getOrgByCode(accountinfoform.getNetpointflag());
			String TaskFlag = (String) request.getParameter("TaskFlag");
			String opendate1 = accountinfoform.getOpendate();
			String opendate2 = accountinfoform.getOpendate1();
			String startdate1 = accountinfoform.getStartdate();
			String startdate2 = accountinfoform.getStartdate1();
			List industrycharacterlist = getQueryService().getHuobh();
			request
					.setAttribute("industrycharacterlist",
							industrycharacterlist);
			if ((accountinfoform.getAccount() == null || ""
					.equals(accountinfoform.getAccount()))
					&& ("".equals(accountinfoform.getNetpointflag()) || accountinfoform
							.getNetpointflag() == null)) {
				return this.showMessageJSP(actionMapping, request, "add",
						"账号或机构代码必输其一");
			}
			if (accountinfoform.getAccount() == null
					|| "".equals(accountinfoform.getAccount())) {
				if (org == null) {
					return this.showMessageJSP(actionMapping, request, "add",
							"机构代码不存在！");
				}
			} else {
				if (zhanhb == null) {
					return this.showMessageJSP(actionMapping, request, "add",
							"账号或机构代码不存在！");
				} else if (accountinfoform.getNetpointflag() != null
						&& !"".equals(accountinfoform.getNetpointflag())
						&& org == null) {
					return this.showMessageJSP(actionMapping, request, "add",
							"机构代码不存在！");
				}
			}
			boolean b = CanOperDesOrg(clerk.getOrgcode(), accountinfoform
					.getNetpointflag());
			if (!b && org != null) {
				return this.showMessageJSP(actionMapping, request, "add",
						"输入无效,没有权限查询此机构!");
			}
			boolean canOperAcc = false;
			String canOperAccRetMsg = "";
			if (accountinfoform.getAccount() != null
					&& !accountinfoform.getAccount().equals("")) {
//				CanOperAccReturn coar = this.getSystemMgrService()
//						.CanOperDesAcc(clerk.getOrgcode(),
//								accountinfoform.getAccount(), zhanhb.getJigh());
				//新方法检查操作账户权限
				CanOperAccReturn coar = this.getSystemMgrService().ProCanOperAcc(clerk.getOrgcode(), accountinfoform.getAccount());
				canOperAcc = coar.getReturnValue();
				canOperAccRetMsg = coar.getReturnMessage();
			}
			if (!canOperAcc && zhanhb != null) {
				return this.showMessageJSP(actionMapping, request, "add",
						canOperAccRetMsg);
			}
			this.getQueryService().CreateCustomaQuery_AccountInfoForNet(TaskFlag, accountinfoform, clerk.getCode(), opendate1, opendate2,startdate1, startdate2);
			return actionMapping.findForward("save.ok");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
 			"add");
		}
	}

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			this.getQueryService().DeleteCustomaQuery(id, clerk.getCode());
			return actionMapping.findForward("delete.ok");
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "delete.ok","写数据库时发生错误!");
		}
	}

//	public ActionForward download(ActionMapping actionMapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) {
//		String id = request.getParameter("id");
//		Connection con = null;
//		Statement st = null;
//		ResultSet rs = null;
//		try {
//			con = DataSourceUtils.getConnection(DataSourceFactory.getDataSourceByPool());
//			con.setAutoCommit(false);
//			st = con.createStatement();
//			rs = st.executeQuery("select fileinfo from dzcxinfo where id='" + id + "'");
//			if (rs.next()) {
//				Blob blob = rs.getBlob(1);
//				InputStream ins = blob.getBinaryStream();
//				response.setContentType("application/unknown");
//				response.addHeader("Content-Disposition", "attachment; filename=" + id + ".xls");
//				OutputStream outStream = response.getOutputStream();
//				byte[] bytes = new byte[1024];
//				int len = 0;
//				while ((len = ins.read(bytes)) != -1)
//				{
//					outStream.write(bytes, 0, len);
//				}
//				ins.close();
//				outStream.close();
//				outStream = null;
//				st.execute("update dzcxinfo set xz_time =to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') where id ='" + id + "'");
//				con.commit();
//				con.setAutoCommit(true);
//			}
//			return null;
//		} catch (Exception e) {
//			return this.errrForLogAndException(e, actionMapping, request,
//					"download.error");
//		} finally {
//			if (st != null) 
//			{
//				try {
//					st.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (rs != null)
//			{
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (con != null)
//			{
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
