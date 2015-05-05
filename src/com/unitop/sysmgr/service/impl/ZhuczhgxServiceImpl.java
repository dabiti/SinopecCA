package com.unitop.sysmgr.service.impl;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjzhb;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.dao.YinjbDao;
import com.unitop.sysmgr.dao.YinjzhbDao;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.dao.ZhuczhgxDao;
import com.unitop.sysmgr.service.ZhuczhgxService;

/*
 * 主从账户关系维护
 * by wp
 */
@Service("ZhuczhgxServiceImpl")
public class ZhuczhgxServiceImpl extends BaseServiceImpl implements ZhuczhgxService 
{
	@Resource
	public ZhuczhgxDao zhuczhgxDao;
	@Resource
	public YinjbDao yinjbDao;
	@Resource
	public YinjzhbDao yinjzhbDao;
	@Resource
	public ZhanghbDao zhanghbDao;
 	
	//获取子账户信息列表
	public List getzizh(String zhangh) throws SQLException {
		return zhuczhgxDao.getzizh(zhangh);
	}

	/*
	 * 保存印鉴信息（新建主从关系）
	 * by wp
	 */
	public void saveyinj(String fuyrq,String congzh,String mainaccount,Zhanghb zhanghb_c,Zhanghb zhanghb_z) throws Exception
	{
		//新建主从关系         1、从账户原有印鉴停用，停用日期为复用日期当天(yinjb)2、将从账户的zhuzh字段更新为主账号(zhanghb),获取复用日期
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhuczhgxDao.set_Session(session);
		yinjbDao.set_Session(session);
		yinjzhbDao.set_Session(session);
		zhanghbDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try 
		{
			List yinjc_oldlist=yinjbDao.getYinj(congzh);//从账户旧印鉴信息
			List yinjz_oldlist=yinjbDao.getYinj(mainaccount);//主账户旧印鉴信息
			List zuhc_oldlist=yinjzhbDao.getYinjzh(congzh);//从账户旧组合信息
			List zuhz_oldlist=yinjzhbDao.getYinjzh(mainaccount);//主账户旧组合信息
			
			Zhanghb z_zhanghb = zhanghbDao.getZhanghb(mainaccount);//主账户信息
			Zhanghb c_zhanghb = zhanghbDao.getZhanghb(congzh);//从账户信息
			
			Yinjzhb yinjzhb_c =new Yinjzhb();//从账户印鉴组合表实体
			Yinjzhb yinjzhb_z =new Yinjzhb();//主账户印鉴组合表实体
			Yinjb yinjb_c = new Yinjb();//从账户印鉴表实体
			Yinjb yinjb_z = new Yinjb();//主账户印鉴实体
			
			//主从账户不能相同
			if(zhanghb_c.getZhangh().equals(zhanghb_z.getZhangh()))
			{
				throw new Exception("主从账户不能是同一个账户！");
			}
			//主账号是否存在及有效
			if(z_zhanghb.getZhangh()==null)
			{
				throw new Exception("主账户不存在！");
			}else{
				if(!("有效".equals( zhanghb_z.getZhanghzt())))
				{
					throw new Exception("主账户必须为有效账户才能建立主从关系！");
				}
			}
			//从账户是否存在及有效
			if(c_zhanghb.getZhangh()==null)
			{
				throw new Exception("从账户不存在！");
			}else{
				if(!("有效".equals( zhanghb_c.getZhanghzt())))
				{
					throw new Exception("从账户必须为有效账户才能建立主从关系！");
				}
			}
			//从账户不是其他账户的主账户       主账户不是其他账户的从账户
			List clist = zhuczhgxDao.getzizh(congzh);//判断congzh是否有子账户
			if(clist.size()==0)
			{
				if(!(zhanghb_z.getZhuzh()==null||"".equals(zhanghb_z.getZhuzh())))
				{
					throw new Exception("主账户已是其他账户的从账户，不能新建主从关系！");	
				}
			}else{
				throw new Exception("从账户已是其他账户的主账户，不能新建主从关系！");
			}
			
			//新建关系时，主账户下必须有印鉴
			if(yinjz_oldlist.size()>0)
			{
				//主账户印鉴审核校验
				for(int i=0;i<yinjz_oldlist.size();i++)
				{
					yinjb_z = (Yinjb) yinjz_oldlist.get(i);
					if(!("已审".equals(yinjb_z.getYinjshzt())))
					{
						throw new Exception("主账户包含未审核印鉴，新建主从账户关系失败！");
					}
				}
				//如果主账户下有组合，必须是已审
				if(zuhz_oldlist.size()>0){
					//主账户组合审核校验
					for(int i=0;i<zuhz_oldlist.size();i++)
					{
						yinjzhb_z = (Yinjzhb) zuhz_oldlist.get(i);
						if(!("已审".equals(yinjzhb_z.getZuhshzt()))){
							throw new Exception("主账户包含未审核组合，新建主从账户关系失败！");
						}
					}
				}
			}else
			{
				throw new Exception("主账户下没有印鉴，新建主从账户关系失败！");
			}
			
			//新建关系时，从账户下不一定要有印鉴和组合，倘若有，必须为已审
			if(yinjc_oldlist.size()>0)
			{
				//从账户印鉴审核校验   
				for(int i=0;i<yinjc_oldlist.size();i++)
				{
					yinjb_c = (Yinjb) yinjc_oldlist.get(i);
				 if(!("已审".equals(yinjb_c.getYinjshzt())))
					{
						throw new Exception("从账户包含未审核印鉴，新建主从账户关系失败！");
					}
				}
			}
			if(zuhc_oldlist.size()>0)
			{
				//从账户组合审核校验
				for(int i=0;i<zuhc_oldlist.size();i++)
				{
					yinjzhb_c = (Yinjzhb) zuhc_oldlist.get(i);
					if(!("已审".equals(yinjzhb_c.getZuhshzt())))
					{
						throw new Exception("从账户包含未审核组合，新建主从账户关系失败！");
					}
				}
			}
			
			//复用日期必须大于从账户下最新批次印鉴的启用日期
			List qiyrqlist = zhuczhgxDao.getqiyrq(congzh);
			for(int i=0;i<qiyrqlist.size();i++)
			{
				Yinjb yinjb = (Yinjb) qiyrqlist.get(i);//从账户印鉴
				String qiyrq = yinjb.getYinjid().getQiyrq();
				int res_q = qiyrq.compareTo(fuyrq); //res_q<0 		启用日期小于复用日期
				if(res_q>0||res_q==0)
				{
					throw new Exception("从账户下包含启用日期晚于或等于当前复用日期的印鉴，不能新建关系！请重新输入复用日期！");
				}
			}
			
			//复用日期必须大于从账户下最新批次组合的启用日期
			List Qiyrqlist = zhuczhgxDao.getQiyrq(congzh);
			for(int i=0;i<Qiyrqlist.size();i++)
			{
				Yinjzhb yinjzh = (Yinjzhb) Qiyrqlist.get(i);
				String qiyrq = yinjzh.getYinjzhid().getQiyrq();
				int res_q = qiyrq.compareTo(fuyrq); //res_q<0 		启用日期小于复用日期
				if(res_q>0||res_q==0)
				{
					throw new Exception("从账户下包含启用日期晚于或等于当前复用日期的组合，不能新建关系！请重新输入复用日期！");
				}
			}
			
			//获取主账户印鉴表中所有启用日期
			List qyrqlist = zhuczhgxDao.getqiyrq(mainaccount);
			for(int i=0;i<qyrqlist.size();i++)
			{
				Yinjb yinj = (Yinjb) qyrqlist.get(i);//主账户印鉴
				String Qiyrq = yinj.getYinjid().getQiyrq();
			    int res_q = Qiyrq.compareTo(fuyrq); //res_q<0 		启用日期小于复用日期
			    if(res_q<0||res_q==0)
			    {
			    	//印鉴表   停用从账户原有印鉴 
			    	for(int j=0;j<yinjc_oldlist.size();j++)
			    	{
			    		yinjb_c = (Yinjb) yinjc_oldlist.get(j);
			    		yinjb_c.setTingyrq(fuyrq);//停用日期为复用日期
						zhuczhgxDao.saveyinj(yinjb_c);
			    	}
					
					//账户表  更新主从关系
			
			    	Zhanghb zhanghbc = zhanghbDao.getZhanghb(congzh);
			    	zhanghbc.setZhuzh(mainaccount);
			    	zhanghbc.setFuyrq(fuyrq);
					zhuczhgxDao.savezhanghb(zhanghbc);
					
					//印鉴组合表   停用从账户原有组合
					for(int k=0;k<zuhc_oldlist.size();k++)
					{
						yinjzhb_c=(Yinjzhb) zuhc_oldlist.get(k);
						yinjzhb_c.setTingyrq(fuyrq);//停用日期为复用日期
						zhuczhgxDao.savezuh(yinjzhb_c);
					}
			     }else{
			    	 throw new Exception("主账户印鉴的启用日期晚于复用日期！");
			     }
			  }
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw e;
		}finally{
			zhuczhgxDao.shifSession();
		}
	}

	/*
	 *取消主从关系
	 *by wp  
	 */
	public void doquxgx(String czhangh, String zzhangh,String quxfyrq_) throws Exception
	{
		try {
			Zhanghb z_zhanghb = zhanghbDao.getZhanghb(zzhangh);//主账户信息
			Zhanghb c_zhanghb = zhanghbDao.getZhanghb(czhangh);//从账户信息
			//主账户存在、有效校验
			if("".equals(z_zhanghb.getZhangh())&&z_zhanghb.getZhangh()==null)
			{
				throw new Exception("主账户不存在！"); 
			}else{
				if(!("有效".equals(z_zhanghb.getZhanghzt())&&"有效".equals(c_zhanghb.getZhanghzt())))
				{
					throw new Exception("主账户或从账户不是有效账户，不能进行取消主从关系操作！"); 
				}
			}
			//主账户审核状态效验
			if(!("已审".equals(z_zhanghb.getZhanghshzt())))
			{
				throw new Exception("主账户审核状态不是已审，不能进行取消主从关系操作！"); 
			}
			
			List yinj_zlist =yinjbDao.getYinj(zzhangh);
			List yinj_clist =yinjbDao.getYinj(czhangh);
			List zuh_zlist =yinjzhbDao.getYinjzh(zzhangh);
			List zuh_clist =yinjzhbDao.getYinjzh(czhangh);
			
			//从账户审核状态校验（从账户存在印鉴时）
			if((yinj_clist.size()!=0))
			{
				if(!("已审".equals(c_zhanghb.getZhanghshzt())))
				{
					throw new Exception("从账户审核状态不是已审，不能进行取消主从关系操作！"); 
				}	
			}
			
			//主账户下若存在组合，则组合信息必须为已审
			if(zuh_zlist.size()>0){
				for(int i=0;i<zuh_zlist.size();i++)
				{
					Yinjzhb yinjzh=(Yinjzhb) zuh_zlist.get(i);
					if(!("已审".equals(yinjzh.getZuhshzt()))){
						throw new Exception("主账户下存在未审组合，取消主从关系失败！"); 	
					}
				}
			}

			//取消关系时，主账户下必须存在印鉴
			  if(yinj_zlist.size()>0)
			{
				  for(int i=0;i<yinj_zlist.size();i++)
				  {
					  Yinjb yinj_z = (Yinjb) yinj_zlist.get(i);
					  if(!("已审".equals(yinj_z.getYinjshzt())))
					  {
						  throw new Exception("主账户下存在未审印鉴，取消主从关系失败！"); 
					  }	  
				  }
			}else{
				 throw new Exception("主账户下没有印鉴信息，取消主从关系失败！"); 
			}
			
			//取消关系时，从账户下若有印鉴，必须为已审
			if(yinj_clist.size()>0)
			{ 
				for(int i=0;i<yinj_clist.size();i++)
				{
					Yinjb yinj_c = (Yinjb) yinj_clist.get(i);
					if(!("已审".equals(yinj_c.getYinjshzt())))
					{
						throw new Exception("从账户下存在未审核的印鉴，取消主从关系失败！"); 
					}
				}
			}
			
			//取消关系时，从账户下若有组合，必须为已审
			if(zuh_clist.size()>0)
			{
				for(int i=0;i<zuh_clist.size();i++)
				{
					Yinjzhb zuh_c = (Yinjzhb) zuh_clist.get(i);
					if(!("已审".equals(zuh_c.getZuhshzt())))
					{
						throw new Exception("从账户下存在未审核的组合，取消主从关系失败！"); 
					}
				}
				
			}
			zhuczhgxDao.doquxgx(zzhangh, czhangh,quxfyrq_);
		} catch (SQLException e){
			throw e;
		}
	}
	
	
}