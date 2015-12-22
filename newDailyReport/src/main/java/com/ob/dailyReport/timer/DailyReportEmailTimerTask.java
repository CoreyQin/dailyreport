package com.ob.dailyReport.timer;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ob.dailyReport.dao.EmployeeDao;
import com.ob.dailyReport.dao.TeamDao;
import com.ob.dailyReport.email.EmailSender;
import com.ob.dailyReport.model.Employee;
import com.ob.dailyReport.service.ReportGenerateService;
import com.ob.dailyReport.util.ReportFileManager;

public class DailyReportEmailTimerTask extends TimerTask {

	private static Logger log = Logger.getLogger(DailyReportEmailTimerTask.class);

	private Map<String, String> team_leaderMap = new HashMap<String, String>();
	
	private Map<String, String> team_DMMap = new HashMap<String, String>();

	@Override
	public void run() {
		if (isWeekend(Calendar.getInstance())) {
			log.info("this day is weekend, task will not run");
			return;
		}
		try {
			// generate team report for all teams and send email the team leader
			List<String> teamList = TeamDao.getTeamList();
			for (String team : teamList) {
				String teamLeader = EmployeeDao.getTeamLeader(team);
				team_leaderMap.put(team, teamLeader);
				
				String dm = EmployeeDao.getDM(team);
				team_DMMap.put(team, dm);
			}
			sendReport();
		} catch (Exception e) {
			log.error(e);
		}
	}

	private boolean isWeekend(Calendar calendar) {
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		if (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	private void sendReport() {
		for (String team : team_leaderMap.keySet()) {
			try {
				File report = ReportGenerateService.generateReport(ReportFileManager.getInstance().getReportPath(),
						team, new Date());
				List<String> receiverList = getReceiverList(team);
				log.info("start to send to : " + receiverList);
				EmailSender.sendReport(receiverList, report);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
	
	private List<String> getReceiverList(String team){
		List<String> receiverList = new ArrayList<String>();
		String teamLeaderName = team_leaderMap.get(team);
		log.info("get the team leader of team '" + team + "' is : " + teamLeaderName);
		try {
			Employee teamLeader = EmployeeDao.getEmployee(teamLeaderName, team);
			if(teamLeader!=null && teamLeader.getEmail()!=null){
				receiverList.add(teamLeader.getEmail());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		String dmName = team_DMMap.get(team);
		log.info("get the dm of team '" + team + "' is : " + dmName);
		try {
			Employee dm = EmployeeDao.getEmployee(dmName, team);
			if(dm!=null && dm.getEmail()!=null){
				receiverList.add(dm.getEmail());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return receiverList;
	}

	public static void main(String[] args) {
		DailyReportEmailTimerTask task = new DailyReportEmailTimerTask();
		task.run();
	}
}
