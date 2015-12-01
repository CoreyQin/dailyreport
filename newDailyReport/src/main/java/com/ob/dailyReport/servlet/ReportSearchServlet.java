package com.ob.dailyReport.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ob.dailyReport.dao.EmployeeTaskDao;
import com.ob.dailyReport.model.EmployeeHistory;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.TaskStatus;

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
		String project = dataJson.getString("project");
		Date date = new Date();
		
		EmployeeReport employeeReport;
		try {
			employeeReport = EmployeeTaskDao.getEmployeeReport(employee, project, date);
			String tasksJson = this.converTasks2Json(employeeReport);
			response.getWriter().print(tasksJson);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String converTasks2Json(EmployeeReport employeeReport){
		JSONObject employeeObject = new JSONObject();
		employeeObject.put("name", employeeReport.getName());
		employeeObject.put("project", employeeReport.getProject());
		employeeObject.put("plans", employeeReport.getPlans());
		JSONArray taskArray = new JSONArray();
		for(TaskStatus status : employeeReport.getCurrentStatus()){
			JSONObject taskObject = new JSONObject();
			taskObject.put("task", status.getDescription());
			taskObject.put("hours", status.getSpentHours());
			taskObject.put("eta", status.getEta());
			taskArray.put(taskObject);
		}
		employeeObject.put("taskList", taskArray);
		return employeeObject.toString();
	}
	
	
	private String getInputJson(HttpServletRequest request) throws IOException{
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
