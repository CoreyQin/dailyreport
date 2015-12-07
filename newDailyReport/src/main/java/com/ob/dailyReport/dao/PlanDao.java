package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ob.dailyReport.db.DataBaseHandler;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.util.Base64Util;
import com.ob.dailyReport.util.DateUtil;

public class PlanDao {

	private static final String table = "plans_info";

	public static boolean addPlan(EmployeeReport employeeReport) throws SQLException {
		String employee = employeeReport.getName();
		String project = employeeReport.getProject();
		String plan = employeeReport.getPlans();
		Date date = employeeReport.getReportDate();

		String sql = "insert into " + table + " (employee,date,project,plans) values('" + employee + "','"
				+ DateUtil.FormatDate2String(date) + "','" + project + "','" + plan + "');";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static boolean removePlan(EmployeeReport employeeReport) throws SQLException {
		String employee = employeeReport.getName();
		String project = employeeReport.getProject();
		Date date = employeeReport.getReportDate();
		String sql = "delete from " + table + " where project = '" + project + "' and employee = '" + employee
				+ "' and date = '" + DateUtil.FormatDate2String(date) + "';";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static String getPlan(EmployeeReport employeeReport) throws SQLException {
		String employee = employeeReport.getName();
		String project = employeeReport.getProject();
		Date date = employeeReport.getReportDate();
		return getPlan(employee, project, date);
	}

	public static String getPlan(String employee, String project, Date date) throws SQLException {
		String dateString = DateUtil.FormatDate2String(date);
		String sql = "select plans from " + table + " where employee='" + employee + "' and date = '" + dateString
				+ "' and project = '" + project + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		if (rs.next()) {
			String plans = Base64Util.decode(rs.getString("plans"));
			return plans;
		}
		return null;
	}

}
