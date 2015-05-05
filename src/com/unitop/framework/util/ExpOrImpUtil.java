/**
 *<dl>
 *<dt><b>数据导入导出工具类:将Oracle中的数据导出到Excel表中。
 *将Excel表中的数据写入到数据库中。</b></dt>
 *<dd></dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *【Update History】
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2011-11-8       IBM ETP      LiuShan		    create
 */
package com.unitop.framework.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.unitop.sysmgr.bo.Clerk;
import com.unitop.sysmgr.bo.Org;

import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author LiuShan
 *
 */
public class ExpOrImpUtil {
	
	/**
	 *    Excel   文件要存放的位置，假定在D盘目录下
	 */  
	 public   static   String   outputFile="D:/info.xls";  
	 public   static   String   inputFile="D:/org.xls";  

	 /**
	  * 
	  * <dl>
	  * <dt><b>readExcel:将Excel中的数据读取出来，并写入柜员对象或机构对象中。</b></dt>
	  * <dd></dd>
	  * </dl>
	  */
	 public static List importExcel(String tablename,String file){
		List list = null;
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			
			/**
			 * 如果导入柜员信息，访问clerkImport方法，
			 * 导入机构信息，访问orgImport方法
			 */
			if("clerktable".equals(tablename)){
				list = clerkImport(sheet);
			}else{
				list = orgImport(sheet);
			}	
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(input!=null){try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}		
		}
		 
		return list;
	}
	 
 	/**
 	 * 
 	 * <dl>
 	 * <dt><b>clerkImport:Excel中的柜员信息解析。</b></dt>
 	 * <dd></dd>
 	 * </dl>
 	 */
 	private static List clerkImport(HSSFSheet sheet){
		List<Clerk> list = new ArrayList<Clerk>();
		
		/**
		 * 按 '行' 循环遍历Excel工作表的内容。
		 */
		int rowCount = sheet.getLastRowNum();
		for(int i=1;i<rowCount+1;i++){
			HSSFRow row = sheet.getRow(i);
			Clerk clerk = new Clerk();
			 
			/**
			 * 将Excel工作表此行的每一列的内容写入到clerk对象中。
			 * 
			 */
			HSSFCell cell0 = row.getCell((short)0);
			clerk.setCode(cell0.getStringCellValue());
			
			HSSFCell cell1 = row.getCell((short)1);
			clerk.setName(cell1.getStringCellValue());
			
			HSSFCell cell2 = row.getCell((short)2);
			clerk.setPassword(cell2.getStringCellValue());
			
			HSSFCell cell3 = row.getCell((short)3);
			clerk.setPostCode(cell3.getStringCellValue());
			
			HSSFCell cell4 = row.getCell((short)4);
			clerk.setUpdateDate(cell4.getStringCellValue());
			
			HSSFCell cell5 = row.getCell((short)5);
			clerk.setOrgcode(cell5.getStringCellValue());
			
			HSSFCell cell6 = row.getCell((short)6);
			clerk.setLogDate(cell6.getStringCellValue());
			
			HSSFCell cell7 = row.getCell((short)7);
			clerk.setCreator(cell7.getStringCellValue());
			
			HSSFCell cell8 = row.getCell((short)8);
			clerk.setShOrgCode(cell8.getStringCellValue());
			
			HSSFCell cell9 = row.getCell((short)9);
			clerk.setWdFlag(cell9.getStringCellValue());
			
			list.add(clerk);
		}
		
 		return list;
 	}
	 	
 	/**
 	 * 
 	 * <dl>
 	 * <dt><b>orgImport:Excel中的机构信息解析。</b></dt>
 	 * <dd></dd>
 	 * </dl>
 	 */
 	private static List orgImport(HSSFSheet sheet){
		List<Org> list = new ArrayList<Org>();
		
		/**
		 * 按 '行' 循环遍历Excel工作表的内容。
		 */
		int rowCount = sheet.getLastRowNum();
		for(int i=1;i<rowCount+1;i++){
			HSSFRow row = sheet.getRow(i);
			Org org = new Org();
			
			/**
			 * 将Excel工作表此行的每一列的内容写入到clerk对象中。
			 * 
			 */
			HSSFCell cell0 = row.getCell((short)0);
			org.setCode(cell0.getStringCellValue());
			
			HSSFCell cell1 = row.getCell((short)1);
			org.setName(cell1.getStringCellValue());
			
			HSSFCell cell2 = row.getCell((short)2);
			org.setParentCode(cell2.getStringCellValue());
			
			HSSFCell cell3 = row.getCell((short)3);
			org.setPaymentCode(cell3.getStringCellValue());
			
			HSSFCell cell4 = row.getCell((short)4);
			org.setShOrgCode(cell4.getStringCellValue());
			
			HSSFCell cell5 = row.getCell((short)5);
			org.setTctd(cell5.getStringCellValue());
			
			HSSFCell cell6 = row.getCell((short)6);
			org.setWdflag(cell6.getStringCellValue());
			
			list.add(org);
		}
		
 		return list;
 	}
	 	
 	/**
 	 * 
 	 * <dl>
 	 * <dt><b>exportExcel:将数据库中的数据导出到Excel表中。</b></dt>
 	 * <dd></dd>
 	 * </dl>
 	 */
 	public static HSSFWorkbook exportExcel(String name,List list,String className,String file){
 		 
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
 				if("com.unitop.sysmgr.bo.Clerk".equals(className)){
 					  
 					  clerkExport(sheet,list);
 				  }else if("com.unitop.sysmgr.bo.Org".equals(className)){
 					  orgExport(sheet,list);
 				  }else{
 					  HSSFWorkbook   wb   =  new HSSFWorkbook(new FileInputStream(file));
 					  HSSFSheet   sheet1   =   wb.getSheetAt(0);
 					  messageExport(sheet1,list);
 					  FileOutputStream   fOut   =   new   FileOutputStream(file); 
 					  wb.write(fOut);  
 					  fOut.flush(); 
 					  fOut.close();
 				  }
 			  
 			  /**
 			   *    新建一输出文件流  
 			   */
// 			  FileOutputStream   fOut   =   new   FileOutputStream(file);  
 			  
 			  /**
 			   *    把相应的Excel   工作簿存盘  
 			   */
// 			  workbook.write(fOut);  
// 			  fOut.flush();  
 			  
 			  /**
 			   *    操作结束，关闭文件  
 			   */
// 			  fOut.close();  
 			  return workbook;
 	 		}catch(Exception   e)   {  
 	 			e.printStackTrace();
 		 }  
 		 
 		 return null;
 	 }
	 	 
 	/**
 	 * 
 	 * <dl>
 	 * <dt><b>clerkExport:将柜员信息导出到Excel表中。</b></dt>
 	 * <dd></dd>
 	 * </dl> 
 	 */
 	@SuppressWarnings("unchecked")
	private static void clerkExport(HSSFSheet sheet,List list){
 		
 		/**
 		 *  设置 Excel表的表头。
 		 */
 		HSSFRow   row_title   =   sheet.createRow((short)0);  
		
		HSSFCell cell0_title = row_title.createCell((short)0); 
		cell0_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0_title.setCellValue("柜员代码"); 
		
		HSSFCell cell1_title = row_title.createCell((short)1); 
		cell1_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell1_title.setCellValue("柜员名称"); 
		
		HSSFCell cell2_title = row_title.createCell((short)2); 
		cell2_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell2_title.setCellValue("密码");
		
		HSSFCell cell3_title = row_title.createCell((short)3); 
		cell3_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell3_title.setCellValue("岗位代码"); 
		
		HSSFCell cell4_title = row_title.createCell((short)4); 
		cell4_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell4_title.setCellValue("密码最近更改时间"); 
		
		HSSFCell cell5_title = row_title.createCell((short)5); 
		cell5_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell5_title.setCellValue("机构号"); 
		
		HSSFCell cell6_title = row_title.createCell((short)6); 
		cell6_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell6_title.setCellValue("最近登录时间"); 
		
		HSSFCell cell7_title = row_title.createCell((short)7); 
		cell7_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell7_title.setCellValue("创建柜员"); 
		
		HSSFCell cell8_title = row_title.createCell((short)8); 
		cell8_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell8_title.setCellValue("省行机构"); 
		
		HSSFCell cell9_title = row_title.createCell((short)9); 
		cell9_title.setEncoding(HSSFCell.ENCODING_UTF_16); 
		cell9_title.setCellValue("网点标识"); 
		
		/**
		 *  循环将柜员信息写入Excel表中。
		 */
 		for(int i=1;i<list.size()+1;i++){
			Clerk clerk = new Clerk();
			clerk = (Clerk)list.get(i-1);
 			HSSFRow   row   =   sheet.createRow((short)i);  
		
 			HSSFCell cell0 = row.createCell((short)0); 
			cell0.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell0.setCellValue(clerk.getCode()); 
			
 			HSSFCell cell1 = row.createCell((short)1); 
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellValue(clerk.getName()); 
			
 			HSSFCell cell2 = row.createCell((short)2); 
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell2.setCellValue(clerk.getPassword()); 
			
 			HSSFCell cell3 = row.createCell((short)3); 
			cell3.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell3.setCellValue(clerk.getPostCode()); 
			
			HSSFCell cell4 = row.createCell((short)4); 
			cell4.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell4.setCellValue(clerk.getUpdateDate()); 
			
 			HSSFCell cell5 = row.createCell((short)5); 
			cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell5.setCellValue(clerk.getOrgcode()); 

 			HSSFCell cell6 = row.createCell((short)6); 
			cell6.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell6.setCellValue(clerk.getLogDate()); 
			
			HSSFCell cell7 = row.createCell((short)7); 
			cell7.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell7.setCellValue(clerk.getCreator()); 
			
 			HSSFCell cell8 = row.createCell((short)8); 
			cell8.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell8.setCellValue(clerk.getShOrgCode()); 
			
 			HSSFCell cell9 = row.createCell((short)9); 
			cell9.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell9.setCellValue(clerk.getWdFlag()); 

 		}
 		  
 	}
 	
 	/**
 	 * 
 	 * <dl>
 	 * <dt><b>orgExport:将机构表中的信息导出到Excel中。</b></dt>
 	 * <dd></dd>
 	 * </dl>
 	 */
 	private static void orgExport(HSSFSheet sheet,List list){
 		/**
 		 *  在Excel表中创建机构表的表头信息。
 		 */
 		HSSFRow   row_title   =   sheet.createRow((short)0);  
		
		HSSFCell cell0_title = row_title.createCell((short)0); 
		cell0_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0_title.setCellValue("机构号"); 
		
		HSSFCell cell1_title = row_title.createCell((short)1); 
		cell1_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell1_title.setCellValue("机构名称"); 
		
		HSSFCell cell2_title = row_title.createCell((short)2); 
		cell2_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell2_title.setCellValue("上级机构");
		
		HSSFCell cell3_title = row_title.createCell((short)3); 
		cell3_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell3_title.setCellValue("人行行号"); 
		
		HSSFCell cell4_title = row_title.createCell((short)4); 
		cell4_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell4_title.setCellValue("省行号"); 
		
		HSSFCell cell5_title = row_title.createCell((short)5); 
		cell5_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell5_title.setCellValue("通存通兑"); 
		
		HSSFCell cell6_title = row_title.createCell((short)6); 
		cell6_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell6_title.setCellValue("网点标识");
 		
		/**
		 *  将机构信息循环写入Excel表格中。
		 */
 		for(int i=1;i<list.size()+1;i++){
			Org org = new Org();
			org = (Org)list.get(i-1);
 			HSSFRow   row   =   sheet.createRow((short)i);  
		
 			HSSFCell cell0 = row.createCell((short)0); 
			cell0.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell0.setCellValue(org.getCode()); 
			
 			HSSFCell cell1 = row.createCell((short)1); 
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellValue(org.getName()); 
			
 			HSSFCell cell2 = row.createCell((short)2); 
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell2.setCellValue(org.getParentCode());
			
 			HSSFCell cell3 = row.createCell((short)3); 
			cell3.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell3.setCellValue(org.getPaymentCode()); 
			
 			HSSFCell cell4 = row.createCell((short)4); 
			cell4.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell4.setCellValue(org.getShOrgCode()); 
			
 			HSSFCell cell5 = row.createCell((short)5); 
			cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell5.setCellValue(org.getTctd()); 
			
 			HSSFCell cell6 = row.createCell((short)6); 
			cell6.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell6.setCellValue(org.getWdflag());
			
 		}
 	}
 
 	private static void messageExport(HSSFSheet sheet,List list){
 		
 		/**
 		 *  设置 Excel表的表头。
 		 */
 		HSSFRow   row_title   =   sheet.getRow(0);
		
		HSSFCell cell0_title = row_title.createCell((short)10); 
		cell0_title.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0_title.setCellValue("未导入列表"); 
		
		
		/**
		 *  循环将柜员信息写入Excel表中。
		 */
 		for(int i=1;i<list.size()+1;i++){

 			HSSFRow   row   = sheet.getRow(i);
 			
 			HSSFCell cell0 = row.createCell((short)10); 
			cell0.setCellType(HSSFCell.CELL_TYPE_STRING);  
			cell0.setCellValue((String)list.get(i-1));
 		}
 		  
 	}
	 
}
