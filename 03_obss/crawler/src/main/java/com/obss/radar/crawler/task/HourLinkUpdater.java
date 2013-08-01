package com.obss.radar.crawler.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.dao.LinkDAO;
import com.obss.radar.crawler.dao.SiteDAO;
import com.obss.radar.crawler.po.Site;

@Component
public class HourLinkUpdater {
	protected static Logger logger = LoggerFactory.getLogger(HourLinkUpdater.class);
	
	private static final String HOURLY = "1";
	@Autowired
	private SiteDAO siteDAO;
	@Autowired
	private LinkDAO linkDAO;
	
	public void doTask(){
		List<Site> hourlySites = siteDAO.getSiteByMonitorExp(HOURLY);
		if(hourlySites == null) return;
		for(Site site : hourlySites){
			linkDAO.updateLinkBySiteId(site.getId());
		}
		logger.info("hourlink updated.count={}",hourlySites.size());
	}
}
