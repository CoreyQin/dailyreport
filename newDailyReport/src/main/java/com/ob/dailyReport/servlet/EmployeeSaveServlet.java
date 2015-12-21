package com.ob.dailyReport.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.service.EmployeeService;

/**
 * Servlet implementation class EmployeeSaveServlet
 */
public class EmployeeSaveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(EmployeeSaveServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeSaveServlet() {
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
		String employeeName = request.getParameter("employee");
		String project = request.getParameter("project");
		String role = request.getParameter("role");
		String email = request.getParameter("email");

		Employee employee = new Employee(employeeName, team, project, role, email, true);
		log.info("request to save employee : " + employee.toJsonObject().toString());

		try {
			EmployeeService.saveEmployee(employee);
			JSONObject result = new JSONObject();
			result.put("success", "true");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
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
