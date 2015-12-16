package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ob.dailyReport.db.DataBaseHandler;
import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.model.PageBean;
import com.ob.dailyReport.model.Role;

public class EmployeeDao {

	public static Employee getEmployee(String employeeName, String team) throws SQLException {
		String sql = "select * from employee where employee = '" + employeeName + "' and team = '" + team
				+ "' and active = '1';";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		Employee employee = null;
		if (rs.next()) {
			employee = new Employee();
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

	public static List<Employee> getEmployeeList(String team, PageBean pageBean) throws SQLException {
		List<Employee> employeeList = new ArrayList<Employee>();
		StringBuffer sb = new StringBuffer("select * from employee where team = '" + team + "' and active = '1'");
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getRows());
		}
		ResultSet rs = DataBaseHandler.executeQuerySql(sb.toString());
		while (rs.next()) {
			Employee employee = new Employee();
			String name = rs.getString("employee");
			String project = rs.getString("project");
			String role = rs.getString("role");
			String email = rs.getString("email");
			employee.setName(name);
			employee.setTeam(team);
			employee.setProject(project);
			employee.setRole(role);
			employee.setEmail(email);

			employeeList.add(employee);
		}
		return employeeList;
	}

	public static int employeeCount(String team) throws SQLException {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from employee where team = '" + team + "' and active = '1'");
		ResultSet rs = DataBaseHandler.executeQuerySql(sb.toString());
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public static List<String> getEmployeeNameList(String team, PageBean pageBean) throws SQLException {
		List<String> employeeList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer(
				"select employee from employee where team = '" + team + "' and active = '1'");
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getRows());
		}
		ResultSet rs = DataBaseHandler.executeQuerySql(sb.toString());
		while (rs.next()) {
			String employee = rs.getString("employee");
			employeeList.add(employee);
		}
		return employeeList;
	}

	public static boolean addEmployee(Employee employee) throws SQLException {
		String employeeName = employee.getName();
		String team = employee.getTeam();
		String project = employee.getProject();
		String role = employee.getRole();
		String email = employee.getEmail();
		String sql = "insert into employee (employee,team,project,role,email,active) values('" + employeeName + "','"
				+ team + "','" + project + "','" + role + "','" + email + "','1')";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static boolean removeEmployee(String employeeName, String team) throws SQLException {
		String sql = "delete from employee where employee = '" + employeeName + "' and team = '" + team + "';";
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

		sql.append(" where employee='" + employeeName + "' and team = '" + team + "';");
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

	public static String getEmployeeRole(String employee, String team, String project) throws SQLException {
		String sql;
		if (project == null || project.equals("")) {
			sql = "select role from employee where employee = '" + employee + "' and team = '" + team + "'";
		} else {
			sql = "select role from employee where employee = '" + employee + "' and team = '" + team
					+ "' and project = '" + project + "'";
		}

		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		if (rs.next()) {
			String role = rs.getString("role");
			return role;
		}
		return null;
	}

	public static String getTeamLeader(String team) throws SQLException {
		List<String> employeeList = getEmployeeNameList(team, null);
		for (String employee : employeeList) {
			String role = getEmployeeRole(employee, team, null);
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
