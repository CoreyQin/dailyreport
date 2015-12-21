package com.ob.dailyReport.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.apache.log4j.Logger;

public class TimerManager {

	// time period
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	
	private static Logger log = Logger.getLogger(TimerManager.class);
	

	public static void startTimer() {
		Timer timer = new Timer();
		DailyReportEmailTimerTask task = new DailyReportEmailTimerTask();
		timer.schedule(task, getStartDate(19, 00, 00), PERIOD_DAY);
		log.info("start a timer, task will be execute at 19:00 every day");

		Timer timer2 = new Timer();
		timer2.schedule(new DailyReportEmailTimerTask(), getStartDate(21, 00, 00), PERIOD_DAY);
		log.info("start a timer, task will be execute at 21:00 every day");
	}

	private static Date getStartDate(int hours, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		// 6:00 PM
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);

		Date startDate = calendar.getTime();

		if (startDate.before(new Date())) {
			startDate = addDay(startDate, 1);
		}
		return startDate;
	}

	private static Date addDay(Date date, int num) {
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date);
		newDate.add(Calendar.DAY_OF_MONTH, num);
		return newDate.getTime();
	}

	public static void main(String[] args) {
		TimerManager manager = new TimerManager();

	}

}
