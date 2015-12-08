package com.ob.dailyReport.service;

import java.sql.SQLException;
import java.util.List;

import com.ob.dailyReport.dao.EmployeeDao;
import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.model.PageBean;

public class EmployeeService {

	public static List<Employee> getEmployeeList(String team, PageBean pageBean) throws SQLException {
		List<Employee> employeeList = EmployeeDao.getEmployeeList(team, pageBean);
		return employeeList;
	}

	public static List<String> getEmployeeNameList(String team, PageBean pageBean) throws SQLException {
		List<String> employeeList = EmployeeDao.getEmployeeNameList(team, pageBean);
		return employeeList;
	}

	public static int employeeCount(String team) throws SQLException {
		return EmployeeDao.employeeCount(team);
	}

	public static boolean isEmployeeExsit(String employeeName) throws SQLException {
		Employee employee = EmployeeDao.getEmployee(employeeName);
		if (employee != null) {
			return true;
		}
		return false;
	}

	public static void saveEmployee(Employee employee) throws SQLException {
		if (isEmployeeExsit(employee.getName())) {
			EmployeeDao.updateEmployee(employee);
		} else {
			EmployeeDao.addEmployee(employee);
		}
	}
	
	public static void removeEmployee(Employee employee) throws SQLException {
		String name = employee.getName();
		EmployeeDao.removeEmployee(name);
	}
	
	public static void removeEmployee(String name)throws SQLException {
		EmployeeDao.removeEmployee(name);
	}

}
