package com.unitop.sysmgr.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.unitop.config.SystemConfig;
import com.unitop.framework.util.FileServerClient;
import com.unitop.sysmgr.bo.Ocxkongzcsb;
import com.unitop.sysmgr.bo.Piaojyxwjb;
import com.unitop.sysmgr.bo.PiaojyxwjbId;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjk;
import com.unitop.sysmgr.dao.OcxkongzcsbDao;
import com.unitop.sysmgr.dao.PiaojyxwjbDao;
import com.unitop.sysmgr.dao.YinjbDao;
import com.unitop.sysmgr.dao.impl.YinjkDaoImpl;
import com.unitop.sysmgr.service.AccountImageServcie;

@Service("AccountImageServcieImpl")
public class AccountImageServcieImpl implements AccountImageServcie {
	
	@Resource
	private PiaojyxwjbDao piaojyxwjbDao =null;
	@Resource
	private YinjkDaoImpl yinjkDaoImpl =null;
	@Resource
	private OcxkongzcsbDao ocxkongzcsbDao =null;
	@Resource
	private YinjbDao yinjbDao;
	
	public List getZhanghYjkList(String zhangh) {
		List list  = yinjkDaoImpl.getYinjkByZhangh(zhangh);
		return list;
	}
	
	public List getBillImgList(String zhangh,String wenjbh) {
		List list  = piaojyxwjbDao.getPiaojyxwjb(zhangh,wenjbh);
		return list;
	}

	public Piaojyxwjb getAccountImageInfo(PiaojyxwjbId id) {
		return piaojyxwjbDao.getPiaojyxwjb(id);
	}
	
	public void downloadBillImage(PiaojyxwjbId id, OutputStream out) {
		Piaojyxwjb zTemp =  piaojyxwjbDao.getPiaojyxwjb(id);
		String wenjdz = zTemp.getWenjfwdz();
		String ip = wenjdz.substring(0,wenjdz.indexOf(":"));
		int port = Integer.valueOf(wenjdz.substring(wenjdz.indexOf(":")+1));
		//通讯获取文件
		try {
				FileServerClient fService = new FileServerClient();
				fService.GetFile(ip, port, 10000, out, zTemp.getPiaojyxdz());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void downloadYinjkImage(String zhangh,String yinjkh,String billcm,OutputStream out) {
		Yinjk yinjk = yinjkDaoImpl.getYinjkByZhangh(zhangh, yinjkh);
		//通讯获取文件
		try {
				SystemConfig systemConfig = SystemConfig.getInstance();
				FileServerClient fService = new FileServerClient();
//				String file_address_ip = systemConfig.getValue("file_address_ip");
//				int file_address_port = Integer.valueOf(systemConfig.getValue("file_address_port"));
				int file_address_outtime = Integer.valueOf(systemConfig.getValue("file_address_port"));
				
				Ocxkongzcsb ocxkongzcsb = ocxkongzcsbDao.getocxkongzcs("jkFileSeverIp");
				String wenjdz = ocxkongzcsb.getValue();
				String file_address_ip = wenjdz.substring(0,wenjdz.indexOf(":"));
				int file_address_port = Integer.valueOf(wenjdz.substring(wenjdz.indexOf(":")+1));
				
				//billcm=1 获取正面图像；billcm=2 获取反面图像
				if("1".equals(billcm))
					fService.GetFile(file_address_ip,file_address_port,file_address_outtime,out,yinjk.getZhengmwjm());
				if("2".equals(billcm))
					fService.GetFile(file_address_ip,file_address_port,file_address_outtime,out,yinjk.getFanmwjm());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public List<Yinjk> getYinjkByQiyrq(String zhangh,String qiyrq){
		List list  = yinjkDaoImpl.getYinjkByQiyrq(zhangh, qiyrq);
		return list;
	}
	public Yinjb downloadYinjImage(String zhangh, String yinjbh, String qiyrq) {
		
		return yinjbDao.getYinj(zhangh,yinjbh,qiyrq);
	}
	public List<Yinjb> getZhanghYjList(String account) {
		// TODO Auto-generated method stub
		return yinjbDao.getYinj(account);
	}
	
	
	public void downloadFromYinxpt(String wenjsy,OutputStream out){
		//从影像平台下载 预留接口
	}
	
}