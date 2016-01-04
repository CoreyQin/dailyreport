package com.ob.dailyReport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ob.dailyReport.db.DataBaseHandler;

public class TeamDao {

	public static boolean addTeam(String team) throws SQLException {
		String sql = "insert into team (team) values('" + team + "');";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static boolean removeTeam(String team) throws SQLException {
		String sql = "delete from team where team = '" + team + "';";
		boolean success = DataBaseHandler.executeSql(sql);
		return success;
	}

	public static List<String> getTeamList() throws SQLException {
		List<String> teamList = new ArrayList<String>();
		String sql = "select team from team";
		ResultSet rs = DataBaseHandler.executeQuerySql(sql);
		while (rs.next()) {
			String team = rs.getString("team");
			teamList.add(team);
		}
		return teamList;
	}

}
