package com.unitop.sysmgr.service;

import java.sql.SQLException;
import java.util.Collection;

import com.unitop.config.Privilege;

public  interface   ChanpcdService {
	public Collection getPostCollection();
	public Privilege getPostCollectionByName(String name) throws SQLException;
}
