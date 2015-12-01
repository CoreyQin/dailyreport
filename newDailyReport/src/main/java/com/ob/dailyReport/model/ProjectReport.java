package com.ob.dailyReport.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectReport {

	private String projectName;

	private List<EmployeeReport> employeeRList = new ArrayList<EmployeeReport>();

	public ProjectReport() {

	}

	public ProjectReport(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<EmployeeReport> getEmployeeRList() {
		return employeeRList;
	}

	public void addEmployeeReport(EmployeeReport r) {
		this.employeeRList.add(r);
	}

}
