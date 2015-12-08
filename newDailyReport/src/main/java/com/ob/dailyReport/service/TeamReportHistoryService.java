package com.ob.dailyReport.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ob.dailyReport.dao.EmployeeDao;
import com.ob.dailyReport.dao.EmployeeTaskDao;
import com.ob.dailyReport.dao.PlanDao;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.ProjectReport;
import com.ob.dailyReport.model.TaskRecord;
import com.ob.dailyReport.model.TaskStatus;

public class TeamReportHistoryService {

	// public static List<ProjectReport> getTeamReport(String team, Date date)
	// throws SQLException {
	// String sql = "select * from team_member where team = '" + team + "'";
	// List<TaskRecord> allEmployee = new ArrayList<TaskRecord>();
	// ResultSet rs = DataBaseHandler.executeQuerySql(sql);
	// while (rs.next()) {
	// String employee = rs.getString("employee");
	// List<TaskRecord> historyList = getEmployeeReport(employee, date);
	// allEmployee.addAll(historyList);
	// }
	// List<ProjectReport> projectList = convertList2Report(allEmployee);
	// return projectList;
	// }

	/**
	 * 
	 * @param team
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public static List<ProjectReport> getTeamReport(String team, Date date) throws SQLException {
		List<TaskRecord> allTaskRecords = new ArrayList<TaskRecord>();
		List<String> employeeList = EmployeeDao.getEmployeeNameList(team, null);
		for (String employee : employeeList) {
			List<TaskRecord> employeeTasks = EmployeeTaskDao.getEmployeeTasks(employee, date);
			allTaskRecords.addAll(employeeTasks);
		}
		List<ProjectReport> projectList = convertList2Report(allTaskRecords);
		return projectList;
	}

	/**
	 * 
	 * @param historyList
	 * @return
	 * @throws SQLException
	 */
	private static List<ProjectReport> convertList2Report(List<TaskRecord> historyList) throws SQLException {
		List<ProjectReport> projectList = new ArrayList<ProjectReport>();
		for (TaskRecord eh : historyList) {
			fillProjectList(projectList, eh);
		}
		return projectList;
	}

	/**
	 * 
	 * @param projectList
	 * @param eh
	 * @throws SQLException
	 */
	private static void fillProjectList(List<ProjectReport> projectList, TaskRecord eh) throws SQLException {
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

	/**
	 * 
	 * @param employeeList
	 * @param eh
	 * @return
	 * @throws SQLException
	 */
	private static EmployeeReport fillEmployeeList(List<EmployeeReport> employeeList, TaskRecord eh)
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
			employeeReport.setPlans(PlanDao.getPlan(employeeName, project, date));
			employeeList.add(employeeReport);
		}
		employeeReport.addStatus(task);
		return employeeReport;
	}

	// private static String getEmployeePlans(String employee, String project,
	// Date date) throws SQLException {
	// String dateString = DateUtil.FormatDate2String(date);
	// String sql = "select plans from plans_info where employee='" + employee +
	// "' and date = '" + dateString
	// + "' and project = '" + project + "'";
	// ResultSet rs = DataBaseHandler.executeQuerySql(sql);
	// if (rs.next()) {
	// String plans = Base64Util.decode(rs.getString("plans"));
	// return plans;
	// }
	// return null;
	// }

	// private static List<TaskRecord> getEmployeeReport(String employeeName,
	// Date date) throws SQLException {
	// List<TaskRecord> historyList = new ArrayList<TaskRecord>();
	// String dateString = DateUtil.FormatDate2String(date);
	// String sql = "select * from task_info where date ='" + dateString + "'
	// and employee = '" + employeeName + "'";
	// ResultSet rs = DataBaseHandler.executeQuerySql(sql);
	// while (rs.next()) {
	// TaskRecord eh = new TaskRecord();
	// eh.setEmployeeName(employeeName);
	// eh.setDate(date);
	// String taskDesc = Base64Util.decode(rs.getString("task_desc"));
	// float hours = rs.getFloat("hours");
	// String eta = rs.getString("eta");
	// String project = rs.getString("project");
	// String role = rs.getString("role");
	// TaskStatus status = new TaskStatus();
	// status.setDescription(taskDesc);
	// status.setSpentHours(hours);
	// status.setEta(eta);
	// eh.setStatus(status);
	// eh.setProjectName(project);
	// eh.setRole(role);
	// historyList.add(eh);
	// }
	// return historyList;
	// }
}
