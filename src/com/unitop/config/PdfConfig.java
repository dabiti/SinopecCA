package com.unitop.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PdfConfig {
	private static Map<String,String> exportPdfMap;
	private static final String filepath="exportPdf.xml";
	
	private PdfConfig() {
		// TODO Auto-generated constructor stub
	}
	
	public static Map<String, String> getExportPdfMap() {
		if(exportPdfMap==null){
			initData();
		}
		
		return exportPdfMap;
	}

	private static void initData() {
		exportPdfMap=new HashMap<String, String>();
		SAXReader read=new SAXReader();
		read.setEncoding("gb2312");
		Document document= null;
		try {
			document=read.read(PdfConfig.class.getClassLoader().getResourceAsStream(filepath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if(document==null){
			return;
		}
		Element root=document.getRootElement();
		for (Iterator ite =root.elementIterator();ite.hasNext();) {
			Element element=(Element)ite.next();
			String name="";
			for (Iterator i=element.elementIterator();i.hasNext();) {
				Element ele=(Element)i.next();
				if(ele.getName().equals("name")){
					name=ele.getTextTrim();
				}
				if(ele.getName().equals("titles")){
					exportPdfMap.put(name+"Titles", ele.getTextTrim());
				}
				if(ele.getName().equals("culs")){
					exportPdfMap.put(name+"Cols", ele.getTextTrim());
				}
				if(ele.getName().equals("culnum")){
					exportPdfMap.put(name+"Colnum", ele.getTextTrim());
				}
				if(ele.getName().equals("colsize")){
					exportPdfMap.put(name+"Colsize", ele.getTextTrim());
				}
				if(ele.getName().equals("titleName")){
					exportPdfMap.put(name+"Titlename", ele.getTextTrim());
				}
			}
		}
	}
}
