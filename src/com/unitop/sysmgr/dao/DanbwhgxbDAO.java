package com.unitop.sysmgr.dao;

import java.util.List;
import com.unitop.sysmgr.bo.Danbwhgxb;

/*
 * �����ϵά����ʵ��DAO
 * by ldd
 */
public interface DanbwhgxbDAO extends BaseDataResourcesInterface{
	public void save(Danbwhgxb danbwhgxb);
	public void delete(Danbwhgxb danbwhgxb);
	public void update(Danbwhgxb danbwhgxb);
	public Danbwhgxb findById(String id);
	public List findDanbwhgxbList(String zhubbh);
}