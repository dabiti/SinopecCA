package com.unitop.sysmgr.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.unitop.config.SystemConfig;
import com.unitop.exception.DAOException;
import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Nighttaskconfig;
import com.unitop.sysmgr.bo.Org;
import com.unitop.sysmgr.dao.ClerkDao;
import com.unitop.sysmgr.dao.OrgDao;
import com.unitop.sysmgr.service.BatchService;
import com.unitop.sysmgr.service.SystemMgrService;
import com.unitop.sysmgr.service.TongbuService;
import com.unitop.util.Base64;
import com.unitop.util.ExportTxtUtil;

@Service("BatchService")
public class BatchServiceImpl extends BaseServiceImpl implements BatchService {
	private static final Category log = Logger.getLogger(BatchServiceImpl.class);
	@Resource
	private OrgDao orgDao ;
	@Resource
	private ClerkDao clerkDao ;
	@Resource
	private TongbuService ts ;
	@Resource
	private SystemMgrService sms;
	
	private File file;
	private int errTime = 0;
	// ���ʧ�����³��Դ���
	private static final int maxFailTime = 2;

	public void orgWork() throws Exception {
		//Session session=super.getBaseHibernateDao().getHibernateSession();
		//orgDao.set_Session(session);
		Map<Org, Exception> failOrg = new HashMap<Org, Exception>();
		try {
			// ͬ�����Ļ�������ʱ����
			insertIntoOrgTemp();

			Org centerBank = orgDao.getCenterBankFromTemp_TB();

			List<Org> orgList_temp = orgDao.getAllTempOrg_TB();
			for (Org org_temp : orgList_temp) {
				try {
					// ͬ������
					tongbOrg(org_temp, centerBank);
					orgDao.commit_TB();
				} catch (DAOException e) {
					orgDao.rollback_TB();
					failOrg.put(org_temp, e);
				}
			}
			// ���³���ͬ����ʧ�ܵĻ��� ��ȡ������δ�ɹ��Ļ���
			failOrg = retryFailOrg(failOrg, centerBank);
			// ɾ�����к���ϵͳ�����ڵĻ��� ���ͱ�ʾΪ "1"�Ļ���
			int errCount = 0;
			while (true) {
				try {
					orgDao.startTransaction_TB();
					orgDao.clearTemp_TB();
					orgDao.deleteUndefindOrg_TB();
					// �޸����л��������ͱ�ʾΪ"1";
					orgDao.updateAllOrgForEnd_TB();
					orgDao.commit_TB();
					break;
				} catch (DAOException e) {
					errCount++;
					orgDao.rollback_TB();
					if (errCount > maxFailTime) {
						throw new Exception(e);
					}
				}
			}
			Set<Entry<Org, Exception>> entrySet = failOrg.entrySet();
			for (Entry<Org, Exception> entry : entrySet) {
				log.info("ͬ��ʧ�ܻ��� �� " + entry.getKey().getCode() + " ʧ��ԭ��"
						+ entry.getValue().getMessage());
			}
			log.info("ͬ������[���]");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			throw new Exception("ͬ����������ʧ��",e);
		}finally{
			orgDao.closeConn_TB();
		}
	}
	public void clerkWork() throws Exception {
		this.file = null;
		BufferedReader in = null;
		log.info("���ع�Ա��Ϣ�ļ�");

		String today=sms.getSystetemNowDate();
		today=DateTool.toSimpleFormatToddmmyyyy(today);
		//String filePath=SystemConfig.getInstance().getValue("night_task_orgFile")+"BRHM_"+today+".txt";
		String filePath=SystemConfig.getInstance().getValue("night_task_clerkFile")+"TELM_"+today+".TXT";
		this.file = new File(filePath);
		in = new BufferedReader(
				new InputStreamReader(new FileInputStream(file),"gbk"));
		String clerkStr = "";
		Map<Clerk, Exception> errClerkMap = new HashMap<Clerk, Exception>();
		Clerk clerk =null;
		try {
			// ���Ĺ�Ա��Ϣ���� ���� �޸� ɾ��
			while ((clerkStr = in.readLine()) != null) {
				clerk=new Clerk();
				 clerk = analysisClerkMsg(clerkStr,clerk);
				if (clerk == null)
					continue;
				try {
					tongbClerk(clerk);
					clerkDao.commit_TB();
				} catch (DAOException e) {
					clerkDao.rollback_TB();
					errClerkMap.put(clerk, e);
				}
			}
			// ����ͬ��ʧ�ܵĹ�Ա
			errClerkMap = retryFailClerk(errClerkMap);

			int errCount = 0;
			while (true) {
				try {
					clerkDao.startTransaction_TB();
					// ɾ�����к���ϵͳ�����ڵĲ�����ʾΪ "1"�Ĺ�Ա
					clerkDao.deleteUndefindClerk_TB();
					// �޸����к��Ĺ�Ա�Ĳ�����ʾΪ"1";
					clerkDao.updateAllClerkForEnd_TB();
					clerkDao.commit_TB();
					break;
				} catch (DAOException e) {
					errCount++;
					clerkDao.rollback_TB();
					if (errCount > maxFailTime) {
						throw new Exception(e);
					}
				}
			}

			Set<Entry<Clerk, Exception>> entrySet = errClerkMap.entrySet();
			for (Entry<Clerk, Exception> entry : entrySet) {
				log.info("ͬ��ʧ�ܹ�Ա ��" + entry.getKey().getCode() + " ʧ��ԭ�� ��"
						+ entry.getValue().getMessage());
			}
			log.info("ͬ����Ա[���]");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
				throw new Exception("ͬ����Աʧ��",e);
		}finally{
			if(in!=null){
			in.close();
			}
			clerkDao.closeConn_TB();
			orgDao.closeConn_TB();
		}
	}

	// ����������txt�ļ�
	private Org analysisOrgMsg(String orgStr) {
		if (orgStr == null)
			return null;
		orgStr = orgStr.replace("^|", ",");
		String[] array = orgStr.split(",", 0);

		try {
			Org org = new Org();
			org.setCode(array[0] == null || array[0].trim().equals("") ? ""
					: array[0].trim());
			org
					.setName(array[2] == null
							|| array[2].trim().equals("") ? "" : array[2]
							.trim());
			org
					.setShOrgCode(array[9] == null
							|| array[9].trim().equals("") ? "" : array[9]
							.trim());
			org.setWdflag(array[8] == null || array[8].equals("C") ? "1"
					: array[8].equals("H") ? "0" : "3");
			org.setParentCode(array[9] == null || array[9].equals("") ? ""
					: array[9].trim());
			return org;
		} catch (Exception e) {
			return null;
		}
	}

	// ������Ա����Ϣ
	private Clerk analysisClerkMsg(String str,Clerk clerk) {
		if (str == null)
			return null;
		str = str.replace("^|", ",");
		String[] array = str.split(",", 0);
		try {
			clerk =null;
			clerk=new Clerk();
			clerk.setCode(array[1] == null ? "" : array[1]);
			clerk.setName(array[10] == null ? "" : array[10]);
			clerk.setOrgcode(array[2] == null ? "" : array[2]);
			clerk.setPostCode(array[6] == null ? "" : array[6]);//����״̬
			return clerk;
		} catch (Exception e) {
			return null;
		}
	}

	// ���³���ͬ��ʧ�ܵĻ���
	private Map<Org, Exception> retryFailOrg(Map<Org, Exception> failOrg,
			Org centerBank) {
		Map<Org, Exception> errOrgMap = new HashMap<Org, Exception>();
		Set<Entry<Org, Exception>> entrySet = failOrg.entrySet();
		for (Entry<Org, Exception> entry : entrySet) {
			Org org = entry.getKey();
			int errTime = 0;
			while (true) {
				try {
					tongbOrg(org, centerBank);
					break;
				} catch (DAOException e) {
					errTime++;
					if (errTime >= maxFailTime) {
						errOrgMap.put(org, e);
						break;
					}
				}
			}

		}
		return errOrgMap;
	}

	// ͬ������
	private void tongbOrg(Org org_temp, Org centerBank) throws DAOException {
		String org_temp_code = org_temp.getCode();
		String org_temp_wdflag = org_temp.getWdflag();
		String org_temp_parent = org_temp.getParentCode();
		Org org = orgDao.getOrgByCode_TB(org_temp_code);
		// ���û�������ӡϵͳ���Ƿ����
		if (org != null) {
			orgDao.updateOrg_TB(org_temp);
		} else {
			// �����ڵĻ�����Ҫ����
			if (org_temp_wdflag != null && org_temp_wdflag.equals("0")) {
				org_temp.setParentCode("");
				org_temp.setShOrgCode(org_temp.getCode());
			} else {
				// �����з��еĵ��ϼ�����Ϊ����
				if (org_temp_parent != null
						&& org_temp_parent.equals(org_temp.getCode())) {
					org_temp.setWdflag("1");
					org_temp.setParentCode(centerBank.getCode());
					org_temp.setShOrgCode(org_temp_parent);
				}
				// �ϼ����������ڵĽ����ϼ���������Ϊ����
				Org parentOrg = orgDao.getOrgFromTemp_TB(org_temp_parent);
				if (parentOrg == null) {
					throw new DAOException("�ϼ�����������");
				}
			}
			orgDao.saveOrg_TB(org_temp);
		}
	}

	// ���������ʱ��
	private void insertIntoOrgTemp() throws Exception {
		this.file = null;
		BufferedReader in = null;
		log.info("���ػ�����Ϣ�ļ�");
		String today=sms.getSystetemNowDate();
		today=DateTool.toSimpleFormatToddmmyyyy(today);
		String filePath=SystemConfig.getInstance().getValue("night_task_orgFile")+"BRHM_"+today+".TXT";
		this.file = new File(filePath);
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					file),"gbk"));
			String orgStr = "";
			orgDao.startTransaction_TB();
			// ��ջ�����ʱ��
			orgDao.clearTemp_TB();
			log.info("���Ļ�����ӵ�������ʱ����");
			// ���Ļ�����ӵ�������ʱ����
			while ((orgStr = in.readLine()) != null) {
				Org org = analysisOrgMsg(orgStr);
				if (org == null)
					continue;
				orgDao.addTemp_TB(org);
			}
			orgDao.commit_TB();
		} catch (DAOException e) {
			log.info(e.getMessage());
			log.info("�ع�����");
			orgDao.rollback_TB();
			errTime++;
			if(errTime<maxFailTime){
				log.info("���¼��ػ�����Ϣ��������ʱ����");
				insertIntoOrgTemp();
			}else{
				throw new Exception("ͬ������ʧ�ܣ�������ʱ���������ʧ��");
			}
		} catch (IOException e) {
			log.info("��ȡ�����ļ�ʧ��"+ e);
		} finally {
			in.close();
		}
	}

	// ���³���ͬ��ʧ�ܵĻ���
	private Map<Clerk, Exception> retryFailClerk(
			Map<Clerk, Exception> errClerkMap) {
		Clerk clerk =null;
		Map<Clerk, Exception> failClerkMap = new HashMap<Clerk, Exception>();
		Set<Entry<Clerk, Exception>> entrySet = errClerkMap.entrySet();
		for (Entry<Clerk, Exception> entry : entrySet) {
			clerk =new Clerk();
			clerk = entry.getKey();
			int errTime = 0;
			while (true) {
				try {
					tongbClerk(clerk);
					break;
				} catch (DAOException e) {
					errTime++;
					if (errTime >= maxFailTime) {
						failClerkMap.put(clerk, e);
						break;
					}
				}
			}
		}
		return failClerkMap;
	}

	private void tongbClerk(Clerk clerk) throws DAOException {
		if (clerk == null)
			return;
		String clerkNum = clerk.getCode();
		String clerkstatu = "";
		if (clerkstatu.equals("99")) {
			return;
		}
		clerkDao.startTransaction_TB();
		try {
			Clerk clerk_ = clerkDao.getClerkByCode_TB(clerkNum);
			if (clerk_ != null) {
				clerkDao.updateClerk_TB(clerk);
				clerk_=null;
			} else {
				String orgCode = clerk.getOrgcode();
				Org org = orgDao.getOrgByCode_TB(orgCode);
				if (org == null) {
					throw new DAOException("��Ա��������������");
				} else {
					clerk.setShOrgCode(org.getShOrgCode());
					clerk.setWdFlag(org.getWdflag());
					clerkDao.saveClerk_TB(clerk);
				}
			}
			clerkDao.commit_TB();
		} catch (DAOException e) {
			clerkDao.rollback_TB();
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String tongb() {
		try {
			System.out.println("����");
			log.info("����[����ͬ������]");
			log.info("����[ͬ����������]");
			this.orgWork();
			log.info("�ر�[ͬ����������]");
			errTime=0;
			log.info("����[ͬ����Ա����]");
			this.clerkWork();
			log.info("�ر�[ͬ����Ա����]");
			System.out.println("����");
			log.info("����ͬ������[���]");
			return "0";
		} catch (Exception e) {
			log.info((e));
			e.printStackTrace();
			return "1";
		}
	}
	
	public String executeSqlForCall(String callName,Map map)throws DAOException {
		Connection conn=null;
		CallableStatement cstmt = null;
		try {
			conn=this.getBaseJDBCDao().getConnection();
			cstmt = conn.prepareCall(callName);
			for (int i = 1; i < map.size() + 1; i++) {
				cstmt.setString(i, (String) map.get(i));
			}
			cstmt.execute();
			return "0";
		} catch (SQLException e) {
			e.printStackTrace();
			return "1";
		} finally {
			try {
				if(conn!=null)
					this.closeConnection(conn);
				if(cstmt!=null)
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	
	
	public List<Nighttaskconfig> getTaskList() {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		List<Nighttaskconfig> taskList=null;
		String hql="from Nighttaskconfig";
		try {
			Query query=session.createQuery(hql);
			taskList=query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return taskList;
	}
	public Nighttaskconfig getNightTaskById(String id) {
		if(id==null||id.trim().equals(""))return null;
		Session session =this.getBaseHibernateDao().getHibernateSession();
		Nighttaskconfig task=null;
		String hql="from Nighttaskconfig where id=:id";
		try {
			Query query=session.createQuery(hql);
			query.setString("id", id);
			task=(Nighttaskconfig)query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		return task;
	}
	public void saveOrUpdate(Nighttaskconfig task) {
		if(task==null)return;
		Session session =this.getBaseHibernateDao().getHibernateSession();
		session.beginTransaction().begin();
		try {
			session.saveOrUpdate(task);
			session.beginTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
	}
	public boolean zftongb() {
		boolean isOk = true;
		log.info("�ܷ�ͬ����������");
		log.info("��ʼ�ռ�ͬ���˺���Ϣ");
		try {
			while(true){
				Map<String, String> sendMap = ts.queryTongbu();
				if (sendMap != null && sendMap.size() != 0) {
				log.info("��ʼ��ȡ����ͬ��������Ϣ");
				Map<String, List> jigMap = ts.queryAllJig(sendMap);
					for (Map.Entry jigEntry : jigMap.entrySet()) {
						Socket socket = null;
						BufferedInputStream in = null;
						BufferedOutputStream out = null;
						String jigh = jigEntry.getKey().toString();
						log.info("��ʼ׼�������" + jigh + "ͬ�������˻���Ϣ");
						String socketadd = ts.getSocketaddByJigh(jigh);
						log.info("����" + jigh + "��ͬ����ַΪ:" + socketadd);
						if (socketadd != null && !"".equals(socketadd)) {
							boolean isContinue = false;
							int count = 0;
							String socketException = "";
							do {
								log.info("��" + (count + 1) + "��ͬ��");
								count++;
								List<String> zhanghList = (List<String>) jigEntry
										.getValue();
								int sendNum = zhanghList.size();
								log.info("����" + jigh + "ͬ���˺�Ϊ" + sendNum + "��");
								try {
									log.info("�ͻ��˿�ʼ���ӷ���˶˿�");
									String[] socketaddsz = socketadd.split(":");
									socket = new Socket(socketaddsz[0], Integer
											.valueOf(socketaddsz[1]));
									in = new BufferedInputStream(socket
											.getInputStream());
									out = new BufferedOutputStream(socket
											.getOutputStream());
									log.info("�ͻ��˿�ʼ����ͬ��������" + sendNum);
									out.write(getMsgnum(sendNum).getBytes());
									out.flush();
									if (zhanghList != null
											&& zhanghList.size() != 0) {
										for (String zhangh : zhanghList) {
											String siglexx = (String) sendMap
											.get(zhangh);
									String sendMapStr = Base64.encodeBytes(siglexx.getBytes());
									out.write(getMsglength(
											sendMapStr.getBytes().length)
											.getBytes());
											out.flush();
											out.write(sendMapStr.getBytes());
											out.flush();
											log.info("�ͻ��˷���ͬ���ַ����ɹ�");
											log.info("�ͻ���׼�����շ�����Ϣ");
											int messageSize = readMessageNum(in);
											log.info("�ͻ���׼�����շ�����Ϣ�ĳ���Ϊ"
													+ messageSize);
											int totalSiz = 0;
											// �����С
											int bytesize = 1024;
											// ����
											byte[] shuz = new byte[bytesize];
											// ƴ�ӱ���
											StringBuffer sb = new StringBuffer();
											int z = 0;
											while (totalSiz < messageSize
													&& (z = in.read(shuz)) != -1) {
												String aa = new String(shuz, 0, z);
												sb.append(aa);
												totalSiz += z;
											}
											String receiveStr = sb.toString();
											log.info("�ͻ����յ�������Ϣ:" + receiveStr);
											String returnResult = ts.returnTongbu(receiveStr, zhangh);
											if("fail".equals(returnResult)){
												isOk = false;
											}
											isContinue = false;
										}
									}
								} catch (UnknownHostException e) {
									isContinue = true;
									socketException = e.getMessage();
									log.error("socket�쳣:" + e.getMessage());
									e.printStackTrace();
									log.error("����ʧ�ܣ���������");
								} catch (Exception e) {
									isContinue = true;
									socketException = e.getMessage();
									log.error("�����쳣:" + e.getMessage());
									e.printStackTrace();
									log.error("����ʧ�ܣ���������");
								} finally {
									try {
										if (out != null) {
											out.close();
										}
										if (in != null) {
											in.close();
										}
										if (socket != null) {
											socket.close();
										}
									} catch (IOException e1) {
										log.info("�ر�����socketʱ���������쳣:"
												+ e1.getMessage());
										e1.printStackTrace();
									}
								}
								if(count==5){
									isOk = false;
									log.error("����:"+jigh+"��ͬ����ַ����ʱ�����쳣:"+socketException);
									ts.saveSendExceptionForJigh("����:"+jigh+"��ͬ����ַ����ʱ�����쳣:"+socketException, jigh);
								}else{
									Thread.sleep(1000);
								}
							} while (isContinue && count < 5);
						} else {
								log.error("����:"+jigh+"��ͬ����ַΪ��");
								ts.saveSendExceptionForJigh("����:"+jigh+"��ͬ����ַΪ��", jigh);
						}
	
					}
					System.gc();
				} else {
					log.info("��ʣ��䶯��Ϣ��ͬ������");
					break;
				}
			}
		} catch (DAOException e) {
			isOk = false;
			e.printStackTrace();
			log.error("��ѯ�����г���ͬ��ȡ��:" + e.getMessage());
		} catch (InterruptedException e) {
			isOk = false;
			e.printStackTrace();
			log.error("�̵߳ȴ��쳣",e);
		}
		return isOk;
	}
	
	// ��ñ��ĳ���
	public static String getMsglength(int length) {
		String len = "" + length;
		String str = "";
		for (int i = 0; i < 8 - len.length(); i++) {
			str += "0";
		}
		str += len;
		return str;
	}

	// ��ñ�����Ŀ
	public static String getMsgnum(int num) {
		return getMsglength(num);
	}

	// �����Ҫ��������
	private int readMessageNum(InputStream ist) throws IOException {
		byte[] b = new byte[8];
		ist.read(b);
		return Integer.parseInt(new String(b, "GBK"));
	}
	public void updateTaskStatu(String id, String result) {
		if(id==null||id.trim().equals(""))return;
		if(result==null||result.trim().length()==0){result ="0";}
		Session session =this.getBaseHibernateDao().getHibernateSession();
		String time = sms.getSystetemNowDate();
		String hql="update nighttaskconfig set lastruntime=:time,lastrunresult=:result  where id=:id";
		try {
			SQLQuery query=session.createSQLQuery(hql);
			query.setString("time", time);
			query.setString("result",result);
			query.setString("id", id);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
	}
	public boolean exportFile(String file1,String file2,String workdate) {
		Session session =this.getBaseHibernateDao().getHibernateSession();
		String sql1="select data from(" +
				"select  zhangh||'^|'||customernumber||'^|'||legalname||'^|'||internalorganizationnumber||'^|'||physicaladdress||'^|'||postalcode||'^|'||lianxr||'^|'||phonenumber||'^|'||kaihrq||'^|'||tongctd||'^|'||currency||'^|'||youwyj||'^|'||youwzh||'^|'||yinjshzt||'^|'||zhanghshzt||'^|'||zuhshzt||'^|'||zhanghzt||'^|'||zhanghxz||'^|'||yanyjb||'^|'||yanyjg||'^|'||beiz||'^|'||zhuzh||'^|'||fuyrq||'^|'||quxfyrq||'^|'||jiankbs||'^|'||tingyrq||'^|'||fuzr||'^|'||phonenumber3||'^|'||yinjkh||'^|'||tongdsz||'^|'||lianxr2||'^|'||phonenumber2||'^|'||fuzr2||'^|'||phonenumber4||'^|'||shifdh||'^|'||areacode||'^|'||areacode2||'^|'||areacode3||'^|'||areacode4||'^|'||extensionnumber||'^|'||extensionnumber2||'^|'||extensionnumber3||'^|'||extensionnumber4||'^|' as data,rownum as rn from zhanghb)" +
				" where rn >=:first and rn<:end";
		
		String sql2="select data from(" +
		"select zhangh||'^|'||managedate||'^|'||managetime||'^|'||managetype||'^|'||managecontent||'^|'||clerknum||'^|'||clerkname||'^|'||ip||'^|'||internalorganizationnumber||'^|'||str1||'^|'||str2||'^|'||str3||'^|'||str4||'^|'||str5||'^|'||str6||'^|'||str7||'^|'||str8||'^|'||str9||'^|'||str10||'^|'||oldcontent||'^|'||newcontent||'^|' as data,rownum as rn from accountmanagelog where managedate='"+workdate+"')" +
		" where rn >=:first and rn<:end";
		try{
			
			List<Object> dataList=new ArrayList<Object>();
			export(file1, sql1,session);
			export(file2, sql2,session);
			return true;			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			this.getBaseHibernateDao().closeSession(session);
		}
		
	}

	private void export(String file1, String sql,Session session) throws IOException {
		SQLQuery query=session.createSQLQuery(sql);
		List<Object> dataList=new ArrayList<Object>();
		ExportTxtUtil etu1=new ExportTxtUtil(file1);
		int first=1;
		int end=0;
		do{
			end=first+10000;
			query.setString("first", first+"");
			query.setString("end", end+"");
			dataList=query.list();
			etu1.exportTxt(dataList);
			first+=10000;
		}while(dataList.size()>0);
	}
}
