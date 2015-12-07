package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ob.dailyReport.db.DataBaseHandler;
import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.model.Role;

public class EmployeeDao {

	public static Employee getEmployee(String employeeName) throws SQLException {
		String sql = "select * from employee where employee = '" + employeeName + "';";
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

	public static boolean addEmployee(Employee employee) throws SQLException {
		String employeeName = employee.getName();
		String team = employee.getTeam();
		String project = employee.getProject();
		String role = employee.getRole();
		String email = employee.getEmail();
		String sql = "insert into employee (employee,team,project,role,email) values('" + employeeName + "','" + team
				+ "','" + project + "','" + role + "','" + email + "');" + employeeName + "';";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static boolean removeEmployee(String employeeName) throws SQLException {
		String sql = "update employee set active = 0 where employee = '" + employeeName + "';";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static boolean updateEmployee(Employee employee) throws SQLException {
		String sql = getUpdateSql(employee);
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	private static String getUpdateSql(Employee employee) {
		String employeeName = employee.getName();
		String team = employee.getTeam();
		String project = employee.getProject();
		String role = employee.getRole();
		String email = employee.getEmail();
		StringBuffer sql = new StringBuffer("update employee set ");
		Boolean hasValue = false;
		if (team != null) {
			sql.append("team='" + team + "'");
			hasValue = true;
		}

		if (project != null) {
			updateSql(sql, "project", project, hasValue);
			hasValue = true;
		}

		if (role != null) {
			updateSql(sql, "role", role, hasValue);
			hasValue = true;
		}
		if (email != null) {
			updateSql(sql, "email", email, hasValue);
			hasValue = true;
		}

		sql.append(" where employee='" + employeeName + "';");
		return sql.toString();
	}

	private static StringBuffer updateSql(StringBuffer sql, String columnName, String value, Boolean hasValue) {
		if (value != null) {
			if (hasValue) {
				sql.append(", ");
			}
			sql.append(columnName + "='" + value + "'");
		}
		return sql;
	}

	public static List<String> getEmployeeList(String team) throws SQLException {
		List<String> employeeList = new ArrayList<String>();
		String sql = "select employee from employee where team = '" + team + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			String employee = rs.getString("employee");
			employeeList.add(employee);
		}
		return employeeList;
	}

	public static String getEmployeeRole(String employee, String project) throws SQLException {
		String sql;
		if (project == null || project.equals("")) {
			sql = "select role from employee where employee = '" + employee + "'";
		} else {
			sql = "select role from employee where employee = '" + employee + "' and project = '" + project + "'";
		}

		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		if (rs.next()) {
			String role = rs.getString("role");
			return role;
		}
		return null;
	}

	public static String getTeamLeader(String team) throws SQLException {
		List<String> employeeList = getEmployeeList(team);
		for (String employee : employeeList) {
			String role = getEmployeeRole(employee, null);
			if (role != null && role.equals(Role.TL.toString())) {
				return employee;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Employee employee = new Employee();
		employee.setName("corey");
		// employee.setTeam("team1");
		employee.setProject("project2");
		employee.setRole("Dev");
		System.out.println(getUpdateSql(employee));
	}

}
