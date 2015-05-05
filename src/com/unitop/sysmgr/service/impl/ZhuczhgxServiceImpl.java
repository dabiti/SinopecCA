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
 * �����˻���ϵά��
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
 	
	//��ȡ���˻���Ϣ�б�
	public List getzizh(String zhangh) throws SQLException {
		return zhuczhgxDao.getzizh(zhangh);
	}

	/*
	 * ����ӡ����Ϣ���½����ӹ�ϵ��
	 * by wp
	 */
	public void saveyinj(String fuyrq,String congzh,String mainaccount,Zhanghb zhanghb_c,Zhanghb zhanghb_z) throws Exception
	{
		//�½����ӹ�ϵ         1�����˻�ԭ��ӡ��ͣ�ã�ͣ������Ϊ�������ڵ���(yinjb)2�������˻���zhuzh�ֶθ���Ϊ���˺�(zhanghb),��ȡ��������
		Session session =this.getBaseHibernateDao().getHibernateSession();
		zhuczhgxDao.set_Session(session);
		yinjbDao.set_Session(session);
		yinjzhbDao.set_Session(session);
		zhanghbDao.set_Session(session);
		Transaction tr = session.beginTransaction();
		try 
		{
			List yinjc_oldlist=yinjbDao.getYinj(congzh);//���˻���ӡ����Ϣ
			List yinjz_oldlist=yinjbDao.getYinj(mainaccount);//���˻���ӡ����Ϣ
			List zuhc_oldlist=yinjzhbDao.getYinjzh(congzh);//���˻��������Ϣ
			List zuhz_oldlist=yinjzhbDao.getYinjzh(mainaccount);//���˻��������Ϣ
			
			Zhanghb z_zhanghb = zhanghbDao.getZhanghb(mainaccount);//���˻���Ϣ
			Zhanghb c_zhanghb = zhanghbDao.getZhanghb(congzh);//���˻���Ϣ
			
			Yinjzhb yinjzhb_c =new Yinjzhb();//���˻�ӡ����ϱ�ʵ��
			Yinjzhb yinjzhb_z =new Yinjzhb();//���˻�ӡ����ϱ�ʵ��
			Yinjb yinjb_c = new Yinjb();//���˻�ӡ����ʵ��
			Yinjb yinjb_z = new Yinjb();//���˻�ӡ��ʵ��
			
			//�����˻�������ͬ
			if(zhanghb_c.getZhangh().equals(zhanghb_z.getZhangh()))
			{
				throw new Exception("�����˻�������ͬһ���˻���");
			}
			//���˺��Ƿ���ڼ���Ч
			if(z_zhanghb.getZhangh()==null)
			{
				throw new Exception("���˻������ڣ�");
			}else{
				if(!("��Ч".equals( zhanghb_z.getZhanghzt())))
				{
					throw new Exception("���˻�����Ϊ��Ч�˻����ܽ������ӹ�ϵ��");
				}
			}
			//���˻��Ƿ���ڼ���Ч
			if(c_zhanghb.getZhangh()==null)
			{
				throw new Exception("���˻������ڣ�");
			}else{
				if(!("��Ч".equals( zhanghb_c.getZhanghzt())))
				{
					throw new Exception("���˻�����Ϊ��Ч�˻����ܽ������ӹ�ϵ��");
				}
			}
			//���˻����������˻������˻�       ���˻����������˻��Ĵ��˻�
			List clist = zhuczhgxDao.getzizh(congzh);//�ж�congzh�Ƿ������˻�
			if(clist.size()==0)
			{
				if(!(zhanghb_z.getZhuzh()==null||"".equals(zhanghb_z.getZhuzh())))
				{
					throw new Exception("���˻����������˻��Ĵ��˻��������½����ӹ�ϵ��");	
				}
			}else{
				throw new Exception("���˻����������˻������˻��������½����ӹ�ϵ��");
			}
			
			//�½���ϵʱ�����˻��±�����ӡ��
			if(yinjz_oldlist.size()>0)
			{
				//���˻�ӡ�����У��
				for(int i=0;i<yinjz_oldlist.size();i++)
				{
					yinjb_z = (Yinjb) yinjz_oldlist.get(i);
					if(!("����".equals(yinjb_z.getYinjshzt())))
					{
						throw new Exception("���˻�����δ���ӡ�����½������˻���ϵʧ�ܣ�");
					}
				}
				//������˻�������ϣ�����������
				if(zuhz_oldlist.size()>0){
					//���˻�������У��
					for(int i=0;i<zuhz_oldlist.size();i++)
					{
						yinjzhb_z = (Yinjzhb) zuhz_oldlist.get(i);
						if(!("����".equals(yinjzhb_z.getZuhshzt()))){
							throw new Exception("���˻�����δ�����ϣ��½������˻���ϵʧ�ܣ�");
						}
					}
				}
			}else
			{
				throw new Exception("���˻���û��ӡ�����½������˻���ϵʧ�ܣ�");
			}
			
			//�½���ϵʱ�����˻��²�һ��Ҫ��ӡ������ϣ������У�����Ϊ����
			if(yinjc_oldlist.size()>0)
			{
				//���˻�ӡ�����У��   
				for(int i=0;i<yinjc_oldlist.size();i++)
				{
					yinjb_c = (Yinjb) yinjc_oldlist.get(i);
				 if(!("����".equals(yinjb_c.getYinjshzt())))
					{
						throw new Exception("���˻�����δ���ӡ�����½������˻���ϵʧ�ܣ�");
					}
				}
			}
			if(zuhc_oldlist.size()>0)
			{
				//���˻�������У��
				for(int i=0;i<zuhc_oldlist.size();i++)
				{
					yinjzhb_c = (Yinjzhb) zuhc_oldlist.get(i);
					if(!("����".equals(yinjzhb_c.getZuhshzt())))
					{
						throw new Exception("���˻�����δ�����ϣ��½������˻���ϵʧ�ܣ�");
					}
				}
			}
			
			//�������ڱ�����ڴ��˻�����������ӡ������������
			List qiyrqlist = zhuczhgxDao.getqiyrq(congzh);
			for(int i=0;i<qiyrqlist.size();i++)
			{
				Yinjb yinjb = (Yinjb) qiyrqlist.get(i);//���˻�ӡ��
				String qiyrq = yinjb.getYinjid().getQiyrq();
				int res_q = qiyrq.compareTo(fuyrq); //res_q<0 		��������С�ڸ�������
				if(res_q>0||res_q==0)
				{
					throw new Exception("���˻��°��������������ڻ���ڵ�ǰ�������ڵ�ӡ���������½���ϵ�����������븴�����ڣ�");
				}
			}
			
			//�������ڱ�����ڴ��˻�������������ϵ���������
			List Qiyrqlist = zhuczhgxDao.getQiyrq(congzh);
			for(int i=0;i<Qiyrqlist.size();i++)
			{
				Yinjzhb yinjzh = (Yinjzhb) Qiyrqlist.get(i);
				String qiyrq = yinjzh.getYinjzhid().getQiyrq();
				int res_q = qiyrq.compareTo(fuyrq); //res_q<0 		��������С�ڸ�������
				if(res_q>0||res_q==0)
				{
					throw new Exception("���˻��°��������������ڻ���ڵ�ǰ�������ڵ���ϣ������½���ϵ�����������븴�����ڣ�");
				}
			}
			
			//��ȡ���˻�ӡ������������������
			List qyrqlist = zhuczhgxDao.getqiyrq(mainaccount);
			for(int i=0;i<qyrqlist.size();i++)
			{
				Yinjb yinj = (Yinjb) qyrqlist.get(i);//���˻�ӡ��
				String Qiyrq = yinj.getYinjid().getQiyrq();
			    int res_q = Qiyrq.compareTo(fuyrq); //res_q<0 		��������С�ڸ�������
			    if(res_q<0||res_q==0)
			    {
			    	//ӡ����   ͣ�ô��˻�ԭ��ӡ�� 
			    	for(int j=0;j<yinjc_oldlist.size();j++)
			    	{
			    		yinjb_c = (Yinjb) yinjc_oldlist.get(j);
			    		yinjb_c.setTingyrq(fuyrq);//ͣ������Ϊ��������
						zhuczhgxDao.saveyinj(yinjb_c);
			    	}
					
					//�˻���  �������ӹ�ϵ
			
			    	Zhanghb zhanghbc = zhanghbDao.getZhanghb(congzh);
			    	zhanghbc.setZhuzh(mainaccount);
			    	zhanghbc.setFuyrq(fuyrq);
					zhuczhgxDao.savezhanghb(zhanghbc);
					
					//ӡ����ϱ�   ͣ�ô��˻�ԭ�����
					for(int k=0;k<zuhc_oldlist.size();k++)
					{
						yinjzhb_c=(Yinjzhb) zuhc_oldlist.get(k);
						yinjzhb_c.setTingyrq(fuyrq);//ͣ������Ϊ��������
						zhuczhgxDao.savezuh(yinjzhb_c);
					}
			     }else{
			    	 throw new Exception("���˻�ӡ���������������ڸ������ڣ�");
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
	 *ȡ�����ӹ�ϵ
	 *by wp  
	 */
	public void doquxgx(String czhangh, String zzhangh,String quxfyrq_) throws Exception
	{
		try {
			Zhanghb z_zhanghb = zhanghbDao.getZhanghb(zzhangh);//���˻���Ϣ
			Zhanghb c_zhanghb = zhanghbDao.getZhanghb(czhangh);//���˻���Ϣ
			//���˻����ڡ���ЧУ��
			if("".equals(z_zhanghb.getZhangh())&&z_zhanghb.getZhangh()==null)
			{
				throw new Exception("���˻������ڣ�"); 
			}else{
				if(!("��Ч".equals(z_zhanghb.getZhanghzt())&&"��Ч".equals(c_zhanghb.getZhanghzt())))
				{
					throw new Exception("���˻�����˻�������Ч�˻������ܽ���ȡ�����ӹ�ϵ������"); 
				}
			}
			//���˻����״̬Ч��
			if(!("����".equals(z_zhanghb.getZhanghshzt())))
			{
				throw new Exception("���˻����״̬�������󣬲��ܽ���ȡ�����ӹ�ϵ������"); 
			}
			
			List yinj_zlist =yinjbDao.getYinj(zzhangh);
			List yinj_clist =yinjbDao.getYinj(czhangh);
			List zuh_zlist =yinjzhbDao.getYinjzh(zzhangh);
			List zuh_clist =yinjzhbDao.getYinjzh(czhangh);
			
			//���˻����״̬У�飨���˻�����ӡ��ʱ��
			if((yinj_clist.size()!=0))
			{
				if(!("����".equals(c_zhanghb.getZhanghshzt())))
				{
					throw new Exception("���˻����״̬�������󣬲��ܽ���ȡ�����ӹ�ϵ������"); 
				}	
			}
			
			//���˻�����������ϣ��������Ϣ����Ϊ����
			if(zuh_zlist.size()>0){
				for(int i=0;i<zuh_zlist.size();i++)
				{
					Yinjzhb yinjzh=(Yinjzhb) zuh_zlist.get(i);
					if(!("����".equals(yinjzh.getZuhshzt()))){
						throw new Exception("���˻��´���δ����ϣ�ȡ�����ӹ�ϵʧ�ܣ�"); 	
					}
				}
			}

			//ȡ����ϵʱ�����˻��±������ӡ��
			  if(yinj_zlist.size()>0)
			{
				  for(int i=0;i<yinj_zlist.size();i++)
				  {
					  Yinjb yinj_z = (Yinjb) yinj_zlist.get(i);
					  if(!("����".equals(yinj_z.getYinjshzt())))
					  {
						  throw new Exception("���˻��´���δ��ӡ����ȡ�����ӹ�ϵʧ�ܣ�"); 
					  }	  
				  }
			}else{
				 throw new Exception("���˻���û��ӡ����Ϣ��ȡ�����ӹ�ϵʧ�ܣ�"); 
			}
			
			//ȡ����ϵʱ�����˻�������ӡ��������Ϊ����
			if(yinj_clist.size()>0)
			{ 
				for(int i=0;i<yinj_clist.size();i++)
				{
					Yinjb yinj_c = (Yinjb) yinj_clist.get(i);
					if(!("����".equals(yinj_c.getYinjshzt())))
					{
						throw new Exception("���˻��´���δ��˵�ӡ����ȡ�����ӹ�ϵʧ�ܣ�"); 
					}
				}
			}
			
			//ȡ����ϵʱ�����˻���������ϣ�����Ϊ����
			if(zuh_clist.size()>0)
			{
				for(int i=0;i<zuh_clist.size();i++)
				{
					Yinjzhb zuh_c = (Yinjzhb) zuh_clist.get(i);
					if(!("����".equals(zuh_c.getZuhshzt())))
					{
						throw new Exception("���˻��´���δ��˵���ϣ�ȡ�����ӹ�ϵʧ�ܣ�"); 
					}
				}
				
			}
			zhuczhgxDao.doquxgx(zzhangh, czhangh,quxfyrq_);
		} catch (SQLException e){
			throw e;
		}
	}
	
	
}