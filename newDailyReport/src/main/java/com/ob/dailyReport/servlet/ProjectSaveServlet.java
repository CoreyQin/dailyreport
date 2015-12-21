package com.ob.dailyReport.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.ob.dailyReport.model.Project;
import com.ob.dailyReport.service.ProjectService;

/**
 * Servlet implementation class ProjectSaveServlet
 */
public class ProjectSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectSaveServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String team = request.getParameter("team");
		String projectName = request.getParameter("project");
		String level = request.getParameter("level");
		String rfa = request.getParameter("rfa");
		String status = request.getParameter("status");
		Project project = new Project(projectName, team, rfa, status, Integer.parseInt(level));
		try {
			ProjectService.saveProject(project);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
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
