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
		
		// 传输的map
		Map<String, String> sendMap = new HashMap<String, String>();

		
		// 查询出同步表中所有信息
		log.info("开始查询同步表中所有记录。");
		List<Map<String, String>> tongbbList = zhanghtbbDao.queryTongbbListForNight();
		log.info("查询结果List为：" + tongbbList);
		String zhangh = null;
		String caozlx = null;
		String shenghjgh = null;
		// 循环迭代同步表中每条记录
		log.info("开始迭代同步表中所有信息：");
		for (Map<String, String> tongbsj : tongbbList) {
			// 账号
			zhangh = tongbsj.get("ZHANGH");
			// 操作类型
			caozlx = tongbsj.get("CAOZLX");
			// shenghjgh
			shenghjgh = tongbsj.get("SHENGHJGH");
			log.info("当前账户账号为：" + zhangh + ",操作类型为:" + caozlx);
			// 若该账户操作类型为新增或修改
			if ("0".equals(caozlx) || "2".equals(caozlx)) {
				
				// 查询该账号在账户表中详细信息
				log.info("查询该账号为" + zhangh + "的账户在ZHANGHB表中详细信息");
				Map<String, String> zhanghsj = zhanghbDao
						.queryZhbByZhangh(zhangh);
				zhanghsj = zhanghbDao.attrConvert(zhanghsj);
				log
						.info("查询该账号为" + zhangh + "的账户在ZHANGHB表中详细信息结果为:"
								+ zhanghsj);
				if (zhanghsj != null&&zhanghsj.size()!=0) {  
					// log.info("该账号为"+zhangh+"的账户所有状态为已审");
					// 存放该账户3张表信息的map
					Map<String, String> singleZhanghxx = new HashMap<String, String>();
					// 查询其省行机构号
					log.info("查询该账号为" + zhangh + "的账户的机构信息");
					Map<String, String> organarchivesMap = zhanghbDao
							.queryOrganarchivesByJigh(zhanghsj.get("JIGH"));
					log.info("查询该账号为" + zhangh + "的账户的机构信息为："
							+ organarchivesMap);
					zhanghsj
							.put("SHENGHJGH", organarchivesMap.get("SHENGHJGH"));
					zhanghsj.put("CAOZLX", caozlx);
					// 将合格账户的账户表信息放入该同步账户中
					log.info("该账号为" + zhangh + "的账户表信息为" + zhanghsj);
					singleZhanghxx.put("ZHANGHB", JsonTool
							.toJsonForObject(zhanghsj));

					
					// 该合格账户的印鉴表完整信息
					List<Map<String, String>> tempyinjList = yinjbDao
							.queryYjbListByZhangh(zhangh);
//					log.info("该账号为" + zhangh + "的印鉴表信息为" + tempyinjList);
					log.info("查询账号为"+zhangh+"的印鉴表信息结束");
					if (tempyinjList != null && tempyinjList.size() != 0) {
						// 将合格账户的印鉴表信息放入该同步账户中
						singleZhanghxx.put("YINJB", JsonTool
								.toJsonForArry(tempyinjList));
					}

					
					// 该合格账户的印鉴组合表完整信息
					List<Map<String, String>> tempyinjzhList = yinjzhbDao
							.queryYjzhbListByZhangh(zhangh);
					log.info("该账号为" + zhangh + "的印鉴组合信息为" + tempyinjzhList);
					if (tempyinjzhList != null && tempyinjzhList.size() != 0) {
						// 将合格账户的印鉴组合表信息放入该同步账户中
						singleZhanghxx.put("YINJZHB", JsonTool
								.toJsonForArry(tempyinjzhList));
					}
					log.info("该账号为" + zhangh + "的所有信息为" + singleZhanghxx);
					sendMap.put(zhangh, JsonTool
							.toJsonForObject(singleZhanghxx));
					// }else{
					// log.info("该账号为"+zhangh+"的账户有未审信息，所以跳过。");
					// }
				}
				// 若该账户操作类型为删除，则只记录其账号和操作类型信息
			} else if ("1".equals(caozlx)) {
				log.info("当前账户账号为：" + zhangh + ",操作类型为:" + caozlx);
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
		// log.info("此次客服端同步程序准备传送的同步字符串为："+sendMapStr);
		// return sendMapStr;
		return sendMap;
	}

	// 对返回信息进行操作
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


	
	
	// 记录Socket未发送成功错误信息(机构)
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
				log.info("保存Socket为空同步失败结果时出错："+e.getMessage());
			}
		}
	}

	
	/**
	 * 获取所有需要进行同步的机构与账号对应关系
	 * @throws DAOException 
	 */
	public HashMap<String, List> queryAllJig(Map<String, String> sendMap) throws DAOException {

		// 传输的map
		HashMap<String, List> jigMap = new HashMap<String, List>();

		if (sendMap != null && sendMap.size() != 0) {
			for (Map.Entry entry : sendMap.entrySet()) {
				// 获得账号
				String zhangh = entry.getKey().toString();
				// 获得该账户所有信息字符串
				String zhanghxxStr = entry.getValue().toString();
				HashMap<String, String> zhanghxx = (HashMap<String, String>) JsonTool
						.toBean(zhanghxxStr, HashMap.class);
				if (zhanghxx.containsKey("ZHANGHB")
						&& !"[]".equals(zhanghxx.get("ZHANGHB"))) {
					// 该账号账户表信息
					String zhbStr = (String) zhanghxx.get("ZHANGHB");
					HashMap<String, String> zhbMap = (HashMap<String, String>) JsonTool
							.toBean(zhbStr, HashMap.class);
					String jigh = zhbMap.get("SHENGHJGH");
					
					
					/***************若为未审，则更新异常信息***************/
					if("未审".equals(zhbMap.get("ZHANGHSHZT"))||"未审".equals(zhbMap.get("YINJSHZT"))||"未审".equals("ZUHSHZT")){
						log.info("账户未审,将更新同步信息");
						Zhanghtbb zhanghtbb = new Zhanghtbb();
						Date rightNow = Calendar.getInstance().getTime();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String date = format.format(rightNow);
						zhanghtbb.setTongbsj(date);
						zhanghtbb.setZhangh(zhangh);
						zhanghtbb.setException("账户存在未审信息");
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
						log.info("在同步机构"+jigh+"中添加同步账号"+zhangh);
					}
					/****************************************************************************/
					
				}
			}
		}
		return jigMap;
	}

	/**
	 * 根据机构号获取机构信息
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
	 * 根据分行号获取同步地址
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
