package com.unitop.sysmgr.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.Pingzxtlxgxb;
import com.unitop.sysmgr.bo.PingzxtlxgxbId;
import com.unitop.sysmgr.bo.Pingzyjlxgxb;
import com.unitop.sysmgr.bo.PingzyjlxgxbId;
import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.dao.PingzxtlxgxbDao;
import com.unitop.sysmgr.dao.PingzyjlxgxbDao;
import com.unitop.sysmgr.dao.SystemDao;
import com.unitop.sysmgr.dao.VoucherDao;
import com.unitop.sysmgr.dao.YinjlxDao;
import com.unitop.sysmgr.service.VoucherMgrService;
@Service("VoucherMgrServiceImpl")
public class VoucherMgrServiceImpl extends BaseServiceImpl implements VoucherMgrService{

	@Resource
	public VoucherDao voucherDao;
	@Resource
	private PingzxtlxgxbDao pingzxtlxgxbDao;
	@Resource
	private PingzyjlxgxbDao pingzyjlxgxbDao;
	@Resource
	private YinjlxDao YinjlxDao;
	@Resource
	private SystemDao SystemDao;
	
	/**
	 * @author ldd
	 * 加载凭证信息列表
	 */
	public List getVoucherList() {
		return voucherDao.getVoucherList();
	}
	
	/**
	 * @param VoucherPk
	 * 根据联合主键机构号和凭证号组成的对象删除数据库凭证数据
	 */
	public void deleteVoucherById(String id) throws BusinessException {
		Session session = this.getHibernateSession();
		Voucher voucher = null;
		try {
			voucher = voucherDao.getVoucher(id);
			if (voucher != null)
			{
				voucherDao.deleteVoucher(voucher);
			} else
				throw new BusinessException("凭证不存在");
		} finally {
			this.closeSession(session);
		}
	}
	
	
	public void setVoucherDao(VoucherDao voucherDao) {
		this.voucherDao = voucherDao;
	}

	public List getXitlxList() {
		return SystemDao.getYanyinSystemType();
	}
	 public List getYinjlxList() {
		return YinjlxDao.getYinjlxList();
	}
	 
	//获取凭证系统关系数据      
	public List getXitlxgxList(String jigh, String pingzh) {
		return pingzxtlxgxbDao.getxitList(jigh, pingzh);
	}
    //获取凭证印鉴关系数据
	public List getYinjlxgxList(String jigh, String pingzh) {
		return pingzyjlxgxbDao.getyinjList(jigh, pingzh);
	}

	public void saveXitlxgx(String jigh, String pingzh, List xitList,
			List yinjList) {
	}

	/*
	 * 凭证参数设置 业务设置保存或更新
	 * by ldd
	 */
	public void saveXitlxgx(String jigh, String pingzh, String[] xitList, String[] yinjList) throws Exception {
		
		if(xitList==null||xitList.length<1||yinjList==null||yinjList.length<1)
		{
			throw new Exception("系统类型或者印鉴类型不能为空!");
		}
		//今天晚上回去看下数据库事务的资料
		
		//增加事务保护
		//开启事务
		Session session =this.getBaseHibernateDao().getHibernateSession();
		pingzxtlxgxbDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try{
			//pingzxtlxgxbDao.delete(jigh,pingzh);
			//删除凭证系统类型关系数据
			pingzxtlxgxbDao.deletexitList(jigh,pingzh);    
			
			//遍历凭证系统类型 并保存更新
			for(int i = 0 ; i < xitList.length; i++)
			{
				Pingzxtlxgxb pingzxtlxgxb = new Pingzxtlxgxb();
				PingzxtlxgxbId id = new PingzxtlxgxbId();
				id.setJigh(jigh);
				id.setPingzbs(pingzh);
				id.setXitlx(xitList[i]);
				pingzxtlxgxb.setId(id);
				pingzxtlxgxbDao.saveOrUpdate(pingzxtlxgxb);
			}
			
			//pingzyjlxgxbDao.delete(jigh,pingzh) hibernate hql 
			//删除凭证印鉴类型关系数据
			 pingzyjlxgxbDao.deleteyjList(jigh,pingzh);
			//遍历凭证印鉴类型 并保存更新
			for(int i = 0 ; i < yinjList.length; i++)
			{
				Pingzyjlxgxb pingzyjlxgxb = new Pingzyjlxgxb();
				PingzyjlxgxbId id = new PingzyjlxgxbId();
				id.setJigh(jigh);
				id.setPingzbs(pingzh);
				id.setYinjlx(yinjList[i]);
				pingzyjlxgxb.setId(id);
				pingzyjlxgxbDao.saveOrUpdate(pingzyjlxgxb);
			}
			//事务提交
			tr.commit();
		}catch(Exception e){
			//事务回滚
			tr.rollback();
			e.printStackTrace();
		}finally{
			//释放事务（释放资源）
			pingzyjlxgxbDao.shifSession();
		}
	}

	public List getVoucherList_zk() {
		return voucherDao.getVoucherList_zk();
	}
}
