package com.ob.dailyReport.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerManager {

	// time period
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	
//	private static final long PERIOD_DAY = 60 * 1000;

	public static void startTimer() {
		Calendar calendar = Calendar.getInstance();
		// 6:00 PM
		calendar.set(Calendar.HOUR_OF_DAY, 13);
		calendar.set(Calendar.MINUTE, 34);
		calendar.set(Calendar.SECOND, 30);

		Date startDate = calendar.getTime(); 

		// 如果第一次执行定时任务的时间 小于 当前的时间
		// 此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (startDate.before(new Date())) {
			startDate = addDay(startDate, 1);
		}

		Timer timer = new Timer();
		DailyReportEmailTimerTask task = new DailyReportEmailTimerTask();
		timer.schedule(task, startDate, PERIOD_DAY);
	}

	private static Date addDay(Date date, int num) {
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date);
		newDate.add(Calendar.DAY_OF_MONTH, num);
		return newDate.getTime();
	}
	
	public static void main(String[] args){
		TimerManager manager = new TimerManager();
		
	}

}
