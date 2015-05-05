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
	// ����XML

	public static UniDBI XMLToDBI(String xml) {
		UniDBI DBI = new UniDBI();
		Reader inputstream = new StringReader(xml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		DataSets dataSets =  new DataSets();
		try {
			doc = builder.build(inputstream);
			Element root = doc.getRootElement();
			// ��ȡ��������
			String funcID = root.getChild("funcID").getTextTrim();
			// ��ȡ���Ʋ���
			Element controlParam = root.getChild("controlParams");
			if (null != controlParam) {
				List<Element> controlParams = controlParam
						.getChildren("controlParam");
				Map<String, String> controlParamMap = new HashMap<String, String>();
				for (int i = 0; i < controlParams.size(); i++) {
					Element parameter = controlParams.get(i);
					// ��ȡ���Ʋ�����
					String name = parameter.getChild("name").getTextTrim();
					// ��ȡ���Ʋ���ֵ
					String value = doParseValue(name, parameter.getChild(
							"value").getTextTrim());
					controlParamMap.put(name, value);
				}
				dataSets.setControlParamMap(controlParamMap);
			}
			/* ��ȡѭ���ڵ㣬�Ƿ����ӽ��� */
			Element multi = root.getChild("multi");
			if (null != multi) {
				// ��ȡ�ӽ��׼���
				List<Element> multilList = multi.getChildren("parameters");
				// ѭ��ÿ���ӽ���
				for (int n = 0; n < multilList.size(); n++) {
					Element multiRoot = multilList.get(n);
					Element rowsRoot = multiRoot.getChild("rows");
					// ��ȡ�ӽ��׵�ID
					String id = multiRoot.getAttributeValue("id");
					// ��ȡ�ӽ��ײ�������
					List<Element> rowList = rowsRoot.getChildren("row");
					List<Map<String, String>> list = new ArrayList<Map<String, String>>();
					for (int k = 0; k < rowList.size(); k++) {
						Map<String, String> parameterMap = new HashMap<String, String>();
						Element rowRoot = rowList.get(k);
						List<Element> parameters = rowRoot
								.getChildren("parameter");
						for (int j = 0; j < parameters.size(); j++) {
							Element parameter = parameters.get(j);
							// ��ȡ������
							String name = parameter.getChild("name")
									.getTextTrim();
							// ��ȡ����ֵ
							String value = doParseValue(name, parameter
									.getChild("value").getTextTrim());
							parameterMap.put(name, value);
						}
						list.add(parameterMap);
					}
					// ��������б�
					CommonUtil.debug(id + "���ײ����б�");
					for (int i = 0; i < list.size(); i++) {
						Map<String, String> map = list.get(i);
						for (Object key : map.keySet()) {
							Object value = map.get(key);
							CommonUtil.debug(key + ", " + value);
						}
					}
					// ����ӽ��׵��ӽ��׼���
					dataSets.putParamMap(id, list);
				}
				DBI.setFuncID(funcID);
				DBI.setDataSets(dataSets);
				return DBI;
			} else {
				// ��ȡ��������
				List<Element> parameters = root.getChild("parameters")
						.getChildren("parameter");
				Map<String, String> parameterMap = new HashMap<String, String>();
				for (int j = 0; j < parameters.size(); j++) {
					Element parameter = parameters.get(j);
					// ��ȡ������
					String name = parameter.getChild("name").getTextTrim()
							.toLowerCase();
					// ��ȡ����ֵ
					String value = doParseValue(name, parameter.getChild(
							"value").getTextTrim());
					parameterMap.put(name, value);
				}
				/** // DataSets XML ���ݣ�����ʱ�����ݼ�
				Element dataSetsElement = root.getChild("datasets");
				if (dataSetsElement != null) {
					List<Element> datasetsList = dataSetsElement
							.getChildren("dataset");
					dataSets = new DataSets();
					dataSets = doXMLforDatasSets(datasetsList);
				}*/
				// ��������б�
				CommonUtil.debug(funcID + "���ײ����б�");
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

	// ���� DataSets XML ����
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

	// ���� DataSets XML ����
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