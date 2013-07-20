package com.obss.radar.crawler.service;

import java.net.MalformedURLException;

import org.springframework.stereotype.Component;

import com.obss.radar.crawler.po.Site;
import com.obss.radar.crawler.util.HTTPUtils;

@Component
public class URLJudger {
	
	public boolean judge(String newLink, Site site) throws MalformedURLException{
		String host = HTTPUtils.getMainHost(newLink);
		return host.equalsIgnoreCase(site.getMainHost());
	}
}
