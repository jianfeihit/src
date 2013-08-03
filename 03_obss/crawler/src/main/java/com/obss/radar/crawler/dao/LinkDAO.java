package com.obss.radar.crawler.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.obss.radar.crawler.po.Link;

@Component
public class LinkDAO extends SqlMapClientDaoSupport {

	@Autowired
	public LinkDAO(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	public void saveLink(Link link) throws SQLException {
		Link dbLink = this.getLinkByLinkMD5(link.getLinkMD5());
		if (dbLink == null) {
			getSqlMapClientTemplate().insert("link.saveLink", link);
		}
	}
	
	public void updateLink(Link link) {
		getSqlMapClientTemplate().update("link.updateLink", link);
	}

	@SuppressWarnings("unchecked")
	public List<Link> getUndoLink(List<String> siteList,int fetchSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("siteList", siteList);
		paramMap.put("fetchSize", fetchSize);
		return getSqlMapClientTemplate().queryForList("link.getUndoLink", paramMap);
	}

	public Link getLinkByLinkMD5(String linkmd5) {
		return (Link) getSqlMapClientTemplate().queryForObject("link.getLinkByLinkMD5",linkmd5);
	}
	
	public Link getLinkByLinkId(String id) {
		return (Link) getSqlMapClientTemplate().queryForObject("link.getLinkByLinkId",id);
	}
	
	public void updateLinkBySiteId(String siteId) {
		getSqlMapClientTemplate().update("link.updateLinkBySiteId", siteId);
	}
}
