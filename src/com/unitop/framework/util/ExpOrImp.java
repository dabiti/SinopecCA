/**
 *<dl>
 *<dt><b>���ݵ��뵼��������:</b></dt>
 *<dd>��Excel���е�����д�뵽���ݿ��С�</dd>
 *<dd>��Oracle�е����ݵ�����Excel���С�</dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *��Update History��
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
	 * Excel �ļ�Ҫ��ŵ�λ�ã��ٶ���D��Ŀ¼��
	 */
	public static String outputFile = "D:/info.xls";

	/**
	 * <dl>
	 * <dt><b>importExcel:</b></dt>
	 * <dd>��Excel�е����ݶ�ȡ��������д��Map�����С�</dd>
	 * </dl>
	 */
	public static List importExcel(HSSFSheet sheet, int n) {
		List list = new ArrayList();
		/**
		 * �� '��' ѭ������Excel����������ݡ�
		 */
		int rowCount = sheet.getLastRowNum();
		try{
			for (int i = 1; i < rowCount + 1; i++) {
				HSSFRow row = sheet.getRow(i);
				Map map = new HashMap();
				/**
				 * ��Excel��������е�ÿһ��Ԫ�������д�뵽Map�С�
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
			// ���ز������͵�ֵ
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// ������ֵ���͵�ֵ
			return String.valueOf(Integer.valueOf((int) (hssfCell.getNumericCellValue())));
		} else {
			// �����ַ������͵�ֵ
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * <dl>
	 * <dt><b>exportExcel:</b></dt>
	 * <dd>�����ݿ��е����ݵ�����Excel���С�</dd>
	 * <dd>����List�д�ŵ���Map(��N�У�ֵ)��</dd>
	 * </dl>
	 */
	public static HSSFWorkbook exportExcel(String name, List list, int n) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();

			/**
			 * ��Excel�������н�һ����������Ϊȱʡֵ ��Ҫ�½�һ��Ϊ"��Ա��Ϣ"�Ĺ����������Ϊ�� HSSFSheet sheet =
			 * workbook.createSheet("��Ա��Ϣ");
			 */
			HSSFSheet sheet = workbook.createSheet(name);

			// ѭ��д��ÿ��
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map = (Map) list.get(i);
				HSSFRow row = sheet.createRow((short) i);

				// ѭ��д��ÿ����Ԫ��
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
