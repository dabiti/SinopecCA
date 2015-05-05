package com.unitop.sysmgr.service.impl;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Service;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Pingzpzb;
import com.unitop.sysmgr.bo.Yanygz;
import com.unitop.sysmgr.dao.PingzmxDao;
import com.unitop.sysmgr.dao.PingzpzbDao;
import com.unitop.sysmgr.service.PingzService;
@Service("PingzServiceImpl")
public class PingzServiceImpl implements PingzService{
	@Resource
	public PingzmxDao PingzmxDao ;
	@Resource
	public PingzpzbDao pingzpzbDao;
	
	//实现依据批次ID查询凭证明细
	public List findPingzByPich(String pich) {
		return PingzmxDao.findPingzByPich(pich);
	}
	
	//依据凭证名称 下载凭证模版
	public void loadPingzmbByName(String pich, ServletOutputStream out) throws FileNotFoundException{
		SystemConfig systemConfig = SystemConfig.getInstance();
		String pingzmbPath =  pich ;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(pingzmbPath);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] b = new byte[1024];
			int readCount = bis.read(b);
			while (readCount > 0) 
			{
				out.write(b, 0, readCount);
				readCount = bis.read(b);
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis!=null)
			{
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public Pingzpzb getPingzpzbByPzbs(String pingzbs) {
		
		return pingzpzbDao.getPingzpzByPingzbs(pingzbs);
	}
	public List<Pingzpzb> getPingzList() {
		return pingzpzbDao.getPingzpzList();
	}
	public List<Yanygz> getYangzList() {
		// TODO Auto-generated method stub
		return pingzpzbDao.getYanygzList();
	}
	public void updatePingzpzb(Pingzpzb pingzpzb) {
		pingzpzbDao.updatePingzpzb(pingzpzb);
		
	}
	public Pingzpzb queryPingzpzbByPzmc(String pingzmc, String pingzbs) {
		// TODO Auto-generated method stub
		return pingzpzbDao.queryPingzpzbByPzmc(pingzmc,pingzbs);
	}
	public void savePingz(Pingzpzb pz) {
		pingzpzbDao.savePingz(pz);
		
	}
	public void deletePingzByPzbs(String pingzbs) {
		pingzpzbDao.deletePingz(pingzbs);
		
	}
}
