package com.unitop.util;

import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfHeaderFooter extends PdfPageEventHelper {
	private BaseFont baseFont;
	private PdfTemplate total;
	private String className;
	
	public PdfHeaderFooter() {
		// TODO Auto-generated constructor stub
	}
	public PdfHeaderFooter(BaseFont font){
		this.baseFont=font;
	}
	public PdfHeaderFooter(BaseFont font,String className){
		this.baseFont=font;
		this.className=className;
	}
	
	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total=writer.getDirectContent().createTemplate(30, 16);
	}
/*	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		// TODO Auto-generated method stub
		//super.onEndPage(writer, document);
		PdfPCell cell1 = new PdfPCell(new Paragraph("Qjk"));
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell1.setBorder(PdfPCell.TOP);
		cell1.setBorderColor(BaseColor.BLACK);
		cell1.setBorderWidth(0.6f);
		PdfPCell cell2 = new PdfPCell(new Paragraph("Lgj"));
		cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell2.setBorder(PdfPCell.TOP);
		cell2.setBorderColor(BaseColor.BLACK);
		cell2.setBorderWidth(0.6f);
		PdfPTable footer =new PdfPTable(2);
		footer.addCell(cell1);
		footer.addCell(cell2);
		footer.setTotalWidth(document.getPageSize().getWidth()-document.leftMargin()-document.rightMargin());
		footer.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
	}*/
	
	/**
	 * 页脚
	 */
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		PdfPTable table =new PdfPTable(6);
		try{
		Rectangle rect=writer.getBoxSize("art");
		//ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(String.format("第%d页", writer.getPageNumber()),new Font(baseFont, 7)), (rect.getRight()+rect.getLeft())/2, rect.getBottom()-6, 0);
		table.setWidths(new int[]{17,21,6,6,21,25});
		
		table.setTotalWidth(527);
		table.setLockedWidth(true);
		//table.getDefaultCell().setFixedHeight(10);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.addCell("");
		table.addCell("");
		PdfPCell c=new PdfPCell();
		Paragraph p1 =new Paragraph(String.format("第%d页", writer.getPageNumber()),new Font(baseFont, 7));
		p1.setAlignment(p1.ALIGN_RIGHT);
		p1.setIndentationRight(-4);
		c.addElement(p1);
		c.setBorder(c.NO_BORDER);
		
		table.addCell(c);
		PdfPCell cell=new PdfPCell();
		cell.setBorder(c.NO_BORDER);
		cell.addElement(Image.getInstance(total));
		table.addCell(cell);
		table.addCell("");
		table.addCell("");
		table.writeSelectedRows(0, -1, rect.getBottom(),rect.getBottom()-15 , writer.getDirectContent());
		}catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页标题头
	 */
	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		if(className!=null){
			List<String>colnames=ExportPdfUtil.parseTitles(className);//列名
			List<Integer>colsize=ExportPdfUtil.parseColsize(className);//每个字段分别占用的列数
			int colnum=ExportPdfUtil.parseConum(className);//总列数
			int currnum=writer.getPageNumber();
			if(currnum!=1){
				PdfPTable pdfPTable=new PdfPTable(colnum);
				ExportPdfUtil.setFirstLine(pdfPTable, colnames, new Font(baseFont, 9),colsize);
				try {
					document.add(pdfPTable);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		Rectangle rect=writer.getBoxSize("art");
	//	ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase("/共"+String.valueOf(writer.getPageNumber()-1)+"页",new Font(baseFont, 7)),(rect.getRight()+rect.getLeft())/2, rect.getBottom()-6,0);
		ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(",共"+String.valueOf(writer.getPageNumber()-1)+"页",new Font(baseFont, 7)),-1, 5.2f,0);
	}	
}
