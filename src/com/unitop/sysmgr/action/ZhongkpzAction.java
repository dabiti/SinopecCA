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
	 * ƾ֤���ҳ����ת
	 */ 
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//�������ƾ֤����
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			
			//��ȡ��ǰ����
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			request.getSession().setAttribute("date", date);
			
			//������ػ�����
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//��Ա����������
			String orgcode = clerk.getOrgcode();
			request.getSession().setAttribute("orgcode", orgcode);
			return actionMapping.findForward("pingzrk");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}
	
	/*
	 * ��ȡƾ֤����(�ڲ�ר��)
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
	 * ���������Ϣ
	 */ 
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			//��ȡ���Ʋ�������
			SystemConfig systemConfig = SystemConfig.getInstance();
			// ��ȡForm�е�ֵ
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
			String qispzh = pzform.getQispzh_();
			String bens = pzform.getBens();
			String zhongzpzh = pzform.getZhongzpzh_();
			String rukjg = pzform.getRukjg();
			String pingzlx = pzform.getPingzlx();
			
			int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//�������ܴ���50��У��
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "��Ȿ�����ܴ���50��");	
			}
			
			//���� ����Ϊ��0������00������000������0000��У��
			if("0".equals(bens)||"00".equals(bens)||"000".equals(bens)||"0000".equals(bens)||"00000".equals(bens))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "��Ȿ������Ϊ0��");	
			}
			//��ʼƾ֤�š���������Ա������Ч��
			if (("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "��������ʼƾ֤�š���������������");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "��������ʼƾ֤�š�������");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "��������ʼƾ֤�š���������");
			}else if (("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzrk", "�����뱾������������");
			}
			// ��֤������ƾ֤���Ƿ��Ѵ���
			List list = zhongkpzService.findPingzByPingzh(qispzh, zhongzpzh,pingzlx);
			if(!(list.size()==0)){
				return this.showMessageJSP(actionMapping, request, "pingzrk", "ƾ֤���Ѵ��ڣ�");	
			}
			
			//ͨ����ʼ����ֹƾ֤�ż��㱾�����ƾ֤������
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long zhangsLong = zhongzpzhLong - qispzhLong + 1;//���������
			String zhangsString = String.valueOf(zhangsLong);
			int zhangsInt = Integer.parseInt(zhangsString);
			
			//���������Ϣ�����̱����ʣ���
			Pingzkcsyb pzs = new Pingzkcsyb();
			pzs.getPingzkcsybid().setJigh(pzform.getRukjg());
		    pzs.getPingzkcsybid().setPingzlx(pzform.getPingzlx());
		    pzs.setShengyzs(zhangsInt);
		    pzs.setZongs(zhangsInt);
		    zhongkpzService.savePingz(qispzh,zhongzpzh,pzform.getPingzlx(),pzform.getRukrq(),pzform.getRukjg(),pzs); 
			return this.showMessageJSP(actionMapping, request, "pingzrk", getPromptService().getPromptMsg("zhongk_rk", map));//������ʾ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzrk");
		} finally {
		}
	}
	
	/*
	 * ��ȡƾ֤ÿ����������⣩
	 */ 
	public ActionForward getPingzmbzs(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//��ȡ����ƾ֤����ID
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
	 * ƾ֤����ҳ����ת
	 */ 
	public ActionForward add_ly(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//��Ӻ�������ƾ֤����
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			//������ػ�����
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//��Ա����������
			String orgcode = clerk.getOrgcode();
			String code =clerk.getCode();
			request.getSession().setAttribute("code", code);
			request.getSession().setAttribute("orgcode", orgcode);
			return actionMapping.findForward("pingzly");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}
	
	/*
	 * ����������Ϣ
	 */ 
	public ActionForward saveLingy(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			
			//��ȡ���Ʋ�������
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
			//�������ܴ���50��У��
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzly", "��Ȿ�����ܴ���50��");	
			}
			//����������Ч��
			if (("".equals(bens)))
			{
				return this.showMessageJSP(actionMapping, request, "pingzly", "���������ñ�����");
			}
			
			if("����".equals(lingymode))
			{    
				if(pzform.getLingyjg()==null||"".equals(pzform.getLingyjg())||pzform.getJiglyfzr()==null||"".equals(pzform.getJiglyfzr()))
				{
					return this.showMessageJSP(actionMapping, request, "pingzly", "���û��������û��������˲���Ϊ�գ�");
					
				}

				//��������  1���ɱ���������Ҫ���õ�����   2���жϱ������Ƿ���ʣ��  3�����㹻����ٱ���������Ϣ
				//������Ϣ������ƾ֤���̱�
				Pingzgcb pingz = new Pingzgcb();
				pingz.getPingzgcbid().setPingzlx(pzform.getPingzlx());
				pingz.setLingyjg(pzform.getLingyjg());
				pingz.setJiglyfzr(pzform.getJiglyfzr());
				pingz.setPingzzt("δ��");    //ƾ֤״̬  ��1�� ��ʾδ��
				
				//������Ϣ������ƾ֤���ʣ���
				Pingzkcsyb pzkc = new Pingzkcsyb();
				pzkc.getPingzkcsybid().setJigh(clerk.getOrgcode());//����ʱ�ĵ�½����
				pzkc.getPingzkcsybid().setPingzlx(pzform.getPingzlx());
				
				//������Ϣ������ƾ֤����ʣ���
				Pingzjgsyb pzjg = new Pingzjgsyb();
				pzjg.getPingzjgsybid().setJigh(pzform.getLingyjg());
				pzjg.getPingzjgsybid().setPingzlx(pzform.getPingzlx());
				String qispzh=zhongkpzService.saveLybyJg(jiglyfzr,bens,pingz,pzkc,pzjg);
				String content="��ʼƾ֤��Ϊ"+qispzh;
				return this.showMessageJSP(actionMapping, request, "pingzly", "[ƾ֤���óɹ�] " + content);
			}else
			{
				if(pzform.getGuiyh()==null||"".equals(pzform.getGuiyh()))
				{
					return this.showMessageJSP(actionMapping, request, "pingzly", "���ù�Ա����Ϊ�գ�");
					
				}
				//��������  1���ɱ���������Ҫ���õ����� 2���жϱ������Ƿ���ʣ��3�����㹻����ٱ���������Ϣ
				//������Ϣ������ƾ֤���̱�
				Pingzgcb pingz = new Pingzgcb();
				pingz.getPingzgcbid().setPingzlx(pzform.getPingzlx());
				pingz.setGuiyh(pzform.getGuiyh());//���ù�Ա
				//pingz.setLingyjg(clerk.getOrgcode());
				pingz.setPingzzt("����");    //ƾ֤״̬��2�� ��ʾ����
				
				//������Ϣ������ƾ֤����ʣ���
				String jigh = clerk.getOrgcode();//���ù�Ա���ڻ���
				Pingzjgsyb pzjg = new Pingzjgsyb();
				pzjg.getPingzjgsybid().setJigh(clerk.getOrgcode());
				pzjg.getPingzjgsybid().setPingzlx(pzform.getPingzlx());
				String qispzh=zhongkpzService.saveLybyGr(Guiyh,bens,pingz,pzjg,jigh);
				String content="��ʼƾ֤��Ϊ"+qispzh;
				return this.showMessageJSP(actionMapping, request, "pingzly", "[ƾ֤���óɹ�] " + content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzly");
		} finally {
		}
	}
	/*
	 * ƾ֤�˻�ҳ����ת
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
			// ��ִ��
		}
	}
	
	/*
	 * �����˻���Ϣ
	 */ 
	public ActionForward saveTuih(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {
			
			//��ȡ���Ʋ�������
			SystemConfig systemConfig = SystemConfig.getInstance();
			//��ȡForm�е�ֵ
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
			//�������ܴ���50��У��
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "��Ȿ�����ܴ���50��");	
			}
			
			//��ȡ��ǰ��½��Ա�Ļ�����
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			String jigh = clerk.getOrgcode();//��ȡ��ǰ��½�����Ļ�����
			
			//��ʼƾ֤�š��������˻ع�Ա������Ч��
			if (("".equals(qispzh_)||qispzh_==null)&&("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "��������ʼƾ֤�š��������˻ع�Ա��");
			}else if(("".equals(qispzh_)||qispzh_==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "��������ʼƾ֤�š�������");
			}else if(("".equals(qispzh_)||qispzh_==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "��������ʼƾ֤�š��˻ع�Ա��");
			}else if (("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzth", "�����뱾�����˻ع�Ա��");
			}
			if("����".equals(tuihmode))
			{
				//�˻���Ϣ���浽����ʣ��� ��   
				Pingzjgsyb pzjg = new Pingzjgsyb();
				pzjg.getPingzjgsybid().setJigh(clerk.getOrgcode());//������¼�˻���Ϣ
				pzjg.getPingzjgsybid().setPingzlx(pingzlx);
				
				//�Թ�Ա����ʽ���հ�ƾ֤�˻ػ���             
				//�˻���Ϣ���浽���̱�  ����Ա�ű�Ϊ�˻ع�Ա�Ĺ�Ա�ţ�ƾ֤���ͱ�Ϊδ��
				Pingzgcb pingz = new Pingzgcb();
				pingz.getPingzgcbid().setPingzlx(pzform.getPingzlx());
				pingz.setGuiyh(guiyh);
				pingz.setPingzzt("����");  //ƾ֤״̬��2�� ��ʾ����
				zhongkpzService.saveTogc(guiyh,qispzh_,zhongzpzh_,pingz,pzjg);
			}else
			{
				//�Ի�������ʽ���հ�ƾ֤�˻زֿ�   
				//�˻���Ϣ���浽���̱� �� �˻ع�Ա�ű��浽guiyh�У�ƾ֤���ͱ�Ϊδ�죬������û��������û���������
				Pingzgcb pingz = new Pingzgcb();
				pingz.setGuiyh(guiyh);
				pingz.getPingzgcbid().setPingzlx(pingzlx);
				pingz.setPingzzt("����");     //ƾ֤״̬��2�� ��ʾ����
				
				//�˻���Ϣ���浽���ʣ���      
				Pingzkcsyb pzkc = new Pingzkcsyb();
				pzkc.getPingzkcsybid().setJigh(jigh);
				pzkc.getPingzkcsybid().setPingzlx(pingzlx);
				
				//�˻���Ϣ���浽����ʣ���       
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
	 *ƾ֤����ҳ����ת 
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
			// ��ִ��
		}
	}
	
	/*
	 * ����������Ϣ
	 */ 
	public ActionForward saveZuof(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
			Map map = new HashMap();
		try {

			//��ȡ���Ʋ�������
			SystemConfig systemConfig = SystemConfig.getInstance();
			//��ȡForm�е�ֵ
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
            String pingzlx = pzform.getPingzlx();
            String guiyh = pzform.getGuiyh();
            String qispzh = pzform.getQispzh_();
            String bens = pzform.getBens();
            String zhongzpzh = pzform.getZhongzpzh_();
            
            int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//�������ܴ���50��У��
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "��Ȿ�����ܴ���50��");	
			}
            
          //��ʼƾ֤�š����������Ϲ�Ա������Ч��
			if (("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "��������ʼƾ֤�š����������Ϲ�Ա��");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "��������ʼƾ֤�š�������");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "��������ʼƾ֤�š����Ϲ�Ա��");
			}else if (("".equals(bens)||bens==null)&&("".equals(guiyh)||guiyh==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzzf", "�����뱾�������Ϲ�Ա��");
			}
			//����������Ϣ
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
	 * ɾ�������Ϣ
	 */ 
	public ActionForward saveQzrk(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		    Map map = new HashMap();
		    Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");//��ȡ��ǰ��¼��Ա��Ϣ
		try {
			//��ȡ���Ʋ�������
			SystemConfig systemConfig = SystemConfig.getInstance();
			// ��ȡForm�е�ֵ
			ZhongkpzForm pzform = (ZhongkpzForm) actionForm;
			String qispzh = pzform.getQispzh_();
			String bens = pzform.getBens();
			String zhongzpzh = pzform.getZhongzpzh_();
			String rukjg = pzform.getRukjg();
			String pingzlx = pzform.getPingzlx();
			
			int bensInt = Integer.parseInt(bens);
			String maxbens=systemConfig.getValue("maxbens");
			int maxbensInt=Integer.parseInt(maxbens);
			//�������ܴ���50��У��
			if(bensInt>maxbensInt)
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "��Ȿ�����ܴ���50��");	
			}
			
			//���� ����Ϊ��0������00������000������0000��У��
			if("0".equals(bens)||"00".equals(bens)||"000".equals(bens)||"0000".equals(bens)||"00000".equals(bens))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "��Ȿ������Ϊ0��");	
			}
			//��ʼƾ֤�š���������Ա������Ч��
			if (("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "��������ʼƾ֤�š���������������");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(bens)||bens==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "��������ʼƾ֤�š�������");
			}else if(("".equals(qispzh)||qispzh==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "��������ʼƾ֤�š���������");
			}else if (("".equals(bens)||bens==null)&&("".equals(rukjg)||rukjg==null))
			{
				return this.showMessageJSP(actionMapping, request, "pingzsc", "�����뱾������������");
			}
			
			//ͨ����ʼ����ֹƾ֤�ż��㱾�����ƾ֤������
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long zhangsLong = zhongzpzhLong - qispzhLong + 1;//���������
			String zhangsString = String.valueOf(zhangsLong);
			int zhangsInt = Integer.parseInt(zhangsString);
			
			//���������Ϣ�����̱����ʣ���
			Pingzkcsyb pzs = new Pingzkcsyb();
			pzs.getPingzkcsybid().setJigh(pzform.getRukjg());
		    pzs.getPingzkcsybid().setPingzlx(pzform.getPingzlx());
		    pzs.setShengyzs(zhangsInt);
		    pzs.setZongs(zhangsInt);
		    zhongkpzService.saveQzrk(qispzh,zhongzpzh,pzform.getPingzlx(),pzform.getRukrq(),pzform.getRukjg()); 
		  //��¼��־
			String content = "ɾ�����ƾ֤(��ʼƾ֤��Ϊ" + qispzh + ";��ֹƾ֤��Ϊ:"+zhongzpzh+";ƾ֤����Ϊ��"+pingzlx+")";
			this.createManageLog(clerk.getCode(), content);
			return this.showMessageJSP(actionMapping, request, "pingzsc", getPromptService().getPromptMsg("zhongk_sc", map));//������ʾ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "pingzsc");
		} finally {
		}
	}
	
	/*
	 * ƾ֤ɾ����������ת
	 */ 
	public ActionForward ruksc(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//�������ƾ֤����
			List<Voucher> pingzlxlist = findPingzlx(request);
			request.getSession().setAttribute("pingzlxlist", pingzlxlist);
			
			//��ȡ��ǰ����
			Date rightNow = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(rightNow);
			request.getSession().setAttribute("date", date);
			
			//������ػ�����
			Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");
			//��Ա����������
			String orgcode = clerk.getOrgcode();
			request.getSession().setAttribute("orgcode", orgcode);
			return actionMapping.findForward("pingzsc");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, null);
		} finally {
			// ��ִ��
		}
	}
	
	
	
}
