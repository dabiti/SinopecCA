package com.unitop.sysmgr.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.framework.util.DateTool;
import com.unitop.framework.util.JsonTool;
import com.unitop.framework.util.MD5Tool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Dayfwcs;
import com.unitop.sysmgr.bo.Pingzmx;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.form.PingzForm;
import com.unitop.sysmgr.service.PingzService;
import com.unitop.sysmgr.service.PingzmxService;
import com.unitop.sysmgr.service.VoucherMgrService;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.impl.PingzmxServiceImpl;
@Controller("/pingz")
public class PiaojErwmAction extends ExDispatchAction {
	@Resource
	private PingzmxService pingzmxService;
	@Resource
	private ZhanghbService zhanghService;
	@Resource
	private ZhanghbDao zhanghbDao ;
	@Resource
	private VoucherMgrService voucherMgrservice;
	@Resource
	public PingzService PingzService;

	// ƾ֤ ����ҳ����ת
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PingzForm pf = (PingzForm) actionForm;
			pf.clear();
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			return actionMapping.findForward("pingzcs");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}
	}

	// ������ϸ�� ��ӡ
	public ActionForward findPingzmx(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List pingzlxlist = findPingzlx(request);
			request.setAttribute("pingzlxlist", pingzlxlist);
			PingzForm pingzForm = (PingzForm) actionForm;

			// �쳣���������һ�ε�¼���� �л���ҳ������ѯ ֱ�ӷ���
			if ("".equals(pingzForm.getEnddate()) && "".endsWith(pingzForm.getBegindate()))
			{
				return actionMapping.findForward("mingxcxdy");
			}
			//�˺�У��
			String accountNum=pingzForm.getZhangh();
			if((accountNum!=null)&&(!"".equals(accountNum.trim()))){
				Zhanghb zhanghb=zhanghbDao.getZhanghb(accountNum);
				if(zhanghb==null){
					return this.showMessageJSP(actionMapping, request, "mingxcxdy","�˺Ų����ڣ�");
				}
			}
			// ƾ֤��У��
			String qispzh = pingzForm.getQispzh();
			String zhongzpzh = pingzForm.getZhongzpzh();
			
			if (("".equals(qispzh) && !"".equals(zhongzpzh)) || ((!"".equals(qispzh) && "".equals(zhongzpzh)))) 
			{ 
				return this.showMessageJSP(actionMapping, request, "mingxcxdy","��ʼƾ֤������ֹƾ֤�ű���ͬʱ���룡");
			} else if (!"".equals(qispzh) && !"".equals(zhongzpzh)) {
				String t1=qispzh.substring(2);
				String t2=zhongzpzh.substring(2);
				if (t1.compareTo(t2)>0) 
				{
					return this.showMessageJSP(actionMapping, request, "mingxcxdy", "��ʼƾ֤�Ų��ܴ�����ֹƾ֤�ţ�");
				}
				String qispzlx = qispzh.toUpperCase().substring(2);
				String zhongzpzlx = zhongzpzh.toUpperCase().substring(2);
				if (Long.valueOf(qispzlx)>Long.valueOf(zhongzpzlx))
				{
					return this.showMessageJSP(actionMapping, request, "mingxcxdy", "��ʼƾ֤�ź���ֹƾ֤�ŵ�ƾ֤���Ͳ���ͬ��");
				}
				pingzForm.setQispzh(qispzh);
				pingzForm.setZhongzpzh(zhongzpzh);
			}
			TabsBo TabsBo = this.createTabsBo(request);
			PingzmxServiceImpl pingzmxServiceImpl = (PingzmxServiceImpl) pingzmxService;
			pingzmxServiceImpl.setTabsService(TabsBo);
			TabsBo tabsBo =pingzmxService.selectAllPingz(pingzForm);
			this.showTabsModel(request, tabsBo);
			List erwmMsgList = new ArrayList();
			for (int i = 0; i < tabsBo.getList().size(); i++) 
			{
				Pingzmx pz =  (Pingzmx) tabsBo.getList().get(i);
				if ("δ��ӡ".equals(pz.getZhuangt()))
				{
					String erwmMsg = makeErWM(pz);
					pz.setErwmMsg(erwmMsg);
					erwmMsgList.add(erwmMsg);
				}
			}
			request.setAttribute("erwmMsgList", JsonTool.toJsonForArry(erwmMsgList));
			request.setAttribute("pingzlist", tabsBo.getList());
			return super.showMessageJSPForFeny(actionMapping,request,tabsBo,"mingxcxdy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "mingxcxdy");
		}
	}

	/**
	 * ��֯��ӡ���ݱ���
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private String makeErWM(Pingzmx pingz){
		List pingzlx = voucherMgrservice.getVoucherList();
		String pingzlxStr = "";
		if(pingzlx!=null)
		{
			Voucher v = (Voucher)pingzlx.get(0);
			pingzlxStr = v.getPingzbs();
		}
		pingz.setFilename(pingzlxStr);
		pingz.setErwmMsg(pingz.getZhangh()+"|"+pingz.getHum()+"|"+pingz.getPingzh());
		String json = JsonTool.toJsonForArry(pingz);
		return json;
	}

	/**
	 * 
	 * <dl>
	 * <dt><b>getAccountForNet:��.net���˻����л�ȡ�˻�����(�½ӿ�)</b></dt>
	 * <dd></dd>
	 * </dl>
	 */
	public ActionForward getZhanghbHum(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String zhangh = request.getParameter("zhangh");
		String hum = "";
		try {
			Zhanghb zhanghinfo = zhanghService.getZhanghb(zhangh);
			if (zhanghinfo != null)
			{
				hum = zhanghinfo.getHum();
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			String zhanghin = hum;
			out.println(zhanghin);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		}

	}

	// ��ϸ��ѯ�� ��ӡҳ����ת
	public ActionForward cxdy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			/*
			 * ��ȡƾ֤����
			 */
			PingzForm pf = (PingzForm) actionForm;
			pf.clear();
			if(pf.getBegindate() == "")
			{
				pf.setBegindate(DateTool.getNowDayForYYYMMDD().substring(0,7)+"-01");
			}
			if(pf.getEnddate() == "")
			{
				pf.setEnddate(DateTool.getNowDayForYYYMMDD());
			}
			List<Voucher> pingzlxlist = findPingzlx(request);
			//�˴�����ʹ��session
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			return actionMapping.findForward("mingxcxdy");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			request.setAttribute("erwmMsgList", "''");
			// ��ִ��
		}
	}

	// ��ȡƾ֤����(�ڲ�ר��)
	private List findPingzlx(HttpServletRequest request) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		String orgcode = clerk.getOrgcode();
		List<Voucher> pingzlxlist = voucherMgrservice.getVoucherList();
		if (pingzlxlist == null || pingzlxlist.size() == 0) 
		{
			String rootcode = SystemConfig.getInstance().getRootCode();
			pingzlxlist = voucherMgrservice.getVoucherList();
		}
		return pingzlxlist;
	}

	// ������ܼ���ϸ��Ϣ
	public ActionForward saveHuiz(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			// ��ȡ��ǰʱ��
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			
			SimpleDateFormat formattime = new SimpleDateFormat("HH:mm:ss");
			String time = formattime.format(rightNow);

			// ��ȡForm�е�ֵ
			PingzForm pingzForm = (PingzForm) actionForm;
			Pingzmx pingzmx = new Pingzmx();
			Zhanghb zhangh = new Zhanghb();

			// ��֤�˺��Ƿ����
			try {
				zhangh = zhanghService.getZhanghb(pingzForm.getZhangh());
			} catch (Exception e) {
				return this.showMessageJSP(actionMapping, request, "pingzcs", "���˻������ڣ�");
			}

			// ƾ֤��У��
			String qispzh = pingzForm.getQispzh().toUpperCase();
			String zhongzpzh = pingzForm.getZhongzpzh().toUpperCase();

			if (("".equals(qispzh) && !"".equals(zhongzpzh)) || ((!"".equals(qispzh) && "".equals(zhongzpzh))))
			{
				return this.showMessageJSP(actionMapping, request, "pingzcs", "��ʼƾ֤������ֹƾ֤�ű���ͬʱ���룡");
			} else if (!"".equals(qispzh) && !"".equals(zhongzpzh)) {
				if (qispzh.compareTo(zhongzpzh)>0) 
				{
					return this.showMessageJSP(actionMapping, request, "pingzcs", "��ʼƾ֤�Ų��ܴ�����ֹƾ֤�ţ�");
				}
				pingzForm.setQispzh(qispzh);
				pingzForm.setZhongzpzh(zhongzpzh);
			}
			// ��֤ƾ֤���Ƿ��Ѵ���
			List<Pingzmx> list = pingzmxService.findPingzByPingzh(qispzh,zhongzpzh);
			if (list == null || list.size() == 0) 
			{
				//��֤ƾ֤һ��¼��������Ƿ����
				Long qis = Long.parseLong(qispzh);
				Long zhongz = Long.parseLong(zhongzpzh);
				Long maxpz = Long.parseLong(SystemConfig.getInstance().getMaxpings());
				/*int qis = Integer.parseInt(qispzh);
				int zhongz = Integer.parseInt(zhongzpzh);
				int maxpz = Integer.parseInt(SystemConfig.getInstance().getMaxpings());*/
				if ((zhongz-qis)>maxpz)
				{
					return this.showMessageJSP(actionMapping, request,"pingzcs", "ƾ֤¼����࣡ÿ�����¼��"+maxpz+"�ţ�");
				}
				pingzmx.setZhangh(pingzForm.getZhangh() == null ? pingzmx.getZhangh() : pingzForm.getZhangh());
				pingzmx.setGuiyh(pingzForm.getGuiyh() == null ? pingzmx.getGuiyh() : pingzForm.getGuiyh());
				pingzmx.setHum(zhangh.getHum());
				pingzmx.setJigh(pingzForm.getJigh() == null ? pingzmx.getJigh(): pingzForm.getJigh());
				pingzmx.setRiq(date);
				pingzmx.setShij(time);
				pingzmx.setPingzlx(pingzForm.getPingzlx() == null ? pingzmx.getPingzlx() : pingzForm.getPingzlx());
				pingzmx.setQispzh(pingzForm.getQispzh() == null ? pingzmx.getQispzh() : pingzForm.getQispzh());
				pingzmx.setZhongzpzh(pingzForm.getZhongzpzh() == null ? pingzmx.getQispzh() : pingzForm.getZhongzpzh());
				pingzmx.setShij("".equals(pingzForm.getShij()) ? pingzmx.getShij() : pingzForm.getShij());
				pingzmx.setZhangs(request.getParameter("zhangs_"));
				pingzmxService.savePingz(pingzmx);
				Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
				String content = "ƾ֤����(�˺�Ϊ" + pingzmx.getZhangh() + ";��ʼƾ֤��:"+pingzmx.getQispzh()+ ";��ֹƾ֤��:"+pingzmx.getZhongzpzh()+")";
				this.createManageLog(clerk.getCode(), content);
				return this.showMessageJSP(actionMapping, request, "pingzcs", getPromptService().getPromptMsg("PJCS_success", map));
			} else {
				return this.showMessageJSP(actionMapping, request, "pingzcs", getPromptService().getPromptMsg("PJCS_alreadyhave", map));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzcs");
		} finally {
			// ��ִ��

		}
	}

	public ActionForward forwardForUpdate(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PingzForm pingzForm = (PingzForm) actionForm;
			Pingzmx pingzmx = pingzmxService.findPingzByPingzh(pingzForm.getPingzh());
			request.setAttribute("pingzmx", pingzmx);
			return actionMapping.findForward("toUpdate");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}

	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PingzForm pingzForm = (PingzForm) actionForm;
			Pingzmx pingzmx = pingzmxService.findPingzByPingzh(pingzForm
					.getPingzh());
			pingzmx.setZhangh(pingzForm.getZhangh() == null ? pingzmx
					.getZhangh() : pingzForm.getZhangh());
			pingzmx.setGuiyh(pingzForm.getGuiyh() == null ? pingzmx.getGuiyh()
					: pingzForm.getGuiyh());
			pingzmx.setHum(pingzForm.getHum() == null ? pingzmx.getHum()
					: pingzForm.getHum());
			pingzmx.setZhuangt(pingzForm.getZhuangt() == null ? pingzmx
					.getZhuangt() : pingzForm.getZhuangt());
			pingzmx.setRiq(pingzForm.getRiq() == null ? pingzmx.getRiq()
					: pingzForm.getRiq());
			pingzmx.setShij("".equals(pingzForm.getShij()) ? pingzmx.getShij()
					: pingzForm.getShij());
			pingzmxService.updatePingz(pingzmx);
			return actionMapping.findForward("mingxcxdy");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String deleteflag = "success";
			String pingzh = request.getParameter("pingzh");
			try {
				pingzmxService.deletePingz(pingzh);
				Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
				String content = "ƾ֤����ɾ��(ƾ֤��" +pingzh+")";
				this.createManageLog(clerk.getCode(), content);
			} catch (Exception e) {
				deleteflag = "fail";
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(deleteflag);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}
	
	/*
	 * ƾ֤����ɾ��   ajax
	 */
	public ActionForward bathDelete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String deleteflag = "success";
			String pingzhString = request.getParameter("pingzh");
			String selectString = request.getParameter("selectString");
			//ת��
			selectString = URLDecoder.decode(selectString.toString(),"utf-8");
			try {
				pingzmxService.batchDeletePingz(pingzhString);
				Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
				String content = "ƾ֤����ɾ��(��ѯ����~"+selectString+")";
				this.createManageLog(clerk.getCode(), content);
			} catch (Exception e) {
				deleteflag = "fail";
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(deleteflag);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}

	public ActionForward getPingzListByPich(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		try {
			String pich = request.getParameter("pich");
			List list = PingzService.findPingzByPich(pich);
			request.setAttribute("list", list);
			request.setAttribute("totalRows", list.size());
			return actionMapping.findForward("huizpzmx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "huizpzmx");
		} finally {
			// ��ִ��
		}
	}

	// ƾ֤������ϸ��־
	public ActionForward getPingzMxRizListByPich(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String pingzh = request.getParameter("pingzh");
			List list = pingzmxService.findPingzRizByPingzh(pingzh);
			request.setAttribute("list", list);
			request.setAttribute("totalRows", list.size());
			return actionMapping.findForward("pingzmxrz");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,
					"pingzmxrz");
		} finally {
			// ��ִ��
		}
	}

	// ����ƾ֤ģ��
	public ActionForward getPingzmb(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream out = null;
		String fileName = request.getParameter("filename");
		String type = request.getParameter("mode");
		try {
			out = response.getOutputStream();
			if ("0".equals(type)) {
				PingzService.loadPingzmbByName(request.getSession()
						.getServletContext().getRealPath("")
						+ File.separatorChar
						+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
						+ fileName + ".html", out);
				response.setHeader("Content-disposition",
						"attachment;filename=" + fileName);
			}
			if ("1".equals(type)) {
				PingzService.loadPingzmbByName(request.getSession()
						.getServletContext().getRealPath("")
						+ File.separatorChar
						+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
						+ fileName+".jpg", out);
				response.setHeader("Content-disposition",
						"attachment;filename=" + fileName);
			}
			System.gc();
			return null;
		} catch (Exception e) {
			try{
				if ("0".equals(type)) {
					PingzService.loadPingzmbByName(request.getSession()
							.getServletContext().getRealPath("")
							+ File.separatorChar
							+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
							+  "default.html", out);
					response.setHeader("Content-disposition", "attachment;filename=" + fileName);
				}
				if ("1".equals(type)) {
					PingzService.loadPingzmbByName(request.getSession()
							.getServletContext().getRealPath("")
							+ File.separatorChar
							+ File.separator+"WEB-INF"+File.separator+"classes"+File.separator
							+ "default.jpg", out);
					response.setHeader("Content-disposition",
							"attachment;filename=" + fileName);
				}} catch (Exception ee) {
			}
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			try {
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ActionForward updateZhuangt(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String updateflag = "success";
			String qispzh = request.getParameter("qispzh");
			String zhongzpzh = request.getParameter("zhongzpzh");
			String dayfwmsg = request.getParameter("dayfwmsg");
			String count = request.getParameter("count");
			log.info("action��������:"+count);
			dayfwmsg = URLDecoder.decode(dayfwmsg,"utf-8");//ת��
			//��ǰ�˴���Ĵ�ӡ������Ϣת��
			dayfwmsg = transDayfwid(dayfwmsg);
		
			try {
				pingzmxService.updateZhuangt(qispzh,zhongzpzh,dayfwmsg,count);
			} catch (Exception e) {
				e.printStackTrace();
				updateflag = "fail";
			}

			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			out.println(updateflag);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}
	public String transDayfwid(String dayfwmsg){
		dayfwmsg = dayfwmsg.replace('\\', '/');
		Dayfwcs d = (Dayfwcs)JsonTool.toBean(dayfwmsg, Dayfwcs.class);
		String[] port = d.getDayfwport().split(".");
		String str ="";
		if(port.length==4){
			 str = d.getDayfwport(); 
		}else{
			int f = d.getDayfwmc().indexOf("//");
			if(f!=-1){
				str = d.getDayfwmc()+"|"+d.getDayfwport()+"|"+d.getDayfwmac();
			}else{
				str = "//"+d.getBendname().toUpperCase()+"/"+d.getDayfwmc()+"|"+d.getDayfwport()+"|"+d.getDayfwmac();
			}
		}
		String dayfwid = MD5Tool.getMD5ByStr(str);
		return dayfwid;
	}
	
	public void setVoucherMgrservice(VoucherMgrService voucherMgrservice) {
		this.voucherMgrservice = voucherMgrservice;
	}
}
