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

import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.model.PageBean;
import com.ob.dailyReport.service.EmployeeService;

public class EmployeeListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teamName = request.getParameter("team");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		PageBean pageBean = null;
		if (page != null && rows != null) {
			pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		}
		try {
			List<Employee> employeeList = EmployeeService.getEmployeeList(teamName, pageBean);
			JSONArray employeeArray = new JSONArray();
			JSONObject returnJson = new JSONObject();
			for (Employee employee : employeeList) {
				employeeArray.put(employee.toJsonObject());
			}
			returnJson.put("rows", employeeArray);
			returnJson.put("total", EmployeeService.employeeCount(teamName));
			// returnJson.put("employeeList", employeeArray);
			response.getWriter().print(returnJson.toString());

		} catch (SQLException e) {
			e.printStackTrace();
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
