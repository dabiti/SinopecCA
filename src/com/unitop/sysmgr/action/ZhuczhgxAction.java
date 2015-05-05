package com.unitop.sysmgr.action;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.form.ZhuczhgxForm;
import com.unitop.sysmgr.service.ZhanghbService;
import com.unitop.sysmgr.service.ZhuczhgxService;
import com.unitop.sysmgr.service.impl.ZhuczhgxServiceImpl;

@Controller("/zhuczh")
public class ZhuczhgxAction extends ExDispatchAction{
	
	@Resource 
	private ZhuczhgxService zhuczhgxService;
	@Resource 
	private ZhanghbService zhanghbService;

	/*
	 * ά�������˻���ϵҳ����ת
	 * by wp
	 */
	public ActionForward zhuczh(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			return actionMapping.findForward("zhuczhgx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "zhuczhgx");
		} finally {
			// ��ִ��
		}
	}
	
	/*
	 * ά�������˻���ϵ
	 * by wp
	 */
	public ActionForward weihzczhgx(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		ZhuczhgxForm zhucform = (ZhuczhgxForm) actionForm;
		String zhangh_old = zhucform.getZhangh();
		try {
			//�˺Ų�Ϊ��У��
			if (zhangh_old==null||"".equals(zhangh_old))
			{
				return this.showMessageJSP(actionMapping, request, "zhuczhgx", "�������˺ţ�");
				}else{
					Zhanghb zhanghb = zhanghbService.getZhanghb(zhangh_old);
					//��֤�˻��Ƿ����
					if(zhanghb.getZhangh()==null||"".equals(zhanghb.getZhangh()))
					{
						return super.showMessageJSP(actionMapping, request, "zhuczhgx", "�˺ţ�["+zhangh_old+"]������!");
					}
				}
			
			//���˻��б�
			List list = zhuczhgxService.getzizh(zhangh_old);
			request.setAttribute("list", list);//jspҳ��{list}������ʾ
			System.out.println(list.size());
			if(list.size()==0)
			{
				//listΪ�ձ�ʾ���˻��������˻�
				return this.showMessageJSP(actionMapping, request, "zhuczhgx", "�������˻��������˻���");
			}
			return actionMapping.findForward("zhuczhgx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "zhuczhgx");
		} finally {
			// ��ִ��
		}
	}
	
	/*
	 * �½����ӹ�ϵҳ����ת
	 * by wp
	 */
	public ActionForward add(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			//��ȡ��ǰ����
			String date = this.getSystemMgrService().getSystetemNowDate().substring(0,10);
			request.getSession().setAttribute("date", date);
			return actionMapping.findForward("xinjgx");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "xinjgx");
		} finally {
			// ��ִ��
		}
	}
	
	/*
	 * ��ȡ���˻���Ϣ���½����ӹ�ϵ����AJAX��
	 * by wp
	 */ 
	public ActionForward getcongzh(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			//��ȡ������˻�
			String congzh = request.getParameter("congzh");
			String congzhname = ""; //���˻�����
			String congzhState ="";//���˻��˻�״̬
			String congzhVerifyState="";//ӡ�����״̬
			String youwyj="";//����ӡ��
			ZhuczhgxServiceImpl zhuczhgxServiceImpl = (ZhuczhgxServiceImpl) this.zhuczhgxService;
			Zhanghb zhanghb = zhuczhgxServiceImpl.zhanghbDao.getZhanghb(congzh);
			if(zhanghb!=null)
			{
				congzhname = zhanghb.getHum();
				congzhState = zhanghb.getZhanghzt();
				congzhVerifyState = zhanghb.getYinjshzt();
				youwyj = zhanghb.getYouwyj();
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			String accountin = congzhname + "," + congzhState + "," + congzhVerifyState + "," + youwyj;
			out.println(accountin);
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "null");
		} 
	}
	
	/*
	 * ��ȡ���˻���Ϣ���½����ӹ�ϵ����AJAX��
	 * js�е�
	 * by wp
	 */ 
	public ActionForward getzhuzh(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		try {
			//��ȡ�������˻�
			String mainAccount = request.getParameter("mainAccount");
			String mainAccountName="";//����
			String mainAccountState = ""; //�˻�״̬
			String mainAccountVerifyState ="";//�˻����״̬
			String mainYouwyj="";//����ӡ��
			
			ZhuczhgxServiceImpl zhuczhgxServiceImpl = (ZhuczhgxServiceImpl) this.zhuczhgxService;
			Zhanghb zhanghb = zhuczhgxServiceImpl.zhanghbDao.getZhanghb(mainAccount);
			if(zhanghb!=null)
			{
				mainAccountName = zhanghb.getHum();
				mainAccountState  = zhanghb.getZhanghzt();
				mainAccountVerifyState = zhanghb.getYinjshzt();
				mainYouwyj = zhanghb.getYouwyj();
			}
			response.setContentType("text/xml");
			response.setLocale(Locale.SIMPLIFIED_CHINESE);
			PrintWriter out = response.getWriter();
			String accountin = mainAccountName + "," + mainAccountState + "," + mainAccountVerifyState + "," + mainYouwyj;
			out.println(accountin);
			out.close();
			return null;
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "null");
		} 
	}
	
	/*
	 * �½����ӹ�ϵ
	 * by wp
	 */
	public ActionForward xinjgx(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");//��ȡ��ǰ��¼��Ա��Ϣ
		try {
			//��ȡ���������˻���Ϣ
			ZhuczhgxForm zhucform = (ZhuczhgxForm) actionForm;
			String congzh = zhucform.getCongzh();// ���˻�
			String mainaccount = zhucform.getMainAccount();//���˻�
			String fuyrq = zhucform.getFuyrq();//��������
			
			//���벻Ϊ��У��
			if(congzh==null||"".equals(congzh)){
				return super.showMessageJSP(actionMapping, request, "xinjgx", "���˻�����Ϊ�գ�");
			}
			if(fuyrq==null||"".equals(fuyrq)){
				return super.showMessageJSP(actionMapping, request, "xinjgx", "�������ڲ���Ϊ�գ�");
			}
			if(mainaccount==null||"".equals(mainaccount)){
				return super.showMessageJSP(actionMapping, request, "xinjgx", "���˻�����Ϊ�գ�");
			}
			
			//��ȡ�����˻�ӡ����Ϣ
			Zhanghb zhanghb_c = zhanghbService.getZhanghb(congzh);
			Zhanghb zhanghb_z = zhanghbService.getZhanghb(mainaccount);
			
			//���潨�������ӹ�ϵ
			zhuczhgxService.saveyinj(fuyrq,congzh,mainaccount,zhanghb_c,zhanghb_z);
			
			//��¼��־
			String content = "�½������˻���ϵ(���˻�Ϊ" + mainaccount + ";��Ա����:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(mainaccount ,"�½������˻���ϵ", "�½������˻���ϵ", clerk);
			String content_ = "�½������˻���ϵ(���˻�Ϊ" + congzh + ";��Ա����:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content_);
			this.createAccountManageLog(congzh,"�½������˻���ϵ", "�½������˻���ϵ", clerk);
			return super.showMessageJSP(actionMapping, request, "xinjgx","�½����ӹ�ϵ�ɹ�!");
		} catch (Exception e) {
			e.printStackTrace();
			return this.errrForLogAndException(e, actionMapping, request, "xinjgx");
		} finally {
			// ��ִ��
		}
	}
	
	/*
	 * ȡ�����ӹ�ϵ
	 * by wp
	 */
	public ActionForward quxgx(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response) 
	{
		Clerk clerk = (Clerk) request.getSession().getAttribute("clerk");//��ȡ��ǰ��¼��Ա��Ϣ
		try {
			String czhangh = request.getParameter("czhangh");//ͨ��URL���ݵĴ��˻�
			String zzhangh = request.getParameter("zzhangh");//ͨ��URL���ݵĴ��˻������˻�
			// ��ȡ��ǰʱ��  //ȡ����������
			String date = this.getSystemMgrService().getSystetemNowDate().substring(0,10);
			zhuczhgxService.doquxgx(czhangh,zzhangh,date);
			//��¼��־
			String content = "ȡ�������˻���ϵ(���˻�Ϊ" + zzhangh + ";��Ա����:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content);
			this.createAccountManageLog(zzhangh ,"ȡ�������˻���ϵ", "ȡ�������˻���ϵ", clerk);
			String content_ = "ȡ�������˻���ϵ(���˻�Ϊ" + czhangh + ";��Ա����:"+clerk.getCode()+")";
			this.createManageLog(clerk.getCode(), content_);
			this.createAccountManageLog(czhangh,"�½������˻���ϵ", "�½������˻���ϵ", clerk);
			return super.showMessageJSP(actionMapping, request, "zhuczhgx","ȡ�������˻���ϵ�ɹ�");
		} catch (Exception e) {
			return this.errrForLogAndException(e, actionMapping, request, "zhuczhgx");
		} finally {
			// ��ִ��
		}
	}
}