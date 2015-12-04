package com.ob.dailyReport.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class StyleManager {

	public static HSSFCellStyle createDefaultStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
		style.setFont(font);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		return style;
	}

	public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = createDefaultStyle(workbook);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		return style;
	}

	public static HSSFCellStyle getContentEmployeeStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = createDefaultStyle(workbook);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		return style;
	}

	public static HSSFCellStyle getContentCenterStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = createDefaultStyle(workbook);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return style;
	}

	public static HSSFCellStyle getContentLeftStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = createDefaultStyle(workbook);
		return style;
	}

	public static void resetColor(HSSFWorkbook workbook) {
		HSSFPalette palette = workbook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) 141, (byte) 179, (byte) 226);
		palette.setColorAtIndex(HSSFColor.LIGHT_BLUE.index, (byte) 198, (byte) 217, (byte) 241);
	}

}
