package com.sinodata.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 * @author Daniel
 * @2013-2-26
 * @TODO
 */
public class JUtils {

	private JUtils() {}

	/**
	 * 简单json转map
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> JsonToMap(JSONObject json) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator keys = json.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 复杂json转map
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> JsonToMapPlus(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator keys = json.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			if (value.startsWith("{") && value.endsWith("}")) {
				map.put(key, JsonToMapPlus(JSONObject.fromObject(value)));
			} else {
				map.put(key, value);
			}
		}
		return map;
	}

	/**
	 * @Describe 
	 *           由于.net对空JSONArray解析不了,引擎返回是对结果为空时多加了一个空对象,因此不能用JSONArray的size来判断返回结果是否为空
	 *           ,本方法用于判断返回的objectdata对象中是否含有有效数据
	 * @param jsonObj
	 * @return
	 */
	public static boolean isObjDataContainValidData(JSONObject jsonObj) {
		JSONArray jsonArray = jsonObj.getJSONArray("objectdata");
		int objDataCount = jsonArray.size();
		if (1 == objDataCount) {
			JSONObject jsonObjData = JSONObject.fromObject(jsonArray.get(0));
			if (jsonObjData.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} else if (objDataCount > 1) {
			return true;
		} else {
			return false;
		}
	}

	public static List<JSONObject> objectdataToList(JSONObject jsonObj) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONArray jsonArray = jsonObj.getJSONArray("objectdata");
		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject tempJsonObj = JSONObject.fromObject(jsonArray.get(i));
			list.add(tempJsonObj);
		}
		return list;
	}
	
	
	/**
	 * 用于生成随机ID
	 * @return
	 */
	public static String getRandomID() {
		UUID uuid = UUID.randomUUID();
		String time = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String tempStr = encrypt(time, "esvs").substring(12, 22);
		String strTemp = encrypt(uuid.toString(), "esvs").substring(12, 22);
		return tempStr + strTemp;
	}
	
	/**
	 * MD5&Base64
	 * @param text
	 * @param updateStr
	 * @return
	 */
	public static String encrypt(String text, String updateStr) {
		byte[] textbyte;
		byte[] updatebyte = updateStr.getBytes(); 
		String cipherTextStr = ""; 
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			textbyte = text.getBytes();
			md.update(updatebyte);
			cipherTextStr = new String(new BASE64Encoder().encode(md.digest(textbyte)));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cipherTextStr;
	}
	
	
	/**
	 * 字符串数组按长度排列,长的在前
	 * @param arrStr
	 * @return
	 */
	public static String[] sortByStrLength(String[] arrStr) {
		 String temp;
		  for (int i = 0; i < arrStr.length; i++) {
		   for (int j = arrStr.length - 1; j > i; j--) {
		    if (arrStr[i].length() < arrStr[j].length()) {
		     temp = arrStr[i];
		     arrStr[i] = arrStr[j];
		     arrStr[j] = temp;
		    }
		   }
		  }
		return  arrStr;
	}
	/**
	 * 返回组合包含的印鉴类型
	 * 
	 */
//	public static List<String> getzhYinjlx(String zuh) throws Exception{
//		String sql = "select yinjlx from yinjb where yinjbh=?";
//		String common_zuh = zuh.replace("&", "|");
//		String[] yinjbh = common_zuh.split("|");
//		List<String> yinjlx = new ArrayList<String>();
//		
//		Connection conn = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		try{
//			conn = DataSourceUtils.getConnection(DataSourceFactory.getDataSourceByPool());
//			pstm = conn.prepareStatement(sql);
//			for(int i = 0;i<yinjbh.length;i++){
//				pstm.setString(1, yinjbh[i]);
//				rs = pstm.executeQuery();
//				while(rs.next()){
//					yinjlx.add(rs.getString(1));
//				}
//			}			
//		}catch(Exception e){
//			e.printStackTrace();
//			throw e;
//		}finally{
//			if(rs!=null){
//				rs.close();
//			}
//			if(pstm!=null){
//				pstm.close();
//			}if(conn!=null){
//				conn.close();
//			}
//		}
//		return yinjlx;
//		
//		
//	}
}
	

