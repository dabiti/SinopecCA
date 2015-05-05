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
	 *���������Ϣ�����̱����ʣ���
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
			//ѭ�����������ʼƾ֤�ź���ֹƾ֤��֮���ƾ֤��Ϣ   ���̱�
			for(Long i=qispzh_;i<=zhongzpzh_;i++)
			{
				Pingzgcb pingzgcb = new Pingzgcb();
				pingzgcb.getPingzgcbid().setPingzh(qispzh.substring(0,qLen)+substr(String.valueOf(i),zLen));
				pingzgcb.getPingzgcbid().setPingzlx(pingzlx);
				pingzgcb.setRukrq(rukrq);
				pingzgcb.setRukjg(rukjg);
				pingzgcb.setPingzzt("δ��");//ƾ֤״̬ ��1����ʾδ��
				zhongkpzDao.savePingz(pingzgcb);
			}
			
			//���ʣ���
			List list = zhongkpzDao.findPingz(pingz.getPingzkcsybid().getJigh(),pingz.getPingzkcsybid().getPingzlx());
			//��ͬ�����š�ƾ֤���͵ļ�¼ֻ��һ��               ����=����+���������                  ʣ������=ʣ������+���������
			if (list.size()==1)
			{
				Pingzkcsyb pzkcsyObj=(Pingzkcsyb) list.get(0);
				Long qispzhLong = Long.parseLong(qispzh);
                Long zhongzpzhLong = Long.parseLong(zhongzpzh);
                Long zhangsLong = zhongzpzhLong - qispzhLong + 1;//���������
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
			throw new Exception("ƾ֤���¼��ʧ��!");
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
	 * �������������Ϣ(���̱����ʣ�������ʣ���)
	 */
	public String saveLybyJg(String jiglyfzr,String bens,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg ) throws Exception 
	{
		String qispzh="";
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//������û��������˲�Ϊ�գ��жϸù�Ա���Ƿ����
			if(jiglyfzr!=null&&!"".equals(jiglyfzr))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(jiglyfzr);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("�������ø����ˣ�[ " + jiglyfzr + " ] �����ڣ�");
				}
			}
			
			//��ѯ��ȡÿ��ƾ֤����
			List pzlist = zhongkpzDao.findMbzs(pingz.getPingzgcbid().getPingzlx());
			Voucher voucher = (Voucher)pzlist.get(0);
			int meibzs =voucher.getMeibzs();
			int bensInt = Integer.parseInt(bens);
			int lingyzs =meibzs * bensInt;//��������
			
			List kclist = zhongkpzDao.findly_jg(pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt());
			if(kclist.size()<lingyzs)
			{
				throw new Exception("���ʣ�������㣬���������뱾����");
			}
			//ͨ��ƾ֤���͡�ƾ֤״̬��ѯƾ֤��Ϣ
			List list = zhongkpzDao.findPz(pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt());
			for(int i=0;i<lingyzs;i++){
				Pingzgcb pzgc = (Pingzgcb)list.get(i);
				String lingyjg_ = pzgc.getLingyjg();
				
					//���û���Ϊ�գ���ǰƾ֤���Ա���������
					if(("".equals(lingyjg_)||lingyjg_==null))
					if(!(list.size()<lingyzs))	
					{
						for(int j=0;j<lingyzs;j++)
						   {
							  Pingzgcb pzgcObj = (Pingzgcb)list.get(j);
							  pzgcObj.setJiglyfzr(pingz.getJiglyfzr());
							  pzgcObj.setLingyjg(pingz.getLingyjg());
							  pzgcObj.getPingzgcbid().setPingzlx(pingz.getPingzgcbid().getPingzlx());
							  pzgcObj.setPingzzt("����");  //ƾ֤״̬��2�� ��ʾ����
							  pzgcObj.setGuiyh(""); //�����������֮����չ�Ա�ţ�������죬�����죩ʱ��Ա�Ų�Ϊ�գ��޷����õ�����
							  zhongkpzDao.saveGc(pzgcObj);
							  
							//������ʼƾ֤�š���ֹƾ֤��
							  if(j==0){
								  qispzh=pzgcObj.getPingzgcbid().getPingzh();
								  System.out.println(qispzh);
							  }
						   }	
					}
				}
			
			//���浽���ʣ���
			List list_kc = zhongkpzDao.findPingz(pzkc.getPingzkcsybid().getJigh(),pzkc.getPingzkcsybid().getPingzlx());
			//��ͬ�����š�ƾ֤���͵ļ�¼ֻ��һ��    ����=ʣ��+���� =����-��������    ʣ������=ʣ������-��������        ��������=��������+�������� 
			int lingyzsInt = meibzs * bensInt;//��������
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
			
				//���浽����ʣ���
				List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(),pzjg.getPingzjgsybid().getPingzlx());
				//��ͬ�����š�ƾ֤���͵ļ�¼ֻ��һ��       ʣ������=ʣ������+��������        
				if (list_jg.size()==1)
				{
					Pingzjgsyb pzjgsyObj=(Pingzjgsyb) list_jg.get(0);
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(pzjgsyObj.getShengyzs()+lingyzsInt);//�м�¼����ʣ������=ʣ������+��������
					zhongkpzDao.savePingz(pzjgsyObj);
					
				}else
				{
					Pingzjgsyb pzjgsyObj= new Pingzjgsyb();
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(lingyzsInt);//û�м�¼����ʣ������=��������
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
	 * �������������Ϣ(���̱�����ʣ���)
	 */
	public String saveLybyGr(String Guiyh,String bens, Pingzgcb pingz,Pingzjgsyb pzjg,String jigh) throws Exception
	{
		String qispzh="";
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//������ù�Ա��Ϊ�գ��жϸù�Ա���Ƿ����
			if(Guiyh!=null&&!"".equals(Guiyh))
			{
				Clerk guiyh = clerkDao.getClerkByCode(Guiyh);
				if(guiyh==null||"".equals(guiyh.getCode())){
					throw new Exception("���ù�Ա��[ " + Guiyh + " ] �����ڣ�");
				}
			}
			//��ѯ��ȡÿ��ƾ֤����
			List pzlist = zhongkpzDao.findMbzs(pingz.getPingzgcbid().getPingzlx());  
			//ͨ��ƾ֤���͡�ƾ֤״̬�����û��� ��ѯ���̱��е�ƾ֤��Ϣ          ���û���=pingz.getLingyjg()  guiyhΪ�ձ�ʾδ����Ա��������
			List list = zhongkpzDao.findPZ(pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt(),jigh);
			Voucher voucher = (Voucher)pzlist.get(0);
			int meibzs =voucher.getMeibzs();
			int bensInt = Integer.parseInt(bens);
			int lingyzs =meibzs * bensInt;//��������
			if(list.size()< lingyzs){
				//������ʾ���ʣ�������㣬���������뱾����
				throw new Exception("���ʣ�������㣬���������뱾����");
			}else{
				for(int i=0;i<lingyzs;i++){
					Pingzgcb pzgcObj=(Pingzgcb) list.get(i);
					if((pzgcObj.getLingyjg()==null||"".equals(pzgcObj.getLingyjg())))
					{
						//���û���Ϊ�գ��û���δ����������
						throw new Exception("���û���Ϊ�գ���ƾ֤δ���������ã�");
					}else{
						//���û�����Ϊ�գ���ʾ�ѱ��������ã���ִ�и������ò���
						  pzgcObj.setGuiyh(pingz.getGuiyh());
						  pzgcObj.setLingyjg(pzgcObj.getLingyjg());
						  pzgcObj.setJiglyfzr(pzgcObj.getJiglyfzr());
						  pzgcObj.getPingzgcbid().setPingzlx(pingz.getPingzgcbid().getPingzlx());
						  pzgcObj.setPingzzt("����");     //ƾ֤״̬��2�� ��ʾ����
						  zhongkpzDao.saveGc(pzgcObj);
						  //������ʼƾ֤�š���ֹƾ֤��
						  if(i==0){
							 qispzh=pzgcObj.getPingzgcbid().getPingzh();
						  }
					}
				}
			}
			
			//����ʣ���
			//��ͬ�����š�ƾ֤���͵ļ�¼ֻ��һ��       ʣ������=ʣ������-��������         ��������=��������+��������    
			List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(),pzjg.getPingzjgsybid().getPingzlx());
			//��ѯ��ȡÿ��ƾ֤����
			int lingyzsInt = meibzs * bensInt;//��������
			if (list_jg.size()==1)
			{
				Pingzjgsyb pzjgsyObj=(Pingzjgsyb) list_jg.get(0);
				if(pzjgsyObj.getYilzs()==null||"".equals(pzjgsyObj.getYilzs()))
				{
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(pzjgsyObj.getShengyzs()-lingyzsInt);//�м�¼����ʣ������=ʣ������-��������
					pzjgsyObj.setYilzs(lingyzsInt); //�м�¼������������=��������+��������  
					zhongkpzDao.savePingz(pzjgsyObj);	
				}else
				{
					pzjgsyObj.getPingzjgsybid().setJigh(pzjg.getPingzjgsybid().getJigh());
					pzjgsyObj.getPingzjgsybid().setPingzlx(pzjg.getPingzjgsybid().getPingzlx());
					pzjgsyObj.setShengyzs(pzjgsyObj.getShengyzs()-lingyzsInt);//�м�¼����ʣ������=ʣ������-��������
					pzjgsyObj.setYilzs(pzjgsyObj.getYilzs()+lingyzsInt); //�м�¼������������=��������+��������  
					zhongkpzDao.savePingz(pzjgsyObj);
				}
			}else{
				//�û������ʣ��������
				throw new Exception("�û������ʣ�������㣡");
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
	 * ��������˻���Ϣ(���̱�����ʣ���)
	 */
	public void saveTogc(String guiyh,String qispzh, String zhongzpzh, Pingzgcb pingz,Pingzjgsyb pzjg) throws Exception 
	{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//������ù�Ա��Ϊ�գ��жϸù�Ա���Ƿ����
			if(guiyh!=null&&!"".equals(guiyh))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(guiyh);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("���ù�Ա��[ " + guiyh + " ] �����ڣ�");
				}
			}
			
			//�����˻�����
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long tuihzsLong = zhongzpzhLong - qispzhLong  + 1 ;
			String tuihzs = String.valueOf(tuihzsLong);
			int tuihzsInt = Integer.parseInt(tuihzs);
			
			//�˻غ�������û������������ø����ˣ���Ա�ű��Ϊ�˻ع�Ա��Ա��    
			List list = zhongkpzDao.findPz(qispzh,zhongzpzh,pingz.getPingzgcbid().getPingzlx(),pingz.getPingzzt());
			if(list.size()<tuihzsInt)
			{
				throw new Exception("�˻��������ڸù�Աʣ��ƾ֤������");
			}
			for(int i=0;i<tuihzsInt;i++)
			{
				Pingzgcb pingzObj = (Pingzgcb) list.get(i);
				//list�����û������������ø����ˡ���Ա�ž���Ϊ�գ���ʾ��ǰ��¼��ƾ֤�ѱ���Ա���ã���ִ�и����˻ز���
				if(!(("".equals(pingzObj.getLingyjg())||pingzObj.getLingyjg()==null)&&("".equals(pingzObj.getGuiyh())||pingzObj.getGuiyh()==null)&&("".equals(pingzObj.getJiglyfzr())||pingzObj.getJiglyfzr()==null)))
				{
					pingzObj.getPingzgcbid().setPingzh(pingzObj.getPingzgcbid().getPingzh());
					pingzObj.getPingzgcbid().setPingzlx(pingzObj.getPingzgcbid().getPingzlx());
					/*pingzObj.setGuiyh(pingz.getGuiyh());
					pingzObj.setJiglyfzr("");
					pingzObj.setLingyjg("");*/
					pingzObj.setGuiyh("");
					pingzObj.setPingzzt("����");   //�Թ�Ա����ʽ��ƾ֤�˻����û���������ƾ֤״̬����Ϊ����
					zhongkpzDao.savePingz(pingzObj);
				}
			}
			
			//����ʣ���
			List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(),pzjg.getPingzjgsybid().getPingzlx());
			if(list_jg.size()==1)
			{
				//ʣ������м�¼
				Pingzjgsyb pz = (Pingzjgsyb) list_jg.get(0);
				Integer shengyzs = pz.getShengyzs();
				if("".equals(pz.getShengyzs())||pz.getShengyzs()==null)
				{
					//list��ʣ������Ϊ��         ʣ������=�˻�����        ��������=��������-�˻�����
					pz.setShengyzs(tuihzsInt);
					pz.setYilzs(pz.getYilzs()+tuihzsInt);
						zhongkpzDao.savePingz(pz);
				}else{
					
					//list��ʣ��������Ϊ��      ʣ������=ʣ������+�˻�����        ��������=��������-�˻�����
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
	 * ��������˻���Ϣ(���̱����ʣ�������ʣ���)
	 */
	public void  savetogc(String guiyh,String qispzh, String zhongzpzh,Pingzgcb pingz,Pingzkcsyb pzkc,Pingzjgsyb pzjg) throws Exception 
	{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//������ù�Ա��Ϊ�գ��жϸù�Ա���Ƿ����
			if(guiyh!=null&&!"".equals(guiyh))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(guiyh);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("���ù�Ա��[ " + guiyh + " ] �����ڣ�");
				}
			}
			//�����˻�����
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
					throw new Exception("�û���ʣ��ƾ֤����С���˻�����");
				}else{
					Pingzgcb pz =(Pingzgcb) list.get(i);
					pz.setGuiyh(pingz.getGuiyh());
					pz.setPingzzt("δ��");   //ƾ֤״̬��1����ʾδ��
					pz.setLingyjg("");
					pz.setJiglyfzr("");
					zhongkpzDao.savePingz(pz);
				}
			}
			
			//���ʣ���
			List list_kc = zhongkpzDao.findPingz(pzkc.getPingzkcsybid().getJigh(),pzkc.getPingzkcsybid().getPingzlx());
			Pingzkcsyb pzObj = (Pingzkcsyb) list_kc.get(0); 
			if("".equals(pzObj.getShengyzs())||pzObj.getShengyzs()==null)
			{
				//ʣ������Ϊ0ʱ
				pzObj.setShengyzs(tuihzsInt);
				pzObj.setZongs(pzObj.getZongs()+tuihzsInt);
				pzObj.setYilzs(pzObj.getYilzs()-tuihzsInt);
				zhongkpzDao.savePingz(pzObj);
			}else{
				//ʣ��������Ϊ0ʱ       
				pzObj.setShengyzs(pzObj.getShengyzs()+tuihzsInt);
				pzObj.setZongs(pzObj.getZongs()+tuihzsInt);
				pzObj.setYilzs(pzObj.getYilzs()-tuihzsInt);
				zhongkpzDao.savePingz(pzObj);
			}
			
			//����ʣ���
			List list_jg = zhongkpzDao.findPingzjg(pzjg.getPingzjgsybid().getJigh(), pzjg.getPingzjgsybid().getPingzlx());
			Pingzjgsyb pz = (Pingzjgsyb) list_jg.get(0);
			if("".equals(pz.getShengyzs())||pz.getShengyzs()==null)
			{
				/*//ʣ������Ϊ0ʱ   
				pz.setShengyzs(tuihzsInt);
				pz.setYilzs(pz.getYilzs()-tuihzsInt);
				zhongkpzDao.savePingz(pz);*/
			}else{
				//ʣ��������Ϊ0
				pz.setShengyzs(pz.getShengyzs()-tuihzsInt);
				//�������ʣ���������������ʣ������������������Ϊ0����Ӧ��ɾ���ü�¼
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

	//����������Ϣ(���̱����ʣ�������ʣ���)
	public void savezf(String qispzh, String zhongzpzh,String guiyh, Pingzgcb pingz,Pingzkcsyb pzkc, Pingzjgsyb pzjg) throws Exception
	{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try {
			
			//������ù�Ա��Ϊ�գ��жϸù�Ա���Ƿ����
			if(guiyh!=null&&!"".equals(guiyh))
			{
				Clerk Guiyh = clerkDao.getClerkByCode(guiyh);
				if(Guiyh==null||"".equals(Guiyh.getCode())){
					throw new Exception("���ù�Ա��[ " + guiyh + " ] �����ڣ�");
				}
			}
			
			//������������
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long zuofzsLong = zhongzpzhLong - qispzhLong  + 1 ;
			String zuofzs = String.valueOf(zuofzsLong);
			int zuofzsInt = Integer.parseInt(zuofzs);
		
		//��ѯ���̱��еļ�¼��Ϣ ������
		List list = zhongkpzDao.findpz(qispzh, zhongzpzh,pingz.getPingzgcbid().getPingzlx());
		  if(list.size()<zuofzsInt)
		   {
			 throw new Exception("���ʣ�಻�㣬������������ʼ����ֹƾ֤�ţ�");
		  }
		     for(int j=0;j<list.size();j++){
		    	 Pingzgcb pz = (Pingzgcb) list.get(j);
					if(!(pz.getLingyjg()==null||"".equals(pz.getLingyjg())))
					{
						//���û�����Ϊ�գ� ����ʣ������ʣ�����䶯
						List jglist = zhongkpzDao.findPingzjg(pz.getLingyjg(), pz.getPingzgcbid().getPingzlx());
						List kclist = zhongkpzDao.findPingz(pz.getRukjg(),pz.getPingzgcbid().getPingzlx());
						
						//���û�������Ա�Ų�Ϊ�գ���ʾ��ƾ֤�ѱ���Ա����
						if(pz.getGuiyh() !=null ||!("".equals(pz.getGuiyh())))
						{
							Pingzkcsyb pz_kc = (Pingzkcsyb) kclist.get(0);
							//������ʣ���     ��������=��������+��������      ��������=��������-��������      
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
							//���û�������Ա�Ŷ���Ϊ�գ�Ϊ��Ա����      ����ʣ����б䶯       ��������=��������-��������        ��������=��������+��������
							pz_jg.getPingzjgsybid().setJigh(pz.getLingyjg());
							pz_jg.getPingzjgsybid().setPingzlx(pz.getPingzgcbid().getPingzlx());
							if(pz_jg.getYilzs()==null||"".equals(pz_jg.getYilzs())){
								pz_jg.setYilzs(0);
							}else{
								pz_jg.setYilzs(pz_jg.getYilzs()-1);
								if((pz_jg.getYilzs())<0){
									//��������С��0��ʾ�����������ڵ�ǰʣ������
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
							//���û���Ϊ�գ���Ա�Ų�Ϊ��    Ϊ��������    ����ʣ����б䶯    ʣ������=ʣ������-��������     ��������=��������+��������
							pz_jg.getPingzjgsybid().setJigh(pz.getLingyjg());
							pz_jg.getPingzjgsybid().setPingzlx(pz.getPingzgcbid().getPingzlx());
							pz_jg.setShengyzs(pz_jg.getShengyzs()-1);
							if(pz_jg.getShengyzs()<0){
								//ʣ������С��0��ʾ�����������ڵ�ǰʣ������
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
						//���û���Ϊ�գ���������Ϊ��      ���ʣ���䶯
						List kclist = zhongkpzDao.findPingz(pz.getRukjg(),pz.getPingzgcbid().getPingzlx());
						Pingzkcsyb pz_kc = (Pingzkcsyb) kclist.get(0);
						//������ʣ���     ��������=��������+��������      ʣ������=ʣ������-��������      
						pz_kc.getPingzkcsybid().setJigh(pz.getRukjg());
						pz_kc.getPingzkcsybid().setPingzlx(pz.getPingzgcbid().getPingzlx());
						pz_kc.setShengyzs(pz_kc.getShengyzs()-1);
						if(pz_kc.getShengyzs()<0){
							//ʣ������С��0 ��ʾ�����������ڵ�ǰʣ������
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
					//ƾ֤״̬��3����ʾ����
					if(pzgc.getPingzzt()!="����")
					{
						pzgc.getPingzgcbid().setPingzh(pzgc.getPingzgcbid().getPingzh());
						pzgc.getPingzgcbid().setPingzlx(pzgc.getPingzgcbid().getPingzlx());
						pzgc.setGuiyh(guiyh);
						pzgc.setPingzzt("����");
						zhongkpzDao.savePingz(pzgc);
					}
				}
		tr.commit();	
		} catch (SQLException e) {
			e.printStackTrace();
			tr.rollback();
			throw new Exception("����ʧ�ܣ�");
		}finally{
			zhongkpzDao.shifSession();
		}
	}

	//������ʼƾ֤�ź���ֹƾ֤�Ų�ѯƾ֤��Ϣ
	public List findPingzByPingzh(String qispzh, String zhongzpzh,String pingzlx) throws SQLException {
		return zhongkpzDao.findPingzByPingzh(qispzh, zhongzpzh,pingzlx);
	}

	//����ǿ�������Ϣ(����ʵ�ֲ��裺1��ɾ��2���������)
	public void saveQzrk(String qispzh, String zhongzpzh, String pingzlx,String rukrq, String rukjg) throws SQLException{
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhongkpzDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try{
			//����ǿ���������
			Long qispzhLong = Long.parseLong(qispzh);
			Long zhongzpzhLong = Long.parseLong(zhongzpzh);
			Long qzrkzsLong = zhongzpzhLong - qispzhLong  + 1 ;
			String qzrkzs = String.valueOf(qzrkzsLong);
			int qzrkzsInt = Integer.parseInt(qzrkzs);
			//ɾ�����̱���ƾ֤������ʼƾ֤�ź���ֹƾ֤��֮���ƾ֤�ż�¼��Ϣ
			zhongkpzDao.deletepz(qispzh,zhongzpzh,pingzlx);
			//ƾ֤���ʣ�������������Ӧ�ı䶯�����٣� ʣ������=ʣ������-ǿ���������              ����=����-ǿ���������
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
				//ƾ֤����ʣ�������������Ӧ�ı䶯�����٣�
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
