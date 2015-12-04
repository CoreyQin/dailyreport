package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ob.dailyReport.db.DataBaseHandler;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.util.Base64Util;
import com.ob.dailyReport.util.DateUtil;

public class PlanDao {
	
	public static boolean addPlan(EmployeeReport employeeReport) {
		String employee = employeeReport.getName();
		String plan = employeeReport.getPlans();
		Date date = employeeReport.getReportDate();
		
		
		
		return false;
	}
	
	
	public static boolean removePlan(EmployeeReport employeeReport){
		String employee = employeeReport.getName();
		String plan = employeeReport.getPlans();
		Date date = employeeReport.getReportDate();
		
		
		return false;
	}
	
	
	public static String getPlan(EmployeeReport employeeReport){
		String employee = employeeReport.getName();
		String plan = employeeReport.getPlans();
		Date date = employeeReport.getReportDate();
		
		
		return null;
	}
	
	public static String getPlan(String employee, String project, Date date) throws SQLException {
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

}
