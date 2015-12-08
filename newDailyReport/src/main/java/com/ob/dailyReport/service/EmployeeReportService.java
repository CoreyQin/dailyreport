package com.ob.dailyReport.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ob.dailyReport.dao.EmployeeTaskDao;
import com.ob.dailyReport.dao.PlanDao;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.TaskRecord;
import com.ob.dailyReport.model.TaskStatus;
import com.ob.dailyReport.util.DateUtil;

public class EmployeeReportService {

	public static EmployeeReport getEmployeeReport(String name, Date date, String project) throws SQLException {
		EmployeeReport employeeReport = new EmployeeReport();
		employeeReport.setName(name);
		employeeReport.setReportDate(date);
		employeeReport.setProject(project);

		List<TaskRecord> taskList = EmployeeTaskDao.getEmployeeTasks(name, project, date);
		List<TaskStatus> statusList = new ArrayList<TaskStatus>();
		for (TaskRecord taskRecord : taskList) {
			statusList.add(taskRecord.getStatus());
		}
		employeeReport.setCurrentStatus(statusList);
		String plans = PlanDao.getPlan(name, project, date);
		employeeReport.setPlans(plans);
		return employeeReport;
	}

	public static void submitReport(EmployeeReport report) throws SQLException {
		String name = report.getName();
		String project = report.getProject();
		Date date = report.getReportDate();

		EmployeeTaskDao.removeAllTasks(name, project, DateUtil.FormatDate2String(date));
		PlanDao.removePlan(report);

		EmployeeTaskDao.addTasks(report);
		PlanDao.addPlan(report);
	}

}
