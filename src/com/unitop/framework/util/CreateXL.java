
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
 *    Excel   文件要存放的位置，假定在D盘目录下
 */  
 public   static   String   outputFile="D:/gongye.xls";  
 
 public static List<Clerk> list_in = new ArrayList<Clerk>();
 
 public static HSSFWorkbook createXls(String name,List<Clerk> list,int m){
	 
	 try{
		 HSSFWorkbook   workbook   =   new   HSSFWorkbook(); 
		 
		  /** 
		   *  在Excel工作簿中建一工作表，其名为缺省值  
		   *  如要新建一名为"柜员信息"的工作表，其语句为：  
		   *  HSSFSheet   sheet   =   workbook.createSheet("柜员信息");  
		  */ 
		 HSSFSheet   sheet   =   workbook.createSheet(name);  
		 
		  /**
		   * 循环写入单元格
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
		   *    新建一输出文件流  
		   */
		  FileOutputStream   fOut   =   new   FileOutputStream(outputFile);  
		  
		  /**
		   *    把相应的Excel   工作簿存盘  
		   */
		  workbook.write(fOut);  
		  fOut.flush();  
		  
		  /**
		   *    操作结束，关闭文件  
		   */
		  fOut.close();  
		  return workbook;
 		}catch(Exception   e)   {  
 			e.printStackTrace();
	  //System.out.println("已运行   xlCreate()   :   "   +   e   );  
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
