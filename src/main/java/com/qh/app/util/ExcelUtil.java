package com.qh.app.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
/**
 * 
 * @author lw
 * excel 操作工具类
 * @param <T>
 */
public class ExcelUtil<T> {

	public HSSFCellStyle getCellStyle(HSSFWorkbook workbook, boolean isHeader) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setLocked(true);
		if (isHeader) {
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}
		return style;
	}

	public void generateHeader(HSSFWorkbook workbook, HSSFSheet sheet,
			String[] headerColumns) {
		HSSFCellStyle style = getCellStyle(workbook, true);
		Row row = sheet.createRow(0);
		row.setHeightInPoints(30);
		for (int i = 0; i < headerColumns.length; i++) {
			Cell cell = row.createCell(i);
			String[] column = headerColumns[i].split("_#_");
			sheet.setColumnWidth(i, Integer.valueOf(column[1]));
			cell.setCellValue(column[0]);
			cell.setCellStyle(style);
		}
	}

	public HSSFSheet creatAuditSheet(HSSFWorkbook workbook, String sheetName,
			String[] headerColumns) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		HSSFSheet sheet = workbook.createSheet(sheetName);
		generateHeader(workbook, sheet, headerColumns);
		return sheet;
	}

	public void addDataToSheet(HSSFWorkbook workbook, HSSFSheet sheet,
			List<T> dataset, String[] fieldColumns) {
		int rowNum = 0;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		HSSFCellStyle style = getCellStyle(workbook, false);
		for (T t : dataset) {
			rowNum++;
			Row row = sheet.createRow(rowNum);
			row.setHeightInPoints(25);
			for (int i = 0; i < fieldColumns.length; i++) {
				String fieldName = fieldColumns[i];

				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class<? extends Object> clazz = t.getClass();
					Method getMethod;
					getMethod = clazz.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					String cellValue = "";
					if (value instanceof Date) {
						Date date = (Date) value;
						cellValue = sd.format(date);
					} else {
						cellValue = null != value ? value.toString() : "";
					}
					Cell cell = row.createCell(i);
					cell.setCellStyle(style);
					cell.setCellValue(cellValue);

				} catch (Exception e) {

				}
			}
		}
	}
}
