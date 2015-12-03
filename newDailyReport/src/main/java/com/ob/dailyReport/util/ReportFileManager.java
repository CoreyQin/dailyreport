package com.ob.dailyReport.util;

public class ReportFileManager {

	private String reportPath;

	private static volatile ReportFileManager instance;

	private ReportFileManager() {
	}

	public static ReportFileManager getInstance() {
		if (instance == null) {
			instance = new ReportFileManager();
		}
		return instance;
	}

	public void setReportPath(String path) {
		this.reportPath = path;
	}

	public String getReportPath() {
		return this.reportPath;
	}

}
