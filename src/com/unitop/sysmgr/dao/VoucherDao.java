package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.bo.VoucherPk;

public interface VoucherDao extends BaseDataResourcesInterface{
	
	public Voucher getVoucher(String id);
	
	public void saveVoucher(Voucher voucher);
	
	public void updateVoucher(Voucher voucher);
	
	public void deleteVoucher(Voucher voucher);
	
	public List getVoucherList();
	
	//20130513 �ؿ��л�ȡƾ֤��Ϣ
	public List getVoucherList_zk();
}
