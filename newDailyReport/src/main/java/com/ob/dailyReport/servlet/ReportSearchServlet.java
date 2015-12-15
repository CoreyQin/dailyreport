package com.ob.dailyReport.servlet;

import java.io.BufferedReader;
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

public class ReportSearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = this.getInputJson(request);
		JSONObject dataJson = new JSONObject(jsonString);
		String employee = dataJson.getString("name");
		String team = dataJson.getString("team");
		String project = dataJson.getString("project");
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
		employeeObject.put("role",employeeReport.getRole());
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
