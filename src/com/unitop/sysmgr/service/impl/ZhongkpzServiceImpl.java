package com.unitop.sysmgr.service.impl;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.unitop.framework.util.StringUtil;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Pingzgcb;
import com.unitop.sysmgr.bo.Pingzjgsyb;
import com.unitop.sysmgr.bo.Pingzkcsyb;
import com.unitop.sysmgr.bo.Voucher;
import com.unitop.sysmgr.dao.ClerkDao;
import com.unitop.sysmgr.dao.ZhongkpzDao;
import com.unitop.sysmgr.service.ClerkManageService;
import com.unitop.sysmgr.service.ZhongkpzService;

/*
 * by wp 
 */
@Service("ZhongkpzServiceImpl")
public class ZhongkpzServiceImpl  extends BaseServiceImpl implements ZhongkpzService{

	@Resource
	private ZhongkpzDao zhongkpzDao;
	@Resource
	private ClerkManageService clerkManageService;
	@Resource
	public ClerkDao clerkDao = null;
	
	/*
	 *保存入库信息（过程表、库存剩余表）
	 */
	public void savePingz(String qispzh,String zhongzpzh,String pingzlx,String rukrq,String rukjg,Pingzkcsyb pingz) throws Exception {
		
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			String qStr=StringUtil.intForIndex(qispzh);
			String zStr=StringUtil.intForIndex(zhongzpzh);
			int qLen=qispzh.length()-qStr.length();
			int zLen=qispzh.length()-qLen;;
			Long qispzh_ = Long.parseLong(qStr);
			Long zhongzpzh_ =Long.parseLong(zStr);
			//循环保存介于起始凭证号和终止凭证号之间的凭证信息   过程表
			for(Long i=qispzh_;i<=zhongzpzh_;i++)
			{
				Pingzgcb pingzgcb = new Pingzgcb();
				pingzgcb.getPingzgcbid().setPingzh(qispzh.substring(0,qLen)+substr(String.valueOf(i),zLen));
				pingzgcb.getPingzgcbid().setPingzlx(pingzlx);
				pingzgcb.setRukrq(rukrq);
				pingzgcb.setRukjg(rukjg);
				pingzgcb.setPingzzt("未领");//凭证状态 “1”表示未领
				zhongkpzDao.savePingz(pingzgcb);
			}
			
			//库存剩余表
			List list = zhongkpzDao.findPingz(pingz.getPingzkcsybid().getJigh(),pingz.getPingzkcsybid().getPingzlx());
			//相同机构号、凭证类型的记录只有一条               总数=总数+入库总张数                  剩余张数=剩余张数+入库总张数
			if (list.size()==1)
			{
				Pingzkcsyb pzkcsyObj=(Pingzkcsyb) list.get(0);
				Long qispzhLong = Long.parseLong(qispzh);
                Long zhongzpzhLong = Long.parseLong(zhongzpzh);
                Long zhangsLong = zhongzpzhLong - qispzhLong + 1;//入库总张数
                String zhangsString = String.valueOf(zhangsLong);
                int zhangsInt = Integer.parseInt(zhangsString);
                pzkcsyObj.setZongs(pzkcsyObj.getZongs()+zhangsInt);
				pzkcsyObj.setShengyzs(pzkcsyObj.getShengyzs()+zhangsInt);
				pzkcsyObj.getPingzkcsybid().setJigh(pzkcsyObj.getPingzkcsybid().getJigh());
				pzkcsyObj.getPingzkcsybid().setPingzlx(pzkcsyObj.getPingzkcsybid().getPingzlx());
				zhongkpzDao.savePingz(pzkcsyObj);
			}else{
				zhongkpzDao.savePingz(pingz);
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw new Exception("凭证入库录入失败!");
		}finally{
			zhongkpzDao.shifSession();
		}
	}
	
	private String substr(String str,int len){
		for (int i=str.length(); i < len; i++) {
		str = "0" + str;
		}
		return str;
	}

	/*
	 * 保存机构领用信息(过程表、库存剩余表、机构剩余表)
	 */
	public String saveLybyJg(String jiglyfzr,String bens,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg ) throws Exception 
	{
		String qispzh="";
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//如果领用机构负责人不为空，判断该柜员号是否存在
			if(jiglyfzr!=null&&!"".equals(jiglyfzr))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(jiglyfzr);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("机构领用负责人：[ " + jiglyfzr + " ] 不存在！");
				}
			}
			
			//查询获取每本凭证张数
			List pzlist = zhongkpzDao.findMbzs(pingz.getPingzgcbid().getPingzlx());
			Voucher voucher = (Voucher)pzlist.get(0);
			int meibzs =voucher.getMeibzs();
			int bensInt = Integer.parseInt(bens);
			int lingyzs =meibzs * bensInt;//领用张数
			
			List kclist = zhongkpzDao.findly_jg(pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt());
			if(kclist.size()<lingyzs)
			{
				throw new Exception("库存剩余量不足，请重新输入本数！");
			}
			//通过凭证类型、凭证状态查询凭证信息
			List list = zhongkpzDao.findPz(pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt());
			for(int i=0;i<lingyzs;i++){
				Pingzgcb pzgc = (Pingzgcb)list.get(i);
				String lingyjg_ = pzgc.getLingyjg();
				
					//领用机构为空，则当前凭证可以被机构领用
					if(("".equals(lingyjg_)||lingyjg_==null))
					if(!(list.size()<lingyzs))	
					{
						for(int j=0;j<lingyzs;j++)
						   {
							  Pingzgcb pzgcObj = (Pingzgcb)list.get(j);
							  pzgcObj.setJiglyfzr(pingz.getJiglyfzr());
							  pzgcObj.setLingyjg(pingz.getLingyjg());
							  pzgcObj.getPingzgcbid().setPingzlx(pingz.getPingzgcbid().getPingzlx());
							  pzgcObj.setPingzzt("已领");  //凭证状态“2” 表示已领
							  pzgcObj.setGuiyh(""); //机构领用完成之后清空柜员号，解决（领，退再领）时柜员号不为空，无法领用的问题
							  zhongkpzDao.saveGc(pzgcObj);
							  
							//领用起始凭证号、终止凭证号
							  if(j==0){
								  qispzh=pzgcObj.getPingzgcbid().getPingzh();
								  System.out.println(qispzh);
							  }
						   }	
					}
				}
			
			//保存到库存剩余表
			List list_kc = zhongkpzDao.findPingz(pzkc.getPingzkcsybid().getJigh(),pzkc.getPingzkcsybid().getPingzlx());
			//相同机构号、凭证类型的记录只有一条    总数=剩余+作废 =总数-领用张数    剩余张数=剩余张数-领用张数        已领张数=已领张数+领用张数 
			int lingyzsInt = meibzs * bensInt;//领用张数
				if (list_kc.size()==1)
				{
					Pingzkcsyb pzkcsyObj=(Pingzkcsyb) list_kc.get(0);
					if(pzkcsyObj.getYilzs()==null||"".equals(pzkcsyObj.getYilzs())){
						pzkcsyObj.getPingzkcsybid().setJigh(pzkc.getPingzkcsybid().getJigh());
						pzkcsyObj.getPingzkcsybid().setPingzlx(pzkc.getPingzkcsybid().getPingzlx());
						pzkcsyObj.setZongs(pzkcsyObj.getZongs()-lingyzsInt);
						pzkcsyObj.setShengyzs(pzkcsyObj.getShengyzs()-lingyzsInt);
						pzkcsyObj.setYilzs(lingyzsInt);
						zhongkpzDao.savePingz(pzkcsyObj);
					}else{
						pzkcsyObj.getPingzkcsybid().setJigh(pzkc.getPingzkcsybid().getJigh());
						pzkcsyObj.getPingzkcsybid().setPingzlx(pzkc.getPingzkcsybid().getPingzlx());
						pzkcsyObj.setZongs(pzkcsyObj.getZongs()-lingyzsInt);
						pzkcsyObj.setShengyzs(pzkcsyObj.getShengyzs()-lingyzsInt);
						pzkcsyObj.setYilzs(pzkcsyObj.getYilzs()+lingyzsInt);
						zhongkpzDao.savePingz(pzkcsyObj);
					}
				}else{
					Pingzkcsyb pzkcsyObj=new Pingzkcsyb();
					pzkcsyObj.getPingzkcsybid().setJigh(pzkc.getPingzkcsybid().getJigh());
					pzkcsyObj.getPingzkcsybid().setPingzlx(pzkc.getPingzkcsybid().getPingzlx());
					pzkcsyObj.setZongs(lingyzsInt);
					pzkcsyObj.setShengyzs(lingyzsInt);
					pzkcsyObj.setYilzs(null);
					pzkcsyObj.setZuofzs(null);
					zhongkpzDao.savePingz(pzkcsyObj);
				}
			
				//保存到机构剩余表
				List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(),pzjg.getPingzjgsybid().getPingzlx());
				//相同机构号、凭证类型的记录只有一条       剩余张数=剩余张数+领用张数        
				if (list_jg.size()==1)
				{
					Pingzjgsyb pzjgsyObj=(Pingzjgsyb) list_jg.get(0);
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(pzjgsyObj.getShengyzs()+lingyzsInt);//有记录，则剩余张数=剩余张数+领用张数
					zhongkpzDao.savePingz(pzjgsyObj);
					
				}else
				{
					Pingzjgsyb pzjgsyObj= new Pingzjgsyb();
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(lingyzsInt);//没有记录，则剩余张数=领用张数
					zhongkpzDao.savePingz(pzjgsyObj);
				}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw e;
		}finally{
			zhongkpzDao.shifSession();
		}
		return qispzh;
	}
	
	/*
	 * 保存个人领用信息(过程表、机构剩余表)
	 */
	public String saveLybyGr(String Guiyh,String bens, Pingzgcb pingz,Pingzjgsyb pzjg,String jigh) throws Exception
	{
		String qispzh="";
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//如果领用柜员不为空，判断该柜员号是否存在
			if(Guiyh!=null&&!"".equals(Guiyh))
			{
				Clerk guiyh = clerkDao.getClerkByCode(Guiyh);
				if(guiyh==null||"".equals(guiyh.getCode())){
					throw new Exception("领用柜员：[ " + Guiyh + " ] 不存在！");
				}
			}
			//查询获取每本凭证张数
			List pzlist = zhongkpzDao.findMbzs(pingz.getPingzgcbid().getPingzlx());  
			//通过凭证类型、凭证状态、领用机构 查询过程表中的凭证信息          领用机构=pingz.getLingyjg()  guiyh为空表示未被柜员个人领用
			List list = zhongkpzDao.findPZ(pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt(),jigh);
			Voucher voucher = (Voucher)pzlist.get(0);
			int meibzs =voucher.getMeibzs();
			int bensInt = Integer.parseInt(bens);
			int lingyzs =meibzs * bensInt;//领用张数
			if(list.size()< lingyzs){
				//界面提示库存剩余量不足，请重新输入本数！
				throw new Exception("库存剩余量不足，请重新输入本数！");
			}else{
				for(int i=0;i<lingyzs;i++){
					Pingzgcb pzgcObj=(Pingzgcb) list.get(i);
					if((pzgcObj.getLingyjg()==null||"".equals(pzgcObj.getLingyjg())))
					{
						//领用机构为空，该机构未被机构领用
						throw new Exception("领用机构为空，该凭证未被机构领用！");
					}else{
						//领用机构不为空，表示已被机构领用，可执行个人领用操作
						  pzgcObj.setGuiyh(pingz.getGuiyh());
						  pzgcObj.setLingyjg(pzgcObj.getLingyjg());
						  pzgcObj.setJiglyfzr(pzgcObj.getJiglyfzr());
						  pzgcObj.getPingzgcbid().setPingzlx(pingz.getPingzgcbid().getPingzlx());
						  pzgcObj.setPingzzt("已领");     //凭证状态“2” 表示已领
						  zhongkpzDao.saveGc(pzgcObj);
						  //领用起始凭证号、终止凭证号
						  if(i==0){
							 qispzh=pzgcObj.getPingzgcbid().getPingzh();
						  }
					}
				}
			}
			
			//机构剩余表
			//相同机构号、凭证类型的记录只有一条       剩余张数=剩余张数-领用张数         已领张数=已领张数+领用张数    
			List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(),pzjg.getPingzjgsybid().getPingzlx());
			//查询获取每本凭证张数
			int lingyzsInt = meibzs * bensInt;//领用张数
			if (list_jg.size()==1)
			{
				Pingzjgsyb pzjgsyObj=(Pingzjgsyb) list_jg.get(0);
				if(pzjgsyObj.getYilzs()==null||"".equals(pzjgsyObj.getYilzs()))
				{
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(pzjgsyObj.getShengyzs()-lingyzsInt);//有记录，则剩余张数=剩余张数-领用张数
					pzjgsyObj.setYilzs(lingyzsInt); //有记录，则已领张数=已领张数+领用张数  
					zhongkpzDao.savePingz(pzjgsyObj);	
				}else
				{
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(pzjgsyObj.getShengyzs()-lingyzsInt);//有记录，则剩余张数=剩余张数-领用张数
					pzjgsyObj.setYilzs(pzjgsyObj.getYilzs()+lingyzsInt); //有记录，则已领张数=已领张数+领用张数  
					zhongkpzDao.savePingz(pzjgsyObj);
				}
			}else{
				//该机构库存剩余量不足
				throw new Exception("该机构库存剩余量不足！");
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw e;
		}finally{
			zhongkpzDao.shifSession();
		}
		return qispzh;
	}


	/*
	 * 保存个人退回信息(过程表、机构剩余表)
	 */
	public void saveTogc(String guiyh,String qispzh, String zhongzpzh, Pingzgcb pingz,Pingzjgsyb pzjg) throws Exception 
	{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//如果领用柜员不为空，判断该柜员号是否存在
			if(guiyh!=null&&!"".equals(guiyh))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(guiyh);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("领用柜员：[ " + guiyh + " ] 不存在！");
				}
			}
			
			//计算退回张数
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long tuihzsLong = zhongzpzhLong - qispzhLong  + 1 ;
			String tuihzs = String.valueOf(tuihzsLong);
			int tuihzsInt = Integer.parseInt(tuihzs);
			
			//退回后清空领用机构、机构领用负责人，柜员号变更为退回柜员柜员号    
			List list = zhongkpzDao.findPz(qispzh,zhongzpzh,pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt());
			if(list.size()<tuihzsInt)
			{
				throw new Exception("退回张数大于该柜员剩余凭证张数！");
			}
			for(int i=0;i<tuihzsInt;i++)
			{
				Pingzgcb pingzObj = (Pingzgcb) list.get(i);
				//list中领用机构、机构领用负责人、柜员号均不为空，表示当前记录的凭证已被柜员领用，可执行个人退回操作
				if(!(("".equals(pingzObj.getLingyjg())||pingzObj.getLingyjg()==null)&&("".equals(pingzObj.getGuiyh())||pingzObj.getGuiyh()==null)&&("".equals(pingzObj.getJiglyfzr())||pingzObj.getJiglyfzr()==null)))
				{
					pingzObj.getPingzgcbid().setPingzh(pingzObj.getPingzgcbid().getPingzh());
					pingzObj.getPingzgcbid().setPingzlx(pingzObj.getPingzgcbid().getPingzlx());
					/*pingzObj.setGuiyh(pingz.getGuiyh());
					pingzObj.setJiglyfzr("");
					pingzObj.setLingyjg("");*/
					pingzObj.setGuiyh("");
					pingzObj.setPingzzt("已领");   //以柜员的形式将凭证退回领用机构机构，凭证状态依旧为已领
					zhongkpzDao.savePingz(pingzObj);
				}
			}
			
			//机构剩余表
			List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(),pzjg.getPingzjgsybid().getPingzlx());
			if(list_jg.size()==1)
			{
				//剩余表中有记录
				Pingzjgsyb pz = (Pingzjgsyb) list_jg.get(0);
				Integer shengyzs = pz.getShengyzs();
				if("".equals(pz.getShengyzs())||pz.getShengyzs()==null)
				{
					//list中剩余张数为空         剩余张数=退回张数        已领张数=已领张数-退回张数
					pz.setShengyzs(tuihzsInt);
					pz.setYilzs(pz.getYilzs()+tuihzsInt);
						zhongkpzDao.savePingz(pz);
				}else{
					
					//list中剩余张数不为空      剩余张数=剩余张数+退回张数        已领张数=已领张数-退回张数
					pz.setShengyzs(pz.getShengyzs()+tuihzsInt);
					pz.setYilzs(pz.getYilzs()-tuihzsInt);
					zhongkpzDao.savePingz(pz);
				}
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw e;
		}finally{
			zhongkpzDao.shifSession();
		}
	}

	/*
	 * 保存机构退回信息(过程表、库存剩余表、机构剩余表)
	 */
	public void  savetogc(String guiyh,String qispzh, String zhongzpzh,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg) throws Exception 
	{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//如果领用柜员不为空，判断该柜员号是否存在
			if(guiyh!=null&&!"".equals(guiyh))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(guiyh);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("领用柜员：[ " + guiyh + " ] 不存在！");
				}
			}
			//计算退回张数
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long tuihzsLong = zhongzpzhLong - qispzhLong  + 1 ;
			String tuihzs = String.valueOf(tuihzsLong);
			int tuihzsInt = Integer.parseInt(tuihzs);
			
			List list = zhongkpzDao.findpz(qispzh, zhongzpzh, pingz.getPingzgcbid().getPingzlx(), pingz.getPingzzt());
			for(int i=0;i<tuihzsInt;i++)
			{
				if(tuihzsInt>list.size())
				{
					throw new Exception("该机构剩余凭证数量小于退回数！");
				}else{
					Pingzgcb pz =(Pingzgcb) list.get(i);
					pz.setGuiyh(pingz.getGuiyh());
					pz.setPingzzt("未领");   //凭证状态“1”表示未领
					pz.setLingyjg("");
					pz.setJiglyfzr("");
					zhongkpzDao.savePingz(pz);
				}
			}
			
			//库存剩余表
			List list_kc = zhongkpzDao.findPingz(pzkc.getPingzkcsybid().getJigh(),pzkc.getPingzkcsybid().getPingzlx());
			Pingzkcsyb pzObj = (Pingzkcsyb) list_kc.get(0); 
			if("".equals(pzObj.getShengyzs())||pzObj.getShengyzs()==null)
			{
				//剩余张数为0时
				pzObj.setShengyzs(tuihzsInt);
				pzObj.setZongs(pzObj.getZongs()+tuihzsInt);
				pzObj.setYilzs(pzObj.getYilzs()-tuihzsInt);
				zhongkpzDao.savePingz(pzObj);
			}else{
				//剩余张数不为0时       
				pzObj.setShengyzs(pzObj.getShengyzs()+tuihzsInt);
				pzObj.setZongs(pzObj.getZongs()+tuihzsInt);
				pzObj.setYilzs(pzObj.getYilzs()-tuihzsInt);
				zhongkpzDao.savePingz(pzObj);
			}
			
			//机构剩余表
			List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(), pzjg.getPingzjgsybid().getPingzlx());
			Pingzjgsyb pz = (Pingzjgsyb) list_jg.get(0);
			if("".equals(pz.getShengyzs())||pz.getShengyzs()==null)
			{
				/*//剩余张数为0时   
				pz.setShengyzs(tuihzsInt);
				pz.setYilzs(pz.getYilzs()-tuihzsInt);
				zhongkpzDao.savePingz(pz);*/
			}else{
				//剩余张数不为0
				pz.setShengyzs(pz.getShengyzs()-tuihzsInt);
				//如果机构剩余表中已领张数、剩余张数、作废张数都为0，则应该删除该记录
				if(pz.getZuofzs()==null||"".equals(pz.getZuofzs())||pz.getZuofzs()==0)
				if(pz.getYilzs()==null||"".equals(pz.getYilzs())||pz.getYilzs()==0)
			    if(pz.getShengyzs()==null||pz.getShengyzs()==0||"".equals(pz.getShengyzs()))
			   /* if(pz.getShengyzs()==null||"".equals(pz.getShengyzs())||"0".equals(pz.getShengyzs()))*/
				{
					zhongkpzDao.deletePingzjg(pz);
				}else{
					zhongkpzDao.savePingz(pz);
				}
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			throw e;
		}finally{
			zhongkpzDao.shifSession();
		}
	}

	//保存作废信息(过程表、库存剩余表、机构剩余表)
	public void savezf(String qispzh, String zhongzpzh,String guiyh, Pingzgcb pingz,Pingzkcsyb pzkc, Pingzjgsyb pzjg) throws Exception
	{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//如果领用柜员不为空，判断该柜员号是否存在
			if(guiyh!=null&&!"".equals(guiyh))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(guiyh);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("领用柜员：[ " + guiyh + " ] 不存在！");
				}
			}
			
			//计算作废张数
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long zuofzsLong = zhongzpzhLong - qispzhLong  + 1 ;
			String zuofzs = String.valueOf(zuofzsLong);
			int zuofzsInt = Integer.parseInt(zuofzs);
		
		//查询过程表中的记录信息 并保存
		List list = zhongkpzDao.findpz(qispzh, zhongzpzh,pingz.getPingzgcbid().getPingzlx());
		  if(list.size()<zuofzsInt)
		   {
			 throw new Exception("库存剩余不足，请重新输入起始、终止凭证号！");
		  }
		     for(int j=0;j<list.size();j++){
		    	 Pingzgcb pz = (Pingzgcb) list.get(j);
					if(!(pz.getLingyjg()==null||"".equals(pz.getLingyjg())))
					{
						//领用机构不为空， 机构剩余表、库存剩余表都会变动
						List jglist = zhongkpzDao.findPingzjg(pz.getLingyjg(), pz.getPingzgcbid().getPingzlx());
						List kclist = zhongkpzDao.findPingz(pz.getRukjg(),pz.getPingzgcbid().getPingzlx());
						
						//领用机构、柜员号不为空，表示该凭证已被柜员领用
						if(pz.getGuiyh() !=null ||!("".equals(pz.getGuiyh())))
						{
							Pingzkcsyb pz_kc = (Pingzkcsyb) kclist.get(0);
							//保存库存剩余表     作废张数=作废张数+作废张数      已领张数=已领张数-作废张数      
							pz_kc.getPingzkcsybid().setJigh(pz.getRukjg());
							pz_kc.getPingzkcsybid().setPingzlx(pz.getPingzgcbid().getPingzlx());
							pz_kc.setShengyzs(pz_kc.getShengyzs());
							if(pz_kc.getZuofzs()==null||"".equals(pz_kc.getZuofzs()))
							{
								pz_kc.setZuofzs(0);
							}else{
								pz_kc.setZuofzs(pz_kc.getZuofzs());
							}
							zhongkpzDao.savePingz(pz_kc);
							
							Pingzjgsyb pz_jg = (Pingzjgsyb) jglist.get(0);
							//领用机构、柜员号都不为空，为柜员领用      机构剩余表中变动       已领张数=已领张数-作废张数        作废张数=作废张数+作废张数
							pz_jg.getPingzjgsybid().setJigh(pz.getLingyjg());
							pz_jg.getPingzjgsybid().setPingzlx(pz.getPingzgcbid().getPingzlx());
							if(pz_jg.getYilzs()==null||"".equals(pz_jg.getYilzs())){
								pz_jg.setYilzs(0);
							}else{
								pz_jg.setYilzs(pz_jg.getYilzs()-1);
								if((pz_jg.getYilzs())<0){
									//已领张数小于0表示作废张数大于当前剩余张数
									pz_jg.setYilzs(0);
								}
							}
							if(pz_jg.getZuofzs()==null||"".equals(pz_jg.getZuofzs()))
							{
								pz_jg.setZuofzs(1);
							}else{
								pz_jg.setZuofzs(pz_jg.getZuofzs()+1);
							}
							zhongkpzDao.savePingz(pz_jg);
						}else{
							Pingzjgsyb pz_jg = (Pingzjgsyb) jglist.get(0);
							//领用机构为空，柜员号不为空    为机构领用    机构剩余表中变动    剩余张数=剩余张数-作废张数     作废张数=作废张数+作废张数
							pz_jg.getPingzjgsybid().setJigh(pz.getLingyjg());
							pz_jg.getPingzjgsybid().setPingzlx(pz.getPingzgcbid().getPingzlx());
							pz_jg.setShengyzs(pz_jg.getShengyzs()-1);
							if(pz_jg.getShengyzs()<0){
								//剩余张数小于0表示作废张数大于当前剩余张数
								pz_jg.setShengyzs(0);
							}
							if(pz_jg.getZuofzs()==null||"".equals(pz_jg.getZuofzs()))
							{
								pz_jg.setZuofzs(1);
							}else{
								pz_jg.setZuofzs(pz_jg.getZuofzs()+1);
							}
							zhongkpzDao.savePingz(pz_jg);
						}
					}else{
						//领用机构为空，入库机构不为空      库存剩余表变动
						List kclist = zhongkpzDao.findPingz(pz.getRukjg(),pz.getPingzgcbid().getPingzlx());
						Pingzkcsyb pz_kc = (Pingzkcsyb) kclist.get(0);
						//保存库存剩余表     作废张数=作废张数+作废张数      剩余张数=剩余张数-作废张数      
						pz_kc.getPingzkcsybid().setJigh(pz.getRukjg());
						pz_kc.getPingzkcsybid().setPingzlx(pz.getPingzgcbid().getPingzlx());
						pz_kc.setShengyzs(pz_kc.getShengyzs()-1);
						if(pz_kc.getShengyzs()<0){
							//剩余张数小于0 表示作废张数大于当前剩余张数
							pz_kc.setShengyzs(0);
						}
						if(pz_kc.getZuofzs()==null||"".equals(pz_kc.getZuofzs()))
						{
							pz_kc.setZuofzs(1);
						}else{
							pz_kc.setZuofzs(pz_kc.getZuofzs()+1);
						}
						zhongkpzDao.savePingz(pz_kc);
					}
		     }
				for(int i=0;i<zuofzsInt;i++)
				{
					Pingzgcb pzgc = (Pingzgcb) list.get(i);
					//凭证状态“3”表示作废
					if(pzgc.getPingzzt()!="作废")
					{
						pzgc.getPingzgcbid().setPingzh(pzgc.getPingzgcbid().getPingzh());
						pzgc.getPingzgcbid().setPingzlx(pzgc.getPingzgcbid().getPingzlx());
						pzgc.setGuiyh(guiyh);
						pzgc.setPingzzt("作废");
						zhongkpzDao.savePingz(pzgc);
					}
				}
		tr.commit();	
		} catch (SQLException e) {
			e.printStackTrace();
			tr.rollback();
			throw new Exception("作废失败！");
		}finally{
			zhongkpzDao.shifSession();
		}
	}

	//根据起始凭证号和终止凭证号查询凭证信息
	public List findPingzByPingzh(String qispzh, String zhongzpzh,String pingzlx) throws SQLException {
		return zhongkpzDao.findPingzByPingzh(qispzh, zhongzpzh,pingzlx);
	}

	//保存强制入库信息(具体实现步骤：1、删除2、重新入库)
	public void saveQzrk(String qispzh, String zhongzpzh, String pingzlx,String rukrq, String rukjg) throws SQLException{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try{
			//计算强制入库张数
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long qzrkzsLong = zhongzpzhLong - qispzhLong  + 1 ;
			String qzrkzs = String.valueOf(qzrkzsLong);
			int qzrkzsInt = Integer.parseInt(qzrkzs);
			//删除过程表中凭证号在起始凭证号和终止凭证号之间的凭证号记录信息
			zhongkpzDao.deletepz(qispzh,zhongzpzh,pingzlx);
			//凭证库存剩余表中张数做相应的变动（减少） 剩余张数=剩余张数-强制入库张数              总数=总数-强制入库张数
			List list = zhongkpzDao.findPingz(rukjg, pingzlx);
			Pingzkcsyb pzs = (Pingzkcsyb) list.get(0);
			if(list.size()==1)
			{
				pzs.getPingzkcsybid().setJigh(pzs.getPingzkcsybid().getJigh());
				pzs.getPingzkcsybid().setPingzlx(pzs.getPingzkcsybid().getPingzlx());
				pzs.setYilzs(pzs.getYilzs());
				pzs.setZuofzs(pzs.getZuofzs());
				pzs.setShengyzs(pzs.getShengyzs()-qzrkzsInt);
				if(pzs.getShengyzs()<0){
					pzs.setShengyzs(0);
				}else{
					pzs.setShengyzs(pzs.getShengyzs());
				}
				pzs.setZongs(pzs.getZongs()-qzrkzsInt);
				if(pzs.getZongs()<0){
					pzs.setZongs(0);
				}else{
					pzs.setZongs(pzs.getZongs());
				}
				zhongkpzDao.savePingz(pzs);
				//凭证机构剩余表中张数做相应的变动（减少）
			}else{
				pzs.getPingzkcsybid().setJigh(rukjg);
				pzs.getPingzkcsybid().setPingzlx(pingzlx);
				pzs.setShengyzs(pzs.getShengyzs()-qzrkzsInt);
				if(pzs.getShengyzs()<0){
					pzs.setShengyzs(0);
				}
				pzs.setZongs(pzs.getZongs()-qzrkzsInt);
				if(pzs.getZongs()<0){
					pzs.setZongs(0);
				}
				zhongkpzDao.savePingz(pzs);
			}
			tr.commit();
		}catch(Exception e){
			e.printStackTrace();
			tr.rollback();
		}finally{
			zhongkpzDao.shifSession();
		}
	}
	
	
}
