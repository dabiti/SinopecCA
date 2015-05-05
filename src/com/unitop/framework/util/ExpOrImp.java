/**
 *<dl>
 *<dt><b>数据导入导出工具类:</b></dt>
 *<dd>将Excel表中的数据写入到数据库中。</dd>
 *<dd>将Oracle中的数据导出到Excel表中。</dd>
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuShan
 */
public class ExpOrImp {
	/**
	 * Excel 文件要存放的位置，假定在D盘目录下
	 */
	public static String outputFile = "D:/info.xls";

	/**
	 * <dl>
	 * <dt><b>importExcel:</b></dt>
	 * <dd>将Excel中的数据读取出来，并写入Map对象中。</dd>
	 * </dl>
	 */
	public static List importExcel(HSSFSheet sheet, int n) {
		List list = new ArrayList();
		/**
		 * 按 '行' 循环遍历Excel工作表的内容。
		 */
		int rowCount = sheet.getLastRowNum();
		try{
			for (int i = 1; i < rowCount + 1; i++) {
				HSSFRow row = sheet.getRow(i);
				Map map = new HashMap();
				/**
				 * 将Excel工作表此行的每一单元格的内容写入到Map中。
				 */
				for (int j = 0; j < n; j++) {
					HSSFCell cell = row.getCell((short) j);
					if(cell==null){
						map.put(j, "");
					}else{
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						map.put(j, getValue(cell));
					}
				}
				list.add(map);
			}
		}catch (Exception e) {
			//e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("static-access")
	public static String getValue(HSSFCell hssfCell)
	{
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(Integer.valueOf((int) (hssfCell.getNumericCellValue())));
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * <dl>
	 * <dt><b>exportExcel:</b></dt>
	 * <dd>将数据库中的数据导出到Excel表中。</dd>
	 * <dd>参数List中存放的是Map(第N列，值)；</dd>
	 * </dl>
	 */
	public static HSSFWorkbook exportExcel(String name, List list, int n) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();

			/**
			 * 在Excel工作簿中建一工作表，其名为缺省值 如要新建一名为"柜员信息"的工作表，其语句为： HSSFSheet sheet =
			 * workbook.createSheet("柜员信息");
			 */
			HSSFSheet sheet = workbook.createSheet(name);

			// 循环写入每行
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map = (Map) list.get(i);
				HSSFRow row = sheet.createRow((short) i);

				// 循环写入每个单元格
				for (int j = 0; j < n + 1; j++) {
					HSSFCell cell = row.createCell((short) j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue((String) map.get(j));
				}
			}
			return workbook;
		} catch (Exception e) {
			return null;
		}
	}
}
