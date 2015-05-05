package com.unitop.sysmgr.action;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Pingzgcb;
import com.unitop.sysmgr.bo.Pingzjgsyb;
import com.unitop.sysmgr.bo.Pingzkcsyb;
import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.dao.ZhongkpzDao;
import com.unitop.sysmgr.form.ZhongkpzForm;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.VoucherMgrService;
import com.unitop.sysmgr.service.ZhongkpzService;
import com.unitop.sysmgr.service.impl.VoucherMgrServiceImpl;

/*
 * by wp
 */
@Controller("/zhongkpz")
public class ZhongkpzAction extends ExDispatchAction{

	@Resource
	private ZhongkpzService zhongkpzService;
	@Resource
	private VoucherMgrService voucherMgrservice;
	@Resource
	private ZhongkpzDao zhongkpzDao;
	/*
	 * 凭证入库页面跳转
	 */ 
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//界面加载凭证种类
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			
			//获取当前日期
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			request.getSession().setAttribute("date", date);
			
			//界面加载机构号
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//柜员所属机构号
			String orgcode = clerk.getOrgcode();
			request.getSession().setAttribute("orgcode", orgcode);
			return actionMapping.findForward("pingzrk");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 获取凭证类型(内部专用)
	 */ 
	private List findPingzlx(HttpServletRequest request) {
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
		List<Voucher> pingzlxlist = voucherMgrservice.getVoucherList_zk();
		if (pingzlxlist == null || pingzlxlist.size() == 0) 
		{
			pingzlxlist = voucherMgrservice.getVoucherList_zk();
		}
		return pingzlxlist;
	}
	
	/*
	 * 保存入库信息
	 */ 
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			//获取控制参数的类
			SystemConfig systemConfig = SystemConfig.getInstance();
			// 获取Form中的值
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
			String qispzh = pzform.getQispzh_();
			String bens = pzform.getBens();
			String zhongzpzh = pzform.getZhongzpzh_();
			String rukjg = pzform.getRukjg();
			String pingzlx = pzform.getPingzlx();
			
			int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//本数不能大于50的校验
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "入库本数不能大于50！");	
			}
			
			//本数 不能为“0”、“00”、“000”、“0000”校验
			if("0".equals(bens)||"00".equals(bens)||"000".equals(bens)||"0000".equals(bens)||"00000".equals(bens))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "入库本数不能为0！");	
			}
			//起始凭证号、本数、柜员的输入效验
			if (("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "请输入起始凭证号、本数、入库机构！");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "请输入起始凭证号、本数！");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "请输入起始凭证号、入库机构！");
			}else if (("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "请输入本数、入库机构！");
			}
			// 验证该类型凭证号是否已存在
			List list = zhongkpzService.findPingzByPingzh(qispzh, zhongzpzh,pingzlx);
			if(!(list.size()==0)){
				return this.showMessageJSP(actionMapping, request, "pingzrk", "凭证号已存在！");	
			}
			
			//通过起始、终止凭证号计算本次入库凭证总张数
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long zhangsLong = zhongzpzhLong - qispzhLong + 1;//入库总张数
			String zhangsString = String.valueOf(zhangsLong);
			int zhangsInt = Integer.parseInt(zhangsString);
			
			//保存入库信息（过程表、库存剩余表）
			Pingzkcsyb pzs = new Pingzkcsyb();
			pzs.getPingzkcsybid().setJigh(pzform.getRukjg());
		    pzs.getPingzkcsybid().setPingzlx(pzform.getPingzlx());
		    pzs.setShengyzs(zhangsInt);
		    pzs.setZongs(zhangsInt);
		    zhongkpzService.savePingz(qispzh,zhongzpzh,pzform.getPingzlx(),pzform.getRukrq(),pzform.getRukjg(),pzs); 
			return this.showMessageJSP(actionMapping, request, "pingzrk", getPromptService().getPromptMsg("zhongk_rk", map));//调用提示信息
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzrk");
		} finally {
		}
	}
	
	/*
	 * 获取凭证每本张数（入库）
	 */ 
	public ActionForward getPingzmbzs(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//获取界面凭证名称ID
			String pingzbs = request.getParameter("pingzbs");
			VoucherMgrServiceImpl voucherMgrServiceImpl = (VoucherMgrServiceImpl) this.voucherMgrservice;
			Voucher voucher = voucherMgrServiceImpl.voucherDao.getVoucher(pingzbs);
			PrintWriter write = response.getWriter();
			write.write(String.valueOf(voucher.getMeibzs()));
			write.flush();
			write.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "pingzrk");
		} 
	}
	
	/*
	 * 凭证领用页面跳转
	 */ 
	public ActionForward add_ly(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//添加后界面加载凭证类型
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			//界面加载机构号
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//柜员所属机构号
			String orgcode = clerk.getOrgcode();
			String code =clerk.getCode();
			request.getSession().setAttribute("code", code);
			request.getSession().setAttribute("orgcode", orgcode);
			return actionMapping.findForward("pingzly");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 保存领用信息
	 */ 
	public ActionForward saveLingy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			
			//获取控制参数的类
			SystemConfig systemConfig = SystemConfig.getInstance();
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
			String lingymode = pzform.getLingymode();
			String  bens = pzform.getBens();
			String jiglyfzr=pzform.getJiglyfzr();
			String Guiyh=pzform.getGuiyh();
			
			int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//本数不能大于50的校验
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzly", "入库本数不能大于50！");	
			}
			//本数的输入效验
			if (("".equals(bens)))
			{
				return this.showMessageJSP(actionMapping, request, "pingzly", "请输入领用本数！");
			}
			
			if("机构".equals(lingymode))
			{    
				if(pzform.getLingyjg()==null||"".equals(pzform.getLingyjg())||pzform.getJiglyfzr()==null||"".equals(pzform.getJiglyfzr()))
				{
					return this.showMessageJSP(actionMapping, request, "pingzly", "领用机构、领用机构负责人不能为空！");
					
				}

				//机构领用  1、由本数计算需要领用的张数   2、判断本机构是否有剩余  3、有足够余额再保存领用信息
				//领用信息保存至凭证过程表
				Pingzgcb pingz = new Pingzgcb();
				pingz.getPingzgcbid().setPingzlx(pzform.getPingzlx());
				pingz.setLingyjg(pzform.getLingyjg());
				pingz.setJiglyfzr(pzform.getJiglyfzr());
				pingz.setPingzzt("未领");    //凭证状态  “1” 表示未领
				
				//领用信息保存至凭证库存剩余表
				Pingzkcsyb pzkc = new Pingzkcsyb();
				pzkc.getPingzkcsybid().setJigh(clerk.getOrgcode());//领用时的登陆机构
				pzkc.getPingzkcsybid().setPingzlx(pzform.getPingzlx());
				
				//领用信息保存至凭证机构剩余表
				Pingzjgsyb pzjg = new Pingzjgsyb();
				pzjg.getPingzjgsybid().setJigh(pzform.getLingyjg());
				pzjg.getPingzjgsybid().setPingzlx(pzform.getPingzlx());
				String qispzh=zhongkpzService.saveLybyJg(jiglyfzr,bens,pingz,pzkc,pzjg);
				String content="起始凭证号为"+qispzh;
				return this.showMessageJSP(actionMapping, request, "pingzly", "[凭证领用成功] " + content);
			}else
			{
				if(pzform.getGuiyh()==null||"".equals(pzform.getGuiyh()))
				{
					return this.showMessageJSP(actionMapping, request, "pingzly", "领用柜员不能为空！");
					
				}
				//个人领用  1、由本数计算需要领用的张数 2、判断本机构是否有剩余3、有足够余额再保存领用信息
				//领用信息保存至凭证过程表
				Pingzgcb pingz = new Pingzgcb();
				pingz.getPingzgcbid().setPingzlx(pzform.getPingzlx());
				pingz.setGuiyh(pzform.getGuiyh());//领用柜员
				//pingz.setLingyjg(clerk.getOrgcode());
				pingz.setPingzzt("已领");    //凭证状态“2” 表示已领
				
				//领用信息保存至凭证机构剩余表
				String jigh = clerk.getOrgcode();//领用柜员所在机构
				Pingzjgsyb pzjg = new Pingzjgsyb();
				pzjg.getPingzjgsybid().setJigh(clerk.getOrgcode());
				pzjg.getPingzjgsybid().setPingzlx(pzform.getPingzlx());
				String qispzh=zhongkpzService.saveLybyGr(Guiyh,bens,pingz,pzjg,jigh);
				String content="起始凭证号为"+qispzh;
				return this.showMessageJSP(actionMapping, request, "pingzly", "[凭证领用成功] " + content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzly");
		} finally {
		}
	}
	/*
	 * 凭证退回页面跳转
	 */ 
	public ActionForward add_th(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			return actionMapping.findForward("pingzth");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 保存退回信息
	 */ 
	public ActionForward saveTuih(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			
			//获取控制参数的类
			SystemConfig systemConfig = SystemConfig.getInstance();
			//获取Form中的值
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
			String pingzlx = pzform.getPingzlx();
			String tuihmode = pzform.getTuihmode();
			String qispzh_ = pzform.getQispzh_();
			String bens = pzform.getBens();
			String zhongzpzh_ = pzform.getZhongzpzh_();
			String guiyh = pzform.getGuiyh();
			
			int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//本数不能大于50的校验
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "入库本数不能大于50！");	
			}
			
			//获取当前登陆柜员的机构号
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String jigh = clerk.getOrgcode();//获取当前登陆机构的机构号
			
			//起始凭证号、本数、退回柜员的输入效验
			if (("".equals(qispzh_)||qispzh_==null)&&("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "请输入起始凭证号、本数、退回柜员！");
			}else if(("".equals(qispzh_)||qispzh_==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "请输入起始凭证号、本数！");
			}else if(("".equals(qispzh_)||qispzh_==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "请输入起始凭证号、退回柜员！");
			}else if (("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "请输入本数、退回柜员！");
			}
			if("个人".equals(tuihmode))
			{
				//退回信息保存到机构剩余表 ：   
				Pingzjgsyb pzjg = new Pingzjgsyb();
				pzjg.getPingzjgsybid().setJigh(clerk.getOrgcode());//机构记录退回信息
				pzjg.getPingzjgsybid().setPingzlx(pingzlx);
				
				//以柜员的形式将空白凭证退回机构             
				//退回信息保存到过程表  ：柜员号变为退回柜员的柜员号，凭证类型变为未领
				Pingzgcb pingz = new Pingzgcb();
				pingz.getPingzgcbid().setPingzlx(pzform.getPingzlx());
				pingz.setGuiyh(guiyh);
				pingz.setPingzzt("已领");  //凭证状态“2” 表示已领
				zhongkpzService.saveTogc(guiyh,qispzh_,zhongzpzh_,pingz,pzjg);
			}else
			{
				//以机构的形式将空白凭证退回仓库   
				//退回信息保存到过程表 ： 退回柜员号保存到guiyh中，凭证类型变为未领，清空领用机构和领用机构负责人
				Pingzgcb pingz = new Pingzgcb();
				pingz.setGuiyh(guiyh);
				pingz.getPingzgcbid().setPingzlx(pingzlx);
				pingz.setPingzzt("已领");     //凭证状态“2” 表示已领
				
				//退回信息到存到库存剩余表      
				Pingzkcsyb pzkc = new Pingzkcsyb();
				pzkc.getPingzkcsybid().setJigh(jigh);
				pzkc.getPingzkcsybid().setPingzlx(pingzlx);
				
				//退回信息保存到机构剩余表       
				Pingzjgsyb pzjg = new Pingzjgsyb();
				pzjg.getPingzjgsybid().setJigh(jigh);
				pzjg.getPingzjgsybid().setPingzlx(pingzlx);
				zhongkpzService.savetogc(guiyh,qispzh_,zhongzpzh_,pingz,pzkc,pzjg);
				
			}
			return this.showMessageJSP(actionMapping, request, "pingzth", getPromptService().getPromptMsg("zhongk_th", map));
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzth");
		} finally {
		}
	}
	
	/*
	 *凭证作废页面跳转 
	 */ 
	public ActionForward add_zf(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			return actionMapping.findForward("pingzzf");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}
	
	/*
	 * 保存作废信息
	 */ 
	public ActionForward saveZuof(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {

			//获取控制参数的类
			SystemConfig systemConfig = SystemConfig.getInstance();
			//获取Form中的值
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
            String pingzlx = pzform.getPingzlx();
            String guiyh = pzform.getGuiyh();
            String qispzh = pzform.getQispzh_();
            String bens = pzform.getBens();
            String zhongzpzh = pzform.getZhongzpzh_();
            
            int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//本数不能大于50的校验
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "入库本数不能大于50！");	
			}
            
          //起始凭证号、本数、作废柜员的输入效验
			if (("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "请输入起始凭证号、本数、作废柜员！");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "请输入起始凭证号、本数！");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "请输入起始凭证号、作废柜员！");
			}else if (("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "请输入本数、作废柜员！");
			}
			//保存作废信息
			Pingzgcb pingz = new Pingzgcb();
			Pingzkcsyb pzkc = new Pingzkcsyb();
			Pingzjgsyb pzjg = new Pingzjgsyb();
			pingz.getPingzgcbid().setPingzlx(pingzlx);
			zhongkpzService.savezf(qispzh, zhongzpzh, guiyh, pingz, pzkc, pzjg);
			return this.showMessageJSP(actionMapping, request, "pingzzf", getPromptService().getPromptMsg("zhongk_zf", map));
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzzf");
		} finally {
		}
	}
	
	/*
	 * 删除入库信息
	 */ 
	public ActionForward saveQzrk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		    Map map = new HashMap();
		    Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");//获取当前登录柜员信息
		try {
			//获取控制参数的类
			SystemConfig systemConfig = SystemConfig.getInstance();
			// 获取Form中的值
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
			String qispzh = pzform.getQispzh_();
			String bens = pzform.getBens();
			String zhongzpzh = pzform.getZhongzpzh_();
			String rukjg = pzform.getRukjg();
			String pingzlx = pzform.getPingzlx();
			
			int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//本数不能大于50的校验
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "入库本数不能大于50！");	
			}
			
			//本数 不能为“0”、“00”、“000”、“0000”校验
			if("0".equals(bens)||"00".equals(bens)||"000".equals(bens)||"0000".equals(bens)||"00000".equals(bens))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "入库本数不能为0！");	
			}
			//起始凭证号、本数、柜员的输入效验
			if (("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "请输入起始凭证号、本数、入库机构！");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "请输入起始凭证号、本数！");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "请输入起始凭证号、入库机构！");
			}else if (("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "请输入本数、入库机构！");
			}
			
			//通过起始、终止凭证号计算本次入库凭证总张数
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long zhangsLong = zhongzpzhLong - qispzhLong + 1;//入库总张数
			String zhangsString = String.valueOf(zhangsLong);
			int zhangsInt = Integer.parseInt(zhangsString);
			
			//保存入库信息（过程表、库存剩余表）
			Pingzkcsyb pzs = new Pingzkcsyb();
			pzs.getPingzkcsybid().setJigh(pzform.getRukjg());
		    pzs.getPingzkcsybid().setPingzlx(pzform.getPingzlx());
		    pzs.setShengyzs(zhangsInt);
		    pzs.setZongs(zhangsInt);
		    zhongkpzService.saveQzrk(qispzh,zhongzpzh,pzform.getPingzlx(),pzform.getRukrq(),pzform.getRukjg()); 
		  //记录日志
			String content = "删除入库凭证(起始凭证号为" + qispzh + ";终止凭证号为:"+zhongzpzh+";凭证类型为："+pingzlx+")";
			this.createManageLog(clerk.getCode(), content);
			return this.showMessageJSP(actionMapping, request, "pingzsc", getPromptService().getPromptMsg("zhongk_sc", map));//调用提示信息
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzsc");
		} finally {
		}
	}
	
	/*
	 * 凭证删除入库界面跳转
	 */ 
	public ActionForward ruksc(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//界面加载凭证种类
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			
			//获取当前日期
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			request.getSession().setAttribute("date", date);
			
			//界面加载机构号
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//柜员所属机构号
			String orgcode = clerk.getOrgcode();
			request.getSession().setAttribute("orgcode", orgcode);
			return actionMapping.findForward("pingzsc");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// 必执行
		}
	}
	
	
	
}
