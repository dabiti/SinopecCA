package com.unitop.sysmgr.service;

import java.util.List;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.VoucherPk;

public interface VoucherMgrService {
	public List getVoucherList();
	public List getVoucherList_zk();
	public void deleteVoucherById(String id)throws BusinessException ;
	public List getXitlxList();
	public List getYinjlxList();
	public List getXitlxgxList(String jigh,String pingzh);//��ȡƾ֤ϵͳ��ϵ�ӿ�
	public List getYinjlxgxList(String jigh,String pingzh);//��ȡƾ֤ӡ����ϵ�ӿ�
	public void saveXitlxgx(String jigh,String pingzh,String[] xitList,String[] yinjList) throws Exception;//����ƾ֤ϵͳ��ϵ��ӡ����ϵ�ӿ�
}
