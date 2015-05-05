package com.unitop.sysmgr.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.corba.se.spi.orb.Operation;
import com.unitop.exception.DAOException;
import com.unitop.framework.util.JsonTool;
import com.unitop.sysmgr.bo.Zhanghtbb;
import com.unitop.sysmgr.dao.YinjbDao;
import com.unitop.sysmgr.dao.YinjzhbDao;
import com.unitop.sysmgr.dao.ZhanghbDao;
import com.unitop.sysmgr.dao.ZhanghtbbDao;
import com.unitop.sysmgr.service.TongbuService;


@Service("TongbuService")
public class TongbuServiceImpl implements TongbuService {

	private static Logger log = Logger.getLogger(TongbuServiceImpl.class);

	@Resource
	private ZhanghtbbDao zhanghtbbDao ;
	@Resource
	private ZhanghbDao zhanghbDao ;
	@Resource
	private YinjbDao yinjbDao;
	@Resource
	private YinjzhbDao yinjzhbDao;
	
	public Map<String, String> queryTongbu() throws DAOException {
		
		// �����map
		Map<String, String> sendMap = new HashMap<String, String>();

		
		// ��ѯ��ͬ������������Ϣ
		log.info("��ʼ��ѯͬ���������м�¼��");
		List<Map<String, String>> tongbbList = zhanghtbbDao.queryTongbbListForNight();
		log.info("��ѯ���ListΪ��" + tongbbList);
		String zhangh = null;
		String caozlx = null;
		String shenghjgh = null;
		// ѭ������ͬ������ÿ����¼
		log.info("��ʼ����ͬ������������Ϣ��");
		for (Map<String, String> tongbsj : tongbbList) {
			// �˺�
			zhangh = tongbsj.get("ZHANGH");
			// ��������
			caozlx = tongbsj.get("CAOZLX");
			// shenghjgh
			shenghjgh = tongbsj.get("SHENGHJGH");
			log.info("��ǰ�˻��˺�Ϊ��" + zhangh + ",��������Ϊ:" + caozlx);
			// �����˻���������Ϊ�������޸�
			if ("0".equals(caozlx) || "2".equals(caozlx)) {
				
				// ��ѯ���˺����˻�������ϸ��Ϣ
				log.info("��ѯ���˺�Ϊ" + zhangh + "���˻���ZHANGHB������ϸ��Ϣ");
				Map<String, String> zhanghsj = zhanghbDao
						.queryZhbByZhangh(zhangh);
				zhanghsj = zhanghbDao.attrConvert(zhanghsj);
				log
						.info("��ѯ���˺�Ϊ" + zhangh + "���˻���ZHANGHB������ϸ��Ϣ���Ϊ:"
								+ zhanghsj);
				if (zhanghsj != null&&zhanghsj.size()!=0) {  
					// log.info("���˺�Ϊ"+zhangh+"���˻�����״̬Ϊ����");
					// ��Ÿ��˻�3�ű���Ϣ��map
					Map<String, String> singleZhanghxx = new HashMap<String, String>();
					// ��ѯ��ʡ�л�����
					log.info("��ѯ���˺�Ϊ" + zhangh + "���˻��Ļ�����Ϣ");
					Map<String, String> organarchivesMap = zhanghbDao
							.queryOrganarchivesByJigh(zhanghsj.get("JIGH"));
					log.info("��ѯ���˺�Ϊ" + zhangh + "���˻��Ļ�����ϢΪ��"
							+ organarchivesMap);
					zhanghsj
							.put("SHENGHJGH", organarchivesMap.get("SHENGHJGH"));
					zhanghsj.put("CAOZLX", caozlx);
					// ���ϸ��˻����˻�����Ϣ�����ͬ���˻���
					log.info("���˺�Ϊ" + zhangh + "���˻�����ϢΪ" + zhanghsj);
					singleZhanghxx.put("ZHANGHB", JsonTool
							.toJsonForObject(zhanghsj));

					
					// �úϸ��˻���ӡ����������Ϣ
					List<Map<String, String>> tempyinjList = yinjbDao
							.queryYjbListByZhangh(zhangh);
//					log.info("���˺�Ϊ" + zhangh + "��ӡ������ϢΪ" + tempyinjList);
					log.info("��ѯ�˺�Ϊ"+zhangh+"��ӡ������Ϣ����");
					if (tempyinjList != null && tempyinjList.size() != 0) {
						// ���ϸ��˻���ӡ������Ϣ�����ͬ���˻���
						singleZhanghxx.put("YINJB", JsonTool
								.toJsonForArry(tempyinjList));
					}

					
					// �úϸ��˻���ӡ����ϱ�������Ϣ
					List<Map<String, String>> tempyinjzhList = yinjzhbDao
							.queryYjzhbListByZhangh(zhangh);
					log.info("���˺�Ϊ" + zhangh + "��ӡ�������ϢΪ" + tempyinjzhList);
					if (tempyinjzhList != null && tempyinjzhList.size() != 0) {
						// ���ϸ��˻���ӡ����ϱ���Ϣ�����ͬ���˻���
						singleZhanghxx.put("YINJZHB", JsonTool
								.toJsonForArry(tempyinjzhList));
					}
					log.info("���˺�Ϊ" + zhangh + "��������ϢΪ" + singleZhanghxx);
					sendMap.put(zhangh, JsonTool
							.toJsonForObject(singleZhanghxx));
					// }else{
					// log.info("���˺�Ϊ"+zhangh+"���˻���δ����Ϣ������������");
					// }
				}
				// �����˻���������Ϊɾ������ֻ��¼���˺źͲ���������Ϣ
			} else if ("1".equals(caozlx)) {
				log.info("��ǰ�˻��˺�Ϊ��" + zhangh + ",��������Ϊ:" + caozlx);
				Map<String, String> zhanghsj = new HashMap<String, String>();
				zhanghsj.put("ZHANGH", zhangh);
				//TODO
				String oldZhangh = zhanghbDao.getOldAccount(zhangh) ;
				if(null!=oldZhangh){
					zhanghsj.put("OLDZHANGH", oldZhangh);
				}
				zhanghsj.put("CAOZLX", caozlx);
				zhanghsj.put("SHENGHJGH", shenghjgh);
				Map<String, String> singleZhanghxx = new HashMap<String, String>();
				singleZhanghxx.put("ZHANGHB", JsonTool
						.toJsonForObject(zhanghsj));
				sendMap.put(zhangh, JsonTool.toJsonForObject(singleZhanghxx));
			}
		}

		// String sendMapStr = JsonTool.toJsonForObject(sendMap).toString();
		// log.info("�˴οͷ���ͬ������׼�����͵�ͬ���ַ���Ϊ��"+sendMapStr);
		// return sendMapStr;
		return sendMap;
	}

	// �Է�����Ϣ���в���
	public String returnTongbu(String returnStr, String tongbzh)
			throws DAOException {
		if (returnStr != null && !"".equals(returnStr)) {
//			Map<String, String> resultMap = (Map<String, String>) JsonTool
//					.toBean(returnStr, HashMap.class);
			JSONObject resultMap = JSONObject.fromObject(returnStr);
			if (resultMap != null && resultMap.containsKey("result")) {
				Zhanghtbb zhanghtbb = new Zhanghtbb();
				Date rightNow = Calendar.getInstance().getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(rightNow);
				zhanghtbb.setTongbsj(date);
				zhanghtbb.setZhangh(tongbzh);
				zhanghtbb.setResult("1");
				if ("fail".equals(resultMap.get("result"))) {
					if (resultMap.containsKey("exception")) {
//						String zhanghMapStr = resultMap.get("faillist");
//						Map<String, String> zhanghMap = (Map<String, String>) JsonTool
//								.toBean(zhanghMapStr, HashMap.class);
//						if (zhanghMap != null && !zhanghMap.isEmpty()) {
//							for (Map.Entry entry : zhanghMap.entrySet()) {
//								String zhangh = (String) entry.getKey();
								String exception = String.valueOf(resultMap.get("exception"));
								zhanghtbb.setException(exception);
								zhanghtbb.setResult("2");
//							}
//						}
					}
				}
			//	ZhanghtbbDaoImpl zhanghtbbDao = new ZhanghtbbDaoImpl(operation);
				zhanghtbbDao.updateZhanghtbbForNight(zhanghtbb);
				return String.valueOf(resultMap.get("result"));
			}
		}
		return "fail";
	}


	
	
	// ��¼Socketδ���ͳɹ�������Ϣ(����)
	public void saveSendExceptionForJigh(String exception, String jigh)
			{
		Zhanghtbb zhanghtbb = new Zhanghtbb();
		Date rightNow = Calendar.getInstance().getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(rightNow);
		zhanghtbb.setTongbsj(date);
		zhanghtbb.setResult("2");
		if(jigh!=null&&!"".equals(jigh)){
			zhanghtbb.setShenghjgh(jigh);
			zhanghtbb.setException(exception);
		//	ZhanghtbbDaoImpl zhanghtbbDao = new ZhanghtbbDaoImpl(operation);
			try {
				zhanghtbbDao.updateZhanghtbbForJighForNight(zhanghtbb);
			} catch (DAOException e) {
				e.printStackTrace();
				log.info("����SocketΪ��ͬ��ʧ�ܽ��ʱ����"+e.getMessage());
			}
		}
	}

	
	/**
	 * ��ȡ������Ҫ����ͬ���Ļ������˺Ŷ�Ӧ��ϵ
	 * @throws DAOException 
	 */
	public HashMap<String, List> queryAllJig(Map<String, String> sendMap) throws DAOException {

		// �����map
		HashMap<String, List> jigMap = new HashMap<String, List>();

		if (sendMap != null && sendMap.size() != 0) {
			for (Map.Entry entry : sendMap.entrySet()) {
				// ����˺�
				String zhangh = entry.getKey().toString();
				// ��ø��˻�������Ϣ�ַ���
				String zhanghxxStr = entry.getValue().toString();
				HashMap<String, String> zhanghxx = (HashMap<String, String>) JsonTool
						.toBean(zhanghxxStr, HashMap.class);
				if (zhanghxx.containsKey("ZHANGHB")
						&& !"[]".equals(zhanghxx.get("ZHANGHB"))) {
					// ���˺��˻�����Ϣ
					String zhbStr = (String) zhanghxx.get("ZHANGHB");
					HashMap<String, String> zhbMap = (HashMap<String, String>) JsonTool
							.toBean(zhbStr, HashMap.class);
					String jigh = zhbMap.get("SHENGHJGH");
					
					
					/***************��Ϊδ��������쳣��Ϣ***************/
					if("δ��".equals(zhbMap.get("ZHANGHSHZT"))||"δ��".equals(zhbMap.get("YINJSHZT"))||"δ��".equals("ZUHSHZT")){
						log.info("�˻�δ��,������ͬ����Ϣ");
						Zhanghtbb zhanghtbb = new Zhanghtbb();
						Date rightNow = Calendar.getInstance().getTime();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String date = format.format(rightNow);
						zhanghtbb.setTongbsj(date);
						zhanghtbb.setZhangh(zhangh);
						zhanghtbb.setException("�˻�����δ����Ϣ");
						zhanghtbb.setResult("2");
						zhanghtbbDao.updateZhanghtbbForNight(zhanghtbb);
					}else{
						if (jigMap.containsKey(jigh)) {
							jigMap.get(jigh).add(zhangh);
						} else {
							ArrayList<String> zhanghList = new ArrayList<String>();
							zhanghList.add(zhangh);
							jigMap.put(jigh, zhanghList);
						}
						log.info("��ͬ������"+jigh+"�����ͬ���˺�"+zhangh);
					}
					/****************************************************************************/
					
				}
			}
		}
		return jigMap;
	}

	/**
	 * ���ݻ����Ż�ȡ������Ϣ
	 * 
	 * @param jigh
	 * @return
	 * @throws DAOException
	 */
	public Map<String, String> queryJigForJigh(String jigh) throws DAOException {
		//ZhanghbDAOimpl zhanghbDao = new ZhanghbDAOimpl(operation);
		Map<String, String> jigMap = zhanghbDao.queryOrganarchivesByJigh(jigh);
		return jigMap;
	}
	
	
	
	/**
	 * ���ݷ��кŻ�ȡͬ����ַ
	 * 
	 * @param jigh
	 * @return
	 * @throws DAOException
	 */
	
	public String getSocketaddByJigh(String jigh) throws DAOException {
		//ZhanghbDAOimpl zhanghbDao = new ZhanghbDAOimpl(operation);
		String socketadd = zhanghbDao.getSocketaddByJigh(jigh);
		return socketadd;
	}

}
