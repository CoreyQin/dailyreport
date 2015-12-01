package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamProjectDao {

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
