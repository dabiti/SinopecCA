package com.unitop.sysmgr.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Pingzxtlxgxb;
import com.unitop.sysmgr.bo.Pingzyjlxgxb;
import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.bo.Xitlx;
import com.unitop.sysmgr.bo.Yinjlxb;
import com.unitop.sysmgr.form.VoucherForm;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.impl.VoucherMgrServiceImpl;

@Controller("/voucherManage")
public class VoucherManageAction extends ExDispatchAction {
	@Resource
	private OrgService OrgService;
	@Resource
	private VoucherMgrServiceImpl voucherMgrServiceImpl;
	
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String code = request.getParameter("netpointflag");
			VoucherForm voucherForm = (VoucherForm) actionForm;
			voucherForm.setJigh(code);
			
			this.imageInfo(code,request);
			request.setAttribute("netpointflag", code);
			return actionMapping.findForward("add");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "add");
		}finally{
			request.setAttribute("type","add");
		}
	}
	
	public ActionForward load(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List list = voucherMgrServiceImpl.getVoucherList();
			request.setAttribute("list", list);
			return actionMapping.findForward("list");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request,"list");
		}
	}

	
	private void imageInfo(String imageName,HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("/");
		File f = new File(path + "/images/voucher/" + imageName + ".jpg");
		if(!f.exists())f = new File(path + "/images/voucher/bill.jpg");
		Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
	    ImageReader reader = (ImageReader)readers.next();
		ImageInputStream iis =null;
		try {
			iis = ImageIO.createImageInputStream(f);
			reader.setInput(iis, true);
			
			int  width = reader.getWidth(0);
			int  height =  reader.getHeight(0);
			
			int i =1; 
			int n =1;
			
			//如果发现分子小于分母 不进行等比例转换
			if(width>500&&height>400)
			{
				 i =4;
				 n =4;
			}
			
			request.setAttribute("width",width);
			request.setAttribute("height",height);
			if(width <300&&height<500)
			{
				request.setAttribute("img_width", width);
				request.setAttribute("img_height", height);
			}else{
				double w_ =width/(float)i;
				double h_ =height/(float)n;
				request.setAttribute("img_width",w_);
				request.setAttribute("img_height",h_);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(iis!=null)
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public ActionForward update(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String vouchcode = request.getParameter("voucher");
			Voucher vouch = voucherMgrServiceImpl.voucherDao.getVoucher(vouchcode);
			
			VoucherForm voucherform = (VoucherForm) actionForm;
			voucherform.setJigh(vouch.getJigh());
			voucherform.setPingzbs(vouch.getPingzbs());
			voucherform.setPingzmc(vouch.getPingzmc());
			voucherform.setPingzhqz(vouch.getPingzhqz());
			voucherform.setYanyjb(vouch.getYanyjb());
			voucherform.setMeibzs(vouch.getMeibzs());
			voucherform.setShifzk(vouch.getShifzk());
			voucherform.setShifqy(vouch.getShifqy());
			request.setAttribute("vouchcode",vouchcode);
			return actionMapping.findForward("modify");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "modify");
		}
	}

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String orgcode = "";
		String vouchcode = "";
		String path = "";
		try {
			orgcode = request.getParameter("orgcode");
			vouchcode = request.getParameter("voucher");
			path = "/voucherManage.do?method=load&netpointflag=" + orgcode;
			voucherMgrServiceImpl.deleteVoucherById(vouchcode);
			String admincode = ((Clerk) request.getSession().getAttribute("clerk")).getCode();
			String content = "删除凭证(凭证类型" + vouchcode + ")";
			this.createManageLog(admincode, content);
			ActionForward forward = new ActionForward();
			forward.setPath(path);
			this.processBusinessException(actionMapping, request, content);
			return forward;
		} catch (BusinessException e) {
			return this.errrForLogAndException(e, actionMapping, request,"load");
		}

	}

	public ActionForward updateVoucher(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			VoucherForm form = (VoucherForm) actionForm;
			Voucher vouch = new Voucher();
			vouch.setPingzbs(form.getPingzbs());
			vouch.setPingzmc(form.getPingzmc());
			vouch.setPingzhqz(form.getPingzhqz());
			vouch.setMeibzs(form.getMeibzs());
			vouch.setJigh(form.getJigh());
			vouch.setYanyjb(form.getYanyjb());
			vouch.setShifqy(form.getShifqy());
			vouch.setShifzk(form.getShifzk());
			voucherMgrServiceImpl.voucherDao.updateVoucher(vouch);
			String admincode = ((Clerk) request.getSession().getAttribute("clerk")).getCode();
			String content = "修改凭证(凭证类型:" + vouch.getPingzbs() + ")";
			this.createManageLog(admincode, content);
			ActionForward forward = mapping.findForward("load");
			this.processBusinessException(mapping, request, content);
			return forward;
		} catch (BusinessException e) {
			return this.errrForLogAndException(e, mapping, request, "error");
		}
	}

	public ActionForward createVoucher(ActionMapping mapping,
			ActionForm actionform, HttpServletRequest request,
			HttpServletResponse response){
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			
			VoucherForm form = (VoucherForm) actionform;
			Voucher vouch = new Voucher();
			vouch.setPingzbs(form.getPingzbs());
			vouch.setPingzmc(form.getPingzmc());
			vouch.setPingzhqz(form.getPingzhqz());
			vouch.setMeibzs(form.getMeibzs());
			vouch.setJigh(form.getJigh());
			vouch.setYanyjb(form.getYanyjb());
			vouch.setShifqy(form.getShifqy());
			vouch.setShifzk(form.getShifzk());
			
		try {
			//获取控制参数的类
			SystemConfig systemConfig = SystemConfig.getInstance();
			//各凭证每本张数不超过1000限制校验
			String maxmeibzs=systemConfig.getValue("maxmeibzs");
			int maxmeibzsInt=Integer.parseInt(maxmeibzs);
			if(vouch.getMeibzs()>maxmeibzsInt)
			{
				return this.showMessageJSP(mapping, request, "add", "每本张数["+vouch.getMeibzs()+"]不能超过1000!");	
			}

			Voucher vouchModel = voucherMgrServiceImpl.voucherDao.getVoucher(vouch.getPingzbs());
			if(vouchModel!=null)
			{
				return this.showMessageJSP(mapping, request, "add", "凭证编号["+vouch.getPingzbs()+"]已经被使用!");	
			}
			voucherMgrServiceImpl.voucherDao.saveVoucher(vouch);
			String content = "新增凭证(凭证类型:" + vouch.getPingzbs() + ")";
			this.createManageLog(clerk.getCode(), content);
			this.processBusinessException(mapping, request, content);
			return mapping.findForward("add.list");
		} catch (Exception e) {
			return this.errrForLogAndException(e, mapping, request, "add.list");
		}
	}
	
	
	/*
	 * 凭证业务设置
	 */
	public ActionForward voucherSetLink(ActionMapping mapping,
			ActionForm actionform, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String orgcode = "000000";
			String vouchcode = request.getParameter("voucher");
			Voucher vouch = voucherMgrServiceImpl.voucherDao.getVoucher(vouchcode);
			request.setAttribute("vouch", vouch);
			
			//if(此凭证是否已经保存)
				//加载已经保存成功业务设置信息
			//else
			
			//凭证系统关系集合
			List xitlist = voucherMgrServiceImpl.getXitlxgxList(orgcode, vouchcode);
			request.setAttribute("xitList", xitlist);
			//凭证印鉴关系集合
			List yinjlist = voucherMgrServiceImpl.getYinjlxgxList(orgcode, vouchcode);
			request.setAttribute("yinjList", yinjlist);
			
			if(xitlist==null)xitlist=new ArrayList();
			if(yinjlist==null)yinjlist=new ArrayList();
			
		
			//获取系统类型 及印鉴类型
			List systemTypeList =  voucherMgrServiceImpl.getXitlxList();
			List YinjTypeList = voucherMgrServiceImpl.getYinjlxList();
			request.setAttribute("systemTypeList", this.doList1(systemTypeList,xitlist));
			request.setAttribute("YinjTypeList", this.doList2(YinjTypeList,yinjlist));
			return mapping.findForward("set");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, mapping, request, "list");
		}
	}
	
	//从List1集合中移除List2的集合 并返回--系统类型
	private List doList1(List list1,List list2)
	{
		List list = new ArrayList();
 		int n = list1.size();
		for(int i=0;i<n ;i++)
		{
			Xitlx p1 =(Xitlx) list1.get(i);
			for(int j=0;j<list2.size();j++)
			{
				Pingzxtlxgxb p2 =(Pingzxtlxgxb) list2.get(j);
				if(p1.getXitlx().equals(p2.getId().getXitlx()))
				{
					list.add(i);
				}
			}
		}
		
		for(int i=list.size()-1;i>=0 ;i--)
		{
			int t = (Integer)list.get(i);
			list1.remove(t);
		}
		return list1;
	}
	
	//从List1集合中移除List2的集合 并返回--系统类型
	private List doList2(List list1,List list2)
	{
		List list = new ArrayList();
 		int n = list1.size();
		for(int i=0;i<n ;i++)
		{
			Yinjlxb p1 =(Yinjlxb) list1.get(i);
			for(int j=0;j<list2.size();j++)
			{
				Pingzyjlxgxb p2 =(Pingzyjlxgxb) list2.get(j);
				if(p1.getYinjlx().equals(p2.getId().getYinjlx()))
				{
					list.add(i);
				}
			}
		}
		
		for(int i=list.size()-1;i>=0 ;i--)
		{
			int t = (Integer)list.get(i);
			list1.remove(t);
		}
		System.out.println(list1);
		return list1;
	}

	/*
	 * 凭证业务设置
	 * 实现 凭证业务设置 保存功能+修改功能
	 */
	public ActionForward voucherSet(ActionMapping mapping,
			ActionForm actionform, HttpServletRequest request,
			HttpServletResponse response) {
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//获取参数
			String orgcode = "000000";
			String vouchcode = request.getParameter("pingzh");
		try {
			Voucher vouch = this.voucherMgrServiceImpl.voucherDao.getVoucher(vouchcode);
			request.setAttribute("vouch", vouch);
			
			String[] xitlxList = request.getParameterValues("s2");
			String[] yanylxList = request.getParameterValues("s4");
			
			//保存、更新
			voucherMgrServiceImpl.saveXitlxgx(orgcode, vouchcode, xitlxList, yanylxList);
			//实现数据保存》service>dao=操作数据库（hibernate）
			
			//记录系统日志
			String admincode = ((Clerk) request.getSession().getAttribute(
			"clerk")).getCode();
			String content = "凭证业务设置(凭证类型" + vouchcode + ")";
			this.createManageLog(admincode, content);
			
			
			request.setAttribute("netpointflag", orgcode);
			return mapping.findForward("list");
		} catch (Exception e) {
			//凭证系统关系集合
			request.setAttribute("xitList", null);
			//凭证印鉴关系集合
			request.setAttribute("yinjList", null);
			
			//获取系统类型 及印鉴类型
			List systemTypeList =  voucherMgrServiceImpl.getXitlxList();
			List YinjTypeList = voucherMgrServiceImpl.getYinjlxList();
			request.setAttribute("systemTypeList", (systemTypeList));
			request.setAttribute("YinjTypeList", (YinjTypeList));
			request.setAttribute("netpointflag", orgcode);
			return this.errrForLogAndException(e, mapping, request, "set");
		}finally{
			//跳转页面 加载资源
			Org org = OrgService.getOrgByCode(orgcode);
			String rootCode = SystemConfig.getInstance().getRootCode();
			String pcode = org.getCode();
			if (pcode == null)
				pcode = "0";
			String wdflag = org.getWdflag();
			if (wdflag == null)
				wdflag = "0";
			List list = voucherMgrServiceImpl.getVoucherList();
			if ((clerk.getOrgcode().equals(rootCode) && (!pcode .equals(rootCode)))||wdflag.equals("2")) {
				request.setAttribute("addflag", "0");
			} else {
				request.setAttribute("addflag", "1");
			}
			String orgname = org.getName();
			if (orgname == null)
				orgname = "";
			request.setAttribute("list", list);
			request.setAttribute("orgname", orgname);
		}
	}
		
}
