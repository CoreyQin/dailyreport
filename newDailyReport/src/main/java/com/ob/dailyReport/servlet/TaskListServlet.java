package com.ob.dailyReport.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.TaskStatus;
import com.ob.dailyReport.service.EmployeeReportService;
import com.ob.dailyReport.util.Base64Util;

public class TaskListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String employee = request.getParameter("name");
		String team = request.getParameter("team");
		String project = request.getParameter("project");
		Date date = new Date();
		EmployeeReport employeeReport;
		try {
			employeeReport = EmployeeReportService.getEmployeeReport(employee, team, date, project);
			String tasksJson = this.converReport2Json(employeeReport);
			response.getWriter().print(tasksJson);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String converReport2Json(EmployeeReport employeeReport) {
		JSONObject employeeObject = new JSONObject();
		employeeObject.put("name", employeeReport.getName());
		employeeObject.put("project", employeeReport.getProject());
		employeeObject.put("plans", Base64Util.encode(employeeReport.getPlans()));
		JSONArray taskArray = new JSONArray();
		for (TaskStatus status : employeeReport.getCurrentStatus()) {
			JSONObject taskObject = new JSONObject();
			taskObject.put("task", Base64Util.encode(status.getDescription()));
			taskObject.put("hours", status.getSpentHours());
			taskObject.put("eta", status.getEta());
			taskArray.put(taskObject);
		}
		employeeObject.put("taskList", taskArray);
		return employeeObject.toString();
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
