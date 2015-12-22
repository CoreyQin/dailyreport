package com.ob.dailyReport.service;

import java.sql.SQLException;
import java.util.List;

import com.ob.dailyReport.dao.ProjectDao;
import com.ob.dailyReport.model.Project;

public class ProjectService {

	public static List<Project> getProjectList(String team) throws SQLException {
		List<Project> projectList = ProjectDao.getProjectList(team);
		return projectList;
	}

	public static void saveProject(Project project) throws SQLException {
		String projectName = project.getName();
		String team = project.getTeam();
		if (ProjectDao.isExsit(projectName,team)) {
			ProjectDao.updateProject(project);
		} else {
			ProjectDao.addProject(project);
		}

	}

	public static void removeProject(Project project) throws SQLException {
		ProjectDao.removeProject(project);
	}
}
