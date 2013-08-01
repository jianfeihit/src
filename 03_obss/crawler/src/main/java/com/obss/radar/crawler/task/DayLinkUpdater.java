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
public class DayLinkUpdater {
	protected static Logger logger = LoggerFactory.getLogger(DayLinkUpdater.class);

	private static final String DAYLY = "2";
	@Autowired
	private SiteDAO siteDAO;
	@Autowired
	private LinkDAO linkDAO;

	public void doTask() {
		List<Site> sites = siteDAO.getSiteByMonitorExp(DAYLY);
		if (sites == null)
			return;
		for (Site site : sites) {
			linkDAO.updateLinkBySiteId(site.getId());
		}
		logger.info("daylink updated.count={}",sites.size());
	}
}
