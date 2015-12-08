package com.ob.dailyReport.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ob.dailyReport.dao.TeamDao;

public class TeamListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<String> teamList = TeamDao.getTeamList();
			JSONArray teamArray = new JSONArray();
//			JSONObject dataJson = new JSONObject();
			for(String team : teamList){
				JSONObject rowObject = new JSONObject();
				rowObject.put("value", team);
				rowObject.put("text", team);
				teamArray.put(rowObject);
			}
//			dataJson.put("teamList", teamArray);
			response.getWriter().print(teamArray.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


}
