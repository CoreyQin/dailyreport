package com.ob.dailyReport.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class TitleWriter {

	public static void write(HSSFSheet sheet) throws Exception {

		HSSFCellStyle style = StyleManager.getTitleStyle(sheet.getWorkbook());
		HSSFRow row = sheet.createRow(0);
		String title[] = { "Project", "Name", "Role", "Status for Today" };
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

		HSSFCell cell_plan = row.createCell(6);
		cell_plan.setCellValue("Plans for Tomorrow");
		cell_plan.setCellStyle(style);

		HSSFRow row2 = sheet.createRow(1);
		String subTitles[] = { "Description", "Spent Hours", "ETA(Date)" };
		for (int i = 0; i < 3; i++) {
			HSSFCell cell = row2.createCell(i + 3);
			cell.setCellValue(subTitles[i]);
			cell.setCellStyle(style);
		}
		
		
		

		// project
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		// name
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
		// role
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
		// status for today
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));
		// plans for tomorrow
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
//		
//		row.setRowStyle(style);
//		row2.setRowStyle(style);
	}

	public static void main(String[] args) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		write(sheet);
		try {
			FileOutputStream fout = new FileOutputStream("t.xls");
			wb.write(fout);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Excel文件生成成功...");
	}

}
