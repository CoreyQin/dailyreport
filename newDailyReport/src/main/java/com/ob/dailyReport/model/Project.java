package com.ob.dailyReport.model;

import org.json.JSONObject;

public class Project {

	private String name;
	private String team;
	private int level = 10;

	public Project() {

	}

	public Project(String name, String team) {
		super();
		this.name = name;
		this.team = team;
	}

	public Project(String name, String team, int level) {
		super();
		this.name = name;
		this.team = team;
		this.level = level;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public JSONObject toJsonObject() {
		JSONObject projectObject = new JSONObject();
		projectObject.put("project", name);
		projectObject.put("team", team);
		projectObject.put("level", level);
		return projectObject;
	}

}
