package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.framework.util.StringUtil;
import com.unitop.sysmgr.bo.Chout;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.KagRenw;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.KagRenwForm;
import com.unitop.sysmgr.service.KagService;
import com.unitop.sysmgr.service.SystemMgrService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.KagServiceImpl;

@Controller("/handleKagRenw")
public class KagRenwAction extends ExDispatchAction {
	@Resource
	private KagService kagService;
	@Resource
	private ZhanghbService zhanghbService;
	@Resource
	private SystemMgrService systemMgrService;

	// ��תʵ���ȡ��
	public ActionForward toPutAndGetCard(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		KagRenwForm kagRenwForm = (KagRenwForm) actionForm;
		kagRenwForm.clear();
		return actionMapping.findForward("toputandgetcard");
	}

	// �򿪿���
	public ActionForward openKag(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		KagRenwForm kagRenwForm = (KagRenwForm) actionForm;
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String jigh = clerk.getOrgcode();
		String yinjks = request.getParameter("yinjks");
		String renwbs = request.getParameter("renwbs");
		String zuob = request.getParameter("zuob").trim();
		
		try {
			// �����˻�ӡ�������ڿ�����λ�ã�����Ҫ����ռ䡣
			Chout chout = kagService.divideSpace(renwbs, jigh, Integer.parseInt(yinjks), zuob);// ��������Ҫ�ڴ򿪿���ķ���ռ���б������ֹͬһ�ռ䱻�����˷���
			
			//ӡ���������ֶ� KAGID��CENG��CHOUTWZ�����ݵĲ���
			KagRenw kagRenw = kagService.getYinjkh(renwbs);
			Yinjk yinjk =kagService.getYinjk(kagRenw.getZhangh(),kagRenw.getYinjkh(),kagRenw.getQiyrq());
			yinjk.setKagid(chout.getChoutid().getKagid());
			yinjk.setCeng(chout.getChoutid().getCeng());
			yinjk.setChoutwz(chout.getChoutid().getChoutwz());
			kagService.saveyinjk(yinjk);
			
			String space = chout.getChoutid().getKagid() + ","
					+ chout.getChoutid().getCeng() + ","
					+ chout.getChoutid().getChoutwz();
			if ("1".equals(chout.getChoutzt())) 
			{// �������״̬Ϊ1��˵���ó�������ʹ�ã�֪ͨҳ��
				space = "1";
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(space);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
		return null;
	}

	// �رտ����ó���״̬Ϊ0������
	public ActionForward closeKag(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String jigh = clerk.getOrgcode();
		String yinjks = request.getParameter("yinjks");
		String renwbs = request.getParameter("renwbs");
		String zuob = request.getParameter("zuob").trim();
		String flag = "";
		try {
			try {
				kagService.closeKag(renwbs, "1", zuob, "0");// ��������ͳ����״̬
			} catch (Exception e) {
				e.printStackTrace();
				flag = "fail";
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(flag);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
		return null;
	}

	public ActionForward searchTask(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		KagRenwForm kagRenwForm = (KagRenwForm) actionForm;
		String zhangh = kagRenwForm.getZhangh();
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			TabsBo TabsBo = this.createTabsBo(request);
			KagServiceImpl kagServiceImpl = (KagServiceImpl) kagService;
			kagServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo = kagService.getTask(zhangh, clerk.getOrgcode());
			this.showTabsModel(request, tabsBo);
			return super.showMessageJSPForFeny(actionMapping,request,tabsBo,"toputandgetcard");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "toputandgetcard");
		}
	}

	public ActionForward toGetActLog(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("toGetActLog");
		return actionMapping.findForward("toGetActLog");
	}

	/*
	 * ������������
	 */
	public ActionForward createKagRenw(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String zhangh = request.getParameter("zhangh");
			String ceng = request.getParameter("ceng");
			String choutwz = request.getParameter("choutwz");
			String kagid = request.getParameter("kagid");
			String yewlx = request.getParameter("yewlx");
			String qiyrq = request.getParameter("qiyrq");
			//20130520 ���ӡ�������Ϊӡ����������֮һ���򿪹���ʱ��ȡ����ӡ�������
			String yinjkh = request.getParameter("yinjkh"); 

			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			Zhanghb zhanghb = zhanghbService.getZhanghb(zhangh);
			String shijian = systemMgrService.getSystetemNowDate();
			String renwrq = shijian.substring(0, 10);
			String renwsj = shijian.substring(shijian.length() - 8, shijian
					.length());

			KagRenw kagrenw = new KagRenw();
			kagrenw.setZhangh(zhangh);
			kagrenw.setQiyrq(qiyrq);
			kagrenw.setYinjkh(yinjkh);
			kagrenw.setHum(zhanghb.getHum());
			kagrenw.setJigh(zhanghb.getJigh());
			if ("2".equals(yewlx)) {
				// ���yewlxΪ�������룬��Ϊ�ſ�����
				kagrenw.setRenwlx("1");
			} else if ("3".equals(yewlx)) {
				kagrenw.setRenwlx("0");
			}
			kagrenw.setYewlx(yewlx);
			kagrenw.setRenwzt("0");
			String zuob = kagid + "," + ceng + "," + choutwz;
			kagrenw.setZuob(zuob);
			kagrenw.setRenwrq(renwrq);
			kagrenw.setRenwsj(renwsj);
			String renwbs = StringUtil.getRandomStr(10);
			kagrenw.setRenwbs(renwbs);
			String flag = "";
			try {
				kagService.createKagRenw(kagrenw);
				String shifzk = "";
				if ("2".equals(yewlx)) {
					shifzk = "0";// ����ǲ������룬�Ƿ��ڿ���Ϊ0��
				} else if ("3".equals(yewlx)) {
					shifzk = "1";// ����ǹ黹���룬�Ƿ��ڿ���Ϊ1��
				}
				String yinjkUpdateSql = "update yinjk set shifzk = '" + shifzk
						+ "' where zhangh='" + zhangh + "' and qiyrq='" + qiyrq
						+ "'";// ����ӡ�����Ƿ��ڿ�״̬where����
				kagService.updateShifzk(yinjkUpdateSql);
			} catch (Exception e) {
				flag = e.getMessage();
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(flag);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
		return null;
	}

	public ActionForward deleteTask(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String renwbs = request.getParameter("renwbs");
		String flag="";
		try {
			try {
				kagService.deleteTask(renwbs);
			} catch (Exception e) {
				flag=e.getMessage();
				throw new Exception(e);
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(flag);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
