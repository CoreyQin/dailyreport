package com.ob.dailyReport.model;

import org.json.JSONObject;

import com.ob.dailyReport.util.Constant;

public class Project {

	private String name;
	private String team;
	private String rfa;
	private String status = Constant.ontrack;
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

	public Project(String name, String team, String rfa, String status, int level) {
		super();
		this.name = name;
		this.team = team;
		this.rfa = rfa;
		this.status = status;
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

	public JSONObject toJsonObject() {
		JSONObject projectObject = new JSONObject();
		projectObject.put("project", name);
		projectObject.put("team", team);
		projectObject.put("rfa", rfa);
		projectObject.put("status", status);
		projectObject.put("level", level);
		return projectObject;
	}

}
