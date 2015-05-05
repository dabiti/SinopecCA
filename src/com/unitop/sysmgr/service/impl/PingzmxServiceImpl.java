package com.unitop.sysmgr.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.bo.Pingzmx;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.dao.DayfwDao;
import com.unitop.sysmgr.dao.PingzmxDao;
import com.unitop.sysmgr.form.PingzForm;
import com.unitop.sysmgr.service.PingzmxService;
@Service("PingzmxServiceImpl")
public class PingzmxServiceImpl  extends BaseServiceImpl  implements PingzmxService{ 
	
	@Resource
	private PingzmxDao pingzmxDao;
	@Resource 
	private DayfwDao dayfwDao;
	
	public void deletePingz(String pingzh) throws SQLException {
		pingzmxDao.deletePingz(pingzh);
	} 
	public void batchDeletePingz(String pingzhString) throws Exception{
		if(pingzhString == null)
		{
			pingzhString = "";
		}
		String[] pingzs = pingzhString.split(",");
		//开启事务
		Session session =this.getBaseHibernateDao().getHibernateSession();
		pingzmxDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try{
			for(int i = 0;i<pingzs.length;i++)
			{
				pingzmxDao.deletePingz(pingzs[i]);
			}
			tr.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw new Exception("凭证批量删除异常!");
		}finally{
			pingzmxDao.shifSession();
		}
	} 

	public List findPingzByPich(String pich) {
		return pingzmxDao.findPingzByPich(pich);
	}

	public Pingzmx findPingzByPingzh(String pingzh) throws SQLException {
		return pingzmxDao.findPingzByPingzh(pingzh);
	}

	
	public void savePingz(Pingzmx pingz) throws Exception {
		//生成批次号
		String uuid = UUID.randomUUID().toString();
		pingz.setPich(uuid);
		
		Long qispzh = 0l;
		Long zhongzpzh = 0l;
		String pingzhQ = pingz.getQispzh().toUpperCase();
		String pingzhZ = pingz.getZhongzpzh().toUpperCase();
		String start = SystemConfig.getInstance().getStartPz();
		
		//开启事务
		Session session =this.getBaseHibernateDao().getHibernateSession();
		pingzmxDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try{
			//存储汇总信息
			pingzmxDao.savePinzhz(pingz); 
			
			//如果凭证以字母开头
			if(pingzhQ.startsWith(start))
			{
				qispzh = Long.parseLong(pingzhQ);
				zhongzpzh = Long.parseLong(pingzhZ);
				
				//循环保存明细信息
				for(long i=zhongzpzh;i<=zhongzpzh;i++)
				{
					Pingzmx pingz_ = new Pingzmx();
					BeanUtils.copyProperties(pingz, pingz_);
					pingz_.setPingzh(""+i);
					pingz_.setZhuangt("未打印");
					pingzmxDao.savePingz(pingz_);
				}
			}else{
			Long qispzh_L = Long.parseLong(pingz.getQispzh());
			Long zhongzpzh_L =Long.parseLong(pingz.getZhongzpzh());
				
				//循环保存明细信息
				for(long i=qispzh_L;i<=zhongzpzh_L;i++)
				{
					Pingzmx pingz_ = new Pingzmx();
					BeanUtils.copyProperties(pingz, pingz_);
					//pingz_.setPingzh(""+i);
			 	    String I=String.valueOf(i);
			 	    int changd=I.length();
			 	    int cha=10-changd;
			 	    String str="0000000000";
			 	    String pingzh=str.substring(0, cha)+i;
					pingz_.setPingzh(""+pingzh);
					pingz_.setZhuangt("未打印");
					pingzmxDao.savePingz(pingz_);
				}
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw new Exception("凭证出售录入失败!,凭证号已经存在");
		}finally{
			pingzmxDao.shifSession();
		}
	}
	
	public TabsBo selectAllPingz(PingzForm pingz) throws SQLException {
		return pingzmxDao.selectAllPingz(pingz,tabsBo.getDangqym(), tabsBo.getFenysl());
	}

	public void updatePingz(Pingzmx pingz) throws SQLException {
		pingzmxDao.updatePingz(pingz);
	}

	/* (non-Javadoc)
	 * @see com.unitop.sysmgr.service.PingzmxService#findPingzByPingzh(java.lang.String, java.lang.String)
	 */
	public List<Pingzmx> findPingzByPingzh(String qispzh, String zhongzpzh) throws SQLException {
		return pingzmxDao.findPingzByPingzh(qispzh,zhongzpzh);
	}

	public void updateZhuangt(String qispzh,String zhongzpzh,String dayfwmsg,String count) throws SQLException {
		int daysl = Integer.parseInt(count);
		dayfwDao.updateDaysl(dayfwmsg,daysl);
		pingzmxDao.updateZhuangt(qispzh,zhongzpzh);
	}
	
	//根据凭证号查询凭证出售明细日志
	public List findPingzRizByPingzh(String pingzh) {
		return pingzmxDao.findPingzRizByPingzh(pingzh);
	}

}