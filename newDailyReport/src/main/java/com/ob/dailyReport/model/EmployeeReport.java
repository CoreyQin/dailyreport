package com.ob.dailyReport.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeReport {

	private String name;

	private String project;

	private String role;

	private Date reportDate;

	private List<TaskStatus> currentStatus = new ArrayList<TaskStatus>();

	private String plans;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public List<TaskStatus> getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(List<TaskStatus> currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getPlans() {
		return plans;
	}

	public void setPlans(String plans) {
		this.plans = plans;
	}

	public void addStatus(TaskStatus status) {
		this.currentStatus.add(status);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

}
