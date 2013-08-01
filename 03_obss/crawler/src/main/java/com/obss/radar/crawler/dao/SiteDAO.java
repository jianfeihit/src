package com.obss.radar.crawler.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.obss.radar.crawler.po.Site;

@Component
public class SiteDAO extends SqlMapClientDaoSupport{
    @Autowired
	public SiteDAO(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
   
    public Site getSite(String id){
    	return (Site)getSqlMapClientTemplate().queryForObject("site.getSite",id);
    }
    
    @SuppressWarnings("unchecked")
	public List<Site> getSiteByMonitorExp(String monitorExp){
    	return getSqlMapClientTemplate().queryForList("site.getSiteByMonitorExp",monitorExp);
    }
    
}
