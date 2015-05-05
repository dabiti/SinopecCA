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
	 * ����ƾ֤��Ϣ�б�
	 */
	public List getVoucherList() {
		return voucherDao.getVoucherList();
	}
	
	/**
	 * @param VoucherPk
	 * �����������������ź�ƾ֤����ɵĶ���ɾ�����ݿ�ƾ֤����
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
				throw new BusinessException("ƾ֤������");
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
	 
	//��ȡƾ֤ϵͳ��ϵ����      
	public List getXitlxgxList(String jigh, String pingzh) {
		return pingzxtlxgxbDao.getxitList(jigh, pingzh);
	}
    //��ȡƾ֤ӡ����ϵ����
	public List getYinjlxgxList(String jigh, String pingzh) {
		return pingzyjlxgxbDao.getyinjList(jigh, pingzh);
	}

	public void saveXitlxgx(String jigh, String pingzh, List xitList,
			List yinjList) {
	}

	/*
	 * ƾ֤�������� ҵ�����ñ�������
	 * by ldd
	 */
	public void saveXitlxgx(String jigh, String pingzh, String[] xitList, String[] yinjList) throws Exception {
		
		if(xitList==null||xitList.length<1||yinjList==null||yinjList.length<1)
		{
			throw new Exception("ϵͳ���ͻ���ӡ�����Ͳ���Ϊ��!");
		}
		//�������ϻ�ȥ�������ݿ����������
		
		//�������񱣻�
		//��������
		Session session =this.getBaseHibernateDao().getHibernateSession();
		pingzxtlxgxbDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try{
			//pingzxtlxgxbDao.delete(jigh,pingzh);
			//ɾ��ƾ֤ϵͳ���͹�ϵ����
			pingzxtlxgxbDao.deletexitList(jigh,pingzh);    
			
			//����ƾ֤ϵͳ���� ���������
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
			//ɾ��ƾ֤ӡ�����͹�ϵ����
			 pingzyjlxgxbDao.deleteyjList(jigh,pingzh);
			//����ƾ֤ӡ������ ���������
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
			//�����ύ
			tr.commit();
		}catch(Exception e){
			//����ع�
			tr.rollback();
			e.printStackTrace();
		}finally{
			//�ͷ������ͷ���Դ��
			pingzyjlxgxbDao.shifSession();
		}
	}

	public List getVoucherList_zk() {
		return voucherDao.getVoucherList_zk();
	}
}
