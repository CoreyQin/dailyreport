package com.ob.dailyReport.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ob.dailyReport.service.EmployeeService;

/**
 * Servlet implementation class EmployeeDeleteServlet
 */
public class EmployeeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(EmployeeDeleteServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeDeleteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String employeeName = request.getParameter("employee");
		String team = request.getParameter("team");
		log.info("request to delete employee : {employee:" + employeeName + "e,team:" + team + "}");
		try {
			EmployeeService.removeEmployee(employeeName, team);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			response.getWriter().print(result.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("delete employee error : " + e.getMessage());
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
