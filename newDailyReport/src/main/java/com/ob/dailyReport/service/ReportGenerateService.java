package com.ob.dailyReport.service;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.ProjectReport;
import com.ob.dailyReport.model.TaskStatus;
import com.ob.dailyReport.util.Constant;
import com.ob.dailyReport.util.DateUtil;
import com.ob.dailyReport.util.StyleManager;
import com.ob.dailyReport.util.TitleWriter;

public class ReportGenerateService {

	private static Logger log = Logger.getLogger(ReportGenerateService.class);

	private static int currentRowIndex = 0;

	private static HSSFCellStyle defaultStyle;
	private static HSSFCellStyle employeeStyle;
	private static HSSFCellStyle contentLeftStyle;
	private static HSSFCellStyle contentCenterStyle;

	public static File generateReport(String filePath, String teamName, Date date) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("report");
		HSSFSheet summarySheet = wb.createSheet("summary");
		sheet.setDefaultRowHeight((short) 500);
		setSheetStyle(sheet);
		// get data from database, build data to format model
		// List<ProjectReport> projectList = fakeModel();

		writeTeamSummary(teamName, summarySheet, date);

		List<ProjectReport> projectList = TeamReportHistoryService.getTeamReport(teamName, date);
		// convert mode data to excel data
		TitleWriter.write(sheet);
		convertModel2Excel(sheet, projectList);

//		configerSize(sheet);
		changeColumnWid(sheet);
		String reportName = teamName + "_" + DateUtil.FormatDate2String(date) + ".xls";
		File file = new File(filePath + "\\" + reportName);
		FileOutputStream fout = new FileOutputStream(file);
		wb.write(fout);
		fout.close();
		log.info("generate Excel sucessfully!");
		return file;
	}

	private static void setSheetStyle(HSSFSheet sheet) {
		StyleManager.resetColor(sheet.getWorkbook());
		defaultStyle = StyleManager.createDefaultStyle(sheet.getWorkbook());

		employeeStyle = StyleManager.getContentEmployeeStyle(sheet.getWorkbook());
		contentLeftStyle = StyleManager.getContentLeftStyle(sheet.getWorkbook());
		contentCenterStyle = StyleManager.getContentCenterStyle(sheet.getWorkbook());
		sheet.setDefaultColumnStyle(0, employeeStyle);
		sheet.setDefaultColumnStyle(1, employeeStyle);
		sheet.setDefaultColumnStyle(2, employeeStyle);

		sheet.setDefaultColumnStyle(3, contentLeftStyle);
		sheet.setDefaultColumnStyle(4, contentCenterStyle);
		sheet.setDefaultColumnStyle(5, contentCenterStyle);
		sheet.setDefaultColumnStyle(6, contentLeftStyle);
	}

	private static void changeColumnWid(HSSFSheet sheet) {
		sheet.setColumnWidth(0, 22 * 256);
		sheet.setColumnWidth(1, 12 * 256);
		sheet.setColumnWidth(2, 6 * 256);
		sheet.setColumnWidth(3, 70 * 256);
		sheet.setColumnWidth(4, 7 * 256);
		sheet.setColumnWidth(5, 7 * 256);
		sheet.setColumnWidth(6, 50 * 256);
	}

//	private static void configerSize(HSSFSheet sheet) {
//		for (int i = 0; i < 7; i++) {
//			sheet.autoSizeColumn(i, true);
//		}
//	}

	private static void convertModel2Excel(HSSFSheet sheet, List<ProjectReport> reportList) {
		currentRowIndex = 2;
		for (int i = 0; i < reportList.size(); i++) {
			ProjectReport project = reportList.get(i);
			convertProject2Excel(sheet, project);
			currentRowIndex++;
		}
	}

	private static void convertProject2Excel(HSSFSheet sheet, ProjectReport project) {
		HSSFRow project_row = sheet.createRow(currentRowIndex);
		// create cell for project name
		HSSFCell project_cell = project_row.createCell(0);
		// project_cell.setCellValue(new
		// HSSFRichTextString(project.getProjectName()));
		configProjectText(project_cell, project.getProjectName(), project.getRfa(), project.getStatus());
		// project_cell.setCellStyle(defaultStyle);

		for (int i = 0; i < project.getEmployeeRList().size(); i++) {
			HSSFRow employee_row = project_row;
			if (i != 0) {
				currentRowIndex++;
				employee_row = sheet.createRow(currentRowIndex);
			}
			EmployeeReport employee = project.getEmployeeRList().get(i);

			// create cell for employee name
			HSSFCell employee_cell = employee_row.createCell(1);
			// employee_cell.setCellValue(new
			// HSSFRichTextString(employee.getName()));
			configWrapText(employee_cell, employee.getName());
			// employee_cell.setCellStyle(defaultStyle);

			// create cell for role
			HSSFCell role_cell = employee_row.createCell(2);
			role_cell.setCellValue(employee.getRole());
			// role_cell.setCellStyle(defaultStyle);

			// create cell for plans
			HSSFCell plans_cell = employee_row.createCell(6);
			// HSSFCellStyle cellStyle = plans_cell.getCellStyle();
			// cellStyle.setWrapText(true);
			// plans_cell.setCellStyle(cellStyle);
			// plans_cell.setCellValue(new
			// HSSFRichTextString(processSpecialChar(employee.getPlans())));
			configWrapText(plans_cell, processSpecialChar(employee.getPlans()));

			generateStatusRows(employee, employee_row);

			// project
			int employeeStartRow = employee_row.getRowNum();
			int employeeEndRow = currentRowIndex;
			// merge cells for employee
			sheet.addMergedRegion(new CellRangeAddress(employeeStartRow, employeeEndRow, 1, 1));
			// merge cells for role
			sheet.addMergedRegion(new CellRangeAddress(employeeStartRow, employeeEndRow, 2, 2));
			// merge cells for plans
			sheet.addMergedRegion(new CellRangeAddress(employeeStartRow, employeeEndRow, 6, 6));

		}

		// project
		int projectStartRow = project_row.getRowNum();
		int projectEndRow = currentRowIndex;
		sheet.addMergedRegion(new CellRangeAddress(projectStartRow, projectEndRow, 0, 0));

	}

	private static void configProjectText(HSSFCell cell, String value, String rfa, String status) {
		HSSFFont rfa_font = cell.getSheet().getWorkbook().createFont();
		rfa_font.setFontHeightInPoints((short) 10);
		rfa_font.setFontName("Calibri");
		rfa_font.setColor(HSSFColor.GREY_50_PERCENT.index);
		rfa_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFFont status_font = cell.getSheet().getWorkbook().createFont();
		status_font.setFontHeightInPoints((short) 11);
		status_font.setFontName("Calibri");

		HSSFRichTextString ts = null;
		if (rfa == null || rfa.equals("")) {
			rfa = "";
		}else{
			rfa = "\n" + "(" + rfa + ")" ;
		}
		if (status == null || status.equals("")) {
			status = "";
		}else{
			status = "\n" + "[" + status + "]";
		}
		ts = new HSSFRichTextString(value + rfa + status);
		ts.applyFont(value.length(), (value + rfa).length(), rfa_font);
		ts.applyFont((value + rfa).length(), ts.length(), status_font);
		cell.setCellValue(ts);
	}

	private static void configWrapText(HSSFCell cell, String value) {
		HSSFCellStyle cellStyle = cell.getCellStyle();
		cellStyle.setWrapText(true);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(value));
	}

	private static String processSpecialChar(String value) {
		return value.replaceAll("<br>", "\n");
	}

	private static void generateStatusRows(EmployeeReport employee, HSSFRow employee_row) {
		for (int j = 0; j < employee.getCurrentStatus().size(); j++) {
			TaskStatus task = employee.getCurrentStatus().get(j);
			HSSFRow task_row = employee_row;
			if (j != 0) {
				currentRowIndex++;
				task_row = employee_row.getSheet().createRow(currentRowIndex);
			}

			HSSFCell desc_cell = task_row.createCell(3);
			// HSSFCellStyle cellStyle = desc_cell.getCellStyle();
			// cellStyle.setWrapText(true);
			// desc_cell.setCellStyle(cellStyle);
			// desc_cell.setCellValue(new
			// HSSFRichTextString(processSpecialChar(task.getDescription())));
			configWrapText(desc_cell, processSpecialChar(task.getDescription()));

			HSSFCell hours_cell = task_row.createCell(4);
			hours_cell.setCellValue(task.getSpentHours());

			HSSFCell eta_cell = task_row.createCell(5);
			eta_cell.setCellValue(task.getEta());
		}
	}

	/**
	 * 
	 * @param team
	 * @param sheet
	 * @param date
	 */
	private static void writeTeamSummary(String team, HSSFSheet sheet, Date date) {
		try {
			Map<String, List<String>> summaryMap = TeamSummaryService.getEmployeeWithTaskList(team, date);
			List<String> employeeWithTasksList = summaryMap.get(Constant.hasTask);
			List<String> employeeWithoutTasksList = summaryMap.get(Constant.noTask);
			HSSFCellStyle style = StyleManager.getTitleStyle(sheet.getWorkbook());
			sheet.setColumnWidth(0, 22 * 256);
			sheet.setColumnWidth(1, 22 * 256);
			HSSFRow titleRow = sheet.createRow(0);
			String title[] = { "employee with task:", "employee without task:" };
			for (int i = 0; i < title.length; i++) {
				HSSFCell cell = titleRow.createCell(i);
				cell.setCellValue(title[i]);
				cell.setCellStyle(style);
			}

			for (int i = 0; i < employeeWithTasksList.size(); i++) {
				String employeeName = employeeWithTasksList.get(i);
				HSSFRow row = sheet.createRow(i + 1);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(employeeName);
			}

			for (int i = 0; i < employeeWithoutTasksList.size(); i++) {
				String employeeName = employeeWithoutTasksList.get(i);
				if (employeeName != null) {
					HSSFRow row = sheet.getRow(i + 1);
					if (row == null) {
						row = sheet.createRow(i + 1);
					}
					HSSFCell cell = row.createCell(1);
					cell.setCellValue(employeeName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		generateReport("/", "team1", new Date());
	}

}
