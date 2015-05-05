package com.unitop.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.sinodata.common.sequence.SequenceObtain;
import com.sinodata.report.model.ReportDictionary;
import com.sinodata.report.util.InitDictionary;
import com.sinodata.report.util.UeportUserDefined;
import com.sinodata.table.model.Table;
import com.sinodata.table.util.TableUserDefined;
import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.dao.DanbwhDao;
import com.unitop.sysmgr.dao.SystemControlParametersDao;
import com.unitop.sysmgr.service.BusinessService;
import com.unitop.sysmgr.service.InitSystem;
import com.unitop.sysmgr.service.PromptService;
/*
 * ��������
 */
@Service("InitSystemImpl")
public class InitSystemImpl extends BaseServiceImpl implements ApplicationListener,InitSystem {
	
	@Resource
	private SystemControlParametersDao SystemControlParametersDao;
	@Resource
	public DanbwhDao DanbwhDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private PromptService prompteService;
	@Resource
	private SequenceObtain sequenceObtain;
	//ϵͳ�����Զ�ͬ��
	public void onApplicationEvent(ApplicationEvent event){
		this.systemParameters();
	}
	
	//�ֶ�ͬ��
	public void synchronousSystemParameters(){
		SystemConfig systemConfig = new SystemConfig();
		String datasyncStr =  systemConfig.getValue("systemmodle");
		if("test".equals(datasyncStr))
		{
			this.systemParameters();
		}
	}
	
	public void systemParameters() {
		//��ʼ������
		SystemConfig systemConfig = new SystemConfig();
		try {
			systemConfig.initArticleCategory(SystemControlParametersDao.findSystemControlParameters());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ͬ��ҵ��������ʾ��Ϣ����
		{
			businessService.getYewgzRules();//����ҵ�������Ϣ���ڴ�
			prompteService.getTisxxMap();//���ؽ�����ʾ��Ϣ
		}
		//ϵͳ���к� ��ʼ��
		sequenceObtain.init();
		//ureport�����û��Զ���
		Table table = new Table("select * from R_BAOBPZ");
		try {
			List list = DanbwhDao.doSelect(table);
			if(list==null) list = new ArrayList();
			Map ureportClassPath = new HashMap();
			for(int i=0;i<list.size();i++)
			{
				Map map = (Map) list.get(i);
				ureportClassPath.put(map.get("baobbs"), map.get("zhidyl"));
			}
			new UeportUserDefined(ureportClassPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ureport�ֵ��ʼ��
		table = new Table("select * from R_ZHIDPZ");
		try {
			List list = DanbwhDao.doSelect(table);
			if(list==null) list = new ArrayList();
			Map dictionaryMap = new HashMap();
			for(int i=0;i<list.size();i++)
			{
				Map map = (Map) list.get(i);
				
				String zhidbs = (String) map.get("zhidbs");
				String suoyz = (String) map.get("suoyz");
				String zhuanhz = (String) map.get("zhuanhz");
				
				Object obj =  dictionaryMap.get(zhidbs);
				if(obj==null)
				{
					ReportDictionary reportDictionary= new ReportDictionary();
					reportDictionary.putDictionary(suoyz,zhuanhz);
					dictionaryMap.put(zhidbs,reportDictionary);
				}else{
					ReportDictionary reportDictionary=(ReportDictionary) obj;
					reportDictionary.putDictionary(suoyz, zhuanhz);
				}
			}
			 new InitDictionary(dictionaryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//table�����û��Զ�����
		table = new Table("select * from DANBWHZB");
		try {
			List list = DanbwhDao.doSelect(table);
			if(list==null) list = new ArrayList();
			Map tableClassPath = new HashMap();
			for(int i=0;i<list.size();i++)
			{
				Map map = (Map) list.get(i);
				tableClassPath.put(map.get("gongnid"), map.get("zhidyl"));
			}
			new TableUserDefined(tableClassPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  		
}