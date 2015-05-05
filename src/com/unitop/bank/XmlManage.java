package com.unitop.bank;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.unitop.bean.UniDBI;
import com.unitop.bean.DataSet;
import com.unitop.bean.DataSets;
import com.unitop.bean.FieldName;

public class XmlManage {
	// 处理XML

	public static UniDBI XMLToDBI(String xml) {
		UniDBI DBI = new UniDBI();
		Reader inputstream = new StringReader(xml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		DataSets dataSets =  new DataSets();
		try {
			doc = builder.build(inputstream);
			Element root = doc.getRootElement();
			// 获取交易名称
			String funcID = root.getChild("funcID").getTextTrim();
			// 获取控制参数
			Element controlParam = root.getChild("controlParams");
			if (null != controlParam) {
				List<Element> controlParams = controlParam
						.getChildren("controlParam");
				Map<String, String> controlParamMap = new HashMap<String, String>();
				for (int i = 0; i < controlParams.size(); i++) {
					Element parameter = controlParams.get(i);
					// 获取控制参数名
					String name = parameter.getChild("name").getTextTrim();
					// 获取控制参数值
					String value = doParseValue(name, parameter.getChild(
							"value").getTextTrim());
					controlParamMap.put(name, value);
				}
				dataSets.setControlParamMap(controlParamMap);
			}
			/* 获取循环节点，是否有子交易 */
			Element multi = root.getChild("multi");
			if (null != multi) {
				// 获取子交易集合
				List<Element> multilList = multi.getChildren("parameters");
				// 循环每个子交易
				for (int n = 0; n < multilList.size(); n++) {
					Element multiRoot = multilList.get(n);
					Element rowsRoot = multiRoot.getChild("rows");
					// 获取子交易的ID
					String id = multiRoot.getAttributeValue("id");
					// 获取子交易参数集合
					List<Element> rowList = rowsRoot.getChildren("row");
					List<Map<String, String>> list = new ArrayList<Map<String, String>>();
					for (int k = 0; k < rowList.size(); k++) {
						Map<String, String> parameterMap = new HashMap<String, String>();
						Element rowRoot = rowList.get(k);
						List<Element> parameters = rowRoot
								.getChildren("parameter");
						for (int j = 0; j < parameters.size(); j++) {
							Element parameter = parameters.get(j);
							// 获取参数名
							String name = parameter.getChild("name")
									.getTextTrim();
							// 获取参数值
							String value = doParseValue(name, parameter
									.getChild("value").getTextTrim());
							parameterMap.put(name, value);
						}
						list.add(parameterMap);
					}
					// 输出参数列表
					CommonUtil.debug(id + "交易参数列表：");
					for (int i = 0; i < list.size(); i++) {
						Map<String, String> map = list.get(i);
						for (Object key : map.keySet()) {
							Object value = map.get(key);
							CommonUtil.debug(key + ", " + value);
						}
					}
					// 添加子交易到子交易集合
					dataSets.putParamMap(id, list);
				}
				DBI.setFuncID(funcID);
				DBI.setDataSets(dataSets);
				return DBI;
			} else {
				// 获取参数集合
				List<Element> parameters = root.getChild("parameters")
						.getChildren("parameter");
				Map<String, String> parameterMap = new HashMap<String, String>();
				for (int j = 0; j < parameters.size(); j++) {
					Element parameter = parameters.get(j);
					// 获取参数名
					String name = parameter.getChild("name").getTextTrim()
							.toLowerCase();
					// 获取参数值
					String value = doParseValue(name, parameter.getChild(
							"value").getTextTrim());
					parameterMap.put(name, value);
				}
				/** // DataSets XML 数据，插入时的数据集
				Element dataSetsElement = root.getChild("datasets");
				if (dataSetsElement != null) {
					List<Element> datasetsList = dataSetsElement
							.getChildren("dataset");
					dataSets = new DataSets();
					dataSets = doXMLforDatasSets(datasetsList);
				}*/
				// 输出参数列表
				CommonUtil.debug(funcID + "交易参数列表：");
				for (Object key : parameterMap.keySet()) {
					Object value = parameterMap.get(key);
					CommonUtil.debug(key + ", " + value);
				}
				dataSets = null;
				DBI.setFuncID(funcID);
				DBI.setParams(parameterMap);
				DBI.setDataSets(dataSets);
				return DBI;
			}
		} catch (JDOMException e) {
			CommonUtil.error("XmlManage XMLToDBI exception", e);
			return null;
		} catch (IOException e) {
			CommonUtil.error("XmlManage XMLToDBI exception", e);
			return null;
		}
	}

	/**
	 * @author zhaolin
	 * @param name
	 * @param value
	 * @return
	 */
	public static String doParseValue(String name, String value) {
		if ("date8".equals(name.toLowerCase())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date(System.currentTimeMillis());
			value = sdf.format(date);
		} else if ("date10".equals(name.toLowerCase())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(System.currentTimeMillis());
			value = sdf.format(date);
		} else if ("time8".equals(name.toLowerCase())) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			value = sdf.format(date);
		} else if ("time12".equals(name.toLowerCase())) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
			Date date = new Date(System.currentTimeMillis());
			value = sdf.format(date);
		}
		return value;
	}

	// 处理 DataSets XML 数据
	public static DataSets doXMLforDatasSets(List datasetsList) {
		DataSets dataSets = new DataSets();
		try {
			for (int i = 0; i < datasetsList.size(); i++) {
				Element datasetRoot = (Element) datasetsList.get(i);
				List FieldNameList = datasetRoot.getChild("FieldNames")
						.getChildren("FieldName");
				List rowsList = datasetRoot.getChild("rows").getChildren("row");
				DataSet dataSet = new DataSet();
				String name = datasetRoot.getAttributeValue("name");
				dataSet.setName(name);
				for (int m = 0; m < FieldNameList.size(); m++) {
					Element fieldNameRoot = (Element) FieldNameList.get(m);
					FieldName fildName = new FieldName();
					fildName.setName(fieldNameRoot.getAttributeValue("Name"));
					fildName.setLength(fieldNameRoot
							.getAttributeValue("Length"));
					fildName.setType(fieldNameRoot.getAttributeValue("Type"));
					dataSet.putFieldName(fildName);
				}
				for (int n = 0; n < rowsList.size(); n++) {
					Element rowRoot = (Element) rowsList.get(n);
					Map rowMap = new HashMap();
					Iterator it = dataSet.getFieldNamesMap().entrySet()
							.iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						String rowName = (String) entry.getKey();
						rowMap.put(rowName, rowRoot.getAttributeValue(rowName));
					}
					dataSet.putRowsMap(rowMap);
				}
				dataSets.putRowSet(dataSet);
			}
		} catch (Exception e) {
			CommonUtil.error("XmlManage doXMLforDatasSets exception", e);
			return null;
		}
		return dataSets;
	}

	// 处理 DataSets XML 数据
	public static DataSets doXMLforRowSets(List datasetsList) {
		DataSets dataSets = new DataSets();
		try {
			for (int i = 0; i < datasetsList.size(); i++) {
				Element datasetRoot = (Element) datasetsList.get(i);
				List FieldNameList = datasetRoot.getChild("FieldNames")
						.getChildren("FieldName");
				List rowsList = datasetRoot.getChild("rows").getChildren("row");
				DataSet dataSet = new DataSet();
				String name = datasetRoot.getAttributeValue("name");
				dataSet.setName(name);
				for (int m = 0; m < FieldNameList.size(); m++) {
					Element fieldNameRoot = (Element) FieldNameList.get(m);
					FieldName fildName = new FieldName();
					fildName.setName(fieldNameRoot.getAttributeValue("Name"));
					fildName.setLength(fieldNameRoot
							.getAttributeValue("Length"));
					fildName.setType(fieldNameRoot.getAttributeValue("Type"));
					dataSet.putFieldName(fildName);
				}
				for (int n = 0; n < rowsList.size(); n++) {
					Element rowRoot = (Element) rowsList.get(n);
					Map rowMap = new HashMap();
					Iterator it = dataSet.getFieldNamesMap().entrySet()
							.iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						String rowName = (String) entry.getKey();
						rowMap.put(rowName, rowRoot.getAttributeValue(rowName));
					}
					dataSet.putRowsMap(rowMap);
				}
				dataSets.putRowSet(dataSet);
			}
		} catch (Exception e) {
			CommonUtil.error("XmlManage doXMLforDatasSets exception", e);
			return null;
		}
		return dataSets;
	}
}