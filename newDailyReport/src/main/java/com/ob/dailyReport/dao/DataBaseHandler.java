package com.ob.dailyReport.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHandler {

	private static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailyreport", "root", "root");
		} catch (Exception e) {
			System.out.println("fail to connect the database : " + e.getMessage());
		}
		return con;
	}

	public static ResultSet executeQuerySql(String sql) throws SQLException{
		Connection conn = getConnection();
		Statement st = (Statement) conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	
	
	public static boolean executeSql(String sql) throws SQLException{
		Connection conn = getConnection();
		Statement st = (Statement) conn.createStatement();
		boolean rs = st.execute(sql);
		return rs;
	}
	
	

	// public static void main(String[] args) throws SQLException {
	// List<TaskStatus> statusList = getTaskList("Byron Liu", new Date());
	// System.out.println(statusList);
	// }

}
