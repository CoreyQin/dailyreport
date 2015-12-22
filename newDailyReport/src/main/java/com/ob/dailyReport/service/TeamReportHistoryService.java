package com.ob.dailyReport.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.ob.dailyReport.dao.EmployeeDao;
import com.ob.dailyReport.dao.EmployeeTaskDao;
import com.ob.dailyReport.dao.PlanDao;
import com.ob.dailyReport.dao.ProjectDao;
import com.ob.dailyReport.model.EmployeeReport;
import com.ob.dailyReport.model.Project;
import com.ob.dailyReport.model.ProjectReport;
import com.ob.dailyReport.model.TaskRecord;
import com.ob.dailyReport.model.TaskStatus;

public class TeamReportHistoryService {

	/**
	 * 
	 * @param team
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public static List<ProjectReport> getTeamReport(String team, Date date) throws SQLException {
		List<TaskRecord> allTaskRecords = new ArrayList<TaskRecord>();
		List<String> employeeList = EmployeeDao.getEmployeeNameList(team, null);
		for (String employee : employeeList) {
			List<TaskRecord> employeeTasks = EmployeeTaskDao.getEmployeeTasks(employee, team, date);
			allTaskRecords.addAll(employeeTasks);
		}
		List<ProjectReport> projectList = convertList2Report(allTaskRecords, team);
		sortProject(projectList, team);
		return projectList;
	}

	private static void sortProject(List<ProjectReport> projectList, final String team) {
		Collections.sort(projectList, new Comparator<ProjectReport>() {
			public int compare(ProjectReport projectReport1, ProjectReport projectReport2) {
				try {
					String name1 = projectReport1.getProjectName();
					String name2 = projectReport2.getProjectName();
					Project project1 = ProjectDao.getProject(name1, team);
					Project project2 = ProjectDao.getProject(name2, team);
					return Integer.compare(project1.getLevel(), project2.getLevel());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}

	/**
	 * 
	 * @param historyList
	 * @return
	 * @throws SQLException
	 */
	private static List<ProjectReport> convertList2Report(List<TaskRecord> historyList, String team)
			throws SQLException {
		List<ProjectReport> projectList = new ArrayList<ProjectReport>();
		for (TaskRecord eh : historyList) {
			try {
				fillProjectList(team, projectList, eh);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectList;
	}

	/**
	 * 
	 * @param projectList
	 * @param eh
	 * @throws SQLException
	 */
	private static void fillProjectList(String team, List<ProjectReport> projectList, TaskRecord eh)
			throws SQLException {
		String projectName = eh.getProjectName();
		ProjectReport projectReport = null;
		for (ProjectReport project : projectList) {
			// project entity exist
			if (project.getProjectName().equals(projectName)) {
				projectReport = project;
				break;
			}
		}
		if (projectReport == null) {
			Project project = ProjectDao.getProject(projectName, team);
			projectReport = new ProjectReport();
			projectReport.setProjectName(projectName);
			projectReport.setRfa(project.getRfa());
			projectReport.setStatus(project.getStatus());
			projectList.add(projectReport);
		}
		fillEmployeeList(team, projectReport.getEmployeeRList(), eh);
	}

	/**
	 * 
	 * @param employeeList
	 * @param eh
	 * @return
	 * @throws SQLException
	 */
	private static EmployeeReport fillEmployeeList(String team, List<EmployeeReport> employeeList, TaskRecord eh)
			throws SQLException {
		String employeeName = eh.getEmployeeName();
		String role = eh.getRole();
		String project = eh.getProjectName();
		Date date = eh.getDate();
		TaskStatus task = eh.getStatus();
		EmployeeReport employeeReport = null;

		for (EmployeeReport report : employeeList) {
			if (report.getName().equals(employeeName)) {
				employeeReport = report;
				break;
			}
		}

		if (employeeReport == null) {
			employeeReport = new EmployeeReport();
			employeeReport.setName(employeeName);
			employeeReport.setTeam(team);
			employeeReport.setRole(role);
			employeeReport.setPlans(PlanDao.getPlan(employeeName, team, project, date));
			employeeList.add(employeeReport);
		}
		employeeReport.addStatus(task);
		return employeeReport;
	}

}
