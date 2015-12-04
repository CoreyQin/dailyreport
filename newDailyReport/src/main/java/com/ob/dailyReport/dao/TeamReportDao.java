package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ob.dailyReport.model.EmployeeHistory;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.ProjectReport;
import com.ob.dailyReport.model.TaskStatus;
import com.ob.dailyReport.util.Base64Util;
import com.ob.dailyReport.util.DateUtil;

public class TeamReportDao {

	public static List<ProjectReport> getTeamReport(String team, Date date) throws SQLException {
		String sql = "select * from team_member where team = '" + team + "'";
		List<EmployeeHistory> allEmployee = new ArrayList<EmployeeHistory>();
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			String employee = rs.getString("employee");
			List<EmployeeHistory> historyList = getEmployeeReport(employee, date);
			allEmployee.addAll(historyList);
		}
		List<ProjectReport> projectList = convertList2Report(allEmployee);
		return projectList;
	}

	private static List<ProjectReport> convertList2Report(List<EmployeeHistory> historyList) throws SQLException {
		List<ProjectReport> projectList = new ArrayList<ProjectReport>();
		for (EmployeeHistory eh : historyList) {
			fillProjectList(projectList, eh);
		}
		return projectList;
	}

	private static void fillProjectList(List<ProjectReport> projectList, EmployeeHistory eh) throws SQLException {
		String projectName = eh.getProjectName();
		ProjectReport projectReport = null;
		for (ProjectReport project : projectList) {
			// project entity exist
			if (project.getProjectName().equals(projectName)) {
				projectReport = project;
				break;
			}
		}
		if (projectReport == null) {
			projectReport = new ProjectReport();
			projectReport.setProjectName(projectName);
			projectList.add(projectReport);
		}
		fillEmployeeList(projectReport.getEmployeeRList(), eh);
	}

	private static EmployeeReport fillEmployeeList(List<EmployeeReport> employeeList, EmployeeHistory eh)
			throws SQLException {
		String employeeName = eh.getEmployeeName();
		String role = eh.getRole();
		String project = eh.getProjectName();
		Date date = eh.getDate();
		TaskStatus task = eh.getStatus();
		EmployeeReport employeeReport = null;

		for (EmployeeReport report : employeeList) {
			if (report.getName().equals(employeeName)) {
				employeeReport = report;
				break;
			}
		}

		if (employeeReport == null) {
			employeeReport = new EmployeeReport();
			employeeReport.setName(employeeName);
			employeeReport.setRole(role);
			employeeReport.setPlans(getEmployeePlans(employeeName, project, date));
			employeeList.add(employeeReport);
		}
		employeeReport.addStatus(task);
		return employeeReport;
	}

	private static String getEmployeePlans(String employee, String project, Date date) throws SQLException {
		String dateString = DateUtil.FormatDate2String(date);
		String sql = "select plans from plans_info where employee='" + employee + "' and date = '" + dateString
				+ "' and project = '" + project + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		if (rs.next()) {
			String plans = Base64Util.decode(rs.getString("plans"));
			return plans;
		}
		return null;
	}

	private static List<EmployeeHistory> getEmployeeReport(String employeeName, Date date) throws SQLException {
		List<EmployeeHistory> historyList = new ArrayList<EmployeeHistory>();
		String dateString = DateUtil.FormatDate2String(date);
		String sql = "select * from task_info where date ='" + dateString + "' and employee = '" + employeeName + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			EmployeeHistory eh = new EmployeeHistory();
			eh.setEmployeeName(employeeName);
			eh.setDate(date);
			String taskDesc = Base64Util.decode(rs.getString("task_desc"));
			float hours = rs.getFloat("hours");
			String eta = rs.getString("eta");
			String project = rs.getString("project");
			String role = rs.getString("role");
			TaskStatus status = new TaskStatus();
			status.setDescription(taskDesc);
			status.setSpentHours(hours);
			status.setEta(eta);
			eh.setStatus(status);
			eh.setProjectName(project);
			eh.setRole(role);
			historyList.add(eh);
		}
		return historyList;
	}
}
