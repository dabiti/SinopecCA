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
 * ��ѯ��Ϣ��ѯ�ӿ�
 */
public interface ZhanghbService {
	
	/*
	 * ��ȡ�ʺ�����
	 */
	public Zhanghb getZhanghb(String zhangh)throws BusinessException;
	/*
	 * ��ȡ�ͻ�-�ʺ�
	 */
	public List getKehh(String kehh,String jigh);

	/*
	 * �����ָ�
	 */
	public void recoverAccount(String zhangh,boolean yinjkFlag, String yzhanghxz);
	/*
	 * �����ָ�
	 */
	public void recoverAccount(String zhangh,boolean yinjkFlag);
	
	/*
	 * �ʺ�����ɾ��
	 */
	public void physicsdelete(String zhangh);
	
	/*
	 * �˻�����ͳ��
	 */
	public AccountNum zhanghNumber(String orgCode,String huobh,String shifbhxj);
	/**
	 * <dl>
	 * <dt><b>closeAccount:����</b></dt>
	 * <dd></dd>
	 * </dl>
	 * @param yinjkhList 
	*/
	public void closeAccount(String zhangh, List<String> yinjkhList);
	
	//����
	public void dongjAccount(String zhangh);
	//��ʧ
	public void guasAccount(String zhangh);
	
	//��ȡ���˻��б���Ϣ
	public List getzzhlist(String account)throws SQLException;
	
	//��ȡyinjb��Ϣ
	public List getYinjb(String account);
	//���濪����Ϣ
	public void createZhanghb(Zhanghb zhanghb, List<String> yinjkbhList) throws Exception;
	public void update(Zhanghb zhanghb)throws Exception;
	public Zhanghb getZhanghbByYinjkbh( String yinjkbh);
	public String getXiaohsj(String zhangh);
	
	
	//��ȡ ӡ�������õ��˺�
	public TabsBo getYinjkShareList(String orgcode, String account ,String yinjkbh);

	public String getInternalReleaseZhangh(String rule);
	public void updateZhanghb(Zhanghb zhanghb, List<String> yinjkbhList,
			List<String> oldYinjkbhList);

	//�����˺Ż�ȡӡ������
	public String getYinjkhByZhangh(String zhangh );
	
	//��ѯ�˻������Ƿ��б䶯
	public Zhanghtbb ischange(String zhangh);
	
	

	//�����˺��Ƿ�ȫ������
	public boolean isHaveTrial(Zhanghb zhanghb);
	
	
	//��ѯ��Ҫͬ���˻�������Ϣ
	public String queryallfortongbu(String zhangh) throws BusinessException;
	
	
	
	//ͬ��Socket
	public String toSocket(String sendAddress,String sendMapStr) throws UnsupportedEncodingException;
	public String returnTongbu(String receiveStr, String tongbzh);
	public int queryShareAccountNum(String zhangh);

	
	//�����������ӡ��
	public List getLastYSyj(String zhangh) throws BusinessException;
	
	//��ȡ�����������
	public List getLastYSzh(String zhangh) throws BusinessException ;
	
	public void cancleYinjk(String zhangh, List<String> yinjkhList);
	
	//��ѯ�˻����������Ƿ���Ҫ������������
	public boolean canCancleYinjk(String zhangh);
	//��ѯ�˻����������ָ�ʱ �Ƿ���Ҫ����ӡ�����ָ�����
	public boolean canResumeYinjk(String zhangh);
	
	
	// ��ѯ�ʺ�������Ϣ
	public ArrayList<Zhanghxzb> getZhanghxzList() ;
	public int countTodoZhanghbList(String jigh);
	public List<Zhanghb> getToDoZhanghbList(String jigh);
	//�����˻���ѯ����Ч�����˻�
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
