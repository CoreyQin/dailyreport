package com.ob.dailyReport.model;

import org.json.JSONObject;

public class Employee {

	private String name;
	private String team;
	private String project;
	private String role;
	private String email;
	private Boolean active;
	
	public Employee(){
	}
	
	public Employee(String name, String team, String project, String role, String email, Boolean active) {
		super();
		this.name = name;
		this.team = team;
		this.project = project;
		this.role = role;
		this.email = email;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public JSONObject toJsonObject() {
		JSONObject employeeOjbect = new JSONObject();
		employeeOjbect.put("employee", name);
		employeeOjbect.put("team", team);
		employeeOjbect.put("project", project);
		employeeOjbect.put("role", role);
		employeeOjbect.put("email", email);
		return employeeOjbect;
	}

}
