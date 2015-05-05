/**
 *<dl>
 *<dt><b>��Աҵ���߼�ʵ����</b></dt>
 *<dd></dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *��Update History��
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2011-11-4       IBM ETP      LiuShan		    create
 */
package com.unitop.sysmgr.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.unitop.sysmgr.dao.ClerkDao;
import com.unitop.sysmgr.dao.OrgDao;
import com.unitop.sysmgr.dao.RoleDao;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.config.DBinfoConig;
import com.unitop.config.SystemConfig;
import com.unitop.exception.BusinessException;
import com.unitop.framework.util.ExpOrImp;
import com.unitop.framework.util.PasswordUtil;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.GuiyjsgxbId;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.bo.Role;
import com.unitop.sysmgr.bo.Shouqrzb;
import com.unitop.util.ByteArrayUtil;
/**
 * @author LiuShan
 */
@Service("ClerkManageServiceImpl")
public class ClerkManageServiceImpl extends BaseServiceImpl implements ClerkManageService {
	 
	@Resource
	public ClerkDao clerkDao = null;
	@Resource
	private RoleDao roleDao;
	@Resource
	private OrgDao orgDao;
	
	

	/**
	 * @see com.unitop.sysmgr.service.ClerkManageService#importClerk()
	 */
	public boolean importClerk(HSSFSheet sheet) throws Exception {
		List list = ExpOrImp.importExcel(sheet, 8);
		boolean isImport = false;
		Connection conn = this.getBaseJDBCDao().getConnection();
		clerkDao.set_Connection(conn);
		try{
			for(int i=0;i<list.size();i++)
			{
				Map map = (Map)list.get(i);
				Clerk clerk = new Clerk();
				clerk.setCode((String)map.get(0));
				clerk.setName((String)map.get(1));
				clerk.setPassword((String)map.get(2));
				clerk.setPostCode((String)map.get(3));
				clerk.setOrgcode((String)map.get(4));
				clerk.setCreator((String)map.get(5));
				clerk.setShOrgCode((String)map.get(6));
				clerk.setWdFlag((String)map.get(7));
				Clerk clerk_ = clerkDao.getClerkByCode(clerk.getCode());
				if(clerk_ != null)
				{
					throw new BusinessException("����ʧ�ܣ�["+clerk.getCode()+"]��Ա�Ѿ�����!");
				}
				isImport = clerkDao.importClerk(clerk);
			}
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw (e);
		}finally{
			clerkDao.shifConnection();
		}
		return isImport;
	}

	/**
	 * @see com.unitop.sysmgr.service.ClerkManageService#getClerkByOrgcode(java.lang.String)
	 */
	public List<Clerk> getClerkByOrgcode(String orgcode) throws Exception {
		List clerkList  = clerkDao.getClerkByOrgcode(orgcode);
		for(int i=0; i<clerkList.size();i++)
		{
			Clerk clerk = (Clerk) clerkList.get(i);
			List roleList = roleDao.getRoleListByClerkCode(clerk.getCode());
			String postName="";
			for(int j=0; j<roleList.size();j++)
			{
				GuiyjsgxbId g = (GuiyjsgxbId) roleList.get(j);
				if(j==0||j==roleList.size()-1)
					postName+= "["+g.getJuesmc()+"]";
				else
					postName+= "["+g.getJuesmc()+"]";
			}
			clerk.setPostName(postName);
		}
		return clerkList;
	}
	
	public String getClerkByOrgClerkName(String clerkCode) throws Exception {
			List roleList = roleDao.getRoleListByClerkCode(clerkCode);
			String postName="";
			for(int j=0; j<roleList.size();j++)
			{
				GuiyjsgxbId g = (GuiyjsgxbId) roleList.get(j);
				if(j==0||j==roleList.size()-1)
					postName+= "["+g.getJuesmc()+"]";
				else
					postName+= "["+g.getJuesmc()+"]";
			}
		return postName;
	}
	
	//��ȡ��Ա��ɫ��Ϣ 
	private String getClerkByOrgClerkGjsgxbxx(String clerkCode) throws Exception {
		List roleList = roleDao.getRoleListByClerkCode(clerkCode);
		String postName="";
		for(int j=0; j<roleList.size();j++)
		{
			GuiyjsgxbId g = (GuiyjsgxbId) roleList.get(j);
			if(j==0||j==roleList.size()-1)
				postName+= "["+g.getJuesid()+":"+g.getJuesmc()+"]";
			else
				postName+= ",["+g.getJuesid()+":"+g.getJuesmc()+"]";
		}
		return postName;
	}

	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.service.ClerkManageService#exportClerk(java.lang.String)
	 */
	public List exportClerk(String orgcode,String include) throws Exception {
		List message = null;
		if("0".equals(include)){
			List list = clerkDao.getAllClerkByOrgcode(orgcode);
			for(int i=0;i<list.size();i++)
			{
				Clerk clerk = (Clerk) list.get(i);
				clerk.setPostCode(this.getClerkByOrgClerkGjsgxbxx(clerk.getCode()));
			}
			message = setMap(list);
		}else{
			List list = clerkDao.exportClerk(orgcode);
			for(int i=0;i<list.size();i++)
			{
				Clerk clerk = (Clerk) 	list.get(i);
				clerk.setPostCode(this.getClerkByOrgClerkGjsgxbxx(clerk.getCode()));
			}
			message = setMap(list);
		}
		return message;
	}
	

	@SuppressWarnings({ "unchecked" })
	private List setMap(List clerkGroup){
		List list = new ArrayList();
		Map title = new HashMap();
		//���ñ�ͷ
		title.put(0, "��Ա����");
		title.put(1, "��Ա����");
		title.put(2, "��Ա����");
		title.put(3, "��ɫ");
		title.put(4, "������");
		title.put(5, "������Ա");
		title.put(6, "ʡ�л���");
		title.put(7, "�����ʶ");
		list.add(title);
		for(int i=0;i<clerkGroup.size();i++){
			Map map = new HashMap();
			Clerk clerk = (Clerk) clerkGroup.get(i);
			map.put(0, clerk.getCode());
			map.put(1, clerk.getName());
			map.put(2, clerk.getPassword());
			map.put(3, clerk.getPostCode());
			map.put(4, clerk.getOrgcode());
			map.put(5, clerk.getCreator());
			map.put(6, clerk.getShOrgCode());
			map.put(7, clerk.getWdFlag());
			list.add(map);
		}
		return list;
	}

	public Clerk getClerkCountByIp(String ip) {
		return clerkDao.getClerkCountByIp(ip);
	}

	public void setErrorNum(String clerkNum, String errorNum) throws Exception {
		clerkDao.setErrorNum(clerkNum, errorNum);
	}

	/**
	 * ��ȡ��Ա��ɫ����
	 */
	public String getClerkJusjb(String clerkNum){
		String juesjb= "0";
		//������Ա��ɫ�б� ���ҳ���Ա��ɫ����߽�ɫ����
		List clerkList = this.getRoleByClerk(clerkNum);
		for(int i=0;i<clerkList.size();i++)
		{
			Role clerkRole = (Role) clerkList.get(i);
			juesjb=Integer.valueOf(clerkRole.getJuesjb())>Integer.valueOf(juesjb)?clerkRole.getJuesjb():juesjb;
		}
	
		SystemConfig systemConfig = SystemConfig.getInstance();
		
		//���й���Ա
		Clerk clerk = this.getClerkByCode(clerkNum);
		if((clerk.getOrgcode()+systemConfig.getSuperManager()).equals(clerkNum))
		{
			juesjb=orgDao.getOrgByCode(clerk.getOrgcode()).getWdflag().equals("1")?"3":"2";
			
		}
		//���ϵͳ��������Ա��Ĭ�ϸ�����߽�ɫ����
		if (systemConfig.getAdminCode().equals(clerkNum))
		{
			juesjb = "4";
		}
		
		return juesjb;
	}
	
	
	/**
	 * ���ݹ�Ա��ɫ�����ѯ��Ա��ɫ�б�
	 */
	public List getAllRoleByClerk(Clerk clerk){
		if(clerk==null) clerk = new Clerk();
		String clerkCode = clerk.getCode();
		String juesjb = this.getClerkJusjb(clerkCode);
		return roleDao.getAllRoleByJuesjb(juesjb);
	}
	/**
	 * ���ݹ�Ա��ɫ����ͻ��������ѯ��Ա��ɫ�б�
	 */
	public List getAllRoleByClerk(Clerk clerk, String wdflag) {
		if(clerk==null) clerk = new Clerk();
		String clerkCode = clerk.getCode();
		String juesjb = this.getClerkJusjb(clerkCode);
		String juesjbByOrg="";
		if(wdflag!=null&&!wdflag.equals("")){
			juesjbByOrg=wdflag.equals("0")?"4":wdflag.equals("1")?"3":wdflag.equals("2")?"2":wdflag.equals("3")?"1":null;
		}
		if(juesjbByOrg!=null){
			juesjb = Integer.valueOf(juesjb)>=Integer.valueOf(juesjbByOrg)?juesjbByOrg:juesjb;
			
		}
		
		return roleDao.getAllRoleByJuesjb(juesjb);
	}
	
	/**
	 * ���ݹ�Ա�Ż�ȡ��ɫ�б���uf_seal����
	 */
	public List getRoleByClerk(String clerknum) {
		List list = clerkDao.getRoleByClerk(clerknum);
		if(list==null)list = new ArrayList();
		return list;
	}
	/**
	 * ���ݹ�Ա��ѯ�����ڸù�Ա������ɫ
	 * ��¼��Ա��delclerknum
	 * ������Ա��clerknum
	 */
	public List getElseRoleByClerk(String delclerknum,String clerknum) {
		String juesjb = this.getClerkJusjb(delclerknum);
		//new 20140227
		String wdflag=orgDao.getOrgByCode(clerkDao.getClerkByCode(clerknum).getOrgcode()).getWdflag();
		String juesjbByOrg="";
		if(wdflag!=null&&!wdflag.equals("")){
			juesjbByOrg=wdflag.equals("0")?"4":wdflag.equals("1")?"3":wdflag.equals("2")?"2":wdflag.equals("3")?"1":null;
		}
		if(juesjbByOrg!=null){
			juesjb = Integer.valueOf(juesjb)>=Integer.valueOf(juesjbByOrg)?juesjbByOrg:juesjb;
			
		}
		
		
		List list = clerkDao.getElseRoleByClerk(clerknum,juesjb);
		if(list==null)list = new ArrayList();
		return list;
	}
	
	//ɾ����Ա+��ɫ��ϵ
	public void deleteRoleByClerk(String clerknum) {
		Clerk clerk = clerkDao.getClerkByCode(clerknum);
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		clerkDao.set_Session(session);
		try {
			clerkDao.deleteClerk(clerk);
			clerkDao.deleteGuiyjsgxb(clerknum);
			transaction.commit();
		} catch (BusinessException e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			clerkDao.shifSession();
		}
	}
	
	public boolean updateClerkRoles(Clerk clerk,String[] jues){
		//��������
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		clerkDao.set_Session(session);
		try {
			clerkDao.updateClerkRoles(clerk.getCode(),jues,"");
			clerk.setPassword(PasswordUtil.encodePwd(clerk.getPassword()));
			clerkDao.saveOrUpdate(clerk);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}finally{
			clerkDao.shifSession();
		}
	}

	public void updateClerk(Clerk clerk) throws BusinessException {
		clerk.setPassword(PasswordUtil.encodePwd(clerk.getPassword()));
	    clerkDao.saveOrUpdate(clerk);
	}

	public void save(Clerk clerk, String[] juesid) throws Exception {
		// ��������
		Session session =super.getBaseHibernateDao().getHibernateSession();
		Transaction transaction = session.beginTransaction();
		clerkDao.set_Session(session);
		try{
			//���ܹ�Ա����
			clerk.setPassword(PasswordUtil.encodePwd(clerk.getPassword()));
			this.clerkDao.saveOrUpdate(clerk);
			this.clerkDao.deleteGuiyjsgxb(clerk.getCode());
			this.clerkDao.saveClerkRoles(clerk.getCode(), juesid);
			session.flush();
			transaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new Exception("��Ա�����쳣!");
		}finally{
			this.clerkDao.shifSession();
		}
	}
	
	/**
	 * @author Lhz
	 * @param clerkCode
	 * @return string[]
	 * ���ڻ�ȡ��Ա��Ϣ�Լ���󶨵�IP
	 */
	public String[] getClerkIP(String clerkCode) {
		Clerk clerk = clerkDao.getClerkByCode(clerkCode);
		String[] string = new String[] { "", "", "", "" };
		String orgCode = clerk.getOrgcode();
		Org org = orgDao.getOrgByCode(orgCode);
		string[0] = clerk.getName();
		string[1] = org.getName();
		string[2] = clerk.getIp();
		return string;
	}
	
	/**
	 * @author Lhz
	 * ͨ����ԱCode�õ�Clerk��Ա����
	 */
	public Clerk getClerkByCode(String code) {
		Clerk clerk = clerkDao.getClerkByCode(code);
		if (clerk != null)
		{
			clerk.setPassword(PasswordUtil.decodePwd(clerk.getPassword()));
		}else{
			clerk = new Clerk();
		}
		return clerk;
	}
	/**
	 * @author LDD
	 * ɾ����Ա
	 */
	public void deleteClerk(String code) throws BusinessException {
		Clerk clerk = this.getClerkByCode(code);
		clerkDao.deleteClerk(clerk);
	}
	
	public boolean CanOperDesClerkCode(Clerk OperClerk, String DesClerkCode)
			throws BusinessException {
//		Session session = this.getHibernateSession();
		Session session = this.getBaseHibernateDao().getHibernateSession();
		boolean ReturnValue = false;
		Clerk DesClerk = this.getClerkByCode(DesClerkCode);
		
		try {
			if (DesClerk == null ||"".equals(DesClerk.getCode()))
				throw new BusinessException("��Ա��[ " + DesClerkCode + " ] �����ڣ�");
			if (OperClerk.getOrgcode().equals(DesClerk.getOrgcode())) {
				ReturnValue = true;
			} else {
				String sql = "";
				String db_type = DBinfoConig.getDBType();
				if("oracle".equals(db_type))
				{
					sql="SELECT internalorganizationnumber as C FROM organarchives "
						+ "WHERE internalorganizationnumber IN ("
						+ "select internalorganizationnumber from organarchives connect by prior internalorganizationnumber=p_internalorganizationnumber start with internalorganizationnumber=:OperOrg"
						+ ") AND internalorganizationnumber=:DesOrg";
				}else{
					sql="SELECT internalorganizationnumber as C FROM organarchives "
						+ "WHERE internalorganizationnumber IN ("
						+ "select * from TABLE(ORGFUNCTION(:OperOrg))) AND internalorganizationnumber=:DesOrg";
				}
				SQLQuery qu = session.createSQLQuery(sql);
				qu.setString("OperOrg", OperClerk.getOrgcode());
				qu.setString("DesOrg", DesClerk.getOrgcode());
				List list = qu.list();
				ReturnValue = list.size() == 1;
			}
		} finally {
//			this.closeSession(session);
			this.getBaseHibernateDao().closeSession(session);
		}
		return ReturnValue;
	}
	
	/**
	 * @author lhz
	 * @param clerkCode
	 * �����IP
	 */
	public void ClearClerkIP(String clerkCode) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
//			Query query = getHibernateSession().createQuery("update Clerk set n_ip=null where code=:clerknum");
			Query query = session.createQuery("update Clerk set n_ip=null where code=:clerknum");
			query.setString("clerknum",clerkCode);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	/**
	 * @author lhz
	 * @param clerkCode
	 * ����������й�Ա��IP
	 */
	public void clearAllOrgclerkIP(String orgCode) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		try {
//			Query query = getHibernateSession().createQuery("update Clerk set n_ip=null where orgcode =:orgcode");
			Query query = session.createQuery("update Clerk set n_ip=null where orgcode =:orgcode");
			query.setString("orgcode",orgCode);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	
	public List getOrgclerkIP(String orgCode) {
		Session session = this.getBaseHibernateDao().getHibernateSession();
		List rlist = new ArrayList();
//		Session session = this.getHibernateSession();
		try{
			String hql = "from Clerk where (n_ip is not null or n_ip <> '') and orgcode=:organnum";
			Query qu = session.createQuery(hql);
			qu.setString("organnum", orgCode);
			rlist = qu.list();
			Org org = orgDao.getOrgByCode(orgCode);
			
			for (int i = 0; i < rlist.size(); i++)
			{
				Clerk clerk = new Clerk();
				clerk =(Clerk) rlist.get(i);
				clerk.setOrgname(org.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
//			this.closeSession(session);
			this.getBaseHibernateDao().closeSession(session);
		}
		return rlist;
	}

	public long getClerkCouns(){
		return this.clerkDao.getClerkCounts();
	}

	public void changePassword(Clerk clerk, String password) throws BusinessException {
		this.clerkDao.changePassword(clerk, password);
	}
	
	/**
	 * ������Ա
	 */
	public void createClerk(Clerk bo) throws BusinessException {
		bo.setPassword(PasswordUtil.encodePwd(bo.getPassword()));
		clerkDao.saveOrUpdate(bo);
	}
	
	
	/**
	 * ƴ�����б���
	 * @param orgCode
	 * @param clerkCode
	 * @param passwd
	 * @param fingerMsg
	 * @return
	 */
	private String createSendMsg(String orgCode,String clerkCode,String passwd,String fingerMsg){
	if(fingerMsg==null||fingerMsg.trim().equals("")){
		fingerMsg="";
	}
	while(fingerMsg.length()<601){
		fingerMsg+=" ";
	}
	StringBuffer msg=new StringBuffer(676);
	msg.append("FP");//��ʼ��־
	msg.append(" 676");//������
	msg.append("C");//�������� C��������  A����Ӧ����
	msg.append("000001");//������ Ĭ��000001
	while(orgCode.length()<20){
		orgCode+=" ";
	}
	msg.append(orgCode);//������
	msg.append("      1000");//ҵ��ϵͳ�� 10λ
	msg.append(fingerMsg.substring(0,1));//���̱�ʾ 1λ
	msg.append("  ");//�����ֶ� 2λ
	//String clerknum=clerkCode;
	if(clerkCode.substring(0, 1).equals("0")){
		clerkCode=clerkCode.substring(1,clerkCode.length());
	}
	while(clerkCode.length()<20){
		clerkCode+=" ";
	}
	msg.append(clerkCode);//��Ա��
	if(passwd==null){
		passwd="";
	}
	while(passwd.length()<10){
		passwd+=" ";
	}
	msg.append(passwd);//��������
	msg.append(fingerMsg.substring(1));
		return msg.toString();
	};
	/**
	 * �������б���
	 * @param msg
	 * @return
	 */
	public String resolveMsg(String msg){
		String result="";
		if(msg!=null&&!msg.equals("")){
			result=msg.substring(46, 52);
			if(result.equals("000000")){
				return result;
			}
			if(result.equals("000001")){
				result=new String(ByteArrayUtil.copyOfRange(msg.getBytes(), 46, 152)).trim();
				return result;
			}
			return result;
		}else {
			return "000003";
		}
	
	};
	/**
	 * �����֤
	 */
	public String checkFeature(HttpServletRequest request)throws Exception {
		String clerkCode=request.getParameter("clerkCode");
		String fingerMsg=request.getParameter("fingerMsg");
		String passwd=request.getParameter("passwd");
		String orgCode="";
		
		String checkType = URLDecoder.decode(request.getParameter("checkType"),"utf-8");
		orgCode=getClerkByCode(clerkCode).getOrgcode();
			
		Socket socket=null;
		OutputStream os=null;
		 InputStream is=null;
		try {
			//��ϵͳ�����л�ȡ�����֤ip��ַ�Ͷ˿ں�
			String ip =SystemConfig.getInstance().getValue("feature_ip");
			String port=SystemConfig.getInstance().getValue("feature_port");
			//��socket����
			 socket = new Socket(ip, Integer.parseInt(port));
			 
			if(socket!=null){
				//System.out.println("finger:" +fingerMsg);
				os=socket.getOutputStream();
				//�������б���
				String sendMsg=createSendMsg(orgCode, clerkCode, passwd, fingerMsg);
				//���ͱ���
			//	System.out.println("sendMsg:"+sendMsg);
				os.write(sendMsg.getBytes());
				is =socket.getInputStream();
				byte[] buff=new byte[182];
				//�������б���
				is.read(buff);
				//�������б�������֤���
				String result=resolveMsg(new String(buff));
				return result;
				
			}
			//000000 ͨ�� 000001δͨ�� 000002 ������� 000003��֤����
			return "000003";
		} catch (Exception e) {
			return "000003";
		}finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null){
				os.close();
			}
			if(is!=null){
				is.close();
			}
			
		}
		
	}
	/**
	 * ��¼��Ȩ��־
	 */
	public void createAuthorizedLog(String account, String manageType,String manageTime,String manageDate,
			String code, String code2) {
		Shouqrzb bo =new Shouqrzb();
		bo.setAccount(account);
		bo.setAuthorizationclerk(code2);
		bo.setOperationclerk(code);
		bo.setManagetype(manageType);
		bo.setManagedate(manageDate);
		bo.setManagetime(manageTime);
		String content="��Ա["+code+"]���˺�["+account+"]����"+manageType+"����";
		bo.setManagecontent(content);
		
		clerkDao.saveAuthorizedLog(bo);
		
		
	}
//	public static void main(String[] args) {
//		System.out.println(new ClerkManageServiceImpl().createSendMsg("000000","1111111","aaaaaa",null).length()+"          "+new ClerkManageServiceImpl().createSendMsg("000000","1111111","aaaaaa",null));
//	}
	
	public String getNewClerkCode(String orgcode) throws BusinessException{
		Org org=orgDao.getOrgByCode(orgcode);
		if(org==null){
			throw new BusinessException("��Ա��������������");
		}
		String shh=org.getShOrgCode();
		if(shh==null||!shh.matches("^\\d{4}$")){
			throw new BusinessException("����"+orgcode+"һ�����к��쳣,����ϵϵͳ����Ա�鿴");
		}
		String head=shh.substring(0,2);
		String code=clerkDao.getMaxClerkCodeByHead(head);
		if(code==null||code.trim().equals("")){
			code=makeClerkCode(head,0);
			return code;
		}else{
			int currnum=0;
			try{
				 currnum=Integer.parseInt(code.substring(2, code.length()));
			}catch (Exception e) {
				throw new BusinessException("���ֹ�Ա���쳣,����ϵϵͳ����Ա�鿴");
			}
				Clerk c=null;
				if(currnum==9999){
				currnum=0;
				}
				int count=0;
				while(true){
					code=makeClerkCode(head,currnum);
					c=clerkDao.getClerkByCode(code);
					if(c==null||c.getCode()==null){
						break;
					}
					currnum++;
					if(currnum==10000){
						currnum=0;
						}
					count++;
					if(count==10000){
						throw new BusinessException("��ӡ��Ա�Ѵ�����");
					}
				}
				return code;
				
			
			
		}
		
	}

private String makeClerkCode(String head, int currnum) {
	String code="";
	code+=head;
	String num=String.valueOf(currnum);
	while(num.length()<4){
		num="0"+num;
	}
	code+=num;
	return code;
}
}
