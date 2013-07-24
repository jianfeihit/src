package com.obss.radar.crawler.service;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.obss.radar.crawler.po.Site;
import com.obss.radar.crawler.util.HTTPUtils;

@Component
public class URLJudger {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public boolean judge(String newLink, Site site) throws MalformedURLException{
		String host = HTTPUtils.getMainHost(newLink);
		boolean result = host.equalsIgnoreCase(site.getMainHost());
		logger.debug("url过滤.host={},mainHost={},result={}",host,site.getMainHost(),result);
		return result;
	}
}
