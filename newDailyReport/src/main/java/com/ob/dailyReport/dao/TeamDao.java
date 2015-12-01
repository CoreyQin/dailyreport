package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ob.dailyReport.model.Role;

public class TeamDao {

	public static List<String> getTeamList() throws SQLException {
		List<String> teamList = new ArrayList<String>();
		String sql = "select distinct(team) from team_project";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			String team = rs.getString("team");
			teamList.add(team);
		}
		return teamList;
	}

	public static List<String> getEmployeeList(String team) throws SQLException {
		List<String> employeeList = new ArrayList<String>();
		String sql = "select employee from team_member where team = '" + team + "'";
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
			sql = "select role from project_info where employee = '" + employee + "'";
		} else {
			sql = "select role from project_info where employee = '" + employee + "' and project = '" + project + "'";
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
			if(role.equals(Role.TL)){
				return employee;
			}
		}
		return null;
	}

}
