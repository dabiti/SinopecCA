
package com.unitop.framework.util;

/**
 * @author LiuShan
 *
 */
import   org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import   org.apache.poi.hssf.usermodel.HSSFSheet;  
import   org.apache.poi.hssf.usermodel.HSSFRow;  
import   org.apache.poi.hssf.usermodel.HSSFCell;  

import com.unitop.sysmgr.bo.Clerk;

import   java.io.FileOutputStream;  
import java.util.ArrayList;
import java.util.List;

public   class   CreateXL   {  
/**
 *    Excel   �ļ�Ҫ��ŵ�λ�ã��ٶ���D��Ŀ¼��
 */  
 public   static   String   outputFile="D:/gongye.xls";  
 
 public static List<Clerk> list_in = new ArrayList<Clerk>();
 
 public static HSSFWorkbook createXls(String name,List<Clerk> list,int m){
	 
	 try{
		 HSSFWorkbook   workbook   =   new   HSSFWorkbook(); 
		 
		  /** 
		   *  ��Excel�������н�һ����������Ϊȱʡֵ  
		   *  ��Ҫ�½�һ��Ϊ"��Ա��Ϣ"�Ĺ����������Ϊ��  
		   *  HSSFSheet   sheet   =   workbook.createSheet("��Ա��Ϣ");  
		  */ 
		 HSSFSheet   sheet   =   workbook.createSheet(name);  
		 
		  /**
		   * ѭ��д�뵥Ԫ��
		   */
		  for(int i=0;i<list.size();i++){
			  HSSFRow   row   =   sheet.createRow((short)i);  
			  Clerk clerk = new Clerk();
			  clerk = (Clerk)list.get(i);
			  
			  HSSFCell cell = row.createCell((short)0);  
			  cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
			  cell.setCellValue(clerk.getCode()); 
			  
			  HSSFCell cell1 = row.createCell((short)1);  
			  cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
			  cell1.setCellValue(clerk.getName()); 
			  
			  HSSFCell cell2 = row.createCell((short)2);  
			  cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
			  cell2.setCellValue(clerk.getOrgcode()); 
		  }
		  
		  /**
		   *    �½�һ����ļ���  
		   */
		  FileOutputStream   fOut   =   new   FileOutputStream(outputFile);  
		  
		  /**
		   *    ����Ӧ��Excel   ����������  
		   */
		  workbook.write(fOut);  
		  fOut.flush();  
		  
		  /**
		   *    �����������ر��ļ�  
		   */
		  fOut.close();  
		  return workbook;
 		}catch(Exception   e)   {  
 			e.printStackTrace();
	  //System.out.println("������   xlCreate()   :   "   +   e   );  
	 }  
	 
	 return null;
 }
 
 
	 public static void main(String argv[])  {
		 List list = new ArrayList();
		 Clerk clerk = new Clerk();
		 clerk.setCode("000230000000");
		 list.add(clerk);
		 CreateXL.createXls("guiyuan",list,1);
		 
	}  
}
