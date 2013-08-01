package com.obss.radar.crawler.task;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.dao.SystemMonitorDAO;
import com.obss.radar.crawler.po.SystemMonitor;

@Component
public class SystemMonitorTask {
	protected static Logger logger = LoggerFactory.getLogger(SystemMonitorTask.class);
	
	@Autowired
	private SystemMonitorDAO systemMonitorDAO;
	
	public void doTask(){
		try {
			SystemMonitor crawler = new SystemMonitor();
			crawler.setType(SystemMonitor.CRAWLER);
			crawler.setValue("1");
			systemMonitorDAO.saveorupdateMonitor(crawler);
			logger.info("crawler state updated");
			
			SystemMonitor db = new SystemMonitor();
			db.setType(SystemMonitor.DB);
			db.setValue("1");
			systemMonitorDAO.saveorupdateMonitor(db);
			logger.info("db state updated");
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
	}
}
