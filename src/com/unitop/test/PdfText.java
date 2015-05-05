package com.unitop.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.util.ExportPdfUtil;

public class PdfText {
	public static void main(String[] args) throws Exception{
		Document document =new Document(PageSize.A3);
		PdfWriter.getInstance(document, new FileOutputStream(new File("D://myPdf.pdf")));
		Font font=new Font(ExportPdfUtil.getBasefont(),9);
		PdfPTable pdfPTable=new PdfPTable(3);
		List<String>colnames=new ArrayList<String>();
		List<String>cols=new ArrayList<String>();
		List<Zhanghb>list=new ArrayList<Zhanghb>();
		colnames.add("账户");colnames.add("户名");colnames.add("开户日期");
		cols.add("zhangh");cols.add("hum");cols.add("kaihrq");
		for (int i = 0; i < 4; i++) {
			Zhanghb zhanghb=new Zhanghb();
			zhanghb.setZhangh("2014061211111111"+i);
			zhanghb.setHum("张"+i);
			zhanghb.setKaihrq("2014-05-06");
			list.add(zhanghb);
		}
		ExportPdfUtil.setFirstLine(pdfPTable, colnames, font);
		ExportPdfUtil.setData(pdfPTable, list, font, Zhanghb.class, cols);
		document.open();
		document.add(pdfPTable);
		document.close();
	}
}
