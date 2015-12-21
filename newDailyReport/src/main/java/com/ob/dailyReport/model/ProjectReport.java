package com.ob.dailyReport.model;

import java.util.ArrayList;
import java.util.List;

import com.ob.dailyReport.util.Constant;

public class ProjectReport {

	private String projectName;
	
	private String rfa;
	
	private String status = Constant.ontrack;

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

	public String getRfa() {
		return rfa;
	}

	public void setRfa(String rfa) {
		this.rfa = rfa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
