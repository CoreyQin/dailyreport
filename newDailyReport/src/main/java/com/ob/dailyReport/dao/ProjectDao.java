package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ob.dailyReport.db.DataBaseHandler;
import com.ob.dailyReport.model.Project;

public class ProjectDao {

	public static boolean addProject(Project project) throws SQLException {
		String projectName = project.getName();
		String team = project.getTeam();
		int level = project.getLevel();
		String sql = "insert into project (project,team,level) values('" + projectName + "','" + team + "'," + level
				+ ");";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static boolean removeProject(Project project) throws SQLException {
		String projectName = project.getName();
		String team = project.getTeam();
		String sql = "delete from project where project = '" + projectName + "' and team = '" + team + "';";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static List<Project> getProjectList(String team) throws SQLException {
		List<Project> projectList = new ArrayList<Project>();
		String sql = "select * from project where team ='" + team + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			String projectName = rs.getString("project");
			int level = rs.getInt("level");
			Project project = new Project(projectName, team, level);
			projectList.add(project);
		}
		return projectList;
	}

	public static boolean isExsit(String projectName) throws SQLException {
		Project project = getProject(projectName);
		if (project != null) {
			return true;
		}
		return false;
	}

	public static Project getProject(String projectName) throws SQLException {
		String sql = "select * from project where project ='" + projectName + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		if (rs.next()) {
			String team = rs.getString("team");
			int level = rs.getInt("level");
			Project project = new Project(projectName, team, level);
			return project;
		}
		return null;
	}

	public static boolean updateProject(Project project) throws SQLException {
		String sql = getUpdateSql(project);
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	private static String getUpdateSql(Project project) {
		String projectName = project.getName();
		String team = project.getTeam();
		int level = project.getLevel();
		StringBuffer sql = new StringBuffer("update project set ");
		Boolean hasValue = false;
		if (team != null) {
			sql.append("team ='" + team + "'");
			hasValue = true;
		}

		updateSql(sql, "level", String.valueOf(level), hasValue);
		hasValue = true;

		sql.append(" where project='" + projectName + "';");
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

}
