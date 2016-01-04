package com.ob.dailyReport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

// TODO:need put the connect string into file
public class DataBaseHandler {

	private static Connection conn;
	
	private static Logger log = Logger.getLogger(DataBaseHandler.class);
	
	public static void setconnection(Connection connection){
		conn = connection;
	}

	public static Connection getConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				return conn;
			}
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newdailyreport", "dailyreport", "dailyreport");
		} catch (Exception e) {
			log.error("fail to connect the database : " + e.getMessage());
		}
		return conn;
	}

	public static ResultSet executeQuerySql(String sql) throws SQLException {
		Connection conn = getConnection();
		Statement st =  conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}

	public static boolean executeSql(String sql) throws SQLException {
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(sql);
		
		return true;
	}

	// public static void main(String[] args) throws SQLException {
	// List<TaskStatus> statusList = getTaskList("Byron Liu", new Date());
	// System.out.println(statusList);
	// }

}
