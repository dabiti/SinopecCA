package com.unitop.sysmgr.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.unitop.config.SystemConfig;
import com.unitop.framework.util.JsonSystemTool;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Menu;
import com.unitop.sysmgr.bo.Privilege;
import com.unitop.sysmgr.bo.Tree;
import com.unitop.sysmgr.dao.DoMenuDao;
import com.unitop.sysmgr.service.DoMenuService;

@Service("DoMenuServiceImpl")
public class DoMenuServiceImpl implements DoMenuService {
	@Resource
    private DoMenuDao DoMenuDao;
	 
	//根据ID查找菜单
	public Menu getMenuForId(String gongnid,String zignid) throws SQLException {
		return DoMenuDao.getMenuForId(gongnid, zignid);
	}

	static List menuList = null;
	
	public  List selectForMenu(Clerk clerk) throws SQLException {
		//系统模式 ：测试模式 生成模式
		String systemModel = SystemConfig.getInstance().getSystemModle();
		if(menuList==null||"test".equals(systemModel))
		{
			 menuList = DoMenuDao.selectForMenu();
			 //柜员信息赋值
			 for(int i=0;i<menuList.size();i++)
			 {
				Tree tree = (Tree) menuList.get(i);
				if(tree.getUrl()!=null)
				{
					//解析表达式备份
					tree.setUrl_(tree.getUrl());
					tree.setUrl(tree.getUrl_().replaceAll("\\$\\{clerknum\\}",JsonSystemTool.toJsonForClerk(clerk)));
				}
			 }
		}
		//柜员信息赋值
		for(int i=0;i<menuList.size();i++)
		{
			Tree tree = (Tree) menuList.get(i);
			if(tree.getUrl()!=null)
			{
				try {
					tree.setUrl_("");
					tree.setUrl(tree.getUrl_().replaceAll("\\$\\{clerknum\\}",JsonSystemTool.toJsonForClerk(clerk)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return menuList ;
	}
	
	public void updateMenu(Menu menu) throws SQLException {
		 DoMenuDao.updateMenu(menu);
	}
	
	public List  getMenuForName(String name) {
		return DoMenuDao.getMenuForName(name);
	}
	public List getMenuForShangjgn(String gongnid) throws SQLException {
		return DoMenuDao.getMenuForShangjgn(gongnid);
	}
	public void save(Menu menu) {
		DoMenuDao.save(menu);
	}
	
	public void delete(String gongnid, String zignid) throws SQLException {
		this.DoMenuDao.deleteProMenu(gongnid,zignid);
	}
	
	public void saveMenu(String gongnid, String zignid, String shangjgn) throws SQLException {
		Menu menu = this.DoMenuDao.getMenuById(gongnid,zignid,shangjgn);
		this.DoMenuDao.saveProMenu(menu);
	}
	
	@SuppressWarnings("unchecked")
	public List getCaidlx() throws SQLException {
		return DoMenuDao.getCaidlx();
	}

	public Menu getMenubyJcgnid(String gongnid,String zignid) throws SQLException {
		return DoMenuDao.getMenuForId(gongnid, zignid);
		
	}

	public List getJicFunctions() {
		List allPrivilegeList = new ArrayList();
			List<Menu> list = DoMenuDao.getAllFunctions();
			for(int i=0;i<list.size();i++){
				Privilege privilege = new Privilege();
				//如果此功能不是一个子功能，则此功能ID即为privilege的ID；如果是一个子功能，则转换
				if("0".equals(list.get(i).getZignid()))
				{
					privilege.setId(list.get(i).getGongnid());
				}else{
					privilege.setId(list.get(i).getGongnid()+"|"+list.get(i).getZignid());
				} 
				privilege.setName(list.get(i).getGongnmc());
				privilege.setBeiz(list.get(i).getBeiz());
				if("0".equals(list.get(i).getZignid())){
					privilege.setpId("1");
				}else{
					privilege.setpId(list.get(i).getGongnid());
				}
				allPrivilegeList.add(privilege);
			}
	
		Privilege privilege = new Privilege();
		privilege.setId("1");
		privilege.setName("中石化对账系统");
		allPrivilegeList.add(privilege);
		return allPrivilegeList;
	}
	
//菜单定制，保存树
	public void saveOrUpdate(List privileList) {
		Map chanpMap = DoMenuDao.getAllChanp();
		DoMenuDao.deleteAllChanp();
		for(int i=0;i<privileList.size();i++){
			Privilege privilege = (Privilege) privileList.get(i);
			int index  = privilege.getId().indexOf("|");
			String gongnid ="";
			String zignid = "";
			if(index==-1){
				gongnid = privilege.getId();
				zignid = "0";
			}else{
				gongnid = privilege.getId().substring(0,index);
				zignid =  privilege.getId().substring(index+1);
			}
			Menu chanpmenu = (Menu) chanpMap.get(gongnid+"|"+zignid);//获取原产品功能表中的信息
			try {
				Menu menu = this.DoMenuDao.getMenuById(gongnid,zignid,"");
				//根据产品功能表中的主键，去基础功能表中查询，如果查不到，该功能肯定为文件夹
				if("1".equals(gongnid)){
					menu.setShangjgn("null");
					menu.setGongnmc(chanpmenu.getGongnmc());
					menu.setClassid("");
				}else{
					menu.setShangjgn(privilege.getpId());
					menu.setGongnmc(privilege.getName());
				}
				
				if("".equals(menu.getGongnid())||menu.getGongnid()==null){
					menu.setGongnid(gongnid);
					menu.setZignid(zignid);
					if(chanpmenu!=null){
						menu.setGongnlx(chanpmenu.getGongnlx());
						menu.setBeiz(chanpmenu.getBeiz());
						menu.setGongnsx(chanpmenu.getGongnsx());
						menu.setZhuangt(chanpmenu.getZhuangt());
						if("null".equals(chanpmenu.getClassid())||chanpmenu.getClassid()==null){
							menu.setClassid("");
						}else{
							menu.setClassid(chanpmenu.getClassid());
						}
					}
					
				}else{
					//如果菜单不为空
						if(chanpmenu==null){
						}else{
							menu.setGongnsx(chanpmenu.getGongnsx());
							menu.setZhuangt(chanpmenu.getZhuangt());
						}
				}
				this.DoMenuDao.saveProMenu(menu);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
