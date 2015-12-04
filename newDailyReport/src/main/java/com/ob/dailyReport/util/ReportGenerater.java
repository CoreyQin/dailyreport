package com.ob.dailyReport.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.ob.dailyReport.handler.TeamReportHandler;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.ProjectReport;
import com.ob.dailyReport.model.TaskStatus;

public class ReportGenerater {

	private static int currentRowIndex = 0;

	private static HSSFCellStyle defaultStyle;
	private static HSSFCellStyle employeeStyle;
	private static HSSFCellStyle contentLeftStyle;
	private static HSSFCellStyle contentCenterStyle;

	public static File generateReport(String filePath, String teamName, Date date) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setDefaultRowHeight((short)600);
		setSheetStyle(sheet);
		// get data from database, build data to format model
		// List<ProjectReport> projectList = fakeModel();

		List<ProjectReport> projectList = TeamReportHandler.getTeamReport(teamName, date);
		// convert mode data to excel data
		TitleWriter.write(sheet);
		convertModel2Excel(sheet, projectList);

		configerSize(sheet);
		changeColumnWid(sheet);
		String reportName = teamName + "_" + DateUtil.FormatDate2String(date) + ".xls";
		File file = new File(filePath + "\\" + reportName);
		FileOutputStream fout = new FileOutputStream(file);
		wb.write(fout);
		fout.close();
		System.out.println("generate Excel sucessfully!");
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
	
	
	private static void changeColumnWid(HSSFSheet sheet){
		sheet.setColumnWidth(0, 22*256);
		sheet.setColumnWidth(1, 12*256);
		sheet.setColumnWidth(2, 6*256);
		sheet.setColumnWidth(3, 70*256);
		sheet.setColumnWidth(4, 7*256);
		sheet.setColumnWidth(5, 7*256);
		sheet.setColumnWidth(6, 50*256);
	}

	private static void configerSize(HSSFSheet sheet) {
		for (int i = 0; i < 7; i++) {
			sheet.autoSizeColumn(i, true);
		}
	}

	public static void convertModel2Excel(HSSFSheet sheet, List<ProjectReport> reportList) {
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
//		project_cell.setCellValue(new HSSFRichTextString(project.getProjectName()));
		configWrapText(project_cell,project.getProjectName());
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
//			employee_cell.setCellValue(new HSSFRichTextString(employee.getName()));
			configWrapText(employee_cell,employee.getName());
			// employee_cell.setCellStyle(defaultStyle);

			// create cell for role
			HSSFCell role_cell = employee_row.createCell(2);
			role_cell.setCellValue(employee.getRole());
			// role_cell.setCellStyle(defaultStyle);

			// create cell for plans
			HSSFCell plans_cell = employee_row.createCell(6);
//			HSSFCellStyle cellStyle = plans_cell.getCellStyle();
//			cellStyle.setWrapText(true);
//			plans_cell.setCellStyle(cellStyle);
//			plans_cell.setCellValue(new HSSFRichTextString(processSpecialChar(employee.getPlans())));
			configWrapText(plans_cell,processSpecialChar(employee.getPlans()));

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
	
	
	private static void configWrapText(HSSFCell cell, String value){
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
//			HSSFCellStyle cellStyle = desc_cell.getCellStyle();
//			cellStyle.setWrapText(true);
//			desc_cell.setCellStyle(cellStyle);
//			desc_cell.setCellValue(new HSSFRichTextString(processSpecialChar(task.getDescription())));
			configWrapText(desc_cell,processSpecialChar(task.getDescription()));

			HSSFCell hours_cell = task_row.createCell(4);
			hours_cell.setCellValue(task.getSpentHours());

			HSSFCell eta_cell = task_row.createCell(5);
			eta_cell.setCellValue(task.getEta());
		}

	}

	// public static List<ProjectReport> fakeModel() {
	//
	// List<ProjectReport> projectList = new ArrayList<ProjectReport>();
	//
	// ProjectReport project1 = new ProjectReport("Ford Motor – Hyperfind
	// Transfer(RFA-31911)");
	// ProjectReport project2 = new ProjectReport("project2");
	//
	// EmployeeReport emp1_report = new EmployeeReport();
	// emp1_report.setName("Byron Liu");
	// emp1_report.setReportDate(new Date());
	// emp1_report.setRole("Dev");
	// emp1_report.addStatus(new TaskStatus("Support QA on testing and issue
	// fixing.", 1f, "Done"));
	// emp1_report.addStatus(new TaskStatus("Support team member for some
	// issue.", 3f, "Done"));
	// emp1_report.setPlans("tomorrow");
	// project1.addEmployeeReport(emp1_report);
	// project2.addEmployeeReport(emp1_report);
	//
	// EmployeeReport emp2_report = new EmployeeReport();
	// emp2_report.setName("Rebecca Cheng");
	// emp2_report.setReportDate(new Date());
	// emp2_report.setRole("QA");
	// emp2_report.addStatus(
	// new TaskStatus("Install SP#52 on custom DB environment
	// (ceng13233-bf6/fordqa2)", 5f, "Done"));
	// emp2_report.addStatus(
	// new TaskStatus("Install SP#52 on automation server (ceng13233-bf1) to run
	// SoapUI tests", 3f, "Done"));
	// emp2_report.setPlans("tomorrow");
	// project1.addEmployeeReport(emp2_report);
	// project2.addEmployeeReport(emp2_report);
	//
	// projectList.add(project1);
	// projectList.add(project2);
	//
	// return projectList;
	// }

	public static void main(String[] args) throws Exception {
		generateReport("/","team1", new Date());
	}

}
