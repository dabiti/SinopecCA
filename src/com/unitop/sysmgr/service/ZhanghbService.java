package com.unitop.sysmgr.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.unitop.exception.BusinessException;
import com.unitop.sysmgr.bo.AccountNum;
import com.unitop.sysmgr.bo.TabsBo;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.sysmgr.bo.Zhanghtbb;
import com.unitop.sysmgr.bo.Zhanghxzb;
import com.unitop.sysmgr.bo.Zhipyxxx;

/*
 * 查询信息查询接口
 */
public interface ZhanghbService {
	
	/*
	 * 获取帐号资料
	 */
	public Zhanghb getZhanghb(String zhangh)throws BusinessException;
	/*
	 * 获取客户-帐号
	 */
	public List getKehh(String kehh,String jigh);

	/*
	 * 销户恢复
	 */
	public void recoverAccount(String zhangh,boolean yinjkFlag, String yzhanghxz);
	/*
	 * 销户恢复
	 */
	public void recoverAccount(String zhangh,boolean yinjkFlag);
	
	/*
	 * 帐号物理删除
	 */
	public void physicsdelete(String zhangh);
	
	/*
	 * 账户数量统计
	 */
	public AccountNum zhanghNumber(String orgCode,String huobh,String shifbhxj);
	/**
	 * <dl>
	 * <dt><b>closeAccount:销户</b></dt>
	 * <dd></dd>
	 * </dl>
	 * @param yinjkhList 
	*/
	public void closeAccount(String zhangh, List<String> yinjkhList);
	
	//冻结
	public void dongjAccount(String zhangh);
	//挂失
	public void guasAccount(String zhangh);
	
	//获取子账户列表信息
	public List getzzhlist(String account)throws SQLException;
	
	//获取yinjb信息
	public List getYinjb(String account);
	//保存开户信息
	public void createZhanghb(Zhanghb zhanghb, List<String> yinjkbhList) throws Exception;
	public void update(Zhanghb zhanghb)throws Exception;
	public Zhanghb getZhanghbByYinjkbh( String yinjkbh);
	public String getXiaohsj(String zhangh);
	
	
	//获取 印鉴卡共用的账号
	public TabsBo getYinjkShareList(String orgcode, String account ,String yinjkbh);

	public String getInternalReleaseZhangh(String rule);
	public void updateZhanghb(Zhanghb zhanghb, List<String> yinjkbhList,
			List<String> oldYinjkbhList);

	//根据账号获取印鉴卡号
	public String getYinjkhByZhangh(String zhangh );
	
	//查询账户当日是否有变动
	public Zhanghtbb ischange(String zhangh);
	
	

	//检查该账号是否全部已审
	public boolean isHaveTrial(Zhanghb zhanghb);
	
	
	//查询需要同步账户所有信息
	public String queryallfortongbu(String zhangh) throws BusinessException;
	
	
	
	//同步Socket
	public String toSocket(String sendAddress,String sendMapStr) throws UnsupportedEncodingException;
	public String returnTongbu(String receiveStr, String tongbzh);
	public int queryShareAccountNum(String zhangh);

	
	//获得最新已审印鉴
	public List getLastYSyj(String zhangh) throws BusinessException;
	
	//获取最新已审组合
	public List getLastYSzh(String zhangh) throws BusinessException ;
	
	public void cancleYinjk(String zhangh, List<String> yinjkhList);
	
	//查询账户在销户是是否需要进行销卡操作
	public boolean canCancleYinjk(String zhangh);
	//查询账户在做销户恢复时 是否需要进行印鉴卡恢复操作
	public boolean canResumeYinjk(String zhangh);
	
	
	// 查询帐号性质信息
	public ArrayList<Zhanghxzb> getZhanghxzList() ;
	public int countTodoZhanghbList(String jigh);
	public List<Zhanghb> getToDoZhanghbList(String jigh);
	//根据账户查询其有效的子账户
	public List<Zhanghb> getZhangbListByZzh(String zhangh);
	public String getJiankgy(String account);
	public String queryXiaohqzt(String zhangh);
	public boolean cancleZhuzh(String zhangh, String gszh);
	public String getZhanghFromShort(String zhangh);
	public String parseTypeN(String n,int len);
	public boolean importZhanghbTemp(String shenghh,HSSFSheet sheet);
	public List<Zhanghb> getZhanghbExcelInfo(String shenghh);
	public List<String> checkZhanghbExcelInfo(List<Zhanghb> zhanghbExcelList);
	public Zhanghb getZhanghbTemp(String account);
	public void tongbAccountinfoFromTemp(Zhanghb zhanghb);
	public void deleteZhanghb_Excel(String shenghh) ;
	public List<String> importZhanghzhb(HSSFSheet sheet)throws Exception ;
	public void deleteZhanghb_Error(String shenghh,String type);
	public void inserZhanghb_Error(String shenghh,List<String> errorlist,String type);
	public List<String> getZhanghb_Error(String shenghh,String type);
	
}
