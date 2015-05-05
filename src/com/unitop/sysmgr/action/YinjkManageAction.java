package com.unitop.sysmgr.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.bo.YinjkManageLog;
import com.unitop.sysmgr.bo.YinjkNum;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.YinjkForm;
import com.unitop.sysmgr.service.KagService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.KagServiceImpl;

@Controller("/yinjkOperate")
public class YinjkManageAction extends ExDispatchAction {
	@Resource
	private ZhanghbService zhanghbService;

	@Resource
	private KagService kagService;

	public ZhanghbService getZhanghbService() {
		return zhanghbService;
	}

	public void setZhanghbService(ZhanghbService zhanghbService) {
		this.zhanghbService = zhanghbService;
	}

	// ��ת����ѯ�͹黹�������
	public ActionForward toApply(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("toApply");
		return actionMapping.findForward("toApply");
	}

	public ActionForward getYinjk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		YinjkForm yinjkForm = (YinjkForm) actionForm;
		try {
			TabsBo TabsBo = this.createTabsBo(request);
			KagServiceImpl kagServiceImpl = (KagServiceImpl) kagService;
			kagServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo = kagService.getYinjk(yinjkForm);
			this.showTabsModel(request, tabsBo);
			return super.showMessageJSPForFeny(actionMapping, request, tabsBo,
					"toApply");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request,
					"toputandgetcard");
		}
	}

	/**
	 * ��ת��ͳ��ӡ��������ҳ��
	 */
	public ActionForward forqueryyinjknum(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yform = (YinjkForm) actionForm;
			// ������ػ�����
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			// ��Ա����������
			yform.setNetpointflag(clerk.getOrgcode());
			return actionMapping.findForward("yinjknum.show.net");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjknum.show.net");
		}
	}

	/**
	 * ͳ�Ʋ�ѯӡ��������
	 */
	public ActionForward queryyinjknum(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			YinjkForm yform = (YinjkForm) actionForm;
			String orgnum = yform.getNetpointflag();
			String shifbhxj = yform.getRemark();
			Org org =OrgService.getOrgByCode(orgnum);
			if(org==null){
				return this.showMessageJSP(actionMapping, request,
						"yinjknum.show.net", "����������!");
			}
			long i = 0;
			if (this.getSystemMgrService().CanOperDesSpecialOrg(clerk.getOrgcode(),
					orgnum)) {
				i = yinjkService.yinjkNumber(orgnum, shifbhxj);
				request.setAttribute("YinjkNum", i);
			} else {
				return this.showMessageJSP(actionMapping, request,
						"yinjknum.show.net", "��Ȩ�޲鿴�û�����Ϣ!");
			}
			yform.setNetpointflag(yform.getNetpointflag());
			return actionMapping.findForward("yinjknum.show.net");
		} catch (Exception e) {
			request.setAttribute("YinjkNum", "��ѯʧ��");
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjknum.show.net");
		}

	}

	/**
	 *�������ҳ����ת
	 */
	public ActionForward saveFenhrkForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkFenhrk");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkFenhrk");
		}
	}

	/**
	 * �������
	 */
	public ActionForward saveFenhrk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			yinjk.setYinjkh(yinjkform.getYinjkh());
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkFenhrk", "���й�Ա���ɽ��д˲���");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != 0) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkFenhrk", "ӡ�����Ѿ����ڣ�");
			}
			yinjk.setYinjkzt("���п��");
			yinjk.setJigh(clerk.getOrgcode());
			int i = Integer.parseInt(num);

			String content = "����[" + clerk.getOrgcode() + "]���" + num + "��ӡ����";
			yinjkService.save(yinjk, i);
			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "�������",
					"���п��", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkFenhrk",
					"ƾ֤�����!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkFenhrk",
					"ƾ֤���ʧ��!");
		}
	}

	private YinjkManageLog createYinjkManageLog(YinjkForm yinjkform,
			Clerk clerk, String manageType, String yinjkzt, String managecontent) {
		YinjkManageLog log = new YinjkManageLog();
		String nowDate = getSystemMgrService().getSystetemNowDate();
		log.setManagedate(nowDate.substring(0, 10));
		log.setManagetime(nowDate.substring(11, 19));
		log.setYinjkh(yinjkform.getYinjkh());
		log.setClerknum(clerk.getCode());
		log.setUpflag(clerk.getOrgcode());
		log.setManagetype(manageType);
		log.setYinjkzt(yinjkzt);
		log.setManagecontent(managecontent);
		return log;
	}

	/**
	 *��������ҳ����ת
	 */
	public ActionForward saveJigckForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkJigck");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkJigck");
		}
	}

	/**
	 * ��������
	 */
	public ActionForward saveJigck(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			String endYinjkh = yinjkform.getEndYinjkh();

			Yinjk yinjk = new Yinjk();

			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigck", "���й�Ա���ɽ��д˲���");
			}
			// �жϵ�ǰ��������
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "����"
					: orgFlag.equals("3") ? "֧��" : "����";
			String yinjkzt = jiginfo + "���";
			// ���ò�ѯ����
			yinjk.setYinjkzt(yinjkzt);
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			int i = Integer.parseInt(num);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigck", "����ӡ���������ڻ�״̬�����ϲ�������!");
			}
			if (!getSystemMgrService().CanOperDesOrg(clerk.getOrgcode(),
					yinjkform.getReceiveOrgCode()))
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigck", "ֻ�����¼�����֮����Խ���ӡ�����ĳ���!");

			yinjk.setYinjkzt(jiginfo + "��;");
			yinjk.setJigh(yinjkform.getReceiveOrgCode());

			String content = jiginfo + "[" + clerk.getOrgcode() + "]����" + num
					+ "��ӡ����������[" + yinjk.getJigh() + "]";
			yinjkService.save(yinjk, i);
			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, jiginfo
					+ "����", jiginfo + "��;", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkJigck",
					"ƾ֤�ѳ���!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkJigck",
					"ƾ֤����ʧ��!");
		}
	}

	// �������û�����Ϣ
	public ActionForward getOrgInfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("gbk");
			out = response.getWriter();
			HttpSession session = request.getSession();
			String receiveOrgCode = request.getParameter("orgCode");
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			if (receiveOrgCode.equals(clerk.getOrgcode())) {
				result = "1";
			}else{
				result ="2";
			} 
				Org org = OrgService.getOrgByCode(receiveOrgCode);
				result += ","+org.getName();
				result +=","+org.getWdflag();
			return null;
		} catch (Exception e) {
			result = "0";
			return null;
		} finally {
			out.print(result);
			out.close();
		}
	}

	/**
	 *�������ҳ����ת
	 */
	public ActionForward saveJigrkForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkJigrk");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkJigrk");
		}
	}

	/**
	 * �������
	 */
	public ActionForward saveJigrk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigrk", "���й�Ա���ɽ��д˲���");
			}

			yinjk.setYinjkzt("������;");
			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkJigrk", "���ӡ���������ڻ�״̬�����ϲ�������!");
			}
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "����"
					: orgFlag.equals("3") ? "֧��" : "����";
			yinjk.setYinjkzt(jiginfo + "���");

			String content = "����[" + clerk.getOrgcode() + "]���" + num + "��ӡ����";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "�������",
					jiginfo + "���", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkJigrk",
					"ƾ֤�����!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkJigrk",
					"ƾ֤���ʧ��!");
		}
	}

	/**
	 *��Ա����ҳ����ת
	 */
	public ActionForward saveGuiylyForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkGuiyly");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkGuiyly");
		}
	}

	/**
	 * ��Ա����
	 */
	public ActionForward saveGuiyly(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "����"
					: orgFlag.equals("3") ? "֧��" : "����";
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "���й�Ա���ɽ��д˲���");
			}

			yinjk.setYinjkzt(jiginfo + "���");
			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "ӡ���������ڻ�״̬�����ϲ�������!");
			}

			Clerk lyClerk = clerkService.getClerkByCode(yinjkform
					.getLyClerkCode());
			if (lyClerk == null) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "���ù�Ա������");

			}
			if (!lyClerk.getOrgcode().equals(clerk.getOrgcode())) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiyly", "���ù�ԱӦΪ��������Ա");
			}

			yinjk.setYinjkzt("����");
			yinjk.setClerkcode(lyClerk.getCode());
			String content = "��Ա[" + yinjk.getClerkcode() + "]����" + num
					+ "��ӡ����";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "��Ա����",
					"����", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkGuiyly",
					"���óɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkGuiyly",
					"����ʧ��!");
		}
	}

	/**
	 *��Ա�Ͻ�ҳ����ת
	 */
	public ActionForward saveGuiysjForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkGuiysj");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkGuiysj");
		}
	}

	/**
	 * ��Ա�Ͻ�
	 */
	public ActionForward saveGuiysj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setClerkcode(yinjkform.getLyClerkCode());
			yinjk.setYinjkzt("����");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "����"
					: orgFlag.equals("3") ? "֧��" : "����";
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiysj", "���й�Ա���ɽ��д˲���!");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiysj", "ӡ���������ڻ�״̬�����ϲ�������!");
			}

			yinjk.setYinjkzt(jiginfo + "���");
			yinjk.setClerkcode("");
			String content = "��Ա[" + yinjkform.getLyClerkCode() + "]�Ͻ�" + num
					+ "��ӡ����";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "��Ա�Ͻ�",
					jiginfo + "���", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkGuiysj",
					"�Ͻɳɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkGuiysj",
					"�Ͻ�ʧ��!");
		}
	}

	/**
	 *��Ա����ҳ����ת
	 */
	public ActionForward saveGuiytjForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkGuiytj");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkGuiytj");
		}
	}

	/**
	 * ��Ա����
	 */
	public ActionForward saveGuiytj(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setClerkcode(clerk.getCode());
			yinjk.setYinjkzt("����");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			String jiginfo = orgFlag.equals("1") || orgFlag.equals("2") ? "����"
					: orgFlag.equals("3") ? "֧��" : "����";
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "���й�Ա���ɽ��д˲���");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "ӡ���������ڻ�״̬�����ϲ�������!");
			}

			Clerk trClerk = clerkService.getClerkByCode(yinjkform
					.getLyClerkCode());
			if (trClerk == null) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "�����Ա������");

			}
			if (!trClerk.getOrgcode().equals(clerk.getOrgcode())) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkGuiytj", "�����ԱӦΪ��������Ա");
			}

			yinjk.setClerkcode(trClerk.getCode());
			String content = "��Ա[" + clerk.getCode() + "]��������Ա["
					+ yinjkform.getLyClerkCode() + "]" + num + "��ӡ����";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "��Ա����",
					"����", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkGuiytj",
					"�����ɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkGuiytj",
					"����ʧ��!");
		}
	}

	/**
	 * ӡ��������ҳ����ת
	 */
	public ActionForward saveYinjkzfForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkZuof");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkZuof");
		}
	}

	/**
	 * ӡ��������
	 */
	public ActionForward saveYinjkzf(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setClerkcode(clerk.getCode());
			yinjk.setYinjkzt("����");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkZuof",
						"���й�Ա���ɽ��д˲���");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request, "yinjkZuof",
						"ӡ���������ڻ�״̬�����ϲ�������!");
			}

			yinjk.setYinjkzt("����");
			String content = "��Ա[" + clerk.getCode() + "]����" + num + "��ӡ����";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"ӡ��������", "����", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkZuof",
					"���ϲ����ɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkZuof",
					"���ϲ���ʧ��!");
		}
	}

	/**
	 * ӡ����Ԥ����ҳ����ת
	 */
	public ActionForward saveYinjkyffForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkYff");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkYff");
		}
	}

	/**
	 * ӡ����Ԥ����
	 */
	public ActionForward saveYinjkyff(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String currYinjkh = yinjkform.getYinjkh();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkYff",
						"���й�Ա���ɽ��д˲���");
			}
			List<Yinjk> yinjkList = yinjkService.getMinCodeYinjk(clerk
					.getCode(), 1);
			Yinjk minYinjk = yinjkList.get(0);
			if (minYinjk == null) {
				return this.showMessageJSP(actionMapping, request, "yinjkYff",
						"��ǰ��Ա�޿���ӡ����!");
			}
			if (!currYinjkh.equals(minYinjk.getYinjkh())) {
				return this.showMessageJSP(actionMapping, request, "yinjkYff",
						"Ԥ���ŵ�ӡ��������Ϊ��Ա���еĿ��ò��ұ����С��ӡ����!");
			}

			minYinjk.setYinjkzt("����");
			String content = "��Ա[" + clerk.getCode() + "]Ԥ����ӡ����" + currYinjkh
					+ "���ͻ���" + yinjkform.getHum();
			yinjkService.save(minYinjk, 1);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"ӡ����Ԥ����", "����", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "yinjkYff",
					"Ԥ���Ų����ɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkYff",
					"Ԥ���Ų���ʧ��!");
		}
	}

	/**
	 * ӡ��������ҳ����ת
	 */
	public ActionForward yinjkffForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("yinjkff");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkff");
		}
	}

	/**
	 * ӡ��������
	 */
	public ActionForward yinjkff(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			List<String> currYinjkhList = new ArrayList<String>();
			currYinjkhList.add(yinjkform.getYinjkh());
			if (yinjkform.getYinjkh2() != null
					&& !yinjkform.getYinjkh2().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh2());
			}
			if (yinjkform.getYinjkh3() != null
					&& !yinjkform.getYinjkh3().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh3());
			}
			if (yinjkform.getYinjkh4() != null
					&& !yinjkform.getYinjkh4().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh4());
			}
			Collections.sort(currYinjkhList);

			List<String> oldYinjkhList = new ArrayList<String>();
			if (yinjkform.getOldyinjkh() != null
					&& !yinjkform.getOldyinjkh().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh());
			}
			if (yinjkform.getOldyinjkh2() != null
					&& !yinjkform.getOldyinjkh2().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh2());
			}
			if (yinjkform.getOldyinjkh3() != null
					&& !yinjkform.getOldyinjkh3().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh3());
			}
			if (yinjkform.getOldyinjkh4() != null
					&& !yinjkform.getOldyinjkh4().trim().equals("")) {
				oldYinjkhList.add(yinjkform.getOldyinjkh4());
			}
			Collections.sort(oldYinjkhList);
			
			// ����������ӡ�������� currYinjkhListΪ����ӡ��������б� oldYinjkhListΪ����ӡ��������б�
			List<String> commenYinjkhList = new ArrayList<String>();

			for (String string : oldYinjkhList) {
				if (currYinjkhList.contains(string)) {
					commenYinjkhList.add(string);
					currYinjkhList.remove(string);
				}
			}
			for (String string : commenYinjkhList) {
				oldYinjkhList.remove(string);
			}
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkff",
						"���й�Ա���ɽ��д˲���");
			}
			List<Yinjk> yinjkList = yinjkService.getMinCodeYinjk(clerk
					.getCode(), currYinjkhList.size());

			if (yinjkList == null || yinjkList.size() < currYinjkhList.size()) {
				return this.showMessageJSP(actionMapping, request, "yinjkff",
						"��ǰ��Ա���㹻����ӡ����");
			}
			for (int i = 0; i < yinjkList.size(); i++) {
				if (!yinjkList.get(i).getYinjkh().equals(currYinjkhList.get(i))) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkff", "���ŵ�ӡ��������Ϊ��Ա���еĿ��ò��ұ����С��ӡ����!");
				}
			}
			String yinjkContent = "";
			String zhangh = yinjkform.getZhangh();
			Zhanghb zhanghb = zhanghbService.getZhanghb(zhangh);
			String ffType = yinjkform.getFfType();
			String zhuzh = zhanghb.getZhuzh();
			if (ffType.equals("0")) {
				if ((zhuzh != null && !zhuzh.equals(""))
						|| (zhanghb.getYinjkbh() != null && !zhanghb
								.getYinjkbh().equals(""))) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkff", "���˺Ŵ��ڹ���ӡ��������ʱ���ܷ���");
				} else {
					for (Yinjk yinjk : yinjkList) {
						yinjk.setZhangh(zhanghb.getZhangh());
						yinjk.setYinjkzt("����");
					}

					String yinjkbh = createYinjkbhStr(currYinjkhList);
					zhanghb.setYinjkbh(yinjkbh);
					zhanghbService.update(zhanghb);
					String content = "�˺ţ�" + zhanghb.getZhangh()
							+ ";ӡ��������(ӡ�����ţ�" + yinjkbh + ");���ͣ�����;��Ա���룺"
							+ clerk.getCode();
					this.createAccountManageLog(zhanghb.getZhangh(), "7",
							content, clerk);
					this.createManageLog(clerk.getCode(), content);

					yinjkService.saveYinjkList(yinjkList);
					yinjkContent = "��Ա[" + clerk.getCode() + "]����ӡ����" + yinjkbh;

					YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
							"ӡ��������|����", "����", yinjkContent);
					yinjkService.saveLog(log);
				}
			} else {
				if ((zhuzh == null || !zhuzh.equals(""))
						&& (zhanghb.getYinjkbh() == null || zhanghb
								.getYinjkbh().equals(""))) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkff", "���˺Ų����ڹ���ӡ��������ʱ���ܷ���");
				}
				String yinjkStr = "";
				for (Yinjk yinjk : yinjkList) {
					yinjk.setZhangh(zhanghb.getZhangh());
					yinjk.setYinjkzt("����");
					yinjkStr += yinjk.getYinjkh();
					yinjkStr += "|";
				}
				// ��ӡ�������и��� ����ӡ������Ϣ
				yinjkService.saveYinjkList(yinjkList);
				yinjkContent = "��Ա[" + clerk.getCode() + "]����ӡ����"
						+ yinjkStr.substring(0, yinjkStr.length() - 1);
				String type = ffType.equals("1") ? "���" : "�滻";
				YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
						"ӡ��������|" + type, "����", yinjkContent);
				yinjkService.saveLog(log);
				if (oldYinjkhList != null && oldYinjkhList.size() != 0) {

					List<Yinjk> oldYinjkList = new ArrayList<Yinjk>();
					for (String string : oldYinjkhList) {
				
						oldYinjkList
								.add(yinjkService.getYinjkByYinjkbh(string));
					}

					String oldYinjkStr = "";
					for (Yinjk yinjk : oldYinjkList) {
						yinjk.setYinjkzt("����");
						oldYinjkStr += yinjk.getYinjkh();
						oldYinjkStr += "|";
					}
					// ��ӡ�������� ���ϱ��滻��ӡ����
					yinjkService.saveYinjkList(oldYinjkList);
					String yinjkContent2 = "��Ա["
							+ clerk.getCode()
							+ "]���ϱ��滻��ӡ����"
							+ oldYinjkStr
									.substring(0, oldYinjkStr.length() - 1);
					log = createYinjkManageLog(yinjkform, clerk, "ӡ��������|"
							+ type, "����", yinjkContent2);
					yinjkService.saveLog(log);
				}
				for (String string : commenYinjkhList) {
					currYinjkhList.add(string);
				}
				Collections.sort(currYinjkhList);
				String yinjkbh = createYinjkbhStr(currYinjkhList);
				zhanghb.setYinjkbh(yinjkbh);
				zhanghbService.update(zhanghb);
				String content = "�˺ţ�" + zhanghb.getZhangh() + ";ӡ��������(ӡ�����ţ�"
						+ yinjkbh + ");���ͣ�" + type + ";��Ա���룺" + clerk.getCode();
				this.createAccountManageLog(zhanghb.getZhangh(), "7", content,
						clerk);
				this.createManageLog(clerk.getCode(), content);

			}
			return this.showMessageJSP(actionMapping, request, "yinjkff",
					"���Ų����ɹ�!" + yinjkContent);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "yinjkff",
					"���Ų���ʧ��!");
		}
	}
	/**
	 * ӡ�����ڲ�����ҳ����ת
	 */
	public ActionForward internalReleaseForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String rule = "7" + clerk.getOrgcode() + "%";
			String maxZhangh = zhanghbService.getInternalReleaseZhangh(rule);
			yinjkform.setZhangh(maxZhangh);

			return actionMapping.findForward("internalRelease");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"internalRelease");
		}
	}

	/**
	 * ӡ�����ڲ�����
	 */
	public ActionForward internalRelease(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String zhangh = yinjkform.getZhangh();
			Zhanghb zhanghb = zhanghbService.getZhanghb(zhangh);
			if (zhanghb != null) {
				return this.showMessageJSP(actionMapping, request,
						"internalRelease", "�ڲ��˺��Ѵ��ڣ�");
			}
			List<String> currYinjkhList = new ArrayList<String>();
			currYinjkhList.add(yinjkform.getYinjkh());
			if (yinjkform.getYinjkh2() != null
					&& !yinjkform.getYinjkh2().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh2());
			}
			if (yinjkform.getYinjkh3() != null
					&& !yinjkform.getYinjkh3().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh3());
			}
			if (yinjkform.getYinjkh4() != null
					&& !yinjkform.getYinjkh4().trim().equals("")) {
				currYinjkhList.add(yinjkform.getYinjkh4());
			}
			Collections.sort(currYinjkhList);
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();
			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "yinjkff",
						"���й�Ա���ɽ��д˲���");
			}
			List<Yinjk> yinjkList = yinjkService.getMinCodeYinjk(clerk
					.getCode(), currYinjkhList.size());

			if (yinjkList == null || yinjkList.size() < currYinjkhList.size()) {
				return this.showMessageJSP(actionMapping, request,
						"internalRelease", "��ǰ��Ա���㹻����ӡ����");
			}
			for (int i = 0; i < yinjkList.size(); i++) {
				if (!yinjkList.get(i).getYinjkh().equals(currYinjkhList.get(i))) {
					return this.showMessageJSP(actionMapping, request,
							"internalRelease", "���ŵ�ӡ��������Ϊ��Ա���еĿ��ò��ұ����С��ӡ����!");
				}
			}
			zhanghb = new Zhanghb();
			zhanghb.setZhangh(zhangh);
			zhanghb.setZhanghxz("������");
			zhanghb.setHum(yinjkform.getHum());
			String nowDate = this.getSystemMgrService().getSystetemNowDate();
			zhanghb.setKaihrq(nowDate.substring(0, 10));

			zhanghb.setLianxr(yinjkform.getLinkman());
			zhanghb.setDianh(yinjkform.getPhone());
			zhanghb.setTongctd("��ͨ��");
			zhanghb.setYouwyj("��");
			zhanghb.setYouwzh("��");
			zhanghb.setZuhshzt("δ��");
			zhanghb.setZhanghshzt("����");
			zhanghb.setYinjshzt("δ��");
			zhanghb.setZhanghzt("��Ч");
			zhanghb.setJigh(clerk.getOrgcode());
			String yinjkhStr = createYinjkbhStr(currYinjkhList);
			zhanghb.setYinjkbh(yinjkhStr);
			
			this
					.createAccountManageLog(zhanghb.getZhangh(), "2", "�˻�����",
							clerk);
			String content = "��Ա[" + clerk.getCode() + "]���ڲ��˻�[" + zhangh
					+ "]����ӡ������" + yinjkhStr;
			for (Yinjk yinjk : yinjkList) {
				yinjk.setYinjkzt("����");
				yinjk.setZhangh(zhangh);
			}
			zhanghbService.createZhanghb(zhanghb,null);
			yinjkService.saveYinjkList(yinjkList);
			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk, "�ڲ�����",
					"����", content);
			yinjkService.saveLog(log);
			this.createManageLog(clerk.getCode(), content);
			return this.showMessageJSP(actionMapping, request,
					"internalRelease", "�ڲ����Ų����ɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request,
					"internalRelease", "�ڲ����Ų���ʧ��!");
		}
	}

	/**
	 * ӡ����������ҳ����ת
	 */
	public ActionForward readydestroyForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("readydestroy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"readydestroy");
		}
	}

	/**
	 * ӡ����������
	 */
	public ActionForward readydestroy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());
			yinjk.setYinjkzt("����");

			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request,
						"readydestroy", "���й�Ա���ɽ��д˲���");
			}

			int yinjkNum = yinjkService.countYinjkNumForDestroy(yinjk,
					endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request,
						"readydestroy", "ӡ���������ڻ�״̬�����ϲ�������!");
			}

			yinjk.setYinjkzt("������");
			String content = "��Ա[" + clerk.getCode() + "]����" + num + "��ӡ����Ϊ������";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"ӡ����������", "������", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "readydestroy",
					"�����ٲ����ɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "readydestroy",
					"�����ٲ���ʧ��!");
		}
	}

	/**
	 * ӡ��������ҳ����ת
	 */
	public ActionForward destroyForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);

			return actionMapping.findForward("destroy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"destroy");
		}
	}

	/**
	 * ӡ��������
	 */
	public ActionForward destroy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			String num = yinjkform.getNum();
			int i = Integer.parseInt(num);
			String endYinjkh = yinjkform.getEndYinjkh();
			Yinjk yinjk = new Yinjk();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjk.setYinjkh(yinjkform.getYinjkh());
			yinjk.setJigh(clerk.getOrgcode());

			yinjk.setYinjkzt("������");
			String orgFlag = OrgService.getOrgByCode(clerk.getOrgcode())
					.getWdflag();

			if (orgFlag.equals("0")) {
				return this.showMessageJSP(actionMapping, request, "destroy",
						"���й�Ա���ɽ��д˲���");
			}

			int yinjkNum = yinjkService.countYinjkNum(yinjk, endYinjkh);
			if (yinjkNum != i) {
				return this.showMessageJSP(actionMapping, request, "destroy",
						"ӡ���������ڻ�״̬�����ϲ�������!");
			}

			yinjk.setYinjkzt("����");
			String content = "��Ա[" + clerk.getCode() + "]����" + num + "��ӡ����";
			yinjkService.save(yinjk, i);

			YinjkManageLog log = createYinjkManageLog(yinjkform, clerk,
					"ӡ��������", "����", content);
			yinjkService.saveLog(log);

			return this.showMessageJSP(actionMapping, request, "destroy",
					"���ٲ����ɹ�!" + content);
		} catch (Exception e) {
			return this.showMessageJSP(actionMapping, request, "destroy",
					"���ٲ���ʧ��!");
		}
	}

	/**
	 * ӡ����������ѯҳ����ת
	 */
	public ActionForward yinjkYulcxForView(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			YinjkForm yinjkform = (YinjkForm) actionForm;
			yinjkform.reset(actionMapping, request);
			HttpSession session = request.getSession();
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			yinjkform.setJigh(clerk.getOrgcode());
			yinjkform.setGuiyh(clerk.getCode());
			yinjkform.setYinjkzt("ȫ��");
			String nowDate = getSystemMgrService().getSystetemNowDate();
			// yinjkform.setBegindate(nowDate.substring(0,10));
			// yinjkform.setEnddate(nowDate.substring(0,10));

			return actionMapping.findForward("yinjkYulcx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkYulcx");
		}
	}

	/**
	 * ӡ����������ѯ
	 */
	public ActionForward yinjkYulcx(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			YinjkForm yinjkform = (YinjkForm) actionForm;
			Clerk clerk = (Clerk) session.getAttribute("clerk");
			String jigh = yinjkform.getJigh();
			String clerkCode = yinjkform.getGuiyh();
			if (clerkCode != null && !clerkCode.trim().equals("")) {
				Clerk clerk_ = clerkService.getClerkByCode(clerkCode);
				if (!clerk_.getOrgcode().equals(jigh)) {
					return this.showMessageJSP(actionMapping, request,
							"yinjkYulcx", "��Ա��������Ų�ƥ��");
				}
			}
			boolean pass = getSystemMgrService().CanOperDesOrg(
					clerk.getOrgcode(), jigh);
			if (!pass) {
				return this.showMessageJSP(actionMapping, request,
						"yinjkYulcx", "��ǰ��Աû��Ȩ�޲鿴����[" + jigh + "]��ӡ��������");
			}
			YinjkNum yinjkNum = yinjkService.selectYinjkNum(yinjkform);
			if (yinjkNum != null) {
				request.setAttribute("yinjkNum", yinjkNum);
			}
			return actionMapping.findForward("yinjkYulcx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"yinjkYulcx");
		}
	}

	/**
	 * ӡ�������ò�ѯ
	 */

}
