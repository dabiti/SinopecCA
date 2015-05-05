package com.unitop.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.unitop.config.PdfConfig;

public class ExportPdfUtil {
	private static  BaseFont basefont ;
	
	static {
		if(basefont==null){
			try {
				basefont=BaseFont.createFont("/simfang.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	public static BaseFont getBasefont() {
		return basefont;
	}
	
	private ExportPdfUtil() {
	}
	
	public static void setFirstLine(PdfPTable pdfPTable ,List<String> colnames,Font font ){
		PdfPCell pcell=new PdfPCell();
		pcell.setBackgroundColor(BaseColor.GRAY);
		for (String string : colnames) {
			pcell.setPhrase(new Paragraph(string, font));
			pdfPTable.addCell(pcell);
		}
	}
	/**
	 * ���ñ�ͷ
	 * @param document
	 * @param title
	 * @param font
	 * @throws DocumentException
	 */
	public static void setTableHead(Document document ,String title,Font font ) throws DocumentException{
		Paragraph p=new Paragraph();
		p.setFont(font);
		p.setAlignment(p.ALIGN_CENTER);
		p.add(title);
		document.add(p);
	//document.addHeader(title, title);
		//document.addHeader(arg0, arg1)
	}
	/**
	 * ���ñ���
	 * @param document
	 * @param title
	 * @param font
	 * @throws DocumentException
	 */
	public static void setTitle(Document document ,String title,Font font ) throws DocumentException{
		Paragraph p=new Paragraph();
		font.setStyle(font.BOLD);
		p.setFont(font);
		p.setAlignment(p.ALIGN_CENTER);
		p.add(title);
		document.add(p);
	//document.addHeader(title, title);
		//document.addHeader(arg0, arg1)
	}
	public static void setData(PdfPTable pdfPTable ,List list,Font font ,Class c,List<String>cols) throws Exception{
		
		PdfPCell pcell=new PdfPCell();
		pcell.setBackgroundColor(BaseColor.WHITE);
		Field field=null;
		Paragraph p =null;
		String val="";
		for (Object object : list) {
			pdfPTable.completeRow();
			for (String string : cols) {
				field=c.getDeclaredField(string);
				field.setAccessible(true);
				 val=(String)field.get(object);
				 p=new Paragraph(val,font);
				pcell.setPhrase(p);
				pdfPTable.addCell(pcell);
				}
			}
		
		}
	
	/**
	 * ��ȡÿ�б���
	 * @param className
	 * @return
	 */
	public static List<String> parseTitles(String className){
		String titlesStr=PdfConfig.getExportPdfMap().get(className+"Titles");
		return splitStr(titlesStr);
	}
	/**
	 * ��ȡÿ�ж�Ӧ��������
	 * @param className
	 * @return
	 */
	public static List<String> parseCols(String className){
		String titlesStr=PdfConfig.getExportPdfMap().get(className+"Cols");
		return splitStr(titlesStr);
	}
	/**
	 * ���ɱ���
	 * @param className
	 * @return
	 */
	public static String parseTitlename(String className){
		String titlesStr=PdfConfig.getExportPdfMap().get(className+"Titlename");
		return titlesStr;
	}
	// ���ӡ�������ַ���
	private static  List<String> splitStr(String string) {

		List<String> strList = new ArrayList<String>();
		if (string != null && string.trim().length() != 0) {
			String[] str = string.split(",", 0);
			for (int i = 0; i < str.length; i++) {
				strList.add(str[i]);
			}
		}
		return strList;
	}
	
	public static void setDataFromMap(PdfPTable pdfPTable, List<Map> list,
			Font font, List<String> cols) {
		PdfPCell pcell=new PdfPCell();
		pcell.setBackgroundColor(BaseColor.WHITE);
		int i=1;
		String val="";
	//	String string="";
		for (Map map : list) {
			pdfPTable.completeRow();
			for (String string : cols) {
				if(string.equals("rowid")){
					val=i+"";
				}else if(string.equals("fuz")||string.equals("youxj")||string.equals("zongs")||string.equals("zid")||string.equals("reng")){
					val=map.get(string).toString();
				}else if(string.equals("areacode")||string.equals("areacode2")||string.equals("areacode3")||string.equals("areacode4")){
					val=PhoneUtil.bulidAreaCode(map.get(string).toString());
				}else{
					val=(String)map.get(string);
				}
				pcell.setPhrase(new Paragraph(val,font));
				pdfPTable.addCell(pcell);
			}
			i++;
		}
	}
	/**
	 * ��Map�ж�ȡ���ݣ����ɱ��
	 * @param pdfPTable
	 * @param list ����
	 * @param font ����
	 * @param cols ÿ�е���������
	 * @param colsize ÿ����ռ�õĵ�Ԫ����
	 */
	public static void setDataFromMap(PdfPTable pdfPTable, List<Map> list,
			Font font, List<String> cols,List<Integer>colsize) {
		PdfPCell pcell=new PdfPCell();
		pcell.setBackgroundColor(BaseColor.WHITE);
		int i=1;
		String val="";
		int count=0;
	//	String string="";
		for (Map map : list) {
			pdfPTable.completeRow();
			for (String string : cols) {
				if(string.equals("rowid")){
					val=i+"";
				}else if(string.equals("fuz")||string.equals("youxj")||string.equals("zongs")||string.equals("zid")||string.equals("reng")){
					val=map.get(string).toString();
				}else if(string.equals("areacode")||string.equals("areacode2")||string.equals("areacode3")||string.equals("areacode4")){
					val=PhoneUtil.bulidAreaCode(map.get(string).toString());
				}else{
					val=(String)map.get(string);
				}
				pcell.setColspan(colsize.get(count));
				pcell.setPhrase(new Paragraph(val,font));
				pdfPTable.addCell(pcell);
				count++;
			}
			count=0;
			i++;
		}
	}
	/**
	 * ��ʵ�弯���ж�ȡ���ݣ����ɱ��
	 * @param pdfPTable
	 * @param list ����
	 * @param font ����
	 * @param cols ÿ�е���������
	 * @param colsize ÿ����ռ�õĵ�Ԫ����
	 */
	public static void setData(PdfPTable pdfPTable, List list,
			Font font, Class c, List<String> cols,
			List<Integer> colsize) throws Exception {
		
		PdfPCell pcell=new PdfPCell();
		pcell.setBackgroundColor(BaseColor.WHITE);
		Field field=null;
		Paragraph p =null;
		int count=0;
		String val="";
		for (Object object : list) {
			pdfPTable.completeRow();
			for (String string : cols) {
				pcell.setColspan(colsize.get(count));
				field=c.getDeclaredField(string);
				field.setAccessible(true);
				 val=(String)field.get(object);
				 p=new Paragraph(val,font);
				pcell.setPhrase(p);
				pdfPTable.addCell(pcell);
				count++;
				}
			count=0;
			}
		
	}
	/**
	 * ��ʵ�弯���ж�ȡ���ݣ����ɱ��
	 * @param pdfPTable
	 * @param list ����
	 * @param font ����
	 * @param cols ÿ�е���������
	 * @param colsize ÿ����ռ�õĵ�Ԫ����
	 * @param num ��ʼ���
	 */
	public static void setData(PdfPTable pdfPTable, List list,
			Font font, Class c, List<String> cols,
			List<Integer> colsize,int num) throws Exception {
		
		PdfPCell pcell=new PdfPCell();
		pcell.setBackgroundColor(BaseColor.WHITE);
		Field field=null;
		Paragraph p =null;
		int count=0;
		int colnum=0;
		String val="";
		for (Object object : list) {
			pdfPTable.completeRow();
			for (String string : cols) {
				pcell.setColspan(colsize.get(count));
				if("index".equals(string)){
					val=String.valueOf(num+colnum);
				}else{
					field=c.getDeclaredField(string);
					field.setAccessible(true);
					val=(String)field.get(object);
				}
				 p=new Paragraph(val,font);
				pcell.setPhrase(p);
				pdfPTable.addCell(pcell);
				count++;
				}
			colnum++;
			count=0;
			}
		
	}
	/**
	 * ��ȡÿ�е�Ԫ����
	 * @param className
	 * @return
	 */
	public static List<Integer> parseColsize(String className) {
		String colsizeStr=PdfConfig.getExportPdfMap().get(className+"Colsize");
		List<String> list=splitStr(colsizeStr);
		List<Integer> colsize=new ArrayList<Integer>();
		for (String string : list) {
			colsize.add(Integer.parseInt(string));
		}
		
		return colsize;
	}

	public static int parseConum(String className) {
		String s=PdfConfig.getExportPdfMap().get(className+"Colnum");
		return Integer.parseInt(s);
	}
	/**
	 * ��������
	 * @param pdfPTable
	 * @param colnames
	 * @param font
	 * @param colsize
	 */
	public static void setFirstLine(PdfPTable pdfPTable, List<String> colnames,
			Font font, List<Integer> colsize) {
		int count=0;
		PdfPCell pcell=new PdfPCell();
		pcell.setBackgroundColor(new BaseColor(223, 228, 232));
		for (String string : colnames) {
			pcell.setColspan(colsize.get(count));
			pcell.setPhrase(new Paragraph(string, font));
			pdfPTable.addCell(pcell);
			count++;
		}
	}
	/**
	 * ���ĵ���β�����ı�
	 * @param document �ĵ�
	 * @param string ������ı�
	 * @param font ����
	 */
	public static void setTailmsg(Document document, String string, Font font)  throws DocumentException{
		try {
			Paragraph p=new Paragraph();
			p.setFont(font);
			p.setAlignment(p.ALIGN_CENTER);
			p.add(string);
			document.add(p);
		} catch (DocumentException e) {
			 throw new DocumentException("�����β�ı�ʧ��");
		}
		
	}

	
}
