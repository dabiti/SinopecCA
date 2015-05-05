package com.unitop.sysmgr.dao;

import java.sql.SQLException;
import java.util.List;

import com.unitop.sysmgr.bo.DanbwhBiaod;
import com.unitop.sysmgr.bo.UnionBiaod;

public interface DanbwhBiaodDao   extends BaseDataResourcesInterface {

	public List getAll(String gongnid)throws SQLException;

	public void delete(UnionBiaod biaod)throws SQLException;

	public DanbwhBiaod find(UnionBiaod biaod)throws SQLException;

	public void update(DanbwhBiaod danbwh)throws SQLException;

	public void add(DanbwhBiaod danbwh)throws SQLException;

}
