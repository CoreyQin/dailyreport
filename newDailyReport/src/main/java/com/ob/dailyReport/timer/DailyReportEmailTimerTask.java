package com.ob.dailyReport.timer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.ob.dailyReport.dao.EmployeeDao;
import com.ob.dailyReport.dao.TeamDao;
import com.ob.dailyReport.email.EmailSender;
import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.service.ReportGenerateService;
import com.ob.dailyReport.util.ReportFileManager;

public class DailyReportEmailTimerTask extends TimerTask {

	private Map<String, String> team_leaderMap = new HashMap<String, String>();

	@Override
	public void run() {
		try {
			// generate team report for all teams and send email the team leader
			List<String> teamList = TeamDao.getTeamList();
			for (String team : teamList) {
				String teamLeader = EmployeeDao.getTeamLeader(team);
				team_leaderMap.put(team, teamLeader);
			}
			sendReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendReport() {
		for (String team : team_leaderMap.keySet()) {
			try {
				File report = ReportGenerateService.generateReport(ReportFileManager.getInstance().getReportPath(),
						team, new Date());
				String teamLeader = team_leaderMap.get(team);
				// get email address of team leader
				Employee employee = EmployeeDao.getEmployee(teamLeader, team);
				String to = employee.getEmail();
				EmailSender.sendReport(to, report);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		DailyReportEmailTimerTask task = new DailyReportEmailTimerTask();
		task.run();
	}
}
