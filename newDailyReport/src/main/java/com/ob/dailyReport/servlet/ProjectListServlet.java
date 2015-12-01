package com.ob.dailyReport.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ob.dailyReport.dao.TeamProjectDao;

public class ProjectListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = this.getInputJson(request);
		if(jsonString == null || jsonString.equals("")){
			return;
		}
		JSONObject dataJson = new JSONObject(jsonString);
		String teamName = dataJson.getString("team");
		try {
			List<String> projectList = TeamProjectDao.getProjectList(teamName);
			JSONArray projectArray = new JSONArray();
			JSONObject returnJson = new JSONObject();
			for(String project : projectList){
				projectArray.put(project);
			}
			returnJson.put("projectList", projectArray);
			response.getWriter().print(returnJson.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getInputJson(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String str;
		while ((str = reader.readLine()) != null) {
			buffer.append(str);
		}
		// reader.close();
		System.out.println(buffer.toString());
		return buffer.toString();
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
