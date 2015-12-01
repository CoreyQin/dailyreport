package com.ob.dailyReport.model;

public class TaskStatus {

	private String description;
	private float spentHours;
	private String eta;

	public TaskStatus() {

	}

	public TaskStatus(String description, float spentHours, String eta) {
		this.description = description;
		this.spentHours = spentHours;
		this.eta = eta;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getSpentHours() {
		return spentHours;
	}

	public void setSpentHours(float spentHours) {
		this.spentHours = spentHours;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

}
