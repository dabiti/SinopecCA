package com.unitop.sysmgr.action.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.unitop.config.SystemConfig;
import com.unitop.framework.util.IPTool;
import com.unitop.sysmgr.action.ExDispatchAction;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.InitSystem;
import com.unitop.sysmgr.service.OrgService;
import com.unitop.sysmgr.service.PrivilegeService;
import com.unitop.sysmgr.service.impl.ControlRights;

/* 
 * �������¼DEMO ʵ��-�ο�������Ŀ��
 * ģ�����ƣ������¼SSO
 * ����������ͨ�������з��ṩCommInterface_test.jar�� ���з���ͳһ��֤ƽ̨����У���û�
 * by ldd
 */
public class SSOLoginAction extends ExDispatchAction {
	@Resource
	private ControlRights controlRights;	
	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private OrgService OrgService;
	@Resource
	private InitSystem InitSystem;
	
	/*
	 *  �����¼ ���
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		//��ʼ��
		SystemConfig systemConfig = SystemConfig.getInstance();
		Date rightNow = Calendar.getInstance().getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(rightNow);
		try {
			//ͬ������
			InitSystem.synchronousSystemParameters();
			//�汾����
			String state = controlRights.control();
			if("��������".equals(state))
			{
				return this.showMessageJSP(mapping,request,"login.error","��ϵͳΪ���ð棬���������ѹ���");
			}else if("�ն�����".equals(state))
			{
				return this.showMessageJSP(mapping, request, "login.error", "ͬʱ�������������ޣ�");
			}else if("�汾����".equals(state))
			{
				return this.showMessageJSP(mapping, request, "login.error", "û��Ȩ��ʹ�ô�ϵͳ");
			}
			if("shiyong".equals(controlRights.getControlMap().get("version")))
			{
				String product_period = "�����ڣ�"+ controlRights.getControlMap().get("productperiod")+"  �汾�ţ�"+controlRights.getControlMap().get("versionnum");
				request.getSession().setAttribute("product_period",product_period);
			}else{
				request.getSession().setAttribute("product_period","��ʽ��   �汾�ţ�"+controlRights.getControlMap().get("versionnum"));
			}
			//��ȡԱ���š�����
			String ssoAuth = request.getParameter("ssoAuth");
			String ssoSign = request.getParameter("ssoSign");
			
			//���û�л�ȡ�������㴫��������о�����ʾ
			if(ssoAuth==null||ssoSign==null)
			{
				return this.showMessageJSP(mapping, request, "login.error", "���棺�ܾ��Ƿ�����!");
			}
				
            /**
             * GetKeys�Ĳ������壺
             * 1.�Ƿ�����ʹ��ͳһ��֤
             * 2.��ϵͳ����
             * 3.��ϵͳ���ڵķ���������ַ
             * 4.ͳһ��֤���������ڵĸ���ַ
             * 5.�̶��ַ��������ֲ���
             */
	      //GetKeys keys = new GetKeys(true,systemConfig.getValue("SSOlocalServerName"),systemConfig.getValue("localServerAddress"),systemConfig.getValue("SSOServerAddress"),systemConfig.getValue("SSOServerString"));
	        
	        /**
	         * ���û���½ʱ���õ��û��Ĺ�Ա�š�
	         * ����ֵʾ�����£�A0210
	         * �ڴ˴����������ַ��������Ѿ����ڣ�ͨ����ϵͳ��֤���������з���ֵ
	         * temp2.getSSOEmployeeCode()�����������壺
	         * 1.ǰ�����ɵ�keys�����ڵ�serviceconfig����
	         * 2.��ͳһ��֤ϵͳ�������ĵ�һ�����ܴ�
	         * 3.��ͳһ��֤ϵͳ�������ĵڶ������ܴ�
	         */
	        //ServiceTool serviceTool = new ServiceTool();
	        String ssoYuangh = ""; //serviceTool.getSSOEmployeeCodeFromAtDecoded(keys.getServiceConfig(),ssoAuth,ssoSign);
	        
	        HttpSession session = request.getSession();
	        Clerk sessionClerk = (Clerk) session.getAttribute("clerk");
	        if(sessionClerk!=null)
	        {
	        	return this.showMessageJSP(mapping, request, "login.error","��Ա��Ա��[<a title='���ע��' href='logout.do?logoutMark="+sessionClerk.getCode()+"'>"+sessionClerk.getCode()+"</a>]�ڱ����Ѿ���½ϵͳ�����ܴ˹�Ա��Ա��������ע����");
	        }
	        
			String clerkCode = ssoYuangh;
			Clerk clerk = clerkManageService.getClerkByCode(clerkCode);
			 // ���жϹ�Ա�Ƿ����
			if (clerk == null||"".equals(clerk.getCode()))
			{
				return this.showMessageJSP(mapping, request, "login.error", "Ա���Ų�����!");
			}else{
				//��ù�Ա��Ϣ
				Org parent_org = OrgService.getOrgByCode(clerk.getShOrgCode());
				clerk.setShOrgName(parent_org.getName());
				Org org = OrgService.getOrgByCode(clerk.getOrgcode());
				if (org==null)
				{
					return this.showMessageJSP(mapping,request,"login.error","��½ʧ�ܣ��˹�Ա����������"+clerk.getOrgcode()+"�����ڣ�");
				}
				clerk.setOrgname(org.getName());
				/*
				 	* ��ȡ��Ա��ɫȨ�޼���
				 */
				Map juesMap =  privilegeService.getPrivilegeForMenue(clerk.getCode());
				clerk.setJuesMap(juesMap);
				
				String roleName = clerkManageService.getClerkByOrgClerkName(clerk.getCode());
				clerk.setPostName(roleName);
			}
			String ipaddress = IPTool.getIpAddr(request);
			clerk.setIp(ipaddress);
			session.setMaxInactiveInterval(Integer.valueOf(systemConfig.getValue("outtime")));
			//��¼��Ա��¼����
			clerk.setLoginDate(date);
			session.setAttribute("clerk", clerk);
			this.createManageLog(clerkCode, "��½");		
			ActionForward forward = new ActionForward() ;
			forward.setPath("/index.jsp");
			forward.setRedirect(true);
			return forward;
		} catch (Exception e) {
			return this.showMessageJSP(mapping, request, "login.error", "�����½�쳣��"+e.getMessage());
		}
	}
}