package com.ob.dailyReport.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ob.dailyReport.dao.EmployeeTaskDao;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.TaskStatus;

public class SubmitReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// get user input
		String jsonString = this.getInputJson(request);
		// convert user input to data model
//		EmployeeReport input = this.fakeModel();
		EmployeeReport input = this.convertJson2Model(jsonString);
		//
		EmployeeTaskDao.submitTasks(input);

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

	private EmployeeReport fakeModel() {
		String jsonString = "{'name':'corey','project':'project1','role':'dev','taskList':[{'task':'task1','hours':'2','eta':'eta'}],'plans':'plans of corey'}";
		EmployeeReport input = this.convertJson2Model(jsonString);
		return input;
	}

	private EmployeeReport convertJson2Model(String jsonString) {
		JSONObject dataJson = new JSONObject(jsonString);

		EmployeeReport input = new EmployeeReport();
		input.setName(dataJson.getString("name"));
		input.setProject(dataJson.getString("project"));
		input.setRole(dataJson.getString("role"));
		input.setReportDate(new Date());
		input.setPlans(dataJson.getString("plans"));

		List<TaskStatus> taskList = new ArrayList<TaskStatus>();
		JSONArray taskArray = dataJson.getJSONArray("taskList");
		for (int i = 0; i < taskArray.length(); i++) {
			JSONObject taskObject = taskArray.getJSONObject(i);
			String taskDesc = taskObject.getString("task");
			String hours = taskObject.getString("hours");
			String eta = taskObject.getString("eta");

			TaskStatus task1 = new TaskStatus();
			task1.setDescription(taskDesc);
			task1.setSpentHours(Float.parseFloat(hours));
			task1.setEta(eta);
			taskList.add(task1);
		}

		input.setCurrentStatus(taskList);

		return input;
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
