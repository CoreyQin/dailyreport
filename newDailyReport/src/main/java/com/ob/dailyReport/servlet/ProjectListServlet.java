package com.ob.dailyReport.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.ob.dailyReport.model.Project;
import com.ob.dailyReport.service.ProjectService;

public class ProjectListServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(ProjectListServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO : need think about communication
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teamName = request.getParameter("team");
		log.info("start getting project list for team : " + teamName);
		try {
			List<Project> projectList = ProjectService.getProjectList(teamName);
			JSONArray projectArray = new JSONArray();
			for (Project project : projectList) {
				projectArray.put(project.toJsonObject());
			}
			response.getWriter().print(projectArray.toString());
			log.info("get project list : " + projectArray.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("get project list error : "+ e.getMessage());
		}
	}

	// private String getInputJson(HttpServletRequest request) throws
	// IOException {
	// BufferedReader reader = request.getReader();
	// StringBuffer buffer = new StringBuffer();
	// String str;
	// while ((str = reader.readLine()) != null) {
	// buffer.append(str);
	// }
	// // reader.close();
	// System.out.println(buffer.toString());
	// return buffer.toString();
	// }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
