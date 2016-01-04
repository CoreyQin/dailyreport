package com.ob.dailyReport.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ob.dailyReport.dao.EmployeeDao;
import com.ob.dailyReport.dao.EmployeeTaskDao;
import com.ob.dailyReport.util.Constant;

public class TeamSummaryService {

	/**
	 * 
	 * @param team
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, List<String>> getEmployeeWithTaskList(String team, Date date) throws SQLException {
		Map<String, List<String>> summaryMap = new HashMap<String, List<String>>();
		List<String> employeeWithTasksList = new ArrayList<String>();
		List<String> employeeWithoutTasksList = new ArrayList<String>();
		summaryMap.put(Constant.hasTask, employeeWithTasksList);
		summaryMap.put(Constant.noTask, employeeWithoutTasksList);

		List<String> allEmployeeList = EmployeeDao.getEmployeeNameList(team, null);
		for (String employeeName : allEmployeeList) {
			boolean hasTask = EmployeeTaskDao.hasReportTasks(employeeName, team, date);
			if (hasTask) {
				employeeWithTasksList.add(employeeName);
			} else {
				employeeWithoutTasksList.add(employeeName);
			}
		}
		
		return summaryMap;
	}

}
