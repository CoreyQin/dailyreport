package com.ob.dailyReport.model;

import org.json.JSONObject;

public class Project {

	private String name;
	private String team;
	private int order = 10;

	public Project() {

	}

	public Project(String name, String team) {
		super();
		this.name = name;
		this.team = team;
	}

	public Project(String name, String team, int order) {
		super();
		this.name = name;
		this.team = team;
		this.order = order;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public JSONObject toJsonObject() {
		JSONObject projectObject = new JSONObject();
		projectObject.put("project", name);
		projectObject.put("team", team);
		projectObject.put("order", order);
		return projectObject;
	}

}
