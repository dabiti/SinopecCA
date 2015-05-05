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
	public List getXitlxgxList(String jigh,String pingzh);//获取凭证系统关系接口
	public List getYinjlxgxList(String jigh,String pingzh);//获取凭证印鉴关系接口
	public void saveXitlxgx(String jigh,String pingzh,String[] xitList,String[] yinjList) throws Exception;//保存凭证系统关系、印鉴关系接口
}
