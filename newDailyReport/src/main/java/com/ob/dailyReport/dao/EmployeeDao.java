package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ob.dailyReport.model.Employee;

public class EmployeeDao {

	public static Employee getEmployee(String employeeName) throws SQLException {
		String sql = "select * from team_member where employee = '" + employeeName + "';";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		Employee employee = null;
		if (rs.next()) {
			employee = new Employee();
			String team = rs.getString("team");
			String project = rs.getString("project");
			String role = rs.getString("role");
			String email = rs.getString("email");
			employee.setTeam(team);
			employee.setProject(project);
			employee.setRole(role);
			employee.setEmail(email);
		}
		return employee;

	}

}
