package com.obss.radar.searcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.obss.radar.searcher.dao.SiteDAO;
import com.obss.radar.searcher.po.Site;

@Component
public class SiteService{
	private MemoryCache cache = new MemoryCache(15*60);

	@Autowired
	private SiteDAO siteDAO;
	
	public Site getSiteById(String siteId){
		String cacheKey = "siteId_"+siteId;
		if(cache.contains(cacheKey)){
			return (Site) cache.get(cacheKey);
		}else{
			Site site = siteDAO.getSite(siteId);
			cache.put(cacheKey, site);
			return site;
		}
	}
}
