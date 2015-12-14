package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ob.dailyReport.model.TaskRecord;
import com.ob.dailyReport.db.DataBaseHandler;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.TaskStatus;
import com.ob.dailyReport.util.Base64Util;
import com.ob.dailyReport.util.DateUtil;

public class EmployeeTaskDao {

	/**
	 * 
	 * @param employee
	 * @throws SQLException
	 */
	public static void addTasks(EmployeeReport employee) throws SQLException {
		String name = employee.getName();
		String project = employee.getProject();
		String role = employee.getRole();
		String date = DateUtil.FormatDate2String(employee.getReportDate());
		// String plans = employee.getPlans();
		// simple strategy : first remove all tasks which the employee submitted
		// the day.
		// removeAllTasks(name, project, date);

		List<TaskStatus> taskList = employee.getCurrentStatus();
		for (TaskStatus task : taskList) {
			String desc = task.getDescription();
			float hours = task.getSpentHours();
			String eta = task.getEta();
			String sql = "insert into task_info values('" + name + "','" + project + "','" + role + "','" + date + "','"
					+ desc + "','" + hours + "','" + eta + "')";
			DataBaseHandler.executeSql(sql);
		}

		// String plansSql = "insert into plans_info values('" + name + "','" +
		// date + "','" + project + "','" + plans
		// + "')";
		// DataBaseHandler.executeSql(plansSql);

	}

	/**
	 * 
	 * @param employee
	 * @param projectName
	 * @param dateString
	 * @throws SQLException
	 */
	public static void removeAllTasks(String employee, String projectName, String dateString) throws SQLException {
		// String plan_sql = "delete from plans_info where employee = '" +
		// employee + "' and project='" + projectName
		// + "' and date = '" + dateString + "'";
		String task_sql = "delete from task_info where employee = '" + employee + "' and project='" + projectName
				+ "' and date = '" + dateString + "'";
		// DataBaseHandler.executeSql(plan_sql);
		DataBaseHandler.executeSql(task_sql);
	}

	
	// TODO: need add team parameter
	public static List<TaskRecord> getEmployeeTasks(String employee, Date date) throws SQLException {
		List<TaskRecord> historyList = new ArrayList<TaskRecord>();
		String dateString = DateUtil.FormatDate2String(date);
		String sql = "select * from task_info where date ='" + dateString + "' and employee = '" + employee + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			TaskRecord eh = new TaskRecord();
			eh.setEmployeeName(employee);
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

	public static List<TaskRecord> getEmployeeTasks(String employee, String project, Date date) throws SQLException {
		List<TaskRecord> historyList = new ArrayList<TaskRecord>();
		String dateString = DateUtil.FormatDate2String(date);
		String sql = "select * from task_info where date ='" + dateString + "' and employee = '" + employee
				+ "' and project = '" + project + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			TaskRecord taskRecord = new TaskRecord();
			taskRecord.setEmployeeName(employee);
			taskRecord.setDate(date);
			String taskDesc = Base64Util.decode(rs.getString("task_desc"));
			float hours = rs.getFloat("hours");
			String eta = rs.getString("eta");
			String role = rs.getString("role");
			TaskStatus status = new TaskStatus();
			status.setDescription(taskDesc);
			status.setSpentHours(hours);
			status.setEta(eta);
			taskRecord.setStatus(status);
			taskRecord.setProjectName(project);
			taskRecord.setRole(role);
			historyList.add(taskRecord);
		}
		return historyList;

	}

	// public static EmployeeReport getEmployeeReport(String employee, String
	// projectName, Date date) throws SQLException {
	// EmployeeReport employeeReport = new EmployeeReport();
	// employeeReport.setName(employee);
	// employeeReport.setProject(projectName);
	// employeeReport.setReportDate(date);
	//
	// String dateString = DateUtil.FormatDate2String(date);
	// String sql = "select * from task_info where employee = '" + employee + "'
	// and project='" + projectName
	// + "' and date = '" + dateString + "'";
	// ResultSet rs = DataBaseHandler.executeQuerySql(sql);
	// while (rs.next()) {
	// String taskDesc = rs.getString("task_desc");
	// float hours = rs.getFloat("hours");
	// String eta = rs.getString("eta");
	//
	// TaskStatus status = new TaskStatus();
	// status.setDescription(taskDesc);
	// status.setSpentHours(hours);
	// status.setEta(eta);
	// employeeReport.addStatus(status);
	// }
	//
	//// String plans_sql = "select plans from plans_info where employee='" +
	// employee + "' and date = '" + dateString
	//// + "' and project = '" + projectName + "'";
	//// rs = DataBaseHandler.executeQuerySql(plans_sql);
	//// if (rs.next()) {
	//// String plans = rs.getString("plans");
	//// employeeReport.setPlans(plans);
	//// }
	// return employeeReport;
	// }

}
