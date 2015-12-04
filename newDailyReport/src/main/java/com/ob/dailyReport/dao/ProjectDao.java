package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ob.dailyReport.db.DataBaseHandler;

public class ProjectDao {

	public static boolean addProject(String project, String team) throws SQLException {
		String sql = "insert into project (project,team) values('" + project + "','" + team + "';";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static boolean removeProject(String project, String team) throws SQLException {
		String sql = "delete from project where project = '" + project + "' and team = '" + team + "';";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static List<String> getProjectList(String team) throws SQLException {
		List<String> projectList = new ArrayList<String>();
		String sql = "select project from team_project where team ='" + team + "'";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			String project = rs.getString("project");
			projectList.add(project);
		}
		return projectList;
	}

}
