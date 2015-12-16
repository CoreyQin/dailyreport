package com.ob.dailyReport.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.service.EmployeeService;

/**
 * Servlet implementation class EmployeeSearchServlet
 */
public class EmployeeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeSearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String teamName = request.getParameter("team");
		String employeeName = request.getParameter("employee");
		try {
			Employee employee = EmployeeService.getEmployee(employeeName, teamName);
			JSONObject returnJson = employee.toJsonObject();
			response.getWriter().print(returnJson.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
