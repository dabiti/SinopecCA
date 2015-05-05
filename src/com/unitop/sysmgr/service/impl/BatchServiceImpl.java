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
	// 最大失败重新尝试次数
	private static final int maxFailTime = 2;

	public void orgWork() throws Exception {
		//Session session=super.getBaseHibernateDao().getHibernateSession();
		//orgDao.set_Session(session);
		Map<Org, Exception> failOrg = new HashMap<Org, Exception>();
		try {
			// 同步核心机构到临时表中
			insertIntoOrgTemp();

			Org centerBank = orgDao.getCenterBankFromTemp_TB();

			List<Org> orgList_temp = orgDao.getAllTempOrg_TB();
			for (Org org_temp : orgList_temp) {
				try {
					// 同步机构
					tongbOrg(org_temp, centerBank);
					orgDao.commit_TB();
				} catch (DAOException e) {
					orgDao.rollback_TB();
					failOrg.put(org_temp, e);
				}
			}
			// 重新尝试同步已失败的机构 获取最终仍未成功的机构
			failOrg = retryFailOrg(failOrg, centerBank);
			// 删除所有核心系统不存在的机构 类型标示为 "1"的机构
			int errCount = 0;
			while (true) {
				try {
					orgDao.startTransaction_TB();
					orgDao.clearTemp_TB();
					orgDao.deleteUndefindOrg_TB();
					// 修改所有机构的类型标示为"1";
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
				log.info("同步失败机构 ： " + entry.getKey().getCode() + " 失败原因："
						+ entry.getValue().getMessage());
			}
			log.info("同步机构[完成]");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			throw new Exception("同步机构服务失败",e);
		}finally{
			orgDao.closeConn_TB();
		}
	}
	public void clerkWork() throws Exception {
		this.file = null;
		BufferedReader in = null;
		log.info("加载柜员信息文件");

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
			// 核心柜员信息分流 新增 修改 删除
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
			// 重新同步失败的柜员
			errClerkMap = retryFailClerk(errClerkMap);

			int errCount = 0;
			while (true) {
				try {
					clerkDao.startTransaction_TB();
					// 删除所有核心系统不存在的操作标示为 "1"的柜员
					clerkDao.deleteUndefindClerk_TB();
					// 修改所有核心柜员的操作标示为"1";
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
				log.info("同步失败柜员 ：" + entry.getKey().getCode() + " 失败原因 ："
						+ entry.getValue().getMessage());
			}
			log.info("同步柜员[完成]");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
				throw new Exception("同步柜员失败",e);
		}finally{
			if(in!=null){
			in.close();
			}
			clerkDao.closeConn_TB();
			orgDao.closeConn_TB();
		}
	}

	// 解析机构表txt文件
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

	// 解析柜员表信息
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
			clerk.setPostCode(array[6] == null ? "" : array[6]);//核心状态
			return clerk;
		} catch (Exception e) {
			return null;
		}
	}

	// 重新尝试同步失败的机构
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

	// 同步机构
	private void tongbOrg(Org org_temp, Org centerBank) throws DAOException {
		String org_temp_code = org_temp.getCode();
		String org_temp_wdflag = org_temp.getWdflag();
		String org_temp_parent = org_temp.getParentCode();
		Org org = orgDao.getOrgByCode_TB(org_temp_code);
		// 检查该机构在验印系统中是否存在
		if (org != null) {
			orgDao.updateOrg_TB(org_temp);
		} else {
			// 不存在的机构需要新增
			if (org_temp_wdflag != null && org_temp_wdflag.equals("0")) {
				org_temp.setParentCode("");
				org_temp.setShOrgCode(org_temp.getCode());
			} else {
				// 核心中分行的的上级机构为本身
				if (org_temp_parent != null
						&& org_temp_parent.equals(org_temp.getCode())) {
					org_temp.setWdflag("1");
					org_temp.setParentCode(centerBank.getCode());
					org_temp.setShOrgCode(org_temp_parent);
				}
				// 上级机构不存在的将其上级机构设置为总行
				Org parentOrg = orgDao.getOrgFromTemp_TB(org_temp_parent);
				if (parentOrg == null) {
					throw new DAOException("上级机构不存在");
				}
			}
			orgDao.saveOrg_TB(org_temp);
		}
	}

	// 插入机构临时表
	private void insertIntoOrgTemp() throws Exception {
		this.file = null;
		BufferedReader in = null;
		log.info("加载机构信息文件");
		String today=sms.getSystetemNowDate();
		today=DateTool.toSimpleFormatToddmmyyyy(today);
		String filePath=SystemConfig.getInstance().getValue("night_task_orgFile")+"BRHM_"+today+".TXT";
		this.file = new File(filePath);
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					file),"gbk"));
			String orgStr = "";
			orgDao.startTransaction_TB();
			// 清空机构临时表
			orgDao.clearTemp_TB();
			log.info("核心机构添加到机构临时表中");
			// 核心机构添加到机构临时表中
			while ((orgStr = in.readLine()) != null) {
				Org org = analysisOrgMsg(orgStr);
				if (org == null)
					continue;
				orgDao.addTemp_TB(org);
			}
			orgDao.commit_TB();
		} catch (DAOException e) {
			log.info(e.getMessage());
			log.info("回滚事物");
			orgDao.rollback_TB();
			errTime++;
			if(errTime<maxFailTime){
				log.info("重新加载机构信息到机构临时表中");
				insertIntoOrgTemp();
			}else{
				throw new Exception("同步机构失败，机构临时表插入数据失败");
			}
		} catch (IOException e) {
			log.info("读取机构文件失败"+ e);
		} finally {
			in.close();
		}
	}

	// 重新尝试同步失败的机构
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
					throw new DAOException("柜员所属机构不存在");
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
			System.out.println("启动");
			log.info("启动[日终同步程序]");
			log.info("启动[同步机构服务]");
			this.orgWork();
			log.info("关闭[同步机构服务]");
			errTime=0;
			log.info("启动[同步柜员服务]");
			this.clerkWork();
			log.info("关闭[同步柜员服务]");
			System.out.println("结束");
			log.info("日终同步程序[完成]");
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
		log.info("总分同步任务启动");
		log.info("开始收集同步账号信息");
		try {
			while(true){
				Map<String, String> sendMap = ts.queryTongbu();
				if (sendMap != null && sendMap.size() != 0) {
				log.info("开始获取所有同步机构信息");
				Map<String, List> jigMap = ts.queryAllJig(sendMap);
					for (Map.Entry jigEntry : jigMap.entrySet()) {
						Socket socket = null;
						BufferedInputStream in = null;
						BufferedOutputStream out = null;
						String jigh = jigEntry.getKey().toString();
						log.info("开始准备向机构" + jigh + "同步其下账户信息");
						String socketadd = ts.getSocketaddByJigh(jigh);
						log.info("机构" + jigh + "的同步地址为:" + socketadd);
						if (socketadd != null && !"".equals(socketadd)) {
							boolean isContinue = false;
							int count = 0;
							String socketException = "";
							do {
								log.info("第" + (count + 1) + "次同步");
								count++;
								List<String> zhanghList = (List<String>) jigEntry
										.getValue();
								int sendNum = zhanghList.size();
								log.info("机构" + jigh + "同步账号为" + sendNum + "个");
								try {
									log.info("客户端开始连接服务端端口");
									String[] socketaddsz = socketadd.split(":");
									socket = new Socket(socketaddsz[0], Integer
											.valueOf(socketaddsz[1]));
									in = new BufferedInputStream(socket
											.getInputStream());
									out = new BufferedOutputStream(socket
											.getOutputStream());
									log.info("客户端开始发送同步次数：" + sendNum);
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
											log.info("客户端发送同步字符串成功");
											log.info("客户端准备接收返回信息");
											int messageSize = readMessageNum(in);
											log.info("客户端准备接收返回信息的长度为"
													+ messageSize);
											int totalSiz = 0;
											// 缓存大小
											int bytesize = 1024;
											// 缓存
											byte[] shuz = new byte[bytesize];
											// 拼接报文
											StringBuffer sb = new StringBuffer();
											int z = 0;
											while (totalSiz < messageSize
													&& (z = in.read(shuz)) != -1) {
												String aa = new String(shuz, 0, z);
												sb.append(aa);
												totalSiz += z;
											}
											String receiveStr = sb.toString();
											log.info("客户端收到返回信息:" + receiveStr);
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
									log.error("socket异常:" + e.getMessage());
									e.printStackTrace();
									log.error("发送失败，重新连接");
								} catch (Exception e) {
									isContinue = true;
									socketException = e.getMessage();
									log.error("传输异常:" + e.getMessage());
									e.printStackTrace();
									log.error("发送失败，重新连接");
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
										log.info("关闭流和socket时发生传输异常:"
												+ e1.getMessage());
										e1.printStackTrace();
									}
								}
								if(count==5){
									isOk = false;
									log.error("机构:"+jigh+"的同步地址连接时发生异常:"+socketException);
									ts.saveSendExceptionForJigh("机构:"+jigh+"的同步地址连接时发生异常:"+socketException, jigh);
								}else{
									Thread.sleep(1000);
								}
							} while (isContinue && count < 5);
						} else {
								log.error("机构:"+jigh+"的同步地址为空");
								ts.saveSendExceptionForJigh("机构:"+jigh+"的同步地址为空", jigh);
						}
	
					}
					System.gc();
				} else {
					log.info("无剩余变动信息，同步结束");
					break;
				}
			}
		} catch (DAOException e) {
			isOk = false;
			e.printStackTrace();
			log.error("查询过程中出错，同步取消:" + e.getMessage());
		} catch (InterruptedException e) {
			isOk = false;
			e.printStackTrace();
			log.error("线程等待异常",e);
		}
		return isOk;
	}
	
	// 获得报文长度
	public static String getMsglength(int length) {
		String len = "" + length;
		String str = "";
		for (int i = 0; i < 8 - len.length(); i++) {
			str += "0";
		}
		str += len;
		return str;
	}

	// 获得报文数目
	public static String getMsgnum(int num) {
		return getMsglength(num);
	}

	// 获得所要报文数量
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
