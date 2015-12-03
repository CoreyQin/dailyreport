package com.ob.dailyReport.timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TaskListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		TimerManager.startTimer();
	}

}
